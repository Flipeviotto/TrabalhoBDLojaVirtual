package com.trabd.flp.dto;

public class ProdutoCarrinhoDTO {
    private long idproduto;
    private String nome;
    private double preco;
    private String descricao;
    private String marca;
    private String nomeCategoria;
    private int quantidade;

    // Construtor
    public ProdutoCarrinhoDTO(long idproduto, String nome, double preco, String descricao, 
                              String marca, String nomeCategoria, int quantidade) {
        this.idproduto = idproduto;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.marca = marca;
        this.nomeCategoria = nomeCategoria;
        this.quantidade = quantidade;
    }

    // Getters
    public long getIdproduto() { return idproduto; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    public String getMarca() { return marca; }
    public String getNomeCategoria() { return nomeCategoria; }
    public int getQuantidade() { return quantidade; }
}
