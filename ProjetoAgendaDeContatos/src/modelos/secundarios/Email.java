/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.secundarios;

/**
 *
 * @author misuka
 */
public class Email {
    private String nome = "";
    private String provedor = "";
    private String dominio = "";

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome.isEmpty()) throw new Exception("O Nome do email não pode estar vazio");
        this.nome = nome;
    }

    public String getProvedor() {
        return provedor;
    }

    public void setProvedor(String provedor) throws Exception{
        if(provedor.isEmpty()) throw new Exception("O Provedor do email não pode estar vazio");
        this.provedor = provedor;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) throws Exception{
        if(dominio.isEmpty()) throw new Exception("O Domínio do email não pode estar vazio");
        this.dominio = dominio;
    }

    public Email(String nome, String provedor, String dominio) throws Exception{
        if(nome.isEmpty()) throw new Exception("O Nome do email não pode estar vazio");
        this.nome = nome;
        if(provedor.isEmpty()) throw new Exception("O Provedor do email não pode estar vazio");
        this.provedor = provedor;
        if(dominio.isEmpty()) throw new Exception("O Domínio do email não pode estar vazio");
        this.dominio = dominio;
    }

    public Email() {
    }

    @Override
    public String toString() {
        return "" + nome + "@" + provedor + "." + dominio;
    }
    
    
}
