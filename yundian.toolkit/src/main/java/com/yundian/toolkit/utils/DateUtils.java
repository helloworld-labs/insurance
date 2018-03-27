package com.yundian.toolkit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: DateUtils
 * @Description: 时间utils,处理时间
 */
public class DateUtils {

	/**
	 * @param strDate
	 *            日期形式的字符串
	 * @return 返回yyyy-MM-dd格式的日期
	 * @throws ParseException
	 */
	public static Date strToDate(String strDate) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(strDate);
	}

	/**
	 * @param strDate
	 *            日期形式的字符串
	 * @param strFormat
	 *            日期格式化的字符串
	 * @return 返回要转换的日期格式的日期
	 * @throws ParseException
	 */
	public static Date strToDate(String strDate, String strFormat) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		return format.parse(strDate);
	}

	/**
	 * @param objDate
	 *            日期格式的对象
	 * @return 返回yyyy-MM-dd格式的日期
	 * @throws Exception
	 */
	public static Date objToDate(Object objDate) throws Exception {
		String dateString = objDate.toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.parse(dateString);
	}

	/**
	 * @param objDate
	 *            日期格式的对象
	 * @return 返回yyyy-MM-dd HH：mm:ss 格式的日期
	 * @throws Exception
	 */
	public static Date objToFullDate(Object objDate) throws Exception {
		String dateString = objDate.toString();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.parse(dateString);
	}

	/**
	 * @param iDate
	 *            日期
	 * @return 返回yyyy-MM-dd格式的日期字符串
	 */
	public static String format(Date iDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(iDate);
	}

	/**
	 * @param iDate
	 *            日期
	 * @param strFormat
	 *            日期格式化的字符串
	 * @return 返回格式化的日期字符串
	 */
	public static String format(Date iDate, String strFormat) {
		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		return format.format(iDate);
	}

	/**
	 * @param idate
	 *            日期
	 * @param strFormat
	 *            日期格式化的字符串
	 * @return 返回格式化的日期
	 * @throws ParseException
	 */
	public static Date formatDateToDate(Date idate, String strFormat) throws ParseException {
		String ddate = null;
		ddate = DateUtils.format(idate);
		return DateUtils.strToDate(ddate, strFormat);
	}

	/**
	 * @param idate
	 *            日期
	 * @return 返回yyyy-MM-dd格式的日期
	 * @throws ParseException
	 */
	public static Date formatToDefaultDate(Date idate) throws ParseException {
		String ddate = null;
		ddate = DateUtils.format(idate);
		return DateUtils.strToDate(ddate, "yyyy-MM-dd");
	}

	/**
	 * 将CST的时间字符串转换成需要的日期格式字符串<br>
	 * 
	 * @param cststr
	 *            The source to be dealed with. (exp:Fri Jan 02 00:00:00 CST
	 *            2009)
	 * @param fmt
	 *            The format string
	 * @return string or <code>null</code> if the cststr is unpasrseable or is
	 *         null return null,else return the string.
	 * @author HitSnail
	 */
	public static String getDateFmtStrFromCST(String cststr, String fmt) {
		if ((null == cststr) || (null == fmt)) {
			return null;
		}
		String str = null;
		SimpleDateFormat sdfy = new SimpleDateFormat(fmt.trim());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
		try {
			str = sdfy.format(sdf.parse(cststr.trim()));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return str;
	}

	/**
	 * 获取当日凌晨时间
	 */
	public static Date getToday() {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取当日结束时刻23:59:59.999
	 */
	public static Date getEndOfDay() {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定天数的某天的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 * 
	 */
	public static Date getStartOfDay(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60 * 1000l);
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取某天的开始时刻00:00:00.000
	 * 
	 * @param date
	 *            需要获取天内的时间
	 * @return Date java.util.Date
	 */
	public static Date getStartOfDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.HOUR_OF_DAY, 0);
		cld.set(Calendar.MINUTE, 0);
		cld.set(Calendar.SECOND, 0);
		cld.set(Calendar.MILLISECOND, 0);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定天数的某天的结束时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfDay(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 24 * 60 * 60 * 1000l);
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取某天的最后时刻23:59:59.999
	 * 
	 * @param date
	 *            需要获取天内的时间
	 * @return Date java.util.Date
	 */
	public static Date getEndOfDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.HOUR_OF_DAY, 23);
		cld.set(Calendar.MINUTE, 59);
		cld.set(Calendar.SECOND, 59);
		cld.set(Calendar.MILLISECOND, 999);
		return cld.getTime();
	}

	/**
	 * 获取距离现在指定星期数的某个星期的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60 * 1000l);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定星期的开始时刻00:00:00.000
	 * 
	 * @param week
	 *            一年中的第几周
	 * @param year
	 *            年份
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(int year, int week) {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.YEAR, year);
		cld.set(Calendar.WEEK_OF_YEAR, week);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离指定日期指定星期数的某个星期的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @param date
	 *            日期
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(Date date, int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTimeInMillis(cld.getTimeInMillis() + interval * 7 * 24 * 60 * 60 * 1000l);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在周的第一天的00:00:00.000
	 * 
	 * @param date
	 *            需要获取周的某天
	 * @return Date java.util.Date
	 */
	public static Date getStartOfWeek(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定周数的某周的最后时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(getStartOfWeek(interval + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取指定周数的最后时刻23:59:59.999
	 * 
	 * @param week
	 *            一年中的第几周
	 * @param year
	 *            年份
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(int year, int week) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(getStartOfWeek(year, week + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取距离指定日期指定周数的某周的最后时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @param date
	 *            日期
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(Date date, int interval) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTime(getStartOfWeek(date, interval + 1));
		return new Date(cld.getTimeInMillis() - 1);
	}

	/**
	 * 获取指定时间所在周的最后一天的23:59:59.999
	 * 
	 * @param date
	 *            需要获取周的某天
	 * @return Date java.util.Date
	 */
	public static Date getEndOfWeek(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return getEndOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在月的第一天的00:00:00.000
	 * 
	 * @param date
	 *            需要获取月的某天
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取某年某月的开始时刻00:00:00.000
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(int year, int month) {
		Calendar cld = Calendar.getInstance();
		cld.set(Calendar.YEAR, year);
		cld.set(Calendar.MONTH, month);
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定月数的某月的开始时刻00:00:00.000
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getStartOfMonth(int interval) {
		Calendar cld = Calendar.getInstance();
		int currentMonth = cld.get(Calendar.MONTH); // 这里得到的值是0～11
		cld.add(Calendar.YEAR, ((interval + currentMonth) / 12));
		cld.set(Calendar.MONTH, ((interval + currentMonth) % 12));
		cld.set(Calendar.DAY_OF_MONTH, 1);
		return getStartOfDay(cld.getTime());
	}

	/**
	 * 获取指定时间所在月的最后一天的23:59:59.999
	 * 
	 * @param date
	 *            需要获取月的某天
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		int maxDay = cld.getActualMaximum(Calendar.DAY_OF_MONTH);
		cld.set(Calendar.DAY_OF_MONTH, maxDay);
		return getEndOfDay(cld.getTime());
	}

	/**
	 * 获取距离现在指定月数的某月的结束时刻23:59:59.999
	 * 
	 * @param interval
	 *            间隔
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(int interval) {
		return new Date(getStartOfMonth(interval + 1).getTime() - 1);
	}

	/**
	 * 获取某年某月的结束时刻23:59:59.999
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return Date java.util.Date
	 */
	public static Date getEndOfMonth(int year, int month) {
		return new Date(getStartOfMonth(year, month + 1).getTime() - 1);
	}

	/**
	 * 获取2个时间点之间的月份数
	 * 
	 * @param startDate
	 *            endDate
	 * @return int
	 */
	public static int monthsBetween(Date startDate, Date endDate) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(startDate);
		int startMonth = cld.get(Calendar.MONTH);
		int startYear = cld.get(Calendar.YEAR);
		cld.setTime(endDate);
		int endMonth = cld.get(Calendar.MONTH);
		int endYear = cld.get(Calendar.YEAR);
		return (endYear - startYear) * 12 + (endMonth - startMonth);
	}

	/**
	 * 获取2个时间点之间的天数
	 * 
	 * @param startDate
	 *            sendDate
	 * @return int
	 */
	public static int daysBetween(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		return daysBetween(c1, c2);
	}

	/**
	 * 计算两个日期之间的天数
	 * 
	 * @param early
	 *            日期
	 * @param late
	 *            日期
	 * @return
	 */
	public static int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

	/**
	 * 
	 * @param c
	 *            日期
	 * @return
	 */
	public static final float toJulian(Calendar c) {
		int Y = c.get(Calendar.YEAR);
		int M = c.get(Calendar.MONTH);
		int D = c.get(Calendar.DATE);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (int) (365.25f * (Y + 4716));
		float F = (int) (30.6001f * (M + 1));
		float JD = (C + D + E + F) - 1524.5f;
		return JD;
	}

	/**
	 * 根据出生日期获得年龄
	 * 
	 * @param birthday
	 *            生日
	 * @return int 年龄
	 */
	public static int getAge(Date birthday) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(birthday);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		int age = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		return age < 0 ? 0 : age;
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 * @param day
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setTimeInMillis(cld.getTimeInMillis() + ((long) day) * 24 * 3600 * 1000);
		return cld.getTime();
	}

	/**
	 * 获取当前日期时间
	 * 
	 * @return 返回现在日期时间
	 */
	public static Date getCurDate() {
		// SimpleDateFormat formatter = new SimpleDateFormat
		// ("yyyy年MM月dd日   HH:mm:ss     ");
		Date curDate = new Date(System.currentTimeMillis());
		// String str = formatter.format(curDate);
		return curDate;
	}

	/**
	 * 获取当前日期的短日期形式
	 * 
	 * @return
	 */
	public static String GetNowShortDate() {
		Date dt = new Date();
		return format(dt);
	}

	/**
	 * 获取日期加减运算
	 * 
	 * @return
	 */
	public static String addSubtractDate(Date date, int y, int m, int d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, y);// 日期减y年
		calendar.add(Calendar.MONTH, m);// 日期加X个月
		calendar.add(Calendar.DAY_OF_YEAR, d);// 日期加d天
		Date dt = calendar.getTime();
		String reStr = sdf.format(dt);
		return reStr;
	}

	/**
	 * 获取时间的开始时间
	 * 
	 * @return Date
	 */
	public static Date getHourStartTime(Date hourDate) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (hourDate != null) {
			calendar.setTime(hourDate);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 获取时间的结束时间
	 * 
	 * @return Date
	 */
	public static Date getHourEndTime(Date hourDate) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (hourDate != null) {
			calendar.setTime(hourDate);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 时间操作-月加减
	 * 
	 * @return Date
	 */
	public static Date addMonth(Date date, int monthnum) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.add(Calendar.MONTH, monthnum);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 返回格式 201607
	 * @param 
	 * @return
	 */
	public static String getStrDate() {
		return getStrDate(0);
	}
	
	/**
	 * 返回格式 201607
	 * 
	 * @param monthNum相比于当前月的月数前一个月传入-1，当前月0，后一个月1，如果传入null默认取当月的数据
	 * @return
	 */
	public static String getStrDate(Integer monthNum) {
		if (monthNum == null) {
			monthNum = 0;
		}
		String strMonth = null;
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1 + monthNum;
		int year = cal.get(Calendar.YEAR);
		if (month < 10) {
			strMonth = year + "0" + month;
		} else {
			strMonth = year + String.valueOf(month);
		}
		return strMonth;
	}
	
	/**
	 * 时间操作-日加减
	 * 
	 * @return Date
	 */
	public static Date addDays(Date date, int daynum) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.add(Calendar.DAY_OF_YEAR, daynum);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 时间操作-时加减
	 * 
	 * @return Date
	 */
	public static Date addHours(Date date, int hournum) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.add(Calendar.HOUR_OF_DAY, hournum);
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 时间操作-分加减
	 * 
	 * @return Date
	 */
	public static Date addsMinute(Date date, int minutenum, int flag) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
		}
		calendar.add(Calendar.MINUTE, minutenum);
		if (flag == 0) {
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		} else if (flag == 1) {
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
		}
		Date resultDate = calendar.getTime();
		return resultDate;
	}

	/**
	 * 时间操作-时间戳转Date
	 * 
	 * @return Date
	 */
	public static Date timestampToDate(Long timestamp) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		Date olddate = new Date(timestamp); // 根据long类型的毫秒数生命一个date类型的时间
		String strdate = formatString(olddate, pattern);
		Date resultDate = parseDate(strdate, pattern);
		return resultDate;
	}

	public static Date parseDate(String dateString, String pattern) {
		try {
			if (StringUtils.isNotBlank(dateString) && StringUtils.isNotBlank(pattern)) {
				SimpleDateFormat sdf = new SimpleDateFormat(pattern);
				return sdf.parse(dateString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatString(Date date, String pattern) {
		if (date != null && StringUtils.isNotBlank(pattern)) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return null;
	}

}
