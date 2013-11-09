package com.success.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {
	
		/**
	    * ��̬����html
	    * @param fileName ���ɵ��ļ�����·��
	    * @param content �ļ�Ҫ���������
	    * @param enc  �ļ�����
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
	     * �ж��ļ��Ƿ����
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
		System.out.println(FileUtils.findFile("C:\\Documents and Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\direction", "dlt30.html"));
	}	
	
}
