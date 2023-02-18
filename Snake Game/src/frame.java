import javax.swing.*;

public class frame extends JFrame {
    frame(){
        //adding the panel component to the frame
        this.add(new panel());

        //setting the title of the window
        this.setTitle("Snake Game");

        //ensuring uniform experience by fixing the size of the window
        this.setResizable((false));

        //layout manager will size the window according to the display
        this.pack();

        //making the frame visible
        this.setVisible(true);
    }
}
