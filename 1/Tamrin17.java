public class Tamrin17 {

    public static void main(String[] args) {
        String S = "x * x + x + x - x * x ";
        int x = 2;
        int result = evaluateExpression(S, x);
        System.out.println("Result: " + result);  
    }

    public static int evaluateExpression(String S, int x) {
        if (S.isEmpty()) {
            return 0;
        }


        S = S.replace("x", String.valueOf(x));
        S = S.replaceAll("\\s+", "");

        int len = S.length();
        int result = 0;
        int currentNumber = 0; 
        char operation = '+'; 

        for (int i = 0; i < len; i++) {
            char ch = S.charAt(i);
            
            if (Character.isDigit(ch)) {
                currentNumber = currentNumber * 10 + (ch - '0'); 
            }

            if (i == len - 1 || ch == '+' || ch == '-' || ch == '*') {
                switch (operation) {
                    case '+':
                        result += currentNumber;
                        break;
                    case '-':
                        result -= currentNumber;
                        break;
                    case '*':
                        result = result * currentNumber; 
                        break;
                }

                operation = ch;
                currentNumber = 0; 
            }
        }

        return result;
    }
}
