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
        String Sobrenome = request.queryParams("Sobrenome");
        int Idade = Integer.parseInt(request.queryParams("Idade"));
        String Senha = request.queryParams("Senha");
        String Email = request.queryParams("Email");
        String Sexo = request.queryParams("Sexo");

        int Id = usuarioDAO.getMaxCodigo() + 1;

        Usuario usuario = new Usuario(Id, Nome, Sobrenome, Idade, Senha, Email, Sexo);

        usuarioDAO.inserirUsuario(usuario);

        response.status(201);

        return Id;
    }

    public Object getUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Usuario usuario = (Usuario) usuarioDAO.procurarUsuario(id);

        if (usuario != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<usuario>\n" +
                        "\t<id>" + usuario.getId() + "</id>\n" +
                        "\t<nome>" + usuario.getNome() + "</nome>\n" +
                        "\t<sobrenome>" + usuario.getSobreNome() + "</sobrenome>\n" +
                        "\t<idade>" + usuario.getIdade() + "</idade>\n" +
                        "\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
                        "\t<senha>" + usuario.getSenha() + "</senha>\n" +
                        "\t<email>" + usuario.getEmail() + "</email>\n" +
                    "</usuario>\n";

        } else {
            response.status(404); // 404 erro
            return "Usuario com id [" + id + "] nao encontrado.";
        }
    }

    public Object updateUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Usuario usuario = (Usuario) usuarioDAO.procurarUsuario(id);

        if (usuario != null) {
            usuario.setNome(request.queryParams("Nome"));
            usuario.setSobreNome(request.queryParams("Sobrenome"));
            usuario.setIdade(Integer.parseInt(request.queryParams("Idade")));
            usuario.setSenha(request.queryParams("Senha"));
            usuario.setEmail(request.queryParams("Email"));
            usuario.setSexo(request.queryParams("Sexo"));

            usuarioDAO.atualizarUsuario(usuario);

            return id;
        } else {
            response.status(404); // 404 Erro
            return "Usuario com id [" + id +"] nao econtrado";
        }
    }

    public Object removeUsuario(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Usuario usuario = (Usuario) usuarioDAO.procurarUsuario(id);

        if (usuario != null) {
            usuarioDAO.excluirUsuario(id);

            response.status(200); // Sucesso
            return id;
        } else {
            response.status(404); // 404 Erro
            return "Usuario com id [" + id +"] nao econtrado";
        }
    }

    public Object getAllUsuario(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");

        if (usuarioDAO.getUsuarios() != null) {
            for (Usuario usuario : usuarioDAO.getUsuarios()) {
                returnValue.append("\n<usuario>\n" +
                                        "\t<id>" + usuario.getId() + "</id>\n" +
                                        "\t<nome>" + usuario.getNome() + "</nome>\n" +
                                        "\t<sobrenome>" + usuario.getSobreNome() + "</sobrenome>\n" +
                                        "\t<idade>" + usuario.getIdade() + "</idade>\n" +
                                        "\t<sexo>" + usuario.getSexo() + "</sexo>\n" +
                                        "\t<senha>" + usuario.getSenha() + "</senha>\n" +
                                        "\t<email>" + usuario.getEmail() + "</email>\n" +
                                    "</usuario>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de Usuarios vazia");
        }

        returnValue.append("</usuarios>");
        response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }
}
