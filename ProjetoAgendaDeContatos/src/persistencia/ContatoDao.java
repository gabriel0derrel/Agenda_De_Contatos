/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
    
    @Override
    public void incluir(Contato objeto) throws Exception{
        try {
            FileWriter arquivo = new FileWriter("Contatos.csv", true);
            BufferedWriter buffArquivo = new BufferedWriter(arquivo);
            String contato = "";
            contato += objeto.getNome()+";"+objeto.getTelefone().toString()+";"+objeto.getEmail().toString();
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
            FileReader arquivo = new FileReader("Contatos.csv");
            BufferedReader buffArquivo = new BufferedReader(arquivo);
            String stringAuxiliar = "";
            String[] vetorAuxiliar = null;
            while((stringAuxiliar=buffArquivo.readLine())!=null){
                vetorAuxiliar = stringAuxiliar.split("[;]+",3);
                contatoAuxiliar = new Contato();
                contatoAuxiliar.setNome(vetorAuxiliar[0].toUpperCase());
                contatoAuxiliar.setTelefone(vetorAuxiliar[1]);
                contatoAuxiliar.setEmail(vetorAuxiliar[2]);
                listaDeContatos.add(contatoAuxiliar);
            }
            Collections.sort(listaDeContatos, new Comparador());
            return listaDeContatos;
        } catch (Exception erro) {
            throw new Exception("Erro na ContatoDAO - Listar - Erro manipulacao do arquivio");
        }
        
    }
    
}
