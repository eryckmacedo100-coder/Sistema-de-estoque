/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import model.CadastroUsuarioModel;

/**
 *
 * @author 232.004390
 */
public class CadastroUsersDAO {
    
  public boolean cadastrar(CadastroUsuarioModel user) {
    
    String sql = "INSERT INTO users " +
            "(username, psw, nameFirst, sobrenome, dtaNascimento, matricula, cpf, sexo, email, telefone, funcao, cep, endereco, bairro, cidade, estado, numero, complemento) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (var con = ConnectionFactory.getConnection()){
            
            PreparedStatement stmt = con.prepareStatement(sql);
        
        // A ORDEM AGORA BATE EXATAMENTE COM A LISTA LÁ EM CIMA:
        stmt.setString(1, user.getNomeUsuario()); // 1. username
        stmt.setString(2, user.getSenha());       // 2. psw
        stmt.setString(3, user.getNome());        // 3. nameFirst
        stmt.setString(4, user.getSobrenome());   // 4. sobrenome
        stmt.setString(5, user.getDtaNascimento());// 5. dtaNascimento
        stmt.setString(6, user.getMatricula());   // 6. matricula
        stmt.setString(7, user.getCpf());         // 7. cpf
        stmt.setString(8, user.getSexo());        // 8. sexo
        stmt.setString(9, user.getEmail());       // 9. email
        stmt.setString(10, user.getTelefone());   // 10. telefone
        stmt.setString(11, user.getFuncao());     // 11. funcao
        stmt.setLong(12, user.getCep());          // 12. cep
        stmt.setString(13, user.getEndereco());   // 13. endereco
        stmt.setString(14, user.getBairro());     // 14. bairro
        stmt.setString(15, user.getCidade());     // 15. cidade
        stmt.setString(16, user.getEstado());     // 16. estado
        stmt.setLong(17, user.getNumero());       // 17. numero
        stmt.setString(18, user.getComplemento());// 18. complemento
        
        stmt.executeUpdate();
        
        return true;

    } catch (Exception e) {
        
        System.out.println("ERRO AO SALVAR NO BANCO: " + e.getMessage()); 
        e.printStackTrace();
        return false;
        }
    }
}