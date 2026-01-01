package org.axiom.tenet;

import java.util.*;

/**
 * Backward induction solver for sequential games.
 * Finds subgame perfect equilibrium by working backwards from terminal nodes.
 */
class BackwardInductionSolver {

    static class Solution {
        final List<String> path; // Sequence of actions
        final List<Double> payoffs;

        Solution(List<String> path, List<Double> payoffs) {
            this.path = path;
            this.payoffs = payoffs;
        }
    }

    /**
     * Solve the game using backward induction.
     * Returns the subgame perfect equilibrium path and payoffs.
     */
    Solution solve(SequentialGameValue game) {
        if (game.rootNodeName == null) {
            return new Solution(new ArrayList<>(), new ArrayList<>());
        }

        // Memoize solutions for each node
        Map<String, Solution> solutions = new HashMap<>();

        return solveNode(game, game.rootNodeName, solutions, new ArrayList<>());
    }

    private Solution solveNode(SequentialGameValue game, String nodeName,
            Map<String, Solution> memo, List<String> pathSoFar) {
        if (memo.containsKey(nodeName)) {
            return memo.get(nodeName);
        }

        Stmt.GameNode node = game.nodes.get(nodeName);
        if (node == null || node.moves.isEmpty()) {
            // Dead end - shouldn't happen in well-formed games
            return new Solution(new ArrayList<>(), new ArrayList<>());
        }

        // Find the player index for this node
        int playerIndex = getPlayerIndex(game, node.player);

        // Evaluate all moves
        Solution bestSolution = null;
        String bestAction = null;

        for (Stmt.GameMove move : node.moves) {
            Solution moveSolution;

            if (move.isTerminal()) {
                // Terminal node - payoffs are known
                moveSolution = new Solution(new ArrayList<>(), move.payoffs);
            } else {
                // Recursive call to child node
                List<String> newPath = new ArrayList<>(pathSoFar);
                newPath.add(move.action.lexeme);
                moveSolution = solveNode(game, move.targetNode.lexeme, memo, newPath);
            }

            // Player picks the move that maximizes their payoff
            if (bestSolution == null ||
                    (playerIndex < moveSolution.payoffs.size() &&
                            (bestSolution.payoffs.isEmpty() ||
                                    moveSolution.payoffs.get(playerIndex) > bestSolution.payoffs.get(playerIndex)))) {
                bestSolution = moveSolution;
                bestAction = move.action.lexeme;
            }
        }

        // Build path including this action
        List<String> resultPath = new ArrayList<>();
        resultPath.add(bestAction);
        if (bestSolution != null) {
            resultPath.addAll(bestSolution.path);
        }

        Solution result = new Solution(resultPath,
                bestSolution != null ? bestSolution.payoffs : new ArrayList<>());
        memo.put(nodeName, result);
        return result;
    }

    private int getPlayerIndex(SequentialGameValue game, Token player) {
        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i).lexeme.equals(player.lexeme)) {
                return i;
            }
        }
        return 0; // Default to first player
    }
}
