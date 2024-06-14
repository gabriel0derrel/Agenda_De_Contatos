/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.principais;

import java.util.ArrayList;

/**
 *
 * @author misuka
 */
public interface Icrud {
    void incluir(Contato objeto) throws Exception;
    void excluir(String nome) throws Exception;
    void alterar(Contato objeto) throws Exception;
    Contato consultar(String nome) throws Exception;
    ArrayList<Contato> listar() throws Exception;
}
