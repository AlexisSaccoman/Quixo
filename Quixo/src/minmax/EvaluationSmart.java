// EvaluationSmart.java
package minmax;

import plateau.Pion;
import plateau.Plateau;

import java.util.ArrayList;

public class EvaluationSmart implements EvaluationStrategy {

    private int[][] scoreMoves = {
            {3, 2, 2, 2, 3},
            {2, 3, 1, 3, 2},
            {2, 1, 4, 1, 2},
            {2, 3, 1, 3, 2},
            {3, 2, 2, 2, 3}
    };

    @Override
    public int evaluate(Plateau board) {
        // Obtenez la liste des coups valides
        ArrayList<int[]> validMoves = board.getValidMoves();

        if (!validMoves.isEmpty()) {
            // Choisissez le coup le plus intéressant parmi les coups valides
            int[] bestMove = null;
            int bestScore = Integer.MIN_VALUE;

            for (int[] move : validMoves) {
                int x = move[0];
                int y = move[1];
                int score = scoreMoves[x][y];

                // Vous pouvez ajouter d'autres critères d'évaluation ici

                // Ajoutez la valeur pondérée de la position en fonction de la grille de score
                int positionScore = score * someFunction(board.getPion(x, y));
                if (positionScore > bestScore) {
                    bestScore = positionScore;
                    bestMove = move;
                }
            }

            // Renvoyez une évaluation basée sur le meilleur coup trouvé
            return bestScore;
        } else {
            // Aucun coup valide disponible
            return 0;
        }
    }

    private int someFunction(Pion pion) {
        // Implémentez votre propre logique pour évaluer le pion
        return 0;
    }
}
