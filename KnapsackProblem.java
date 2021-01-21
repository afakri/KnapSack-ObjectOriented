/**
 * @author Ayman Fakri
 * @email afakr040@uottawa.ca
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class KnapsackProblem {

    public static void main(String[] args) {
        BufferedReader sc;
        KnapsackSolution solution = new KnapsackSolution();
        try {
            sc = new BufferedReader(new FileReader(args[0]));
            solution.initialize(sc);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        // launchs the bruteforce approach
        if (args[1].endsWith("F")) {
            solution.bruteForce(solution.items);
            System.out.println(solution.bestSac.toString());
        }
        // launchs the dynamic approach
        else {
            System.out.println(solution.dynamic().toString());
        }

    }
    //solution class
    static class KnapsackSolution {
        private  int numberItems;
        private  Knapsack sac;
        private  Knapsack bestSac; // desired output for bruteforce
        private  ArrayList<Item> items;
        private  int cap; // capacity of the Knapsack
        private  int bestvalue = 0;

        public KnapsackSolution(){

        }
        // Setting up the variables
        public void initialize(BufferedReader in) {
            try {
                numberItems = Integer.parseInt(in.readLine());
                items = new ArrayList<Item>();
                for (int i = 0; i < numberItems; i++) {
                    String[] info = in.readLine().split(" ");
                    Item item = new Item(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]));
                    items.add(i, item);
                }
                sac = new Knapsack(Integer.parseInt(in.readLine()));
                cap = sac.getCapacity();
                in.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        // Bruteforce method generating all possible combination of knapsacks
        @SuppressWarnings("unchecked")
        public ArrayList<Knapsack> bruteForce(ArrayList<Item> elements) {
            // base case
            if (elements.size() == 0) {
                ArrayList<Knapsack> empty = new ArrayList<Knapsack>();
                Knapsack emp = new Knapsack(cap);
                empty.add(emp);
                return empty;
            }
            // first item of the list
            Item first = elements.get(0);
            // list without the first item
            ArrayList<Item> rest = new ArrayList<Item>();
            rest = (ArrayList<Item>) elements.clone();
            rest.remove(0);
            // we generate the combinations that dont contain the first element recursivly
            ArrayList<Knapsack> combsWithoutFirst = bruteForce(rest);
            ArrayList<Knapsack> combsWithFirst = new ArrayList<Knapsack>();
            // for every combination we add the first element to it
            for (Knapsack i : combsWithoutFirst) {
                Knapsack combWithFirst = new Knapsack();
                combWithFirst.clone(i);
                combWithFirst.add(first);
                // keeping track of the solution
                if (combWithFirst.getValue() > bestvalue && combWithFirst.getCapacity() >= 0) {
                    bestSac = combWithFirst;
                    bestvalue = bestSac.getValue();
                }
                // adding all the combinations
                combsWithFirst.add(combWithFirst);
            }
            combsWithFirst.addAll(combsWithoutFirst);
            // returning all the combinations
            return combsWithFirst;
        }

        // Dynamic method
        public Knapsack dynamic() {
            // solution matrix
            Knapsack[][] solution = new Knapsack[items.size() + 1][cap + 1];
            Knapsack empty = new Knapsack(cap);
            // filling the first row with empty Knapsacks
            Arrays.fill(solution[0], empty);
            for (int i = 1; i < items.size() + 1; i++) {
                for (int j = 0; j < cap + 1; j++) {
                    // item doesnt fit
                    if (items.get(i - 1).getWeight() > j) {
                        Knapsack temp = new Knapsack(j);
                        temp.clone(solution[i - 1][j], j);
                        solution[i][j] = temp;
                    } else {
                        // Knapsack same as the previous cell with addition of the new item
                        Knapsack temp2 = new Knapsack(j);
                        temp2.clone(solution[i - 1][j - items.get(i - 1).getWeight()], j);
                        temp2.add(items.get(i - 1));
                        // verifiying temp2 is a valid Knapsack
                        if ((items.get(i - 1).getValue() + solution[i - 1][j - items.get(i - 1).getWeight()]
                                .getValue() > solution[i - 1][j].getValue()) && temp2.getCapacity() >= 0) {
                            solution[i][j] = temp2;
                        } else {
                            Knapsack temp3 = new Knapsack(j);
                            temp3.add(items.get(i - 1));
                            solution[i][j] = temp3;
                        }

                    }
                }
            }
            return solution[items.size()][cap];
        }
    }

    // Knapsack class
    static class Knapsack {

        private ArrayList<Item> items;
        private int capacity;
        private int value;

        public Knapsack() {
        }

        public Knapsack(int c) {
            capacity = c;
            value = 0;
            items = new ArrayList<Item>();
        }

        // adding item to the end of the array
        public void add(Item item) {
            items.add(item);
            capacity -= item.getWeight();
            value += item.getValue();
        }

        // adding item to the desired index
        public void add(Item item, int index) {
            items.add(index, item);
            capacity -= item.getWeight();
            value += item.getValue();
        }

        // String output of the Knapsack
        public String toString() {
            String sol = getValue() + "\n";
            for (Item i : items) {
                sol += i.getName() + " ";
            }
            return sol;
        }

        public void remove(int index) {
            Item item = (Item) items.remove(index);
            value -= item.getValue();
            capacity += item.getWeight();
        }

        public int getValue() {
            return value;
        }

        public int getCapacity() {
            return capacity;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public Item getItem(int index) {
            return items.get(index);
        }

        // make the Knapsack a clone of the chosen one
        @SuppressWarnings("unchecked")
        public void clone(Knapsack s) {
            capacity = s.getCapacity();
            value = s.getValue();
            items = (ArrayList<Item>) s.getItems().clone();

        }

        // make the Knapsack a clone of the chosen one with the desired capacity
        @SuppressWarnings("unchecked")
        public void clone(Knapsack s, int cap) {
            capacity = s.getCapacity();
            capacity = cap;
            value = s.getValue();
            items = (ArrayList<Item>) s.getItems().clone();

        }

        @SuppressWarnings("unchecked")
        public void setItems(ArrayList<Item> it) {
            items = (ArrayList<Item>) it.clone();
        }

    }

    static class Item {

        private int value;
        private int weight;
        private String name;

        public Item() {
        }

        public Item(String n, int v, int w) {
            value = v;
            weight = w;
            name = n;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }

        public String getName() {
            return name;
        }
    }
}