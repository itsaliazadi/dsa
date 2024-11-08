import java.util.Arrays;

public class BigNumber {
    private int[] num;
    private int length;
    private boolean sign;

    public BigNumber() {
        sign = false;
        length = 1;
        num = new int[length];
        num[0] = 0;
    }

    public BigNumber(int number) {
        if (number == 0) {
            sign = false;
            length = 1;
            num = new int[length];
            num[0] = 0;
        } else {
            sign = number < 0;
            number = Math.abs(number);
            length = (int) Math.log10(number) + 1;
            num = new int[length];

            for (int i = 0; i < length; i++) {
                num[i] = number % 10;
                number /= 10;
            }
        }
    }

    public BigNumber(String number) {
        if (number.charAt(0) == '-') {
            sign = true;
            number = number.substring(1);
        } else {
            sign = false;
        }
        length = number.length();
        num = new int[length];

        for (int i = 0; i < length; i++) {
            num[length - 1 - i] = number.charAt(i) - '0';
        }
    }

    public BigNumber(int[] number, int len, boolean sign) {
        this.length = len;
        this.sign = sign;
        num = Arrays.copyOf(number, len);
    }

    public BigNumber(BigNumber other) {
        this.length = other.length;
        this.sign = other.sign;
        this.num = Arrays.copyOf(other.num, other.length);
    }

    public void printNumber() {
        if (sign) {
            System.out.print("-");
        }
        for (int i = length - 1; i >= 0; i--) {
            System.out.print(num[i]);
        }
        System.out.println();
    }

    private static boolean isGreater(BigNumber number1, BigNumber number2) {
        if (number1.length != number2.length) {
            return number1.length > number2.length;
        } else {
            for (int i = number1.length - 1; i >= 0; i--) {
                if (number1.num[i] != number2.num[i]) {
                    return number1.num[i] > number2.num[i];
                }
            }
        }
        return false;
    }

    public static BigNumber sum(BigNumber number1, BigNumber number2) {
        int maxLen = Math.max(number1.length, number2.length);
        int[] sumRes = new int[maxLen + 1];
        int carry = 0;

        if (number1.sign == number2.sign) {
            for (int i = 0; i < maxLen; i++) {
                int digit1 = (i < number1.length) ? number1.num[i] : 0;
                int digit2 = (i < number2.length) ? number2.num[i] : 0;

                int res = digit1 + digit2 + carry;
                if (res >= 10) {
                    carry = 1;
                    res -= 10;
                } else {
                    carry = 0;
                }
                sumRes[i] = res;
            }

            if (carry == 1) {
                sumRes[maxLen] = 1;
                return new BigNumber(sumRes, maxLen + 1, number1.sign);
            } else {
                return new BigNumber(sumRes, maxLen, number1.sign);
            }
        } else {
            return number1.subtract(new BigNumber(number2.num, number2.length, !number2.sign));
        }
    }

    public BigNumber subtract(BigNumber number2) {
        if (this.sign == number2.sign) {
            boolean resSign = this.sign;
            BigNumber larger, smaller;

            if (isGreater(this, number2)) {
                larger = this;
                smaller = number2;
            } else {
                larger = number2;
                smaller = this;
                resSign = !resSign;
            }

            int maxLen = larger.length;

            int[] subtractionRes = new int[maxLen];
            int borrow = 0;

            for (int i = 0; i < maxLen; i++) {
                int digit1 = (i < larger.length) ? larger.num[i] : 0;
                int digit2 = (i < smaller.length) ? smaller.num[i] : 0;

                digit1 -= borrow;

                if (digit2 > digit1) {
                    borrow = 1;
                    digit1 += 10;
                } else {
                    borrow = 0;
                }
                subtractionRes[i] = digit1 - digit2;
            }

            int resultLen = maxLen;
            while (resultLen > 1 && subtractionRes[resultLen - 1] == 0) {
                resultLen--;
            }

            return new BigNumber(subtractionRes, resultLen, resSign);
        } else {
            return sum(this, new BigNumber(number2.num, number2.length, !number2.sign));
        }
    }

    public BigNumber leftShift() {
        BigNumber result = new BigNumber(this); 
        return sum(result, result); 
    }

    public BigNumber rightShift() {
        if (length == 1 && num[0] == 0) {
            return new BigNumber(); 
        }
        
        boolean resultSign = sign;

        int newLen = (length == 1 && num[0] == 0) ? 1 : length;  
        int[] newNum = new int[newLen]; 

        int carry = 0; 
        for (int i = length - 1; i >= 0; i--) {
            int current = num[i] + carry * 10; 
            newNum[i] = current / 2;  
            carry = current % 2;
        }

        if (newNum[newLen - 1] == 0) { 
            newLen--;
        }

        return new BigNumber(newNum, newLen, resultSign); 
    }

