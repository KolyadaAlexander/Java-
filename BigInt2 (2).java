import java.util.Collections;
import java.util.ArrayList;

public class BigInt2 {
    final ArrayList<Integer> digits = new ArrayList<>();
    private boolean sign;

    public BigInt2(String str) {
        if (str.charAt(0) == '-') {
            this.sign = false;
            for (int i = 0; i < str.length() - 1; i++) {
                this.digits.add(i,str.charAt(i + 1) - '0');
            }
        } else {
            this.sign = true;
            for (int i = 0; i < str.length(); i++) {
                this.digits.add(i, str.charAt(i) - '0');
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        if (!sign)
            res.append('-');
        for (int i = 0; i < digits.size(); i++)
            res.append(digits.get(i));
        return res.toString();
    }

    public static BigInt2 valueOf(long obj) {
        String st = Long.toString(obj);
        return new BigInt2(st);
    }

    public BigInt2 add(BigInt2 other) {
        String SUM = "";
        int count = 0;
        ArrayList<Integer> LongNum;
        ArrayList<Integer> ShortNum;
        ArrayList<Integer> result = new ArrayList<>();
        if (digits.size() > other.digits.size()) {
            LongNum = digits;
            ShortNum = other.digits;
        } else {
            ShortNum = digits;
            LongNum = other.digits;
        }
        int s1 = LongNum.size();
        int s2 = ShortNum.size();
        for (int i = s2 - 1; i >= 0; i--) {
            int res = LongNum.get(i + s1 - s2) + ShortNum.get(i) + count;
            count = res / 10;
            result.add(res % 10);
        }
        for (int i = s1 - s2 - 1; i >= 0; i--) {
            if (LongNum.get(i) + count == 10) {
                result.add(0);
                count = 1;
            }
            else {
                result.add(LongNum.get(i) + count);
                count = 0;
            }
        }
        if (count == 1) {
            result.add(1);
        }
        for (int i = 0; i < result.size()/2; i++) {
            Collections.swap(result, i , result.size() - i - 1);
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (!sign && !other.sign) {
            stringBuilder.append('-');
        }
        for (int i = 0; i < result.size(); i++) {
            stringBuilder.append(result.get(i));
            SUM = stringBuilder.toString();
        }
        return new BigInt2(SUM);
    }
    public BigInt2 subtract(BigInt2 other) {
        ArrayList<Integer> max;
        ArrayList<Integer> min;
        int count = 0;
        String Res = "";
        String copy = "";
        String otherCopy = "";
        ArrayList<Integer> result = new ArrayList<>();
        int len1 = digits.size();
        int len2 = other.digits.size();
        if (sign && !other.sign) {
            for (int i = 0; i < len1; i++)
                copy += digits.get(i);
            for (int i = 0; i < len2; i++)
                otherCopy += other.digits.get(i);
            BigInt2 sum = new BigInt2(copy).add(new BigInt2(otherCopy));
            Res = sum.toString();
            return new BigInt2(Res);
        }
        if (!sign && other.sign) {
            Res = "-";
            for (int i = 0; i < len1; i++)
                copy += digits.get(i);
            for (int i = 0; i < len2; i++)
                otherCopy += other.digits.get(i);
            BigInt2 sum = new BigInt2(copy).add(new BigInt2(otherCopy));
            Res += sum.toString();
            return new BigInt2(Res);
        }
        BigInt2 num = new BigInt2(digits.toString());
        BigInt2 otherNum = new BigInt2(other.digits.toString());
        if (num.compareTo(otherNum) == -1) {
            BigInt2 res = otherNum.subtract(num);
            if (res.sign == false) {
                res.sign = true;
            } else {
                res.sign = false;
            }
            return res;
        }

    }
    public int compareTo(BigInt2 other) {
            if (sign && !other.sign) {
                return 1;
            }
            if (!sign && other.sign) {
                return -1;
            }
            if (sign && digits.size() > other.digits.size()) {
                return 1;
            }
            if (sign && digits.size() < other.digits.size()) {
                return -1;
            }
            if (!other.sign && digits.size() > other.digits.size()) {
                return 1;
            }
            if (!other.sign && digits.size() < other.digits.size()) {
                return -1;
            }
            if (sign) {
                for (int i = 0; i < digits.size(); i++) {
                    if (digits.get(i) > other.digits.get(i)) {
                        return 1;
                    }
                    else {
                        if (digits.get(i) < other.digits.get(i)) {
                            return -1;
                        }
                    }
                }
            }
            else {
                for (int i = 0; i < digits.size(); i++) {
                    if (digits.get(i) > other.digits.get(i)) {
                        return -1;
                    }
                    else {
                        if (digits.get(i) < other.digits.get(i)) {
                            return 1;
                        }
                    }
                }
            }
        return 0;
    }
}
