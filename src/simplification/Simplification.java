/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplification;

import java.util.ArrayList;
import NLP_ITB.POSTagger.HMM.Train.MainTrainer;
import NLP_ITB.POSTagger.HMM.Decoder.MainTagger;

/**
 *
 * @author Fiqih Fatwa
 */
public class Simplification {
    
    private static final ArrayList<Tag> listPostTag = new ArrayList<>();
    private static ArrayList<String> listPostTag1 = new ArrayList<>();
    
    public Boolean KalimatMajemuk(String sentence) {
        String[] konjungsi = new String[]{"yang", "dan", "atau", "tetapi", "melainkan", "bahkan", "sedangkan","kemudian", "ketika", "sehingga", "karena", "lalu", "meskipun", "agar"};
        String hasil = "", kalimat1, kalimat2, konjungsinya;
        String kumpulan = sentence;
        kumpulan = cleaning(kumpulan);
        kumpulan = cleaninglagi(kumpulan);
        kumpulan = kumpulan.trim();

        String kalimat[] = kumpulan.split("\\r?\\n");

        for(int i=0; i<kalimat.length;i++){
             kalimat[i] = kalimat[i].trim();
             for(int j=0; j<konjungsi.length; j++){
                 if(kalimat[i].contains(" "+konjungsi[j]+" ")==true){
                     String[] parts = kalimat[i].split(" "+konjungsi[j]+" ");

                     hasil= simplifikasi(parts[0], konjungsi[j], parts[1]);
                     
                     return true;
                  }
             }

         }

         return false;
    }
    
    public String HasilSemua(String sentence) {
       String[] konjungsi = new String[]{"yang", "dan", "atau", "tetapi", "melainkan", "bahkan", "sedangkan","kemudian", "ketika", "sehingga", "karena", "lalu", "meskipun", "agar"};
       
       String hasil = "", kalimat1, kalimat2, konjungsinya;
       String kumpulan = sentence;
       kumpulan = cleaning(kumpulan);
       kumpulan = cleaninglagi(kumpulan);
       kumpulan = kumpulan.trim();
       
       String kalimat[] = kumpulan.split("\\r?\\n");
       
       for(int i=0; i<kalimat.length;i++){
            kalimat[i] = kalimat[i].trim();
            for(int j=0; j<konjungsi.length; j++){
                if(kalimat[i].contains(" "+konjungsi[j]+" ")==true){
                    String[] parts = kalimat[i].split(" "+konjungsi[j]+" ");
//                    String[] spok1 = cekspok(parts[0] + " -");
//                    String[] spok2 = cekspok(parts[1] + " -");
                    
                    hasil= simplifikasi(parts[0], konjungsi[j], parts[1]);
                    
//                    System.out.println("hasil >> "+hasil); 
                    break;
                 }
                else
                {
                    if(j == konjungsi.length - 1)
                    {
                        hasil = kumpulan; 
                    }
                }
                
            }
            
//            String[] kumpulann = hasil.split("\n");
//            kalimat1 = kumpulann[0];
//            konjungsinya = kumpulann[1];
//            kalimat2 = kumpulann[2];
//            
//            if(kalimat[i].contains("ketika")==true){
//                String word = "ketika";
//                String finalSentence = "";
//                
//                String[] parts = kalimat[i].split(" ");
//                String [] s = parts[0].split(" ");
//                for (int j = 0; j < s.length; j++) {
//                    if (word.equals(s[j])) {
//                        continue;
//                    } else {
//                        finalSentence += s[j] + " ";
//                    }
//                
//                }
                
//                String[] spok1 = cekspok(finalSentence + " -");
//                String[] spok2 = cekspok(parts[1] + " -");

//                hasil= simplifikasi(finalSentence, "ketika", parts[1]);
//            }
            
        }
        
        return hasil;
    }
    
