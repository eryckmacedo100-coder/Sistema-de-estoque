// Calcula total automaticamente (igual professor fez com CEP)
document.getElementById("valor").addEventListener("input", calcularTotal);
document.getElementById("quantidade").addEventListener("input", calcularTotal);

function calcularTotal() {
    var valor = parseFloat(document.getElementById("valor").value) || 0;
    var quantidade = parseInt(document.getElementById("quantidade").value) || 0;
    document.getElementById("total").value = (valor * quantidade).toFixed(2);
}

// Gera código de barras EAN-13 automaticamente
function gerarCodigoBarras() {
    var codigo = "";
    for (var i = 0; i < 12; i++) {
        codigo += Math.floor(Math.random() * 10);
    }

    // Calcula dígito verificador EAN-13
    var soma = 0;
    for (var i = 0; i < 12; i++) {
        var digito = parseInt(codigo[i]);
        soma += (i % 2 === 0) ? digito : digito * 3;
    }
    var verificador = (10 - (soma % 10)) % 10;
    codigo += verificador;

    document.getElementById("codigoBarras").value = codigo;
}
