package gui;

import main.Position;
import main.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Square extends JButton {
    private Color color;
    private String key;
    private Piece piece;
    private boolean isActive;
    private List<Position> possibleMoves;
    public Square() {
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isActive = true;
                possibleMoves = piece.getPossibleMoves();
                setEnabled(false);
            }
        });
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIcon(String url) {
        try {
            Image img = ImageIO.read(getClass().getResource(url));
            img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            super.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        setIcon(piece.getIconUrl());
    }

    public List<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        setBackground(color);
        this.color = color;
    }

    public void restBackGround() {
        setBackground(this.color);
    }

    public boolean isActive() {
        return isActive;
    }

    public Square setActive(boolean active) {
        isActive = active;
        setEnabled(!active);
        return this;
    }
}
