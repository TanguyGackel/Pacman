package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.CONTROLEUR.ControleJeu;
import BACK.Game;
import javafx.scene.layout.StackPane;

public class JeuCompletView extends StackPane {
    private Game game;
    private PauseView pauseView;
    private VueJeu vueJeu;

    public JeuCompletView(ControleBouton controleBouton, Game game){
        this.game = game;
        this.pauseView = new PauseView(controleBouton,this.game);


    }

    public void setControleJeu(ControleJeu controleJeu){
        vueJeu = new VueJeu(controleJeu,this.game);

    }
    public void setVueJeu(VueJeu vueJeu){
        this.vueJeu = vueJeu;
    }

    public void initStack(){
        this.getChildren().clear();
        this.getChildren().add(vueJeu);
        this.getChildren().add(pauseView);

    }

    public PauseView getPauseView() {
        return pauseView;
    }

    public VueJeu getVueJeu() {
        return vueJeu;
    }


}
