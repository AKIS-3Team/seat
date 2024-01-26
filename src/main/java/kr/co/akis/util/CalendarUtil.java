/**
 * ================================================================================================
 * Copyright (c) 2003 JJang (Seon-joo, Jang)
 * Licensed under the MIT license (http://www.opensource.org/licenses/MIT)
 * 
 * @Author      : Seon-joo, Jang (jeuse7@gmail.com)
 * @File Name   : CalendarUtil.java
 * @Description : 달력 클래스.
 * @Version     : 3.0.0
 * @History     : [2003.12.22] 장선주 - 최초 생성.
 *                [2019.07.07] 장선주 - 전체 정리.
 * ================================================================================================
 */
package kr.co.akis.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	
	private Calendar calendar = Calendar.getInstance();
	public int year;      // 년도
	public int month;     // 월
	public int day;       // 일
	public int dow;       // 요일
	public int lastDay;   // 마지막 일
	public int lastWeek;  // 마지막 주
	
	/**
	 * <p>Constructor</p>
	 */
	public CalendarUtil() {
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		this.initialize();
	}
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param date (Date Object)
	 */
	public CalendarUtil(Date date) {
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		this.initialize();
	}
	
	/**
	 * <p>Constructor</p>
	 * 
	 * @param year  (년도)
	 * @param month (월)
	 */
	public CalendarUtil(int year, int month) {
		calendar.set(year, month - 1, 1);
		this.initialize();
	}
	
	/**
	 * <p>Class initialize</p>
	 */
	public void initialize() {
		this.year = calendar.get(Calendar.YEAR);
		this.month = calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DATE);
		this.dow = calendar.get(Calendar.DAY_OF_WEEK);
		this.lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, lastDay);
		this.lastWeek = calendar.get(Calendar.WEEK_OF_MONTH);
		calendar.set(Calendar.DATE, 1);
	}
	
	/**
	 * <p>해당 날짜의 달력을 구해서 리턴한다.</p>
	 * 
	 * @return String[][] (달력 배열)
	 */
	public String[][] getCalendar() {
		String[][] dayArr = new String[this.lastWeek][7];
		int dayCnt = 1;
		for (int i = 0; i < this.lastWeek; i++) {
			for (int j = 0; j < 7; j++) {
				if ((this.dow - 1) > 0 || dayCnt > this.lastDay) { // 시작일 전과 마지막일 후는  공백으로 처리
					dayArr[i][j] = "";
					this.dow--;
					continue;
				} else {
					dayArr[i][j] = String.valueOf(dayCnt);
				}
				dayCnt++;
			}
		}
		return dayArr;
	}
	
}