    public static String cleaning(String kalimat){
//        String hasilcleaning = "";
        kalimat = kalimat.trim();
        kalimat = kalimat.replace("!", ".");
        kalimat = kalimat.replace(":", ".");
        kalimat = kalimat.replace(";", ".");
        kalimat = kalimat.replace("=", ".");
        kalimat = kalimat.replace("?", ".");
        kalimat = kalimat.replace("\"", "");
        kalimat = kalimat.replaceAll("[!\"#$%&'*+/:;<=>?@\\[\\]^_`{|}~]", "");
//        String kumpulan[] = kalimat.split("\\.");
//        for (String kumpulan1 : kumpulan) {
//            if(kumpulan1.charAt(0) == ' ')
//            {
//                kumpulan1 = kumpulan1.substring(1);
//            }
//            hasilcleaning = hasilcleaning + kumpulan1 + "\n";
//        }
        kalimat = kalimat.replace(".", " ");
        System.out.println("cleaning1 >> " + kalimat);
        String hasilcleaning = kalimat;
        
        return hasilcleaning;
    }
    
    public static String cleaninglagi(String kalimat){
        String hasilcleaning = "";
        kalimat = kalimat.trim();
        String kumpulan[] = kalimat.split("\\r?\\n");
        for(int i=0; i < kumpulan.length; i++)
        {
            String banyaksuku[] = kumpulan[i].split(" ");
            if(banyaksuku.length >= 2)
            {
                hasilcleaning = hasilcleaning + kumpulan[i] + "\n";
            }
        }
        
        System.out.println("cleaning2 >> " + hasilcleaning);
        return hasilcleaning;
    }
    
    public static String[] cekspok(String kalimat) {
        ArrayList<String> listTag = new ArrayList<>();
        listTag.clear();
        listPostTag.clear();
        MainTrainer.Train("src\\resource\\Corpus.crp");
        String hasil = "";
        int lm 		= 1;
	int affix	= 1;
	int pass2	= 0;
	int lex		= 1;
	MainTagger mt = new MainTagger("src\\resource\\Lexicon.trn", "src\\resource\\Ngram.trn", lm, 3, 3, 0, affix, false, 0.2, pass2, 500.0, lex);
	ArrayList<String> ret = mt.taggingStr(kalimat);
        ret.stream().map((ret1) -> ret1.split("/")).map((t) -> {
            listPostTag.add(new Tag(t[0], t[1]));
            return t;
        }).forEach((t) -> {
            listTag.add(t[1]);
        });
        listPostTag1 = ret;
        System.out.println("List Tag :" + ret);
        String[] spok = cek_kalimatspok(listPostTag);
        return spok;
    }
    
    public static String[] cek_kalimatspok(ArrayList<Tag> listPosTag) {
        String[] spok;
        spok = new String[5];
        for(int i=0; i<spok.length;i++)
        {
            spok[i] = "";
        }
        String S = "";
        String P = "";
        String O = "";
        String K = "";
        String PEL = "";
        //String KON = "";
        
        //S
        int i = 0;
        if (listPosTag.size() >= (i + 4)
                && ((listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("SC") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("NNG"))
                || (listPosTag.get(i).getTag().equals("NNP") && listPosTag.get(i + 1).getTag().equals("NNP") && listPosTag.get(i + 2).getTag().equals("CC") && listPosTag.get(i + 3).getTag().equals("NNG"))
                || (listPosTag.get(i).getTag().equals("NNP") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("CDP") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("DT")))) {
            S = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata() + " " + listPosTag.get(i + 3).getKata();
            i = i + 4;
        }       
        else if (listPosTag.size() >= (i + 3)
                && ((listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("CC") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("CDO") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNP") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CDO") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNU") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NNU") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("PRP")))) {
            S = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata();
            i = i + 3;
        } else if (listPosTag.size() >= (i + 2)
                && ((listPosTag.get(i).getTag().equals("PRP") && listPosTag.get(i).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("PRP") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("PRP") && listPosTag.get(i + 1).getTag().equals("CDC"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("CDO"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NNG"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("DT") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("NNP") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("SC"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNU") && listPosTag.get(i + 1).getTag().equals("DT")))) {
            S = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata();
            i = i + 2;
        } else if (listPosTag.size() >= (i + 1)
                && ((listPosTag.get(i).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("PRN"))
                || (listPosTag.get(i).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("DT")))) {
            S = listPosTag.get(i).getKata();
            i = i + 1;
        }
//        System.out.println("===============================\nS:" + S);
        //P ( PREDIKAT )
        if (listPosTag.size() >= (i + 3)
                && ((listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("MD") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("MD") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("NEG") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("NEG") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBI") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBI") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBT") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBT") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("JJ") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("JJ") && listPosTag.get(i + 2).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("VBI") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("MD") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("VBI") && listPosTag.get(i + 2).getTag().equals("VBT")))) {
            P = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata();
            i = i + 3;
        } else if (listPosTag.size() >= (i + 2)
                && ((listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("VBI") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("VBI") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("VBT") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("VBT") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("VBT") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("JJ") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("JJ") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("JJ")))) {
            P = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata();
            i = i + 2;
        } else if (listPosTag.size() >= (i + 1) && ((listPosTag.get(i).getTag().equals("VBT")
                || listPosTag.get(i).getTag().equals("VBI")|| listPosTag.get(i).getTag().equals("JJ") | listPosTag.get(i).getTag().equals("CDP") || listPosTag.get(i).getTag().equals("MD")))) {
            P = listPosTag.get(i).getKata();
            i = i + 1;
        }
