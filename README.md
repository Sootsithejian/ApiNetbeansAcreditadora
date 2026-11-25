# Acreditadora1

API REST de la Acreditadora (Jakarta REST / Jersey 3.1) como proyecto web de NetBeans (Ant) para Tomcat 11 y Java 21.

## Estructura

```
src/java/mx/acreditadora/
├── config/       Database.java        -> conexión a MySQL
├── models/       Cliente, Credito, Pago, Producto, Usuarios
├── controllers/  Acceso a datos (JDBC) por entidad
├── rest/         RestApplication (/api), HealthResource, RestCreditos
└── utils/        passwordUtil (BCrypt)
web/WEB-INF/lib/  Dependencias (Jersey, Gson, MySQL Connector/J, jBCrypt)
```

## Configuración de la base de datos

Copia `src/java/db.properties.example` como `src/java/db.properties` y llena tus datos.
También se pueden usar variables de entorno (`DB_URL`, `DB_USER`, `DB_PASSWORD`), que tienen prioridad.

## Ejecutar

Abrir el proyecto en NetBeans, seleccionar el servidor Tomcat 11 y dar **Run**.

## Endpoints (base: `http://localhost:8080/Acreditadora1/api`)

| Método | Ruta |
|---|---|
| GET  | `/health` |
| GET  | `/Acreditadora/clientes`, `/Acreditadora/clientes/{idCliente}` |
| POST | `/Acreditadora/clientes/registrarCliente` |
| GET  | `/Acreditadora/creditos`, `/Acreditadora/creditos/{idCliente}`, `/Acreditadora/credito/{idCredito}` |
| POST | `/Acreditadora/credito/registrarCredito` |
| GET  | `/Acreditadora/productos`, `/Acreditadora/productos/{idProducto}` |
| POST | `/Acreditadora/productos/registrarProducto` |
| GET  | `/Acreditadora/pagos/{idCredito}` |
| POST | `/Acreditadora/pagos/registrarPago` |
| GET  | `/Acreditadora/usuarios` |
| POST | `/Acreditadora/usuarios/registrarUsuario`, `/Acreditadora/login` |
