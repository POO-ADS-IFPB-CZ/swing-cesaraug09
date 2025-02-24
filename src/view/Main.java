package view;

import dao.ProdutoDao;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ProdutoDao produtoDao = new ProdutoDao();
        SwingUtilities.invokeLater(() -> configurarInterface(produtoDao));
    }

    private static void configurarInterface(ProdutoDao produtoDao) {
        JFrame janela = new JFrame("Gestão de Produtos");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(400, 250);
        janela.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrarProduto(produtoDao));

        JButton btnMostrar = new JButton("Exibir");
        btnMostrar.addActionListener(e -> exibirProdutos(produtoDao));

        JButton btnDeletar = new JButton("Remover");
        btnDeletar.addActionListener(e -> deletarProduto(produtoDao));

        JButton btnEncerrar = new JButton("Sair");
        btnEncerrar.addActionListener(e -> System.exit(0));

        janela.add(btnCadastrar);
        janela.add(btnMostrar);
        janela.add(btnDeletar);
        janela.add(btnEncerrar);

        janela.setLocationRelativeTo(null);
        janela.setVisible(true);
    }

    private static void cadastrarProduto(ProdutoDao produtoDao) {
        String idStr = JOptionPane.showInputDialog("Informe o código do produto:");
        if (idStr == null) return;

        try {
            int codigo = Integer.parseInt(idStr);
            String descricao = JOptionPane.showInputDialog("Descrição do produto:");
            if (descricao == null) return;

            String precoStr = JOptionPane.showInputDialog("Preço do produto:");
            if (precoStr == null) return;
            float preco = Float.parseFloat(precoStr);

            String validadeStr = JOptionPane.showInputDialog("Data de validade (AAAA-MM-DD):");
            if (validadeStr == null) return;
            LocalDate validade = LocalDate.parse(validadeStr);

            Produto produto = new Produto(codigo, descricao, preco, validade);
            boolean sucesso = produtoDao.adicionarProduto(produto);

            JOptionPane.showMessageDialog(null, sucesso ? "Produto registrado!" : "Erro ao registrar.");
        } catch (NumberFormatException | DateTimeParseException | IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao processar dados.");
        }
    }

    private static void exibirProdutos(ProdutoDao produtoDao) {
        try {
            Set<Produto> produtos = produtoDao.getProdutos();
            if (produtos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto cadastrado.");
            } else {
                StringBuilder lista = new StringBuilder("Produtos:\n");
                for (Produto p : produtos) {
                    lista.append(formatarProduto(p)).append("\n");
                }
                JOptionPane.showMessageDialog(null, lista.toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar produtos.");
        }
    }

    private static String formatarProduto(Produto produto) {
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Date dataValidade = java.sql.Date.valueOf(produto.getValidade());
        String dataFormatada = formatoData.format(dataValidade);

        return String.format("ID: %d - %s - R$ %.2f - Validade: %s",
                produto.getId(), produto.getDescricao(), produto.getPreco(), dataFormatada);
    }

    private static void deletarProduto(ProdutoDao produtoDao) {
        String idStr = JOptionPane.showInputDialog("Código do produto para remover:");
        if (idStr == null) return;

        try {
            int codigo = Integer.parseInt(idStr);
            Set<Produto> produtos = produtoDao.getProdutos();
            Produto produtoDeletar = produtos.stream()
                    .filter(p -> p.getId() == codigo)
                    .findFirst()
                    .orElse(null);

            if (produtoDeletar != null) {
                boolean sucesso = produtoDao.removerProduto(produtoDeletar);
                JOptionPane.showMessageDialog(null, sucesso ? "Produto removido!" : "Erro ao remover.");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (NumberFormatException | IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao acessar dados.");
        }
    }
}
