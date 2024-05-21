/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.principais;

import modelos.secundarios.Email;
import modelos.secundarios.Telefone;

/**
 *
 * @author misuka
 */
public class Contato {
    private String nome = "";
    private Telefone telefone = null;
    private Email email = null;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws Exception{
        if(nome.isEmpty()) throw new Exception("O Nome não pode estar vazio");
        this.nome = nome;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) throws Exception{
        if(telefone == null) throw new Exception("O Telefone não pode estar vazio");
        this.telefone = telefone;
    }
    
    public void setTelefone(String telefone) throws Exception{
        if(telefone.isEmpty()) throw new Exception("O Telefone não pode estar vazio.");
        String[] vetorTelefone = telefone.split("\\D+");
        this.telefone = new Telefone(Integer.parseInt(vetorTelefone[1]), Integer.parseInt(vetorTelefone[2]), Integer.parseInt(vetorTelefone[3]));
        
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) throws Exception{
        if(email == null) throw new Exception("O Email não pode estar vazio");        
        this.email = email;
    }
    
    public void setEmail(String email) throws Exception{
        if(email.isEmpty()) throw new Exception("O Email não pode estar vazio.");
        String[] vetorEmail = email.split("[@.]+", 3);
        this.email = new Email(vetorEmail[0], vetorEmail[1], vetorEmail[2]);
    }

    public Contato(String nome, Telefone telefone, Email email) throws Exception{
        if(nome.isEmpty()) throw new Exception("O Nome não pode estar vazio");
        this.nome = nome;
        if(telefone == null) throw new Exception("O Telefone não pode estar vazio");
        this.telefone = telefone;
        if(email == null) throw new Exception("O Email não pode estar vazio");        
        this.email = email;
    }

    public Contato() {
    }
    
    
}
