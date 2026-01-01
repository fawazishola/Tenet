package org.axiom.tenet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Solves Game Theory models to find Nash Equilibria.
 * Currently supports Pure Strategy Nash Equilibrium (PSNE) for 2-player games.
 */
class NashSolver {

    private final Interpreter interpreter;

    NashSolver(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    /**
     * Finds all pure strategy Nash Equilibria for a given game.
     */
    List<StrategyProfile> findPureEquilibria(GameValue game) {
        List<StrategyProfile> equilibria = new ArrayList<>();
        List<Token> players = game.players;
        List<Token> strategies = game.strategies;

        if (players.size() != 2) {
            // TODO: Support N-player games
            return equilibria;
        }

        // Iterate through every possible strategy profile (s1, s2)
        for (Token s1 : strategies) {
            for (Token s2 : strategies) {
                // Construct current profile
                List<Token> currentStratList = new ArrayList<>();
                currentStratList.add(s1);
                currentStratList.add(s2);
                StrategyProfile currentProfile = new StrategyProfile(currentStratList);

                if (isNashEquilibrium(game, currentProfile)) {
                    equilibria.add(currentProfile);
                }
            }
        }

        return equilibria;
    }

    private boolean isNashEquilibrium(GameValue game, StrategyProfile profile) {
        // Check deviation for Player 1
        if (canPlayerDeviate(game, profile, 0))
            return false;

        // Check deviation for Player 2
        if (canPlayerDeviate(game, profile, 1))
            return false;

        return true;
    }

    private boolean canPlayerDeviate(GameValue game, StrategyProfile currentProfile, int playerIndex) {
        Token player = game.players.get(playerIndex);
        Token currentStrategy = currentProfile.strategies.get(playerIndex);

        // Get current payoff
        Double currentPayoff = evaluatePayoff(game, player, currentProfile);
        if (currentPayoff == null)
            return false; // Error evaluating

        // Try all other strategies for this player
        for (Token otherStrat : game.strategies) {
            if (otherStrat.lexeme.equals(currentStrategy.lexeme))
                continue;

            // Construct new profile with deviation
            List<Token> deviationStrats = new ArrayList<>(currentProfile.strategies);
            deviationStrats.set(playerIndex, otherStrat);
            StrategyProfile deviationProfile = new StrategyProfile(deviationStrats);

            Double deviationPayoff = evaluatePayoff(game, player, deviationProfile);

            if (deviationPayoff != null && deviationPayoff > currentPayoff) {
                return true; // Player has incentive to deviate
            }
        }

        return false;
    }

    private Double evaluatePayoff(GameValue game, Token player, StrategyProfile profile) {
        Object result = game.getPayoff(player, profile, interpreter);
        return getDoubleValue(result);
    }

    private Double getDoubleValue(Object obj) {
        if (obj instanceof Double)
            return (Double) obj;
        if (obj instanceof Integer)
            return ((Integer) obj).doubleValue();
        return null; // Should handle this case better, maybe 0.0?
    }
}
