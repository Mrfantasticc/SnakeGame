import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeGame extends JFrame implements ActionListener {
    Board board;
//    menu bar
    JMenuBar menuBar;

//    menu
    JMenu file;

//    menu items
    JMenuItem newGame , closeGame;
    SnakeGame(){
        menuBar=new JMenuBar();
        file=new JMenu("File");

        newGame=new JMenuItem("New Game");
        closeGame=new JMenuItem("Close");
        newGame.addActionListener(this);
        closeGame.addActionListener(this);
        file.add(newGame);
        file.add(closeGame);
        menuBar.add(file);
        setJMenuBar(menuBar);



        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);

    }
    public static void main(String[] args) {

        SnakeGame snakeGame = new SnakeGame();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==newGame){
            SnakeGame snakeGame = new SnakeGame();

        }
        if(actionEvent.getSource()==closeGame){
            System.exit(0);
        }
    }
}