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
