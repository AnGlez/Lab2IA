import java.util.Comparator;

public class Comparer implements Comparator<Node> {
	@Override
	public int compare(Node x, Node y) {
      if (x.getCost() > y.getCost())
          return 1;
      else
          return 0;
	} 
}
