package mx.acreditadora.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mx.acreditadora.config.Database;
import mx.acreditadora.models.Producto;

public class ProductoController {
    
    public List<Producto> getProductos() throws SQLException {

        List<Producto> lista = new ArrayList<>();

        String sql = "SELECT id_producto, monto, plazo, pago_semanal "
                    + "FROM productos";
        
        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {

                while(rs.next()) {
                    Producto productos = new Producto();
                    productos.setIdProducto(rs.getInt("id_producto"));
                    productos.setMonto(rs.getDouble("monto"));
                    productos.setPlazo(rs.getInt("plazo"));
                    productos.setPagoSemanal(rs.getDouble("pago_semanal"));
                    lista.add(productos);
                }
            }
            return lista;
    }

    public int registrarProducto(Producto producto) throws SQLException {

        String sql = """
                     INSERT INTO productos (monto, plazo, pago_semanal)
                     VALUES (?, ?, ?)
                     """;

        try (Connection conn = Database.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                
               if (producto.getMonto() <= 0 || producto.getPlazo() <= 0 || producto.getPagoSemanal() <= 0 ) {
                    return 0;
                }

                pstm.setDouble(1, producto.getMonto());

                pstm.setInt(2, producto.getPlazo());
                pstm.setDouble(3, producto.getPagoSemanal());

                int filas = pstm.executeUpdate();
                if(filas == 0) {
                    return 0;
                }
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
             }
    }

    public Producto getProductoPorId(int idProducto) throws SQLException{

        String sql = """
                     SELECT id_producto, monto, plazo, pago_semanal
                     FROM productos
                     WHERE id_producto = ?
                     """;
        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {

                pstm.setInt(1, idProducto);

                try(ResultSet rs = pstm.executeQuery()){
                    if(rs.next()) {
                        Producto producto = new Producto();

                        producto.setIdProducto(rs.getInt("id_producto"));
                        producto.setMonto(rs.getDouble("monto"));
                        producto.setPlazo(rs.getInt("plazo"));
                        producto.setPagoSemanal(rs.getDouble("pago_semanal"));

                        return producto;
                    }
                }
            }
            return null;
    }
}