    public BigNumber multiply(BigNumber number2) {
        BigNumber larger, smaller;

        if (isGreater(this, number2)) {
            larger = this;
            smaller = number2;
        } else {
            larger = number2;
            smaller = this;
        }

        int[] multiplicationRes = new int[larger.length + smaller.length];
        
        for (int i = 0; i < smaller.length; i++) {
            int carry = 0;

            for (int j = 0; j < larger.length; j++) {
                int res = multiplicationRes[i + j] + (smaller.num[i] * larger.num[j]) + carry;
                carry = res / 10; 
                multiplicationRes[i + j] = res % 10; 
            }

            multiplicationRes[i + larger.length] += carry;
        }

        int resultLen = multiplicationRes.length;
        while (resultLen > 1 && multiplicationRes[resultLen - 1] == 0) {
            resultLen--;
        }

        boolean resSign = this.sign != number2.sign;

        return new BigNumber(multiplicationRes, resultLen, resSign);
    }

    public static BigNumber fact(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers.");
        }
        
        BigNumber result = new BigNumber(1); 

        for (int i = 2; i <= n; i++) {
            result = result.multiply(new BigNumber(i)); 
        }

        return result;
    }

    public BigNumber karatsuba(BigNumber number2) {
        if (this.length == 1 && this.num[0] == 0 || number2.length == 1 && number2.num[0] == 0) {
            return new BigNumber(0);
        }

        if (this.length == 1 && number2.length == 1) {
            return new BigNumber(this.num[0] * number2.num[0]);
        }

        int maxLength = Math.max(this.length, number2.length);
        int halfLength = (maxLength + 1) / 2; 

        BigNumber x0 = new BigNumber(Arrays.copyOf(this.num, Math.min(this.length, halfLength)), Math.min(this.length, halfLength), this.sign);
        BigNumber x1 = this.length > halfLength ?
            new BigNumber(Arrays.copyOfRange(this.num, halfLength, this.length), this.length - halfLength, this.sign) : 
            new BigNumber(0); 

        BigNumber y0 = new BigNumber(Arrays.copyOf(number2.num, Math.min(number2.length, halfLength)), Math.min(number2.length, halfLength), number2.sign);
        BigNumber y1 = number2.length > halfLength ?
            new BigNumber(Arrays.copyOfRange(number2.num, halfLength, number2.length), number2.length - halfLength, number2.sign) : 
            new BigNumber(0); 

        BigNumber z0 = x0.karatsuba(y0); 
        BigNumber z1 = x1.karatsuba(y1); 
        BigNumber z2 = BigNumber.sum(x0, x1).karatsuba(BigNumber.sum(y0, y1)).subtract(z0).subtract(z1);

        BigNumber shiftedZ1 = z1.multiply(new BigNumber((int) Math.pow(10, 2 * (this.length - halfLength))));
        BigNumber shiftedZ2 = z2.multiply(new BigNumber((int) Math.pow(10, this.length - halfLength)));
        BigNumber result = BigNumber.sum(shiftedZ1, BigNumber.sum(shiftedZ2, z0));

        result.sign = this.sign != number2.sign;

        return result;
}


    public BigNumber divide(BigNumber divisor) {
        int quotient = 0;
        BigNumber n = new BigNumber(this);
        while (isGreater(n, divisor)){
            n = n.subtract(divisor);
            quotient++;
        }
        return new BigNumber(quotient);
    }

    public BigNumber power(int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be a non-negative integer.");
        }
        if (exponent == 0) {
            return new BigNumber(1); 
        }

        BigNumber result = new BigNumber(1); 
        BigNumber base = new BigNumber(this); 

        for (int i = 0; i < exponent; i++) {
            result = result.multiply(base); 
        }

        if (this.sign && exponent % 2 == 1) {
            result.sign = true; 
        }

        return result;
    }

    public static void main(String[] args) {
        BigNumber num1 = new BigNumber(33);
        BigNumber num2 = new BigNumber(89);

        System.out.print("num1: ");
        num1.printNumber(); 
        System.out.print("num2: ");
        num2.printNumber(); 

        BigNumber multiplyResult = num1.multiply(num2);
        System.out.print("Multiplication: ");
        multiplyResult.printNumber(); 

        BigNumber divideResult = num1.divide(new BigNumber(123));
        System.out.print("Division: ");
        divideResult.printNumber(); 

        BigNumber factorialResult = BigNumber.fact(10);
        System.out.print("Factorial of 10: ");
        factorialResult.printNumber();

        BigNumber powerResult = num1.power(2);
        System.out.print("Power (num1^3): ");
        powerResult.printNumber(); 

        BigNumber karatsubaResult = num1.karatsuba(num2);
        System.out.print("Karatsuba Multiplication: ");
        karatsubaResult.printNumber(); 

    }

}
