package service;

import dao.AnotacoesDAO;
import model.Anotacoes;

import spark.Request;
import spark.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AnotacoesService {

    private AnotacoesDAO anotacoesDAO; 

    public AnotacoesService() {
        try {
            anotacoesDAO = new AnotacoesDAO();
            anotacoesDAO.conectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

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
