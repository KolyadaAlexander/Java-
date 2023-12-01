import java.util.Objects;

public class Matrix {
    private final int[] arr2;
    private final int[][] arr1;

    public Matrix(int[] arr2) {
        this.arr2 = new int[9];
        this.arr1 = new int[3][3];
        for (int k = 0; k < 9; k++) {
            this.arr2[k] = arr2[k];
        }
    }

    public int get(int i, int j) {
        if (i == 0)
            return arr2[j];
        if (i == 1)
            return arr2[j + 3];
        return arr2[j + 6];
    }

    public int trace() {
        return arr2[0] + arr2[4] + arr2[8];
    }

    public int det() {
        return arr2[0] * arr2[4] * arr2[8] + arr2[1] * arr2[5] * arr2[6] + arr2[3] * arr2[7] * arr2[2] - arr2[2] * arr2[4] * arr2[6] - arr2[0] * arr2[5] * arr2[7] - arr2[1] * arr2[3] * arr2[8];
    }

    public Matrix add(Matrix other) {
        Matrix r1 = new Matrix(arr2);
        for (int i = 0; i < 9; i++) {
            r1.arr2[i] = arr2[i] + other.arr2[i];
        }
        return r1;
    }

    public Matrix subtract(Matrix other) {
        Matrix r1 = new Matrix(arr2);
        for (int i = 0; i < 9; i++) {
            r1.arr2[i] = arr2[i] - other.arr2[i];
        }
        return r1;
    }

    public Matrix multiply(Matrix other) {
        Matrix r1 = new Matrix(arr2);
        r1.arr2[0] = arr2[0]*other.arr2[0] + arr2[1]*other.arr2[3] + arr2[2]*other.arr2[6];
        r1.arr2[1] = arr2[0]*other.arr2[1] + arr2[1]*other.arr2[4] + arr2[2]*other.arr2[7];
        r1.arr2[2] = arr2[0]*other.arr2[2] + arr2[1]*other.arr2[5] + arr2[2]*other.arr2[8];
        r1.arr2[3] = arr2[3]*other.arr2[0] + arr2[4]*other.arr2[3] + arr2[5]*other.arr2[6];
        r1.arr2[4] = arr2[3]*other.arr2[1] + arr2[4]*other.arr2[4] + arr2[5]*other.arr2[7];
        r1.arr2[5] = arr2[3]*other.arr2[2] + arr2[4]*other.arr2[5] + arr2[5]*other.arr2[8];
        r1.arr2[6] = arr2[6]*other.arr2[0] + arr2[7]*other.arr2[3] + arr2[8]*other.arr2[6];
        r1.arr2[7] = arr2[6]*other.arr2[1] + arr2[7]*other.arr2[4] + arr2[8]*other.arr2[7];
        r1.arr2[8] = arr2[6]*other.arr2[2] + arr2[7]*other.arr2[5] + arr2[8]*other.arr2[8];
        return r1;
    }
    public String toString() {
        String X = "[[";
        String[] arr3 = new String[9];
        for (int i = 0; i < 9; i++) {
            arr3[i] = String.valueOf(arr2[i]);
        }
        return "[[" + arr3[0] + ", " + arr3[1] + ", " + arr3[2] + "]" + ", " + "[" + arr3[3] + ", " + arr3[4] + ", " + arr3[5] + "]" + ", " + "[" + arr3[6] + ", " + arr3[7] + ", " + arr3[8] + "]]";
    }
    public boolean equals(Matrix other) {
        int y = 0;
        Matrix r1 = new Matrix(arr2);
        for (int i = 0; i < 9; i++) {
            if (r1.arr2[i] == other.arr2[i])
                y++;
        }
        return (y == 9);
    }
    public int hashCode() {
        return Objects.hash(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5], arr2[6], arr2[7], arr2[8]);
    }
}
