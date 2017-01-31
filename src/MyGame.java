import javax.swing.JLabel;
import java.awt.event.KeyAdapter;

/**
 * Created by Dmitry on 05.12.2016.
 */
abstract class MyGame extends Thread{
    DisplayField displayField;
    JLabel scoreLabel;
    int[][] field; // array X*Y filled with Color

    public MyGame(){
        this.displayField = new DisplayField();
    }

    public void replot() {
        refreshField();
        displayField.setField(field);
    }

    abstract void refreshField();

    abstract void pause(); /// and unPause

    abstract KeyAdapter getGameKeyListener();

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    abstract void refreshScore(); // set scoreLabel
}
