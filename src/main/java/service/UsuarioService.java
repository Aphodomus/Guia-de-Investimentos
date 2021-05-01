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

    public Object addUsuario(Request request, Response response) {
        String Nome = request.queryParams("Nome");
        String SobreNome = request.queryParams("SobreNome");
        int Idade = Integer.parseInt(request.queryParams("Idade"));
        String Senha = request.queryParams("Senha");
        String Email = request.queryParams("Email");
        String Sexo = request.queryParams("Sexo");

        int Id = usuarioDAO.getMaxCodigo() + 1;

        Usuario usuario = new Usuario(Id, Nome, SobreNome, Idade, Senha, Email, Sexo);

        usuarioDAO.inserirUsuario(usuario);

        response.status(201);

        return Id;
    }

    public Object getUsuario(Request request, Response response) {

    }

    public Object updateUsuario(Request request, Response response) {

    }

    public Object removeUsuario(Request request, Response response) {

    }

    public Object getAllUsuario(Request request, Response response) {

    }
}
