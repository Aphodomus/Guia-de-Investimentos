package model;

public class Usuario {
    //----------------------------Atributos--------------------------//

    private int id;
    private String nome;
    private String sobrenome;
    private int idade;
    private String senha;
    private String email;
    private String sexo;

    //----------------------------Metodos Especiais-------------------------//

    public Usuario() {
        this.id = -1;
        this.nome = "a";
        this.sobrenome = "b";
        this.idade = 0;
        this.senha = "-";
        this.email = "@";
        this.sexo = "_";
    }

    public Usuario(int id, String nome, String sobrenome, int idade, String senha, String email, String sexo) {
        setId(id);
        setEmail(email);
        setIdade(idade);
        setNome(nome);
        setSobreNome(sobrenome);
        setSenha(senha);
        setSexo(sexo);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int Id) {
        this.id = Id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String Nome) {
        if (Nome.length() > 2 && Nome.length() <= 1000) {
            this.nome = Nome;
        } else {
            System.out.println("Erro !!! Nome menor ou igual a 2 OU maior que 1000 caracteres");
        }
    }

    public String getSobreNome() {
        return this.sobrenome;
    }

    public void setSobreNome(String Sobrenome) {
        if (Sobrenome.length() > 2 && Sobrenome.length() <= 1000) {
            this.sobrenome = Sobrenome;
        } else {
            System.out.println("Erro !!! Sobrenome menor ou igual a 2 OU maior que 1000 caracteres");
        }
    }

    public int getIdade() {
        return this.idade;
    }

    public void setIdade(int Idade) {
        if (Idade > 9 && Idade < 120) {
            this.idade = Idade;
        } else {
            System.out.println("Erro !!! Idade menor 10 OU maior que 120 ");
        }
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String Senha) {
        this.senha = Senha;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String Email) {
        this.email = Email;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String Sexo) {
        if (sexo == "opcao1") {
            this.sexo = "Masculino";
        } else {
            this.sexo = "Feminino";
        }
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "Usuario [Id = " + id + ", Nome = " + nome + ", Sobrenome = " + sobrenome + ", Idade = " + idade + ", Senha = " + senha + ", Email = " + email + ", Sexo = " + sexo +"]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}
}
