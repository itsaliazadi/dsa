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

    private static boolean equals(BigNumber number1, BigNumber number2){
        if (number1.length != number2.length) {
            return false;
        }
        else{
            for (int i = 0; i < number1.length; i++){
                if (number1.num[i] != number2.num[i]){
                    return false;
                }
            }
        }
        return true;
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
            result = result.karatsuba(new BigNumber(i)); 
        }

        return result;
    }

   public BigNumber karatsuba(BigNumber number2) {

        if (this.length < 10 || number2.length < 10) {
            return this.multiply(number2);
        }

        int maxLength = Math.max(this.length, number2.length);
        int mid = maxLength / 2;

        BigNumber a1 = splitHigh(mid);
        BigNumber a0 = splitLow(mid);  
        BigNumber b1 = number2.splitHigh(mid);  
        BigNumber b0 = number2.splitLow(mid);  

        BigNumber z2 = a1.karatsuba(b1); 
        BigNumber z0 = a0.karatsuba(b0);  
        
        BigNumber sumA = BigNumber.sum(a1, a0);
        BigNumber sumB = BigNumber.sum(b1, b0);
        BigNumber z1 = sumA.karatsuba(sumB).subtract(z2).subtract(z0);

        BigNumber result = shiftLeft(z0, 2 * mid);
        result = BigNumber.sum(result, shiftLeft(z1, mid));
        result = BigNumber.sum(result, z2);

        result.sign = this.sign != number2.sign;
        
        return result;
}

    private BigNumber splitHigh(int mid) {
        if (this.length <= mid) {
            return new BigNumber();
        }
        
        int newLength = this.length - mid;
        int[] newNum = new int[newLength];
        System.arraycopy(this.num, mid, newNum, 0, newLength);
        
        return new BigNumber(newNum, newLength, false);
    }

    private BigNumber splitLow(int mid) {
        int newLength = Math.min(mid, this.length);
        int[] newNum = new int[newLength];
        System.arraycopy(this.num, 0, newNum, 0, newLength);
        
        return new BigNumber(newNum, newLength, false);
    }

    private static BigNumber shiftLeft(BigNumber num, int shift) {
        if (num.length == 1 && num.num[0] == 0) {
            return new BigNumber();
        }
        
        int[] newNum = new int[num.length + shift];
        System.arraycopy(num.num, 0, newNum, shift, num.length);
        
        return new BigNumber(newNum, newNum.length, num.sign);
    }


    public BigNumber divide(BigNumber divisor) {

        boolean quotientSign = this.sign != divisor.sign;

        BigNumber copyA = new BigNumber(this);
        copyA.sign = false;

        BigNumber copyB = new BigNumber(divisor);
        copyB.sign = false;

        int q = 0;
        while (isGreater(copyA, copyB) || equals(copyA, copyB)){
            copyA = copyA.subtract(copyB);
            q++;
        }

        BigNumber quotient = new BigNumber(q);
        quotient.sign = quotientSign;

        return new BigNumber(quotient);
    }

    public BigNumber power(int n) {
        if (n == 0) {
            return new BigNumber(1); 
        }
        if (n == 1) {
            return new BigNumber(this); 
        }

        BigNumber half_power = this.power(n / 2);
        half_power = half_power.karatsuba(half_power);

        if (n % 2 == 1) {
            half_power = half_power.karatsuba(this); 
        }

        return half_power;
    }

    public static void main(String[] args) {
        BigNumber num1 = new BigNumber("-987654321");
        BigNumber num2 = new BigNumber("12344444");

        System.out.print("num1: ");
        num1.printNumber(); 
        System.out.print("num2: ");
        num2.printNumber(); 

        BigNumber multiplyResult = num1.multiply(num2);
        System.out.print("Multiplication: ");
        multiplyResult.printNumber(); 

        BigNumber divideResult = num1.divide(num2);
        System.out.print("Division: ");
        divideResult.printNumber(); 

        BigNumber factorialResult = BigNumber.fact(100);
        System.out.print("Factorial of 100: ");
        factorialResult.printNumber();

        BigNumber powerResult = num1.power(5);
        System.out.print("Power (num1^5): ");
        powerResult.printNumber(); 

        BigNumber karatsubaResult = num1.karatsuba(num2);
        System.out.print("Karatsuba Multiplication: ");
        karatsubaResult.printNumber(); 

    }

}
