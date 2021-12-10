/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package segmentasi.simplifikasi;

import java.util.ArrayList;
import java.util.List;
import segmentation.SentenceDetector;
import segmentation.SentenceDetectorFactory;
import simplification.Simplification;

/**
 *
 * @author Fiqih Fatwa
 */
public class SegmentasiSimplifikasi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SentenceDetectorFactory sentenceDetectorFactory = new SentenceDetectorFactory();
        SentenceDetector sentenceDetector = sentenceDetectorFactory.createSentenceDetector();
        
        String text = "Saya dan kamu punya email fiqih.fatwa@gmail.com. Saya belajar NLP Bahasa Indonesia di Jl. Prof. Dr. Soepomo SH no 11. Saya pergi ke pasar. Saya punya uang 5.000. Saya punya website www.fiqih.com. IP website saya 172.1.108.104 masih local.";
        List<String> sentences = sentenceDetector.detect(text);
        
        List<String> singleSentences = new ArrayList<>();
        Simplification simplifikasi = new Simplification();
        
        for(String sentence : sentences){
            String singleSentence = simplifikasi.HasilSemua(sentence);
            singleSentences.add(singleSentence);
        }
        
        System.out.println(singleSentences); 

    }
    
}
