import java.util.List;
import java.util.Map;

public class ComparisonTest {
    public void testSimpleComparisons() {
        int x = 5;
        int y = 10;

        // Simple variable comparisons - SHOULD be refactored
        if (y < x) { }
        if (y <= x) { }
    }

    public void testMethodCalls() {
        // Method calls on objects - SHOULD be refactored
        if (5 < this.getValue()) { }
        if (10 <= this.getValue()) { }
    }

    public void testChainedMethodCalls() {
        // Chained method calls - SHOULD be refactored
        if (0 < this.getList().size()) { }
        if (1 <= this.getList().size()) { }
    }

    public void testPropertyAccess() {
        // Property access and method calls - SHOULD be refactored (like QueryTest case)
        Object[] items = new Object[0];
        if (0 < items.length) { }
        if (1 <= items.length) { }
    }

    public void testComplexExpression() {
        // Complex: literal <= method().property
        if (1 <= getParameterTypes().length) { }
    }

    public void testGenerics() {
        // Generics - should NOT be changed
        List<Integer> intList = null;
        Map<String, Integer> map = null;
        List<String> strings = null;
    }

    public void testMixedWithGenerics() {
        // Has generics AND comparisons on same line
        List<Integer> items = null;
        if (0 < items.size()) { }  // This comparison SHOULD be refactored
    }

    // Helper methods
    private int getValue() { return 0; }
    private List<?> getList() { return null; }
    private Object[] getParameterTypes() { return new Object[0]; }
}
