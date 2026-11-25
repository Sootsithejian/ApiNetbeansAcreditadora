package mx.acreditadora.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import mx.acreditadora.config.Database;
import mx.acreditadora.models.Cliente;

public class ClienteController {

    public List<Cliente> getClientes() throws SQLException{

        List<Cliente> lista = new ArrayList<>();

        String sql = "SELECT id_cliente, nombre, primer_apellido, segundo_apellido, "
                   + "curp, fecha_registro FROM clientes";

        try (Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {
                
                while(rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setPrimerApellido(rs.getString("primer_apellido"));
                    cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                    cliente.setCurp(rs.getString("curp"));
                    cliente.setFechaRegistro(rs.getString("fecha_registro"));
                    lista.add(cliente);
                }
            } 
            return lista;
    
    }

    public boolean existeCURP(String curp) throws SQLException {
        String sql = "SELECT COUNT(*) FROM clientes WHERE curp = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

                pstm.setString(1, curp);

                try (ResultSet rs = pstm.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
             }
    }


    public int registrarCliente(Cliente cliente) throws SQLException{

        if(cliente.getCurp() != null && !cliente.getCurp().isBlank()){
            if(existeCURP(cliente.getCurp())){
                return -1;
            }
        }

        String sql =  "INSERT INTO clientes (nombre, primer_apellido, segundo_apellido, "
                   + "curp, fecha_registro) "
                   + "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (Connection conn = Database.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                pstm.setString(1, cliente.getNombre());
                pstm.setString(2, cliente.getPrimerApellido());

                if(cliente.getSegundoApellido() != null && !cliente.getSegundoApellido().isBlank()){
                    pstm.setString(3, cliente.getSegundoApellido());
                } else {
                    pstm.setNull(3, Types.VARCHAR);
                }
                if (cliente.getCurp() != null && !cliente.getCurp().isBlank()) {
                    pstm.setString(4, cliente.getCurp());
                } else {
                    pstm.setNull(4, Types.VARCHAR);
                }
               
                int filas = pstm.executeUpdate();
                if(filas == 0) {
                    return 0;
                }
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
             }
    }

    public Cliente getClientePorId(int idCliente) throws SQLException {

        String sql = """
                     SELECT id_cliente, nombre, primer_apellido,
                            segundo_apellido, curp, fecha_registro
                     FROM clientes
                     WHERE id_cliente = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idCliente);

                try(ResultSet rs = pstm.executeQuery()){
                    if (rs.next()) {
                        Cliente cliente = new Cliente();

                        cliente.setIdCliente(rs.getInt("id_Cliente"));
                        cliente.setNombre(rs.getString("nombre"));
                        cliente.setPrimerApellido(rs.getString("primer_apellido"));
                        cliente.setSegundoApellido(rs.getString("segundo_apellido"));
                        cliente.setCurp(rs.getString("curp"));
                        cliente.setFechaRegistro(rs.getString("fecha_registro"));

                        return cliente;
                    }
                }
            }
            return null;
    }
    
}
