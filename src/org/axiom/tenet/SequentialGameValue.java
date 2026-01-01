package org.axiom.tenet;

import java.util.*;

/**
 * Runtime representation of a sequential (extensive form) game.
 * The game tree is represented as nodes and edges (moves).
 */
class SequentialGameValue {
    final String name;
    final List<Token> players;
    final Map<String, Stmt.GameNode> nodes; // name -> node
    final String rootNodeName; // First node is the root

    SequentialGameValue(Stmt.SequentialGame stmt) {
        this.name = stmt.name.lexeme;
        this.players = stmt.players;
        this.nodes = new LinkedHashMap<>();

        // Build node map
        for (Stmt.GameNode node : stmt.nodes) {
            nodes.put(node.name.lexeme, node);
        }

        // First node is the root
        this.rootNodeName = stmt.nodes.isEmpty() ? null : stmt.nodes.get(0).name.lexeme;
    }

    @Override
    public String toString() {
        return "<sequential game " + name + ">";
    }
}
