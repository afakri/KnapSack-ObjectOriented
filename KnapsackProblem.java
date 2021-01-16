import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class KnapsackProblem{
    private int numberItems;
    private String approach ;
    private Knapsack sac;


    public void initialize(BufferedReader in){
        try {
            numberItems = Integer.parseInt(in.readLine());
            for(int i=0;i<numberItems;i++){
                String[] info = in.readLine().split(" ");
                Item item= new Item(info[0],Integer.parseInt(info[1]),Integer.parseInt(info[2]));
            }
            sac = new Knapsack(Integer.parseInt(in.readLine()));
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }
    public void bruteForce(){
        //*******TODO********
    }
    public void dynamic(){
        //*******TODO*********
    }
    
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

    }



    class Knapsack{

        private Stack<Item> items;
        private int capacity;
        private int value;

        public Knapsack(){
        }

        public Knapsack(int c){
            capacity = c;
            value=0;
            items = new Stack<Item>();
        }

        public void add(Item item){
            items.push(item);
            capacity-=item.getWeight();
            value+=item.getValue();
        }

        public void remove(){
            Item item =(Item)items.pop();
            value-=item.getValue();
            capacity+=item.getWeight();
        }
    }

    class Item{

        private int value;
        private int weight;
        private String name;

        public Item(){
        }

        public Item(String n, int v, int w){
            value = v;
            weight = w;
            name = n;
        }

        public int getValue(){
            return value;
        }

        public int getWeight(){
            return weight;
        }
        public String getName(){
            return name;
        }
    }
}