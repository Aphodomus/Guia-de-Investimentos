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
        String primeiroNome = request.queryParams("primeiroNome");
        String segundoNome = request.queryParams("segundoNome");
        int idade = Integer.parseInt(request.queryParams("idade"));
        String senha = request.queryParams("senha");
        String email = request.queryParams("email");
        String sexo = request.queryParams("sexo");

        int id = usuarioDAO.getMaxCodigo() + 1;

        Usuario usuario = new Usuario(id, primeiroNome, segundoNome, idade, senha, email, sexo);

        usuarioDAO.inserirUsuario(usuario);

        response.status(201);

        return id;
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
