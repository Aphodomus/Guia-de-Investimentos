package dao;

import java.sql.*;
import java.beans.Statement;
import java.sql.Connection;

import model.ToDoList;
import model.Todolist;

public class ToDoListDAO{
    private Connection conexao;
    private int maxId;
}

public ToDoListDAO(){
    conexao = null;
}

public int getMaxIdToDoList(){
    return maxId;
}

public boolean conectar() {
    String driverName = "org.postgresql.Driver";                    
    String serverName = "localhost"; // Nome da azure que ela vai nos fornecer
    String mydatabase = "aindanaodefinido"; // Eu tenho que criar na azure
    int porta = 5432; // Vou escolher na azure
    String url = "jdbc:postgresql:// " + serverName + ":" + porta +"/" + mydatabase + "?gssEncMode=disable"; // 
    String username = "aindanaodefinido";
    String password = "aindanaodefinido";
    boolean status = false;

    try {
        Class.forName(driverName);
        conexao = DriverManager.getConnection(url, username, password);
        status = (conexao == null);
        System.out.println("Conexão efetuada com o postgres!");
    } catch (ClassNotFoundException e) { 
        System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
    } catch (SQLException e) {
        System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
    }

    return status;
}

public boolean close() {
    boolean status = false;
    
    try {
        conexao.close();
        status = true;
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
    return status;
}

public boolean inserirToDoList(Todolist todolist){

    boolean status =  false;

    Statement st = conexao.createStatement();
    st.executeUpdate("INSERT INTO TODOLIST(IdToDoList, Nome, Usuario)"
    +"VALUES("+todolist.getID()+", '"+todolist.getNome()+"', '"todolist.getUsuario()+"');");
    
    st.close();
    status = true;
    
    this.maxId = this.maxId + 1;
}
    catch (SQLException u) {  
    throw new RuntimeException(u);
}

return status;
}



public boolean atualizarToDoList(Todolist todolist) {
    boolean status = false;

    try {  
        Statement st = conexao.createStatement();
        String sql = "UPDATE TODOLIST SET Nome = '" + todolist.getNome() + "', Usuario = '"  
         + todolist.getUsuario() + "'" + "WHERE IdToDoList = " + todolist.getId();
        st.executeUpdate(sql);
        st.close();
        status = true;

    
    } catch (SQLException u) {  
        throw new RuntimeException(u);
    }

    return status;
}

public Todolist procurarTodolist(int id) {
    Todolist todolist = null;
    
    try {
        Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery("SELECT * FROM TODOLIST WHERE TODOLIST.Id = " + id);

        if (rs.next()) {
            todolist = new Todolist(rs.getInt("Id"),rs.getString("Nome"),rs.getString("Usuario"));
        }

        st.close();
    } catch (Exception e) {
        System.err.println(e.getMessage());
    }

    return todolist;
}
}