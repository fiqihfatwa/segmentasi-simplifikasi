/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.ArrayList;
import java.util.List;
import segmentation.util.Span;
import segmentation.util.StringUtil;

/**
 *
 * @author win7
 */
public class SentenceDetector implements SentenceDetectorInterface {

    private EndOfSentenceScannerInterface eosScanner;
    
    private StringUtil stringUtil;
    
    SentenceDetector(EndOfSentenceScannerInterface _eosScanner) {
        this.eosScanner = _eosScanner;
        this.stringUtil = new StringUtil();
    }

    @Override
    public List<String> detect(String text) {
        List<Span> spans = this.detectPositions(text);
        List<String> sentences = new ArrayList<>();

//        System.out.println("SentenceDeteror:detect:spans => " + spans);
        
        for (Span span : spans) {
            sentences.add(span.getCoveredText(text));
        }

        return sentences;
    }

    @Override
    public List<Span> detectPositions(String text) {
        
        List<Integer> positions = this.eosScanner.getPositions(text);   
        List<Span> spans = new ArrayList<>();
//        System.out.println("SentenceDeteror:detectPositions:positions => " + positions);
        
        int start, end;

        // string does not contain any sentence end positions
        if (positions.isEmpty()) {
            start = stringUtil.getNextNonWhitespace(text);
            end   = stringUtil.getPrevNonWhitespace(text);

            if ((end - start) > 0) {
                spans.add(new Span(start, end + 1, null));
            }

            return spans;
        }
        
        // convert positions to spans
        spans = new ArrayList<>();
        
        int i = 0;
        for(int pos : positions) {
            // first sentence starts from 0, else starts from $pos[$i-1] + 1
            if (i == 0) {
                start = stringUtil.getNextNonWhitespace(text);
            } else {
                // start from previous eos position + 1
                start = positions.get(i - 1) + 1;

                // ignore closing quote if exist
                if (text.substring(start, start + 1) == "\"") {
                    start++;
                }

                start = stringUtil.getNextNonWhitespace(text, start - 1);
            }

            end = stringUtil.getPrevNonWhitespace(text, pos);
            end++;
            
            // include closing quote if exist
            if (end < text.length() - 1) {
                if (text.substring(end + 1, end + 2) == "\"") {
                    end++;
                }
            }

            // .   .   .
            if (end < start) {
                end = start;
            }

            spans.add(new Span(start, end + 1, null));
            i++;
        }
        
        Span leftover = this.getLeftoverSpan(text, positions);
        if (leftover != null) {
            spans.add(leftover);
        }

        return spans;
    }
    
    private Span getLeftoverSpan(String text, List<Integer> positions)
    {
        if (positions.get(positions.size() - 1) != text.length() - 1) {
            int start = stringUtil.getNextNonWhitespace(text, positions.get(positions.size() - 1));
            int end = stringUtil.getPrevNonWhitespace(text);

            if (start != -1 && (end - start) > 0) {
                return new Span(start, end + 1, null);
            }
        }
        return null;
    }
}
