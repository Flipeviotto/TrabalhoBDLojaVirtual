package com.trabd.flp.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trabd.flp.FabricaDeConexao;
import com.trabd.flp.models.Produto;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/compra")
public class CompraController {

    @PostMapping("/ajustar_quantidade")
    public String ajustarQuantidade(
        @RequestParam("idProduto") Long idProduto,
        @RequestParam("operacao") String operacao,
        @RequestParam("cpfCliente") String cpfCliente,
        HttpSession session) {
    
        Map<Long, Integer> quantidades = (Map<Long, Integer>) session.getAttribute("quantidades");
        if (quantidades == null) {
            quantidades = new HashMap<>();
            session.setAttribute("quantidades", quantidades);
        }

        int novaQuantidade = quantidades.getOrDefault(idProduto, 0);
        if ("incrementar".equals(operacao)) {
            novaQuantidade++;
        } else {
            novaQuantidade = Math.max(0, novaQuantidade - 1);
        }
        
        quantidades.put(idProduto, novaQuantidade);

        return "redirect:/cliente/categoria/" + session.getAttribute("categoriaAtualNome") + "?cpf=" + cpfCliente;
    }

    @PostMapping("/adicionar_ao_carrinho")
    public String adicionarAoCarrinho(
        @RequestParam("idProduto") Long idProduto,
        @RequestParam("cpfCliente") String cpfCliente,
        HttpSession session) {

        Map<Long, Integer> quantidades = (Map<Long, Integer>) session.getAttribute("quantidades");
        int quantidade = quantidades.getOrDefault(idProduto, 0);

        if (quantidade > 0) {
            
            try (Connection conexao = FabricaDeConexao.getConexao()) {
                String sqlEstoque = "UPDATE produto SET estoque = estoque - ? WHERE idproduto = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sqlEstoque)) {
                    stmt.setInt(1, quantidade);
                    stmt.setLong(2, idProduto);
                    stmt.executeUpdate();
                }

                String sqlCarrinho = "INSERT INTO carrinhoProduto (cpfCliente, idProduto, quantidade) "
                                    + "VALUES (?, ?, ?) "
                                    + "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";
                try (PreparedStatement stmt = conexao.prepareStatement(sqlCarrinho)) {
                    stmt.setString(1, cpfCliente);
                    stmt.setLong(2, idProduto);
                    stmt.setInt(3, quantidade);
                    stmt.executeUpdate();
                }
                quantidades.remove(idProduto);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/cliente/categoria/" + session.getAttribute("categoriaAtualNome") + "?cpf=" + cpfCliente;
    }
}