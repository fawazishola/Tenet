# Tenet Language Support for VS Code

Syntax highlighting for the **Tenet** game theory programming language.

## Features

- **Full syntax highlighting** for `.tenet` files
- **Game theory keywords**: `game`, `players`, `strategies`, `payoff`, `solve`
- **Language features**: `var`, `fun`, `class`, `if`, `while`, `for`
- **Smart bracket matching** and auto-closing
- **Comment highlighting**

## Screenshot

```tenet
game PrisonersDilemma {
    players Alice, Bob
    strategies Cooperate, Defect
    
    payoff Alice {
        (Cooperate, Cooperate): 3
        (Cooperate, Defect): 0
        (Defect, Cooperate): 5
        (Defect, Defect): 1
    }
}

solve PrisonersDilemma;
```

## Installation

### Method 1: Copy to Extensions (Quick)

1. Copy the entire `tenet-vscode` folder
2. Paste it into your VS Code extensions directory:
   - **Windows**: `%USERPROFILE%\.vscode\extensions\`
   - **Mac/Linux**: `~/.vscode/extensions/`
3. Restart VS Code
4. Open any `.tenet` file

### Method 2: VSIX Package

```bash
# Install vsce if you haven't
npm install -g @vscode/vsce

# Package the extension
cd tenet-vscode
vsce package

# Install the .vsix file
code --install-extension tenet-language-0.1.0.vsix
```

### Method 3: Development Mode

1. Open the `tenet-vscode` folder in VS Code
2. Press `F5` to launch Extension Development Host
3. Open a `.tenet` file in the new window

## Highlighting Scopes

| Element | Scope | Example |
|---------|-------|---------|
| Game theory keywords | `keyword.control.gametheory` | `game`, `payoff`, `solve` |
| Control flow | `keyword.control` | `if`, `while`, `return` |
| Declarations | `keyword.declaration` | `var`, `fun`, `class` |
| Game/Class names | `entity.name.type` | `PrisonersDilemma` |
| Functions | `entity.name.function` | `fibonacci` |
| Strings | `string.quoted.double` | `"Hello"` |
| Numbers | `constant.numeric` | `42`, `3.14` |
| Comments | `comment.line` | `// comment` |

## Language Features

- **Comments**: `// single line`
- **Strings**: `"double quoted"`
- **Bracket matching**: `{}`, `[]`, `()`
- **Auto-indentation**: After `{`

## Contributing

This extension is part of the [Tenet](https://github.com/yourusername/tenet) project.

## License

MIT
