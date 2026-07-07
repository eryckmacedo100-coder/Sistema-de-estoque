package controller;

import dao.CadastroUsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.CadastroUsuarioModel;

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

        String cepStr = request.getParameter("cep").replaceAll("\\D", "");
        user.setCep(Long.parseLong(cepStr));

        user.setEndereco(request.getParameter("endereco"));
        user.setEstado(request.getParameter("estado"));
        user.setBairro(request.getParameter("bairro"));
        user.setCidade(request.getParameter("cidade"));

        String numStr = request.getParameter("numero").replaceAll("\\D", "");
        user.setNumero(numStr.isEmpty() ? 0 : Long.parseLong(numStr));

        user.setComplemento(request.getParameter("complemento"));
        user.setNomeusuario(request.getParameter("usuario"));
        user.setSenha(request.getParameter("password"));
        user.setFuncao(request.getParameter("funcao"));

        CadastroUsersDAO dao = new CadastroUsersDAO();

        if (dao.cadastrar(user)) {
            // Redireciona para login com usuário preenchido
            String usuario = request.getParameter("usuario");
            response.sendRedirect(request.getContextPath() + "/index.html?usuario=" + usuario);
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/cadastro.html");
        }
    }
}
