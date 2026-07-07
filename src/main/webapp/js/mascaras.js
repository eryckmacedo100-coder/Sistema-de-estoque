// CPF: 000.000.000-00
document.getElementById("cpf").addEventListener("input", function () {
    let v = this.value.replace(/\D/g, "").slice(0, 11);
    if (v.length > 9)      v = v.replace(/(\d{3})(\d{3})(\d{3})(\d{0,2})/, "$1.$2.$3-$4");
    else if (v.length > 6) v = v.replace(/(\d{3})(\d{3})(\d{0,3})/, "$1.$2.$3");
    else if (v.length > 3) v = v.replace(/(\d{3})(\d{0,3})/, "$1.$2");
    this.value = v;
});

// Telefone: (00) 0 0000-0000
document.getElementById("telefone").addEventListener("input", function () {
    let v = this.value.replace(/\D/g, "").slice(0, 11);
    if (v.length > 7)      v = v.replace(/(\d{2})(\d{1})(\d{4})(\d{0,4})/, "($1) $2 $3-$4");
    else if (v.length > 3) v = v.replace(/(\d{2})(\d{0,5})/, "($1) $2");
    else                   v = v.replace(/(\d{0,2})/, "($1");
    this.value = v;
});

// CEP: 00000-000
document.getElementById("cep").addEventListener("input", function () {
    let v = this.value.replace(/\D/g, "").slice(0, 8);
    if (v.length > 5) v = v.replace(/(\d{5})(\d{0,3})/, "$1-$2");
    this.value = v;
});

// Matrícula: só números
document.getElementById("matricula").addEventListener("input", function () {
    this.value = this.value.replace(/\D/g, "");
});
