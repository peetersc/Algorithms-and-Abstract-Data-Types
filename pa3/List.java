//----------------------------------------------------
//Author: Cameron Peeters
//Cruz ID: 1675143
//Professor: Patrick Tantalo
//Class: CMPS101 - Algorithms and Abstract Data Types 
//Project: Programming Assignment 3 
//Date: 4/29/2019
//File: List.java
//----------------------------------------------------

/**Abstract:
*
* The List ADT is a bi-directional queue that includes a “cursor” to 
* be used for iteration
*
* The set of “mathematical structures” for this ADT consists of all finite 
* sequences of integers in which at most one element is underscored. A list 
* has two ends referred to as “front” and “back” respectively. The cursor will 
* be used by the client to traverse the list in either direction. Each list 
* element is associated with an index ranging from 0 (front) to n-1 (back), 
* where n is the length of the list.
*
**/

public class List 
{
	/* Private Class ------------------------------------------------------ */

	/**Node
	 * private inner class of List that holds integer data and node locations as a 
	 * linked list.
	 *
	 * The base underlying data structure is a bi-directional(previous/next) queue.
	 */
	private class Node
	{
		// Node fields
		private Object  data;
		private Node previous;
		private Node next;
		
		// Constructor
		Node(Object data) { this.data = data; previous = next = null;}

		// Overides the default toString method
		public String toString()
		{
			return String.valueOf(data);
		}

	}

	/* Fields ------------------------------------------------------------- */

	// List private Fields 
	private String  name;
	private Node    front;
	private Node    back;
	private Node    cursor;
	private int     length;

	/* Constructors -------------------------------------------------------- */

	// Default Constructor
	/**List()
	 * creates a new empty list with no name
	 */
	List()
	{
		name   = "List";
		front  = back = cursor = null;
		length = 0;
	}

	// Overrides Default Constructor
	/**List(name)
	 * creates a new empty list with a name
	 */
	List(String name)
	{
		this.name = name;
		front     = back = cursor = null;
		length    = 0;
	}
	
    /* Access Functions --------------------------------------------------- */
	
	/**isEmpty()
	*  Returns true if this Queue is empty, false otherwise.
	*/
	boolean isEmpty() 
	{ 
	      return length == 0; 
	}
	
	/**length()
	 * Returns the number of elements in this list.
	 */
	int length() 
	{
		return length;
	}
	/**name()
	 * Returns the name of the list.
	 */
	public String name()
	{
		StringBuffer sb = new StringBuffer();
	   	Node N = front;
    	if(N!=null)
    	{
    	   sb.append(name.toString());
    	}
	    
    	return new String(sb);
	}

	/**index()
	 * If cursor is defined, returns the index of the cursor element,
	 * otherwise returns -1.
	 */
	int index() 
	{
		 if(isEmpty())
		 {
	         //throw new RuntimeException(
	            //"Queue Error: index() called on empty Queue");
	     }
		 
		 //temp node
		 Node newNode = front;
		 
		 //traverse list
		 for (int i = 0; i < length(); i++) 
		 {
			 if (newNode == cursor) 
			 {
				 return i;
			 }
			 newNode = newNode.next;
		 }
		 
		 //cursor not defined
		 return -1;
	}
	
	/**front()
	 * Returns front element. Pre: length()>0
	 */
	Object front() 
	{
		if (length() <= 0) // checks precondition: length()>0 
		{
			//precondition not met
			throw new RuntimeException(
					"List Exception: front() called on length()<0");
		}
		
		return front.data; // postcondition !< 0
	}
	
	/**back()
	 * Returns back element. Pre: length()>0
	 */
	Object back() 
	{
		if (length() <= 0) // checks precondition: length()>0 
		{
			//precondition not met
			throw new RuntimeException(
					"List Exception: back() called on length()<0");
		}
		
		return back.data; // postcondition !< 0
	}
	
	/**get()
	 * Returns cursor element. Pre: length()>0, index()>=0
	 */
	Object get() 
	{
		if (length() <= 0 || index()<0) 
		{
			//precondition not met
			throw new RuntimeException(
					"List Exception: get() called on length()<0 or index < 0");
		}

		return cursor.data;
	}
	
	/**equals(List)
	 * Returns true if and only if this List and L are the same
	 * integer sequence. The states of the cursors in the two Lists
	 * are not used in determining equality.
	 */
	boolean equals(List L) 
	{
	      boolean eq  = true;
	      Node N, M;

	      N = front;
	      M = L.front;
	      
	      while( eq && N!=null )
	      {
	    	 //checks if the lengths and the data are equal
	    	 eq = (length() == L.length()) && 
	    		  (N.data   == M.data);

	         N = N.next;
	         M = M.next;
	      }
	      
	      return eq;
	}
	
    /* Manipulation procedures -------------------------------------------- */
	
	/**clear()
	 * Resets this List to its original empty state.
	 */
	void clear() 
	{
		front  = back = cursor = null;
		length = 0;
		name   = "List";
	}
	
	/**moveFront()
	 * If List is non-empty, places the cursor under the FRONT element,
	 * otherwise does nothing.
	 */
	void moveFront()
	{
		 if(!isEmpty())
		 {
			cursor = front;
	     }
		 
	}
	
	/**moveBack()
	 * If List is non-empty, places the cursor under the BACK element,
	 * otherwise does nothing.
	 */
	void moveBack()
	{
		 if(!isEmpty())
		 {
	  		cursor = back;
	     }		 
	}
	
