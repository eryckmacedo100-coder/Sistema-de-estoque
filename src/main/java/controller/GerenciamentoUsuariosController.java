package controller;

import com.google.gson.Gson;
import dao.GerenciamentoUsuariosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.CadastroUsuarioModel;

@WebServlet("/api/gerenciamento/usuarios")
public class GerenciamentoUsuariosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        GerenciamentoUsuariosDAO dao = new GerenciamentoUsuariosDAO();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (idParam != null && !idParam.isEmpty()) {
            CadastroUsuarioModel u = dao.buscarPorId(Integer.parseInt(idParam));
            response.getWriter().write(new Gson().toJson(u));
        } else {
            List<CadastroUsuarioModel> lista = dao.listarTodos();
            response.getWriter().write(new Gson().toJson(lista));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String metodo = request.getParameter("_method");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        GerenciamentoUsuariosDAO dao = new GerenciamentoUsuariosDAO();

        if ("DELETE".equals(metodo)) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean ok = dao.deletar(id);
            response.getWriter().write("{\"sucesso\":" + ok + "}");
            return;
        }

        // UPDATE
        CadastroUsuarioModel u = new CadastroUsuarioModel();
        u.setId(Integer.parseInt(request.getParameter("id")));
        u.setNome(request.getParameter("nome"));
        u.setSobrenome(request.getParameter("sobrenome"));
        u.setEmail(request.getParameter("email"));
        u.setTelefone(request.getParameter("telefone"));
        u.setFuncao(request.getParameter("funcao"));
        u.setMatricula(request.getParameter("matricula"));

        boolean ok = dao.atualizar(u);
        response.getWriter().write("{\"sucesso\":" + ok + "}");
    }
}
