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
        // Grammar grammar = new Grammar();
        // grammar.readGrammarFromFile("D:\\University\\Year - 3\\Tehnici de compilare\\Lab3\\Lab3\\src\\g2.txt"); // Provide the file name containing the grammar

        // grammar.printNonTerminals();
        // grammar.printTerminals();
        // grammar.printProductions();

        // boolean isCFG = grammar.isCFG();
        // System.out.println("Is the grammar in CFG form? " + isCFG);
        //------------------------------------------------------------------
        Parser parser = new Parser();
        parser.printFirstAndFollowSets();
    }
}
