package org.axiom.tenet;

import java.util.List;
import java.util.Map;

/**
 * Generates Graphviz DOT format output for game visualizations.
 * Output can be rendered using: https://dreampuf.github.io/GraphvizOnline/
 * Or with the Graphviz VS Code extension.
 */
class GraphGenerator {

    /**
     * Generate a DOT representation of a game's payoff matrix
     */
    static String generatePayoffMatrix(GameValue game) {
        StringBuilder dot = new StringBuilder();

        dot.append("digraph ").append(game.name).append(" {\n");
        dot.append("    // Graph styling\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [fontname=\"Arial\", fontsize=12];\n");
        dot.append("    edge [fontname=\"Arial\", fontsize=10];\n");
        dot.append("    label=\"").append(game.name).append("\";\n");
        dot.append("    labelloc=t;\n");
        dot.append("    fontsize=16;\n");
        dot.append("    fontname=\"Arial Bold\";\n");
        dot.append("\n");

        // Player nodes
        dot.append("    // Player nodes\n");
        for (int i = 0; i < game.players.size(); i++) {
            String player = game.players.get(i).lexeme;
            String color = (i == 0) ? "#4ECDC4" : "#FF6B6B";
            dot.append("    ").append(player)
                    .append(" [shape=ellipse, style=filled, fillcolor=\"")
                    .append(color).append("\", fontcolor=white];\n");
        }
        dot.append("\n");

        // Create outcome nodes for each strategy profile
        dot.append("    // Outcome nodes\n");
        dot.append("    node [shape=box, style=filled, fillcolor=\"#F7DC6F\"];\n");

        List<Token> strategies = game.strategies;
        String p1 = game.players.get(0).lexeme;
        String p2 = game.players.get(1).lexeme;

        for (Token s1 : strategies) {
            for (Token s2 : strategies) {
                String nodeName = "outcome_" + s1.lexeme + "_" + s2.lexeme;

                // Get payoffs
                String payoff1 = getPayoff(game, p1, s1, s2);
                String payoff2 = getPayoff(game, p2, s1, s2);

                String label = "(" + payoff1 + ", " + payoff2 + ")";
                dot.append("    ").append(nodeName)
                        .append(" [label=\"").append(label).append("\"];\n");
            }
        }
        dot.append("\n");

        // Decision nodes
        dot.append("    // Decision structure\n");
        dot.append("    ").append(p1).append("_decision [shape=point, width=0.1];\n");
        dot.append("    ").append(p1).append(" -> ").append(p1).append("_decision [arrowhead=none];\n");
        dot.append("\n");

        // Edges from P1 decision to P2 decisions
        for (Token s1 : strategies) {
            String p2_decision = p2 + "_" + s1.lexeme;
            dot.append("    ").append(p2_decision).append(" [shape=point, width=0.1];\n");
            dot.append("    ").append(p1).append("_decision -> ").append(p2_decision)
                    .append(" [label=\"").append(s1.lexeme).append("\"];\n");

            // Edges from P2 decision to outcomes
            for (Token s2 : strategies) {
                String nodeName = "outcome_" + s1.lexeme + "_" + s2.lexeme;
                dot.append("    ").append(p2_decision).append(" -> ").append(nodeName)
                        .append(" [label=\"").append(s2.lexeme).append("\"];\n");
            }
        }

        dot.append("}\n");

        return dot.toString();
    }

    /**
     * Generate a simple matrix table view in DOT format
     */
    static String generateMatrixView(GameValue game) {
        StringBuilder dot = new StringBuilder();

        List<Token> strategies = game.strategies;
        String p1 = game.players.get(0).lexeme;
        String p2 = game.players.get(1).lexeme;

        dot.append("digraph ").append(game.name).append(" {\n");
        dot.append("    rankdir=LR;\n");
        dot.append("    node [shape=none];\n");
        dot.append("    label=\"").append(game.name).append(" - Payoff Matrix\";\n");
        dot.append("    labelloc=t;\n");
        dot.append("    fontsize=16;\n");
        dot.append("\n");

        // Build HTML table
        dot.append("    matrix [label=<\n");
        dot.append("        <TABLE BORDER=\"1\" CELLBORDER=\"1\" CELLSPACING=\"0\" CELLPADDING=\"8\">\n");

        // Header row
        dot.append("            <TR>\n");
        dot.append("                <TD BGCOLOR=\"#95A5A6\"><B>").append(p1).append(" \\ ").append(p2)
                .append("</B></TD>\n");
        for (Token s : strategies) {
            dot.append("                <TD BGCOLOR=\"#3498DB\"><FONT COLOR=\"white\"><B>").append(s.lexeme)
                    .append("</B></FONT></TD>\n");
        }
        dot.append("            </TR>\n");

        // Data rows
        for (Token s1 : strategies) {
            dot.append("            <TR>\n");
            dot.append("                <TD BGCOLOR=\"#E74C3C\"><FONT COLOR=\"white\"><B>").append(s1.lexeme)
                    .append("</B></FONT></TD>\n");
            for (Token s2 : strategies) {
                String payoff1 = getPayoff(game, p1, s1, s2);
                String payoff2 = getPayoff(game, p2, s1, s2);
                dot.append("                <TD>(").append(payoff1).append(", ").append(payoff2).append(")</TD>\n");
            }
            dot.append("            </TR>\n");
        }

        dot.append("        </TABLE>\n");
        dot.append("    >];\n");
        dot.append("}\n");

        return dot.toString();
    }

