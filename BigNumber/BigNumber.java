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


    public static void main(String[] args) {
        BigNumber num1 = new BigNumber(-100);
        BigNumber num2 = new BigNumber("50");
        BigNumber num3 = BigNumber.sum(num1, num2);

        System.out.print("num1:");
        num1.printNumber();
        System.out.print("num2:");
        num2.printNumber();
        System.out.print("num3:");
        num3.printNumber();

        BigNumber num4 = num3.subtract(num2);
        System.out.print("num4:");
        num4.printNumber();

        BigNumber num5 = new BigNumber(568);
        System.out.print("num5:");
        num5.printNumber(); 

        BigNumber shiftedLeft = num5.leftShift();
        System.out.print("num5 shifted left:");
        shiftedLeft.printNumber(); 

        BigNumber shiftedRight = num5.rightShift();
        System.out.print("num5 shifted right:");
        shiftedRight.printNumber(); 
    }
}