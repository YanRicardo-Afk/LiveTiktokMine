const apiUrlInput = document.getElementById("apiUrl");
const tokenInput = document.getElementById("token");
const viewerInput = document.getElementById("viewer");
const targetInput = document.getElementById("target");
const customPresetInput = document.getElementById("customPreset");

const statusElement = document.getElementById("status");
const logElement = document.getElementById("log");

const STORAGE_KEY = "liveevents-simulator-settings";

carregarConfiguracoes();

document.querySelectorAll("[data-preset]").forEach((button) => {
    button.addEventListener("click", () => {
        executarPreset(button.dataset.preset);
    });
});

document.getElementById("btnCustom").addEventListener("click", () => {
    const preset = customPresetInput.value.trim();

    if (!preset) {
        atualizarStatus("Informe um preset", false);
        return;
    }

    executarPreset(preset);
});

document.getElementById("btnHealth").addEventListener("click", testarConexao);

document.getElementById("btnClear").addEventListener("click", () => {
    logElement.innerHTML = "";
});

[apiUrlInput, tokenInput, viewerInput, targetInput].forEach((input) => {
    input.addEventListener("change", salvarConfiguracoes);
});

async function testarConexao() {
    try {
        atualizarStatus("Testando...", null);

        const response = await fetch(`${obterApiUrl()}/health`);
        const message = await response.text();

        if (!response.ok) {
            throw new Error(message || `HTTP ${response.status}`);
        }

        atualizarStatus("API online", true);
        adicionarLog(message, true);
    } catch (error) {
        atualizarStatus("API indisponível", false);
        adicionarLog(error.message, false);
    }
}

async function executarPreset(preset) {
    const token = tokenInput.value.trim();
    const viewer = viewerInput.value.trim() || "Espectador";
    const target = targetInput.value.trim() || "random";

    if (!token) {
        atualizarStatus("Informe o token", false);
        return;
    }

    salvarConfiguracoes();
    bloquearBotoes(true);
    atualizarStatus(`Enviando ${preset}...`, null);

    const body = new URLSearchParams({
        token,
        preset,
        viewer,
        target
    });

    try {
        const response = await fetch(`${obterApiUrl()}/preset`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body
        });

        const message = await response.text();

        if (!response.ok) {
            throw new Error(message || `HTTP ${response.status}`);
        }

        atualizarStatus("Evento enviado", true);
        adicionarLog(
            `${preset} — ${viewer} → ${target}: ${message}`,
            true
        );
    } catch (error) {
        atualizarStatus("Falha no envio", false);
        adicionarLog(
            `${preset}: ${error.message}`,
            false
        );
    } finally {
        bloquearBotoes(false);
    }
}

function obterApiUrl() {
    return apiUrlInput.value.trim().replace(/\/+$/, "");
}

function atualizarStatus(message, success) {
    statusElement.textContent = message;
    statusElement.className = "status";

    if (success === true) {
        statusElement.classList.add("success");
    } else if (success === false) {
        statusElement.classList.add("error");
    } else {
        statusElement.classList.add("neutral");
    }
}

function adicionarLog(message, success) {
    const entry = document.createElement("div");
    const time = new Date().toLocaleTimeString("pt-BR");

    entry.className = `log-entry ${success ? "success" : "error"}`;
    entry.textContent = `[${time}] ${message}`;

    logElement.prepend(entry);
}

function bloquearBotoes(disabled) {
    document.querySelectorAll(
        ".preset, #btnCustom"
    ).forEach((button) => {
        button.disabled = disabled;
    });
}

function salvarConfiguracoes() {
    const settings = {
        apiUrl: apiUrlInput.value,
        token: tokenInput.value,
        viewer: viewerInput.value,
        target: targetInput.value
    };

    localStorage.setItem(
        STORAGE_KEY,
        JSON.stringify(settings)
    );
}

function carregarConfiguracoes() {
    try {
        const settings = JSON.parse(
            localStorage.getItem(STORAGE_KEY)
        );

        if (!settings) {
            return;
        }

        apiUrlInput.value = settings.apiUrl || apiUrlInput.value;
        tokenInput.value = settings.token || "";
        viewerInput.value = settings.viewer || viewerInput.value;
        targetInput.value = settings.target || targetInput.value;
    } catch {
        localStorage.removeItem(STORAGE_KEY);
    }
}