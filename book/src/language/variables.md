# Variables & Scope

> How to declare, assign, and scope variables in Tenet.

---

## Declaration

Use `var` to declare a variable:

```tenet
var name = "Alice";
var age = 25;
var score;  // Uninitialized = nil
```

---

## Assignment

Reassign with `=`:

```tenet
var x = 10;
x = 20;  // Reassignment
print x; // 20
```

---

## Scope

Tenet uses **lexical scoping**. Variables are visible from their declaration to the end of their enclosing block.

```tenet
var global = "I'm global";

{
    var local = "I'm local";
    print global;  // Works
    print local;   // Works
}

print global;  // Works
print local;   // Error! Not in scope
```

---

## Shadowing

Inner scopes can shadow outer variables:

```tenet
var x = "outer";
{
    var x = "inner";
    print x;  // "inner"
}
print x;  // "outer"
```

The inner `x` is a completely separate variable that "shadows" the outer one.

---

## Block Scope

Blocks are created with `{ }`:

```tenet
var a = "outside";
{
    var a = "inside";
    {
        var a = "deep inside";
        print a;  // "deep inside"
    }
    print a;  // "inside"
}
print a;  // "outside"
```

---

## Variables in Loops

Variables declared in a `for` loop are scoped to the loop:

```tenet
for (var i = 0; i < 3; i = i + 1) {
    print i;
}
// print i;  // Error! i is not in scope
```

---

## Next Steps

- **[Control Flow →](./control-flow.md)** — Conditionals and loops
