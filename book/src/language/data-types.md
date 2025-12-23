# Data Types

> Tenet is dynamically typed. Values have types; variables do not.

---

## Numbers

All numbers are 64-bit floating point (IEEE 754 double precision).

```tenet
var x = 42;        // Integer
var pi = 3.14159;  // Decimal
var neg = -17.5;   // Negative
```

<div class="info">
<strong>Note:</strong> Scientific notation (e.g., <code>1.5e10</code>) is not yet supported.
</div>

---

## Strings

Strings are enclosed in double quotes. Single quotes are **not** supported.

```tenet
var greeting = "Hello, World!";
var empty = "";
var multiword = "Game Theory DSL";
```

### String Concatenation

Use `+` to join strings:

```tenet
var full = "Hello, " + "World!";
print full;  // Hello, World!
```

---

## Booleans

Two boolean literals: `true` and `false`.

```tenet
var yes = true;
var no = false;
```

### Truthiness

When used in conditionals:

- `false` and `nil` are **falsy**
- Everything else is **truthy** (including `0` and `""`)

```tenet
if (0) {
    print "zero is truthy";  // This runs!
}
```

---

## Nil

The absence of a value.

```tenet
var nothing = nil;
var uninitialized;  // Also nil
```

---

## Type Summary

| Type | Examples | Description |
|------|----------|-------------|
| **Number** | `42`, `3.14`, `-5` | 64-bit floating point |
| **String** | `"hello"`, `""` | Text in double quotes |
| **Boolean** | `true`, `false` | Logical values |
| **Nil** | `nil` | Absence of value |

---

## Next Steps

- **[Variables & Scope →](./variables.md)** — How to use variables
