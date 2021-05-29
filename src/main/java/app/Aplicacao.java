package app;

import static spark.Spark.*;

import service.Service;


public class Aplicacao {
    private static Service service = new Service();

    public static void main(String[] args) {
        port(6789);

        //Usuario
        post("/usuario", (request, response) -> service.addUsuario(request, response));

        get("/usuario/:id", (request, response) -> service.getUsuario(request, response));

        get("/usuario/update/:id", (request, response) -> service.updateUsuario(request, response));

        get("/usuario/delete/:id", (request, response) -> service.removeUsuario(request, response));

        get("/usuario", (request, response) -> service.getAllUsuario(request, response));

        post("/login", (request, response) -> service.logarUsuario(request, response));

        //Anotacoes
        post("/anotacoes", (request, response) -> service.addAnotacao(request, response));

        get("/anotacoes/:idanotacoes", (request, response) -> service.getAnotacao(request, response));

        get("/anotacoes/update/:idanotacoes", (request, response) -> service.updateAnotacao(request, response));

        get("/anotacoes/delete/:idanotacoes", (request, response) -> service.removeAnotacao(request, response));

        get("/anotacoes", (request, response) -> service.getAllAnotacoes(request, response));

        //Todolist
        post("/todolist", (request, response) -> service.addToDoList(request, response));

        get("/todolist/:idtodolist", (request, response) -> service.getToDoList(request, response));

        get("/todolist/update/:idtodolist", (request, response) -> service.updateToDoList(request, response));

        get("/todolist/delete/:idtodolist", (request, response) -> service.removerToDoList(request, response));

        get("/todolist", (request, response) -> service.getAllToDoList(request, response));
        
    }
}
