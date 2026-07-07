package controller;
import com.google.gson.Gson;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/api/reposicao")
public class ReposicaoController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json"); res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new Gson().toJson(new CadastroProdutosDAO().listarParaReposicao()));
    }
}
