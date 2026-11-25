
package mx.acreditadora.rest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mx.acreditadora.models.Cliente;
import mx.acreditadora.models.Credito;
import mx.acreditadora.models.Pago;
import mx.acreditadora.models.Producto;
import mx.acreditadora.models.Usuarios;
import mx.acreditadora.controllers.ClienteController;
import mx.acreditadora.controllers.CreditoController;
import mx.acreditadora.controllers.PagoController;
import mx.acreditadora.controllers.ProductoController;
import mx.acreditadora.controllers.UsuariosController;
import java.util.List;

@Path("/Acreditadora")
public class RestCreditos {

    
    @Path("/creditos")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getAllCreditos(){
        CreditoController cc = new CreditoController();
        List<Credito> lista = new ArrayList<>();
        Gson gson = new Gson();
        String out = "";
        try{
            lista = cc.getCreditos();
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"Empty table"}
                  """;
        }
        return Response.ok(out).build();
    }


    @Path("/creditos/{idCliente}")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getCreditosPorCliente(@PathParam("idCliente") int idCliente) {
        if( idCliente <= 0) {
             return Response.status(Response.Status.BAD_REQUEST)
            .entity("{\"error\":\"ID de cliente inválido\"}")
            .build(); 
        }
        CreditoController cc = new CreditoController();
        Gson gson = new Gson();

        try{
            List<Credito> lista = cc.getCreditosPorCliente(idCliente);
            return Response.ok(gson.toJson(lista)).build();
        } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al obtener los créditos del cliente\"}").build();
        }
    }

    @Path("/credito/{idCredito}")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getCreditoPorId(@PathParam("idCredito") int idCredito) {
        if (idCredito <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("{\"error\":\"ID de crédito inválido\"}")
            .build();
        }
        CreditoController cc = new CreditoController();
        Gson gson = new Gson();

        try{
            Credito credito = cc.getCreditoPorId(idCredito);
            if (credito == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Crédito no encontrado\"}").build();
            }
            return Response.ok(gson.toJson(credito)).build();
        } catch (Exception e) {
            e.printStackTrace();
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al obtener el crédito\"}").build();
        }
        }

    @Path("/credito/registrarCredito")
    @POST 
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response registrarCredito(Credito credito) {

        if (credito == null ||credito.getIdCliente() <= 0 ||credito.getIdProducto() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                   .entity("""
                           {"result":"error","message":"idCliente y idProducto son obligatorios"}
                           """)
                   .build();
       }
       try{
        int id = new CreditoController().registrarCredito(credito);
        if(id == 0){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                             .entity("""
                               {"result":"error","message":"No se pudo registrar el credito"}
                               """)
                             .build();
        }
       return Response.status(Response.Status.CREATED).entity(String.format("{\"result\":\"ok\",\"idCredito\":%d}", id)).build();

        } catch (SQLIntegrityConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                        .entity("""
                           {"result":"error","message":"El Cliente o producto no existe"}
                           """)
                        .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("""
                                     {"result":"error","message":"Error al registrar el credito"}
                                     """)
                            .build();
        }
    }

    @Path("/clientes")
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClientes() {
        ClienteController cc = new ClienteController();
        List<Cliente> lista = new ArrayList<>();
        Gson gson = new Gson();
        String out = "";
        try {
            lista = cc.getClientes();
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"Empty table"}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("clientes/{idCliente}")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getClientePorId(@PathParam("idCliente") int idCliente) {
        if (idCliente <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("{\"error\":\"ID de cliente inválido\"}")
            .build();
        }
        ClienteController cc = new ClienteController();
        Gson gson = new Gson();

        try {
            Cliente cliente = cc.getClientePorId(idCliente);
            if(cliente == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Cliente no encontrado\"}").build();
            }
            return Response.ok(gson.toJson(cliente)).build();
            } catch (Exception e) {
                e.printStackTrace();

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al obtener el cliente\"}").build();
        }
        }
    

    @Path("clientes/registrarCliente")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarCliente(Cliente cliente) {
        if (cliente == null || cliente.getNombre() == null  || cliente.getNombre().isBlank() 
            || cliente.getPrimerApellido() == null || cliente.getPrimerApellido().isBlank()
            || cliente.getCurp() == null || cliente.getCurp().isBlank() ) {
             return Response.status(Response.Status.BAD_REQUEST)
                    .entity("""
                            {"result":"error","message":"Nombre, Primer Apellido y Curp son obligatorios"}
                            """)
                            .build();   
            }
            try {
                int id = new ClienteController().registrarCliente(cliente);

                if(id == 0) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("""
                                    {"result":"error","message":"No se pudo registrar el Cliente"}
                                    """)
                            .build();
                }

                return Response.status(Response.Status.CREATED).entity(String.format("{\"result\":\"ok\",\"idCliente\":%d}", id)).build();   
            } catch (SQLIntegrityConstraintViolationException e) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("""
                                {"result":"error","message":"Curp ya está registrada"}
                                """)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                     .entity("""
                                            {"result":"error","message":"Error al registrar el usuario"}
                                            """)
                                     .build();
            }
    }

    
   

   

    @Path("/usuarios")
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response getAllUsuarios(){
        UsuariosController uc = new UsuariosController();
        List<Usuarios> lista = new ArrayList<>();
        Gson gson = new Gson();
        String out = ""; 

        try{
            lista = uc.getUsuarios();
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"Empty table"}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("/usuarios/registrarUsuario")
    @POST 
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response registrarUsuario(Usuarios usuarios) {
        if(usuarios == null || usuarios.getNombre() == null || usuarios.getNombre().isBlank() || 
           usuarios.getPrimerApellido() == null || usuarios.getPrimerApellido().isBlank() ||
           usuarios.getUsuario() == null || usuarios.getUsuario().isBlank() ||
           usuarios.getPass() == null || usuarios.getPass().isBlank() ||
           usuarios.getCurp() == null || usuarios.getCurp().isBlank() ||
           usuarios.getEmail() == null || usuarios.getEmail().isBlank()) {

           return Response.status(Response.Status.BAD_REQUEST).entity(
            """
            {"result":"error","message":"Todos los campos obligatorios deben de estar completos"}

           """).build();
           }
           try {
            int id = new UsuariosController().registrarUsuario(usuarios);

            if (id == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("""
                                    {"result":"error","message":"No se pudo registrar el usuario"}
                                    """)
                            .build();
            }

            return Response.status(Response.Status.CREATED).entity(String.format("{\"result\":\"ok\",\"idUsuario\":%d}", id)).build();
                
           } catch (SQLIntegrityConstraintViolationException e) {
                return Response.status(Response.Status.CONFLICT)
                .entity("""
                        {"result":"error","message":"El usuario o CURP ya está registrado"}
                        """)
                        .build();
           } catch (Exception e) {
            e.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                     .entity("""
                                            {"result":"error","message":"Error al registrar el usuario"}
                                            """)
                                     .build();
           }
    }

    @Path("/login")
    @POST 
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response login(Usuarios usuarios) {

        if(usuarios == null ||
            usuarios.getUsuario() == null || usuarios.getUsuario().isBlank() ||
            usuarios.getPass() == null || usuarios.getPass().isBlank()) {
                
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("""
                            {"result":"error","message":"Usuario y contraseña son obligatorios"}
                            """)
                            .build();
            }
            try {
                Usuarios resultado = new UsuariosController().login(usuarios.getUsuario(), usuarios.getPass());

                if (resultado == null) {
                    return Response.status(Response.Status.UNAUTHORIZED)
                            .entity("""
                                    {"result":"error","message":"Usuario o contraseña incorrectos"}
                                    """)
                                    .build();
                }
                return Response.ok(new Gson().toJson(resultado)).build();
            } catch (Exception e) {
                e.printStackTrace();

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("""
                                {"result":"error","message":"Error al iniciar sesion"}
                                """)
                                .build();
            }
    }  

    @Path("/productos")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getAllProductos(){
        ProductoController pc = new ProductoController();
        List<Producto> lista = new ArrayList<>();
        Gson gson = new Gson();
        String  out = "";

        try{
            lista = pc.getProductos();
            out = gson.toJson(lista);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"Empty table"}
                  """;
        }
        return Response.ok(out).build();
    }

    @Path("/productos/registrarProducto")
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrarProducto(Producto producto) {
        if(producto == null || producto.getMonto() <= 0 || 
        producto.getPlazo() <= 0 || producto.getPagoSemanal() <= 0 ) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("""
                            {"result":"error","message":"monto, plazo y pago semanal deben de ser mayores a 0"}
                            """)
                            .build();
        }
        try{
            int id = new ProductoController().registrarProducto(producto);
            if(id == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("""
                                {"result":"error","message":"No se pudo registrar el producto"}
                                """)
                                .build();
            }
            return Response.status(Response.Status.CREATED).entity(String.format("{\"result\":\"ok\",\"idProducto\":%d}", id)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                   .entity("""
                           {"result":"error","message":"Error al registrar producto"}
                           """)
                           .build(); 
        }
    }

    @Path("productos/{idProducto}")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getProductoPorId(@PathParam("idProducto") int idProducto) {
        if (idProducto <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("{\"error\":\"ID de producto inválido\"}")
            .build();
        }
        ProductoController pc = new ProductoController();
        Gson gson = new Gson();

        try{
            Producto producto = pc.getProductoPorId(idProducto);
            if (producto == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Producto no encontrado\"}").build();
            }
            return Response.ok(gson.toJson(producto)).build();
        } catch (Exception e) {
            e.printStackTrace();

             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\":\"Error al obtener producto\"}").build();
        }
    }

    @Path("pagos/{idCredito}")
    @GET 
    @Produces (MediaType.APPLICATION_JSON)
    public Response getPagosPorCredito(@PathParam("idCredito") int idCredito) {


    if(idCredito <= 0) {
        return Response.status(Response.Status.BAD_REQUEST)
         .entity("{\"error\":\"ID de crédito inválido\"}")
        .build();
    }
    PagoController pc = new PagoController();
    Gson gson = new Gson();
    List<Pago> lista = new ArrayList<>();
    String out = "";


    try {
        lista = pc.getPagosPorCredito(idCredito);
        out = gson.toJson(lista);
    } catch (Exception e) {
        e.printStackTrace();
        out = """
              {"result":"Empty table"}
              """;
    }
    return Response.ok(out).build();

    }

    @Path("pagos/registrarPago") 
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registarPago(Pago pago) {
        if(pago == null || pago.getIdCredito() <= 0 || pago.getMonto() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
            .entity("""
                           {"result":"error","message":"idCredito y Monto son obligatorios"}
                           """)
                   .build();
        }
        try {
            int id = new PagoController().registrarPago(pago);
            if(id == 0) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("""
                        {"result":"error","message":"No se puede registrar el pago"}
                        """)
                        .build();
            }
            return Response.status(Response.Status.CREATED).entity(String.format("{\"result\":\"ok\",\"idPago\":%d}", id)).build();
        } catch (SQLIntegrityConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                        .entity("""
                           {"result":"error","message":"El Credito no existe"}
                           """)
                        .build();
        } catch (Exception  e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("""
                                     {"result":"error","message":"Error al registrar el pago"}
                                     """)
                            .build();
        }
    }
   
}
