async function carregarGrafico() {
    try {
        const res   = await fetch("/estoque?nome=&tipo=&data=&prateleira=");
        const dados = await res.json();

        const canvas = document.getElementById("graficoCanvas");
        if (!canvas) return;
        const ctx = canvas.getContext("2d");

        // Agrupa por mês
        const meses = {};
        dados.forEach(item => {
            const mes = item.dataFabricacao ? item.dataFabricacao.substring(0, 7) : "N/A";
            if (!meses[mes]) meses[mes] = { entrada: 0, saida: 0 };
            if (item.status === "entrada") meses[mes].entrada += item.quantidade ?? 0;
            else                           meses[mes].saida   += item.quantidade ?? 0;
        });

        const labels   = Object.keys(meses).sort().slice(-6);
        const entradas = labels.map(m => meses[m].entrada);
        const saidas   = labels.map(m => meses[m].saida);
        const maxVal   = Math.max(...entradas, ...saidas, 10);

        const W = canvas.offsetWidth || 800;
        const H = 220;
        canvas.width  = W;
        canvas.height = H;

        const pad   = { top: 20, bottom: 40, left: 50, right: 20 };
        const barW  = ((W - pad.left - pad.right) / labels.length) * 0.35;
        const gap   = ((W - pad.left - pad.right) / labels.length);

        ctx.clearRect(0, 0, W, H);

        // Linhas de grade
        ctx.strokeStyle = "#e5e7eb";
        ctx.lineWidth = 1;
        for (let i = 0; i <= 4; i++) {
            const y = pad.top + ((H - pad.top - pad.bottom) / 4) * i;
            ctx.beginPath(); ctx.moveTo(pad.left, y); ctx.lineTo(W - pad.right, y); ctx.stroke();
            ctx.fillStyle = "#9ca3af";
            ctx.font = "11px Arial";
            ctx.textAlign = "right";
            ctx.fillText(Math.round(maxVal - (maxVal / 4) * i), pad.left - 6, y + 4);
        }

        labels.forEach((mes, i) => {
            const x      = pad.left + i * gap + gap * 0.1;
            const hEnt   = ((entradas[i] / maxVal) * (H - pad.top - pad.bottom));
            const hSai   = ((saidas[i]   / maxVal) * (H - pad.top - pad.bottom));
            const baseY  = H - pad.bottom;

            // Barra entrada
            ctx.fillStyle = "#3282b8";
            ctx.beginPath();
            ctx.roundRect(x, baseY - hEnt, barW, hEnt, [4, 4, 0, 0]);
            ctx.fill();

            // Barra saída
            ctx.fillStyle = "#f39c12";
            ctx.beginPath();
            ctx.roundRect(x + barW + 4, baseY - hSai, barW, hSai, [4, 4, 0, 0]);
            ctx.fill();

            // Label mês
            ctx.fillStyle = "#6b7280";
            ctx.font = "11px Arial";
            ctx.textAlign = "center";
            ctx.fillText(mes, x + barW, H - pad.bottom + 16);
        });

        // Legenda
        ctx.fillStyle = "#3282b8"; ctx.fillRect(W - 130, 8, 14, 14);
        ctx.fillStyle = "#374151"; ctx.font = "12px Arial"; ctx.textAlign = "left";
        ctx.fillText("Entrada", W - 112, 20);
        ctx.fillStyle = "#f39c12"; ctx.fillRect(W - 60, 8, 14, 14);
        ctx.fillStyle = "#374151"; ctx.fillText("Saída", W - 42, 20);

    } catch (e) { console.error("Erro gráfico", e); }
}
carregarGrafico();
