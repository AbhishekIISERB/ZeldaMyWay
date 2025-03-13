import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    int scale;

    final int tileSize;
    final int maxScreenCol;
    final int maxScreenRow;
    final int screenWidth;
    final int screenHeight;

    // Game loop variables
    Thread gameThread;
    final int FPS = 60; // Target frame rate

    // Player variables
    int playerX, playerY; // Player position
    int playerSpeed = 4;
    int playerWidth, playerHeight;
    int xDirection = 1, yDirection = 1; // Movement direction

    public GamePanel() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidthPx = screenSize.width;
        int screenHeightPx = screenSize.height;


        scale = (screenWidthPx >= 2560) ? 4 : (screenWidthPx >= 1920) ? 3 : 2;


        tileSize = originalTileSize * scale;
        maxScreenCol = screenWidthPx / tileSize;
        maxScreenRow = screenHeightPx / tileSize;
        screenWidth = maxScreenCol * tileSize;
        screenHeight = maxScreenRow * tileSize;

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);


        playerWidth = tileSize;
        playerHeight = tileSize;
        playerX = screenWidth / 2 - playerWidth / 2;
        playerY = screenHeight / 2 - playerHeight / 2;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // Time per frame (nanoseconds)
        double nextDrawTime = System.nanoTime() + drawInterval;
//GAMELOOP
        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime > 0) {
                    Thread.sleep((long) remainingTime);
                }
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        playerX += playerSpeed * xDirection;
        playerY += playerSpeed * yDirection;

        if (playerX <= 0 || playerX + playerWidth >= screenWidth) {
            xDirection *= -1; // Reverse direction when hitting horizontal edges
        }
        if (playerY <= 0 || playerY + playerHeight >= screenHeight) {
            yDirection *= -1; // Reverse direction when hitting vertical edges
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw player (rectangle)
        g2.setColor(Color.RED);
        g2.fillRect(playerX, playerY, playerWidth, playerHeight);

        g2.dispose();
    }

}
