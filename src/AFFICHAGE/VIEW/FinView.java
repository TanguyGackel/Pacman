package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.CONTROLEUR.ControleJeu;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;

public class FinView extends GridPane {

    private Game game;
    private Button retourMenu;

    private Scene scene;

    public FinView(Game game){
        this.game = game;
        initGrid();

    }


    public void initGrid(){
        this.setId("pane");
        ColumnConstraints columnuss = new ColumnConstraints(game.getWIDTH_FEN());
        this.getColumnConstraints().add(columnuss);

        for(int i=0;i<21;i++){
            RowConstraints row = new RowConstraints((int)game.getWIDTH_FEN()/11.0);//110
            this.getRowConstraints().add(row);
        }

        Text textFin = new Text();
        textFin.setFont(javafx.scene.text.Font.font ("Minecraft", 30));
        textFin.setFill(Color.WHITE);
        textFin.setTextAlignment(TextAlignment.CENTER);
        textFin.setText("Fin de la partie");

        Text textScored = new Text();
        textScored.setFont(javafx.scene.text.Font.font ("Minecraft", 30));
        textScored.setFill(Color.WHITE);
        textScored.setTextAlignment(TextAlignment.CENTER);
        try {
            textScored.setText("Vous avez fait un score de : " + game.getScore() + "\n Votre meilleur score : " + game.getRecord().getRecord(Liens.getCheminFichierRecord(),game.getName()) );
        } catch (IOException e) {
            e.printStackTrace();
        }


        retourMenu = new Button("Menu principal");
        FinView.this.retourMenu.setId("button");
        FinView.this.retourMenu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(FinView.this.retourMenu, HPos.CENTER);
        this.setAlignment(Pos.BASELINE_CENTER);
        this.add(FinView.this.retourMenu, 0, 10);
        this.add(textFin, 0, 5);
        this.add(textScored, 0, 7);

    }

    public void setController(ControleBouton controleBouton){
        this.retourMenu.setOnAction(controleBouton);
    }



    public Button getRetourMenu() {
        return retourMenu;
    }


}
