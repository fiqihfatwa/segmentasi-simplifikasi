/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.Arrays;

/**
 *
 * @author win7
 */
public class EndOfSentenceScanner implements EndOfSentenceScannerInterface {
    
    private String[] eosChars;
    
    public EndOfSentenceScanner(String[] _eosChars) {
        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};
        
        if(_eosChars.length == 0){
            cars = {".", "?", "!"};
        }
        
        this.eosChars = _eosChars;
    }

    @Override
    public String getEndOfSentenceCharacters() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Arrays getPositions(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
