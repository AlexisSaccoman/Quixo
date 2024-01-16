package affichage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import joueur.Joueur;
import joueur.JoueurHumain;
import joueur.JoueurIA;
import plateau.Plateau;

import java.io.IOException;




public class GameController {

    /*
        sélectionner les sprites en fonction
    */
    private String spriteDefault = "./sprites/Blanc.png";
    private String spriteJ1 = "./sprites/res/pion.png";
    private String spriteJ2 = "./sprites/res/pion(2).png";

    private int scoreJ1 = 0;
    private int scoreJ2 = 0;

    JoueurHumain j1 = new JoueurHumain("Joueur 1", "X");
    Joueur j2;

    Plateau board;

    public String modeJeu = "Humain";


    @FXML
    private Button abandonner_button;

    @FXML
    private Pane back_to_menu;

    @FXML
    private AnchorPane background_game;

    @FXML
    private AnchorPane background_score_j1;

    @FXML
    private AnchorPane background_score_j2;

    @FXML
    private GridPane board_affichage;

    @FXML
    private Label j1_titre;

    @FXML
    private Label j2_titre;

    @FXML
    private Button nouvellePartie_button;

    @FXML
    private Label score_j1;

    @FXML
    private Label score_j2;

    @FXML
    private Label tour_joueur;

    @FXML
    void abandonner(ActionEvent event) {
        Joueur j = board.getTour();
        if(j == j1){
            score_j2.setText(Integer.toString(this.scoreJ2+1));
        }else{
            score_j1.setText(Integer.toString(this.scoreJ1+1));
        }
        board = new Plateau(j1, j2);
        init();
    }



