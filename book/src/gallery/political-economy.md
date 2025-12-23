# Political Economy

> Games modeling collective action, international relations, and resource management.

---

## Arms Race

Two nations decide whether to arm or disarm.

```tenet
game ArmsRace {
    players Nation1, Nation2
    strategies Arm, Disarm
    
    payoff Nation1 {
        (Arm, Arm): -100      // Expensive standoff
        (Arm, Disarm): 50     // Military advantage
        (Disarm, Arm): -200   // Vulnerable
        (Disarm, Disarm): 0   // Peace dividend
    }
    
    payoff Nation2 {
        (Arm, Arm): -100
        (Disarm, Arm): 50
        (Arm, Disarm): -200
        (Disarm, Disarm): 0
    }
}
```

**Nash Equilibrium:** Both arm — a costly, suboptimal outcome.

This is structurally identical to the Prisoner's Dilemma.

---

## Tragedy of the Commons

Multiple actors sharing a depletable resource.

```tenet
game TragedyOfCommons {
    players Farmer1, Farmer2
    strategies Restrain, Overuse
    
    // Shared pasture: overgrazing destroys it
    
    payoff Farmer1 {
        (Restrain, Restrain): 100  // Sustainable yield
        (Restrain, Overuse): 20    // I'm cautious, they exploit
        (Overuse, Restrain): 150   // I exploit, they're cautious
        (Overuse, Overuse): 10     // Pasture collapses
    }
    
    payoff Farmer2 {
        (Restrain, Restrain): 100
        (Overuse, Restrain): 20
        (Restrain, Overuse): 150
        (Overuse, Overuse): 10
    }
}
```

**Key Insight:** Individual rationality leads to collective ruin.

---

## Voting Game

Strategic voting when there are multiple candidates.

```tenet
game StrategicVoting {
    players Voter1, Voter2
    strategies VoteA, VoteB, VoteC
    
    // Three candidates: A, B, C
    // Voters have different preferences
    // Voter1 prefers: A > B > C
    // Voter2 prefers: B > C > A
    
    payoff Voter1 {
        (VoteA, VoteA): 10   // A wins
        (VoteA, VoteB): 5    // Tie or B wins
        (VoteA, VoteC): 7    // A or C wins
        (VoteB, VoteA): 5    // Tie
        (VoteB, VoteB): 7    // B wins (second choice)
        (VoteB, VoteC): 4    // B or C
        (VoteC, VoteA): 3    // A or C
        (VoteC, VoteB): 4    // B or C
        (VoteC, VoteC): 0    // C wins (worst)
    }
    
    payoff Voter2 {
        (VoteA, VoteA): 0    // A wins (worst)
        (VoteB, VoteA): 5
        (VoteC, VoteA): 3
        (VoteA, VoteB): 5
        (VoteB, VoteB): 10   // B wins (best)
        (VoteC, VoteB): 7
        (VoteA, VoteC): 3
        (VoteB, VoteC): 7
        (VoteC, VoteC): 5    // C wins
    }
}
```

---

## Climate Agreement

Nations deciding whether to reduce emissions.

```tenet
var global_benefit = 100;
var reduction_cost = 40;

game ClimateAgreement {
    players Country1, Country2
    strategies Reduce, Pollute
    
    payoff Country1 {
        (Reduce, Reduce): global_benefit - reduction_cost   // 60
        (Reduce, Pollute): global_benefit/2 - reduction_cost // 10
        (Pollute, Reduce): global_benefit/2                  // 50
        (Pollute, Pollute): 0                                // Catastrophe
    }
    
    payoff Country2 {
        (Reduce, Reduce): global_benefit - reduction_cost
        (Pollute, Reduce): global_benefit/2 - reduction_cost
        (Reduce, Pollute): global_benefit/2
        (Pollute, Pollute): 0
    }
}
```

**Challenge:** How do we escape `(Pollute, Pollute)`?

---

## Next Steps

- **[Grammar Specification →](../reference/grammar.md)** — Formal language specification
