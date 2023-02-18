import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class panel extends JPanel implements ActionListener {

    static int width = 1200;
    static int height = 600;
    static int unit = 50;
    Timer timer;
    static int delay = 160;
    Random random;

    //food co-ords
    int fx, fy;

    //initial body length of the snake
    int body = 3;
    char dir = 'R';
    int score;

    //to keep a check on the state of the game
    boolean flag = false;

    //arrays to store the co-ords of the snake
    int xSnake[] = new int[288];
    int ySnake[] = new int[288];

    panel(){
        //sets the height of the panel
        this.setPreferredSize(new Dimension(width, height));

        this.setBackground(Color.BLACK);

        //to enable keyboard input
        this.setFocusable(true);

        //keyListener tells whether a key is pressed
        this.addKeyListener(new myKey());

        random = new Random();
        gameStart();
    }

    public void gameStart(){
        flag = true;
        spawnFood();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnFood(){
        fx = random.nextInt((int) width/unit) * unit;
        fy = random.nextInt((int) height/unit) * unit;
    }

    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic){
        if(flag == true)
        {
            //spawning the food particle
            graphic.setColor(Color.ORANGE);
            graphic.fillOval(fx, fy, unit, unit);

            //spawning the snake
            for (int i = 0; i<body; i++)
            {
                if(i==0)
                {
                    //this is the head
                    graphic.setColor((Color.RED));
                    graphic.fillRect(xSnake[i], ySnake[i], unit, unit);
                }
                else
                {
                    //this is rest of the snakes' head
                    graphic.setColor((Color.GREEN));
                    graphic.fillRect(xSnake[i], ySnake[i], unit, unit);
                }
            }

            //display the score
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("comic sans", Font.BOLD, 40));
            FontMetrics fme = getFontMetrics(graphic.getFont());
            graphic.drawString("Score:"+ score, (width - fme.stringWidth("Score: "+ score))/2, graphic.getFont().getSize());
        }
        else
        {
            gameOver(graphic);
        }
    }

    public void gameOver(Graphics graphic){
        //drawing the score
        graphic.setColor(Color.CYAN);
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:"+ score, (width - fme.stringWidth("Score: "+ score))/2, graphic.getFont().getSize());

        //drawing the "GAME OVER" text
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("comic sans", Font.BOLD, 80));
        FontMetrics fme1 = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER", (width - fme1.stringWidth("GAME OVER"))/2, height/2);

        //drawing the replay prompt
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("comic sans", Font.BOLD, 40));
        FontMetrics fme2 = getFontMetrics(graphic.getFont());
        graphic.drawString("Press 'R' To Replay", (width - fme2.stringWidth("Press 'R' To Replay"))/2, height/2 - 150);
    }

    public void move(){
        for (int i = body; i > 0; i--)
        {
            //for updating the body except the head
            xSnake[i] = xSnake[i-1];
            ySnake[i] = ySnake[i-1];
        }

        switch(dir){
            case 'R':
                xSnake[0] = xSnake[0] + unit;
                break;
            case 'L':
                xSnake[0] = xSnake[0] - unit;
                break;
            case 'U':
                ySnake[0] = ySnake[0] - unit;
                break;
            case 'D':
                ySnake[0] = ySnake[0] + unit;
                break;
        }
    }
    public void check(){
        //checking if the snake hits the borders of the window
        if(xSnake[0] < 0)
        {
            flag = false;
        }
        else if (xSnake[0] > width)
        {
            flag = false;
        }
        else if (ySnake[0] < 0)
        {
            flag = false;
        }
        else if (ySnake[0] > height)
        {
            flag = false;
        }

        //checking if the snake hits its own body
        for (int i = body; i>0; i--)
        {
            if ((xSnake[0] == xSnake[i]) && (ySnake[0] == ySnake[i]))
            {
                flag = false;
            }
        }

        if(flag==false)
        {
            timer.stop();
        }
    }

    public void eat(){
        if ((xSnake[0] == fx) && (ySnake[0] == fy))
        {
            body++;
            score++;
            spawnFood();
        }
    }

    public class myKey extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_UP:
                    if (dir!='D')
                    {
                        dir = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir!='U')
                    {
                        dir = 'D';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir!='L')
                    {
                        dir = 'R';
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (dir!='R')
                    {
                        dir = 'L';
                    }
                    break;
                case KeyEvent.VK_R:
                    if (!flag)
                    {
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xSnake, 0);
                        Arrays.fill(ySnake, 0);
                        gameStart();
                    }
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if (flag)
        {
            move();
            eat();
            check();
        }
        repaint();
    }
}
