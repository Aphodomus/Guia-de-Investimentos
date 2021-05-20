package model;

public class Usuario {
    //----------------------------Atributos--------------------------//

    private int Id;
    private String Nome;
    private String Sobrenome;
    private int Idade;
    private String Senha;
    private String Email;
    private String Sexo;

    //----------------------------Metodos Especiais-------------------------//

    public Usuario() {
        this.Id = -1;
        this.Nome = "a";
        this.Sobrenome = "b";
        this.Idade = 0;
        this.Senha = "-";
        this.Email = "@";
        this.Sexo = "_";
    }

    public Usuario(String Nome, String Sobrenome, int Idade, String Senha, String Email, String Sexo) {
        setEmail(Email);
        setIdade(Idade);
        setNome(Nome);
        setSobreNome(Sobrenome);
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
        if (Nome.length() > 2 && Nome.length() <= 1000) {
            this.Nome = Nome;
        } else {
            System.out.println("Erro !!! Nome menor ou igual a 2 OU maior que 1000 caracteres");
        }
    }

    public String getSobreNome() {
        return this.Sobrenome;
    }

    public void setSobreNome(String Sobrenome) {
        if (Sobrenome.length() > 2 && Sobrenome.length() <= 1000) {
            this.Sobrenome = Sobrenome;
        } else {
            System.out.println("Erro !!! Sobrenome menor ou igual a 2 OU maior que 1000 caracteres");
        }
    }

    public int getIdade() {
        return this.Idade;
    }

    public void setIdade(int Idade) {
        if (Idade > 9 && Idade < 120) {
            this.Idade = Idade;
        } else {
            System.out.println("Erro !!! Idade menor 10 OU maior que 120 ");
        }
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
        if (Sexo.equals("opcao2")) {
            this.Sexo = "Feminino";
        } else if (Sexo.equals("opcao1")) {
            this.Sexo = "Masculino";
        } else {
            
        }
    }

    //----------------------------Funcoes e Metodos-------------------------//

    //Metodo sobreposto que e executado quando um objeto precisa ser mostrado na forma de String
    @Override
    public String toString() {
        return "Usuario [Id = " + Id + ", Nome = " + Nome + ", Sobrenome = " + Sobrenome + ", Idade = " + Idade + ", Senha = " + Senha + ", Email = " + Email + ", Sexo = " + Sexo +"]";
    }

    //Metodo sobreposto para verificar se 2 objetos sao iguais
    @Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Usuario) obj).getId());
	}
}

