package com.example.snakeladder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


import java.lang.Math;

import java.util.HashMap;


public class HelloController {

    Game SnL;
    @FXML
    private ImageView Blue;

    @FXML
    private ImageView Board;

    @FXML
    private ImageView Fixblue;

    @FXML
    private ImageView Fixgreen;

    @FXML
    private ImageView Green;

    @FXML
    Button rollBtn;

    @FXML
    private ImageView dieImage;

    @FXML
    private ImageView win;

    @FXML
    private VBox v1;

    @FXML
    private VBox v2;

    @FXML
    private Label Player1;

    @FXML
    private Label Player2;






    private Roller clock;


    private class Roller extends AnimationTimer {
        Die die = new Die(6,1);
        private long frames_ps= 50L;
        private long gap = 1000000000L / frames_ps;
        private int roll_max = 25;

        private long last = 0;
        private int count = 0;

        @Override
        public void handle(long now) {
            if (now - last > gap) {
                int r = 2 + (int)(Math.random() * 5);
                setDieImage(r);
                last = now;
                count++;
                if (count > roll_max) {
                    clock.stop();
                    disableButtons(false);
                    roll();
                    count = 0;
                }
            }
        }

    }
    @FXML
    public void initialize() {
        clock = new Roller();
        SnL = new Game("Player 1", Blue,"Player 2", Green);
        setDieImage(SnL.getDie().Topget());

        v2.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

    public void updateScreen() {

        setDieImage(SnL.getDie().Topget());
        int dist = SnL.getCurrent().getCurrPos()-SnL.getCurrent().getPrevPos();


        System.out.println(SnL.getX_tile(SnL.getCurrent().getCurrPos()) +" "+ SnL.getY_tile(SnL.getCurrent().getCurrPos()));
        System.out.println(SnL.getX_tile(SnL.getCurrent().getPrevPos())+" "+ SnL.getY_tile(SnL.getCurrent().getPrevPos()));
        if(SnL.getCurrent().getPrevPos()%10==0){

            SnL.getCurrent().getToken().setLayoutY(SnL.getY_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);

        }
        else if((10-(SnL.getCurrent().getPrevPos()%10)) <= (dist)){

            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile((((SnL.getCurrent().getPrevPos()/10)+1)*10)));
            SnL.getCurrent().getToken().setLayoutY(SnL.getY_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);
            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);
        }
        else{

            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);
        }

        if(SnL.Ladders.containsKey(SnL.getCurrent().getCurrPos())){
            SnL.getCurrent().update_if_SL(SnL.Ladders.get(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setLayoutY(SnL.getY_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);
        }

        if(SnL.Snakes.containsKey(SnL.getCurrent().getCurrPos())){
            SnL.getCurrent().update_if_SL(SnL.Snakes.get(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setLayoutY(SnL.getY_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setLayoutX(SnL.getX_tile(SnL.getCurrent().getCurrPos()));
            SnL.getCurrent().getToken().setTranslateY(0);
            SnL.getCurrent().getToken().setTranslateX(0);
        }
        if (SnL.p1Turn()) {
            v1.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            v2.setBackground(null);
        } else {
            v2.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            v1.setBackground(null);
        }
        if(SnL.gameOver()) {
            disableButtons(true);

            if(SnL.p1Turn()) {
                win.setImage(new Image(String.valueOf(HelloApplication.class.getResource("Player1w.png"))));
            }

            else
                win.setImage(new Image(String.valueOf(HelloApplication.class.getResource("Player2w.png"))));
        }
    }

    public void setDieImage(int top) {

        Image img = new Image(String.valueOf(HelloApplication.class.getResource("Dice" + top + ".jpeg")));
        dieImage.setImage(img);

    }

    public void disableButtons(boolean disable) {
        rollBtn.setDisable(disable);
    }

    @FXML
    void rollAnimation() {
        clock.start();
        disableButtons(true);
    }
    public void roll() {
        SnL.roll();
        updateScreen();
        SnL.changePlayer();
    }

}

class Player {

    private String name;
    private int prev_pos;
    private int curr_pos;
    private ImageView token;

    // Constructor
    public Player(String name, ImageView token) {
        this.token=token;
        this.name = name;
        prev_pos = 0;
        curr_pos = 0;
    }


    public ImageView getToken() {
        return token;
    }

    public String getName() {
        return name;
    }


    public int getPrevPos() {
        return prev_pos;
    }
    public int getCurrPos() {
        return curr_pos;
    }

    public void updatePos(int roll) {
        System.out.println(roll);
        if(curr_pos+roll<=100) {
            prev_pos = curr_pos;
            curr_pos += roll;
        }
    }

    public void update_if_SL(int des){
        curr_pos=des;
        prev_pos = curr_pos;
    }

}
class Die {

    private int faces;
    private int t;

    public Die(int faces, int top) {
        this.faces = faces;
        this.t = top;
    }

    public Die() {
        faces = 6;
        t = 1;
    }

    public int Topget() {
        return t;
    }

    public void Topset(int t) {
        if (t >= 1 && t <= faces) {
            this.t = t;
        }
    }

    public void roll() {
        t = 1 + (int)(Math.random() * faces);
    }

}

class Game {
    public static final int MAX_SCORE = 100;

    int[] x_tile = new int[101];
    int[] y_tile = new int[101];
    HashMap<Integer, Integer> Snakes = new HashMap<>();
    HashMap<Integer, Integer> Ladders = new HashMap<>();

    // Data Fields
    private Die d;
    private Player p1;
    private Player p2;
    private Player current;

    {
        Snakes.put(24, 5);
        Snakes.put(43, 22);
        Snakes.put(56, 25);
        Snakes.put(60, 42);
        Snakes.put(69, 48);
        Snakes.put(86, 53);
        Snakes.put(90, 72);
        Snakes.put(94, 73);
        Snakes.put(96, 84);
        Snakes.put(98, 58);


        Ladders.put(3, 21);
        Ladders.put(8, 46);
        Ladders.put(16, 26);
        Ladders.put(29, 33);
        Ladders.put(37, 65);
        Ladders.put(50, 70);
        Ladders.put(64, 77);
        Ladders.put(61, 82);
        Ladders.put(76,95);
        Ladders.put(89,91);

        x_tile[0] = 45;
        y_tile[0] = 564;

        int temp_x = x_tile[0];
        int temp_y = y_tile[0];
        for (int i = 1; i <= 100; i += 10) {
            temp_y -= 45;
            if ((i / 10) % 2 == 0) {
                for (int j = i; j < i + 10; j++) {
                    x_tile[j] = temp_x;
                    y_tile[j] = temp_y;
                    temp_x += 33;
                }
            } else {
                for (int j = i; j < i + 10; j++) {
                    temp_x -= 33;
                    x_tile[j] = temp_x;
                    y_tile[j] = temp_y;
                }
            }
        }
    }

    // Constructor
    public Game(String p1name, ImageView token1, String p2name, ImageView token2) {
        d = new Die(6,1);
        p1 = new Player(p1name, token1);
        p2 = new Player(p2name, token2);
        current = p1;
    }

    public int getX_tile(int pos) {
        return x_tile[pos];
    }
    public int getY_tile(int pos) {
        return y_tile[pos];
    }

    public Die getDie() {
        return d;
    }

    public Player getCurrent() {
        return current;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public boolean gameOver() {
        return current.getCurrPos() == MAX_SCORE;
    }

    public boolean p1Turn() {
        return current == p1;
    }

    public void switchTurn() {
        if (p1Turn()) {
            current = p2;
        } else {
            current = p1;
        }
    }

    public void roll() {
        d.roll();
        int t = d.Topget();
        if(current.getCurrPos()!=0 || t==1)
            current.updatePos(t);

    }

    public void changePlayer(){
        if (!gameOver()) {
            switchTurn();
        }
    }
}
