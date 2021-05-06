/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.util;

/**
 *
 * @author Fiqih Fatwa
 */
public interface SpanInterface {
    
    public int getStart();
    
    public int getEnd();
    
    public String getType();
    
    public int getLength();
    
    public String getCoveredText(String text);
    
    public boolean contains(SpanInterface span);
    
    public boolean containsIndex(int index);
    
    public boolean startsWith(SpanInterface span);
    
    public boolean intersects(SpanInterface span);
    
    public boolean crosses(SpanInterface span);
    
    public String __toString();
}
