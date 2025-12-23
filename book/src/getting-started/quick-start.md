# Quick Start

> Write and run your first Tenet program in 5 minutes.

---

## The REPL

Start the interactive interpreter:

```bash
.\tenet
```

Try some expressions:

```
Tenet v0.1.0
>>> 2 + 2
4
>>> "Hello, " + "World!"
Hello, World!
>>> 10 > 5
true
```

---

## Variables

```tenet
var x = 10;
var name = "Alice";
var isPlaying = true;

print x;        // 10
print name;     // Alice
print isPlaying; // true
```

---

## Functions

```tenet
fun greet(name) {
    print "Hello, " + name + "!";
}

greet("Bob");  // Hello, Bob!
```

Functions can return values:

```tenet
fun add(a, b) {
    return a + b;
}

var sum = add(3, 4);
print sum;  // 7
```

---

## Control Flow

```tenet
var score = 85;

if (score >= 90) {
    print "A";
} else if (score >= 80) {
    print "B";
} else {
    print "C";
}
// Output: B
```

Loops:

```tenet
for (var i = 0; i < 5; i = i + 1) {
    print i;
}
// Output: 0, 1, 2, 3, 4
```

---

## Your First Game

This is what Tenet was built for:

```tenet
game RockPaperScissors {
    players Player1, Player2
    strategies Rock, Paper, Scissors
    
    payoff Player1 {
        (Rock, Rock): 0
        (Rock, Paper): -1
        (Rock, Scissors): 1
        (Paper, Rock): 1
        (Paper, Paper): 0
        (Paper, Scissors): -1
        (Scissors, Rock): -1
        (Scissors, Paper): 1
        (Scissors, Scissors): 0
    }
    
    payoff Player2 {
        (Rock, Rock): 0
        (Paper, Rock): -1
        (Scissors, Rock): 1
        (Rock, Paper): 1
        (Paper, Paper): 0
        (Scissors, Paper): -1
        (Rock, Scissors): -1
        (Paper, Scissors): 1
        (Scissors, Scissors): 0
    }
}

solve RockPaperScissors;
```

Save this as `rps.tenet` and run:

```bash
.\tenet rps.tenet
```

---

## Next Steps

- **[Your First Game →](./first-game.md)** — Deep dive into the Prisoner's Dilemma
- **[Language Guide](../language/data-types.md)** — Learn all language features
- **[Game Theory DSL](../game-theory/defining-games.md)** — Master game definitions
