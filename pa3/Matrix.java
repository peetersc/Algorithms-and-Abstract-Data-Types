//-------------------------------------------------------------------------\\
//Author: Cameron Peeters
//Cruz ID: 1675143
//Professor: Patrick Tantalo
//Class: CMPS101 - Algorithms and Abstract Data Types 
//Project: Programming Assignment 3 
//Date: 4/29/2019
//File: Matrix.java
//-------------------------------------------------------------------------\\

/**Abstract:
*
* The Matrix ADT is a calculator for performing matrix operations that 
* exploits the (expected) sparseness of it’s matrix operands that is
* capable of performing fast matrix operations, even on very large  
* matrices, provided they are sparse.
*
* Sparce: An 𝑛 × 𝑛 square matrix is said to be sparse if the number of
*         non-zero entries (abbreviated NNZ) is small compared to the  
*         total number of entries, 𝑛2
*
**/

public class Matrix
{
    /* Private Class ------------------------------------------------------ */

    /**Entry
    *  Private inner class of Matrix that holds Entry data for the
    *  matrix (i.e) column and data
    */
    private class Entry
    {
        // Entry fields
        public int    column;
        public double data;
        
        // Entry Constructor
        Entry(int column, double data){this.column = column; this.data = data;}

        // Overides the default toString method
        public String toString()
        {
            return("("+String.valueOf(column)+", "+ String.valueOf(data)+")");
        }

        // equals(): overrides Entry's equals() method
        public boolean equals(Object x)
        {
            boolean eq    = false;
            Entry   that;

            if(x instanceof Entry)
            {
                that = (Entry)x;
                eq   = (this.data   == that.data)  && 
                       (this.column == that.column);
            }
            return eq;
        }
    }

    /* Fields ------------------------------------------------------------- */
    
    private List[] row; 
    private int    NNZ; //number of non-zero entries

    /* Constructor -------------------------------------------------------- */
    
    /**Matrix
    *  Makes a new n x n zero Matrix.          pre-Condition: n>=1
    */
    Matrix(int n) 
    {
        if (!(n >= 1)){
            throw new RuntimeException("Matrix Error: Matrix() !(n >= 1)");
        }

        row = new List[n+1];
        NNZ    = 0;

        for(int i=1; i<n+1; i++) {
            row[i] = new List(String.valueOf(i));
        }
    }

    /* Access Functions --------------------------------------------------- */

    /**isEmpty
    *  Returns true if Matrix is empty
    */
    boolean isEmpty()
    {
        boolean eq = false;

        for (int i = 1; i < getSize() && row[i].isEmpty(); i++)
        {
            eq = (row[i].isEmpty());
        }

        return eq;
    }

    /**getSize
    *  Returns n, is the number of rows and columns of this Matrix
    */
    int getSize() 
    {
        return (row.length - 1);
    }

    /**getNNZ
    *  Returns the number of non-zero entries in this Matrix
    */
    int getNNZ() 
    {
        return NNZ;
    }

    /**equals
    *  overrides Object's equals() method
    */
    public boolean equals(Object x) 
    {
      boolean eq  = false;
      Matrix  Q;

      if(x instanceof Matrix)
      {
        Q = (Matrix)x;

        // compare lengeth and number of non-zeros
        eq = (getSize() == Q.getSize()) && 
             (getNNZ()  == Q.getNNZ());

        for (int i = 1; eq && (i <= getSize() && i <= Q.getSize()); i++)
        { 
            // check for non empty and then move to front
            if(!row[i].isEmpty())
            {
                row[i].moveFront();
            }
            if(!Q.row[i].isEmpty())
            {
                Q.row[i].moveFront();
            }

            // for the length check if the elements are equal
            for (int j = 1; row[i].index() != -1 && Q.row[i].index() != -1; j++)
            {
                Entry e1 = (Entry)   row[i].get();
                Entry e2 = (Entry) Q.row[i].get();

                eq = e1.equals(e2);

                  row[i].moveNext();
                Q.row[i].moveNext();
            }
        }
      }
      return eq;
    }

    /* Manipulation procedures -------------------------------------------- */
    
    /**makeZero
    *  sets this Matrix to the zero state
    */
    void makeZero() 
    {
        NNZ = 0;
        for (int i = 1; i <= getSize(); i++)
        {
            row[i].clear();
        }
    }

    /**copy
    *  Returns a new Matrix having the same entries as this Matrix
    */
    Matrix copy()
    {   

        Matrix copy  = new Matrix(getSize());
        Entry  entry = null;


        if (isEmpty())
        {
            return copy;
        }

        // loop through each row
        for( int i = 1; i <= getSize(); i++) 
        {
            if (!row[i].isEmpty())
            {
                 row[i].moveFront();

            }else continue;
            
            // loop through each element and copy entries
            while(row[i].index() >= 0)
            { 
                entry = (Entry) row[i].get();
                copy.changeEntry(i, entry.column, entry.data);
                row[i].moveNext();
            }
        }
        return copy;
    }

