/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.secundarios;

import java.util.Comparator;
import modelos.principais.Contato;

/**
 *
 * @author misuka
 */
public class Comparador implements Comparator<Contato>{
    @Override
    public int compare(Contato contato1, Contato contato2) {
        int value = contato1.getNome().compareTo(contato2.getNome());
        if(value > 0){
            return 1;
        }
        else if(value < 0){
            return -1;
        }
        else{
            return 0;
        }
    }
}
