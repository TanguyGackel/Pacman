package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.Game;
import BACK.PREDEF.Liens;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CommentJouerView extends GridPane {

    private Button retourMenu;
    private final Game game;
    Image[] imagesTouches;
    ImageView[] imageTouchesResized;


    public CommentJouerView(ControleBouton controleBouton, Game game){
        this.game = game;
        initImagesTouches();
        initImagesTouchesViewResized();
        initGrid();
        retourMenu.setOnAction(controleBouton);
    }

    public void initGrid(){
        this.setId("pane");

        ColumnConstraints columnConstraints = new ColumnConstraints(game.getWIDTH_FEN());
        this.getColumnConstraints().add(columnConstraints);

        for(int i=0;i<15;i++){
            RowConstraints rowConstraints = new RowConstraints(game.getWIDTH_FEN()/11.0);
            this.getRowConstraints().add(rowConstraints);
        }

        Font font = Font.font("Minecraft", 12);

        for(int i=0;i<5;i++){
            Rectangle recOption = new Rectangle(500, 45);
            Text text = new Text(game.getTextCommentJouer()[i]);

            recOption.setArcWidth(20);
            recOption.setArcHeight(20);

            text.setFont(font);
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);

            if (i == 4) {
                recOption.setHeight(150);
                text.setFont(javafx.scene.text.Font.font ("Minecraft", 16));
                text.setWrappingWidth(450);
            }


            StackPane stackOption = new StackPane();
            if (i <3) {
                HBox hbox = new HBox();
                hbox.getChildren().addAll(imageTouchesResized[i], text);
                hbox.setAlignment(Pos.CENTER);
                hbox.setMinSize(500, 45);
                hbox.setMaxSize(500, 45);
                hbox.setPadding(new Insets(0, 0, 0, 8));
                hbox.setSpacing(8);
                stackOption.getChildren().addAll(recOption, hbox);
                StackPane.setAlignment(hbox, Pos.CENTER);
                this.add(stackOption, 0, i + 4);
            }
            else
                stackOption.getChildren().addAll(recOption,text);

            if (i >3)
                this.add(stackOption, 0, i + 4);
        }

        retourMenu = new Button("Menu principal");
        retourMenu.setId("button");
        retourMenu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(retourMenu, HPos.CENTER);


        this.add(retourMenu,0,10);

    }

    public void initImagesTouches() {
        imagesTouches = new Image[3];
        imagesTouches[0] = new Image(Liens.getCheminFleches());
        imagesTouches[1] = new Image(Liens.getCheminEscape());
        imagesTouches[2] = new Image(Liens.getCheminSpace());
    }

    public void initImagesTouchesViewResized() {
        imageTouchesResized= new ImageView[3];
        for(int i = 0; i<3; i++) {
            imageTouchesResized[i] = new ImageView(imagesTouches[i]);
            imageTouchesResized[i].setPreserveRatio(true);
        }
        imageTouchesResized[0].setFitWidth(imagesTouches[0].getWidth()*0.17);
        imageTouchesResized[1].setFitWidth(imagesTouches[1].getWidth()*0.26);
        imageTouchesResized[2].setFitWidth(imagesTouches[2].getWidth()*0.25);

    }

    public Button getRetourMenu() {
        return retourMenu;
    }

}
