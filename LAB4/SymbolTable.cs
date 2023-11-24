using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class SymbolTable
    {
        private HashTable<int, Object> symTable;
        private int counter = 0;

        public SymbolTable()
        {
            symTable = new HashTable<int, Object>();
        }

        public int Put(Object value)
        {
            if (counter == 0 || !symTable.Contains(value))
            {
                int key = counter++;
                return symTable.Put(key, value);
            }

            return symTable.PositionOf(value);
        }

        public bool Contains(string id)
        {
            return symTable.Contains(id);
        }

        

        public override string? ToString()
        {
            return "Symbol Table=" + symTable.ToString();
        }
    }
}