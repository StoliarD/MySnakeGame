import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Created by Dmitry on 05.12.2016.
 */
public class SnakeGame extends MyGame {
    private final static int FIELD_WIDTH = 26;
    private final static int FIELD_HEIGHT = 22;
    private final static int DISPLAY_COLOR = 0;
    private Snake snake;
    private Piece food;
    public long score = 0;
    private static final int foodColor = 2;
    private boolean pause = false;
    //int[][] field  Declared in abstract superclass

    private final int[][] gameOverField = initializeGameOverField();
    private int[][] initializeGameOverField() {
        // interesting to do this for all constant fields
        int[][] res = new int[FIELD_HEIGHT][FIELD_WIDTH];
        int x; int y; int min;
        if (FIELD_WIDTH > FIELD_HEIGHT) {
            x = (FIELD_WIDTH - FIELD_HEIGHT)/2;
            y=0;
            min = FIELD_HEIGHT;
        } else {
            y = (FIELD_HEIGHT - FIELD_WIDTH)/2;
            x=0;
            min = FIELD_WIDTH;
        }
        System.out.println(x + " " + y + " " + min);

        for (int i = 2; i < min-2; i++) {
            res[y+i][x+i] = foodColor;
            res[min-1+y-i][x+i] = foodColor;
        }
        return res;
    }

    SnakeGame() {
        super();
        displayField.setSizeInPieces(FIELD_WIDTH,FIELD_HEIGHT);
        field = new int[FIELD_HEIGHT][FIELD_WIDTH];
        snake = new Snake();
        refreshField();
    }

    @Override
    public void run(){
        try {
            generateFood();
            refreshScore();
            while (!isInterrupted()) {
                System.out.println("tick");
                if (!pause) {
                    snake.move();
                    if (snake.head().equals(food)) {
                        System.out.println("food eaten");
                        generateFood();
                        snake.eatAndFasten();
                    }
                    refreshField();
                }
                try {
                    sleep((long) (1000 / snake.speed));
                } catch (InterruptedException e) {
                    System.out.println("sleep interrupted");
                }
            }
        } catch (InterruptedException e){
            System.out.println("game over and exception catch");
            displayField.setField(gameOverField);
        }
    }

    @Override
    void refreshField() {
//        field[snake.tail().y][snake.tail().x] = DISPLAY_COLOR;
        field = new int[FIELD_HEIGHT][FIELD_WIDTH];
        for (Piece i : snake.pieces) {
            i.addToField(field,snake.COLOR);
        }
        if (food!=null)
            food.addToField(field,foodColor);
        displayField.setField(field);
    }

    private Piece randomPiece() {
        return Piece.getRandomPiece(FIELD_WIDTH, FIELD_HEIGHT);
    }

    private void generateFood() {
        food = randomPiece();
        while (snake.pieces.contains(food))
            food = randomPiece();
    }

    private class Snake {
        final int COLOR = 1;
        float speed = 1;
        private HashSet<String> directionSet = new HashSet<>(Arrays.asList("north", "south", "west", "east"));
        private String direction = "none";
        private LinkedList<Piece> pieces;  // decide later which structure
        Snake() {
            pieces = new LinkedList<>();
            pieces.add(randomPiece());
            pieces.add(pieces.getFirst());
        }

        Piece head() {
            return pieces.getLast();
        }

        Piece tail() {
            return pieces.getFirst();
        }

        void move() throws InterruptedException{
            switch (direction) {
                case "north":
                    pieces.add(new Piece(head().x, head().y - 1));
                    pieces.removeFirst();
                    break;
                case "south":
                    pieces.add(new Piece(head().x, head().y + 1));
                    pieces.removeFirst();
                    break;
                case "west":
                    pieces.add(new Piece(head().x - 1, head().y));
                    pieces.removeFirst();
                    break;
                case "east":
                    pieces.add(new Piece(head().x + 1, head().y));
                    pieces.removeFirst();
                    break;
            }
            if (snake.head().x < 0 || snake.head().y < 0 ||
                    snake.head().x > FIELD_WIDTH-1 || snake.head().y > FIELD_HEIGHT-1) {
                snake.pieces = null;
                food = null;
                throw new InterruptedException();
            } else {
                //реализовать через итератор
                for (int i = 0; i < pieces.size()-2; i++) {
                    if (pieces.get(i).equals(head())){
                        snake.pieces = null;
                        food = null;
                        throw new InterruptedException();
                    }
                }
            }
        }

        void setDirection(String direction) {
//            if (directionSet.contains(direction)) {
                switch (direction) {
                    case "north":
                        if (!this.direction.equals("south"))
                            this.direction = direction;
                        break;
                    case "south":
                        if (!this.direction.equals("north"))
                            this.direction = direction;
                        break;
                    case "west":
                        if (!this.direction.equals("east"))
                            this.direction = direction;
                        break;
                    case "east":
                        if (!this.direction.equals("west"))
                            this.direction = direction;
                        break;
                }
//            } else
//                System.out.println("wrong direction input");
        }

        void eatAndFasten() {
            pieces.addFirst(pieces.getFirst());
            speed+=0.2;
            score++;
            refreshScore();
        }
    }

    @Override
    void pause() {
        pause = !pause;
        refreshScore();
    }

    @Override
    KeyAdapter getGameKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                System.out.println("keyCode" + key);
                switch (key) {
                    case 37: //left
                        snake.setDirection("west");
                        break;
                    case 38: //up
                        snake.setDirection("north");
                        break;
                    case 39: //right
                        snake.setDirection("east");
                        break;
                    case 40: //down
                        snake.setDirection("south");
                        break;
                    case 32:
                        pause();
                        break;
                    case 19:
                        pause();
                        break;
                }
            }
        };
    }

    @Override
    void refreshScore() {
        scoreLabel.setText("score:" + score
                + (pause ? "   (paused)":""));
    }
}
