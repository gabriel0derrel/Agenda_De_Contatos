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
            if(objeto.getNome().isEmpty()) stringDeErro += "Erro na Controle - Incluir - O Nome não pode estar vazio\n";
            
            if(objeto.getTelefone().getDdi().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O DDI não pode estar vazio\n";
            if(objeto.getTelefone().getDdd().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O DDD não pode estar vazio\n";
            if(objeto.getTelefone().getNumero().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Numero de Telefone não pode estar vazio\n";
            
            if(objeto.getEmail().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O E-mail não pode estar vazio\n";
            
            if(objeto.getEndereco().getLogradouro().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Logradouro não pode estar vazio\n";
            if(objeto.getEndereco().getNumero().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Número do Endereço não pode estar vazio\n";
            if(!objeto.getEndereco().getNumero().matches("\\d+")) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Numero do Endereço deve conter apenas números.\n";            
            if(objeto.getEndereco().getCep().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O CEP não pode estar vazio\n";                
            if(objeto.getEndereco().getCidade().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - A Cidade não pode estar vazio\n";
            if(objeto.getEndereco().getEstado().isEmpty()) stringDeErro += "Erro na Controle - "+nomeDaFuncao+" - O Estado não pode estar vazio\n";
            
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
    public Contato consultar(String nome) throws Exception {
        try {
            String stringDeErro = "";
            if(nome.isEmpty()) stringDeErro += "Erro na Controle - Consultar - O Nome não pode estar vazio.\n";
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
            nome = nome.toUpperCase();
            return persistencia.consultar(nome);
        } catch (Exception erro) {
            throw erro;
        }
    }
    
}
