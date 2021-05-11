package model;

import java.lang.String;


public class ToDoList{

    private int id;
    private String nome;
    private String usuario;

    public ToDoList(){
        this.id = -1;
        this.nome = "";
        this.usuario ="";
    }

    //----Metodos especiais-------//

   public ToDoList(int Id, String Nome, String Usuario){
        setId(Id);
        setNome(Nome);
        setUsuario(Usuario);
   }

    //----Metodos get------//

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getUsuario(){
        return this.usuario;
    }

    //----Metodos set------//

    public void setId(int Id){
        this.id = Id;
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


}