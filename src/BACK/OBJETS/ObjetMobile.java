package BACK.OBJETS;

import BACK.PREDEF.typeObjet;

/*

Comprend pacman et les fantomes


 */

public class ObjetMobile extends Objet{

    private int[] coordonees;

    private Objet objetSauvegarde;

    private boolean boosted;

    public ObjetMobile(typeObjet t, int coordonneeX, int coordoneeY) {
        super(t);
        super.setObjetMobile(this);
        this.coordonees = new int[]{coordonneeX, coordoneeY};
        this.objetSauvegarde = new ObjetDecors(typeObjet.VIDE);
    }

    public ObjetMobile(typeObjet t, int coordonneeX, int coordoneeY, String Image) {
        super(t);
        super.setObjetMobile(this);
        this.coordonees = new int[]{coordonneeX, coordoneeY};
        super.setImage(Image);
    }




    /////////////////////////


    public Objet getObjetSauvegarde() {
        return objetSauvegarde;
    }

    public void setObjetSauvegarde(Objet objetSauvegarde) {
        this.objetSauvegarde = objetSauvegarde;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public void setCoordonees(int[] coordonees) {
        this.coordonees = coordonees;
    }

    public int[] getCoordonees() {
        return coordonees;
    }
}
