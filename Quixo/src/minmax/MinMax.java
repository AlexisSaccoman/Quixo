// MinMax.java
package minmax;

import plateau.Plateau;

//   ------------------------------- V2 multi-threadé avec un thread par branche  -------------------------------

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MinMax {

    private EvaluationStrategy evaluationStrategy;

    public MinMax(EvaluationStrategy evalStrat) {
        this.evaluationStrategy = evalStrat;
    }

    public int[] getCoordByMinMax(Plateau board) {
        int[] bestMove = new int[]{};
        int bestScore = Integer.MIN_VALUE;

        // Pour chaque coup possible
        for (int[] move : board.getValidMoves()) {
            Plateau newBoard = board.clone();  // Utilisez la méthode clone() pour créer une copie indépendante du plateau
            newBoard.jouerPion(move[0], move[1], "");

            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() ->
                    evaluateMove(newBoard, 2, true)
            );

            try {
                int score = future.get();
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return bestMove;
    }

    private int evaluateMove(Plateau board, int depth, boolean maximizingPlayer) {
        if (depth == 0 || board.isGameWon() != null) {
            // Utilisez votre stratégie d'évaluation spécifique ici
            return this.evaluationStrategy.evaluate(board);
        }

        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Pour chaque coup possible
        for (int[] move : board.getValidMoves()) {
            Plateau newBoard = board.clone();
            newBoard.jouerPion(move[0], move[1], "");

            int score = evaluateMove(newBoard, depth - 1, !maximizingPlayer);

            // Mettez à jour le meilleur score
            if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                bestScore = score;
            }
        }

        return bestScore;
    }
}











/* ------------------------------- V1 sans les threads -------------------------------
public class MinMax {

    private EvaluationStrategy evaluationStrategy;

    public MinMax(EvaluationStrategy evalStrat) {
        this.evaluationStrategy = evalStrat;
    }

    public int[] getCoordByMinMax(Plateau board) {
        return minMax(board, 3, true);
    }

    private int[] minMax(Plateau board, int depth, boolean maximizingPlayer) {
        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] bestMove = null;

        // Pour chaque coup possible
        for (int[] move : board.getValidMoves()) {
            Plateau newBoard = board.clone();  // Utilisez la méthode clone() pour créer une copie indépendante du plateau

            newBoard.jouerPion(move[0], move[1], "");

            int score = evaluateMove(newBoard, depth, maximizingPlayer);

            // Mettez à jour le meilleur coup si le score actuel est meilleur
            if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                bestScore = score;
                bestMove = move;
            }
        }

        return bestMove;
    }


    private int evaluateMove(Plateau board, int depth, boolean maximizingPlayer) {
        if (depth == 0 || board.isGameWon() != null) {
            // Utilisez votre stratégie d'évaluation spécifique ici
            return this.evaluationStrategy.evaluate(board);
        }

        int[] nextMove = minMax(board, depth - 1, !maximizingPlayer);
        Plateau newBoard = board.clone();
        newBoard.jouerPion(nextMove[0], nextMove[1], "");

        return evaluateMove(newBoard, depth - 1, maximizingPlayer);
    }
}
*/
