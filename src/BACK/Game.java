package BACK;

import AFFICHAGE.VIEW.FinView;
import AFFICHAGE.VIEW.VueJeu;
import BACK.CONTROLEUR.ControleBouton;
import BACK.OBJETS.Objet;
import BACK.OBJETS.ObjetMobile;
import BACK.PREDEF.Directions;
import BACK.PREDEF.Liens;
import BACK.PREDEF.typeObjet;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Game<boosted> {
    final int HEIGHT_FEN = 720;
    final int WIDTH_FEN = 660;

    private Grille grilleDuJeu;

    private int score;

    private Record record;

    private String[] textCommentJouer;

    private VueJeu vueJeu;

    private int nombreVies;

    private boolean enCours, boosted, pacDonald;

    public boolean premierDemarrage = true;

    private String name;

    private Directions directions;

    private int niveauActuel;

    private Directions derniereDirection;

    public Audio audioBoost;

    public Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), actionEvent -> run()));

    public boolean direc;
    private int tempsRestantBooste;
    private Directions directionsF[];

    boolean[] scatter = {false, false, false, false};

    int scatterCompte = 0;

    boolean chaseMode = true;

    int[] scatterRouge = {1, 20};

    int[] scatterB = {20, 20};

    int[] scatterRose = {1,1};

    int[] scatterV = {20, 1};



    public Game(){

        setTextCommentJouer();



        record = new Record();

        timeline.setCycleCount(Animation.INDEFINITE);

        this.grilleDuJeu = new Grille(this);

        this.score = 0;

        this.nombreVies = 3;

        this.niveauActuel = 1;


        this.directions = Directions.DROITE;
        this.derniereDirection = Directions.DROITE;
        this.enCours = true;
        this.directionsF = new Directions[]{Directions.NULL,Directions.NULL,Directions.NULL,Directions.NULL};
        try {
            audioBoost = new Audio(Liens.getCheminSon_intermission());
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {


        System.out.println("Nombre de bonbons mangés: " + grilleDuJeu.getNbBonbonsManges() + "/" + grilleDuJeu.getNbBonbons());



        this.vueJeu.refresh();
        boostedActions();
        if (this.score % 10000 == 0 && this.score > 0){this.nombreVies+=1;}

        grilleDuJeu.genFruit();

        jeuPacman();


        jeuFantomes();

        if(tempsRestantBooste == 0){
            tempsRestantBooste += 15;
            grilleDuJeu.setSerie(1);
            setBoosted(false);
        }else if(boosted){
            tempsRestantBooste--;
        }

        grilleDuJeu.printObjetMob();
        System.out.println("//////////////////////////////////");


        if (checkFin()){
            System.out.println("FIN DU JEU");
            try {
                this.record.setRecord(Liens.getCheminFichierRecord(),this.name, String.valueOf(score));
            } catch (IOException e) {
                e.printStackTrace();
            }
            timeline.stop();
            vueJeu.stop();
        }


    }

    public void jeuPacman(){


     /*


        if(!this.grilleDuJeu.deplaceObjet(this.grilleDuJeu.getPacMan(),directions)){
            this.grilleDuJeu.deplaceObjet(this.grilleDuJeu.getPacMan(),derniereDirection);
            direc = false;

        }else{
            direc = true;

            this.derniereDirection = directions;

        }

         */


        if(!this.grilleDuJeu.deplacerPacman(directions, this.grilleDuJeu.getPacMan())){
            this.grilleDuJeu.deplacerPacman(derniereDirection, this.grilleDuJeu.getPacMan());
            direc = false;
        }else{
            direc = true;
            this.derniereDirection = directions;
        }

    }

    public void jeuFantomes(){
        scatterCompte++;

        ObjetMobile[] tabFantome = this.grilleDuJeu.getFantomes();
        Noeud[][] tabCoo= new Noeud[22][22];
        Noeud fantomeRouge = null;
        Noeud fantomeB = null;
        Noeud fantomeRose = null;
        Noeud fantomeV = null;
        Noeud pacman = null;



        for(int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                if (this.grilleDuJeu.getGrilleJeu()[i][j].equals(tabFantome[2])){
                    fantomeB = new Noeud(i, j, "f");
                    tabCoo[i][j] = fantomeB;

                } else if (this.grilleDuJeu.getGrilleJeu()[i][j].equals(tabFantome[1])) {
                    fantomeRouge = new Noeud(i, j, "f");
                    tabCoo[i][j] = fantomeRouge;

                } else if (this.grilleDuJeu.getGrilleJeu()[i][j].equals(tabFantome[0])) {
                    fantomeRose = new Noeud(i, j, "f");
                    tabCoo[i][j] = fantomeRose;

                } else if (this.grilleDuJeu.getGrilleJeu()[i][j].equals(tabFantome[3])) {
                    fantomeV = new Noeud(i, j, "f");
                    tabCoo[i][j] = fantomeV;

                } else if (this.grilleDuJeu.getGrilleJeu()[i][j].equals(this.grilleDuJeu.getPacMan())) {
                    pacman = new Noeud(i, j, "p");
                    tabCoo[i][j] = pacman;
                } else if (this.grilleDuJeu.getGrilleJeu()[i][j].isTraversable() || this.grilleDuJeu.contains(i,j).getType().equals(typeObjet.PORTE) ){
                    tabCoo[i][j] = new Noeud(i, j, "o");
                }else{
                    tabCoo[i][j] = new Noeud(i, j, "n");
                }
            }
        }

        if(scatterCompte > 80 && chaseMode)
        {
            chaseMode = false;
            for(int i = 0; i < 4; i++){
                scatter[i] = true;
            }
            scatterCompte = 0;
        }
        if(scatterCompte > 80)
        {
            chaseMode = true;
            for(int i = 0; i < 4; i++){
                scatter[i] = false;
            }
            scatterCompte = 0;
        }

        if(!boosted){
            iaRouge(tabCoo, fantomeRouge, pacman, tabFantome[1]);
            iaBleu(tabCoo, fantomeB, pacman, tabFantome[2], fantomeRouge);
            iaRose(tabCoo, fantomeRose, pacman, tabFantome[0]);
            iaVert(tabCoo, fantomeV, pacman, tabFantome[3]);
        }
        else{
            iaAleatoire();
        }

    }

    public void deplacementFApresAStar(Directions direction, ObjetMobile fantome, int ordre)
    {
        boolean verif = false;
        if (direction != null && !direction.equals(Directions.NULL)) {

            verif = this.grilleDuJeu.deplacerFantome(direction, fantome);
            directionsF[ordre] = direction;
            while (!verif) {
                Directions directTempo = this.grilleDuJeu.genDir();
                verif = this.grilleDuJeu.deplacerFantome(directTempo, fantome);
                directionsF[ordre] = directTempo;
            }
        } else {
            while (!verif) {
                Directions directTempo = this.grilleDuJeu.genDir();
                verif = this.grilleDuJeu.deplacerFantome(directTempo, fantome);
                directionsF[ordre] = directTempo;

            }

        }
    }

    public void iaRouge(Noeud[][] tab, Noeud fantome, Noeud cible, ObjetMobile mFantome){
        if(!scatter[1]){
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 1);
            }
        }
        else{
            tab[cible.getX()][cible.getY()] = new Noeud(cible.getX(), cible.getY(), "o");
            cible = new Noeud(scatterRouge[0], scatterRouge[1], "p");
            tab[cible.getX()][cible.getY()] = cible;
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 1);
            }
            if(fantome.getX() == scatterRouge[0] && fantome.getY() == scatterRouge[1]){
                scatter[1] = false;
            }
        }
    }
    public void iaBleu(Noeud[][] tab, Noeud fantome, Noeud cible, ObjetMobile mFantome, Noeud fantomeRouge){
        if(!scatter[2]){
            int xPacmanRouge = cible.getX() - fantomeRouge.getX();
            int yPacmanRouge = cible.getY() - fantomeRouge.getY();
            if(cible.getX()+(this.grilleDuJeu.convertDir(derniereDirection)[0])*2+xPacmanRouge < 22 && cible.getX()+(this.grilleDuJeu.convertDir(derniereDirection)[0])*2+xPacmanRouge >= 22 && cible.getY()+(this.grilleDuJeu.convertDir(derniereDirection)[1])*2+yPacmanRouge < 22 && cible.getY()+(this.grilleDuJeu.convertDir(derniereDirection)[1])*2+yPacmanRouge >= 0){
                cible.setX(cible.getX()+this.grilleDuJeu.convertDir(derniereDirection)[0]*2+xPacmanRouge);
                cible.setY(cible.getY()+this.grilleDuJeu.convertDir(derniereDirection)[1]*2+yPacmanRouge);
                if(derniereDirection.equals(Directions.HAUT) && cible.getY()-2 > 0){
                    cible.setY(cible.getY()-2);
                }
            }
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 0);
            }
        }
        else{
            tab[cible.getX()][cible.getY()] = new Noeud(cible.getX(), cible.getY(), "o");
            cible = new Noeud(scatterB[0], scatterB[1], "p");
            tab[cible.getX()][cible.getY()] = cible;
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 2);
            }
            if(fantome.getX() == scatterB[0] && fantome.getY() == scatterB[1]){
                scatter[2] = false;
            }
        }


    }
    public void iaRose(Noeud[][] tab, Noeud fantome, Noeud cible, ObjetMobile mFantome){
        if(!scatter[0]){
            if(cible.getX()+(this.grilleDuJeu.convertDir(derniereDirection)[0])*4 < 22 && cible.getX()+(this.grilleDuJeu.convertDir(derniereDirection)[0])*4 >= 22 && cible.getY()+(this.grilleDuJeu.convertDir(derniereDirection)[1])*4 < 22 && cible.getY()+(this.grilleDuJeu.convertDir(derniereDirection)[1])*4 >= 0)
            {
                cible.setX(cible.getX()+this.grilleDuJeu.convertDir(derniereDirection)[0]*4);
                cible.setY(cible.getY()+this.grilleDuJeu.convertDir(derniereDirection)[1]*4);
                if(derniereDirection.equals(Directions.HAUT) && cible.getY()-4 > 0){
                    cible.setY(cible.getY()-4);
                }
            }
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 0);
            }
        }
        else{
            tab[cible.getX()][cible.getY()] = new Noeud(cible.getX(), cible.getY(), "o");
            cible = new Noeud(scatterRose[0], scatterRose[1], "p");
            tab[cible.getX()][cible.getY()] = cible;
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 0);
            }
            if(fantome.getX() == scatterRose[0] && fantome.getY() == scatterRose[1]){
                scatter[0] = false;
            }
        }
    }
    public void iaVert(Noeud[][] tab, Noeud fantome, Noeud cible, ObjetMobile mFantome){
        if(Noeud.manhattan(fantome.getX(), cible.getX(), fantome.getY(), cible.getY()) < 8)
        {
            scatter[3] = true;
        }
        if(!scatter[3]){
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 3);
            }
        }
        else{
            tab[cible.getX()][cible.getY()] = new Noeud(cible.getX(), cible.getY(), "o");
            cible = new Noeud(scatterV[0], scatterV[1], "p");
            tab[cible.getX()][cible.getY()] = cible;
            ArrayList<Directions> cheminR = Noeud.aStar(tab, fantome, cible);
            if(cheminR != null) {
                deplacementFApresAStar(cheminR.get(0), mFantome, 3);
            }
            if(fantome.getX() == scatterV[0] && fantome.getY() == scatterV[1]){
                scatter[3] = false;
            }
        }
    }




    public void iaAleatoire(){
        Objet[] fantomes = grilleDuJeu.getFantomes();
        int n = 0;
        boolean verif;
        for(int i = 0; i < 4; i++)
        {
            n++;
            if(this.directionsF[i] == Directions.NULL)
            {
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            if(n >= 15){
                n = 0;
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            if(this.directionsF[i].equals(Directions.GAUCHE) || this.directionsF[i].equals(Directions.DROITE))
            {
                Random random = new Random();
                int nombreAleatoire = random.nextInt(6);
                if(nombreAleatoire == 1 || nombreAleatoire == 5){
                    this.directionsF[i] = Directions.BAS;
                }
                if(nombreAleatoire == 3){
                    this.directionsF[i] = Directions.HAUT;
                }
            }
            while(!(this.grilleDuJeu.contains(fantomes[i].getObjetMobile().getCoordonees()[0]+this.grilleDuJeu.convertDir(directionsF[i])[0], fantomes[i].getObjetMobile().getCoordonees()[1]+this.grilleDuJeu.convertDir(directionsF[i])[1]).isTraversable())){
                this.directionsF[i] = this.grilleDuJeu.genDir();
            }
            verif = grilleDuJeu.deplacerFantome(this.directionsF[i], fantomes[i]);
            if(!verif){
                this.directionsF[i] = Directions.NULL;
            }
        }
    }





    public void addBoost(){
        if(!boosted){

            this.boosted = true;
        }
        this.tempsRestantBooste += 20;
        audioBoost.loop();
    }



    public boolean checkFin(){

        if(this.nombreVies == 0){
            return true;
        }else if(this.grilleDuJeu.getNbBonbonsManges() == this.grilleDuJeu.getNbBonbons()){
            try {
                grilleDuJeu.initGrille();
                boosted = false;
                this.niveauActuel += 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public String[][] getCinqMaxRecords() throws IOException {
        return record.getMaxRecord(Liens.getCheminFichierRecord());
    }

    public Grille getGrilleDuJeu(){
        return grilleDuJeu;
    }

    public void setGrilleDuJeu(Grille grilleDuJeu) {
        this.grilleDuJeu = grilleDuJeu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNombreVies() {
        return nombreVies;
    }

    public void setNombreVies(int nombreVies) {
        this.nombreVies = nombreVies;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
        if(boosted){
            this.tempsRestantBooste = 20;
            vueJeu.setBoostImages();
        }else{
            vueJeu.undoSetBoostImages();
        }
    }

    public void boostedActions(){
        if(this.boosted){
            vueJeu.setBoostImages();
        }else{
            vueJeu.undoSetBoostImages();
            audioBoost.pause();
        }
    }

    public boolean isEnCours() {
        return enCours;
    }

    public void setEnCours(boolean enCours) {
        this.enCours = enCours;
    }

    public boolean isPremierDemarrage() {
        return premierDemarrage;
    }

    public void setPremierDemarrage(boolean premierDemarrage) {
        this.premierDemarrage = premierDemarrage;
    }

    public Directions getDirections() {
        return directions;
    }

    public void setDirections(Directions directions) {
        this.directions = directions;
    }

    public Directions getDerniereDirection() {
        return derniereDirection;
    }

    public void setDerniereDirection(Directions derniereDirection) {
        this.derniereDirection = derniereDirection;
    }

    public Timeline getTimeline() {

        return timeline;
    }

    public VueJeu getVueJeu() {
        return vueJeu;
    }

    public void setVueJeu(VueJeu vueJeu) {
        this.vueJeu = vueJeu;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public int getTempsRestantBooste() {
        return tempsRestantBooste;
    }

    public void setTempsRestantBooste(int tempsRestantBooste) {
        this.tempsRestantBooste = tempsRestantBooste;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHEIGHT_FEN() {
        return HEIGHT_FEN;
    }

    public int getWIDTH_FEN() {
        return WIDTH_FEN;
    }

    public Record getRecord() {
        return record;
    }

    public String[] getTextCommentJouer() {
        return textCommentJouer;
    }


    public void setTextCommentJouer() {
        this.textCommentJouer = new String[5];
        textCommentJouer[0] = "Utiliser les flèches directionnelles pour se déplacer";
        textCommentJouer[1] = "Touche ECHAPE pour afficher le menu Pause";
        textCommentJouer[2] = "Touche ESPACE pour quitter le menu Pause";
        textCommentJouer[3] = "";
        textCommentJouer[4] = """
                Pacman a 3 vies et doit manger les Gums qui lui font augmenter son score tout en esquivant les fantomes.
                Manger une grosse gum lui permet de manger les fantômes et de devenir invincible temporairement.
                Manger toutes les Gums lui font passer un niveau.
                Le but est d'obtenir le meilleur score.""";
    }

        public Directions[] getDirectionsF() {
        return directionsF;
    }
    public int getNiveauActuel() {
        return niveauActuel;
    }

    public void setNiveauActuel(int niveauActuel) {
        this.niveauActuel = niveauActuel;
    }
    public boolean isPacDonald() {
        return pacDonald;
    }

    public void setPacDonald(boolean pacDonald) {
        this.pacDonald = pacDonald;
    }






}
