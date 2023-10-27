using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class HashTable<K, V>
    {
        private LinkedList<Pair<K, V>>[] table;
        private int size;

        public LinkedList<Pair<K, V>>[] Table { get => table; set => table = value; }
        public int Size { get => size; set => size = value; }



        public HashTable() 
        {
            table = new LinkedList<Pair<K, V>>[10];
            size = 0;
        }

        public HashTable(int cap)
        {
            table = new LinkedList<Pair<K, V>>[cap];
        }

        private int Hash(int key)
        {
            return key % size;
        }

        private int Hash(String key)
        {
            int sum = 0;
            for (int i = 0; i < key.Length; i++)
            {
                sum += key[i];
            }
            return sum % size;
        }

        private int GetHash(K key)
        {
            int hashValue = -1;
            if (key.GetType() == typeof(int)) {
                hashValue = Hash((int)(object)key);
            } else if (key.GetType() == typeof(string)) {
                hashValue = Hash((string)(object)key);
            }
            return hashValue;
        }


        private int GetIndex(K key)
        {
            int index = GetHash(key);
            return index;
        }

        public void Put(K key, V value)
        {
            size++;
            int index = GetIndex(key);
            if (table[index] == null)
            {
                table[index] = new LinkedList<Pair<K, V>>();
            }

            foreach (Pair<K, V> pair in table[index])
            {
                if (pair.Key.Equals(key))
                {
                    pair.Value = value;
                    return;
                }
            }

            table[index].AddLast(new Pair<K, V>(key, value));
        }

        public bool Contains(V value)
        {
            foreach (K key in GetKeys())
            {
                if (Get(key).Equals(value))
                {
                    return true;
                }
            }
            return false;
        }

        public V? Get(K key)
        {
            int index = GetIndex(key);
            if (table[index] != null)
            {
                foreach (Pair<K, V> pair in table[index])
                {
                    if (pair.Key.Equals(key))
                    {
                        return pair.Value;
                    }
                }
            }
            return default(V);
        }

        public List<K> GetKeys()
        {
            List<K> keys = new List<K>();
            for (int i = 0; i < table.Length; i++)
            {
                if (table[i] != null)
                {
                    foreach (Pair<K, V> pair in table[i])
                    {
                        keys.Add(pair.Key);
                    }
                }
            }
            return keys;
        }

        public List<V> GetValues()
        {
            List<V> values = new List<V>();
            for (int i = 0; i < table.Length; i++)
            {
                if (table[i] != null)
                {
                    foreach (Pair<K, V> pair in table[i])
                    {
                        values.Add(pair.Value);
                    }
                }
            }
            return values;
        }

        public override string? ToString()
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("{");

            bool first = true;
            for (int i = 0; i < table.Length; i++)
            {
                if (table[i] != null)
                {
                    foreach (Pair<K, V> pair in table[i])
                    {
                        if (!first)
                        {
                            sb.Append(", ");
                        }
                        else
                        {
                            first = false;
                        }
                        sb.Append(pair.Key).Append(" = ").Append(pair.Value);
                    }
                }
            }

            sb.Append("}");
            return sb.ToString();
        }
    }
}