	/**movePrev()
	 * If cursor is defined and not at front, moves cursor one step toward
	 * front of this List, if cursor is defined and at front, cursor becomes
	 * undefined, if cursor is undefined does nothing.
	 */
	void movePrev()
	{
	     //cursor falls of the list
		 if (cursor == front) 
		 {
			 cursor = null;
		 }
		 else if (cursor != null)
		 {
			 cursor = cursor.previous;
		 }

	}
	
	/**moveNext()
	 * If cursor is defined and not at back, moves cursor one step toward 
	 * back of this List, if cursor is defined and at back, cursor becomes
	 * undefined, if cursor is undefined does nothing.
	 */
	void moveNext()
	{
	     //cursor falls off the list
		 if (cursor == back) 
		 {
			 cursor = null;
		 }
		 else if (cursor != null)
		 {
			 cursor = cursor.next;
		 }
	}
	
	/**prepend(int)
	 * Insert new element into this List. If List is non-empty, 
	 * insertion takes place before front element.
	 */
	void prepend(Object data)
	{
		Node N = new Node(data);
				
	    if(isEmpty()) 
	    { 
	    	front = back = N;
	    }
	    else
	    { 
	    	front.previous = N;
	    	N.next         = front;
	    	front          = N;
	    }
	    
	    length++;
	}
	
	/**append()
	 * Insert new element into this List. If List is non-empty,
	 * insertion takes place after back element.
	 */
	void append(Object data)
	{
		Node N = new Node(data);
		
	    if(isEmpty()) 
	    { 
	    	front = back = N;
	    }
	    else
	    { 
	    	back.next  = N; 
	    	N.previous = back;
	        back       = N; 
	    }
	    
	    length++;
	}
	
	/**insertBefore(int)
	 * Insert new element before cursor.
	 * Pre: length()>0, index()>=0
	 */
	void insertBefore(Object data)
	{
		if (length() <= 0 || index() < 0) 
		{
			throw new RuntimeException(
					"List Exception: insertBefore() called on len <= 0 or index < 0");
		}
		
		Node N = new Node(data);
		
		if (cursor == front) 
		{
			front.previous = N;
			N.next = front;
			front = N;
		}
		else 
		{
			N.previous = cursor.previous;
			cursor.previous.next = N;
			N.next = cursor;
			cursor.previous = N;
		}
		
		length++;
	}
	
	/**insertAfter(int)
	 * Inserts new element after cursor.
	 * Pre: length()>0, index()>=0
	 */
	void insertAfter(Object data)
	{
		if (length() <= 0 || index() < 0) 
		{
			throw new RuntimeException(
					"List Exception: insertAfter()  called on len <= 0 or index < 0");
		}
		
		Node N = new Node(data);
		
		if (cursor == back) 
		{
			back.next  = N;
			N.previous = back;
			back = N;
		}
		else 
		{
			N.next = cursor.next;
			cursor.next.previous = N;
			N.previous = cursor;
			cursor.next = N;
		}
		
		length++;
	}
	
	/**deleteFront()
	 * Deletes the front element. Pre: length()>0
	 */
	void deleteFront()
	{
		// check for not empty
		if(isEmpty())
		{
			throw new RuntimeException(
					"List Exception: deleteFront() called on empty Queue");
	    }
		
	    if(length>1)
	    {
	        front.next.previous = null;
	        front = front.next;
	    }
	    else
	    {
	    	front = back = null;
	    }
	    
	    length--;
	}
	
	/**deleteBack()
	 * Deletes the back element. Pre: length()>0
	 */
	void deleteBack()
	{
		// check for not empty
		if(isEmpty())
		{
			throw new RuntimeException(
					"List Exception: deleteBack() called on empty Queue");
	    }
		
	    if(length>1)
	    {
	        back.previous.next = null;
	        back = back.previous;
	    }
	    else
	    {
	    	front = back = null;
	    }
	    
	    length--;
	}
	
	/**delete()
	 * Deletes cursor element, making cursor undefined.
	 * Pre: length()>0, index()>=0
	 */
	void delete()
	{
		// check preconditions
		if(length()<= 0)
		{
			throw new RuntimeException(
					"List Exception: delete() len <= 0 || index < 0");
	    }
		
		//find cursor and delete it
		if(cursor == front)
		{
			deleteFront();
		}
		else if (cursor == back)
		{
			deleteBack();
		}
		else
		{
			cursor.previous.next = cursor.next;
			cursor.next.previous = cursor.previous;
			cursor = null;
		
			length--;	
		}	
	}
	
    /* Other Functions ---------------------------------------------------- */

	/**toString()
	* Overrides Object's toString method. Returns a String
	* representation of this List consisting of a space
	 * separated sequence of integers, with front on left.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
	   	Node N = front;
    	while(N!=null)
    	{
    	   sb.append(N.toString());
    	   sb.append(" ");
    	   N = N.next;
    	}
	    
    	return new String(sb);
	}	
	/**copy()
	 * Returns a new List representing the same integer sequence as this
	 * List. The cursor in the new list is undefined, regardless of the
	 * state of the cursor in this List. This List is unchanged.
	 */
	List copy()
	{
		List Q = new List(name);
	    Node N = front;

	    while( N!=null )
	    {
	       Q.append(N.data);
	       N = N.next;
	    }
	    
	    return Q;
	}
	
	/**concat(List)
	 * Returns a new List which is the concatenation of
	 * this list followed by L. The cursor in the new List
	 * is undefined, regardless of the states of the cursors
	 * in this List and L. The states of this List and L are
	 * unchanged.
	 */
	List concat(List L)
	{
		return L;
	}
}