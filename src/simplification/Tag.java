/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplification;

import java.util.ArrayList;

/**
 *
 * @author win7
 */
public class Tag {
    private String kata, tag;

    public Tag(String kata, String tag) {
        this.kata = kata;
        this.tag = tag.toUpperCase();
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static void print(ArrayList<Tag> listPostTag) {
        listPostTag.forEach((tag) -> {
            System.out.print(tag.getKata() + ":" + tag.tag + " ");
        });

        System.out.println("");
    }
}
