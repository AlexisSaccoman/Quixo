package affichage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MenuController {

    @FXML
    private AnchorPane background_menu;

    @FXML
    private Button jvsIA_button;

    @FXML
    private Button jvsj_button;

    @FXML
    private Label titre_menu;

    private String modeJeu;

    @FXML
    void lancer_partie_jvsIA(ActionEvent event) {
        this.modeJeu = "IA";
        startGame(event);
    }

    @FXML
    void lancer_partie_jvsj(ActionEvent event) {
        this.modeJeu = "Humain";
        startGame(event);
    }


    // méthode de lancement d'une partie
    private void startGame(ActionEvent event){
        try {
            // Charger le fichier FXML de la deuxième page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
            Parent root = loader.load();

            // Transmettre la variable au game controller
            GameController gameController = loader.getController();
            gameController.modeJeu = this.modeJeu;

            gameController.initialize();

            // Créer une nouvelle scène avec le contenu de la deuxième page
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Configurer la fenêtre principale
            stage.setScene(scene);
            stage.setResizable(false); // Désactiver la redimension
            stage.setFullScreen(false); // Désactiver le mode plein écran
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}