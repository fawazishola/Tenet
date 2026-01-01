package org.axiom.tenet;

import java.util.List;
import java.util.Map;

/**
 * Runtime representation of a game definition.
 * Stores players, strategies, and payoff mappings.
 */
class GameValue {
    final String name;
    final List<Token> players;
    final List<Token> strategies;
    final Map<Token, Map<StrategyProfile, Expr>> payoffs;

    GameValue(String name, List<Token> players, List<Token> strategies,
            Map<Token, Map<StrategyProfile, Expr>> payoffs) {
        this.name = name;
        this.players = players;
        this.strategies = strategies;
        this.payoffs = payoffs;
    }

    String getPlayersString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(players.get(i).lexeme);
        }
        return sb.toString();
    }

    String getStrategiesString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strategies.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(strategies.get(i).lexeme);
        }
        return sb.toString();
    }

    Object getPayoff(Token player, StrategyProfile profile, Interpreter interpreter) {
        Map<StrategyProfile, Expr> playerPayoffs = null;
        for (Token key : payoffs.keySet()) {
            if (key.lexeme.equals(player.lexeme)) {
                playerPayoffs = payoffs.get(key);
                break;
            }
        }

        if (playerPayoffs == null)
            return 0.0;

        for (Map.Entry<StrategyProfile, Expr> entry : playerPayoffs.entrySet()) {
            if (matches(entry.getKey(), profile)) {
                return interpreter.evaluate(entry.getValue());
            }
        }
        return 0.0;
    }

    private boolean matches(StrategyProfile p1, StrategyProfile p2) {
        if (p1.strategies.size() != p2.strategies.size())
            return false;
        for (int i = 0; i < p1.strategies.size(); i++) {
            if (!p1.strategies.get(i).lexeme.equals(p2.strategies.get(i).lexeme)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "<game " + name + ">";
    }
}
