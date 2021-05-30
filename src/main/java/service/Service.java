package service;

import dao.UsuarioDAO;
import model.Usuario;
import dao.ToDoListDAO;
import model.ToDoList;
import dao.AnotacoesDAO;
import model.Anotacoes;

import spark.Request;
import spark.Response;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Service {
    private UsuarioDAO usuarioDAO;
    private ToDoListDAO todolistDAO;
    private AnotacoesDAO anotacoesDAO;
    private int usuarioID = 0;

    public Service() {
        try {
            //Conexao de usuario
            usuarioDAO = new UsuarioDAO();
            usuarioDAO.conectar();

            //Conexao da todolist
            todolistDAO = new ToDoListDAO();
            todolistDAO.conectar();

            //Conexao de anotacoes
            anotacoesDAO = new AnotacoesDAO();
            anotacoesDAO.conectar();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Usuario
    public Object addUsuario(Request request, Response response) {
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/login.html\"</script>";

        String Nome = request.queryParams("Nome");
        String Sobrenome = request.queryParams("Sobrenome");
        String dataNascimento = request.queryParams("Idade");
        String Senha = request.queryParams("Senha");
        String Email = request.queryParams("Email");
        String Sexo = request.queryParams("Sexo");

        int Idade = calcularIdade(dataNascimento, "yyyy-MM-dd");

        Usuario usuario = new Usuario(Nome, Sobrenome, Idade, Senha, Email, Sexo);

        usuarioDAO.inserirUsuario(usuario);

        int idUsuario = usuarioDAO.idUsuario(usuario);

        ToDoList todolist = new ToDoList("Diary", idUsuario);

        todolistDAO.inserirToDoList(todolist);

        int idToDoList = todolistDAO.idToDoList(todolist);

        Date date = new Date();

        Anotacoes anotacao = new Anotacoes(idToDoList, date, "Sua primeira anotação");

        anotacoesDAO.inserirAnotacao(anotacao);

        response.status(201);

        return redirect;
    }

    public Object logarUsuario(Request request, Response response) {
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/index.html\"</script>";
        String redirect2 = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/login.html\"</script>";

        String Email = request.queryParams("Email");
        String Senha = request.queryParams("Senha");

        boolean resp1 = usuarioDAO.pesquisarEmail(Email);
        boolean resp2 = usuarioDAO.pesquisarSenha(Senha);

        if (resp1 == true && resp2 == true) {
            response.status(201);

            usuarioID = usuarioDAO.procurarEmailSenha(Email, Senha);

            return redirect;
        }

        response.status(201);

        return redirect2;
    }

    public Object verificar(Request request, Response response) {
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/diary.html\"</script>";
        String redirect2 = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/services.html\"</script>";

        if (usuarioID >= 0) {
            response.status(201);

            System.out.println(usuarioID);

            return redirect;
        }

        response.status(201);

        return redirect2;
    }

    //Tratar a data de nascimento e devolver a idade da pessoa
    public int calcularIdade(String dataNasc, String pattern){
        DateFormat sdf = new SimpleDateFormat(pattern);
        Date dataNascInput = null;
        
        try {
            dataNascInput= sdf.parse(dataNasc);
        } catch (Exception e) {

        }
        
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNascInput);
        
        // Cria um objeto calendar com a data atual
        Calendar today = Calendar.getInstance();
        
        // Obt�m a idade baseado no ano
        int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
        
        dateOfBirth.add(Calendar.YEAR, age);
        
        if (today.before(dateOfBirth)) {
            age--;
        }
        
        return age;
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
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/formulario.html\"</script>";

        Usuario usuario = (Usuario) usuarioDAO.procurarUsuario(id);

        if (usuario != null) {
            usuarioDAO.excluirUsuario(id);

            response.status(200); // Sucesso

            return redirect;
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

    //ToDoList
    public Object addToDoList(Request request,Response response){
        String Nome = request.queryParams("Nome");

        ToDoList todolist = new ToDoList(Nome);

        todolistDAO.inserirToDoList(todolist);

        response.status(201);

        return "Sucesso";
    }

    public Object getToDoList(Request request, Response response){
        int id = Integer.parseInt(request.params(":idtodolist"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarToDoList(id);

        if(todolist != null){
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
        int id = Integer.parseInt(request.params(":idtodolist"));

        ToDoList todolist = (ToDoList) todolistDAO.procurarToDoList(id);

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

        ToDoList todolist = (ToDoList) todolistDAO.procurarToDoList(id);

        if (todolist != null) {
            todolistDAO.excluirToDoList(id);

            response.status(200);
            return id;
        } else {
            response.status(404);
            return "Todolist com id [" + id + "] nao encontrado";
        }
    }
    
    public Object getAllToDoList(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<usuarios type=\"array\">");

        if (todolistDAO.getToDoList() != null) {
            for (ToDoList todolist : todolistDAO.getToDoList()) {
                returnValue.append("\n<todolist>\n" +
                                        "\t<idtodolist>" + todolist.getId() + "</idtodolist>\n" +
                                        "\t<nome>" + todolist.getNome() + "</nome>\n" +
                                        "\t<usuario>" + todolist.getUsuario() + "</usuario>\n" +
                                    "</todolist>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de todolists vazia");
        }

        returnValue.append("</usuarios>");
        response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }

    //Anotacoes
    public Object addAnotacao(Request request, Response response) {
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/novoguiadeinvestimentos/src/main/resources/formulario.html\"</script>";
        Date datacriacao = new Date();
        String descricao = request.queryParams("Descricao");

        Anotacoes anotacoes = new Anotacoes(datacriacao, descricao);

        anotacoesDAO.inserirAnotacao(anotacoes);

        response.status(201);

        return redirect;
    }

    public Object getAnotacao(Request request, Response response) {
        int idanotacoes = Integer.parseInt(request.params(":idanotacoes"));

        Anotacoes anotacoes = (Anotacoes) anotacoesDAO.procurarAnotacao(idanotacoes);

        if (anotacoes != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<anotacao>\n" +
                        "\t<idanotacoes>" + anotacoes.getIdAnotacoes() + "</idanotacoes>\n" +
                        "\t<datacriacao>" + anotacoes.getDataCriacao() + "</datacriacao>\n" +
                        "\t<descricao>" + anotacoes.getDescricao() + "</descricao>\n" +
                    "</anotacao>\n";

        } else {
            response.status(404); // 404 erro
            return "Anotacao com idanotacao [" + idanotacoes + "] nao encontrado.";
        }
    }

    public Object updateAnotacao(Request request, Response response) {
        int idanotacoes = Integer.parseInt(request.params(":idanotacoes"));

        Anotacoes anotacoes = (Anotacoes) anotacoesDAO.procurarAnotacao(idanotacoes);

        if (anotacoes != null) {
            Date datacriacao = processarData(request.queryParams("datacriacao"));
            anotacoes.setDataCriacao(datacriacao);
            anotacoes.setDescricao(request.queryParams("descricao"));

            anotacoesDAO.atualizarAnotacao(anotacoes);

            return idanotacoes;
        } else {
            response.status(404); // 404 Erro
            return "Anotacao com idanotacao [" + idanotacoes +"] nao econtrado";
        }
    }

    //Processar a data em forma de string e tranformar em Date
    private Date processarData(String queryParams) {
        Date resp = new Date();
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date newDate = formato.parse(queryParams);

            resp = newDate;   
        } catch (Exception e) {
            System.out.println(e + "Erro ao tentar formatar a data");
        }

        return resp;
    }

    public Object removeAnotacao(Request request, Response response) {
        int idanotacao = Integer.parseInt(request.params(":idanotacoes"));

        Anotacoes anotacoes = (Anotacoes) anotacoesDAO.procurarAnotacao(idanotacao);

        if (anotacoes != null) {
            anotacoesDAO.excluirAnotacao(idanotacao);

            response.status(200); // Sucesso
            return idanotacao;
        } else {
            response.status(404); // 404 Erro
            return "Anotacao com idanotacao [" + idanotacao +"] nao econtrado";
        }
    }

    public Object getAllAnotacoes(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<anotacoes type=\"array\">");

        if (anotacoesDAO.getAnotacoes() != null) {
            for (Anotacoes anotacoes : anotacoesDAO.getAnotacoes()) {
                returnValue.append("\n<anotacao>\n" +
                                        "\t<idanotacoes>" + anotacoes.getIdAnotacoes() + "</idanotacoes>\n" +
                                        "\t<datacriacao>" + anotacoes.getDataCriacao() + "</datacriacao>\n" +
                                        "\t<descricao>" + anotacoes.getDescricao() + "</descricao>\n" +
                                    "</anotacao>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de anotacoes vazia");
        }

        returnValue.append("</anotacoes>");
        response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }
}
