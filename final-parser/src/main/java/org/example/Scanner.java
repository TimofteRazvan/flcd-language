package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {
    private SymbolTable identifierTable;
    private SymbolTable constantTable;
    private List<String> tokens;
    private List<Pair<String, Integer>> pif;

    public Scanner() throws IOException {
        identifierTable = new SymbolTable();
        constantTable = new SymbolTable();
        tokens = new ArrayList<>();
        pif = new ArrayList<>();
        readTokens();
    }

    public void readTokens() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Lab3/src/token.in"));
        String data = null;
        while ((data = br.readLine()) != null) {
            String[] tmp = data.split(" ");
            for(String s: tmp) {
                tokens.add(s);
            }
        }
    }

    public void readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String data = null;
        int counter = 0;
        while ((data = br.readLine()) != null) {
            String[] tmp = data.split("((?=:|;| |\\(|\\)|\\[|\\]|,)|(?<=:|;| |\\(|\\)|\\[|\\]|,))");
            counter++;
            for(String s: tmp) {
                if(s.length() != 0 && s.compareTo(" ") != 0) {
                    if (tokens.contains(s)) {
                        pif.add(new Pair<>(s, -1));
                    } else {
                        if (isConstant(s)) {
                            constantTable.put(s);
                            pif.add(new Pair<>(s, constantTable.getPosition(s)));
                        } else if (isIdentifier(s)) {
                            identifierTable.put(s);
                            pif.add(new Pair<>(s, identifierTable.getPosition(s)));
                        } else {
                            System.out.println(s + " is not identifier nor constant! Line: " + Integer.toString(counter));
                        }
                    }
                }
            }
        }
    }

    private boolean isConstant(String s) {
        String regex = "^true$|^false$|^[1-9][0-9]*$|^'.'$|^0$|^-[1-9][0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches())
            return true;
        return false;
    }

    private boolean isIdentifier(String s) {
        String regex = "^[a-zA-Z][a-zA-Z0-9]*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);

        if (matcher.matches())
            return true;
        return false;
    }

    public void display(String pifFilePath, String stFilePath) throws IOException {
        System.out.println("PIF: ");
        for (Pair<String, Integer> p : pif) {
            System.out.println(p.getKey() + " ----- " + p.getValue());
        }
        System.out.println("\nIdentifier table: ");
        System.out.println(identifierTable.toString());
        System.out.println("\nConstant table: ");
        System.out.println(constantTable.toString());

        try {
            FileWriter pifFile = new FileWriter(pifFilePath);
            FileWriter stFile = new FileWriter(stFilePath);
            for (Pair<String, Integer> p : pif) {
                pifFile.write(p.getKey() + " ----- " + p.getValue() + "\n");
            }
            stFile.write("Identifier table: " + identifierTable.toString());
            stFile.write("\n");
            stFile.write("Constant table: " + constantTable.toString());
            pifFile.close();
            stFile.close();
            System.out.println("\nSuccessfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("\nAn error occurred.");
            e.printStackTrace();
        }
    }
}