//        System.out.println("===============================\nP:" + P);
        //O
        if (listPosTag.size() >= (i + 3)
                && ((listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNP") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CDP") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NEG") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNU")))) {
            //perhatikan i sudah bertambah dari p
            O = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata();
            i = i + 3;
        } else if (listPosTag.size() >= (i + 2)
                && ((listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("CDP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("NNU") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("CDI") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("JJ") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("CDP") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NNU") && listPosTag.get(i + 1).getTag().equals("NNC")))) {
            O = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata();
            i = i + 2;
        } else if (listPosTag.size() >= (i + 1)
                && (listPosTag.get(i).getTag().equals("NN")
                || listPosTag.get(i).getTag().equals("NNC")
                || listPosTag.get(i).getTag().equals("RB")
                || listPosTag.get(i).getTag().equals("NNU")
                || listPosTag.get(i).getTag().equals("NNG")
                || listPosTag.get(i).getTag().equals("NNP")
                || listPosTag.get(i).getTag().equals("PRP")
                || listPosTag.get(i).getTag().equals("FW"))) {
            O = listPosTag.get(i).getKata();
            i = i + 1;
        }
//        System.out.println("===============================\nO:" + O);
        //Kel
        if (listPosTag.size() >= (i + 3)
                && ((listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NEG") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NEG") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("RB") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("MD") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBT") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBI") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("MD") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN")))) {
            PEL = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata();
            i = i + 3;
        } else if (listPosTag.size() >= (i + 2)
                && ((listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("CDO"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("JJ")))) {
            PEL = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata();
            i = i + 2;
        } else if ((listPosTag.size() >= i + 1)
                && (listPosTag.get(i).getTag().equals("JJ") 
                || listPosTag.get(i).getTag().equals("CDO")
                || listPosTag.get(i).getTag().equals("VBT")
                || listPosTag.get(i).getTag().equals("VBI"))) {
            PEL = listPosTag.get(i).getKata();
            i = i + 1;
        }
//        System.out.println("===============================\nPEL:" + PEL);
        
        //K
        if (listPosTag.size() >= (i + 4) && (((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NNP")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NNC")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NNU")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NNG")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("VBI")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("RB") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("CDP") && listPosTag.get(i + 3).getTag().equals("NNC")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NNP") && listPosTag.get(i + 3).getTag().equals("DT")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("CDP") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("CDP") && listPosTag.get(i + 2).getTag().equals("CDP") && listPosTag.get(i + 3).getTag().equals("NNC")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("CDP") && listPosTag.get(i + 2).getTag().equals("CDP") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("CDP") && listPosTag.get(i + 2).getTag().equals("NNC") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("DT")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNP") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNP") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("NNP")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNP") && listPosTag.get(i + 2).getTag().equals("NNP") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNC") && listPosTag.get(i + 3).getTag().equals("NN")))
                || ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("VBI") && listPosTag.get(i + 2).getTag().equals("IN") && listPosTag.get(i + 3).getTag().equals("JJ")))
                || ((listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NNC") && listPosTag.get(i + 3).getTag().equals("NNC")))
                || ((listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NN") && listPosTag.get(i + 3).getTag().equals("NN"))))) {
            K = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata() + " " + listPosTag.get(i + 3).getKata();
            i = i + 4;
        } else if (listPosTag.size() >= (i + 3) && (((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("DT") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("DT") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("DT") && listPosTag.get(i + 2).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN") && listPosTag.get(i + 2).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("IN") && listPosTag.get(i + 2).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC") && listPosTag.get(i + 2).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("JJ") && listPosTag.get(i + 2).getTag().equals("NNC"))))) {
            K = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata() + " " + listPosTag.get(i + 2).getKata();
            i = i + 3;
        } else if (listPosTag.size() >= (i + 2)
                && ((listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("PRL"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNP"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNG"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("PRP"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("IN") && listPosTag.get(i + 1).getTag().equals("CDP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("DT"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("CDP"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("NN") && listPosTag.get(i + 1).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NNU"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("CC") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBT"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("SC") && listPosTag.get(i + 1).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("NNC") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("RB"))
                || (listPosTag.get(i).getTag().equals("RB") && listPosTag.get(i + 1).getTag().equals("NN"))
                || (listPosTag.get(i).getTag().equals("MD") && listPosTag.get(i + 1).getTag().equals("JJ"))
                || (listPosTag.get(i).getTag().equals("JJ") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("JJ") && listPosTag.get(i + 1).getTag().equals("VBI"))
                || (listPosTag.get(i).getTag().equals("DT") && listPosTag.get(i + 1).getTag().equals("NNC"))
                || (listPosTag.get(i).getTag().equals("RP") && listPosTag.get(i + 1).getTag().equals("NN")))) {
            K = listPosTag.get(i).getKata() + " " + listPosTag.get(i + 1).getKata();
            i = i + 2;
        } else if (listPosTag.size() >= (i + 1) && (listPosTag.get(i).getTag().equals("NN")
                || listPosTag.get(i).getTag().equals("NNC")
                || listPosTag.get(i).getTag().equals("IN")
                || listPosTag.get(i).getTag().equals("RB")
                || listPosTag.get(i).getTag().equals("SC")
                || listPosTag.get(i).getTag().equals("DT")
                || listPosTag.get(i).getTag().equals("JJ"))) {
            K = listPosTag.get(i).getKata();
            i = i + 1;
        }
//        System.out.println("===============================\nK:" + K);
        
//        //Konjungsi
//        if (listPosTag.size() >= (i + 1) && (listPosTag.get(i).getTag().equals("CC"))) {
//            KON = listPosTag.get(i).getKata();
//            i = i + 1;
//        }
//        System.out.println("===============================\nK:" + KON);
        
//        System.out.println("S:" + S + " P:" + P + " O:" + O + " PEL:" + PEL + " K:" + K);
        //jika i tidak sama dengan panjang tag, maka tidak semua terpenuhi
//        System.out.println("jumlah tag : " + listPosTag.size() + " i:" + i);
        
        if (listPosTag.size() > 0) {
            if (!S.trim().equals("")) {
                spok[0] = S;
            }
            if (!P.trim().equals("")) {
                spok[1] = P;
            }
            if (!O.trim().equals("")) {
                spok[2] = O;
            }
            if (!PEL.trim().equals("")) {
                spok[3] += PEL + " ";
            }
            if (!K.trim().equals("")) {
                spok[4] = K;
            }
//            if (!KON.trim().equals("")) {
//                spok[5] = KON;
//            }
            
            return spok;
        } else {
            return spok;
        }
        
    }
    
    public String simplifikasi(String parts1, String konjungsi, String parts2)
    {
        String hasil = "";
        String[] spok1 = cekspok(parts1 + " -");
        String[] spok2 = cekspok(parts2 + " -");
        
        if(konjungsi=="dan"){
            if(spok1[0]!="" && spok1[1]=="" && spok1[2]=="" && spok1[3]=="" && spok1[4]==""){
                hasil = spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]=="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]=="" && spok1[3]=="" && spok1[4]==""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }            
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]=="" && spok1[4]==""){
                if(spok2[1]==""){
                    hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok2[3]+" "+spok2[4]+" \n"+spok1[0]+" "+spok1[1]+" "+spok2[2]+" "+spok2[0]+" "+spok2[4];
                }
                else{ 
                    hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok2[3]+" "+spok2[4]+" \n"+spok1[0]+" "+spok1[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
                }
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if (spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]=="" && spok1[4]!="" && spok2[0]!="" && spok2[1]!="" && spok2[2]!="" && spok2[3]=="" && spok2[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if(konjungsi=="kemudian"){
             if(spok1[0]!="" && spok1[1]!="" && spok1[2]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if(konjungsi=="melainkan"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]==""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if(konjungsi=="sedangkan"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
           else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!="" && spok2[0]!="" && spok2[1]!="" && spok2[2]!="" && spok2[3]!="" && spok2[4]!="" ){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="tetapi"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]=="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="karena"){
            if(spok1[0]!="" && spok2[0]=="" && spok1[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4] ;
            }
            else if(spok1[0]!="" && spok2[0]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+spok1[2]+" "+spok1[3]+" "+spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4] ;
            }
        }
        else if (konjungsi=="sehingga") {
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]==""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+spok1[3]+" "+spok1[4]+" "+" \n"+spok1[0]+" "+spok2[1]+" "+ spok2[2]+" " + spok2[3]+" " + spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" " + spok1[3]+" " + spok1[4]+" "+" \n"+spok2[0]+" "+spok2[1]+" "+ spok2[2]+" " + spok2[3]+" " + spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" " + spok1[3]+" " + spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+ spok2[2]+" " + spok2[3]+" " + spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" " + spok1[3]+" " + spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" " + spok1[3]+" " + spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="atau"){
            if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok2[0]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[0]+" "+spok1[1]+" "+spok2[0];
            }
            else if(spok1[0]!="" && spok2[0]!="" && spok2[1]!="" && spok2[4]!=""){
                hasil = spok1[0]+" "+spok2[1]+" "+ spok2[2]+" "+ spok2[3]+" "+ spok2[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="yang"){
            if(spok1[0]!="" && spok1[1]!="" && spok1[2]!="" && spok1[3]!="" && spok1[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[2]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[2]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="bahkan"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        else if (konjungsi=="lalu"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[2]!="" && spok2[4]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
        
        
        else if (konjungsi=="ketika"){
            if(spok1[0]=="" && spok1[1]!="" && spok1[2]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = konjungsi+" \n"+ spok2[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
        }
//        else if (konjungsi=="jika"){
//            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
//                hasil = konjungsi+" \n"+ spok2[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
//            }
////            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
////                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
////            }
//        }
              
        else if (konjungsi=="agar"){
            if(spok1[0]!="" && spok1[1]!="" && spok2[1]!=""){
               hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok1[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
            else if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
               hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
            }
           
//            if(spok1[0]!="" && spok1[1]!="" && spok2[0]!="" && spok2[1]!=""){
//                hasil = spok1[0]+" "+spok1[1]+" "+ spok1[2]+" "+ spok1[3]+" "+ spok1[4]+" \n"+spok2[0]+" "+spok2[1]+" "+spok2[2]+" "+spok2[3]+" "+spok2[4];
//            }
        }
        
        return hasil;
    }
    
}
