using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class Pair<K, V>
    {
        private K _key;
        private V _value;

        public K Key
        {
            get
            {
                return _key;
            }
            set
            {
                _key = value;
            }
        }

        public V Value
        {
            get
            {
                return _value;
            }
            set
            {
                _value = value;
            }
        }

        public Pair(K key, V value)
        {
            this._key = key;
            this._value = value;
        }

        public override string ToString()
        {
            return this.Key + " " + this.Value;
        }

        public override bool Equals(object? obj)
        {
            return obj is Pair<K, V> pair &&
                   EqualityComparer<K>.Default.Equals(_key, pair._key) &&
                   EqualityComparer<V>.Default.Equals(_value, pair._value);
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(_key, _value);
        }
    }
}
