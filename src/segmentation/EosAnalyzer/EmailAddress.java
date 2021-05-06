/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

import java.util.Arrays;
import segmentation.util.StringUtil;

/**
 *
 * @author win7
 */
public class EmailAddress implements AnalyzerInterface {
    
    private String[] eosChars;
    
    private StringUtil stringUtil = new StringUtil();;
    
    public EmailAddress() {
        this.eosChars = stringUtil.getStandardEosChars();
    }

    @Override
    public boolean shouldSplit(Model model) {
        
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        
        String token = this.getToken(model.getText(), model.getPosition());
        
//        System.out.println("EmailAddress:shouldSplit:token => " + token + " " + token.matches(regex));
       
        if (token != "" && token.matches(regex)) {
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
}
