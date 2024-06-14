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
    private int id = 0;
    private String nome = "";
    private Telefone telefone = null;
    private String email = "";
    private Endereco endereco = null;

    private enum Coluna {
        id, nome, telefone, email, logradouro, numero, CEP, estado, cidade, complemento
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        String[] vetorAuxiliar = telefone.split("\\D+");
        this.telefone = new Telefone(vetorAuxiliar[1], vetorAuxiliar[2], vetorAuxiliar[3]+" "+vetorAuxiliar[4]+"-"+vetorAuxiliar[5]);
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

    public Contato(int id, String nome, Telefone telefone, String email, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Contato() {
    }
    
}
