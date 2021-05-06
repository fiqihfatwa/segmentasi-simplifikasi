/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import segmentation.util.StringUtil;

/**
 *
 * @author win7
 */
public class Tld implements AnalyzerInterface {
    
    private List<String> tlds;
    
    private String[] eosChars;
    
    private StringUtil stringUtil = new StringUtil();
    
    public Tld(List<String> _tlds){
        this.tlds = _tlds;
        this.eosChars = stringUtil.getStandardEosChars();
    }

    @Override
    public boolean shouldSplit(Model model) {
        
        String token = this.getToken(model.getText(), model.getPosition());
        
        if (token != "" && this.isValidTld(token)) {
            return false;
        } 

        return true;
    }
    
    private String getToken(String text, int position)
    {
        if (position < (text.length() - 1) && !stringUtil.isWhitespace(text.substring(position + 1, position + 2))) {
            int nextWs = stringUtil.getNextWhitespace(text, position);
            int prevWs = stringUtil.getPrevWhitespace(text, position);

            int tokenStart = (prevWs == -1) ? 0 : prevWs + 1;
            int tokenEnd = (nextWs == -1) ? text.length() : nextWs;

            String token = text.substring(tokenStart, tokenEnd);

            // strip trailing .
            if (!token.isEmpty() && Arrays.asList(this.eosChars).contains(Character.toString(token.charAt(token.length() - 1)))) {
                token = token.substring(0, token.length() - 1) ;
            }

            return token;
        } else {
            return "";
        }
    }
    
    private boolean isValidTld(String string)
    {
        for (String tld : this.tlds) {
            String regex1 = "\\"+tld+"$";
            Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
            Matcher matcher1 = pattern1.matcher(string);
            
            String regex2 = "-?\\"+tld+"([\\/\\#\\.])";
            Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
            Matcher matcher2 = pattern2.matcher(string);
            
            if (matcher1.find() || matcher2.find()) {
                return true;
            }
        }
            
        return false;
    }
}
