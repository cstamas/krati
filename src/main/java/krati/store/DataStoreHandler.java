package krati.store;

import java.util.List;
import java.util.Map.Entry;

public interface DataStoreHandler {
    
    public byte[] assemble(byte[] key, byte[] value);
    
    public byte[] assemble(byte[] key, byte[] value, byte[] data);
    
    public int countCollisions(byte[] key, byte[] data);
    
    public byte[] extractByKey(byte[] key, byte[] data);
    
    public int removeByKey(byte[] key, byte[] data);  
    
    public List<byte[]> extractKeys(byte[] data);
    
    public List<Entry<byte[], byte[]>> extractEntries(byte[] data);
}
