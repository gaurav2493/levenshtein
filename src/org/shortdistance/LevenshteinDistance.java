/**
 * LevenshteinDistance is a class that allows an application to implement Levenshtein Distance algorithm to find the shortest distance between two words.
 * It can also help in comparing a word with a list of different words to find the nearest matching word.
 * 
 * A small example --
 * 
 * 		List list=new ArrayList();
 *		list.add("hello");
 *		list.add("bye");
 *		list.add("Good night");
 *		LevenshteinDistance lev=new LevenshteinDistance("helo",list);
 *		System.out.println(lev.computeNearestWord());
 *
 *In this example, the nearest word which matches `helo` in the list `hello` gets printed.
 * 
 * @author Gaurav Sharma
 * @version 1.0
 */

package org.shortdistance;

import java.util.Arrays;
import java.util.List;


public class LevenshteinDistance implements DistanceCalculator {
	
	private String inputString;
	private List<String> compareStringList;
	
	/**
	 * 
	 * @param inputString Contains the String to be compared
	 * @param compareStringContains the list of Strings to be compared with inputString
	 */
	
	public LevenshteinDistance(String inputString,String compareString)
	{
		/**
		 * This constructor can be used to create an object with only 1 String to compare with.
		 * Furthur, {@code addToCompareList} method can be used to add more Strings for comparison 
		 */
		this.inputString=inputString;
		this.compareStringList.add(compareString);
	}
	
	public LevenshteinDistance(String inputString,String compareString[])
	{
		this.inputString=inputString;
		compareStringList=Arrays.asList(compareString);
	}
	
	public LevenshteinDistance(String inputString,List<String> compareList)
	{
		this.inputString=inputString;
		this.compareStringList=compareList;
	}
	
	public String computeNearestWord() throws InvalidTypeException
	{
		if(compareStringList.size()<=1)
			throw new InsufficientParameterException();
		try{
			return compareStringList.get(computeNearestWordIndex(inputString,compareStringList));
		}catch(java.lang.ClassCastException e)
		{
			throw new InvalidTypeException();
		}
	}
	
	public static int computeNearestWordIndex(String inputString, String compareString[])
	{
		int temp,distance=99,index=-1;
		for(int i=0;i<compareString.length;i++)
		{
			temp=computeDistance(inputString,compareString[i]);
			if(temp<=distance)
			{
				distance=temp;
				index=i;
			}
		}
		return index;
	}
	
	public static int computeNearestWordIndex(String inputString, List<String> compareStringList)
	{
		int temp,distance=99,index=-1;
		for(int i=0;i<compareStringList.size();i++)
		{
			temp=computeDistance(inputString,compareStringList.get(i));
			if(temp<=distance)
			{
				distance=temp;
				index=i;
			}
		}
		return index;
	}
	
	public static int[] computeAllDistances(String inputString, List<String> compareStringList)
	{
		if(compareStringList.size()==0)
			throw new InsufficientParameterException();
		int distances[]=new int[compareStringList.size()];
		for(int i=0;i<compareStringList.size();i++)
		{
			distances[i]=computeDistance(inputString,compareStringList.get(i));
		}
		return distances;
	}
	
	public static int[] computeAllDistances(String inputString, String[] compareString)
	{
		int distances[]=new int[compareString.length];
		for(int i=0;i<compareString.length;i++)
		{
			distances[i]=computeDistance(inputString,compareString[i]);
		}
		return distances;
	}
	
	public static int computeDistance(String inputString,String compareString) {
	 try{
	    int[] costs = new int[compareString.length() + 1];
	    for (int i = 0; i <= inputString.length(); i++) {
	      int lastValue = i;
	      for (int j = 0; j <= compareString.length(); j++) {
	        if (i == 0)
	          costs[j] = j;
	        else {
	          if (j > 0) {
	            int newValue = costs[j - 1];
	            if (inputString.charAt(i - 1) != compareString.charAt(j - 1))
	              newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
	            costs[j - 1] = lastValue;
	            lastValue = newValue;
	          }
	        }
	      }
	      if (i > 0)
	        costs[compareString.length()] = lastValue;
	    }
	    
	    return costs[compareString.length()];
	 }catch(NullPointerException e)
	 {
		 throw new NullStringObjectException();
	 }
	    
	  }

	@Override
	public void addToCompareList(String compareWord)
	{
		if(compareWord==null || compareWord.length()<=1)
			throw new TooShortStringException();
		compareStringList.add(compareWord);
	}

	@Override
	public void addToCompareList(List<String> compareList) 
	{		
		compareStringList.addAll(compareList);		
	}

	@Override
	public int computeShortestDistance() {
		int temp,distance=99;
		for(int i=0;i<compareStringList.size();i++)
		{
			temp=computeDistance(inputString,compareStringList.get(i));
			if(temp<=distance)
			{
				distance=temp;
			}
		}
		return distance;
	}	
}