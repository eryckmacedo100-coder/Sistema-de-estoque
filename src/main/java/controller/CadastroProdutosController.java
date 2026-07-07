package controller;
import dao.CadastroProdutosDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import jakarta.servlet.ServletException;
import model.CadastroProdutoModel;
@WebServlet("/cadastroProdutos")
public class CadastroProdutosController extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CadastroProdutoModel p = new CadastroProdutoModel();
        p.setCodigoBarras(req.getParameter("codigoBarras"));
        p.setNomeProduto(req.getParameter("nomeProduto"));
        p.setFabricante(req.getParameter("fabricante"));
        p.setMarca(req.getParameter("marca"));
        p.setDataFabricacao(req.getParameter("dataFabricacao"));
        p.setDataVencimento(req.getParameter("dataVencimento"));
        p.setQuantidade(Long.parseLong(req.getParameter("quantidade")));
        p.setValor(req.getParameter("valor"));
        p.setTotal(req.getParameter("total"));
        p.setStatus(req.getParameter("status"));
        p.setPrateleira(req.getParameter("prateleira"));
        String qtdMin = req.getParameter("qtdMinima");
        p.setQtdMinima(qtdMin!=null&&!qtdMin.isEmpty() ? Long.parseLong(qtdMin) : 0);
        CadastroProdutosDAO dao = new CadastroProdutosDAO();
        if (dao.salvar(p)) res.sendRedirect("pages/projeto.html");
        else res.sendRedirect("pages/cadastroProdutos.html");
    }
}
