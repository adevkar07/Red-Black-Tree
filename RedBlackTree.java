/** @author 
 *  Abhilasha Devkar : acd170130
 *  Rashmi Priya : rxp170021
 *  SP7 : Red Black Tree(Single Pass)
 **/

package acd170130;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * This is RedBlackTree class which is used to 
 * perform various operations on the same.
 */
public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Entry<T> header;		//dummy node (Root's parent node)
    private Entry<T> nil = new Entry<T>(null, null, null);		//Blank nodes
    Entry<T> root = (Entry<T>) super.root;
    Entry<T> curr;
	Entry<T> parent;		//Parent of current node
	Entry<T> gparent;		//Grand Parent of current node
	Entry<T> great;			//Great Grand Parent of current node

	
	/**
     * This is static class Entry
     * @param color : color of the node.
     **/
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;
        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = BLACK;
        }

        /**
         * This method is used to Checks if color of node is Red. 
         * @return True : If Red.
         * @return False : If Black.
         */
        boolean isRed() {
	    return color == RED;
        }

        /**
         * This method is used to Checks if color of node is Black. 
         * @return True : If Black.
         * @return False : If Red.
         */
        boolean isBlack() {
	    return color == BLACK;
        }
    }

    /**
     * Constructor
     * Setting header node with Integer.MIN_VALUE
     **/
    RedBlackTree() {
    	 super();
    	 nil.color = BLACK;
    	 header = new Entry(Integer.MIN_VALUE, null, null);
	   	 header.left = nil;
	   	 header.right = nil;   	
    }
    
    
    /**
     * This method is used to add element in RBT.
     * @param x : Element to be added. 
     * @return True : Addition was successfully.
     */
    public boolean add(T x1) {
    	 curr = (Entry<T>) header;
    	 parent = (Entry<T>) header;
    	 gparent = (Entry<T>) header;
    	
    	//To add first node into RBT 
    	if(size==0)
    	{
    		root = new Entry(x1,null,null); 
    		
    		size = 1;
    		root.left = nil;
    		root.right = nil;
    		root.color = BLACK;
    		curr = root;
    		header.right = root;	//Header's right child is root.
    		root.parent = header;
    		super.root = root;
    		return true;
    	}  
    	
    	//dummyN is a dummyNode which has copy of x1, elem to be added.
    	Entry<T> dummyN = new Entry(x1,null,null);
    	
    	//traverse the tree to find the location where new node 
    	//needs to be added & balance it on its way down. 
    	while(curr.element.compareTo(x1) != 0)
    	{
    		great = gparent;
    		gparent = parent;
    		parent = curr;
    		
    		curr = x1.compareTo(curr.element) < 0 ? (Entry<T>) curr.left : (Entry<T>) curr.right;
    		if(curr==dummyN || curr == nil)
    		{
    			break;
    		}
    		if(((Entry<T>)curr.left).isRed() && ((Entry<T>)curr.right).isRed())
			{
				reOrganize(x1);
			}    	   				    	
    	}
    	
    	if(curr!=nil) 
    	{
    		return false;
    	}
    	curr = new Entry<T>(x1,null,null);
    	curr.left = nil;
    	curr.right = nil;
    	
    	if(x1.compareTo(parent.element) < 0)
    	{
    		parent.left = curr;
    	}
    	else
    	{
    		parent.right = curr;
    	}
    	curr.parent = parent;
    	
    	reOrganize(x1);
    	size++;
    	super.root = root;
    	root.parent = header;
    	return true;    	
    }
    
    /**
     * This method is used to balance RBT.
     * @param x : Element where violation was found. 
     */
    private void reOrganize(T x1) {
	  curr.color = RED;
	  ((Entry<T>)curr.left).color = BLACK;
	  ((Entry<T>)curr.right).color = BLACK;
		
	  if(parent.isRed())
	  {
		  gparent.color = RED;
		  if((x1.compareTo(gparent.element) < 0) != (x1.compareTo(parent.element) < 0))
		  {
			  parent = rotate(x1,gparent);
		  }
		  curr = rotate(x1,great);
		  curr.color = BLACK;
	  }
	  root = (Entry<T>)header.right;
	  ((Entry<T>)root).color = BLACK;	  
	}
    
    /**
     * This method is used to rotate left.
     * @param x : Element upon which left rotation is to be performed. 
     */
    public void rotateLeft(Entry<T> x) {
    	Entry<T> y = (Entry<T>) x.right;
    	x.right = y.left;
    	if(y.left != nil && y.left != null) {
    		y.left.parent = x;
    	}
    	y.parent = x.parent;
    	if(x.parent==null || x.parent == header)
    	{
    		this.root = y;
    	}
    	else
    	{
    		if(x == x.parent.left)
    		{
    			x.parent.left = y;
    		}
    		else
    		{
    			x.parent.right = y;
    		}   		
    	}
    	y.left = x;
		x.parent = y;
    }
    
    /**
     * This method is used to rotate right.
     * @param x : Element upon which right rotation is to be performed. 
     */
    public void rotateRight(Entry<T> x) {    	
    	Entry<T> y = (Entry<T>) x.left;
    	x.left = y.right;
    	if(y.right != nil && y.right != null) {
    		y.right.parent = x;
    	}
    	y.parent = x.parent;
    	if(x.parent==null || x.parent == header)
    	{
    		this.root = y;
    	}
    	else
    	{
    		if(x == x.parent.right)
    		{
    			x.parent.right = y;
    		}
    		else
    		{
    			x.parent.left = y;
    		}
    		
    	}
    	y.right = x;
		x.parent = y;
    }

    /**
     * This method is used to rotate RBT.
     * Checks if node is on the left or right side of gparent.
     */
	private Entry<T> rotate(T item, Entry<T> parent) {
		if( item.compareTo( parent.element ) < 0 )
		{
			//Checks if node is on the left side of parent
			if(item.compareTo(parent.left.element)<0)
			{
				parent.left = rotateWithLeftChild((Entry<T>) parent.left);
				parent.left.parent = parent;
				return (Entry<T>) (parent.left);
			}
			//Checks if node is on the right side of parent
			else
			{
				parent.left = rotateWithRightChild((Entry<T>) parent.left);
				parent.left.parent = parent;
				return (Entry<T>) (parent.left);
				
			}
		}
		else
		{
			//Checks if node is on the left side of parent
			if(item.compareTo(parent.right.element)<0)
			{
				parent.right = rotateWithLeftChild((Entry<T>) parent.right);
				parent.right.parent = parent;
				return (Entry<T>) (parent.right);
				
			}
			//Checks if node is on the right side of parent
			else
			{
				parent.right = rotateWithRightChild((Entry<T>) parent.right);
				parent.right.parent = parent;
				return (Entry<T>) (parent.right);
				
			}
		}        	
	}
		
	 /**
     * This method is used to Remove v from tree. 
     * @return v : Return v if found, otherwise return 
     * @return null : for all other cases.
     * */
	public T remove(T v)
    {
		
		if(root == null || root == nil) {
			return null;
		}
		
		acd170130.BinarySearchTree.Entry<T> v1 = super.find(v);
    	if(v1.element != v) {
    		System.out.println("Element "+v+" not present");
    		return null;
    	}
    	if(size == 1 && root.element == v) {
			header.right = nil;
			root = null;
			return v;
		}
        RedBlackTree.Entry<T> z=( RedBlackTree.Entry<T>)v1;
        
        RedBlackTree.Entry<T> x = nil;
        RedBlackTree.Entry<T> y = nil;
        
        // atleast one non nil child, copy z into y so y and z both point to same 
        // element to be deleted
        if(z.left == nil || z.right==nil)
        {
        	y = z;
        }      
        else
        {
        	s.push(z);
        	acd170130.BinarySearchTree.Entry<T> min = super.find(z.right, z.element);
        	RedBlackTree.Entry<T> minRight=( RedBlackTree.Entry<T>)min;
    		y = minRight;

        }
        
        if(y.left!=nil)
        {
        	x = (Entry<T>) y.left;        	
        }
        else
        {
        	x = (Entry<T>) y.right;
        }
        
        x.parent = y.parent;
        if(y.parent == header || y.parent == nil || y.parent == null)
        {
        	root = x; 
        }
        else if(y.parent.left!=nil && y.parent.left==y)
        {
        	y.parent.left = x;
        }
        else if(y.parent.right!=nil && y.parent.right==y)
        {
        	y.parent.right = x;
        }
        if( y != z) {
        	z.element = y.element;
        }
    	if(y.isBlack()) {
    		fixUp(x);
    	}
    	
    	return v;
    	
    }
	
	/**
	* Rotate binary tree node with left child.
	*/
	private Entry<T> rotateWithLeftChild( Entry<T> k2 )
	{
	    	Entry<T> k1 = (Entry<T>) k2.left;
	        k2.left = k1.right;
	        k1.right = k2;
	        k2.parent = k1;
	        k2.left.parent = k2;
	        return k1;
	}

	/**
	* Rotate binary tree node with right child.
	*/
	private Entry<T> rotateWithRightChild( Entry<T> k1 )
	{
	    	Entry<T> k2 = (Entry<T>) k1.right;
	        k1.right = k2.left;
	        k2.left = k1;
	        k1.parent = k2;
	        k1.right.parent = k1;
	        return k2;
	}
    
	/**
	* This helper method is used by remove function to balance RBT. 
	**/
    private void fixUp(Entry<T> x) {
		while(x != this.root && x.isBlack()) {		
	        RedBlackTree.Entry<T> xparent=( RedBlackTree.Entry<T>)x.parent;
			if(x.parent.left ==  x) {
				Entry<T> w = (Entry<T>)xparent.right;
				if(w.isRed()) {
					w.color = BLACK;
					xparent.color = RED;
					rotateLeft(xparent);
					w = (Entry<T>)xparent.right;
				}
				if(((Entry<T>)w.left).isBlack() && ((Entry<T>)w.right).isBlack()) 
				{
					w.color = RED;
					x = (Entry<T>)x.parent;
				}
				else if(((Entry<T>)w.right).isBlack()) {
					((Entry<T>)w.left).color = BLACK;
					w.color = RED;
					rotateRight(w);
					w = (Entry<T>)x.parent.right;
				}
				else {
					w.color = ((Entry<T>)x.parent).color;
					((Entry<T>)x.parent).color = BLACK;
					((Entry<T>)w.right).color = BLACK;
					rotateLeft((Entry<T>)x.parent);
					x = (Entry<T>)root;
				}
				
			}
			else {
				Entry<T> w = (Entry<T>)x.parent.left;
				if(w.isRed()) {
					w.color = BLACK;
					((Entry<T>)x.parent).color = RED;
					rotateRight((Entry<T>)x.parent);
					w = (Entry<T>)x.parent.left;
				}
				if(((Entry<T>)w.left).isBlack() && ((Entry<T>)w.right).isBlack()) 
				{
					w.color = RED;
					x = (Entry<T>)x.parent;
				}
				else if(((Entry<T>)w.left).isBlack()) {
					((Entry<T>)w.right).color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = (Entry<T>)x.parent.left;
				}
				else {
					w.color = ((Entry<T>)x.parent).color;
					((Entry<T>)x.parent).color = BLACK;
					((Entry<T>)w.left).color = BLACK;
					rotateRight((Entry<T>)x.parent);
					x = (Entry<T>)root;
				}
				
			}
			
		}
		x.color = BLACK;
	}
    
    /**
     * This helper method is used to print inOrder of the RBT. 
     * */
	public void inorder()
	{
		System.out.println("\n-----------------------------------------------");
		System.out.println("Node \t\tColor \t\t Parent Node");
		System.out.println("-----------------------------------------------");
		printInorder(root);
		System.out.println("-----------------------------------------------");
		System.out.println();
	}
    
	/**
    * This helper method is used to print inOrder of the RBT. 
    * */
	public void printInorder(Entry<T> start)
	{		
		if(start == nil)
		{
			return;
		}
		if(start != null && start != nil ) 
		{
			printInorder((Entry<T>)start.left);
			System.out.println(start.element+ "\t\t"+ (start.color == true ? "RED" : "BLACK")+"\t\t "+start.parent.element);
			printInorder((Entry<T>)start.right);
		}
	}
	
	/**
	* This helper method is used to print Level Order of the RBT. 
    * */
	public void levelorder() {
		System.out.println("\nLevel Order Traversal : \n");
		if(root == null || root == nil) {
			return;
		}
		Queue<Entry<T>> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i = 0;i < size;i++) {
				Entry<T> node = queue.remove();
				System.out.print(node.element + "\t");
				if(node.left != nil ) {
					queue.add((Entry<T>) node.left);
				}
				if(node.right != nil) {
					queue.add((Entry<T>) node.right);
				}
			}
			System.out.println();
			System.out.println("-----------------------------------------------");
		}
	}
    
    public static void main(String[] args)
    {
    	RedBlackTree<Integer> t = new RedBlackTree<>();
    	System.out.println("Options");
		System.out.println("1. Add Node to the tree");
		System.out.println("2. Remove Node from the tree");
		System.out.println("3. Print Tree");
		System.out.println("Any other key to exit");
    	Scanner in = new Scanner(System.in);    	 
    	whileloop:
    		while(in.hasNext())
    		{
    		    int com = in.nextInt();
    		    switch(com)
    		    {
    		    case 1:  
    		    	int x = in.nextInt();
                    t.add(x);
    		    	break;
    		    case 2: 
    		    	x = in.nextInt();
                    t.remove(x);
    		    	break;
    		    case 3 :     		    	
                    t.inorder();
                    t.levelorder();
    		    	break;
    		      		
    		    default:  // Exit loop
    		    	break whileloop;
    		    }
    		}		  
    	
        }
}
