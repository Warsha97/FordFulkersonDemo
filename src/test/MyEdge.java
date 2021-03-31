package test;

public class MyEdge {

    int weight;

    public MyEdge(int weight){
        this.weight=weight;

    }

    @Override
    public String toString(){
        return (""+this.weight);
    }



    public int getWeight(){
        return this.weight;
    }
}
