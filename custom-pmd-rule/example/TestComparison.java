public class TestComparison {
    public static void main(String[] args) {
        int x = 5;
        int y = 10;

        // These should trigger violations
        if (y < x) {
            System.out.println("x is greater than y");
        }

        if (y <= x) {
            System.out.println("x is greater than or equal to y");
        }

        // These are fine (less than operators)
        if (x < y) {
            System.out.println("x is less than y");
        }

        if (x <= y) {
            System.out.println("x is less than or equal to y");
        }

        // Chained comparison
        if (0 < x && x < 100) {
            System.out.println("x is between 0 and 100");
        }

        // Greater than in while loop
        while (0 < x) {
            x--;
        }
    }
}
