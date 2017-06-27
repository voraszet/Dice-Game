/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dicegame;

import javax.swing.ImageIcon;

/**
 *
 * @author w1440760
 */
public class Die implements DieIntf, Comparable<Die> {
    int dieValue;
    ImageIcon image;

    public ImageIcon getDieImage() {
        return image;
    }
 
    public void setImage() {
        image = new ImageIcon("img/" + dieValue + ".jpg");
    }
 
    public void setValue(int v) {
       this.dieValue = v;
       setImage();
    }

    public int getValue() {
      return this.dieValue;
    }

    public int compareTo(Die die) {
        if(this.dieValue < die.dieValue){
            return 1;
        } else if(this.dieValue == die.dieValue){
            return 0;
        } else{
            return -1;
        }
    }  
}
