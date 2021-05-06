/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import segmentation.FuzzyEndOfSentenceScanner;
import segmentation.SentenceDetector;

/**
 *
 * @author win7
 */
public class SentenceDetectorFactory {
    
    public SentenceDetector createSentenceDetector(){
        
        FuzzyEndOfSentenceScanner eosScanner = new FuzzyEndOfSentenceScanner();
        SentenceDetector sentenceDetector = new SentenceDetector(eosScanner);
        
        return sentenceDetector;
    }
}
