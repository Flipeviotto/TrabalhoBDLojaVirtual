package com.trabd.flp.models;

public class Categoria {
    private String nome;
    private String nomeCategoriaPai;

    public Categoria() {} 

    public Categoria(String nome, String nomeCategoriaPai) {
        this.nome = nome;
        this.nomeCategoriaPai = nomeCategoriaPai;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNomeCategoriaPai() { return nomeCategoriaPai; }
    public void setNomeCategoriaPai(String nomeCategoriaPai) { this.nomeCategoriaPai = nomeCategoriaPai; }
}