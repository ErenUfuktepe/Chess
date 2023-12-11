package gui;

import main.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Square extends JButton {
    private Color color;
    private String key;
    private Piece piece;
    private boolean isActive = false;
    private boolean hasPiece = false;

    public Square() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        if (piece == null) {
            this.piece = null;
            this.hasPiece = false;
            setIcon(null);
        }
        else {
            this.hasPiece = true;
            this.piece = piece;

            try {
                Image img = ImageIO.read(getClass().getResource(piece.getIconUrl()));
                img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                setIcon(new ImageIcon(img));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        setBackground(color);
        this.color = color;
    }

    public boolean isActive() {
        return isActive;
    }

    public Square setActive(boolean active) {
        this.isActive = active;
        setEnabled(!active);
        Color color = active ? Color.YELLOW : this.color;
        this.setBackground(color);
        return this;
    }

    public boolean hasPiece() {
        return this.hasPiece;
    }

    public Square reset() {
        setBackground(this.color);
        return this;
    }


    public boolean isMoved() {
        if (this.hasPiece) {
            return !this.piece.getPosition().getKey().equals(this.key);
        }
        return false;
    }

    public Square makeMovable(Color color) {
        setBackground(color);
        setEnabled(true);
        return this;
    }


    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
    }

    @Override
    public void addChangeListener(ChangeListener l) {
        super.addChangeListener(l);
    }
}
