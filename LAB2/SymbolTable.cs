using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class SymbolTable
    {
        private HashTable<int, Object> identifierTable;
        private HashTable<int, Object> constantTable;
        private int identifierCounter = 0;
        private int constantCounter = 0;

        public SymbolTable()
        {
            identifierTable = new HashTable<int, Object>();
            constantTable = new HashTable<int, Object>();
        }

        public void putIdentifier(Object value)
        {
            if (identifierCounter == 0 || !identifierTable.Contains(value))
            {
                int newKey = identifierCounter++;
                identifierTable.Put(newKey, value);
            }
        }

        public int getIdentifierPosition(Object value)
        {
            foreach (int key in identifierTable.GetKeys())
            {
                if (identifierTable.Get(key).Equals(value))
                {
                    return key;
                }
            }
            return -1;
        }

        public bool hasIdentifier(int identifier)
        {
            return identifierTable.Get(identifier) != null;
        }

        public void putConstant(Object value)
        {
            if (constantCounter == 0 || !constantTable.Contains(value))
            {
                int key = constantCounter++;
                constantTable.Put(key, value);
            }
                
        }

        public int getConstantPosition(Object value)
        {
            foreach (int key in constantTable.GetKeys())
            {
                if (constantTable.Get(key).Equals(value))
                {
                    return key;
                }
            }
            return -1;
        }

        public bool hasConstant(int constant)
        {
            return constantTable.Get(constant) != null;
        }

        public override string? ToString()
        {
            return "SymbolTable :" + "\nidentifierTable=" + identifierTable.ToString() + "\nconstantTable=" + constantTable.ToString();
        }
    }
}
