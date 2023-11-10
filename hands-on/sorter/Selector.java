import java.util.Arrays;
/**
* Selector Class.
* @Author Aneesh Akella
*/

public final class Selector {

   private Selector() { }

/**
* The min method returnss the minimum value.
* @param  int[] a
*/
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int out = a[0];
      for (int i = 1; i < a.length; i ++) {
         if (a[i] < out) {
            out = a[i];
         }
      }
      return out;
   }
   
/**
* The max method returns the max value.
* @param int[] a
*/   
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      } 
      int out = a[0];
      for (int i = 1; i < a.length; i++) {
         if (a[i] > out) {
            out = a[i];
         }
      }
      return out;
   }
   
/**
* The range method returns an array of all values betwen the lowest and highest numbers.
* @param int[] a
* @param int low
* @param int high
*/   
   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int newsize = 0;
      for (int i = 0; i < a.length; i ++) {
         if (a[i] >= low && a[i] <= high) {
            newsize++;
         }
      }
      int[] range = new int[newsize];
      if (newsize == 0) {
         return range;
      }
      int j = 0;
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= low && a[i] <= high) {
            range[j] = a[i];
            j++;
         }
      }
      return range;
   }
   
   /**
   * The ceiling method returns the smallest value that is greater than a certain number.
   * @param int[] a 
   * @param int key 
   */   
   public static int ceiling(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int out = 0;
      boolean found = false; 
      for (int i = 0; i < a.length; i ++) {
         if (!found && a[i] >= key) {
            out = a[i];
            found = true;
         }
         else if (a[i] >= key && a[i] <= out) {
            out = a[i];
         }
      }
      if (!found) {
         throw new IllegalArgumentException();
      }
      return out;
   }

/**
* The floor method returns the largest value in a given array less than a certain number.
* @param int[] a
* @param int key 
*/      
   public static int floor(int[] a, int key) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException();
      }
      int out = 0;
      boolean found = false; 
      for (int i = 0; i < a.length; i ++) {
         if (!found && a[i] <= key) {
            out = a[i];
            found = true;
         }
         else if (a[i] <= key && a[i] >= out) {
            out = a[i];
         }
      }
      if (!found) {
         throw new IllegalArgumentException();
      }
      return out;
   }
   /**
   * kmin selects the kth minimum.
   * @param int[] a
   * @param int k 
   */   
   public static int kmin(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] b = new int[a.length];
      for (int i = 0; i < a.length; i ++) {
         b[i] = a[i];
      }
      Arrays.sort(b);
      int dist = 1;
      int temp = b[0];
      int out = 0;
      if (k == 1) {
         out = b[0];
         return out;
      }
      for (int i = 1; i < a.length; i++) {
         if (b[i] != temp) {
            dist++;
            if (dist == k) {
               out = b[i];
            }
         }
         temp = b[i];
      }
      if (k > dist) {
         throw new IllegalArgumentException();
      }
      return out;
   }

/**
* kmax selects the kth maximum.
* @param int[] a
* @param int k
*/    
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException();
      }
      int[] b = new int[a.length];
      for (int i = 0; i < a.length; i ++) {
         b[i] = a[i];
      }
      Arrays.sort(b);
      int dist = 1;
      int temp = b[b.length - 1];
      int out = 0;
      if (k == 1) {
         out = b[b.length - 1];
         return out;
      }
      for (int i = b.length - 1; i >= 0; i--) {
         if (b[i] != temp) {
            dist++;
            if (dist == k) {
               out = b[i];
            }
         }
         temp = b[i];
      }
      if (k > dist) {
         throw new IllegalArgumentException();
      }      
      return out;
   }
}