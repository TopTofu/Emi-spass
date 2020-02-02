package com.company;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Math;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import static java.lang.Character.isWhitespace;

public class Main{

    static char[] alphabet = { 'a', 'b', 'c', 'd', 'h', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', ',', '.', '?' };
    static String[] morse = { ". -", "- . . .", "- . - .", "- . .", ".", ". . -.", "- - .", ". . . .", ". .", ". - - -",
            "- . -", ". - . .", "- -", "- .", "- - -", ". - - - .", "- - . -", ". - .", ". . .", "-", ". . -",
            ". . . -", ". - -", "- . . -", "- . - -", "- - . .", ". - - - -", ". . - - -", ". . . - -", ". . . . -",
            ". . . . .", "- . . . .", "- - . . .", "- - - . .", "- - - - .", "- - - - -", "- - . . - -", ". - . - . -",
            ". . - - . ." };

    public static void main(String[] args) {

        JFrame f = new JFrame("A JFrame");
        f.setSize(1000, 250);
        f.setLocation(300, 200);
        final JTextArea textArea = new JTextArea(1, 1000);
        f.getContentPane().add(BorderLayout.NORTH, textArea);
        final JButton button = new JButton("Translate");
        f.getContentPane().add(BorderLayout.SOUTH, button);
        final JTextArea Ausgabe = new JTextArea(1, 1000);
        f.getContentPane().add(BorderLayout.CENTER, Ausgabe);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent h) {
                Ausgabe.append(translate(textArea.getText()));

            }
        });

        f.setVisible(true);

    }

    private static HashMap<Character, Integer> prob(String text) {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        String s = text;
        for (int i = 0; i < s.length(); i++) {          // für jeden Char in s wird zu erst ein eintrag in die map
            char c = s.charAt(i);                       // mit der Zahl 1 getätigt und für jedes weitere Vorkommen dieses Char
            Integer val = map.get(c);                   // der count um 1 erhöht
            if (val != 0) {
                map.put(c, val + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }

    private static Double avglength(String text) {
        String s = text;                                    //sucht für jeden char im String s das Zeichen in Alphabet
        int pi = 0;                                         //und nimmt den zugehörigen morse code c und berechnet 
        double l = 0;                                       //(summe aus Pi * länge von c)
        HashMap<Character,Integer> map = prob(s);
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < alphabet.length; j++) {
                if (s.charAt(i) == alphabet[j]){
                    char p = s.charAt(i);
                    pi = map.get(p) / s.length();
                    String c = morse[j];
                    l = l +  pi * (c.length()+1);           //+1 für 1 leerzeichen pro buchstabe
                }
            }
        
        }
        return l;
    }      

    private static Double entropy(String text){
        String s = text;                                    //für jeden Char im Text wird -(Pi * log2(Pi)) berechnet und
        int pi = 0;                                         //aufsummiert.
        double h = 0;   
        HashMap<Character,Integer> map = prob(s);
        for (int i = 0; i < map.size(); i++){
            char p = s.charAt(i);
            pi = map.get(p) / s.length();
            h = h - (pi *  (Math.log(pi) / Math.log(2)));
        }
        return h;
    }

    private static String translate(String eingabe) {

        if (eingabe.length() == 0) return "Error, Wort zu kurz";
        else {


            char[] einglist = eingabe.toCharArray();

            String output = "";

            for (int i = 0; i < einglist.length; i++) {
                for (int j = 0; j < alphabet.length; j++) {

                    if (alphabet[j] == einglist[i]) {
                        output = output + morse[j] + "   ";
                    } else if (isWhitespace(einglist[i])) {
                        output += "       ";
                        break;
                    }
                }
            }
            return output;
        }
    }

}