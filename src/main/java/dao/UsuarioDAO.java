package dao;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO {
    private Connection conexao;
    private int maxId;

    public UsuarioDAO() {
		conexao = null;
	}

    public int getMaxCodigo() {
		return this.maxId;
	}

    public boolean conectar() {
		String driverName = "org.postgresql.Driver";                                 
		String serverName = "localhost"; // Nome da azure que ela vai nos fornecer
		String mydatabase = "teste01"; // Eu tenho que criar na azure
		int porta = 5432; // Vou escolher na azure
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase; //+ "?gssEncMode=disable"; 
		String username = "postgres";
		String password = "truta74822";
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

    public boolean inserirUsuario(Usuario usuario) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuario (id, sexo, senha, idade, email, nome, sobrenome) "
					       + "VALUES (" + usuario.getId() + ", '" + usuario.getSexo() + "', '"  
					       + usuario.getSenha() + "', '" + usuario.getIdade() + "', '" + usuario.getEmail()
                           + "', '" + usuario.getNome()  + "', '" + usuario.getSobreNome() +"');");
			st.close();
			status = true;

            //Somar mais um ao maxID
			this.maxId = maxId + 1;
            usuario.setId(maxId + 1);

			System.out.println("Insercao do usuario com id [" + usuario.getId() + "] efetuada com sucesso.");

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public boolean atualizarUsuario(Usuario usuario) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE USUARIO SET Nome = '" + usuario.getNome() + "', Sobrenome = '"  
				       + usuario.getSobreNome() + "', Idade = '" + usuario.getIdade() + "', Senha = '" + usuario.getSenha() + 
                       "', Email = '" + usuario.getEmail() + "', Sexo = '" + usuario.getSexo() + "'" + " WHERE Id = " + usuario.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;

			System.out.println("Atualizacao do usuario com codigo [" + usuario.getId() + "] efetuada com sucesso.");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public boolean excluirUsuario(int id) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM USUARIO WHERE Id = " + id);
			st.close();
			status = true;

            //subtrair um ao maxID
            if (this.maxId > 0) {
                this.maxId = this.maxId - 1;
            }

			System.out.println("Remocao do Usuario com id [" + id + "] efetuada com sucesso.");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public Usuario[] getUsuarios() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIO");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("Id"), rs.getString("Nome"), 
	                		                  rs.getString("Sobrenome"), rs.getInt("Idade"), rs.getString("Senha"), rs.getString("Email"), rs.getString("Sexo"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return usuarios;
	}

    public Usuario[] getUsuariosIdosos() {
		Usuario[] usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIO WHERE USUARIO.Idade >= 60");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		                usuarios[i] = new Usuario(rs.getInt("Id"), rs.getString("Nome"), 
                                                  rs.getString("Sobrenome"), rs.getInt("Idade"), rs.getString("Senha"), rs.getString("Email"), rs.getString("Sexo"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return usuarios;
	}

    public Usuario procurarUsuario(int id) {
		Usuario usuarios = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIO WHERE USUARIO.Id = " + id);

			if (rs.next()) {
				usuarios = new Usuario(rs.getInt("Id"), rs.getString("Nome"), 
                                       rs.getString("Sobrenome"), rs.getInt("Idade"), rs.getString("Senha"), rs.getString("Email"), rs.getString("Sexo"));
			}

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return usuarios;
	}
}
