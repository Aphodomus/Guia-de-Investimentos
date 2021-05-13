package dao;

import java.sql.*;
import model.Anotacoes;

public class AnotacoesDAO {
    private Connection conexao;
    private int maxId;
    
    public AnotacoesDAO() {
        conexao = null;
    }

    public int getMaxCodigo() {
        return maxId;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                                 
		String serverName = "projetosirius.postgres.database.azure.com"; // Nome da azure que ela vai nos fornecer
		String mydatabase = "sirius"; // Eu tenho que criar na azure
		int porta = 5432; // Vou escolher na azure
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase+ "?gssEncMode=disable"; 
		String username = "projetoSirius@projetosirius";
		String password = "siriusProjeto01";
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

    public boolean inserirAnotacao(Anotacoes anotacoes) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO anotacoes (todolist, idanotacoes, datacriacao, descricao) "
					       + "VALUES (" + anotacoes.getToDoList() + ", '" + anotacoes.getIdAnotacoes() + "', '"  
					       + anotacoes.getDataCriacao() + "', '" + anotacoes.getDescricao() + "');");
			st.close();
			status = true;

            //Somar mais um ao maxID
            this.maxId = this.maxId + 1;

			System.out.println("Insercao da anotacao com id [" + anotacoes.getIdAnotacoes() + "] efetuada com sucesso.");

		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public boolean atualizarAnotacao(Anotacoes anotacoes) {
        boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE anotacoes SET descricao = '" + anotacoes.getDescricao() + "' WHERE idanotacoes = " + anotacoes.getIdAnotacoes();
			st.executeUpdate(sql);
			st.close();
			status = true;

			System.out.println("Atualizacao da anotacao com codigo [" + anotacoes.getIdAnotacoes() + "] efetuada com sucesso.");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
    }

    public boolean excluirAnotacao(int id) {
		boolean status = false;

		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM anotacoes WHERE idanotacoes = " + id);
			st.close();
			status = true;

            //subtrair um ao maxID
            if (this.maxId > 0) {
                this.maxId = this.maxId - 1;
            }

			System.out.println("Remocao da anotacao com id [" + id + "] efetuada com sucesso.");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}

		return status;
	}

    public Anotacoes[] getAnotacoes() {
		Anotacoes[] anotacoes = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM anotacoes");		
	         if(rs.next()){
	             rs.last();
	             anotacoes = new Anotacoes[rs.getRow()];
	             rs.beforeFirst();

	             for(int i = 0; rs.next(); i++) {
	                anotacoes[i] = new Anotacoes(rs.getInt("todolist"), rs.getInt("idanotacoes"), 
	                		                  rs.getDate("datacriacao"), rs.getString("descricao"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return anotacoes;
	}

    public Anotacoes procurarAnotacao(int id) {
		Anotacoes anotacoes = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM anotacoes WHERE anotacoes.idanotacoes = " + id);

			if (rs.next()) {
				anotacoes = new Anotacoes(rs.getInt("todolist"), rs.getInt("idanotacoes"), 
                                          rs.getDate("datacriacao"), rs.getString("descricao"));
			}

	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return anotacoes;
	}
}
