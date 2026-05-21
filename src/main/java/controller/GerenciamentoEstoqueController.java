package controller;

import com.google.gson.Gson;
import dao.GerenciamentoEstoqueDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.CadastroProdutoModel;

@WebServlet("/api/gerenciamento/estoque")
public class GerenciamentoEstoqueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        GerenciamentoEstoqueDAO dao = new GerenciamentoEstoqueDAO();

        String tipo = request.getParameter("tipo");
        if ("baixo".equals(tipo)) {
            List<CadastroProdutoModel> lista = dao.listarEstoqueBaixo(5);
            response.getWriter().write(new Gson().toJson(lista));
        } else {
            List<CadastroProdutoModel> lista = dao.listarEstoque();
            response.getWriter().write(new Gson().toJson(lista));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String codigoBarras = request.getParameter("codigoBarras");
        String tipoMov = request.getParameter("tipoMovimentacao");
        String qtdParam = request.getParameter("quantidade");

        if (codigoBarras == null || tipoMov == null || qtdParam == null) {
            response.getWriter().write("{\"sucesso\":false,\"mensagem\":\"Parametros invalidos\"}");
            return;
        }

        long quantidade = Long.parseLong(qtdParam);
        GerenciamentoEstoqueDAO dao = new GerenciamentoEstoqueDAO();
        boolean ok = dao.registrarMovimentacao(codigoBarras, tipoMov, quantidade);

        if (ok) {
            response.getWriter().write("{\"sucesso\":true,\"mensagem\":\"Movimentacao registrada com sucesso\"}");
        } else {
            response.getWriter().write("{\"sucesso\":false,\"mensagem\":\"Erro ao registrar movimentacao. Verifique o codigo e a quantidade.\"}");
        }
    }
}
