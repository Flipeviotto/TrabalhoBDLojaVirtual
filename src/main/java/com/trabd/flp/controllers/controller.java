package com.trabd.flp.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class controller {
    
    @GetMapping("/historico_compras")
    public String historicoCompras(@RequestParam String cpf, Model model) {
        // Lógica para histórico de compras
        return "historico_compras";
    }

    @GetMapping("/carrinho")
    public String carrinho(@RequestParam String cpf, Model model) {
        // Lógica para carrinho
        return "carrinho";
    }

    @GetMapping("/categoria/{nome}")
    public String categoria(@PathVariable String nome, Model model) {
        List<String> categorias = Arrays.asList(
            "Laticínios",
            "Hortifrúti",
            "Doces",
            "Brinquedos", 
            "Carnes", 
            "Cereais", 
            "Bebidas", 
            "Eletrodomésticos", 
            "Utensílios"
        );
        model.addAttribute("categorias", categorias);
        return "categoria";
    }
}
