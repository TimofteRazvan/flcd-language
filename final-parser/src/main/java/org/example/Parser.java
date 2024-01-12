package org.example;

import java.util.*;

public class Parser {
    private Grammar grammar;
    private Map<String, Set<String>> firstSets;
    private Map<String, Set<String>> followSets;
    private Map<String, Map<String, List<String>>> parsingTable;
    private ParseTreeNode parseTreeRoot;
    // private String treeString = "aaabbb";

    public Parser() {
        this.grammar = new Grammar();
        grammar.readGrammarFromFile("C:\\Users\\Robert\\IdeaProjects\\FLCD_Parser\\src\\main\\java\\org\\example\\g5.txt");
        this.firstSets = calculateFirstSets(grammar);
        this.followSets = calculateFollowSets(grammar, firstSets);
        this.parsingTable = new HashMap<>();
        calculateParsingTable();
        this.parseTreeRoot = parse("a");
    }

    public ParseTreeNode getParseTreeRoot() {
        return parseTreeRoot;
    }

     public ParseTreeNode parse(String inputString) {
        Stack<ParseTreeNode> stack = new Stack<>();
        stack.push(new ParseTreeNode("$"));
        stack.push(new ParseTreeNode(grammar.getStartingSymbol()));
    
        parseTreeRoot = stack.peek();
    
        int inputIndex = 0;
    
        while (!stack.isEmpty()) {
            ParseTreeNode currentNode = stack.pop();
            System.out.println("Stack: " + stack);
            System.out.println("Input Index: " + inputIndex);
    
            String stackTop = currentNode.getValue();
    
            if (grammar.isTerminal(stackTop)) {
                if (inputIndex < inputString.length() && stackTop.equals(inputString.substring(inputIndex, inputIndex + 1))) {
                    inputIndex++;
                } else {
                    System.out.println("Mismatched terminal at input index " + inputIndex);
                    return null;
                }
            } else if (stackTop.equals("$")) {
                if (stack.isEmpty()) {
                    System.out.println("Parsing successful!");
                    return parseTreeRoot;
                } else {
                    System.out.println("Unexpected end of input");
                    return null;
                }
            } else {
                Map<String, List<String>> tableEntry = parsingTable.get(stackTop);
    
                if (tableEntry != null) {
                    List<String> production = tableEntry.get(inputIndex < inputString.length() ? inputString.substring(inputIndex, inputIndex + 1) : "$");
    
                    if (production != null && !production.isEmpty()) {
                        String[] productionSymbols = production.get(0).split("\\s+");
                        for (int i = productionSymbols.length - 1; i >= 0; i--) {
                            String symbol = productionSymbols[i];
                            if (!symbol.equals("e")) {
                                ParseTreeNode newNode = new ParseTreeNode(symbol);
                                currentNode.addChild(newNode);
                                stack.push(newNode);
                            }
                        }
                    } else {
                        System.out.println("Unexpected symbol at input index " + inputIndex);
                        return null;
                    }
                } else {
                    System.out.println("Invalid non-terminal: " + stackTop);
                    return null;
                }
            }
        }
    
        if (inputIndex == inputString.length()) {
            System.out.println("Parsing successful!");
            return parseTreeRoot;
        } else {
            System.out.println("Parsing incomplete. Remaining input: " + inputString.substring(inputIndex));
            return null;
        }
    }            
             
    public void printParseTree(ParseTreeNode node, int indent) {
        StringBuilder sb = new StringBuilder();
        // for (int i = 0; i < indent; i++) {
        //     sb.append("  ");
        // }
        sb.append(node);
        System.out.println(sb.toString());
    
        for (ParseTreeNode child : node.getChildren()) {
            printParseTree(child, indent + 1);
        }
    }          

    private Map<String, Set<String>> calculateFirstSets(Grammar grammar) {
        Map<String, Set<String>> firstSets = new HashMap<>();
        Set<String> nonTerminals = grammar.getNonTerminals();
    
        for (String nonTerminal : nonTerminals) {
            firstSets.put(nonTerminal, new HashSet<>());
        }
    
        boolean changed;
        do {
            changed = false;
    
            for (String nonTerminal : nonTerminals) {
                List<String> productions = grammar.getProductionsForNonTerminal(nonTerminal);
    
                for (String production : productions) {
                    String[] symbols = production.split("\\s+");
                    int i = 0;

                    while (i < symbols.length) {
                        String symbol = symbols[i];
    
                        if (symbol.equals("e")) {
                            changed |= firstSets.get(nonTerminal).add(symbol);
                            break;
                        } else if (grammar.isTerminal(symbol)) {
                            System.out.println(symbol);
                            changed |= firstSets.get(nonTerminal).add(symbol);
                            break;
                        } else if (firstSets.containsKey(symbol)) {
                            Set<String> symbolFirstSet = new HashSet<>(firstSets.get(symbol));
                            symbolFirstSet.remove("e");
    
                            changed |= firstSets.get(nonTerminal).addAll(symbolFirstSet);
    
                            if (!symbolFirstSet.contains("e")) {
                                break;
                            }
                        } else {
                            break;
                        }
                        i++;
                    }
                }
            }
        } while (changed);
    
        return firstSets;
    }     
        
