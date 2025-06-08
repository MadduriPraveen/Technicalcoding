import java.util.*;

public class MiniInterpreter {

    private final Map<String, Integer> variables = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("Mini Interpreter Started. Type 'exit' to quit.");
        while (true) {
            System.out.print(">>> ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;
            try {
                if (line.startsWith("let ")) {
                    handleAssignment(line);
                } else if (line.startsWith("if ")) {
                    System.out.println(evaluateIf(line));
                } else {
                    System.out.println(evaluateExpression(line));
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void handleAssignment(String line) {
        String[] parts = line.substring(4).split("=");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid assignment");

        String var = parts[0].trim();
        int value = evaluateExpression(parts[1].trim());
        variables.put(var, value);
        System.out.println("Assigned: " + var + " = " + value);
    }

    private int evaluateIf(String line) {
        // Format: if x > 2 then x + 3 else x - 1
        line = line.substring(3).trim();
        String[] parts = line.split("then|else");
        if (parts.length != 3) throw new IllegalArgumentException("Invalid if statement");

        String condition = parts[0].trim();
        String trueExpr = parts[1].trim();
        String falseExpr = parts[2].trim();

        return evaluateCondition(condition) ? evaluateExpression(trueExpr) : evaluateExpression(falseExpr);
    }

    private boolean evaluateCondition(String cond) {
        String[] ops = {">=", "<=", "==", "!=", ">", "<"};
        for (String op : ops) {
            if (cond.contains(op)) {
                String[] parts = cond.split(Pattern.quote(op));
                if (parts.length != 2) throw new IllegalArgumentException("Invalid condition");

                int left = evaluateExpression(parts[0].trim());
                int right = evaluateExpression(parts[1].trim());

                switch (op) {
                    case ">": return left > right;
                    case "<": return left < right;
                    case ">=": return left >= right;
                    case "<=": return left <= right;
                    case "==": return left == right;
                    case "!=": return left != right;
                }
            }
        }
        throw new IllegalArgumentException("Invalid operator in condition");
    }

    private int evaluateExpression(String expr) {
        // Replace variables with their values
        for (String var : variables.keySet()) {
            expr = expr.replaceAll("\\b" + var + "\\b", String.valueOf(variables.get(var)));
        }

        // Evaluate using JavaScript engine (for simplicity)
        try {
            return (int) Math.round(evalMath(expr));
        } catch (Exception e) {
            throw new RuntimeException("Failed to evaluate expression: " + expr);
        }
    }

    private double evalMath(String expr) {
        // A basic parser for + - * / (no parentheses)
        Stack<Double> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c)) {
                int val = 0;
                while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
                    val = val * 10 + (expr.charAt(i++) - '0');
                }
                nums.push((double) val);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    nums.push(applyOp(ops.pop(), nums.pop(), nums.pop()));
                }
                ops.push(c);
                i++;
            } else {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }

        while (!ops.isEmpty()) {
            nums.push(applyOp(ops.pop(), nums.pop(), nums.pop()));
        }

        return nums.pop();
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : 2;
    }

    private double applyOp(char op, double b, double a) {
        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            default -> throw new IllegalArgumentException("Invalid operator: " + op);
        };
    }

    public static void main(String[] args) {
        new MiniInterpreter().run();
    }
}
