package app;

import static spark.Spark.*;

import service.UsuarioService;

public class Aplicacao {
    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        port(6789);

        post("/usuario", (request, response) -> usuarioService.addUsuario(request, response));

        get("/usuario/:id", (request, response) -> usuarioService.getUsuario(request, response));

        get("/usuario/update/:id", (request, response) -> usuarioService.updateUsuario(request, response));

        get("/usuario/delete/:id", (request, response) -> usuarioService.removeUsuario(request, response));

        get("/usuario", (request, response) -> usuarioService.removeUsuario(request, response));
        
    }
}
