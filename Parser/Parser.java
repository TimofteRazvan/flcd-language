import java.util.*;

public class Parser {
    private Grammar grammar;
    private Map<String, Set<String>> firstSets;
    private Map<String, Set<String>> followSets;

    public Parser() {
        this.grammar = new Grammar();
        grammar.readGrammarFromFile("D:\\University\\Year - 3\\Tehnici de compilare\\Lab3\\Lab3\\src\\g4.txt");
        this.firstSets = calculateFirstSets(grammar);
        this.followSets = calculateFollowSets(grammar, firstSets);
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
                    boolean hasEpsilon = true;
    
                    while (i < symbols.length) {
                        String symbol = symbols[i];
    
                        if (symbol.equals("e")) {
                            changed |= firstSets.get(nonTerminal).add(symbol);
                            hasEpsilon = false;
                            break;
                        } else if (grammar.isTerminal(symbol)) {
                            System.out.println(symbol);
                            changed |= firstSets.get(nonTerminal).add(symbol);
                            hasEpsilon = false;
                            break;
                        } else if (firstSets.containsKey(symbol)) {
                            Set<String> symbolFirstSet = new HashSet<>(firstSets.get(symbol));
                            symbolFirstSet.remove("e");
    
                            changed |= firstSets.get(nonTerminal).addAll(symbolFirstSet);
    
                            if (!symbolFirstSet.contains("e")) {
                                hasEpsilon = false;
                                break;
                            }
                        } else {
                            hasEpsilon = false;
                            break;
                        }
                        i++;
                    }
    
                    if (hasEpsilon) {
                        changed |= firstSets.get(nonTerminal).add("e");
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
}
