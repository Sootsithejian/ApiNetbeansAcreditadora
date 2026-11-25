package mx.acreditadora.models;

public class Usuarios {
    public int idUsuario;
    public String nombre;
    public String primerApellido;
    public String segundoApellido;
    public String usuario;
    public String pass;
    public String curp;
    public String email;
    public String fechaRegistro;

    public Usuarios(){

    }

    public Usuarios(
        int idUsuario,
        String nombre,
        String primerApellido,
        String segundoApellido,
        String usuario,
        String pass,
        String curp,
        String email,
        String fechaRegistro
    ){
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.usuario = usuario;
        this.pass = pass;
        this.curp = curp;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }

    public int getIdUsuario(){
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
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
    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario = usuario;
    }
    public String getPass(){
        return pass;
    }
    public void setPass(String pass){
        this.pass = pass;
    }
    public String getCurp(){
        return curp;
    }
    public void setCurp(String curp){
        this.curp = curp;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getFechaRegistro(){
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }
}


