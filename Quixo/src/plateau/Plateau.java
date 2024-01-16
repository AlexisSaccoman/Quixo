package plateau;

import joueur.Joueur;

import java.util.ArrayList;

public class Plateau {
    private ArrayList<ArrayList<Pion>> plateau = new ArrayList();
    private int boardRows = 5;
    private int boardCols = 5;

    private Joueur j1; // signe associé => X
    private Joueur j2; // signe associé => O
    private Joueur tour = j1;

    public int scorej1 = 0;
    public int scorej2 = 0;

    public int[] lastMove;



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
    // méthode principale pour jouer un coup
    public boolean jouerPion(int row, int col, String direction){


        if(row == 0 || row == this.boardRows-1 || col == 0 || col == this.boardCols-1){
            if(this.plateau.get(row).get(col).getState() == "B"){
                jouerCoup(row,col,direction);
                return true;
            }
            if(this.plateau.get(row).get(col).getState() == this.tour.status){
                jouerCoup(row,col,direction);
                return true;
            }
        }

        return false;
    }

    void jouerCoup(int row, int col, String direction){
        // Mettez à jour lastMove avec les coordonnées du dernier mouvement
        lastMove = new int[]{row, col};
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

        // vérifier les lignes
        for (int i = 0; i < boardRows; i++) {
            init = this.plateau.get(i).get(0).getState();
            if (init != "B") {
                for (int j = 1; j < boardCols; j++) {
                    if (this.plateau.get(i).get(j).getState().equals(init)) {
                        if(j == boardCols-1){
                            return this.tour;
                        }
                    }else{
                        break;
                    }
                }
            }
        }


        // vérifier les colonnes
        for (int i = 0; i < boardCols; i++) {
            init = this.plateau.get(0).get(i).getState();
            if (init != "B") {
                for (int j = 1; j < boardRows; j++) {
                    if (this.plateau.get(j).get(i).getState().equals(init)) {
                        if (j == boardRows - 1) {
                            return this.tour;
                        }
                    }else{
                        break;
                    }
                }
            }
        }

        // vérifier la diagonale de gauche à droite
        init = this.plateau.get(0).get(0).getState();
        if (init != "B") {
            for (int i = 1; i < boardCols; i++) {
                if (this.plateau.get(i).get(i).getState().equals(init)) {
                    if(i == boardCols - 1){
                        return this.tour;
                    }
                }else{
                    break;
                }
            }
        }


        // vérifier la diagonale de droite à gauche
        init = this.plateau.get(0).get(boardCols - 1).getState();
        if (init != "B") {
            for (int i = 1; i < boardCols; i++) {
                if (this.plateau.get(i).get(boardCols - i - 1).getState().equals(init)) {
                    if(i == boardCols - 1){
                        return this.tour;
                    }
                }else{
                    break;
                }
            }
        }

        return null;
    }

    //
    public void setScore(Joueur j){
        if(j == this.j1){
            scorej1++;
        }else{
            scorej2++;
        }
    }


    public ArrayList<int[]> getValidMoves() {
        ArrayList<int[]> validMoves = new ArrayList<>();

        // Parcours de la première ligne
        for (int j = 0; j < boardCols; j++) {
            addValidMoveIfValid(0, j, validMoves);
        }

        // Parcours de la première colonne (à l'exception du coin déjà parcouru)
        for (int i = 1; i < boardRows; i++) {
            addValidMoveIfValid(i, 0, validMoves);
        }

        // Parcours de la dernière colonne (à l'exception du coin déjà parcouru)
        for (int i = 1; i < boardRows; i++) {
            addValidMoveIfValid(i, boardCols - 1, validMoves);
        }

        // Parcours de la dernière ligne
        for (int j = 0; j < boardCols; j++) {
            addValidMoveIfValid(boardRows - 1, j, validMoves);
        }

        return validMoves;
    }

    private void addValidMoveIfValid(int row, int col, ArrayList<int[]> validMoves) {
        // Vérifiez si la case est vide ou possède le statut du joueur actuel
        if (this.plateau.get(row).get(col).getState().equals("B") || this.plateau.get(row).get(col).getState().equals(this.tour.status)) {
            // Ajoutez les coordonnées de la case comme coup valide
            int[] move = {row, col};
            validMoves.add(move);
        }
    }

    public Plateau clone() {
        Plateau newBoard = new Plateau(j1, j2);  // Créer une nouvelle instance de Plateau

        for (int i = 0; i < boardRows; i++) {
            ArrayList<Pion> newRow = new ArrayList<>();

            for (int j = 0; j < boardCols; j++) {
                // Utiliser un constructeur de copie dans la classe Case si disponible
                Pion clonedCase = new Pion(this.plateau.get(i).get(j).getState());
                newRow.add(clonedCase);
            }

            newBoard.plateau.add(newRow);
        }

        return newBoard;
    }


    public int[] getMoveFromLastMove() {
        return lastMove;
    }
}
