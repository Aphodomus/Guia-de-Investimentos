package model;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Anotacoes {
    //----------------------------Atributos--------------------------//
    private int todolist; //FK
    private int idanotacoes; //PK
    private Date datacriacao;
    private String descricao;

    //----------------------------Metodos Especiais-------------------------//

    public Anotacoes() {
        this.todolist = -1;
        this.idanotacoes = -1;
        this.datacriacao = new Date();
        this.descricao = "bruh";
    }

    public Anotacoes(int todolist, int idanotacoes, Date datacriacao, String descricao) {
        setToDoList(todolist);
        setIdAnotacoes(idanotacoes);
        setDataCriacao(datacriacao);
        setDescricao(descricao);
    }

    public int getToDoList() {
        return this.todolist;
    }

    public int getIdAnotacoes() {
        return this.idanotacoes;
    }

    public Date getDataCriacao() {
        return this.datacriacao;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setToDoList(int todolist) {
        this.todolist = todolist;
    }

    public void setIdAnotacoes(int idanotacoes) {
        this.idanotacoes = idanotacoes;
    }

    public void setDataCriacao(Date datacriacao) {
        this.datacriacao = datacriacao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //----------------------------Funcoes e Metodos-------------------------//
    @Override
    public String toString() {
        return "Anotacoes [todolist = " + todolist + ", idanotacoes = " + idanotacoes + ", datacriacao = " + datacriacao + ", descricao = " + descricao + "]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getIdAnotacoes() == ((Anotacoes) obj).getIdAnotacoes());
	}
}
