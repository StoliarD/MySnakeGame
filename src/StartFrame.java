import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Created by Dmitry on 05.12.2016.
 */
public class StartFrame extends JFrame {
    private MyGame game;
    private DisplayField displayField;

    StartFrame() throws HeadlessException {
        super("STOL_GAME");
        setSize(640, 600);
        setLocation(200,200);
        setResizable(true);
        setLayout(new BorderLayout());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setFocusable(true);
        setBackground(Color.GRAY);

        JButton newSnakeGameButton = new JButton("newSnakeGame");
        JButton hintsButton = new JButton("hints");
        JLabel scoreLabel = new JLabel("");
        JToolBar menuPanel = new JToolBar(SwingConstants.HORIZONTAL);//(new FlowLayout(FlowLayout.LEFT));

        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (button == newSnakeGameButton) {
                    if (game != null)
                        game.interrupt();
                    game = new SnakeGame();
                    game.setScoreLabel(scoreLabel);
                    displayField = game.displayField;
                    add(displayField, BorderLayout.CENTER);
                    addKeyListener(game.getGameKeyListener());
                    requestFocusInWindow();
                    revalidate();
                    game.start();
                } else if (button == hintsButton) {
                    game.pause();
                    JDialog hints = new Hints();
                }
            }
        };
        newSnakeGameButton.addActionListener(menuListener);
        hintsButton.addActionListener(menuListener);

        menuPanel.setFloatable(false);
        menuPanel.addSeparator();
        menuPanel.add(newSnakeGameButton);
        menuPanel.add(hintsButton);
        menuPanel.addSeparator(new Dimension(50,20));
        menuPanel.add(scoreLabel);
        this.add(menuPanel, BorderLayout.NORTH);
        revalidate();
    }

    public static void main(String... args) {
        StartFrame startFrame = new StartFrame();
    }

    class Hints extends JDialog {
        Hints() throws HeadlessException {
            super();
            setSize(200,200);
            setLocationRelativeTo(StartFrame.this);
            setVisible(true);
            setAlwaysOnTop(true);
//            setModalityType(ModalityType.APPLICATION_MODAL);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            JLabel label = new JLabel("<html>"
                    + "<h3>header</h3>"
                    + "<p>         moving with arrows. space or pause to pause</p>"
                    + "</html>");
//            label.set
            setLayout(new FlowLayout(FlowLayout.CENTER));
            label.setPreferredSize(new Dimension(100,100));
            add(label);

            addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent arg) {
                    super.focusLost(arg);
                    try{Thread.sleep(100);} catch (InterruptedException e) {}
                    Hints.this.requestFocus();
                }
            });
        }

        @Override
        public void dispose() {
            super.dispose();
            StartFrame.this.requestFocus();
        }
    }
}
