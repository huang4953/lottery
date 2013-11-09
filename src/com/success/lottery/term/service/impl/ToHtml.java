package com.success.lottery.term.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.success.lottery.exception.LotteryException;
import com.success.lottery.term.domain.LotteryTermModel;
import com.success.lottery.term.service.interf.ToHtmlInterf;
import com.success.lottery.util.LotteryInfo;
import com.success.lottery.web.formbean.RaceBean;
import com.success.utils.FileUtils;

public class ToHtml implements ToHtmlInterf{
	@Override
	public boolean dltfileSource(List<LotteryTermModel> list, String name,
			String enc, String url) throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../dltdirection.jhtml');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
		
		
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append("</ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='zs_blue'><img src='../images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>选择期数</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value='dlt30.html'>30期</option>");
		sb.append("<option  value='dlt50.html'>50期</option>");
		sb.append("<option value='dlt100.html'>100期</option></select></div>");
		sb.append("<div class='zs_qishu_zl'><ul><li class='zs_qishu_zl_on'><a href='dlt30.html'>基本走势</a></li>");
		sb.append("</ul></div></div>");
		sb
				.append("<div class='zs_trend'><div class='zs_trend_title'>大乐透基本走势</div>");
		sb
				.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'><tr>");
		sb
				.append("<td colspan='38' bgcolor='#d71b16' class='zs_table_title'>前区号码</td>");
		sb
				.append("<td colspan='12' bgcolor='#4272d8' class='zs_table_title'>后区号码</td></tr>");
		sb.append("<tr><td width='90' rowspan='2' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='154' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("<td colspan='12' bgcolor='#fcf3f0'>一区</td>");
		sb.append("<td colspan='12' bgcolor='#fcf5e1'>二区</td>");
		sb.append("<td colspan='11' bgcolor='#fff9eb'>三区</td>");
		sb.append("<td colspan='6' bgcolor='#e4eeff'>一区</td>");
		sb.append("<td colspan='6' bgcolor='#f0ffff'>二区</td></tr>");
		sb.append("<tr>");
		Map<String, Integer> redAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> redFailedCountMap = new HashMap<String, Integer>();// 遗漏
		Map<String, Integer> blueAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> blueFailedCountMap = new HashMap<String, Integer>();// 遗漏
		for (int i = 1; i <= 35; i++) {
			redAppearCountMap.put("red_" + i, 0);
			redFailedCountMap.put("red_" + i, 0);
		}
		for (int i = 1; i <= 12; i++) {
			blueAppearCountMap.put("blue_" + i, 0);
			blueFailedCountMap.put("blue_" + i, 0);
		}

		char[] redcodechar, bulecodechar;
		String redcodestr, bulecodestr;
		for (int i = 1; i <= 12; i++) {
			sb
					.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>");
			if (i <= 9)
				sb.append("0" + i).append("</td>");
			else
				sb.append(i).append("</td>");
		}
		for (int i = 13; i <= 24; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf5e1' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 25; i <= 35; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");

		for (int i = 1; i <= 12; i++) {
			sb
					.append("<td width='18' height='18' bgcolor='#e4eeff' class='font_red_n'>");
			if (i <= 9)
				sb.append("0" + i).append("</td>");
			else
				sb.append(i).append("</td>");
		}
		sb.append("</tr>");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (LotteryTermModel model : list) {
			sb.append("<tr><td width='90' bgcolor='#FFFFFF'>");
			sb.append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td width='70' bgcolor='#FFFFFF'>").append(
					model.getTermNo()).append("</td>");
			redcodechar = model.getLotteryResult().toString().split("\\|")[0]
					.toCharArray();
			bulecodechar = model.getLotteryResult().toString().split("\\|")[1]
					.toCharArray();
			redcodestr = "" + redcodechar[0] + redcodechar[1] + ","
					+ redcodechar[2] + redcodechar[3] + "," + redcodechar[4]
					+ redcodechar[5] + "," + redcodechar[6] + redcodechar[7]
					+ "," + redcodechar[8] + redcodechar[9];
			bulecodestr = "" + bulecodechar[0] + bulecodechar[1] + ","
					+ bulecodechar[2] + bulecodechar[3];
			sb.append("<td width='154' bgcolor='#FFFFFF'>").append(
					"<span class='font_red_n'>").append(redcodestr).append(
					"</span>|");
			sb.append("<span class=’font_blue_n’>").append(bulecodestr).append(
					"</span></td>");
			DecimalFormat df = new DecimalFormat("00");
			String code = "";
			String style = "";
			for (int i = 1; i <= 35; i++) {
				code = df.format(i);
				style = i < 13 ? "#fcf3f0" : i < 25 ? "#fcf5e1" : "#fff9eb";
				if (redcodestr.contains(code)) {
					redFailedCountMap.put("red_" + i, 0);
					redAppearCountMap.put("red_" + i, redAppearCountMap
							.get("red_" + i) + 1);
					sb.append("<td width='18' height='18' bgcolor='").append(
							style).append("'").append("class='zs_redball_on'>")
							.append(code).append("</td>");
				} else {
					redFailedCountMap.put("red_" + i, redFailedCountMap
							.get("red_" + i) + 1);
					sb.append("<td width=’18' height='18' bgcolor='").append(
							style).append("'").append("class='font_red_n' >");
					sb.append(redFailedCountMap.get("red_" + i))
							.append("</td>");
				}
			}
			for (int i = 1; i <= 12; i++) {
				code = df.format(i);
				style = i < 6 ? "#e4eeff" : "#f0ffff";
				if (bulecodestr.contains(code)) {
					blueFailedCountMap.put("blue_" + i, 0);
					blueAppearCountMap.put("blue_" + i, blueAppearCountMap
							.get("blue_" + i) + 1);
					sb.append("	<td width='18' height='18' bgcolor='").append(
							style).append("class='zs_blueball_on' >").append(
							code).append("</td>");
				} else {
					blueFailedCountMap.put("blue_" + i, blueFailedCountMap
							.get("blue_" + i) + 1);
					sb.append("<td width='18' height='18' bgcolor='").append(
							style).append("' class='font_blue_n' >");
					sb.append(blueFailedCountMap.get("blue_" + i));
					sb.append("</td>");
				}
			}

		}
		sb.append("</tr>").append("<tr><td colspan='3' bgcolor='#FFFFFF'>")
				.append("出现次数").append("</td>");

		for (int i = 1; i <= 35; i++)
			sb.append("<td height='18' bgcolor='#FFFFFF'>").append(
					redAppearCountMap.get("red_" + i)).append("</td>");
		for (int i = 1; i <= 12; i++) {
			sb.append("<td height='18' bgcolor='#FFFFFF'>").append(
					blueAppearCountMap.get("blue_" + i)).append("</td>");
		}
		sb
				.append(
						"</tr><tr><td height='50' colspan='3' bgcolor='#FFFFFF' ><p>")
				.append("最近遗漏</p><p>&nbsp;</p></td>");

		for (int i = 1; i <= 35; i++) {
			sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
					.append(redFailedCountMap.get("red_" + i)).append("<br/>");
			sb.append("<img src='../images/zs01.gif' width='8' height='").append(
					redFailedCountMap.get("red_" + i)).append("' ></td>");
		}
		for (int i = 1; i <= 12; i++) {
			sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
					.append(blueFailedCountMap.get("blue_" + i))
					.append("<br/>");
			sb.append("<img src='../images/zs01.gif' width='8' height='").append(
					blueFailedCountMap.get("blue_" + i)).append("' /></td>");
		}
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td width='90' rowspan='2' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='90' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='90' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		for (int i = 1; i <= 12; i++) {
			sb
					.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>");
			if (i <= 9)
				sb.append("0" + i).append("</td>");
			else
				sb.append(i).append("</td>");
		}
		for (int i = 13; i <= 24; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf5e1' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 25; i <= 35; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");

		for (int i = 1; i <= 12; i++) {
			sb
					.append("<td width='18' height='18' bgcolor='#e4eeff' class='font_red_n'>");
			if (i <= 9)
				sb.append("0" + i).append("</td>");
			else
				sb.append(i).append("</td>");
		}
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan='12' bgcolor='#fcf3f0'>一区</td>");
		sb.append("<td colspan='12' bgcolor='#fcf5e1'>二区</td>");
		sb.append("<td colspan='11' bgcolor='#fff9eb'>三区</td>");
		sb.append("<td colspan='6' bgcolor='#e4eeff'>一区</td>");
		sb.append("<td colspan='6' bgcolor='#f0ffff'>二区</td>");
		sb.append("</tr>").append("</table>").append("</div>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;'></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean qxcfileSource(List<LotteryTermModel> list, String name,
			String enc, String url) throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='zs_blue'><img src='../images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>选择期数</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value='qxc30.html'>30期</option>");
		sb.append("<option  value='qxc50.html'>50期</option>");
		sb.append("<option value='qxc100.html'>100期</option></select></div>");
		sb.append("<div class='zs_qishu_zl'><ul><li class='zs_qishu_zl_on'><a href='qxc30.html'>基本走势</a></li>");
		sb.append("</ul></div></div>");
		sb
				.append("<div class='zs_trend1'><div class='zs_trend_title_qxc'>七星彩基本走势</div>");
		Map<String, Integer> qxcAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> qxcFailedCountMap = new HashMap<String, Integer>();// 遗漏

		String trstyle = "";
		String condestyel = "";

		for (int i = 0; i < 7; i++) {
			for (int k = 0; k < 10; k++) {
				qxcAppearCountMap.put(i + "_" + k, 0);
				qxcFailedCountMap.put(i + "_" + k, 0);
			}
		}
		sb
				.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table_qxc'>");
		sb.append("<tr>");
		sb.append("<td width='80' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='80' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='110' rowspan='3' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第一位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第二位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第三位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第四位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第五位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第六位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第七位</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		sb.append("</tr>");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (LotteryTermModel model : list) {
			sb.append("<tr>").append("<td width='80' bgcolor='#FFFFFF'>")
					.append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td width='80' bgcolor='#FFFFFF'>").append(
					model.getTermNo()).append("</td>");
			char[] result = model.getLotteryResult().toCharArray();
			sb.append("<td width='110' bgcolor='#FFFFFF'>").append(
					"<span class='font_red_n'>");
			sb.append(result[0]).append(result[1]).append(result[2]).append(
					result[3]).append(result[4]).append(result[5]).append(
					result[6]).append("</span></td>");
			for (int i = 0; i < 7; i++) {
				trstyle = i % 2 == 0 ? "#fcf3f0" : "#fff9eb";
				condestyel = i % 2 == 0 ? "zs_redball_on" : "zs_blueball_on";
				for (int k = 0; k < 10; k++) {
					if (String.valueOf(result[i]).equals(String.valueOf(k))) {
						qxcFailedCountMap.put(i + "_" + k, 0);
						qxcAppearCountMap.put(i + "_" + k, qxcAppearCountMap
								.get(i + "_" + k) + 1);
						sb.append("<td width='16' height='16' bgcolor='")
								.append(trstyle).append("'").append("class='")
								.append(condestyel).append("'>");
						sb.append(k).append("</td>");
					} else {
						qxcFailedCountMap.put(i + "_" + k, qxcFailedCountMap
								.get(i + "_" + k) + 1);
						sb.append("<td width='16' height='16' bgcolor='")
								.append(trstyle).append("'").append(
										"class='font_gray_n'>");
						sb.append(qxcFailedCountMap.get(i + "_" + k)).append(
								"</td>");
					}
				}

			}
			sb.append("</tr>");
		}
		sb.append("<tr>");
		sb.append("<td colspan='3' bgcolor='#FFFFFF'>");
		sb.append("出现次数");
		sb.append("</td>");
		for (int i = 0; i < 7; i++)
			for (int k = 0; k < 10; k++)
				sb.append("<td width='16' height='16' bgcolor='#FFFFFF'>")
						.append(qxcAppearCountMap.get(i + "_" + k)).append(
								"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='50' colspan='3' bgcolor='#FFFFFF'>");
		sb.append("<p>最近遗漏</p>");
		sb.append("<p>&nbsp;</p>");
		sb.append("</td>");
		for (int i = 0; i < 7; i++)
			for (int k = 0; k < 10; k++) {
				sb.append(
						"<td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
						.append(qxcFailedCountMap.get(i + "_" + k)).append(
								"<br />");
				sb.append("<img src='../images/zs01.gif' width='8' height='")
						.append(qxcFailedCountMap.get(i + "_" + k)).append("'")
						.append("</td>");
			}
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append(" <td width='80' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='80' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='110' rowspan='3' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='16' height='16' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		sb.append("</tr>");
		sb.append(" <tr>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第一位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第二位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第三位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第四位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第五位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第六位</td>");
		sb.append(" <td colspan='10' bgcolor='#fcf3f0'>第七位</td>");
		sb.append("</tr>").append("</table>").append("</div>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;'></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plsfileSource(List<LotteryTermModel> list, String name,
			String enc, String url) throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='zs_blue'><img src='../images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>选择期数</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value='pls30.html'>30期</option>");
		sb.append("<option  value='pls50.html'>50期</option>");
		sb.append("<option value='pls100.html'>100期</option></select></div>");
		sb.append("<div class='zs_qishu_zl'><ul><li class='zs_qishu_zl_on'><a href='pls30.html'>基本走势</a></li>");
		sb.append("</ul></div></div>");
		sb
				.append("<div class='zs_trend'><div class='zs_trend_title'>排列三基本走势</div>");
		sb
				.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		sb.append("<tr>");
		sb.append("<td width='75' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='59' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='67' rowspan='3' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>百位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>十位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>个位</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n'>和值数</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n'>奇偶比</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n'>大小比</td>");
		sb.append("<td colspan='10' bgcolor='#e4eeff'>跨度走势</td>");
		sb.append("<td colspan='10' bgcolor='#f0ffff'>合数值走势</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#e4eeff' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#f0ffff' class='font_red_n'>")
					.append(i).append("</td>");
		sb.append("</tr>");
		Map<String, Integer> plsAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> plsFailedCountMap = new HashMap<String, Integer>();// 遗漏
		Map<String, Integer> plsKuaDCountMap = new HashMap<String, Integer>();// 跨度
		Map<String, Integer> plsKuaDAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> plsHeShuCountMap = new HashMap<String, Integer>();// 合数
		Map<String, Integer> plsHeShuAppearCountMap = new HashMap<String, Integer>();// 次数
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 10; k++) {
				plsAppearCountMap.put(i + "_" + k, 0);
				plsFailedCountMap.put(i + "_" + k, 0);
			}
		}
		for (int i = 0; i < 10; i++) {
			plsKuaDCountMap.put("" + i, 0);
			plsKuaDAppearCountMap.put("" + i, 0);
			plsHeShuCountMap.put("" + i, 0);
			plsHeShuAppearCountMap.put("" + i, 0);
		}
		String bai = "";
		String shi = "";
		String ge = "";
		String trstyle;
		int zhihe = 0, ji = 0, ou = 0, da = 0, xiao = 0, kuad = 0, zuida = 0, zuix = 9;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (LotteryTermModel model : list) {
			sb.append("<tr>");
			sb.append("<td width='75' bgcolor='#FFFFFF'>");
			sb.append(sf.format(model.getWinLine()));
			sb.append("</td>");
			char[] result = model.getLotteryResult().toCharArray();
			zhihe = 0;
			ji = 0;
			ou = 0;
			da = 0;
			xiao = 0;
			kuad = 0;
			zuida = 0;
			zuix = 9;
			for (int i = 0; i < 3; i++) {
				zhihe = zhihe + Integer.parseInt(String.valueOf(result[i]));
				if (Integer.parseInt(String.valueOf(result[i])) % 2 == 0)
					ou++;
				else
					ji++;
				if (Integer.parseInt(String.valueOf(result[i])) > 4)
					da++;
				else
					xiao++;
				zuida = Integer.parseInt(String.valueOf(result[i])) > zuida ? Integer
						.parseInt(String.valueOf(result[i]))
						: zuida;
				zuix = Integer.parseInt(String.valueOf(result[i])) < zuix ? Integer
						.parseInt(String.valueOf(result[i]))
						: zuix;
			}
			kuad = zuida - zuix;
			sb.append("<td width='59' bgcolor='#FFFFFF'>");
			sb.append(model.getTermNo());
			sb.append("</td>");
			sb.append("<td width='67' bgcolor='#FFFFFF'>");
			sb.append("<span class='font_red_n'>");
			sb.append(result[0]);
			sb.append(",");
			sb.append(result[1]);
			sb.append(",");
			sb.append(result[2]);
			sb.append("</span>");
			sb.append("</td>");
			for (int i = 0; i < 3; i++) {
				trstyle = i % 2 == 0 ? "#fcf3f0" : "#fff9eb";
				for (int k = 0; k < 10; k++) {
					if (String.valueOf(result[i]).equals(String.valueOf(k))) {
						plsFailedCountMap.put(i + "_" + k, 0);
						plsAppearCountMap.put(i + "_" + k, plsAppearCountMap
								.get(i + "_" + k) + 1);
						sb.append("	<td width='18' height='18' bgcolor='")
								.append(trstyle).append(
										"' class='zs_redball_on'>");
						sb.append(k);
						sb.append("</td>");
					} else {
						plsFailedCountMap.put(i + "_" + k, plsFailedCountMap
								.get(i + "_" + k) + 1);
						sb.append("<td width='18' height='18' bgcolor='")
								.append(trstyle).append(
										"' class='font_gray_n'>");
						sb.append(plsFailedCountMap.get(i + "_" + k));
						sb.append("</td>");
					}

				}
			}
			sb.append("<td width='18' height='18' bgcolor='#f0ffff'>");
			sb.append(zhihe);
			sb.append("</td>");
			sb.append("<td width='18' height='18' bgcolor='#e4eeff'>");
			sb.append(ji).append(":").append(ou);
			sb.append("</td>");
			sb.append("<td width='18' height='18' bgcolor='#fff9eb'>");
			sb.append(da).append(":").append(xiao);
			sb.append("</td>");
			for (int i = 0; i < 10; i++) {
				if (kuad == i) {
					plsKuaDCountMap.put("" + i, 0);
					plsKuaDAppearCountMap.put("" + i, plsKuaDAppearCountMap
							.get("" + i) + 1);
					sb
							.append("<td width='18' height='18' bgcolor='#e4eeff' class='zs_blueball_on'>");
					sb.append(i);
					sb.append("</td>");
				} else {
					plsKuaDCountMap
							.put("" + i, plsKuaDCountMap.get("" + i) + 1);
					sb
							.append("<td width='18' height='18' bgcolor='#e4eeff' class='font_gray_n'>");
					sb.append(plsKuaDCountMap.get("" + i));
					sb.append("</td>");
					sb.append("");
				}
			}
			if(zhihe>9)
			{
				String zhiheStr=zhihe+"";
				zhihe=Integer.parseInt(zhiheStr.substring(1, 2));
			}
			for (int i = 0; i < 10; i++) {
				if (zhihe == i) {
					plsHeShuCountMap.put("" + i, 0);
					plsHeShuAppearCountMap.put("" + i, plsHeShuAppearCountMap
							.get("" + i) + 1);
					sb
							.append("<td width='18' height='18' bgcolor='#f0ffff' class='zs_blueball_on'>");
					sb.append(i);
					sb.append("</td>");
				} else {
					plsHeShuCountMap.put("" + i,
							plsHeShuCountMap.get("" + i) + 1);
					sb
							.append("<td width='18' height='18' bgcolor='#f0ffff' class='font_gray_n'>");
					sb.append(plsHeShuCountMap.get("" + i));
					sb.append("</td>");
				}
			}
			sb.append("</tr>");
		}
		sb.append("<tr>").append("<td colspan='3' bgcolor='#FFFFFF'>").append(
				"出现次数").append("</td>");
		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 10; k++)
				sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>")
						.append(plsAppearCountMap.get(i + "_" + k)).append(
								"</td>");
		sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		for (int i = 0; i < 10; i++)
			sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>").append(
					plsKuaDAppearCountMap.get("" + i)).append("</td>");
		for (int i = 0; i < 10; i++)
			sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>").append(
					plsHeShuAppearCountMap.get("" + i)).append("</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td height='50' colspan='3' bgcolor='#FFFFFF'>");
		sb.append("<p>最近遗漏</p><p>&nbsp;</p></td>");
		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 10; k++) {
				sb
						.append(
								" <td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
						.append(plsFailedCountMap.get(i + "_" + k)).append(
								"<br />");
				sb.append("<img src='../images/zs01.gif' width='8' height='")
						.append(plsFailedCountMap.get(i + "_" + k)).append(
								"' />").append("</td>");
			}
		sb.append("<td width='18' height='50' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		sb.append("<td width='18' height='50' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		sb.append("<td width='18' height='50' bgcolor='#FFFFFF'>&nbsp;")
				.append("</td>");
		for (int i = 0; i < 10; i++) {
			sb.append(" <td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
					.append(plsKuaDCountMap.get("" + i));
			sb.append("<br /><img src='../images/zs01.gif' width='8' height='")
					.append(plsKuaDCountMap.get("" + i)).append("' />").append(
							"</td>");
		}
		for (int i = 0; i < 10; i++) {
			sb.append(" <td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
					.append(plsHeShuCountMap.get("" + i));
			sb.append("<br /><img src='../images/zs01.gif' width='8' height='")
					.append(plsHeShuCountMap.get("" + i)).append("' />")
					.append("</td>");
		}
		sb.append("</tr><tr>");
		sb.append("<td width='75' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='59' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb
				.append("<td width='67' rowspan='3' bgcolor='#FFFFFF'>开奖号码</td></tr><tr>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fff9eb' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>")
					.append(i).append("</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n' >和值数</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n' >奇偶比</td>");
		sb
				.append("<td rowspan='2' bgcolor='#FFFFFF' class='font_blue_n' >大小比</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#e4eeff' class='font_red_n'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb
					.append(
							"<td width='18' height='18' bgcolor='#f0ffff' class='font_red_n'>")
					.append(i).append("</td>");
		sb.append("</tr><tr>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>百位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>十位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>个位</td>");
		sb.append("<td colspan='10' bgcolor='#e4eeff'>跨度走势</td>");
		sb.append("<td colspan='10' bgcolor='#f0ffff'>合数值走势</td>");
		sb.append("</tr></table></div>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;'></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plwfileSource(List<LotteryTermModel> list, String name,
			String enc, String url) throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html')\">彩票新闻</li>");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append("<li   class=\"zs_bar_caizhong_on\"><a href=\"plw30.html\">排列五</a></li>");
		sb.append(" </ul></div></div>");
		sb.append("<div class='zs_blue'><img src='../images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>选择期数</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value='plw30.html'>30期</option>");
		sb.append("<option  value='plw50.html'>50期</option>");
		sb.append("<option value='plw100.html'>100期</option></select></div>");
		sb.append("<div class='zs_qishu_zl'><ul><li class='zs_qishu_zl_on'><a href='plw30.html'>基本走势</a></li>");
		sb.append("</ul></div></div>");
		sb
				.append("<div class='zs_trend'><div class='zs_trend_title'>排列五基本走势</div>");
		Map<String, Integer> plwAppearCountMap = new HashMap<String, Integer>();// 次数
		Map<String, Integer> plwFailedCountMap = new HashMap<String, Integer>();// 遗漏

		String trstyle = "";
		String condestyel = "";

		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 10; k++) {
				plwAppearCountMap.put(i + "_" + k, 0);
				plwFailedCountMap.put(i + "_" + k, 0);
			}
		}
		sb
				.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		sb.append("<tr>");
		sb.append("<td width='75' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='59' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb
				.append("<td width='67' rowspan='3' bgcolor='#FFFFFF'>开奖号码<span class='font_blue_n'></span></td>");
		sb.append("</tr><tr>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第一位</td>");
		sb.append(" <td colspan='10' bgcolor='#fff9eb'>第二位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第三位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第四位</td>");
		sb.append(" <td colspan='10' bgcolor='#fcf3f0'>第五位</td>");
		sb.append(" </tr>");
		sb.append("<tr class='font_red_n'>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fff9eb'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fff9eb'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		sb.append("</tr>");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for (LotteryTermModel model : list) {
			sb.append("<tr><td width='75' bgcolor='#FFFFFF'>").append(
					sf.format(model.getWinLine())).append("</td>");
			sb.append("<td width='59' bgcolor='#FFFFFF'>").append(
					model.getTermNo()).append("</td>");
			char[] result = model.getLotteryResult().toCharArray();
			sb.append("<td width='67' bgcolor='#FFFFFF'>").append(
					"<span class='font_red_n'>");
			sb.append(result[0]).append(result[1]).append(result[2]).append(
					result[3]).append(result[4]);
			sb.append("</span></td>");
			for (int i = 0; i < 5; i++) {
				trstyle = i % 2 == 0 ? "#fcf3f0" : "#fff9eb";
				condestyel = i % 2 == 0 ? "zs_redball_on" : "zs_blueball_on";
				for (int k = 0; k < 10; k++) {
					if (String.valueOf(result[i]).equals(String.valueOf(k))) {
						plwFailedCountMap.put(i + "_" + k, 0);
						plwAppearCountMap.put(i + "_" + k, plwAppearCountMap
								.get(i + "_" + k) + 1);
						sb.append("<td width='18' height='18' bgcolor='")
								.append(trstyle).append("' class='");
						sb.append(condestyel).append("'>").append(k).append(
								"</td>");
					} else {
						plwFailedCountMap.put(i + "_" + k, plwFailedCountMap
								.get(i + "_" + k) + 1);
						sb.append("<td width='18' height='18' bgcolor='")
								.append(trstyle)
								.append("' class='font_gray_n'>");
						sb.append(plwFailedCountMap.get(i + "_" + k)).append(
								"</td>");
					}

				}
			}
			sb.append("</tr>");
		}
		sb.append("<tr><td colspan='3' bgcolor='#FFFFFF'>出现次数</td>");
		for (int i = 0; i < 5; i++)
			for (int k = 0; k < 10; k++)
				sb.append("<td width='18' height='18' bgcolor='#FFFFFF'>")
						.append(plwAppearCountMap.get(i + "_" + k)).append(
								"</td>");
		sb.append("</tr><tr>");
		sb.append("<td height='50' colspan='3' bgcolor='#FFFFFF'>");
		sb.append("<p>最近遗漏</p><p>&nbsp;</p></td>");
		for (int i = 0; i < 5; i++)
			for (int k = 0; k < 10; k++) {
				sb
						.append(
								" <td height='50px' bgcolor='#FFFFFF' valign='bottom'>")
						.append(plwFailedCountMap.get(i + "_" + k));
				sb.append(
						"<br /> <img src='../images/zs01.gif' width='8' height='")
						.append(plwFailedCountMap.get(i + "_" + k))
						.append("'>");
				sb.append("</td>");
			}
		sb.append("</tr><tr>");
		sb.append("<td width='75' rowspan='3' bgcolor='#FFFFFF'>日期</td>");
		sb.append("<td width='59' rowspan='3' bgcolor='#FFFFFF'>期号</td>");
		sb
				.append("<td width='67' rowspan='3' bgcolor='#FFFFFF'>开奖号码<span class='font_blue_n'></span></td></tr>");
		sb.append("<tr class='font_red_n'>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fff9eb'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fff9eb'>")
					.append(i).append("</td>");
		for (int i = 0; i <= 9; i++)
			sb.append("<td width='18' height='18' bgcolor='#fcf3f0'>")
					.append(i).append("</td>");
		sb.append("</tr><tr> ");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第一位</td>");
		sb.append(" <td colspan='10' bgcolor='#fff9eb'>第二位</td>");
		sb.append(" <td colspan='10' bgcolor='#fcf3f0'>第三位</td>");
		sb.append("<td colspan='10' bgcolor='#fff9eb'>第四位</td>");
		sb.append("<td colspan='10' bgcolor='#fcf3f0'>第五位</td>");
		sb.append("</tr></table></div>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;'></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dltkjcxfileSource( String name,
			String enc, String url,
			LotteryTermModel termModel, LotteryTermModel othertermModel)
			throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		
		
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\">排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\">胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		sb.append("<div class='kj_tishi'>").append(
				"<div class='kj_list_title'>");
		sb.append("开奖查询->大乐透").append("</div>").append(
				"<div class='kj_list_chaxun'>");
		sb.append("<form >").append(
				"<div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style='border: 0;'  frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000001'>");
		sb.append("</div></form></div></div>").append(
				"<div class='kj_more_top'>");
		sb.append("<div class='kj_top_logo'>").append(
				"<img src='../../images/kj_logo_dlt.gif' width='288' height='70' />");
		sb.append("</div>").append("<div class='kj_top_title'>").append("第");
		sb.append("<span class='font_red_n'>").append(termModel.getTermNo())
				.append("</span> 期开奖结果:");
		sb.append("</div>").append("<div class='kj_ball'>").append("<ul>");
		for (int i = 0; i <= 8;) {
			sb.append("<li class='kj_redball_m'>").append(
					termModel.getLotteryResult().substring(i, i + 2)).append(
					"</li>");
			i += 2;
		}
		for (int i = 11; i <= 13;) {
			sb.append("<li class='kj_blueball_m'>").append(
					termModel.getLotteryResult().substring(i, i + 2)).append(
					"</li>");
			i += 2;
		}
		Map<String, TreeMap<String, String[]>> superLotteryResult = termModel
				.getLotteryInfo().getSuperWinResult();
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		sb.append("</ul></div></div>");
		sb
				.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr><td colspan='5' class='kj_more_xl'>");
		sb.append("第<span class='font_red_n'>").append(termModel.getTermNo()).append("</span> 期全国销量");
		sb.append("<span class='font_red_n'>").append(
				termModel.getSalesVolume())
				.append("</span> 元</td>");
		sb
				.append("</tr><tr class='kj_caizhong_title'><td colspan='2'>奖级</td><td>中奖注数(注)</td><td>每注奖金(元)</td><td>应派奖金(元)</td></tr>");
		for (int i = 1; i <= 9; i++) {
			if(i!=8)
			sb
					.append("<tr class='kj_table_base'><td rowspan='2' class='kj_table_caizhong2'>");
			else
				sb.append("<tr class='kj_table_base'><td colspan='2' class='kj_table_caizhong2'>");
			switch (i) {
			case 1:
				sb.append("一等奖</td>");
				break;
			case 2:
				sb.append("二等奖</td>");
				break;
			case 3:
				sb.append("三等奖</td>");
				break;
			case 4:
				sb.append("四等奖</td>");
				break;
			case 5:
				sb.append("五等奖</td>");
				break;
			case 6:
				sb.append("六等奖</td>");
				break;
			case 7:
				sb.append("七等奖</td>");
				break;
			case 8:
				sb.append("8等奖</td>");
				break;
			default:
				sb.append("12选2</td>");
				break;
			}
			
			
			if (i >= 1 && i <= 7) {
				sb.append("<td class='kj_table_caizhong2'>基本</td>");
				sb.append("<td>").append(superLotteryResult.get(i+"").get("A")[0])
						.append("</td>");
				sb.append("<td>").append(superLotteryResult.get(i+"").get("A")[1])
				.append("</td>");
				sb.append("<td>").append(fmt.format(new BigDecimal(superLotteryResult.get(i+"").get("A")[0]).multiply(new BigDecimal(superLotteryResult.get(i+"").get("A")[1]))));
				sb.append("</td></tr>");
				if (superLotteryResult.get(i+"").get("B") != null) {
					sb
					.append("<tr class='kj_table_base'><td class='kj_table_caizhong2'>追加</td><td>");
					sb.append(superLotteryResult.get(""+i).get("B")[0]).append(
							"</td>");//
					sb.append("<td>").append(superLotteryResult.get(""+i).get("B")[1]).append(
							"</td><td>");//
					sb.append(fmt.format(new BigDecimal(superLotteryResult.get(i+"").get("B")[0]).multiply(new BigDecimal(superLotteryResult.get(i+"").get("B")[1]))));
					sb.append("</td></tr>");
				} else {
					sb.append("<tr><td></td><td></td><td></td></tr>");
				}
			}
			if(i==8)
			{
				sb.append("<td>").append(superLotteryResult.get("8").get("A")[0]).append("</td>");
				sb.append("<td>").append(superLotteryResult.get("8").get("A")[1]).append("</td>");
				sb.append("<td>").append(fmt.format(new BigDecimal(superLotteryResult.get(i+"").get("A")[0]).multiply(new BigDecimal(superLotteryResult.get(i+"").get("A")[1]))));
				sb.append("</td></tr>");
			}
			if(i==9)
			{
				sb.append("<td  class='kj_table_caizhong2'></td><td>").append(othertermModel.getLotteryInfo().getHappyZodiacWinResult()[0]).append("</td>");
				sb.append("<td>").append(othertermModel.getLotteryInfo().getHappyZodiacWinResult()[1]).append("</td>");
				sb.append("<td>").append(fmt.format(new BigDecimal(othertermModel.getLotteryInfo().getHappyZodiacWinResult()[0]).multiply(new BigDecimal(othertermModel.getLotteryInfo().getHappyZodiacWinResult()[1]))));
				sb.append("</td></tr>");
			}
		}
		sb.append("</table>");
		sb.append("<div class='footer'>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}
	

	@Override
	public boolean qxckjcxfileSource(String name, String enc, String url,
			 LotteryTermModel termModel,
			LotteryTermModel othertermModel) throws LotteryException {
		boolean flg = false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\">排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\">胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->七星彩</div>");
		sb.append("<form action='qxcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\"  height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000002'></form></div>");
		Map<String, String[]> superLotteryResult = termModel.getLotteryInfo().getSevenColorWinResult();
		NumberFormat fmt =NumberFormat.getCurrencyInstance();
		sb.append("<div class='kj_more_top'><div class='kj_top_logo'>");
		sb.append("<img src='../../images/kj_logo_qxc.gif' width='288' height='70' /></div>");
		sb.append("<div class='kj_top_title'>第<span class='font_red_n'>").append(termModel.getTermNo());
		sb.append("</span> 期开奖结果:</div><div class='kj_ball_pl'><ul>");
		for (int i = 0; i <= 6;i++) {
			sb.append("<li class='kj_redball_m'>").append(
					termModel.getLotteryResult().substring(i, i + 1)).append(
					"</li>");
		}
		sb.append("</ul></div></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><div class='kj_date'>");
		sb.append("开奖日期：").append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期全国销量");
		sb.append("<span class='font_red_n'>").append(fmt.format(Integer.parseInt(termModel.getSalesVolume())));
		sb.append("</span> 元</div></td></tr><tr class='kj_caizhong_title'><td width='182'>");
		sb.append("奖级</td><td width='341'>中奖注数(注)</td><td width='427'>每注奖金(元)</td></tr>");
		for(int i=1;i<=6;i++)
		{
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong3'>");
			switch (i) {
			case 1:
				sb.append("一等奖</td>");
				break;
			case 2:
				sb.append("二等奖</td>");
				break;
			case 3:
				sb.append("三等奖</td>");
				break;
			case 4:
				sb.append("四等奖</td>");
				break;
			case 5:
				sb.append("五等奖</td>");
				break;
			case 6:
				sb.append("六等奖</td>");
				break;
			}
			if(superLotteryResult.get(i+"")!=null)
			{
				sb.append("<td>").append(superLotteryResult.get(i+"")[0]).append("</td>");
				sb.append("<td>").append(fmt.format(Integer.parseInt(superLotteryResult.get(i+"")[1]))).append("</td></tr>");
			}else
				sb.append("<td></td><td></td></tr>");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plskjcxfileSource(String name, String enc, String url,
			LotteryTermModel termModel,
			LotteryTermModel othertermModel) throws LotteryException {
		boolean flg = false;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\">排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\">胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->排列三");
		sb.append("</div><form action='plsdrawdetail.jhtml' id='subform'>");
		sb.append("<div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\"  height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000003'></form></div>");
		Map<String, String[]> superLotteryResult = termModel
		.getLotteryInfo().getArrangeThreeWinResult();
		NumberFormat fmt =NumberFormat.getCurrencyInstance();
		char[] resultchar = termModel.getLotteryResult().toCharArray();
		sb.append("<div class='kj_more_top'>");
		sb.append("<div class='kj_top_logo'>");
		sb.append("<img src='../../images/kj_logo_pl3.gif' width='288' height='70' />");
		sb.append("</div><div class='kj_top_title'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期开奖结果:</div>");
		sb.append("<div class='kj_ball_pl'><ul>");
		for (int i = 0; i <= 2;i++) {
			sb.append("<li class='kj_redball_m'>").append(
					termModel.getLotteryResult().substring(i, i + 1)).append(
					"</li>");
		}
		sb.append("</ul><div class='kj_lx'>号码类型:<span class='font_red_n'>");
		
		if (resultchar[0] == resultchar[1]|| resultchar[0] == resultchar[2]|| resultchar[1] == resultchar[2]) 
			sb.append("组三");
		else
			sb.append("组六");
		sb.append("</span></div></div></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><div class='kj_date'>");
		sb.append("开奖日期：").append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>").append(termModel.getTermNo());
		sb.append("</span> 期全国销量<span class='font_red_n'>").append(fmt.format(Integer.parseInt(termModel.getSalesVolume())));
		sb.append("</span> 元</div></td></tr>");
		sb.append("<tr class='kj_caizhong_title'><td width='182'>奖级</td><td width='341'>中奖注数(注)</td>");
		sb.append("<td width='427'>每注奖金(元)</td></tr><tr class='kj_table_base'>");
		sb.append("<td width='182' class='kj_table_caizhong3'>排列三直选</td>");
		sb.append("<td>").append(superLotteryResult.get("1")[0]).append("</td>");
		sb.append("<td>").append(superLotteryResult.get("1")[1]).append("</td>");
		if (resultchar[0] == resultchar[1]|| resultchar[0] == resultchar[2]|| resultchar[1] == resultchar[2]) {
			if(superLotteryResult.get("2")!=null)
			{
				sb.append("</tr><tr class='kj_table_base'>");
				sb.append("<td width='182' class='kj_table_caizhong3'>排列三组三</td>");
				sb.append("<td>").append(superLotteryResult.get("2")[0]).append("</td>");
				sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult.get("2")[1])));
				sb.append("</td></tr>");
			}
		} else {
			if(superLotteryResult.get("3")!=null)
			{
				sb.append("</tr><tr class='kj_table_base'>");
				sb.append("<td width='182' class='kj_table_caizhong3'>排列三组六</td>");
				sb.append("<td>").append(superLotteryResult.get("3")[0]).append("</td>");
				sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult.get("3")[1])));
				sb.append("</td></tr>");
			}
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plwkjcxfileSource(String name, String enc, String url,
			LotteryTermModel termModel,
			LotteryTermModel othertermModel) throws LotteryException {
		boolean flg = false;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\">胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->排列五</div>");
		sb.append("<form action='plwdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\"  height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000004'></form></div>");
		String [] superLotteryResult = termModel.getLotteryInfo().getArrangeFiveWinResult();NumberFormat fmt =NumberFormat.getCurrencyInstance();
		sb.append("<div class='kj_more_top'><div class='kj_top_logo'><img src='../../images/kj_logo_plw.gif' width='288' height='70' />");
		sb.append("</div><div class='kj_top_title'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期开奖结果:</div>");
		sb.append("<div class='kj_ball_pl'><ul>");
		for (int i = 0; i <= 4;i++) {
			sb.append("<li class='kj_redball_m'>").append(
					termModel.getLotteryResult().substring(i, i + 1)).append(
					"</li>");
		}
		sb.append("</ul></div></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><div class='kj_date'>");
		sb.append("开奖日期：").append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期全国销量");
		sb.append("<span class='font_red_n'>").append(fmt.format(Integer.parseInt(termModel.getSalesVolume())));
		sb.append("</span> 元</div></td></tr>");
		sb.append("<tr class='kj_caizhong_title'><td width='182'>奖级</td><td width='341'>");
		sb.append("中奖注数(注)</td><td width='427'>每注奖金(元)</td></tr>");
		sb.append("<tr class='kj_table_base'><td width='182' class='kj_table_caizhong3'>排列五直选</td>");
		sb.append("<td>").append(superLotteryResult[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult[1]))).append("</tr>");
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean zckjcxfileSource(String name, String enc, String url,
			 LotteryTermModel termModel,
			LotteryTermModel othertermModel,List<RaceBean> raceList) throws LotteryException {
		boolean flg = false;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat fmt=NumberFormat.getCurrencyInstance();
		NumberFormat myfmt=NumberFormat.getInstance();
		Map<String,String[]> superLotteryResult = termModel.getLotteryInfo().getWinOrFailWinResult(); 
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->足球彩票</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style='border: 0;' frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300001'></form></div>");
		sb.append("<div class='kj_more_top'><div class='kj_top_logo'><img src='../../images/kj_logo_sfc.gif' width='288' height='70' />");
		sb.append("</div><div class='kj_top_title'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期开奖结果:");
		sb.append("</div><div class='kj_zc'>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
		{
			sb.append(termModel.getLotteryResult().substring(i,i+1));
			if(i!=termModel.getLotteryResult().length()-1)
			sb.append(",");
		}
		sb.append("</div></div><table border='0' cellspacing='1' class='kj_caizhong_table'><tr>");
		sb.append("<td colspan='3' class='kj_table_base'><div class='kj_date'>开奖日期：");
		sb.append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>").append(termModel.getTermNo());
		sb.append("</span> 期胜负彩销量");
		sb.append("<span class='font_red_n'>").append(myfmt.format(Integer.parseInt(termModel.getSalesVolume())));
		sb.append("</span> 元 | 任九场销量<span class='font_red_n'>").append(myfmt.format(Integer.parseInt(othertermModel.getSalesVolume())));
		sb.append("</span> 元 | 胜负彩奖池滚存<span class='font_red_n'>");
		sb.append(myfmt.format(Integer.parseInt(othertermModel.getJackpot()))).append("</span> 元</div></td></tr>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><table width='100%' border='0' cellpadding='0' cellspacing='1'>");
		sb.append("<tr class='kj_caizhong_title'>");
		for (RaceBean raceBean : raceList) {
			sb.append("<th>").append(raceBean.getHomeTeam()).append("</th>");
		}
		sb.append("</tr><tr class='zc_dz'>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
			sb.append("<td>").append(termModel.getLotteryResult().substring(i, i+1)).append("</td>");
		sb.append("</tr></table></td></tr><tr class='kj_caizhong_title'><td width='240'>奖级</td>");
		sb.append("<td width='405'>中奖注数(注)</td>");
		sb.append("<td width='305'>每注奖金(元)</td></tr><tr class='kj_table_base'>");
		sb.append("<td class='kj_table_caizhong3'>一等奖</td><td>").append(superLotteryResult.get("1")[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult.get("1")[1]))).append("</td></tr>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong3'>二等奖</td>");
		sb.append("<td>").append(superLotteryResult.get("2")[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult.get("2")[1]))).append("</td></tr>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong3'>任选九场一等奖</td>");
		sb.append("<td>").append((othertermModel.getLotteryInfo().getArbitry9WinResult())[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong((othertermModel.getLotteryInfo().getArbitry9WinResult())[1])));
		sb.append("</td></tr></table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dltlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->大乐透</div>");
		sb.append("<form ><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../dlthtml/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000001'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr class='kj_caizhong_title'><td>期号</td><td>开奖号码</td>");
		sb.append("<td>开奖时间</td><td>开奖详情</td></tr>");
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'>");
			sb.append("<a href='../dlthtml/").append(model.getTermNo()).append(".html'>");
			sb.append(model.getTermNo()).append("期</a></td>");
			sb.append("<td><div class='kj_ballbox'><ul>");
			for (int i = 0; i < 10; ) {
				sb.append("<li class='kj_redball'>").append(model.getLotteryResult().substring(i, i+2));
				sb.append("</li>");
				i+=2;
			}
			for (int i = 11; i < 15; ) {
				sb.append("<li class='kj_blueball'>").append(model.getLotteryResult().substring(i, i+2));
				sb.append("</li>");
				i+=2;
			}
			sb.append("</ul></div></td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../dlthtml/").append(model.getTermNo());
			sb.append(".html'><img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean qxclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->七星彩</div>");
		sb.append("<form action='qxcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../qxchtml/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000002'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../qxchtml/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td><div class='kj_ballbox'><ul>");
			for (int i = 0; i < 7;i++ ) {
				sb.append("<li class='kj_redball'>").append(model.getLotteryResult().substring(i, i+1));
				sb.append("</li>");
			}
			sb.append("</ul></div></td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../qxchtml/").append(model.getTermNo());
			sb.append(".html'><img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
			sb.append("");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plslistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html><head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->排列三</div>");
		sb.append("<form action='plsdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../plshtml/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000003'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../plshtml/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td><div class='kj_ballbox'><ul>");
			for (int i = 0; i < 3;i++ ) {
				sb.append("<li class='kj_redball'>").append(model.getLotteryResult().substring(i, i+1));
				sb.append("</li>");
			}
			sb.append("</ul></div></td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../plshtml/").append(model.getTermNo());
			sb.append(".html'><img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plwlistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->排列五</div>");
		sb.append("<form action='plwdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../plwhtml/select.html' scrolling='no'  style=\"border: 0\" frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000004'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../plwhtml/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td><div class='kj_ballbox'><ul>");
			for (int i = 0; i < 5;i++ ) {
				sb.append("<li class='kj_redball'>").append(model.getLotteryResult().substring(i, i+1));
				sb.append("</li>");
			}
			sb.append("</ul></div></td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../plwhtml/").append(model.getTermNo());
			sb.append(".html'><img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div><div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean zclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list)
			throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li ><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->胜负彩</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../zchtml/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300001'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../zchtml/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td>");
			for (int i = 0; i < 14; i++) {
				String str=model.getLotteryResult().substring(i, i+1);
				if(str.equals("9"))
					str="*";
				sb.append(str);
				if(i!=13)
					sb.append(",");
			}
			sb.append("</td><td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../zchtml/").append(model.getTermNo()).append(".html'>");
			sb.append("<img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}
	@Override
	public boolean kjlistfileSource(String name, String enc, String url,Map<Integer,LotteryTermModel> termMap)
			throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../js/utils/jquery.alerts.js\"></script>");
		sb.append("<script src='../js/head.js'></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		//-------------------------头结束-------------------------------------------------
		sb.append("<div><img src='../images/blank.gif' width='1' height='3'></div><div class='zs_bar'>");
		sb.append("<div class='zs_bar_logo'><img src='../images/menu_kj_bar_logo.gif' width='40' height='29' /></div>");
		sb.append("<div class='zs_bar_title'>开奖查询</div><div class='zs_bar_caizhong'>");
		sb.append("<ul><li><a href='dlt/dltlist.html'>大乐透</a></li>");
		sb.append("<li><a href='qxc/qxclist.html'>七星彩</a></li>");
		sb.append("<li><a href='pls/plslist.html'>排列三</a></li>");
		sb.append("<li><a href='plw/plwlist.html'>排列五</a></li>");
		sb.append("<li><a href='zc/zclist.html'>胜负彩</a></li>");
		sb.append("<li><a href='zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		sb.append("<div><img src='../images/blank.gif' width='1' height='3'></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr class='kj_caizhong_title'><td>彩种</td><td>期号</td><td>开奖时间</td>");
		sb.append("<td>开奖号码</td><td>奖池滚存</td><td>开奖日</td><td>开奖详情</td></tr>");
		sb.append("<tr class='kj_caizhong_blank'><td colspan='7'>&nbsp;</td></tr>");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		char[] resultchararray;  
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='dlt/dltlist.html'>大乐透</a></td>");
		sb.append("<td>").append(termMap.get(1000001).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1000001).getWinLine())).append("</td>");
		sb.append("<td><div class='kj_ballbox'><ul>");
		resultchararray = termMap.get(1000001).getLotteryResult().toCharArray();
		for(int i=0;i<=8;)
		{
			sb.append("<li class='kj_redball'>");
			sb.append(resultchararray[i]).append(resultchararray[i+1]).append("</li>");
			i+=2;
		}
		for(int i=11;i<=13;){
			sb.append("<li class='kj_redball'>");
			sb.append(resultchararray[i]).append(resultchararray[i+1]).append("</li>");
			i+=2;
		}
		sb.append("</ul></div></td>");
		sb.append("<td>").append(termMap.get(1000001).getJackpot()).append("元</td>");
		sb.append("<td>一 三 六</td><td><a href='dlthtml/");
		sb.append(termMap.get(1000001).getTermNo()).append(".html'>");
		sb.append("<img src='../images/kj_more.gif' width='19' height='18' /></a></td></tr>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='qxc/qxclist.html'>七星彩</a></td>");
		sb.append("<td>").append(termMap.get(1000002).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1000002).getWinLine())).append("</td>");
		sb.append("<td><div class='kj_ballbox'><ul>");
		resultchararray = termMap.get(1000002).getLotteryResult().toCharArray();
		for(int i=0;i<=6;i++)
		sb.append("<li class='kj_yellowball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td>");
		sb.append("<td>").append(termMap.get(1000002).getJackpot()).append("元</td>");
		sb.append("<td>二 五 日</td><td><a href='qxchtml/");
		sb.append(termMap.get(1000002).getTermNo()).append(".html'>");
		sb.append("<img src='../images/kj_more.gif' width='19' height='18' /></a></td></tr>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='qls/qlslist.html'>排列三</a></td>");
		sb.append("<td>").append(termMap.get(1000003).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1000003).getWinLine())).append("</td>");
		sb.append("<td><div class='kj_ballbox'><ul>");
		resultchararray = termMap.get(1000003).getLotteryResult().toCharArray();
		for(int i=0;i<=2;i++)
			sb.append("<li class='kj_yellowball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td><td>0元</td><td>每日</td><td><a href='plshtml/");
		sb.append(termMap.get(1000003).getTermNo()).append(".html'><img src='../images/kj_more.gif' width='19' height='18' /></a></td>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='plw/plwlist.html'>排列五</a></td>");
		sb.append("<td>"+termMap.get(1000004).getTermNo()+"期</td><td>");
		sb.append(dateFormat.format(termMap.get(1000004).getWinLine())).append("</td>");
		sb.append("<td><div class='kj_ballbox'><ul>");
		resultchararray = termMap.get(1000004).getLotteryResult().toCharArray();
		for(int i=0;i<=4;i++)
			sb.append("<li class='kj_yellowball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td><td>0元</td><td>每日</td><td>");
		sb.append("<a href='plwhtml/").append(termMap.get(1000004).getTermNo());
		sb.append(".html'><img src='../images/kj_more.gif' width='19' height='18' /></a></td></tr>");
		sb.append("<tr class='kj_caizhong_blank'><td colspan='7'>&nbsp;</td></tr>");
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='zc/zclist.html'>胜负彩</a></td>");
		sb.append("<td>").append(termMap.get(1300001).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1300001).getWinLine()));
		sb.append("</td><td>");
		resultchararray = termMap.get(1300001).getLotteryResult().replaceAll("9","*").toCharArray();
		sb.append("<div class='kj_ballbox'>");
		for(int i=0;i<=13;i++)
			sb.append(resultchararray[i]+" ");
		sb.append("</div></td><td>0元</td><td>不定期</td><td><a href='zchtml/");
		sb.append(termMap.get(1300001).getTermNo()).append(".html'><img src='../images/kj_more.gif' width='19' height='18' /></a></td>");
		sb.append("</tr>");
		//------------------------------------------
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='zc6/zc6list.html'>六场半全</a></td>");
		sb.append("<td>").append(termMap.get(1300003).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1300003).getWinLine()));
		sb.append("</td><td>");
		resultchararray = termMap.get(1300003).getLotteryResult().replaceAll("9","*").toCharArray();
		sb.append("<div class='kj_ballbox'>");
		for(int i=0;i<=11;i++)
			sb.append(resultchararray[i]+" ");
		sb.append("</div></td><td>0元</td><td>不定期</td><td><a href='zc6html/");
		sb.append(termMap.get(1300003).getTermNo()).append(".html'><img src='../images/kj_more.gif' width='19' height='18' /></a></td>");
		sb.append("</tr>");
        
		//------------------------------------------
		sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='jq4/jq4list.html'>四场进球</a></td>");
		sb.append("<td>").append(termMap.get(1300004).getTermNo()).append("期</td>");
		sb.append("<td>").append(dateFormat.format(termMap.get(1300004).getWinLine()));
		sb.append("</td><td>");
		resultchararray = termMap.get(1300004).getLotteryResult().replaceAll("9","*").toCharArray();
		sb.append("<div class='kj_ballbox'>");
		for(int i=0;i<=7;i++)
			sb.append(resultchararray[i]+" ");
		sb.append("</div></td><td>0元</td><td>不定期</td><td><a href='jq4html/");
		sb.append(termMap.get(1300004).getTermNo()).append(".html'><img src='../images/kj_more.gif' width='19' height='18' /></a></td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("<div class='footer'>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dltseletfileSource(String termName, String enc, String url,List<String> numlist)throws LotteryException{
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<style type=\"text/css\">");
		sb.append("body {margin-left: 0px;margin-top: 0px;}</style>");
		sb.append("</head><body><div style=\"width:200!important;width:300 \" style='font-size:12px;' style=\"background:url(../../images/menu_kj_tishi_bg.gif)\"><div style='float:right;'>");                                                                    
		sb.append("<table><tr><td><font style='font-size:12px;'>历史记录:</font></td><td><select name='termNo' id='termnoinput' class='textfield2' style=\"vertical-align:top\" onchange='formsubmit();'>");
		sb.append("<option value=''>").append(termName).append("</option>");
		String href="";
		if("大乐透".equals(termName))
			href="dlthtml";
		else if("七星彩".equals(termName))
			href="qxchtml";
		else if("排列三".equals(termName))
			href="plshtml";
		else if("排列五".equals(termName))
			href="plwhtml";
		else if("四场进球".equals(termName))
			href="jq4html";
		else if("六场半全".equals(termName))
			href="zc6html";
		else
			href="zchtml";
		for (String strNo : numlist) 
				sb.append("<option value='../").append(href).append("/").append(strNo).append(".html' >").append(strNo).append("</option>");
		sb.append("</select></td></tr></table></div></body></html>");
		flg=FileUtils.writeStringToFile(url, "select.html", sb.toString(), enc);
		return flg;
	}
	@Override
	public boolean kjlistfileSourceOnIndex(String name, String enc, String url,
			Map<Integer, LotteryTermModel> termMap) throws LotteryException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyMMdd");
		char[] resultchararray; 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		sb.append("");
		sb.append("<link href='css/base.css' rel='stylesheet' type='text/css'>");
		sb.append("<div class='newkj_title' style='background-color:#000'><img src='images/newkj_title.gif' width='294' height='32' border='0' usemap='#Map2' /></div>");
		sb.append("<map name='Map2' id='Map2'><area shape='rect' coords='247,5,290,29' href='drawlottery/index.html' /></map>");
		sb.append("<div class='newkj_bg'>");
		sb.append("<table border='0' cellpadding='0' cellspacing='0' class='newkj_table'>");
		sb.append("<tr><td><div class='newkj_cz'>大乐透</div>");
		sb.append("<div class='newkj_qs'>第").append(termMap.get(1000001).getTermNo()).append("期</div>");
		sb.append("<div class='newkj_sj'>开奖日:一三六 最后更新:").append(dateFormat.format(termMap.get(1000001).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/dlthtml/").append(termMap.get(1000001).getTermNo()).append(".html'style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a></div>");
		resultchararray = termMap.get(1000001).getLotteryResult().toCharArray();
		
		sb.append("<div class='newkj_ball'><ul>");
		for(int i=0;i<=8;){
		sb.append("<li class='kj_redball'>").append(resultchararray[i]).append(resultchararray[i+1]).append("</li>");
		i+=2;
		}
		sb.append("<li class='kj_blueball'>").append(resultchararray[11]).append(resultchararray[12]).append("</li>");
		sb.append("<li class='kj_blueball'>").append(resultchararray[13]).append(resultchararray[14]).append("</li>");
		sb.append("</ul></div></td></tr>");
		sb.append("<tr><td><div class='newkj_cz'>排列三</div> <div class='newkj_qs'>第").append(termMap.get(1000003).getTermNo()).append("期</div>");
		sb.append("<div class='newkj_sj'>开奖日:每日 最后更新:").append(dateFormat.format(termMap.get(1000003).getWinLine())).append("&nbsp;");
		sb.append(" <a href='drawlottery/plshtml/").append(termMap.get(1000003).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a></div>");
		resultchararray = termMap.get(1000003).getLotteryResult().toCharArray();
		sb.append("<div class='newkj_ball'><ul><li class='kj_yellowball'>").append(resultchararray[0]).append("</li>");
		sb.append("<li class='kj_yellowball'>").append(resultchararray[1]).append("</li>");
		sb.append("<li class='kj_yellowball'>").append(resultchararray[2]).append("</li></ul></div></td></tr>");
		sb.append("<tr><td>    <div class='newkj_cz'>排列五</div>");
		resultchararray = termMap.get(1000004).getLotteryResult().toCharArray();
		sb.append("<div class='newkj_qs'>第").append(termMap.get(1000004).getTermNo()).append("期</div>");
		sb.append(" <div class='newkj_sj'>开奖日:每日 最后更新:").append(dateFormat.format(termMap.get(1000004).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/plwhtml/").append(termMap.get(1000004).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a></div>");
		sb.append("<div class='newkj_ball'><ul>");
		for(int i=0;i<=4;i++)
		{
			sb.append("<li class='kj_yellowball'>").append(resultchararray[i] ).append("</li>");
		}
		sb.append("</ul></div></td></tr>");
		sb.append("<tr><td><div class='newkj_cz'>七星彩</div>");
		sb.append("<div class='newkj_qs'>第").append(termMap.get(1000002).getTermNo()).append("期</div>");
		sb.append("<div class='newkj_sj'>开奖日:二五日 最后更新:").append(dateFormat.format(termMap.get(1000002).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/qxchtml/").append(termMap.get(1000002).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a></div>");
		resultchararray = termMap.get(1000002).getLotteryResult().toCharArray();
		sb.append("<div class='newkj_ball'><ul>");
		for (int i = 0; i <=6; i++) {
			sb.append(" <li class='kj_yellowball'>").append(resultchararray[i]).append("</li>");
		}
		sb.append("</ul></div></td></tr>");
		//=====================
		sb.append("<tr> <td><div class='newkj_cz'>多乐彩</div> <div class='newkj_qs'>").append(termMap.get(1200001).getTermNo()).append("期</div>");
		resultchararray = termMap.get(1200001).getLotteryResult().toCharArray();
		sb.append("<div class='newkj_sj'>最后更新:").append(dateFormat.format(termMap.get(1200001).getWinLine2()));
		sb.append("&nbsp;<a href='drawlottery/dlchtml/").append(sf.format(termMap.get(1200001).getWinLine2())).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a> </div>");
		sb.append("<div class='newkj_ball'><ul>");
		for(int i=0;i<=8;i+=2)
			sb.append("<li class='kj_yellowball'>").append(resultchararray[i]+""+resultchararray[i+1]).append("</li>");
		sb.append("</ul></div></td></tr>");
		sb.append("<tr> <td><div class='newkj_cz'>胜负彩</div> <div class='newkj_qs'>第").append(termMap.get(1300001).getTermNo()).append("期</div>");
		resultchararray = termMap.get(1300001).getLotteryResult().replaceAll("9","*" ).toCharArray();
		sb.append("<div class='newkj_sj'>开奖日:不定期 最后更新:").append(dateFormat.format(termMap.get(1300001).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/zchtml/").append(termMap.get(1300001).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a> </div>");
		sb.append("<div class='newkj_ballzc'><ul>");
		for(int i=0;i<=13;i++)
			sb.append("<li class='kj_zcball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td></tr>");
		//=====================
		sb.append("<tr> <td><div class='newkj_cz'>六场半全场</div> <div class='newkj_qs'>第").append(termMap.get(1300003).getTermNo()).append("期</div>");
		resultchararray = termMap.get(1300003).getLotteryResult().replaceAll("9","*" ).toCharArray();
		sb.append("<div class='newkj_sj'>开奖日:不定期 最后更新:").append(dateFormat.format(termMap.get(1300003).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/zc6html/").append(termMap.get(1300003).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a> </div>");
		sb.append("<div class='newkj_ballzc'><ul>");
		for(int i=0;i<=11;i++)
			sb.append("<li class='kj_zcball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td></tr>");
		//=====================
		sb.append("<tr> <td><div class='newkj_cz'>四场进球</div> <div class='newkj_qs'>第").append(termMap.get(1300004).getTermNo()).append("期</div>");
		resultchararray = termMap.get(1300004).getLotteryResult().replaceAll("9","*" ).toCharArray();
		sb.append("<div class='newkj_sj'>开奖日:不定期 最后更新:").append(dateFormat.format(termMap.get(1300004).getWinLine()));
		sb.append("&nbsp;<a href='drawlottery/jq4html/").append(termMap.get(1300004).getTermNo()).append(".html' style='text-decoration:none'><span style='color:#38aff3'>[详细]</span></a> </div>");
		sb.append("<div class='newkj_ballzc'><ul>");
		for(int i=0;i<=7;i++)
			sb.append("<li class='kj_zcball'>").append(resultchararray[i]).append("</li>");
		sb.append("</ul></div></td></tr>");
		
		sb.append("</table></div>");
		boolean flg=FileUtils.writeStringToFile(url,name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dltzjkaijianSource(String name, String enc, String url,LotteryTermModel currentTerm,
			List<LotteryTermModel> historyTermList,List<String[]> WinResultList) throws LotteryException {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class='bet_right_1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_2'><div class='bet_right_new'>");
		sb.append("<div class='right_area'><div class='right_menu'>最新开奖</div>");
		sb.append("<div class='right_qishu'>大乐透第").append(currentTerm.getBeforeWinTermNo()).append("期</div>");
		sb.append("<div class='right_ball'><ul>");
		for(int i=0;i<=8;){
			sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(i, i+2)).append("</li>");
			i+=2;
		}
		sb.append("<li class='right_ball_blue'>").append(currentTerm.getLotteryResult().substring(11,13)).append("</li>");
		sb.append("<li class='right_ball_blue'>").append(currentTerm.getLotteryResult().substring(13,15)).append("</li>");
		sb.append("</ul></div>");
		sb.append("<div class='right_jiangchi'>奖池累计：<span class='font_red'>").append(currentTerm.getJackpot()).append("元</span></div>");
		sb.append("<table border='1' class='right_table'>");
		sb.append("<tr><td class='right_table_title'>奖项</td><td class='right_table_title'>中奖注数</td>");
		sb.append("<td class='right_table_title'>每注奖金</td></tr>");
		sb.append("<tr><td>一等奖</td><td>").append(WinResultList.get(0)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append(WinResultList.get(0)[1]).append("元</td></tr>");
		sb.append("<tr><td>二等奖</td><td>").append(WinResultList.get(1)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append(WinResultList.get(1)[1]).append("元</td></tr>");
		sb.append("<tr><td>三等奖</td><td>").append(WinResultList.get(2)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append(WinResultList.get(2)[1]).append("元</td></tr>");
		sb.append("<tr><td>四等奖</td><td>").append(WinResultList.get(3)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("3000元</td></tr>");
		sb.append("<tr><td>五等奖</td><td>").append(WinResultList.get(4)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("600元</td></tr>");
		sb.append("<tr><td>六等奖</td><td>").append(WinResultList.get(5)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("100元</td></tr>");
		sb.append("<tr><td>七等奖</td><td>").append(WinResultList.get(6)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("10元</td></tr>");
		sb.append("<tr><td>八等奖</td><td>").append(WinResultList.get(7)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("5元</td></tr>");
		sb.append("<tr><td>12选2</td><td>").append(WinResultList.get(8)[0]).append("</td>");
		sb.append("<td class='font_red_n'>").append("60元</td></tr></table>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></div> </div>");
		sb.append("</div><div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_check'><div class='right_area'>");
		sb.append("<div class='right_menu'>开奖查询<span class='right_more'><a href='drawlottery/dlt/dltlist.html' >更多</a></span></div>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<table border='1' class='right_table'><tr><td width='55' class='right_table_title'>期号</td>");
		sb.append("<td width='95' class='right_table_title'>前区</td> <td width='48' class='right_table_title'>后区</td></tr>");
		for (LotteryTermModel model : historyTermList) {
			sb.append("<tr><td>").append(model.getTermNo()).append("</td>");
			sb.append("<td class='font_red_n'>").append(model.getLotteryResult().substring(0,10)).append("</td>");
			sb.append("<td class='font_blue_n'>").append(model.getLotteryResult().substring(11,15)).append("</td></tr>");
		}
		sb.append("</table><div class='blank'><img src='images/blank.gif' width='1' height='1'></div> </div>");
		sb.append("</div><div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_trend'><div class='right_area'><div class='right_menu'>走势分析</div>");
		sb.append("<div class='right_trend'><ul>");
		sb.append("<li><a href='direction/dlt30.html'>最近30期</a></li>");
		sb.append("<li><a href='direction/dlt50.html'>最近50期</a></li>");
		sb.append("<li><a href='direction/dlt100.html'>最近100期</a></li>");
		sb.append("</ul></div>");
		sb.append("</div></div></div></div>");
		boolean flg=FileUtils.writeStringToFile(url,name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean qxczjkaijianSource(String name, String enc, String url,
			LotteryTermModel currentTerm,
			List<LotteryTermModel> historyTermList, List<String[]> WinResultList)
			throws LotteryException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
		sb.append("<div class='bet_right_1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_2'><div class='bet_right_new'><div class='right_area'>");
		sb.append("<div class='right_menu'>最新开奖</div> <div class='right_qishu'>七星彩第");
		sb.append(currentTerm.getBeforeWinTermNo()).append("期</div>");;
		sb.append("<div class='right_ball'><ul>");
		for(int i=0;i<=6;i++)
			sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(i, i+1)).append("</li>");
		sb.append("</ul></div><table border='1' class='right_table'><tr><td class='right_table_title'>奖项</td>");
		sb.append("<td class='right_table_title'>每注奖金</td></tr>");
		sb.append("<tr><td>一等奖</td><td class='font_red_n'>").append(WinResultList.get(0)[1]).append("元</td></tr>");
		sb.append("<tr><td>二等奖</td><td class='font_red_n'>").append(WinResultList.get(1)[1]).append("元</td></tr>");
		sb.append("<tr><td>三等奖</td><td class='font_red_n'>").append("1800元</td></tr>");
		sb.append("<tr><td>四等奖</td><td class='font_red_n'>").append("300元</td></tr>");
		sb.append("<tr><td>五等奖</td><td class='font_red_n'>").append("20元</td></tr>");
		sb.append("<tr><td>五等奖</td><td class='font_red_n'>").append("5元</td></tr>");
		sb.append("</table><div class='blank'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("</div></div> <div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_check'><div class='right_area'>");
		sb.append("<div class='right_menu'>开奖查询<span class='right_more'><a href='drawlottery/qxc/qxclist.html'>").append("更多</a></span></div>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<table border='1' class='right_table'><tr><td width='52' class='right_table_title'>期号</td>");
		sb.append("<td width='57' class='right_table_title'>日期</td><td width='89' class='right_table_title'>开奖结果</td></tr>");
		for (LotteryTermModel model :historyTermList) {
			sb.append("<tr><td>").append(model.getTermNo()).append("</td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td class='font_red_n'>").append(model.getLotteryResult()).append("</td></tr>");
		}
		sb.append("</table><div class='blank'><img src='images/blank.gif' width='1' height='1'></div></div>");
		sb.append("</div> <div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_trend'><div class='right_area'>");
		sb.append("<div class='right_menu'>走势查询</div><div class='right_trend'><ul>");
		sb.append("<li><a href='direction/qxc30.html'>最近30期</a></li>");
		sb.append("<li><a href='direction/qxc50.html'>最近50期</a></li>");
		sb.append("<li><a href='direction/qxc100.html'>最近100期</a></li>");
		sb.append("</ul></div></div></div></div></div>");
		boolean flg=FileUtils.writeStringToFile(url,name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plszjkaijianSource(String name, String enc, String url,
			LotteryTermModel currentTerm, List<LotteryTermModel> historyTermList)
			throws LotteryException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
		sb.append("<div class='bet_right_1'><img src='images/blank.gif' width='1' height='1' /></div>");
		sb.append("<div class='bet_right_2'><div class='bet_right_new'><div class='right_area'>");
		sb.append("<div class='right_menu'>最新开奖</div><div class='right_qishu'>");
		sb.append("排列三第").append(currentTerm.getBeforeWinTermNo()).append("期</div>");
		sb.append("<div class='right_ball'><ul>");
		sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(0, 1)).append("</li>");
		sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(1, 2)).append("</li>");
		sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(2, 3)).append("</li>");
		sb.append("</ul></div><table border='1' class='right_table'><tr><td class='right_table_title'>奖项</td>");
		sb.append("<td class='right_table_title'>每注奖金</td>");
		sb.append("</tr><tr><td>直选</td><td class='font_red_n'>1000元</td></tr><tr><td>组三</td>");
		sb.append("<td class='font_red_n'>320元</td></tr><tr><td>组六</td><td class='font_red_n'>");
		sb.append("160元</td></tr></table>");
		sb.append("<div><img src='images/blank.gif' width='1' height='2'/></div></div></div>");
		sb.append("<div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_check'><div class='right_area'><div class='right_menu'>");
		sb.append("开奖查询<span class='right_more'><a href='drawlottery/pls/plslist.html' >更多</a></span></div>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></img></div>");
		sb.append("<table border='1' class='right_table'><tr><td width='54' class='right_table_title'>");
		sb.append("期号</td><td width='86' class='right_table_title'>时间</td>");
		sb.append("<td width='58' class='right_table_title'>开奖结果</td></tr>");
		for (LotteryTermModel model : historyTermList) {
			sb.append("<tr><td>").append(model.getTermNo()).append("</td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td class='font_red_n'>").append(model.getLotteryResult()).append("</td></tr>");
		}
		sb.append("</table><div class='blank'><img src='images/blank.gif' width='1' height='1'></div></div></div>");
		sb.append("<div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>	");
		sb.append("<div class='bet_right_trend'><div class='right_area'><div class='right_menu'>");
		sb.append("走势查询</div><div class='right_trend'><ul>");
		sb.append("<li><a href='direction/pls30.html'>最近30期</a></li>");
		sb.append("<li><a href='direction/pls50.html'>最近50期</a></li>");
		sb.append("<li><a href='direction/pls100.html'>最近100期</a></li>");
		sb.append("</ul></div></div></div></div></div>");
		boolean flg=FileUtils.writeStringToFile(url,name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean plwzjkaijianSource(String name, String enc, String url,
			LotteryTermModel currentTerm, List<LotteryTermModel> historyTermList)
			throws LotteryException {
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
		sb.append("<div class='bet_right_1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_2'><div class='bet_right_new'><div class='right_area'>");
		sb.append("<div class='right_menu'>最新开奖</div> <div class='right_qishu'>排列五第");
		sb.append(currentTerm.getBeforeWinTermNo()).append("期</div>");
		sb.append("<div class='right_ball'><ul>");
		for(int i=0;i<=4;i++)
		sb.append("<li class='right_ball_red'>").append(currentTerm.getLotteryResult().substring(i, i+1)).append("</li>");
		sb.append("</ul></div><table border='1' class='right_table'><tr> <td class='right_table_title'>奖项</td>");
		sb.append("<td class='right_table_title'>每注奖金</td></tr><tr><td>直选</td>");
		sb.append("<td class='font_red_n'>100,000元</td></tr></table>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></div></div>");
		sb.append("</div><div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_check'><div class='right_area'>");
		sb.append("<div class='right_menu'>开奖查询<span class='right_more'>");
		sb.append("<a href='drawlottery/pls/plslist.html'>更多</a></span></div>");
		sb.append("<div class='blank'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<table border='1' class='right_table'><tr><td width='53' class='right_table_title'>期号</td>");
		sb.append("<td width='74' class='right_table_title'>日期</td><td width='71' class='right_table_title'>开奖结果</td></tr>");
		for (LotteryTermModel model : historyTermList) {
			sb.append("<tr><td>").append(model.getTermNo()).append("</td>");
			sb.append("<td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td class='font_red_n'>").append(model.getLotteryResult()).append("</td></tr>");
		}
		sb.append("</table><div class='blank'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("</div></div><div class='blank1'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='bet_right_trend'><div class='right_area'>");
		sb.append(" <div class='right_menu'>走势查询</div>");
		sb.append("<div class='right_trend'><ul>");
		sb.append("<li><a href='direction/plw30.html'>最近30期</a></li>");
		sb.append("<li><a href='direction/plw50.html'>最近50期</a></li>");
		sb.append("<li><a href='direction/plw100.html'>最近100期</a></li>");
		sb.append("</ul></div></div></div> </div></div>");
		boolean flg=FileUtils.writeStringToFile(url,name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean jq4listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list) throws LotteryException {
		// TODO Auto-generated method stub
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li ><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href=\"../zc6/zc6list.html\" >六场半全</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->四场进球</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../jq4html/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300001'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		if(list!=null&&list.size()>0)
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../jq4html/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td>");
			for (int i = 0; i < 8; i++) {
				sb.append(model.getLotteryResult().substring(i, i+1).replaceAll("9", "*"));
				if(i!=7)
					sb.append(",");
			}
			sb.append("</td><td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../jq4html/").append(model.getTermNo()).append(".html'>");
			sb.append("<img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}
	//进球四详细

	@Override
	public boolean jq4kjcxfileSource(String name, String enc, String url,
			LotteryTermModel termModel,List<RaceBean> raceList) throws LotteryException {
		boolean flg = false;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat fmt=NumberFormat.getCurrencyInstance();
		NumberFormat myfmt=NumberFormat.getInstance();
	
		String[]  superLotteryResult = termModel.getLotteryInfo().getBall4WinResult(); 
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href=\"../zc6/zc6list.html\" >六场半全</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->足球彩票</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style='border: 0;' frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300004'></form></div>");
		sb.append("<div class='kj_more_top'><div class='kj_top_logo'><img src='../../images/kj_logo_sfc.gif' width='288' height='70' />");
		sb.append("</div><div class='kj_top_title'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期开奖结果:");
		sb.append("</div><div class='kj_zc'>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
		{
			sb.append(termModel.getLotteryResult().substring(i,i+1).replaceAll("9", "*"));
			if(i!=termModel.getLotteryResult().length()-1)
			sb.append(",");
		}
		sb.append("</div></div><table border='0' cellspacing='1' class='kj_caizhong_table'><tr>");
		sb.append("<td colspan='3' class='kj_table_base'><div class='kj_date'>开奖日期：");
		sb.append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>").append(termModel.getTermNo());
		sb.append("</span> 期四场进球销量").append("<span class='font_red_n'>").append(myfmt.format(Integer.parseInt(termModel.getSalesVolume())));
        sb.append("</span> 元</div></td></tr>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><table width='100%' border='0' cellpadding='0' cellspacing='1'>");
		sb.append("<tr class='kj_caizhong_title'>");
		sb.append("<th>场次</th>");
		for(int i=1;i<=8;i++)
		{
			sb.append("<th >"+i+"</th>");
		}
		sb.append("</tr>");
		sb.append("<tr class='zc_dz'><td>对阵</td>");
		
		for (RaceBean raceBean : raceList) {
			String hometeam=raceBean.getHomeTeam();
			String awaryterm=raceBean.getAwaryTeam();
			sb.append("<td>").append(hometeam).append("</td>");
			sb.append("<td>").append(awaryterm).append("</td>");
		}
		sb.append("</tr>");
		sb.append("<tr class='zc_dz'>");
		sb.append("<td>彩果</td>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
			sb.append("<td>").append(termModel.getLotteryResult().substring(i, i+1).replaceAll("9","*")).append("</td>");
		sb.append("</tr></table></td></tr><tr class='kj_caizhong_title'><td width='240'>奖级</td>");
		sb.append("<td width='405'>中奖注数(注)</td>");
		sb.append("<td width='305'>每注奖金(元)</td></tr><tr class='kj_table_base'>");
		sb.append("<td class='kj_table_caizhong3'>一等奖</td><td>").append(superLotteryResult[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult[1]))).append("</td></tr>");
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean zc6kjcxfileSource(String name, String enc, String url,
			LotteryTermModel termModel, List<RaceBean> raceList)
			throws LotteryException {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat fmt=NumberFormat.getCurrencyInstance();
		NumberFormat myfmt=NumberFormat.getInstance();
	
		String[]  superLotteryResult = termModel.getLotteryInfo().getHalf6WinResult(); 
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../zc6/zc6list.html\" >六场半全</a></li>");
		sb.append("<li ><a href=\"../jq4/jq4list.html\" >四场进球</a></li>");
		sb.append("<li ><a href=\"../dlc/dlclist.html\" >多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->足球彩票</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='select.html' scrolling='no' style='border: 0;' frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300004'></form></div>");
		sb.append("<div class='kj_more_top'><div class='kj_top_logo'><img src='../../images/kj_logo_sfc.gif' width='288' height='70' />");
		sb.append("</div><div class='kj_top_title'>第<span class='font_red_n'>");
		sb.append(termModel.getTermNo()).append("</span> 期开奖结果:");
		sb.append("</div><div class='kj_zc'>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
		{
			sb.append(termModel.getLotteryResult().substring(i,i+1).replaceAll("9", "*"));
			if(i!=termModel.getLotteryResult().length()-1)
			sb.append(",");
		}
		sb.append("</div></div><table border='0' cellspacing='1' class='kj_caizhong_table'><tr>");
		sb.append("<td colspan='3' class='kj_table_base'><div class='kj_date'>开奖日期：");
		sb.append(sf.format(termModel.getWinLine())).append("</div>");
		sb.append("<div class='kj_more_xl'>第<span class='font_red_n'>").append(termModel.getTermNo());
		sb.append("</span> 期六场半全销量").append("<span class='font_red_n'>").append(myfmt.format(Integer.parseInt(termModel.getSalesVolume())));
        sb.append("</span> 元</div></td></tr>");
		sb.append("<tr><td colspan='3' class='kj_table_base'><table width='100%' border='0' cellpadding='0' cellspacing='1'>");
		sb.append("<tr class='kj_caizhong_title'>");
		sb.append("<th>场次</th>");
		for(int i=1;i<=6;i++)
		{
			sb.append("<th colspan=2>"+i+"</th>");
		}
		sb.append("</tr>");
		sb.append("<tr class='kj_caizhong_title'><td class='kj_caizhong_title'>对阵</td>");
		
		for (RaceBean raceBean : raceList) {
			String hometeam=raceBean.getHomeTeam();
			String awaryterm=raceBean.getAwaryTeam();
			sb.append("<td>").append(hometeam).append("</td>");
			sb.append("<td>").append(awaryterm).append("</td>");
		}
		
		sb.append("</tr>");
		sb.append("<tr  class='kj_caizhong_title' >");
		sb.append("<td rowspan=2  >彩果</td>");
		for(int i=1;i<=6;i++)
			 sb.append("<td  >半</td ><td >全</td>");
		sb.append("</tr><tr class='zc_dz'>");
		for(int i=0;i<termModel.getLotteryResult().length();i++)
			sb.append("<td>").append(termModel.getLotteryResult().substring(i, i+1).replaceAll("9","*")).append("</td>");
		sb.append("</tr>");
		sb.append("</table></td></tr><tr class='kj_caizhong_title'><td width='240'>奖级</td>");
		sb.append("<td width='405'>中奖注数(注)</td>");
		sb.append("<td width='305'>每注奖金(元)</td></tr><tr class='kj_table_base'>");
		sb.append("<td class='kj_table_caizhong3'>一等奖</td><td>").append(superLotteryResult[0]).append("</td>");
		sb.append("<td>").append(fmt.format(Long.parseLong(superLotteryResult[1]))).append("</td></tr>");
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		boolean flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean zc6listfileSource(String name, String enc, String url,
			List<LotteryTermModel> list) throws LotteryException {
		// TODO Auto-generated method stub
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li ><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li class=\"zs_bar_caizhong_on\"><a href=\"../zc6/zc6list.html\" >六场半全</a></li>");
		sb.append("<li ><a href=\"../jq4/jq4list.html\" >四场进球</a></li>");
		sb.append("<li ><a href=\"../dlc/dlclist.html\" >多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->四场进球</div>");
		sb.append("<form action='zcdrawdetail.jhtml' id='subform'><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../zc6html/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1300001'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'><tr class='kj_caizhong_title'>");
		sb.append("<td>期号</td><td>开奖号码</td><td>开奖时间</td><td>开奖详情</td></tr>");
		if(list!=null&&list.size()>0)
		for (LotteryTermModel model : list) {
			sb.append("<tr class='kj_table_base'><td class='kj_table_caizhong'><a href='../zchtml/");
			sb.append(model.getTermNo()).append(".html'>").append(model.getTermNo()).append("期</a></td>");
			sb.append("<td>");
			for (int i = 0; i < 12; i++) {
				sb.append(model.getLotteryResult().substring(i, i+1).replaceAll("9", "*"));
				if(i!=11)
					sb.append(",");
			}
			sb.append("</td><td>").append(sf.format(model.getWinLine())).append("</td>");
			sb.append("<td><a href='../zc6html/").append(model.getTermNo()).append(".html'>");
			sb.append("<img src='../../images/kj_more.gif' width='19' height='19' /></a></td></tr>");
		}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	

	@Override
	public boolean dlcseletfileSource(String enc, String url)
			throws LotteryException {
		boolean flg=false;
		Calendar today=Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df=new SimpleDateFormat("yyMMdd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<script src=\"../../js/selectdetail.js\"></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<style type=\"text/css\">");
		sb.append("body {margin-left: 0px;margin-top: 0px;}</style>");
		sb.append("</head><body><div style=\"width:200!important;width:300 \" style='font-size:12px;' style=\"background:url(../../images/menu_kj_tishi_bg.gif)\"><div style='float:right;'>");                                                                    
		sb.append("<table><tr><td><font style='font-size:12px;'>按天查询:</font></td><td><select name='termNo' id='termnoinput'  style=\"vertical-align:top\" onchange='formsubmit();'>");
		sb.append("<option value=''>").append("多乐彩").append("</option>");
		String href="dlchtml";
		for (int i=100;i>=1;i--) 
		{
			sb.append("<option value='../").append(href).append("/").append(df.format(today.getTime())).append(".html' >").append(sf.format(today.getTime())).append("</option>");
			today.add(Calendar.DAY_OF_MONTH,-1);
		}
		sb.append("</select></td></tr></table></div></body></html>");
		flg=FileUtils.writeStringToFile(url, "select.html", sb.toString(), enc);
		return flg;
	}
	
	@Override
	public boolean dlc_rxfileSource(List<LotteryTermModel> list,
			String name, String enc, String url) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<title>走势分析-掌上赢家-彩票-体育彩票-足球彩票</title>");
		sb.append("<script src='../js/utils/jquery.min.js'></script>");
		sb.append("<script src='../js/utils/jquery.alerts.js'></script>");
		sb.append("<script src='../js/utils/jquery.countdown.pack.js'></script>");
		sb.append("<script src='../js/utils/jquery.countdown-zh-CN.js'></script>");
		sb.append("<script src='../js/trade/dlc_zs.js'></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/dlc_select.js\"></script>");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("</head>");
		sb.append("<body>");
		//===========================头开始
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//=================================内容开始
		sb.append("<div class='zs_blue'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'>");
		sb.append("<div class='zs_qishu_title'>按天数：</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='dlc_rx_1.html'>最近一天</option>");
		sb.append("<option value='dlc_rx_2.html'>最近二天</option>");
		sb.append("<option value='dlc_rx_3.html'>最近三天</option>");
		sb.append("<option value='dlc_rx_5.html'>最近五天</option></select>");
		sb.append("</div>");
		sb.append("<div class='zs_qishu_zl'>");
		sb.append("<ul>");
		sb.append("	<li class='zs_qishu_zl_on'><a href='dlc_rx_1.html'>任 选 走 势</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zhi_1.html'>前 二 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zu_1.html'>前 二 组 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zhi_1.html'>前 三 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zu_1.html'>前 三 组 选</a></li>");
		sb.append("</ul>");
		sb.append("</div></div>");
		sb.append("<div class='zs_trend'><div class='zs_trend_title'>");
		sb.append("多乐彩任选走势</div>");
		DecimalFormat df = new DecimalFormat("00");
		String codestr;
		sb.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		sb.append("<tr><td width='90' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='70' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("<td colspan='11' bgcolor='#fcf3f0'>开奖号码分布图</td>");
		sb.append(" <td colspan='6' bgcolor='#fcf5e1' id='ji'>奇数个数遗漏（<a href=\"javascript:changeJO('ji','ou')\">偶数</a>）</td>");
		sb.append("<td colspan='6' bgcolor='#fcf5e1' id='ou' style='display: none;'>偶数个数遗漏（<a href=\"javascript:changeJO('ou','ji')\">奇数</a>）</td>");
		sb.append("<td colspan='6' bgcolor='#fff9eb' id='xiao'>小数个数遗漏（<a href=\"javascript:changeDX('xiao','da')\">大数</a>）</td>");
		sb.append("<td colspan='6' bgcolor='#fff9eb' id='da' style='display:none;'>大数个数遗漏（<a href=\"javascript:changeDX('da','xiao')\">小数</a>）</td>");
		sb.append("<td colspan='6' bgcolor='#fcf5e1' id='zhi'>质数数个数遗漏（<a href=\"javascript:changeZH('zhi','he')\">合数</a>）</td>");
		sb.append("<td colspan='6' bgcolor='#fcf5e1' id='he' style='display:none;'>合数数个数遗漏（<a href=\"javascript:changeZH('he','zhi')\">质数</a>）</td>");
		sb.append("</tr><tr>");
		for(int i=1;i<=11;i++)
		   if(i<10)
			  sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
		   else
			  sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
	    for(int k=0;k<3;k++)
	    	for(int i=0;i<=5;i++)
	 			  sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("</tr>");
		Map<String,Integer> allcount=new HashMap<String,Integer>();
		for(int i=1;i<=11;i++)
		  allcount.put(i+"",0);
		Map<Integer,Integer> jimap=new HashMap<Integer,Integer>();	//奇
		Map<Integer,Integer> oumap=new HashMap<Integer,Integer>();	//偶		
		Map<Integer,Integer> damap=new HashMap<Integer,Integer>();  //大
		Map<Integer,Integer> xiaomap=new HashMap<Integer,Integer>(); //小
		Map<Integer,Integer> zhimap=new HashMap<Integer,Integer>();  //质
		Map<Integer,Integer> hemap=new HashMap<Integer,Integer>();	//合
		Map<String,Integer> maxMap=new HashMap<String,Integer>();//最大遗漏
		Map<String,Integer> maxlcMap=new HashMap<String,Integer>();//连出遗漏
		for(int i=0;i<=5;i++)
		{
			zhimap.put(i,0);
			hemap.put(i,0);
			damap.put(i,0);
			xiaomap.put(i,0);
			jimap.put(i,0);
			oumap.put(i,0);
		}
	    LotteryTermModel model=list.get(list.size()-1);
	    //初始化
	    for(int k=1;k<=11;k++) //基本
	    	maxlcMap.put("B-"+df.format(k),0);
	    for(int k=0;k<=5;k++) {
	    	maxlcMap.put("ON-"+k,0);//奇数  
	    	maxlcMap.put("EN-"+k,0);//偶数
	    	maxlcMap.put("SN-"+k,0);//小数 
	    	maxlcMap.put("BN-"+k,0);//大数
	    	maxlcMap.put("PN-"+k,0);//质数  
	    	maxlcMap.put("CN-"+k,0);//合数
	    }
	    int [] arrs={0,0,0,0,0,0,0,0,0,0,0};
	    int[] jiarr={0,0,0,0,0,0};
	    int[] ouarr={0,0,0,0,0,0};
	    int[] xiaoarr={0,0,0,0,0,0};
	    int[] daarr={0,0,0,0,0,0};
	    int[] zhiarr={0,0,0,0,0,0};
	    int[] hearr={0,0,0,0,0,0};
	    for(int i=0;i<list.size();i++)
	    {
	         LotteryTermModel m=list.get(i);
	         Map<String,Integer> yl=m.getLotteryInfo().getMissCountResult();
	    		if(i==0)
	    		{
	    		    for(int k=1;k<=11;k++) //基本
	    		       maxMap.put("B-"+df.format(k),yl.get("B-"+df.format(k)));
	    		    for(int k=0;k<=5;k++) {
	    		       maxMap.put("ON-"+k,yl.get("ON-"+k));//奇数  
	    		       maxMap.put("EN-"+k,yl.get("EN-"+k));//偶数
	    		       maxMap.put("SN-"+k,yl.get("SN-"+k));//小数 
	    		       maxMap.put("BN-"+k,yl.get("BN-"+k));//大数
	    		       maxMap.put("PN-"+k,yl.get("PN-"+k));//质数  
	    		       maxMap.put("CN-"+k,yl.get("CN-"+k));//合数
	    		    }
	    		}else
	    		{
	    			for(int k=1;k<=11;k++) //基本
	    			   if(maxMap.get("B-"+df.format(k))<yl.get("B-"+df.format(k)))
	    		       		maxMap.put("B-"+df.format(k),yl.get("B-"+df.format(k)));
	    		    for(int k=0;k<=5;k++) {
						if(maxMap.get("ON-"+k)<yl.get("ON-"+k))   		    
	    		    	   maxMap.put("ON-"+k,yl.get("ON-"+k));//奇数  
	    		    	if(maxMap.get("EN-"+k)<yl.get("ON-"+k))
	    		       	   maxMap.put("EN-"+k,yl.get("EN-"+k));//偶数
	    		        if(maxMap.get("SN-"+k)<yl.get("SN-"+k))   
	    		           maxMap.put("SN-"+k,yl.get("SN-"+k));//小数 
	    		        if(maxMap.get("BN-"+k)<yl.get("BN-"+k))  
	    		           maxMap.put("BN-"+k,yl.get("BN-"+k));//大数
	    		        if(maxMap.get("PN-"+k)<yl.get("PN-"+k))   
	    		           maxMap.put("PN-"+k,yl.get("PN-"+k));//质数  
	    		        if(maxMap.get("CN-"+k)<yl.get("CN-"+k))   
	    		           maxMap.put("CN-"+k,yl.get("CN-"+k));//合数
	    		    }
	    		}
	    		//计算最大连出值
	    		
	    		for(int k=1;k<=11;k++){
	    		   if(yl.get("B-"+df.format(k))==0)
	    		   		arrs[k-1]=arrs[k-1]+1;
	    		   else
	    		   {
	    		       if(maxlcMap.get("B-"+df.format(k))<arrs[k-1])
	    		       		maxlcMap.put("B-"+df.format(k),arrs[k-1]);
	    		       arrs[k-1]=0;
	    		   }
	    		}
	    		
	    		for(int k=0;k<=5;k++){
	    		   if(yl.get("ON-"+k)==0)
	    		   		jiarr[k]=jiarr[k]+1;
	    		   else{
	    		   		if(maxlcMap.get("ON-"+k)<jiarr[k])
	    		       		maxlcMap.put("ON-"+k,jiarr[k]);
	    		       jiarr[k]=0;
	    		   }
	    		   
	    		   if(yl.get("EN-"+k)==0)
	    		   		ouarr[k]+=ouarr[k];
	    		   else{
	    		   		if(maxlcMap.get("EN-"+k)<ouarr[k])
	    		       		maxlcMap.put("EN-"+k,ouarr[k]);
	    		       ouarr[k]=0;
	    		   }
	    		   
	    		   if(yl.get("SN-"+k)==0)
	    		   		xiaoarr[k]=xiaoarr[k]+1;
	    		   else{
	    		   		if(maxlcMap.get("SN-"+k)<xiaoarr[k])
	    		       		maxlcMap.put("SN-"+k,xiaoarr[k]);
	    		       xiaoarr[k]=0;
	    		   }
	    		   
	    		   if(yl.get("BN-"+k)==0)
	    		   		daarr[k]=daarr[k]+1;
	    		   else{
	    		   		if(maxlcMap.get("BN-"+k)<daarr[k])
	    		       		maxlcMap.put("BN-"+k,daarr[k]);
	    		       daarr[k]=0;
	    		   }
	    		   
	    		   if(yl.get("PN-"+k)==0)
	    		   		zhiarr[k]=zhiarr[k]+1;
	    		   else{
	    		   		if(maxlcMap.get("PN-"+k)<zhiarr[k])
	    		       		maxlcMap.put("PN-"+k,zhiarr[k]);
	    		       zhiarr[k]=0;
	    		   }
	    		   
	    		   if(yl.get("CN-"+k)==k)
	    		   		hearr[k]=hearr[k]+1;
	    		   else{
	    		   		if(maxlcMap.get("CN-"+k)<hearr[k])
	    		       		maxlcMap.put("CN-"+k,hearr[k]);
	    		       hearr[k]=0;
	    		    }
	    	}
	    }
	    Map<String, Integer> zjyl=model.getLotteryInfo().getMissCountResult();
	    for (LotteryTermModel m : list) {
	    	sb.append("<tr><td width='70' bgcolor='#FFFFFF'>").append(m.getTermNo()).append("</td>");
	    	sb.append("<td width='70' bgcolor='#FFFFFF'>").append(m.getLotteryResult()).append("</td>");
	    	codestr=m.getLotteryResult();
	    	String[] arr={codestr.substring(0,2),codestr.substring(2,4),codestr.substring(4,6),codestr.substring(6,8),codestr.substring(8,10)};
			LotteryInfo lotteryInfo =m.getLotteryInfo();
			Map<String, Integer> yiLoumap=lotteryInfo.getMissCountResult();
			for(int i=1;i<=11;i++)
			   {
			       int count=0;
			       for(String str:arr)
			           if(i<10){
			          	  if(str.equals("0"+i))
			          		count++;
			          	}
			          	else{
			          	  if(str.equals(i+""))
			          		count++;
			          	}       
			       if(count==0)
			         {
			    	   sb.append(" <td width='28' height='28' class='font_red_n' bgcolor='#fcf3f0'>");
			    	   sb.append(yiLoumap.get("B-"+df.format(i)));
			   	       sb.append("</td>");
			   	    }
			   	    else{
			   	      Integer num=allcount.get(""+i);
			   	      allcount.put(""+i,++num);
			   	      sb.append(" <td width='28' height='28' bgcolor='#fcf3f0' class='zs_redball_on'> ").append(df.format(i)).append("</td>");
			   	    }
			   }
			   for(int i=0;i<=5;i++){
			        if(yiLoumap.get("ON-"+i)!=null&&yiLoumap.get("ON-"+i)==0)
			        {
			            jimap.put(i,jimap.get(i)+1);
			        }
			        if(yiLoumap.get("EN-"+i)!=null&&yiLoumap.get("EN-"+i)==0)
			        {
			        	oumap.put(i,oumap.get(i)+1);
			        }
			        if(yiLoumap.get("ON-"+i)!=0){
			        	sb.append("<td attr='1' area='1' bgcolor='#fcf5e1' class='font_red_n' >");
			        	sb.append(yiLoumap.get("ON-"+i)).append("</td>");
			        }else
			        {
			        	sb.append("<td attr='1' area='1' bgcolor='#fcf5e1' class='zs_redball_on' >");
			        	sb.append(i).append("</td>");
			        }
			        if(yiLoumap.get("EN-"+i)!=0){
			        	sb.append("<td attr='2'  area='2' bgcolor='#fcf5e1' class='font_red_n'  style='display: none;'>");
				   		sb.append(yiLoumap.get("EN-"+i)).append("</td>");
			        }else
			        {
			        	sb.append("<td attr='2'  area='2' bgcolor='#fcf5e1' class='zs_redball_on'  style='display: none;'>");
				   		sb.append(i).append("</td>");
			        }
			   }
			   for(int i=0;i<=5;i++){
				    if(yiLoumap.get("SN-"+i)!=null&&yiLoumap.get("SN-"+i)==0)
				        {
				            xiaomap.put(i,xiaomap.get(i)+1);
				        }
				        if(yiLoumap.get("BN-"+i)!=null&&yiLoumap.get("BN-"+i)==0)
				        {
				        	damap.put(i,damap.get(i)+1);
				        }
				        if(yiLoumap.get("SN-"+i)!=0){
				        	  sb.append("<td attr='3'  area='3' bgcolor='#fff9eb' class='font_red_n' >").append(yiLoumap.get("SN-"+i)).append("</td>");
				        }else
				        {
				        	sb.append("<td attr='3'  area='3' bgcolor='#fcf5e1' class='zs_redball_on' >");
					   		sb.append(i).append("</td>");
				        }
				        if(yiLoumap.get("BN-"+i)!=0){
				        	  sb.append("<td attr='4'  area='4' bgcolor='#fff9eb' class='font_red_n'  style='display: none;'>").append(yiLoumap.get("BN-"+i)).append("</td>");
				        }else
				        {
				        	sb.append("<td attr='4'  area='4' bgcolor='#fcf5e1' class='zs_redball_on'  style='display: none;'>");
					   		sb.append(i).append("</td>");
				        }
			   }
			   for(int i=0;i<=5;i++){ 
			 		if(yiLoumap.get("PN-"+i)!=null&&yiLoumap.get("PN-"+i)==0)
			        {
			            zhimap.put(i,zhimap.get(i)+1);
			        }
			        if(yiLoumap.get("CN-"+i)!=null&&yiLoumap.get("CN-"+i)==0)
			        {
			        	hemap.put(i,hemap.get(i)+1);
			        }
			        if(yiLoumap.get("PN-"+i)!=0){
			        	sb.append("<td attr='5' area='5' bgcolor='#fcf5e1' class='font_red_n' >");
				        sb.append(yiLoumap.get("PN-"+i)).append("</td>");
				      }else
			        {
			        	sb.append("<td attr='5'  area='5' bgcolor='#fcf5e1' class='zs_redball_on' > ");
				   		sb.append(i).append("</td>");
			        }
			        if(yiLoumap.get("CN-"+i)!=0){
			        	sb.append("<td attr='6' area='6' bgcolor='#fcf5e1' class='font_red_n' style='display: none;'>");
				        sb.append(yiLoumap.get("CN-"+i)).append("</td>");
				       }else
			        {
			        	sb.append("<td attr='6'  area='6' bgcolor='#fcf5e1' class='zs_redball_on'  style='display: none;'>");
				   		sb.append(i).append("</td>");
			        }
			}
	    	sb.append("</tr>");
		}
	    sb.append("<tr><td width='98' colspan='2' bgcolor='#FFFFFF'>出现总次数</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get(""+i)).append("</td>");
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='1'>").append(jimap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='2' style='display: none;'>").append(oumap.get(i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='3'>").append(xiaomap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='4' style='display: none;'>").append(damap.get(i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n' attr='5'>").append(zhimap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n' attr='6' style='display: none;'>").append(hemap.get(i)).append("</td>");
    	}
    	sb.append("</tr><tr><td width='98' colspan='2' bgcolor='#FFFFFF'>平均遗漏值</td>");
    	for(int i=1;i<=11;i++)
    		sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get(i+"")==0?list.size():list.size()/allcount.get(i+"")).append("</td>");
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='1'>").append(jimap.get(i)==0?list.size():list.size()/jimap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='2' style='display: none;'>").append(oumap.get(i)==0?list.size():list.size()/oumap.get(i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='3'>").append(xiaomap.get(i)==0?list.size():list.size()/xiaomap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='4' style='display: none;'>").append(damap.get(i)==0?list.size():list.size()/damap.get(i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n' attr='5'>").append(zhimap.get(i)==0?list.size():list.size()/zhimap.get(i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n' attr='6' style='display: none;'>").append(hemap.get(i)==0?list.size():list.size()/hemap.get(i)).append("</td>");
    	}
    	sb.append("</tr> <tr><td width='98' colspan='2' bgcolor='#FFFFFF'>最大遗漏值</td>");
    	for(int i=1;i<=11;i++){
    		sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>").append(maxMap.get("B-"+df.format(i))).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='1'>").append(maxMap.get("ON-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='2' style='display: none;'>").append(maxMap.get("EN-"+i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='3'>").append(maxMap.get("SN-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='4' style='display: none;'>").append(maxMap.get("BN-"+i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='5'>").append(maxMap.get("PN-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='6' style='display: none;'>").append(maxMap.get("CN-"+i)).append("</td>");
    	}
    	sb.append("</tr><tr><td width='98' colspan='2' bgcolor='#FFFFFF'>最大连出值</td>");
    	for(int i=1;i<=11;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("B-"+df.format(i)));
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='1'>").append(maxlcMap.get("ON-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='2' style='display: none;'>").append(maxlcMap.get("EN-"+i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='3'>").append(maxlcMap.get("SN-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='4' style='display: none;'>").append(maxlcMap.get("BN-"+i)).append("</td>");
    	}
    	for(int i=0;i<=5;i++)
    	{
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='5'>").append(maxlcMap.get("PN-"+i)).append("</td>");
    		sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n' attr='6' style='display: none;'>").append(maxlcMap.get("CN-"+i)).append("</td>");
    	}
    	sb.append("</tr><tr><td width='98' colspan='2' bgcolor='#FFFFFF'>当期遗漏值</td>");
    	for(int i=1;i<=11;i++ )
    	{
    		sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjyl.get("B-"+df.format(i))).append("<br/>");
    		sb.append("<img src='images/zs01.gif' width='8' height='").append(zjyl.get("B-"+df.format(i))).append("' /></td>");
    	}
    	for(int i=0;i<=5;i++)
    	 sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n'> </td>");
    	for(int i=0;i<=5;i++)
       	 sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n'> </td>");
    	for(int i=0;i<=5;i++)
       	 sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n'> </td>");
    	sb.append("</tr><tr><td width='90' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
    	sb.append("<td width='70' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
    	for(int i=1;i<=11;i++)
    		if(i<10)
    			sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
    		else
    			sb.append("<td width='28' height='28' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
    	for(int i=0;i<=5;i++)
       	 sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n'>"+i+"</td>");
       	for(int i=0;i<=5;i++)
          	 sb.append("<td width='28' height='28' bgcolor='#fff9eb' class='font_red_n'>"+i+"</td>");
       	for(int i=0;i<=5;i++)
          	 sb.append("<td width='28' height='28' bgcolor='#fcf5e1' class='font_red_n'>"+i+"</td>");
       	sb.append("</tr><tr><td colspan='11' bgcolor='#fcf3f0'>开奖号码分布图</td>");
       	sb.append("<td colspan='6' bgcolor='#fcf5e1' attr='1'>奇数个数遗漏（<a href=\"javascript:changeJO('ji','ou')\">偶数</a>）</td>");
       	sb.append("<td colspan='6' bgcolor='#fcf5e1' attr='2' style='display: none;'>偶数个数遗漏（<a href=\"javascript:changeJO('ou','ji')\">奇数</a>）</td>");
       	sb.append("<td colspan='6' bgcolor='#fff9eb' attr='3'>小数个数遗漏（<a href=\"javascript:changeDX('xiao','da')\">大数</a>）</td>");
       	sb.append("<td colspan='6' bgcolor='#fff9eb' attr='4' style='display:none;'>大数个数遗漏（<a href=\"javascript:changeDX('da','xiao')\">小数</a>）</td>");
       	sb.append("<td colspan='6' bgcolor='#fcf5e1' attr='5'>质数数个数遗漏（<a href=\"javascript:changeZH('zhi','he')\">合数</a>）</td>");
       	sb.append("<td colspan='6' bgcolor='#fcf5e1' attr='6' style='display:none;'>合数数个数遗漏（<a href=\"javascript:changeZH('he','zhi')\">质数</a>）</td>");
       	sb.append("</tr></table>");
       	sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dlc_qe_zhifileSource(List<LotteryTermModel> list,
			String name, String enc, String url) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<title>走势分析-掌上赢家-彩票-体育彩票-足球彩票</title>");
		sb.append("<script src='../js/utils/jquery.min.js'></script>");
		sb.append("<script src='../js/utils/jquery.alerts.js'></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/dlc_select.js\"></script>");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("</head>");
		sb.append("<body>");
		//===========================头开始
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//=================================内容开始
		sb.append("<div class='zs_blue'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>选择期数</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='dlc_qe_zhi_1.html'>最近一天</option>");
		sb.append("<option value='dlc_qe_zhi_2.html'>最近二天</option>");
		sb.append("<option value='dlc_qe_zhi_3.html'>最近三天</option>");
		sb.append("<option value='dlc_qe_zhi_5.html'>最近五天</option></select>");
		sb.append("</div>");
		sb.append("<div class='zs_qishu_zl'><ul>");
		sb.append("	<li class='zs_qishu_zl_on'><a href='dlc_rx_1.html'>任 选 走 势</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zhi_1.html'>前 二 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zu_1.html'>前 二 组 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zhi_1.html'>前 三 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zu_1.html'>前 三 组 选</a></li>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='zs_trend'>");
		sb.append("<div class='zs_trend_title'>");
		sb.append("多乐彩前二直选走势</div>");
		  Map<String,Integer> maxlcMap=new HashMap<String,Integer>();//最大连出值
   	   LotteryTermModel model=list.get(list.size()-1);
   	   Map<String,Integer> zjmap=model.getLotteryInfo().getMissCountResult(); 
   	   Map<String,Integer> maxmap=new HashMap<String,Integer>();
   	   DecimalFormat df = new DecimalFormat("00");
   	   int [] z1arr={0,0,0,0,0,0,0,0,0,0,0};
   	   int [] z2arr={0,0,0,0,0,0,0,0,0,0,0};
   	   Map<String,Integer> allcount=new HashMap<String,Integer>();
						for(int i=1;i<=11;i++){
						  	allcount.put("Z1-"+i,0);
						  	allcount.put("Z2-"+i,0);
						  	maxmap.put("Z1-"+i,0);
						  	maxmap.put("Z2-"+i,0);
						  	maxlcMap.put("Z1-"+i,0);
						  	maxlcMap.put("Z2-"+i,0);
						  	} 
			for(int i=0;i<list.size();i++){
			     LotteryTermModel m=list.get(i);
				  for(int k=1;k<=11;k++){
				  		if(m.getLotteryResult().substring(0,2).equals(df.format(k)))
				  				allcount.put("Z1-"+k,allcount.get("Z1-"+k)+1);
				  		if(m.getLotteryResult().substring(2,4).equals(df.format(k)))
				  				allcount.put("Z2-"+k,allcount.get("Z2-"+k)+1);
				  		if(m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))>maxmap.get("Z1-"+k))
				  				maxmap.put("Z1-"+k,m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))+1);
				  		if(m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))>maxmap.get("Z2-"+k))
				  				maxmap.put("Z2-"+k,m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))+1);
				 		if(m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))==0)
   		   				z1arr[k-1]=z1arr[k-1]+1;
   		  	 		else{
   		   				if(maxlcMap.get("Z1-"+k)<z1arr[k-1])
   		       				maxlcMap.put("Z1-"+k,z1arr[k-1]);
   		       			z1arr[k-1]=0;
   		     		}
   		   
				 		if(m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))==0)
   		   				z2arr[k-1]=z2arr[k-1]+1;
   		  	 		else{
   		   				if(maxlcMap.get("Z2-"+k)<z2arr[k-1])
   		       				maxlcMap.put("Z2-"+k,z2arr[k-1]);
   		       			z2arr[k-1]=0;
   		     		}
				 
				 }
			}
			sb.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
			sb.append("<tr>");
			sb.append("<td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
			sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
			sb.append("<td colspan='11' bgcolor='#fcf3f0'>第一位</td>");
			sb.append("<td colspan='11' bgcolor='#fcf3f0'>第二位</td>");
			sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
			sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
			sb.append("</tr><tr>");
			for(int k=0;k<=1;k++)
				for(int i=1;i<=11;i++)
					if(i<10)
						sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
					else
						sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
			sb.append("</tr>");
			for (LotteryTermModel term :list) {
				String lotteryResult=term.getLotteryResult();
		  		int ji=0;
		  		int ou=0;
		  		int da=0;
		  		int xiao=0;
		  		if(Integer.parseInt(lotteryResult.substring(0,2))%2!=0)
		  		   ji++;
		  		else
		  		   ou++;
		  		if(Integer.parseInt(lotteryResult.substring(2,4))%2!=0)
		  		   ji++;
		  		else
		  		   ou++;
		  		if(Integer.parseInt(lotteryResult.substring(0,2))>4)
		  		   da++;
		  		else 
		  		   xiao++;
		  		if(Integer.parseInt(lotteryResult.substring(2,4))>4)
		  		   da++;
		  		else
		  		   xiao++;
		  		sb.append("<tr>");
		  		sb.append("<td width='70' bgcolor='#FFFFFF' >").append(term.getTermNo()).append("</td>");
		  		sb.append("<td width='70' bgcolor='#FFFFFF' class='font_red_n'>");
				sb.append(lotteryResult.substring(0,2)+",");
				sb.append(lotteryResult.substring(2,4)+"</td>");
				LotteryInfo lotteryInfo =term.getLotteryInfo();
			    Map<String, Integer> yiLoumap=lotteryInfo.getMissCountResult();
			    for(int i=1;i<=11;i++){
			    	sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='");
			    	sb.append(yiLoumap.get("Z1-"+df.format(i))==0?"zs_redball_on":"font_red_n");
			    	sb.append("'>");
			    	sb.append(yiLoumap.get("Z1-"+df.format(i))==0?df.format(i):yiLoumap.get("Z1-"+df.format(i)));
			    	sb.append("</td>");
			    }
			    for(int i=1;i<=11;i++){
			    	sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='");
			    	sb.append(yiLoumap.get("Z2-"+df.format(i))==0?"zs_redball_on":"font_red_n");
			    	sb.append("'>");
			    	sb.append(yiLoumap.get("Z2-"+df.format(i))==0?df.format(i):yiLoumap.get("Z2-"+df.format(i)));
			    	sb.append("</td>");
			    }
			    sb.append("<td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>&nbsp;&nbsp;");
			    sb.append(da+":"+xiao+"</td>");
			    sb.append("<td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>&nbsp;&nbsp;");
			    sb.append(ji+":"+ou+"</td>");
			    sb.append("</tr>");
			}
			sb.append("<tr>");
			sb.append("<td width='70' rowspan='1' bgcolor='#FFFFFF'>出现总数值</td>");
			sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
			for(int i=1;i<=11;i++)
				sb.append(" <td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Z1-"+i)).append("</td>");
			for(int i=1;i<=11;i++)
				sb.append(" <td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Z2-"+i)).append("</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("</tr><tr>");
			sb.append("<td width='70' rowspan='1' bgcolor='#FFFFFF'>平均遗漏值</td>");
			sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
			for(int i=1;i<=11;i++)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Z1-"+i)).append("</td>");
			for(int i=1;i<=11;i++)	
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Z2-"+i)).append("</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
			sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大遗漏值</td>");
			sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
			for(int i=1;i<=11;i++)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Z1-"+i)).append("</td>");
			for(int i=1;i<=11;i++)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Z2-"+i)).append("</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
			sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大连出值</td>");
			sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
			for(int i=1;i<=11;i++)
				sb.append(" <td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Z1-"+i)).append("</td>");
			for(int i=1;i<=11;i++)
				sb.append(" <td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Z2-"+i)).append("</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
			sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最近遗漏</td>");
			sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
			for(int i=1;i<=11;i++){
				sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjmap.get("Z1-"+df.format(i))).append("<br />");
				sb.append("<img src='../images/zs01.gif' width='8' height='").append(zjmap.get("Z1-"+df.format(i))).append("' ></td>");
			}
			for(int i=1;i<=11;i++){
				sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjmap.get("Z2-"+df.format(i))).append("<br />");
				sb.append("<img src='../images/zs01.gif' width='8' height='").append(zjmap.get("Z2-"+df.format(i))).append("' ></td>");
			}
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
			sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td><td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
			
			for(int k=0;k<=1;k++)
				for(int i=1;i<=11;i++)
					if(i<10)
						sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
					else
						sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'></td>");
			sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'></td>");
			sb.append("</tr>");
			sb.append("<tr><td colspan='11' bgcolor='#fcf3f0'>第一位</td><td colspan='11' bgcolor='#fcf3f0'>第二位</td>");
			sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td><td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
			sb.append("</tr></table>");
		//=================结束
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dlc_qs_zhifileSource(List<LotteryTermModel> list,
			String name, String enc, String url) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<title>走势分析-掌上赢家-彩票-体育彩票-足球彩票</title>");
		sb.append("<script src='../js/utils/jquery.min.js'></script>");
		sb.append("<script src='../js/utils/jquery.alerts.js'></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/dlc_select.js\"></script>");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("</head>");
		sb.append("<body>");
		//===========================头开始
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//=================================内容开始
		sb.append("<div class='zs_blue'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>按天数：</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='dlc_qs_zhi_1.html'>最近一天</option>");
		sb.append("<option value='dlc_qs_zhi_2.html'>最近二天</option>");
		sb.append("<option value='dlc_qs_zhi_3.html'>最近三天</option>");
		sb.append("<option value='dlc_qs_zhi_5.html'>最近五天</option></select>");
		sb.append("</div>");
		sb.append("<div class='zs_qishu_zl'><ul>");
		sb.append("	<li class='zs_qishu_zl_on'><a href='dlc_rx_1.html'>任 选 走 势</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zhi_1.html'>前 二 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zu_1.html'>前 二 组 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zhi_1.html'>前 三 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zu_1.html'>前 三 组 选</a></li>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='zs_trend'>");
		sb.append("<div class='zs_trend_title'>");
		sb.append("多乐彩前三直选走势</div>");
	   Map<String,Integer> maxlcMap=new HashMap<String,Integer>();//最大连出值
   	   LotteryTermModel model=list.get(list.size()-1);
   	   Map<String,Integer> zjmap=model.getLotteryInfo().getMissCountResult(); 
   	   Map<String,Integer> maxmap=new HashMap<String,Integer>();
   	   DecimalFormat df = new DecimalFormat("00");
   	   int [] z1arr={0,0,0,0,0,0,0,0,0,0,0};
   	   int [] z2arr={0,0,0,0,0,0,0,0,0,0,0};
   	   int [] z3arr={0,0,0,0,0,0,0,0,0,0,0};
   	   Map<String,Integer> allcount=new HashMap<String,Integer>();
	   for(int i=1;i<=11;i++){
			allcount.put("Z1-"+i,0);
			allcount.put("Z2-"+i,0);
			allcount.put("Z3-"+i,0);
			maxmap.put("Z1-"+i,0);
			maxmap.put("Z2-"+i,0);
			maxmap.put("Z3-"+i,0);
			maxlcMap.put("Z1-"+i,0);
			maxlcMap.put("Z2-"+i,0);
			maxlcMap.put("Z3-"+i,0);
		} 
	   for(int i=0;i<list.size();i++){
		     LotteryTermModel m=list.get(i);
			  for(int k=1;k<=11;k++){
			  		if(m.getLotteryResult().substring(0,2).equals(df.format(k)))
			  				allcount.put("Z1-"+k,allcount.get("Z1-"+k)+1);
			  		if(m.getLotteryResult().substring(2,4).equals(df.format(k)))
			  				allcount.put("Z2-"+k,allcount.get("Z2-"+k)+1);
			  		if(m.getLotteryResult().substring(4,6).equals(df.format(k)))
			  				allcount.put("Z3-"+k,allcount.get("Z3-"+k)+1);
			  		if(m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))>maxmap.get("Z1-"+k))
			  				maxmap.put("Z1-"+k,m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))+1);
			  		if(m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))>maxmap.get("Z2-"+k))
			  				maxmap.put("Z2-"+k,m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))+1);
			  		if(m.getLotteryInfo().getMissCountResult().get("Z3-"+df.format(k))>maxmap.get("Z3-"+k))
			  				maxmap.put("Z3-"+k,m.getLotteryInfo().getMissCountResult().get("Z3-"+df.format(k))+1);
			 		if(m.getLotteryInfo().getMissCountResult().get("Z1-"+df.format(k))==0)
		   				z1arr[k-1]=z1arr[k-1]+1;
		  	 		else{
		   				if(maxlcMap.get("Z1-"+k)<z1arr[k-1])
		       				maxlcMap.put("Z1-"+k,z1arr[k-1]);
		       			z1arr[k-1]=0;
		     		}
		   
			 		if(m.getLotteryInfo().getMissCountResult().get("Z2-"+df.format(k))==0)
		   				z2arr[k-1]=z2arr[k-1]+1;
		  	 		else{
		   				if(maxlcMap.get("Z2-"+k)<z2arr[k-1])
		       				maxlcMap.put("Z2-"+k,z2arr[k-1]);
		       			z2arr[k-1]=0;
		     		}
		     		if(m.getLotteryInfo().getMissCountResult().get("Z3-"+df.format(k))==0)
		   				z3arr[k-1]=z3arr[k-1]+1;
		  	 		else{
		   				if(maxlcMap.get("Z3-"+k)<z3arr[k-1])
		       				maxlcMap.put("Z3-"+k,z3arr[k-1]);
		       			z3arr[k-1]=0;
		     		}
			 }
		}
	   	sb.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("<td colspan='11' bgcolor='#fcf3f0'>第一位</td><td colspan='11' bgcolor='#fcf3f0'>第二位</td>");
		sb.append("<td colspan='11' bgcolor='#fcf3f0'>第三位</td><td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td></tr>");
		sb.append("<tr>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("</tr>");
		for(LotteryTermModel modle:list)
		{
			 String lotteryResult=modle.getLotteryResult(); 
	  		   int ji=0;
	  		   int ou=0;
	  		   int da=0;
	  		   int xiao=0;
	  		   if(Integer.parseInt(lotteryResult.substring(0,2))%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  			if(Integer.parseInt(lotteryResult.substring(2,4))%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		      if(Integer.parseInt(lotteryResult.substring(4,6))%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		   if(Integer.parseInt(lotteryResult.substring(0,2))>4)
	  		      da++;
	  		   else 
	  		      xiao++;
	  		   if(Integer.parseInt(lotteryResult.substring(2,4))>4)
	  		      da++;
	  		   else
	  		      xiao++;
	  		      if(Integer.parseInt(lotteryResult.substring(4,6))>4)
	  		      da++;
	  		   else
	  		      xiao++;
	  		 sb.append("<tr>");
	  		 sb.append("<td width='70' bgcolor='#FFFFFF' >").append(modle.getTermNo()).append("</td>");
	  		 sb.append("<td width='50' bgcolor='#FFFFFF' class='font_red_n'>").append(lotteryResult.substring(0,2));
	  		 sb.append(",").append(lotteryResult.substring(2,4)).append("</td>");
	  		LotteryInfo lotteryInfo =modle.getLotteryInfo();
		    Map<String, Integer> yiLoumap=lotteryInfo.getMissCountResult();
		    for(int i=1;i<=11;i++){
		    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='");
		    	sb.append(yiLoumap.get("Z1-"+df.format(i))==0?"zs_redball_on":"font_red_n");
		    	sb.append("'>").append(yiLoumap.get("Z1-"+df.format(i))==0?df.format(i):yiLoumap.get("Z1-"+df.format(i))).append("</td>");
		    }
		    for(int i=1;i<=11;i++){
		    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='");
		    	sb.append(yiLoumap.get("Z2-"+df.format(i))==0?"zs_redball_on":"font_red_n");
		    	sb.append("'>").append(yiLoumap.get("Z2-"+df.format(i))==0?df.format(i):yiLoumap.get("Z2-"+df.format(i))).append("</td>");
		    }
		    for(int i=1;i<=11;i++){
		    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='");
		    	sb.append(yiLoumap.get("Z3-"+df.format(i))==0?"zs_redball_on":"font_red_n");
		    	sb.append("'>").append(yiLoumap.get("Z3-"+df.format(i))==0?df.format(i):yiLoumap.get("Z3-"+df.format(i))).append("</td>");
		    }
		    sb.append(" <td width='18' height='18' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>&nbsp;&nbsp;");
		    sb.append(da+":"+xiao).append("</td>");
		    sb.append(" <td width='18' height='18' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>&nbsp;&nbsp;");
		    sb.append(ji+":"+ou).append("</td></tr>");
		}
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>出现总数值</td><td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Z1-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Z2-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Z3-"+i)).append("</td>");
		
		sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td> </tr>");
	    sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>平均遗漏值</td>");
	    sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Z1-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Z2-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Z3-"+i)).append("</td>");
	    
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
	    sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大遗漏值</td>");
	    sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Z1-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Z2-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Z3-"+i)).append("</td>");
	    
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
	    sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大连出值</td>");
	    sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Z1-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Z2-"+i)).append("</td>");
	    for(int i=1;i<=11;i++)
	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Z3-"+i)).append("</td>");
	    
	    
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
	    sb.append("");
	    
	    sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最近遗漏</td>");
	    sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
	    for(int i=1;i<=11;i++){
	    	sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjmap.get("Z1-"+df.format(i)));
	    	sb.append("<br /> <img src='../images/zs01.gif' width='8' height='");
	    	sb.append(zjmap.get("Z1-"+df.format(i))).append("'></td>");
	    }
	    for(int i=1;i<=11;i++){
	    	sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjmap.get("Z2-"+df.format(i)));
	    	sb.append("<br /> <img src='../images/zs01.gif' width='8' height='");
	    	sb.append(zjmap.get("Z2-"+df.format(i))).append("'></td>");
	    }
	    for(int i=1;i<=11;i++){
	    	sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>").append(zjmap.get("Z3-"+df.format(i)));
	    	sb.append("<br /> <img src='../images/zs01.gif' width='8' height='");
	    	sb.append(zjmap.get("Z3-"+df.format(i))).append("'></td>");
	    }
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td></tr>");
	    sb.append("<tr><td width='54' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
	    sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
	    for(int k=0;k<=2;k++)
	    	for(int i=1;i<=11;i++)
	    	    if(i<10)	
	    	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
	    	    else
	    	    	sb.append("<td width='18' height='18' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'></td>");
	    sb.append("<td width='36' height='18' colspan='1'  bgcolor='#fff9eb' class='font_red_n'></td></tr>");
	    sb.append("<tr><td colspan='11' bgcolor='#fcf3f0'>第一位</td>");
	    sb.append("<td colspan='11' bgcolor='#fcf3f0'>第二位</td>");
	    sb.append("<td colspan='11' bgcolor='#fcf3f0'>第三位</td>");
	    sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
	    sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
	    sb.append("</tr>");
	    sb.append("</table>");
	    sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
	    flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dlc_qe_zufileSource(List<LotteryTermModel> list,
			String name, String enc, String url) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<title>走势分析-掌上赢家-彩票-体育彩票-足球彩票</title>");
		sb.append("<script src='../js/utils/jquery.min.js'></script>");
		sb.append("<script src='../js/utils/jquery.alerts.js'></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/dlc_select.js\"></script>");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("</head>");
		sb.append("<body>");
		//===========================头开始
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//=================================内容开始
		sb.append("<div class='zs_blue'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>按天数：</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='dlc_qe_zu_1.html'>最近一天</option>");
		sb.append("<option value='dlc_qe_zu_2.html'>最近二天</option>");
		sb.append("<option value='dlc_qe_zu_3.html'>最近三天</option>");
		sb.append("<option value='dlc_qe_zu_5.html'>最近五天</option></select>");
		sb.append("</div>");
		sb.append("<div class='zs_qishu_zl'><ul>");
		sb.append("	<li class='zs_qishu_zl_on'><a href='dlc_rx_1.html'>任 选 走 势</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zhi_1.html'>前 二 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zu_1.html'>前 二 组 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zhi_1.html'>前 三 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zu_1.html'>前 三 组 选</a></li>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='zs_trend'>");
		sb.append("<div class='zs_trend_title'>");
		sb.append("多乐彩前二组选走势</div>");
		sb.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		Map<String,Integer> maxlcMap=new HashMap<String,Integer>();//最大连出值
 	   LotteryTermModel model=list.get(list.size()-1);
 	   Map<String,Integer> zjmap=model.getLotteryInfo().getMissCountResult(); 
 	   Map<String,Integer> maxmap=new HashMap<String,Integer>();
 	   DecimalFormat df = new DecimalFormat("00");
 	   int [] zu2arr={0,0,0,0,0,0,0,0,0,0,0};
 	   Map<String,Integer> allcount=new HashMap<String,Integer>();
						for(int i=1;i<=11;i++){
						  	allcount.put("Q2-"+i,0);
						  	maxmap.put("Q2-"+i,0);
						  	maxlcMap.put("Q2-"+i,0);
						  	} 
			for(int i=0;i<list.size();i++){
			     LotteryTermModel m=list.get(i);
			       for(int k=1;k<=11;k++){
				  		if(m.getLotteryInfo().getMissCountResult().get("Q2-"+df.format(k))==0){
				  		     allcount.put("Q2-"+k,allcount.get("Q2-"+k)+1);
						 	 zu2arr[k-1]=zu2arr[k-1]+1;
						 }		   
				         else
						 {
						 		if(maxlcMap.get("Q2-"+k)<zu2arr[k-1])
						 	     	maxlcMap.put("Q2-"+k,zu2arr[k-1]);
						 	     	zu2arr[k-1]=0;
						 }  
						 if(maxmap.get("Q2-"+k)<m.getLotteryInfo().getMissCountResult().get("Q2-"+df.format(k)))
						 		maxmap.put("Q2-"+k,m.getLotteryInfo().getMissCountResult().get("Q2-"+df.format(k)));
				 }
				  
			}
		sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("<td colspan='11' bgcolor='#fcf3f0'>号码分布</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>质合比</td>");
		sb.append("</tr><tr>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("</tr>");
		for(LotteryTermModel m:list)
		{
			 String lotteryResult=m.getLotteryResult(); 
	  		   int ji=0;
	  		   int ou=0;
	  		   int da=0;
	  		   int xiao=0;
	  		   int zhi=0;
	  		   int he=0;
	  		   int fst=Integer.parseInt(lotteryResult.substring(0,2));
	  		   int sc=Integer.parseInt(lotteryResult.substring(2,4));
	  		   if(fst%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  			if(sc%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		   if(fst>4)
	  		      da++;
	  		   else 
	  		      xiao++;
	  		   if(sc>4)
	  		      da++;
	  		   else
	  		      xiao++;
	  		   if(fst==1||fst==2||fst==3||fst==5||fst==7||fst==11)
	  		      zhi++;
	  		    else
	  		      he++;
	  		    if(sc==1||sc==2||sc==3||sc==5||sc==7||sc==11)
	  		      zhi++;
	  		    else
	  		      he++;
	  		  sb.append("<tr>");
	  		  sb.append("<td width='70' bgcolor='#FFFFFF' >").append(m.getTermNo()).append("</td>");
	  		  sb.append("<td width='70' bgcolor='#FFFFFF' class='font_red_n'>");
	  		  sb.append(lotteryResult.substring(0,2)).append(",").append(lotteryResult.substring(2,4)).append("</td>");
	  		  LotteryInfo lotteryInfo =m.getLotteryInfo();
	  		  Map<String, Integer> yiLoumap=lotteryInfo.getMissCountResult();
	  		  for(int i=1;i<=11;i++){
	  			  sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='");
	  			  sb.append(yiLoumap.get("Q2-"+df.format(i))==0?"zs_redball_on":"font_red_n");
	  			  sb.append("'>").append(yiLoumap.get("Q2-"+df.format(i))==0?df.format(i):yiLoumap.get("Q2-"+df.format(i)));
	  			  sb.append("</td>");
	  		  }
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+da+":"+xiao).append("</td>");
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+ji+":"+ou).append("</td>");
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+zhi+":"+he).append("</td></tr>");
		}
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>出现总数值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Q2-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>平均遗漏值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Q2-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大遗漏值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Q2-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大连出值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Q2-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最近遗漏</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'>&nbsp;</td>");
		for(int i=1;i<=11;i++){
			sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>");
			sb.append(zjmap.get("Q2-"+df.format(i)));
			sb.append("<br />");
			sb.append("<img src='../images/zs01.gif' width='8' height='").append(zjmap.get("Q2-"+df.format(i)));
			sb.append("' /></td>");
		}
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td colspan='11' bgcolor='#fcf3f0'>号码分布</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>质合比</td>");
		sb.append("</tr></table>");
		sb.append("");
		sb.append("");
		sb.append("");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
	    flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}
	
	public boolean dlc_qs_zufileSource(List<LotteryTermModel> list,
			String name, String enc, String url) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb.append("<title>走势分析-掌上赢家-彩票-体育彩票-足球彩票</title>");
		sb.append("<script src='../js/utils/jquery.min.js'></script>");
		sb.append("<script src='../js/utils/jquery.alerts.js'></script>");
		sb.append("<script src=\"../js/head.js\"></script>");
		sb.append("<script src=\"../js/dlc_select.js\"></script>");
		sb
				.append("<link href='../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("</head>");
		sb.append("<body>");
		//===========================头开始
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../register.jhtml\"><img src=\"../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../gobill.jhtml\"><img src=\"../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../dltlotteryindex.jhtml\"><img src=\"../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../help/cjwt.html\"><img src=\"../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li class=\"menu_middle_on\" onclick=\"onclickMenu('../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li onclick=\"onclickMenu('../drawlottery/index.html') \">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../mobile.html');\">手机购彩</li></ul></div></div>");
//		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
//		sb.append("	<li><a href=\"../dltlotteryindex.jhtml\">大乐透</a></li>");
//		sb.append("<li><a href=\"../qxclotteryindex.jhtml\">七星彩</a></li>");
//		sb.append("<li><a href=\"../plslotteryindex.jhtml\">排列三</a></li>");
//		sb.append("<li><a href=\"../plwlotteryindex.jhtml\">排列五</a></li>");
//		sb.append("</div><div class=\"zucai\"><li><a href=\"../rsraceindex.jhtml\">胜负彩</a></li>");
//		sb.append("<li><a href=\"../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		
		//======================================替换购彩导航
		sb.append("<div><div class='menu_caizhong'><ul>");
		sb.append("<div class='ticai2'>");
		sb.append("<li><a href='../dltlotteryindex.jhtml'>大乐透</a></li>");
		sb.append("<li><a href='../qxclotteryindex.jhtml'>七星彩</a></li>");
		sb.append("<li><a href='../plslotteryindex.jhtml'>排列三</a></li>");
		sb.append("<li><a href='../plwlotteryindex.jhtml'>排列五</a></li>");
		sb.append("<li><a href='../rsraceindex.jhtml'>胜负彩</a></li>");
		sb.append("<li><a href='../rjraceindex.jhtml'>任选9场</a></li>");
		sb.append("<li><a href='../zc6raceindex.jhtml'>六场半全</a></li>");
		sb.append("<li><a href='../jq4raceindex.jhtml'>四场进球</a></li>");
		sb.append("<li><a href='../syxwlotteryindex.jhtml'>多乐彩</a></li>");
		sb.append("</div></ul></div></div><div class='menu_hr'><hr width='100%'/>");
		sb.append("</div>");
		//======================================
		sb.append("<iframe src=\"../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">走势分析</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li ><a href=\"dlt30.html\">大乐透</a></li>");
		sb.append("<li><a href=\"qxc30.html\">七星彩</a></li>");
		sb.append("<li><a href=\"pls30.html\">排列三</a></li>");
		sb.append("<li><a href=\"plw30.html\">排列五</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href=\"dlc_rx_1.html\">多乐彩</a></li>");
		sb.append(" </ul></div></div>");
		//=================================内容开始
		sb.append("<div class='zs_blue'><img src='images/blank.gif' width='1' height='1'></div>");
		sb.append("<div class='zs_qishu'><div class='zs_qishu_title'>按天数：</div>");
		sb.append("<div class='zs_qishu_list'>");
		sb.append("<select id='selectTermNum' onChange='change()'>");
		sb.append("<option value=''>请选择</option>");
		sb.append("<option value='dlc_qs_zu_1.html'>最近一天</option>");
		sb.append("<option value='dlc_qs_zu_2.html'>最近二天</option>");
		sb.append("<option value='dlc_qs_zu_3.html'>最近三天</option>");
		sb.append("<option value='dlc_qs_zu_5.html'>最近五天</option></select>");
		sb.append("</div>");
		sb.append("<div class='zs_qishu_zl'><ul>");
		sb.append("	<li class='zs_qishu_zl_on'><a href='dlc_rx_1.html'>任 选 走 势</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zhi_1.html'>前 二 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qe_zu_1.html'>前 二 组 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zhi_1.html'>前 三 直 选</a></li>");
		sb.append("<li class='zs_qishu_zl_on'>	<a href='dlc_qs_zu_1.html'>前 三 组 选</a></li>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("<div class='zs_trend'>");
		sb.append("<div class='zs_trend_title'>");
		sb.append("多乐彩前三组选走势</div>");
		sb.append("<table border='0' cellpadding='0' cellspacing='1' class='zs_table'>");
		Map<String,Integer> maxlcMap=new HashMap<String,Integer>();//最大连出值
 	   LotteryTermModel model=list.get(list.size()-1);
 	   Map<String,Integer> zjmap=model.getLotteryInfo().getMissCountResult(); 
 	   Map<String,Integer> maxmap=new HashMap<String,Integer>();
 	   DecimalFormat df = new DecimalFormat("00");
 	   int [] zu2arr={0,0,0,0,0,0,0,0,0,0,0};
 	   Map<String,Integer> allcount=new HashMap<String,Integer>();
						for(int i=1;i<=11;i++){
						  	allcount.put("Q3-"+i,0);
						  	maxmap.put("Q3-"+i,0);
						  	maxlcMap.put("Q3-"+i,0);
						  	} 
			for(int i=0;i<list.size();i++){
			     LotteryTermModel m=list.get(i);
			       for(int k=1;k<=11;k++){
				  		if(m.getLotteryInfo().getMissCountResult().get("Q3-"+df.format(k))==0){
				  		     allcount.put("Q3-"+k,allcount.get("Q3-"+k)+1);
						 	 zu2arr[k-1]=zu2arr[k-1]+1;
						 }		   
				         else
						 {
						 		if(maxlcMap.get("Q3-"+k)<zu2arr[k-1])
						 	     	maxlcMap.put("Q3-"+k,zu2arr[k-1]);
						 	     	zu2arr[k-1]=0;
						 }  
						 if(maxmap.get("Q3-"+k)<m.getLotteryInfo().getMissCountResult().get("Q3-"+df.format(k)))
						 		maxmap.put("Q3-"+k,m.getLotteryInfo().getMissCountResult().get("Q3-"+df.format(k)));
				 }
				  
			}
		sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		sb.append("<td colspan='11' bgcolor='#fcf3f0'>号码分布</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>质合比</td>");
		sb.append("</tr><tr>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("</tr>");
		for(LotteryTermModel m:list)
		{
			 String lotteryResult=m.getLotteryResult(); 
	  		   int ji=0;
	  		   int ou=0;
	  		   int da=0;
	  		   int xiao=0;
	  		   int zhi=0;
	  		   int he=0;
	  		   int fst=Integer.parseInt(lotteryResult.substring(0,2));
	  		   int sc=Integer.parseInt(lotteryResult.substring(2,4));
	  		   int thr=Integer.parseInt(lotteryResult.substring(4,6));
	  		   if(fst%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		   if(sc%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		   if(thr%2!=0)
	  		      ji++;
	  		   else
	  		      ou++;
	  		   if(fst>4)
	  		      da++;
	  		   else 
	  		      xiao++;
	  		   if(sc>4)
	  		      da++;
	  		   else
	  		      xiao++;
	  		   if(thr>4)
	  		      da++;
	  		   else
	  		      xiao++;
	  		   if(fst==1||fst==2||fst==3||fst==5||fst==7||fst==11)
	  		      zhi++;
	  		    else
	  		      he++;
	  		    if(sc==1||sc==2||sc==3||sc==5||sc==7||sc==11)
	  		      zhi++;
	  		    else
	  		      he++;
	  		    if(thr==1||thr==2||thr==3||thr==5||thr==7||thr==11)
	  		      zhi++;
	  		    else
	  		      he++;
	  		  sb.append("<tr>");
	  		  sb.append("<td width='70' bgcolor='#FFFFFF' >").append(m.getTermNo()).append("</td>");
	  		  sb.append("<td width='70' bgcolor='#FFFFFF' class='font_red_n'>");
	  		  sb.append(lotteryResult.substring(0,2)).append(",").append(lotteryResult.substring(2,4)).append(",").append(lotteryResult.substring(4,6)).append("</td>");
	  		  LotteryInfo lotteryInfo =m.getLotteryInfo();
	  		  Map<String, Integer> yiLoumap=lotteryInfo.getMissCountResult();
	  		  for(int i=1;i<=11;i++){
	  			  sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='");
	  			  sb.append(yiLoumap.get("Q3-"+df.format(i))==0?"zs_redball_on":"font_red_n");
	  			  sb.append("'>").append(yiLoumap.get("Q3-"+df.format(i))==0?df.format(i):yiLoumap.get("Q3-"+df.format(i)));
	  			  sb.append("</td>");
	  		  }
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+da+":"+xiao).append("</td>");
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+ji+":"+ou).append("</td>");
	  		  sb.append(" <td width='24' height='24' bgcolor='#fff9eb' class='font_red_n' style='text-align: center;'>");
	  		  sb.append("&nbsp;&nbsp"+zhi+":"+he).append("</td></tr>");
		}
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>出现总数值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(allcount.get("Q3-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>平均遗漏值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(list.size()/allcount.get("Q3-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大遗漏值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxmap.get("Q3-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最大连出值</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'></td>");
		for(int i=1;i<=11;i++)
			sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>").append(maxlcMap.get("Q3-"+i)).append("</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='1' bgcolor='#FFFFFF'>最近遗漏</td>");
		sb.append("<td width='50' rowspan='1' bgcolor='#FFFFFF'>&nbsp;</td>");
		for(int i=1;i<=11;i++){
			sb.append("<td height='50px' bgcolor='#FFFFFF' valign='bottom'>");
			sb.append(zjmap.get("Q3-"+df.format(i)));
			sb.append("<br />");
			sb.append("<img src='../images/zs01.gif' width='8' height='").append(zjmap.get("Q3-"+df.format(i)));
			sb.append("' /></td>");
		}
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td width='70' rowspan='2' bgcolor='#FFFFFF'>期号</td>");
		sb.append("<td width='50' rowspan='2' bgcolor='#FFFFFF'>开奖号码</td>");
		for(int i=1;i<=11;i++)
			if(i<10)
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>0"+i+"</td>");
			else
				sb.append("<td width='24' height='24' bgcolor='#fcf3f0' class='font_red_n'>"+i+"</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("<td width='46' height='24' colspan='1'  bgcolor='#fff9eb' class='font_red_n'>&nbsp;</td>");
		sb.append("</tr>");
		sb.append("<tr><td colspan='11' bgcolor='#fcf3f0'>号码分布</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>大小比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>奇偶比</td>");
		sb.append("<td colspan='1' rowspan='2' bgcolor='#fff9eb'>质合比</td>");
		sb.append("</tr></table>");
		sb.append("<iframe src='../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
	    flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}

	@Override
	public boolean dlclistfileSource(String name, String enc, String url,
			List<LotteryTermModel> list) throws LotteryException {
		boolean flg=false;
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//-------------------------头开始-------------------------------------------------
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\">");
		sb
				.append("<link href='../../css/base.css' rel='stylesheet' type='text/css'>");
		sb
				.append("<link href='../../css/zs.css' rel='stylesheet' type='text/css'>");
		sb.append("<script src=\"../../js/head.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.min.js\"></script>");
		sb.append("<script src=\"../../js/utils/jquery.alerts.js\"></script>");
		sb.append("</head>");
		sb.append("<body>");
		//-------------------------头开始-------------------------------------------------
		sb.append("<div class=\"header\"><div class=\"header_logo\">");
		sb.append("<div class=\"logo\"><img src=\"../../images/logo.gif\"  width=\"320\" height=\"70\"></div>");
		sb.append("<div class=\"logo_left\">");
		sb.append("<div class=\"logo_fav\"><img src=\"../../images/logo_1.gif\" onclick=\"SetHome(this)\"  width=\"75\" height=\"24\" style=\"cursor:pointer\"/><img src=\"../../images/logo_2.gif\" width=\"75\" onclick=\"AddFavorite()\"  height=\"24\" style=\"cursor:pointer\" /></div>");
		sb.append("<div class=\"logo_menu\"><a href=\"../../register.jhtml\"><img src=\"../../images/logo_3.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a><a href=\"../../gobill.jhtml\"><img src=\"../../images/logo_4.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../dltlotteryindex.jhtml\"><img src=\"../../images/logo_5.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a>");
		sb.append("<a href=\"../../help/cjwt.html\"><img src=\"../../images/logo_6.gif\" width=\"73\" height=\"19\" style=\"cursor:pointer\" /></a></div>");
		sb.append("</div></div>");
		sb.append("<div class=\"header_menu\"><div class=\"menu_middle\"><ul id=\"menu\" name=\"menu\" class=\"menu\">");
		sb.append("<li  onclick=\"onclickMenu('../../home.jhtml');\">首页</li>");
		sb.append("<li onclick=\"onclickMenu('../../dltlotteryindex.jhtml');\">购彩大厅</li>");
		sb.append("<li onclick=\"onclickMenu('../../userindex.jhtml');\">帐户管理</li>	");
		sb.append("<li onclick=\"onclickMenu('../../direction/dlt30.html');\" >走势分析</li>");
		sb.append("<li  class=\"menu_middle_on\">开奖查询</li>");
		sb.append("<li onclick=\"onclickMenu('../../newsindex/xinwengonggao/list_1.html');\">彩票新闻</li>	");
		sb.append("<li onclick=\"onclickMenu('../../mobile.html');\">手机购彩</li></ul></div></div>");
		sb.append("<div class=\"header_caizhong\"><div class=\"menu_caizhong\"><ul><div class=\"ticai\">");
		sb.append("	<li><a href=\"../../dltlotteryindex.jhtml\">大乐透</a></li>");
		sb.append("<li><a href=\"../../qxclotteryindex.jhtml\">七星彩</a></li>");
		sb.append("<li><a href=\"../../plslotteryindex.jhtml\">排列三</a></li>");
		sb.append("<li><a href=\"../../plwlotteryindex.jhtml\">排列五</a></li>");
		sb.append("</div><div class=\"zucai\"><li><a href=\"../../rsraceindex.jhtml\">胜负彩</a></li>");
		sb.append("<li><a href=\"../../rjraceindex.jhtml\">任选9场</a></li></div></ul></div></div>");
		sb.append("<iframe src=\"../../checkLogin.jhtml\"  height=\"35\" width=\"960\" scrolling=\"no\" style=\"border: 0\" frameborder=\"0\"></iframe></div>");
		sb.append("<div class=\"zs_bar\"><div class=\"zs_bar_logo\">");
		sb.append("<img src=\"../../images/menu_kj_bar_logo.gif\" width=\"40\" height=\"29\" /></div>");
		sb.append("<div class=\"zs_bar_title\">开奖查询</div><div class=\"zs_bar_caizhong\"><ul>");
		sb.append("<li><a href=\"../dlt/dltlist.html\">大乐透</a></li>");
		sb.append("<li ><a href=\"../qxc/qxclist.html\">七星彩</a></li>");
		sb.append("<li ><a href=\"../pls/plslist.html\">排列三</a></li>");
		sb.append("<li><a href=\"../plw/plwlist.html\" >排列五</a></li>");
		sb.append("<li ><a href=\"../zc/zclist.html\" >胜负彩</a></li>");
		sb.append("<li><a href='../zc6/zc6list.html'>六场半全</a></li>");
		sb.append("<li><a href='../jq4/jq4list.html'>四场进球</a></li>");
		sb.append("<li  class=\"zs_bar_caizhong_on\"><a href='../dlc/dlclist.html'>多乐彩</a></li></ul></div></div>");
		//----------头结束-----------------
		sb.append("<div class='kj_tishi'><div class='kj_list_title'>开奖查询->多乐彩</div>");
		sb.append("<form ><div class='kj_list_chaxun'>");
		sb.append("<iframe src='../dlchtml/select.html' scrolling='no' style=\"border: 0\" frameborder=\"0\" height=\"22\" height=\"22\"></iframe>");
		sb.append("<input type='hidden' name='type' value='1000001'></form></div>");
		sb.append("<table border='0' cellspacing='1' class='kj_caizhong_table'>");
		sb.append("<tr class='kj_caizhong_title'><td>期号</td><td>开奖号码</td>");
		sb.append("<td>开奖时间</td></tr>");
		if(list!=null&&list.size()>0)
			for (LotteryTermModel modle : list) {
				sb.append("<tr class='kj_table_base'>");
				sb.append("<td class='kj_table_caizhong'>").append(modle.getTermNo()).append("</td>");
				sb.append("<td><div class='kj_ballbox'><ul><li class='kj_redball'>");
				sb.append(modle.getLotteryResult().substring(0, 2)).append("</li>");
				sb.append("<li class='kj_redball'>");
				sb.append(modle.getLotteryResult().substring(2,4)).append("</li>");
				sb.append("<li class='kj_redball'>");
				sb.append(modle.getLotteryResult().substring(4,6)).append("</li>");
				sb.append("<li class='kj_redball'>");
				sb.append(modle.getLotteryResult().substring(6,8)).append("</li>");
				sb.append("<li class='kj_redball'>");
				sb.append(modle.getLotteryResult().substring(8,10)).append("</li>");
				sb.append("</ul></div></td>");
				System.err.println(modle.getWinLine2());
				if(modle.getWinLine2()!=null)
					sb.append("<td>").append(sf.format(modle.getWinLine2())).append("</td>");
				else
					sb.append("<td>").append("").append("</td>");
				sb.append("</tr>");
			}
		sb.append("</table>");
		sb.append("<iframe src='../../foot.jsp'  height='90' width='960' scrolling='no' style='border: 0;' frameborder=\"0\"></iframe>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		flg = FileUtils.writeStringToFile(url, name, sb.toString(), enc);
		return flg;
	}
}
