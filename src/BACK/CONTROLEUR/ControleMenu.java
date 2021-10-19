package BACK.CONTROLEUR;

import AFFICHAGE.VIEW.MenuView;
import BACK.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControleMenu implements EventHandler<KeyEvent> {


    private MenuView menuView;

    private Game game;

    private boolean EE1;

    private ArrayList<KeyCode> KC;



    public ControleMenu(Game game, MenuView menuView){

        this.game = game;
        this.EE1 = false;
        this.KC = new ArrayList<KeyCode>();
        this.menuView = menuView;
        this.menuView.setOnKey(this);

    }
    public void setGame(Game game){
        this.game = game;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        KeyCode source = (KeyCode) keyEvent.getCode();
        System.out.println(source.getChar());
        if(source== KeyCode.M)
        {
            System.out.println("M");

            if (EE1){
                System.out.println("je desactive");
                EE1 = false;
                this.game.setPacDonald(false);
            }else{
                System.out.println("J'active");
                EE1 = true;
                this.game.setPacDonald(true);
            }


        }
        if (source == KeyCode.Q) {
            System.out.println("A appuyé");
            KC.add(source);
            tryKC();
        }
        if (source == KeyCode.B) {
            System.out.println("B appuyé");
            KC.add(source);
            tryKC();
        }
        if(source == KeyCode.UP)
        {
            System.out.println("Flèche haut appuyée");
            KC.add(source);
            tryKC();
        }
        //goes down
        if(source== KeyCode.DOWN)
        {
            System.out.println("Flèche bas appuyée");
            KC.add(source);
            tryKC();


        }
        // goes right
        if(source == KeyCode.RIGHT)
        {
            System.out.println("Flèche droite appuyée");
            KC.add(source);
            tryKC();


        }
        // goes left
        if(source == KeyCode.LEFT)
        {
            System.out.println("Flèche gauche appuyée");
            KC.add(source);
            tryKC();

        }
    }



    public void tryKC(){

        KeyCode[] sampleKC = new KeyCode[]{KeyCode.UP,KeyCode.UP,KeyCode.DOWN,KeyCode.DOWN,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.LEFT,KeyCode.RIGHT,KeyCode.B,KeyCode.A};

        for (int i = 0; i < 10; i++) {

            if (!(sampleKC[i].equals(KC.get(i)))){

                KC = new ArrayList<KeyCode>();
                return;
            }

        }

        //TODO: Ajouter fonction konami COde


    }



}