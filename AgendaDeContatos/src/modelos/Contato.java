package modelos;

public class Contato {
    String nome;
    Telefone telefone;
    String email;

    public Contato(String nome, Telefone telefone, String email) {
        this.nome = nome.toUpperCase();
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome + ";" + telefone + ";" + email;
    }

    public static Contato fromString(String contatoStr) {
    String[] partes = contatoStr.split(";");
    if (partes.length < 3) {
        throw new IllegalArgumentException("Invalid contact string: " + contatoStr);
    }
    return new Contato(partes[0].trim(), Telefone.fromString(partes[1].trim()), partes[2].trim());
}
}