async function carregarSaudacao() {
    try {
        const res  = await fetch("/api/perfil");
        const data = await res.json();
        const nome = data.nome && data.nome.trim() ? data.nome.split(" ")[0] : data.usuario;
        document.getElementById("nomeUsuario").textContent = nome || "—";
    } catch (e) { console.error(e); }
}
carregarSaudacao();
