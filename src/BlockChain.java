import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockChain {

    private final List<Block> blockChain;
    public int difficultyInBits;

    BlockChain(int difficultyInBits){
        this.difficultyInBits = difficultyInBits;
        blockChain = new ArrayList<>();
        Block genesis = new Block();
        genesis.no = 0;
        genesis.prevHash = Util.Sha256Hash("".getBytes());
        genesis.mineBlock(difficultyInBits);
        blockChain.add(genesis);
    }

    boolean addBlock(Block b){
        if (b == null || b.prevHash == null)
            return false;
        if (b.no != getNextNo())
            return false;
        if (!Arrays.equals(b.prevHash, getPrevHash()))
            return false;
        if (Util.leadingZerosBits(b.getHash()) >= difficultyInBits) {
            blockChain.add(b);
            return true;
        }
        return false;
    }

    public Block getBlock(int no) {
        try {
            return blockChain.get(no);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public int getNextNo(){
        return blockChain.size();
    }

    public byte[] getPrevHash(){
        return blockChain.get(blockChain.size()-1).getHash();
    }

}
