package com.success.lottery.operatorlog.domain;

import java.util.Date;


/**
 * OperationLog entity. @author MyEclipse Persistence Tools
 */

public class OperationLog  implements java.io.Serializable {


    // Fields    

     private Long id;//日志ID
     private Integer level;//日志级别
     private Integer type;//日志类别
     private Integer rank;//日志等级
     private Long userid;//用户ID
     private String name;//用户姓名
     private String userkey;//IP地址
     private String keyword1;//关键字1
     private String keyword2;//关键字2
     private String keyword3;//关键字3
     private String keyword4;//关键字4
     private Date operattime;//记录日志时间
     private String message;//日志信息
     private String reserve;


    // Constructors

    /** default constructor */
    public OperationLog() {
    }

	/** minimal constructor */
    public OperationLog(Integer level, Integer type, Integer rank, Long userid, Date operattime, String message) {
        this.level = level;
        this.type = type;
        this.rank = rank;
        this.userid = userid;
        this.operattime = operattime;
        this.message = message;
    }
    
    /** full constructor */
    public OperationLog(Integer level, Integer type, Integer rank, Long userid, String name, String userkey, String keyword1, String keyword2, String keyword3, String keyword4, Date operattime, String message, String reserve) {
        this.level = level;
        this.type = type;
        this.rank = rank;
        this.userid = userid;
        this.name = name;
        this.userkey = userkey;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.keyword4 = keyword4;
        this.operattime = operattime;
        this.message = message;
        this.reserve = reserve;
    }

   
    // Property accessors

    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return this.level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getType() {
        return this.type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRank() {
        return this.rank;
    }
    
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getUserid() {
        return this.userid;
    }
    
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getUserkey() {
        return this.userkey;
    }
    
    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getKeyword1() {
        return this.keyword1;
    }
    
    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return this.keyword2;
    }
    
    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return this.keyword3;
    }
    
    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getKeyword4() {
        return this.keyword4;
    }
    
    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }

    public Date getOperattime() {
        return this.operattime;
    }
    
    public void setOperattime(Date operattime) {
        this.operattime = operattime;
    }

    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public String getReserve() {
        return this.reserve;
    }
    
    public void setReserve(String reserve) {
        this.reserve = reserve;
    }
   








}