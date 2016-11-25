package org.iegs.services.books.employees;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.iegs.services.books.employees.config.MyConfigurator;
import org.iegs.services.books.employees.controller.DatabaseEngine;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by petrovdmitry on 13.08.16.
 */
public class MainForm extends Application {

    private MyConfigurator configurator;
    private DatabaseEngine dbEngine;
    private String pathToDatabase;
    private Text actionTarget;
    private Stage window;

    // Main section

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void init() {

        configurator = MyConfigurator.getInstance();
        dbEngine = configurator.databaseEngine();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        window.setTitle("Справочник телефонов");

        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20, 0, 20, 20));

        // Top panel with the buttons
        HBox hBox1 = addHBox();
        border.setTop(hBox1);

        // Left panel with the hyper links
        VBox vBox = addVBox();
        border.setLeft(vBox);

        // Bottom panel with the indicators
        HBox hBox2 = addBottomBox();
        border.setBottom(hBox2);

        border.setCenter(addGridPane());

        Scene scene = new Scene(border);

        window.setScene(scene);
        window.show();

    }

    @Override
    public void stop() {

        try {

            if (dbEngine.getConnection() != null
                    && !dbEngine.getConnection().isClosed()) {

                dbEngine.getConnection().close();

            }

        } catch (SQLException sqex) {

            sqex.printStackTrace();

        }

    }



    // End of main section

    // Add controls

    public HBox addHBox() {

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setMaxWidth(250);
        hbox.setStyle("-fx-background-color: #696969;");

        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);

        Button btnCreate = new Button("Создать БД");
        btnCreate.setPrefSize(100, 20);

        btnCreate.addEventHandler(MouseEvent.MOUSE_MOVED,
                event -> btnCreate.setEffect(dropShadow));

        btnCreate.setOnAction(event -> chooseDatabaseDirectory(window));

        Button btnCancel = new Button("Выход");
        btnCancel.setPrefSize(100, 20);

        btnCancel.addEventHandler(MouseEvent.MOUSE_MOVED,
                event -> btnCancel.setEffect(dropShadow));

        btnCancel.setOnAction(event -> closeProgram());

        hbox.getChildren().addAll(btnCreate, btnCancel);

        return hbox;

    }


    public VBox addVBox() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Обработка данных");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Hyperlink hrefCreate = new Hyperlink("Создать");
        Hyperlink hrefChange = new Hyperlink("Изменить");
        Hyperlink hrefRead = new Hyperlink("Просмотреть");
        Hyperlink hrefReport = new Hyperlink("Отчет");


        // Events for hyperlinks selection
        hrefCreate.setOnAction(event -> createItem());

        Hyperlink options[] = new Hyperlink[] {
            hrefCreate, hrefChange, hrefRead, hrefReport,
        };


        for (int i=0; i < 4; i++) {
            VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
            vbox.getChildren().add(options[i]);
        }

        return vbox;
    }

    public HBox addBottomBox() {
        
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setMaxWidth(250);
        hBox.setStyle("-fx-background-color: #696969;");

        actionTarget = new Text();
        hBox.getChildren().add(actionTarget);

        return hBox;
    }

    // Add main area
    public GridPane addGridPane() {

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

//        Text category = new Text("Список сотрудников");
//        category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        grid.add(category, 3, 0);

        return grid;
    }

    // end of controls section

    // Event handlers
    
    // Open dialog for the database selection

    private void closeProgram() {
        window.close();
    }

    // Open dialog for database choosing
    private void chooseDatabaseDirectory(Stage primaryStage) {

        DirectoryChooser dirChooser = new DirectoryChooser();

        File selectedDirectory = dirChooser.showDialog(primaryStage);

        if (selectedDirectory == null) {

            tellAboutThis(Color.RED, "Каталог не определен");

        } else {

            pathToDatabase = selectedDirectory.getAbsolutePath();
            initDatabaseConnection(pathToDatabase);

        }

    }

    // Get the connection to the SQLite database

    private void initDatabaseConnection(String path) {

        dbEngine.setPathTo(path);

        try {

            dbEngine.createConnection();
            tellAboutThis(Color.ALICEBLUE, "База данных: " + dbEngine.getDBNAME());

        } catch (SQLException sqe) {

            tellAboutThis(Color.RED, sqe.getMessage());

        }

    }

    // Hyper link event

    private void createItem() {

    }

    private void changeItem() {

    }

    private void readItem() {

    }

    private void executeReport() {

    }

    private void tellAboutThis(Color colOR, String message) {

        actionTarget.setFill(colOR);
        actionTarget.setText(message);

    }

    // end of event handlers section

}
