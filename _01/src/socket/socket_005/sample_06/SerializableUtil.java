package socket.socket_005.sample_06;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
  
public class SerializableUtil {  
      
    public static byte[] toBytes(Object object) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ObjectOutputStream oos = null;  
        try {  
        	
        	//创建写入 ByteArrayOutputStream 的 ObjectOutputStream
            oos = new ObjectOutputStream(baos);  
            //将指定的对象写入 ObjectOutputStream
            oos.writeObject(object);  
            byte[] bytes = baos.toByteArray();  
            return bytes;  

        } catch(IOException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } finally {  
            try {  
                oos.close();  
            } catch (Exception e) {}  
        }  
    }  
      
    public static Object toObject(byte[] bytes) {  
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);  
        ObjectInputStream ois = null;  
        try {  
        		
        	//创建从ByteArrayInputStream 读取的 ObjectInputStream
            ois = new ObjectInputStream(bais);  
            //从 ObjectInputStream 读取对象
            Object object = ois.readObject();  
            return object;        
            
        } catch(IOException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } catch(ClassNotFoundException ex) {  
            throw new RuntimeException(ex.getMessage(), ex);  
        } finally {  
            try {  
                ois.close();  
            } catch (Exception e) {}  
        }  
    }  
}
