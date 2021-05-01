package model;

public class Usuario {
    //----------------------------Atributos--------------------------//

    private int Id;
    private String Nome;
    private String SobreNome;
    private int Idade;
    private String Senha;
    private String Email;
    private String Sexo;

    //----------------------------Metodos Especiais-------------------------//

    public Usuario() {
        this.Id = -1;
        this.Nome = "a";
        this.SobreNome = "b";
        this.Idade = 0;
        this.Senha = "-";
        this.Email = "@";
        this.Sexo = "_";
    }

    public Usuario(int Id, String Nome, String SobreNome, int Idade, String Senha, String Email, String Sexo) {
        setId(Id);
        setEmail(Email);
        setIdade(Idade);
        setNome(Nome);
        setSobreNome(SobreNome);
        setSenha(Senha);
        setSexo(Sexo);
    }

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getSobreNome() {
        return this.SobreNome;
    }

    public void setSobreNome(String SobreNome) {
        this.SobreNome = SobreNome;
    }

    public int getIdade() {
        return this.Idade;
    }

    public void setIdade(int Idade) {
        this.Idade = Idade;
    }

    public String getSenha() {
        return this.Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public String getEmail() {
        return this.Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSexo() {
        return this.Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "Usuario [Id = " + Id + ", Nome = " + Nome + ", SobreNome = " + SobreNome + ", Idade = " + Idade + ", Senha = " + Senha + ", Email = " + Email + ", Sexo = " + Sexo +"]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}
}
