import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Doublets.java
 * Provides an implementation of the WordLadderGame interface. The lexicon
 * is stored as a TreeSet of Strings.
 *
 * @author Aneesh Akella (ana0039@auburn.edu)
 * @author Dean Hendrix (dh@auburn.edu)
 * @version 2017-04-28
 */
public class Doublets implements WordLadderGame {

   ////////////////////////////////////////////
   // DON'T CHANGE THE FOLLOWING TWO FIELDS. //
   ////////////////////////////////////////////

   
   List<String> EMPTY_LADDER = new ArrayList<>();

   
   TreeSet<String> lex;



   /**
    * Instantiates a new instance of Doublets.
    */
   public Doublets(InputStream in) {
      try {
         lex = new TreeSet<String>();
         Scanner scan =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (scan.hasNext()) {
            String str = scan.next();
            lex.add(str.toLowerCase());
            scan.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }

   /**
    * Returns the Hamming distance between two strings.
    *
    * @param  str1 first string
    * @param  str2 second string
    * @return      the Hamming distance between str1 and str2
    */
   public int getHammingDistance(String str1, String str2) {
      if (str1.length() != str2.length()) {
         return -1;
      }
      str1 = str1.toLowerCase();
      str2 = str2.toLowerCase();
      int out = 0;
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            out++;
         }
      }
      return out;
   }

  /**
   * Returns a minimum-length word ladder from start to end.
   *
   * @param  start  the starting word
   * @param  end    the ending word
   * @return        a minimum length word ladder from start to end
   */
   public List<String> getMinLadder(String start, String end) {
      start = start.toLowerCase();
      end = end.toLowerCase();
      ArrayList<String> back = new ArrayList<String>();
      List<String> minLadder = new ArrayList<String>();
      if (start.equals(end)) {
         minLadder.add(start);
         return minLadder;
      }
      if (getHammingDistance(start, end) == -1) {
         return EMPTY_LADDER;
      }
      if(isWord(start) && isWord(end)) {
         back = bfs(start, end);
      }
      if (back.isEmpty()) {
         return EMPTY_LADDER;
      }
      for (int i = back.size() -1; i >= 0; i--) {
         minLadder.add(back.get(i));
      }
      return minLadder;
   }
   
   private ArrayList<String> bfs(String start, String end) {
      Deque<Node> queue = new ArrayDeque<Node>();
      HashSet<String> visited = new HashSet<String>();
      ArrayList<String> backwards = new ArrayList<String>();
      visited.add(start);
      queue.addLast(new Node(start, null));
      Node endNode = new Node(end, null);
      outerloop:
      while (!queue.isEmpty()) {
         Node n = queue.removeFirst();
         String word = n.word;
         List<String> neighbors = getNeighbors(word);
         for (String neighbor : neighbors) {
            if(!visited.contains(neighbor)) {
               visited.add(neighbor);
               queue.addLast(new Node(neighbor, n));
               if (neighbor.equals(end)) {
                  endNode.predecessor = n;
                  break outerloop;
               }
            }
         }
      }
      if(endNode.predecessor == null)
      {
         return backwards;
      }
      Node m = endNode;
      while (m != null) {
         backwards.add(m.word);
         m = m.predecessor;
      }
      return backwards;
   }

   /**
    * Returns all the words that have a Hamming distance of one relative to the
    * given word.
    *
    * @param  word the given word
    * @return      the neighbors of the given word
    */
   public List<String> getNeighbors(String word) {
      List<String> neighbors = new ArrayList<String>();
      char[] wordChar = word.toCharArray();
      char[] temp;
      char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
      for (int i = 0; i < wordChar.length; i++) {
         for (char letter: alphabet) {
            temp = Arrays.copyOf(wordChar, wordChar.length);
            temp[i] = letter;
            String temp2 = new String(temp);
            if (isWord(temp2)) {
               neighbors.add(temp2);
            }
         }
      }
      for (int j = 0; j < wordChar.length; j++) {
         neighbors.remove(word);
      }
      return neighbors;
   }


   /**
    * Returns the total number of words in the current lexicon.
    *
    * @return number of words in the lexicon
    */
   public int getWordCount() {
      return lex.size();
   }


   /**
    * Checks to see if the given string is a word.
    *
    * @param  str the string to check
    * @return     true if str is a word, false otherwise
    */
   public boolean isWord(String str) {
      str = str.toLowerCase();
      if (lex.contains(str)) {
         return true;
      }
      return false;
   }


   /**
    * Checks to see if the given sequence of strings is a valid word ladder.
    *
    * @param  sequence the given sequence of strings
    * @return          true if the given sequence is a valid word ladder,
    *                       false otherwise
    */
   public boolean isWordLadder(List<String> sequence) {
      String word1 = "";
      String word2 = "";
      if (sequence.isEmpty()) {
         return false;
      }
      for (int i = 0; i < sequence.size()-1; i ++) {
         word1 = sequence.get(i);
         word2 = sequence.get(i+1);
         if (!isWord(word1) || !isWord(word2)) {
            return false;
         } 
         if (getHammingDistance(word1, word2) != 1) {
            return false;
         }
      }
      return true;
   }


    /**
    * Constructs a node for linking positions together.
    */
   private class Node {
      String word;
      Node predecessor;
   
      public Node(String s, Node pred) {
         word = s;
         predecessor = pred;
      }
   }
}