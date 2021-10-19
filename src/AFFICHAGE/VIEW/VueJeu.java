package AFFICHAGE.VIEW;

import BACK.CONTROLEUR.ControleBouton;
import BACK.CONTROLEUR.ControleJeu;
import BACK.Game;
import BACK.PREDEF.Directions;
import BACK.PREDEF.Liens;
import BACK.PREDEF.typeObjet;
import BACK.Record;
import BACK.SpriteAnimation;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;


public class VueJeu extends BorderPane{
    private final Game game;
    private GridPane gridPane;
    private Scene scene;
    private Image[] images, imagesTouches;
    private Stage stage;

    private Image pacmanImg, FBImg, FVImg, FRoseImg, FRougeImg;

    private Button retourMenu;

    Text score, niveau;
    Text nbVies;


    static int x = 0;

    static int y = 0;


    public VueJeu(ControleJeu controleJeu, Game game) {
        this.game = game;
        initGrid();
    }
    public VueJeu(Game game, Scene scene){
        this.game = game;
        this.scene = scene;
        initGrid();
        initImages();
    }

    public void initGrid(){
        this.setId("borderpane");
        retourMenu = new Button("Menu principal");
        this.gridPane = new GridPane();

        Font font = Font.font ("Minecraft", 15);

        score = new Text();
        nbVies = new Text("Nombre de vies restantes: XX");

        niveau = new Text("Niveau : X");
        niveau.setFont(font);
        niveau.setFill(Color.WHITE);

        score.setFont(font);
        score.setFill(Color.WHITE);

        nbVies.setFont(font);
        nbVies.setFill(Color.WHITE);

        GridPane barreHauteInfos = new GridPane();
        barreHauteInfos.setHgap(80);

        barreHauteInfos.add(score, 1,0);
        barreHauteInfos.add(niveau,2,0);
        barreHauteInfos.add(nbVies, 3,0);
        barreHauteInfos.setPadding(new Insets(10,10,10,10));
        this.setTop(barreHauteInfos);
        initImages();
        this.setBottom(gridPane);
    }


    public void setBoostImages(){



        FBImg = new Image(Liens.getCheminFantomeBoost());
        FVImg = new Image(Liens.getCheminFantomeBoost());
        FRoseImg = new Image(Liens.getCheminFantomeBoost());
        FRougeImg = new Image(Liens.getCheminFantomeBoost());




    }

    public void undoSetBoostImages(){

        images[0] = new Image(Liens.getCheminFantomeBleu());
        images[1] = new Image(Liens.getCheminFantomeRose());
        images[2] = new Image(Liens.getCheminFantomeRouge());
        images[3] = new Image(Liens.getCheminFantomeVert());

    }

    public void stop(){

        GridPane gridFin = new GridPane();

        gridFin.setId("pane");
        ColumnConstraints columnuss = new ColumnConstraints(game.getWIDTH_FEN());
        gridFin.getColumnConstraints().add(columnuss);

        for(int i=0;i<21;i++){
            RowConstraints row = new RowConstraints((int)game.getWIDTH_FEN()/11.0);//110
            gridFin.getRowConstraints().add(row);
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
            textScored.setText("Vous avez fait un score de : " + game.getScore() + "\n Votre meilleur score : " + game.getRecord().getRecord(Liens.getCheminFichierRecord(),game.getName()) );        } catch (IOException e) {
            e.printStackTrace();
        }
        GridPane.setHalignment(textFin, HPos.CENTER);
        GridPane.setHalignment(textScored, HPos.CENTER);


        retourMenu.setId("button");
        retourMenu.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        GridPane.setHalignment(retourMenu, HPos.CENTER);
        gridFin.setAlignment(Pos.BASELINE_CENTER);
        gridFin.add(retourMenu, 0, 10);
        gridFin.add(textFin, 0, 5);
        gridFin.add(textScored, 0, 7);

        ControleJeu controleJeu = new ControleJeu(game);
        controleJeu.addControleFin(scene);

        this.getChildren().clear();

        this.scene.setRoot(gridFin);


    }



    public void initImages(){
        this.images = new Image[20];
        images[0] = new Image(Liens.getCheminFantomeBleu());
        images[1] = new Image(Liens.getCheminFantomeRose());
        images[2] = new Image(Liens.getCheminFantomeRouge());
        images[3] = new Image(Liens.getCheminFantomeVert());
        images[4] = new Image(Liens.getCheminFondNoir());
        images[5] = new Image(Liens.getCheminGrosBonbon());
        images[6] = new Image(Liens.getCheminMur());
        images[7] = new Image(Liens.getCheminPacManBas());
        images[8] = new Image(Liens.getCheminPacManDroite());
        images[9] = new Image(Liens.getCheminPacManGauche());
        images[10] = new Image(Liens.getCheminPacManHaut());
        images[11] = new Image(Liens.getCheminPetitBonbon());
        images[12] = new Image(Liens.getCheminPorteClosed());
        images[13] = new Image(Liens.getCheminCerise());
        images[14] = new Image(Liens.getCheminFraise());
        images[15] = new Image(Liens.getCheminRaisin());
        images[16] = new Image(Liens.getCheminPomme());
        images[17] = new Image(Liens.getCheminOrange());
        images[18] = new Image(Liens.getCheminBanane());
        images[19] = new Image(Liens.getCheminPoire());




    }


