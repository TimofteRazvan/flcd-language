import java.util.*;

public class Parser {
    private Grammar grammar;
    private Map<String, Set<String>> firstSets;
    private Map<String, Set<String>> followSets;

    public Parser() {
        this.grammar = new Grammar();
        grammar.readGrammarFromFile("D:\\University\\Year - 3\\Tehnici de compilare\\Lab3\\Lab3\\src\\g3.txt");
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
                    while (i < symbols.length) {
                        String symbol = symbols[i];

                        if (grammar.isTerminal(symbol)) {
                            changed = changed || firstSets.get(nonTerminal).add(symbol);
                            break;
                        } else {
                            Set<String> symbolFirstSet = new HashSet<>();
                            if (firstSets.containsKey(symbol)) {
                                symbolFirstSet.addAll(firstSets.get(symbol));
                                symbolFirstSet.remove(""); // Remove epsilon if it exists
                            } else {
                                symbolFirstSet.add("");
                            }
                            changed = changed || firstSets.get(nonTerminal).addAll(symbolFirstSet);

                            if (!symbolFirstSet.contains("")) {
                                break;
                            }
                        }
                        i++;
                    }

                    if (i == symbols.length) {
                        changed = changed || firstSets.get(nonTerminal).add("");
                    }
                }
            }
        } while (changed);

        return firstSets;
    }

    private Map<String, Set<String>> calculateFollowSets(Grammar grammar, Map<String, Set<String>> firstSets) {
        Map<String, Set<String>> followSets = new HashMap<>();
        Set<String> nonTerminals = grammar.getNonTerminals();
        followSets.put(grammar.getStartingSymbol(), Set.of("$"));

        for (String nonTerminal : nonTerminals) {
            followSets.put(nonTerminal, new HashSet<>());
        }

        boolean changed;
        do {
            changed = false;

            for (String nonTerminal : nonTerminals) {
                for (Map.Entry<String, List<String>> entry : grammar.getProductions().entrySet()) {
                    String leftHandSide = entry.getKey();
                    List<String> productionList = entry.getValue();

                    for (String production : productionList) {
                        int idx = production.indexOf(nonTerminal);

                        while (idx >= 0) {
                            int followIdx = idx + nonTerminal.length();
                            if (followIdx < production.length()) {
                                String rest = production.substring(followIdx).trim();

                                if (!rest.isEmpty()) {
                                    String[] symbols = rest.split("\\s+");
                                    for (String symbol : symbols) {
                                        if (grammar.isNonTerminal(symbol)) {
                                            Set<String> followSet = followSets.get(symbol);
                                            Set<String> firstOfRest = calculateFirstOfRest(grammar, firstSets, symbol, rest);
                                            changed |= followSet.addAll(firstOfRest);
                                            if (!firstOfRest.contains("e")) {
                                                break;
                                            }
                                        } else if (grammar.isTerminal(symbol)) {
                                            changed |= followSets.get(nonTerminal).add(symbol);
                                            break;
                                        }
                                    }
                                } else {
                                    changed |= followSets.get(nonTerminal).addAll(followSets.get(leftHandSide));
                                }
                            }

                            idx = production.indexOf(nonTerminal, idx + 1);
                        }
                    }
                }
            }
        } while (changed);

        return followSets;
    }

    private Set<String> calculateFirstOfRest(Grammar grammar, Map<String, Set<String>> firstSets, String symbol, String rest) {
        String[] symbols = rest.split("\\s+");
        Set<String> firstSet = new HashSet<>();

        for (String s : symbols) {
            if (grammar.isTerminal(s)) {
                firstSet.add(s);
                return firstSet;
            } else if (grammar.isNonTerminal(s)) {
                Set<String> first = firstSets.get(s);
                firstSet.addAll(first);
                if (!first.contains("e")) {
                    return firstSet;
                }
            }
        }

        firstSet.addAll(followSets.get(symbol));
        return firstSet;
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
