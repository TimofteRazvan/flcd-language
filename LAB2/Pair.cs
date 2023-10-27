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
    }
}
