import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.Delayed;

public class Board extends JPanel implements ActionListener {

//    setting height of the board
    int B_Height=400;
//    setting width of the board
    int B_width=400;

//    setting maximum dot size in board (400 *400=1600)
    int Max_Dots=1600;
//    size of dot
    int Dot_Size=10;
//    no of dots at initila
    int Dots=3;

    int []x=new int [Max_Dots];
    int []y=new int [Max_Dots];

//    direction of apple in x
    int apple_x;
//    direction of apple in y
    int apple_y;
//    images
    Image body, head, apple;

//    timer in microsecond
    Timer timer;
//    setting delay or speed of sanke
    int DELAY=150;

//    checking movement of sanke
    boolean leftDirection=true;
    boolean rightDirection=false;
    boolean upDirection=false;
    boolean downDirection=false;

//    setting  if in a game then make it true otherwise make it false
    boolean inGame=true;

//    operation performed on board
    Board(){
//        creating object of TAdapter to get in input from keyboard and move snake in different direction
        TAdapter tAdapter = new TAdapter();
//        key given by tAdapter is listened ny addKeyListener
        addKeyListener(tAdapter);
        setFocusable(true);
//        setting up the size and dimension of board
        setPreferredSize(new Dimension(B_Height,B_width));
//        making background color black
        setBackground(Color.black);
//        initializing the game
        initGame();
//        take image and loading
        loadImage();

    }

//    creating a method to initializing the game
    public void initGame(){
        Dots=3;

        x[0]=250;
        y[0]=250;

        for(int i=1;i<Dots;i++){
            x[i]=x[0]+Dot_Size*i;
            y[i]=y[0];
        }

        locateApple();
//        passing the delay time and the observer to the board && this => board is observer
        timer=new Timer(DELAY, this);
//        starting the timer
        timer.start();
    }

//    method to load image
    public void loadImage(){
//        adding body image to snake body in board
        ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png");
        body = bodyIcon.getImage();
//        adding head to snake body in board
        ImageIcon headIcon = new ImageIcon("src/resources/head.png");
        head = headIcon.getImage();
//        adding apple in board
        ImageIcon appleIcon = new ImageIcon("src/resources/apple.png");
        apple = appleIcon.getImage();
    }

//    graphics
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

//    drawing images in board
    public void doDrawing(Graphics g){

        if(inGame){
//            live score
            showScore(g);
//            setting the position of apple when game start
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<Dots;i++){
                if(i==0){
//                    position of head when game start
                    g.drawImage(head,x[0],y[0],this);
                }else {
//                    position of body when game start
                    g.drawImage(body,x[i],y[i],this);
                }
            }
        }else {
            gameOver(g);
            timer.stop();
        }
    }

//    displaying score
    public void showScore(Graphics g)
    {
        //Score
        int score = (Dots-3)*100;
        String scoremsg = "Score:"+Integer.toString(score);
//        message and score font style and font type and size
        Font small = new Font("Helvetica", Font.BOLD, 14);
        g.setColor(Color.WHITE);
        g.setFont(small);
//        message display coordinate
        g.drawString(scoremsg, B_width-90, B_Height-380);


    }

//    locating apple randomly
    public void locateApple(){
        apple_x=((int)(Math.random()*39))*Dot_Size;
        apple_y=((int)(Math.random()*39))*Dot_Size;
    }

//    actions performed in games
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

//    method to move snake in board
    public void move(){
        for(int i=Dots-1;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
//        if snake is at (0,0) coordinate
//        if we move left side of (0,0) coordinate on x-axis , we get -ve
//        if we move right side of (0,0) coordinate on x-axis , we get +ve
//        if we move upside of (0,0) coordinate on y-axis , we get +ve
//        if we move downside of (0,0) coordinate on y-axis , we get -ve
        if(leftDirection){
            x[0] -= Dot_Size;
        }
        if(rightDirection){
            x[0] += Dot_Size;
        }
        if(upDirection){
            y[0] -= Dot_Size;
        }
        if(downDirection){
            y[0] += Dot_Size;
        }

    }

//    method to check collision
    public void checkCollision(){
        for(int i=1;i<Dots;i++){
//            if head(x[0],y[0]) coordinate get equals to body (x[i],y[i]) coordinate ,it means collision occur
            if (i>4 && x[0]==x[i] && y[0]==y[i]){
//                move out of game and display score
                inGame=false;
            }
        }

//        checking left boundary
        if (x[0]<0){
            inGame=false;
        }
//        checking right boundary
        if (x[0]>=B_Height){
            inGame=false;
        }
//        checking upper boundary
        if (y[0]<0){
            inGame=false;
        }
//        checking down boundary
        if (y[0]>=B_width){
            inGame=false;
        }

    }

//    checking apple get collide with snake head
    public void checkApple(){
        if(apple_x==x[0] && apple_y==y[0]){
            Dots++;
            locateApple();
        }

    }

//    display game over message and score
    public void gameOver(Graphics g){
//        message
        String msg = "Game Over";
//        calculating score
        int score = (Dots-3)*100;
        String scoremsg = "Score:"+Integer.toString(score);
//        message and score font style and font type and size
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fontMetrics = getFontMetrics(small);

//        colour of font
        g.setColor(Color.WHITE);
//        font size type
        g.setFont(small);
//        dividing board are and displaying score and message in appropriate place
        g.drawString(msg,(B_width-fontMetrics.stringWidth(msg))/2 , B_Height/4);
        g.drawString(scoremsg,(B_width-fontMetrics.stringWidth(scoremsg))/2 , 3*(B_Height/4));
    }

//    using KeyAdapter class to take input from user
//    overriding parent class method
    public class TAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent keyEvent){
            int key=keyEvent.getKeyCode();

//            action performed on pressing left arrow key
            if (key==keyEvent.VK_LEFT && !rightDirection){
                leftDirection=true;
                upDirection=false;
                downDirection=false;
            }

//            action performed on pressing right arrow key
             if (key==keyEvent.VK_RIGHT && !leftDirection){
                rightDirection=true;
                upDirection=false;
                downDirection=false;
            }

//            action performed on pressing up arrow key
             if (key==keyEvent.VK_UP && !downDirection){
                leftDirection=false;
                upDirection=true;
                rightDirection=false;
            }

//            action performed on pressing down arrow key
             if (key==keyEvent.VK_DOWN && !upDirection){
                leftDirection=false;
                rightDirection=false;
                downDirection=true;
            }

        }

    }
}
