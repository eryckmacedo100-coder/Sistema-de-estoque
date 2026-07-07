async function carregarVencimento() {
    try {
        const res   = await fetch("/api/vencimento");
        const lista = await res.json();

        const cardEl  = document.getElementById("cardVencimento");
        const cardQtd = document.getElementById("cardQtdVencimento");
        cardQtd.textContent = lista.length;
        lista.length > 0 ? cardEl.classList.add("card-alerta") : cardEl.classList.remove("card-alerta");

        const painel = document.getElementById("painelVencimento");
        const corpo  = document.getElementById("corpoVencimento");
        corpo.innerHTML = "";

        if (lista.length === 0) { painel.style.display = "none"; return; }
        painel.style.display = "block";

        const hoje = new Date();
        lista.forEach(item => {
            const venc  = item.dataVencimento || "—";
            const prat  = item.prateleira || "—";
            let diasLabel = "—";
            if (venc !== "—") {
                const diff = Math.ceil((new Date(venc) - hoje) / (1000 * 60 * 60 * 24));
                if (diff < 0)      diasLabel = `<span class="badge-critico">🔴 Vencido há ${Math.abs(diff)} dias</span>`;
                else if (diff === 0) diasLabel = `<span class="badge-critico">🔴 Vence hoje!</span>`;
                else               diasLabel = `<span class="badge-venc">🟡 ${diff} dias restantes</span>`;
            }
            corpo.innerHTML += `<tr><td>${item.nomeProduto??'—'}</td><td>${prat}</td><td>${item.quantidade??0}</td><td>${venc}</td><td>${diasLabel}</td></tr>`;
        });
    } catch (e) { console.error("Erro vencimento", e); }
}
carregarVencimento();
