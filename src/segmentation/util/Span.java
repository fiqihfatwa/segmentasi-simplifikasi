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
public class Span implements SpanInterface {
    
    private int start = 0;
    
    private int end = 0;
    
    private String type;
    
    public Span(int _start, int _end, String _type){
       if(_start < 0){
           throw new UnsupportedOperationException("start index must be zero or greater: " + _start + " given.");
       } 
       
       if(_end < 0){
           throw new UnsupportedOperationException("end index must be zero or greater: " + _end + " given.");
       }
       
       if(_start > _end){
           throw new UnsupportedOperationException("start index can not be greater than end index: start=" + _start + " end=" +_end);
       }
       
       this.start = _start;
       this.end = _end;
       this.type = _type;
    }
    
    @Override
    public int getStart(){
        return this.start;
    }
    
    @Override
    public int getEnd(){
        return this.end;
    }
    
    @Override
    public String getType(){
        return this.type;
    }
    
    @Override
    public int getLength(){
        return this.end - this.start;
    }
    
    @Override
    public String getCoveredText(String text){
//        System.out.println("Span:getCoveredText:this.start => " + this.start + " Span:getCoveredText:this.end => " + this.end);
        return text.substring(this.start, this.end);
    }
    
    @Override
    public boolean contains(SpanInterface span){
        return this.start <= span.getStart() && span.getStart() <= this.end;
    }
    
    @Override
    public boolean containsIndex(int index){
        return this.start <= index && index < this.end;
    }
    
    @Override
    public boolean startsWith(SpanInterface span){
        return this.getStart() == span.getStart() && this.contains(span);
    }

    @Override
    public boolean intersects(SpanInterface span) {
        int sstart = span.getStart();
        
        return this.contains(span) || span.contains(this) || 
                this.getStart() <= sstart && sstart < this.getEnd() ||
                sstart <= this.getStart() && this.getStart() < span.getEnd();
    }

    @Override
    public boolean crosses(SpanInterface span) {
        int sstart = span.getStart();
        
        return !this.contains(span) && !span.contains(this) &&
                (this.getStart() <= sstart && sstart < this.getEnd() ||
                sstart <= this.getStart() && this.getStart() < span.getEnd());
    }

    @Override
    public String __toString() {
        String string = "["+this.getStart()+".."+this.getEnd()+")";
        
        if(this.getType() != null){
            string += " "+this.getType();
        }
        
        return string;
    }
}
