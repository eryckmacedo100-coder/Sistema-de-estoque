// Prefixo obrigatório
const PREFIXO = "7890";

// Gera código aleatório com prefixo 7890 + 9 dígitos = 13 dígitos
function gerarCodigo() {
    let nums = "";
    for (let i = 0; i < 9; i++) {
        nums += Math.floor(Math.random() * 10);
    }
    return PREFIXO + nums;
}

// Botão gerar automático
document.getElementById("btnGerar").addEventListener("click", function () {
    document.getElementById("codigoBarras").value = gerarCodigo();
    document.getElementById("msgErro").style.display = "none";
});

// Validação ao digitar
document.getElementById("codigoBarras").addEventListener("input", function () {
    // só números
    this.value = this.value.replace(/\D/g, "").slice(0, 13);
});

// Validação ao enviar o formulário
document.getElementById("formCadastro").addEventListener("submit", function (e) {
    const codigo = document.getElementById("codigoBarras").value;
    const msgErro = document.getElementById("msgErro");

    if (codigo.length !== 13) {
        e.preventDefault();
        msgErro.textContent = "❌ O código de barras deve ter exatamente 13 dígitos.";
        msgErro.style.display = "block";
        window.scrollTo(0, 0);
        return;
    }

    if (!codigo.startsWith(PREFIXO)) {
        e.preventDefault();
        msgErro.textContent = `❌ O código de barras deve começar com "${PREFIXO}". Use o botão "Gerar automático" ou corrija o código.`;
        msgErro.style.display = "block";
        window.scrollTo(0, 0);
        return;
    }

    msgErro.style.display = "none";
});
