package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MyGraph {

    int max;
    int min;
    int vertices;
    int[][]residualGraph;
    //ArrayList<ArrayList>pathList=new ArrayList<>();
    ArrayList<Integer>path=new ArrayList<>();



    public int[][] generateGraph(){
        //defining maximum and the minimum number of vertices
        max=12;
        min=6;
        //generating a random number for number of Vertices (between given range)
        vertices=(int)(Math.random() * ((max - min) + 1)) + min;

        System.out.println("number of vertices:"+vertices);

        //declaring a 2 dimensional array to create the matrix
        int graph[][]=new int[vertices][vertices];

        //setting 0 to the indexes where it should always be 0
        for(int i=0;i<graph.length;i++) {
            graph[graph.length - 1][i]=0;
            graph[i][0]=0;
            graph[i][i]=0;
        }

        //declaring and initializing  m1 and m2 which becomes 1 if the required index are connected
        int m1=0;
        int m2=0;

        //do-while 1st row has at least one 1
        //last column has at least one 1
        //2 nearby vertices at least have one connection between them (by fulfilling this we can make sure
        // that the network will not going to be disconnected)
        do {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < vertices; j++) {
                    if ((i != graph.length - 1 && j != 0) && i != j) {
                        graph[i][j] = createConnection();

                    }

                }
            }

            for (int n = 0; n < vertices; n++) {
                if (graph[0][n] == 1) {//if first row has at least one connection,
                    m1 = 1;            //m1 becomes 1

                }
                if (graph[n][graph.length - 1] == 1) {//if last column has at least one connection
                    m2 = 1;                           //m2 becomes 1

                }

            }

            for (int n = 1; n < vertices-1; n++) {
                if(graph[n][n+1]!=1 && graph[n+1][n]!=1){// if there isn't any connection between 2 nearby nodes
                    graph[n][n+1]=1;                    //make a connection
                }
            }


        }while(m1==0||m2==0);



        for(int[] net:graph){

            System.out.println(Arrays.toString(net));
        }



        return graph;
    }



    public int createConnection(){
        return (int)(Math.random() * ((1 - 0) + 1)) + 0;
    }



    public int[][] setCapities(int rowgraph[][]){
        rowgraph=generateGraph();

        for (int i = 0; i < rowgraph.length; i++) {
            for (int j = 0; j < vertices; j++) {
                if (rowgraph[i][j]==1) {
                    rowgraph[i][j] = generateRandomC();
                }

            }
        }

        for(int[] net:rowgraph){

            System.out.println(Arrays.toString(net));
        }

        return rowgraph;
    }



    public int generateRandomC(){
        int maxC=20;
        int minC=5;
        return (int)(Math.random() * ((maxC - minC) + 1)) + minC;
    }

    //BFS
    public boolean bfs(int rGraph[][], int s, int t, int parent[]){
        // Create a visited array and mark all vertices as not
        // visited
        boolean visited[] = new boolean[vertices];
        for(int i=0; i<vertices; ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        //BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();

            for (int v=0; v<vertices; v++)
            {
                if (visited[v]==false && rGraph[u][v] > 0)
                {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }

            }



        }



        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }



    //MAXIMUM FLOW
    public int findMaxFlow(int graph[][], int s, int t){
        int u, v;

        int rGraph[][] = new int[vertices][vertices];

        for (u = 0; u < vertices; u++)
            for (v = 0; v < vertices; v++)
                rGraph[u][v] = graph[u][v];

        // This array is filled by BFS and to store path
        int parent[] = new int[vertices];

        int max_flow = 0;  // There is no flow initially

        // Augment the flow while there is path from source
        // to sink


        while (bfs(rGraph, s, t, parent))
        {

            //System.out.println(Arrays.toString(parent));

            int path_flow = Integer.MAX_VALUE;
            for (v=t; v!=s; v=parent[v])
            {
                u = parent[v];
                path_flow = Math.min(path_flow, rGraph[u][v]); //bottle neck capacity
                //System.out.println(u+","+v);

                path.add(u);//add
                path.add(v);//add

            }
            path.add(-1);//add


            // update residual capacities of the edges and
            // reverse edges along the path
            for (v=t; v != s; v=parent[v])
            {
                u = parent[v];
                rGraph[u][v] -= path_flow;
                rGraph[v][u] += path_flow;
            }


            // Add path flow to overall flow
            max_flow += path_flow;
        }
        residualGraph=rGraph;

        // Return the overall flow
        return max_flow;
    }


}
