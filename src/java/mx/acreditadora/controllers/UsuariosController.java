package mx.acreditadora.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import java.sql.Statement;
import mx.acreditadora.utils.passwordUtil;

import mx.acreditadora.config.Database;
import mx.acreditadora.models.Usuarios;

public class UsuariosController {
    
    public List<Usuarios> getUsuarios() throws SQLException {

        List<Usuarios> lista = new ArrayList<>();

        String sql = "SELECT id_usuario, nombre, primer_apellido, segundo_apellido," 
                    + "usuario, curp, email, fecha_registro FROM users";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {
                
                while(rs.next()) {
                    Usuarios usuarios = new Usuarios();
                    usuarios.setIdUsuario(rs.getInt("id_usuario"));
                    usuarios.setNombre(rs.getString("nombre"));
                    usuarios.setPrimerApellido(rs.getString("primer_apellido"));
                    usuarios.setSegundoApellido(rs.getString("segundo_apellido"));
                    usuarios.setUsuario(rs.getString("usuario"));
                    usuarios.setCurp(rs.getString("curp"));
                    usuarios.setEmail(rs.getString("email"));
                    usuarios.setFechaRegistro(rs.getString("fecha_registro"));
                    lista.add(usuarios);
                }
            }
            return lista;
    }

    public int registrarUsuario(Usuarios usuario) throws SQLException {
        
        String sql = """
        INSERT INTO users (
            nombre, primer_apellido, segundo_apellido,
            usuario, pass, curp, email, fecha_registro
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)
    """;

        String passwordHash = passwordUtil.hashPassword(usuario.getPass());

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstm.setString(1, usuario.getNombre());
                pstm.setString(2, usuario.getPrimerApellido());
                pstm.setString(3, usuario.getSegundoApellido());
                pstm.setString(4, usuario.getUsuario());
                pstm.setString(5, passwordHash);
                pstm.setString(6, usuario.getCurp());
                pstm.setString(7, usuario.getEmail());

                int filas = pstm.executeUpdate();
                if(filas == 0) {
                    return 0;
                }
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
            }
    }

    public Usuarios login(String username, String password) throws SQLException {

        String sql = """
                     SELECT id_usuario, nombre, primer_apellido, segundo_apellido,
                     usuario, pass, curp, email, fecha_registro
                     FROM users
                     WHERE usuario = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
                
                pstm.setString(1, username);
                try (ResultSet rs = pstm.executeQuery()) {
                    
                    if(rs.next()) {
                        String passwordHash = rs.getString("pass");

                        if(passwordUtil.checkPassword(password, passwordHash)) {
                            Usuarios usuario = new Usuarios();

                            usuario.setIdUsuario(rs.getInt("id_usuario"));
                            usuario.setNombre(rs.getString("nombre"));
                            usuario.setPrimerApellido(rs.getString("primer_apellido"));
                            usuario.setSegundoApellido(rs.getString("segundo_apellido"));
                            usuario.setUsuario(rs.getString("usuario"));
                            usuario.setCurp(rs.getString("curp"));
                            usuario.setEmail(rs.getString("email"));
                            usuario.setFechaRegistro(rs.getString("fecha_registro"));

                            return usuario;
                        }
                    }
                }
            }
            return null;
    }
}
