package org.axiom.tenet;

import java.util.List;
import java.util.Objects;

/**
 * Represents a strategy profile - a combination of strategies, one for each
 * player.
 * For example, in a 2-player game: (Cooperate, Defect)
 */
class StrategyProfile {
    final List<Token> strategies;

    StrategyProfile(List<Token> strategies) {
        this.strategies = strategies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StrategyProfile that = (StrategyProfile) o;
        if (strategies.size() != that.strategies.size())
            return false;
        for (int i = 0; i < strategies.size(); i++) {
            if (!strategies.get(i).lexeme.equals(that.strategies.get(i).lexeme)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Token t : strategies) {
            hash = 31 * hash + t.lexeme.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < strategies.size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(strategies.get(i).lexeme);
        }
        sb.append(")");
        return sb.toString();
    }
}
