import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MerkleTree {
    public final List<Transaction> dataBlocks;

    public MerkleTree() {
        dataBlocks = new ArrayList<>();
    }

    public void addTransaction(Transaction t){
        dataBlocks.add(t);
    }

    public byte[] getMerkleRoot() {
        return getHash(dataBlocks.toArray(new Transaction[0]));
    }

    /*
    Left-right separation:
    (1) (2)
    (1 2) (3)
    (1 2) (3 4)
    (1 2 3 4) (5)
    (1 2 3 4) (5 6)
     */
    private byte[] getHash(Transaction[] t) {
        if (t.length == 0)
            return Util.Sha256Hash("".getBytes());
        if (t.length == 1)
            return Util.Sha256Hash(concatByteArrays(t[0].toString().getBytes(),t[0].signature));
        Transaction[] left = Arrays.copyOfRange(t, 0, (int) Math.pow(2, log2(t.length - 1)));
        Transaction[] right = Arrays.copyOfRange(t, (int) Math.pow(2, log2(t.length - 1)), t.length - 1);
        return Util.Sha256Hash(concatByteArrays(getHash(left), getHash(right)));
    }

    private static int log2(int N) {
        return (int) (Math.log(N) / Math.log(2));
    }

    private static byte[] concatByteArrays(byte[] array1, byte[] array2) {
        byte[] result = Arrays.copyOf(array1, array1.length + array2.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

}
