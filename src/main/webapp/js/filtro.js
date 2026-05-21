// Busca ao vivo enquanto digita (sem precisar clicar em Pesquisar)
document.getElementById("pesquisarNome").addEventListener("input", function() {
    filtroEstoque();
});

// Também filtra ao mudar tipo ou data
document.getElementById("tipoMovimentacao").addEventListener("change", filtroEstoque);
document.getElementById("filtroData").addEventListener("change", filtroEstoque);

// Botão pesquisar continua funcionando
document.getElementById("btnPesquisar").addEventListener("click", filtroEstoque);

async function filtroEstoque() {
    var nome = document.getElementById("pesquisarNome").value;
    var tipo = document.getElementById("tipoMovimentacao").value;
    var data = document.getElementById("filtroData").value;

    var url = "/api/estoque?nome=" + encodeURIComponent(nome) +
              "&tipo=" + encodeURIComponent(tipo) +
              "&data=" + encodeURIComponent(data);

    try {
        var response = await fetch(url);
        var dados = await response.json();
        renderizarTabela(dados);
    } catch (erro) {
        console.error("Erro ao filtrar", erro);
    }
}

function renderizarTabela(dados) {
    var tabela = document.getElementById("corpoTabela");
    tabela.innerHTML = "";

    if (dados.length === 0) {
        tabela.innerHTML = "<tr><td colspan='7' style='text-align:center;padding:20px;color:#888'>Nenhum produto encontrado.</td></tr>";
        return;
    }

    dados.forEach(function(item) {
        var linha = "<tr>" +
            "<td>" + (item.codigoBarras || "") + "</td>" +
            "<td>" + (item.nomeProduto || "") + "</td>" +
            "<td>" + (item.fabricante || "") + "</td>" +
            "<td>" + (item.marca || "") + "</td>" +
            "<td>" + (item.quantidade || 0) + "</td>" +
            "<td>R$ " + parseFloat(item.valor || 0).toFixed(2) + "</td>" +
            "<td>R$ " + parseFloat(item.total || 0).toFixed(2) + "</td>" +
            "</tr>";
        tabela.innerHTML += linha;
    });
}
