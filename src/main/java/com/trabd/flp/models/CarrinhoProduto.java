package com.trabd.flp.models;

public class CarrinhoProduto {
    private String cpfCliente;
    private long idProduto;
    private int quantidade;

    public CarrinhoProduto(String cpfCliente, long idProduto, int quantidade) {
        this.cpfCliente = cpfCliente;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }

    public long getIdProduto() { return idProduto; }
    public void setIdProduto(long idProduto) { this.idProduto = idProduto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}