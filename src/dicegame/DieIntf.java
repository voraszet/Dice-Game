
package dicegame;
import javax.swing.ImageIcon;

public interface DieIntf {
    public ImageIcon getDieImage();
    public void setImage();
    public void setValue(int v);
    public int getValue();
}
