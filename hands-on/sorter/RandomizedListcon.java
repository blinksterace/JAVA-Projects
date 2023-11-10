import java.util.Iterator;
import java.util.Random;

/**
 * RandomizedList.java. Implements the behavior
 * described in RandomizedListInterface. In addition,
 * the following performance guarantees are provided.
 *
 * Time
 * -------------------------
 * add      - O(1) amortized 
 * remove   - O(1) amortized
 * sample   - O(1)
 * size     - O(1)
 * isEmpty  - O(1)
 * iterator - O(N) creation; O(1) for each method on resulting iterator
 *
 * Space
 * -------------------------
 * This class uses memory proportional to the **current** size of the
 * list. That is, the amount of memory used at any point in time is
 * O(N) where N is the current size of the list. This class can use up
 * to a linear amount of extra memory per iterator.
 *
 *
 * @author    Aneesh Akella (ana0039@auburn.edu)
 * @author    Dean Hendrix (dh@auburn.edu)
 * @version   2020-03-11
 *
 */
public class RandomizedListcon<T> implements RandomizedList<T> {
   private T[] elem;
   private int sz;
   private static final int DEFAULT_CAPACITY = 5;
   /**
   * Default constructor
   */
   public RandomizedListcon() {
      this(DEFAULT_CAPACITY);
   }
   
   /**
   * Constructor
   */
   @SuppressWarnings("unchecked") 
   public RandomizedListcon(int capacity) { 
      elem = (T[]) new Object[capacity];
      sz = 0;
   }
   
    /**
    * Returns the number of elements.
    */
   public int size() {
      return sz;
   }
 
   /**
    * Returns true if this list contains no elements.
    */
   public boolean isEmpty() {
      return sz == 0;
   }
    
   /**
    * Adds the specified element to this list..
    */
   public void add(T element){ 
      if (element == null)
      {
         throw new IllegalArgumentException();
      }
      if (sz == elem.length)
      {
         resize(elem.length*2);
      }
      elem[sz] = element;
      sz++;
   }
  
   /**
    * Selects and removes an element.
    */
   public T remove() {
      if (this.isEmpty()) {
         return null;
      }
      Random rand = new Random();
      int val = rand.nextInt(sz); 
      T removed = elem[val]; 
      elem[val] = null; 
      if (val != (sz-1)) { 
         elem[val] = elem[sz-1];
         elem[sz-1] = null;
      }
      sz --; 
      if (sz > 0 && sz < elem.length / 4) {
         resize(elem.length/2);
      }
      return removed;
   }
   
   /**
    * Selects but does not remove an element.
    */
   public T sample() {
      if (this.isEmpty()) {
         return null;
      }
      Random rand = new Random();
      int val = rand.nextInt(sz); 
      return elem[val];
   }
   
   /**
    * Creates and returns an iterator over the elements of this list.
    */
   public Iterator<T> iterator() {
      return new ArrayIterator(elem, sz);
   }
   
   /**
   * Resizes an array
   */   
   private void resize(int capacity) {
      T[] a = (T[]) new Object[capacity];
      for (int i = 0; i < size(); i ++) {
         a[i] = elem[i];
      }
      elem = a;
   }
   
  /** 
   *makes an iterator.
   */
   public class ArrayIterator<T> implements Iterator<T> {
      private T[] items; 
      private int length; 
      
      /**
      * Construct
      */
      public ArrayIterator(T[] elem, int sizeIn) {
         items = elem;
         length = sizeIn;
      }
      
      /**
      * Checks to see if there is a next element
      */
      public boolean hasNext() {
         return (length > 0); 
      }
      
      /**
      does nothing
      */
      public void remove() {
         throw new UnsupportedOperationException();
      }
      
      /**
      * Returns the next item in the list.
      */
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         Random rand = new Random();
         int val = rand.nextInt(length);
         T next = items[val]; 
         if (val != (length-1)) { 
            items[val] = items[length-1];
            items[length-1] = next;
         }
         length--;
         return next;
      }
   }
}