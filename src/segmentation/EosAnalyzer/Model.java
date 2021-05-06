/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentation.EosAnalyzer;

import java.util.List;

/**
 *
 * @author win
 */
public class Model {
    
    private String text;
    
    private List<Integer> eosPositions;
    
    private int position;
    
    public Model(String _text, List<Integer> _eosPositions, int _position){
        this.text = _text;
        this.eosPositions = _eosPositions;
        this.position = _position;
    }
    
    public String getText(){
        return this.text;
    }
    
    public List<Integer> getEosPositions(){
        return this.eosPositions;
    }
    
    public int getPosition(){
        return this.position;
    }
}
