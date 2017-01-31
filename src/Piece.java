import java.util.Random;

/**
 * Created by Dmitry on 06.12.2016.
 */
public class Piece {
    private final static Random random = new Random();
    int x;
    int y;
    Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Piece getRandomPiece(int xMax, int yMax) {
        return new  Piece(random.nextInt(xMax),random.nextInt(yMax));
    }

    void addToField(int[][] field, int color){
        field[y][x] = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (x != piece.x) return false;
        return y == piece.y;
    }
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * x + y;
        return result;
    }
}
