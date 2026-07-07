document.getElementById("valor").addEventListener("input", calcular);
document.getElementById("quantidade").addEventListener("input", calcular);
function calcular() {
    const valor = parseFloat(document.getElementById("valor").value) || 0;
    const qtd   = parseInt(document.getElementById("quantidade").value) || 0;
    document.getElementById("total").value = (valor * qtd).toFixed(2);
}
