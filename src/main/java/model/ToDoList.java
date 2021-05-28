package model;

import java.lang.String;


public class ToDoList{

    private int idtodolist;
    private String nome;
    private String usuario;

    public ToDoList(){
        this.idtodolist = -1;
        this.nome = "";
        this.usuario ="";
    }

    //----Metodos especiais-------//

   public ToDoList(String Nome){
        setNome(Nome);
   }

    //----Metodos get------//

    public int getId(){
        return this.idtodolist;
    }

    public String getNome(){
        return this.nome;
    }

    public String getUsuario(){
        return this.usuario;
    }

    //----Metodos set------//

    public void setId(int Id){
        this.idtodolist = Id;
    }

    public void setNome(String Nome){
        if (Nome.length()>=3){
            this.nome = Nome;
        }
        else System.out.print("Erro na atribuicao do nome");
    }

    public void setUsuario(String Usuario){
        this.usuario = Usuario;
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "Usuario [Id = " + idtodolist + ", nome = " + nome + ", usuario = " + usuario + "]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((ToDoList) obj).getId());
	}
}