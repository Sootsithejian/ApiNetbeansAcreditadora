package mx.acreditadora.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import mx.acreditadora.config.Database;
import mx.acreditadora.models.Credito;

public class CreditoController {
    
    public List<Credito> getCreditos() throws SQLException {

        List<Credito> lista = new ArrayList<>();

        String sql = "SELECT id_credito, id_cliente, id_producto, monto, plazo, "
                    + "fecha_registro FROM creditos";

        try (Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {

                while(rs.next()) {
                    Credito credito = new Credito();
                    credito.setIdCredito(rs.getInt("id_credito"));
                    credito.setIdCliente(rs.getInt("id_cliente"));
                    credito.setIdProducto(rs.getInt("id_producto"));
                    credito.setMonto(rs.getDouble("monto"));
                    credito.setPlazo(rs.getInt("plazo"));
                    credito.setFechaRegistro(rs.getString("fecha_registro"));
                    lista.add(credito);
                }
                return lista;
            }
    } 
    
    public List<Credito> getCreditosPorCliente(int idCliente) throws SQLException{

        List<Credito> lista = new ArrayList<>();

        String sql = """
                     SELECT id_credito, id_producto, monto, plazo, fecha_registro
                     FROM creditos
                     WHERE id_cliente = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idCliente);

                try(ResultSet rs = pstm.executeQuery()) {
                    while(rs.next()) {
                        Credito credito = new Credito();

                        credito.setIdCliente(idCliente);
                        credito.setIdCredito(rs.getInt("id_credito"));
                        credito.setIdProducto(rs.getInt("id_producto"));
                        credito.setMonto(rs.getDouble("monto"));
                        credito.setPlazo(rs.getInt("plazo"));
                        credito.setFechaRegistro(rs.getString("fecha_registro"));
                        lista.add(credito);


                    }
                }
            }
            return lista;
    }
    
    public Credito getCreditoPorId(int idCredito) throws SQLException{

        String sql = """ 
                     SELECT id_credito, id_cliente, id_producto,
                            monto, plazo, fecha_registro
                     FROM creditos
                     WHERE id_credito = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

                pstm.setInt(1, idCredito);

                try(ResultSet rs = pstm.executeQuery()) {
                    if (rs.next()) {
                        Credito credito = new Credito();

                        credito.setIdCredito(rs.getInt("id_credito"));
                        credito.setIdCliente(rs.getInt("id_cliente"));
                        credito.setIdProducto(rs.getInt("id_producto"));
                        credito.setMonto(rs.getDouble("monto"));
                        credito.setPlazo(rs.getInt("plazo"));
                        credito.setFechaRegistro(rs.getString("fecha_registro"));
                        
                        return credito;
                    }
                }
            }
            return null;
    }

    public int registrarCredito(Credito credito) throws SQLException {

        if(credito.getIdCliente() <= 0 || credito.getIdProducto() <= 0){
            return 0;
        }

        String sql = """
                     INSERT INTO creditos (id_cliente, id_producto, monto, plazo, fecha_registro)
                     SELECT ?, id_producto, monto, plazo, CURRENT_DATE
                     FROM productos
                     WHERE id_producto = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pstm.setInt(1, credito.getIdCliente());
                pstm.setInt(2, credito.getIdProducto());

                int filas = pstm.executeUpdate();
                if (filas == 0) {
                    return 0;
                }
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
            }

    }

    
}
