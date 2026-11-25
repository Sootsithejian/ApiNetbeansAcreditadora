package mx.acreditadora.models;

public class Cliente {
    public int idCliente;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public String curp;
    public String fechaRegistro;


    public Cliente(){

    }

    public Cliente(
        int idCliente,
        String nombre,
        String primerApellido,
        String segundoApellido,
        String curp,
        String fechaRegistro
    ){
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.curp = curp;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdCliente(){
        return idCliente;
    }
    public void setIdCliente(int idCliente){
        this.idCliente = idCliente;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getPrimerApellido(){
        return primerApellido;
    }
    public void setPrimerApellido(String primerApellido){
        this.primerApellido = primerApellido;
    }
    public String getSegundoApellido(){
        return segundoApellido;
    }
    public void setSegundoApellido(String segundoApellido){
        this.segundoApellido = segundoApellido;
    }
    public String getCurp(){
        return curp;
    }
    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getFechaRegistro(){
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }
}