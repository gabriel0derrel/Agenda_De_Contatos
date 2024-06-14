/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.secundarios;

/**
 *
 * @author misuka
 */
public class Endereco {
    private String logradouro = "";
    private String numero = "";
    private String cep = "";
    private String estado = "";
    private String cidade = "";
    private String complemento = "";

    public Endereco(String logradouro, String numero, String complemento, String cep, String cidade, String estado) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.complemento = complemento;
    }

    public Endereco() {
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Endereco{" + "logradouro=" + logradouro + ", numero=" + numero + ", cep=" + cep + ", estado=" + estado + ", cidade=" + cidade + ", complemento=" + complemento + '}';
    }
    
}
