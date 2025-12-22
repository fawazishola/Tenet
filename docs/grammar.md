# Tenet Grammar Specification

> Formal grammar for the Tenet programming language in EBNF notation.

## Notation

- `|` — alternation (choice)
- `*` — zero or more
- `+` — one or more
- `?` — optional (zero or one)
- `( )` — grouping
- `" "` — terminal (literal token)

---

## Program Structure

```ebnf
program        → declaration* EOF ;
```

---

## Declarations

```ebnf
declaration    → classDecl
               | funDecl
               | varDecl
               | gameDecl
               | statement ;

classDecl      → "class" IDENTIFIER ( "<" IDENTIFIER )? "{" function* "}" ;

funDecl        → "fun" function ;

function       → IDENTIFIER "(" parameters? ")" block ;

parameters     → IDENTIFIER ( "," IDENTIFIER )* ;

varDecl        → "var" IDENTIFIER ( "=" expression )? ";" ;
```

---

## Game Theory Declarations

```ebnf
gameDecl       → "game" IDENTIFIER "{" gameBody "}" ;

gameBody       → playersDecl strategiesDecl payoffDecl* ;

playersDecl    → "players" identifierList ;

strategiesDecl → "strategies" identifierList ;

identifierList → IDENTIFIER ( "," IDENTIFIER )* ;

payoffDecl     → "payoff" IDENTIFIER "{" payoffRule* "}" ;

payoffRule     → "(" identifierList ")" ":" payoffValue ;

payoffValue    → payoffTerm ;

payoffTerm     → payoffFactor ( ( "-" | "+" ) payoffFactor )* ;

payoffFactor   → payoffUnary ( ( "/" | "*" ) payoffUnary )* ;

payoffUnary    → "-" payoffUnary | payoffPrimary ;

payoffPrimary  → NUMBER | IDENTIFIER ;
```

---

## Statements

```ebnf
statement      → exprStmt
               | forStmt
               | ifStmt
               | printStmt
               | returnStmt
               | whileStmt
               | solveStmt
               | block ;

exprStmt       → expression ";" ;

forStmt        → "for" "(" ( varDecl | exprStmt | ";" )
                           expression? ";"
                           expression? ")" statement ;

ifStmt         → "if" "(" expression ")" statement
                 ( "else" statement )? ;

printStmt      → "print" expression ";" ;

returnStmt     → "return" expression? ";" ;

whileStmt      → "while" "(" expression ")" statement ;

solveStmt      → "solve" IDENTIFIER ( "using" IDENTIFIER )? ";" ;

block          → "{" declaration* "}" ;
```

---

## Expressions

```ebnf
expression     → assignment ;

assignment     → ( call "." )? IDENTIFIER "=" assignment
               | logic_or ;

logic_or       → logic_and ( "or" logic_and )* ;

logic_and      → equality ( "and" equality )* ;

equality       → comparison ( ( "!=" | "==" ) comparison )* ;

comparison     → term ( ( ">" | ">=" | "<" | "<=" ) term )* ;

term           → factor ( ( "-" | "+" ) factor )* ;

factor         → unary ( ( "/" | "*" ) unary )* ;

unary          → ( "!" | "-" ) unary | call ;

call           → primary ( "(" arguments? ")" | "." IDENTIFIER )* ;

arguments      → expression ( "," expression )* ;

primary        → "true" | "false" | "nil" | "this"
               | NUMBER | STRING | IDENTIFIER
               | "(" expression ")"
               | "super" "." IDENTIFIER ;
```

---

## Lexical Grammar

```ebnf
NUMBER         → DIGIT+ ( "." DIGIT+ )? ;

STRING         → "\"" <any char except "\"">* "\"" ;

IDENTIFIER     → ALPHA ( ALPHA | DIGIT )* ;

ALPHA          → "a" ... "z" | "A" ... "Z" | "_" ;

DIGIT          → "0" ... "9" ;
```

---

## Keywords

```
and        class      else       false      for
fun        game       if         nil        or
payoff     players    print      return     solve
strategies super      this       true       using
var        while
```

**Total: 22 keywords**

---

## Operators & Punctuation

### Single-Character Tokens

```
(  )  {  }  ,  .  -  +  ;  /  *  :
```

### One or Two Character Tokens

```
!   !=
=   ==
>   >=
<   <=
```

---

## Operator Precedence

From lowest to highest:

| Precedence | Operators | Associativity |
|------------|-----------|---------------|
| 1 | `or` | Left |
| 2 | `and` | Left |
| 3 | `==` `!=` | Left |
| 4 | `<` `<=` `>` `>=` | Left |
| 5 | `+` `-` | Left |
| 6 | `*` `/` | Left |
| 7 | `!` `-` (unary) | Right |
| 8 | `.` `()` (call) | Left |

---

## Comments

```ebnf
COMMENT        → "//" <any char except newline>* NEWLINE ;
```

Multi-line comments (`/* */`) are not supported.

---

## Whitespace

Whitespace (spaces, tabs, newlines) is ignored except:
- Inside strings
- To separate tokens

---

## Example: Complete Grammar in Action

```tenet
// Variable declaration
var reward = 3;

// Function declaration
fun utility(x, y) {
    return x + y;
}

// Class with inheritance
class Player < Entity {
    init(name) {
        super.init();
        this.name = name;
    }
    
    describe() {
        print "Player: " + this.name;
    }
}

// Game definition
game PrisonersDilemma {
    players Alice, Bob
    strategies Cooperate, Defect
    
    payoff Alice {
        (Cooperate, Cooperate): reward
        (Cooperate, Defect): 0
        (Defect, Cooperate): reward + 2
        (Defect, Defect): 1
    }
    
    payoff Bob {
        (Cooperate, Cooperate): reward
        (Defect, Cooperate): 0
        (Cooperate, Defect): reward + 2
        (Defect, Defect): 1
    }
}

// Solve statement
solve PrisonersDilemma;

// Control flow
if (reward > 2) {
    print "High reward";
} else {
    print "Low reward";
}

// Loop
for (var i = 0; i < 5; i = i + 1) {
    print i;
}
```