    SpriteAnimation imgAnim, imgAnimFB, imgAnimFV, imgAnimFRose, imgAnimFRouge;
    private int nbFrame;

    public void refresh(){

        this.gridPane.getChildren().clear();

        int col = 0;
        int lin = 0;



        this.score.setText("SCORE : " + game.getScore());
        this.nbVies.setText("Nombre de vies restantes : " + game.getNombreVies());
        this.niveau.setText("Niveau : " + game.getNiveauActuel());

        int colonne = 0;
        int ligne = 0;

        for(int i = 0; i < game.getGrilleDuJeu().getGrilleJeu().length; i++)
        {
            for(int j = 0; j < game.getGrilleDuJeu().getGrilleJeu()[i].length; j++)
            {
                //ImageView img = new ImageView(game.getGrilleDuJeu().getGrilleJeu()[i][j].getImage());
                ImageView img = new ImageView();

                switch (game.getGrilleDuJeu().getGrilleJeu()[i][j].getType()){

                    case FANTOME_BLEU -> setFantome("FB");
                    case FANTOME_ROSE -> setFantome("FRose");
                    case FANTOME_ROUGE -> setFantome("FRouge");
                    case FANTOME_VERT -> setFantome("FV");
                    case VIDE -> img = new ImageView(getImages()[4]);
                    case GROS_BONBON -> img = new ImageView(getImages()[5]);
                    case MUR -> img = new ImageView(getImages()[6]);
                    case PETIT_BONBON -> img = new ImageView(getImages()[11]);
                    case PORTE -> img = new ImageView(getImages()[12]);
                    case CERISE -> img = new ImageView(getImages()[13]);
                    case FRAISE -> img = new ImageView(getImages()[14]);
                    case RAISIN -> img = new ImageView(getImages()[15]);
                    case POMME -> img = new ImageView(getImages()[16]);
                    case ORANGE -> img = new ImageView(getImages()[17]);
                    case BANANE -> img = new ImageView(getImages()[18]);
                    case POIRE -> img = new ImageView(getImages()[19]);
                    case PACMAN -> setPacmanImage();
                    case TELEPORT -> img = new ImageView(getImages()[4]);


                }

                if(game.getGrilleDuJeu().getGrilleJeu()[i][j].getType().equals(typeObjet.PACMAN)){
                    if (activeEE1){
                        nbFrame = 1;
                        pacmanImg = new Image(Liens.getCheminPacDonald());
                    }
                    imgAnim = new SpriteAnimation(img, pacmanImg, 1, 3, nbFrame, 50, 50, 15);
                    img.setImage(imgAnim.getImageView().getImage());
                    imgAnim.start();
                }else if(game.getGrilleDuJeu().getGrilleJeu()[i][j].getType().equals(typeObjet.FANTOME_BLEU)){
                    imgAnimFB = new SpriteAnimation(img, FBImg, 1, 3, nbFrame, 50, 50, 10);
                    img.setImage(imgAnimFB.getImageView().getImage());
                    imgAnimFB.start();
                }else if(game.getGrilleDuJeu().getGrilleJeu()[i][j].getType().equals(typeObjet.FANTOME_VERT)){
                    imgAnimFV = new SpriteAnimation(img, FVImg, 1, 3, nbFrame, 50, 50, 10);
                    img.setImage(imgAnimFV.getImageView().getImage());
                    imgAnimFV.start();
                }else if(game.getGrilleDuJeu().getGrilleJeu()[i][j].getType().equals(typeObjet.FANTOME_ROSE)){
                    imgAnimFRose = new SpriteAnimation(img, FRoseImg, 1, 3, nbFrame, 50, 50, 10);
                    img.setImage(imgAnimFRose.getImageView().getImage());
                    imgAnimFRose.start();
                }else if(game.getGrilleDuJeu().getGrilleJeu()[i][j].getType().equals(typeObjet.FANTOME_ROUGE)){
                    imgAnimFRouge = new SpriteAnimation(img, FRougeImg, 1, 3, nbFrame, 50, 50, 10);
                    img.setImage(imgAnimFRouge.getImageView().getImage());
                    imgAnimFRouge.start();
                }

                img.setFitHeight(30);
                img.setFitWidth(30);

                gridPane.add(img, colonne++, ligne);

                if (colonne >= game.getGrilleDuJeu().getGrilleJeu()[i].length)
                {
                    colonne = 0;
                    ligne++;
                }
            }
        }


    }

    public void initImagesTouches() {
        imagesTouches = new Image[3];
        imagesTouches[0] = new Image(Liens.getCheminFleches());
        imagesTouches[1] = new Image(Liens.getCheminEscape());
        imagesTouches[2] = new Image(Liens.getCheminSpace());
    }

