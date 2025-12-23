# Behavioral Economics

> Games involving trust, fairness, and social preferences.

---

## Ultimatum Game

One player proposes a split; the other accepts or rejects.

```tenet
game UltimatumGame {
    players Proposer, Responder
    strategies Fair, Unfair, Accept, Reject
    
    // Proposer: Fair (50-50) or Unfair (90-10)
    // Responder: Accept or Reject
    
    payoff Proposer {
        (Fair, Accept): 50     // Fair split accepted
        (Fair, Reject): 0      // Fair split rejected (spite?)
        (Unfair, Accept): 90   // Greedy split accepted
        (Unfair, Reject): 0    // Greedy split rejected
    }
    
    payoff Responder {
        (Fair, Accept): 50     // Get fair share
        (Fair, Reject): 0      // Reject fair (irrational)
        (Unfair, Accept): 10   // Accept small share
        (Unfair, Reject): 0    // Reject unfair offer
    }
}
```

**Economic prediction:** Responder accepts any positive offer.  
**Behavioral reality:** Unfair offers are often rejected.

---

## Trust Game

Player 1 can trust (send money), Player 2 can reciprocate or betray.

```tenet
game TrustGame {
    players Investor, Trustee
    strategies Trust, NoTrust, Return, Keep
    
    // Investor sends $10, it triples to $30
    // Trustee can return $15 (split) or keep all
    
    payoff Investor {
        (Trust, Return): 15    // Investment + return
        (Trust, Keep): 0       // Betrayed
        (NoTrust, Return): 10  // Kept original
        (NoTrust, Keep): 10    // Kept original
    }
    
    payoff Trustee {
        (Trust, Return): 15    // Fair split
        (Trust, Keep): 30      // Betray (keep all)
        (NoTrust, Return): 0   // Nothing to return
        (NoTrust, Keep): 0     // Nothing to keep
    }
}
```

**Nash Equilibrium:** `(NoTrust, Keep)` — but this is socially inefficient.

---

## Dictator Game

One player unilaterally decides the split.

```tenet
game DictatorGame {
    players Dictator, Recipient
    strategies Generous, Selfish, Accept, Accept2
    
    // Dictator has all power
    // Recipient can only accept
    
    payoff Dictator {
        (Generous, Accept): 50
        (Generous, Accept2): 50
        (Selfish, Accept): 100
        (Selfish, Accept2): 100
    }
    
    payoff Recipient {
        (Generous, Accept): 50
        (Generous, Accept2): 50
        (Selfish, Accept): 0
        (Selfish, Accept2): 0
    }
}
```

**Economic prediction:** Dictator keeps everything.  
**Behavioral reality:** Many dictators share 20-30%.

---

## Public Goods Game

N players decide whether to contribute to a public good.

```tenet
game PublicGoods2P {
    players Player1, Player2
    strategies Contribute, FreeRide
    
    // Each contribution costs 10, generates 8 for each player
    // Total benefit = 16, but I only get 8
    
    payoff Player1 {
        (Contribute, Contribute): 6   // 16 - 10 = 6
        (Contribute, FreeRide): -2    // 8 - 10 = -2
        (FreeRide, Contribute): 8     // Free benefit
        (FreeRide, FreeRide): 0       // No public good
    }
    
    payoff Player2 {
        (Contribute, Contribute): 6
        (FreeRide, Contribute): -2
        (Contribute, FreeRide): 8
        (FreeRide, FreeRide): 0
    }
}
```

**Nash Equilibrium:** Both free-ride — the tragedy of rational self-interest.

---

## Next Steps

- **[Political Economy →](./political-economy.md)** — Arms races, commons, and collective action
