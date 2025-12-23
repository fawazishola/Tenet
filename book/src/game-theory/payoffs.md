# Payoff Matrices

> Defining outcomes for every combination of strategies.

---

## Basic Syntax

```tenet
payoff PlayerName {
    (MyStrategy, TheirStrategy): value
}
```

The tuple `(MyStrategy, TheirStrategy)` represents:
- What **I** play (first position)
- What **they** play (second position)

---

## Payoff Values

### Numeric Literals

```tenet
payoff Alice {
    (Cooperate, Cooperate): 3
    (Cooperate, Defect): 0
    (Defect, Cooperate): 5
    (Defect, Defect): 1
}
```

### Negative Values

```tenet
payoff Player1 {
    (Swerve, Straight): -1   // Lose face
    (Straight, Straight): -10 // Crash!
}
```

### Decimal Values

```tenet
payoff Firm {
    (High, High): 4.5
    (High, Low): 2.25
}
```

---

## Variables in Payoffs

Use variables for parameterized games:

```tenet
var T = 5;  // Temptation
var R = 3;  // Reward
var P = 1;  // Punishment
var S = 0;  // Sucker's payoff

game PrisonersDilemma {
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
```

Now you can experiment by changing `T`, `R`, `P`, `S`!

---

## Expressions in Payoffs

Payoff values can be arithmetic expressions:

```tenet
var base = 10;
var bonus = 5;

payoff Firm1 {
    (Cooperate, Cooperate): base
    (Cooperate, Defect): base - bonus
    (Defect, Cooperate): base + bonus
    (Defect, Defect): base / 2
}
```

Supported operators:
- `+` addition
- `-` subtraction
- `*` multiplication
- `/` division

---

## Zero-Sum Games

In zero-sum games, one player's gain is another's loss:

```tenet
game MatchingPennies {
    players Matcher, Mismatcher
    strategies Heads, Tails
    
    payoff Matcher {
        (Heads, Heads): 1
        (Heads, Tails): -1
        (Tails, Heads): -1
        (Tails, Tails): 1
    }
    
    payoff Mismatcher {
        (Heads, Heads): -1
        (Heads, Tails): 1
        (Tails, Heads): 1
        (Tails, Tails): -1
    }
}
```

Notice: For each cell, `Matcher + Mismatcher = 0`.

---

## Payoff Matrix Visualization

The payoff blocks above represent this bimatrix:

```
                    Mismatcher
                Heads       Tails
        ┌───────────┬───────────┐
Heads   │  (1, -1)  │  (-1, 1)  │
Matcher ├───────────┼───────────┤
Tails   │  (-1, 1)  │  (1, -1)  │
        └───────────┴───────────┘
```

Each cell shows `(Matcher's payoff, Mismatcher's payoff)`.

---

## Next Steps

- **[Solving Games →](./solving.md)** — Finding Nash equilibria
