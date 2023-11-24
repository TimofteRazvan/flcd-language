using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FLCD_2_Tables
{
    public class FiniteAutomaton
    {
        private List<string> states;
        private List<string> alphabet;
        private Dictionary<Pair<string, string>, List<string>> transitions;
        private string initialState;
        private List<string> finalStates;

        public List<string> States
        {
            get => states;
            set => states = value ?? throw new ArgumentNullException(nameof(value));
        }

        public List<string> Alphabet
        {
            get => alphabet;
            set => alphabet = value ?? throw new ArgumentNullException(nameof(value));
        }

        public Dictionary<Pair<string, string>, List<string>> Transitions
        {
            get => transitions;
            set => transitions = value ?? throw new ArgumentNullException(nameof(value));
        }

        public string InitialState
        {
            get => initialState;
            set => initialState = value ?? throw new ArgumentNullException(nameof(value));
        }

        public List<string> FinalStates
        {
            get => finalStates;
            set => finalStates = value ?? throw new ArgumentNullException(nameof(value));
        }

        public FiniteAutomaton()
        {
            states = new List<string>();
            alphabet = new List<string>();
            finalStates = new List<string>();
            transitions = new Dictionary<Pair<string, string>, List<string>>();
        }

        public FiniteAutomaton(List<string> states, List<string> alphabet, Dictionary<Pair<string, string>, List<string>> transitions, string initialState, List<string> finalStates)
        {
            this.states = states;
            this.alphabet = alphabet;
            this.transitions = transitions;
            this.initialState = initialState;
            this.finalStates = finalStates;
        }

        public void ReadFromFile(string file)
        {
            using (StreamReader reader =
                   new StreamReader(Path.Combine("D:", "VISUAL_STUDIO", "FLCD_2_Tables", "FLCD_2_Tables", file)))
            {
                string? line;
                while ((line = reader.ReadLine()) != null)
                {
                    //var line = reader.ReadLine();
                    if (!string.IsNullOrEmpty(line))
                    {
                        if (line.Equals("States:"))
                        {
                            states.AddRange(reader.ReadLine().Trim().Split(' '));
                        }
                        else if (line.Equals("Alphabet:"))
                        {
                            alphabet.AddRange(reader.ReadLine().Split(' '));
                        }
                        else if (line.Equals("Initial State:"))
                        {
                            initialState = reader.ReadLine();
                        }
                        else if (line.Equals("Final States:"))
                        {
                            finalStates.AddRange(reader.ReadLine().Split(' '));
                        }
                        else if (line.Equals("Transitions:"))
                        {
                            string transitionSeq;
                            while ((transitionSeq = reader.ReadLine()) != null)
                            {
                                string[] lineTransitions = transitionSeq.Split(' ');
                                if (states.Contains(lineTransitions[0]) && alphabet.Contains(lineTransitions[1]) &&
                                    states.Contains(lineTransitions[2]))
                                {
                                    Pair<string, string> pair =
                                        new Pair<string, string>(lineTransitions[0], lineTransitions[1]);
                                    // if the pair already exists as a key, add the transition to the end
                                    if (transitions.ContainsKey(pair))
                                    {
                                        transitions[pair].Add(lineTransitions[2]);
                                    }
                                    // if no such key-value pair already exists, create it
                                    else
                                    {
                                        List<string> values = new List<string>() { lineTransitions[2] };
                                        transitions.Add(pair, values);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public bool CheckDeterminism()
        {
            foreach (Pair<string, string> pair in this.transitions.Keys)
            {
                if (this.transitions[pair].Count > 1)
                {
                    return false;
                }
            }

            return true;
        }

        public bool CheckCorrectness(string sequence)
        {
            if (!CheckDeterminism())
            {
                return false;
            }

            string state = initialState;
            //Console.WriteLine(state);

            while (sequence.Length > 0)
            {
                Pair<string, string> pair = new Pair<string, string>(state, sequence.Substring(0, 1));
                Console.WriteLine(pair.Key + " " + pair.Value);
                if (!transitions.Keys.Contains(pair))
                {
                    //Console.WriteLine(transitions.Keys.First());
                    Console.WriteLine(this.transitions.Keys.First().Key + " " + this.transitions.Keys.First().Value);
                    //Console.WriteLine("how the fuck");
                    return false;
                }

                state = transitions[pair].First();
                Console.WriteLine(state);
                sequence = sequence.Substring(1);
            }

            return finalStates.Contains(state);
        }

        public void PrintTransitions()
        {
            foreach (Pair<string, string> pair in this.transitions.Keys)
            {
                Console.Write(pair.Key + " " + pair.Value + " ");
                foreach (string str in this.transitions[pair])
                {
                    Console.WriteLine(str + " ");
                }
            }
        }
    }
}
