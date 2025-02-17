package com.trabd.flp.controllers;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trabd.flp.FabricaDeConexao;
import com.trabd.flp.dto.HistoricoCompraDTO;
import com.trabd.flp.dto.ProdutoCarrinhoDTO;
import com.trabd.flp.models.Categoria;
import com.trabd.flp.models.Cliente;
import com.trabd.flp.models.Produto;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cliente")
public class clienteController {

    @GetMapping("/cadastrarCliente")
    public String exibirFormulario() {
        return "formularioCliente";
    }

    @PostMapping("/cadastrarCliente")
    public String cadastrarCliente(@ModelAttribute Cliente cliente) throws Exception {
        String sql = "INSERT INTO cliente (cpf, senha, telefone, sexo, nome, dataNascimento, email, bairro, rua, cep, complemento, estado, cidade)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Connection conexao = FabricaDeConexao.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getSenha());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, String.valueOf(cliente.getSexo()));
            stmt.setString(5, cliente.getNome());
            stmt.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            stmt.setString(7, cliente.getEmail());
            stmt.setString(8, cliente.getBairro());
            stmt.setString(9, cliente.getRua());
            stmt.setString(10, cliente.getCep());
            stmt.setString(11, cliente.getComplemento());
            stmt.setString(12, cliente.getEstado());
            stmt.setString(13, cliente.getCidade());
            stmt.execute();
            stmt.close();
            conexao.close();
            return "redirect:/";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/fazerLogin")
    public String login() {
        return "login";
    }

    @PostMapping("/fazerLogin")
    public String fazerLogin(@ModelAttribute Cliente cliente, HttpSession session) {
        String sql = "SELECT * FROM cliente WHERE email = ? AND senha = ?";
        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getEmail());
            stmt.setString(2, cliente.getSenha());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente clienteLogado = new Cliente();
                clienteLogado.setNome(rs.getString("nome"));
                clienteLogado.setEmail(rs.getString("email"));
                clienteLogado.setCpf(rs.getString("cpf"));
                clienteLogado.setTelefone(rs.getString("telefone"));
                clienteLogado.setSexo(rs.getString("sexo").charAt(0));
                clienteLogado.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                clienteLogado.setBairro(rs.getString("bairro"));
                clienteLogado.setRua(rs.getString("rua"));
                clienteLogado.setCep(rs.getString("cep"));
                clienteLogado.setComplemento(rs.getString("complemento"));
                clienteLogado.setEstado(rs.getString("estado"));
                clienteLogado.setCidade(rs.getString("cidade"));
                clienteLogado.setSenha(rs.getString("senha"));

                session.setAttribute("clienteLogado", clienteLogado);
                return "redirect:/cliente/dashboard";
            } else {
                return "redirect:/login?erro=Credenciais inválidas";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "redirect:/erro";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");

        if (cliente == null) {
            return "redirect:/login";
        }

        List<Categoria> categorias = new ArrayList<>();
        String sqlCategorias = "SELECT nome FROM categoria WHERE nomeCategoriaPai IS NULL";

        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sqlCategorias)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getString("nome"),
                        null // Garante que a categoria pai seja nulo
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar categorias");
            return "erro";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("categorias", categorias);
        return "dashboard";
    }

    @GetMapping("/categoria/{nome}")
    public String categoria(@PathVariable("nome") String nome, @RequestParam String cpf, HttpSession session, Model model) {
        

        if (session.getAttribute("quantidades") == null) {
        session.setAttribute("quantidades", new HashMap<Long, Integer>());}
        
        session.setAttribute("categoriaAtualNome", nome);
        List<Produto> produtos = new ArrayList<>();
        String sqlProdutos = "SELECT * FROM produto WHERE nomeCategoria = ?";
        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sqlProdutos)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdproduto(rs.getLong("idproduto"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setMarca(rs.getString("marca"));
                produto.setNomeCategoria(rs.getString("nomeCategoria"));
                produtos.add(produto);
            }
        } catch (SQLException e){
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar produtos da categoria");
            return "erro";
        }

        List<Categoria> subcategorias = new ArrayList<>();
        String sqlSubcategorias = "SELECT nome, nomeCategoriaPai FROM categoria WHERE nomeCategoriaPai = ?";
        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sqlSubcategorias)) {
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria subcategoria = new Categoria(
                        rs.getString("nome"),
                        rs.getString("nomeCategoriaPai")
                );
                subcategorias.add(subcategoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar subcategorias");
            return "erro";
        }

        model.addAttribute("cpfCliente", cpf);
        model.addAttribute("categoriaAtual", nome);
        model.addAttribute("produtos", produtos);
        model.addAttribute("subcategorias", subcategorias);
        return "categoria";
    }

    @GetMapping("/historico_compras")
    public String historicoCompras(@RequestParam String cpf, Model model) {
        List<HistoricoCompraDTO> historico = new ArrayList<>();
        String sql = "SELECT p.nome, p.preco, p.descricao, p.marca, p.nomeCategoria, " +
                     "c.dia, cp.quantidade, c.total, c.statusAprovado " +
                     "FROM produto p " +
                     "JOIN compraProduto cp ON p.idproduto = cp.idProduto " +
                     "JOIN compra c ON cp.numeroCompra = c.numero " +
                     "WHERE c.cpfCliente = ? " +
                     "ORDER BY c.dia DESC";

        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HistoricoCompraDTO dto = new HistoricoCompraDTO(
                        rs.getDate("dia").toLocalDate(),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade"),
                        rs.getDouble("total"),
                        rs.getBoolean("statusAprovado"),
                        rs.getString("descricao"),
                        rs.getString("marca"),
                        rs.getString("nomeCategoria")
                );
                historico.add(dto);
            }

            model.addAttribute("historico", historico);
            return "historicoCompras";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar histórico de compras");
            return "erro";
        }
    }

    @GetMapping("/carrinho")
    public String carrinho(@RequestParam String cpf, Model model) {
        List<ProdutoCarrinhoDTO> produtosCarrinho = new ArrayList<>();
        String sql = "SELECT p.idproduto, p.nome, p.preco, p.descricao, p.marca, p.nomeCategoria, cp.quantidade " +
                     "FROM produto p " +
                     "JOIN carrinhoProduto cp ON p.idproduto = cp.idProduto " +
                     "WHERE cp.cpfCliente = ?";

        try (Connection conexao = FabricaDeConexao.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutoCarrinhoDTO produto = new ProdutoCarrinhoDTO(
                        rs.getLong("idproduto"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("descricao"),
                        rs.getString("marca"),
                        rs.getString("nomeCategoria"),
                        rs.getInt("quantidade")
                );
                produtosCarrinho.add(produto);
            }

            model.addAttribute("produtosCarrinho", produtosCarrinho);
            return "carrinho";
        } catch (SQLException e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar carrinho");
            return "erro";
        }
    }
}
