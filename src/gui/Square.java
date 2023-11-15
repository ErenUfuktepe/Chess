package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Square extends JButton {
    private String key;

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
}
