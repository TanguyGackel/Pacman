 package BACK;

        import BACK.OBJETS.Objet;
        import BACK.OBJETS.ObjetDecors;
        import BACK.OBJETS.ObjetMobile;
        import BACK.PREDEF.Directions;
        import BACK.PREDEF.Liens;
        import BACK.PREDEF.typeObjet;

        import javax.sound.sampled.LineUnavailableException;
        import javax.sound.sampled.UnsupportedAudioFileException;
        import java.io.*;
        import java.util.Random;

public class Grille {

    private Objet[][] grilleJeu;

    private ObjetDecors[][] grilleDecors;

    private ObjetMobile[][] grilleObjetsMobiles;

    private int nbBonbons;

    private int nbBonbonsManges;

    private ObjetMobile PacMan;

    private ObjetMobile[] fantomes; // rose, rouge, bleu , vert

    private Game game;

    private Audio audio;

    private boolean fruitPop;

    private Objet[] dernieresCasesSupprimees = {new Objet(typeObjet.VIDE), new Objet(typeObjet.VIDE), new Objet(typeObjet.VIDE),new Objet(typeObjet.VIDE), null};

    public Grille(Game game){

        this.game = game;

        this.fruitPop = false;

        this.nbBonbons = 0;
        this.nbBonbonsManges = 0;

        fantomes = new ObjetMobile[4];

        try {
            initGrille();
        } catch (IOException e) {
            System.out.println("Erreur à l'initialisation de la grille");
        }
    }

    public String selectMap(){
        Random rd = new Random();
        int nb = 1 + rd.nextInt(2);
        String str = "src/RESSOURCES/cartes/carte"+ nb + ".txt";
        return str;



    }


    public void initGrille() throws IOException {

        char[][] tab = new char[22][22];

        Objet[][] toReturn = new Objet[22][22];

        ObjetMobile[][] toReturnMob = new ObjetMobile[22][22];
        ObjetDecors[][] toReturnFix = new ObjetDecors[22][22];


        // Le fichier d'entrée
        File file = new File(selectMap());
        // Créer l'objet File Reader
        FileReader fileReader = new FileReader(file);
        // Créer l'objet BufferedReader
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int c = 0;
        // Lire caractère par caractère
        int x = 0;
        int y = 0;
        while((c = bufferedReader.read()) != '-')
        {
            char ch = (char) c;
            Objet convert = null;
            ObjetMobile toAddMob = null;
            ObjetDecors toAddFix = null;



            if (ch != '\r') {
                if(ch != '\n') {

                    tab[x][y] = ch;
                    switch (ch) {
                        case '0':

                            toAddFix = new ObjetDecors(typeObjet.PETIT_BONBON);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.nbBonbons += 1;
                            break;

                        case '1':
                            toAddFix = new ObjetDecors(typeObjet.MUR);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            break;
                        case '2':
                            toAddMob = new ObjetMobile(typeObjet.PACMAN, x, y);
                            convert = toAddMob;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.PacMan = toAddMob;
                            break;
                        case '3':
                            toAddMob = new ObjetMobile(typeObjet.FANTOME_ROSE, x, y);
                            convert = toAddMob;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.fantomes[0] = toAddMob;
                            break;
                        case '4':
                            toAddMob = new ObjetMobile(typeObjet.FANTOME_ROUGE, x, y);
                            convert = toAddMob;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.fantomes[1] = toAddMob;
                            break;
                        case '5':
                            toAddMob = new ObjetMobile(typeObjet.FANTOME_BLEU, x, y);
                            convert = toAddMob;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.fantomes[2] = toAddMob;
                            break;
                        case '6':
                            toAddMob = new ObjetMobile(typeObjet.FANTOME_VERT, x, y);
                            convert = toAddMob;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.fantomes[3] = toAddMob;
                            break;
                        case '7':
                            toAddFix = new ObjetDecors(typeObjet.PORTE);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            break;
                        case '8':
                            toAddFix = new ObjetDecors(typeObjet.GROS_BONBON);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            this.nbBonbons += 1;
                            break;
                        case '9':
                            toAddFix = new ObjetDecors(typeObjet.VIDE);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            break;
                        case 'T':
                            toAddFix = new ObjetDecors(typeObjet.TELEPORT);
                            convert = toAddFix;
                            toReturnFix[x][y] = toAddFix;
                            toReturnMob[x][y] = toAddMob;
                            toReturn[x][y] = convert;
                            break;


                        default:
                            System.out.println("ERREUR INITIALISATION" + x + " " + y);
                    }
                    //toReturn[x][y] = convert;
                    y += 1;
                }
            }else{
                y=0;
                x+=1;
            }
        }
        this.grilleJeu = toReturn;
        this.grilleDecors = toReturnFix;
        this.grilleObjetsMobiles = toReturnMob;
    }



