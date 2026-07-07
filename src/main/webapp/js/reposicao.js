async function carregarReposicao() {
    try {
        const response = await fetch("/api/reposicao");
        const lista    = await response.json();
        const cardQtd  = document.getElementById("cardQtdReposicao");
        const cardEl   = document.getElementById("cardReposicao");
        cardQtd.textContent = lista.length;
        lista.length > 0 ? cardEl.classList.add("card-alerta") : cardEl.classList.remove("card-alerta");
        const corpo  = document.getElementById("corpoReposicao");
        const painel = document.getElementById("painelReposicao");
        corpo.innerHTML = "";
        if (lista.length === 0) { painel.style.display = "none"; return; }
        painel.style.display = "block";
        lista.forEach(item => {
            const qtd  = item.quantidade ?? 0;
            const prat = item.prateleira || "—";
            const sit  = qtd === 0
                ? `<span class="badge-critico">🔴 Sem estoque — Emitir nota de compra</span>`
                : `<span class="badge-alerta">🟡 Abaixo do mínimo — Solicitar reposição</span>`;
            corpo.innerHTML += `<tr><td>${item.nomeProduto??'—'}</td><td>${prat}</td><td>${qtd}</td><td>${item.qtdMinima??0}</td><td>${sit}</td></tr>`;
        });
    } catch (erro) { console.error("Erro ao carregar reposição", erro); }
}
carregarReposicao();
