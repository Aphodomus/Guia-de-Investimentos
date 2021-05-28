package app;

import static spark.Spark.*;

import service.ToDoListService;

public class Aplicacao {
    private static UsuarioService usuarioService = new UsuarioService();
    private static ToDoListService todolistService = new ToDoListService();
    private static AnotacoesService anotacoesService = new AnotacoesService();

    public static void main(String[] args) {
        port(6789);

        //Usuario
        post("/usuario", (request, response) -> usuarioService.addUsuario(request, response));

        get("/usuario/:id", (request, response) -> usuarioService.getUsuario(request, response));

        get("/usuario/update/:id", (request, response) -> usuarioService.updateUsuario(request, response));

        get("/usuario/delete/:id", (request, response) -> usuarioService.removeUsuario(request, response));

        get("/usuario", (request, response) -> usuarioService.getAllUsuario(request, response));

        get("/logar", (request, response) -> usuarioService.logarUsuario(request, response));

        //ToDoList
        post("/todolist", (request, response) -> todolistService.addToDoList(request, response));

        get("/todolist/:idtodolist", (request, response) -> todolistService.getToDoList(request, response));

        get("/todolist/update/:idtodolist", (request, response) -> todolistService.updateToDoList(request, response));

        get("/todolist/delete/:idtodolist", (request, response) -> todolistService.removerToDoList(request, response));

        get("/todolist", (request, response) -> todolistService.getAllToDoList(request, response));

        //Anotacoes
        post("/anotacoes", (request, response) -> anotacoesService.addAnotacao(request, response));

        get("/anotacoes/:idanotacoes", (request, response) -> anotacoesService.getAnotacao(request, response));

        get("/anotacoes/update/:idanotacoes", (request, response) -> anotacoesService.updateAnotacao(request, response));

        get("/anotacoes/delete/:idanotacoes", (request, response) -> anotacoesService.removeAnotacao(request, response));

        get("/anotacoes", (request, response) -> anotacoesService.getAllAnotacoes(request, response));
        
    }
}
