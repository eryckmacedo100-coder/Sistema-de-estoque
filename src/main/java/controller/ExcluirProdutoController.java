package controller;
import dao.GerenciamentoProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/excluirProduto")
public class ExcluirProdutoController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        boolean ok = new GerenciamentoProdutosDAO().excluir(req.getParameter("codigoBarras"));
        res.setContentType("text/plain"); res.setCharacterEncoding("UTF-8");
        res.setStatus(ok ? 200 : 500);
        res.getWriter().write(ok ? "ok" : "Erro ao excluir.");
    }
}
