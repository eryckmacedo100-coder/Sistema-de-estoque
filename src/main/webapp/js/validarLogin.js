async function validarLogin() {
    try {
        const res  = await fetch("/api/perfil");
        const dado = await res.json();
        if (!dado.perfil || dado.perfil.toLowerCase() !== "admin") {
            const btn = document.querySelector(".btn-menu");
            if (btn) btn.style.display = "none";
        }
    } catch (e) { console.error("Erro ao verificar perfil.", e); }
}
validarLogin();
