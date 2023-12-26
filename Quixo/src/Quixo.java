import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import joueur.Joueur;
import joueur.JoueurHumain;
import plateau.Plateau;

import java.util.ArrayList;

public class Quixo extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("affichage/MenuView.fxml"));
        primaryStage.setTitle("Quixo");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.setResizable(false); // Désactiver la redimension
        primaryStage.setFullScreen(false); // Désactiver le mode plein écran
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
