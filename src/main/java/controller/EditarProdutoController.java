package controller;
import dao.GerenciamentoProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/editarProduto")
public class EditarProdutoController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        long qtd = 0; try { qtd = Long.parseLong(req.getParameter("qtdMinima")); } catch (Exception ignored) {}
        boolean ok = new GerenciamentoProdutosDAO().editar(
            req.getParameter("codigoBarras"), req.getParameter("nomeProduto"),
            req.getParameter("fabricante"), req.getParameter("marca"),
            req.getParameter("valor"), req.getParameter("prateleira"), qtd);
        res.setContentType("text/plain"); res.setCharacterEncoding("UTF-8");
        res.setStatus(ok ? 200 : 500);
        res.getWriter().write(ok ? "ok" : "Erro ao editar produto.");
    }
}
