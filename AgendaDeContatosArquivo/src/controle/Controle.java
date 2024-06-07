/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.ArrayList;
import modelos.principais.Contato;
import persistencia.ContatoDao;
import modelos.principais.Icrud;

/**
 *
 * @author puc
 */
public class Controle implements Icrud{
    
    private final Icrud persistencia = new ContatoDao();
    
    private void validarContato(Contato objeto, String nomeDaFuncao) throws Exception{
        try {
            String stringDeErro = "";
            if(objeto.getNome().compareTo("Insira seu nome...") == 0) stringDeErro += "Erro na Controle - Incluir - O Nome não pode estar vazio\n";
            
            if(objeto.getTelefone().toString().compareTo("+000 (00) 0 0000-0000") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Numero de Telefone não pode estar vazio\n";
            
            if(objeto.getEmail().compareTo("usuario@gmail.com") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O E-mail não pode estar vazio\n";
            
            if(objeto.getEndereco().getLogradouro().compareTo("Logradouro") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Logradouro não pode estar vazio\n";
            if(objeto.getEndereco().getNumero().compareTo("Número") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Número do Endereço não pode estar vazio\n";      
            if(objeto.getEndereco().getCep().compareTo("CEP") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O CEP não pode estar vazio\n";                
            if(objeto.getEndereco().getCidade().compareTo("Cidade") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - A Cidade não pode estar vazio\n";
            if(objeto.getEndereco().getEstado().compareTo("Estado") == 0) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Estado não pode estar vazio\n";
            
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
        } catch (Exception erro) {
            throw erro;
        }
    }
    
    @Override
    public void incluir(Contato objeto) throws Exception {
        try {
            validarContato(objeto, "Incluir");
            objeto.setNome(objeto.getNome().toUpperCase());
            persistencia.incluir(objeto);
        } catch (Exception erro) {
            throw erro;
        }
        
    }

    @Override
    public ArrayList<Contato> listar() throws Exception {
        try {
            return persistencia.listar();
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void excluir(String nome) throws Exception {
        try {
            String stringDeErro = "";
            if(nome.isEmpty()) stringDeErro += "Erro na Controle - Excluir - O Nome não pode estar vazio.\n";
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
            nome = nome.toUpperCase();
            persistencia.excluir(nome);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void alterar(Contato objeto) throws Exception {
        try {
            validarContato(objeto, "Alterar");
            objeto.setNome(objeto.getNome().toUpperCase());
            persistencia.alterar(objeto);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public ArrayList<Contato> consultar(Contato filtro) throws Exception {
        try {
            String stringDeErro = "";
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
            return persistencia.consultar(filtro);
        } catch (Exception erro) {
            throw erro;
        }
    }
    
}
