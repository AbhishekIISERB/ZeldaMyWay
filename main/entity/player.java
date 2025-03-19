package entity;

import main.GamePanel;
import main.KeyHandler;

public class player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public player(GamePanel gp, KeyHandler keyH) {

        this.gp=gp;
        this.keyH=keyH;

    }
}