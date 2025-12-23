# Players & Strategies

> Configuring the decision-makers and their available actions.

---

## Players

### Basic Declaration

```tenet
players Alice, Bob
```

### Meaningful Names

Use names that reflect the domain:

```tenet
// Economic games
players Firm1, Firm2
players Buyer, Seller
players Incumbent, Entrant

// Political games
players Congress, President
players Country1, Country2

// Social games
players Driver1, Driver2
players Hunter1, Hunter2
```

---

## Strategies

### Basic Declaration

```tenet
strategies Cooperate, Defect
```

### Multiple Strategies

```tenet
strategies Rock, Paper, Scissors
strategies Low, Medium, High
strategies Enter, Exit, Wait
```

### Shared Strategy Sets

Currently, all players share the same strategy set. This works for symmetric games like:

- Prisoner's Dilemma
- Stag Hunt
- Rock-Paper-Scissors

---

## Modeling Asymmetric Games

For games where players have different actions, include all strategies and assign payoff 0 to invalid combinations:

```tenet
game EntryDeterrence {
    players Entrant, Incumbent
    strategies Enter, StayOut, Fight, Accommodate
    
    // Entrant only uses: Enter, StayOut
    // Incumbent only uses: Fight, Accommodate
    
    payoff Entrant {
        (Enter, Accommodate): 2    // Valid
        (Enter, Fight): -1         // Valid
        (StayOut, Accommodate): 0  // Valid (no entry = no payoff)
        (StayOut, Fight): 0        // Valid
        // Entrant doesn't use Fight or Accommodate
    }
    
    payoff Incumbent {
        (Enter, Accommodate): 1    // Valid
        (Enter, Fight): -1         // Valid
        (StayOut, Accommodate): 2  // Valid (monopoly maintained)
        (StayOut, Fight): 2        // Valid
    }
}
```

<div class="info">
<strong>Future:</strong> Asymmetric strategy sets (<code>strategies Alice: X, Y</code> and <code>strategies Bob: A, B</code>) are on the roadmap.
</div>

---

## N-Player Games

Games with more than 2 players are partially supported:

```tenet
players P1, P2, P3
strategies Left, Right
```

However, payoff rules currently only support 2-strategy profiles. Full N-player syntax is coming in a future release.

---

## Next Steps

- **[Payoff Matrices →](./payoffs.md)** — Complex payoff expressions
