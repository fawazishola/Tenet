# Functions

> Define and call reusable code blocks.

---

## Declaration

```tenet
fun greet(name) {
    print "Hello, " + name + "!";
}
```

Call with parentheses:

```tenet
greet("Alice");  // Hello, Alice!
```

---

## Return Values

```tenet
fun add(a, b) {
    return a + b;
}

var sum = add(3, 4);  // 7
```

Functions without `return` implicitly return `nil`.

---

## First-Class Functions

Functions are values and can be:

- Stored in variables
- Passed as arguments
- Returned from other functions

```tenet
fun makeGreeter(greeting) {
    fun greeter(name) {
        print greeting + ", " + name + "!";
    }
    return greeter;
}

var hello = makeGreeter("Hello");
hello("Alice");  // Hello, Alice!
```

---

## Closures

Functions capture their enclosing environment:

```tenet
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

Each call to `makeCounter()` creates a new, independent counter.

---

## Recursion

Functions can call themselves:

```tenet
fun fibonacci(n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

print fibonacci(10);  // 55
```

---

## Native Functions

Built-in functions:

| Function | Description |
|----------|-------------|
| `clock()` | Returns current time in seconds since epoch |

```tenet
var start = clock();
// ... some computation ...
var elapsed = clock() - start;
print "Elapsed: " + elapsed + " seconds";
```

---

## Next Steps

- **[Classes →](./classes.md)** — Object-oriented programming
