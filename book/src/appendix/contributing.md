# Contributing

> Help us build the future of game theory programming.

---

## Getting Started

### 1. Fork the Repository

```bash
git clone https://github.com/yourusername/tenet.git
cd tenet
```

### 2. Set Up Development Environment

```bash
# Compile
javac -d out src/org/axiom/tenet/*.java

# Run tests
.\tenet test_suite.lox
```

### 3. Make Changes

Create a new branch:
```bash
git checkout -b feature/my-feature
```

---

## Contribution Areas

### High Priority 🔥

1. **Nash Equilibrium Solver**
   - Pure strategy finder
   - Best response algorithm
   - Mixed strategy support (Lemke-Howson)

2. **Example Games**
   - Add games from economics literature
   - Include expected equilibria
   - Document real-world applications

### Medium Priority 📝

3. **Documentation**
   - Tutorial improvements
   - API documentation
   - More examples

4. **Testing**
   - Expand test coverage
   - Edge case testing
   - Performance benchmarks

### Good First Issues 🌱

5. **Syntax Highlighting**
   - Additional editor support (Vim, Emacs)
   - TreeSitter grammar

6. **Error Messages**
   - Improve error clarity
   - Add suggestions for common mistakes

---

## Code Style

### Java Conventions

- 4-space indentation
- Opening braces on same line
- Descriptive variable names
- Comments for non-obvious logic

### Example

```java
public class GameSolver {
    private final Game game;
    
    public GameSolver(Game game) {
        this.game = game;
    }
    
    // Find all pure strategy Nash equilibria
    public List<StrategyProfile> findNashEquilibria() {
        List<StrategyProfile> equilibria = new ArrayList<>();
        
        for (StrategyProfile profile : game.allProfiles()) {
            if (isNashEquilibrium(profile)) {
                equilibria.add(profile);
            }
        }
        
        return equilibria;
    }
}
```

---

## Pull Request Process

1. **Create a descriptive PR title**
   - Good: `Add pure strategy Nash equilibrium solver`
   - Bad: `Fix stuff`

2. **Include:**
   - Description of changes
   - Test coverage
   - Documentation updates

3. **Ensure:**
   - All tests pass
   - No compiler warnings
   - Code follows style guide

---

## Questions?

- Open an Issue for bugs or feature requests
- Start a Discussion for questions
- Check existing Issues before creating new ones

---

## Code of Conduct

Be respectful, inclusive, and constructive. See [CODE_OF_CONDUCT.md](../../../CODE_OF_CONDUCT.md) for details.
