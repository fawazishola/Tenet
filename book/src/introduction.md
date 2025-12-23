# Tenet

> A domain-specific language for game theory modeling, analysis, and simulation.

<div class="warning">
<strong>🚧 Active Development</strong> — Tenet is under active development. Some features described in these docs are planned but not yet implemented.
</div>

---

## What is Tenet?

Tenet is a programming language designed for **game theorists, economists, and researchers** who want to model strategic interactions without becoming expert programmers.

Instead of wrestling with matrices in Python or MATLAB, you describe games in a natural, readable syntax:

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
  
  payoff Bob {
    (Cooperate, Cooperate): 3
    (Defect, Cooperate): 0
    (Cooperate, Defect): 5
    (Defect, Defect): 1
  }
}

solve PrisonersDilemma;
// Output: Game analysis with Nash equilibrium
```

---

## Why Tenet?

**Libraries like `nashpy` or `gambit` are powerful, but they require users to think like programmers first and game theorists second.** Tenet inverts this:

| Approach | Focus | Error Handling |
|---|---|---|
| Python + Library | "How do I structure this data?" | Runtime crashes |
| **Tenet** | "How do I model this game?" | Compile-time validation |

By making game theory a **first-class citizen** of the language, we can:

1. **Catch errors early** — Invalid payoff matrices are syntax errors, not runtime bugs
2. **Optimize simulations** — The compiler knows the game structure and can optimize  
3. **Enforce correctness** — You can't accidentally create a game with mismatched strategies

---

## Features

| Feature | Description |
|---|---|
| **Clean Syntax** | Minimalist, readable syntax—no boilerplate |
| **Game Definitions** | Define players, strategies, and payoff matrices naturally |
| **Full Language** | Functions, classes, loops—a complete programming language |
| **Economic Models** | 12+ pre-built models from microeconomics to political science |

---

## Part of the Axiom Ecosystem

Tenet is one component of a larger computational stack:

| Project | Description |
|---|---|
| **Tenet** | Game Theory DSL *(you are here)* |
| **Flux** | Math-first programming language |
| **Axiom** | Neurosymbolic AI-native math operating system |
| **Alexthia** | Reasoning LLM that understands the entire stack |

---

## Quick Example: Cournot Duopoly

```tenet
// Two firms compete on quantity
var max_price = 100;
var unit_cost = 10;

game CournotDuopoly {
    players Firm1, Firm2
    strategies Low, High
    
    payoff Firm1 {
        (Low, Low): 900    // Price high, profits high
        (Low, High): 450   // They flood market, I suffer
        (High, Low): 675   // I flood market, I gain short-term
        (High, High): 0    // Price crashes, nobody profits
    }
    
    payoff Firm2 {
        (Low, Low): 900
        (High, Low): 450
        (Low, High): 675
        (High, High): 0
    }
}

solve CournotDuopoly;
```

---

## Next Steps

- **[Installation](./getting-started/installation.md)** — Get Tenet running on your machine
- **[Quick Start](./getting-started/quick-start.md)** — Write your first Tenet program
- **[Language Guide](./language/data-types.md)** — Learn the full language
- **[Game Theory DSL](./game-theory/defining-games.md)** — Deep dive into game definitions
