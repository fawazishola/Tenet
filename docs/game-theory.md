# Game Theory in Tenet

> A comprehensive guide to modeling and analyzing strategic games in Tenet.

## Table of Contents

1. [Introduction](#introduction)
2. [Basic Concepts](#basic-concepts)
3. [Game Definition Syntax](#game-definition-syntax)
4. [Classic Games](#classic-games)
5. [Advanced Features](#advanced-features)
6. [Roadmap](#roadmap)

---

## Introduction

Tenet is designed to make game theory accessible. Instead of wrestling with matrices in Python or MATLAB, you describe games in a natural, readable syntax:

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

---

## Basic Concepts

### What is a Game?

In game theory, a **game** consists of:

1. **Players** — The decision-makers (e.g., Alice and Bob)
2. **Strategies** — Available actions for each player (e.g., Cooperate, Defect)
3. **Payoffs** — The outcomes for each combination of strategies

### Strategy Profiles

A **strategy profile** is a combination of strategies, one for each player:

```
(Alice's strategy, Bob's strategy)
```

For example: `(Cooperate, Defect)` means Alice cooperates while Bob defects.

### Payoff Matrix

A **payoff matrix** shows the outcome for every possible strategy profile:

|               | Bob: Cooperate | Bob: Defect |
|---------------|----------------|-------------|
| **Alice: Cooperate** | (3, 3)         | (0, 5)      |
| **Alice: Defect**    | (5, 0)         | (1, 1)      |

In Tenet, we specify this as separate payoff blocks for each player.

---

## Game Definition Syntax

### Complete Structure

```tenet
game GameName {
    players Player1, Player2
    strategies Strategy1, Strategy2, ...
    
    payoff Player1 {
        (MyStrategy, TheirStrategy): value
        ...
    }
    
    payoff Player2 {
        (MyStrategy, TheirStrategy): value
        ...
    }
}
```

### Players

```tenet
players Alice, Bob          // 2 players
players P1, P2, P3          // 3 players (future support)
players Firm1, Firm2        // Meaningful names
```

### Strategies

```tenet
strategies Cooperate, Defect
strategies High, Medium, Low
strategies Rock, Paper, Scissors
```

Currently, all players share the same strategy set.

### Payoff Rules

Each rule maps a strategy profile to a payoff value:

```tenet
payoff Alice {
    (Cooperate, Cooperate): 3    // (my strategy, their strategy): my payoff
    (Cooperate, Defect): 0
    (Defect, Cooperate): 5
    (Defect, Defect): 1
}
```

### Computed Payoffs

Payoff values can be expressions:

```tenet
var reward = 3;
var temptation = 5;
var punishment = 1;
var suckerPay = 0;

game PD {
    players P1, P2
    strategies C, D
    
    payoff P1 {
        (C, C): reward
        (C, D): suckerPay
        (D, C): temptation
        (D, D): punishment
    }
    
    payoff P2 {
        (C, C): reward
        (D, C): suckerPay
        (C, D): temptation
        (D, D): punishment
    }
}
```

---

## Classic Games

### 1. Prisoner's Dilemma

The most famous game in game theory. Two prisoners must decide whether to cooperate or defect.

**Key insight:** Both players defecting is the only Nash equilibrium, even though both cooperating would be better for everyone.

```tenet
game PrisonersDilemma {
    players Prisoner1, Prisoner2
    strategies Cooperate, Defect
    
    // Payoffs represent years of freedom (higher = better)
    payoff Prisoner1 {
        (Cooperate, Cooperate): 3  // Both stay quiet: light sentence
        (Cooperate, Defect): 0     // I stay quiet, they rat: worst outcome
        (Defect, Cooperate): 5     // I rat, they stay quiet: go free
        (Defect, Defect): 1        // Both rat: moderate sentence
    }
    
    payoff Prisoner2 {
        (Cooperate, Cooperate): 3
        (Defect, Cooperate): 0
        (Cooperate, Defect): 5
        (Defect, Defect): 1
    }
}
```

### 2. Coordination Game (Battle of the Sexes)

Two equilibria exist. Success requires coordination.

```tenet
game BattleOfSexes {
    players Alice, Bob
    strategies Opera, Football
    
    // Alice prefers opera, Bob prefers football
    // But both prefer being together over apart
    
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
```

### 3. Matching Pennies (Zero-Sum)

No pure strategy Nash equilibrium exists. One player wins, the other loses.

```tenet
game MatchingPennies {
    players Matcher, Mismatcher
    strategies Heads, Tails
    
    // Matcher wins if coins match, Mismatcher wins if different
    
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

### 4. Stag Hunt

A game about trust and cooperation.

```tenet
game StagHunt {
    players Hunter1, Hunter2
    strategies Stag, Hare
    
    // Hunting stag requires cooperation but yields more meat
    // Hunting hare is safe but yields less
    
    payoff Hunter1 {
        (Stag, Stag): 4     // Successful cooperative hunt
        (Stag, Hare): 0     // Partner abandons, stag escapes
        (Hare, Stag): 3     // Safe small catch
        (Hare, Hare): 3     // Safe small catch
    }
    
    payoff Hunter2 {
        (Stag, Stag): 4
        (Hare, Stag): 0
        (Stag, Hare): 3
        (Hare, Hare): 3
    }
}
```

### 5. Chicken (Hawk-Dove)

A game about brinkmanship and aggression.

```tenet
game Chicken {
    players Driver1, Driver2
    strategies Swerve, Straight
    
    payoff Driver1 {
        (Swerve, Swerve): 0      // Tie, no shame
        (Swerve, Straight): -1   // I'm the chicken
        (Straight, Swerve): 1    // I win, they're the chicken
        (Straight, Straight): -10 // Crash!
    }
    
    payoff Driver2 {
        (Swerve, Swerve): 0
        (Straight, Swerve): -1
        (Swerve, Straight): 1
        (Straight, Straight): -10
    }
}
```

---

## Advanced Features

### Asymmetric Strategies

While players currently share strategy sets, you can model asymmetric games by ignoring certain combinations:

```tenet
game AsymmetricGame {
    players Leader, Follower
    strategies Enter, StayOut, Fight, Accommodate
    
    // Leader only uses Enter/StayOut
    // Follower only uses Fight/Accommodate
    // Other combinations have payoff 0 (unused)
    
    payoff Leader {
        (Enter, Accommodate): 2
        (Enter, Fight): -1
        (StayOut, Accommodate): 0
        (StayOut, Fight): 0
    }
    
    payoff Follower {
        (Enter, Accommodate): 1
        (Enter, Fight): -1
        (StayOut, Accommodate): 2
        (StayOut, Fight): 2
    }
}
```

### Variables for Parameterized Games

Study how outcomes change with different payoffs:

```tenet
var T = 5;  // Temptation
var R = 3;  // Reward
var P = 1;  // Punishment
var S = 0;  // Sucker's payoff

// Classic PD requires: T > R > P > S and 2R > T + S

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

---

## Roadmap

### Phase 1: Game Definitions ✅ Complete

- `game` declarations
- `players` specification
- `strategies` specification
- `payoff` matrices
- `solve` statement (displays game info)

### Phase 2: Nash Equilibrium Solver (Coming Soon)

```tenet
solve PrisonersDilemma;
// Output:
// Nash Equilibria (Pure):
//   (Defect, Defect)
//     Alice: 1, Bob: 1
```

- Pure strategy Nash equilibrium finder
- Display equilibrium profiles and payoffs
- Detect games with no pure equilibria

### Phase 3: Mixed Strategy Support (Planned)

```tenet
solve MatchingPennies;
// Output:
// Nash Equilibrium (Mixed):
//   Matcher: 50% Heads, 50% Tails
//   Mismatcher: 50% Heads, 50% Tails
//   Expected payoffs: Matcher: 0, Mismatcher: 0
```

### Phase 4: Simulations & Strategies (Future)

```tenet
strategy TitForTat {
    if (round == 1) return Cooperate;
    return opponent.lastMove;
}

simulate PrisonersDilemma {
    P1 uses TitForTat
    P2 uses AlwaysDefect
    rounds 100
}
// Output: Detailed round-by-round analysis
```

---

## Resources

### Game Theory Texts

- **"Game Theory: An Introduction"** by Steven Tadelis
- **"Strategy: An Introduction to Game Theory"** by Joel Watson
- **"The Art of Strategy"** by Dixit & Nalebuff

### Online Resources

- [Game Theory 101 (YouTube)](https://www.youtube.com/GameTheory101)
- [Stanford Encyclopedia of Philosophy - Game Theory](https://plato.stanford.edu/entries/game-theory/)

---

## Quick Reference

| Element | Syntax |
|---------|--------|
| Game definition | `game Name { ... }` |
| Players | `players A, B` |
| Strategies | `strategies X, Y` |
| Payoff block | `payoff Player { ... }` |
| Payoff rule | `(S1, S2): value` |
| Solve | `solve GameName;` |
