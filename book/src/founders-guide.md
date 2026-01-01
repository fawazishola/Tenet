# 🚀 Tenet for Founders: Strategic Modeling for Startups

> **Game theory isn't just for economists.** Every startup decision — pricing, partnerships, platform strategy, competitive positioning — is a strategic game. Tenet helps you see the equilibria before your competitors do.

---

## Why Game Theory for Startups?

Most founders make decisions based on:
- Gut instinct
- Copying competitors
- Investor advice

But every market is a **game with multiple players**, each with their own incentives. The winners are founders who understand:
- **What equilibrium will the market settle into?**
- **How can I shift that equilibrium in my favor?**

Tenet makes this reasoning explicit, testable, and shareable with your team.

---

## Case Study 1: LinkedIn — The Network Effect Trap

### The Problem (2002)

Reid Hoffman faced the classic chicken-and-egg problem:
- LinkedIn is only valuable if your professional contacts are there
- But early users have no contacts on the platform yet
- So why would anyone join?

### The Tenet Model

```tenet
game NetworkBootstrap {
    players EarlyAdopter, LateAdopter
    strategies JoinNow, WaitAndSee
    
    payoff EarlyAdopter {
        (JoinNow, JoinNow): 100     // Network effect kicks in!
        (JoinNow, WaitAndSee): -20  // Lonely, no value
        (WaitAndSee, JoinNow): 50   // Free-ride on their work
        (WaitAndSee, WaitAndSee): 0 // No network exists
    }
    
    payoff LateAdopter {
        (JoinNow, JoinNow): 100
        (WaitAndSee, JoinNow): 50
        (JoinNow, WaitAndSee): -20
        (WaitAndSee, WaitAndSee): 0
    }
}

solve NetworkBootstrap;
```

### Analysis

**Two Nash Equilibria exist:**
1. `(JoinNow, JoinNow)` → Everyone joins, network thrives
2. `(WaitAndSee, WaitAndSee)` → Nobody joins, network dies

Both are stable! The market could go either way.

### Reid's Intervention

Reid didn't just launch and hope. He:
1. **Seeded the network** with 13 influential Silicon Valley contacts
2. **Created artificial scarcity** (invite-only)
3. **Made joining a status signal**

He manually pushed users toward the good equilibrium.

**Lesson:** When two equilibria exist, the founder's job is to **coordinate** the market toward the better one.

---

## Case Study 2: Uber — Fighting Incumbents (Taxis)

### The Problem (2010)

Travis Kalanick knew Uber was better than taxis. But:
- Riders were scared to use a stranger's car
- Drivers were skeptical of a "startup" vs. stable taxi licensing
- Regulators were hostile

### The Tenet Model

```tenet
game RiderAdoption {
    players Rider, UberDriver
    strategies TryUber, StickWithTaxi, DriveForUber, StayTraditional
    
    payoff Rider {
        (TryUber, DriveForUber): 90      // Fast, cheap, convenient
        (TryUber, StayTraditional): -50  // Tried app, no drivers available
        (StickWithTaxi, DriveForUber): 30 // Missed opportunity
        (StickWithTaxi, StayTraditional): 40 // Status quo
    }
    
    payoff UberDriver {
        (TryUber, DriveForUber): 80      // Full time Uber income
        (TryUber, StayTraditional): 0    // No passengers to drive
        (StickWithTaxi, DriveForUber): 20 // Driving empty
        (StickWithTaxi, StayTraditional): 50 // Stable taxi job
    }
}

// INSIGHT: (TryUber, StayTraditional) = disaster for riders
// INSIGHT: Must guarantee driver supply BEFORE rider adoption
```

### Uber's Intervention

1. **Subsidized drivers** heavily in new markets (guaranteed $XX/hour)
2. **Locked in supply** before promoting to riders
3. **Created urgency** for drivers (bonuses for early adopters)

**Lesson:** In two-sided markets, subsidize the constrained side first to break the coordination problem.

---

## Case Study 3: Airbnb — Trust Between Strangers

### The Problem (2008)

Brian Chesky had to convince:
- **Hosts** to let strangers sleep in their homes
- **Guests** to sleep in strangers' homes

Both sides faced significant risk.

### The Tenet Model

```tenet
var host_risk = 50;   // Damage, theft, liability
var guest_risk = 30;  // Scam, safety, discomfort

game TrustGame {
    players Host, Guest
    strategies List, DontList, Book, DontBook
    
    payoff Host {
        (List, Book): 100 - host_risk     // Revenue minus risk
        (List, DontBook): -10             // Listed but empty
        (DontList, Book): 0               // Not on platform
        (DontList, DontBook): 0           // Status quo
    }
    
    payoff Guest {
        (List, Book): 80 - guest_risk      // Great stay minus risk
        (List, DontBook): 0                // Chose not to book
        (DontList, Book): -100             // Wanted to book, nothing available
        (DontList, DontBook): 0            // Uses hotel instead
    }
}

// INSIGHT: High risk values make (DontList, DontBook) the equilibrium
// INSIGHT: Reduce perceived risk to shift equilibrium
```

### Airbnb's Intervention

