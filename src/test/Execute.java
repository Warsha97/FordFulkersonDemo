//Student name: Warsha Kiringoda
//UoW ID : w1697817
//IIT number: 2017366

package test;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import static java.awt.Color.*;


public  class Execute  {

    private JPanel panel1;
    private JPanel panel2;
    private JFrame frame;


    public static VisualizationViewer<Integer,String> display(int[][] adj, String name, Color clr){


        //Populate Graph object
        Graph<Integer, MyEdge> graph=new SparseMultigraph<>();
        for(int i=0;i<adj.length;i++){
            graph.addVertex(i);
            for(int j=0;j<adj[0].length;j++){

                if(adj[i][j]>0)
                    graph.addEdge(new MyEdge(adj[i][j]), i, j, EdgeType.DIRECTED);

            }
        }

        //VisualizationImageServer vs = new VisualizationImageServer(new CircleLayout(g), new Dimension(650, 650));
        //Initialize visualization
        Layout<Integer, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(720,620));
        VisualizationViewer<Integer,String> vs = new VisualizationViewer<Integer,String>(layout);
        vs.setPreferredSize(new Dimension(650,650));

        //Creates GraphMouse and adds to visualization
        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vs.setGraphMouse(gm);

        //Initialize JFrames
        // JFrame frame = new JFrame(name);

        JPanel panel1=new JPanel();
        panel1.setSize(700,700);
        panel1.setBorder(new LineBorder(BLACK));
        // JPanel panel2=new JPanel();
        //panel2.setSize(700,700);
        //panel2.setBorder(new LineBorder(BLACK));
        //panel1.add(vs);
        //JLabel lbl=new JLabel("RESIDUAL GRAPH");
        //panel2.add(lbl);
        //frame.add(panel1);
        //frame.add(panel2);
        //frame.getContentPane().setBackground(Color.BLUE);

        //frame.getContentPane().add(vs);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        //frame.setVisible(true);


        //Colors Vertices
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
                return clr;
            }
        };



        //Labels Edges
        float dash[] = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer =new Transformer<String, Stroke>(){
            public Stroke transform(String s) {
                return edgeStroke;
            }
        };


        //Renders Vertex colors/labels
        vs.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

        //Renders Edge labels
        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        return vs;
    }






    public static void main(String[] args) {

       JFrame frame=new JFrame("GRAPH WIZ");
       frame.setPreferredSize(new Dimension(800,600));
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setLayout(new GridLayout(1,2));

       JPanel leftPanel=new JPanel();
       leftPanel.setBorder(BorderFactory.createTitledBorder("INITIAL GRAPH"));
       leftPanel.setLayout(new BorderLayout());
       frame.add(leftPanel);

        JPanel rightPanel=new JPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder("FINAL GRAPH"));
        rightPanel.setLayout(new BorderLayout());
        frame.add(rightPanel);

        JPanel leftCenterPanel=new JPanel();
        //centerPanel.setBorder(BorderFactory.createTitledBorder("FINAL GRAPH"));
        leftPanel.add(leftCenterPanel,BorderLayout.CENTER);

        JPanel rightCenterPanel=new JPanel();
        //centerPanel.setBorder(BorderFactory.createTitledBorder("FINAL GRAPH"));
        rightPanel.add(rightCenterPanel,BorderLayout.CENTER);

        JPanel rightSouthPanel=new JPanel();

        rightSouthPanel.setLayout(new GridLayout(1,2));
        //centerPanel.setBorder(BorderFactory.createTitledBorder("FINAL GRAPH"));
        rightPanel.add(rightSouthPanel,BorderLayout.SOUTH);



        //JPanel controlPanel=new JPanel();
        //controlPanel.setBorder(BorderFactory.createTitledBorder("CONTROLLER"));
        //controlPanel.setLayout(new GridLayout(1,3));
        //frame.add(controlPanel,BorderLayout.NORTH);

        JButton button=new JButton("GENERATE NEW GRAPH");
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.white);
        leftPanel.add(button,BorderLayout.SOUTH);


       JLabel lblMaxFlow=new JLabel("MAXIMUM FLOW");
       rightSouthPanel.add(lblMaxFlow);


        JTextField txtMaxFlow=new JTextField();
        txtMaxFlow.setEditable(false);
        rightSouthPanel.add(txtMaxFlow);

        JTextArea txtPaths=new JTextArea();

        txtPaths.setEditable(false);
        rightSouthPanel.add(txtPaths);
        txtPaths.setBackground(yellow);

        txtPaths.setFont(txtPaths.getFont().deriveFont(16f));
        txtMaxFlow.setFont(txtMaxFlow.getFont().deriveFont(20f));
        txtMaxFlow.setBackground(green);
        lblMaxFlow.setFont(lblMaxFlow.getFont().deriveFont(20f));
        button.setFont(button.getFont().deriveFont(20f));




        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyGraph graph=new MyGraph();
                int[][]initialGraph=graph.generateGraph();
                initialGraph=graph.setCapities(initialGraph);

                int maxflow=graph.findMaxFlow(initialGraph,0,initialGraph.length-1);
                txtPaths.setText(null);
                int j=0;
                int txtFieldRowNo=1;
                for(int i=0; i<graph.path.size(); i++){


                    if( graph.path.get(i)==-1){
                        txtPaths.append("\npath"+txtFieldRowNo+":  ");
                        txtFieldRowNo++;
                        System.out.println(graph.path.subList(j,i));

                        System.out.println(graph.path.get(i-2));
                        txtPaths.append(String.valueOf(graph.path.get(i-2)));
                        for(int x=i; x>j; x=x-2){

                            //System.out.println(graph.path.get(x-2));
                            System.out.println(graph.path.get(x-1));
                            txtPaths.append(String.valueOf("→"+graph.path.get(x-1)));


                        }
                          j=i+1;

                    }


                }


                VisualizationViewer<Integer,String> vs1=display(initialGraph,"ini",Color.green);
                VisualizationViewer<Integer,String> vs2=display(graph.residualGraph,"fin",Color.yellow);


                if(leftCenterPanel.getComponentCount()>0){
                    leftCenterPanel.removeAll();
                    leftCenterPanel.add(vs1);
                }

                if(rightCenterPanel.getComponentCount()>0){
                    rightCenterPanel.removeAll();
                    rightCenterPanel.add(vs2);
                }
                leftPanel.add(vs1);
                rightCenterPanel.add(vs2);

                txtMaxFlow.setText(String.valueOf(maxflow));


                frame.repaint();
                frame.revalidate();
            }
        });

       frame.pack();
       frame.setVisible(true);



    }














}