    /////////////////// PREVIOUS FUNCTIONS //////////////////////


    public Objet getPacMan(){

        return this.PacMan;
    }





    public Objet contains(int x, int y){

        return grilleJeu[x][y];
    }

    public int[] convertDir(Directions dir) {
        switch (dir) {

            case BAS -> {
                return new int[]{1, 0};
            }
            case HAUT -> {
                return new int[]{-1, 0};
            }
            case GAUCHE -> {
                return new int[]{0, -1};
            }
            case DROITE -> {
                return new int[]{0, 1};
            }
            case NULL -> {
                return new int[]{0,0};
            }
        }
        return null;
    }

    public void printObjetMob(){

        for (int i = 0; i < 22; i++) {

            for (int j = 0; j < 22; j++) {

                if(this.grilleJeu[i][j].getObjetMobile() != null){

                }

            }

        }






    }

    public Directions genDir(){

        Directions[] toReturn = new Directions[4];

        Directions dir = Directions.DROITE;
        toReturn[0] = dir;

        dir = Directions.GAUCHE;
        toReturn[1] = dir;


        dir = Directions.HAUT;
        toReturn[2] = dir;

        dir = Directions.BAS;
        toReturn[3] = dir;


        Random random = new Random();
        int nombreAleatoire = random.nextInt(4);


        return toReturn[nombreAleatoire];


    }


    public void eraseObjetMobiles(){

        for (int i = 0; i < 22; i++) {

            for (int j = 0; j < 22; j++) {

                if(this.grilleJeu[i][j].getObjetMobile() != null){

                    if(this.grilleJeu[i][j].getObjetMobile().getObjetSauvegarde() != null){

                        if (this.grilleJeu[i][j].getObjetMobile().getObjetSauvegarde().getType().equals(typeObjet.PACMAN)){
                            this.grilleJeu[i][j] = new ObjetDecors(typeObjet.VIDE);
                        }else{
                            this.grilleJeu[i][j] = this.grilleJeu[i][j].getObjetMobile().getObjetSauvegarde();
                        }


                    }else {

                        this.grilleJeu[i][j] = new ObjetDecors(typeObjet.VIDE);
                    }
                }

            }

        }


    }