1. **Professional photography** (reduced guest uncertainty)
2. **Review system** (reputation as trust proxy)
3. **Host guarantee** ($1M insurance, reduced host risk)
4. **Verified ID** (reduced both sides' risk)

Each intervention mathematically reduced `host_risk` and `guest_risk`, shifting the equilibrium toward `(List, Book)`.

**Lesson:** When trust is the barrier, invest in mechanisms that asymmetrically reduce risk for both sides.

---

## Case Study 4: Netflix — Disrupting Blockbuster

### The Problem (1997)

Reed Hastings knew DVDs-by-mail could beat Blockbuster stores. But:
- Blockbuster had brand recognition
- Customers were habituated to stores
- Switching costs seemed high

### The Tenet Model

```tenet
game StreamingWar {
    players Netflix, Blockbuster
    strategies Innovate, DefendStores, Switch, StayLoyal
    
    payoff Netflix {
        (Innovate, Switch): 100          // Captured customer
        (Innovate, StayLoyal): 10        // Built tech, waiting
        // Netflix doesn't have a "DefendStores" option
    }
    
    payoff Blockbuster {
        (Innovate, Switch): -50          // Lost to Netflix
        (Innovate, StayLoyal): 80        // Kept customer despite threat
        (DefendStores, Switch): -100     // Lost without trying
        (DefendStores, StayLoyal): 100   // Status quo
    }
}

// INSIGHT: Blockbuster's optimal strategy is DefendStores
// (because their stores are sunk costs)
// INSIGHT: This creates an opening for Netflix to capture switchers
```

### The "Innovator's Dilemma" in Game Theory

Blockbuster was **rationally** choosing `DefendStores`:
- Their assets were in physical stores
- Cannibalization of stores = destroying shareholder value
- Short-term: defending made sense

But Netflix exploited this predictable behavior.

**Lesson:** Incumbents are often trapped by their own rational incentives. Disruptors can model this and exploit the gap.

---

## Case Study 5: Stripe — The Integration Strategy

### The Problem (2010)

Patrick Collison knew payments were broken. But:
- PayPal was dominant
- Banks were gatekeepers
- Developers didn't trust new payment APIs

### The Tenet Model

```tenet
game DeveloperAdoption {
    players Developer, Stripe, PayPal
    strategies IntegrateStripe, StayPayPal, Improve, StayComplacent
    
    payoff Developer {
        (IntegrateStripe, Improve): 80      // Great API, PayPal catches up
        (IntegrateStripe, StayComplacent): 100 // Stripe dominates
        (StayPayPal, Improve): 70           // PayPal improves
        (StayPayPal, StayComplacent): 50    // Status quo, painful
    }
    
    payoff Stripe {
        (IntegrateStripe, Improve): 50      // Won customer, more competition
        (IntegrateStripe, StayComplacent): 100 // Won market
        (StayPayPal, Improve): 0            // Lost
        (StayPayPal, StayComplacent): 10    // Still chance
    }
}

// INSIGHT: Stripe wins if they can make integration SO EASY
// that switching cost < improvement benefit
```

### Stripe's Intervention

1. **7-line integration** (famous "just add stripe.js")
2. **Developer-first** (best docs in fintech)
3. **Instant approval** (no bank bureaucracy)

By making `IntegrateStripe` essentially costless, they shifted the equilibrium entirely.

**Lesson:** When competing against an incumbent, reduce your side of the switching cost to near-zero.

---

## The Founder's Game Theory Toolkit

### Step 1: Identify the Players

Who has agency in your market?
- Users (different segments?)
- Competitors (incumbents? other startups?)
- Suppliers / Partners
- Regulators

### Step 2: Map Their Strategies

What can each player choose to do?
- Often binary (adopt/don't, fight/cooperate)
- Sometimes multiple levels (price high/medium/low)

### Step 3: Quantify Payoffs

What does each outcome mean for each player?
- Revenue / profit
- Risk / uncertainty
- Status / reputation
- Option value (future flexibility)

### Step 4: Find the Equilibrium

Where will the market naturally settle?
- Is there one equilibrium or multiple?
- Is it the one you want?

### Step 5: Design Interventions

How can you shift the equilibrium?
- Subsidies (Uber drivers)
- Trust mechanisms (Airbnb reviews)
- Reducing friction (Stripe's 7 lines)
- Coordination (LinkedIn's invite-only)

---

## Template: Model Your Own Startup

```tenet
// Replace with your actual players and strategies
var switching_cost = 30;
var your_value_add = 50;

game YourStartup {
    players Customer, Incumbent
    strategies SwitchToYou, StayWithIncumbent, Fight, Ignore
    
    payoff Customer {
        (SwitchToYou, Ignore): your_value_add - switching_cost
        (SwitchToYou, Fight): your_value_add - switching_cost - 10
        (StayWithIncumbent, Ignore): 0
        (StayWithIncumbent, Fight): -5
    }
    
    payoff Incumbent {
        (SwitchToYou, Ignore): -100  // Lost customer
        (SwitchToYou, Fight): -80    // Fought but lost
        (StayWithIncumbent, Ignore): 100
        (StayWithIncumbent, Fight): 90
    }
}

solve YourStartup;
// Ask yourself:
// 1. What's the current equilibrium?
// 2. What intervention shifts it in my favor?
// 3. What's my "unfair advantage" in this game?
```

---

## Key Takeaways for Founders

| Insight | Example |
|---------|---------|
| **Multiple equilibria exist** — your job is to coordinate toward the good one | LinkedIn's invite-only launch |
| **Two-sided markets need supply-side subsidies** | Uber paying drivers guaranteed wages |
| **Trust is quantifiable** — reduce risk asymmetrically | Airbnb's host guarantee |
| **Incumbents are rationally trapped** | Blockbuster defending stores |
| **Reduce your switching cost to zero** | Stripe's 7-line integration |

---

## What's Next?

- **[Comparison: Tenet vs Python](./comparison.md)** — See the signal-to-noise advantage
- **[AI Safety Vision](./ai-safety-vision.md)** — How Tenet helps LLMs reason strategically
- **[Classic Games](./gallery/classic-games.md)** — The foundations of game theory

---

> *"In game theory, there's no such thing as 'luck.' There's only understanding the payoff matrix better than your opponent."*
