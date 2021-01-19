import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
public class KnapsackProblem{
    private static int numberItems;
    private static String approach ;
    private static Knapsack bestSac;
    private static Item[] items;
    private static int  cap;
    private static String bestsol;


    public static void initialize(BufferedReader in){
        try {
            numberItems = Integer.parseInt(in.readLine());
            items = new Item[numberItems];
            for(int i=0;i<numberItems;i++){
                String[] info = in.readLine().split(" ");
                Item item= new Item(info[0],Integer.parseInt(info[1]),Integer.parseInt(info[2]));
                items[i]=item;
            }
            bestSac = new Knapsack(Integer.parseInt(in.readLine()));
            cap=bestSac.getCapacity();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }
    /*public static int bruteForce(int index,String sol, int c){
        int vin,vex;
        Knapsack sac = new Knapsack(numberItems);
        if(index==numberItems || c<=0){
            return 0;
        }
        if(items[index].getWeight()<=c){
            vin=items[index].getValue()+bruteForce(index+1, sol,c-items[index].getWeight());
            vex=bruteForce(index+1, sol,c);
            return Math.max(vin,vex);
        }
        else{
            
            vex = bruteForce(index+1, sol,c);
            return vex;
        }  

    }*/
    
    public static ArrayList<ArrayList<Integer>> combinations(ArrayList<Integer> elements){
        if(elements.size()==0){
            ArrayList<ArrayList<Integer>> empty = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> emp = new ArrayList<>();
            empty.add(emp);
            return empty;
        }
        int first=elements.get(0);
        ArrayList<Integer>rest =(ArrayList<Integer>) elements.clone();
        rest.remove(0);
        ArrayList<ArrayList<Integer>> combsWithoutFirst = combinations(rest);
        ArrayList<ArrayList<Integer>> combsWithFirst = new ArrayList<ArrayList<Integer>>();
        for(ArrayList<Integer> i: combsWithoutFirst){
             ArrayList<Integer> combWithFirst = (ArrayList<Integer>) i.clone();
             combWithFirst.add(Integer.valueOf(first));
             combsWithFirst.add(combWithFirst);
        }
        combsWithFirst.addAll(combsWithoutFirst);
        return combsWithFirst;
    }

    public void dynamic(){
        //*******TODO*********
    }
    
    public static void main(String[] args) {
       // BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        //initialize(sc);
        ArrayList<Integer> elements = new ArrayList<Integer>();
        elements.add(1);
        elements.add(2);
        elements.add(3);
        ArrayList<ArrayList<Integer>> elem=combinations(elements);
        System.out.println(combinations(elements));

        


    }



    static class Knapsack{

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

        public int getValue(){
            return value;
        }

        public int getCapacity(){
            return capacity;
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