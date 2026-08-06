# LiveEvents Engine - Architecture v1.0

## Objetivo

Criar uma Engine de Eventos para Lives onde TikTok, simulador, comandos
e futuras integrações utilizem o mesmo núcleo.

## Fluxo

``` text
Connector
    ↓
Preset Loader
    ↓
Preset
    ↓
BaseEvent(s)
    ↓
LiveEngine
    ↓
EventQueue
    ↓
EventScheduler
    ↓
EventHandler
    ↓
Minecraft
```

## Estrutura

``` text
core/
├── engine/
├── queues/
├── scheduler/
├── events/
├── handlers/
├── presets/
├── contexts/
└── exceptions/

connectors/
commands/
listeners/
config/
dashboard/
```

## Regras

-   Connectors nunca acessam a API do Minecraft.
-   Presets apenas descrevem eventos.
-   Eventos não conhecem TikTok.
-   Handlers executam apenas um tipo de evento.
-   O núcleo (Engine, Queue e Scheduler) deve permanecer estável.
-   Novas funcionalidades devem ser adicionadas criando novos Events,
    Handlers e Presets, sem alterar o Core.