    /**changeEntry
    *  changes ith row, jth column of this Matrix to x
    *  pre: 1<=i<=getSize(), 1<=j<=getSize()
    */
    void changeEntry(int i, int j, double x)
    {

        // Pre-conditions
        if((i <= 1 && i >= getSize()) || (j <= 1 && j >= getSize()))
        {
            throw new RuntimeException(
                    "Matrix Error: changeEntry() !(1<=i<=getSize() || 1<=j<=getSize()).");
        }  

        // Four cases
        /* 1) Aij  = 0 and x  = 0 (do nothing)
        *  2) Aij  = 0 and x != 0 (insert or append)
        *  3) Aij != 0 and x  = 0 (delete col j)
        *  4) Aij != 0 and x != 0 (Overwrite data with x)
        */ 

        // Etry object for columb and data
        Entry entry = new Entry(j, x);
        List Row = row[i];

        //case 1
        if (Row.isEmpty()) 
        {
            if (x != 0)
            {
                Row.append(entry);
                NNZ++;
            }
            return;
        } 
        else
        {
            // Set cursor 
            if (Row.length() >= 0)
            {
                Row.moveFront();
            }
            else
            {
                return;
            }

            for (int k = 1; k <= j; k++) 
            {
                entry = (Entry) Row.get();

                if (entry.column == j) 
                {
                    //case 3
                    if (x == 0) 
                    {
                        Row.delete();
                        NNZ--;
                        return;
                    } 
                    //case 4
                    else 
                    {
                        //overwrite data
                        entry.data = x;
                        return;
                    }
                }
                //case 2
                else if (entry.column > j) 
                {
                    if (x == 0) 
                    {
                        return;
                    }
                    else
                    {
                        Row.insertBefore(new Entry(j, x));
                        NNZ++;
                        return;
                    }
                }
                else
                {
                    Row.moveNext();
                    if (Row.index() == -1) 
                    {
                        if (x == 0) 
                        {
                            return;
                        }
                        else
                        {
                            Row.append(new Entry(j, x));
                            NNZ++;
                            return;
                        }
                    }
                }
            }
        }
    }

    /**scalarMult
    *  Returns new Matrix that is the scalar product of this Matrix
    */
    Matrix scalarMult(double x)
    {
        Matrix product = new Matrix(getSize());

        for( int i = 1; i <= getSize(); i++) 
        {
            if (!row[i].isEmpty())
            {
                row[i].moveFront();

            }else continue;

            while(row[i].index() >= 0)
           {
                Entry entry = (Entry) row[i].get();
                product.changeEntry(i, entry.column, entry.data*x);
                row[i].moveNext();
            }
        }
        return product;
    }

    /**add
    *  Returns a new Matrix that is the sum of this Matrix with M
    *  pre: getSize()==M.getSize()
    */
    Matrix add(Matrix Q)
    {
        // Pre-conditions
        if (this.getSize() != Q.getSize())
        {
            throw new RuntimeException(
                    "Matrix Error: add() !(this.getSize() == M.getSize()).");
        }

        // new return matrix sum
        Matrix sum = new Matrix(this.getSize());
        //Matrix Q = N.copy();

        //new entries
        Entry e1 = null;
        Entry e2 = null;

        // For improved eficiency check equals
        if (this.equals(Q))
        {
            return this.scalarMult(2);
        }

        // Loop through all rows of the matrix
        for (int i = 1; i <= getSize() || i <= Q.getSize(); i++)
        { 

            // check if emety and move to front 
            if (!row[i].isEmpty())
            {
                row[i].moveFront();
            }

            if(!Q.row[i].isEmpty())
            {
                Q.row[i].moveFront();
            }
            
            // Loop through all elements of the matrix rows
            while(row[i].index() != -1 || Q.row[i].index() != -1)
            {

                e1 = null;
                e2 = null;

                // set enteries e1,e2 to the row element
                if (row[i].index() != -1)
                {
                     e1 = (Entry) row[i].get();
                }
                
                if (Q.row[i].index() != -1)
                {
                     e2 = (Entry) Q.row[i].get();
                }

                // if not null add elements together
                if (e1 != null && e2!=null)
                {
                    if (e1.column == e2.column)
                    {
                        sum.changeEntry(i, e1.column, e1.data + e2.data);
                        Q.row[i].moveNext();
                        row[i].moveNext();
                    }
                    else if(e1.column < e2.column) 
                    {
                        sum.changeEntry(i, e1.column, e1.data);
                        row[i].moveNext();
                    } 
                    else if(e1.column > e2.column) 
                    {
                        sum.changeEntry(i, e2.column, e2.data);
                        Q.row[i].moveNext();
                    } 
                }

                // else if one element is null change entry of non null entries
                if (e1 != null && e2 == null)
                {
                    sum.changeEntry(i, e1.column, e1.data);
                    row[i].moveNext();
                }
                if (e1 == null && e2 != null)
                {
                    sum.changeEntry(i, e2.column, e2.data);
                    Q.row[i].moveNext();
                }
            }
        }

        return sum;
    }