    @FXML
    void goBackToMenu(MouseEvent event) {
        try {
            // Charger le fichier FXML de la fenêtre du menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec le contenu du menu
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle à partir de l'événement
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Fermer la scène actuelle (fenêtre actuelle)
            currentStage.close();

            // Afficher la nouvelle scène (fenêtre du menu)
            Stage menuStage = new Stage();
            menuStage.setScene(scene);
            menuStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void nouvellePartie(ActionEvent event) {
        board = new Plateau(j1, j2);
        init();
    }

    void lancerPartie() {

    }

    void start(){

    }

    @FXML
    public void initialize() {
        // on si on est dans humain vs Ia on doit avoir j2 de type JoueurIA sinon on a j2 de type JoueurHumain
        if(this.modeJeu == "Humain"){
            j2 = new JoueurHumain("Joueur 2", "O");
        }else{
            j2 = new JoueurIA("Joueur 2", "O");
        }
        board = new Plateau(j1, j2);
        init();
    }

    // Initialiser le tableau avec des sprites blancs
    private void init() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                // Charger l'image du sprite blanc (ajustez le chemin d'accès en fonction de votre projet)
                Image spriteBlanc = new Image(getClass().getResourceAsStream(this.spriteDefault));

                // Créer un ImageView avec l'image du sprite blanc
                ImageView imageView = new ImageView(spriteBlanc);

                // Définir fitWidth et fitHeight (ajustez les valeurs en fonction de vos besoins)
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);

                // Créer un StackPane pour centrer l'ImageView dans la cellule
                StackPane stackPane = new StackPane(imageView);
                stackPane.setAlignment(Pos.CENTER);
                stackPane.setOnMouseClicked(this::jouerCoup);

                // Ajouter le StackPane à la grille
                board_affichage.add(stackPane, col, row);
            }
        }
    }



    @FXML
    public void jouerCoup(MouseEvent event) {

        jouerCoupHumain(event);

    }

    // méthode qui permet de jouer un coup (fait appel aux différentes méthodes de la classe Plateau)
    public void jouerCoupHumain(MouseEvent event){
        // Récupérer la source de l'événement (la case cliquée)
        StackPane clickedCell = (StackPane) event.getSource();

        // Récupérer les indices de la case cliquée dans la grille
        int row = GridPane.getRowIndex(clickedCell);
        int col = GridPane.getColumnIndex(clickedCell);

        Joueur j = board.getTour();

        // on gère la direction
        String direction = selectionnerDirection();

        boolean verif = board.jouerPion(row, col, direction);

        if (verif) {
            updateGraphic();
            Joueur gagnant = board.isGameWon();
            if (gagnant != null) {
                // La partie est gagnée, mettre à jour le score
                board.setScore(gagnant);
                this.scoreJ1 += board.scorej1;
                this.scoreJ2 += board.scorej2;
                if (gagnant.equals(j1)) {
                    score_j1.setText(Integer.toString(this.scoreJ1));
                } else if (gagnant.equals(j2)) {
                    score_j2.setText(Integer.toString(this.scoreJ2));
                }

                // on remet la grille à 0
                board = new Plateau(j1, j2);
                init();
            }else{
                board.setTour();
                tour_joueur.setText(board.getTour().name);

                // Gestion du cas si c'est l'IA qui joue
                if(j2 instanceof JoueurIA){
                    jouerCoupIA((JoueurIA) this.j2);
                }
            }
        }
    }

    public void jouerCoupIA(JoueurIA j2){


        int[] coord = j2.getRandomMove(board);
        //int[] coord = j2.getSmarterMove()
        String direction = j2.getDirection();

        Joueur j = board.getTour();

        boolean verif = board.jouerPion(coord[0], coord[1], direction);

        if (verif) {
            updateGraphic();
            Joueur gagnant = board.isGameWon();
            if (gagnant != null) {
                // La partie est gagnée, mettre à jour le score
                board.setScore(gagnant);
                this.scoreJ1 += board.scorej1;
                this.scoreJ2 += board.scorej2;
                if (gagnant.equals(j1)) {
                    score_j1.setText(Integer.toString(this.scoreJ1));
                } else if (gagnant.equals(j2)) {
                    score_j2.setText(Integer.toString(this.scoreJ2));
                }

                // on remet la grille à 0
                board = new Plateau(j1, j2);
                init();
            }else{
                board.setTour();
                tour_joueur.setText(board.getTour().name);
            }
        }
    }


    @FXML
    public String selectionnerDirection() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Sélection de Direction");
        primaryStage.initStyle(StageStyle.UTILITY);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        // Créer des boutons avec des flèches de direction
        Button btnUp = createDirectionButton("↑", "haut", primaryStage);
        Button btnDown = createDirectionButton("↓", "bas", primaryStage);
        Button btnLeft = createDirectionButton("←", "gauche", primaryStage);
        Button btnRight = createDirectionButton("→", "droite", primaryStage);

        // Ajouter les boutons à la VBox
        root.getChildren().addAll(btnUp, btnDown, btnLeft, btnRight);

        // Définir la scène de la fenêtre principale
        Scene scene = new Scene(root, 150, 300);
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.showAndWait(); // Utilisez showAndWait() pour bloquer l'exécution jusqu'à ce que la fenêtre soit fermée

        // on gère la sélection de la direction
        return selectedDirection;
    }

    private String selectedDirection;

    private Button createDirectionButton(String text, String direction, Stage stage) {
        Button button = new Button(text);
        button.setMinSize(60, 60); // Définir la taille du bouton

        // Ajouter un gestionnaire d'événements pour le clic sur le bouton
        button.setOnAction(event -> {
            selectedDirection = direction;
            stage.close(); // Fermer la fenêtre après la sélection
        });

        return button;
    }

    // méthode updateGraphic qui met à jour l'affichage du tableau en fonction du contenu de chaque case de l'objet board

    public void updateGraphic(){
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                // Charger l'image du sprite blanc (ajustez le chemin d'accès en fonction de votre projet)
                Image spriteBlanc = new Image(getClass().getResourceAsStream(this.spriteDefault));
                Image spriteJ1 = new Image(getClass().getResourceAsStream(this.spriteJ1));
                Image spriteJ2 = new Image(getClass().getResourceAsStream(this.spriteJ2));

                Image sprite = null;
                if(board.getPion(row, col).getState() == "B"){
                    sprite = spriteBlanc;
                }else if(board.getPion(row, col).getState() == "X"){
                    sprite = spriteJ1;
                }else if(board.getPion(row, col).getState() == "O"){
                    sprite = spriteJ2;
                }


                // Créer un ImageView avec l'image du sprite blanc
                ImageView imageView = new ImageView(sprite);

                // Définir fitWidth et fitHeight (ajustez les valeurs en fonction de vos besoins)
                imageView.setFitWidth(80);
                imageView.setFitHeight(80);

                // Créer un StackPane pour centrer l'ImageView dans la cellule
                StackPane stackPane = new StackPane(imageView);
                stackPane.setAlignment(Pos.CENTER);
                stackPane.setOnMouseClicked(this::jouerCoup);

                // Ajouter le StackPane à la grille
                board_affichage.add(stackPane, col, row);

            }
        }
    }



}

