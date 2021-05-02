package app;

import static spark.Spark.*;

import service.UsuarioService;

public class Aplicacao {
    private static UsuarioService usuarioService = new UsuarioService();

    public static void main(String[] args) {
        port(6789);

        post("/Usuario", (request, response) -> usuarioService.addUsuario(request, response));

        //get("/Usuario/:id", (request, response) -> usuarioService.getUsuario(request, response));

        //get("/Usuario/update/:id", (request, response) -> usuarioService.updateUsuario(request, response));

        //get("/Usuario/delete/:id", (request, response) -> usuarioService.removeUsuario(request, response));

        //get("/Usuario", (request, response) -> usuarioService.removeUsuario(request, response));
        
    }
}
