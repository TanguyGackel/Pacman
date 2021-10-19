package BACK.CONTROLEUR;

import AFFICHAGE.VIEW.*;
import BACK.Audio;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class ControleBouton implements EventHandler {

    private Stage primaryStage;
    private Game game;
    private MenuView menuView;
    private RecordView recordView;
    private VueJeu vueJeu;
    private JeuCompletView jeuCompletView;

    Audio audioMenu;
    //private final PauseView pauseView;
    private boolean verif = false;
    //private FinView finView;
    private CommentJouerView commentJouerView;
    private ControleMenu controleMenu;

    private NameView nameView;

    public ControleBouton(Stage primaryStage, Game game){
        this.game = game;

        menuView = new MenuView(this,game);
        controleMenu = new ControleMenu(game,getMenuView());
        recordView = new RecordView(this,game);
        commentJouerView = new CommentJouerView(this,game);
        jeuCompletView = new JeuCompletView(this,game);
        nameView = new NameView(this,game);


        this.primaryStage = primaryStage;
        try {
            this.audioMenu = new Audio(Liens.getCheminSon_1());
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        audioMenu.loop();
    }


    @Override
    public void handle(Event event) {
        Button source = (Button) event.getSource();

        // BOUTON DU MENU

        if(source.equals(menuView.getBoutJouer())){
            final Scene scene;
            if(getNameView().getButtonJouer().getScene() == null) scene = new Scene(nameView);
            else scene = nameView.getScene();

            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }

        if(source.equals(menuView.getBoutRecord())){
            final Scene scene;
            if(getRecordView().getRetourMenu().getScene() == null) scene = new Scene(recordView);
            else scene = recordView.getScene();

            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }


        if(source.equals(menuView.getBoutCommentJouer())){
            final Scene scene;
            if(getCommentJouerView().getRetourMenu().getScene() == null) scene = new Scene(commentJouerView);
            else scene = commentJouerView.getScene();

            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }

        if(source.equals(menuView.getBoutQuitter())){

            System.exit(0);

        }

        // BOUTON FENETRE NAME

        if(source.equals(nameView.getButtonJouer())){
            final Scene scene;
            if(nameView.getEnterName().getText() == null || nameView.getEnterName().getText().isEmpty()){
                nameView.setError("Veuillez entrer un pseudo.");
            } else {
                try{
                    if(game.getRecord().ifExistsJoueur(Liens.getCheminFichierRecord(),nameView.getEnterName().getText()) && !verif){
                        nameView.setError("Ce pseudo existe déjà, est-ce bien vous ? \n Si oui, appuyez sur \"jouer\".");
                        verif= true;
                    } else {


                        if(getJeuCompletView().getPauseView().getRetourMenu().getScene() == null){

                            scene = new Scene(jeuCompletView);
                        }

                        else {
                            this.game = new Game();

                            this.jeuCompletView = new JeuCompletView(this,game);
                            jeuCompletView.setVueJeu(this.vueJeu);
                            this.controleMenu = new ControleMenu(game, new MenuView(this,game));
                            scene = new Scene(jeuCompletView);
                        }

                        final ControleJeu controleJeu = new ControleJeu(game);

                        this.vueJeu = new VueJeu(game,scene);
                        vueJeu.setActionRetourMenu(this);
                        if(game.isPacDonald()){
                            vueJeu.initEE1Images();
                        } else{
                            vueJeu.initImages();
                        }
                        controleJeu.addControleJeu(scene, getJeuCompletView().getPauseView());

                        jeuCompletView.setVueJeu(this.vueJeu);

                        jeuCompletView.getPauseView().setVisible(false);

                        jeuCompletView.initStack();

                        game.setVueJeu(jeuCompletView.getVueJeu());

                        game.setName(nameView.getEnterName().getText());

                        jeuCompletView.getPauseView().setTextNom(game.getName());
                        jeuCompletView.getPauseView().setTextRecord("Votre meilleur score : " + game.getRecord().getRecord(Liens.getCheminFichierRecord(),game.getName()));

                        jeuCompletView.getVueJeu().refresh();

                        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                        primaryStage.setScene(scene);
                        jeuCompletView.getPauseView().setTextRecord("Votre meilleur score : " + game.getRecord().getRecord(Liens.getCheminFichierRecord(),game.getName()));
                        game.getTimeline().play();
                        audioMenu.pause();

                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        // BOUTON FENETRE RECORD

        if(source.equals(recordView.getRetourMenu())){
            final Scene scene = menuView.getScene();;
            audioMenu.loop();
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }

        // BOUTON FENETRE COMMENT JOUER

        if(source.equals(commentJouerView.getRetourMenu())){
            final Scene scene = menuView.getScene();
            audioMenu.loop();
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }

        // RETOUR MENU FENETRE PAUSE

        if(source.equals(getJeuCompletView().getPauseView().getRetourMenu())){

            final Scene scene = menuView.getScene();
            audioMenu.loop();
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
        }


        if(getJeuCompletView().getVueJeu() != null){
            if(source.equals(getJeuCompletView().getVueJeu().getRetourMenu())){
                final Scene scene = menuView.getScene();
                audioMenu.loop();
                this.game = new Game();
                scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setScene(scene);
            }
        }




    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public RecordView getRecordView() {
        return recordView;
    }

    public CommentJouerView getCommentJouerView() {
        return commentJouerView;
    }

    public NameView getNameView() {
        return nameView;
    }

    public JeuCompletView getJeuCompletView() {
        return jeuCompletView;
    }

}
