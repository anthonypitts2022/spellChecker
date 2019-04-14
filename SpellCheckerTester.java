import java.io.*;
import java.util.*;
public class SpellCheckerTester{
    public static void main(String args[]){
        SpellChecker testChecker = new SpellChecker("words.txt");
        List<String> badWordsList = testChecker.getIncorrectWords("test.txt");
        for(String eachWord : badWordsList){
            Set<String> suggSet = testChecker.getSuggestions(eachWord);
            System.out.println("Instead of: " + eachWord);
            System.out.println("Try: ");
            System.out.println(suggSet);
        }
    }
}