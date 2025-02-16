package com.trabd.flp.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Compra {
    private int numero;
    private LocalDate dia;
    private LocalTime hora;
    private double total;
    private boolean statusAprovado;
    private String cpfCliente;

    // Construtor, getters e setters
    public Compra(int numero, LocalDate dia, LocalTime hora, double total, boolean statusAprovado, String cpfCliente) {
        this.numero = numero;
        this.dia = dia;
        this.hora = hora;
        this.total = total;
        this.statusAprovado = statusAprovado;
        this.cpfCliente = cpfCliente;
    }

    // Getters e Setters
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public LocalDate getDia() { return dia; }
    public void setDia(LocalDate dia) { this.dia = dia; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public boolean isStatusAprovado() { return statusAprovado; }
    public void setStatusAprovado(boolean statusAprovado) { this.statusAprovado = statusAprovado; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
}