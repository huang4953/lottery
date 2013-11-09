package com.success.lottery.term.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.LotteryTermServiceInterf;
import com.success.lottery.term.service.interf.ToHtmlInterf;
import com.success.lottery.web.formbean.RaceBean;
import com.success.utils.ApplicationContextUtils;
import com.success.utils.AutoProperties;
import com.success.utils.FileUtils;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class CreateHtml implements WrapperListener {

	private Log logger = LogFactory.getLog(CreateHtml.class.getName());

	/**
	 * �������쿪���Ĳ��ֲ��ڵ����HTML
	 */
	public void cretaeTadayHTML() {
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		List<LotteryTermModel> termList = null;
		Map<Integer, LotteryTermModel> termMap = null;
		LotteryTermModel termModel = null;
		List<RaceBean> raceList = null;
		LotteryTermModel othertermModel = null;
		LotteryTermModel currentTerm;
		List<LotteryTermModel> historyTermList;// ��ʷ��������
		List<String[]> WinResultList = new ArrayList<String[]>();
		boolean flg = false;
		// ��ȡ���в������¿�������
		try {
			termList = termservice.queryAllLastTermInfo();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// ������ؾ�̬ҳ��

		termMap = new HashMap<Integer, LotteryTermModel>();
		for (LotteryTermModel term : termList) {
			termMap.put(term.getLotteryId(), term);
		}
		List<String> numlist = null;
		String type = "1000001";
		String url = AutoProperties.getString("DLTXX", "",
				"com.success.lottery.term.service.html");
		String termNo = termMap.get(1000001).getTermNo();
		String name = termNo + ".html";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", new Date().toLocaleString());
		param.put("userId", "0");
		param.put("userName", "Robot");
		param.put("keyword1", "1000001");
		param.put("keyword2", termNo);
		param.put("keyword3", "����͸��" + termNo + "��������ϸҳ��");
		param.put("keyword4", name);
		try {
			termModel = termMap.get(1000001);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			othertermModel = termservice.queryTermInfo(Integer
					.parseInt("1000005"), termModel.getTermNo());
			flg = tohtml.dltkjcxfileSource(name, "GBK", url, termModel,
					othertermModel);
			if (flg) {
				// ��¼��־
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
				// ���ɳɹ������������˵������Ʒ���
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("����͸", "GBK", url, numlist);
				param.put("keyword4", "select.html");
				param.put("keyword3", "����͸��" + termModel.getTermNo()
						+ "�����������б�");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("DLTLIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.dltlistfileSource("dltlist.html", "gbk", url,
						termList);
				param.put("keyword4", "dltlist.html");
				param.put("keyword3", "����͸��" + termModel.getTermNo()
						+ "�����ɲ����б�");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
				// �������Ʒ���30�ڣ�50�ڣ�100��
				url = AutoProperties.getString("ZSFX", "",
						"com.success.lottery.term.service.html");
				int[] arr = { 30, 50, 100 };
				for (int i : arr) {
					String names = "dlt" + i + ".html";
					termList = termservice.queryHaveWinTermInfo(Integer
							.parseInt(type), i);
					Collections.reverse(termList);
					flg = tohtml.dltfileSource(termList, names, "GBK", url);
					param.put("keyword4", names);
					param.put("keyword3", "����͸��" + termModel.getTermNo()
							+ "������" + i + "�����Ʒ���");
					if (flg)
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(40030, param);
					else
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(41030, param);
				}
				// ���ɹ��ʴ�������͸��ؾ�̬ҳ��
				currentTerm = termservice.queryTermCurrentInfo(1000001);
				Map<String, TreeMap<String, String[]>> superLotteryResult = currentTerm
						.getLotteryInfo().getSuperWinResult();
				String[] happyZodiacWinResult = termservice
						.queryTermCurrentInfo(1000005).getLotteryInfo()
						.getHappyZodiacWinResult();
				for (int i = 1; i < 9; i++)
					WinResultList.add(superLotteryResult.get("" + i).get("A"));
				WinResultList.add(happyZodiacWinResult);
				historyTermList = termservice.queryHaveWinTermInfo(1000001, 10);
				flg = tohtml.dltzjkaijianSource("dltzj.html", "GBK",
						AutoProperties.getString("DLTKJJG", "",
								"com.success.lottery.term.service.html"),
						currentTerm, historyTermList, WinResultList);
				param.put("keyword4", "dltzj.html");
				param.put("keyword3", "���ʴ�������͸���¿���");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);

		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		numlist = null;
		type = "1000002";
		url = AutoProperties.getString("QXCXX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1000002).getTermNo();
		name = termNo + ".html";
		Map<String, String> paramqxc = new HashMap<String, String>();
		paramqxc.put("keyword1", new Date().toLocaleString());
		paramqxc.put("userId", "0");
		paramqxc.put("userName", "Robot");
		paramqxc.put("keyword1", "1000002");
		paramqxc.put("keyword2", termNo);
		paramqxc.put("keyword3", "���ǲʵ�" + termModel.getTermNo() + "��������ϸҳ��");
		paramqxc.put("keyword4", name);
		try {
			termModel = termMap.get(1000002);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			flg = tohtml.qxckjcxfileSource(name, "GBK", url, termModel,
					othertermModel);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramqxc);
				// ���ɳɹ������������˵������Ʒ���
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("���ǲ�", "GBK", url, numlist);
				paramqxc.put("keyword4", "select.html");
				paramqxc.put("keyword3", "���ǲʵ�" + termModel.getTermNo()
						+ "�����������б�");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramqxc);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramqxc);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("QXCLIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.qxclistfileSource("qxclist.html", "gbk", url,
						termList);
				paramqxc.put("keyword4", "qxclist.html");
				paramqxc.put("keyword3", "���ǲʵ�" + termModel.getTermNo()
						+ "�����ɲ����б�");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramqxc);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramqxc);
				// �������Ʒ���30�ڣ�50�ڣ�100��
				url = AutoProperties.getString("ZSFX", "",
						"com.success.lottery.term.service.html");
				int[] arr = { 30, 50, 100 };
				for (int i : arr) {
					String names = "qxc" + i + ".html";
					termList = termservice.queryHaveWinTermInfo(Integer
							.parseInt(type), i);
					Collections.reverse(termList);
					flg = tohtml.qxcfileSource(termList, names, "GBK", url);
					paramqxc.put("keyword4", names);
					paramqxc.put("keyword3", "���ǲʵ�" + termModel.getTermNo()
							+ "������" + i + "�����Ʒ���");
					if (flg)
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(40030, paramqxc);
					else
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(41030, paramqxc);
				}
				currentTerm = termservice.queryTermCurrentInfo(1000002);
				Map<String, String[]> superLotteryResult = currentTerm
						.getLotteryInfo().getSevenColorWinResult();
				historyTermList = termservice.queryHaveWinTermInfo(1000002, 20);
				for (int i = 1; i < 6; i++)
					WinResultList.add(superLotteryResult.get("" + i));
				flg = tohtml.qxczjkaijianSource("qxczj.html", "GBK",
						AutoProperties.getString("QXCKJJG", "",
								"com.success.lottery.term.service.html"),
						currentTerm, historyTermList, WinResultList);
				paramqxc.put("keyword4", "qxczj.html");
				paramqxc.put("keyword3", "���ʴ������ǲ����¿���");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramqxc);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramqxc);
			} else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, paramqxc);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramqxc);
		}
		numlist = null;
		type = "1000003";
		url = AutoProperties.getString("PLSXX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1000003).getTermNo();
		name = termNo + ".html";
		Map<String, String> parampls = new HashMap<String, String>();
		parampls.put("keyword1", new Date().toLocaleString());
		parampls.put("userId", "0");
		parampls.put("userName", "Robot");
		parampls.put("keyword1", "1000003");
		parampls.put("keyword2", termNo);
		parampls.put("keyword3", "��������" + termModel.getTermNo() + "��������ϸҳ��");
		parampls.put("keyword4", name);
		try {
			termModel = termMap.get(1000003);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			flg = tohtml.plskjcxfileSource(name, "GBK", url, termModel,
					othertermModel);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, parampls);
				// ���ɳɹ������������˵������Ʒ���
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("������", "GBK", url, numlist);
				parampls.put("keyword3", "��������" + termModel.getTermNo()
						+ "�����������б�");
				parampls.put("keyword4", "select.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, parampls);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, parampls);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("PLSLIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.plslistfileSource("plslist.html", "gbk", url,
						termList);
				parampls.put("keyword3", "��������" + termModel.getTermNo()
						+ "�����ɲ����б�");
				parampls.put("keyword4", "plslist.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, parampls);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, parampls);
				// �������Ʒ���30�ڣ�50�ڣ�100��
				url = AutoProperties.getString("ZSFX", "",
						"com.success.lottery.term.service.html");
				int[] arr = { 30, 50, 100 };
				for (int i : arr) {
					String names = "pls" + i + ".html";
					termList = termservice.queryHaveWinTermInfo(Integer
							.parseInt(type), i);
					Collections.reverse(termList);
					parampls.put("keyword3", "��������" + termModel.getTermNo()
							+ "������" + i + "�����Ʒ���");
					parampls.put("keyword4", names);
					flg = tohtml.plsfileSource(termList, names, "GBK", url);
					if (flg)
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(40030, parampls);
					else
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(41030, parampls);
				}
				currentTerm = termservice.queryTermCurrentInfo(1000003);
				historyTermList = termservice.queryHaveWinTermInfo(1000003, 20);
				flg = tohtml.plszjkaijianSource("plszj.html", "GBK",
						AutoProperties.getString("PLSKJJG", "",
								"com.success.lottery.term.service.html"),
						currentTerm, historyTermList);
				parampls.put("keyword3", "���ʴ������������¿���");
				parampls.put("keyword4", "plszj.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, parampls);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, parampls);
			}
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					parampls);
		}
		numlist = null;
		type = "1000004";
		url = AutoProperties.getString("PLWXX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1000004).getTermNo();
		name = termNo + ".html";
		Map<String, String> paramplw = new HashMap<String, String>();
		paramplw.put("keyword1", new Date().toLocaleString());
		paramplw.put("userId", "0");
		paramplw.put("userName", "Robot");
		paramplw.put("keyword1", "1000004");
		paramplw.put("keyword2", termNo);
		paramplw.put("keyword3", "�������" + termModel.getTermNo() + "��������ϸҳ��");
		paramplw.put("keyword4", name);
		try {
			termModel = termMap.get(1000004);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			flg = tohtml.plwkjcxfileSource(name, "GBK", url, termModel,
					othertermModel);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramqxc);
				// ���ɳɹ������������˵������Ʒ���
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("������", "GBK", url, numlist);
				paramplw.put("keyword3", "�������" + termModel.getTermNo()
						+ "�����������б�ҳ��");
				paramplw.put("keyword4", "select.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramplw);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramplw);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("PLWLIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.plwlistfileSource("plwlist.html", "gbk", url,
						termList);
				paramplw.put("keyword3", "�������" + termModel.getTermNo()
						+ "�����ɲ����б�ҳ��");
				paramplw.put("keyword4", "plwlist.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramplw);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramplw);
				// �������Ʒ���30�ڣ�50�ڣ�100��
				url = AutoProperties.getString("ZSFX", "",
						"com.success.lottery.term.service.html");
				int[] arr = { 30, 50, 100 };
				for (int i : arr) {
					String names = "plw" + i + ".html";
					termList = termservice.queryHaveWinTermInfo(Integer
							.parseInt(type), i);
					Collections.reverse(termList);
					flg = tohtml.plwfileSource(termList, names, "GBK", url);
					paramplw.put("keyword3", "�������" + termModel.getTermNo()
							+ "������" + i + "�����Ʒ���HTML");
					paramplw.put("keyword4", names);
					if (flg)
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(40030, paramplw);
					else
						com.success.lottery.operatorlog.service.OperatorLogger
								.log(41030, paramplw);
				}
				currentTerm = termservice.queryTermCurrentInfo(1000004);
				historyTermList = termservice.queryHaveWinTermInfo(1000004, 20);
				flg = tohtml.plwzjkaijianSource("plwzj.html", "GBK",
						AutoProperties.getString("PLWKJJG", "",
								"com.success.lottery.term.service.html"),
						currentTerm, historyTermList);
				paramplw.put("keyword3", "���ʴ������������¿���");
				paramplw.put("keyword4", "plwzj.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramplw);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramplw);
			}
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramplw);
		}
		numlist = null;
		type = "1300001";
		url = AutoProperties.getString("ZCXX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1300001).getTermNo();
		name = termNo + ".html";
		Map<String, String> paramzc = new HashMap<String, String>();
		paramzc.put("keyword1", new Date().toLocaleString());
		paramzc.put("userId", "0");
		paramzc.put("userName", "Robot");
		paramzc.put("keyword1", "1300001");
		paramzc.put("keyword2", termNo);
		paramzc.put("keyword3", "ʤ���ʵ�" + termModel.getTermNo() + "��������ϸҳ��ҳ��");
		paramzc.put("keyword4", name);
		try {
			termModel = termMap.get(1300001);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			raceList = RaceBean.getRaceBeanList(termModel);
			othertermModel = termservice.queryTermInfo(Integer
					.parseInt("1300002"), termModel.getTermNo());
			flg = tohtml.zckjcxfileSource(name, "GBK", url, termModel,
					othertermModel, raceList);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramzc);
				// ���ɳɹ������������˵�
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("ʤ����", "GBK", url, numlist);
				paramzc.put("keyword3", "ʤ���ʵ�" + termModel.getTermNo()
						+ "�����������б�");
				paramzc.put("keyword4", "select.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramzc);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramzc);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("ZCLIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.zclistfileSource("zclist.html", "gbk", url,
						termList);
				paramzc.put("keyword3", "ʤ���ʵ�" + termModel.getTermNo()
						+ "�����ɲ����б�");
				paramzc.put("keyword4", "zclist.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramzc);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramzc);
			}
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramzc);
		}
		/** **************************************** */
		/** **************************************** */
		numlist = null;
		type = "1300003";
		url = AutoProperties.getString("ZC6XX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1300004).getTermNo();
		name = termNo + ".html";
		Map<String, String> paramzc6 = new HashMap<String, String>();
		paramzc6.put("keyword1", new Date().toLocaleString());
		paramzc6.put("userId", "0");
		paramzc6.put("userName", "Robot");
		paramzc6.put("keyword1", "1300003");
		paramzc6.put("keyword2", termNo);
		paramzc6.put("keyword3", "������ȫ��" + termModel.getTermNo() + "��������ϸҳ��ҳ��");
		paramzc6.put("keyword4", name);
		try {
			termModel = termMap.get(1300003);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			raceList = RaceBean.getRaceBeanList(termModel);
			flg = tohtml.zc6kjcxfileSource(name, "GBK", url, termModel,
					raceList);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramzc6);
				// ���ɳɹ������������˵�
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("������ȫ", "GBK", url, numlist);
				paramzc6.put("keyword3", "������ȫ��" + termModel.getTermNo()
						+ "�����������б�");
				paramzc6.put("keyword4", "select.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramzc6);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramzc6);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("ZC6LIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.zc6listfileSource("zc6list.html", "gbk", url,
						termList);
				paramzc6.put("keyword3", "������ȫ�������" + termModel.getTermNo()
						+ "�����ɲ����б�");
				paramzc6.put("keyword4", "zc6list.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramzc6);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramzc6);
			}
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramzc6);
		}
		/** **************************************** */
		numlist = null;
		type = "1300004";
		url = AutoProperties.getString("JQ4XX", "",
				"com.success.lottery.term.service.html");
		termNo = termMap.get(1300004).getTermNo();
		name = termNo + ".html";
		Map<String, String> paramjq4 = new HashMap<String, String>();
		paramjq4.put("keyword1", new Date().toLocaleString());
		paramjq4.put("userId", "0");
		paramjq4.put("userName", "Robot");
		paramjq4.put("keyword1", "1300004");
		paramjq4.put("keyword2", termNo);
		paramjq4.put("keyword3", "�ĳ������" + termModel.getTermNo() + "��������ϸҳ��ҳ��");
		paramjq4.put("keyword4", name);
		try {
			termModel = termMap.get(1300004);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			raceList = RaceBean.getRaceBeanList(termModel);
			flg = tohtml.jq4kjcxfileSource(name, "GBK", url, termModel,
					raceList);
			if (flg) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramjq4);
				// ���ɳɹ������������˵�
				numlist = termservice.queryHaveWinTermNos(Integer
						.parseInt(type), -1);
				flg = tohtml.dltseletfileSource("�ĳ�����", "GBK", url, numlist);
				paramjq4.put("keyword3", "�ĳ������" + termModel.getTermNo()
						+ "�����������б�");
				paramjq4.put("keyword4", "select.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramjq4);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramjq4);
				// ����Listҳ
				int num = 30;
				url = AutoProperties.getString("JQ4LIST", "",
						"com.success.lottery.term.service.html");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), num);
				flg = tohtml.jq4listfileSource("jq4list.html", "gbk", url,
						termList);
				paramjq4.put("keyword3", "�ĳ������" + termModel.getTermNo()
						+ "�����ɲ����б�");
				paramjq4.put("keyword4", "jq4list.html");
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, paramjq4);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, paramjq4);
			}
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramjq4);
		}
		// ���ɲ�ѯ��ҳ
		url = (AutoProperties.getString("KJCXSY", "",
				"com.success.lottery.term.service.html"));
		name = "index.html";
		Map<String, String> paramcx = new HashMap<String, String>();
		paramcx.put("keyword1", new Date().toLocaleString());
		paramcx.put("userId", "0");
		paramcx.put("userName", "Robot");
		paramcx.put("keyword1", "��ѯ��ҳ");
		paramcx.put("keyword2", "��ѯ��ҳ");
		paramcx.put("keyword3", "��ѯ��ҳ");
		paramcx.put("keyword4", name);
		try {
			termList = termservice.queryAllLastTermInfo();
			termMap = new HashMap<Integer, LotteryTermModel>();
			for (LotteryTermModel term : termList)
				termMap.put(term.getLotteryId(), term);
			flg = tohtml.kjlistfileSource(name, "gbk", url, termMap);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramcx);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, paramcx);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramcx);
		}
		// ��ҳ��̬ҳ��
		termList = null;
		termMap = null;
		url = AutoProperties.getString("ZCKJJG", "",
				"com.success.lottery.term.service.html");//
		name = "lef_index.html";
		Map<String, String> paramindex = new HashMap<String, String>();
		paramindex.put("keyword1", new Date().toLocaleString());
		paramindex.put("userId", "0");
		paramindex.put("userName", "Robot");
		paramindex.put("keyword1", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword2", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword3", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword4", name);
		try {
			termList = termservice.queryAllLastTermInfo();
			termMap = new HashMap<Integer, LotteryTermModel>();
			for (LotteryTermModel term : termList)
				termMap.put(term.getLotteryId(), term);
			flg = tohtml.kjlistfileSourceOnIndex(name, "gbk", url, termMap);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramindex);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, paramindex);

		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramindex);
		}
	}

	/**
	 * �����������Ʒ�������ѯ��ҳ
	 */
	public void createHTMLZS() {
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		List<LotteryTermModel> termList = null;
		Map<Integer, LotteryTermModel> termMap = null;
		boolean flg = false;
		// ������ؾ�̬ҳ��
		Map<String, String> param = new HashMap<String, String>();

		String type = "1000001";
		param.put("keyword1", new Date().toLocaleString());
		param.put("userId", "0");
		param.put("userName", "Robot");

		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		try {
			int[] arr = { 30, 50, 100 };
			for (int i : arr) {
				String names = "dlt" + i + ".html";
				param.put("keyword4", names);
				param.put("keyword1", type);
				param.put("keyword2", "��" + i + "��");
				param.put("keyword3", "����͸��" + i + "���Ʒ���");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), i);
				Collections.reverse(termList);
				flg = tohtml.dltfileSource(termList, names, "GBK", url);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			}
			type = "1000002";
			for (int i : arr) {
				String names = "qxc" + i + ".html";
				param.put("keyword4", names);
				param.put("keyword1", type);
				param.put("keyword2", "��" + i + "��");
				param.put("keyword3", "���ǲʽ�" + i + "���Ʒ���");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), i);
				Collections.reverse(termList);
				flg = tohtml.qxcfileSource(termList, names, "GBK", url);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);

			}
			type = "1000003";
			for (int i : arr) {
				String names = "pls" + i + ".html";
				param.put("keyword4", names);
				param.put("keyword1", type);
				param.put("keyword2", "��" + i + "��");
				param.put("keyword3", "��������" + i + "���Ʒ���");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), i);
				Collections.reverse(termList);
				flg = tohtml.plsfileSource(termList, names, "GBK", url);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);

			}
			type = "1000004";
			for (int i : arr) {
				String names = "plw" + i + ".html";
				param.put("keyword4", names);
				param.put("keyword1", type);
				param.put("keyword2", "��" + i + "��");
				param.put("keyword3", "�������" + i + "���Ʒ���");
				termList = termservice.queryHaveWinTermInfo(Integer
						.parseInt(type), i);
				Collections.reverse(termList);
				flg = tohtml.plwfileSource(termList, names, "GBK", url);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);

			}
		} catch (LotteryException e) {
			logger.error("���������Ʒ���HTML���ִ���" + e.toString());
		}
		url = (AutoProperties.getString("KJCXSY", "",
				"com.success.lottery.term.service.html"));
		String name = "index.html";
		param.put("keyword1", "��ѯ��ҳ");
		param.put("keyword2", "��ѯ��ҳ");
		param.put("keyword3", "��ѯ��ҳ");
		param.put("keyword4", name);
		try {
			termList = termservice.queryAllLastTermInfo();
			termMap = new HashMap<Integer, LotteryTermModel>();
			for (LotteryTermModel term : termList)
				termMap.put(term.getLotteryId(), term);

			flg = tohtml.kjlistfileSource(name, "gbk", url, termMap);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ����͸������ϸ������ҳ��
	 */
	public void dltckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		Map<String, String> param = new HashMap<String, String>();
		String type = "1000001";
		param.put("keyword1", new Date().toLocaleString());
		param.put("userId", "0");
		param.put("userName", "Robot");
		param.put("keyword1", type);
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;
		String url = AutoProperties.getString("DLTXX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			String termNo = string;
			LotteryTermModel termModel = null;
			LotteryTermModel othertermModel = null;
			String name = termNo + ".html";
			param.put("keyword2", string);
			param.put("keyword3", "����͸��" + termNo + "��������ϸҳ��");
			param.put("keyword4", name);
			try {
				if (StringUtils.isEmpty(termNo))
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				else
					termModel = termservice.queryTermInfo(Integer
							.parseInt(type), termNo);
				if (null == termModel || null == termModel.getLotteryInfo())
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				othertermModel = termservice.queryTermInfo(Integer
						.parseInt("1000005"), termModel.getTermNo());
				flg = tohtml.dltkjcxfileSource(name, "GBK", url, termModel,
						othertermModel);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} catch (LotteryException e) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
			}
		}

		int num = 30;
		param.put("keyword2", "�Ѿ�������");
		param.put("keyword3", "�����ڴ���͸�����б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			flg = tohtml.dltseletfileSource("����͸", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}

		String name = "dltlist.html";
		url = AutoProperties.getString("DLTLIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "��30�ڼ���ҳ��");
		param.put("keyword3", "���ɴ���͸����ҳ��");
		param.put("keyword4", name);
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltlistfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ���ǲʿ�����ϸ������ҳ��
	 */
	public void qxcckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1000002";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;
		String url = AutoProperties.getString("QXCXX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			String termNo = string;
			LotteryTermModel termModel = null;
			LotteryTermModel othertermModel = null;
			String name = termNo + ".html";
			param.put("keyword2", string);
			param.put("keyword3", "���ǲʵ�" + termNo + "��������ϸҳ��");
			param.put("keyword4", name);
			try {
				if (StringUtils.isEmpty(termNo))
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				else
					termModel = termservice.queryTermInfo(Integer
							.parseInt(type), termNo);
				if (null == termModel || null == termModel.getLotteryInfo())
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				flg = tohtml.qxckjcxfileSource(name, "GBK", url, termModel,
						othertermModel);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} catch (LotteryException e) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
			}
		}
		int num = 30;

		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			param.put("keyword2", "���ǲ������б�");
			param.put("keyword3", "�������ǲ������б�ҳ��");
			param.put("keyword4", "select.html");
			flg = tohtml.dltseletfileSource("���ǲ�", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "qxclist.html";
		url = AutoProperties.getString("QXCLIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "�������ǲʽ�30�ڼ���ҳ��");
		param.put("keyword3", "�������ǲʽ�30�ڼ���ҳ��");
		param.put("keyword4", name);
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.qxclistfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ������������ϸ������ҳ��
	 */
	public void plsckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1000003";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;
		String url = AutoProperties.getString("PLSXX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			String termNo = string;
			LotteryTermModel termModel = null;
			LotteryTermModel othertermModel = null;
			String name = termNo + ".html";
			param.put("keyword2", string);
			param.put("keyword3", "��������" + termNo + "��������ϸҳ��");
			param.put("keyword4", name);
			try {
				if (StringUtils.isEmpty(termNo))
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				else
					termModel = termservice.queryTermInfo(Integer
							.parseInt(type), termNo);
				if (null == termModel || null == termModel.getLotteryInfo())
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				flg = tohtml.plskjcxfileSource(name, "GBK", url, termModel,
						othertermModel);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} catch (LotteryException e) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
			}
		}
		int num = 30;
		param.put("keyword2", "�����б�ҳ��");
		param.put("keyword3", "���������������б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltseletfileSource("������", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "plslist.html";
		url = AutoProperties.getString("PLSLIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "������������30�ڼ���ҳ��");
		param.put("keyword3", "������������30�ڼ���ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.plslistfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ�����忪����ϸ������ҳ��
	 */
	public void plwckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1000004";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;
		String url = AutoProperties.getString("PLWXX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			String termNo = string;
			LotteryTermModel termModel = null;
			LotteryTermModel othertermModel = null;
			String name = termNo + ".html";
			param.put("keyword2", string);
			param.put("keyword3", "�������" + termNo + "��������ϸҳ��");
			param.put("keyword4", name);
			try {
				if (StringUtils.isEmpty(termNo))
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				else
					termModel = termservice.queryTermInfo(Integer
							.parseInt(type), termNo);
				if (null == termModel || null == termModel.getLotteryInfo())
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				flg = tohtml.plwkjcxfileSource(name, "GBK", url, termModel,
						othertermModel);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} catch (LotteryException e) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
			}
		}
		int num = 30;
		param.put("keyword2", "�����б�ҳ��");
		param.put("keyword3", "���������������б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltseletfileSource("������", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "plwlist.html";
		url = AutoProperties.getString("PLWLIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "�����������30�ڼ���ҳ��");
		param.put("keyword3", "�����������30�ڼ���ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.plwlistfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷʤ���ʿ�����ϸ������ҳ��
	 */
	public void zcckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1300001";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;
		String url = AutoProperties.getString("ZCXX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			List<RaceBean> raceList = null;
			String termNo = string;
			LotteryTermModel termModel = null;
			LotteryTermModel othertermModel = null;
			String name = termNo + ".html";
			param.put("keyword2", string);
			param.put("keyword3", "ʤ���ʵ�" + termNo + "��������ϸҳ��");
			param.put("keyword4", name);
			try {
				if (StringUtils.isEmpty(termNo))
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				else
					termModel = termservice.queryTermInfo(Integer
							.parseInt(type), termNo);
				if (null == termModel || null == termModel.getLotteryInfo())
					termModel = termservice.queryTermLastCashInfo(Integer
							.parseInt(type));
				raceList = RaceBean.getRaceBeanList(termModel);
				othertermModel = termservice.queryTermInfo(Integer
						.parseInt("1300002"), termModel.getTermNo());
				flg = tohtml.zckjcxfileSource(name, "GBK", url, termModel,
						othertermModel, raceList);
				if (flg)
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							40030, param);
				else
					com.success.lottery.operatorlog.service.OperatorLogger.log(
							41030, param);
			} catch (LotteryException e) {
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
			}
		}
		int num = 30;
		param.put("keyword2", "�����б�ҳ��");
		param.put("keyword3", "����ʤ���������б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltseletfileSource("ʤ����", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "zclist.html";
		url = AutoProperties.getString("ZCLIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "����ʤ���ʽ�30�ڼ���ҳ��");
		param.put("keyword3", "����ʤ���ʽ�30�ڼ���ҳ��");
		param.put("keyword4", name);
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.zclistfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ�ĳ����򿪽���ϸ������ҳ��
	 */
	public void jq4ckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1300004";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;

		String url = AutoProperties.getString("JQ4XX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			this.jq4kjxx(string);
		}
		int num = 30;
		param.put("keyword2", "�����б�ҳ��");
		param.put("keyword3", "�����ĳ����������б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltseletfileSource("�ĳ�����", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "jq4list.html";
		url = AutoProperties.getString("JQ4LIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "�����ĳ������30�ڼ���ҳ��");
		param.put("keyword3", "�����ĳ������30�ڼ���ҳ��");
		param.put("keyword4", name);
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.jq4listfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ʷ�ĳ����򿪽���ϸ������ҳ��
	 */
	public void zc6ckxxHTML() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<String> numlist = null;
		List<LotteryTermModel> termList = null;
		String type = "1300003";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),
					-1);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LotteryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean flg = false;

		String url = AutoProperties.getString("ZC6XX", "",
				"com.success.lottery.term.service.html");
		for (String string : numlist) {
			this.zc6kjxx(string);
		}
		int num = 30;
		param.put("keyword2", "�����б�ҳ��");
		param.put("keyword3", "����������ȫ�����б�ҳ��");
		param.put("keyword4", "select.html");
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.dltseletfileSource("������ȫ", "GBK", url, numlist);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
		String name = "zc6list.html";
		url = AutoProperties.getString("ZC6LIST", "",
				"com.success.lottery.term.service.html");
		param.put("keyword2", "����������ȫ��30�ڼ���ҳ��");
		param.put("keyword3", "����������ȫ��30�ڼ���ҳ��");
		param.put("keyword4", name);
		try {
			termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
					num);
			flg = tohtml.zc6listfileSource(name, "gbk", url, termList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * �ĳ�����ĳһ�ڵĲ�ѯ��ϸҳ��
	 * 
	 * @param string
	 */
	public void jq4kjxx(String string) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		String type = "1300004";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		String url = AutoProperties.getString("JQ4XX", "",
				"com.success.lottery.term.service.html");
		List<RaceBean> raceList = null;
		String termNo = string;
		LotteryTermModel termModel = null;
		String name = termNo + ".html";
		param.put("keyword2", string);
		param.put("keyword3", "�ĳ������" + termNo + "��������ϸҳ��");
		param.put("keyword4", name);
		Boolean flg = false;
		try {
			if (StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type),
						termNo);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			raceList = RaceBean.getRaceBeanList(termModel);
			flg = tohtml.jq4kjcxfileSource(name, "GBK", url, termModel,
					raceList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ������ȫĳһ�ڵĲ�ѯ��ϸҳ��
	 * 
	 * @param string
	 */
	public void zc6kjxx(String string) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		String type = "1300003";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword1", type);
		param.put("userId", "0");
		param.put("userName", "Robot");
		String url = AutoProperties.getString("ZC6XX", "",
				"com.success.lottery.term.service.html");
		List<RaceBean> raceList = null;
		String termNo = string;
		LotteryTermModel termModel = null;
		String name = termNo + ".html";
		param.put("keyword2", string);
		param.put("keyword3", "������ȫ��" + termNo + "��������ϸҳ��");
		param.put("keyword4", name);
		Boolean flg = false;
		try {
			if (StringUtils.isEmpty(termNo))
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			else
				termModel = termservice.queryTermInfo(Integer.parseInt(type),
						termNo);
			if (null == termModel || null == termModel.getLotteryInfo())
				termModel = termservice.queryTermLastCashInfo(Integer
						.parseInt(type));
			raceList = RaceBean.getRaceBeanList(termModel);
			flg = tohtml.zc6kjcxfileSource(name, "GBK", url, termModel,
					raceList);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ���ֲ���ѡ����
	 */
	public void dlc_rxfileSource(String name, int count) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<LotteryTermModel> termList;
		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ���ѡǰ" + count + "�����Ʒ���ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			termList = termservice.queryHaveWinTermInfo(1200001, 65 * count);
			Collections.reverse(termList);
			boolean flg = tohtml.dlc_rxfileSource(termList, name, "GBK", url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();

	}

	/**
	 * ���ֲ�ǰ��ֱѡ����
	 * @param name
	 * @param count
	 */
	public void dlc_qe_zhifileSource(String name, int count) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<LotteryTermModel> termList;
		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ�ѡǰ��ֱѡǰ" + count + "�����Ʒ���ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			termList = termservice.queryHaveWinTermInfo(1200001, 65 * count);
			Collections.reverse(termList);
			boolean flg = tohtml.dlc_qe_zhifileSource(termList, name, "GBK",
					url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();

	}

	/**
	 * ���ֲ�ǰ��ֱѡ����
	 * @param name
	 * @param count
	 */
	public void dlc_qs_zhifileSource(String name, int count) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<LotteryTermModel> termList;
		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ�ѡǰ��ֱѡǰ" + count + "�����Ʒ���ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			termList = termservice.queryHaveWinTermInfo(1200001, 65 * count);
			Collections.reverse(termList);

			boolean flg = tohtml.dlc_qs_zhifileSource(termList, name, "GBK",
					url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();

	}

	/**
	 * ���ֲ�ǰ����ѡ����
	 * @param name
	 * @param count
	 */
	public void dlc_qe_zufileSource(String name, int count) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<LotteryTermModel> termList;
		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ�ѡǰ����ѡǰ" + count + "�����Ʒ���ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			termList = termservice.queryHaveWinTermInfo(1200001, 65 * count);
			Collections.reverse(termList);

			boolean flg = tohtml
					.dlc_qe_zufileSource(termList, name, "GBK", url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();

	}

	/**
	 * ���ֲ�ǰ����ѡ����
	 * @param name
	 * @param count
	 */
	public void dlc_qs_zufileSource(String name, int count) {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		List<LotteryTermModel> termList;
		String url = AutoProperties.getString("ZSFX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ�ѡǰ����ѡǰ" + count + "�����Ʒ���ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			termList = termservice.queryHaveWinTermInfo(1200001, 65 * count);
			Collections.reverse(termList);

			boolean flg = tohtml
					.dlc_qs_zufileSource(termList, name, "GBK", url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();

	}

	/**
	 * ���ֲ������˵�
	 */
	public void dlc_selectPage() {
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		String url = AutoProperties.getString("DLCXX", "",
				"com.success.lottery.term.service.html");
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ������˵��б�ҳ��");
		param.put("keyword4", "select.html");
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			boolean flg = tohtml.dlcseletfileSource("GBK", url);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ���ֲ�list��ҳ
	 * @param date
	 */
	public void dlclist(String date) {
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		Calendar today = Calendar.getInstance();
		List<LotteryTermModel> list = termservice
				.queryHaveWinTermInfoToDate(date);
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		String url = AutoProperties.getString("DLCLIST", "",
				"com.success.lottery.term.service.html");
		String name = "dlclist.html";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲʲ�ѯ��ҳҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			boolean flg = tohtml.dlclistfileSource(name, "GBK", url, list);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					param);
		}
	}

	/**
	 * ��ʷ��
	 * @param date
	 */
	public void dlcofDateTolist(String date) {
		LotteryTermServiceInterf termservice = ApplicationContextUtils
				.getService("lotteryTermService",
						LotteryTermServiceInterf.class);
		Calendar today = Calendar.getInstance();
		List<LotteryTermModel> list = termservice
				.queryHaveWinTermInfoToDate(date);
		ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
				ToHtmlInterf.class);
		String url = AutoProperties.getString("DLCXX", "",
				"com.success.lottery.term.service.html");
		String name = date + ".html";
		Map<String, String> param = new HashMap<String, String>();
		param.put("keyword2", "");
		param.put("keyword3", "���ֲ�" + date + "��ϸ����ҳ��");
		param.put("keyword4", name);
		param.put("keyword1", "1200001");
		param.put("userId", "0");
		param.put("userName", "Robot");
		try {
			boolean flg = tohtml.dlclistfileSource(name, "GBK", url, list);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, param);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, param);
		} catch (LotteryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ɶ��ֲ���ʷ����
	 */
	public void dlcckxxHTML() {
		Calendar strat=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		strat.set(Calendar.YEAR, 2010);
		strat.set(Calendar.MONTH,4);
		strat.set(Calendar.DAY_OF_MONTH, 28);
		SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
		while (1 == 1) {
			dlcofDateTolist(sf.format(strat.getTime()));
			if (sf.format(strat.getTime()).equals(sf.format(end.getTime())))
				break;
			strat.add(Calendar.DAY_OF_MONTH, 1);
		}
	}
	/**
	 * ��ʼ�����ֲ����Ʒ���
	 */
	public void cretaeHTMLZS_DLC(){
		this.dlc_selectPage();
		this.dlc_rxfileSource("dlc_rx_1.html",1);
		this.dlc_rxfileSource("dlc_rx_2.html",2);
		this.dlc_rxfileSource("dlc_rx_3.html",3);
		this.dlc_rxfileSource("dlc_rx_5.html",5);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_1.html",1);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_2.html",2);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_3.html",3);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_5.html",5);
		this.dlc_qe_zufileSource("dlc_qe_zu_1.html",1);
		this.dlc_qe_zufileSource("dlc_qe_zu_2.html",2);
		this.dlc_qe_zufileSource("dlc_qe_zu_3.html",3);
		this.dlc_qe_zufileSource("dlc_qe_zu_5.html",5);
		this.dlc_qs_zufileSource("dlc_qs_zu_1.html",1);
		this.dlc_qs_zufileSource("dlc_qs_zu_2.html",2);
		this.dlc_qs_zufileSource("dlc_qs_zu_3.html",3);
		this.dlc_qs_zufileSource("dlc_qs_zu_5.html",5);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_1.html", 1);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_2.html", 2);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_3.html", 3);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_5.html", 5);
		this.fisIndex_left();
	}
	//ʮ���ӵ���һ�εķ���
    public void cretaeTadayToDlcHTML(){
    	SimpleDateFormat sf = new SimpleDateFormat("yyMMdd");
    	String date=sf.format(new Date());
        this.dlclist(date);
        this.dlcofDateTolist(date);
        this.dlc_selectPage();
		this.dlc_rxfileSource("dlc_rx_1.html",1);
		this.dlc_rxfileSource("dlc_rx_2.html",2);
		this.dlc_rxfileSource("dlc_rx_3.html",3);
		this.dlc_rxfileSource("dlc_rx_5.html",5);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_1.html",1);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_2.html",2);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_3.html",3);
		this.dlc_qe_zhifileSource("dlc_qe_zhi_5.html",5);
		this.dlc_qe_zufileSource("dlc_qe_zu_1.html",1);
		this.dlc_qe_zufileSource("dlc_qe_zu_2.html",2);
		this.dlc_qe_zufileSource("dlc_qe_zu_3.html",3);
		this.dlc_qe_zufileSource("dlc_qe_zu_5.html",5);
		this.dlc_qs_zufileSource("dlc_qs_zu_1.html",1);
		this.dlc_qs_zufileSource("dlc_qs_zu_2.html",2);
		this.dlc_qs_zufileSource("dlc_qs_zu_3.html",3);
		this.dlc_qs_zufileSource("dlc_qs_zu_5.html",5);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_1.html", 1);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_2.html", 2);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_3.html", 3);
		this.dlc_qs_zhifileSource("dlc_qs_zhi_5.html", 5);
		this.fisIndex_left();
    }
    public void fisIndex_left(){
    	List<LotteryTermModel> termList = null;
		Map<Integer, LotteryTermModel> termMap = null;
		String url = AutoProperties.getString("ZCKJJG", "",
				"com.success.lottery.term.service.html");//
		String name = "lef_index.html";
		Map<String, String> paramindex = new HashMap<String, String>();
		paramindex.put("keyword1", new Date().toLocaleString());
		LotteryTermServiceInterf termservice = ApplicationContextUtils
		.getService("lotteryTermService",
				LotteryTermServiceInterf.class);
			ToHtmlInterf tohtml = ApplicationContextUtils.getService("tohtml",
		ToHtmlInterf.class);
		paramindex.put("userId", "0");
		paramindex.put("userName", "Robot");
		paramindex.put("keyword1", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword2", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword3", "��ҳ���¿�����̬ҳ��");
		paramindex.put("keyword4", name);
		try {
			termList = termservice.queryAllLastTermInfo();
			termMap = new HashMap<Integer, LotteryTermModel>();
			for (LotteryTermModel term : termList)
				termMap.put(term.getLotteryId(), term);
			boolean flg = tohtml.kjlistfileSourceOnIndex(name, "gbk", url, termMap);
			if (flg)
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						40030, paramindex);
			else
				com.success.lottery.operatorlog.service.OperatorLogger.log(
						41030, paramindex);

		} catch (LotteryException e) {
			com.success.lottery.operatorlog.service.OperatorLogger.log(41030,
					paramindex);
		}
    }
	public static void main(String[] args) {
		/************* ���´����벻Ҫ�Ķ��������Ӱ��Wrapper���� ***********/
		CreateHtml ch = new CreateHtml();
		if (args == null || args.length == 0) {
			//���ֲʸ��¿�����������Ʒ���
			ch.cretaeTadayHTML();
		} else if ("INIT".equals(args[0])) {
			ch.dltckxxHTML();
			ch.qxcckxxHTML();
			ch.plsckxxHTML();
			ch.plwckxxHTML();
			//���ʤ���ʿ�������
			ch.zcckxxHTML();
			//��ʽ���ʿ�������
			ch.jq4ckxxHTML();
			//��ʰ�ȫ����������
			ch.zc6ckxxHTML();
			//���ֲ����Ʒ���
			ch.createHTMLZS();
			//���ֲʿ�������
			ch.dlcckxxHTML();
			//���ֲ����Ʒ���
			ch.cretaeHTMLZS_DLC();
		} else if("DLC".equals(args[0])){
			//���ֲʸ������Ʒ����Ϳ�������
			ch.cretaeTadayToDlcHTML();
		}
		/************* ���ϴ����벻Ҫ�Ķ��������Ӱ��Wrapper���� ***********/

//		CreateHtml ch = new CreateHtml();
//		ch.dlclist("110109");
//		 Calendar strat=Calendar.getInstance();
//		 Calendar end=Calendar.getInstance();
//		 strat.set(Calendar.YEAR, 2010);
//		 strat.set(Calendar.MONTH,4);
//		 strat.set(Calendar.DAY_OF_MONTH, 28);
//		 end.set(Calendar.YEAR, 2011);
//		 end.set(Calendar.MONTH,1);
//		 end.set(Calendar.DAY_OF_MONTH, 9);
//		 ch.dlcckxxHTML(strat, end);
//		 ch.dlc_selectPage();
//		 ch.dlc_rxfileSource("dlc_rx_1.html",1);
//		 ch.dlc_rxfileSource("dlc_rx_2.html",2);
//		 ch.dlc_rxfileSource("dlc_rx_3.html",3);
//		 ch.dlc_rxfileSource("dlc_rx_5.html",5);
//		 ch.dlc_qe_zhifileSource("dlc_qe_zhi_1.html",1);
//		 ch.dlc_qe_zhifileSource("dlc_qe_zhi_2.html",2);
//		 ch.dlc_qe_zhifileSource("dlc_qe_zhi_3.html",3);
//		 ch.dlc_qe_zhifileSource("dlc_qe_zhi_5.html",5);
//		 ch.dlc_qe_zufileSource("dlc_qe_zu_1.html",1);
//		 ch.dlc_qe_zufileSource("dlc_qe_zu_2.html",2);
//		 ch.dlc_qe_zufileSource("dlc_qe_zu_3.html",3);
//		 ch.dlc_qe_zufileSource("dlc_qe_zu_5.html",5);
//		 ch.dlc_qs_zufileSource("dlc_qs_zu_1.html",1);
//		 ch.dlc_qs_zufileSource("dlc_qs_zu_2.html",2);
//		 ch.dlc_qs_zufileSource("dlc_qs_zu_3.html",3);
//		 ch.dlc_qs_zufileSource("dlc_qs_zu_5.html",5);
//		 ch.dlc_qs_zhifileSource("dlc_qs_zhi_1.html", 1);
//		 ch.dlc_qs_zhifileSource("dlc_qs_zhi_2.html", 2);
//		 ch.dlc_qs_zhifileSource("dlc_qs_zhi_3.html", 3);
//		 ch.dlc_qs_zhifileSource("dlc_qs_zhi_5.html", 5);
		// // if(args == null || args.length == 0){
		 //ch.cretaeTadayHTML();
		// }else if("INIT".equals(args[0])){
//		 ch.dltckxxHTML();
//		 ch.qxcckxxHTML();
//		 ch.plsckxxHTML();
//		 ch.plwckxxHTML();
//		 ch.zcckxxHTML();
//		 ch.createHTMLZS();
//		 ch.jq4ckxxHTML();
//		 ch.zc6ckxxHTML();
		// }

		// ch.zc6kjxx("09001");

		// ch.jq4kjxx("09001");
		// ��ҳ��̬ҳ��
		// List<LotteryTermModel> termList =null;
		// Map<Integer,LotteryTermModel> termMap=null;
		// String
		// url=AutoProperties.getString("ZCKJJG","","com.success.lottery.term.service.html");//
		// boolean flg=false;
		// String name="lef_index.html";
		// try {
		// termList = termservice.queryAllLastTermInfo();
		// termMap = new HashMap<Integer, LotteryTermModel>();
		// for(LotteryTermModel term : termList)
		// termMap.put(term.getLotteryId(), term);
		// flg=tohtml.kjlistfileSourceOnIndex(name, "gbk", url, termMap);
		// if(flg)
		// System.out.println("��ҳ���¿������ɳɹ�");
		// else
		// System.out.println("��ҳ���¿�������ʧ��");
		//					
		// } catch (LotteryException e) {
		// System.out.println("��ҳ���¿��������쳣:"+e.toString());
		// e.printStackTrace();
		// }
		// //���ʴ�������͸��̬ҳ��
		// LotteryTermModel currentTerm;
		// List<LotteryTermModel> historyTermList;//��ʷ��������
		// List<String[]> WinResultList;
		// WinResultList = new ArrayList<String[]>();
		// try {
		// currentTerm = termservice.queryTermCurrentInfo(1000001);
		// Map<String,TreeMap<String, String[]>> superLotteryResult =
		// currentTerm.getLotteryInfo().getSuperWinResult();
		// String[] happyZodiacWinResult =
		// termservice.queryTermCurrentInfo(1000005).getLotteryInfo().getHappyZodiacWinResult();
		// for(int i=1;i<9;i++)
		// WinResultList.add(superLotteryResult.get(""+i).get("A"));
		// WinResultList.add(happyZodiacWinResult);
		// historyTermList = termservice.queryHaveWinTermInfo(1000001, 10);
		// boolean flg=tohtml.dltzjkaijianSource("dltzj.html",
		// "GBK",AutoProperties.getString("DLTKJJG","","com.success.lottery.term.service.html")
		// , currentTerm, historyTermList, WinResultList);
		// if(flg)
		// System.out.println("���ʴ�������͸���¿������ɳɹ�");
		// else
		// System.out.println("���ʴ�������͸��������ʧ��");
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// System.out.println("���ʴ�������͸���¿��������쳣:"+e.toString());
		// e.printStackTrace();
		// }
		// ================���ʴ������ǲ����¿���=============
		// try {
		// currentTerm = termservice.queryTermCurrentInfo(1000002);
		// Map<String,String[]> superLotteryResult =
		// currentTerm.getLotteryInfo().getSevenColorWinResult();
		// historyTermList = termservice.queryHaveWinTermInfo(1000002, 20);
		// for(int i=1;i<6;i++)
		// WinResultList.add(superLotteryResult.get(""+i));
		// boolean flg=tohtml.qxczjkaijianSource("qxczj.html",
		// "GBK",AutoProperties.getString("QXCKJJG","","com.success.lottery.term.service.html")
		// , currentTerm, historyTermList, WinResultList);
		// if(flg)
		// System.out.println("���ʴ������ǲ����¿������ɳɹ�");
		// else
		// System.out.println("���ʴ������ǲ�͸��������ʧ��");
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// System.out.println("���ʴ������ǲ����¿��������쳣:"+e.toString());
		// e.printStackTrace();
		// }
		// //====================���ʴ������������¿���===========
		// try {
		// currentTerm = termservice.queryTermCurrentInfo(1000003);
		// historyTermList = termservice.queryHaveWinTermInfo(1000003, 20);
		// boolean flg=tohtml.plszjkaijianSource("plszj.html",
		// "GBK",AutoProperties.getString("PLSKJJG","","com.success.lottery.term.service.html")
		// , currentTerm, historyTermList);
		// if(flg)
		// System.out.println("���ʴ������������¿������ɳɹ�");
		// else
		// System.out.println("���ʴ���������͸��������ʧ��");
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// System.out.println("���ʴ������������¿��������쳣:"+e.toString());
		// e.printStackTrace();
		// }
		// //====================���ʴ������������¿���===========
		// try {
		// currentTerm = termservice.queryTermCurrentInfo(1000004);
		// historyTermList = termservice.queryHaveWinTermInfo(1000004, 20);
		// boolean flg=tohtml.plwzjkaijianSource("plwzj.html",
		// "GBK",AutoProperties.getString("PLWKJJG","","com.success.lottery.term.service.html")
		// , currentTerm, historyTermList);
		// if(flg)
		// System.out.println("���ʴ������������¿������ɳɹ�");
		// else
		// System.out.println("���ʴ���������͸��������ʧ��");
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// System.out.println("���ʴ������������¿��������쳣:"+e.toString());
		// e.printStackTrace();
		// }

		// ���Դ���͸
		// int lottype=1000001;
		// List<LotteryTermModel> termList;
		// String name="dlt100.html";
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\direction";
		// boolean flg=false;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// termList = termservice.queryHaveWinTermInfo(lottype, 100);
		// Collections.reverse(termList);
		// //yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();
		//			
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// termList=null;
		// }
		// // //�������ǲ�
		// int lottype=1000002;
		// List<LotteryTermModel> termList;
		// String name="qxc100.html";
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\direction";
		// boolean flg=false;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// termList = termservice.queryHaveWinTermInfo(lottype, 100);
		// Collections.reverse(termList);
		// flg=termservice.qxcfileSource(termList, name, "GBK", url);
		// //yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();
		//			
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// termList=null;
		// }
		// //����������
		// int lottype=1000003;
		// List<LotteryTermModel> termList;
		// String name="pls50.html";
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\direction";
		// boolean flg=false;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// termList = termservice.queryHaveWinTermInfo(lottype, 50);
		// Collections.reverse(termList);
		// flg=termservice.plsfileSource(termList, name, "GBK", url);
		// //yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();
		//			
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// termList=null;
		// }
		// //����������
		// int lottype=1000004;
		// List<LotteryTermModel> termList;
		// String name="plw100.html";
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\direction";
		// boolean flg=false;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// termList = termservice.queryHaveWinTermInfo(lottype, 100);
		// Collections.reverse(termList);
		// flg=termservice.plwfileSource(termList, name, "GBK", url);
		// //yiLouMap = termList.get(0).getLotteryInfo().getMissCountResult();
		//			
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// termList=null;
		// }
		// //���Դ���͸������ϸ
		/***********************************************************************
		 * queryTermInfo queryTermLastCashInfo ������ type���ֱ�� ���أ� ��ѯ�ò������һ�ڵ���ϸ����
		 **********************************************************************/
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// List<String> numlist=null;
		// String type="1000001";
		// try {
		// numlist = termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// } catch (NumberFormatException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (LotteryException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\dlthtml";
		// for (String string : numlist) {
		// String termNo=string;
		// LotteryTermModel termModel= null;
		// LotteryTermModel othertermModel = null;
		// List<RaceBean> raceList;
		// String name=termNo+".html";
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// if(type.equals("1300002"))
		// type="1300001";
		// try {
		// if(StringUtils.isEmpty(termNo))
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// else
		// termModel = termservice.queryTermInfo(Integer.parseInt(type),
		// termNo);
		// if(null == termModel || null == termModel.getLotteryInfo())
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// if(type.equals("1000001"))
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1000005"),
		// termModel.getTermNo());
		// if(type.equals("1300001")){
		// raceList = RaceBean.getRaceBeanList(termModel);
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1300002"),
		// termModel.getTermNo());
		// }
		//					
		//					
		// flg=termservice.dltkjcxfileSource(name, "GBK", url, termModel,
		// othertermModel);
		// } catch (LotteryException e) {}
		// System.out.println(flg);
		// }
		// try {
		// flg=termservice.dltseletfileSource("����͸", "GBK", url, numlist);
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//			
		//		
		// //�������ǲʿ�����ϸ
		// /**queryTermInfo queryTermLastCashInfo
		// * ������ type���ֱ��
		// * ���أ� ��ѯ�ò������һ�ڵ���ϸ����
		// * ***/ List<String> numlist= null;
		// String type="1000002";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\qxchtml";
		// try {
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// } catch (NumberFormatException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (LotteryException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// LotteryTermModel termModel= null;
		// LotteryTermModel othertermModel = null;
		// boolean flg=false;
		// for (String string : numlist) {
		// String termNo=string;
		// List<RaceBean> raceList;
		// String name=termNo+".html";
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// if(type.equals("1300002"))
		// type="1300001";
		// try {
		// if(StringUtils.isEmpty(termNo))
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// else
		// termModel = termservice.queryTermInfo(Integer.parseInt(type),
		// termNo);
		// if(null == termModel || null == termModel.getLotteryInfo())
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// if(type.equals("1000001"))
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1000005"),
		// termModel.getTermNo());
		// if(type.equals("1300001")){
		// raceList = RaceBean.getRaceBeanList(termModel);
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1300002"),
		// termModel.getTermNo());
		// }
		// flg=termservice.qxckjcxfileSource(name, "GBK", url, numlist,
		// termModel, othertermModel);
		// } catch (LotteryException e) {}
		//
		// System.out.println(flg);
		// }
		// try {
		// flg=termservice.dltseletfileSource("���ǲ�", "GBK", url, numlist);
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//		
		// ����������������ϸ
		/***********************************************************************
		 * queryTermInfo queryTermLastCashInfo ������ type���ֱ�� ���أ� ��ѯ�ò������һ�ڵ���ϸ����
		 **********************************************************************/
		// String type="1000003";
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\plshtml";
		//		
		// LotteryTermModel termModel= null;
		// LotteryTermModel othertermModel = null;
		// List<String> numlist= null;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// } catch (NumberFormatException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (LotteryException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// boolean flg=false;
		// for (String string : numlist) {
		// String termNo=string;
		// List<RaceBean> raceList;
		// String name=termNo+".html";
		//		
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// if(type.equals("1300002"))
		// type="1300001";
		// try {
		// if(StringUtils.isEmpty(termNo))
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// else
		// termModel = termservice.queryTermInfo(Integer.parseInt(type),
		// termNo);
		// if(null == termModel || null == termModel.getLotteryInfo())
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// if(type.equals("1000001"))
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1000005"),
		// termModel.getTermNo());
		// if(type.equals("1300001")){
		// raceList = RaceBean.getRaceBeanList(termModel);
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1300002"),
		// termModel.getTermNo());
		// }
		// flg=termservice.plskjcxfileSource(name, "GBK", url, numlist,
		// termModel, othertermModel);
		// } catch (LotteryException e) {}
		//
		// System.out.println(flg);
		//
		// }
		//		
		// try {
		// flg=termservice.dltseletfileSource("������", "GBK", url, numlist);
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// //������
		// String type="1000004";
		//		
		// LotteryTermModel termModel= null;
		// LotteryTermModel othertermModel = null;
		// List<String> numlist= null;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\plwhtml";
		// List<RaceBean> raceList;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// } catch (NumberFormatException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (LotteryException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// boolean flg=false;
		// for (String string : numlist) {
		// String termNo=string;
		// String name=termNo+".html";
		//		
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// if(type.equals("1300002"))
		// type="1300001";
		// try {
		// if(StringUtils.isEmpty(termNo))
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// else
		// termModel = termservice.queryTermInfo(Integer.parseInt(type),
		// termNo);
		// if(null == termModel || null == termModel.getLotteryInfo())
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// if(type.equals("1000001"))
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1000005"),
		// termModel.getTermNo());
		// if(type.equals("1300001")){
		// raceList = RaceBean.getRaceBeanList(termModel);
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1300002"),
		// termModel.getTermNo());
		// }
		//					
		//					
		// flg=termservice.plwkjcxfileSource(name, "GBK", url, numlist,
		// termModel, othertermModel);
		// } catch (LotteryException e) {}
		//
		// System.out.println(flg);
		// }
		// try {
		// flg=termservice.dltseletfileSource("������", "GBK", url, numlist);
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// // //ʤ����
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// String type="1300001";
		//		
		// LotteryTermModel termModel= null;
		// LotteryTermModel othertermModel = null;
		// List<String> numlist= null;
		// try {
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// } catch (NumberFormatException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (LotteryException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\zchtml";
		// boolean flg=false;
		// for (String string : numlist) {
		// List<RaceBean> raceList= null;
		// String termNo=string;
		// String name=termNo+".html";
		//			
		//			
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// if(type.equals("1300002"))
		// type="1300001";
		// try {
		// if(StringUtils.isEmpty(termNo))
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// else
		// termModel = termservice.queryTermInfo(Integer.parseInt(type),
		// termNo);
		// if(null == termModel || null == termModel.getLotteryInfo())
		// termModel =
		// termservice.queryTermLastCashInfo(Integer.parseInt(type));
		// if(type.equals("1000001"))
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1000005"),
		// termModel.getTermNo());
		// if(type.equals("1300001")){
		// raceList = RaceBean.getRaceBeanList(termModel);
		// othertermModel =
		// termservice.queryTermInfo(Integer.parseInt("1300002"),
		// termModel.getTermNo());
		// }
		// flg=termservice.zckjcxfileSource(name, "GBK", url, numlist,
		// termModel, othertermModel,raceList);
		// } catch (LotteryException e) {}
		//
		// System.out.println(flg);
		// }
		// try {
		// flg=termservice.dltseletfileSource("ʤ����", "GBK", url, numlist);
		// System.out.println(flg);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//		
		// ���Դ���͸list
		// String type="1000001";
		// int num=30;
		// List<LotteryTermModel> termList =null;
		// List<String> numlist=null;
		// String name="dltlist.html";
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\dlt";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// try {
		// termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
		// num);
		// //��ý������ڲ��ڼ���
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// flg=termservice.dltlistfileSource(name, "gbk", url, numlist,
		// termList);
		// } catch (LotteryException e) {
		// e.printStackTrace();
		// }
		// System.out.println(flg);
		// //���ǲ�
		// String type="1000002";
		// int num=30;
		// List<LotteryTermModel> termList =null;
		// List<String> numlist=null;
		// String name="qxclist.html";
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\qxc";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// try {
		// termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
		// num);
		// //��ý������ڲ��ڼ���
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// flg=termservice.qxclistfileSource(name, "gbk", url, numlist,
		// termList);
		// } catch (LotteryException e) {k
		// e.printStackTrace();
		// }
		// System.out.println(flg);
		// //������
		// String type="1000003";
		// int num=30;
		// List<LotteryTermModel> termList =null;
		// List<String> numlist=null;
		// String name="plslist.html";
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\pls";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// try {
		// termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
		// num);
		// //��ý������ڲ��ڼ���
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// flg=termservice.plslistfileSource(name, "gbk", url, numlist,
		// termList);
		// } catch (LotteryException e) {
		// e.printStackTrace();
		// }
		// System.out.println(flg);
		// //������
		// String type="1000004";
		// int num=30;
		// List<LotteryTermModel> termList =null;
		// List<String> numlist=null;
		// String name="plwlist.html";
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\plw";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// try {
		// termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
		// num);
		// //��ý������ڲ��ڼ���
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// flg=termservice.plwlistfileSource(name, "gbk", url, numlist,
		// termList);
		// } catch (LotteryException e) {
		// e.printStackTrace();
		// }
		// System.out.println(flg);
		// ʤ����
		// String type="1300001";
		// int num=30;
		// List<LotteryTermModel> termList =null;
		// List<String> numlist=null;
		// String name="zclist.html";
		// boolean flg=false;
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery\\zc";
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// if(StringUtils.isEmpty(type))
		// type="1000001";
		// try {
		// termList = termservice.queryHaveWinTermInfo(Integer.parseInt(type),
		// num);
		// //��ý������ڲ��ڼ���
		// numlist= termservice.queryHaveWinTermNos(Integer.parseInt(type),-1);
		// flg=termservice.zclistfileSource(name, "gbk", url, numlist,
		// termList);
		// } catch (LotteryException e) {
		// e.printStackTrace();
		// }
		// System.out.println(flg);
		// //���Կ�����¼��ѯ��ҳ
		// String url="C:\\Documents and
		// Settings\\Administrator\\����\\work\\mylottery\\WebRoot\\drawlottery";
		// String name="index.html";
		// Map<Integer,LotteryTermModel> termMap=null;
		// List<LotteryTermModel> termList=null;
		// boolean flg=false;
		// LotteryTermServiceInterf termservice =
		// ApplicationContextUtils.getService("lotteryTermService",
		// LotteryTermServiceInterf.class);
		// try {
		// termList = termservice.queryAllLastTermInfo();
		// termMap = new HashMap<Integer, LotteryTermModel>();
		// for(LotteryTermModel term : termList)
		// termMap.put(term.getLotteryId(), term);
		// flg=termservice.kjlistfileSource(name, "gbk", url, termMap);
		// } catch (LotteryException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println(flg);
	}

	@Override
	public Integer start(String[] args) {
		if(args == null){
			logger.info("CHENHAO ARGS IS NULL!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}else{
			logger.info("CHENHAO ARGS LENGTH=" + args.length + "!!!!!!!!!");
		}

		CreateHtml ch = new CreateHtml();
		if (args == null || args.length == 0) {
			//���ֲʸ��¿�����������Ʒ���
			ch.cretaeTadayHTML();
		} else if ("INIT".equals(args[0])) {
			ch.dltckxxHTML();
			ch.qxcckxxHTML();
			ch.plsckxxHTML();
			ch.plwckxxHTML();
			//���ʤ���ʿ�������
			ch.zcckxxHTML();
			//��ʽ���ʿ�������
			ch.jq4ckxxHTML();
			//��ʰ�ȫ����������
			ch.zc6ckxxHTML();
			//���ֲ����Ʒ���
			ch.createHTMLZS();
			//���ֲʿ�������
			ch.dlcckxxHTML();
			//���ֲ����Ʒ���
			ch.cretaeHTMLZS_DLC();
		} else if("DLC".equals(args[0])){
			//���ֲʸ������Ʒ����Ϳ�������
			ch.cretaeTadayToDlcHTML();
		}
		return null;
	}

	@Override
	public int stop(int i) {
		return i;
	}

	@Override
	public void controlEvent(int event) {
		if (!WrapperManager.isControlledByNativeWrapper()
				&& (event == 200 || event == 201 || event == 203))
			WrapperManager.stop(0);
	}
}
