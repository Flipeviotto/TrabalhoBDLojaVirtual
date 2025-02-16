package com.trabd.flp.models;

public class CompraProduto {
    private long idProduto;
    private int numeroCompra;
    private double precoOriginal;
    private int quantidade;

    // Construtor, getters e setters
    public CompraProduto(long idProduto, int numeroCompra, double precoOriginal, int quantidade) {
        this.idProduto = idProduto;
        this.numeroCompra = numeroCompra;
        this.precoOriginal = precoOriginal;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public long getIdProduto() { return idProduto; }
    public void setIdProduto(long idProduto) { this.idProduto = idProduto; }

    public int getNumeroCompra() { return numeroCompra; }
    public void setNumeroCompra(int numeroCompra) { this.numeroCompra = numeroCompra; }

    public double getPrecoOriginal() { return precoOriginal; }
    public void setPrecoOriginal(double precoOriginal) { this.precoOriginal = precoOriginal; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
