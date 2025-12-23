# Classic Games

> The foundational games of game theory, modeled in Tenet.

---

## Prisoner's Dilemma

The most famous game in game theory. Two prisoners must decide whether to cooperate or defect.

**Key insight:** Both players defecting is the only Nash equilibrium, even though mutual cooperation would be better.

```tenet
game PrisonersDilemma {
    players Prisoner1, Prisoner2
    strategies Cooperate, Defect
    
    payoff Prisoner1 {
        (Cooperate, Cooperate): 3  // Both stay quiet
        (Cooperate, Defect): 0     // I'm quiet, they rat
        (Defect, Cooperate): 5     // I rat, they're quiet
        (Defect, Defect): 1        // Both rat
    }
    
    payoff Prisoner2 {
        (Cooperate, Cooperate): 3
        (Defect, Cooperate): 0
        (Cooperate, Defect): 5
        (Defect, Defect): 1
    }
}
```

**Nash Equilibrium:** `(Defect, Defect)` with payoffs `(1, 1)`

---

## Battle of the Sexes

A coordination game with two equilibria. Success requires coordination.

```tenet
game BattleOfSexes {
    players Alice, Bob
    strategies Opera, Football
    
    payoff Alice {
        (Opera, Opera): 3       // Alice's preference
        (Opera, Football): 0
        (Football, Opera): 0
        (Football, Football): 2
    }
    
    payoff Bob {
        (Opera, Opera): 2
        (Opera, Football): 0
        (Football, Opera): 0
        (Football, Football): 3 // Bob's preference
    }
}
```

**Nash Equilibria:** 
- `(Opera, Opera)` with payoffs `(3, 2)`
- `(Football, Football)` with payoffs `(2, 3)`

---

## Matching Pennies

A zero-sum game with no pure strategy equilibrium.

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

**Nash Equilibrium (Mixed):** Both players randomize 50-50

---

## Stag Hunt

A game about trust and social cooperation.

```tenet
game StagHunt {
    players Hunter1, Hunter2
    strategies Stag, Hare
    
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

**Nash Equilibria:**
- `(Stag, Stag)` — Pareto optimal
- `(Hare, Hare)` — Risk dominant

---

## Chicken (Hawk-Dove)

A game about brinkmanship and aggression.

```tenet
game Chicken {
    players Driver1, Driver2
    strategies Swerve, Straight
    
    payoff Driver1 {
        (Swerve, Swerve): 0       // Tie
        (Swerve, Straight): -1    // I'm the chicken
        (Straight, Swerve): 1     // I win
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

**Nash Equilibria:**
- `(Swerve, Straight)` — Driver2 wins
- `(Straight, Swerve)` — Driver1 wins

---

## Next Steps

- **[Industrial Organization →](./industrial-org.md)** — Economic competition models
