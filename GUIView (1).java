package reversi;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIView implements IView {

    IModel model;
    IController controller;
    JLabel whiteMessage = new JLabel();
    JLabel blackMessage = new JLabel();
    JPanel board1 = new JPanel();
    JPanel board2 = new JPanel();
    JFrame frame1 = new JFrame();
    JFrame frame2 = new JFrame();
    JPanel buttonPlayer1 = new JPanel();
    JPanel buttonPlayer2 = new JPanel();
    JButton p1AI = new JButton();
    JButton p1Restart = new JButton();
    JButton p2AI = new JButton();
    JButton p2Restart = new JButton();


    public static void main(String[] args) {
    }

    @Override
    public void initialise(IModel model, IController controller) {
        this.model = model;
        this.controller = controller;

//        int width = model.getBoardWidth();
//        int height = model.getBoardHeight();

        frame1.setTitle("White player");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(whiteMessage, BorderLayout.NORTH);
        board1.setLayout(new GridLayout(model.getBoardWidth(), model.getBoardHeight()));

        frame2.setTitle("Black player");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.add(blackMessage, BorderLayout.NORTH);
        board2.setLayout(new GridLayout(model.getBoardWidth(), model.getBoardHeight()));

        for (int i = 0; i < model.getBoardHeight(); i++) {
            for (int j = 0; j < model.getBoardWidth(); j++) {
                int p1X = j;
                int p1Y = i;
                GUIViewBoardSquare p1Square = new GUIViewBoardSquare(model, p1X, p1Y);
                p1Square.addActionListener(e -> controller.squareSelected(1, p1X, p1Y));
                board1.add(p1Square);

                int p2X = model.getBoardWidth()-1-i;
                int p2Y = model.getBoardHeight()-1-j;
                GUIViewBoardSquare p2Square = new GUIViewBoardSquare(model, p2X, p2Y);
                p2Square.addActionListener(e -> controller.squareSelected(2, p2X, p2Y));
                board2.add(p2Square);
            }
        }


        frame1.add(board1,BorderLayout.CENTER);

        buttonPlayer1.setLayout(new GridLayout(2,1));

        p1AI.setText("Greedy AI (play white)");
        p1AI.addActionListener(e -> controller.doAutomatedMove(1));


        p1Restart.setText("Restart");
        p1Restart.addActionListener(e -> controller.startup());




        frame2.add(board2,BorderLayout.CENTER);
        buttonPlayer2.setLayout(new GridLayout(2,1));

        p2AI.setText("Greedy AI (play black)");
        p2AI.addActionListener(e -> controller.doAutomatedMove(2));


        p2Restart.setText("Restart");
        p2Restart.addActionListener(e -> controller.startup());

        buttonPlayer1.add(p1AI);
        buttonPlayer1.add(p1Restart);
        frame1.add(buttonPlayer1, BorderLayout.SOUTH);
        frame1.pack();
        frame1.setVisible(true);

        buttonPlayer2.add(p2AI);
        buttonPlayer2.add(p2Restart);
        frame2.add(buttonPlayer2, BorderLayout.SOUTH);
        frame2.pack();
        frame2.setVisible(true);
    }

    @Override
    public void refreshView() {
        board1.repaint();
        board2.repaint();
    }

    @Override
    public void feedbackToUser(int player, String result) {
        if(player == 1) {
            whiteMessage.setText(result);
        }
        else if (player == 2){
            blackMessage.setText(result);
        }
    }
}
