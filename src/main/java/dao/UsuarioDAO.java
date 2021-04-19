package dao;

import java.sql.*;
import model.Usuario;

public class UsuarioDAO {
    private Connection conexao;
    private int maxCodigo;

    public UsuarioDAO() {
		conexao = null;
	}

    public int getMaxCodigo() {
		return maxCodigo;
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
			st.executeUpdate("INSERT INTO livro (id, primeiroNome, segundoNome, idade, senha, email, sexo) "
					       + "VALUES (" + usuario.getId() + ", '" + usuario.getPrimeiroNome() + "', '"  
					       + usuario.getSegundoNome() + "', '" + usuario.getIdade() + "', '" + usuario.getSenha() 
                           + "', '" + usuario.getEmail() + "', '" + usuario.getSexo() +"');");
			st.close();
			status = true;

            //Somar mais um ao maxID
            this.maxCodigo = this.maxCodigo + 1;

			System.out.println("Insercao do usuario com id [" + usuario.getId() + "] efetuada com sucesso.");

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}
}
