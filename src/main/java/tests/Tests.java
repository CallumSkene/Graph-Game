package tests;
import com.graph.cs308group8.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    @Test
    public void calcRouteTest() {
        ModelADT model = new Model("src/main/java/com/graph/cs308group8/data.txt");
        List<PairADT<Integer, List<NodeADT>>> pairs = new ArrayList<>();
        int weight = 19;
        List<NodeADT> list = new ArrayList<>();
        for(int value : new int[]{46, 5, 47, 10, 4, 39}) {
            list.add(new Node(value));
        }
        pairs.add(new Pair<>(weight, list));

        Assert.assertEquals(pairs, model.calcRoutes(new Node(46), new Node(39)));
    }

    @Test
    public void nodeTests(){
        NodeADT node1 = new Node(5);

        Assert.assertEquals(0, node1.compareTo(new Node(5)));
        Assert.assertTrue(node1.compareTo(new Node(3)) > 0);
        Assert.assertTrue(node1.compareTo(new Node(9)) < 0);
    }

    @Test
    public void testCompareRoute(){
        List<PairADT<Integer, List<NodeADT>>> pairs = new ArrayList<>();
        List<NodeADT> list = new ArrayList<>();
        for(int value : new int[]{17, 10, 47, 5, 1, 18, 21}) {
            list.add(new Node(value));
        }
        pairs.add(new Pair<>(9, list));

        List<NodeADT> list2 = new ArrayList<>();
        for(int value : new int[]{17, 5, 1, 18, 21}) {
            list2.add(new Node(value));
        }
        pairs.add(new Pair<>(9, list2));

        Assert.assertTrue(Model.compareRoutes("9", pairs));
        Assert.assertTrue(Model.compareRoutes("17, 10, 47, 5, 1, 18, 21", pairs));
        Assert.assertTrue(Model.compareRoutes("17, 5, 1, 18, 21", pairs));
        Assert.assertFalse(Model.compareRoutes("17, 5, 1, 18, 21, 4, 11", pairs));
        Assert.assertFalse(Model.compareRoutes("11", pairs));
    }
}
