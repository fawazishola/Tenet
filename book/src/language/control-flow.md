# Control Flow

> Conditionals and loops in Tenet.

---

## If Statements

```tenet
if (condition) {
    // then branch
} else {
    // else branch (optional)
}
```

### Chained Conditions

```tenet
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

---

## While Loops

```tenet
var i = 0;
while (i < 5) {
    print i;
    i = i + 1;
}
```

---

## For Loops

C-style for loops:

```tenet
for (var i = 0; i < 5; i = i + 1) {
    print i;
}
```

All three clauses are optional:

```tenet
var i = 0;
for (; i < 5;) {
    print i;
    i = i + 1;
}
```

---

## Logical Operators

### AND (Short-Circuit)

```tenet
true and true;   // true
true and false;  // false
false and true;  // false (right side not evaluated)
```

### OR (Short-Circuit)

```tenet
true or false;   // true (right side not evaluated)
false or true;   // true
false or false;  // false
```

### NOT

```tenet
!true;   // false
!false;  // true
!nil;    // true (nil is falsy)
```

---

## Short-Circuit Evaluation

```tenet
false and expensive();  // expensive() never called
true or expensive();    // expensive() never called
```

This is useful for guard conditions:

```tenet
if (user != nil and user.isAdmin) {
    // Safe: user.isAdmin only checked if user exists
}
```

---

## Comparison Operators

| Operator | Description |
|----------|-------------|
| `==` | Equal |
| `!=` | Not equal |
| `<` | Less than |
| `<=` | Less than or equal |
| `>` | Greater than |
| `>=` | Greater than or equal |

---

## Next Steps

- **[Functions →](./functions.md)** — Defining and calling functions
