package io.ktrade.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IpfsApplicationTests {

    private final IPFS ipfs = new IPFS(new MultiAddress("/ip4/127.0.0.1/tcp/5001"));

    @Test
    public void singleFileTest() throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper("E:\\work\\ipfs\\go-ipfs\\test_add3.txt", "Happy Hacking!!!".getBytes());
        fileTest(file);
    }

    @Test
    public void singlePhotoTest() throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("E:\\work\\ipfs\\go-ipfs\\111.png"));
        fileTest(file);
    }

//    @Test
//    public void singlePhotoByteTest() throws IOException {
//        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper();
//        fileTest(file);
//    }

    public void fileTest(NamedStreamable file)  throws IOException{
        MerkleNode addResult = ipfs.add(file).get(0);
        Multihash multihash = addResult.hash;
        System.out.println(multihash);
    }
}
