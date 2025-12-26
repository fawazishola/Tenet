package org.axiom.tenet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static org.axiom.tenet.TokenType.*;

class Parser {
    private static class ParseError extends RuntimeException {
    }

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;

    }

    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(declaration());
        }
        return statements;
    }

    private Expr expression() {
        return assignment();
    }

    private Stmt statement() {
        if (match(LEFT_BRACE))
            return new Stmt.Block(block());

        if (match(RETURN)) {
            return returnStatement();
        }
        if (match(PRINT))
            return printStatement();
        if (match(IF)) {
            return ifStatement();
        }
        if (match(WHILE))
            return whileStatement();
        if (match(FOR))
            return forStatement();
        return expressionStatement();
    }

    private Stmt forStatement() {
        consume(LEFT_PAREN, "Expect '(' after for. ");

        Stmt initializer;
        if (match(SEMICOLON)) {
            initializer = null;
        } else if (match(VAR)) {
            initializer = varDeclaration();
        } else {
            initializer = expressionStatement();
        }

        Expr condition = null;
        if (!check(SEMICOLON)) {
            condition = expression();
        }
        consume(SEMICOLON, "Expect ';' after loop condition");

        Expr increment = null;
        if (!check(RIGHT_PAREN)) {
            increment = expression();
        }
        consume(RIGHT_PAREN, "Expect ')' after clauses");

        Stmt body = statement();

        if (increment != null) {
            body = new Stmt.Block(
                    Arrays.asList(body, new Stmt.Expression(increment)));

        }

        if (condition == null)
            condition = new Expr.Literal(true);
        body = new Stmt.While(condition, body);

        if (initializer != null) {
            body = new Stmt.Block(Arrays.asList(initializer, body));
        }

        return body;

    }

    private Stmt declaration() {
        try {
            if (match(IMPORT))
                return importStatement();
            if (match(TWEAK))
                return tweakStatement();
            if (match(SEQUENTIAL))
                return sequentialGameDeclaration();
            if (match(GAME))
                return gameDeclaration();
            if (match(SOLVE))
                return solveStatement();
            if (match(VISUALIZE))
                return visualizeStatement();
            if (match(CLASS))
                return classDeclaration();
            if (match(FUN))
                return function("function");
            if (match(VAR))
                return varDeclaration();

            return statement();
        } catch (ParseError error) {
            synchronize();
            return null;
        }
    }

    /**
     * Parses: tweak GAME_NAME { VARIABLE from NUMBER to NUMBER step NUMBER }
     * Example: tweak PD { reward from 1 to 10 step 1 }
     */
    private Stmt tweakStatement() {
        Token keyword = previous();
        Token gameName = consume(IDENTIFIER, "Expect game name after 'tweak'.");
        consume(LEFT_BRACE, "Expect '{' after game name.");

        Token variable = consume(IDENTIFIER, "Expect variable name to tweak.");
        consume(FROM, "Expect 'from' after variable name.");
        Token fromToken = consume(NUMBER, "Expect number after 'from'.");
        double fromValue = (double) fromToken.literal;

        consume(TO, "Expect 'to' after from value.");
        Token toToken = consume(NUMBER, "Expect number after 'to'.");
        double toValue = (double) toToken.literal;

        consume(STEP, "Expect 'step' after to value.");
        Token stepToken = consume(NUMBER, "Expect number after 'step'.");
        double stepValue = (double) stepToken.literal;

        consume(RIGHT_BRACE, "Expect '}' after tweak body.");

        return new Stmt.Tweak(keyword, gameName, variable, fromValue, toValue, stepValue);
    }

    /**
     * Parses: import "library_name";
     */
    private Stmt importStatement() {
        Token keyword = previous();
        Token pathToken = consume(STRING, "Expect library name after 'import'.");
        consume(SEMICOLON, "Expect ';' after import statement.");
        return new Stmt.Import(keyword, (String) pathToken.literal);
    }

    /**
     * Parses sequential (extensive form) games:
     * sequential UltimatumGame {
     * players Proposer, Responder
     * 
     * node Start {
     * player Proposer
     * move Fair -> Accept
     * move Unfair -> Reject
     * }
     * 
     * node Accept {
     * player Responder
     * move Accept -> (5, 5)
     * move Reject -> (0, 0)
     * }
     * }
     */
    private Stmt sequentialGameDeclaration() {
        Token name = consume(IDENTIFIER, "Expect sequential game name.");
        consume(LEFT_BRACE, "Expect '{' after game name.");

        // Parse players
        consume(PLAYERS, "Expect 'players' declaration.");
        List<Token> players = new ArrayList<>();
        do {
            players.add(consume(IDENTIFIER, "Expect player name."));
        } while (match(COMMA));

        // Parse nodes
        List<Stmt.GameNode> nodes = new ArrayList<>();
        while (match(NODE)) {
            nodes.add(parseGameNode());
        }

        consume(RIGHT_BRACE, "Expect '}' after sequential game body.");

        return new Stmt.SequentialGame(name, players, nodes);
    }

    private Stmt.GameNode parseGameNode() {
        Token nodeName = consume(IDENTIFIER, "Expect node name.");
        consume(LEFT_BRACE, "Expect '{' after node name.");

        // Parse player for this node
        consume(PLAYERS, "Expect 'player' declaration in node.");
        Token player = consume(IDENTIFIER, "Expect player name.");

        // Parse moves
        List<Stmt.GameMove> moves = new ArrayList<>();
        while (match(MOVE)) {
            Token action = consume(IDENTIFIER, "Expect action name.");
            consume(ARROW, "Expect '->' after action.");

            if (match(LEFT_PAREN)) {
                // Terminal payoffs: (num, num, ...)
                List<Double> payoffs = new ArrayList<>();
                do {
                    Token numToken = consume(NUMBER, "Expect payoff number.");
                    payoffs.add((Double) numToken.literal);
                } while (match(COMMA));
                consume(RIGHT_PAREN, "Expect ')' after payoffs.");
                moves.add(new Stmt.GameMove(action, payoffs));
            } else {
                // Target node
                Token targetNode = consume(IDENTIFIER, "Expect target node name.");
                moves.add(new Stmt.GameMove(action, targetNode));
            }
        }

        consume(RIGHT_BRACE, "Expect '}' after node body.");
        return new Stmt.GameNode(nodeName, player, moves);
    }

    private Stmt classDeclaration() {
        Token name = consume(IDENTIFIER, "Expect class name.");

        Expr.Variable superclass = null;
        if (match(LESS)) {
            consume(IDENTIFIER, "Expect superclass name.");
            superclass = new Expr.Variable(previous());
        }

        consume(LEFT_BRACE, "Expect '{' before class body.");

        List<Stmt.Function> methods = new ArrayList<>();
        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            methods.add(function("method"));
        }

        consume(RIGHT_BRACE, "Expect '}' after class body.");
        return new Stmt.Class(name, superclass, methods);
    }

    private Stmt varDeclaration() {
        Token name = consume(IDENTIFIER, "Please provide variable name");

        Expr initializer = null;
        if (match(EQUAL)) {
            initializer = expression();
        }

        consume(SEMICOLON, "you didnt add a semi colon to close the variable declaration.");
        return new Stmt.Var(name, initializer);
    }

    private Stmt printStatement() {
        Expr value = expression();
        consume(SEMICOLON, "Expect ';' after value.");
        return new Stmt.Print(value);
    }

    private Stmt expressionStatement() {
        Expr value = expression();
        consume(SEMICOLON, "Expect ';' after expression.");
        return new Stmt.Expression(value);
    }

    private Stmt.Function function(String kind) {
        Token name = consume(IDENTIFIER, "Expect" + kind + "name.");
        consume(LEFT_PAREN, "Expect '(' after" + kind + "name.");
        List<Token> parameters = new ArrayList<>();
        if (!check(RIGHT_PAREN)) {
            do {
                if (parameters.size() >= 255) {
                    error(peek(), "Can't have more than 255 parameters.");
                }
                parameters.add(
                        consume(IDENTIFIER, "Expect parameter name."));

            } while (match(COMMA));
        }
        consume(RIGHT_PAREN, "Expect ')' after parameters.");

        consume(LEFT_BRACE, "Expect '{' before" + kind + "body");
        List<Stmt> body = block();
        return new Stmt.Function(name, parameters, body);
    }

    private Stmt ifStatement() {
        consume(LEFT_PAREN, "Expect ( after if .");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ) after if condition.");

        Stmt thenBranch = statement();

        Stmt elseBranch = null;
        if (match(ELSE)) {
            elseBranch = statement();
        }
        return new Stmt.If(condition, thenBranch, elseBranch);
    }

    private Stmt whileStatement() {
        consume(LEFT_PAREN, "Expect '(' after 'while");
        Expr condition = expression();
        consume(RIGHT_PAREN, "Expect ')' after condition");
        Stmt body = statement();

        return new Stmt.While(condition, body);
    }

    private Stmt returnStatement() {
        Token keyword = previous();
        Expr value = null;
        if (!check(SEMICOLON)) {
            value = expression();
        }
        consume(SEMICOLON, "Expect ';' after return value");
        return new Stmt.Return(keyword, value);
    }

    private List<Stmt> block() {
        List<Stmt> statements = new ArrayList<>();
        while (!check(RIGHT_BRACE) && !isAtEnd()) {
            statements.add(declaration());
        }
        consume(RIGHT_BRACE, "Expect '}' after block.");
        return statements;
    }

    private Expr assignment() {
        Expr expr = or();

        if (match(EQUAL)) {
            Token equals = previous();
            Expr value = assignment();

            if (expr instanceof Expr.Variable) {
                Token name = ((Expr.Variable) expr).name;
                return new Expr.Assign(name, value);
            } else if (expr instanceof Expr.Get) {
                Expr.Get get = (Expr.Get) expr;
                return new Expr.Set(get.object, get.name, value);
            }
            error(equals, "Invalid assignment target.");
        }
        return expr;
    }

    private Expr or() {
        Expr expr = and();

        while (match(OR)) {
            Token operator = previous();
            Expr right = and();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr and() {
        Expr expr = equality();

        while (match(AND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Logical(expr, operator, right);
        }
        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();
        while (match(EQUAL_EQUAL, BANG_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;

    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }
        return call();
    }

    private Expr finishCall(Expr callee) {
        List<Expr> arguments = new ArrayList<>();
        if (!check(RIGHT_PAREN)) {
            do {
                if (arguments.size() >= 255) {
                    error(peek(), "Cant have more than 255 arguments");
                }
                arguments.add(expression());
            } while (match(COMMA));
        }

        Token paren = consume(RIGHT_PAREN, "Expect')' after arguments.");
        return new Expr.Call(callee, paren, arguments);
    }

    private Expr call() {
        Expr expr = primary();

        while (true) {
            if (match(LEFT_PAREN)) {
                expr = finishCall(expr);
            } else if (match(DOT)) {
                Token name = consume(IDENTIFIER, "Expect property name after '.'.");
                expr = new Expr.Get(expr, name);
            } else {
                break;
            }
        }
        return expr;
    }

    private Expr primary() {
        if (match(FALSE))
            return new Expr.Literal(false);
        if (match(TRUE))
            return new Expr.Literal(true);
        if (match(NIL))
            return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(SUPER)) {
            Token keyword = previous();
            consume(DOT, "Expect '.' after 'super'.");
            Token method = consume(IDENTIFIER, "Expect superclass method name.");
            return new Expr.Super(keyword, method);
        }

        if (match(THIS))
            return new Expr.This(previous());

        if (match(IDENTIFIER)) {
            return new Expr.Variable(previous());
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        throw error(peek(), "Expect expression.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type))
            return advance();
        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd())
            return false;
        return peek().type == type;

    }

    private Token advance() {
        if (!isAtEnd())
            current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private ParseError error(Token token, String message) {
        Tenet.error(token, message);
        return new ParseError();
    }

    private void synchronize() {
        advance();
        while (!isAtEnd()) {
            if (previous().type == SEMICOLON)
                return;
            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                case GAME:
                case SOLVE:
                    return;
            }
            advance();
        }
    }

    // ===== GAME THEORY PARSING =====

    /**
     * Parses: game NAME { players ... strategies ... payoff ... }
     */
    private Stmt gameDeclaration() {
        Token name = consume(IDENTIFIER, "Expect game name.");
        consume(LEFT_BRACE, "Expect '{' after game name.");

        // Parse players
        consume(PLAYERS, "Expect 'players' declaration.");
        List<Token> players = parseIdentifierList();

        // Parse strategies
        consume(STRATEGIES, "Expect 'strategies' declaration.");
        List<Token> strategies = parseIdentifierList();

        // Parse payoff sections
        Map<Token, Map<StrategyProfile, Expr>> payoffs = new HashMap<>();
        while (match(PAYOFF)) {
            Token playerName = consume(IDENTIFIER, "Expect player name after 'payoff'.");
            consume(LEFT_BRACE, "Expect '{' before payoff rules.");

            Map<StrategyProfile, Expr> playerPayoffs = new HashMap<>();
            while (!check(RIGHT_BRACE) && !isAtEnd()) {
                // Parse (Strategy1, Strategy2): value
                consume(LEFT_PAREN, "Expect '(' before strategy profile.");
                List<Token> profileStrategies = parseIdentifierList();
                consume(RIGHT_PAREN, "Expect ')' after strategy profile.");
                consume(COLON, "Expect ':' after strategy profile.");
                Expr value = payoffValue(); // Use restricted parser

                StrategyProfile profile = new StrategyProfile(profileStrategies);
                playerPayoffs.put(profile, value);
            }
            consume(RIGHT_BRACE, "Expect '}' after payoff rules.");
            payoffs.put(playerName, playerPayoffs);
        }

        consume(RIGHT_BRACE, "Expect '}' after game body.");

        // === VALIDATION: Catch incomplete games at parse time ===
        validateGameCompleteness(name, players, strategies, payoffs);

        return new Stmt.Game(name, players, strategies, payoffs);
    }

    /**
     * Validates that a game definition is complete:
     * 1. Every player has a payoff definition
     * 2. Every payoff definition covers all strategy combinations
     * 3. Payoff rules use only declared strategies
     */
    private void validateGameCompleteness(
            Token name,
            List<Token> players,
            List<Token> strategies,
            Map<Token, Map<StrategyProfile, Expr>> payoffs) {

        // Check 1: Every player must have a payoff definition
        for (Token player : players) {
            boolean found = false;
            for (Token payoffPlayer : payoffs.keySet()) {
                if (payoffPlayer.lexeme.equals(player.lexeme)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw error(player,
                        "Player '" + player.lexeme + "' has no payoff definition in game '" +
                                name.lexeme + "'. Add: payoff " + player.lexeme + " { ... }");
            }
        }

        // Check 2: Every payoff definition must cover all strategy combinations
        int expectedCombinations = (int) Math.pow(strategies.size(), players.size());
        for (Map.Entry<Token, Map<StrategyProfile, Expr>> entry : payoffs.entrySet()) {
            Token payoffPlayer = entry.getKey();
            int actualCombinations = entry.getValue().size();

            if (actualCombinations < expectedCombinations) {
                // Find missing combinations for helpful error message
                StringBuilder missing = new StringBuilder();
                int missingCount = 0;
                for (Token s1 : strategies) {
                    for (Token s2 : strategies) {
                        boolean found = false;
                        for (StrategyProfile profile : entry.getValue().keySet()) {
                            if (profile.strategies.size() >= 2 &&
                                    profile.strategies.get(0).lexeme.equals(s1.lexeme) &&
                                    profile.strategies.get(1).lexeme.equals(s2.lexeme)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            if (missingCount < 3) { // Show up to 3 missing
                                if (missingCount > 0)
                                    missing.append(", ");
                                missing.append("(").append(s1.lexeme).append(", ").append(s2.lexeme).append(")");
                            }
                            missingCount++;
                        }
                    }
                }
                if (missingCount > 3) {
                    missing.append(" and ").append(missingCount - 3).append(" more");
                }

                throw error(payoffPlayer,
                        "Incomplete payoff matrix for '" + payoffPlayer.lexeme + "' in game '" +
                                name.lexeme + "': " + actualCombinations + "/" + expectedCombinations +
                                " combinations defined. Missing: " + missing.toString());
            }
        }

        // Check 3: Verify payoff player names match declared players
        for (Token payoffPlayer : payoffs.keySet()) {
            boolean found = false;
            for (Token player : players) {
                if (player.lexeme.equals(payoffPlayer.lexeme)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw error(payoffPlayer,
                        "Payoff defined for '" + payoffPlayer.lexeme + "' but this player is not declared. " +
                                "Declared players: " + formatPlayerList(players));
            }
        }
    }

    /**
     * Helper to format player list for error messages
     */
    private String formatPlayerList(List<Token> players) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(players.get(i).lexeme);
        }
        return sb.toString();
    }

    /**
     * Parses a payoff value: number, variable, or simple arithmetic (+, -, *, /)
     * Does NOT allow function calls (which would consume next rule's '(')
     */
    private Expr payoffValue() {
        return payoffTerm();
    }

    private Expr payoffTerm() {
        Expr expr = payoffFactor();
        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = payoffFactor();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr payoffFactor() {
        Expr expr = payoffUnary();
        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = payoffUnary();
            expr = new Expr.Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr payoffUnary() {
        if (match(MINUS)) {
            Token operator = previous();
            Expr right = payoffUnary();
            return new Expr.Unary(operator, right);
        }
        return payoffPrimary();
    }

    private Expr payoffPrimary() {
        if (match(NUMBER)) {
            return new Expr.Literal(previous().literal);
        }
        if (match(IDENTIFIER)) {
            return new Expr.Variable(previous());
        }
        throw error(peek(), "Expect payoff value (number or variable).");
    }

    /**
     * Parses: solve GAME_NAME;
     */
    private Stmt solveStatement() {
        Token gameName = consume(IDENTIFIER, "Expect game name after 'solve'.");
        Token algorithm = null;
        if (match(USING)) {
            algorithm = consume(IDENTIFIER, "Expect algorithm name after 'using'.");
        }
        consume(SEMICOLON, "Expect ';' after solve statement.");
        return new Stmt.Solve(gameName, algorithm);
    }

    /**
     * Parses: visualize GAME_NAME;
     */
    private Stmt visualizeStatement() {
        Token keyword = previous();
        Token gameName = consume(IDENTIFIER, "Expect game name after 'visualize'.");
        consume(SEMICOLON, "Expect ';' after visualize statement.");
        return new Stmt.Visualize(keyword, gameName);
    }

    /**
     * Parses a comma-separated list of identifiers (e.g., "Alice, Bob")
     */
    private List<Token> parseIdentifierList() {
        List<Token> identifiers = new ArrayList<>();
        do {
            identifiers.add(consume(IDENTIFIER, "Expect identifier."));
        } while (match(COMMA));
        return identifiers;
    }

}
