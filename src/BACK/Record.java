package BACK;

import java.io.*;
import java.nio.file.*;

public class Record {
    public static int nbrRecordMax = 5;

    public Record() {
    }

    /**
     * Créer le fichier, si il n'existe pas
     * @param filePath nom du fichier en .txt
     * @throws IOException
     */
    public void createFileIfNotExists(String filePath)throws IOException{
        File tempFile = new File(filePath);
        if(!tempFile.exists()){
            tempFile.createNewFile();
        }
    }

    /**
     * Suprime le fichier .txt
     * @param filePath nom du fichier en .txt
     */
    public void deleteFile(String filePath){
        File tempFile = new File(filePath);
        if(tempFile.exists()){
            tempFile.delete();
        }
    }

    /**
     * Lit le contenu du fichier .txt
     * @param filePath nom du fichier .txt
     * @return retourne le contenu du fichier
     * @throws IOException
     */
    public String readFile(String filePath)throws IOException{
        String records = "";
        records = new String (Files.readAllBytes(Paths.get(filePath)));


        return records;
    }

    /**
     * Ajoute à la fin du fichier
     * @param filePath nom du fichier .txt
     * @param text contenu à ajouter à la fin du fichier
     * @param sautLigne boolean, true si ajout d'un saut de ligne, false si pas de saut de ligne
     * @throws IOException
     */
    public void writeEndFile(String filePath, String text, Boolean sautLigne)throws IOException{
        String records = readFile(filePath);
        Path file = Path.of(filePath);
        if(sautLigne){
            Files.writeString(file, records + text + "\n");
        }else{
            Files.writeString(file, records + text);
        }

    }

    /**
     * Renvoie le nombre de ligne du fichier .txt
     * @param filePath nom du fichier .txt
     * @return nombre de ligne du fichier .txt
     * @throws IOException
     */
    public int getNumberOfLinesFile(String filePath)throws IOException{
        // lecture du fichier records
        String records = readFile(filePath);
        records = records.replace("\n", ";");
        int nbr = 0;
        for(int i=0; i<records.length(); i++){
            if(records.charAt(i) == ';'){
                nbr++;
            }
        }
        return nbr;
    }

    /**
     * Vérifi si un joueur est enregistré sur le fichier .txt
     * @param filePath nom du fichier .txt
     * @param personne nom du joeur
     * @return retourne true ou false
     * @throws IOException
     */
    public Boolean ifExistsJoueur(String filePath, String personne)throws IOException{
        // lecture du fichier records
        String records = readFile(filePath);
        records = records.replace("\n", ";");

        String string =""; // joueur+record
        String joueur =""; // joueur

        // récupération du début du fichier
        for(int i=0; i<records.length(); i++){
            while(records.charAt(i) != ','){
                joueur += records.charAt(i);
                i++;
            }
            if(joueur.equals(personne)){
                return true;
            }
            joueur = "";
            while(records.charAt(i) != ';'){
                i++;
            }
        }
        return false;
    }

    /**
     * Renvoie le record du joeur
     * @param filePath nom du fichier .txt
     * @param personne nom du joueur
     * @return retourne le record du joueur
     * @throws IOException
     */
    public String getRecord(String filePath, String personne)throws IOException{
        // lecture du fichier records
        String records = readFile(filePath);
        records = records.replace("\n", ";;");

        String string =""; // joueur+record
        String record =""; // record
        String joueur =""; // joueur
        int k;


        for(int i=0; i<records.length(); i++){    // dans le fichier records
            while(records.charAt(i) != ';'){        // séparation d'une ligne
                string += records.charAt(i);
                i++;
            }
            i++;
            string += ';';
            k = 0;
            while(string.charAt(k) != ','){         // séparation du nom du joueur
                joueur += string.charAt(k);
                k++;
            }
            k++;
            if(joueur.equals(personne)){           // séparation du record
                while(string.charAt(k) != ';'){
                    record += string.charAt(k);
                    k++;
                }
                return record;
            }
            string = "";
            record = "";
            joueur = "";
        }
        return "0";
    }

