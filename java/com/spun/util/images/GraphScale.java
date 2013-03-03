package com.spun.util.images;

import com.spun.util.MySystem;
import java.util.Date;
/**
  * An Interface for graphic objects
  **/
public class GraphScale
{
	public static final double VALUE_UNITS[] = {0.01,0.05,0.10,0.25,0.5,
																				 	 1, 2, 5,	10, 20, 100,200,1000};
	private double minValue = 0.0;
	private double maxValue = 0.0;
  private double valueUnit = 0.0;
	private int valueUnitCount = 0;
  /***********************************************************************/
  /**
    * 
    **/
  public GraphScale(Date minDate, Date maxDate, double minValue,double maxValue)
  {
		setValueUnits(minValue, maxValue);
  }
	
  /***********************************************************************/
	private void setValueUnits(double minValue,double maxValue)
	{
		double range = maxValue - minValue;
		double unit = .01;
		double unitCount = range / .01;
		for (int i = 0; i < VALUE_UNITS.length; i++)
		{
			double tempCount = (range / VALUE_UNITS[i]);
			if (tempCount  >= 4)
			{
				if (tempCount < unitCount)
				{
					unitCount = tempCount;
					unit = VALUE_UNITS[i];
				}
			}
		}
		this.valueUnit = unit;
		this.minValue = minValue - (minValue % unit);
		this.maxValue = maxValue + unit - (maxValue % unit); 
		this.valueUnitCount = (int) Math.round((maxValue - minValue) / unit);
	}
  /***********************************************************************/
  /**
    *
    **/
  public String toString()
  {
		String text = "GraphScale [" + minValue + "-" + maxValue + "(" + valueUnit + "*" + valueUnitCount + "]";
		return text;
  }
  /***********************************************************************/
	public static void main(String args[])
	{
		MySystem.variable(new GraphScale(null,null, 8, 15).toString());
	}
  /***********************************************************************/
  /***********************************************************************/
}
