import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    public static int leadingZerosBits(byte[] byteArray) {
        if (byteArray == null)
            return 0;
        int c = 0;
        for (int i = 0; i < byteArray.length; i++) {
            String s = Integer.toBinaryString(0xff & byteArray[i]);
            if (s.equals("0")) {
                c += 8;
            } else {
                c += 8 - s.length();
                i = byteArray.length;
            }
        }
        return c;
    }

    public static String bytesToHex(byte[] byteArray)
    {
        if (byteArray == null)
            return null;
        StringBuilder hexString = new StringBuilder(2 * byteArray.length);
        for (byte b : byteArray) {
            hexString.append(String.format("%2s", Integer.toHexString(0xff & b)).replace(" ", "0"));
        }
        return hexString.toString();
    }

    public static String bytesToBin(byte[] byteArray)
    {
        if (byteArray == null)
            return null;
        StringBuilder binString = new StringBuilder(8 * byteArray.length);
        for (byte b : byteArray) {
            binString.append(String.format("%8s", Integer.toBinaryString(0xff & b)).replace(" ", "0"));
        }
        return binString.toString();
    }

    public static byte[] Sha256Hash(byte[] msg)
    {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(msg);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
