# Reserved Words

> Words that cannot be used as identifiers.

---

## Complete List

The following words are reserved and cannot be used as variable names, function names, player names, or strategy names:

```
and        class      else       false      for
fun        game       if         nil        or
payoff     players    print      return     solve
strategies super      this       true       using
var        while
```

---

## Future Reserved Words

These words may be reserved in future versions:

```
break      continue   import     export     
match      case       enum       struct
async      await      yield      from
```

Using these as identifiers is discouraged.

---

## Valid Identifiers

Identifiers must:
- Start with a letter (`a-z`, `A-Z`) or underscore (`_`)
- Contain only letters, digits (`0-9`), or underscores
- Not be a reserved word

### Examples

| Identifier | Valid? |
|------------|--------|
| `player1` | ✅ |
| `_private` | ✅ |
| `MyGame` | ✅ |
| `nash_equilibrium` | ✅ |
| `1stPlayer` | ❌ (starts with digit) |
| `player-one` | ❌ (hyphen not allowed) |
| `class` | ❌ (reserved word) |
| `print` | ❌ (reserved word) |

---

## Naming Conventions

| Element | Convention | Example |
|---------|------------|---------|
| Variables | camelCase | `myScore` |
| Functions | camelCase | `calculatePayoff` |
| Classes | PascalCase | `GameSolver` |
| Games | PascalCase | `PrisonersDilemma` |
| Players | PascalCase | `Alice`, `Firm1` |
| Strategies | PascalCase | `Cooperate`, `Defect` |
| Constants | UPPER_SNAKE | `MAX_ROUNDS` |
