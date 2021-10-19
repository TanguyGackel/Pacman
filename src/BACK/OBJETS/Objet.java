package BACK.OBJETS;

import BACK.PREDEF.typeObjet;
import BACK.PREDEF.Liens;


public class Objet {

    private typeObjet type;

    private String image;

    private boolean isTraversable; // true si oui false si non

    ObjetMobile objetMobile;

    public Objet(typeObjet t){

        this.type = t;
        initObjet();
    }

    public Objet(){}

    public void initObjet(){

        switch (this.type){
            case MUR -> {

                this.image = Liens.getCheminMur();
                this.isTraversable = false;
                break;
            }

            case VIDE -> {
                this.image = Liens.getCheminFondNoir();
                this.isTraversable = true;
                break;
            }

            case PACMAN -> {
                this.image = Liens.getCheminPacManDroite();
                this.isTraversable = true;
                break;
            }
            case PORTE -> {
                this.image = Liens.getCheminPorteClosed();
                this.isTraversable = false;
                break;
            }

            case FANTOME_BLEU -> {
                this.image = Liens.getCheminFantomeBleu();
                this.isTraversable = true;
                break;
            }
            case FANTOME_ROSE -> {
                this.image = Liens.getCheminFantomeRose();
                this.isTraversable = true;
                break;
            }
            case FANTOME_ROUGE -> {
                this.image = Liens.getCheminFantomeRouge();
                this.isTraversable = true;
                break;
            }
            case FANTOME_VERT -> {
                this.image = Liens.getCheminFantomeVert();
                this.isTraversable = true;
                break;
            }
            case PETIT_BONBON -> {
                this.image = Liens.getCheminPetitBonbon();
                this.isTraversable = true;
                break;
            }
            case GROS_BONBON -> {
                this.image = Liens.getCheminGrosBonbon();
                this.isTraversable = true;
                break;
            }
            case CERISE -> {
                this.image = Liens.getCheminCerise();
                this.isTraversable = true;
                break;
            }
            case FRAISE -> {
                this.image = Liens.getCheminFraise();
                this.isTraversable = true;
                break;
            }
            case RAISIN -> {
                this.image = Liens.getCheminRaisin();
                this.isTraversable = true;
                break;
            }
            case POMME -> {
                this.image = Liens.getCheminPomme();
                this.isTraversable = true;
                break;
            }
            case ORANGE -> {
                this.image = Liens.getCheminOrange();
                this.isTraversable = true;
                break;
            }
            case BANANE-> {
                this.image = Liens.getCheminBanane();
                this.isTraversable = true;
                break;
            }
            case POIRE -> {
                this.image = Liens.getCheminPoire();
                this.isTraversable = true;
                break;
            }
            default ->
                    System.out.println("Erreur Initialisation Objet " + this.type);
        }



    }

    ///////////////////////////////////


    public typeObjet getType() {
        return type;
    }

    public void setType(typeObjet type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isTraversable() {
        return isTraversable;
    }

    public void setTraversable(boolean traversable) {
        isTraversable = traversable;
    }

    public ObjetMobile getObjetMobile() {
        return objetMobile;
    }

    public void setObjetMobile(ObjetMobile objetMobile) {
        this.objetMobile = objetMobile;
    }
}
