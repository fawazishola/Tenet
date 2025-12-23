# Contributing to Tenet

Thank you for your interest in contributing to Tenet! This document provides guidelines and instructions for contributing.

## Table of Contents

- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Coding Conventions](#coding-conventions)
- [Testing](#testing)
- [Submitting Changes](#submitting-changes)

## Getting Started

1. Fork the repository
2. Clone your fork: `git clone https://github.com/YOUR_USERNAME/Tenet.git`
3. Create a feature branch: `git checkout -b feature/your-feature-name`

## Development Setup

### Prerequisites

- Java JDK 11 or higher
- Git

### Building the Project

```bash
# Compile the project
javac -d out src/com/craftinginterpreters/lox/*.java

# Run the REPL
java -cp out com.craftinginterpreters.lox.Lox

# Run a script
java -cp out com.craftinginterpreters.lox.Lox script.tnt
```

### IDE Setup

This project works with any Java IDE:
- IntelliJ IDEA (recommended)
- Eclipse
- VS Code with Java Extension Pack

## Coding Conventions

### Java Style

- Use 4 spaces for indentation (no tabs)
- Opening braces on the same line
- Maximum line length: 100 characters
- Use meaningful variable and method names

### Example

```java
private Expr parseExpression() {
    Expr left = parseTerm();
    
    while (match(PLUS, MINUS)) {
        Token operator = previous();
        Expr right = parseTerm();
        left = new Expr.Binary(left, operator, right);
    }
    
    return left;
}
```

### Commit Messages

Use clear, descriptive commit messages:

```
feat: add support for while loops
fix: correct precedence for unary operators
docs: update grammar documentation
test: add tests for string concatenation
```

## Testing

### Running Tests

```bash
# Coming soon - test framework being set up
java -cp out com.craftinginterpreters.lox.TestRunner
```

### Writing Tests

1. Create test files in `tests/` directory
2. Use `.tnt` extension for Tenet code tests
3. Include expected output in comments

```tenet
// tests/arithmetic.tnt
// expect: 6
print 2 + 4;

// expect: 14
print 2 + 3 * 4;
```

## Submitting Changes

### Pull Request Process

1. Ensure your code compiles without errors
2. Run all tests and ensure they pass
3. Update documentation if needed
4. Create a Pull Request with a clear description

### PR Title Format

```
feat: Add support for X
fix: Resolve issue with Y
docs: Update Z documentation
refactor: Improve performance of W
```

### Review Process

1. All PRs require at least one review
2. Address any feedback promptly
3. Keep PRs focused and reasonably sized

## Questions?

Feel free to open an issue for any questions or discussions!

---

Thank you for contributing to Tenet! 🚀
