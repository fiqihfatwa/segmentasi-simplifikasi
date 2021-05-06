/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author win
 */
public class RepetitiveEos implements AnalyzerInterface {

    @Override
    public boolean shouldSplit(Model model) {
        List<Integer> eos = model.getEosPositions();
        
        int i = eos.indexOf(model.getPosition());

//        System.out.println("RepetitiveEos:shouldSplit => " + i);
        
        if (i < (eos.size()-1) && eos.get(i+1) == eos.get(i)+1) {
            return false;
        }

        return true;
    }
    
}
