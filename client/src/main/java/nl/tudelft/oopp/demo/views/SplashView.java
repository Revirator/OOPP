package nl.tudelft.oopp.demo.views;

import java.io.IOException;
import java.net.URL;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SplashView extends MainView {

    /*
     * Font sizes for splash screen.
     */
    private DoubleProperty preBoxFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty boxFontSize = new SimpleDoubleProperty(10);

    /**
     * Creates the splash screen scene and loads it on the primary stage.
     * @param primaryStage primary stage of the app
     * @throws IOException if FXMLLoader fails to load the url
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Load the splash file
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/splash.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        // Create new scene with splash root
        Scene scene = new Scene(root);

        // Set scene on primary stage
        primaryStage.setScene(scene);
        AnchorPane anchorPane = (AnchorPane) root.lookup("#anchor");
        anchorPane.requestFocus();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        primaryStage.show();

        bindFonts(scene);
    }

    /**
     * Makes all font sizes responsive in the UI.
     * @param scene current scene
     */
    @Override
    public void bindFonts(Scene scene) {

        // Bind font sizes to screen size
        preBoxFontSize.bind(Bindings.min(20,
                scene.widthProperty().add(scene.heightProperty()).divide(85)));

        boxFontSize.bind(Bindings.min(15,
                scene.widthProperty().add(scene.heightProperty()).divide(100)));

        // Bind shared fonts
        super.bindFonts(scene);

        Parent root = scene.getRoot();

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".preBoxText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    preBoxFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".boxText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    boxFontSize.asString(), ";"));
        }

        for (Node node : root.lookupAll(".createRoom")) {
            node.styleProperty().bind(Bindings.concat("-fx-background-color: #f1be3e;"));
        }

        for (Node node : root.lookupAll(".enterRoom")) {
            node.styleProperty().bind(Bindings.concat("-fx-background-color: #adeaf7;"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
