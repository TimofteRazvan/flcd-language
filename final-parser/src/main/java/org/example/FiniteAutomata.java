package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private List<String> statesList;
    private List<String> symbolsList;
    private String initialState;
    private List<String> endStates;
    private List<Triple<String, String, String>> listOfTransitions;

    public FiniteAutomata() {
        statesList = new ArrayList<>();
        symbolsList = new ArrayList<>();
        initialState = "";
        endStates = new ArrayList<>();
        listOfTransitions = new ArrayList<>();
    }

    public void menu() {
        System.out.println("Menu:");
        System.out.println("1. States list");
        System.out.println("2. Symbols list");
        System.out.println("3. Initial state");
        System.out.println("4. End states");
        System.out.println("5. List of transitions");
        System.out.println("6. Check sequence");
        System.out.println("0. Exit");
    }

    public void start() throws IOException {
        menu();
        read();
        Scanner keyboard = new Scanner(System.in);
        Scanner keyboard1 = new Scanner(System.in);
        while(true) {
            System.out.print(">> ");
            try {
                int option = keyboard.nextInt();

                if (option == 0) {
                    break;
                } else if (option == 1) {
                    System.out.println("States list: " + statesList);
                } else if (option == 2) {
                    System.out.println("Symbols list: " + symbolsList);
                } else if (option == 3) {
                    System.out.println("Initial state: " + initialState);
                } else if (option == 4) {
                    System.out.println("End states: " + endStates);
                } else if (option == 5) {
                    System.out.println("List of transitions:");
                    for (Triple<String, String, String> t: listOfTransitions) {
                        System.out.println(t.getLeft() + "," + t.getMiddle() + "=" + t.getRight());
                    }
                } else if (option == 6) {
                    System.out.print("Input sequence: ");
                    String inputSequence = keyboard1.nextLine();

                    if (isValidSequence(inputSequence)) {
                        System.out.println("The sequence " + inputSequence + " is valid.");
                    } else {
                        System.out.println("The sequence " + inputSequence + " is not valid.");
                    }
                } else {
                    System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("The user input needs to be a number!");
                break;
            }
        }

        keyboard.close();
        keyboard1.close();
    }

    public void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/FA.in"));
        String data = null;
        data = br.readLine();
        String[] tmp = data.split(",");
        for (String state: tmp) {
            statesList.add(state);
        }
        data = br.readLine();
        tmp = data.split(",");
        for (String symbol: tmp) {
            symbolsList.add(symbol);
        }
        initialState = br.readLine();
        data = br.readLine();
        tmp = data.split(",");
        for(String state: tmp) {
            endStates.add(state);
        }
        while ((data = br.readLine()) != null) {
            tmp = data.split(",|=");
            listOfTransitions.add(new Triple<String, String, String>(tmp[0], tmp[1], tmp[2]));
        }

        br.close();
    }

    public boolean isValidSequence(String inputSequence) {
        String currentState = initialState;
    
        for (char symbol : inputSequence.toCharArray()) {
            String symbolStr = String.valueOf(symbol);
            boolean transitionFound = false;
    
            for (Triple<String, String, String> transition : listOfTransitions) {
                if (transition.getLeft().equals(currentState) && transition.getMiddle().equals(symbolStr)) {
                    currentState = transition.getRight();
                    transitionFound = true;
                    break;
                }
            }
    
            if (!transitionFound) {
                return false;
            }
        }

        return endStates.contains(currentState);
    }
    

    public void display() {
        System.out.print("States list: ");
        for (String s: statesList) {
            System.out.print(s + " ");
        }
        System.out.print("\nSymbol list: ");
        for (String s: symbolsList) {
            System.out.print(s + " ");
        }
        System.out.print("\nInitial state: " + initialState);
        System.out.print("\nEnd states list: ");
        for (String s: endStates) {
            System.out.print(s + " ");
        }
        System.out.println("\nList of transactions: ");
        for (Triple<String, String, String> t: listOfTransitions) {
            System.out.println(t.getLeft() + "," + t.getMiddle() + "=" + t.getRight());
        }
    }
}