import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class Block {

    public int no;
    public long timestamp;
    public int nonce;
    @JsonIgnore
    public byte[] prevHash;
    @JsonProperty("prevHash")
    private String  getPrevHash(){
        return Util.bytesToHex(prevHash);
    }
    @JsonIgnore
    private MerkleTree merkleTree;
    @JsonProperty("merkleRoot")
    public String  getMerkleRoot(){
        return Util.bytesToHex(merkleTree.getMerkleRoot());
    }



    Block() {
        timestamp = Instant.now().getEpochSecond();
        merkleTree = new MerkleTree();
        Random r = new Random();
        nonce = r.nextInt();
    }

    Block(BlockChain bc) {
        timestamp = Instant.now().getEpochSecond();
        merkleTree = new MerkleTree();
        Random r = new Random();
        nonce = r.nextInt();
        no = bc.getNextNo();
        prevHash = bc.getPrevHash();
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
    public byte[] getHash(){
        return Util.Sha256Hash(this.toString().getBytes());
    }

    public boolean addTransaction(Transaction t){
        if (t.verify() && t.amount > 0) {
            merkleTree.addTransaction(t);
            return true;
        }
        return false;
    }

    @JsonIgnore
    public void mineBlock(int bits)
    {
        while (Util.leadingZerosBits(getHash()) < bits) {
            nonce += 1;
        }
    }

}
