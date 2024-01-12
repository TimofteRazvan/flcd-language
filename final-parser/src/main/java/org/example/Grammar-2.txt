package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    private String startingSymbol;
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private Map<String, List<String>> productions;

    public Grammar() {
        nonTerminals = new HashSet<>();
        terminals = new HashSet<>();
        productions = new HashMap<>();
    }

    public void readGrammarFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            line = br.readLine();
            String[] nonTrmSymbols = line.split("\\s+");
            for (String symbol : nonTrmSymbols) {
                symbol = symbol.trim();
                nonTerminals.add(symbol);
            }
    
            line = br.readLine();
            String[] trmSymbols = line.split("\\s+");
            for (String symbol : trmSymbols) {
                symbol = symbol.trim();
                terminals.add(symbol);
            }
    
            line = br.readLine();
            startingSymbol = line.trim();
    
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("->");
                String lhsSymbol = parts[0].trim();
                String[] productionRules = parts[1].split("\\|");
    
                for (String productionRule : productionRules) {
                    productionRule = productionRule.trim();
                    productions.computeIfAbsent(lhsSymbol, k -> new ArrayList<>()).add(productionRule);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }        

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }
    
    public void printNonTerminals() {
        System.out.println("Non-terminals: " + nonTerminals);
    }

    public void printTerminals() {
        System.out.println("Terminals: " + terminals);
    }

    public void printProductions() {
        System.out.println("Productions:");
        for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public List<String> getProductionsForNonTerminal(String nonTerminal) {
        return productions.getOrDefault(nonTerminal, Collections.emptyList());
    }

    public boolean isCFG() {
        if (!nonTerminals.contains(startingSymbol)) {
            return false;
        }

        for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
            String leftHandSide = entry.getKey();
            if (leftHandSide.split(" ").length != 1 || !nonTerminals.contains(leftHandSide)) {
                return false;
            }
        }

        return true;
    }

    public String getStartingSymbol() {
        return startingSymbol;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public boolean isTerminal(String symbol) {
        return terminals.contains(symbol);
    }

    public boolean isNonTerminal(String symbol) {
        return nonTerminals.contains(symbol);
    }
}