    /**
     * Ajoute le nouveau record au fichier .txt, ou modifi le record du joeur
     * @param filePath nom du fichier .txt
     * @param personne nom du joueur
     * @param record nouveau record
     * @throws IOException
     */
    public void setRecord(String filePath, String personne, String record)throws IOException{
        // lecture du fichier records
        String records = readFile(filePath);
        records = records.replace("\n", ";");

        String stringBefore =""; // apres
        String stringAfter ="";  // avant
        String recordActuel =""; // joueur+record
        String joueur =""; // joueur
        int i = 0;

        if(ifExistsJoueur(filePath, personne)){
            // récupération du début du fichier
            while(! joueur.equals(personne)){
                joueur ="";
                while(records.charAt(i) != ','){
                    joueur += records.charAt(i);
                    i++;
                }
                if(! joueur.equals(personne)){
                    stringAfter += joueur;

                    while(records.charAt(i) != ';'){
                        stringAfter += records.charAt(i);
                        i++;
                    }
                    stringAfter += '\n';
                    i++;
                }
            }

            i++;
            while(records.charAt(i) != ';'){
                recordActuel += records.charAt(i);
                i++;
            }

            if(Integer.parseInt(recordActuel) < Integer.parseInt(record)) {
                // récupération de la fin du fichier
                for (int j = i + 1; j < records.length(); j++) {
                    if (records.charAt(j) == ';') {
                        stringBefore += '\n';
                    } else {
                        stringBefore += records.charAt(j);
                    }

                }

                deleteFile(filePath);
                createFileIfNotExists(filePath);
                writeEndFile(filePath, stringAfter, false);
                writeEndFile(filePath, personne + ',' + record, true);
                writeEndFile(filePath, stringBefore, false);
            }

        }else{
            writeEndFile(filePath, personne+','+record, true);
        }

    }

    /**
     * Retourne un double tableau des 10 meilleur records du fichier .txt
     * @param filePath nom du fichier .txt
     * @return retourne un double tableau de taille 10
     * @throws IOException vérif des erreurs
     */
    public String[][] getMaxRecord(String filePath)throws IOException{
        // lecture du fichier records
        String records = readFile(filePath);
        records = records.replace("\n", ";;");

        int nbr = getNumberOfLinesFile(filePath);
        String string =""; // joueur+record
        String record =""; // record
        String joueur =""; // joueur
        String[][] tableau = new String[nbr][2];
        String[][] tableauTri = new String[nbrRecordMax][2];
        int k;
        int j=0;


        for(int i=0; i<records.length(); i++){    // dans le fichier records
            while(records.charAt(i) != ';'){        // séparation d'une ligne
                string += records.charAt(i);
                i++;
            }
            i++;
            string += ';';
            k = 0;
            while(string.charAt(k) != ','){         // séparation du nom du joueur
                joueur += string.charAt(k);
                k++;
            }
            k++;
            // séparation du record
            while(string.charAt(k) != ';'){
                record += string.charAt(k);
                k++;
            }
            tableau[j][0] = joueur;
            tableau[j][1] = record;
            j++;
            string = "";
            record = "";
            joueur = "";
        }
        tableau = triBulle(tableau, getNumberOfLinesFile(filePath));
        for(int l=0; l<nbrRecordMax; l++){
            tableauTri[l][0] = tableau[l][0];
            tableauTri[l][1] = tableau[l][1];
        }
        return tableauTri;
    }

    /**
     * trie le tableau dans l'ordre croissant
     * @param tab tableau à trier
     * @return retourne un double tableau trié dans l'ordre croissant
     */
    public String[][] triBulle(String[][] tab, int taille){
        String[][] tableau = tab;
        int iMin;
        String min_joueur;
        String min_record;
        int limite;

        for(limite=0; limite<=taille-2; limite++){
            iMin = limite;
            for(int i=limite+1; i<=taille-1 ;i++){
                if(Double.parseDouble(tab[i][1]) > Double.parseDouble(tab[iMin][1])){
                    iMin = i;
                }
            }
            min_joueur = tab[iMin][0];
            min_record = tab[iMin][1];
            tab[iMin][0] = tab[limite][0];
            tab[iMin][1] = tab[limite][1];
            tab[limite][0] = min_joueur;
            tab[limite][1] = min_record;
        }

        return tableau;
    }

}
