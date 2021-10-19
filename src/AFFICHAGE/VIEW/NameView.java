package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class NameView extends GridPane {

    private Button buttonJouer;
    private final Game game;

    private TextField enterName;
    private Text error;

    public NameView(ControleBouton controleBouton, Game game){
        this.game = game;
        initGrid();
        buttonJouer.setOnAction(controleBouton);
    }

    public void initGrid(){
        this.setId("pane");

        ColumnConstraints columnConstraints = new ColumnConstraints(game.getWIDTH_FEN());
        this.getColumnConstraints().add(columnConstraints);

        for(int i=0;i<6;i++){
            RowConstraints rowConstraints = new RowConstraints(game.getWIDTH_FEN()/6.0);
            this.getRowConstraints().add(rowConstraints);
        }

        Font font = Font.font("Minecraft", 25);

        enterName = new TextField();

        enterName = new TextField();
        enterName.setPromptText("Entrez votre pseudo : ");
        enterName.setFont(font);
        enterName.setMaxWidth(300);
        enterName.setMinHeight(50);
        GridPane.setHalignment(enterName, HPos.CENTER);
        enterName.setFocusTraversable(false);

        error = new Text();
        error.setFont(javafx.scene.text.Font.font ("Minecraft", 25));
        error.setFill(Color.WHITE);
        GridPane.setHalignment(error,HPos.CENTER);
        this.add(error,0,4);

        buttonJouer = new Button("Jouer");

        buttonJouer.setId("button");
        buttonJouer.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(buttonJouer, HPos.CENTER);





        this.add(enterName,0,3);




        this.add(buttonJouer,0,5);


    }

    public Button getButtonJouer() {
        return buttonJouer;
    }

    public TextField getEnterName() {
        return enterName;
    }

    public void setEnterName(TextField enterName) {
        this.enterName = enterName;
    }

    public Text getError() {
        return error;
    }

    public void setError(String error) {
        this.error.setText(error);
    }
}
