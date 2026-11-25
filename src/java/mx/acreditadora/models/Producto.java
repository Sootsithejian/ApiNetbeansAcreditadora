package mx.acreditadora.models;

public class Producto {
    public int idProducto;
    public double monto;
    public int plazo;
    public double pagoSemanal;


    public Producto(){

    }

    public Producto(
        int idProducto,
        double monto,
        int plazo,
        double pagoSemanal
    ){
        this.idProducto = idProducto;
        this.monto = monto;
        this.plazo = plazo;
        this.pagoSemanal = pagoSemanal;
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
    public double getPagoSemanal(){
        return pagoSemanal;
    }
    public void setPagoSemanal(double pagoSemanal){
        this.pagoSemanal = pagoSemanal;
    }
}
