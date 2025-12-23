# Installation

> Get Tenet running on your machine in under 5 minutes.

---

## Prerequisites

- **Java 11+** (OpenJDK or Oracle JDK)
- **Git** (for cloning the repository)

### Check Java Version

```bash
java -version
```

You should see output like:
```
openjdk version "11.0.x" ...
```

---

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/tenet.git
cd tenet
```

### 2. Compile

```bash
javac -d out src/org/axiom/tenet/*.java
```

This compiles all Tenet source files into the `out/` directory.

### 3. Run the REPL

**Windows:**
```bash
.\tenet
```

**macOS/Linux:**
```bash
java -cp out org.axiom.tenet.Tenet
```

You should see:
```
Tenet v0.1.0
>>> 
```

### 4. Run a File

```bash
.\tenet examples/demo.tenet
```

---

## Directory Structure

```
tenet/
├── src/                    # Source code
│   └── org/axiom/tenet/
│       ├── Tenet.java      # Entry point
│       ├── Scanner.java    # Lexer
│       ├── Parser.java     # Parser
│       └── ...
├── examples/               # Example .tenet files
│   ├── demo.tenet
│   ├── classic_games/
│   ├── behavioral/
│   └── ...
├── out/                    # Compiled classes
└── tenet.bat               # Windows launcher
```

---

## Verify Installation

Create a file `hello.tenet`:

```tenet
print "Hello, Game Theory!";
```

Run it:

```bash
.\tenet hello.tenet
```

Expected output:
```
Hello, Game Theory!
```

---

## VS Code Extension

Tenet has a VS Code extension for syntax highlighting:

1. Open VS Code
2. Go to Extensions (`Ctrl+Shift+X`)
3. Search for "Tenet" or install from `tenet-vscode/` folder
4. Open any `.tenet` file

---

## Troubleshooting

### "java: command not found"

Install Java:
- **Windows**: Download from [adoptium.net](https://adoptium.net/)
- **macOS**: `brew install openjdk@11`
- **Linux**: `sudo apt install openjdk-11-jdk`

### "cannot find symbol" during compilation

Make sure you're in the root `tenet/` directory and running the exact compile command:

```bash
javac -d out src/org/axiom/tenet/*.java
```

---

## Next Steps

- **[Quick Start →](./quick-start.md)** — Write your first Tenet program
