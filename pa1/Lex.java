//----------------------------------------------------
//Author: Cameron Peeters
//Cruz ID: 1675143
//Professor: Patrick Tantalo
//Class: CMPS101 - Algorithms and Abstract Data Types 
//Project: Programming Assignment 1 
//Date: 4/11/2019
//File: Lex.java
//----------------------------------------------------

/**Abstract:
*
* Lex Does the following:
*
* Checks that there are two command line arguments.
* Quits with a usage message to stderr if more than or less than two
* strings are given on the command line
*
* Counts the number of lines n in the file named by args[0]. 
* Creates a String array of length n and read in the lines of the file as Strings, 
* placing them into the array.
*
* Creates a List whose elements are the indices of the above String array. 
* These indices should be arranged in an order that effectively sorts the array.
*
**/

import java.io.*;
import java.util.Scanner;

public class Lex 
{
	public static void main(String args[]) throws IOException
	{
		//FileIO from fileIO.java example
		int n = 0;
		int lineNumber = 0;

		PrintWriter out   = null;
		Scanner in        = null;

	    String line       = null;
		String [] array   = null;
		String [] lines;
		
	    if(args.length < 2)
	    {
	    	System.err.println("Usage: FileIO infile outfile");
	        System.exit(1);
	    }
	    
	    in = new Scanner(new File(args[0]));

	    while( in.hasNextLine())
	    {
	    	n++; 
	    	in.nextLine();
	    }
	    in.close();

	    in = new Scanner(new File(args[0]));
	    lines = new String[n];

	    for (int i = 0; i < n; i++)
	    {
	    	lines[i] = in.nextLine();
	    }
	    in.close();
	    
	    //list of array indices to be sorted 
		List list = new List("Lexicongraphic");
		list.prepend(0); 
		
        //from insertion sort
        int j;
        String word;
        for (int i = 1; i < n; i++) 
        {
            list.moveBack();  
            j = list.get();


            //String temp = lines.get(list.get());
            while (lines[i].compareTo(lines[j]) <= 0) 
            {
            	list.movePrev();
            	if (list.index() == -1)
            	{
            		list.prepend(i);
            		break;
            	}
            	j = list.get();
            }

            if (list.index() >= 0) 
            {
            	list.insertAfter(i);
            } 
        }
        
        System.out.println("Indices: " + list);
        System.out.print("Lexicon: "); 

        // out to file
        out = new PrintWriter(new FileWriter(args[1]));
        list.moveFront();

        for (int i = 0; i < n; i++)
	    {
	    	out.println(lines[list.get()]);
	    	System.out.print(lines[list.get()] + " ");
	    	list.moveNext();

	    }
	    out.close();
	}
}
