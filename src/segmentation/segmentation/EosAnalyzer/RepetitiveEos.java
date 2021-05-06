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
        
//        boolean i = Arrays.asList(eos).contains(model.getPosition());
//        
//        if(i == false){
//            return true;
//        }
//        
//        if (i < eos.length) {
//            return false;
//        }

        return true;
    }
    
}
