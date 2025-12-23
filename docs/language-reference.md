# Tenet Language Reference

> Complete reference for the Tenet programming language.

## Table of Contents

1. [Data Types](#data-types)
2. [Operators](#operators)
3. [Variables](#variables)
4. [Control Flow](#control-flow)
5. [Functions](#functions)
6. [Classes](#classes)
7. [Game Theory DSL](#game-theory-dsl)

---

## Data Types

Tenet is dynamically typed. Values have types; variables do not.

### Numbers

All numbers are 64-bit floating point (IEEE 754 double precision).

```javascript
var x = 42;        // Integer
var pi = 3.14159;  // Decimal
var neg = -17.5;   // Negative
var sci = 1.5e10;  // Scientific notation (NOT supported yet)
```

### Strings

Strings are enclosed in double quotes. Single quotes are NOT supported.

```javascript
var greeting = "Hello, World!";
var empty = "";
var multiword = "Game Theory DSL";
```

**String operations:**
- Concatenation: `"Hello, " + "World!"`

### Booleans

Two boolean literals: `true` and `false`.

```javascript
var yes = true;
var no = false;
```

### Nil

The absence of a value.

```javascript
var nothing = nil;
```

---

## Operators

### Arithmetic Operators

| Operator | Description | Example |
|----------|-------------|---------|
| `+` | Addition | `3 + 4` → `7` |
| `-` | Subtraction | `10 - 3` → `7` |
| `*` | Multiplication | `5 * 6` → `30` |
| `/` | Division | `20 / 4` → `5` |
| `-` (unary) | Negation | `-5` → `-5` |

**Operator Precedence** (highest to lowest):
1. Unary (`-`, `!`)
2. Multiplication, Division (`*`, `/`)
3. Addition, Subtraction (`+`, `-`)
4. Comparison (`<`, `<=`, `>`, `>=`)
5. Equality (`==`, `!=`)
6. Logical AND (`and`)
7. Logical OR (`or`)

### Comparison Operators

| Operator | Description |
|----------|-------------|
| `==` | Equal |
| `!=` | Not equal |
| `<` | Less than |
| `<=` | Less than or equal |
| `>` | Greater than |
| `>=` | Greater than or equal |

### Logical Operators

| Operator | Description |
|----------|-------------|
| `and` | Logical AND (short-circuit) |
| `or` | Logical OR (short-circuit) |
| `!` | Logical NOT |

**Short-circuit evaluation:**
```javascript
false and expensive();  // expensive() never called
true or expensive();    // expensive() never called
```

---

## Variables

### Declaration

```javascript
var name = "Alice";
var age = 25;
var uninitialized;  // Value is nil
```

### Assignment

```javascript
var x = 10;
x = 20;  // Reassignment
```

### Scope

Tenet uses **lexical scoping**. Variables are visible from their declaration to the end of their enclosing block.

```javascript
var global = "I'm global";

{
    var local = "I'm local";
    print global;  // Works
    print local;   // Works
}

print global;  // Works
print local;   // Error! Not in scope
```

### Shadowing

Inner scopes can shadow outer variables:

```javascript
var x = "outer";
{
    var x = "inner";
    print x;  // "inner"
}
print x;  // "outer"
```

---

## Control Flow

### If Statements

```javascript
if (condition) {
    // then branch
} else {
    // else branch (optional)
}
```

**Chained conditions:**
```javascript
if (score >= 90) {
    print "A";
} else if (score >= 80) {
    print "B";
} else if (score >= 70) {
    print "C";
} else {
    print "F";
}
```

### While Loops

```javascript
var i = 0;
while (i < 5) {
    print i;
    i = i + 1;
}
```

### For Loops

C-style for loops:

```javascript
for (var i = 0; i < 5; i = i + 1) {
    print i;
}
```

All three clauses are optional:
```javascript
var i = 0;
for (; i < 5;) {
    print i;
    i = i + 1;
}
```

---

## Functions

### Declaration

```javascript
fun greet(name) {
    print "Hello, " + name + "!";
}
```

### Calling Functions

```javascript
greet("Alice");  // Hello, Alice!
```

### Return Values

```javascript
fun add(a, b) {
    return a + b;
}

var sum = add(3, 4);  // 7
```

Functions without `return` implicitly return `nil`.

### First-Class Functions

Functions are values and can be:
- Stored in variables
- Passed as arguments
- Returned from other functions

```javascript
fun makeGreeter(greeting) {
    fun greeter(name) {
        print greeting + ", " + name + "!";
    }
    return greeter;
}

var hello = makeGreeter("Hello");
hello("Alice");  // Hello, Alice!
```

### Closures

Functions capture their enclosing environment:

```javascript
fun makeCounter() {
    var count = 0;
    fun increment() {
        count = count + 1;
        return count;
    }
    return increment;
}

var counter = makeCounter();
print counter();  // 1
print counter();  // 2
print counter();  // 3
```

### Recursion

```javascript
fun fibonacci(n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

print fibonacci(10);  // 55
```

### Native Functions

| Function | Description |
|----------|-------------|
| `clock()` | Returns current time in seconds since epoch |

---

## Classes

### Declaration

```javascript
class Person {
    init(name, age) {
        this.name = name;
        this.age = age;
    }
    
    greet() {
        print "Hi, I'm " + this.name;
    }
}
```

### Instantiation

```javascript
var alice = Person("Alice", 25);
```

### Properties

```javascript
alice.name;        // Get property
alice.age = 26;    // Set property
alice.job = "Dev"; // Add new property
```

### Methods

```javascript
alice.greet();  // Hi, I'm Alice
```

### The `this` Keyword

Inside methods, `this` refers to the instance:

```javascript
class Counter {
    init() {
        this.count = 0;
    }
    
    increment() {
        this.count = this.count + 1;
        return this.count;
    }
}
```

### Inheritance

Use `<` to inherit from a superclass:

```javascript
class Animal {
    speak() {
        print "Some sound";
    }
}

class Dog < Animal {
    speak() {
        print "Woof!";
    }
}
```

### The `super` Keyword

Call superclass methods:

```javascript
class Dog < Animal {
    init(name) {
        super.init();  // Call parent constructor
        this.name = name;
    }
}
```

---

## Game Theory DSL

### Game Definition

```javascript
game GameName {
    players Player1, Player2
    strategies Strategy1, Strategy2
    
    payoff Player1 {
        (Strategy1, Strategy1): value
        (Strategy1, Strategy2): value
        (Strategy2, Strategy1): value
        (Strategy2, Strategy2): value
    }
    
    payoff Player2 {
        // Same structure
    }
}
```

### Players

Declare two or more players:

```javascript
players Alice, Bob
players P1, P2, P3  // Also valid
```

### Strategies

Declare available strategies (currently shared by all players):

```javascript
strategies Cooperate, Defect
strategies Rock, Paper, Scissors
```

### Payoff Matrices

Define payoffs for each player:

```javascript
payoff Alice {
    (Cooperate, Cooperate): 3
    (Cooperate, Defect): 0
    (Defect, Cooperate): 5
    (Defect, Defect): 1
}
```

**Strategy profile format:** `(MyStrategy, TheirStrategy)`

**Payoff values can be:**
- Numbers: `3`, `-1`, `0.5`
- Variables: `reward`, `punishment`
- Expressions: `reward * 2`, `cost - benefit`

### Solve Statement

Analyze a game:

```javascript
solve PrisonersDilemma;
```

Currently outputs game information. Nash equilibrium solver coming in Phase 2.

### Complete Example

```javascript
// Prisoner's Dilemma
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

## Reserved Words

The following are reserved and cannot be used as identifiers:

```
and     class    else    false   for     fun     game
if      nil      or      payoff  players print   return
solve   strategies   super   this    true    using   var
while
```

---

## Comments

Single-line comments start with `//`:

```javascript
// This is a comment
var x = 5;  // Inline comment
```

Multi-line comments are NOT supported (yet).

---

## File Extension

Tenet files use the `.tenet` extension:
- `game.tenet`
- `simulation.tenet`

The `.lox` extension is also supported for backwards compatibility.
