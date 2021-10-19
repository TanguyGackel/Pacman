package BACK.CONTROLEUR;

import AFFICHAGE.VIEW.JeuCompletView;
import AFFICHAGE.VIEW.VueJeu;
import BACK.Audio;
import BACK.Game;
import BACK.PREDEF.Directions;
import BACK.PREDEF.Liens;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ControleJeu {
    Game game;

    public Audio audio;

    public ControleJeu(Game game){
        this.game = game;
        //addControleJeu(scene);
        try {
            this.audio = new Audio(Liens.getCheminSon_chomp());
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }




    public void addControlePause(Scene scene){


        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.SPACE) {
                    game.getTimeline().play();

                }
            }
        });
    }

    public void addControleFin(Scene scene){

        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {

                }
            }
        });
    }




    public void addControleJeu(Scene scene, GridPane gridPanePause) {


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {

                    game.getTimeline().stop();
                    game.audioBoost.pause();
                    gridPanePause.setVisible(true);

                } else if (event.getCode() == KeyCode.SPACE) {
                    game.getTimeline().play();
                    if (game.isBoosted()) game.audioBoost.loop();
                    gridPanePause.setVisible(false);
                } else if (event.getCode() == KeyCode.UP) {
                    game.setDirections(Directions.HAUT);



                }
                //goes down
                else if (event.getCode() == KeyCode.DOWN) {
                    game.setDirections(Directions.BAS);


                }
                // goes right
                else if (event.getCode() == KeyCode.RIGHT) {
                    game.setDirections(Directions.DROITE);

                }
                // goes left
                else if (event.getCode() == KeyCode.LEFT) {
                    game.setDirections(Directions.GAUCHE);

                }
            }
        });

    }



}
