package com.success.protocol.smip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SMIP_DataPack {

	public static final String dumpLine = "------------------------------------------------------";
	
	public static final int SMIP_CONNECT = 0x00000001;
	public static final int SMIP_CONNECT_RESP = 0x80000001;
	public static final int SMIP_TERMINATE = 0x00000002;
	public static final int SMIP_TERMINATE_RESP = 0x80000002;
	public static final int SMIP_ACTIVE_TEST = 0x00000003;
	public static final int SMIP_ACTIVE_TEST_RESP = 0x80000003;
	public static final int SMIP_MT = 0x00000004;
	public static final int SMIP_MT_RESP = 0x80000004;
	public static final int SMIP_MO = 0x00000005;
	public static final int SMIP_MO_RESP = 0x80000005;
	public static final int SMIP_ORDER = 0x00000006;
	public static final int SMIP_ORDER_RESP = 0x80000006;
	public static final int SMIP_QUERY = 0x00000007;
	public static final int SMIP_QUERY_RESP = 0x80000007;

	public static final int SMIP_CONNECT_LEN = 39;
	public static final int SMIP_CONNECT_RESP_LEN = 17;
	public static final int SMIP_TERMINATE_LEN = 12;
	public static final int SMIP_TERMINATE_RESP_LEN = 12;
	public static final int SMIP_ACTIVE_TEST_LEN = 12;
	public static final int SMIP_ACTIVE_TEST_RESP_LEN = 12;
	public static final int SMIP_MT_LEN = 236;
	public static final int SMIP_MT_RESP_LEN = 17;
	public static final int SMIP_MO_LEN = 173;
	public static final int SMIP_MO_RESP_LEN = 17;
	public static final int SMIP_ORDER_LEN = 272;
	public static final int SMIP_ORDER_RESP_LEN = 275;
	public static final int SMIP_QUERY_LEN = 141;
	public static final int SMIP_QUERY_RESP_LEN = 150;

	/**
	 * bMsg:
	 */
	protected byte[] bMsg;
	/**
	 * footprint:
	 */
	protected long footprint;

	/**
	 * 
	 */
	public SMIP_DataPack() {
		this.footprint = System.currentTimeMillis();
	}

	/**
	 * 
	 * @param b
	 */
	public SMIP_DataPack(byte[] b) {
		this.bMsg = b;
		this.footprint = System.currentTimeMillis();
	}

	/**
	 * BCD��ת��long, bcd.length, ������10�ֽڵ�msgid��ת��
	 * 
	 * @param bcd:
	 *            Ҫת����BCD���byte.
	 * @return: ת�����γɵ�long
	 */
	public static long bcd2Long(byte[] bcd) {
		if (bcd.length == 0)
			return -1;
		byte[] x = new byte[bcd.length - 1];
		System.arraycopy(bcd, 0, x, 0, 6);
		System.arraycopy(bcd, 7, x, 6, 3);
		int shorter[] = new int[x.length * 2];
		for (int i = 0; i < x.length; i++) {
			shorter[i * 2] = x[i] >>> 4 & 0x0f;
			shorter[i * 2 + 1] = x[i] & 0x0f;
		}
		return bcd2Long(shorter);
	}

	/**
	 * int ii[] = {12,13,15,14}; long ll = CMPP_DataPack.bcd2Long(ii); ���ϣ�ll
	 * =12131514L;
	 * 
	 * @param i:
	 *            Ҫת����һ��int
	 * @return: ת�����long
	 */
	public static long bcd2Long(int[] i) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i.length; j++)
			sb.append(i[j]);
		try {
			return Long.parseLong(sb.toString());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * bcd��ת�����ַ���
	 * 
	 * @param bcd:
	 *            Ҫת����BCD��
	 * @return: ת������ַ���
	 */
	public static String bcd2String(byte[] bcd) {
		if (bcd.length == 0)
			return null;
		int shorter[] = new int[bcd.length * 2];
		for (int i = 0; i < bcd.length; i++) {
			shorter[i * 2] = bcd[i] >>> 4 & 0x0f;
			shorter[i * 2 + 1] = bcd[i] & 0x0f;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < shorter.length; i++)
			sb.append(shorter[i]);
		return sb.toString();
	}

	public static String dumpPackString(byte[] b, int off, int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int k = off + i;
			if (b[k] < 0x10 && b[k] > -1) {
				// ps.print('0');
				sb.append('0');
			}
			// ps.print(Integer.toHexString(0xff & b[k]));
			sb.append(Integer.toHexString(0xff & b[k]));
			if (i % 4 == 3)
				// ps.print(' ');
				sb.append(' ');
			if (i % 4 == 7)
				// ps.print(' ');
				sb.append(' ');
			// ps.print(i % 16 == 15 ? '\n' : ' ');
			sb.append(i % 16 == 15 ? '\n' : ' ');
		}
		// ps.println();
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * for debuging purpose ��byte b��ֵ����16���Ʒ�ʽ��ӡ����
	 * 
	 * @param b:
	 *            Ҫ��ӡ��byte����
	 * @param off:
	 *            ��ӡbyte���鿪ʼ��λ�ã���0��ʼ
	 * @param len:
	 *            �ӿ�ʼλ�ÿ�ʼ��ӡ�ĸ���
	 */
	public static void dumpPack(PrintStream ps, byte[] b, int off, int len) {
		for (int i = 0; i < len; i++) {
			int k = off + i;
			if (b[k] < 0x10 && b[k] > -1) {
				ps.print('0');
			}
			ps.print(Integer.toHexString(0xff & b[k]));
			if (i % 4 == 3)
				ps.print(' ');
			if (i % 4 == 7)
				ps.print(' ');
			ps.print(i % 16 == 15 ? '\n' : ' ');
		}
		ps.println();
	}

	public static List<String> getDataPack(byte[] b, int off, int len) {
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int k = off + i;
			if (b[k] < 0x10 && b[k] > -1) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(0xff & b[k]));
			if (i % 4 == 3)
				sb.append(' ');
			if (i % 4 == 7)
				sb.append(' ');
			if (i % 16 == 15 || i == len - 1){
				list.add(sb.toString());
				sb.delete(0, sb.length());
			} else {
				sb.append(' ');
			}
		}
		return list;
	}

	public static void dumpPack(byte[] b, int off, int len) {
		dumpPack(System.out, b, off, len);
	}

	/**
	 * ��byte����b�У���start��ʼ��ȡ��len��byte
	 * 
	 * @param b:
	 *            Ҫȡ����byte����
	 * @param start:
	 *            ��ʼλ�ã���0��ʼ
	 * @param len:
	 *            ȡ����byte����
	 * @return
	 */
	public static byte[] getBytes(byte[] b, int start, int len) {
		byte[] d = new byte[len];
		System.arraycopy(b, start, d, 0, len);
		return d;
	}

	/**
	 * ��byte�����startλ�ÿ�ʼ���len��byteת����int
	 * 
	 * @param b:
	 *            byte����
	 * @param start:
	 *            byte���鿪ʼλ�ã���0��ʼ
	 * @param len:
	 *            byte�ĸ���
	 * @return: ת�����int
	 */
	public static int getInt(byte[] b, int start, int len) {
		return (int) getLong(b, start, len);
	}

	/**
	 * ��byte�����startλ�ÿ�ʼ���len��byteת����long
	 * 
	 * @param b:
	 *            byte����
	 * @param start:
	 *            byte���鿪ʼλ�ã���0��ʼ
	 * @param len:
	 *            byte�ĸ���
	 * @return: ת�����long
	 */
	public static long getLong(byte[] b, int start, int len) {
		long n = 0;
		int endi = start + len - 1;
		for (int i = endi; i >= start; i--) {
			n |= ((long) b[i] & 0xff) << (8 * (endi - i));
		}
		return n;
	}

	/**
	 * ���ַ���org���ճ���len�Ҷ��룬����
	 * 
	 * @param org:
	 *            �ַ���
	 * @param len:
	 *            �ַ����ܳ���
	 * @return: �������ַ���
	 */
	public static String octZero(String org, int len) {
		StringBuffer sb = new StringBuffer();
		for (int i = len; i > org.length(); i--)
			sb.append("0");
		return sb.append(org).toString();
	}

	public static String decodeUcs2(byte[] msgByte, int off, int len) {
		StringBuffer sb = new StringBuffer("");
		if (msgByte != null)
			for (int i = off; i + 1 < off + len; i += 2)
				sb.append((char) (((0xff & (int) msgByte[i]) << 8) | (0xff & (int) msgByte[i + 1])));
		return sb.toString();
	}

	/**
	 * ÿ�δ�is�ж�ȡlen��byte���ݴ�ŵ�d�У����ʱ��offλ�ÿ�ʼ��ֱ��is��û�����ݻ����Ѿ�������len������
	 * 
	 * @param is:
	 *            InputStream.
	 * @param d:
	 *            ������ݵ�byte����
	 * @param off:
	 *            ��off��ʼ���
	 * @param len:
	 *            ��ȡ���ݵĳ���
	 * @return: �ܹ���ȡ��byte����
	 * @throws IOException
	 */
	public static int readFully(InputStream is, byte d[], int off, int len) throws IOException {
		int r = 0, n = 0;
		while (len > 0) {
			n = is.read(d, off, len);
			if (n < 0)
				return n;
			off += n;
			len -= n;
			r += n;
		}
		return r;
	}

	/**
	 * ��sour�д�sourStart��ʼ��len���ȵ����ݸ��Ƶ�dest�д�destStart��ʼ֮��.������len��dest֮���������0
	 * 
	 * @param dest
	 * @param destStart
	 * @param len
	 * @param sour
	 * @param sourStart
	 */
	public static void setBytes(byte[] dest, int destStart, int len, byte[] sour, int sourStart) {
		int l = Math.min(len, sour.length - sourStart);
		System.arraycopy(sour, sourStart, dest, destStart, l);
		// for (; (destStart + l) < len - (sour.length - sourStart); l++){
		for (; l < len; l++) {
			dest[destStart + l] = 0;
		}
	}

	/**
	 * sour�п�ʼλ��ȡ��len���ȵ����ݸ��Ƶ�dest�д�destStart��ʼ֮��.������len��dest֮���������0.
	 * 
	 * @param dest
	 * @param start
	 * @param len
	 * @param sour
	 */
	public static void setBytes(byte[] dest, int start, int len, byte[] sour) {
		setBytes(dest, start, len, sour, 0);
	}

	/**
	 * ������numת���ɸ��ֽ���ǰ��byte������b�д�start��ʼ��λ�ã�lenָ��ת���ɶ��ٸ�byte
	 * 
	 * @param b
	 * @param start
	 * @param len
	 * @param num
	 */
	public static void setInt(byte[] b, int start, int len, long num) {
		int n = 0, endi = start + len - 1;
		for (int i = endi; i >= start; i--)
			b[i] = (byte) ((num >> (8 * (endi - i))) & 0xff);
	}

	/**
	 * ��������ʱ�����ʱ�������
	 * 
	 * @return: ʱ��������ĸ�ʽΪmmddhhmmss��������ʱ���룬10λ���ֵ�����
	 */
	public static int createTimeStamp() {
		Calendar c = Calendar.getInstance();
		int ts = (c.get(Calendar.MONTH) + 1) * 100000000;
		ts += c.get(Calendar.DATE) * 1000000;
		ts += c.get(Calendar.HOUR_OF_DAY) * 10000;
		ts += c.get(Calendar.MINUTE) * 100;
		return ts += c.get(Calendar.SECOND);
	}

	public byte getByte(int i) {
		return bMsg[i];
	}

	public int getTotalLength() {
		return getInt(bMsg, 0, 4);
	}

	public void setTotalLength(int totalLen) {
		setInt(bMsg, 0, 4, totalLen);
	}

	public int getCommandID() {
		return getInt(bMsg, 4, 4);
	}

	public void setCommandID(int requestID) {
		setInt(bMsg, 4, 4, requestID);
	}

	public int getSequenceID() {
		return getInt(bMsg, 8, 4);
	}

	public void setSequenceID(int sequenceID) {
		setInt(bMsg, 8, 4, sequenceID);
	}

	public void writePack(OutputStream os) throws IOException {
		os.write(bMsg, 0, getTotalLength());
		os.flush();
	}

	public List<String> getDataPack(){
		return getDataPack(bMsg, 0, getTotalLength());
	}
	
	public void dumpPack() {
		try {
			dumpPack(bMsg, 0, getTotalLength());
		} catch (Exception e) {
		}
	}

	public void dumpPack(OutputStream os) {
		try {
			dumpPack(bMsg, 0, this.getTotalLength());
		} catch (Exception e) {
		}
	}

	public String dumpPackString() {
		return dumpPackString(bMsg, 0, getTotalLength());
	}

	private static boolean validCommand(int commandId){
		if(commandId == SMIP_DataPack.SMIP_ACTIVE_TEST) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_ACTIVE_TEST_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_CONNECT) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_CONNECT_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_MO) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_MO_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_MT) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_MT_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_ORDER) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_ORDER_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_QUERY) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_QUERY_RESP) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_TERMINATE) {
			return true;
		}
		if(commandId == SMIP_DataPack.SMIP_TERMINATE_RESP) {
			return true;
		}
		return false;
	}
	
	public static byte[] readPackBytes(InputStream is) throws IOException {
		byte[] bHead = new byte[12];
		int readCount = readFully(is, bHead, 0, 12);
		if (readCount < 12) {
			if (readCount < 0) {
				throw new IOException("Close connection by peer, read bytes:" + readCount);
			} else {
				throw new IllegalStateException("Not enough bytes for the DataPack head: 12/" + readCount);
			}
		}
		if ((readCount = getInt(bHead, 0, 4)) >= 12) {
			if (readCount > 4096) {
				throw new IllegalStateException("DataPack length too long: " + readCount);
			}
			if (!validCommand(getInt(bHead, 4, 4))){
				throw new IllegalStateException("DataPack command is invalid: " + getInt(bHead, 4, 4));
			}
			byte[] bMsg = new byte[readCount];
			System.arraycopy(bHead, 0, bMsg, 0, 12);
			readCount = readFully(is, bMsg, 12, readCount - 12);
			bHead = bMsg;
			if (readCount < bMsg.length - 12) {
				throw new IllegalStateException("Not enough bytes for the DataPack body: "
					+ (bMsg.length - 12) + "/" + readCount);
			}
		}
		return bHead;
	}

	public static SMIP_DataPack readPack(InputStream is, OutputStream os) throws IOException {
		byte[] b = readPackBytes(is);
		SMIP_DataPack dp = null;
		switch (getInt(b, 4, 4)) {
			case SMIP_ACTIVE_TEST:
				dp = new SMIP_ActiveTest(b);
				new SMIP_ActiveTestResp(dp).writePack(os);
				break;
			case SMIP_ACTIVE_TEST_RESP:
				dp = new SMIP_ActiveTestResp(b);
				break;
			case SMIP_TERMINATE:
				dp = new SMIP_Terminate(b);
				new SMIP_TerminateResp(dp).writePack(os);
				break; //
			case SMIP_TERMINATE_RESP:
				dp = new SMIP_TerminateResp(b);
				break;
			case SMIP_CONNECT:
				dp = new SMIP_Connect(b);
				break;
			case SMIP_CONNECT_RESP:
				dp = new SMIP_ConnectResp(b);
				break;
			case SMIP_MO:
				dp = new SMIP_MO(b);
				new SMIP_MOResp(dp).writePack(os);
				break;
			case SMIP_MO_RESP:
				dp = new SMIP_MOResp(b);
				break;
			case SMIP_QUERY:
				dp = new SMIP_Query(b);
				new SMIP_QueryResp(dp).writePack(os);
				break; //
			case SMIP_QUERY_RESP:
				dp = new SMIP_QueryResp(b);
				break;
			case SMIP_MT:
				dp = new SMIP_MT(b);
				new SMIP_MTResp(dp).writePack(os);
				break;
			case SMIP_MT_RESP:
				dp = new SMIP_MTResp(b);
				break;
			case SMIP_ORDER:
				dp = new SMIP_Order(b);
				new SMIP_OrderResp(dp).writePack(os);
				break;
			case SMIP_ORDER_RESP:
				dp = new SMIP_OrderResp(b);
				break;
			default:
				dp = new SMIP_DataPack(b);
		}
		return dp;
	}

	/**
	 * return format: Status|ErrorDescription
	 * @return is null or empty string, validate passed! 
	 */
	public String validate(){
		return null;
	}

	

	public int getRespStatus(){
		String resp = validate();
		if(resp == null || resp.trim().length() < 1) {
			return 0;
		}
		String[] resps = resp.split("\\|");
		if (resps.length >= 1){
			try{
				return Integer.parseInt(resps[0]);
			}catch(Exception e){
				return 0;
			}
		} else {
			return 0;
		}
	}

	public String getErrStr(){
		String resp = validate();
		if (resp == null) {
			return "";
		}
		String[] resps = resp.split("[|]");
		if(resps.length > 1){
			return resps[1];
		} else {
			return "";
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SMIP_DataPack_Head").append("{TotalLength=").append(getTotalLength()).append(
				", CommandID=").append(getCommandID()).append(", SequenceID=").append(getSequenceID())
				.append("}");
		return sb.toString();
	}

	public static void main(String[] args) {
	}
}
