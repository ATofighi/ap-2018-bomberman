package com.atofighi.bomberman.views.client.game;

import com.atofighi.bomberman.configs.BoardConfiguration;
import com.atofighi.bomberman.models.Map;

import java.awt.*;
import java.io.IOException;

import static com.atofighi.bomberman.configs.BoardConfiguration.cellSize;

public class LosePainter extends GamePainter {

    private boolean isLosed = false;

    private Map map;
    private Font font;

    public LosePainter(Map map) {
        super();
        this.map = map;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getClassLoader().getResourceAsStream("good-times.ttf"));
            font = font.deriveFont(Font.PLAIN, 36);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics2D g2) {
        if (isLosed) {
            Font oldFont = g2.getFont();
            g2.setFont(font);
            g2.setColor(new Color(0, 0, 0, 0.8f));
            int width = BoardConfiguration.boardWidth * cellSize;
            int height = BoardConfiguration.boardHeight * cellSize;
            String loseString = "You Lose!";
            g2.fillRect(0, 0, width, height);
            Rectangle bounds = g2.getFont().getStringBounds(loseString, g2.getFontRenderContext()).getBounds();
            g2.setColor(Color.decode("#ffffff"));
            g2.drawString("You Lose!", (width - bounds.width) / 2, (height - bounds.height) / 2);
            g2.setFont(oldFont);
        }
    }

    public void setLosed(boolean losed) {
        isLosed = losed;
    }

    @Override
    public int zIndex() {
        return 1000;
    }

    public boolean isLosed() {
        return isLosed;
    }
}
