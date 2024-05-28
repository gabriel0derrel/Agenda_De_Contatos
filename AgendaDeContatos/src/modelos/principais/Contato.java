/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.principais;

import modelos.secundarios.*;

/**
 *
 * @author misuka
 */
public class Contato {
    private String nome = "";
    private Telefone telefone = null;
    private String email = "";
    private Endereco endereco = null;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = new Telefone(telefone.substring(0, 3), telefone.substring(3, 5), telefone.substring(5));
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    
    public void setEndereco(String logradouro, String numero, String complemento, String cep, String cidade, String estado){
        this.endereco = new Endereco(logradouro, numero, complemento, cep, cidade, estado);
    }

    public Contato(String nome, Telefone telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Contato() {
    }

    @Override
    public String toString() {
        return "Contato{" + "nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", endereco=" + endereco + '}';
    }
    
    
}
