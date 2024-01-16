package joueur;

import minmax.EvaluationRandom;
import minmax.EvaluationSmart;
import minmax.EvaluationStrategy;
import minmax.MinMax;
import plateau.Plateau;

import java.util.Random;

public class JoueurIA extends Joueur{

    public JoueurIA(String n, String s) {
        super(n,s);
    }


    public int[] getRandomMove(Plateau board){ // méthode qui calcule le meilleur coup avec la Random way

        // on instancie l'objet de la calsse MinMax qui va nous permettre de calculer le meilleur coup
        EvaluationStrategy evalStrat = new EvaluationSmart();
        MinMax minMax = new MinMax(evalStrat);

        // utilisation du MinMax pour return les coordonnées du bon coup calculé
        return minMax.getCoordByMinMax(board);
    }


    public int[] getSmarterMove(Plateau board){ // méthode qui calcule le meilleur coup avec la Smart way

        // on instancie l'objet de la calsse MinMax qui va nous permettre de calculer le meilleur coup
        EvaluationStrategy evalStrat = new EvaluationSmart();
        MinMax minMax = new MinMax(evalStrat);

        // utilisation du MinMax pour return les coordonnées du coup
        minMax.getCoordByMinMax(board);

        return new int[]{0,0};
    }

    public String getDirection(){
        String[] dir = {"haut", "bas", "gauche", "droite"};
        Random random = new Random();
        return dir[random.nextInt(3)];
    }
}
