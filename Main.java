import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    CountAndSum result = getCountAndSum(nodes, 1.0);
    return result.sum / result.count;
  }

  public static CountAndSum getCountAndSum(List<Node> nodes, double coef) {
    int count = 0;
    double sum = 0.0;

    if (nodes != null) {
      for (Node n : nodes) {
        count++;
        sum += n.getValue();

        if (n.getNodes() != null && !n.getNodes().isEmpty()) {
          CountAndSum cas = getCountAndSum(n.getNodes(), coef);
          count += cas.count;
          sum += coef * cas.sum;
        }
      }
    }

    return new CountAndSum(count, sum);
  }

  public record CountAndSum(int count, double sum) {}

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
