/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

/**
 *
 * @author win7
 */
public class NumericSeparator implements AnalyzerInterface {

    @Override
    public boolean shouldSplit(Model model) {
        String regex = "-?\\d+(\\.\\d+)?";
        
        int position = model.getPosition();
        String text = model.getText();

        if (position != 0 && position < text.length() - 1) {
            String prevChar = text.substring(position - 1, position);
            String nextChar = text.substring(position + 1, position + 2);

            if (prevChar.matches(regex) && nextChar.matches(regex)) {
                return false;
            }
        }

        return true;
    }
    
}
