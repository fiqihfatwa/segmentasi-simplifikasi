/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.util;

import java.util.Arrays;


/**
 *
 * @author Fiqih Fatwa
 */
public class StringUtil {
    private final String[] whitespaceChars = {" ", "\t", "\n"};
    private final String[] standardEosChars = {".", "?", "!"};
    
    public boolean isWhitespace(String string){    
        return Arrays.asList(whitespaceChars).contains(string);
    }
    
    public int getNextWhitespace(String string, int start){
        while(start < (string.length()-1) && !isWhitespace(Character.toString(string.charAt(start + 1)))){
            start++;
        }
        
        if(start == (string.length()-1)){
            return -1;
        }
            
        start++;
        
        return start;
    }
    
    public int getNextWhitespace(String string){
        int start = -1;
        
        while(start < (string.length()-1) && !isWhitespace(Character.toString(string.charAt(start + 1)))){
            start++;
        }
        
        if(start == (string.length()-1)){
            return -1;
        }
            
        start++;
        
        return start;
    }
    
    public int getPrevWhitespace(String string, int start){
        while(start > 0 && !isWhitespace(Character.toString(string.charAt(start - 1)))){
            start--;
        }
        
        if(start == 0){
            return -1;
        }
            
        start--;
        
        return start;
    }
    
    public int getPrevWhitespace(String string){
        int start = string.length();
        
        while(start > 0 && !isWhitespace(Character.toString(string.charAt(start - 1)))){
            start--;
        }
        
        if(start == 0){
            return -1;
        }
            
        start--;
        
        return start;
    }
    
    public int getNextNonWhitespace(String string, int start){
        while(start < (string.length()-1) && isWhitespace(Character.toString(string.charAt(start + 1)))){
            start++;
        }
        
        if(start == (string.length()-1)){
            return -1;
        }
            
        start++;
        
        return start;
    }
    
    public int getNextNonWhitespace(String string){
        int start = -1;
        
        while(start < (string.length()-1) && isWhitespace(Character.toString(string.charAt(start + 1)))){
            start++;
        }
        
        if(start == (string.length()-1)){
            return -1;
        }
            
        start++;
        
        return start;
    }
    
    public int getPrevNonWhitespace(String string, int start){
        while(start > 0 && isWhitespace(Character.toString(string.charAt(start - 1)))){
            start--;
        }
        
        if(start == 0){
            return -1;
        }
            
        start--;
        
        return start;
    }
    
    public int getPrevNonWhitespace(String string){
        int start = string.length();
        
        while(start > 0 && isWhitespace(Character.toString(string.charAt(start - 1)))){
            start--;
        }
        
        if(start == 0){
            return -1;
        }
            
        start--;
        
        return start;
    }
    
    public String[] getStandardEosChars(){
        return this.standardEosChars;
    }
}
