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
    private String ddi = "";
    private String ddd = "";
    private String numero = "";

    public String getDdi() {
        return ddi;
    }

    public void setDdi(String ddi) {
        this.ddi = ddi;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Telefone(String ddi, String ddd, String numero) {
        this.ddi = ddi;
        this.ddd = ddd;
        this.numero = numero;
    }

    public Telefone() {
    }

    @Override
    public String toString() {
        return ddi+ddd+numero;
    }
}
