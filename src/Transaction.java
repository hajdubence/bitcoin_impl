import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.security.*;
import java.util.Random;

public class Transaction {

    public int nonce;
    public int amount;
    @JsonIgnore
    public PublicKey from;
    @JsonProperty("from")
    private String getFrom(){
        return from.toString();
    }
    @JsonIgnore
    public PublicKey to;
    @JsonProperty("to")
    private String getTo(){
        return to.toString();
    }
    @JsonIgnore
    public byte[] signature;


    @JsonIgnore
    public Transaction(PublicKey from, PublicKey to, int amount){
        Random r = new Random();
        nonce = r.nextInt();
        this.amount = amount;
        this.from = from;
        this.to = to;
    }

    @JsonIgnore
    public String toString()
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnore
    public boolean verify(){
        return verify(this);
    }

    @JsonIgnore
    public static boolean verify(Transaction t) {
        if (t == null || t.from == null || t.to == null || t.signature == null)
            return false;
        Signature sign;
        try {
            sign = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        try {
            sign.initVerify(t.from);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        try {
            sign.update(t.toString().getBytes());
            return sign.verify(t.signature);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        }
    }

}
