/**
 * Title: ReadPrizeFile.java
 * @Package com.success.lottery.business.service
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2011-4-27 ����11:50:48
 * @version V1.0
 */
package com.success.lottery.business.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.success.lottery.business.domain.PrizeUserDomain;

/**
 * com.success.lottery.business.service
 * ReadPrizeFile.java
 * ReadPrizeFile
 * (������һ�仰��������������)
 * @author gaoboqin
 * 2011-4-27 ����11:50:48
 * 
 */

public class ReadPrizeFile {
	/**
	 * 
	 * Title: readLeastUserPrize<br>
	 * Description: <br>
	 *              <br>�û������н�
	 * @return
	 */
	public static List<PrizeUserDomain> readLeastUserPrize(){
		List<PrizeUserDomain> userPrize = new ArrayList<PrizeUserDomain>();
		try{
			List<String> fileContent = getFileContent("com/usernewprize.conf");
			if(fileContent != null){
				for(String one : fileContent){
					PrizeUserDomain onePrize = new PrizeUserDomain();
					String [] tmpArr = one.split(",");
					onePrize.setLotteryId(Integer.parseInt(tmpArr[0]));
					onePrize.setMobilePhone(tmpArr[1]);
					onePrize.setPrize(Integer.parseInt(tmpArr[2]));
					userPrize.add(onePrize);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return userPrize;
		
	}
	/**
	 * 
	 * Title: readUserPrizeOrder<br>
	 * Description: <br>
	 *              <br>�û��н�����
	 * @return
	 */
	public static List<PrizeUserDomain> readUserPrizeOrder(){
		List<PrizeUserDomain> userPrize = new ArrayList<PrizeUserDomain>();
		try{
			List<String> fileContent = getFileContent("com/userprizeorder.conf");
			if(fileContent != null){
				for(String one : fileContent){
					PrizeUserDomain onePrize = new PrizeUserDomain();
					String [] tmpArr = one.split(",");
					onePrize.setMobilePhone(tmpArr[0]);
					onePrize.setPrize(Integer.parseInt(tmpArr[1]));
					userPrize.add(onePrize);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return userPrize;
	}
	
	public static List<String> getFileContent(String filePath) {
		List<String> fileContent = new ArrayList<String>();
		try {
			String fullPath = ReadPrizeFile.class.getClassLoader().getResource(filePath).getPath();
			BufferedReader file = new BufferedReader(new FileReader(fullPath));
			String m = "";
			while ((m = file.readLine()) != null) {
				if (!m.equals("")){//����Ҫ��ȡ����
					fileContent.add(m);
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
	public static void main(String[] args) {
		List<String> result = getFileContent("com/userprizeorder.conf");
		if(result != null){
			for(String one : result){
				System.out.println("one==="+one);
			}
		}
	}

}
