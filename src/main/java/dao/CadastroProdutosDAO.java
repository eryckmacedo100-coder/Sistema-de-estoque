package dao;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CadastroProdutoModel;

public class CadastroProdutosDAO {

    public boolean salvar(CadastroProdutoModel p) {
        String sql = "INSERT INTO produtos(codigo_barras,nome_produto,fabricante,marca,data_fabricacao,data_vencimento,quantidade,valor,total,status,prateleira,qtd_minima) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1,p.getCodigoBarras()); s.setString(2,p.getNomeProduto()); s.setString(3,p.getFabricante());
            s.setString(4,p.getMarca()); s.setDate(5,Date.valueOf(p.getDataFabricacao())); s.setDate(6,Date.valueOf(p.getDataVencimento()));
            s.setLong(7,p.getQuantidade()); s.setString(8,p.getValor()); s.setString(9,p.getTotal());
            s.setString(10,p.getStatus()); s.setString(11,p.getPrateleira()); s.setLong(12,p.getQtdMinima());
            s.executeUpdate(); return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    public List<CadastroProdutoModel> listarComFiltro(String nome, String tipo, String data, String prateleira) {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM produtos WHERE 1=1");
        if (nome!=null&&!nome.isEmpty())       sql.append(" AND LOWER(nome_produto) LIKE ?");
        if (tipo!=null&&!tipo.isEmpty())       sql.append(" AND status = ?");
        if (data!=null&&!data.isEmpty())       sql.append(" AND data_fabricacao = ?");
        if (prateleira!=null&&!prateleira.isEmpty()) sql.append(" AND LOWER(prateleira) LIKE ?");
        sql.append(" ORDER BY id DESC");
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(sql.toString())) {
            int i=1;
            if (nome!=null&&!nome.isEmpty())       s.setString(i++,"%"+nome.toLowerCase()+"%");
            if (tipo!=null&&!tipo.isEmpty())       s.setString(i++,tipo);
            if (data!=null&&!data.isEmpty())       s.setString(i++,data);
            if (prateleira!=null&&!prateleira.isEmpty()) s.setString(i++,"%"+prateleira.toLowerCase()+"%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<CadastroProdutoModel> listarParaReposicao() {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE qtd_minima > 0 AND quantidade <= qtd_minima ORDER BY nome_produto";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<CadastroProdutoModel> listarVencendoEm30Dias() {
        List<CadastroProdutoModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE data_vencimento IS NOT NULL AND data_vencimento BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 30 DAY) ORDER BY data_vencimento ASC";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            ResultSet rs = s.executeQuery();
            while (rs.next()) { lista.add(mapear(rs)); }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    private CadastroProdutoModel mapear(ResultSet rs) throws SQLException {
        CadastroProdutoModel p = new CadastroProdutoModel();
        p.setCodigoBarras(rs.getString("codigo_barras"));
        p.setNomeProduto(rs.getString("nome_produto"));
        p.setFabricante(rs.getString("fabricante"));
        p.setMarca(rs.getString("marca"));
        p.setDataVencimento(rs.getString("data_vencimento"));
        p.setDataFabricacao(rs.getString("data_fabricacao"));
        p.setQuantidade(rs.getLong("quantidade"));
        p.setValor(rs.getString("valor"));
        p.setTotal(rs.getString("total"));
        p.setStatus(rs.getString("status"));
        p.setPrateleira(rs.getString("prateleira"));
        p.setQtdMinima(rs.getLong("qtd_minima"));
        return p;
    }
}
