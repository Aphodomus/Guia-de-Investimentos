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
		return maxId;
	}

    public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "aindanaodefinido";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
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

    public boolean inserirUsuario(Usuario usuario) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO usuario (id, primeiroNome, segundoNome, idade, senha, email, sexo) "
					       + "VALUES (" + usuario.getId() + ", '" + usuario.getPrimeiroNome() + "', '"  
					       + usuario.getSegundoNome() + "', '" + usuario.getIdade() + "', '" + usuario.getSenha() 
                           + "', '" + usuario.getEmail() + "', '" + usuario.getSexo() +"');");
			st.close();
			status = true;

            //Somar mais um ao maxID
            this.maxId = this.maxId + 1;

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
			String sql = "UPDATE usuario SET primeiroNome = '" + usuario.getPrimeiroNome() + "', segundoNome = '"  
				       + usuario.getSegundoNome() + "', idade = '" + usuario.getIdade() + "', senha = '" + usuario.getSenha() + 
                       "', email = '" + usuario.getEmail() + "', sexo = '" + usuario.getSexo() + "'" + " WHERE id = " + usuario.getId();
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
			st.executeUpdate("DELETE FROM usuario WHERE id = " + id);
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
			ResultSet rs = st.executeQuery("SELECT * FROM usuario");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                usuarios[i] = new Usuario(rs.getInt("id"), rs.getString("primeiroNome"), 
	                		                  rs.getString("segundoNome"), rs.getInt("idade"), rs.getString("senha"), rs.getString("email"), rs.getString("sexo"));
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
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.idade >= 60");		
	         if(rs.next()){
	             rs.last();
	             usuarios = new Usuario[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
		                usuarios[i] = new Usuario(rs.getInt("id"), rs.getString("primeiroNome"), 
                                                  rs.getString("segundoNome"), rs.getInt("idade"), rs.getString("senha"), rs.getString("email"), rs.getString("sexo"));
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
			ResultSet rs = st.executeQuery("SELECT * FROM usuario WHERE usuario.id = " + id);

			if (rs.next()) {
				usuarios = new Usuario(rs.getInt("id"), rs.getString("primeiroNome"), 
                                       rs.getString("segundoNome"), rs.getInt("idade"), rs.getString("senha"), rs.getString("email"), rs.getString("sexo"));
			}

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return usuarios;
	}
}
