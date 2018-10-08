package com.senac.renan.exerciciomvc.DAO;

import com.senac.renan.exerciciomvc.model.Produto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DAOProduto {

    private static Connection obterConexao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        return DriverManager.getConnection("jdbc:mysql://localhost:3306/produtobd", "root", "");
    }

    public long incluir(Produto produto) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO produto (NOME, DESCRICAO, PRECO_COMPRA,"
                + "PRECO_VENDA, QUANTIDADE, DISPONIVEL, DT_CADASTRO)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        long idProduto = 0;

        System.out.println(produto.getNome());
        try (Connection conn = obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, produto.getNome());
                stmt.setString(2, produto.getDescricao());
                stmt.setDouble(3, produto.getPrecoCompra());
                stmt.setDouble(4, produto.getPrecoVenda());
                stmt.setInt(5, produto.getQuantidade());
                stmt.setBoolean(6, produto.isDisponivel());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                stmt.setTimestamp(7, timestamp);
                stmt.executeUpdate();

                try (ResultSet chave = stmt.getGeneratedKeys()) {
                    if (chave.next()) {
                        idProduto = chave.getLong(1);
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
        return idProduto;
    }

}
