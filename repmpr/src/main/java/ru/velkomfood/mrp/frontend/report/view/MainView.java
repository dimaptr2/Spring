package ru.velkomfood.mrp.frontend.report.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by dpetrov on 30.06.2016.
 */

public class MainView {

    private Stage primaryStage;
    private GridPane grid;
    private Scene scene;
    private String mainTitle;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
        primaryStage.setTitle(this.mainTitle);
    }

    public void createSceneAndPane() {
        grid = new GridPane();
        scene = new Scene(grid, 300, 500);
        primaryStage.setScene(scene);
    }

    public void showMainWindow() {
        primaryStage.show();
    }

}
