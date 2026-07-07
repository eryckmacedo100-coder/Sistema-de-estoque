package controller;
import com.google.gson.Gson;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import model.CadastroProdutoModel;

@WebServlet("/estoque")
public class EstoqueController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String nome       = req.getParameter("nome");
        String tipo       = req.getParameter("tipo");
        String data       = req.getParameter("data");
        String prateleira = req.getParameter("prateleira");

        List<CadastroProdutoModel> lista = new CadastroProdutosDAO().listarComFiltro(nome, tipo, data, prateleira);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new Gson().toJson(lista));
    }
}
