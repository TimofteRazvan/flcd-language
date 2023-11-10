using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class Scanner
    {
        private List<string> operators;
        private List<string> separators;
        private List<string> keywords;
        private SymbolTable table;
        private List<Pair<string, int>> pif;
        private string file;
        private string line;
        private int crtLine = 1;
        private int crtChar = 0;

        public Scanner(string file)
        {
            this.operators = new List<string>() { "+", "-", "*", "/", ">=", "<=", "=", "==", ">", "<", "!=", "mod", "%" };
            this.separators = new List<string>() { "{", "}", "(", ")", "[", "]", ",", ";", "space", "newline", ":" };
            this.keywords = new List<string>() { "return", "int", "string", "bool", "array", "if", "else", "while", "write", "read", "char" };
            this.table = new SymbolTable();
            this.pif = new List<Pair<string, int>>();
            this.file = file;
        }

        public void Scan()
        {
            try
            {
                string[] lines = File.ReadAllLines(Path.Combine("D:", "VISUAL_STUDIO", "FLCD_2_Tables", "FLCD_2_Tables", file));
                
                //until eof, read lines
                foreach (string line in lines)
                {
                    crtChar = 0;
                    this.line = line;
                    
                    //Console.WriteLine();
                    //detect what each char is as long as we haven't reached end of line
                    while (crtChar < line.Length)
                    {
                        Detect();
                    }

                    crtLine++;
                }

                WriteToFile();
            }
            catch (CustomException e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void Detect()
        {
            int errType = 0;
            if (char.IsWhiteSpace(line[crtChar]))
            {
                crtChar++;
                return;
            }
            if (CheckString())
            {
                return;
            }
            if (CheckInt())
            {
                return;
            }
            if (CheckIdentifier())
            {
                return;
            }
            if (CheckToken())
            {
                return;
            }
            throw new CustomException("LEXICAL ERROR!\n" + crtLine + ":" + crtChar + " in " + line);
        }

        private bool CheckString()
        {
            Regex regex = new Regex("^\"([A-Za-z0-9 !@#$%^&*_]+)\"");
            Match matcher = regex.Match(line.Substring(crtChar));
            if (!regex.IsMatch(line.Substring(crtChar)))
                return false;

            string strConstant = matcher.Groups[0].Value;
            crtChar += strConstant.Length;
            int pos = table.Put(strConstant);
            pif.Add(new Pair<string, int>("stringConstant", pos));
            return true;
        }

        private bool CheckInt()
        {
            Regex regex = new Regex("^([-]?[1-9][0-9]*|0)");
            Match matcher = regex.Match(line.Substring(crtChar));
            if (!matcher.Success)
                return false;

            
            Regex invalidIntRegex = new Regex("^([-]?[1-9][0-9]*|0)[a-zA-Z!@#$%^&*_]");
            if (invalidIntRegex.IsMatch(line.Substring(crtChar)))
                return false;

            string intConstant = matcher.Groups[1].Value;
            crtChar += intConstant.Length;
            int pos = table.Put(intConstant);
            pif.Add(new Pair<string, int>("intConstant", pos));
            return true;
        }

        private bool CheckIdentifier()
        {
            Regex regex = new Regex("^[a-zA-Z_][a-zA-Z0-9_]*");
            Match matcher = regex.Match(line.Substring(crtChar));
            if (!matcher.Success)
                return false;

            string identifier = matcher.Groups[0].Value;
            if (keywords.Contains(identifier))
                return false;

            crtChar += identifier.Length;
            int pos = table.Put(identifier);
            pif.Add(new Pair<string, int>("identifier", pos));
            return true;
        }

        private bool CheckToken()
        {
            string crtToken = line.Substring(crtChar).Split(' ')[0];
            foreach (var op in operators)
            {
                if (op.Equals(crtToken))
                {
                    crtChar += op.Length;
                    pif.Add(new Pair<string, int>(op, -1));
                    return true;
                }
                else if (crtToken.StartsWith(op))
                {
                    crtChar += op.Length;
                    pif.Add(new Pair<string, int>(op, -1));
                    return true;
                }
            }

            foreach (var sep in separators)
            {
                if (sep.Equals(crtToken))
                {
                    crtChar += sep.Length;
                    pif.Add(new Pair<string, int>(sep, -1));
                    return true;
                }
                else if (crtToken.StartsWith(sep))
                {
                    crtChar += sep.Length;
                    pif.Add(new Pair<string, int>(sep, -1));
                    return true;
                }
            }

            foreach (var keyword in keywords)
            {
                if (crtToken.Equals(keyword))
                {
                    crtChar += keyword.Length;
                    pif.Add(new Pair<string, int>(keyword, -1));
                    return true;
                }
            }

            return false;
        }

        private void WriteToFile()
        {
            try
            {
                string pifOutFilePath = Path.Combine("D:", "VISUAL_STUDIO", "FLCD_2_Tables", "FLCD_2_Tables", file + "_pif.out");
                string stOutFilePath = Path.Combine("D:", "VISUAL_STUDIO", "FLCD_2_Tables", "FLCD_2_Tables", file + "_ST.out");

                using (StreamWriter pifFileWriter = new StreamWriter(pifOutFilePath))
                {
                    foreach (Pair<string, int> p in pif)
                    {
                        pifFileWriter.WriteLine(p.Key + " -> " + p.Value);
                    }
                }

                using (StreamWriter stFileWriter = new StreamWriter(stOutFilePath))
                {
                    stFileWriter.Write(table.ToString());
                }
            }
            catch (CustomException e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
