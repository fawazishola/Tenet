# Classes

> Object-oriented programming in Tenet.

---

## Declaration

```tenet
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

---

## Instantiation

Create an instance by calling the class like a function:

```tenet
var alice = Person("Alice", 25);
```

---

## Properties

```tenet
alice.name;        // Get property: "Alice"
alice.age = 26;    // Set property
alice.job = "Dev"; // Add new property
```

---

## Methods

```tenet
alice.greet();  // Hi, I'm Alice
```

---

## The `this` Keyword

Inside methods, `this` refers to the instance:

```tenet
class Counter {
    init() {
        this.count = 0;
    }
    
    increment() {
        this.count = this.count + 1;
        return this.count;
    }
}

var c = Counter();
print c.increment();  // 1
print c.increment();  // 2
```

---

## Inheritance

Use `<` to inherit from a superclass:

```tenet
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

var dog = Dog();
dog.speak();  // Woof!
```

---

## The `super` Keyword

Call superclass methods:

```tenet
class Animal {
    init() {
        this.alive = true;
    }
}

class Dog < Animal {
    init(name) {
        super.init();  // Call parent constructor
        this.name = name;
    }
}

var buddy = Dog("Buddy");
print buddy.alive;  // true
print buddy.name;   // Buddy
```

---

## Complete Example

```tenet
class Player {
    init(name) {
        this.name = name;
        this.score = 0;
    }
    
    addPoints(points) {
        this.score = this.score + points;
        print this.name + " now has " + this.score + " points";
    }
    
    describe() {
        print "Player: " + this.name + " (Score: " + this.score + ")";
    }
}

var p1 = Player("Alice");
var p2 = Player("Bob");

p1.addPoints(10);  // Alice now has 10 points
p2.addPoints(5);   // Bob now has 5 points
p1.describe();     // Player: Alice (Score: 10)
```

---

## Next Steps

- **[Game Theory DSL](../game-theory/defining-games.md)** — This is what Tenet was built for
