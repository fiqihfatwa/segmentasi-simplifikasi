/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Fiqih Fatwa
 */
public interface EndOfSentenceScannerInterface {
    
    public String[] getEndOfSentenceCharacters();
    
    public List<Integer> getPositions(String string);
    
}
