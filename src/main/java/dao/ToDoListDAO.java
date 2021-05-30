package dao;

import java.sql.*;
import model.ToDoList;

public class ToDoListDAO{
    private Connection conexao;
    private int maxId;

    public ToDoListDAO(){
        conexao = null;
    }
    
    public int getMaxIdToDoList(){
        return this.maxId;
    }
    
    public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "projetosirius.postgres.database.azure.com";
		String mydatabase = "sirius";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase + "?gssEncMode=disable";;
		String username = "projetoSirius@projetosirius";
		String password = "siriusProjeto01";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
			System.out.println("Conexão todolist efetuada");
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
    
    public boolean inserirToDoList(ToDoList todolist) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO todolist (nome, usuario) "
					       + "VALUES ('" + todolist.getNome() + "', '"  
					       + todolist.getUsuario() + "');");
			st.close();
			status = true;

			System.out.println("Insercao da todolist com id [" + todolist.getIdToDoList() + "] efetuada com sucesso.");

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}
    
    
    
    public boolean atualizarToDoList(ToDoList todolist) {
        boolean status = false;
    
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE todolist SET nome = '" + todolist.getNome() + "', usuario = '"  
             + todolist.getUsuario() + "'" + "WHERE idtodolist = " + todolist.getIdToDoList();
            st.executeUpdate(sql);
            st.close();
            status = true;
    
        
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
    
        return status;
    }
    
    public ToDoList procurarToDoList(int idtodolist) {
		ToDoList todolist = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM TODOLIST WHERE TODOLIST.idtodolist = " + idtodolist);
		
			
			if (rs.next()) {
				todolist = new ToDoList(rs.getString("nome"));
			}

            System.out.println("id: " + todolist.getIdToDoList());

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return todolist;
	}

    public boolean excluirToDoList(int id) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM todolist WHERE idtodolist = " + id);
			st.close();
			status = true;

			System.out.println("Remocao da todolist com idtodolist [" + id + "] efetuada com sucesso.");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public ToDoList[] getToDoList() {
        ToDoList[] todolist = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM todolist");		
            if(rs.next()){
                rs.last();
                todolist = new ToDoList[rs.getRow()];
                rs.beforeFirst();

                for(int i = 0; rs.next(); i++) {
                    todolist[i] = new ToDoList(rs.getString("nome"));
                }
	        }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return todolist;
    }

	public int idToDoList(ToDoList todolist) {
		int resp = -1;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT TODOLIST.idtodolist FROM TODOLIST WHERE TODOLIST.usuario = '" + todolist.getUsuario() + "'");		
	         
			if (rs.next()) {
				int id = rs.getInt("idtodolist");
				resp = id;
			}

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return resp;
	}

	public int pesquisarToDoListID(int id) {
		int resp = 0;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT TODOLIST.idtodolist FROM TODOLIST WHERE TODOLIST.usuario = " + id);		
	         
			if (rs.next()) {
				int frase = rs.getInt("idtodolist");
				resp = frase;
			}

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return resp;
	}
}

