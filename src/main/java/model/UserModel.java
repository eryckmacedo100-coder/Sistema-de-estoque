/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * @author 232.004390
 */

public class UserModel {
    
    private int id;
    private String username;
    private String password;
    private String funcao;
    
    public UserModel () {
        
    }
    
    public int getId() {
        return id;
    }
    
    public void setId (int id) {
        this.id = id;
    }
    
    public String getUsername () {
        return this.username;
    }
    
    public void setUsername (String username) {
        this.username = username;
    }
    
    public String getPassword () {
        return this.password;
    }
    
    public void setPassword (String password) {
        this.password = password;
    }
    
    public String getFuncao () {
        return this.funcao;
    }
    
    public void setFuncao (String funcao) {
        this.funcao = funcao;
    }
}
