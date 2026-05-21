// ===== DADOS EM MEMÓRIA =====
let todosProdutos = [];
let todosUsuarios = [];

// ===== ABAS =====
function trocarAba(nome, botao) {
    document.querySelectorAll(".painel").forEach(p => p.classList.remove("ativo"));
    document.querySelectorAll(".aba-btn").forEach(b => b.classList.remove("ativa"));

    document.getElementById("painel-" + nome).classList.add("ativo");
    botao.classList.add("ativa");

    if (nome === "produtos") carregarProdutos();
    if (nome === "usuarios") carregarUsuarios();
    if (nome === "estoque") carregarEstoqueGer();
    if (nome === "relatorio") carregarRelatorio();
}

// ===== TOAST =====
function mostrarToast(msg, tipo) {
    const t = document.getElementById("toast");
    t.textContent = msg;
    t.className = "toast " + tipo;
    t.style.display = "block";
    setTimeout(() => { t.style.display = "none"; }, 3000);
}

// ===== MODAL =====
function abrirModal(id) {
    document.getElementById(id).classList.add("aberto");
}

function fecharModal(id) {
    document.getElementById(id).classList.remove("aberto");
}

// Fechar modal ao clicar fora
document.querySelectorAll(".modal-overlay").forEach(overlay => {
    overlay.addEventListener("click", function(e) {
        if (e.target === this) this.classList.remove("aberto");
    });
});

// ===== PRODUTOS =====
async function carregarProdutos() {
    try {
        const resp = await fetch("/api/gerenciamento/produtos");
        todosProdutos = await resp.json();
        renderizarProdutos(todosProdutos);
    } catch (e) {
        console.error("Erro ao carregar produtos", e);
    }
}

function renderizarProdutos(lista) {
    const corpo = document.getElementById("corpoProdutos");
    document.getElementById("contagemProdutos").textContent = lista.length + " produto(s)";

    if (lista.length === 0) {
        corpo.innerHTML = "<tr><td colspan='8' class='empty-state'>Nenhum produto encontrado.</td></tr>";
        return;
    }

    corpo.innerHTML = lista.map(p => `
        <tr>
            <td>${p.codigoBarras || ""}</td>
            <td>${p.nomeProduto || ""}</td>
            <td>${p.fabricante || ""}</td>
            <td>${p.marca || ""}</td>
            <td>${p.quantidade || 0}</td>
            <td>R$ ${parseFloat(p.valor || 0).toFixed(2)}</td>
            <td><span class="badge badge-${p.status || "entrada"}">${p.status || "—"}</span></td>
            <td>
                <button class="btn-editar" onclick='abrirEditarProduto(${JSON.stringify(p)})'>Editar</button>
                <button class="btn-deletar" onclick="confirmarDeletar('produto','${p.codigoBarras}')">Deletar</button>
            </td>
        </tr>
    `).join("");
}

function filtrarTabelaProdutos() {
    const busca = document.getElementById("buscaProduto").value.toLowerCase();
    const filtrado = todosProdutos.filter(p =>
        (p.nomeProduto || "").toLowerCase().includes(busca) ||
        (p.codigoBarras || "").toLowerCase().includes(busca) ||
        (p.fabricante || "").toLowerCase().includes(busca)
    );
    renderizarProdutos(filtrado);
}

function abrirEditarProduto(p) {
    document.getElementById("editCodigo").value = p.codigoBarras || "";
    document.getElementById("editNome").value = p.nomeProduto || "";
    document.getElementById("editFabricante").value = p.fabricante || "";
    document.getElementById("editMarca").value = p.marca || "";
    document.getElementById("editDataFab").value = p.dataFabricacao || "";
    document.getElementById("editDataVenc").value = p.dataVencimento || "";
    document.getElementById("editQuantidade").value = p.quantidade || 0;
    document.getElementById("editValor").value = p.valor || "";
    document.getElementById("editTotal").value = p.total || "";
    document.getElementById("editStatus").value = p.status || "entrada";
    abrirModal("modalProduto");
}

async function salvarProduto() {
    const params = new URLSearchParams();
    params.append("codigoBarras", document.getElementById("editCodigo").value);
    params.append("nomeProduto", document.getElementById("editNome").value);
    params.append("fabricante", document.getElementById("editFabricante").value);
    params.append("marca", document.getElementById("editMarca").value);
    params.append("dataFabricacao", document.getElementById("editDataFab").value);
    params.append("dataVencimento", document.getElementById("editDataVenc").value);
    params.append("quantidade", document.getElementById("editQuantidade").value);
    params.append("valor", document.getElementById("editValor").value);
    params.append("total", document.getElementById("editTotal").value);
    params.append("status", document.getElementById("editStatus").value);

    try {
        const resp = await fetch("/api/gerenciamento/produtos", { method: "POST", body: params });
        const dados = await resp.json();
        if (dados.sucesso) {
            fecharModal("modalProduto");
            mostrarToast("Produto atualizado com sucesso!", "sucesso");
            carregarProdutos();
        } else {
            mostrarToast("Erro ao atualizar produto.", "erro");
        }
    } catch (e) {
        mostrarToast("Erro de conexão.", "erro");
    }
}

