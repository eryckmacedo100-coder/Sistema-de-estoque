package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CadastroUsuarioModel;

public class GerenciamentoUsuariosDAO {

    public List<CadastroUsuarioModel> listarTodos() {
        List<CadastroUsuarioModel> lista = new ArrayList<>();
        String sql = "SELECT id, username, nameFirst, sobrenome, email, telefone, funcao, matricula, cpf, sexo, dtaNascimento FROM users ORDER BY id DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CadastroUsuarioModel u = new CadastroUsuarioModel();
                u.setId(rs.getInt("id"));
                u.setNomeusuario(rs.getString("username"));
                u.setNome(rs.getString("nameFirst"));
                u.setSobrenome(rs.getString("sobrenome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setFuncao(rs.getString("funcao"));
                u.setMatricula(rs.getString("matricula"));
                u.setCpf(rs.getString("cpf"));
                u.setSexo(rs.getString("sexo"));
                u.setDtaNascimento(rs.getString("dtaNascimento"));
                lista.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public CadastroUsuarioModel buscarPorId(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                CadastroUsuarioModel u = new CadastroUsuarioModel();
                u.setId(rs.getInt("id"));
                u.setNomeusuario(rs.getString("username"));
                u.setNome(rs.getString("nameFirst"));
                u.setSobrenome(rs.getString("sobrenome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setFuncao(rs.getString("funcao"));
                u.setMatricula(rs.getString("matricula"));
                u.setCpf(rs.getString("cpf"));
                u.setSexo(rs.getString("sexo"));
                u.setDtaNascimento(rs.getString("dtaNascimento"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean atualizar(CadastroUsuarioModel u) {
        String sql = "UPDATE users SET nameFirst=?, sobrenome=?, email=?, telefone=?, funcao=?, matricula=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getSobrenome());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getTelefone());
            stmt.setString(5, u.getFuncao());
            stmt.setString(6, u.getMatricula());
            stmt.setInt(7, u.getId());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
