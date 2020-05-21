package acd170130;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

import acd170130.RedBlackTree.Entry;

import java.util.Scanner;


/**
 * This is BinarySearchTree class which is used to 
 * perform various operations on the same.
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    
    /**
     * This is static class Entry
     * @param element : Element to be processed.
     * @param left :  Left child
     * @param right : Right child
     **/
	static class Entry<T> {
        T element;
        Entry<T> left, right, parent;
        
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	        this.left = left;
	        this.right = right;
	        this.parent = null;
        }
    }
    
    Entry<T> root;
    int size;
    Stack<Entry<T>> s;

    public BinarySearchTree() {
	root = null;
	size = 0;
    }

    /**
     * This method is used to detect if element is present in the given
     * BST or not.
     * @param x : Input element. 
     * @return True : If present.
     * @return False : If absent.
     */
    public boolean contains(T x) 
    {
    	Entry<T> t = find(x);
    	if (t == null || t.element != x) {
    		return false;
    	}
    	else
    		return true;
    }

    /**
     * This method is used to detect if there is an 
     * element that is equal to x in the tree
     * @param x : Input element. 
     * @return Element in tree that is equal to x
     * @return null otherwise
     */
    public T get(T x) 
    {   	
    	Entry<T> t = find(x);
    	if (t == null || t.element != x) {
    		return null;
    	}
    	else
    		return t.element;
    }

    /**
     * This method is used to Add x to tree
     * @param x : Input element. 
     * @return true : if x is a new element added to tree
     */
    public boolean add(T x) 
    { 
    	Entry<T> x_ent = new Entry<>(x, null, null);
    	
    	return add(x, x_ent);
    }

	protected boolean add(T x, Entry<T> x_ent) {
		if (size == 0)
    	{
    		root = x_ent;
    		size = 1;
    		return true;
    	}
    	else
    	{
    		
    		Entry<T> t = find(x);
    		if (t.element == x) 
    		{
    			t.element = x;
    			return false;
    		}
    		else if (x.compareTo(t.element) < 0)
    		{
    			t.left = x_ent;
    			t.left.parent = t;
    		}
    		else
    		{
    			t.right = x_ent;
    			t.right.parent = t;
    		}
    		size++;
    	}
	    return true;
	}
    
    /** Driver method for Find
     */
    public Entry<T> find(T x) 
    {
    	s = new Stack<Entry<T>> ();
    	s.push(null);
    	return find(this.root, x);    	
    }
    
    /** Find x within subtree rooted at X
     */
    public Entry<T> find (Entry<T> t, T x) 
    {
    	if (t == null || t.element == x) 
    	{
    		return t;
    	}
    	while(true) 
    	{
//    		System.out.println("x: "+x);
//    		System.out.println("t: "+t.element);
    		if (x.compareTo(t.element) == 0)
    		{
    			break;
    		}
    		else if (x.compareTo(t.element) < 0) 
    		{
    			if (t.left == null || t.left.element == null) 
    			{
       				break;
    			}
    			else 
    			{
    				s.push(t);
    				t = t.left;
    			}
    		}
    		else if (t.right == null || t.right.element == null) {    			
    			break;
    		}
    		else {
    			s.push(t);
    			t = t.right;
    		}
    	}
    	return t;
    }

    /**
     * This method is used to Remove x from tree. 
     * @return x : Return x if found, otherwise return 
     * @return null : for all other cases.
     * */
    public T remove(T x) 
    {
    	if (root == null)
    	{
    		return null;
    	}
    	Entry<T> t = find(x);
    	if (t.element != x) {
    		return null;
    	}
    	T result = t.element;
    	if (t.left == null || t.left.element == null || t.right == null ||t.right.element == null) {
    		splice(t);
    	}
    	else {
    		s.push(t);
    		Entry<T> minRight = find(t.right, t.element);
    		t.element = minRight.element;
    		splice(minRight);
    	}
    	size--;
	    return result;
    }
    
    /**
     * This method is used to link parent and child node.
     * */
    public void splice(Entry<T> t)
    {
    	Entry<T> pt = s.peek();
    	Entry<T> c = (t.left == null || t.left.element == null) ? t.right : t.left;
    	if (pt == null)
    		root = c;
    	else if (pt.left == t) {
    		pt.left = c;
    		
    	}else {
    		pt.right = c;
    	}
    	c.parent = pt;
    }

    /**
     * This method is used to find min element from BST. 
     * (Leftmost Node)
     * @return min : Minimum element 
     * */
    public T min() {
    	if (size == 0)
    		return null;
    	Entry<T> t = root;
    	while(t.left != null)
    	{
    		t = t.left;
    	}
	    return t.element;
    }

    /**
     * This method is used to find max element from BST.
     * (Rightmost Node) 
     * @return max : Maximum element 
     * */
    public T max() {
    	if (size == 0)
    	{
    		return null;
    	}
    	Entry<T> t = root;
    	while(t.right != null) {
    		t = t.right;
    	}
        return t.element;
    }

    /**
     * This method is used to Create an array with 
     * the elements using in-order traversal of tree.
     * @return Array : Array with elements in Inorder. 
     * */
    public Comparable[] toArray() 
    {
	    Comparable[] arr = new Comparable[size];
	    int i = 0;
	    Stack<Entry<T>> s = new Stack<Entry<T>> ();
	    Entry<T> curr = root;
	    while(!s.empty() || curr != null) {	    	
	    	while(curr != null) {
	    		s.push(curr);
	    		curr = curr.left;	    		
	    	}
	    	curr = s.pop();
	    	arr[i] = ((Comparable)curr.element);
	    	i++;
	    	curr = curr.right;   	
	    }
	    return arr;
    }
    
    
    /**
     * This method is used to print size of tree along with 
     * the elements using in-order traversal of tree.
     */
    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    /**
     * Inorder traversal of tree of tree.
     * @param root : Root Node. 
     * */
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
	}
    }

// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
	return null;
    }

    
    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }
// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
 
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }           
        }
    }



}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/

