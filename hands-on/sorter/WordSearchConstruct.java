import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * Creates a word search game based on definitions from WordSearchGame.
 *
 * @author Aneesh Akella (ana0039@auburn.edu)
 * @version 03/29/2020
 * 
 */
public class WordSearchConstruct implements WordSearchGame {
   private TreeSet<String> wordlist; 
   private String[][] brd; 
   private static final int MAX_NEIGHBORS = 8;
   private int width;
   private int height;
   private boolean[][] visited; 
   private ArrayList<Integer> path; 
   private String WrdSF; 
   private SortedSet<String> wordSet;
   private ArrayList<Position> secpath; 
   
   /**
   * Constructor.
   */
   public WordSearchConstruct() {
      wordlist = null;
      brd = new String[4][4];
      brd[0][0] = "E"; 
      brd[0][1] = "E"; 
      brd[0][2] = "C"; 
      brd[0][3] = "A"; 
      brd[1][0] = "A"; 
      brd[1][1] = "L"; 
      brd[1][2] = "E"; 
      brd[1][3] = "P"; 
      brd[2][0] = "H"; 
      brd[2][1] = "N"; 
      brd[2][2] = "B"; 
      brd[2][3] = "O"; 
      brd[3][0] = "Q"; 
      brd[3][1] = "T"; 
      brd[3][2] = "T"; 
      brd[3][3] = "Y";    
      width = brd.length;
      height = brd[0].length;
      markAllUnvisited(); 
   }
       
    /**
    * Loads the wordlist into a data structure. 
    * 
    * @param fileName A string containing the name of the file to be opened.
    * @throws IllegalArgumentException if fileName is null
    * @throws IllegalArgumentException if fileName cannot be opened.
    */
   public void loadLexicon(String fileName) {
      wordlist = new TreeSet<String>(); 
      if (fileName == null) {
         throw new IllegalArgumentException();
      }
      try {
         Scanner scan = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
         while (scan.hasNext()) {
            String str = scan.next();
            str = str.toUpperCase();
            wordlist.add(str); 
            scan.nextLine();
         }
      }
      catch (java.io.FileNotFoundException e) {
         throw new IllegalArgumentException();
      } 
   }
   
