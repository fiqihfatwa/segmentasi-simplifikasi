/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation;

import java.util.List;
import segmentation.util.Span;

/**
 *
 * @author Fiqih Fatwa
 */
public interface SentenceDetectorInterface {
    
    public List<String> detect(String text);
    
    public List<Span> detectPositions(String text);
    
}
