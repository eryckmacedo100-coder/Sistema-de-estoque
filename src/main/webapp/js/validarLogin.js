// Verifica se usuário está logado e esconde menu de cadastro se não for admin
async function validarLogin() {
    try {
        var res = await fetch("/api/perfil");
        var dado = await res.json();

        if (!dado.perfil || dado.perfil.toLowerCase() !== "admin") {
            var btnMenu = document.querySelector(".btn-menu");
            if (btnMenu) {
                btnMenu.style.display = "none";
            }
        }
    } catch (e) {
        console.error("Erro ao verificar o perfil.", e);
    }
}

validarLogin();
