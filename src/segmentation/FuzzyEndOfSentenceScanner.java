/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.ArrayList;
import java.util.List;
import segmentation.EosAnalyzer.AnalyzerInterface;
import segmentation.EosAnalyzer.Model;
import segmentation.EosAnalyzer.RepetitiveEos;

/**
 *
 * @author Fiqih Fatwa
 */
public class FuzzyEndOfSentenceScanner implements EndOfSentenceScannerInterface {
    
    private EndOfSentenceScanner defaultScanner;
    
    private List<AnalyzerInterface> eosAnalyzers = new ArrayList<>();
    
    public FuzzyEndOfSentenceScanner() {
         this.defaultScanner = new EndOfSentenceScanner();
         
         this.eosAnalyzers.add(new RepetitiveEos());
    }

    @Override
    public String[] getEndOfSentenceCharacters() {
        return this.defaultScanner.getEndOfSentenceCharacters();
    }

    @Override
    public List<Integer> getPositions(String string) {
        List<Integer> enders = this.defaultScanner.getPositions(string);
        List<Integer> candidates = new ArrayList<>();
        
        for (int i = 0; i < enders.size(); i++) {
            if (this.shouldSplit(string, enders, enders.get(i))) {
                candidates.add(enders.get(i));
            }
        }
        
//        System.out.println("FuzzyEndOfSentenceScanner:getPositions:enders => " + enders);
        return candidates;
    }
    
    private boolean shouldSplit(String text, List<Integer> eosPositions, int position)
    {
        Model model = new Model(text, eosPositions, position);

        for (AnalyzerInterface analyzer : this.eosAnalyzers) {
            if (analyzer.shouldSplit(model) == false) {
                return false;
            }
        }

        return true;
    }
    
}
