# Your First Game

> Model the most famous game in game theory: the Prisoner's Dilemma.

---

## The Story

Two criminals are arrested and interrogated separately. Each has two choices:

- **Cooperate** (stay silent) — Don't rat on your partner
- **Defect** (betray) — Testify against your partner

The outcomes depend on what *both* players choose:

| | Partner Cooperates | Partner Defects |
|---|---|---|
| **You Cooperate** | Both get 1 year | You get 3 years, they go free |
| **You Defect** | You go free, they get 3 years | Both get 2 years |

---

## The Tenet Model

Create a file `prisoners_dilemma.tenet`:

```tenet
// Prisoner's Dilemma
// Payoffs represent "years of freedom" (higher = better)

game PrisonersDilemma {
    players Alice, Bob
    strategies Cooperate, Defect
    
    payoff Alice {
        (Cooperate, Cooperate): 3  // Both silent: light sentence
        (Cooperate, Defect): 0     // I'm silent, they rat: worst outcome
        (Defect, Cooperate): 5     // I rat, they're silent: I go free
        (Defect, Defect): 1        // Both rat: moderate sentence
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

---

## Understanding the Payoff Matrix

```
                    Bob
                C       D
        ┌───────┬───────┐
    C   │ (3,3) │ (0,5) │
Alice   ├───────┼───────┤
    D   │ (5,0) │ (1,1) │
        └───────┴───────┘
```

Each cell shows `(Alice's payoff, Bob's payoff)`.

---

## The Dilemma

Here's the tragedy:

1. **If Alice knows Bob will Cooperate**: Alice should Defect (5 > 3)
2. **If Alice knows Bob will Defect**: Alice should *still* Defect (1 > 0)

Defect is Alice's **dominant strategy** — it's better no matter what Bob does.

The same logic applies to Bob. So both players Defect, getting `(1, 1)`.

But wait — if both had Cooperated, they'd get `(3, 3)`!

**This is the dilemma**: Individual rationality leads to a collectively worse outcome.

---

## Adding Variables

Make the game parameterized:

```tenet
var T = 5;  // Temptation to defect
var R = 3;  // Reward for mutual cooperation
var P = 1;  // Punishment for mutual defection
var S = 0;  // Sucker's payoff

// Classic PD requires: T > R > P > S

game ParameterizedPD {
    players P1, P2
    strategies C, D
    
    payoff P1 {
        (C, C): R
        (C, D): S
        (D, C): T
        (D, D): P
    }
    
    payoff P2 {
        (C, C): R
        (D, C): S
        (C, D): T
        (D, D): P
    }
}

solve ParameterizedPD;
```

Now you can experiment with different payoff values!

---

## What You Learned

- How to define `players` and `strategies`
- How to specify `payoff` matrices
- The concept of dominant strategies
- The Nash equilibrium of the Prisoner's Dilemma

---

## Next Steps

- **[Model Gallery](../gallery/classic-games.md)** — Explore more classic games
- **[Payoff Matrices](../game-theory/payoffs.md)** — Advanced payoff features
- **[Solving Games](../game-theory/solving.md)** — Finding Nash equilibria
