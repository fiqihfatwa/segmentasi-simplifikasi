/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Fiqih Fatwa
 */
public class EndOfSentenceScanner implements EndOfSentenceScannerInterface {
    
    private String[] eosChars;
    
    public EndOfSentenceScanner(String[] _eosChars) {
        this.eosChars = _eosChars;
    }
    
    public EndOfSentenceScanner(){
        String[] _eosChars = {".", "?", "!"};
        this.eosChars = _eosChars;
    }

    @Override
    public String[] getEndOfSentenceCharacters() {
        return this.eosChars;
    }

    @Override
    public List<Integer> getPositions(String string) {

        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            if (Arrays.asList(this.eosChars).contains(Character.toString(string.charAt(i))) != false) {
                positions.add(i);
            }
        }

//        System.out.println("EndOfSentenceScanner:getPositions:positions => " + positions);
        return positions; 
    }
    
}
