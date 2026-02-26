/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor777.senai777;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author 232.004390
 */
@WebServlet("/login")
public class Login extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String usuario = request.getParameter("users");  // Aqui a gente define o parametro 'name' no html. Dessa forma o Java e ele "conversam" entre si para encontrar o usuário
        String senha = request.getParameter("passw");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if ("admin".equals(usuario) && "1234".equals(senha)) {
            response.sendRedirect("projeto.html");
           // out.println("<h2>Login realizado</h2>");
        } else {
            out.println("<h2>Usuário ou senha incorreto. </h2>");
        }
    }
}