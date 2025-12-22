package org.axiom.tenet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Tenet {
    private static final Interpreter interpreter = new Interpreter();
    static boolean hadError = false;
    static boolean hadRuntimeError = false;

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            runPrompt();
        } else if (args.length == 1) {
            if (args[0].equals("--help") || args[0].equals("-h")) {
                printHelp();
            } else {
                runFile(args[0]);
            }
        } else if (args.length == 2) {
            if (args[0].equals("--graph")) {
                generateGraph(args[1], "tree");
            } else if (args[0].equals("--matrix")) {
                generateGraph(args[1], "matrix");
            } else {
                printHelp();
            }
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("Tenet - Game Theory Programming Language");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  tenet                    Start interactive REPL");
        System.out.println("  tenet <file>             Run a .tenet file");
        System.out.println("  tenet --graph <file>     Generate game tree (DOT format)");
        System.out.println("  tenet --matrix <file>    Generate payoff matrix (DOT format)");
        System.out.println("  tenet --help             Show this help message");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  tenet examples/demo.tenet");
        System.out.println("  tenet --graph examples/classic_games/prisoners_dilemma.tenet > game.dot");
    }

    private static void generateGraph(String path, String mode) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        String source = new String(bytes, Charset.defaultCharset());

        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError) {
            System.exit(65);
            return;
        }

        // Find game definitions and generate DOT
        for (Stmt stmt : statements) {
            if (stmt instanceof Stmt.Game) {
                Stmt.Game gameStmt = (Stmt.Game) stmt;
                GameValue game = new GameValue(
                        gameStmt.name.lexeme,
                        gameStmt.players,
                        gameStmt.strategies,
                        gameStmt.payoffs);

                if (mode.equals("matrix")) {
                    System.out.println(GraphGenerator.generateMatrixView(game));
                } else {
                    System.out.println(GraphGenerator.generatePayoffMatrix(game));
                }
            }
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
        if (hadError)
            System.exit(65);
        if (hadRuntimeError)
            System.exit(70);
    }

    // ANSI Color Codes
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String DIM = "\u001B[2m";

    private static void printSlow(String text) {
        System.out.println(text);
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
        }
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        // Boot sequence
        System.out.println();
        printSlow(DIM + "  Initializing Tenet Kernel..." + RESET);
        printSlow(DIM + "  Loading Game Theory Modules..." + RESET);
        printSlow(DIM + "  Starting Nash Equilibrium Solver..." + RESET);
        System.out.println();

        // Neon ASCII Banner (ANSI Shadow font)
        System.out.println(CYAN + BOLD);
        printSlow("  ████████╗███████╗███╗   ██╗███████╗████████╗");
        printSlow("  ╚══██╔══╝██╔════╝████╗  ██║██╔════╝╚══██╔══╝");
        printSlow("     ██║   █████╗  ██╔██╗ ██║█████╗     ██║   ");
        printSlow("     ██║   ██╔══╝  ██║╚██╗██║██╔══╝     ██║   ");
        printSlow("     ██║   ███████╗██║ ╚████║███████╗   ██║   ");
        printSlow("     ╚═╝   ╚══════╝╚═╝  ╚═══╝╚══════╝   ╚═╝   ");
        System.out.println(RESET);

        printSlow(CYAN + "  ╔════════════════════════════════════════════╗" + RESET);
        printSlow(CYAN + "  ║" + RESET + "  Game Theory Programming Language         " + CYAN + "║" + RESET);
        printSlow(CYAN + "  ║" + RESET + "  " + YELLOW + "Version 0.1.0" + RESET + "  ·  org.axiom.tenet       " + CYAN
                + "║" + RESET);
        printSlow(CYAN + "  ╚════════════════════════════════════════════╝" + RESET);
        System.out.println();

        printSlow(GREEN + "  ✓" + RESET + " Type expressions to evaluate");
        printSlow(GREEN + "  ✓" + RESET + " Use 'game { }' to define games");
        printSlow(DIM + "  Press Ctrl+C to exit" + RESET);
        System.out.println();

        for (;;) {
            System.out.print(CYAN + "tenet" + RESET + "> ");
            String line = reader.readLine();
            if (line == null)
                break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        Parser parser = new Parser(tokens);
        List<Stmt> statements = parser.parse();

        if (hadError)
            return;

        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        if (hadError)
            return;

        interpreter.interpret(statements);
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    static void runtimeError(RuntimeError error) {
        System.err.println(error.getMessage() + "\n[line " + error.token.line + "]");
        hadRuntimeError = true;
    }

    private static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    static void error(Token token, String message) {
        if (token.type == TokenType.EOF) {
            report(token.line, " at end", message);
        } else {
            report(token.line, " at '" + token.lexeme + "'", message);
        }
    }
}
