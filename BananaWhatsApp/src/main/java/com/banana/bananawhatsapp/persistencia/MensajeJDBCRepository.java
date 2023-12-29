package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
//@Repository
public class MensajeJDBCRepository implements IMensajeRepository{
    @Value("${db.conn}")
    private String connUrl;

    @Autowired
    private IUsuarioRepository usuRepository;

    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException{
         String sql = "INSERT INTO mensaje (`id`, `cuerpo`, `fecha`, `from_user`, `to_user`)  values (NULL,?,?,?,?)";

         Connection conn = DriverManager.getConnection(connUrl);
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, mensaje.getCuerpo());
            stmt.setDate(2, Date.valueOf(mensaje.getFecha()));
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Mensaje creado erroneamente!!!");
            }

        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario remitente, Usuario destinatario) throws SQLException{
        List<Mensaje> listADevolver = new ArrayList<>();
        String sql = "SELECT * FROM mensaje m WHERE from_user=? and to_user=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                // ordenes sql
                PreparedStatement pstm = conn.prepareStatement(sql);

        ) {
            pstm.setInt(1, remitente.getId());
            pstm.setInt(2, destinatario.getId());
            ResultSet rs = pstm.executeQuery();
            Usuario usuarioFrom = null;
            Usuario usuarioTo = null;
            try {
                usuarioFrom = usuRepository.buscar(remitente.getId());
                usuarioTo = usuRepository.buscar(destinatario.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            while (rs.next()) {
                LocalDate fecha = rs.getDate("fecha").toLocalDate();
                listADevolver.add(new Mensaje(rs.getInt("m.id"), usuarioFrom, usuarioTo, rs.getString("cuerpo"),fecha ));
            }
        }

        return listADevolver;
    }

    @Override
    public boolean borrarTodos(Usuario remitente, Usuario destinatario) throws SQLException{
        String sql = "DELETE FROM mensaje WHERE from_user=? and to_user=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, remitente.getId());
            stmt.setInt(2, destinatario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                System.out.println("No se han encontrado mensajes para borrar!!!");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }
}
