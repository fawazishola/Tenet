# Changelog

All notable changes to Tenet will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- **Game Theory DSL** - Define strategic games with players, strategies, and payoff matrices
- **Game keyword** - `game GameName { ... }` syntax for game definitions
- **Players declaration** - `players Alice, Bob` for specifying game participants
- **Strategies declaration** - `strategies Cooperate, Defect` for available actions
- **Payoff blocks** - `payoff Player { (S1, S2): value }` for payoff matrices
- **Solve statement** - `solve GameName;` for game analysis
- New token types: `GAME`, `PLAYERS`, `STRATEGIES`, `PAYOFF`, `SOLVE`, `USING`, `COLON`
- `StrategyProfile.java` - Runtime representation of strategy tuples
- `GameValue.java` - Runtime representation of game definitions
- Example games in `examples/game_theory_examples.tenet`

### Changed
- Rebranded from "lox" to "tenet" package
- Updated README with correct syntax and examples
- Parser now handles game-specific grammar rules

## [0.2.0] - 2024-12-21

### Added
- Complete Lox interpreter (Chapters 1-13 of Crafting Interpreters)
- Classes with constructors (`init` method)
- Inheritance with `super` keyword
- Method overriding
- Property access and mutation
- `this` binding in methods

### Changed
- Resolver now handles class and method scopes

## [0.1.0] - 2024-12-16

### Added
- Lexer/Scanner with full token support
- Recursive descent parser
- Tree-walk interpreter
- Variables and lexical scoping
- Control flow: `if`, `else`, `while`, `for`
- Functions and closures
- First-class functions
- Native `clock()` function
- REPL mode
- File execution mode

---

## Version History Summary

| Version | Date | Highlights |
|---------|------|------------|
| 0.3.0 | TBD | Nash equilibrium solver |
| **0.2.1** | 2024-12-21 | Game Theory DSL (current) |
| 0.2.0 | 2024-12-21 | Classes & Inheritance |
| 0.1.0 | 2024-12-16 | Core interpreter |
