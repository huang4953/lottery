/**
 * Title: LotteryAbstractTool.java
 * @Package com.success.lottery.util
 * Description: TODO(��һ�仰�������ļ���ʲô)
 * @author gaoboqin
 * @date 2010-3-26 ����01:13:07
 * @version V1.0
 */
package com.success.lottery.util.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * com.success.lottery.util.core
 * LotteryAbstractTool.java
 * LotteryAbstractTool
 * ���ֳ����࣬����һЩ���ֵ�Ͷע��ʽ��У������Լ�һЩ���Թ��õķ���ʵ��
 * @author gaoboqin
 * 2010-3-26 ����01:13:07
 *
 */
public abstract class LotteryAbstractTool {
	
	/**
	 * ��������͸
	 */
	@SuppressWarnings("unused")
	////////////////////////////////////////////////////////////////////////////////////
	protected static final String SUPER_PLATTYPE_ADDITIONAL = "1";//׷��
	protected static final String SUPER_PLATTYPE_NO_ADDITIONAL = "0";//��׷��
	protected static final String SUPER_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String SUPER_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String SUPER_BETTYPE_DANTUO = "2";//����
	protected static final String SUPER_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	/**
	 * 0-��ʽ
	 * ��1-35����ѡ5�����ظ��ĺ�����Ϊǰ����1-12����ѡ2�����ظ��ĺ�����Ϊ�������룬1-9ǰ����0���롣
	 * �ظ������������ķ����ж�
	 */
	protected static final String SUPER_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|3[0-5]){5}\\|(0[1-9]|1[0-2]){2}$";//0-��ʽ
	/**
	 * 1-��ʽ
	 * ��1-35����ѡM�����ظ��ĺ�����Ϊǰ����1-12����ѡN�����ظ��ĺ�����Ϊ�������룬�����㣨M>=6��N=2�����ߣ�M=5��N>=3������(M>=6��N>=3)ʱ��Ϊ��ʽ
	 * 
	 * �����
	 * ��1-35����ѡM�����ظ��ĺ�����Ϊǰ����1-12����ѡN�����ظ��ĺ�����Ϊ�������룬M>=5����N>=2��λһ�����룬
	 * ������λ��'0'��ǰ���ͺ���ʹ��"|"�ָ�����ע֮����"^"�ָ�010203040523|060711^08091011122425|080901
	 * 
	 */
	//protected static final String SUPER_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-5]){6,}\\|(0[1-9]|1[0-2]){2}|(0[1-9]|1\\d|2\\d|3[0-5]){5}\\|(0[1-9]|1[0-2]){3,}|(0[1-9]|1\\d|2\\d|3[0-5]){6,}\\|(0[1-9]|1[0-2]){3,})$";//1-��ʽ
	protected static final String SUPER_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-5]){5,}\\|(0[1-9]|1[0-2]){2,})$";//1-��ʽ
	/**
	 * ��1-35��ѡ��M��0<M<=4����������Ϊ���룬ѡ��N��N+M>=6��N>=2�������ظ��Ҳ����������������Ϊ�������ǰ��Ͷע��
	 * ��1-12��ѡ��M��0<M<=1����������Ϊ���룬ѡ��N��N+M>=3��N>=2�������ظ��Ҳ����������������Ϊ������ɺ���Ͷע��
	 * ��λһ�����룬������λ��'0'��ǰ���ͺ���ʹ��"|"�ָ������������֮��ʹ��"*"�ָ���������ǰ
	 * ������ظ��Լ�������Ҫ����У��
	 * 
	 * �����
	 *  ǰ������M(1<=M<=4)������N(N>=1 and M+N>=5)�����ҵ������벻���ظ���
	 *  ��������M(M>=0 and M<=1)������N(N>=1 and M+N>=2)��λһ�����룬
	 *  ������λ��'0'��ǰ���ͺ���ʹ��"|"�ָ�����������֮����*�ָ��������������Ϊ0�����������λ�ò��������ǰ�����ں�
	 *  ��ע֮����"^"�ָ�����ͨ���ϣ�01020*3040506|01*0203^01020304*05|01*02������M=0ʱ��01020*3040506|*020304
	 * 
	 */
	//protected static final String SUPER_BET_2_REGULAR = "^(0[1-9]|1\\d|2\\d|3[0-5]){1,4}\\*(0[1-9]|1\\d|2\\d|3[0-5]){2,}\\|(0[1-9]|1[0-2]){1}\\*(0[1-9]|1[0-2]){2,}$";//2-����
	protected static final String SUPER_BET_2_REGULAR = "^(0[1-9]|1\\d|2\\d|3[0-5]){1,4}\\*(0[1-9]|1\\d|2\\d|3[0-5]){1,}\\|(0[1-9]|1[0-2]){0,1}\\*(0[1-9]|1[0-2]){1,}$";//2-����
	
    ////////////////////////////////////////////////////////////////////////////////////
	//��Ф��
	protected static final String HAPPY_PLAYTYPE_NO_ADDITIONAL = "0";//��׷��
	protected static final String HAPPY_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String HAPPY_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String HAPPY_BETTYPE_DANTUO = "2";//����
	protected static final String HAPPY_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * ��1-12��12������������ѡ��2��������Ͷע����Ʊ���5עֱѡ���롣����1-9ǰ����0����
	 * ��λһ�����룬������λ��'0'��������5ע��֮����"^"�ָ�
	 * ��Ʊ���5ע�������У��
	 */
	protected static final String HAPPY_BET_0_REGULAR = "^(0[1-9]|1[0-2]){2}$";//0-��ʽ
	/**
	 * ��1-12��12������������ѡ��2��������Ͷע������1-9ǰ����0���롣��ѡ�ĺ���������2��������������Ϊ��ע����
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 * 
	 * �����
	 * ��1-12��12������������ѡ��M(M>=2)��������Ͷע��λһ�����룬������λ��'0'��ǰ���ͺ���ʹ��"|"�ָ�����ע֮����"^"�ָ�01020304^0809
	 * 
	 */
	protected static final String HAPPY_BET_1_REGULAR = "^(0[1-9]|1[0-2]){2,}$";//1-��ʽ
	/**
	 * ��1-12��12������������ѡ��2��������Ͷע����1-12��ѡ��M��0<M<=1����������Ϊ���룬ѡ��N��N+M>=3��N>=2�������ظ��Ҳ����������������Ϊ���롣
	 * ��λһ�����룬������λ��'1'�����������֮��ʹ��"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�  01*0203^03*0506
	 * 
	 */
	protected static final String HAPPY_BET_2_REGULAR = "^(0[1-9]|1[0-2]){1}\\*(0[1-9]|1[0-2]){2,}$";//2-����
	
	/////////////////////////////////////////////////////////////////////////////////////
	//���ǲ�
	protected static final String COLOR_PLAYTYPE_NO_ADDITIONAL = "0";//��׷��
	protected static final String COLOR_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String COLOR_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String COLOR_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * ��0-9��10������������ѡ��7��������Ͷע����Ʊ���5עֱѡ����
	 * ����˳��һλһ�����룬����֮����"*"�������������5ע��֮����"^"�ָ���
	 * 0*1*2*3*4*5*6
	 * 
	 */
	protected static final String COLOR_BET_0_REGULAR = "^(\\d\\*){6}(\\d){1}$";//0-��ʽ
	/**
	 * ��0-9��10������������ѡ��7��������Ͷע��ÿλ����ѡ��1-10�����롣ÿһλ����ĸ�����˼�Ϊ��ע����
	 * һλ���ִ���һλ���룬����λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 01*23*4*56*65*8
	 * 
	 * �����
	 * ��0-9��10��������ѡ��7����������M(M>=7)����Ͷע��ÿ��λ�ñ���ѡ������һ�����֣���ͬλ�õ����ֿ����ظ���ͬһλ�õ����ֲ����ظ��� 
	 * ����˳��һλһ���������룬����֮����"*"��������ע֮����"^"�ָ��� 
	 * 1*23*34*34*45*23*45^1*2*3*4*5*6*7
	 * 
	 */
	protected static final String COLOR_BET_1_REGULAR = "^(\\d{1,10}\\*){6}(\\d{1,10}){1}$";//1-��ʽ
	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * ����3
	 */
	protected static final String ARRANGE_3_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String ARRANGE_3_BETTYPE_0 = "0";// 0-ֱѡ��ʽ
	protected static final String ARRANGE_3_BETTYPE_1 = "1";// 1-ֱѡ��ʽ
	protected static final String ARRANGE_3_BETTYPE_2 = "2";// 2-ֱѡ��ֵ
	protected static final String ARRANGE_3_BETTYPE_3 = "3";// 3-��ѡ��ʽ
	protected static final String ARRANGE_3_BETTYPE_4 = "4";// 4-��ѡ����ʽ
	protected static final String ARRANGE_3_BETTYPE_5 = "5";// 5-��ѡ����ʽ
	protected static final String ARRANGE_3_BETTYPE_6 = "6";// 6-��ѡ��ֵ
	protected static final String ARRANGE_3_BETTYPE_7 = "7";// 7-ֱѡ���
	protected static final String ARRANGE_3_BETTYPE_8 = "8";// 8-ֱѡ��ϵ���
	protected static final String ARRANGE_3_BETTYPE_9 = "9";// 9-��ѡ������
	protected static final String ARRANGE_3_BETTYPE_10 = "10";// 10-��ѡ������
	protected static final String ARRANGE_3_BETTYPE_11 = "11";// 11-ֱѡ���
	protected static final String ARRANGE_3_BETTYPE_12 = "12";// 12-��ѡ�����
	protected static final String ARRANGE_3_BETTYPE_13 = "13";// 13-��ѡ�����
	protected static final String ARRANGE_3_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	/*
	 * 0-ֱѡ��ʽ
	 * ��0-9��10������������ѡ��3�����ֽ���Ͷע����Ʊ���5עֱѡ����
	 * ����˳��һλһ�����룬����֮����"*"������������5ע��֮����"^"�ָ�
	 * 1*2*3
	 */
	protected static final String ARRANGE_3_BET_0_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";
	/*
	 * 1-ֱѡ��ʽ
	 * ��0-9��10������������ѡ�񣬸�λ��ʮλ�Ͱ�λ��ĳλ���λ�ĺ����������1��ÿλ����ѡ��1-10�����롣�����λѡ��X�����룬ʮλѡ��Y������λѡ��Z�����ܽ��=X*Y*Z*2��
	 * һλ���ִ���һλ���룬����λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 12*23*34
	 * 
	 * �����
	 * ��0-9��10������������ѡ��3���������������ֽ���Ͷע����ͬλ�����ֿ�����ͬ��ͬһλ�õ����ֲ�����ͬ��
	 * ����˳��һλһ���������룬����֮����"*"��������ע֮����"^"�ָ���
	 * 
	 */
	protected static final String ARRANGE_3_BET_1_REGULAR = "^(\\d){1,10}\\*(\\d){1,10}\\*(\\d){1,10}$";
	/*
	 * 2-ֱѡ��ֵ
	 * ��1-26����ѡһ������������λ�������֮��Ϊ5������ֱѡ���룬����005��014��122����
	 * 1-26֮���1λ���֡������ԣ�ע��֮����"^"�ָ�
	 * 5
	 * 
	 */
	protected static final String ARRANGE_3_BET_2_REGULAR = "([1-9]|1[0-9]|2[0-6]{1})";
	/*
	 * 3-��ѡ��ʽ
	 * ��0-9��10������������ѡ��3��������Ͷע��Ͷע���������У�����������ͬ����ѡ������������������ͬ������ѡ������
	 * ����˳��һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ�
	 * 1*2*3
	 * 
	 * �����
	 *  ��0-9��10��������ѡ��3��������Ͷע��������ѡ������ͬ������Ͷע���������У�����������ͬ����ѡ������������������ͬ������ѡ������
	 *   һλһ�����룬����֮����"*"��������ע֮����"^"�ָ���
	 * 
	 */
	protected static final String ARRANGE_3_BET_3_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";
	/*
	 * 4-��ѡ����ʽ
	 * ��0-9��10������������ѡ��4���������Ͷע������ѡ�ĺ����У��������������������ΪͶע��ע�������磺1*2*3*4�γɵ����123��124��134��234������4עͶע���롣
	 * һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ�
	 * 1*2*3*4*5
	 * 
	 * �����
	 * ��0-9��10������������ѡ��M�����루M>=3������Ͷע����ѡ�ĺ�����벻����ͬ��
	 * ���磺1*2*3*4�γɵ����123��124��134��234������4עͶע���롣 һλһ�����룬����֮����"*"��������ע֮����"^"�ָ���1*2*3^2*4*5*6^3*8*7*4
	 */
	protected static final String ARRANGE_3_BET_4_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}(\\*\\d){0,}$";
	/*
	 * 5-��ѡ����ʽ
	 * ��0-9��10����������ѡ2-10���������Ͷע������ѡ�ĺ����У������������������*2��ΪͶע��ע�������磺1*2*3 �ɵ����12��13��23��ÿ����϶�Ӧ2ע����3*2=6ע��
	 * һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ�
	 * 1*2*3*4
	 * 
	 * �����
	 * ��0-9��10������������ѡ��M�����루M>=2������Ͷע����ѡ�ĺ�����벻����ͬ��һλһ�����룬����֮����"*"��������ע֮����"^"�ָ���
	 */
	protected static final String ARRANGE_3_BET_5_REGULAR = "^(\\d\\*){1,9}(\\d){1}$";
	/*
	 * 6-��ѡ��ֵ
	 * ��2-25������ѡ��1��������Ͷע����ѡ��ֵ��Ӧ��Ͷע���룬��������������ͬΪ��ѡ����������������ͬΪ��ѡ��
	 * 2-25֮���1λ���֡������ԣ�ע��֮����"^"�ָ�
	 * 6
	 * 
	 * �����
	 * ��2-25��ѡ��1��������Ͷע����ֵ���Ϊ����������ӵ�����ѡ���ֵ�Ͷע��������������һ�����ֵĺͣ���ѡ��ֵ��Ӧ��Ͷע���룬
	 * ��������������ͬΪ��ѡ����������������ͬΪ��ѡ������ע֮����"^"�ָ���
	 */
	protected static final String ARRANGE_3_BET_6_REGULAR = "([2-9]|1[0-9]|2[0-5]{1})";;
	/*
	 * 7-ֱѡ���
	 * ��0-9��10������������ѡ��3��������Ͷע�����ָѡ�������������������ɵ�ʽͶע���������123 ��ʽ���룺123��132��213��231��312��321
	 * һλһ�����룬����֮������ָ��������ԣ�ע��֮����"^"�ָ�
	 * 1234
	 */
	protected static final String ARRANGE_3_BET_7_REGULAR = "^\\d{3,}$";
	/*
	 * 8-ֱѡ��ϵ���
	 * ��0-9��10��������ѡ��1����2����Ϊ���룬�����뵨�벻�ظ���������Ϊ���룬��ɵ���*�������ʽͶע������01*2 ��Ӧ�ĵ�ʽ�������012��021��102��120��210��201����ע��
	 * һλһ�����룬����֮������ָ������������֮��ʹ��*�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 * 01*2
	 * (��ҪУ���ظ�)
	 */
	protected static final String ARRANGE_3_BET_8_REGULAR = "^(\\d){1,2}\\*(\\d)$";
	/*
	 * 9-��ѡ������
	 * ��0-9��10��������ѡ��1��Ϊ���룬�����뵨�벻�ظ���������Ϊ���룬��ɵ���*�������ʽͶע������1*23��Ӧ�ĵ�ʽ�������112��122��113��133����ע��
	 * һλһ�����룬����֮������ָ������������֮��ʹ��*�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 * 1*23
	 * (��ҪУ���ظ�)
	 */
	protected static final String ARRANGE_3_BET_9_REGULAR = "^(\\d){1}\\*(\\d)$";
	/*
	 * 10-��ѡ������
	 * ��0-9��10��������ѡ��1����2����Ϊ���룬�����뵨�벻�ظ���������Ϊ���룬��ɵ���*�������ʽͶע������12*34 ��Ӧ�ĵ�ʽ�������123��124����עͶע���롣
	 * һλһ�����룬����֮������ָ������������֮��ʹ��*�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 * 12*34
	 * (��ҪУ���ظ�)
	 * �Ƿ���ҪУ�鵨����ظ�
	 */
	protected static final String ARRANGE_3_BET_10_REGULAR = "^(\\d){1,2}\\*(\\d)$";
	/*
	 * 11-ֱѡ���
	 * ��0-9��10��������ѡ��һ��������Ͷע�����=Ͷע������������ - ��С���롣������1�ĵ�ʽͶע������112��667��343
	 * 0-9֮���1λ���֡������ԣ�ע��֮����"^"�ָ�
	 * 7
	 */
	protected static final String ARRANGE_3_BET_11_REGULAR = "^(\\d){1}$";
	/*
	 * 12-��ѡ�����
	 * ��1-9������ѡ��1���������Ͷע���������п����ͬ��������������2���γɵ�ʽ��������220��202��133��331��16ע��
	 * 1-9֮���1λ���֡������ԣ�ע��֮����"^"��
	 * 6
	 */
	protected static final String ARRANGE_3_BET_12_REGULAR = "^([1-9]){1}$";
	/*
	 * 13-��ѡ�����
	 * ��2-9������ѡ��1���������Ͷע���������п����ͬ��������������2���γɵ�ʽ��������012��123��234��345��8ע��
	 * 2-9֮���1λ���֡������ԣ�ע��֮����"^"�ָ�
	 * 4
	 */
	protected static final String ARRANGE_3_BET_13_REGULAR = "^([2-9]){1}$";
	////////////////////////////////////////////////////////////////////////////////////
	//����5
	protected static final String ARRANGE_5_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String ARRANGE_5_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String ARRANGE_5_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String ARRANGE_5_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * ��0-9��10������������ѡ��5�����ֽ���Ͷע����Ʊ���5עֱѡ����
	 * ����˳��һλһ�����룬����֮����"*"������������5ע��֮����"^"�ָ���
	 * 1*2*3*4*5
	 */
	protected static final String ARRANGE_5_BET_0_REGULAR = "^(\\d\\*){4}(\\d){1}$";//0-��ʽ
	/**
	 * ��0-9��10������������ѡ��ÿһλ����ĸ�����˼�Ϊ��ע��
	 * һλ���ִ���һλ���룬����λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 1*23*34*34*45
	 * �����
	 * 
	 * ��0-9��10��������ѡ��5����������M(M>=5)����Ͷע��ÿ��λ�ñ���ѡ������һ�����֣�
	 * ��ͬλ�õ����ֿ����ظ���ͬһλ�õ����ֲ����ظ�������˳��һλһ���������룬����֮����"*"��������ע֮����"^"�ָ���
	 * 1*23*34*34*45^1*2*3*4*5
	 */
	protected static final String ARRANGE_5_BET_1_REGULAR = "^(\\d{1,}\\*){4}(\\d{1,}){1}$";//1-��ʽ
	
    ////////////////////////////////////////////////////////////////////////////////////
	//ʤ����
	
	protected static final String WINORCLOSE_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String WINORCLOSE_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String WINORCLOSE_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String WINORCLOSE_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 14���������ֱ���3��1��0��������ʤƽ��
	 * ���ճ���һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ���
	 * 3*1*1*3*3*1*0*3*3*3*3*0*1*3
	 */
	protected static final String WINORCLOSE_BET_0_REGULAR = "^(([0]|[1]|[3]){1}\\*){13}([0]|[1]|[3]){1}$";//0-��ʽ
	/**
	 * 14���������ֱ���3��1��0��������ʤƽ��
	 * ���ճ��θ���λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 3*01*1*3*31*1*03*3*3*3*3*0*1*3
	 * �����
	 * 14���������ֱ���3��1��0��������ʤƽ����ѡ��14�������Ľ��
	 * ���ճ���һλһ���������룬����֮����"*"��������ע֮����"^"�ָ���
	 * 
	 * 
	 */
	protected static final String WINORCLOSE_BET_1_REGULAR = "^(([0]|[1]|[3]){1,}\\*){13}([0]|[1]|[3]){1,}$";//1-��ʽ
	//////////////////////////////////////////////////////////////////////////////////////
	//��ѡ�ų�
	protected static final String ARBITRARY_9_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String ARBITRARY_9_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String ARBITRARY_9_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String ARBITRARY_9_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 14���������ֱ���3��1��0��������ʤƽ������ѡ�ĳ���ʹ������4
	 * ���ճ���һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ���
	 * 3*4*1*3*4*1*0*4*3*4*3*4*1*3
	 */
	protected static final String ARBITRARY_9_BET_0_REGULAR = "^((([0]|[1]|[3]){1}\\*)|([4]{1}\\*)){13}(([0]|[1]|[3]){1}|([4]{1})){1}$";//0-��ʽ
	/**
	 * 14���������ֱ���3��1��0��������ʤƽ������ѡ�ĳ���ʹ������4
	 * ���ճ��θ���λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 3*4*13*30*4*1*0*4*3*4*3*4*1*3
	 */
	protected static final String ARBITRARY_9_BET_1_REGULAR = "^((([0]|[1]|[3]){1,}\\*)|([4]{1}\\*)){13}(([0]|[1]|[3]){1,}|([4]{1})){1}$";//1-��ʽ
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//6����ȫ��
	protected static final String HALF_6_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String HALF_6_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String HALF_6_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String HALF_6_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 6��������12���볡���ֱ���3��1��0�������Ӱ볡ʤƽ��
	 * ���ճ���һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ���
	 * 3*1*1*3*3*1*0*3*3*3*3*0
	 */
	protected static final String HALF_6_BET_0_REGULAR = "^(([0]|[1]|[3]){1}\\*){11}([0]|[1]|[3]){1}$";//0-��ʽ
	/**
	 * 6��������12���볡���ֱ���3��1��0�������Ӱ볡ʤƽ��
	 * ���ճ��θ���λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 3*1*130*3*30*1*0*3*3*3*3*0
	 */
	protected static final String HALF_6_BET_1_REGULAR = "^(([0]|[1]|[3]){1,}\\*){11}([0]|[1]|[3]){1,}$";//1-��ʽ
	
	////////////////////////////////////////////////////////////////////////////////////////
	//4�������
	protected static final String BALL_4_PLAYTYPE_NO_ADDITIONAL = "0";// ��׷��
	protected static final String BALL_4_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String BALL_4_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String BALL_4_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 4��������ÿ�����������ӽ������ͿͶӽ����������˸����֣��ֱ���0��1��2��3����0��1��2��3�����Ͻ���
	 * ���ճ���һλһ�����룬����֮����"*"�����������ԣ�ע��֮����"^"�ָ���
	 * 3*1*2*3*3*1*0*3
	 */
	protected static final String BALL_4_BET_0_REGULAR = "^([0-3]{1}\\*){7}([0-3]){1}$";//0-��ʽ
	/**
	 * 4��������ÿ�����������ӽ������ͿͶӽ����������˸����֣��ֱ���0��1��2��3����0��1��2��3�����Ͻ���
	 * ���ճ��θ���λ�ú���֮����"*"������ÿ��λ�ÿ�����д������롣�����ԣ�ע��֮����"^"�ָ�
	 * 3*012*2*123*3*1*0*3
	 */
	protected static final String BALL_4_BET_1_REGULAR = "^([0-3]{1,}\\*){7}([0-3]){1,}$";//1-��ʽ
	
	//////////////////////////////////////////////////////////////////////////////////////
	//ʮһ�˶��
	protected static final String ELEVEN_PLAYTYPE_1 = "1";//��ѡ1
	protected static final String ELEVEN_PLAYTYPE_2 = "2";//��ѡ2
	protected static final String ELEVEN_PLAYTYPE_3 = "3";//��ѡ3
	protected static final String ELEVEN_PLAYTYPE_4 = "4";//��ѡ4
	protected static final String ELEVEN_PLAYTYPE_5 = "5";//��ѡ5
	protected static final String ELEVEN_PLAYTYPE_6 = "6";//��ѡ6
	protected static final String ELEVEN_PLAYTYPE_7 = "7";//��ѡ7
	protected static final String ELEVEN_PLAYTYPE_8 = "8";//��ѡ8
	protected static final String ELEVEN_PLAYTYPE_9 = "9";//ѡǰ��ֱѡ
	protected static final String ELEVEN_PLAYTYPE_10 = "10";//ѡǰ��ֱѡ
	protected static final String ELEVEN_PLAYTYPE_11 = "11";//ѡǰ����ѡ
	protected static final String ELEVEN_PLAYTYPE_12 = "12";//ѡǰ����ѡ
	
	protected static final String ELEVEN_BETTYPE_0 = "0";// 0-��ʽ
	protected static final String ELEVEN_BETTYPE_1 = "1";// 1-��ʽ
	protected static final String ELEVEN_BETTYPE_2 = "2";// 2-����
	protected static final String ELEVEN_BETTYPE_3 = "3";// 3-��λ��ʽ
	protected static final String ELEVEN_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/*
	 * ��ѡһ
	 * 0-��ʽ	
     * 01-11��ѡһ������	
     * 03	
     * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_1_0_REGULAR = "^(0[1-9]|1[0-1]){1}$";
	/*
	 * ��ѡ��
	 * *0-��ʽ	
     *01-11��ѡ2������,���벻���ظ�
     *0305	
     *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_2_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * ��ѡ��
	 *1-��ʽ	
	 *01-11��ѡ������2�����룬���벻���ظ����ɰ�����ʽ
	 *03040506	
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_2_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * ��ѡ��
	 *2-����	
	 *01-11��ѡ������ѡ��һ�����룬������������	
	 *01*020304	
	 *��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 *����Ͷע��ָ��ѡȡ���ڵ�ʽͶע��������ĺ�����Ϊ���루��ÿע��Ʊ�������ĺ��룩��
	 *��ѡȡ����������ĺ�����Ϊ���룬�������������֮������ڵ�ʽͶע����������ɵ��������밴�õ�ʽͶע��ʽ��ɶ�ע��Ʊ��Ͷע
	 *�������붼�����ظ������벻�ܰ����ڵ�����
	 */
	protected static final String ELEVEN_BET_2_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ��ѡ��
	 *0-��ʽ	
	 *01-11��ѡ3�����룬���벻���ظ�
	 *020304	
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_3_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * ��ѡ��
	 *1-��ʽ	
	 *01-11��ѡ������3�����룬���԰�����ʽ	
	 *0203040506	
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_3_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * ��ѡ��
	 *2-����
	 *
	 *			
	 *��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_3_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|0[0-1]){1,}$";
	/*
	 * ��ѡ��
	 * 0-��ʽ	
	 * 01-12��ѡ4������		
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_4_0_REGULAR = "^(0[1-9]|1[0-1]){4}$";
	/*
	 * ��ѡ��
	 *1-��ʽ	
	 *01-11��ѡ������4������		
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_4_1_REGULAR = "^(0[1-9]|1[0-1]){4,}$";
	/*
	 * ��ѡ��
	 *2-����	
	 *
	 *		
	 *��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_4_2_REGULAR = "^(0[1-9]|1[0-1]){1,3}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ��ѡ��
	 *0-��ʽ	
	 *01-13��ѡ��������		
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_5_0_REGULAR = "^(0[1-9]|1[0-1]){5}$";
	/*
	 * ��ѡ��
	 *1-��ʽ	
	 *01-11��ѡ������5������		
	 *��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_5_1_REGULAR = "^(0[1-9]|1[0-1]){5,}$";
	/*
	 * ��ѡ��
	 *2-����	
	 * 
	 * 		
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_5_2_REGULAR = "^(0[1-9]|1[0-1]){1,4}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 *��ѡ��
	 * 0-��ʽ	
	 * 01-14��ѡ��������		
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_6_0_REGULAR = "^(0[1-9]|1[0-1]){6}$";
	/*
	 * ��ѡ��
	 * 1-��ʽ	
	 * 01-11��ѡ������6������		
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_6_1_REGULAR = "^(0[1-9]|1[0-1]){6,}$";
	/*
	 *��ѡ��
	 * 2-����	
	 * 
	 * 		
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_6_2_REGULAR = "^(0[1-9]|1[0-1]){1,5}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ��ѡ��
	 * 0-��ʽ	
	 * 01-15��ѡ��������		
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_7_0_REGULAR = "^(0[1-9]|1[0-1]){7}$";
	/*
	 * ��ѡ��
	 * 1-��ʽ	
	 * 01-11��ѡ������7������		
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_7_1_REGULAR = "^(0[1-9]|1[0-1]){7,}$";
	/*
	 * ��ѡ��
	 * 2-����
	 * 
	 * 			
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_7_2_REGULAR = "^(0[1-9]|1[0-1]){1,6}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ��ѡ��
	 * 0-��ʽ	
	 * 01-16��ѡ��������		
	 * 
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_8_0_REGULAR = "^(0[1-9]|1[0-1]){8}$";
	/*
	 * 1-��ʽ	
	 * 01-11��ѡ������8������		
	 * 
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_8_1_REGULAR = "^(0[1-9]|1[0-1]){8,}$";
	/*
	 * 2-����
	 * 
	 * 			
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_8_2_REGULAR = "^(0[1-9]|1[0-1]){1,7}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 0-��ʽ	
	 * 01-11��ѡ2�����룬��˳��	�����벻���ظ�
	 * 0305	
	 * ��˳����λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_9_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * ѡǰ��ֱѡ
	 * 1-��ʽ	
	 * 01-11��ѡ2�����Ϻ��룬����ͬ���벻ͬ˳����ϣ����벻���ظ�
	 * 03050607	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_9_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 2-����
	 * 
	 * 		
	 * 03*030506	
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_9_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 3-��λ��ʽ	
	 * ��˳��ÿ��λ�ÿ�ѡ�������	
	 * 03*040506	
	 * ��λһ�����룬������λ��'0'��λ��֮����"*"�ָ�����˳��ֱ�Ϊ��1��2λ��ÿ��λ�ÿ��Զ�����룻�����ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_9_3_REGULAR = "^(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 0-��ʽ	
	 * 01-11��ѡ3������	
	 * 030507	
	 * ��˳����λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_10_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * ѡǰ��ֱѡ
	 * 1-��ʽ	
	 * 01-11��ѡ4������	
	 * 03050607	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_10_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 2-����
	 * 		
	 * 03*030506	
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_10_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|0[0-1]){1,}$";
	/*
	 * ѡǰ��ֱѡ
	 * 3-��λ��ʽ	
	 * ��˳��ÿ��λ�ÿ�ѡ�������	
	 * 03*040506*02	
	 * ��λһ�����룬������λ��'0'��λ��֮����"*"�ָ�����˳��ֱ�Ϊ��1��2��3λ��ÿ��λ�ÿ��Զ�����룻�����ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_10_3_REGULAR = "^(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ѡǰ����ѡ
	 * 0-��ʽ	
	 * 01-11��ѡ2������	
	 * 0305	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_11_0_REGULAR = "^(0[1-9]|1[0-1]){2}$";
	/*
	 * ѡǰ����ѡ
	 * 1-��ʽ	
	 * 01-11��ѡ����2������	
	 * 030506	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_11_1_REGULAR = "^(0[1-9]|1[0-1]){2,}$";
	/*
	 * ѡǰ����ѡ
	 * 2-����	
	 * 	
	 * 01*020304	
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_11_2_REGULAR = "^(0[1-9]|1[0-1]){1}\\*(0[1-9]|1[0-1]){1,}$";
	/*
	 * ѡǰ����ѡ
	 * 0-��ʽ	
	 * 01-11��ѡ3������	
	 * 030506	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_12_0_REGULAR = "^(0[1-9]|1[0-1]){3}$";
	/*
	 * ѡǰ����ѡ
	 * 1-��ʽ	
	 * 01-11��ѡ����3������	
	 * 0305060809	
	 * ��λһ�����룬������λ��'0'�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_12_1_REGULAR = "^(0[1-9]|1[0-1]){3,}$";
	/*
	 * ѡǰ����ѡ
	 * 2-����		
	 * 0102*030405	
	 * ��λһ�����룬������λ��'0'�����������֮����"*"�ָ���������ǰ�������ԣ�ע��֮����"^"�ָ�
	 */
	protected static final String ELEVEN_BET_12_2_REGULAR = "^(0[1-9]|1[0-1]){1,2}\\*(0[1-9]|1[0-1]){1,}$";
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * ��������
	 */
	/**
	 * ˫ɫ��
	 */
	////////////////////////////////////////////////////////////////////////////////////
	protected static final String SSQ_PLATTYPE_NO_ADDITIONAL = "0";//�淨����׷��
	protected static final String SSQ_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String SSQ_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String SSQ_BETTYPE_DANTUO = "2";//����
	protected static final String SSQ_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	/**
	 * 0-��ʽ
	 * ��01-33����ѡ6�����ظ��ĺ�����Ϊ����01-16����ѡ1��������Ϊ����1-9ǰ����0���롣
	 * �ظ������������ķ����ж�
	 */
	protected static final String SSQ_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|3[0-3]){6}\\|(0[1-9]|1[0-6]){1}$";//0-��ʽ
	/**
	 * 1-��ʽ
	 * ��1-33����ѡM�����ظ��ĺ�����Ϊǰ����1-16����ѡN�����ظ��ĺ�����Ϊ�������룬M>=6����N>=1
	 * 
	 */
	protected static final String SSQ_BET_1_REGULAR = "^((0[1-9]|1\\d|2\\d|3[0-3]){6,}\\|(0[1-9]|1[0-6]){1,})$";//1-��ʽ
	
	//���ֲ�
	protected static final String QLC_PLAYTYPE_NO_ADDITIONAL = "0";//�淨����׷��
	protected static final String QLC_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String QLC_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String QLC_BETTYPE_DANTUO = "2";//����
	protected static final String QLC_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 01-30������ѡ��7�����ظ��ĺ���,������˳��Ҫ��
	 * 
	 */
	protected static final String QLC_BET_0_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){7}$";//0-��ʽ
	/**
	 * 01-30����������ѡ��M�����ظ��ĺ������Ͷע,M>=7��M<=16;��ʽÿ7�����Ϊһ����ʽͶע
	 * 
	 */
	protected static final String QLC_BET_1_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){7��16}$";//1-��ʽ
	/**
	 * 01-30��ѡ�񣻵���M(1<=M<=6),����N(N>=1),M+N >=7;���������벻���ظ�
	 */
	protected static final String QLC_BET_2_REGULAR = "^(0[1-9]|1[0-9]|2[0-9]|30){1,6}\\*(0[1-9]|1[0-9]|2[0-9]|30){1,}$";//2-����
	
	
	//����3D
	protected static final String FC3D_PLAYTYPE_NO_ADDITIONAL = "0";//�淨����׷��
	protected static final String FC3D_BETTYPE_0 = "0";// 0-ֱѡ��ʽ
	protected static final String FC3D_BETTYPE_1 = "1";// 1-ֱѡ��ʽ
	protected static final String FC3D_BETTYPE_2 = "2";// 2-��ѡ��ʽ
	protected static final String FC3D_BETTYPE_3 = "3";// 3-��ѡ3��ʽ
	protected static final String FC3D_BETTYPE_4 = "4";// 4-��ѡ����ʽ
	protected static final String FC3D_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	/*
	 * ��0-9��10������������ѡ��3�����ֽ���Ͷע�����ֿ�����ͬ
	 */
	protected static final String FC3D_BET_0_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";//0-ֱѡ��ʽ
	/*
	 * 
	 * ��0-9��10������������ѡ��3���������������ֽ���Ͷע����ͬλ�����ֿ�����ͬ��ͬһλ�õ����ֲ�����ͬ
	 */
	protected static final String FC3D_BET_1_REGULAR = "^(\\d){1,10}\\*(\\d){1,10}\\*(\\d){1,10}$";//1-ֱѡ��ʽ
	
	/*
	 *   ��0-9��10��������ѡ��3��������Ͷע��������ѡ������ͬ������Ͷע���������У�����������ͬ����ѡ������������������ͬ������ѡ������
	 * 
	 */
	protected static final String FC3D_BET_2_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}$";//2-��ѡ��ʽ
	/*
	 * ��0-9��10������������ѡ��M�����루M>=2������Ͷע����ѡ�ĺ�����벻����ͬ��һλһ�����룬����֮����"*"��������ע֮����"^"�ָ���
	 */
	protected static final String FC3D_BET_3_REGULAR = "^(\\d\\*){1,9}(\\d){1}$";//3-��ѡ3��ʽ
	/*
	 * ��0-9��10������������ѡ��M�����루M>=3������Ͷע����ѡ�ĺ�����벻����ͬ��
	 * ���磺1*2*3*4�γɵ����123��124��134��234������4עͶע���롣 һλһ�����룬����֮����"*"��������ע֮����"^"�ָ���1*2*3^2*4*5*6^3*8*7*4
	 */
	protected static final String FC3D_BET_4_REGULAR = "^(\\d){1}\\*(\\d){1}\\*(\\d){1}(\\*\\d){0,}$";//4-��ѡ����ʽ
	
	//15ѡ5
	protected static final String SWXW_PLAYTYPE_NO_ADDITIONAL = "0";//�淨����׷��
	protected static final String SWXW_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String SWXW_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String SWXW_BETTYPE_DANTUO = "2";//����
	protected static final String SWXW_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	
	/**
	 * 01-15��������ѡ��5�����ظ��ĺ���
	 * 
	 */
	protected static final String SWXW_BET_0_REGULAR = "^(0[1-9]|1[0-5]){5}$";//0-��ʽ
	/**
	 * 01-15��������ѡ��M�����ظ��ĺ���Ͷע��5<=M<=13
	 * 
	 */
	protected static final String SWXW_BET_1_REGULAR = "^(0[1-9]|1[0-5]){5��13}$";//1-��ʽ
	/**
	 * 01-15��ѡ�񣻵���M(1<=M<=4), ����N(1<=N),��M+N>=5,�������벻���ظ�
	 */
	protected static final String SWXW_BET_2_REGULAR = "^(0[1-9]|1[0-5]){1,4}\\*(0[1-9]|1[0-5]){1,}$";//2-����
	
	/**
	 * ����6+1
	 */
	protected static final String DF6J1_PLATTYPE_NO_ADDITIONAL = "0";//�淨����׷��
	protected static final String DF6J1_BETTYPE_SINGLE = "0";//��ʽ
	protected static final String DF6J1_BETTYPE_DUPLEX = "1";//��ʽ
	protected static final String DF6J1_BET_GROUP_SEPARATOR = "\\^";//��ע֮��ķָ����
	/**
	 * 0-��ʽ
	 * ��0-9��10��������ѡ��6��������Ϊ������������˳��ѡ�����ֿ����ظ�����12��Ф��ѡ��һ����Ф��Ϊ��Ф��(��Ф����˳�������01-12��ʶ)
	 * �ظ������������ķ����ж�
	 */
	protected static final String DF6J1_BET_0_REGULAR = "^(\\d\\*){5}(\\d){1}\\|(0[1-9]|1[0-2]){1}$";//0-��ʽ
	/**
	 * 1-��ʽ
	 * ����������0-9��10��������ѡ��6������������M(M>=6)����Ͷע��ÿ��λ�ñ���ѡ������һ�����֣���ͬλ�õ����ֿ����ظ���ͬһλ�õ����ֲ����ظ���
     *��Ф������12��Ф��ѡ��1�������ϸ���ФN(N>=1)Ͷע,��Ф�������ظ�,���벻�÷ָ�
	 */
	protected static final String DF6J1_BET_1_REGULAR = "^(\\d{1,10}\\*){5}(\\d{1,10}){1}\\|(0[1-9]|1[0-2]){1,})$";//1-��ʽ
	
	/**
	 * 
	 * ���������Ŀ�괮����У��
	 * @param rules ����Ĺ���
	 * @param targetStr Ŀ���ַ���
	 * @return boolean  ���Ϲ��򷵻�true �����Ϲ��򷵻�false
	 * @throws
	 */
	protected boolean checkRules(String rules,String targetStr) {
		boolean result = Pattern.matches(rules, targetStr);
		return result;
	}
	
	/**
	 * 
	 * �ж���len����ָ�������ַ����Ƿ������ַ������ظ�����
	 * @param targetStr �����ֻ���ĸ��ɵ��ַ���
	 * @param len �ָ�ĳ���
	 * @return boolean    ������ظ�����true,����ظ�����false
	 * @throws
	 */
	protected boolean checkRepeat(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>(); 
		boolean result = true;
		try {
			String regu = "\\w{"+len+"}";
			Pattern p = Pattern.compile(regu);
			Matcher m = p.matcher(targetStr);
			while(m.find()) {
				list.add(m.group());
				set.add(m.group());
			}
			if(set.size() < list.size()){
				result = false;
			}
		} catch (RuntimeException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * Title: checkRepeatNum<br>
	 * Description: �ж���len����ָ�������ַ����Ƿ������ַ������ظ����ֵ�λ����<br>
	 * @param targetStr
	 * @param len
	 * @return int 0 ��������λ�ö����ظ���1������һ��λ���ظ�
	 */
	protected int checkRepeatNum(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<String>(); 
		int result = -1;
		try {
			String regu = "\\w{"+len+"}";
			Pattern p = Pattern.compile(regu);
			Matcher m = p.matcher(targetStr);
			while(m.find()) {
				list.add(m.group());
				set.add(m.group());
			}
			result = list.size() - set.size();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * ���ճ��Ȳ���ַ���Ϊ�����б����ܲ�ֺ��ֺ��������
	 * @param targetStr Ŀ���ַ���
	 * @param len ����
	 * @return List<String> �𿪵������б�
	 * @throws ���쳣δ������
	 */
	protected List<String> splitStrByLen(String targetStr,int len){
		List<String> list = new ArrayList<String>();
		String regu = "\\w{"+len+"}";
		Pattern p = Pattern.compile(regu);
		Matcher m = p.matcher(targetStr);
		while(m.find()) {
			list.add(m.group());
		}
		return list;
	}
	/**
	 * 
	 * �ϲ����Լ���Ϊ�ַ���
	 * @param list
	 * @return String    ��������
	 * @throws
	 */
	protected String mergeListToStr(List<String> list){
		StringBuffer result = new StringBuffer();
		for(String elem : list){
			result.append(elem);
		}
		return result.toString();
	}
	/**
	 * 
	 * �����Լ�����signָ�����źϲ�Ϊ�ַ���
	 * @param list �����б�
	 * @param sign �ϲ�����
	 * @return String  �ϲ������ַ���
	 * @throws
	 */
	protected String mergeListToStrBySign(List<String> list,String sign){
		StringBuffer result = new StringBuffer();
		boolean flag = false;
		for(String elem : list){
			if(flag){
				result.append(sign);
			}else{
				flag = true;
			}
			result.append(elem);
		}
		return result.toString();
	}
	
	/**
	 * 
	 * ���ݲ�ַ��Ž��ַ������Ϊ����
	 * @param targetStr Ŀ���ַ���
	 * @param Splitsign ��ַ���
	 * @param isEnforce �Ƿ�ǿ�Ʋ�֣����������true,���ַ�����������ֲ�ַ��ţ������һ����ַ��ź�Ҳ��Ϊһ������Ԫ��
	 * @return String[] ���ݲ�ַ��Ų𿪵��ַ���
	 * @throws
	 */
	protected String [] splitGroup(String targetStr,String Splitsign,boolean isEnforce){
		if(isEnforce){
			return targetStr.split(Splitsign,-1);
		}else{
			return targetStr.split(Splitsign);
		}
		
	}
	
	/**
	 * 
	 * ������ǰ�油0����0�����ݵĳ�����defLenָ��
	 * @param target Ҫ��0������
	 * @param defLen ��0�����ݵĳ���
	 * @return String    ��ʽ������ַ���
	 * @throws
	 */
	protected String formatLenByTag(int target,int defLen){
		String formatStr = "%1$0"+defLen+"d";
		return String.format(formatStr,target);
	}
	
	/**
	 * 
	 * �������ַ��������ַ����г��ֵĴ���
	 * @param mainStr Ŀ���ַ���
	 * @param subStr ���ַ���
	 * @return  ���ֵĴ���
	 */
	protected int checkCharViews(String mainStr,String subStr){
		String regux = "\\b"+subStr+"\\b";
		Pattern p =  Pattern.compile(regux);
		Matcher m = p.matcher(mainStr);
		int num = 0;
		while(m.find()){
			num++;
		}
		
		return num;
	}
	/**
	 * 
	 * У�鵨�ϵ��ظ�
	 * @param target ҪУ��ĵ��ϴ�
	 * @return boolean ������ظ�����true �ظ�����false
	 * @throws
	 */
	protected boolean checkDanTuoRepeat(String target,int weiLen) {
		boolean checkResult = true;
		String[] danTuoGroup = target.split("\\*");// �˴��Ѿ�����У�飬������������룬��������Ϊ2��ֵ
		List<String> dan = splitStrByLen(danTuoGroup[0], weiLen);
		List<String> tuo = splitStrByLen(danTuoGroup[1], weiLen);
		for (String singleDan : dan) {
			if (tuo.contains(singleDan)) {
				checkResult = false;
				break;
			}
		}
		return checkResult;
	}
	/**
	 * 
	 * ����ʽͶע��Ϊ��ʽͶע����������˳��Ҫ���
	 * @param betCode 01*23*4*56*65*8^01*23*4*56*65*8
	 * @param groupSign ��ע֮��Ĳ�ַ��� �������֣����Ҫ�������ת�� ����"\\^"��ʶ"^"
	 * @param weiSign ÿһע�и���λ�õĲ�ַ��� ���Ҫ�������ת�� ����"\\*"��ʶ"*"
	 * @param oneWeiLen ÿһ��λ�õĳ���
	 * @return List<String> ��ʽͶע�ĺ��봮����
	 * @throws
	 */
	protected List<String> combineDuplex(String betCode,String groupSign,String weiSign,int oneWeiLen){
		List<String> result = new ArrayList<String>();
		String [] groupArr = splitGroup(betCode,groupSign,false);
		for(String group : groupArr){//����ÿһע����
			String [] weiGroup = group.split(weiSign);
			Stack<List<String>> oneStack = new Stack<List<String>>();
			for(int k = weiGroup.length -1 ; k >= 0 ;k--){//������ջ
				oneStack.push(splitStrByLen(weiGroup[k],oneWeiLen));
			}
			
			while(oneStack.size() > 1){
				oneStack.push(combineTwo(oneStack.pop(),oneStack.pop(),weiSign.replace("\\", "")));
			}
			result.addAll(oneStack.pop());
		}
		return result;
	}
	/**
	 * 
	 * �����������б�ϳ�һ�������б��ϲ���ʽΪfirst*second
	 * @param first ��һ�����Լ���
	 * @param second �ڶ������Լ���
	 * @param weiSign ÿһ��λ�õĺϲ����� �����������֣���˲��������ת��
	 * @return List<String>    ���صļ���sizeӦ��Ϊfirsr.size*second.size
	 * @throws
	 */
	protected List<String> combineTwo(List<String> first,List<String> second,String weiSign){
		List<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		for(String fistStr : first){
			for(String secondStr : second){
				buf.delete(0, buf.length());//��ջ���
				buf.append(fistStr);
				buf.append(weiSign);
				buf.append(secondStr);
				result.add(buf.toString());
			}
		}
		return result;
	}
	
	
	/**
	 * 
	 * ���firstList�еĺ�����secondList�ĺ��뼯���г��ֵĴ���
	 * ��secondList�еĺ���ƥ���һ�κ�Ͳ���ƥ��
	 * @param firstList 
	 * @param secondList 
	 * @return String ���ֵĴ���
	 * @throws
	 */
	protected String hitHaoNum(List<String> firstList,List<String> secondList){
		int hitNum = 0;
		if(firstList == null || secondList == null){
			return null;
		}
		List<String> innerSecondList = new ArrayList<String>(Arrays.asList(new String[secondList.size()]));
		Collections.copy(innerSecondList, secondList);
		for(String one : firstList){
			if(innerSecondList.contains(one)){
				hitNum++;
				innerSecondList.remove(one);
			}
		}
		return String.valueOf(hitNum);
	}
	
	/**
	 * 
	 * ��ǰ���б�ͺ����б���ϳɶ���ַ�����ǰȥ"+sign+"������
	 * @param sign �ϲ�����
	 * @param beforeList ǰ���б�
	 * @param endList �����б�
	 * @return List<String> List<��ǰȥ"+sign+"������>
	 * @throws
	 */
	protected List<String> combineBeforeEnd(List<String> beforeList,List<String> endList,String sign){
		List<String> result = new ArrayList<String>();
		StringBuffer groupBuf = new StringBuffer();
		for(String before : beforeList){
			for(String end : endList){
				groupBuf.delete(0, groupBuf.length());
				groupBuf.append(before);
				groupBuf.append(sign);
				groupBuf.append(end);
				result.add(groupBuf.toString());
			}
		}
		return result;
	}
	
	/**
	 * 
	 * �����Ϸ�ʽ��ǰȥ���ߺ�����ϳ��ַ�������,ע��ֻ����ϣ�����˳������
	 * @param oneQu ��ʾǰȥ���ߺ������ַ���
	 * @param Totallen  ǰȥ���ߺ����ĳ��ȣ����ڴ���͸��ǰ��Ϊ5������Ϊ2,������Ф��Ϊ2
	 * @param weiLen ����weiLen���Ƚ��ַ�����
	 * @param sign ���ϵķָ���� ע�������ַ�
	 * @return List<String> ��Ϻ��ǰȥ������ַ�������
	 * @throws
	 */
	protected List<String> combineDanTuo(String oneQu,int Totallen,int weiLen,String sign){
		List<String> result = new ArrayList<String>();
		StringBuffer buf = new StringBuffer();
		String [] danTuoArr = oneQu.split(sign);
		if(danTuoArr.length == 2){
			String dan = danTuoArr[0];//����
			String tuo = danTuoArr[1];//����
			int danLen = splitStrByLen(dan,weiLen).size();//���볤��
			int combineLen = Totallen - danLen;//�ܳ��ȼ�ȥ����ĳ���
			List<String> tuoCombine = combineToList(splitStrByLen(tuo,weiLen),combineLen);
			for(String tuoCom : tuoCombine){
				buf.delete(0, buf.length());
				buf.append(dan);
				buf.append(tuoCom);
				result.add(buf.toString());
			}
		}
		return result;
	}
	/**
	 * 
	 * Title: splitBallSalesInfoResult<br>
	 * Description: <br>
	 *            �����ʵ�������Ϣ<br>
	 *            <br>1,A-AC����&B-��������,����,ʱ��
	 * @param salesInfo
	 * @return ��ֺ�ļ���
	 */	
	protected Map<Integer, Map<String,String>> splitBallSalesInfoResult(String salesInfo) {
		
		Map<Integer, Map<String,String>> resultMap = new TreeMap<Integer, Map<String,String>>();
		
		String[] groupsArr = salesInfo.split("\\|");//���������ηֿ�
		for (String group : groupsArr) {
			TreeMap<String, String> teamMap = new TreeMap<String, String>();
			String[] subContent = group.split(",",-1);//�������Σ��������飬���ƣ�ʱ��
			Integer Showno = Integer.valueOf(String.valueOf(subContent[0]));
			String[] teamArr = subContent[1].split("&");
			for(String teamkn : teamArr){
				String teamKey = teamkn.substring(0, 1);
				String teamName = teamkn.substring(2, teamkn.length());
				teamMap.put(teamKey, teamName);
			}
			teamMap.put("C", subContent[2]);
			teamMap.put("D", subContent[3]);
			
			resultMap.put(Showno, teamMap);
		}
		
		return resultMap;
	}
	/**
	 * 
	 * Title: mergeBallSalesInfoResult<br>
	 * Description: <br>
	 *            �ϲ���ʵ�������Ϣ<br>
	 * @param infoMap
	 * @return �ϲ�����ַ���
	 */
	protected String mergeBallSalesInfoResult(Map<Integer, Map<String,String>> infoMap) {
		StringBuffer salesInfoBuf = new StringBuffer();
		
		TreeMap<Integer, TreeMap<String, String>> salesMap = new TreeMap<Integer, TreeMap<String, String>>();

		for (Map.Entry<Integer, Map<String, String>> entry : infoMap.entrySet()) {
			Integer key = entry.getKey();
			Map<String, String> teamMap = entry.getValue();
			TreeMap<String, String> teamSortMap = new TreeMap<String, String>();
			for (Map.Entry<String, String> teamEntry : teamMap.entrySet()) {
				//teamSortMap.put(teamEntry.getKey()+"-", teamEntry.getValue());
				teamSortMap.put(teamEntry.getKey(), teamEntry.getValue());
			}
			salesMap.put(key, teamSortMap);
		}
		
		for (Map.Entry<Integer, TreeMap<String, String>> entry : salesMap.entrySet()) {
			Integer key = entry.getKey();
			salesInfoBuf.append(key).append(",");
			TreeMap<String, String> teamMap = entry.getValue();			
			if(teamMap.get("A") == null){
				salesInfoBuf.append("A-&");
			}else{
				salesInfoBuf.append("A-" + teamMap.get("A").trim() + "&");
			}
			if(teamMap.get("B") == null){
				salesInfoBuf.append("B-,");
			}else{
				salesInfoBuf.append("B-" + teamMap.get("B").trim() + ",");
			}
			if(teamMap.get("C") == null){
				salesInfoBuf.append(",");
			}else{
				salesInfoBuf.append(teamMap.get("C").trim() + ",");
			}
			if(teamMap.get("D") == null){
				salesInfoBuf.append("|");
			}else{
				salesInfoBuf.append(teamMap.get("D").trim() + "|");
			}
//			
//			String formatStr = "1-2&3-4,5,6";
//			int termCount = teamMap.size();
//			for (Map.Entry<String, String> teamEntry : teamMap.entrySet()) {
//				if(teamEntry.getKey().equals("A")){
//					formatStr = formatStr.replaceAll("1", teamEntry.getKey()).replaceAll("2", teamEntry.getValue());
//					//salesInfoBuf.append(teamEntry.getKey()).append("-").append(teamEntry.getValue()).append("&");
//				}
//				if(teamEntry.getKey().equals("B")){
//					formatStr = formatStr.replaceAll("3", teamEntry.getKey()).replaceAll("4", teamEntry.getValue());
//					//salesInfoBuf.append(teamEntry.getKey()).append("-").append(teamEntry.getValue());
//				}
//				if(teamEntry.getKey().equals("C")){
//					formatStr = formatStr.replaceAll("5", teamEntry.getValue());
//				}
//				if(teamEntry.getKey().equals("D")){
//					formatStr = formatStr.replaceAll("6", teamEntry.getValue());
//				}
//				if(termCount < 4){
//					formatStr = formatStr.replaceAll("6", "");
//				}
//				if(termCount < 3){
//					formatStr = formatStr.replaceAll("5", "");
//				}
//				
//			}
//			salesInfoBuf.append(formatStr);
//			salesInfoBuf.append("|");
		}
		
		salesInfoBuf.deleteCharAt(salesInfoBuf.lastIndexOf("|"));
		
		return salesInfoBuf.toString();
	}
	/**
	 * 
	 * ���ַ�������ȡcombineLen���ȵ����
	 * @param targetArr Ŀ���ַ���
	 * @param combineLen ����
	 * @return List<String>  ��Ϻ�ļ���
	 * @throws 
	 */
	protected List<String> combineToList(List<String> target, int combineLen){
		List<String> result= new ArrayList<String>();
		try {
			String [] targetArr = new String[target.size()];
			target.toArray(targetArr);
			List<String[]> innetList = combine(targetArr,combineLen);
			for(String [] sub : innetList){
				result.add(mergeListToStr(Arrays.asList(sub)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * ȡ��Ϸ���
	 * @param targetArr
	 * @param m
	 * @throws Exception    �趨�ļ�
	 * @return List<String[]>    ��������
	 * @throws
	 */
	private  List<String []> combine(String [] targetArr, int m) throws Exception{
		int n = targetArr.length;
		if (m > n) {
			throw new Exception("��������targetArr��ֻ��" + n + "��Ԫ�ء�" + m + "����" + n + "!");
		}

		List<String []> result = new ArrayList<String []>();
		
		if(m == n){
			result.add(targetArr);
			return result;
		}

		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		//��ʼ��
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		//�����ҵ���һ��10��ϣ�Ȼ����01��ͬʱ��������е�1�ƶ�������������
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(combinePrint(bs, targetArr, m));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			//����ߵ�1ȫ���ƶ�������������

			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}

			//����Ƿ����е�1���ƶ��������ұ�
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		result.add(combinePrint(bs, targetArr, m));

		return result;
	}
	/**
	 * 
	 * (������һ�仰�����������������)
	 * @param bs
	 * @param targetArr
	 * @param m
	 * @return String[]    ��������
	 * @throws
	 */
	private String [] combinePrint(int[] bs, String [] targetArr, int m) {
		String[] result = new String[m];
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[pos] = targetArr[i];
				pos++;
			}
		}
		return result;
	}
	/**
	 * 
	 * �������б�ȡnum���ȵ����� 
	 * @param datas Ҫ���е������б�
	 * @param num ���еĳ���
	 * @return List<String>  ���num�Ĵ���datas��Ԫ�ظ�����������null��ʹ��ʱ��Ҫע���ָ��
	 * @throws
	 */
	protected List<String> arrange(List<String> datas,int num){
		if(num > datas.size()){
			return null;
		}
		List<String> tmpList = new ArrayList<String>();
		List<String> retList = new ArrayList<String>();
		arrangeSort(datas,tmpList,retList,num);
		return retList;
	}
	/**
	 * 
	 * �������б�ȡnum���ȵ�����
	 * @param datas Ҫ���е������б�
	 * @param tmpList �ṩһ�������б�ʹ��
	 * @param retList ���ص����м���
	 * @param num    ȡ���еĳ���
	 * @throws
	 */
	private void arrangeSort(List<String> datas,List<String> tmpList,List<String> retList,int num){
		if (tmpList.size() == num) {
			StringBuffer buf = new StringBuffer();
			for(String one : tmpList){
				buf.append(one);
			}
			retList.add(buf.toString());
			return;
		}
		for(int i = 0; i < datas.size(); i++){
			List<String> newDatas = new ArrayList<String>(datas);
			List<String> newTmpList = new ArrayList<String>(tmpList);
			newTmpList.add(newDatas.get(i));
			newDatas.remove(i);
			arrangeSort(newDatas, newTmpList,retList,num);
		}
	}
	
	
}
