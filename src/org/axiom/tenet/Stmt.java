package org.axiom.tenet;

import java.util.List;

abstract class Stmt {
    interface Visitor<R> {
        R visitBlockStmt(Block stmt);

        R visitClassStmt(Class stmt);

        R visitExpressionStmt(Expression stmt);

        R visitFunctionStmt(Function stmt);

        R visitGameStmt(Game stmt);

        R visitIfStmt(If stmt);

        R visitImportStmt(Import stmt);

        R visitPrintStmt(Print stmt);

        R visitReturnStmt(Return stmt);

        R visitSequentialGameStmt(SequentialGame stmt);

        R visitSolveStmt(Solve stmt);

        R visitTweakStmt(Tweak stmt);

        R visitVarStmt(Var stmt);

        R visitVisualizeStmt(Visualize stmt);

        R visitWhileStmt(While stmt);
    }

    static class Block extends Stmt {
        Block(List<Stmt> statements) {
            this.statements = statements;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }

        final List<Stmt> statements;
    }

    static class Class extends Stmt {
        Class(Token name, Expr.Variable superclass, List<Stmt.Function> methods) {
            this.name = name;
            this.superclass = superclass;
            this.methods = methods;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitClassStmt(this);
        }

        final Token name;
        final Expr.Variable superclass;
        final List<Stmt.Function> methods;
    }

    static class Expression extends Stmt {
        Expression(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        final Expr expression;
    }

    static class Function extends Stmt {
        Function(Token name, List<Token> params, List<Stmt> body) {
            this.name = name;
            this.params = params;
            this.body = body;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        final Token name;
        final List<Token> params;
        final List<Stmt> body;
    }

    static class If extends Stmt {
        If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitIfStmt(this);
        }

        final Expr condition;
        final Stmt thenBranch;
        final Stmt elseBranch;
    }

    static class Print extends Stmt {
        Print(Expr expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrintStmt(this);
        }

        final Expr expression;
    }

    static class Return extends Stmt {
        Return(Token keyword, Expr value) {
            this.keyword = keyword;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }

        final Token keyword;
        final Expr value;
    }

    static class Var extends Stmt {
        Var(Token name, Expr initializer) {
            this.name = name;
            this.initializer = initializer;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVarStmt(this);
        }

        final Token name;
        final Expr initializer;
    }

    static class While extends Stmt {
        While(Expr condition, Stmt body) {
            this.condition = condition;
            this.body = body;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitWhileStmt(this);
        }

        final Expr condition;
        final Stmt body;
    }

    // Game theory: Game declaration
    static class Game extends Stmt {
        Game(Token name, List<Token> players, List<Token> strategies,
                java.util.Map<Token, java.util.Map<StrategyProfile, Expr>> payoffs) {
            this.name = name;
            this.players = players;
            this.strategies = strategies;
            this.payoffs = payoffs;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGameStmt(this);
        }

        final Token name;
        final List<Token> players;
        final List<Token> strategies;
        final java.util.Map<Token, java.util.Map<StrategyProfile, Expr>> payoffs;
    }

    // Game theory: Solve statement
    static class Solve extends Stmt {
        Solve(Token gameName, Token algorithm) {
            this.gameName = gameName;
            this.algorithm = algorithm;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSolveStmt(this);
        }

        final Token gameName;
        final Token algorithm; // null means default (pure)
    }

    // Standard library: Import statement
    static class Import extends Stmt {
        Import(Token keyword, String path) {
            this.keyword = keyword;
            this.path = path;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitImportStmt(this);
        }

        final Token keyword;
        final String path;
    }

    // Mechanism design: Tweak statement for parameter sweeping
    static class Tweak extends Stmt {
        Tweak(Token keyword, Token gameName, Token variable,
                double fromValue, double toValue, double stepValue) {
            this.keyword = keyword;
            this.gameName = gameName;
            this.variable = variable;
            this.fromValue = fromValue;
            this.toValue = toValue;
            this.stepValue = stepValue;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitTweakStmt(this);
        }

        final Token keyword;
        final Token gameName;
        final Token variable;
        final double fromValue;
        final double toValue;
        final double stepValue;
    }

    // Visualization: Generate DOT graph
    static class Visualize extends Stmt {
        Visualize(Token keyword, Token gameName) {
            this.keyword = keyword;
            this.gameName = gameName;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVisualizeStmt(this);
        }

        final Token keyword;
        final Token gameName;
    }

    // Sequential games (extensive form)
    static class SequentialGame extends Stmt {
        SequentialGame(Token name, List<Token> players, List<GameNode> nodes) {
            this.name = name;
            this.players = players;
            this.nodes = nodes;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSequentialGameStmt(this);
        }

        final Token name;
        final List<Token> players;
        final List<GameNode> nodes;
    }

    // Represents a decision node in the game tree
    static class GameNode {
        GameNode(Token name, Token player, List<GameMove> moves) {
            this.name = name;
            this.player = player;
            this.moves = moves;
        }

        final Token name;
        final Token player;
        final List<GameMove> moves;
    }

    // Represents a move/edge in the game tree
    static class GameMove {
        // Move to another node
        GameMove(Token action, Token targetNode) {
            this.action = action;
            this.targetNode = targetNode;
            this.payoffs = null;
        }

        // Move to terminal payoffs
        GameMove(Token action, List<Double> payoffs) {
            this.action = action;
            this.targetNode = null;
            this.payoffs = payoffs;
        }

        final Token action;
        final Token targetNode; // null if terminal
        final List<Double> payoffs; // null if not terminal

        boolean isTerminal() {
            return payoffs != null;
        }
    }

    abstract <R> R accept(Visitor<R> visitor);
}
