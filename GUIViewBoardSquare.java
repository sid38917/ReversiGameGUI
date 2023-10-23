package reversi;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GUIViewBoardSquare extends JButton {
    IModel model;
     int i;
    int j;

    public GUIViewBoardSquare(IModel model, int i, int j) {
        setMinimumSize( new Dimension(50, 50) );
        setPreferredSize( new Dimension(50, 50) );
        setI(i);
        setJ(j);
        setModel(model);
    }

    public int returnI() {
        return i;
    }

    public int returnJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setModel(IModel model) {
        this.model = model;
    }

    public IModel returnModel() {
        return model;
    }





    public Color colorPiece() {
        if(returnModel().getBoardContents(returnI(), returnJ()) == 1) {
            return Color.white;
        }
        else if(returnModel().getBoardContents(returnI(), returnJ()) == 2) {
            return Color.black;
        }
        else {
            return null;
        }
    }

    public Color colorBorder() {
        if(colorPiece() == Color.white) {
            return Color.black;
        }
        else if(colorPiece() == Color.black) {
            return Color.white;
        }
        else {
            return null;
        }
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        setBackground(Color.green);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));


        int boardContents = returnModel().getBoardContents(returnI(), returnJ());
        if (boardContents != 0) {
            Color pieceColor = (boardContents == 1) ? Color.white : Color.black;


            graphics.setColor(pieceColor);
            int padding = 4;
            int pieceSize = Math.min(getWidth() - padding * 2, getHeight() - padding * 2);
            int x = (getWidth() - pieceSize) / 2;
            int y = (getHeight() - pieceSize) / 2;
            graphics.fillOval(x, y, pieceSize, pieceSize);


            graphics.setColor(Color.white);
            graphics.drawOval(x, y, pieceSize, pieceSize);
        }
    }
}
