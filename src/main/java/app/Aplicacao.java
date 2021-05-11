package app;

import static spark.Spark.*;

import service.AnotacoesService;

public class Aplicacao {
    private static AnotacoesService usuarioService = new AnotacoesService();

    public static void main(String[] args) {
        port(6789);

        post("/anotacoes", (request, response) -> usuarioService.addAnotacao(request, response));

        get("/anotacoes/:idanotacoes", (request, response) -> usuarioService.getAnotacao(request, response));

        get("/anotacoes/update/:idanotacoes", (request, response) -> usuarioService.updateAnotacao(request, response));

        get("/anotacoes/delete/:idanotacoes", (request, response) -> usuarioService.removeAnotacao(request, response));

        get("/anotacoes", (request, response) -> usuarioService.getAllAnotacoes(request, response));
        
    }
}
