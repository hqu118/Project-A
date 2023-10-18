package main;

public class GraphControl {
    private Graph graph;
    private final GraphUI sUI;

    /**
     * The constructor that initializes all private members
     */
    public GraphControl() {
        sUI = new GraphUI();
    }

    private void createGraph() {
        graph = new Graph(sUI.getRelationElements(), sUI.getWeightElements());
    }
}