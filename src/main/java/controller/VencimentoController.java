package controller;
import com.google.gson.Gson;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import model.CadastroProdutoModel;

@WebServlet("/api/vencimento")
public class VencimentoController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        List<CadastroProdutoModel> lista = new CadastroProdutosDAO().listarVencendoEm30Dias();
        res.setContentType("application/json"); res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new Gson().toJson(lista));
    }
}
