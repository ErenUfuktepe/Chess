package gui;

import main.Position;
import main.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Square extends JButton {
    private Color color;
    private String key;
    private Piece piece;
    private boolean isActive;
    private boolean hasPiece = false;
    private List<Position> possibleMoves;
    private List<Position> conditionalMoves = new ArrayList<>();

    public Square() {
        super.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Square square = ((Square) e.getSource());
                if (square.isActive) {
                    setBackground(Color.YELLOW);
                    square.setEnabled(false);
                }
                else if (!(square.hasPiece) && (square.getBackground().equals(Color.WHITE)
                        || square.getBackground().equals(Color.DARK_GRAY))) {
                    square.setEnabled(false);
                }
                else if (!(square.hasPiece) && square.getBackground().equals(Color.GREEN)) {
                    square.setEnabled(true);
                }
            }
        });
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
            this.possibleMoves = this.piece.getPossibleMoves();
            this.conditionalMoves = this.piece.getConditionalMoves();

            try {
                Image img = ImageIO.read(getClass().getResource(piece.getIconUrl()));
                img = img.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                setIcon(new ImageIcon(img));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Position> getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(List<Position> possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public List<Position> getConditionalMoves() {
        return conditionalMoves;
    }

    public void setConditionalMoves(List<Position> conditionalMoves) {
        this.conditionalMoves = conditionalMoves;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        setBackground(color);
        this.color = color;
    }

    public void reset() {
        isActive = false;
        setBackground(this.color);
        setEnabled(true);
    }

    public boolean isActive() {
        return isActive;
    }

    public Square setActive(boolean active) {
        isActive = active;
        setEnabled(!active);
        return this;
    }

    public boolean hasPiece() {
        return this.hasPiece;
    }

    public Square restBackGround() {
        setBackground(this.color);
        return this;
    }

    public void setMovable() {
        setBackground(Color.GREEN);
        setEnabled(true);
    }

    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);
    }

    protected void swapPieces(Square to) {
        to.setPiece(this.piece);
        this.setPiece(null);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
    }
}
