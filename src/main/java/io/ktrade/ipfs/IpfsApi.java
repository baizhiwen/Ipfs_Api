package io.ktrade.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by WNLTW on 2018/2/8.
 */
@Component
public class IpfsApi {
    private final IPFS ipfs = new IPFS(new MultiAddress("/ip4/127.0.0.1/tcp/5001"));

    public String addSingleFile(MultipartFile files) throws Exception {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(files.getBytes());
        MerkleNode addResult = ipfs.add(file).get(0);
        Multihash multihash = addResult.hash;
        return multihash.toString();
    }

    public void fileTest(NamedStreamable file) throws Exception {
        MerkleNode addResult = ipfs.add(file).get(0);
        Multihash multihash = addResult.hash;
        System.out.println(multihash);
    }

    public String addString(String str) throws Exception {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(str.getBytes());
        MerkleNode addResult = ipfs.add(file).get(0);
        Multihash multihash = addResult.hash;
        return multihash.toString();
    }
}
