/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import connection.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.UserModel;
import util.SenhaUtil;

/**
 *
 * @author 232.004390
 */
public class UserDAO {
    
    public UserModel validarLogin (UserModel userModel) {
        String sql 
                = "SELECT * FROM users WHERE username= ?";
        try (var con = ConnectionFactory.getConnection()){
         
            PreparedStatement stmt = 
                    con.prepareStatement(sql);
            
            stmt.setString(1, userModel.getUsername());
            
           
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                System.out.println(" Usuario encontrado no banco");
                String hashBanco = rs.getString("psw");
                
                boolean senhaValida = SenhaUtil.verificarSenha(
                        userModel.getPassword(),
                        hashBanco
                );
                
                if (senhaValida) {
                    System.out.println("Senha correta");
                    UserModel user = new UserModel();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(hashBanco);
                    user.setFuncao(rs.getString("funcao"));
                    
                    return user;
                } else {
                    System.out.println("Senha digitada errada: " + userModel.getPassword() + " | Banco: " + hashBanco);
                }
            } else {
                System.out.println(" Usuario nao encontrado com o nome: " + userModel.getUsername());
            }
            
            return null;
                   
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
    }


}
