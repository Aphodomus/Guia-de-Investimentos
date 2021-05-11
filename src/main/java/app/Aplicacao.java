package app;

import static spark.Spark.*;

import service.AnotacoesService;

public class Aplicacao {
    private static AnotacoesService usuarioService = new AnotacoesService();

    public static void main(String[] args) {
        port(6789);

        post("/usuario", (request, response) -> usuarioService.addAnotacao(request, response));

        get("/usuario/:id", (request, response) -> usuarioService.getAnotacao(request, response));

        get("/usuario/update/:id", (request, response) -> usuarioService.updateAnotacao(request, response));

        get("/usuario/delete/:id", (request, response) -> usuarioService.removeAnotacao(request, response));

        get("/usuario", (request, response) -> usuarioService.removeAnotacao(request, response));
        
    }
}
