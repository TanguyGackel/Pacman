package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.Game;
import BACK.PREDEF.Liens;
import BACK.Record;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
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

public class RecordView extends GridPane {

    private Button retourMenu;
    private Game game;
    private Font font;

    public RecordView(ControleBouton controleBouton, Game game) {
        this.game = game;
        initGrid();
        retourMenu.setOnAction(controleBouton);
    }

    public void initGrid(){
        this.setId("pane");

        ColumnConstraints columnConstraints = new ColumnConstraints(game.getHEIGHT_FEN());
        this.getColumnConstraints().add(columnConstraints);

        for(int i=0;i<14;i++){
            RowConstraints rowConstraints = new RowConstraints(game.getWIDTH_FEN()/14.0);
            this.getRowConstraints().add(rowConstraints);
        }

        font = javafx.scene.text.Font.font ("Minecraft", 20);

        for(int i=0; i<5;i++){

            StackPane stackPane = new StackPane();

            Rectangle rec = new Rectangle();
            rec.setWidth(250);
            rec.setHeight(30);
            rec.setArcWidth(20);
            rec.setArcHeight(20);

            Text textRecord = null;
            try {
                textRecord = new Text(game.getCinqMaxRecords()[i][0] + " = " + game.getCinqMaxRecords()[i][1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textRecord.setFont(font);
            textRecord.setFill(Color.WHITE);

            stackPane.getChildren().addAll(rec,textRecord);

            this.add(stackPane,0,i+5);
        }


        retourMenu = new Button("Menu principal");
        retourMenu.setId("button");
        retourMenu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(retourMenu, HPos.CENTER);
        this.setAlignment(Pos.BASELINE_CENTER);

        this.add(retourMenu,0,11);
        

    }

    public Button getRetourMenu() {
        return retourMenu;
    }

}
