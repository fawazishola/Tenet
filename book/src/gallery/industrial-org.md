# Industrial Organization

> Models of firm competition and market structure.

---

## Cournot Duopoly

Two firms compete by choosing production quantities.

```tenet
// Cournot competition: quantity competition
// If both produce low: price stays high, both profit
// If both produce high: price crashes, profits disappear

game CournotDuopoly {
    players Firm1, Firm2
    strategies Low, High
    
    payoff Firm1 {
        (Low, Low): 900     // Both restrain, high prices
        (Low, High): 450    // They flood market
        (High, Low): 675    // I flood market
        (High, High): 0     // Price war, no profits
    }
    
    payoff Firm2 {
        (Low, Low): 900
        (High, Low): 450
        (Low, High): 675
        (High, High): 0
    }
}
```

**Nash Equilibrium:** Depends on specific payoffs — often results in overproduction.

---

## Bertrand Competition

Two firms compete by setting prices.

```tenet
game BertrandDuopoly {
    players Firm1, Firm2
    strategies HighPrice, LowPrice
    
    payoff Firm1 {
        (HighPrice, HighPrice): 50  // Both price high
        (HighPrice, LowPrice): 0    // They undercut me
        (LowPrice, HighPrice): 80   // I undercut them
        (LowPrice, LowPrice): 10    // Price war
    }
    
    payoff Firm2 {
        (HighPrice, HighPrice): 50
        (LowPrice, HighPrice): 0
        (HighPrice, LowPrice): 80
        (LowPrice, LowPrice): 10
    }
}
```

**Nash Equilibrium:** Both undercut → `(LowPrice, LowPrice)`

---

## Entry Deterrence

An incumbent firm tries to prevent a new entrant.

```tenet
game EntryDeterrence {
    players Entrant, Incumbent
    strategies Enter, StayOut, Fight, Accommodate
    
    // Entrant chooses: Enter or StayOut
    // Incumbent responds: Fight or Accommodate
    
    payoff Entrant {
        (Enter, Accommodate): 2   // Entry succeeds
        (Enter, Fight): -1        // Entry but costly war
        (StayOut, Accommodate): 0 // No entry
        (StayOut, Fight): 0       // No entry
    }
    
    payoff Incumbent {
        (Enter, Accommodate): 1   // Share market
        (Enter, Fight): -1        // Costly war
        (StayOut, Accommodate): 3 // Monopoly
        (StayOut, Fight): 3       // Monopoly
    }
}
```

**Key Question:** Can the incumbent credibly commit to fighting?

---

## Stackelberg Leadership

A leader moves first, follower observes and responds.

```tenet
// Simultaneous-move approximation
// Full sequential game requires extensive form (future feature)

var leader_advantage = 2;

game StackelbergApprox {
    players Leader, Follower
    strategies High, Low
    
    payoff Leader {
        (High, High): 20
        (High, Low): 35 + leader_advantage
        (Low, High): 25
        (Low, Low): 30
    }
    
    payoff Follower {
        (High, High): 20
        (Low, High): 35
        (High, Low): 15
        (Low, Low): 25
    }
}
```

---

## Next Steps

- **[Behavioral Economics →](./behavioral.md)** — Trust, ultimatums, and social preferences
