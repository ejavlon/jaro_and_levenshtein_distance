package com.company.myapp;

import com.company.myapp.container.ComponentContainer;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class Application extends javafx.application.Application {
    private static ProgressBar progressBar;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("window1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 900);
        stage.setTitle(ComponentContainer.APP_NAME);
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        ComponentContainer.SCENE = scene;
        progressBar = (ProgressBar) scene.lookup("#progresBar");
        startProcess(stage);
    }
    private void startProcess(Stage stage) {

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                int steps = 100;
                updateProgress(0, steps);

                BufferedReader reader = new BufferedReader(new FileReader(ComponentContainer.FILE));
                String str = reader.readLine();

                while (Objects.nonNull(str) && !str.isEmpty()){
                    ComponentContainer.WORDSLIST.add(str);
                    str = reader.readLine();
                }

                for (int i = 0; i < steps; i++) {
                    Thread.sleep(20); // Pause briefly
                    updateProgress(i, steps);
                    updateMessage(String.valueOf(i));
                }
                return null;
            }
        };

        task.setOnFailed(wse -> {
            wse.getSource().getException().printStackTrace();
        });

        task.setOnSucceeded(wse -> {
            try {
                replaceSceneContent(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private Parent replaceSceneContent(Stage stage) throws Exception {
        Parent page = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource("window2.fxml")), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page, 1200, 900);
            stage.setScene(scene);
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }

    public static void main(String[] args) {
        launch(args);
    }
}