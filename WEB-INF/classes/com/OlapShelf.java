package com;
import java.lang.*;

public class OlapShelf
{
    private int img_count;
    private String year;
    private String month;
    private int week;

    public OlapShelf(int i, String y, int m, int w)
    {
	img_count = i;
	year = y;
	week = w;
	switch(m)
	    {
	    case 1:
		{
		    month = "Jan";
		    break;
		}
	    case 2:
		{
		    month = "Feb";
		    break;
		}
	    case 3:
		{
		    month = "Mar";
		    break;
		}
	    case 4:
		{
		    month = "Apr";
		    break;
		}
	    case 5:
		{
		    month = "May";
		    break;
		}
	    case 6:
		{
		    month = "Jun";
		    break;
		}
	    case 7:
		{
		    month = "Jul";
		    break;
		}
	    case 8:
		{
		    month = "Aug";
		    break;
		}
	    case 9:
		{
		    month = "Sep";
		    break;
		}
	    case 10:
		{
		    month = "Oct";
		    break;
		}
	    case 11:
		{
		    month = "Nov";
		    break;
		}
	    case 12:
		{
		    month = "Dec";
		    break;
		}
	    default:
		month = "None";
	    }
    }

    public String getYear()
    {
	return year;
    }

    public String getMonth()
    {
	return month;
    }

    public String getWeek()
    {
	if(Integer.toString(week).length() < 2)
	    {
		return "0" + Integer.toString(week);
	    }
	else
	    return Integer.toString(week);
    }

    public String getCount()
    {
	return Integer.toString(img_count);
    }
}
