package model;

public class Usuario {
    //----------------------------Atributos--------------------------//

    private int id;
    private String primeiroNome;
    private String segundoNome;
    private int idade;
    private String senha;
    private String email;
    private String sexo;

    //----------------------------Metodos Especiais-------------------------//

    public Usuario() {
        this.id = -1;
        this.primeiroNome = "a";
        this.segundoNome = "b";
        this.idade = 0;
        this.senha = "-";
        this.email = "@";
        this.sexo = "_";
    }

    public Usuario(int id, String primeiroNome, String segundoNome, int idade, String senha, String email, String sexo) {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrimeiroNome() {
        return this.primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return this.segundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "Usuario [id = " + id + ", primeiroNome = " + primeiroNome + ", segundoNome = " + segundoNome + ", idade = " + idade + ", senha = " + senha + ", email = " + email + ", sexo = " + sexo +"]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}
}
