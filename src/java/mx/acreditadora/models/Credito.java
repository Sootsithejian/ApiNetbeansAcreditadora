package mx.acreditadora.models;

public class Credito {
    public int idCredito;
    public int idCliente;
    public int idProducto;
    public double monto;
    public int plazo;
    public String fechaRegistro;

    public Credito(){

    }

    public Credito (
        int idCredito,
        int idCliente,
        int idProducto,
        double monto,
        int plazo,
        String fechaRegistro
    ){
        this.idCredito = idCredito;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.monto = monto;
        this.plazo = plazo;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCredito(){
        return idCredito;
    }
    public void setIdCredito(int idCredito){
        this.idCredito = idCredito;
    }
    public int getIdCliente(){
        return idCliente;
    }
    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
    public int getIdProducto(){
        return idProducto;
    }
    public void setIdProducto(int idProducto){
        this.idProducto = idProducto;
    }
    public double getMonto(){
        return monto;
    }
    public void setMonto(double monto){
        this.monto = monto;
    }
    public int getPlazo(){
        return plazo;
    }
    public void setPlazo(int plazo){
        this.plazo = plazo;
    }
    public String getFechaRegistro(){
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }
}
