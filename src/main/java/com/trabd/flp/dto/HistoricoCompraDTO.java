package com.trabd.flp.dto;

import java.time.LocalDate;

public class HistoricoCompraDTO {
    private LocalDate dataCompra;
    private String nomeProduto;
    private double precoUnitario;
    private int quantidade;
    private double totalItem;
    private double totalCompra;
    private boolean statusAprovado;
    private String descricao;
    private String marca;
    private String categoria;

    public HistoricoCompraDTO(LocalDate dataCompra, String nomeProduto, double precoUnitario, 
                             int quantidade, double totalCompra, boolean statusAprovado, 
                             String descricao, String marca, String categoria) {
        this.dataCompra = dataCompra;
        this.nomeProduto = nomeProduto;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.totalItem = precoUnitario * quantidade;
        this.totalCompra = totalCompra;
        this.statusAprovado = statusAprovado;
        this.descricao = descricao;
        this.marca = marca;
        this.categoria = categoria;
    }

    // Getters
    public LocalDate getDataCompra() { return dataCompra; }
    public String getNomeProduto() { return nomeProduto; }
    public double getPrecoUnitario() { return precoUnitario; }
    public int getQuantidade() { return quantidade; }
    public double getTotalItem() { return totalItem; }
    public double getTotalCompra() { return totalCompra; }
    public boolean isStatusAprovado() { return statusAprovado; }
    public String getDescricao() { return descricao; }
    public String getMarca() { return marca; }
    public String getCategoria() { return categoria; }
}