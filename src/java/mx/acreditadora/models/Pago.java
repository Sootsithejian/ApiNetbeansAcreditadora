package mx.acreditadora.models;

public class Pago {
    public int idPago;
    public int idCredito;
    public double monto;
    public String fechaPago;  

    public Pago(){

    }
    
    public Pago(
        int idPago,
        int idCredito,
        double monto,
        String fechaPago
    ){
        this.idPago = idPago;
        this.idCredito = idCredito;
        this.monto = monto;
        this.fechaPago = fechaPago;

    }

    public int getIdPago(){
        return idPago;
    }
    public void setIdPago(int idPago){
        this.idPago = idPago;
    }
    public int getIdCredito(){
        return idCredito;
    }
    public void setIdCredito(int idCredito){
        this.idCredito = idCredito;
    }
    public double getMonto(){
        return monto;
    }
    public void setMonto(double monto){
        this.monto = monto;
    }
    public String getFechaPago(){
        return fechaPago;
    }
    public void setFechaPago(String fechaPago){
        this.fechaPago = fechaPago;
    }
}