    /**sub
    *  Returns a new Matrix that is the difference of this Matrix
    *  pre: getSize()==M.getSize()
    */
    Matrix sub(Matrix Q)
    {
        // Pre-conditions
        if (this.getSize() != Q.getSize())
        {
            throw new RuntimeException(
                    "Matrix Error: sub() !(this.getSize() == M.getSize()).");
        }
        // new return matrix sum
        Matrix diff = new Matrix(this.getSize());
        //Matrix Q = N.copy();

        //new entries
        Entry e1 = null;
        Entry e2 = null;


        // improve efficency
        if (this.equals(Q))
        {
            diff = Q.copy();
            diff.makeZero();
            return diff;
        }
        // Loop through all rows of the matrix
        for (int i = 1; i <= getSize() || i <= Q.getSize(); i++)
        { 

            // check if emety and move to front 
            if (!row[i].isEmpty())
            {
                row[i].moveFront();
            }

            if(!Q.row[i].isEmpty())
            {
                Q.row[i].moveFront();
            }
            
            // Loop through all elements of the matrix rows
            while(row[i].index() != -1 || Q.row[i].index() != -1)
            {

                e1 = null;
                e2 = null;

                // set enteries e1,e2 to the row element
                if (row[i].index() != -1)
                {
                     e1 = (Entry) row[i].get();
                }
                
                if (Q.row[i].index() != -1)
                {
                     e2 = (Entry) Q.row[i].get();
                }

                // if not null add elements together
                if (e1 != null && e2!=null)
                {
                    if (e1.column == e2.column)
                    {
                        diff.changeEntry(i, e1.column, e1.data - e2.data);
                        Q.row[i].moveNext();
                        row[i].moveNext();
                    }
                    else if(e1.column < e2.column) 
                    {
                        diff.changeEntry(i, e1.column, e1.data);
                        row[i].moveNext();
                    } 
                    else if(e1.column > e2.column) 
                    {
                        diff.changeEntry(i, e2.column, e2.data);
                        Q.row[i].moveNext();
                    } 
                }

                // else if one element is null change entry of non null entries
                if (e1 != null && e2 == null)
                {
                    diff.changeEntry(i, e1.column, e1.data*-1);
                    row[i].moveNext();
                }
                if (e1 == null && e2 != null)
                {
                    diff.changeEntry(i, e2.column, e2.data*-1);
                    Q.row[i].moveNext();
                }
            }
        }
        return diff;
        //return this.add(N.scalarMult(-1));

    }

    /**transpose
    *  Returns a new Matrix that is the transpose of this Matrix
    */
    Matrix transpose()
    {
        Matrix T = new Matrix(getSize());

        for( int i = 1; i <= getSize(); i++) 
        {
            if (!row[i].isEmpty())
            {
                row[i].moveFront();
            }else continue;
            
            while(row[i].index() >= 0)
           {
                Entry entry = null;

                if (row[i].index() >= 0)
                {
                    entry = (Entry) row[i].get();
                    T.changeEntry(entry.column, i, entry.data);
                    row[i].moveNext();
                }
            }
        }
        return T;
    }

    /**mult
    *  Returns a new Matrix that is the product of this Matrix with M 
    *  pre: getSize()==M.getSize()
    */
    Matrix mult(Matrix N)
    {
      if(getSize() != N.getSize()){
         throw new RuntimeException("Matrix: mult() called on Matrix");
      }

      Matrix product = new Matrix(getSize());
      Matrix Q = N.transpose();

      for(int i = 1; i <= getSize(); i++) 
      {
         if(row[i].length() == 0) 
         {
            continue;
         }

         for(int j = 1; j <= getSize(); j++) 
         {
            if(Q.row[j].length() == 0) 
            {
                continue;
            }

            product.changeEntry(i, j, dot(row[i], Q.row[j]));
         }
      }
      return product; 
    }

    /**dot
    *  Returns a scaler that is the dot-product of this two vectors P, Q
    */
    private static double dot(List P, List Q)
    {
        double scaler = 0.0;
        Entry e1 = null;
        Entry e2 = null;

        // check if emety and move to front 
        if (!P.isEmpty())
        {
            P.moveFront();
        }

        if(!Q.isEmpty())
        {
            Q.moveFront();
        }

        while(P.index() >= 0 && Q.index() >= 0) 
        {
            // set enteries e1,e2 to the row element
            if (P.index() != -1)
            {
                e1 = (Entry) P.get();
            }
            if (Q.index() != -1)
            {
                e2 = (Entry) Q.get();
            }

            //find the dot product
            if (e1.column == e2.column)
            {
                scaler += (e1.data * e2.data);
                P.moveNext();
                Q.moveNext();
            }
            else if(e1.column > e2.column) 
            {
                Q.moveNext();
            } 
            else if(e1.column < e2.column) 
            {
                P.moveNext();
            } 
        }

      return scaler;
    }

    /* Other Functions ---------------------------------------------------- */
    
    /**toString
    *  overrides Object's toString() method
    */
    public String toString() 
    {
        StringBuffer sb = new StringBuffer();
       
        for(int i = 1; i <= getSize(); i++){
            if (row[i].isEmpty())
            {
                continue;
            }
            else
            {
                sb.append(i + ": " + row[i] + "\n");
            }
        }

        return new String(sb);
    }
}