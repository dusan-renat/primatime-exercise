import java.util.*;

class Main {
  public static void main(String[] args) {
    // 1,
    // 2 -> 3, 4
    // 5 -> 6 -> 7
    List<Node> list = new ArrayList<>();
    list.add(node(1));
    list.add(node(2, new Node[]{ node(3), node(4) }));
    list.add(node(5, new Node[]{ node(6, new Node[]{ node(7) })}));

    // should return 4
    // because (1 + 2 + 3 + 4 + 5 + 6 + 7) / 7 = 4
    System.out.println(getMeanValue(list));
  }

  public static interface Node {
    public double getValue();
    public List<Node> getNodes();
  }

  public static double getMeanValue(List<Node> nodes) {
    // please implement algorithm for mean value of all given nodes
    // each node has own value and sub-nodes of the same structure,
    // mean value should be calculated across all values in the tree
    return getMeanValue(nodes, 1.0);
  }

  public static double getMeanValue(List<Node> nodes, double coef) {
    int count = 0;
    double sum = 0.0;

    if (nodes != null) {
      Queue<NodeAndDepth> queue = new LinkedList<>();

      nodes
              .stream()
              .map( n -> new NodeAndDepth(n, 0))
              .forEach(queue::add);

      while (!queue.isEmpty()) {
        NodeAndDepth nad = queue.poll();
        count++;
        sum += nad.node.getValue() * Math.pow(coef, nad.depth);

        if (nad.node.getNodes() != null) {
          for (Node n : nad.node.getNodes()) {
            queue.add(new NodeAndDepth(n, nad.depth + 1));
          }
        }
      }
    }

    return sum / count;
  }

  public record NodeAndDepth(Node node, int depth) {}

  // builders

  public static Node node(double value) {
    return node(value, new Node[]{});
  }

  public static Node node(double value, Node[] nodes) {
    return new Node() {
      public double getValue() {
        return value;
      }
      public List<Node> getNodes() {
        return Arrays.asList(nodes);
      }
    };
  }
}
