/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

/**
 *
 * @author Fiqih Fatwa
 */
public class LeadingEos implements AnalyzerInterface {

    @Override
    public boolean shouldSplit(Model model) {
        if (model.getText().substring(0, model.getPosition()).trim() == "") {
            return false;
        }

        return true;
    }
    
}
