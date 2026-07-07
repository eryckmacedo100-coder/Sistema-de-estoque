let debounceTimer;

async function filtroEstoque() {
    const nome       = document.getElementById("pesquisarNome").value;
    const tipo       = document.getElementById("tipoMovimentacao").value;
    const data       = document.getElementById("filtroData").value;
    const prateleira = document.getElementById("filtroPrateleira") ? document.getElementById("filtroPrateleira").value : "";

    const spinner   = document.getElementById("spinner");
    const container = document.getElementById("tabelaEstoqueContainer");
    const tabela    = document.getElementById("corpoTabela");

    spinner.style.display  = "block";
    container.style.display = "none";

    try {
        const url = `/estoque?nome=${encodeURIComponent(nome)}&tipo=${encodeURIComponent(tipo)}&data=${encodeURIComponent(data)}&prateleira=${encodeURIComponent(prateleira)}`;
        const dados = await fetch(url).then(r => r.json());

        tabela.innerHTML = "";
        if (dados.length === 0) {
            tabela.innerHTML = `<tr><td colspan="10" style="text-align:center;padding:36px;color:#aaa;">Nenhum produto encontrado.</td></tr>`;
        } else {
            dados.forEach(item => {
                const qtd    = item.quantidade ?? 0;
                const qtdMin = item.qtdMinima  ?? 0;
                const valor  = parseFloat(item.valor) || 0;
                const total  = parseFloat(item.total) || 0;
                const prat   = item.prateleira || "—";
                let badge = "";
                if (qtdMin > 0) {
                    if (qtd === 0)          badge = `<span class="badge-critico">🔴 Sem estoque</span>`;
                    else if (qtd <= qtdMin) badge = `<span class="badge-alerta">🟡 Repor agora</span>`;
                    else                    badge = `<span class="badge-ok">🟢 OK</span>`;
                } else badge = `<span style="color:#aaa">—</span>`;

                tabela.innerHTML += `
                    <tr>
                        <td>${item.codigoBarras??'—'}</td>
                        <td>${item.nomeProduto??'—'}</td>
                        <td>${item.fabricante??'—'}</td>
                        <td>${item.marca??'—'}</td>
                        <td>${prat}</td>
                        <td><strong>${qtd}</strong></td>
                        <td>R$ ${valor.toFixed(2)}</td>
                        <td>R$ ${total.toFixed(2)}</td>
                        <td>${item.status??'—'}</td>
                        <td>${badge}</td>
                    </tr>`;
            });

            // Atualiza cards
            let totalEntrada = 0, totalSaida = 0;
            dados.forEach(item => {
                if (item.status === "entrada") totalEntrada += item.quantidade ?? 0;
                if (item.status === "saida")   totalSaida   += item.quantidade ?? 0;
            });
            const cardEntrada = document.getElementById("cardEntrada");
            const cardSaida   = document.getElementById("cardSaida");
            const cardTotal   = document.getElementById("cardTotal");
            if (cardEntrada) cardEntrada.textContent = totalEntrada;
            if (cardSaida)   cardSaida.textContent   = totalSaida;
            if (cardTotal)   cardTotal.textContent   = totalEntrada - totalSaida;
        }
    } catch (e) { console.error(e); }

    spinner.style.display  = "none";
    container.style.display = "block";
}

document.getElementById("pesquisarNome").addEventListener("input", () => {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(filtroEstoque, 350);
});
document.getElementById("tipoMovimentacao").addEventListener("change", filtroEstoque);
document.getElementById("filtroData").addEventListener("change", filtroEstoque);
document.getElementById("btnPesquisar").addEventListener("click", filtroEstoque);

const filtroPrat = document.getElementById("filtroPrateleira");
if (filtroPrat) filtroPrat.addEventListener("input", () => {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(filtroEstoque, 350);
});

filtroEstoque();