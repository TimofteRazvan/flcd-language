package org.example;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static void main(String[] args) throws Exception {
        // Scanner scanner = new Scanner();
        // scanner.readFile("Lab3/src/Lab1-1err.txt");
        // scanner.display("Lab3/src/PIF.out", "Lab3/src/ST.out");
        // FiniteAutomata fa = new FiniteAutomata();
        // fa.start();
        // fa.read();
        // fa.display();

        // String userInput = "011";

        // if (fa.isValidSequence(userInput)) {
        //     System.out.println("The sequence " + userInput + " is valid.");
        // } else {
        //     System.out.println("The sequence " + userInput + " is not valid.");
        // }
        //--------------------------------------------------------------
        Grammar grammar = new Grammar();
        grammar.readGrammarFromFile("C:\\Users\\Robert\\IdeaProjects\\FLCD_Parser\\src\\main\\java\\org\\example\\g5.txt"); // Provide the file name containing the grammar

        grammar.printNonTerminals();
        grammar.printTerminals();
        grammar.printProductions();

        // boolean isCFG = grammar.isCFG();
        // System.out.println("Is the grammar in CFG form? " + isCFG);
        //------------------------------------------------------------------
        Parser parser = new Parser();
        parser.printParsingTable();
        parser.printFirstAndFollowSets();
        parser.printParseTree(parser.getParseTreeRoot(), 0);

        // ParserOutput parserOutput = new ParserOutput();
        // String input = "R +";
        // ParserOutput.Node parsingTree = parserOutput.transformParsingTree(input);
        // System.out.println("Parsing Tree:");
        // parserOutput.printToScreen(parsingTree);
    }
}
