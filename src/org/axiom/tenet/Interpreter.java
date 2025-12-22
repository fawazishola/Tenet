package org.axiom.tenet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Interpreter implements Expr.Visitor<Object>, Stmt.Visitor<Void> {

    final Environment globals = new Environment();
    private Environment environment = globals;
    private final Map<Expr, Integer> locals = new HashMap<>();

    Interpreter() {
        // Native function: clock()
        globals.define("clock", new TenetCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return (double) System.currentTimeMillis() / 1000.0;
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: random() - returns 0.0 to 1.0
        globals.define("random", new TenetCallable() {
            @Override
            public int arity() {
                return 0;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                return Math.random();
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: max(a, b)
        globals.define("max", new TenetCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double a = ((Number) arguments.get(0)).doubleValue();
                double b = ((Number) arguments.get(1)).doubleValue();
                return Math.max(a, b);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: min(a, b)
        globals.define("min", new TenetCallable() {
            @Override
            public int arity() {
                return 2;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double a = ((Number) arguments.get(0)).doubleValue();
                double b = ((Number) arguments.get(1)).doubleValue();
                return Math.min(a, b);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: abs(x)
        globals.define("abs", new TenetCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double x = ((Number) arguments.get(0)).doubleValue();
                return Math.abs(x);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: sqrt(x)
        globals.define("sqrt", new TenetCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double x = ((Number) arguments.get(0)).doubleValue();
                return Math.sqrt(x);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: floor(x)
        globals.define("floor", new TenetCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double x = ((Number) arguments.get(0)).doubleValue();
                return Math.floor(x);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: ceil(x)
        globals.define("ceil", new TenetCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double x = ((Number) arguments.get(0)).doubleValue();
                return Math.ceil(x);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });

        // Native function: round(x)
        globals.define("round", new TenetCallable() {
            @Override
            public int arity() {
                return 1;
            }

            @Override
            public Object call(Interpreter interpreter, List<Object> arguments) {
                double x = ((Number) arguments.get(0)).doubleValue();
                return (double) Math.round(x);
            }

            @Override
            public String toString() {
                return "<native fn>";
            }
        });
    }

    void interpret(List<Stmt> statements) {
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeError error) {
            Tenet.runtimeError(error);
        }
    }

    private void execute(Stmt stmt) {
        stmt.accept(this);
    }

    @Override
    public Object visitLiteralExpr(Expr.Literal expr) {
        return expr.value;
    }

    @Override
    public Object visitCallExpr(Expr.Call expr) {
        Object callee = evaluate(expr.callee);

        List<Object> arguments = new ArrayList<>();
        for (Expr argument : expr.arguments) {
            arguments.add(evaluate(argument));
        }

        if (!(callee instanceof TenetCallable)) {
            throw new RuntimeError(expr.paren, "can only call functions and classes");
        }
        TenetCallable function = (TenetCallable) callee;

        if (arguments.size() != function.arity()) {
            throw new RuntimeError(expr.paren,
                    "Expected" + function.arity() + " arguments but got" + arguments.size() + ".");
        }

        return function.call(this, arguments);
    }

    @Override
    public Object visitGroupingExpr(Expr.Grouping expr) {
        return evaluate(expr.expression);
    }

    private Object evaluate(Expr expr) {
        return expr.accept(this);
    }

    @Override
    public Object visitBinaryExpr(Expr.Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case GREATER:
                checkNumberOperands(expr.operator, left, right);
                return (double) left > (double) right;
            case GREATER_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left >= (double) right;
            case LESS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left < (double) right;
            case LESS_EQUAL:
                checkNumberOperands(expr.operator, left, right);
                return (double) left <= (double) right;
            case MINUS:
                checkNumberOperands(expr.operator, left, right);
                return (double) left - (double) right;
            case SLASH:
                checkNumberOperands(expr.operator, left, right);
                return (double) left / (double) right;
            case STAR:
                checkNumberOperands(expr.operator, left, right);
                return (double) left * (double) right;
            case PLUS:
                if (left instanceof Double && right instanceof Double) {
                    return (double) left + (double) right;
                }
                if (left instanceof String && right instanceof String) {
                    return (String) left + (String) right;
                }
                throw new RuntimeError(expr.operator, "Operands must be two numbers or two strings.");
            case BANG_EQUAL:
                return !isEqual(left, right);
            case EQUAL_EQUAL:
                return isEqual(left, right);
        }

        return null;
    }

    @Override
    public Object visitUnaryExpr(Expr.Unary expr) {
        Object right = evaluate(expr.right);

        switch (expr.operator.type) {
            case MINUS:
                checkNumberOperand(expr.operator, right);
                return -(double) right;
            case BANG:
                return !isTruthy(right);
        }

        return null;
    }

    @Override
    public Object visitVariableExpr(Expr.Variable expr) {
        return lookUpVariable(expr.name, expr);
    }

    @Override
    public Void visitExpressionStmt(Stmt.Expression stmt) {
        evaluate(stmt.expression);

        return null;
    }

    @Override
    public Void visitIfStmt(Stmt.If stmt) {
        if (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.thenBranch);
        } else if (stmt.elseBranch != null) {
            execute(stmt.elseBranch);
        }
        return null;
    }

    @Override
    public Void visitPrintStmt(Stmt.Print stmt) {
        Object value = evaluate(stmt.expression);
        System.out.println(stringify(value));
        return null;
    }

    @Override
    public Void visitVarStmt(Stmt.Var stmt) {
        Object value = null;
        if (stmt.initializer != null) {
            value = evaluate(stmt.initializer);
        }

        environment.define(stmt.name.lexeme, value);
        return null;

    }

    @Override
    public Void visitWhileStmt(Stmt.While stmt) {
        while (isTruthy(evaluate(stmt.condition))) {
            execute(stmt.body);
        }

        return null;
    }

    @Override
    public Object visitAssignExpr(Expr.Assign expr) {
        Object value = evaluate(expr.value);
        environment.assign(expr.name, value);
        return value;
    }

    @Override
    public Void visitBlockStmt(Stmt.Block stmt) {
        executeBlock(stmt.statements, new Environment(environment));
        return null;
    }

    @Override
    public Void visitFunctionStmt(Stmt.Function stmt) {
        TenetFunction function = new TenetFunction(stmt, environment, false);
        environment.define(stmt.name.lexeme, function);
        return null;
    }

    @Override
    public Void visitClassStmt(Stmt.Class stmt) {
        Object superclass = null;
        if (stmt.superclass != null) {
            superclass = evaluate(stmt.superclass);
            if (!(superclass instanceof TenetClass)) {
                throw new RuntimeError(stmt.superclass.name, "Superclass must be a class.");
            }
        }

        environment.define(stmt.name.lexeme, null);

        if (stmt.superclass != null) {
            environment = new Environment(environment);
            environment.define("super", superclass);
        }

        Map<String, TenetFunction> methods = new HashMap<>();
        for (Stmt.Function method : stmt.methods) {
            TenetFunction function = new TenetFunction(method, environment,
                    method.name.lexeme.equals("init"));
            methods.put(method.name.lexeme, function);
        }

        TenetClass klass = new TenetClass(stmt.name.lexeme, (TenetClass) superclass, methods);

        if (superclass != null) {
            environment = environment.enclosing;
        }

        environment.assign(stmt.name, klass);
        return null;
    }

    @Override
    public Object visitGetExpr(Expr.Get expr) {
        Object object = evaluate(expr.object);
        if (object instanceof TenetInstance) {
            return ((TenetInstance) object).get(expr.name);
        }
        throw new RuntimeError(expr.name, "Only instances have properties.");
    }

    @Override
    public Object visitSetExpr(Expr.Set expr) {
        Object object = evaluate(expr.object);

        if (!(object instanceof TenetInstance)) {
            throw new RuntimeError(expr.name, "Only instances have fields.");
        }

        Object value = evaluate(expr.value);
        ((TenetInstance) object).set(expr.name, value);
        return value;
    }

    @Override
    public Object visitThisExpr(Expr.This expr) {
        return lookUpVariable(expr.keyword, expr);
    }

    @Override
    public Object visitSuperExpr(Expr.Super expr) {
        int distance = locals.get(expr);
        TenetClass superclass = (TenetClass) environment.getAt(distance, "super");
        TenetInstance object = (TenetInstance) environment.getAt(distance - 1, "this");
        TenetFunction method = superclass.findMethod(expr.method.lexeme);
        if (method == null) {
            throw new RuntimeError(expr.method, "Undefined property '" + expr.method.lexeme + "'.");
        }
        return method.bind(object);
    }

    @Override
    public Void visitReturnStmt(Stmt.Return stmt) {
        Object value = null;
        if (stmt.value != null)
            value = evaluate(stmt.value);

        throw new Return(value);
    }

    @Override
    public Object visitLogicalExpr(Expr.Logical expr) {
        Object left = evaluate(expr.left);

        if (expr.operator.type == TokenType.OR) {
            if (isTruthy(left))
                return left;

        } else {
            if (!isTruthy(left))
                return left;
        }

        return evaluate(expr.right);
    }

    void executeBlock(List<Stmt> statements, Environment environment) {
        Environment previous = this.environment;

        try {
            this.environment = environment;

            for (Stmt statement : statements) {
                execute(statement);
            }

        } finally {
            this.environment = previous;
        }
    }

    private void checkNumberOperand(Token operator, Object operand) {
        if (operand instanceof Double)
            return;
        throw new RuntimeError(operator, "Operand must be a number.");
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double)
            return;
        throw new RuntimeError(operator, "Operands must be numbers.");
    }

    private boolean isTruthy(Object object) {
        if (object == null)
            return false;
        if (object instanceof Boolean)
            return (Boolean) object;
        return true;
    }

    private boolean isEqual(Object left, Object right) {
        if (left == null && right == null)
            return true;
        if (left == null)
            return false;
        return left.equals(right);
    }

    private String stringify(Object object) {
        if (object == null)
            return "nil";

        if (object instanceof Double) {
            String text = object.toString();
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            return text;
        }

        return object.toString();
    }

    void resolve(Expr expr, int depth) {
        locals.put(expr, depth);
    }

    private Object lookUpVariable(Token name, Expr expr) {
        Integer distance = locals.get(expr);
        if (distance != null) {
            return environment.getAt(distance, name.lexeme);
        } else {
            return globals.get(name);
        }
    }

    // Game theory: store game definition in environment
    @Override
    public Void visitGameStmt(Stmt.Game stmt) {
        GameValue game = new GameValue(
                stmt.name.lexeme,
                stmt.players,
                stmt.strategies,
                stmt.payoffs);
        environment.define(stmt.name.lexeme, game);
        return null;
    }

    // Game theory: solve statement (placeholder for now)
    @Override
    public Void visitSolveStmt(Stmt.Solve stmt) {
        Object game = environment.get(stmt.gameName);
        if (!(game instanceof GameValue)) {
            throw new RuntimeError(stmt.gameName, "Can only solve games.");
        }
        GameValue gameValue = (GameValue) game;
        System.out.println("═══════════════════════════════════════");
        System.out.println("Game: " + gameValue.name);
        System.out.println("Players: " + gameValue.getPlayersString());
        System.out.println("Strategies: " + gameValue.getStrategiesString());
        System.out.println("═══════════════════════════════════════");
        // TODO: Implement Nash equilibrium solver in Phase 2
        return null;
    }
}
