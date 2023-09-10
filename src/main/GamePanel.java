package main;
//hey
import entity.Entity;
import entity.Player;
import tile.TileManager;

import java.awt.*;
import java.beans.Customizer;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    //הגדרות מסך
    final int originalTileSize = 16; // 16x16 בגודל
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 בגודל
    public final int maxScreenCol = 66;
    public final int maxScreenRow = 28;
    public final int screenWidth = tileSize * (maxScreenCol-12); // 768 פיקסלים
    public final int screenHeight = tileSize * (maxScreenRow-7); // 576 פיקסלים

    // הגדרות עולם
    public final int maxWorldCol = 66;
    public final int maxWorldRow = 66;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    //System
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    Thread getGameThread;

    //Entity
    public Player player = new Player(this,keyH);

    public  GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
//    public void setupGame() {
//        aSetter.setObject();{
//
//            playMusic(0);
//        }
//    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS; // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            // 1. עדכון: מעדכן מידע כמו תזוזות של שחקן
            update();

            //2. הצגה: מציג את המסך עם המידע המעודכן
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/10000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){

        player.update();

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose();
    }
    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {

        sound.stop();
    }
    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }
}

