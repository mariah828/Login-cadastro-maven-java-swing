# ReflecTV

Aplicação desktop em **Java Swing** que simula uma plataforma de streaming de filmes. O usuário cria uma conta, escolhe se quer um plano **Premium**, faz login e vê um catálogo de pôsteres. Quem tem plano Premium enxerga filmes extras. Há também uma área de administração para gerenciar os usuários cadastrados.

## Funcionalidades

- **Cadastro** com e-mail, senha e nome
- **Login** com verificação de senha
- **Escolha de plano** no cadastro (Premium ou Nenhum), com validação dos dados de pagamento
- **Catálogo de filmes** em grade, com botão ▶ que aparece ao passar o mouse sobre o pôster
- **Conteúdo exclusivo Premium**: 8 filmes para todos e mais 3 para assinantes
- **Painel do administrador**: procurar, listar, alterar e excluir usuários

## Tecnologias

- Java 11 ou superior (usa `String.isBlank()`)
- Swing / AWT para a interface
- [jBCrypt](https://github.com/jeremyh/jBCrypt) para o hash das senhas
- Arquivo CSV para persistência dos dados

## Estrutura do projeto

```
src/
├── Principal/
│   ├── Main.java          # Cria a janela (700x600) e abre a tela de login
│   ├── TelaPrincipal.java # Todas as telas e a lógica da interface
│   ├── Usuario.java       # Modelo: nome, email, senha, plano
│   └── UsuarioCsv.java    # Leitura e gravação do arquivo CSV
└── imagens/
    ├── filme1.jpg ... filme8.jpg    # Catálogo geral
    └── filme9.jpg ... filme11.jpg   # Exclusivos Premium
```

## Como executar

1. Clone o repositório e abra em uma IDE Java (Eclipse, IntelliJ, NetBeans etc.).
2. Adicione a biblioteca **jBCrypt** ao projeto (JAR no classpath ou dependência Maven/Gradle).
3. Confirme que a pasta `imagens/` está no classpath (por exemplo, dentro de `src/`), pois os pôsteres são carregados com `getResource("/imagens/...")`.
4. Execute a classe `Principal.Main`.

Se uma imagem não for encontrada, o cartão do filme mostra o texto "Sem imagem".

## Telas e fluxo

```
Login ──► Catálogo de filmes ──► (admin) Busca de usuários
  │
  └─► Cadastro ──► Nome + plano ──► (se Premium) Dados do cartão ──► Catálogo
```

| Tela | O que faz |
|---|---|
| **Login** | Entra com e-mail e senha ou vai para o cadastro |
| **Plano** | Pede o nome e pergunta se o usuário quer o plano Premium |
| **Pagamento** | Pede número do cartão, CPF e CVV (só aparece para quem escolheu Premium) |
| **Filmes** | Grade de 4 colunas; usuários Premium veem uma terceira linha com 3 filmes extras |
| **Busca** (admin) | Procurar por e-mail, listar todos, alterar nome/plano e excluir |

## Validações

- **E-mail**: não pode ser vazio, não pode conter `;`, e precisa ter `@` e `.com`
- **Senha**: não pode ser vazia
- **CPF**: exatamente 11 dígitos
- **Cartão**: exatamente 16 dígitos
- **CVV**: exatamente 3 dígitos
- E-mails duplicados são rejeitados no cadastro

## Armazenamento de dados

Os usuários ficam em `Informacoes.csv`, na pasta de execução, uma linha por usuário:

```
nome;email;senhaHash;plano
```

- A senha é guardada com **BCrypt** (nunca em texto puro).
- Os dados do cartão e o CPF são usados apenas para validar o formato e **não são gravados**.
- O separador é `;`, por isso esse caractere não é aceito em e-mails nem no nome ao alterar um usuário.

## Administrador

Existe um usuário administrador definido diretamente no código. Ao entrar com essa conta, aparece o botão **Buscar** na tela de filmes, que abre o painel com:

- **Procurar**: busca um usuário pelo e-mail e mostra nome e plano
- **Listar all**: mostra todos os usuários cadastrados com seus planos
- **Alterar**: muda o nome e o plano (Premium ou Nenhum)
- **Excluir**: remove o usuário, com confirmação; o administrador não pode ser excluído

## Limitações conhecidas e melhorias futuras

- O botão ▶ ainda não reproduz nada.
- O nome digitado no cadastro não é checado contra `;`, o que pode corromper uma linha do CSV.
- A validação de e-mail é simples (apenas `@` e `.com`).
- O catálogo é fixo (`filme1.jpg` a `filme11.jpg`); poderia vir de um arquivo ou banco de dados.
- Trocar o CSV por um banco de dados.

## Autoria

Projeto desenvolvido por **[Mariah Lima]**.
