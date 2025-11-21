import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.util.*;

public class DijkstraComplexityComparison {

    public static void main(String[] args) {

                int[] sizes = {50, 100, 150, 200, 250};
                double[] actualTimes = new double[sizes.length];
                double[] theoreticalValues = new double[sizes.length];

                XYSeries actualSeries = new XYSeries("Actual Time");
                XYSeries theoreticalSeries = new XYSeries("Theoretical O((V+E)log₂V)");


                for (int i = 0; i < sizes.length; i++) {
                    int nodeCount = sizes[i];
                    List<Node> graph = generateGraph(nodeCount);
                    int edgeCount = countEdges(graph);


                    for (Node node : graph) {
                        node.setDistance(Integer.MAX_VALUE);
                        node.setPath(new ArrayList<>());
                    }


                    long startTime = System.nanoTime();
                    Dijkstra.calculateShortestPath(graph.get(0), graph.get(graph.size() - 1));
                    long endTime = System.nanoTime();

                    double timeMs = (endTime - startTime) / 1_000_000.0;
                    actualTimes[i] = timeMs;

                    // Calculate theoretical (V+E)log₂V
                    double vPlusE = nodeCount + edgeCount;
                    double log2V = Math.log(nodeCount) / Math.log(2); // log base 2
                    theoreticalValues[i] = vPlusE * log2V;

                    actualSeries.add(nodeCount, timeMs);

                    System.out.printf("V=%d, E=%d, Time=%.3fms, (V+E)log₂V=%.1f%n",
                            nodeCount, edgeCount, timeMs, theoreticalValues[i]);
                }

                // Scale theoretical values to match actual times
                double scale = actualTimes[0] / theoreticalValues[0];
                for (int i = 0; i < sizes.length; i++) {
                    double scaledTheoretical = theoreticalValues[i] * scale;
                    theoreticalSeries.add(sizes[i], scaledTheoretical);

                }

                // Create comparison chart
                XYSeriesCollection comparisonDataset = new XYSeriesCollection();
                comparisonDataset.addSeries(actualSeries);
                comparisonDataset.addSeries(theoreticalSeries);

                JFreeChart comparisonChart = ChartFactory.createXYLineChart(
                        "Dijkstra: Actual vs O((V+E)log₂V)",
                        "Number of Nodes (V)",
                        "Time (ms)",
                        comparisonDataset,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );

                ChartFrame comparisonFrame = new ChartFrame("Complexity Comparison", comparisonChart);
                comparisonFrame.setSize(800, 600);
                comparisonFrame.setVisible(true);




            }

            private static List<Node> generateGraph(int nodeCount) {
                List<Node> nodes = new ArrayList<>();
                Random rand = new Random(42);

                // Create nodes
                for (int i = 0; i < nodeCount; i++) {
                    nodes.add(new Node("Node" + i));
                }

                // Create edges - sparse graph (2-4 edges per node)
                for (int i = 0; i < nodeCount; i++) {
                    int edges = 2 + rand.nextInt(100); // 2-4 edges

                    for (int j = 0; j < edges; j++) {
                        int target = rand.nextInt(nodeCount);
                        if (target != i) {
                            int weight = 1 + rand.nextInt(70);
                            nodes.get(i).addAdjacentNode(nodes.get(target), weight);
                        }
                    }
                }

                // Ensure graph is connected
                for (int i = 0; i < nodeCount - 1; i++) {
                    if (!nodes.get(i).getAdjacentNodes().containsKey(nodes.get(i + 1))) {
                        nodes.get(i).addAdjacentNode(nodes.get(i + 1), 1);
                    }
                }

                return nodes;
            }

            private static int countEdges(List<Node> graph) {
                int total = 0;
                for (Node node : graph) {
                    total += node.getAdjacentNodes().size();
                }
                return total / 2; // Undirected graph
            }
        }