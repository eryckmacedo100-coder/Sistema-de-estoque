package controller;

import com.google.gson.Gson;
import dao.GerenciamentoProdutosDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.CadastroProdutoModel;

@WebServlet("/api/gerenciamento/produtos")
public class GerenciamentoProdutosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String codigo = request.getParameter("codigo");
        GerenciamentoProdutosDAO dao = new GerenciamentoProdutosDAO();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (codigo != null && !codigo.isEmpty()) {
            CadastroProdutoModel p = dao.buscarPorCodigo(codigo);
            response.getWriter().write(new Gson().toJson(p));
        } else {
            List<CadastroProdutoModel> lista = dao.listarTodos();
            response.getWriter().write(new Gson().toJson(lista));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String metodo = request.getParameter("_method");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        GerenciamentoProdutosDAO dao = new GerenciamentoProdutosDAO();

        if ("DELETE".equals(metodo)) {
            String codigo = request.getParameter("codigoBarras");
            boolean ok = dao.deletar(codigo);
            response.getWriter().write("{\"sucesso\":" + ok + "}");
            return;
        }

        // UPDATE
        CadastroProdutoModel p = new CadastroProdutoModel();
        p.setCodigoBarras(request.getParameter("codigoBarras"));
        p.setNomeProduto(request.getParameter("nomeProduto"));
        p.setFabricante(request.getParameter("fabricante"));
        p.setMarca(request.getParameter("marca"));
        p.setDataFabricacao(request.getParameter("dataFabricacao"));
        p.setDataVencimento(request.getParameter("dataVencimento"));
        p.setQuantidade(Long.parseLong(request.getParameter("quantidade")));
        p.setValor(request.getParameter("valor"));
        p.setTotal(request.getParameter("total"));
        p.setStatus(request.getParameter("status"));

        boolean ok = dao.atualizar(p);
        response.getWriter().write("{\"sucesso\":" + ok + "}");
    }
}
