# Axiom Ecosystem

> Tenet is one component of a larger computational stack.

---

## The Stack

```
┌─────────────────────────────────────────┐
│              Alexthia                    │
│     Neurosymbolic Reasoning LLM         │
├─────────────────────────────────────────┤
│         Axiom OS                         │
│   AI-Native Math Operating System        │
├─────────────────────────────────────────┤
│      Flux          │      Tenet          │
│  Math-First Lang   │  Game Theory DSL    │
└─────────────────────────────────────────┘
```

---

## Components

### Tenet (You Are Here)

**Purpose:** Game theory modeling and analysis

**Key Features:**
- Clean syntax for game definitions
- Payoff matrix specification
- Nash equilibrium solver
- Strategy simulation

**Use Cases:**
- Academic research
- Economic modeling
- Strategic analysis
- Decision theory

---

### Flux

**Purpose:** Math-first programming language

**Philosophy:** Mathematics should be a first-class citizen, not an afterthought.

**Key Features:**
- Native mathematical notation
- Symbolic computation
- Automatic differentiation
- Numerical optimization

**Integration with Tenet:**
```ruby
# In Flux
export utility(x, y) = x^2 - 2*x*y + y^2

# In Tenet
import "utility.flx"

game CustomGame {
    players A, B
    strategies Low, High
    
    payoff A {
        (Low, Low): utility(1, 1)
        (Low, High): utility(1, 2)
        (High, Low): utility(2, 1)
        (High, High): utility(2, 2)
    }
}
```

---

### Axiom OS

**Purpose:** Neurosymbolic AI-native math operating system

**Vision:** A computational environment where:
- Symbolic reasoning meets neural computation
- Mathematical objects are first-class
- AI assists at every level
- Formal verification is built-in

---

### Alexthia

**Purpose:** Reasoning LLM for the Axiom stack

**Capabilities:**
- Understands Tenet, Flux, and Axiom
- Can generate game theory models
- Assists with mathematical reasoning
- Provides proof assistance

---

## Philosophy

The Axiom ecosystem is built on a core belief:

> **The future of computing is neurosymbolic.**
>
> Pure neural networks lack precision.  
> Pure symbolic systems lack flexibility.  
> The combination unlocks new possibilities.

Each component of the stack embodies this principle:
- **Tenet** provides symbolic precision for strategic reasoning
- **Flux** provides symbolic power for mathematical expression
- **Axiom** provides the computational substrate
- **Alexthia** provides the neural intelligence

---

## Learn More

- [Flux Repository](https://github.com/axiom/flux)
- [Axiom Project](https://github.com/axiom/axiom-os)
- [Alexthia Research](https://github.com/axiom/alexthia)
