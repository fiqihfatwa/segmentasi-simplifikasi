/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

import java.util.List;
import segmentation.util.StringUtil;

/**
 *
 * @author Fiqih Fatwa
 */
public class Abbreviation implements AnalyzerInterface {
    
    private List<String> abbreviationDictionary;
    
    private StringUtil stringUtil = new StringUtil();
    
    public Abbreviation(List<String> _abbreviationDictionary){
        this.abbreviationDictionary = _abbreviationDictionary;
    }

    @Override
    public boolean shouldSplit(Model model) {
        String text     = model.getText();
        int position = model.getPosition();

        // detect wether preceding token is an abbreviation
        if (position != 0) {
            if (this.abbreviationDictionary.contains(this.getPrecedingToken(text, position).toLowerCase())) {
                return false;
            }
        }

        // detect wether preceding token is part of an abbreviation
        // for example: a.n., e.g., i.e., a.m.v.b.
        if (position < text.length() - 1) {
            String token = this.getToken(text, position);

            if (token != "" && token.indexOf(".") != -1) {
                if (this.abbreviationDictionary.contains(token.toLowerCase())) {
                    return false;
                }
            }
        }

        return true;
    }
    
    private String getPrecedingToken(String text, int position)
    {
        int start = position;
        while (start > 0 && !stringUtil.isWhitespace(Character.toString(text.charAt(start - 1)))) {
            start--;
        }

        String precedingToken = text.substring(start, position);

        return precedingToken;
    }
    
    private String getToken(String text, int position)
    {
        if (position >= text.length() - 1) {
            return "";
        }

        int nextWs = stringUtil.getNextWhitespace(text, position);
        int prevWs = stringUtil.getPrevWhitespace(text, position);

        int tokenStart = (prevWs == -1) ? 0 : prevWs + 1;
        int tokenEnd   = ((nextWs == -1) ? text.length() : nextWs) - 1;

        String token  = text.substring(tokenStart, tokenEnd);

        return token;
    }
    
}
