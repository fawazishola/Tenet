# Roadmap

> Current status and future plans for Tenet.

---

## Completed ✅

### Language Core
- [x] Lexer/Scanner
- [x] Expression Parser (recursive descent)
- [x] Expression Interpreter (tree-walk)
- [x] Statements & Print
- [x] Variables & State
- [x] Control Flow (if/else, while, for)
- [x] Functions & Closures
- [x] Resolving & Binding
- [x] Classes
- [x] Inheritance

### Game Theory DSL
- [x] `game` declarations
- [x] `players` specification
- [x] `strategies` specification
- [x] `payoff` matrices
- [x] Variable payoffs
- [x] Expression payoffs
- [x] `solve` statement (game info display)

---

## In Progress 🔨

### Nash Equilibrium Solver (Phase 2)
- [ ] Pure strategy Nash equilibrium finder
- [ ] Best response calculation
- [ ] Dominant strategy detection
- [ ] Equilibrium output formatting

---

## Planned 📅

### Mixed Strategy Support (Phase 3)
- [ ] Mixed strategy Nash equilibrium
- [ ] Lemke-Howson algorithm
- [ ] Probability distribution output
- [ ] Expected payoff calculation

### Simulations (Phase 4)
- [ ] `strategy` definitions
- [ ] `simulate` statement
- [ ] Round-by-round execution
- [ ] History tracking
- [ ] Strategy tournaments

### N-Player Games (Phase 5)
- [ ] Support for 3+ players
- [ ] Asymmetric strategy sets
- [ ] Coalition games

---

## Future Vision 🔮

### Language Enhancements
- [ ] Multi-line comments (`/* */`)
- [ ] Scientific notation (`1.5e10`)
- [ ] String interpolation
- [ ] Arrays/lists
- [ ] Higher-order functions

### Tooling
- [ ] Language server protocol (LSP)
- [ ] Debugger support
- [ ] REPL improvements

### Interoperability
- [ ] Flux integration
- [ ] Import/export system
- [ ] JSON game format
- [ ] Gambit compatibility

---

## Contributing

Want to help? Check the [Contributing Guide](./contributing.md)!

Priority areas:
1. Nash equilibrium solver implementation
2. Documentation improvements
3. Example games
4. Bug reports and testing
