import java.util.*;

public class Main {
    public static void main(String[] args) {

        Node ACC = new Node("Accra");
        Node AIR = new Node("Airport");
        Node ACH = new Node("Achimota");
        Node LEG = new Node("Legon");
        Node HAT = new Node("Haatso");
        Node EAS = new Node("East Legon");
        Node MAD = new Node("Madina");
        Node ADT = new Node("Adenta");
        Node OYA = new Node("Oyarifa");

        Node TES = new Node("Teshie");
        Node SPX = new Node("Spintex");
        Node SAK = new Node("Sakumono");

        Node ASH = new Node("Ashaiman");
        Node TM1 = new Node("Tema Com 1");
        Node TM9 = new Node("Tema Com 9");

        connectTwoWay(ACC, AIR, 7);
        connectTwoWay(AIR, ACH, 6);
        connectTwoWay(ACH, LEG, 5);
        connectTwoWay(LEG, HAT, 3);
        connectTwoWay(HAT, MAD, 6);
        connectTwoWay(MAD, ADT, 8);
        connectTwoWay(ADT, OYA, 7);

        connectTwoWay(EAS, MAD, 5);
        connectTwoWay(EAS, TES, 7);
        connectTwoWay(TES, SPX, 3);
        connectTwoWay(SPX, SAK, 2);
        connectTwoWay(SAK, ASH, 5);

        connectTwoWay(AIR, SPX, 8);
        connectTwoWay(ASH, TM1, 3);
        connectTwoWay(TM1, TM9, 2);
        connectTwoWay(MAD, ASH, 10);

        List<VisualNode> visualNodes = Arrays.asList(
                new VisualNode(ACC, 100, 500),
                new VisualNode(AIR, 200, 430),
                new VisualNode(ACH, 200, 340),
                new VisualNode(LEG, 320, 360),
                new VisualNode(HAT, 340, 290),
                new VisualNode(EAS, 420, 430),
                new VisualNode(MAD, 450, 330),
                new VisualNode(ADT, 550, 260),
                new VisualNode(OYA, 650, 200),

                new VisualNode(TES, 420, 520),
                new VisualNode(SPX, 500, 510),
                new VisualNode(SAK, 580, 480),

                new VisualNode(ASH, 650, 420),
                new VisualNode(TM1, 720, 400),
                new VisualNode(TM9, 780, 360)
        );

        SimulationWindow.start(visualNodes);
    }

    public static void connectTwoWay(Node a, Node b, int w) {
        a.addAdjacentNode(b, w);
        b.addAdjacentNode(a, w);
    }
}
