package plateau;

import joueur.Joueur;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Plateau {
    private ArrayList<ArrayList<Pion>> plateau = new ArrayList();
    private int boardRows = 5;
    private int boardCols = 5;

    private Joueur j1; // signe associé => X
    private Joueur j2; // signe associé => O
    private Joueur tour = j1;



    public Plateau(Joueur tj1, Joueur tj2){
        init();
        this.j1 = tj1;
        this.j2 = tj2;
        this.tour = j1;
    }

    void init(){
        this.plateau.clear();
        for(int i=0; i<5; i++){
            ArrayList<Pion> row = new ArrayList();
            for(int j=0; j<5; j++){
                row.add(new Pion());
            }
            this.plateau.add(row);
        }
    }

    // actions de jeu

    public boolean jouerPion(int row, int col, String direction){

        if(this.plateau.get(row).get(col).getState() == "B"){
            if(row == 0 || row == this.boardRows-1 || col == 0 || col == this.boardCols-1){
                jouerCoup(row,col,direction);
                setTour();
                return true;
            }
        }
        return false;
    }

    void jouerCoup(int row, int col, String direction){
        if(this.tour == j1){
            this.plateau.get(row).get(col).setStateX();
            moove(row, col, direction);
        }else if(this.tour == j2){
            this.plateau.get(row).get(col).setStateO();
            moove(row, col, direction);
        }
    }

    // mouvements plateau

    void moove(int row, int col, String direction){
        // choisis l'action en fonction de la direction
        switch (direction) {
            case "haut":
                pousserHaut(col);
                break;

            case "bas":
                pousserBas(col);
                break;

            case "gauche":
                pousserGauche(row);
                break;

            case "droite":
                pousserDroite(row);
                break;
        }

    }


    boolean pousserHaut(int col){
        Pion tmp = new Pion();
        tmp = getPion(0,col);
        for(int i=0; i<4; i++){
            this.plateau.get(i).set(col, this.plateau.get(i+1).get(col));
        }
        this.plateau.get(boardRows-1).set(col, tmp);
        return true;
    }

    boolean pousserBas(int col) {
        Pion tmp = getPion(boardRows - 1, col);

        for (int i = boardRows - 1; i > 0; i--) {
            this.plateau.get(i).set(col, this.plateau.get(i - 1).get(col));
        }

        this.plateau.get(0).set(col, tmp);
        return true;
    }


    boolean pousserGauche(int row) {
        Pion tmp = getPion(row, 0);

        for (int i = 0; i < boardCols - 1; i++) {
            this.plateau.get(row).set(i, this.plateau.get(row).get(i + 1));
        }

        this.plateau.get(row).set(boardCols - 1, tmp);
        return true;
    }


    boolean pousserDroite(int row) {
        Pion tmp = getPion(row, boardCols - 1);

        for (int i = boardCols - 1; i > 0; i--) {
            this.plateau.get(row).set(i, this.plateau.get(row).get(i - 1));
        }

        this.plateau.get(row).set(0, tmp);
        return true;
    }



    // getters & setters

    public void setTour() {
        if(this.tour == j1){
            this.tour = j2;
        }else if(this.tour == j2){
            this.tour = j1;
        }else if(this.tour == null){
            this.tour = j1;
        }
    }

    public Pion getPion(int i, int j){
        return this.plateau.get(i).get(j);
    }

    public void display(){
        System.out.println("#####");
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                System.out.print(this.plateau.get(i).get(j).getState());
            }
            System.out.println(" ");
        }
        System.out.println("#####");
    }


    public Joueur getTour() {
        return tour;
    }

    // méthode qui vérifie si un des joueurs a gagné (si 5 pionts sont alignés)
    public Joueur isGameWon() {
        String init;

        // vérifier les lignes - V
        for (int i = 0; i < boardCols; i++) {
            init = this.plateau.get(i).get(0).getState();
            for (int j = 0; j < boardCols-1; j++) {
                if(this.plateau.get(i).get(j+1).getState() == init){
                    if(j == boardCols-1){
                        return this.tour;
                    }
                }
            }
        }

        // vérifier les colonnes - X
        for (int i = 0; i < boardCols; i++) {
            init = this.plateau.get(0).get(i).getState();
            for (int j = 0; j < boardCols-1; j++) {
                if(this.plateau.get(j+1).get(i).getState() == init){
                    if(j == boardCols-1){
                        return this.tour;
                    }
                }
            }
        }

        // vérifier la 1ère diagonale - X
        for (int i = 0; i < boardCols; i++) {
            init = this.plateau.get(0).get(i).getState();
            for (int j = 0; j < boardCols-1; j++) {
                if(this.plateau.get(j+1).get(i).getState() == init){
                    if(j == boardCols-1){
                        return this.tour;
                    }
                }
            }
        }

        // vérifier la 2ème diagonale - X

        return null;

    }

}
