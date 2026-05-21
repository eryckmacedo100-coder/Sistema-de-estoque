// Logout - redireciona para a servlet de logout
var btnLogout = document.getElementById("btnLogout");
if (btnLogout) {
    btnLogout.addEventListener("click", function() {
        window.location.href = "/logout";
    });
}
