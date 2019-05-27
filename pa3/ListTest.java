public class ListTest{

    /**Entry
    *  Private inner class of Matrix that holds Entry data for the
    *  matrix (i.e) column and data
    */
    static private class Entry // made static for testing purposes to be used in main
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
    }

    // Testing --------------------------------------------------------
    public static void main(String args[]) 
    {
        //creates lists
        List list  = new List("List 1");
        List queue = new List("List 2");

        Entry A = new Entry(1,2);
        Entry B = new Entry(3,4); 
        Entry C = new Entry(5,6);

        //populates list
        list.append(A);
        list.append(B);
        list.append(C);

        System.out.println(A);
        System.out.println(B);
        System.out.println(C);

        
        //assigns cursor to the front of the list
        list.moveFront();  
        
        //insert before/after and delete
        list.insertBefore(A);  
        list.insertAfter(B);
        
        //assigns cursor to the front of the list
        list.moveFront();          
        list.delete();
        
        //delete front and back
        list.deleteFront();
        list.deleteBack();  
        
        //assigns cursor to the front of the list
        list.moveFront();  
        
        //testing copy method
        queue = list.copy();

        //testing equals
        System.out.print("Is equals: ");
        System.out.println(list.equals(queue));

        //testing clear
        queue.clear();
        
    }
}
