package krati.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import krati.store.ArrayStorePartition;

import org.apache.log4j.Logger;

/**
 * SimplePartitionLoader.
 * 
 * @author jwu
 *
 */
public class SimplePartitionLoader implements PartitionLoader
{
    private final static Logger _log = Logger.getLogger(SimplePartitionLoader.class);
    
    @Override
    public void load(ArrayStorePartition cache, File dataFile) throws IOException
    {
        String line;
        FileReader reader = new FileReader(dataFile);
        BufferedReader in = new BufferedReader(reader);

        int index = cache.getIdStart();
        int stopIndex = index + cache.getIdCount();
        
        while((line = in.readLine()) != null && index < stopIndex)
        {
            try
            {
                cache.set(index, line.getBytes(), index);
            }
            catch(Exception e)
            {
                _log.error("index=" + index + ": " + e.getMessage());
                e.printStackTrace();
            }
            index++;
        }
        
        in.close();
        reader.close();
        cache.persist();
    }
    
    @Override
    public void dump(ArrayStorePartition cache, File dumpFile) throws IOException
    {
        byte[] data;
        String line;
        FileOutputStream fos = new FileOutputStream(dumpFile);
        PrintWriter out = new PrintWriter(fos);
        
        for(int index = cache.getIdStart(), cnt = cache.getIdCount(); index < cnt; index++)
        {
            data = cache.get(index);
            if(data != null)
            {
                line = new String(data);
                out.println(line);
            }
            else
            {
                out.println();
            }
            
            if(index % 10000 == 0) out.flush();
        }
        
        out.flush();
        out.close();
        fos.close();
    }
}
