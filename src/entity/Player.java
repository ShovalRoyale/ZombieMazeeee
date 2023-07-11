package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16, 32,32);
//        solidArea = new Rectangle(this.worldX + this.solidArea.x,this.worldY + this.solidArea.y,
//                this.worldX + this.solidArea.x + this.solidArea.width,
//                this.worldY + this.solidArea.y + this.solidArea.height);

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues(){

        worldX = gp.tileSize * 12;
        worldY = gp.tileSize * 10;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){

        try{

            SF = ImageIO.read(getClass().getResourceAsStream("/images/standing_front.png"));
            F1 = ImageIO.read(getClass().getResourceAsStream("/images/walk_f_1.png"));
            F2 = ImageIO.read(getClass().getResourceAsStream("/images/walk_f_2.png"));
            SB = ImageIO.read(getClass().getResourceAsStream("/images/standing_back.png"));
            B1 = ImageIO.read(getClass().getResourceAsStream("/images/walk_b_1.png"));
            B2 = ImageIO.read(getClass().getResourceAsStream("/images/walk_b_2.png"));
            SR = ImageIO.read(getClass().getResourceAsStream("/images/standing_right.png"));
            R1 = ImageIO.read(getClass().getResourceAsStream("/images/walk_r_1.png"));
            R2 = ImageIO.read(getClass().getResourceAsStream("/images/walk_r_2.png"));
            SL = ImageIO.read(getClass().getResourceAsStream("/images/standing_left.png"));
            L1 = ImageIO.read(getClass().getResourceAsStream("/images/walk_l_1.png"));
            L2 = ImageIO.read(getClass().getResourceAsStream("/images/walk_l_2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(){

        if ((worldX/gp.tileSize)<0||(worldX/gp.tileSize)>=50||(worldY/gp.tileSize)<0||(worldY/gp.tileSize)>=50){
            System.out.println("out of bounds");
            return;
        }
        if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true){

            if(keyH.upPressed){
                direction = "up";
            }
            else if(keyH.downPressed){
                direction = "down";
            }
            else if(keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }
            //בודק פגיעה בבלוק מוצק
            boolean collisionOn = false;
            gp.cChecker.checkTile(this);

            //אם פגיעה שווה לfalse שחקן יכול לזוז
            if(collisionOn == false){
                int entityLeftWorldX = this.worldX + this.solidArea.x;
                int entityRightWorldX = this.worldX + this.solidArea.x + this.solidArea.width;
                int entityTopWorldY = this.worldY + this.solidArea.y;
                int entityBottomWorldY = this.worldY + this.solidArea.y + this.solidArea.height;
                switch(direction){
                    case "up":
                        worldY -= speed;
                        if ((worldX/gp.tileSize)<0||(worldX/gp.tileSize)>=50||(worldY/gp.tileSize)<0||(worldY/gp.tileSize)>=50){
                            System.out.println("out of bounds");
                            worldY += speed;
                            return;
                        }
                        break;
                    case "down":
                        worldY += speed;
                        if ((worldX/gp.tileSize)<0||(worldX/gp.tileSize)>=50||(worldY/gp.tileSize)<0||(worldY/gp.tileSize)>=50){
                            System.out.println("out of bounds");
                            worldY -= speed;
                            return;
                        }
                        break;
                    case "left":
                        worldX -= speed;
                        if ((worldX/gp.tileSize)<0||(worldX/gp.tileSize)>=50||(worldY/gp.tileSize)<0||(worldY/gp.tileSize)>=50){
                            System.out.println("out of bounds");
                            worldX += speed;
                            return;
                        }
                        break;
                    case "right":
                        worldX += speed;
                        if ((worldX/gp.tileSize)<0||(worldX/gp.tileSize)>=50||(worldY/gp.tileSize)<0||(worldY/gp.tileSize)>=50){
                            System.out.println("out of bounds");
                            worldX -= speed;
                            return;
                        }
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum = 2;
                }
                else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){

//        g2.setColor(Color.WHITE);
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                    image = B1;
                }
                if(spriteNum == 2){
                    image = B2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = F1;
                }
                if(spriteNum == 2){
                    image = F2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = R1;
                }
                if(spriteNum == 2){
                    image = R2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = L1;
                }
                if(spriteNum == 2){
                    image = L2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
