package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.CONTROLEUR.ControleMenu;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Objects;

public class MenuView extends GridPane {
    private Button boutJouer, boutRecord, boutCommentJouer, boutQuitter;
    private Game game;

    public MenuView(ControleBouton controleBouton, Game game){
        this.game = game;
        initGrid();
        boutJouer.setOnAction(controleBouton);
        boutRecord.setOnAction(controleBouton);
        boutCommentJouer.setOnAction(controleBouton);
        boutQuitter.setOnAction(controleBouton);

    }

    public void setOnKey(ControleMenu controleMenu){
        this.setOnKeyPressed(controleMenu);
    }

    public void initGrid(){
        this.setId("pane");

        ColumnConstraints columnConstraints = new ColumnConstraints(game.getWIDTH_FEN());
        this.getColumnConstraints().add(columnConstraints);

        for(int i=0;i<6;i++){
            RowConstraints rowConstraints = new RowConstraints(game.getWIDTH_FEN()/6.0);
            this.getRowConstraints().add(rowConstraints);
        }

        boutJouer = new Button("Jouer");
        boutJouer.setFocusTraversable(false);
        boutJouer.setId("button");
        boutJouer.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(boutJouer, HPos.CENTER);

        boutRecord = new Button("Records");
        boutRecord.setFocusTraversable(false);
        boutRecord.setId("button");
        boutRecord.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(boutRecord, HPos.CENTER);

        boutCommentJouer = new Button("Comment jouer");
        boutCommentJouer.setFocusTraversable(false);
        boutCommentJouer.setId("button");
        boutCommentJouer.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(boutCommentJouer, HPos.CENTER);

        boutQuitter = new Button("Quitter");
        boutQuitter.setId("button");
        boutQuitter.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        boutQuitter.setMaxWidth(100);
        GridPane.setHalignment(boutQuitter, HPos.CENTER);


        this.add(boutJouer,0,2);
        this.add(boutRecord, 0,3);
        this.add(boutCommentJouer,0,4);
        this.add(boutQuitter,0,5);

        //this.getStylesheets().add(getClass().getResource("./style.css").toExternalForm());
    }

    public Button getBoutJouer() {
        return boutJouer;
    }

    public Button getBoutRecord() {
        return boutRecord;
    }

    public Button getBoutCommentJouer() {
        return boutCommentJouer;
    }

    public Button getBoutQuitter() {
        return boutQuitter;
    }


}
