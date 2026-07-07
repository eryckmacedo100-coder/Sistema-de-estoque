package controller;
import dao.GerenciamentoProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
@WebServlet("/movimentarProduto")
public class MovimentarProdutoController extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cod = req.getParameter("codigoBarras"), tipo = req.getParameter("tipo");
        long qtd = 0; try { qtd = Long.parseLong(req.getParameter("quantidade")); } catch (Exception ignored) {}
        res.setContentType("text/plain"); res.setCharacterEncoding("UTF-8");
        if (qtd <= 0) { res.setStatus(400); res.getWriter().write("Quantidade inválida."); return; }
        GerenciamentoProdutosDAO dao = new GerenciamentoProdutosDAO();
        if ("saida".equals(tipo)) {
            long saldo = dao.getQuantidadeAtual(cod);
            if (qtd > saldo) { res.setStatus(400); res.getWriter().write("Estoque insuficiente. Saldo: " + saldo); return; }
        }
        boolean ok = dao.movimentar(cod, tipo, qtd);
        res.setStatus(ok ? 200 : 500);
        res.getWriter().write(ok ? "ok" : "Erro na movimentação.");
    }
}