    private Map<String, Set<String>> calculateFollowSets(Grammar grammar, Map<String, Set<String>> firstSets) {
        Map<String, Set<String>> followSets = new HashMap<>();
        Set<String> nonTerminals = grammar.getNonTerminals();
    
        for (String nonTerminal : nonTerminals) {
            followSets.put(nonTerminal, new HashSet<>());
        }
    
        followSets.get(grammar.getStartingSymbol()).add("$");
    
        boolean changed;
        do {
            changed = false;
    
            for (String nonTerminal : nonTerminals) {
                List<String> productions = grammar.getProductionsForNonTerminal(nonTerminal);
    
                for (String production : productions) {
                    String[] symbols = production.split("\\s+");
                    int len = symbols.length;
    
                    for (int i = 0; i < len; i++) {
                        String currentSymbol = symbols[i];
    
                        if (grammar.isNonTerminal(currentSymbol)) {
                            if (i < len - 1) {
                                String nextSymbol = symbols[i + 1];
    
                                if (grammar.isTerminal(nextSymbol)) {
                                    changed |= followSets.get(currentSymbol).add(nextSymbol);
                                } else if (grammar.isNonTerminal(nextSymbol)) {
                                    Set<String> nextFirstSet = new HashSet<>(firstSets.get(nextSymbol));
                                    nextFirstSet.remove("e");
    
                                    changed |= followSets.get(currentSymbol).addAll(nextFirstSet);
    
                                    if (firstSets.get(nextSymbol).contains("e")) {
                                        changed |= followSets.get(currentSymbol).addAll(followSets.get(nonTerminal));
                                    }
                                }
                            } else {
                                changed |= followSets.get(currentSymbol).addAll(followSets.get(nonTerminal));
                            }
                        }
                    }
                }
            }
        } while (changed);
    
        return followSets;
    }

    private void calculateParsingTable() {
        Set<String> nonTerminals = grammar.getNonTerminals();
        Set<String> terminals = grammar.getTerminals();

        // Initialize parsing table
        for (String nonTerminal : nonTerminals) {
            parsingTable.put(nonTerminal, new HashMap<>());
            for (String terminal : terminals) {
                parsingTable.get(nonTerminal).put(terminal, new ArrayList<>());
            }
        }

        for (String nonTerminal : nonTerminals) {
            List<String> productions = grammar.getProductionsForNonTerminal(nonTerminal);

            for (String production : productions) {
                Set<String> firstSet = calculateFirstForString(production);

                for (String terminal : firstSet) {
                    if (!terminal.equals("e")) {
                        List<String> existingProduction = parsingTable.get(nonTerminal).get(terminal);
                        if (existingProduction != null && !existingProduction.isEmpty()) {
                            // Conflict detected, handle the conflict (e.g., print a warning)
                            System.out.println("Conflict for nonTerminal: " + nonTerminal +
                                    ", terminal: " + terminal +
                                    ", existing production: " + existingProduction.get(0) +
                                    ", new production: " + production);
                        } else {
                            // No conflict, add the production to the table
                            parsingTable.get(nonTerminal).put(terminal, List.of(production));
                        }
                    }
                }

                if (firstSet.contains("e")) {
                    Set<String> followSet = followSets.get(nonTerminal);

                    for (String terminal : followSet) {
                        List<String> existingProduction = parsingTable.get(nonTerminal).get(terminal);
                        if (existingProduction != null && !existingProduction.isEmpty()) {
                            // Conflict detected, handle the conflict (print a warning)
                            System.out.println("First-Follow conflict for nonTerminal: " + nonTerminal +
                                    ", terminal: " + terminal +
                                    ", existing production: " + existingProduction.get(0) +
                                    ", new production: " + production);
                        } else {
                            // No conflict, add the production to the table
                            parsingTable.get(nonTerminal).put(terminal, List.of(production));
                        }
                    }
                }
            }
        }
    }

    private Set<String> calculateFirstForString(String input) {
        String[] symbols = input.split("\\s+");
        Set<String> result = new HashSet<>();

        for (String symbol : symbols) {
            if (grammar.isTerminal(symbol)) {
                result.add(symbol);
                break;
            } else if (grammar.isNonTerminal(symbol)) {
                Set<String> firstSet = firstSets.get(symbol);
                result.addAll(firstSet);
                if (!firstSet.contains("e")) {
                    break;
                }
            } else if (symbol.equals("e")) {
                result.add("e");
                break;
            }
        }

        return result;
    }

    public void printParsingTable() {
        System.out.println("LL(1) Parsing Table:");

        // Print table header
        System.out.print(String.format("%-20s", ""));
        Set<String> terminals = grammar.getTerminals();
        for (String terminal : terminals) {
            System.out.print(String.format("%-20s", terminal));
        }
        System.out.println();

        // Print table rows
        for (String nonTerminal : parsingTable.keySet()) {
            System.out.print(String.format("%-20s", nonTerminal));
            for (String terminal : terminals) {
                List<String> production = parsingTable.get(nonTerminal).get(terminal);
                System.out.print(String.format("%-20s", production.isEmpty() ? "" : production.get(0)));
            }
            System.out.println();
        }
    }

    public void printFirstAndFollowSets() {
        System.out.println("FIRST sets:");
        for (Map.Entry<String, Set<String>> entry : firstSets.entrySet()) {
            System.out.println("FIRST(" + entry.getKey() + ") = " + entry.getValue());
        }

        System.out.println("\nFOLLOW sets:");
        for (Map.Entry<String, Set<String>> entry : followSets.entrySet()) {
            System.out.println("FOLLOW(" + entry.getKey() + ") = " + entry.getValue());
        }
    }

    public Grammar getGrammar() {
        return grammar;
    }
}
