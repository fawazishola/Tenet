# Defining Games

> The core of Tenet: expressing strategic games in clean, readable syntax.

---

## Basic Structure

```tenet
game GameName {
    players Player1, Player2
    strategies Strategy1, Strategy2
    
    payoff Player1 {
        (Strategy1, Strategy1): value
        (Strategy1, Strategy2): value
        (Strategy2, Strategy1): value
        (Strategy2, Strategy2): value
    }
    
    payoff Player2 {
        // Same structure
    }
}
```

---

## Components

### 1. Game Declaration

```tenet
game PrisonersDilemma {
    // ...
}
```

Game names should be:
- PascalCase (recommended)
- Descriptive of the strategic situation

### 2. Players

```tenet
players Alice, Bob
```

- Two or more players
- Names are identifiers (no quotes)

### 3. Strategies

```tenet
strategies Cooperate, Defect
```

- Currently shared by all players
- Names are identifiers

### 4. Payoff Blocks

```tenet
payoff Alice {
    (Cooperate, Cooperate): 3
    (Cooperate, Defect): 0
    // ...
}
```

Each rule maps `(MyStrategy, TheirStrategy)` → `PayoffValue`.

---

## Complete Example

```tenet
game BattleOfSexes {
    players Alice, Bob
    strategies Opera, Football
    
    // Alice prefers opera, Bob prefers football
    // But both prefer being together
    
    payoff Alice {
        (Opera, Opera): 3       // Together at opera (Alice's preference)
        (Opera, Football): 0    // Apart
        (Football, Opera): 0    // Apart
        (Football, Football): 2 // Together at football
    }
    
    payoff Bob {
        (Opera, Opera): 2       // Together at opera
        (Opera, Football): 0    // Apart
        (Football, Opera): 0    // Apart
        (Football, Football): 3 // Together at football (Bob's preference)
    }
}

solve BattleOfSexes;
```

---

## Next Steps

- **[Players & Strategies →](./players-strategies.md)** — Advanced player configuration
- **[Payoff Matrices →](./payoffs.md)** — Complex payoff expressions