// ===== USUÁRIOS =====
async function carregarUsuarios() {
    try {
        const resp = await fetch("/api/gerenciamento/usuarios");
        todosUsuarios = await resp.json();
        renderizarUsuarios(todosUsuarios);
    } catch (e) {
        console.error("Erro ao carregar usuários", e);
    }
}

function renderizarUsuarios(lista) {
    const corpo = document.getElementById("corpoUsuarios");
    document.getElementById("contagemUsuarios").textContent = lista.length + " usuário(s)";

    if (lista.length === 0) {
        corpo.innerHTML = "<tr><td colspan='7' class='empty-state'>Nenhum usuário encontrado.</td></tr>";
        return;
    }

    corpo.innerHTML = lista.map(u => `
        <tr>
            <td>${u.id || ""}</td>
            <td>${u.nomeUsuario || ""}</td>
            <td>${(u.nome || "") + " " + (u.sobrenome || "")}</td>
            <td>${u.email || ""}</td>
            <td>${u.telefone || ""}</td>
            <td>${u.funcao || ""}</td>
            <td>
                <button class="btn-editar" onclick='abrirEditarUsuario(${JSON.stringify(u)})'>Editar</button>
                <button class="btn-deletar" onclick="confirmarDeletar('usuario',${u.id})">Deletar</button>
            </td>
        </tr>
    `).join("");
}

function filtrarTabelaUsuarios() {
    const busca = document.getElementById("buscaUsuario").value.toLowerCase();
    const filtrado = todosUsuarios.filter(u =>
        (u.nome || "").toLowerCase().includes(busca) ||
        (u.nomeUsuario || "").toLowerCase().includes(busca) ||
        (u.email || "").toLowerCase().includes(busca)
    );
    renderizarUsuarios(filtrado);
}

function abrirEditarUsuario(u) {
    document.getElementById("editUserId").value = u.id || "";
    document.getElementById("editUserUsername").value = u.nomeUsuario || "";
    document.getElementById("editUserNome").value = u.nome || "";
    document.getElementById("editUserSobrenome").value = u.sobrenome || "";
    document.getElementById("editUserEmail").value = u.email || "";
    document.getElementById("editUserTelefone").value = u.telefone || "";
    document.getElementById("editUserFuncao").value = u.funcao || "";
    document.getElementById("editUserMatricula").value = u.matricula || "";
    abrirModal("modalUsuario");
}

async function salvarUsuario() {
    const params = new URLSearchParams();
    params.append("id", document.getElementById("editUserId").value);
    params.append("nome", document.getElementById("editUserNome").value);
    params.append("sobrenome", document.getElementById("editUserSobrenome").value);
    params.append("email", document.getElementById("editUserEmail").value);
    params.append("telefone", document.getElementById("editUserTelefone").value);
    params.append("funcao", document.getElementById("editUserFuncao").value);
    params.append("matricula", document.getElementById("editUserMatricula").value);

    try {
        const resp = await fetch("/api/gerenciamento/usuarios", { method: "POST", body: params });
        const dados = await resp.json();
        if (dados.sucesso) {
            fecharModal("modalUsuario");
            mostrarToast("Usuário atualizado com sucesso!", "sucesso");
            carregarUsuarios();
        } else {
            mostrarToast("Erro ao atualizar usuário.", "erro");
        }
    } catch (e) {
        mostrarToast("Erro de conexão.", "erro");
    }
}

// ===== DELETAR (genérico) =====
function confirmarDeletar(tipo, identificador) {
    const nome = tipo === "produto" ? "este produto" : "este usuário";
    if (!confirm("Tem certeza que deseja deletar " + nome + "? Esta ação não pode ser desfeita.")) return;
    deletar(tipo, identificador);
}

async function deletar(tipo, identificador) {
    const url = tipo === "produto" ? "/api/gerenciamento/produtos" : "/api/gerenciamento/usuarios";
    const params = new URLSearchParams();
    params.append("_method", "DELETE");
    if (tipo === "produto") params.append("codigoBarras", identificador);
    else params.append("id", identificador);

    try {
        const resp = await fetch(url, { method: "POST", body: params });
        const dados = await resp.json();
        if (dados.sucesso) {
            mostrarToast("Deletado com sucesso!", "sucesso");
            if (tipo === "produto") carregarProdutos();
            else carregarUsuarios();
        } else {
            mostrarToast("Erro ao deletar.", "erro");
        }
    } catch (e) {
        mostrarToast("Erro de conexão.", "erro");
    }
}

