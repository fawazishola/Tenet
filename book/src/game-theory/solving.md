# Solving Games

> Analyzing games and finding Nash equilibria.

---

## The `solve` Statement

```tenet
solve GameName;
```

This analyzes the specified game and outputs information.

---

## Current Output

In the current version, `solve` displays game structure:

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
```

Output:
```
=== Game: PrisonersDilemma ===
Players: Alice, Bob
Strategies: Cooperate, Defect
Payoff matrix displayed...
```

---

## Coming Soon: Nash Equilibrium Finder

### Phase 2: Pure Strategy Nash Equilibria

```tenet
solve PrisonersDilemma;
// Expected output:
// Nash Equilibria (Pure):
//   (Defect, Defect)
//     Alice: 1, Bob: 1
```

**Algorithm**: Check all strategy profiles for mutual best responses.

### Phase 3: Mixed Strategy Nash Equilibria

```tenet
solve MatchingPennies;
// Expected output:
// Nash Equilibrium (Mixed):
//   Matcher: 50% Heads, 50% Tails
//   Mismatcher: 50% Heads, 50% Tails
//   Expected payoffs: 0, 0
```

**Algorithm**: Lemke-Howson for 2-player bimatrix games.

---

## Solve Options (Planned)

```tenet
solve GameName using "pure";      // Only pure strategy NE
solve GameName using "mixed";     // Include mixed strategies
solve GameName using "dominant";  // Find dominant strategies
```

---

## Understanding Nash Equilibrium

A **Nash Equilibrium** is a strategy profile where no player can improve their payoff by unilaterally changing their strategy.

In Prisoner's Dilemma:
- At `(Defect, Defect)`: 
  - Alice gets 1. If she switches to Cooperate, she gets 0. (Worse)
  - Bob gets 1. If he switches to Cooperate, he gets 0. (Worse)
  - Neither wants to deviate → **Nash Equilibrium**

---

## Roadmap

| Phase | Feature | Status |
|-------|---------|--------|
| 1 | Game definitions & `solve` display | ✅ Complete |
| 2 | Pure strategy Nash equilibrium | 🔜 Coming |
| 3 | Mixed strategy Nash equilibrium | 📅 Planned |
| 4 | N-player games | 📅 Planned |
| 5 | Simulations with strategies | 📅 Future |

---

## Next Steps

- **[Classic Games →](../gallery/classic-games.md)** — See solved examples
