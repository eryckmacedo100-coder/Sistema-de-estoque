function toast(msg, erro = false) {
    const t = document.getElementById("toast");
    t.textContent = msg;
    t.className = "visivel" + (erro ? " erro" : "");
    setTimeout(() => { t.className = ""; }, 3000);
}
function abrirModal(id)  { document.getElementById(id).classList.add("aberto"); }
function fecharModal(id) { document.getElementById(id).classList.remove("aberto"); }
document.querySelectorAll(".modal-overlay").forEach(o => {
    o.addEventListener("click", e => { if (e.target === o) o.classList.remove("aberto"); });
});

async function carregarProdutos(nome = "", status = "") {
    try {
        const url  = `/estoque?nome=${encodeURIComponent(nome)}&tipo=${encodeURIComponent(status)}&data=`;
        const res  = await fetch(url);
        const data = await res.json();
        renderizarTabela(data);
    } catch (e) { toast("Erro ao carregar produtos.", true); }
}

function renderizarTabela(lista) {
    const corpo = document.getElementById("corpoGerenciamento");
    corpo.innerHTML = "";
    if (lista.length === 0) {
        corpo.innerHTML = `<tr><td colspan="10"><div class="empty-state"><p>Nenhum produto encontrado.</p></div></td></tr>`;
        return;
    }
    lista.forEach(p => {
        const qtd    = p.quantidade ?? 0;
        const qtdMin = p.qtdMinima  ?? 0;
        const valor  = parseFloat(p.valor) || 0;
        const prat   = p.prateleira || "—";
        let badge = "";
        if (qtdMin > 0) {
            if (qtd === 0)          badge = `<span class="badge-critico">🔴 Sem estoque</span>`;
            else if (qtd <= qtdMin) badge = `<span class="badge-alerta">🟡 Repor agora</span>`;
            else                    badge = `<span class="badge-ok">🟢 OK</span>`;
        } else badge = `<span style="color:#aaa">—</span>`;
        corpo.innerHTML += `
            <tr>
                <td>${p.codigoBarras??'—'}</td>
                <td>${p.nomeProduto??'—'}</td>
                <td>${p.fabricante??'—'}</td>
                <td>${p.marca??'—'}</td>
                <td>${prat}</td>
                <td><strong>${qtd}</strong></td>
                <td>R$ ${valor.toFixed(2)}</td>
                <td>${p.status??'—'}</td>
                <td>${badge}</td>
                <td>
                    <button class="btn-acao btn-entrada" onclick="abrirMovimentacao('${p.codigoBarras}','${p.nomeProduto}',${qtd},'entrada')">⬆ Entrada</button>
                    <button class="btn-acao btn-saida"   onclick="abrirMovimentacao('${p.codigoBarras}','${p.nomeProduto}',${qtd},'saida')">⬇ Saída</button>
                    <button class="btn-acao btn-editar"  onclick="abrirEditar(${JSON.stringify(p).replace(/"/g,'&quot;')})">✏️ Editar</button>
                    <button class="btn-acao btn-excluir" onclick="abrirExcluir('${p.codigoBarras}','${p.nomeProduto}')">🗑 Excluir</button>
                </td>
            </tr>`;
    });
}

document.getElementById("btnBuscar").addEventListener("click", () => {
    carregarProdutos(document.getElementById("buscarNome").value, document.getElementById("buscarStatus").value);
});
document.getElementById("buscarNome").addEventListener("keydown", e => { if (e.key === "Enter") document.getElementById("btnBuscar").click(); });

function abrirEditar(p) {
    document.getElementById("editCodigo").value    = p.codigoBarras ?? "";
    document.getElementById("editCodigoExib").value = p.codigoBarras ?? "";
    document.getElementById("editNome").value       = p.nomeProduto  ?? "";
    document.getElementById("editFabricante").value = p.fabricante   ?? "";
    document.getElementById("editMarca").value      = p.marca        ?? "";
    document.getElementById("editValor").value      = p.valor        ?? "";
    document.getElementById("editPrateleira").value = p.prateleira   ?? "";
    document.getElementById("editQtdMinima").value  = p.qtdMinima    ?? 0;
    abrirModal("modalEditar");
}
document.getElementById("fecharEditar").addEventListener("click",   () => fecharModal("modalEditar"));
document.getElementById("cancelarEditar").addEventListener("click", () => fecharModal("modalEditar"));
document.getElementById("formEditar").addEventListener("submit", async e => {
    e.preventDefault();
    const body = new URLSearchParams({
        codigoBarras: document.getElementById("editCodigo").value,
        nomeProduto:  document.getElementById("editNome").value,
        fabricante:   document.getElementById("editFabricante").value,
        marca:        document.getElementById("editMarca").value,
        valor:        document.getElementById("editValor").value,
        prateleira:   document.getElementById("editPrateleira").value,
        qtdMinima:    document.getElementById("editQtdMinima").value,
    });
    try {
        const res = await fetch("/editarProduto", { method: "POST", body });
        if (res.ok) { toast("Produto atualizado! ✅"); fecharModal("modalEditar"); carregarProdutos(); }
        else toast("Erro ao salvar.", true);
    } catch { toast("Erro de conexão.", true); }
});

function abrirMovimentacao(codigo, nome, qtdAtual, tipo) {
    document.getElementById("movCodigo").value   = codigo;
    document.getElementById("movTipo").value      = tipo;
    document.getElementById("movNomeProduto").textContent = nome;
    document.getElementById("movQtdAtual").value = qtdAtual;
    document.getElementById("movQtd").value       = "";
    document.getElementById("tituloMovimentacao").textContent = tipo === "entrada" ? "⬆️ Entrada de Estoque" : "⬇️ Saída de Estoque";
    document.getElementById("btnConfirmarMov").style.backgroundColor = tipo === "entrada" ? "var(--success)" : "var(--warning)";
    abrirModal("modalMovimentacao");
}
document.getElementById("fecharMovimentacao").addEventListener("click",   () => fecharModal("modalMovimentacao"));
document.getElementById("cancelarMovimentacao").addEventListener("click", () => fecharModal("modalMovimentacao"));
document.getElementById("formMovimentacao").addEventListener("submit", async e => {
    e.preventDefault();
    const body = new URLSearchParams({ codigoBarras: document.getElementById("movCodigo").value, tipo: document.getElementById("movTipo").value, quantidade: document.getElementById("movQtd").value });
    try {
        const res = await fetch("/movimentarProduto", { method: "POST", body });
        if (res.ok) { toast("Movimentação registrada! ✅"); fecharModal("modalMovimentacao"); carregarProdutos(); }
        else { const msg = await res.text(); toast(msg || "Erro.", true); }
    } catch { toast("Erro de conexão.", true); }
});

function abrirExcluir(codigo, nome) {
    document.getElementById("codigoExcluir").value = codigo;
    document.getElementById("nomeExcluir").textContent = nome;
    abrirModal("modalExcluir");
}
document.getElementById("cancelarExcluir").addEventListener("click", () => fecharModal("modalExcluir"));
document.getElementById("confirmarExcluir").addEventListener("click", async () => {
    const body = new URLSearchParams({ codigoBarras: document.getElementById("codigoExcluir").value });
    try {
        const res = await fetch("/excluirProduto", { method: "POST", body });
        if (res.ok) { toast("Produto excluído. 🗑"); fecharModal("modalExcluir"); carregarProdutos(); }
        else toast("Erro ao excluir.", true);
    } catch { toast("Erro de conexão.", true); }
});

carregarProdutos();
