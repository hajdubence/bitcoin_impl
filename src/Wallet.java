import java.security.*;

public class Wallet {

    private final PrivateKey privateKey;
    public final PublicKey publicKey;

    public Wallet() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyPairGen.initialize(1024);
        KeyPair pair = keyPairGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    public void sign(Transaction t){
        if (t == null)
            return;
        Signature sign;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            sign.initSign(privateKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        try {
            sign.update(t.toString().getBytes());
            t.signature = sign.sign();
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

}
