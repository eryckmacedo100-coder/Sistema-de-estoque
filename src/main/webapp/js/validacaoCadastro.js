document.getElementById("formCadastro").addEventListener("submit", function(e) {
    const senha    = document.getElementById("password").value;
    const confirma = document.getElementById("confirmarSenha").value;

    if (senha !== confirma) {
        e.preventDefault();
        alert("❌ As senhas não coincidem. Verifique e tente novamente.");
        document.getElementById("confirmarSenha").focus();
    }
});
