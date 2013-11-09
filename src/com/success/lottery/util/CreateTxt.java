package com.success.lottery.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

public class CreateTxt {
	public void txt(String sql,String file){
		byte[] buff=new byte[]{};   
        try    
        {   
            String aa=sql;   
            buff=aa.getBytes();   
            FileOutputStream out=new FileOutputStream(file);   
            out.write(buff,0,buff.length);   
               
        }    
        catch (FileNotFoundException e)    
        {   
            e.printStackTrace();   
        }   
        catch (IOException e)    
        {   
            e.printStackTrace();   
        }   

	}
	
	public String readFile(String filePath){
		String text = null;   
        CharBuffer cbuf = null;   
        File file = new File(filePath);   
        try  
        {   
            FileReader fReader = new FileReader(file);   
            cbuf = CharBuffer.allocate((int) file.length());   
            fReader.read(cbuf);   
            text = new String(cbuf.array());   
        }   
        catch (FileNotFoundException e)    
        {   
            e.printStackTrace();   
        }    
        catch (IOException e)    
        {   
            // TODO Auto-generated catch block   
            e.printStackTrace();   
        }
		return text;   
	}
}
