import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Dmitry on 05.12.2016.
 */
public class DisplayField extends JPanel {
    private int width;
    private int height;
    private int[][] piecesArray; // array X*Y filled with Color
    private final static Color DISPLAY_COLOR = Color.LIGHT_GRAY;
    private static final Color BACKGROUND = Color.DARK_GRAY;
    private static final Color[] colors = {Color.GRAY, Color.BLUE, Color.RED};
    private final static int PIECE_SIZE = 20;
    private final static int PIECE_SPACE = 2;
    private String key = null;

    public DisplayField() {
        super();
        setBackground(DISPLAY_COLOR);
    }

    void setSizeInPieces(int widthInPieces, int heightInPieces) {
        this.width = widthInPieces;
        this.height = heightInPieces;
        setBackground(DISPLAY_COLOR);
        piecesArray = new int[heightInPieces][widthInPieces];
        setPreferredSize((new Dimension(
                PIECE_SPACE + widthInPieces*(PIECE_SIZE+PIECE_SPACE),
                PIECE_SPACE + heightInPieces*(PIECE_SIZE+PIECE_SPACE))));
    }

    public void setField(int[][] widthHeightColor){
        this.piecesArray =  widthHeightColor;
        repaint();
    }

    @Nullable
    String getKey() {
        String res = key;
        key = null;
        return res;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x;
        int y;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                x = PIECE_SPACE + j*(PIECE_SPACE+PIECE_SIZE) + 1;
                y = PIECE_SPACE + i*(PIECE_SPACE+PIECE_SIZE) + 1;
                g.setColor(colors[piecesArray[i][j]]);
                g.fillRect(x,y,PIECE_SIZE,PIECE_SIZE);
                g.setColor(BACKGROUND);
                g.drawRect(x-1,y-1,PIECE_SIZE+1,PIECE_SIZE+1);
            }
        }
    }
}
