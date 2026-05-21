// Carrega os cards de resumo (Entrada / Saída / Total)
async function carregarResumo() {
    try {
        var response = await fetch("/api/resumo");
        var dados = await response.json();

        document.getElementById("cardEntrada").textContent = dados.entrada || 0;
        document.getElementById("cardSaida").textContent = dados.saida || 0;
        document.getElementById("cardTotal").textContent = dados.total || 0;
    } catch (erro) {
        console.error("Erro ao carregar resumo.", erro);
    }
}

// Carrega a tabela de produtos
async function carregarEstoque() {
    try {
        var response = await fetch("/api/estoque");
        var dados = await response.json();

        var tabela = document.getElementById("corpoTabela");
        tabela.innerHTML = "";

        if (dados.length === 0) {
            tabela.innerHTML = "<tr><td colspan='7' style='text-align:center;padding:20px;color:#888'>Nenhum produto cadastrado ainda.</td></tr>";
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

    } catch (erro) {
        console.error("Erro ao carregar produtos.", erro);
    }
}

// Paginação simples
var itensPorPagina = 10;
var paginaAtual = 1;
var todosItens = [];

async function carregarComPaginacao() {
    try {
        var response = await fetch("/api/estoque");
        todosItens = await response.json();
        renderizarPagina(paginaAtual);
        atualizarSelectPagina();
    } catch (erro) {
        console.error("Erro ao carregar.", erro);
    }
}

function renderizarPagina(pagina) {
    var inicio = (pagina - 1) * itensPorPagina;
    var fim = inicio + itensPorPagina;
    var itensPagina = todosItens.slice(inicio, fim);

    var tabela = document.getElementById("corpoTabela");
    tabela.innerHTML = "";

    if (itensPagina.length === 0) {
        tabela.innerHTML = "<tr><td colspan='7' style='text-align:center;padding:20px;color:#888'>Nenhum produto encontrado.</td></tr>";
        return;
    }

    itensPagina.forEach(function(item) {
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

function atualizarSelectPagina() {
    var totalPaginas = Math.ceil(todosItens.length / itensPorPagina) || 1;
    var select = document.getElementById("pagina");
    select.innerHTML = "";
    for (var i = 1; i <= totalPaginas; i++) {
        var opt = document.createElement("option");
        opt.value = i;
        opt.textContent = i;
        select.appendChild(opt);
    }
}

document.getElementById("btnProximo").addEventListener("click", function() {
    var totalPaginas = Math.ceil(todosItens.length / itensPorPagina) || 1;
    if (paginaAtual < totalPaginas) {
        paginaAtual++;
        document.getElementById("pagina").value = paginaAtual;
        renderizarPagina(paginaAtual);
    }
});

document.getElementById("btnVoltar").addEventListener("click", function() {
    if (paginaAtual > 1) {
        paginaAtual--;
        document.getElementById("pagina").value = paginaAtual;
        renderizarPagina(paginaAtual);
    }
});

document.getElementById("pagina").addEventListener("change", function() {
    paginaAtual = parseInt(this.value);
    renderizarPagina(paginaAtual);
});

// Inicia tudo ao carregar a página
window.onload = function() {
    carregarResumo();
    carregarComPaginacao();
};
