package modelos;

public class Telefone {
    private int ddi;
    private int ddd;
    private int numero;

    public Telefone(int ddi, int ddd, int numero) {
        this.ddi = ddi;
        this.ddd = ddd;
        this.numero = numero;
    }

    public int getDdi() {
        return ddi;
    }

    public void setDdi(int ddi) {
        this.ddi = ddi;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return String.format("%02d%02d%08d", ddi, ddd, numero);
    }

    public static Telefone fromString(String telefoneStr) {
    // Remove espaços em branco e caracteres não numéricos
    telefoneStr = telefoneStr.replaceAll("[^0-9]", "");
    
    if (telefoneStr.length() < 8) {
        throw new NumberFormatException("Telefone string is too short: " + telefoneStr);
    }

    String ddiStr = telefoneStr.substring(0, 2);
    String dddStr = telefoneStr.substring(2, 4);
    String numeroStr = telefoneStr.substring(4);

    int ddi = Integer.parseInt(ddiStr);
    int ddd = Integer.parseInt(dddStr);
    int numero = Integer.parseInt(numeroStr);

    return new Telefone(ddi, ddd, numero);
}
}