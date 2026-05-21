package controller;

import dao.CadastroUsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.CadastroUsuarioModel;
import util.SenhaUtil;

/**
 *
 * @author 232.004390
 */
@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        CadastroUsuarioModel user = new CadastroUsuarioModel();
        
        user.setNome(request.getParameter("nameFirst"));
        user.setSobrenome(request.getParameter("sobrenome"));
        user.setMatricula(request.getParameter("matricula"));
        user.setCpf(request.getParameter("cpf"));
        user.setSexo(request.getParameter("sexo"));
        user.setDtaNascimento(request.getParameter("dtaNascimento"));
        user.setEmail(request.getParameter("email"));
        user.setTelefone(request.getParameter("telefone"));
        user.setCep(Long.parseLong(request.getParameter("cep")));
        user.setEndereco(request.getParameter("endereco"));
        user.setEstado(request.getParameter("estado"));
        user.setBairro(request.getParameter("bairro"));
        user.setCidade(request.getParameter("cidade"));
        user.setNumero(Long.parseLong(request.getParameter("numero")));
        user.setComplemento(request.getParameter("complemento")); 
        
        
        user.setNomeusuario(request.getParameter("usuario"));
        
        user.setSenha(request.getParameter("password"));
        
        user.setFuncao(request.getParameter("funcao"));
        
        CadastroUsersDAO dao = new CadastroUsersDAO();
        
        if (dao.cadastrar(user)) {
            response.sendRedirect("pages/projeto.html");
        } else {
            response.sendRedirect("pages/cadastro.html");
        }
    }
}