/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.secundarios;

/**
 *
 * @author misuka
 */
public class Telefone {
    private int ddi = 0;
    private int ddd = 0;
    private int numero = 0;

    public int getDdi() {
        return ddi;
    }

    public void setDdi(int ddi) throws Exception{
        if(ddi <= 0) throw new Exception("O DDI não pode ser <= 0");
        this.ddi = ddi;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) throws Exception{
        if(ddd <= 0) throw new Exception("O DDD não pode ser <= 0");
        this.ddd = ddd;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws Exception{
        if(numero <= 0) throw new Exception("O Número não pode ser <= 0");
        this.numero = numero;
    }

    public Telefone(int ddi, int ddd, int numero) throws Exception{
        if(ddi <= 0) throw new Exception("O DDI não pode ser <= 0");
        this.ddi = ddi;
        if(ddd <= 0) throw new Exception("O DDD não pode ser <= 0");
        this.ddd = ddd;
        if(numero <= 0) throw new Exception("O Número não pode ser <= 0");
        this.numero = numero;
    }

    public Telefone() {
    }

    @Override
    public String toString() {
        return "+"+ddi+"("+ddd+")"+numero;
    }
    
}
