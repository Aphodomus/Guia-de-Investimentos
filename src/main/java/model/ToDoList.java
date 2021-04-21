package model;

import java.util.Date;

public class ToDoList {
    //----------------------------Atributos--------------------------//

    private int idToDoList;
    private String anotacoes;
    private Date dataDeCriacao;

    //----------------------------Metodos Especiais-------------------------//

    public ToDoList() {
        this.idToDoList = -1;
        this.anotacoes = null;
        this.dataDeCriacao = null;
    }

    public ToDoList(int idToDoList, String[] anotacoes, Date dataDeCriacao) {
        setIdToDoList(idToDoList);
        setAnotacoes(anotacoes);
        setDataDeCriacao(dataDeCriacao);
    }

    public int getIdToDoList() {
        return this.idToDoList;
    }

    public void setIdToDoList(int idToDoList) {
        this.idToDoList = idToDoList;
    }

    public String[] getAnotacoes() {
        return this.anotacoes;
    }

    public void setAnotacoes(String[] anotacoes) {
        this.anotacoes = anotacoes;
    }

    public Date getDataDeCriacao() {
        return this.dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "ToDoList [idToDoList = " + idToDoList + ", anotacoes = " + anotacoes + ", dataDeCriacao = " + dataDeCriacao + "]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getIdToDoList() == ((ToDoList) obj).getIdToDoList());
	}	
}