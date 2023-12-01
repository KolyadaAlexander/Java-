 abstract class  AsciiCharSequence implements CharSequence {
    private byte[] arr1;
    public AsciiCharSequence(byte[] arr1) {
        for (int k = 0; k < 9; k++) {
            this.arr1[k] = arr1[k];
        }
    }
    public int length() {
        return arr1.length;
    }
   /* public AsciiCharSequence subSequence(int i, int j) {
        AsciiCharSequence res = new AsciiCharSequence(new byte[] arr1);
        for (int k = i; k < j; k++)
            res[i]
            }
   */
    public char charAt(int i) {
        return (char)arr1[i];
    }
    public String toString() {
        String X = "";
        for (int i = 0; i < arr1.length; i++)
            X += (char)arr1[i];
        return X;
    }
}
