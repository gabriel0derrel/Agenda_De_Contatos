/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import modelos.principais.Contato;
import modelos.principais.Icrud;
import modelos.secundarios.Comparador;

/**
 *
 * @author misuka
 */
public class ContatoDao implements Icrud{
    
    private Path encontrarCaminhoDoArquivo(String nomeDoAqruivo) throws Exception{
        try{
            String packagePath = "src/arquivos";
            Path currentWorkingDir = Paths.get("").toAbsolutePath();
            Path packageDir = currentWorkingDir.resolve(packagePath);
            if(!Files.exists(packageDir)){
                Files.createDirectories(packageDir);
            }
            Path filePath = packageDir.resolve(nomeDoAqruivo);
            return filePath;
        }catch(Exception erro){
            throw new Exception("Erro na ContatoDao - encontrarCaminhoDoArquivo - Erro na Manipulaçõa de Arquivo");
        }
    }
    
    @Override
    public void incluir(Contato objeto) throws Exception{
        try {
            Path caminhoDoArquivo = encontrarCaminhoDoArquivo("Contatos.csv");
            FileWriter arquivo = new FileWriter(caminhoDoArquivo.toFile(), true);
            BufferedWriter buffArquivo = new BufferedWriter(arquivo);
            String contato = "";
            contato += objeto.getNome()+";"+objeto.getTelefone().toString()+";"+objeto.getEmail().toString()+";"+objeto.getEndereco().toStringArquivo();
            buffArquivo.append(contato+"\n");
            buffArquivo.close();
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Incluir - Erro manipulacao do arquivio");
        }
        
    }

    @Override
    public ArrayList<Contato> listar() throws Exception{
        try {
            ArrayList<Contato> listaDeContatos = new ArrayList<>();
            Contato contatoAuxiliar = null;
            Path caminhoDoArquivo = encontrarCaminhoDoArquivo("Contatos.csv");
            FileReader arquivo = new FileReader(caminhoDoArquivo.toFile());
            BufferedReader buffArquivo = new BufferedReader(arquivo);
            String stringAuxiliar = "";
            String[] vetorAuxiliar = null;
            while((stringAuxiliar=buffArquivo.readLine())!=null){
                vetorAuxiliar = stringAuxiliar.split("[;]+");
                contatoAuxiliar = new Contato();
                contatoAuxiliar.setNome(vetorAuxiliar[0]);
                contatoAuxiliar.setTelefone(vetorAuxiliar[1]);
                contatoAuxiliar.setEmail(vetorAuxiliar[2]);
                contatoAuxiliar.setEndereco(vetorAuxiliar[3], vetorAuxiliar[4], vetorAuxiliar[5], vetorAuxiliar[6], vetorAuxiliar[7], vetorAuxiliar[8]);
                listaDeContatos.add(contatoAuxiliar);
            }
            if(listaDeContatos.size() >= 2){
                Collections.sort(listaDeContatos, new Comparador());
            }
            buffArquivo.close();
            return listaDeContatos;
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Listar - Erro manipulacao do arquivio");
        } 
    }

    @Override
    public ArrayList<Contato> consultar(Contato filtro) throws Exception {
        ArrayList<Contato> contatosFiltrados = new ArrayList<>();
        try {
            ArrayList<Contato> listaDeContatos = listar();

            for (Contato contato : listaDeContatos) {
                boolean match = true;

                if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                    if (!contato.getNome().toLowerCase().contains(filtro.getNome().toLowerCase())) {
                        match = false;
                    }
                }

                if (filtro.getTelefone() != null && !filtro.getTelefone().toString().isEmpty()) {
                    if (!contato.getTelefone().toString().contains(filtro.getTelefone().toString())) {
                        match = false;
                    }
                }

                if (filtro.getEmail() != null && !filtro.getEmail().isEmpty()) {
                    if (!contato.getEmail().toLowerCase().contains(filtro.getEmail().toLowerCase())) {
                        match = false;
                    }
                }

                if (filtro.getEndereco().getEstado() != null && !filtro.getEndereco().getEstado().isEmpty()) {
                    if (!contato.getEndereco().getEstado().toLowerCase().contains(filtro.getEndereco().getEstado().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getCep()!= null && !filtro.getEndereco().getCep().isEmpty()) {
                    if (!contato.getEndereco().getCep().toLowerCase().contains(filtro.getEndereco().getCep().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getComplemento()!= null && !filtro.getEndereco().getComplemento().isEmpty()) {
                    if (!contato.getEndereco().getComplemento().toLowerCase().contains(filtro.getEndereco().getComplemento().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getLogradouro()!= null && !filtro.getEndereco().getLogradouro().isEmpty()) {
                    if (!contato.getEndereco().getLogradouro().toLowerCase().contains(filtro.getEndereco().getLogradouro().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getNumero()!= null && !filtro.getEndereco().getNumero().isEmpty()) {
                    if (!contato.getEndereco().getNumero().toLowerCase().contains(filtro.getEndereco().getNumero().toLowerCase())) {
                        match = false;
                    }
                }
                
                if (filtro.getEndereco().getCidade()!= null && !filtro.getEndereco().getCidade().isEmpty()) {
                    if (!contato.getEndereco().getCidade().toLowerCase().contains(filtro.getEndereco().getCidade().toLowerCase())) {
                        match = false;
                    }
                }

                if (match) {
                    contatosFiltrados.add(contato);
                }
            }

            if (contatosFiltrados.isEmpty()) {
                throw new Exception("Contato não Encontrado");
            }
        } catch (Exception erro) {
            throw erro;
        }

        return contatosFiltrados;
    }


    @Override
    public void excluir(String nome) throws Exception {
        try {
            Path caminhoDoArquivo = encontrarCaminhoDoArquivo("Contatos.csv");
            ArrayList<Contato> listaDeContatos = listar();
            for(int i = 0; i < listaDeContatos.size(); i++){
                if(listaDeContatos.get(i).getNome().equals(nome)){
                    listaDeContatos.remove(i);
                    break;
                }
                System.out.println(listaDeContatos.get(i).toString());
            }
            FileWriter arquivo = new FileWriter(caminhoDoArquivo.toFile(), false);
            BufferedWriter buffArquivo = new BufferedWriter(arquivo);
            for(int iterador = 0; iterador < listaDeContatos.size(); iterador++){
                String linha = listaDeContatos.get(iterador).getNome()+";"+listaDeContatos.get(iterador).getTelefone().toString()+
                        ";"+listaDeContatos.get(iterador).getEmail()+";"+listaDeContatos.get(iterador).getEndereco().getLogradouro()+
                        ";"+listaDeContatos.get(iterador).getEndereco().getNumero()+";"+listaDeContatos.get(iterador).getEndereco().getComplemento()+
                        ";"+listaDeContatos.get(iterador).getEndereco().getCep()+";"+listaDeContatos.get(iterador).getEndereco().getCidade()+
                        ";"+listaDeContatos.get(iterador).getEndereco().getEstado();
                buffArquivo.write(linha+"\n");
            }
            buffArquivo.close();
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Excluir - Erro manipulacao do arquivio");
        }
    }

    @Override
    public void alterar(Contato objeto) throws Exception {
        try {
            excluir(objeto.getNome());
            incluir(objeto);
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Alterar - Erro manipulacao do arquivio");
        }
    }
    
    
}
