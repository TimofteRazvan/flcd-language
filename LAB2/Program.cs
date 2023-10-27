using FLCD_2_Tables;

public class App
{
    public static void Main()
    {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.putConstant("4");
        Console.WriteLine("add 4 constant\n" + symbolTable.ToString());
        symbolTable.putIdentifier("a");
        Console.WriteLine("add a identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("b");
        Console.WriteLine("add b identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("a");
        Console.WriteLine("add a identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("a");
        Console.WriteLine("add a identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("a");
        Console.WriteLine("add a identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("b");
        Console.WriteLine("add b identifier\n" + symbolTable.ToString());
        symbolTable.putIdentifier("b");
        Console.WriteLine("add b identifier\n" + symbolTable.ToString());
        symbolTable.putConstant("3");
        Console.WriteLine("add 3 constant\n" + symbolTable.ToString());
        symbolTable.putConstant("3");
        Console.WriteLine("add 3 constant\n" + symbolTable.ToString());
        symbolTable.putConstant("3");
        Console.WriteLine("add 3 constant\n" + symbolTable.ToString());
        symbolTable.putConstant("4");
        Console.WriteLine("add 4 constant\n" + symbolTable.ToString());
    }
}

