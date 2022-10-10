
public class Main {
    public static void main(String[] args) {

        Wallet Alice = new Wallet();
        Wallet Bob = new Wallet();
        BlockChain blockChain = new BlockChain(16);
        Block b1 = new Block(blockChain);

        Transaction t1 = new Transaction(Alice.publicKey, Bob.publicKey, 100);
        Alice.sign(t1);
        System.out.println("t1 is signed: " + t1.verify());
        System.out.println("t1 added to b1: " + b1.addTransaction(t1));
        // System.out.println(t1);

        b1.mineBlock(16);
        System.out.println("b1 added to blockChain: " + blockChain.addBlock(b1));

        for (int i = 0; i < 6; i++) {
            Block block = new Block(blockChain);
            block.mineBlock(16);
            System.out.println("block added to blockChain: " + blockChain.addBlock(block));
        }

        for (int i = 0; i < 10; i++) {
            if (blockChain.getBlock(i) != null) {
                System.out.println(blockChain.getBlock(i));
                System.out.println(Util.bytesToHex(blockChain.getBlock(i).getHash()));
            }
        }
    }
}