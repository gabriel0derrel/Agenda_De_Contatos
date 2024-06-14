/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.util.List;
import modelos.principais.Contato;
import modelos.principais.Icrud;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import conexao.ConexaoDB;
import java.util.LinkedList;
import modelos.secundarios.Endereco;

public class ContatoDao implements Icrud{
    
    private Connection conexao = null;
    
    public ContatoDao() throws Exception{
        conexao = ConexaoDB.getConexao();
    }
    
    private void criarTabelaCasoNaoExista() throws Exception{
        try {
            String sql = "create table if not exists Contatos(id serial, nome varchar(60) not null unique, telefone varchar(35) not null unique, email varchar(70) not null unique, logradouro varchar(100) not null, numero varchar(15) not null, CEP varchar(25) not null, estado varchar(25) not null, cidade varchar(25) not null, complemento varchar(100), primary key(id));";
            Statement comando = conexao.createStatement();
            comando.execute(sql);
            
            comando.close();
        } catch (SQLException erro) {
            throw erro;
        }
    }
    
    @Override
    public void incluir(Contato objeto) throws Exception{
        try {
            criarTabelaCasoNaoExista();
            String sql = "insert into Contatos(nome, telefone, email, logradouro, numero, CEP, estado, cidade, complemento) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement comando = conexao.prepareStatement(sql);
            
            comando.setString(1, objeto.getNome());
            comando.setString(2, objeto.getTelefone().toString());
            comando.setString(3, objeto.getEmail());
            comando.setString(4, objeto.getEndereco().getLogradouro());
            comando.setString(5, objeto.getEndereco().getNumero());
            comando.setString(6, objeto.getEndereco().getCep());
            comando.setString(7, objeto.getEndereco().getEstado());
            comando.setString(8, objeto.getEndereco().getCidade());
            comando.setString(9, objeto.getEndereco().getComplemento());
            
            comando.executeUpdate();
            
            comando.close();
        } catch (SQLException erro) {
            throw new Exception("Erro na ContatoDAO - Incluir - SQL: " + erro.getMessage());
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Incluir -" + erro.getMessage());
        }
        
    }

    @Override
    public List<Contato> listar() throws Exception{
        LinkedList<Contato> lista = new LinkedList<>();
        try {
            criarTabelaCasoNaoExista();
            String sql = "select * from Contatos order by nome ASC;";
            Statement comando = conexao.createStatement();
            ResultSet resultados = comando.executeQuery(sql);
            while(resultados.next()){
                Contato contatoAuxiliar = new Contato();
                Endereco enderecoAuxiliar = new Endereco();
                
                contatoAuxiliar.setId(resultados.getInt("id"));
                contatoAuxiliar.setNome(resultados.getString("nome"));
                contatoAuxiliar.setTelefone(resultados.getString("telefone"));
                contatoAuxiliar.setEmail(resultados.getString("email"));
                
                enderecoAuxiliar.setLogradouro(resultados.getString("logradouro"));
                enderecoAuxiliar.setNumero(resultados.getString("numero"));
                enderecoAuxiliar.setCep(resultados.getString("CEP"));
                enderecoAuxiliar.setEstado(resultados.getString("estado"));
                enderecoAuxiliar.setCidade(resultados.getString("cidade"));
                enderecoAuxiliar.setComplemento(resultados.getString("complemento"));
                contatoAuxiliar.setEndereco(enderecoAuxiliar);
                
                lista.add(contatoAuxiliar);
            }
            
            resultados.close();
            comando.close();
        } catch (SQLException erro) {
            throw new Exception("Erro na ContatoDAO - Listar - SQL: " + erro.getMessage());
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Listar - " + erro.getMessage());
        }
        return lista;
    }

    @Override
    public Contato consultar(int id) throws Exception {
        Contato contato = new Contato();
        try {
            criarTabelaCasoNaoExista();
            String sql = "select * from Contatos where id = ?;";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            Endereco enderecoAuxiliar = new Endereco();

            resultado.next();
            contato.setId(resultado.getInt("id"));
            contato.setNome(resultado.getString("nome"));
            contato.setTelefone(resultado.getString("telefone"));
            contato.setEmail(resultado.getString("email"));

            enderecoAuxiliar.setLogradouro(resultado.getString("logradouro"));
            enderecoAuxiliar.setNumero(resultado.getString("numero"));
            enderecoAuxiliar.setCep(resultado.getString("CEP"));
            enderecoAuxiliar.setEstado(resultado.getString("estado"));
            enderecoAuxiliar.setCidade(resultado.getString("cidade"));
            enderecoAuxiliar.setComplemento(resultado.getString("complemento"));
            contato.setEndereco(enderecoAuxiliar);
            
            resultado.close();
            comando.close();
        } catch (SQLException erro) {
            throw new Exception("Erro na ContatoDAO - Consultar - SQL: " + erro.getMessage());
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Consultar - " + erro.getMessage());
        }
        return contato;
    }


    @Override
    public void excluir(int id) throws Exception {
        try {
            criarTabelaCasoNaoExista();
            String sql = "delete from Contatos where id = ?;";
            PreparedStatement comando = conexao.prepareStatement(sql);
            comando.setInt(1, id);
            
            comando.executeUpdate();
            
            comando.close();
        } catch (SQLException erro) {
            throw new Exception("Erro na ContatoDAO - Excluir - SQL: " + erro.getMessage());
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Excluir - " + erro.getMessage());
        }
    }

    @Override
    public void alterar(Contato objeto) throws Exception {
        try {
            criarTabelaCasoNaoExista();
            String sql = "update Contatos set telefone = ?, email = ?, logradouro = ?, numero = ?, CEP = ?, estado = ?, cidade = ?, complemento = ? where id = ?;";
            PreparedStatement comando = conexao.prepareStatement(sql);
            
            comando.setString(1, objeto.getTelefone().toString());
            comando.setString(2, objeto.getEmail());
            comando.setString(3, objeto.getEndereco().getLogradouro());
            comando.setString(4, objeto.getEndereco().getNumero());
            comando.setString(5, objeto.getEndereco().getCep());
            comando.setString(6, objeto.getEndereco().getEstado());
            comando.setString(7, objeto.getEndereco().getCidade());
            comando.setString(8, objeto.getEndereco().getComplemento());
            comando.setInt(9, objeto.getId());
            
            comando.executeUpdate();
            
            comando.close();
        } catch (SQLException erro) {
            throw new Exception("Erro na ContatoDAO - Alterar - SQL: " + erro.getMessage());
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Alterar - " + erro.getMessage());
        }
    }
    
    
}
