package app;

import static spark.Spark.*;

import service.AnotacoesService;

public class AplicacaoAnotacoes {
    private static AnotacoesService anotacoesService = new AnotacoesService();

    public static void main(String[] args) {
        port(6789);

        post("/anotacoes", (request, response) -> anotacoesService.addAnotacao(request, response));

        get("/anotacoes/:idanotacoes", (request, response) -> anotacoesService.getAnotacao(request, response));

        get("/anotacoes/update/:idanotacoes", (request, response) -> anotacoesService.updateAnotacao(request, response));

        get("/anotacoes/delete/:idanotacoes", (request, response) -> anotacoesService.removeAnotacao(request, response));

        get("/anotacoes", (request, response) -> anotacoesService.getAllAnotacoes(request, response));
        
    }
}
