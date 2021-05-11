package app;

import static spark.Spark.*;

import service.ToDoListService;

public class Aplicacao {
	
    private static ToDoListService todolistService = new ToDoListService();

    public static void main(String[] args) {
        port(6789);

        post("/todolist", (request, response) -> todolistService.addToDoList(request, response));

        get("/todolist/:idtodolist", (request, response) -> todolistService.getToDoList(request, response));

        get("/todolist/update/:idtodolist", (request, response) -> todolistService.updateToDoList(request, response));

        get("/todolist/delete/:idtodolist", (request, response) -> todolistService.removerToDoList(request, response));

        get("/todolist", (request, response) -> todolistService.getAllToDoList(request, response));
        
    }
}
