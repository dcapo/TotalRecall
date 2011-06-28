package edu.stanford.jdiprete;

public class SMSDate {

	private int day;
	private int month;
	private int year;
	
	public SMSDate(int d_year, int d_month, int d_day)
	{
		day = d_day;
		month = d_month;
		year = d_year;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getYear()
	{
		return year;
	}
	

}
