package com.example.springboot.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateUtil {

	//默认显示日期的格式
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";
    public static final String DATAFORMAT_STR2 = "yyyy-MM";

  //默认显示日期的格式
    public static final String DATAFORMAT_STR_DOT = "yyyy.MM.dd";
     
    //默认显示日期的格式
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";
     
    //默认显示日期时间的格式
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";
     
    //默认显示简体中文日期的格式
    public static final String ZHCN_DATAFORMAT_STR = "yyyy年MM月dd日";
     
    //默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR = "yyyy年MM月dd日HH时mm分ss秒";
     
    //默认显示简体中文日期时间的格式
    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy年MM月dd日HH时mm分";
     
    private static DateFormat dateFormat = null;
     
    private static DateFormat dateTimeFormat = null;
     
    private static DateFormat zhcnDateFormat = null;
     
    private static DateFormat zhcnDateTimeFormat = null;
    static
    {
        dateFormat = new SimpleDateFormat(DATAFORMAT_STR);
        dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);
        zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);
        zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);
    }
     
    private static DateFormat getDateFormat(String formatStr)
    {
        if (formatStr.equalsIgnoreCase(DATAFORMAT_STR))
        {
            return dateFormat;
        }
        else
            if (formatStr.equalsIgnoreCase(DATATIMEF_STR))
            {
                return dateTimeFormat;
            }
            else
                if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR))
                {
                    return zhcnDateFormat;
                }
                else
                    if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR))
                    {
                        return zhcnDateTimeFormat;
                    }
                    else
                    {
                        return new SimpleDateFormat(formatStr);
                    }
    }
     
    /**
     * 按照默认显示日期时间的格式"yyyy-MM-dd HH:mm:ss"，转化dateTimeStr为Date类型
     * dateTimeStr必须是"yyyy-MM-dd HH:mm:ss"的形式
     * @param dateTimeStr
     * @return
     */
    public static Date getDate(String dateTimeStr)
    {
        return getDate(dateTimeStr, DATATIMEF_STR);
    }
     
    /**
     * 按照默认formatStr的格式，转化dateTimeStr为Date类型
     * dateTimeStr必须是formatStr的形式
     * @param dateTimeStr
     * @param formatStr
     * @return
     */
    public static Date getDate(String dateTimeStr, String formatStr)
    {
        try
        {
            if (dateTimeStr == null || dateTimeStr.equals(""))
            {
                return null;
            }
            DateFormat sdf = getDateFormat(formatStr);
            java.util.Date d = sdf.parse(dateTimeStr);
            return d;
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将YYYYMMDD转换成Date日期
     * @param date
     * @return
     * @throws BusinessException
     */
    public static Date transferDate(String date) throws Exception
    {
        if (date == null || date.length() < 1)
            return null;

        if (date.length() != 8)
            throw new Exception("日期格式错误");
        String con = "-";

        String yyyy = date.substring(0, 4);
        String mm = date.substring(4, 6);
        String dd = date.substring(6, 8);

        int month = Integer.parseInt(mm);
        int day = Integer.parseInt(dd);
        if (month < 1 || month > 12 || day < 1 || day > 31)
            throw new Exception("日期格式错误");

        String str = yyyy + con + mm + con + dd;
        return DateUtil.getDate(str, DateUtil.DATAFORMAT_STR);
    }

    /**
     * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
     * @param date
     * @return
     */
    public static String dateToDateString(Date date)
    {
        return dateToDateString(date, DATATIMEF_STR);
    }

    /**
     * 将Date转换成formatStr格式的字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String dateToDateString(Date date, String formatStr)
    {
        DateFormat df = getDateFormat(formatStr);
        return df.format(date);
    }

    /**
     * 返回一个yyyy-MM-dd HH:mm:ss 形式的日期时间字符串中的HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String getTimeString(String dateTime)
    {
        return getTimeString(dateTime, DATATIMEF_STR);
    }

    /**
     * 返回一个formatStr格式的日期时间字符串中的HH:mm:ss
     * @param dateTime
     * @param formatStr
     * @return
     */
    public static String getTimeString(String dateTime, String formatStr)
    {
        Date d = getDate(dateTime, formatStr);
        String s = dateToDateString(d);
        return s.substring(DATATIMEF_STR.indexOf('H'));
    }

    /**
     * 获取当前日期yyyy-MM-dd的形式
     * @return
     */
    public static String getCurDate()
    {
        //return dateToDateString(new Date(),DATAFORMAT_STR);
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);
    }

    /**
     * 获取当前日期yyyy-MM的形式
     * @return
     */
    public static String getCurDate2()
    {
        //return dateToDateString(new Date(),DATAFORMAT_STR);
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR2);
    }

    /**
     * 获取当前日期yyyy年MM月dd日的形式
     * @return
     */
    public static String getCurZhCNDate()
    {
        return dateToDateString(new Date(), ZHCN_DATAFORMAT_STR);
    }

    /**
     * 获取当前日期时间yyyy-MM-dd HH:mm:ss的形式
     * @return
     */
    public static String getCurDateTime()
    {
        return dateToDateString(new Date(), DATATIMEF_STR);
    }

    /**
     * 获取当前日期时间yyyy年MM月dd日HH时mm分ss秒的形式
     * @return
     */
    public static String getCurZhCNDateTime()
    {
        return dateToDateString(new Date(), ZHCN_DATATIMEF_STR);
    }

    /**
     * 获取日期d的days天后的一个Date
     * @param d
     * @param days
     * @return
     */
    public static Date getInternalDateByDay(Date d, int days)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.DATE, days);
        return now.getTime();
    }

    public static Date getInternalDateByMon(Date d, int months)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MONTH, months);
        return now.getTime();
    }

    public static Date getInternalDateByYear(Date d, int years)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.YEAR, years);
        return now.getTime();
    }

    public static Date getInternalDateBySec(Date d, int sec)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.SECOND, sec);
        return now.getTime();
    }

    public static Date getInternalDateByMin(Date d, int min)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.MINUTE, min);
        return now.getTime();
    }

    public static Date getInternalDateByHour(Date d, int hours)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        now.add(Calendar.HOUR_OF_DAY, hours);
        return now.getTime();
    }

    /**
     * 根据一个日期字符串，返回日期格式，目前支持4种
     * 如果都不是，则返回null
     * @param DateString
     * @return
     */
    public static String getFormateStr(String DateString)
    {
        String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}"; //"yyyy-MM-dd"
        String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"; //"yyyy-MM-dd HH:mm:ss";
        String patternStr3 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日";//"yyyy年MM月dd日"
        String patternStr4 = "[0-9]{4}年[0-9]{1,2}月[0-9]{1,2}日[0-9]{1,2}时[0-9]{1,2}分[0-9]{1,2}秒";//"yyyy年MM月dd日HH时mm分ss秒"

        Pattern p = Pattern.compile(patternStr1);
        Matcher m = p.matcher(DateString);
        boolean b = m.matches();
        if (b)
            return DATAFORMAT_STR;
        p = Pattern.compile(patternStr2);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return DATATIMEF_STR;

        p = Pattern.compile(patternStr3);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATAFORMAT_STR;

        p = Pattern.compile(patternStr4);
        m = p.matcher(DateString);
        b = m.matches();
        if (b)
            return ZHCN_DATATIMEF_STR;
        return null;
    }

    /**
     * 将一个"yyyy-MM-dd HH:mm:ss"字符串，转换成"yyyy年MM月dd日HH时mm分ss秒"的字符串
     * @param dateStr
     * @return
     */
    public static String getZhCNDateTime(String dateStr)
    {
        Date d = getDate(dateStr);
        return dateToDateString(d, ZHCN_DATATIMEF_STR);
    }

    /**
     * 将一个"yyyy-MM-dd"字符串，转换成"yyyy年MM月dd日"的字符串
     * @param dateStr
     * @return
     */
    public static String getZhCNDate(String dateStr)
    {
        Date d = getDate(dateStr, DATAFORMAT_STR);
        return dateToDateString(d, ZHCN_DATAFORMAT_STR);
    }

    /**
     * 将dateStr从fmtFrom转换到fmtTo的格式
     * @param dateStr
     * @param fmtFrom
     * @param fmtTo
     * @return
     */
    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo)
    {
        Date d = getDate(dateStr, fmtFrom);
        return dateToDateString(d, fmtTo);
    }

    /**
     * 比较两个"yyyy-MM-dd HH:mm:ss"格式的日期，之间相差多少毫秒,time2-time1
     * @param time1
     * @param time2
     * @return
     */
    public static long compareDateStr(String time1, String time2)
    {
        Date d1 = getDate(time1);
        Date d2 = getDate(time2);
        return d2.getTime() - d1.getTime();
    }

    /**
     * 将小时数换算成返回以毫秒为单位的时间
     * @param hours
     * @return
     */
    public static long getMicroSec(BigDecimal hours)
    {
        BigDecimal bd;
        bd = hours.multiply(new BigDecimal(3600 * 1000));
        return bd.longValue();
    }

    /**
     * 获取Date中的分钟
     * @param d
     * @return
     */
    public static int getMin(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MINUTE);
    }

    /**
     * 获取Date中的小时(24小时)
     * @param d
     * @return
     */
    public static int getHour(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取Date中的秒
     * @param d
     * @return
     */
    public static int getSecond(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.SECOND);
    }

    /**
     * 获取xxxx-xx-xx的日
     * @param d
     * @return
     */
    public static int getDay(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取月份，1-12月
     * @param d
     * @return
     */
    public static int getMonth(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取19xx,20xx形式的年
     * @param d
     * @return
     */
    public static int getYear(Date d)
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        now.setTime(d);
        return now.get(Calendar.YEAR);
    }

    /**
     * 得到d的上个月的年份+月份,如200505
     * @return
     */
    public static String getYearMonthOfLastMon(Date d)
    {
        Date newdate = getInternalDateByMon(d, -1);
        String year = String.valueOf(getYear(newdate));
        String month = String.valueOf(getMonth(newdate));
        return year + month;
    }

    /**
     * 得到当前日期的年和月如200509
     * @return String
     */
    public static String getCurYearMonth()
    {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        String DATE_FORMAT = "yyyyMM";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    public static Date getNextMonth(String year, String month)
    {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, 1);
    }

    public static Date getLastMonth(String year, String month)
    {
        String datestr = year + "-" + month + "-01";
        Date date = getDate(datestr, DATAFORMAT_STR);
        return getInternalDateByMon(date, -1);
    }

    /**
     * 得到日期d，按照页面日期控件格式，如"2001-3-16"
     * @param d
     * @return
     */
    public static String getSingleNumDate(Date d)
    {
        return dateToDateString(d, "dd");
    }

    /**
     * 得到d半年前的日期,"yyyy-MM-dd"
     * @param d
     * @return
     */
    public static String getHalfYearBeforeStr(Date d)
    {
        return dateToDateString(getInternalDateByMon(d, -6), DATAFORMAT_STR);
    }

    /**
     * 得到当前日期D的月底的前/后若干天的时间,<0表示之前，>0表示之后
     * @param d
     * @param days
     * @return
     */
    public static String getInternalDateByLastDay(Date d, int days)
    {

        return dateToDateString(getInternalDateByDay(d, days), DATAFORMAT_STR);
    }

    /**
     * 日期中的年月日相加
     *  @param field int  需要加的字段  年 月 日
     * @param amount int 加多少
     * @return String
     */
    public static String addDate(int field, int amount)
    {
        int temp = 0;
        if (field == 1)
        {
            temp = Calendar.YEAR;
        }
        if (field == 2)
        {
            temp = Calendar.MONTH;
        }
        if (field == 3)
        {
            temp = Calendar.DATE;
        }

        String Time = "";
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.add(temp, amount);
            Time = sdf.format(cal.getTime());
            return Time;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获得系统当前月份的天数
     * @return
     */
    public static int getCurentMonthDay()
    {
        Date date = Calendar.getInstance().getTime();
        return getMonthDay(date);
    }

    /**
     * 获得指定日期月份的天数
     * @return
     */
    public static int getMonthDay(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    /**
     * 获得指定日期月份的天数  yyyy-mm-dd
     * @return
     */
    public static int getMonthDay(String date)
    {
        Date strDate = getDate(date, DATAFORMAT_STR);
        return getMonthDay(strDate);

    }

    public static String getStringDate(Calendar cal)
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }

    /**
    * 返回默认的实施时间的结束时间
    * @return
    */
    public static String getDefaultPEndDate(Date beginDate){
    	String pEndDateStr="";
    	int todayDay=0;
    	Calendar pEndDate= Calendar.getInstance();
    	if(beginDate!=null){
    		todayDay=getDay(beginDate);
    		pEndDate.setTime(beginDate);
    	}else{
    		 todayDay=getDay(Calendar.getInstance().getTime());
    	}
    	if(todayDay<20){
    		pEndDate.set(Calendar.DATE, 20);

    	}else{
    		pEndDate.add(Calendar.MONTH, 1);
    		pEndDate.set(Calendar.DATE, 20);
    	}
    	pEndDateStr=dateToDateString(pEndDate.getTime(),DATAFORMAT_STR);
    	return pEndDateStr;
    }

    /**
     * 返回日期的星期中文值
     * @param d
     * @return
     */
    public static String getDateWeekNoCn(Date d){
    	String dateWeekNoCn="";
    	String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
    	if(d!=null){
    		Calendar cal = Calendar.getInstance();
            cal.setTime(d);

            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;

            dateWeekNoCn= weekDays[w];
    	}
    	return dateWeekNoCn;
    }

    public static boolean isWeekend(Date d){
    	boolean isWeekend=false;
    	if(d!=null){
    		Calendar cal = Calendar.getInstance();
    	    cal.setTime(d);
    	    if(cal.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){
    	    	isWeekend=true;
    	    }
    	}

    	return isWeekend;
    }

    /**
     * 得到本月的第一天
     * @return
     */
    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));

        return getStringDate(calendar);
    }

    /**
     * 得到本月的最后一天
     *
     * @return
     */
    public static String getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return getStringDate(calendar);
    }

    /**
     * 获取某月最后一天
     * @param d
     * @return
     */
    public static String getLastCertainMonthDay(Date d){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(d);
    	calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return getStringDate(calendar);
    }



    public static int daysBetween(Date early, Date late){
    	 java.util.Calendar calst = java.util.Calendar.getInstance();
         java.util.Calendar caled = java.util.Calendar.getInstance();
         calst.setTime(early);
          caled.setTime(late);
          //设置时间为0时
          calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
          calst.set(java.util.Calendar.MINUTE, 0);
          calst.set(java.util.Calendar.SECOND, 0);
          caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
          caled.set(java.util.Calendar.MINUTE, 0);
          caled.set(java.util.Calendar.SECOND, 0);
         //得到两个日期相差的天数   
          int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst   
                 .getTime().getTime() / 1000)) / 3600 / 24;   
          
         return days;   
    }
    
    /**
     * 计算两个日期相差的月份
     * 如：2017-2-10    2017-6-20    返回   4
     * @param early
     * @param late
     * @return  -1:如果第二个参数小于第一个参数
     */
    public static int monthsBetween(Date early, Date late){
    	if(early.after(late)){
    		return -1;
//    		throw new IllegalArgumentException("后面的日期怎么能小于前面的日期呢？骚年，搞错了吧！[param1:"+early+"],[param2:"+late+"]");
    	}
    	Calendar calSd = Calendar.getInstance();
    	Calendar calEd = Calendar.getInstance();
    	calSd.setTime(early);
    	calEd.setTime(late);
    	return (calEd.get(Calendar.YEAR) - calSd.get(Calendar.YEAR))*12
    			+ (calEd.get(Calendar.MONTH) - calSd.get(Calendar.MONTH));
    }

    /**
     * 处理时间获取当前月和下月时间格式（yyyy-MM）
     * @param year
     * @param month
     * @return
     */
    public static Map<String,String> dealDate(String year, String month) {
        Map<String,String> map = new HashMap<>();
        String start = "";
        Calendar calendar = Calendar.getInstance();
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH) + 1;
        String end = "";
        if (StringUtils.isEmpty(year) || StringUtils.isEmpty(month)) {
            start  = year1 + "-0" + month1;
            if (month1 < 9) {
                end = year1 + "-0" + (month1 + 1);
            } else if (month1 == 9) {
                end = year1 + "-" + (month1 + 1);
            } else if (month1 == 12){
                end  = String.valueOf(Integer.valueOf(Integer.valueOf(year1) + 1)) + "-01";
            } else {
                start = year1 + "-" + month1;
                end = year1 + "-" + String.valueOf(Integer.valueOf(Integer.valueOf(month1) + 1));
            }
        } else {
            if(month.length()==1){
                start  = year + "-0" + month;
            }else{
                start = year + "-" + month;
            }
            if (Integer.valueOf(month) < 9) {
                end = year + "-0" + String.valueOf(Integer.valueOf(month) + 1);
            } else if (Integer.valueOf(month) == 9) {
                end = year + "-" + String.valueOf(Integer.valueOf(month) + 1);
            }else if (Integer.valueOf(month) == 12){
                end  = String.valueOf(Integer.valueOf(Integer.valueOf(year) + 1)) + "-01";
            } else {

                end = year1 + "-" + String.valueOf(Integer.valueOf(Integer.valueOf(month) + 1));
            }
        }
        map.put("start",start);
        map.put("end",end);
        return map;
    }

    /**
     * 获得该月第一天
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        //      //System.out.print(DateUtil.getDate("04:04:04","HH:mm:ss"));
        //      System.out.print("\n"+DateUtil.getCurZhCNDateTime());
        //      System.out.print("\n"+getFormateStr(DateUtil.getCurDate()));
        //      System.out.print("\n"+compareDateStr("1900-1-1 1:1:2","1900-1-1 1:1:3"));
        //      System.out.print("\n"+getDay(new Date()));
        //      System.out.print("\n"+getMonth(new Date()));
        //      System.out.print("\n"+getYear(new Date()));
        //      System.out.print("\n"+getMin(new Date()));
        ////        System.out.print("\n"+new Date().getSeconds());
        /*Date d1 = new Date(2007,11,30);
        Date d2 = new Date(2007,12,1);
        if(d2.compareTo(d1)>0){
            System.out.println("d2大于d1");
        }else{
            System.out.println("d2小于d1");
        }*/
 
//        System.out.println(addDate(1, 1));
//        System.out.println(addDate(2, 1));
//        System.out.println(addDate(3, 1));
         
//        System.out.println(getYYYYMMDDHHMMSSDate(new Date()));
         
        System.out.println(getFirstDayOfMonth());
        System.out.println(getSingleNumDate(new Date()));
        System.out.println(daysBetween(new Date(),DateUtil.getDate("2016-09-01","yyyy-mm-dd")));
        System.out.println(getLastCertainMonthDay(DateUtil.getDate("2016-08-01","yyyy-mm-dd")));
    }
     
}
