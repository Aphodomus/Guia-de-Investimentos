package model;

import java.lang.String;


public class ToDoList{

    private int Id;
    private String Nome;
    private String Usuario;

    public ToDoList(){
        this.Id = -1;
        this.Nome = "";
        this.Usuario ="";
    }

    //----Metodos especiais-------//

   public ToDoList(int Id, String Nome, String Usuario){
        setId(Id);
        setNome(Nome);
        setUsuario(Usuario);
   }

    //----Metodos get------//

    public int getId(){
        return this.Id;
    }

    public String getNome(){
        return this.Nome;
    }

    public String getUsuario(){
        return this.Usuario;
    }

    //----Metodos set------//

    public void setId(int Id){
        this.Id = Id;
    }

    public void setNome(String Nome){
        if (Nome.length()>=3){
            this.Nome = Nome;
        }
        else System.out.print("Erro na atribuicao do nome");
    }

    public void setUsuario(String Usuario){
        this.Usuario = Usuario;
    }


}