    /**
     * Generate a DOT representation of a sequential game tree
     */
    static String generateGameTree(SequentialGameValue game) {
        StringBuilder dot = new StringBuilder();

        dot.append("digraph ").append(game.name).append(" {\n");
        dot.append("    rankdir=TB;\n");
        dot.append("    node [fontname=\"Arial\", fontsize=12];\n");
        dot.append("    edge [fontname=\"Arial\", fontsize=10];\n");
        dot.append("    label=\"").append(game.name).append("\";\n");
        dot.append("    labelloc=t;\n");
        dot.append("    fontsize=16;\n");
        dot.append("    fontname=\"Arial Bold\";\n");
        dot.append("\n");

        // Use a set to track visited nodes to avoid duplicates if cycles exist (though
        // tree shouldn't have cycles)
        // For simple tree traversal, we'll recurse from root
        if (game.rootNodeName != null) {
            generateNode(dot, game, game.rootNodeName);
        }

        dot.append("}\n");
        return dot.toString();
    }

    private static void generateNode(StringBuilder dot, SequentialGameValue game, String nodeName) {
        Stmt.GameNode node = game.nodes.get(nodeName);
        if (node == null)
            return;

        // Decision Node
        String color = "#3498DB"; // Default blue
        // Color based on player?
        // Simple mapping: 0 -> Blue, 1 -> Red, etc.
        int playerIdx = 0;
        for (int i = 0; i < game.players.size(); i++) {
            if (game.players.get(i).lexeme.equals(node.player.lexeme)) {
                playerIdx = i;
                break;
            }
        }
        if (playerIdx == 1)
            color = "#E74C3C"; // Red
        if (playerIdx == 2)
            color = "#2ECC71"; // Green

        dot.append("    ").append(nodeName)
                .append(" [shape=ellipse, style=filled, fillcolor=\"").append(color)
                .append("\", fontcolor=white, label=\"").append(node.player.lexeme).append("\"];\n");

        // Edges
        for (Stmt.GameMove move : node.moves) {
            String edgeLabel = move.action.lexeme;

            if (move.isTerminal()) {
                // Terminal node
                String termNodeName = nodeName + "_" + move.action.lexeme + "_term";
                StringBuilder payoffs = new StringBuilder("(");
                for (int i = 0; i < move.payoffs.size(); i++) {
                    if (i > 0)
                        payoffs.append(", ");
                    double p = move.payoffs.get(i);
                    if (p == (long) p)
                        payoffs.append((long) p);
                    else
                        payoffs.append(p);
                }
                payoffs.append(")");

                dot.append("    ").append(termNodeName)
                        .append(" [shape=box, style=filled, fillcolor=\"#F1C40F\", label=\"")
                        .append(payoffs).append("\"];\n");

                dot.append("    ").append(nodeName).append(" -> ").append(termNodeName)
                        .append(" [label=\"").append(edgeLabel).append("\"];\n");
            } else {
                // Edge to next node
                String targetName = move.targetNode.lexeme;
                // Recursive call?
                // We should only generate nodes once. But if it's a tree, distinct paths might
                // lead to same node definition if we are exploring structure?
                // Actually, in extensive form, checks usually ensure it's a tree.
                // But let's just draw the edge. The node definition will be handled if we
                // iterate all nodes or recurse.
                // If we recurse, we might duplicate if simple recursion.
                // Better approach: Iterate all nodes in the game to define them, then iterate
                // all moves to define edges.
                // BUT, to get the hierarchy right, recursion is fine if strict tree.
                // Let's just draw edge. The node generation handles duplicates implicitly by
                // DOT being idempotent on node attributes?
                // No, duplicate nodes in DOT is fine, duplicate EDGES is what we want to avoid.
                // Let's just recursively call generateNode?
                // Wait, if I recurse, I might generate the target node definition multiple
                // times.
                // DOT handles repeated node definitions by merging/overwriting.
                // So simple recursion is safe for definitions.

                dot.append("    ").append(nodeName).append(" -> ").append(targetName)
                        .append(" [label=\"").append(edgeLabel).append("\"];\n");

                generateNode(dot, game, targetName);
            }
        }
    }

    private static String getPayoff(GameValue game, String player, Token s1, Token s2) {
        Map<StrategyProfile, Expr> playerPayoffs = null;

        for (Map.Entry<Token, Map<StrategyProfile, Expr>> entry : game.payoffs.entrySet()) {
            if (entry.getKey().lexeme.equals(player)) {
                playerPayoffs = entry.getValue();
                break;
            }
        }

        if (playerPayoffs == null)
            return "?";

        // Find matching profile
        for (Map.Entry<StrategyProfile, Expr> entry : playerPayoffs.entrySet()) {
            List<Token> strats = entry.getKey().strategies;
            if (strats.size() >= 2 &&
                    strats.get(0).lexeme.equals(s1.lexeme) &&
                    strats.get(1).lexeme.equals(s2.lexeme)) {
                // Try to get literal value
                if (entry.getValue() instanceof Expr.Literal) {
                    Object val = ((Expr.Literal) entry.getValue()).value;
                    if (val instanceof Double) {
                        double d = (Double) val;
                        if (d == Math.floor(d)) {
                            return String.valueOf((int) d);
                        }
                    }
                    return String.valueOf(val);
                }
                return "expr";
            }
        }

        return "?";
    }
}
