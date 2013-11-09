package com.success.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {
	
		/**
	    * 动态生成html
	    * @param fileName 生成的文件绝对路径
	    * @param content 文件要保存的内容
	    * @param enc  文件编码
	    * @return
	    */
	    public static boolean writeStringToFile(String url,String fileName,String content,String enc) {
	        File file = new File(url+"/"+fileName);
	        
	        try {
	            if(file.isFile()){
	                file.delete();
	                file = new File(file.getAbsolutePath());
	            }
	            OutputStreamWriter os = null;
	            if(enc==null||enc.length()==0){
	                os = new OutputStreamWriter(new FileOutputStream(file));
	            }else{
	                os = new OutputStreamWriter(new FileOutputStream(file),enc);
	            }
	            os.write(content);
	            os.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;
	    }
	    /**
	     * 判断文件是否存在
	     * @param url
	     * @param fileName
	     * @return
	     */
	    public static boolean findFile(String url,String fileName){
	    	  File file = new File(url+"/"+fileName);
		      boolean flg=file.exists();
	    	  return flg;
	    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(FileUtils.findFile("C:\\Documents and Settings\\Administrator\\桌面\\work\\mylottery\\WebRoot\\direction", "dlt30.html"));
	}	
	
}
