package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CadastroProdutoModel;

public class GerenciamentoProdutosDAO {

    public List<CadastroProdutoModel> listarTodos() {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos ORDER BY id DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CadastroProdutoModel p = new CadastroProdutoModel();
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setFabricante(rs.getString("fabricante"));
                p.setMarca(rs.getString("marca"));
                p.setDataFabricacao(rs.getString("data_fabricacao"));
                p.setDataVencimento(rs.getString("data_vencimento"));
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

    public CadastroProdutoModel buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM produtos WHERE codigo_barras = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CadastroProdutoModel p = new CadastroProdutoModel();
                p.setCodigoBarras(rs.getString("codigo_barras"));
                p.setNomeProduto(rs.getString("nome_produto"));
                p.setFabricante(rs.getString("fabricante"));
                p.setMarca(rs.getString("marca"));
                p.setDataFabricacao(rs.getString("data_fabricacao"));
                p.setDataVencimento(rs.getString("data_vencimento"));
                p.setQuantidade(rs.getLong("quantidade"));
                p.setValor(rs.getString("valor"));
                p.setTotal(rs.getString("total"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(CadastroProdutoModel p) {
        String sql = "UPDATE produtos SET nome_produto=?, fabricante=?, marca=?, " +
                     "data_fabricacao=?, data_vencimento=?, quantidade=?, valor=?, total=?, status=? " +
                     "WHERE codigo_barras=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNomeProduto());
            stmt.setString(2, p.getFabricante());
            stmt.setString(3, p.getMarca());
            stmt.setDate(4, java.sql.Date.valueOf(p.getDataFabricacao()));
            stmt.setDate(5, java.sql.Date.valueOf(p.getDataVencimento()));
            stmt.setLong(6, p.getQuantidade());
            stmt.setString(7, p.getValor());
            stmt.setString(8, p.getTotal());
            stmt.setString(9, p.getStatus());
            stmt.setString(10, p.getCodigoBarras());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(String codigoBarras) {
        String sql = "DELETE FROM produtos WHERE codigo_barras = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigoBarras);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