// ===== ESTOQUE =====
async function carregarEstoqueGer() {
    try {
        const resp = await fetch("/api/gerenciamento/estoque");
        const lista = await resp.json();
        renderizarEstoqueGer(lista);

        // Alerta estoque baixo
        const baixoResp = await fetch("/api/gerenciamento/estoque?tipo=baixo");
        const baixo = await baixoResp.json();
        const alertaDiv = document.getElementById("alertaEstoqueBaixo");
        if (baixo.length > 0) {
            alertaDiv.style.display = "block";
            document.getElementById("listaBaixo").textContent = baixo.map(p => p.nomeProduto + " (" + p.quantidade + ")").join(", ");
        } else {
            alertaDiv.style.display = "none";
        }
    } catch (e) {
        console.error("Erro ao carregar estoque", e);
    }
}

function renderizarEstoqueGer(lista) {
    const corpo = document.getElementById("corpoEstoqueGer");
    if (lista.length === 0) {
        corpo.innerHTML = "<tr><td colspan='6' class='empty-state'>Nenhum produto no estoque.</td></tr>";
        return;
    }
    corpo.innerHTML = lista.map(p => {
        const qtd = p.quantidade || 0;
        const badgeClass = qtd <= 5 ? "badge-baixo" : (p.status === "saida" ? "badge-saida" : "badge-entrada");
        return `
        <tr>
            <td>${p.codigoBarras || ""}</td>
            <td>${p.nomeProduto || ""}</td>
            <td>${p.marca || ""}</td>
            <td><span class="badge ${badgeClass}">${qtd}</span></td>
            <td>R$ ${parseFloat(p.valor || 0).toFixed(2)}</td>
            <td><span class="badge badge-${p.status || "entrada"}">${p.status || "—"}</span></td>
        </tr>
        `;
    }).join("");
}

async function registrarMovimentacao() {
    const codigo = document.getElementById("movCodigo").value.trim();
    const tipo = document.getElementById("movTipo").value;
    const quantidade = document.getElementById("movQuantidade").value;

    if (!codigo || !quantidade || quantidade <= 0) {
        mostrarToast("Preencha o código e a quantidade.", "erro");
        return;
    }

    const params = new URLSearchParams();
    params.append("codigoBarras", codigo);
    params.append("tipoMovimentacao", tipo);
    params.append("quantidade", quantidade);

    try {
        const resp = await fetch("/api/gerenciamento/estoque", { method: "POST", body: params });
        const dados = await resp.json();
        if (dados.sucesso) {
            mostrarToast(dados.mensagem, "sucesso");
            document.getElementById("movCodigo").value = "";
            document.getElementById("movQuantidade").value = "1";
            carregarEstoqueGer();
        } else {
            mostrarToast(dados.mensagem, "erro");
        }
    } catch (e) {
        mostrarToast("Erro de conexão.", "erro");
    }
}

// ===== RELATÓRIO =====
async function carregarRelatorio() {
    try {
        // Produtos
        const respProd = await fetch("/api/gerenciamento/produtos");
        const produtos = await respProd.json();

        const entradas = produtos.filter(p => p.status === "entrada").length;
        const saidas = produtos.filter(p => p.status === "saida").length;
        document.getElementById("relTotalProdutos").textContent = produtos.length;
        document.getElementById("relEntradas").textContent = entradas;
        document.getElementById("relSaidas").textContent = saidas;

        // Estoque baixo
        const respBaixo = await fetch("/api/gerenciamento/estoque?tipo=baixo");
        const baixo = await respBaixo.json();
        document.getElementById("relBaixo").textContent = baixo.length;

        const corpo = document.getElementById("corpoRelBaixo");
        if (baixo.length === 0) {
            corpo.innerHTML = "<tr><td colspan='5' class='empty-state'>Nenhum produto com estoque baixo.</td></tr>";
        } else {
            corpo.innerHTML = baixo.map(p => `
                <tr>
                    <td>${p.codigoBarras || ""}</td>
                    <td>${p.nomeProduto || ""}</td>
                    <td>${p.fabricante || ""}</td>
                    <td><span class="badge badge-baixo">${p.quantidade}</span></td>
                    <td><span class="badge badge-${p.status || "entrada"}">${p.status || "—"}</span></td>
                </tr>
            `).join("");
        }

        // Usuários
        const respUsers = await fetch("/api/gerenciamento/usuarios");
        const users = await respUsers.json();
        document.getElementById("relUsuarios").textContent = users.length;

    } catch (e) {
        console.error("Erro ao carregar relatório", e);
    }
}

// ===== INIT =====
window.onload = () => {
    carregarProdutos();
};
