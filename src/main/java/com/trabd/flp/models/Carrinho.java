package com.trabd.flp.models;

public class Carrinho {
    private double total;
    private String cpfCliente;

    // Construtor, getters e setters
    public Carrinho(double total, String cpfCliente) {
        this.total = total;
        this.cpfCliente = cpfCliente;
    }

    // Getters e Setters
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
}
