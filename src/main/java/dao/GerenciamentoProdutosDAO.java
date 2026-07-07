package dao;
import connection.ConnectionFactory;
import java.sql.*;
public class GerenciamentoProdutosDAO {
    public boolean editar(String cod, String nome, String fab, String marca, String valor, String prat, long qtdMin) {
        String sql = "UPDATE produtos SET nome_produto=?,fabricante=?,marca=?,valor=?,prateleira=?,qtd_minima=? WHERE codigo_barras=?";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(sql)) {
            s.setString(1,nome); s.setString(2,fab); s.setString(3,marca); s.setString(4,valor);
            s.setString(5,prat); s.setLong(6,qtdMin); s.setString(7,cod);
            return s.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    public boolean excluir(String cod) {
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement("DELETE FROM produtos WHERE codigo_barras=?")) {
            s.setString(1,cod); return s.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
    public long getQuantidadeAtual(String cod) {
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement("SELECT SUM(CASE WHEN status='entrada' THEN quantidade ELSE -quantidade END) AS saldo FROM produtos WHERE codigo_barras=?")) {
            s.setString(1,cod); ResultSet rs = s.executeQuery(); if (rs.next()) return rs.getLong("saldo");
        } catch (Exception e) { e.printStackTrace(); } return 0;
    }
    public boolean movimentar(String cod, String tipo, long qtd) {
        String q = "SELECT nome_produto,fabricante,marca,valor,prateleira,qtd_minima FROM produtos WHERE codigo_barras=? ORDER BY id DESC LIMIT 1";
        try (Connection c = ConnectionFactory.getConnection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1,cod); ResultSet rs = s.executeQuery(); if (!rs.next()) return false;
            String nome=rs.getString("nome_produto"),fab=rs.getString("fabricante"),marca=rs.getString("marca"),valor=rs.getString("valor"),prat=rs.getString("prateleira");
            long qtdMin=rs.getLong("qtd_minima");
            double vlr = Double.parseDouble(valor!=null?valor.replace(",","."):  "0");
            String total = String.format("%.2f", vlr * qtd);
            String ins = "INSERT INTO produtos(codigo_barras,nome_produto,fabricante,marca,data_fabricacao,data_vencimento,quantidade,valor,total,status,prateleira,qtd_minima) VALUES(?,?,?,?,CURDATE(),CURDATE(),?,?,?,?,?,?)";
            PreparedStatement si = c.prepareStatement(ins);
            si.setString(1,cod); si.setString(2,nome); si.setString(3,fab); si.setString(4,marca);
            si.setLong(5,qtd); si.setString(6,valor); si.setString(7,total); si.setString(8,tipo); si.setString(9,prat); si.setLong(10,qtdMin);
            si.executeUpdate(); return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}