    public ImageView[] getImagesTouchesViewResized() {
        ImageView[] resized = new ImageView[3];
        for(int i = 0; i<3; i++) {
            resized[i] = new ImageView(imagesTouches[i]);
            resized[i].setPreserveRatio(true);
        }
        resized[0].setFitWidth(imagesTouches[0].getWidth()*0.17);
        resized[1].setFitWidth(imagesTouches[1].getWidth()*0.26);
        resized[2].setFitWidth(imagesTouches[2].getWidth()*0.25);
        return resized;
    }

    public Image[] getImagesTouches() {
        return this.imagesTouches;
    }

    public void setPacmanImage() {
        Directions dir = game.getDirections();
        nbFrame = 3;
        if (!game.direc) { dir = game.getDerniereDirection(); }

        int[] coordsPacman = game.getGrilleDuJeu().getPacMan().getObjetMobile().getCoordonees();

        if (coordsPacman[1] != 0 && coordsPacman[1] != 21) {    //REGLER LE PROBLEME DES TP CAR ON VERIFIE DEVANT NOUS DANS LE VIDE (A RETIRER, C'EST JUSTE TEMPORAIREMENT)
            if ((game.getGrilleDuJeu().contains(coordsPacman[0] + game.getGrilleDuJeu().convertDir(dir)[0], coordsPacman[1] + game.getGrilleDuJeu().convertDir(dir)[1]).isTraversable()))
                pacmanImg = dirGivesImage(dir);
            else
                pacmanImg = dirGivesImage(game.getDerniereDirection());
        }
        else
            pacmanImg = dirGivesImage(dir);  //ON AFFICHE L'IMAGE SI ON EST SUR LES TP QUAND MEME (A RETIRER)
    }

    public Image dirGivesImage(Directions dir) {
        return switch (dir) {
            case DROITE -> new Image(Liens.getCheminPacManDroite());
            case GAUCHE -> new Image(Liens.getCheminPacManGauche());
            case BAS -> new Image(Liens.getCheminPacManBas());
            case HAUT -> new Image(Liens.getCheminPacManHaut());
            default -> null;
        };
    }

    public void setFantome(String fantome){
        nbFrame = 2;

        if(!game.isBoosted()){
            switch (fantome) {
                case "FB":
                    Directions dirB = game.getDirectionsF()[2];
                    switch (dirB) {
                        case DROITE, NULL -> FBImg = new Image(Liens.getCheminFantomeBleuDroite());
                        case GAUCHE -> FBImg = new Image(Liens.getCheminFantomeBleuGauche());
                        case HAUT -> FBImg = new Image(Liens.getCheminFantomeBleuHaut());
                        case BAS -> FBImg = new Image(Liens.getCheminFantomeBleuBas());
                    }
                case "FV":
                    Directions dirV = game.getDirectionsF()[3];
                    switch (dirV) {
                        case DROITE, NULL -> FVImg = new Image(Liens.getCheminFantomeVertDroite());
                        case GAUCHE -> FVImg = new Image(Liens.getCheminFantomeVertGauche());
                        case HAUT -> FVImg = new Image(Liens.getCheminFantomeVertHaut());
                        case BAS -> FVImg = new Image(Liens.getCheminFantomeVertBas());
                    }
                case "FRose":
                    Directions dirRose = game.getDirectionsF()[0];
                    switch (dirRose) {
                        case DROITE, NULL -> FRoseImg = new Image(Liens.getCheminFantomeRoseDroite());
                        case GAUCHE -> FRoseImg = new Image(Liens.getCheminFantomeRoseGauche());
                        case HAUT -> FRoseImg = new Image(Liens.getCheminFantomeRoseHaut());
                        case BAS -> FRoseImg = new Image(Liens.getCheminFantomeRoseBas());
                    }
                case "FRouge":
                    Directions dirRouge = game.getDirectionsF()[1];
                    switch (dirRouge) {
                        case DROITE, NULL -> FRougeImg = new Image(Liens.getCheminFantomeRougeDroite());
                        case GAUCHE -> FRougeImg = new Image(Liens.getCheminFantomeRougeGauche());
                        case HAUT -> FRougeImg = new Image(Liens.getCheminFantomeRougeHaut());
                        case BAS -> FRougeImg = new Image(Liens.getCheminFantomeRougeBas());
                    }
            }
        }
    }

    private boolean activeEE1;
    public void initEE1Images(){
        activeEE1 = true;
        images[5] = new Image(Liens.getCheminGrosBonbonEE1());
        images[6] = new Image(Liens.getCheminMurEE1());
        images[11] = new Image(Liens.getCheminBonbonEE1());
    }






    //////////////////////////////////////////////

    public void setAttrib(Text s, Text v){

        this.score = s;
        this.nbVies = v;

    }


    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Text getScore() {
        return score;
    }

    public void setScore(Text score) {
        this.score = score;
    }

    public Text getNbVies() {
        return nbVies;
    }

    public void setNbVies(Text nbVies) {
        this.nbVies = nbVies;
    }
    public void setActionRetourMenu(ControleBouton controleBouton){
        this.retourMenu.setOnAction(controleBouton);
    }
    public Button getRetourMenu() {
        return retourMenu;
    }

}
