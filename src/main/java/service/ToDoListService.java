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
            todolistDAO.conectar();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Object addToDoList(Request request,Response response){
        int Id = todolistDAO.getMaxIdToDoList() + 1;
        String Nome = request.queryParams("Nome");
        String Usuario = request.queryParams("Usuario");

        ToDoList todolist = new ToDoList(Id, Nome, Usuario);

        todolistDAO.inserirToDoList(todolist);

        response.status(201);

        return Id;
    }

    public Object getToDoList(Request request, Response response){
        int id = Integer.parseInt(request.params(":idtodolist"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarTodolist(id);

        if(todolist!=null){
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");
        

        return "<ToDoList>\n" +
                    "\t<Id>"+ todolist.getId() +"</Id>\n" + 
                    "\t<Nome>" + todolist.getNome() + "</Nome>\n" +
                    "\t<Usuario>" + todolist.getUsuario() + "</Nome>\n" +
                "</ToDoList>\n";

    }else {
        response.status(404); // 404 erro
        return "ToDoList com id [" + id + "] nao encontrado.";
    }
}

    public Object updateToDoList(Request request, Response response){
        int id = Integer.parseInt(request.params(":Id"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarTodolist(id);

        if(todolist!=null){
            todolist.setId(Integer.parseInt(request.queryParams("Id")));
            todolist.setNome(request.queryParams("Nome"));
            todolist.setUsuario(request.queryParams("Usuario"));

            todolistDAO.atualizarToDoList(todolist);

            return id;
        }else {
            response.status(404); // 404 Erro
            return "ToDoList com id [" + id +"] nao econtrado";
        }
    }

    public Object removerToDoList(Request request, Response response) {
        int id = Integer.parseInt(request.params(":idtodolist"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarTodolist(id);

        if (todolist != null) {
            todolistDAO.excluirToDoList(id);

            response.status(200);
            return id;
        } else {
            response.status(404);
            return "Todolist com id [" + id + "] nao encontrado";
        }
    }
    
    public Object getAllToDoList(Request request, Response response){
        StringBuffer returnValue = new StringBuffer("<todolist type=\"array\">");

        if(todolistDAO.getToDoList()!=null){
            for(ToDoList todolist : todolistDAO.getToDoList()){
                returnValue.append("\n<todolist>\n" + 
                                "\t<id>" + todolist.getId() + "</Id>\n" + 
                                "\t<Nome>" + todolist.getNome() + "</Nome>\n" +
                                "\t<Usuario>" + todolist.getUsuario() + "</Usuario>\n"+
                                "<todolist>\n");
            }

        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de ToDoList vazia");
        }
        returnValue.append("</todolist>");
        response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    } 

}

