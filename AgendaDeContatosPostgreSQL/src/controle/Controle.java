/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.util.List;
import modelos.principais.Contato;
import persistencia.ContatoDao;
import modelos.principais.Icrud;

/**
 *
 * @author puc
 */
public class Controle implements Icrud{
    
    private Icrud persistencia = null;
    
    
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
            if(persistencia == null){
                persistencia = new ContatoDao();
            }
            validarContato(objeto, "Incluir");
            objeto.setNome(objeto.getNome().toUpperCase());
            persistencia.incluir(objeto);
        } catch (Exception erro) {
            throw erro;
        }
        
    }

    @Override
    public List<Contato> listar() throws Exception {
        try {
            if(persistencia == null){
                persistencia = new ContatoDao();
            }
            return persistencia.listar();
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try {
            if(persistencia == null){
                persistencia = new ContatoDao();
            }
            String stringDeErro = "";
            if(id < 0) stringDeErro += "Erro na Controle - Excluir - O Id não pode ser < 0\n";
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
            persistencia.excluir(id);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public void alterar(Contato objeto) throws Exception {
        try {
            if(persistencia == null){
                persistencia = new ContatoDao();
            }
            validarContato(objeto, "Alterar");
            objeto.setNome(objeto.getNome().toUpperCase());
            persistencia.alterar(objeto);
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public Contato consultar(int id) throws Exception {
        try {
            if(persistencia == null){
                persistencia = new ContatoDao();
            }
            String stringDeErro = "";
            if(id < 0) stringDeErro += "Erro na Controle - Consultar - O Id não pode ser < 0\n";
            if(!stringDeErro.isEmpty()) throw new Exception(stringDeErro);
            return persistencia.consultar(id);
        } catch (Exception erro) {
            throw erro;
        }
    }
    
}
