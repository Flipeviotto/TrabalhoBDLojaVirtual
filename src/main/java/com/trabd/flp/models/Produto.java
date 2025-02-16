package com.trabd.flp.models;

public class Produto {
    private long idproduto;
    private String nome;
    private double preco;
    private String descricao;
    private int estoque;
    private String marca;
    private String nomeCategoria;

    // Construtor, getters e setters
    public Produto() {}

    // Getters e Setters
    public long getIdproduto() { return idproduto; }
    public void setIdproduto(long idproduto) { this.idproduto = idproduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getNomeCategoria() { return nomeCategoria; }
    public void setNomeCategoria(String nomeCategoria) { this.nomeCategoria = nomeCategoria; }
}
