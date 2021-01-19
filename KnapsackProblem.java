import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class KnapsackProblem{
    private static int numberItems;
    private static String approach ;
    private static Knapsack sac;
    private static Knapsack bestSac;
    private static ArrayList<Item> items;
    private static int  cap;
    private static int bestvalue=0;


    public static void initialize(BufferedReader in){
        try {
            numberItems = Integer.parseInt(in.readLine());
            items = new ArrayList<Item>();
            for(int i=0;i<numberItems;i++){
                String[] info = in.readLine().split(" ");
                Item item= new Item(info[0],Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                items.add(i,item);
            }
            sac = new Knapsack(Integer.parseInt(in.readLine()));
            cap=sac.getCapacity();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }
    
    public static ArrayList<Knapsack> combinations(ArrayList<Item> elements){
        if(elements.size()==0){
            ArrayList<Knapsack> empty = new ArrayList<Knapsack>();
            Knapsack emp = new Knapsack(cap);
            empty.add(emp);
            return empty;
        }
        Item first=elements.get(0);
        ArrayList<Item>  rest = new ArrayList<Item>();
        rest = (ArrayList<Item>) elements.clone();
        rest.remove(0);
        ArrayList<Knapsack> combsWithoutFirst = combinations(rest);
        ArrayList<Knapsack> combsWithFirst = new ArrayList<Knapsack>();
        for(Knapsack i: combsWithoutFirst){
             Knapsack combWithFirst = new Knapsack();
             combWithFirst.clone(i);
             combWithFirst.add(first);
             if(combWithFirst.getValue()>bestvalue && combWithFirst.getCapacity()>=0){
                 bestSac=combWithFirst;
                 bestvalue=bestSac.getValue();
             }
             combsWithFirst.add(combWithFirst);
        }
        combsWithFirst.addAll(combsWithoutFirst);
        
        return combsWithFirst;
    }

    public void dynamic(){
        //*******TODO*********
    }
    
    public static void main(String[] args) {
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        initialize(sc);
        ArrayList<Knapsack> elem=combinations(items);
        System.out.println(bestSac.toString());
        
    }



    static class Knapsack{

        private ArrayList<Item> items;
        private int capacity;
        private int value;

        public Knapsack(){
        }

        public Knapsack(int c){
            capacity = c;
            value=0;
            items = new ArrayList<Item>();
        }

        public void add(Item item){
            items.add(item);
            capacity-=item.getWeight();
            value+=item.getValue();
        }

        public void add(Item item, int index){
            items.add(index,item);
            capacity-=item.getWeight();
            value+=item.getValue();
        }
        public String toString(){
            String sol =getValue()+"\n";
            for(Item i:items){
                sol+=i.getName()+" ";
            }
            return sol;
        }

        public void remove(int index){
            Item item =(Item)items.remove(index);
            value-=item.getValue();
            capacity+=item.getWeight();
        }

        public int getValue(){
            return value;
        }

        public int getCapacity(){
            return capacity;
        }
        public ArrayList<Item> getItems(){
            return items;
        }
        public Item getItem(int index){
            return items.get(index);
        }
        public void clone(Knapsack s){
            capacity=s.getCapacity();
            value=s.getValue();
            items=(ArrayList<Item>) s.getItems().clone();
            
        }

    }

    static class Item{

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