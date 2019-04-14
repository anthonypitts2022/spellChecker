//Author: Anthony Pitts

import java.io.*;
import java.util.*;

public class SpellChecker implements SpellCheckerInterface{
    //hases a set of words from a given dictionary file
    private Set<String> dictionary = new HashSet<>();
    
    //constructor that takes the dictionary and puts the words into dictionary set
    public SpellChecker(String filename){
        File dictFile = new File(filename);
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String currentLine = reader.readLine();
            while(currentLine!=null){
                //removes the punctuation and splits words by blank space
                String linePunctuationRemoved = currentLine.replaceAll("[^a-zA-Z]", "").toLowerCase();
                String [] wordsOnLine = linePunctuationRemoved.split("\\s+");
                //adds each word from dictionary file to dictionary set
                for(String eachWord : wordsOnLine){ 
                    dictionary.add(eachWord);
                }
                currentLine = reader.readLine();
            }
        }
        catch(Exception excep){
            excep.printStackTrace();
        }
    }
    
    //finds words in input file that are not in dictionary
    public List<String> getIncorrectWords(String filename){
        File inputFile = new File(filename);
        //list of all misspelled words
        List<String> badWords = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String currentLine = reader.readLine();
            while(currentLine!=null){
                if(!currentLine.equals("")){
                    String [] wordsOnLine = currentLine.split("\\s+");
                    for(String curWord : wordsOnLine){
                        //removes punctuation of word
                        String wordPunctuationRemoved = curWord.replaceAll("[^a-zA-Z]", "").toLowerCase();
                        //if not in dictionary, it is added to misspelled words list
                        if(false == dictionary.contains(wordPunctuationRemoved)){
                            badWords.add(wordPunctuationRemoved);
                        }
                    }
                }
                currentLine = reader.readLine();
            }
        }
        catch(Exception excep){
            excep.printStackTrace();
        }
        //returns a list of all misspelled words
        return badWords;
    }
    
    public Set<String> getSuggestions(String word){
        Set<String> suggestionSet = new HashSet<>(); //set of all suggested words
        String potentialWord = "";
        
        //test for removing a character
        
        for(int i=0; i<word.length(); i++){
            potentialWord = word.toLowerCase().substring(0,i) + word.toLowerCase().substring(i+1,word.length());
            //if word with removed character is in dictionary, add it to suggested
            if(dictionary.contains(potentialWord)){
                suggestionSet.add(potentialWord);
            }
        }
        
        
        //test for adding a character
        
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for(int i=0; i<word.length(); i++){
            for(char charac : alphabet){
                potentialWord = word.toLowerCase().substring(0,i) + charac + word.toLowerCase().substring(i,word.length());
                //if word with added character is in dictionary, add it to suggested
                if(dictionary.contains(potentialWord)){
                    suggestionSet.add(potentialWord);
                }
                //addresses the special case of adding letter at the end of the word
                if(i==word.length()-1){
                    potentialWord = word + charac;
                    if(dictionary.contains(potentialWord)){
                        suggestionSet.add(potentialWord);
                    }
                }
            }
        }
        
        
        //test for swapping characters
    
        for(int i=1; i<word.length(); i++){
            //if at any index other than the last
            if(i!=word.length()-1){
                //swaps two adjacent characters in word
                potentialWord = word.toLowerCase().substring(0,i-1) +word.toLowerCase().substring(i,i+1) + word.toLowerCase().substring(i-1,i) + word.toLowerCase().substring(i+1,word.length());
            }
            //addresses special case of last index
            else{
                potentialWord = word.toLowerCase().substring(0,i-1) +word.toLowerCase().substring(i,i+1) + word.toLowerCase().substring(i-1,i);
            }
            //if word with swapped characters is in dictionary, add it to suggested
            if(dictionary.contains(potentialWord)){
                suggestionSet.add(potentialWord);
            }
        }
    // returns the suggested set of words for each misspelled word
    return suggestionSet;
    }

}