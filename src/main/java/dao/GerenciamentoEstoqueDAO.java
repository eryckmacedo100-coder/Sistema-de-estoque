package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CadastroProdutoModel;

public class GerenciamentoEstoqueDAO {

    public boolean registrarMovimentacao(String codigoBarras, String tipo, long quantidade) {
        // Busca produto atual
        String sqlSelect = "SELECT quantidade FROM produtos WHERE codigo_barras = ?";
        String sqlUpdate = "UPDATE produtos SET quantidade=?, status=? WHERE codigo_barras=?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
            stmtSelect.setString(1, codigoBarras);
            ResultSet rs = stmtSelect.executeQuery();

            if (!rs.next()) {
                return false;
            }

            long qtdAtual = rs.getLong("quantidade");
            long novaQtd;

            if ("entrada".equals(tipo)) {
                novaQtd = qtdAtual + quantidade;
            } else {
                novaQtd = qtdAtual - quantidade;
                if (novaQtd < 0) return false;
            }

            PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
            stmtUpdate.setLong(1, novaQtd);
            stmtUpdate.setString(2, tipo);
            stmtUpdate.setString(3, codigoBarras);
            stmtUpdate.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CadastroProdutoModel> listarEstoque() {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT codigo_barras, nome_produto, fabricante, marca, quantidade, valor, total, status FROM produtos ORDER BY nome_produto ASC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CadastroProdutoModel p = new CadastroProdutoModel();
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setFabricante(rs.getString("fabricante"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getLong("quantidade"));
                p.setValor(rs.getString("valor"));
                p.setTotal(rs.getString("total"));
                p.setStatus(rs.getString("status"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<CadastroProdutoModel> listarEstoqueBaixo(int limite) {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT codigo_barras, nome_produto, fabricante, marca, quantidade, valor, status FROM produtos WHERE quantidade <= ? ORDER BY quantidade ASC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CadastroProdutoModel p = new CadastroProdutoModel();
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setFabricante(rs.getString("fabricante"));
                p.setMarca(rs.getString("marca"));
                p.setQuantidade(rs.getLong("quantidade"));
                p.setValor(rs.getString("valor"));
                p.setStatus(rs.getString("status"));
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
