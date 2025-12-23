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

    @Override
    public String toString() {
        return "<game " + name + ">";
    }
}