    public void restart(){

        try {
            audio = new Audio(Liens.getCheminSon_death());
            audio.jouer();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        game.getTimeline().pause();
        game.setNombreVies(game.getNombreVies()-1);

        eraseObjetMobiles();





        this.PacMan = new ObjetMobile(typeObjet.PACMAN, 20, 1);

        grilleJeu[20][1] = this.PacMan;

        this.fantomes[0] = new ObjetMobile(typeObjet.FANTOME_ROSE, 6, 11);

        grilleJeu[6][11] = this.fantomes[0];

        this.fantomes[1] = new ObjetMobile(typeObjet.FANTOME_ROUGE, 6, 12);
        grilleJeu[6][12] = this.fantomes[1];

        this.fantomes[2] = new ObjetMobile(typeObjet.FANTOME_BLEU, 6, 9);
        grilleJeu[6][9] = this.fantomes[2];

        this.fantomes[3] =new ObjetMobile(typeObjet.FANTOME_VERT, 6, 10);
        grilleJeu[6][10] = this.fantomes[3];





        game.getTimeline().play();



    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public boolean estUnFruit(Objet Case){
        typeObjet type = Case.getType();

        if(type.equals(typeObjet.BANANE) || type.equals(typeObjet.CERISE) || type.equals(typeObjet.FRAISE) || type.equals(typeObjet.RAISIN) || type.equals(typeObjet.POMME) || type.equals(typeObjet.ORANGE) || type.equals(typeObjet.POIRE)){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean estUnFantome(Objet Case){
        typeObjet type = Case.getType();

        if(type.equals(typeObjet.FANTOME_VERT) || type.equals(typeObjet.FANTOME_BLEU) || type.equals(typeObjet.FANTOME_ROUGE) || type.equals(typeObjet.FANTOME_ROSE)){
            return true;
        }
        else{
            return false;
        }
    }

    public void impact(Objet pacMan, Objet fantome){
        int[] cooPacMan = pacMan.getObjetMobile().getCoordonees();
        int[] cooFantome = fantome.getObjetMobile().getCoordonees();
        ObjetMobile fantomeMob = fantome.getObjetMobile();
        typeObjet typeFantome = fantome.getType();

        if(fantomeMob.getObjetSauvegarde().getType().equals(typeObjet.PETIT_BONBON)){
            game.setScore(game.getScore() + 25);
            this.nbBonbonsManges++;
        }
        else if(fantomeMob.getObjetSauvegarde().getType().equals(typeObjet.GROS_BONBON)){
            game.setScore(game.getScore() + 50);
            this.nbBonbonsManges ++;
        }
        this.grilleJeu[cooFantome[0]][cooFantome[1]] = pacMan;
        pacMan.getObjetMobile().setCoordonees(new int[]{cooFantome[0], cooFantome[1]});
        this.grilleJeu[cooPacMan[0]][cooPacMan[1]] = new ObjetDecors(typeObjet.VIDE);
        initFantome(typeFantome);

        try {
            audio = new Audio(Liens.getCheminSon_eat_ghost());
            audio.jouer();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void initFantome(typeObjet typeFantome){
        switch (typeFantome) {
            case FANTOME_ROSE-> {
                this.fantomes[0] = new ObjetMobile(typeObjet.FANTOME_ROSE,6,11);
                this.grilleJeu[6][11] = this.fantomes[0];
            }
            case FANTOME_ROUGE-> {
                this.fantomes[1] = new ObjetMobile(typeObjet.FANTOME_ROUGE,6,12);
                this.grilleJeu[6][12] = this.fantomes[1];
            }
            case FANTOME_BLEU-> {
                this.fantomes[2] = new ObjetMobile(typeObjet.FANTOME_BLEU,6,9);
                this.grilleJeu[6][9] = this.fantomes[2];
            }
            case FANTOME_VERT -> {
                this.fantomes[3] = new ObjetMobile(typeObjet.FANTOME_VERT,6,10);
                this.grilleJeu[6][10] = this.fantomes[3];
            }
        }
    }

    private int[] checkTP(int[] coordon, int[] aAjouter) {
        if (coordon[1] == 21){
            return new int[]{0,-20};
        }else if (coordon[1] == 0){
            return new int[]{0,20};
        }
        return aAjouter;
    }

    private int serie = 1;

    public boolean deplacerPacman(Directions dir, Objet PACMAN){

        int[] previousCoo = PACMAN.getObjetMobile().getCoordonees();
        int[] aj = convertDir(dir);
        int[] aAjouter = checkTP(previousCoo, aj);

        Objet prochaineCase = contains(previousCoo[0]+aAjouter[0], previousCoo[1]+aAjouter[1]);

        if (prochaineCase.isTraversable()){
            if (estUnFantome(prochaineCase)){
                if (game.isBoosted()){
                    game.setScore(game.getScore() + (200 * serie));
                    serie++;
                    impact(PACMAN, prochaineCase);
                    return true;
                }else {
                    restart();
                    return true;
                }
            }else{
                if (prochaineCase.getType().equals(typeObjet.PETIT_BONBON)){
                    this.nbBonbonsManges++;
                    game.setScore(game.getScore()+25);

                    try {
                        audio = new Audio(Liens.getCheminSon_chomp());
                        audio.jouer();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else if (prochaineCase.getType().equals(typeObjet.GROS_BONBON)){
                    this.nbBonbonsManges++;
                    game.setScore(game.getScore()+50);
                    game.addBoost();
                    try {
                        audio = new Audio(Liens.getCheminSon_eatfruit());
                        audio.jouer();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (estUnFruit(prochaineCase)){
                    game.setScore(game.getScore()+getPointsByFruits(prochaineCase));
                    try {
                        audio = new Audio(Liens.getCheminSon_eatfruit());
                        audio.jouer();
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    } catch (UnsupportedAudioFileException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                this.grilleJeu[previousCoo[0]+aAjouter[0]][previousCoo[1]+aAjouter[1]] = PACMAN;
                PACMAN.getObjetMobile().setCoordonees(new int[]{previousCoo[0]+aAjouter[0],previousCoo[1]+aAjouter[1]});
                this.grilleJeu[previousCoo[0]][previousCoo[1]] = new ObjetDecors(typeObjet.VIDE);
                return true;


            }
        }
        return false;
    }

    public boolean deplacerFantome(Directions dir, Objet Fantome){

        int[] previousCoo = Fantome.getObjetMobile().getCoordonees();
        int[] aj = convertDir(dir);
        int[] aAjouter = checkTP(previousCoo, aj);

        Objet prochaineCase = contains(previousCoo[0]+aAjouter[0], previousCoo[1]+aAjouter[1]);

        if (prochaineCase.isTraversable()){

            if (prochaineCase.getType().equals(typeObjet.PACMAN)){

                if (game.isBoosted()){
                    impact(prochaineCase, Fantome);
                    game.setScore(game.getScore() + (200 * serie));
                    serie++;
                    return true;
                }
                else{
                    restart();
                    return true;
                }
            }else if(estUnFantome(prochaineCase)){
                return false;
            }
            else{


                if (Fantome.getObjetMobile().getObjetSauvegarde().getType().equals(typeObjet.PACMAN)){
                    this.grilleJeu[previousCoo[0]][previousCoo[1]] = new ObjetDecors(typeObjet.VIDE);
                }else{
                    this.grilleJeu[previousCoo[0]][previousCoo[1]] = Fantome.getObjetMobile().getObjetSauvegarde();
                }
                Fantome.getObjetMobile().setObjetSauvegarde(prochaineCase);
                this.grilleJeu[previousCoo[0]+aAjouter[0]][previousCoo[1]+aAjouter[1]] = Fantome;
                Fantome.getObjetMobile().setCoordonees(new int[]{previousCoo[0]+aAjouter[0], previousCoo[1]+aAjouter[1]});


                return true;
            }
        }
        else if (prochaineCase.getType().equals(typeObjet.PORTE) && dir.equals(Directions.HAUT)){
            if (Fantome.getObjetMobile().getObjetSauvegarde().getType().equals(typeObjet.PACMAN)){
                this.grilleJeu[previousCoo[0]][previousCoo[1]] = new ObjetDecors(typeObjet.VIDE);
            }else{
                this.grilleJeu[previousCoo[0]][previousCoo[1]] = Fantome.getObjetMobile().getObjetSauvegarde();
            }
            Fantome.getObjetMobile().setObjetSauvegarde(prochaineCase);
            this.grilleJeu[previousCoo[0]+aAjouter[0]][previousCoo[1]+aAjouter[1]] = Fantome;
            Fantome.getObjetMobile().setCoordonees(new int[]{previousCoo[0]+aAjouter[0], previousCoo[1]+aAjouter[1]});
        }else{

            /*
            if (Fantome.getObjetMobile().getObjetSauvegarde().getType().equals(typeObjet.PACMAN)){
                this.grilleJeu[previousCoo[0]][previousCoo[1]] = new ObjetDecors(typeObjet.VIDE);
            }else{
                this.grilleJeu[previousCoo[0]][previousCoo[1]] = Fantome.getObjetMobile().getObjetSauvegarde();
            }
            Fantome.getObjetMobile().setObjetSauvegarde(prochaineCase);
            this.grilleJeu[previousCoo[0]+aAjouter[0]][previousCoo[1]+aAjouter[1]] = Fantome;
            Fantome.getObjetMobile().setCoordonees(new int[]{previousCoo[0]+aAjouter[0], previousCoo[1]+aAjouter[1]});
             */
            return false;
        }

        return false;
    }


    public int getPointsByFruits(Objet objet){

        typeObjet typeFruit = objet.getType();

        switch (typeFruit){
            case CERISE -> {
                return 200;
            }
            case FRAISE -> {
                return  400;
            }
            case RAISIN -> {
                return 600;
            }
            case POMME -> {
                return 800;
            }
            case ORANGE -> {
                return 1300;
            }
            case BANANE -> {
                return 2500;
            }
            case POIRE -> {
                return 5000;
            }
            default -> {
                System.out.println("ERREUR: nombre de points par type");
                return 0;
            }
        }


    }

    public void genFruit(){

        int niveauActuel = game.getNiveauActuel();

        typeObjet typeToPop = null;

        if ((nbBonbonsManges > (nbBonbons / 2) && fruitPop == false)){
            this.fruitPop = true;

            switch(niveauActuel){
                case 1 -> {
                    typeToPop = typeObjet.CERISE;
                    break;
                }
                case 2 -> {
                    typeToPop = typeObjet.FRAISE;
                    break;
                }
                case 3 -> {
                    typeToPop = typeObjet.RAISIN;
                    break;
                }
                case 4 -> {
                    typeToPop = typeObjet.RAISIN;
                    break;
                }
                case 5 -> {
                    typeToPop = typeObjet.POMME;
                    break;
                }
                case 6 -> {
                    typeToPop = typeObjet.POMME;
                    break;
                }
                case 7 -> {
                    typeToPop = typeObjet.ORANGE;
                    break;
                }
                case 8 -> {
                    typeToPop = typeObjet.ORANGE;
                    break;
                }
                case 9 -> {
                    typeToPop = typeObjet.BANANE;
                    break;
                }
                case 10 -> {
                    typeToPop = typeObjet.BANANE;
                    break;
                }
                default -> {
                    typeToPop = typeObjet.POIRE;
                    break;
                }
            }

            grilleJeu[18][10] = new ObjetDecors(typeToPop);
            System.out.println("Fruit pop:" + typeToPop);
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public Objet[][] getGrilleJeu() {
        return grilleJeu;
    }


    public void setGrilleJeu(Objet[][] grilleJeu) {
        this.grilleJeu = grilleJeu;
    }

    public int getNbBonbons() {
        return nbBonbons;
    }

    public void setNbBonbons(int nbBonbons) {
        this.nbBonbons = nbBonbons;
    }

    public int getNbBonbonsManges() {
        return nbBonbonsManges;
    }

    public void setNbBonbonsManges(int nbBonbonsManges) {
        this.nbBonbonsManges = nbBonbonsManges;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ObjetDecors[][] getGrilleDecors() {
        return grilleDecors;
    }

    public ObjetMobile[][] getGrilleObjetsMobiles() {
        return grilleObjetsMobiles;
    }

    public Objet[] getDernieresCasesSupprimees() {
        return dernieresCasesSupprimees;
    }

    public void setGrilleDecors(ObjetDecors[][] grilleDecors) {
        this.grilleDecors = grilleDecors;
    }

    public void setGrilleObjetsMobiles(ObjetMobile[][] grilleObjetsMobiles) {
        this.grilleObjetsMobiles = grilleObjetsMobiles;
    }

    public void setDernieresCasesSupprimees(Objet[] dernieresCasesSupprimees) {
        this.dernieresCasesSupprimees = dernieresCasesSupprimees;
    }

    public void setPacMan(ObjetMobile pacMan) {
        PacMan = pacMan;
    }

    public ObjetMobile[] getFantomes() {
        return fantomes;
    }

    public void setFantomes(ObjetMobile[] fantomes) {
        this.fantomes = fantomes;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }
}

