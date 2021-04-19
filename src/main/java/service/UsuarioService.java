package service;

import dao.UsuarioDAO;
import model.Usuario;

import spark.Request;
import spark.Response;

public class UsuarioService {
    
    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        try {
            usuarioDAO = new UsuarioDAO();
            //usuarioDAO.conectar(); <----- Precisa ser implementado
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object add(Request request, Response response) {

    }

    public Object get(Request request, Response response) {

    }

    public Object update(Request request, Response response) {

    }

    public Object remove(Request request, Response response) {

    }

    public Object getAll(Request request, Response response) {

    }
}
