package bases_cl.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

 
  
public class SerializeUtil {  
  
    /** 
     * 序列化 
     *  
     * @param object 
     * @return 
     */  
    public static byte[] serialize(Object object) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); ;  
        ObjectOutputStream oos = null;  
        
        try {  
        	
            baos = new ByteArrayOutputStream(); 
            oos = new ObjectOutputStream(baos); 
           
            oos.writeObject(object);  
            System.out.println( object.toString());
            byte[] bytes = baos.toByteArray();  
            return bytes;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                oos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                baos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return null;  
    }  
  
    /** 
     * 反序列化 
     *  
     * @param bytes 
     * @return 
     */  
    public static Object unserialize(byte[] bytes) {  
  
        ByteArrayInputStream bais = null;  
        ObjectInputStream ois = null;  
        try {  
  
            bais = new ByteArrayInputStream(bytes);  
            ois = new ObjectInputStream(bais);  
            return ois.readObject();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
  
            try {  
                ois.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                bais.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return null;  
  
    }  
  
}  