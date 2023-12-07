package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Repository
public class UsuarioJDBCRepository implements IUsuarioRepository{
    @Value("${db_url}")
    private String connUrl;


    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (`id`, `activo`, `alta`, `email`, `nombre`)  values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            stmt.setBoolean(1, usuario.isActivo());
            stmt.setDate(2, Date.valueOf(usuario.getAlta()));
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                usuario.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Usuario creado erroneamente!!!");
            }

        } catch (SQLException e) {
            throw new SQLException("Usuario no creado!!");
        }

        return usuario;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario set email=?, nombre=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
             throw new SQLException("Usuario no actualizado!!");
        }

        return usuario;
    }

    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if (rows <= 0) {
                throw new SQLException("Usuario no borrado, no encontrado!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        Set<Usuario> usuarios = new HashSet<>();

        String sql = "SELECT * FROM usuario where id!=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                // ordenes sql
                PreparedStatement pstm = conn.prepareStatement(sql);

        ) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            int cont = 0;
            while (rs.next() && cont <= max) {
                LocalDate alta = rs.getDate("alta").toLocalDate();
                usuarios.add(new Usuario(rs.getInt("id"),rs.getString("nombre"), rs.getString("email"), alta, rs.getBoolean("activo")));
                cont++;
            }
        }

        return usuarios;
    }
    @Override
    public Usuario buscar(Integer id) throws SQLException {
        Usuario usu = null;
        String sql = "SELECT u.* FROM usuario u WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(connUrl);
                // ordenes sql
                PreparedStatement pstm = conn.prepareStatement(sql);

        ) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                LocalDate alta = LocalDate.parse(rs.getString("alta"));
                usu = new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"),alta,rs.getBoolean("activo"));
            }else {
                throw new SQLException("Usuario no encontrado!!");
            }
        }

        return usu;
    }
}
