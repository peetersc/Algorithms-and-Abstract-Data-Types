//-------------------------------------------------------------------------\\
//Author: Cameron Peeters
//Cruz ID: 1675143
//Professor: Patrick Tantalo
//Class: CMPS101 - Algorithms and Abstract Data Types 
//Project: Programming Assignment 3 
//Date: 4/29/2019
//File: Sparse.java
//-------------------------------------------------------------------------\\

/**Abstract:
*
* Takes two program arguments file in and file out.
* File in is the sparse matrix formatted as following:
*		   395    
*
*          1 1 1.0
*          1 2 2.0
*          1 3 3.0
*          2 1 4.0
*          2 2 5.0
*          2 3 6.0
*          3 1 7.0
*          3 2 8.0
*          3 3 9.0
*
*          1 1 1.0
*          1 3 1.0
*          3 1 1.0
*          3 2 1.0
*          3 3 1.0
*
* As to represent:
*			A = [1.0 2.0 3.0]	and 	B = [1.0 0.0 1.0]
*				[4.0 5.0 6.0]				[0.0 0.0 0.0]
*				[7.0 8.0 9.0]				[1.0 1.0 1.0]
*
* File out is the output of the operations performed on A and B.
**/

import java.io.*;
import java.util.Scanner;

public class Sparse {
	
	public static void main(String[ ] args) throws IOException
	{
		// Check file arguments
	    if(args.length < 2){
	        System.err.println("Usage: FileIO infile outfile");
	        System.exit(1);
	    }

	    // File objects
	    Scanner     in  = new Scanner(new File(args[0]));
        PrintWriter out = new PrintWriter(new FileWriter(args[1]));

        // Matrices dimensions read from file
        int size     = in.nextInt();
        int rowSizeA = in.nextInt();
        int rowSizeB = in.nextInt();

        // Matrix A and Matrix B
        Matrix A = new Matrix(size);
        Matrix B = new Matrix(size);

        // Populate Matrix A
        for (int i = 0; i < rowSizeA; i++) {
			int    row = in.nextInt();
			int    col = in.nextInt();
			double val = in.nextDouble();
			A.changeEntry(row,col,val);
		}

        // Populate Matrix B
        for (int i = 0; i < rowSizeB; i++) {
			int    row = in.nextInt();
			int    col = in.nextInt();
			double val = in.nextDouble();
			B.changeEntry(row,col,val);
		}
		// close in file
		in.close();

		// write out to file
		out.println("A has " + rowSizeA + " non-zero entries:");
		out.println(A);

		out.println("B has " + rowSizeB + " non-zero entries:");
		out.println(B);

		out.println("(1.5)*A =");
		out.println(A.scalarMult(1.5));

		out.println("A+B =");
		out.println(A.add(B));

		out.println("A+A =");
		out.println(A.add(A));

		out.println("B-A =");
		out.println(B.sub(A));

		out.println("A-A =");
		out.println(A.sub(A));

		out.println("Transpose(A) =");
		out.println(A.transpose());

		out.println("A*B =");
		out.println(A.mult(B));

		out.println("B*B =");
		out.println(B.mult(B));

		// close out file
		out.close();

		return;
	}
}