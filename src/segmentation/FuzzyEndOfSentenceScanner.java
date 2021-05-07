/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import segmentation.EosAnalyzer.Abbreviation;
import segmentation.EosAnalyzer.AnalyzerInterface;
import segmentation.EosAnalyzer.EmailAddress;
import segmentation.EosAnalyzer.LeadingEos;
import segmentation.EosAnalyzer.Model;
import segmentation.EosAnalyzer.NumericSeparator;
import segmentation.EosAnalyzer.RepetitiveEos;
import segmentation.EosAnalyzer.Tld;

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
         this.eosAnalyzers.add(new LeadingEos());
         this.eosAnalyzers.add(new EmailAddress());
         this.eosAnalyzers.add(new NumericSeparator());
         
        try {
            Scanner fileTld = new Scanner(new File("src/segmentation/data/tld.txt"));
            List<String> tlds = new ArrayList<>();
            while (fileTld.hasNext()){
                tlds.add(fileTld.next());
            }
            fileTld.close();
            
            this.eosAnalyzers.add(new Tld(tlds));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FuzzyEndOfSentenceScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        try {
            Scanner fileAbbreviation = new Scanner(new File("src/segmentation/data/abbreviations.txt"));
            List<String> abbreviations = new ArrayList<>();
            while (fileAbbreviation.hasNext()){
                abbreviations.add(fileAbbreviation.next());
            }
            fileAbbreviation.close();
            
            this.eosAnalyzers.add(new Abbreviation(abbreviations));
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FuzzyEndOfSentenceScanner.class.getName()).log(Level.SEVERE, null, ex);
        }      
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
