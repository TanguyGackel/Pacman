package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class PauseView extends GridPane {
    private Button retourMenu;
    private final Game game;
    Text textNom, textRecord;

    public PauseView(ControleBouton controleBouton, Game game){
        this.game = game;
        initGrid();
        retourMenu.setOnAction(controleBouton);
    }

    public void initGrid() {


        ColumnConstraints columnConstraints = new ColumnConstraints(450);
        this.getColumnConstraints().add(columnConstraints);

        for(int i=0;i<9;i++){
            RowConstraints rowConstraints = new RowConstraints(400.0/9.0);
            this.getRowConstraints().add(rowConstraints);
        }

        this.setId("pane");
        this.setMaxWidth(450);
        this.setMaxHeight(400);

        Font font = Font.font("Minecraft", 12);

        Text pressSpace = new Text("Appuyer sur espace pour continuer.");
        pressSpace.setFont(font);
        pressSpace.setFill(Color.WHITE);
        GridPane.setHalignment(pressSpace, HPos.CENTER);
        this.add(pressSpace,0,3);

        StackPane stackPane = new StackPane();
        Rectangle rec = new Rectangle();
        rec.setWidth(250);
        rec.setHeight(30);
        rec.setArcWidth(20);
        rec.setArcHeight(20);

        textNom = new Text();
        textNom.setFont(font);
        textNom.setFill(Color.WHITE);

        StackPane stackRecord = new StackPane();
        Rectangle recRecord = new Rectangle();
        recRecord.setWidth(250);
        recRecord.setHeight(30);
        recRecord.setArcWidth(20);
        recRecord.setArcHeight(20);
        try {
            System.out.println(game.getName());
            textRecord = new Text("Meilleur score : " + game.getRecord().getRecord(Liens.getCheminFichierRecord(),game.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textRecord.setFont(font);
        textRecord.setFill(Color.WHITE);

        stackPane.getChildren().addAll(rec,textNom);
        stackRecord.getChildren().addAll(recRecord,textRecord);

        this.add(stackPane,0,4);
        this.add(stackRecord,0,5);




        retourMenu = new Button("Menu Principal");

        retourMenu.setId("button");
        retourMenu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        retourMenu.setFocusTraversable(false);
        GridPane.setHalignment(retourMenu, HPos.CENTER);
        this.add(retourMenu,0,7);

    }

    public Button getRetourMenu() {
        return retourMenu;
    }

    public Text getTextNom() {
        return textNom;
    }

    public void setTextNom(String textNom) {
        this.textNom.setText(textNom);
    }

    public Text getTextRecord() {
        return textRecord;
    }

    public void setTextRecord(String textRecord) {
        this.textRecord.setText(textRecord);
    }
}
