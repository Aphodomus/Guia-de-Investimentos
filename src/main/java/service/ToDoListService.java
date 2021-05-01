package service;


import dao.ToDoListDAO;
import model.ToDoList;

import spark.Request;
import spark.Response;

public class ToDoListService {

    private ToDoListDAO todolistDAO;

    public ToDoListService(){
        try{
            todolistDAO = new ToDoListDAO();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Object addToDoList(Request request,Response response){
        int Id = ToDoList.getMaxCodigo() + 1;
        String Nome = request.queryParams("Nome");
        String Usuario = request.queryParams("Usuario");

        ToDoList todolist = new Todolist(Id,Nome,Usuario);

        todolistDAO.inserirToDoList(todolist);

        response.status(201);

        return Id;
    }

    public Object getToDoList(Request request, Response response){
        int id = Integer.parseInt(request.params(":Id"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarTodolist(Id);

        if(todolist!=null){
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");
        }

        return "<ToDoList>\n" +
        "\t<Id>"+ todolist.getId() +"</Id>\n";

    }







}
