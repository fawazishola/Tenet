# Solving Games

> Analyzing games and finding Nash equilibria.

---

## The `solve` Statement

```tenet
solve GameName;
```

This analyzes the specified game and outputs information.

---

## Nash Equilibrium Solver

Tenet supports finding **Pure Strategy Nash Equilibria**.

```tenet
solve PrisonersDilemma;
```

**Output:**

```
═══════════════════════════════════════
Game: PrisonersDilemma
Players: Alice, Bob
Strategies: Cooperate, Defect
───────────────────────────────────────
Nash Equilibria (Pure Strategy):
  -> (Defect, Defect) with payoffs (1, 1)
═══════════════════════════════════════
```

### How it Works
The solver iterates through every possible strategy profile (combination of strategies). For each profile, it checks if any player has a unilateral incentive to deviate (switch strategies) to improve their payoff. If no player wants to switch, it is a Nash Equilibrium.

### Multiple Equilibria
If a game has multiple pure strategy equilibria, like the **Battle of the Sexes**, Tenet will list them all:

```
Nash Equilibria (Pure Strategy):
  -> (Opera, Opera) with payoffs (3, 2)
  -> (Football, Football) with payoffs (2, 3)
```

### No Pure Equilibrium
Some games, like **Matching Pennies**, have no pure strategy equilibrium. In this case, Tenet will inform you:

```
No Pure Strategy Nash Equilibrium found.
Try mixed strategies (coming soon).
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
