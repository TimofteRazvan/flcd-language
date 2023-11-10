using FLCD_2_Tables;

public class App
{
    public static void Main()
    {
        SymbolTable symbolTable = new SymbolTable();
        Scanner lexicalAnalyzer = new Scanner("p1.txt");
        lexicalAnalyzer.Scan();
        Console.WriteLine(symbolTable.ToString());
        
    }
}