   /**
    * Stores the incoming array of Strings in a data structure.
    * 
    * @param letterArray This array of length N^2 stores the contents of the
    *     game brd in row-major order.
    * @throws IllegalArgumentException if letterArray is null, or is  not
    *     square.
    */
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException();
      }
      int n = (int)Math.sqrt(letterArray.length);
      if ((n * n) != letterArray.length) {
         throw new IllegalArgumentException();
      }
      brd = new String[n][n];
      width = n;
      height = n;
      int index = 0;
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            brd[i][j] = letterArray[index];
            index++;
         }
      }
      markAllUnvisited();       
   }
   
   /**
    * Creates a String representation of the brd.
    */
   public String getBoard() {
      String strBrd = "";
      for (int i = 0; i < height; i ++) {
         if (i > 0) {
            strBrd += "\n";
         }
         for (int j = 0; j < width; j++) {
            strBrd += brd[i][j] + " ";
         }
      }
      return strBrd;
   }
   
   /**
    * Retrieves all valid words on the game brd.
    * 
    * @param minimumWordLength The minimum allowed length (i.e., number of
    *     characters) for any word found on the brd.
    * @return java.util.SortedSet which contains all the words of minimum length
    *     found on the game brd and in the wordlist.
    * @throws IllegalArgumentException if minimumWordLength < 1
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public SortedSet<String> getAllValidWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (wordlist == null) {
         throw new IllegalStateException();
      }
      secpath = new ArrayList<Position>(); 
      wordSet = new TreeSet<String>();
      WrdSF = "";
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j ++) {
            WrdSF = brd[i][j];
            if (isValidWord(WrdSF) && WrdSF.length() >= minimumWordLength) {
               wordSet.add(WrdSF);
            }
            if (isValidPrefix(WrdSF)) {
               Position temp = new Position(i,j);
               secpath.add(temp);
               dfs2(i, j, minimumWordLength); 
               secpath.remove(temp);
            }
         }
      }
      return wordSet;
   }
   
   private void dfs2(int x, int y, int min) {
      Position start = new Position(x, y);
      markAllUnvisited(); 
      markPathVisited(); 
      for (Position p : start.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (isValidPrefix(WrdSF + brd[p.x][p.y])) {
               WrdSF += brd[p.x][p.y];
               secpath.add(p);
               if (isValidWord(WrdSF) && WrdSF.length() >= min) {
                  wordSet.add(WrdSF);
               }
               dfs2(p.x, p.y, min);
               secpath.remove(p);
               int endIndex = WrdSF.length() - brd[p.x][p.y].length();
               WrdSF = WrdSF.substring(0, endIndex);
            }
         }
      }
      markAllUnvisited(); 
      markPathVisited(); 
   }
   
  /**
   * Computes the cummulative score for the scorable words in the given set.
   * @param words The set of words that are to be scored.
   * @param minimumWordLength The minimum number of characters required per word
   * @return the cummulative score of all scorable words in the set
   * @throws IllegalArgumentException if minimumWordLength < 1
   * @throws IllegalStateException if loadLexicon has not been called.
   */  
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException();
      }
      if (wordlist == null) {
         throw new IllegalStateException();
      }
      int score = 0;
      Iterator<String> itr = words.iterator();
      while (itr.hasNext()) {
         String word = itr.next();
         if (word.length() >= minimumWordLength && isValidWord(word)
             && !isOnBoard(word).isEmpty()) {
            score += (word.length() - minimumWordLength) + 1;
         }
      }
      return score;
   }
   
   /**
    * Determines if the given word is in the wordlist.
    * 
    * @param wordToCheck The word to validate
    * @return true if wordToCheck appears in wordlist, false otherwise.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidWord(String wordToCheck) {
      if (wordlist == null) {
         throw new IllegalStateException();
      }
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      wordToCheck = wordToCheck.toUpperCase();
      return wordlist.contains(wordToCheck);
   }
   
   /**
    * Determines if there is at least one word in the wordlist with the 
    * given prefix.
    * 
    * @param prefixToCheck The prefix to validate
    * @return true if prefixToCheck appears in wordlist, false otherwise.
    * @throws IllegalArgumentException if prefixToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public boolean isValidPrefix(String prefixToCheck) {
      if (wordlist == null) {
         throw new IllegalStateException();
      }
      if (prefixToCheck == null) {
         throw new IllegalArgumentException();
      }
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = wordlist.ceiling(prefixToCheck);
      if (word != null) {
         return word.startsWith(prefixToCheck);
      }
      return false;
   }
      
   /**
    * Determines if the given word is in on the game brd. If so, it returns
    * the path that makes up the word.
    * @param wordToCheck The word to validate
    * @return java.util.List containing java.lang.Integer 
    * returns the objects with  the path
    * that makes up the word on the game brd.
    * @throws IllegalArgumentException if wordToCheck is null.
    * @throws IllegalStateException if loadLexicon has not been called.
    */
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException();
      }
      if (wordlist == null) {
         throw new IllegalStateException();
      }
      secpath = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      WrdSF = "";
      path = new ArrayList<Integer>();
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j ++) {
            if (wordToCheck.equals(brd[i][j])) {
               path.add(i * width + j); 
               return path;
            }
            if (wordToCheck.startsWith(brd[i][j])) {
               Position pos = new Position(i, j);
               secpath.add(pos); 
               WrdSF = brd[i][j]; 
               dfs(i, j, wordToCheck); 
               if (!wordToCheck.equals(WrdSF)) {
                  secpath.remove(pos);
               }
               else {
                  for (Position p: secpath) {
                     path.add((p.x * width) + p.y);
                  } 
                  return path;
               }
            }
         }
      }
      return path;
   }
   
   /**
   * Depth-First Search for isOnBoard.
   * @param x the x value
   * @param y the y value
   * @param wordToCheck the word to check for.
   */
   private void dfs(int x, int y, String wordToCheck) {
      Position start = new Position(x, y);
      markAllUnvisited();       
      markPathVisited(); 
      for (Position p: start.neighbors()) {
         if (!isVisited(p)) {
            visit(p);
            if (wordToCheck.startsWith(WrdSF + brd[p.x][p.y])) {
               WrdSF += brd[p.x][p.y];
               secpath.add(p);
               dfs(p.x, p.y, wordToCheck);
               if (wordToCheck.equals(WrdSF)) {
                  return;
               }
               else {
                  secpath.remove(p);
                  int endIndex = WrdSF.length() - brd[p.x][p.y].length();
                  WrdSF = WrdSF.substring(0, endIndex);
               }
            }
         }
      }
      markAllUnvisited(); 
      markPathVisited(); 
   }

   /**
   * Marks all positions unvisited.
   */
   private void markAllUnvisited() {
      visited = new boolean[width][height];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   /**
   * Marks path as visited.
   */
   private void markPathVisited() {
      for (int i = 0; i < secpath.size(); i ++) {
         visit(secpath.get(i));
      }
   }
   

   /**
    * Models an (x,y) position.
    */
   private class Position {
      int x;
      int y;
   
      /** Constructs a Position with coordinates (x,y). 
      */
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      /** Returns a string representation of this Position. 
      */
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      /** Returns all the neighbors of this Position. */
      public Position[] neighbors() {
         Position[] nebors = new Position[MAX_NEIGHBORS];
         int count = 0;
         Position p;
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     nebors[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(nebors, count);
      }
   }

   /**
    * checks if a position is valid.
    * @param p the position
    */
   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < width) && (p.y >= 0) && (p.y < height);
   }

   /**
    * Checks if a position has been visited.
    * @param p the position
    */
   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   /**
    * Mark this valid position as having been visited.
    */
   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }

}