package BACK;

import BACK.PREDEF.Directions;

import java.util.ArrayList;

public class Noeud {

    private int x;
    private int y;
    private int g;
    private int h;
    private int f;
    private String statut;
    private Noeud parent;

    public Noeud(int x, int y, String statut)
    {
        this.x = x;
        this.y = y;
        this.statut = statut;
    }

    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public int getG(){return this.g;}
    public int getH() {
        return h;
    }
    public int getF(){return this.f;}
    public String getStatut(){return this.statut;}
    public Noeud getParent(){return this.parent;}

    public void setParent(Noeud noeud){this.parent = noeud;}
    public void setStatut(String statut){this.statut = statut;}
    public void setG(int g){this.g = g;}
    public void setH(int h){this.h = h;}
    public void setF(int f){this.f = f;}
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    public static ArrayList<Directions> conversionNoeudToDirection(ArrayList<Noeud> list){
        ArrayList<Directions> toReturn = new ArrayList<>();

        for(int i = list.size()-1; i > 0; i--){
            if(list.get(i) != null && list.get(i-1) != null){
                if(list.get(i).getX()+1 == list.get(i-1).getX()){
                    toReturn.add(Directions.BAS);
                }
                if(list.get(i).getX()-1 == list.get(i-1).getX()){
                    toReturn.add(Directions.HAUT);
                }
                if(list.get(i).getY()+1 == list.get(i-1).getY()){
                    toReturn.add(Directions.DROITE);
                }
                if(list.get(i).getY()-1 == list.get(i-1).getY()){
                    toReturn.add(Directions.GAUCHE);
                }
            }
        }


        return toReturn;
    }

    public static Noeud linearSearch(ArrayList<Noeud> tab, Noeud noeud){
        for(int i = 0; i < tab.size(); i++){
            if(tab.get(i).equals(noeud.getParent()))
            {
                tab.remove(noeud.getParent());
                return noeud.getParent();
            }
        }
        return null;
    }

    public static ArrayList<Noeud> addNeighbors(Noeud noeud, Noeud[][] tab){
        ArrayList<Noeud> toReturn = new ArrayList<>();
        if(noeud.getX() < 21 && noeud.getX() > 0 && noeud.getY() < 21 && noeud.getY() > 0) {
            if (tab[noeud.getX() + 1][noeud.getY()].getStatut().equals("o") || tab[noeud.getX() + 1][noeud.getY()].getStatut().equals("p") && !tab[noeud.getX()+1][noeud.getY()].equals(noeud.getParent())) {
                toReturn.add(tab[noeud.getX() + 1][noeud.getY()]);
            }
            if (tab[noeud.getX()][noeud.getY() + 1].getStatut().equals("o") || tab[noeud.getX()][noeud.getY() + 1].getStatut().equals("p")&& !tab[noeud.getX()][noeud.getY()+1].equals(noeud.getParent())) {
                toReturn.add(tab[noeud.getX()][noeud.getY() + 1]);
            }
            if (tab[noeud.getX() - 1][noeud.getY()].getStatut().equals("o") || tab[noeud.getX() - 1][noeud.getY()].getStatut().equals("p")&& !tab[noeud.getX()-1][noeud.getY()].equals(noeud.getParent())) {
                toReturn.add(tab[noeud.getX() - 1][noeud.getY()]);
            }
            if (tab[noeud.getX()][noeud.getY() - 1].getStatut().equals("o") || tab[noeud.getX()][noeud.getY() - 1].getStatut().equals("p")&& !tab[noeud.getX()][noeud.getY()-1].equals(noeud.getParent())) {
                toReturn.add(tab[noeud.getX()][noeud.getY() - 1]);
            }
        }



        return toReturn;
    }

    public static Noeud lowestF(ArrayList<Noeud> list){
        Noeud toReturn = null;
        for (Noeud noeud : list) {
            if (toReturn == null || toReturn.getF() > noeud.getF()) {
                toReturn = noeud;
            }
        }
        return toReturn;
    }

    public static int manhattan(int a, int b, int c, int d){
        a -= b;
        if(a < 0)
            a *= -1;
        c -= d;
        if(c < 0)
            c *= -1;
        return a+c;
    }

    public static ArrayList<Directions> aStar(Noeud[][] tab, Noeud start, Noeud goal){

        ArrayList<Noeud> open = new ArrayList<>();
        ArrayList<Noeud> closed = new ArrayList<>();
        ArrayList<Noeud> neighbors;
        ArrayList<Noeud> chemin = new ArrayList<>();

        int n = 0;

        open.add(start);

        boolean cheminEstTrouve = false;

        while(!cheminEstTrouve && n < 500){
            n++;
            if(open.isEmpty()){
                break;
            }

            Noeud current = lowestF(open);
            open.remove(current);

            neighbors = addNeighbors(current, tab);

            for(Noeud noeud : neighbors){
                if(noeud == goal || n >= 500){
                    noeud.setParent(current);
                    noeud.setStatut("p");
                    closed.add(noeud);
                    chemin.add(noeud);
                    cheminEstTrouve = true;
                    break;
                }
                if(!closed.contains(noeud)){
                    noeud.setParent(current);
                    noeud.setG(current.getG()+1);
                    noeud.setH(manhattan(noeud.getX(), goal.getX(), noeud.getY(), goal.getY()));
                    noeud.setF(noeud.getG() + noeud.getH());
                    open.add(noeud);
                }
            }
            closed.add(current);

        }

        if(chemin.size() != 0)
        {
            for(int i = 0; i < closed.size(); i++){
                chemin.add(linearSearch(closed, chemin.get(i)));
                if(chemin.get(i+1) == null)
                {

                    break;
                }
                if(chemin.get(i+1).getStatut().equals(start.statut))
                {
                    break;
                }

            }
            return conversionNoeudToDirection(chemin);
        }

        return null;



    }
}