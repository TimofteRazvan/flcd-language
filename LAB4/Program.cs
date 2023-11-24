using System;
using System.Runtime.CompilerServices;
using FLCD_2_Tables;

public class App
{
    public static void Menu()
    {
        Console.WriteLine("1. States");
        Console.WriteLine("2. Alphabet");
        Console.WriteLine("3. Transitions");
        Console.WriteLine("4. Initial state");
        Console.WriteLine("5. Final state");
        Console.WriteLine("6. Check determinism");
        Console.WriteLine("7. Check sequence correctness");
        Console.WriteLine("8. Quit");
    }
    public static void Main()
    {
        //SymbolTable symbolTable = new SymbolTable();
        //Scanner lexicalAnalyzer = new Scanner("p1err.txt");
        //lexicalAnalyzer.Scan();
        //Console.WriteLine(symbolTable.ToString());
        string file = "finite_automaton_det.txt";
        FiniteAutomaton fa = new FiniteAutomaton();
        fa.ReadFromFile(file);
        Menu();
        var reader = new System.IO.StreamReader(Console.OpenStandardInput());
        Console.Write(">> ");
        var option = reader.ReadLine();

        while (option != null)
        {
            if (option == "1")
            {
                Console.WriteLine("States:");
                Console.WriteLine(string.Join(" ", fa.States));
            }
            else if (option == "2")
            {
                Console.WriteLine("Alphabet:");
                Console.WriteLine(string.Join(" ", fa.Alphabet));
            }
            else if (option == "3")
            {
                Console.WriteLine("Transitions:");
                fa.PrintTransitions();
            }
            else if (option == "4")
            {
                Console.WriteLine("Initial state:");
                Console.WriteLine(fa.InitialState);
            }
            else if (option == "5")
            {
                Console.WriteLine("Final states:");
                Console.WriteLine(string.Join(" ", fa.FinalStates));
            }
            else if (option == "6")
            {
                Console.WriteLine("Check determinism:");
                Console.WriteLine(fa.CheckDeterminism());
            }
            else if (option == "7")
            {
                Console.WriteLine("Check if sequence is correct:");
                Console.WriteLine("Please write a sequence...");
                var sequence = reader.ReadLine();
                //Console.WriteLine(sequence);
                Console.WriteLine(fa.CheckCorrectness(sequence));
            }
            else
            {
                Console.WriteLine("Stopping FA...");
                return;
            }

            Console.Write(">> ");
            option = reader.ReadLine();
        }
    }
}

