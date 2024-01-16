package minmax;

import plateau.Plateau;

import java.util.ArrayList;
import java.util.Random;

public class EvaluationRandom implements EvaluationStrategy{

    @Override
    public int evaluate(Plateau board) {
        /*
        // Obtenez la liste des coups valides
        ArrayList<int[]> validMoves = board.getValidMoves();

        if (!validMoves.isEmpty()) {
            // Choisissez un coup au hasard parmi les coups valides
            Random random = new Random();
            int randomIndex = random.nextInt(validMoves.size());
            return validMoves.get(randomIndex);
        } else {
            // Aucun coup valide disponible
            return null;
        }*/
        return 0;
    }


}
