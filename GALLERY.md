# 🎮 Tenet Model Gallery

> A curated collection of game-theoretic models demonstrating Tenet's capabilities.

## Quick Navigation

| Category | Models | Concepts |
|----------|--------|----------|
| [🏭 Industrial Organization](#-industrial-organization) | Cournot, Bertrand, Entry Deterrence | Firm competition, pricing, market entry |
| [🧠 Behavioral Economics](#-behavioral-economics) | Ultimatum, Trust, Stag Hunt, Chicken | Fairness, trust, coordination, brinkmanship |
| [🏛️ Political Economy](#️-political-economy) | Commons, Public Goods, Arms Race | Resource management, collective action |
| [🎲 Classic Games](#-classic-games) | Prisoner's Dilemma, Matching Pennies | Fundamental game theory concepts |

---

## 🏭 Industrial Organization

### Cournot Duopoly

**File:** `examples/industrial_org/cournot_duopoly.tenet`

Two firms compete by choosing **quantities**. Total supply determines price.

```
Price = MaxPrice - (Quantity₁ + Quantity₂)
Profit = (Price - Cost) × MyQuantity
```

**The Math:**
$$\pi_i = (a - q_1 - q_2 - c) \cdot q_i$$

**Key Insight:** Both firms producing "Low" maximizes joint profit, but each is tempted to overproduce.

---

### Bertrand Competition

**File:** `examples/industrial_org/bertrand_competition.tenet`

Firms compete on **price**. Lowest price wins the entire market.

**The Bertrand Paradox:** With just two firms, competition drives prices to marginal cost—zero economic profit!

**Real-world examples:** Gas stations, airlines, online retail.

---

### Entry Deterrence

**File:** `examples/industrial_org/entry_deterrence.tenet`

A monopolist decides whether to build excess capacity to scare off potential entrants.

**Key Insight:** "Wasteful" investment can be strategically rational if it prevents competition.

---

## 🧠 Behavioral Economics

### Ultimatum Game

**File:** `examples/behavioral/ultimatum_game.tenet`

Proposer offers a split of $100. Responder accepts or rejects. If rejected, **both get nothing**.

| Prediction | Observation |
|------------|-------------|
| Accept any positive offer | Reject offers < 30% |
| Offer $1 | Offer 40-50% |

**Why it matters:** Proves humans care about fairness, not just money.

---

### Trust Game

**File:** `examples/behavioral/trust_game.tenet`

Investor sends money (which **triples**). Trustee decides how much to return.

```
Send $10 → Becomes $30 → Trustee keeps or shares
```

**Foundation of:** Banking, credit, contracts, civilization itself.

---

### Stag Hunt

**File:** `examples/behavioral/stag_hunt.tenet`

Hunt a **Stag** (high payoff, requires cooperation) or a **Hare** (low payoff, safe).

| Strategy | Stag | Hare |
|----------|------|------|
| **Stag** | (10, 10) | (0, 3) |
| **Hare** | (3, 0) | (3, 3) |

**Two equilibria:** Cooperation is stable, but so is playing it safe.

---

### Chicken (Hawk-Dove)

**File:** `examples/behavioral/chicken.tenet`

Two drivers race toward each other. Who swerves first?

**Applications:**
- Cuban Missile Crisis (1962)
- Labor negotiations
- Corporate takeovers

**The danger:** No guaranteed outcome. Miscalculation → catastrophe.

---

## 🏛️ Political Economy

### Tragedy of the Commons

**File:** `examples/political_economy/tragedy_of_commons.tenet`

Shared resources get overexploited when everyone acts selfishly.

**Real-world examples:**
- Overfishing
- Climate change
- Traffic congestion
- Antibiotic resistance

---

### Public Goods Game

**File:** `examples/political_economy/public_goods.tenet`

Contributions are doubled and shared equally. Rational strategy: **contribute nothing**.

**Why we need:** Taxes, regulations, social pressure.

---

### Arms Race

**File:** `examples/political_economy/arms_race.tenet`

Two nations spend on weapons. Both end up **poorer but equally safe**.

**The Security Dilemma:**
> "If they arm and I don't, I'm vulnerable. So I must arm too."

Result: Mutually Assured Destruction.

---

## 🎲 Classic Games

### Prisoner's Dilemma

**File:** `examples/classic_games/prisoners_dilemma.tenet`

The most studied game in game theory.

|  | Cooperate | Defect |
|---|-----------|--------|
| **Cooperate** | (3, 3) | (0, 5) |
| **Defect** | (5, 0) | (1, 1) |

**Nash Equilibrium:** (Defect, Defect) — but (Cooperate, Cooperate) is better for both!

---

### Matching Pennies

**File:** `examples/classic_games/matching_pennies.tenet`

A zero-sum game with **no pure strategy equilibrium**.

**Solution:** Randomize 50/50. 

**Applications:** Penalty kicks, poker bluffing, security patrols.

---

### Battle of the Sexes

**File:** `examples/classic_games/battle_of_sexes.tenet`

A coordination game with **two equilibria**—but which one?

**Solved by:** Communication, social norms, focal points.

---

## Running the Models

```bash
# Run any model
java -cp out com.craftinginterpreters.tenet.Tenet examples/classic_games/prisoners_dilemma.tenet

# Run the main demo
java -cp out com.craftinginterpreters.tenet.Tenet examples/demo.tenet
```

---

## Model Parameters

All models use **variables** for key parameters, making them easy to modify:

```javascript
// In Prisoner's Dilemma
var reward = 3;       // Mutual cooperation
var temptation = 5;   // Defecting when partner cooperates
var sucker = 0;       // Cooperating when partner defects
var punishment = 1;   // Mutual defection
```

Change these values to explore how equilibria shift!

---

## Coming Soon

- [ ] **Auctions:** First-price, second-price (Vickrey), all-pay
- [ ] **Signaling Games:** Education as signal (Spence)
- [ ] **Market for Lemons:** Adverse selection (Akerlof)
- [ ] **Bank Runs:** Financial panic coordination
- [ ] **Median Voter:** Political positioning

---

*Built with [Tenet](https://github.com/yourusername/tenet) — Where Game Theory Meets Programming*
