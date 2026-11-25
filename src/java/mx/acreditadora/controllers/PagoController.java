package mx.acreditadora.controllers;

import mx.acreditadora.config.Database;
import mx.acreditadora.models.Pago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PagoController {
    
    public List<Pago> getPagosPorCredito(int idCredito) throws SQLException {
        
        List<Pago> lista = new ArrayList<>();

        String sql = """
                     SELECT id_pago, id_credito, monto, fecha_pago
                     FROM pagos
                     WHERE id_credito = ?
                     """;

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, idCredito);

                try(ResultSet rs = pstm.executeQuery()) {
                    while (rs.next()) {
                        Pago pago = new Pago();

                        pago.setIdPago(rs.getInt("id_pago"));
                        pago.setIdCredito(rs.getInt("id_credito"));
                        pago.setMonto(rs.getDouble("monto"));
                        pago.setFechaPago(rs.getString("fecha_pago"));
                        lista.add(pago);
                        
                    }
                }
            }
            return lista;
    }

    public int registrarPago(Pago pago) throws SQLException {

        if (pago.idCredito <= 0 || pago.monto <= 0) {
            return 0;
        }

        String sql = "INSERT INTO pagos (id_credito, monto, fecha_pago)"
                    + "VALUES ( ?, ?, CURRENT_DATE)";

        try(Connection conn = Database.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                pstm.setInt(1, pago.idCredito);
                pstm.setDouble(2, pago.monto);

            
                int filas = pstm.executeUpdate();
                if(filas == 0) {
                    return 0;
                }
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    return rs.next() ? rs.getInt(1) : 0;
                }
            }

    }
}
