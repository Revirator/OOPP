package nl.tudelft.oopp.demo.views;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public abstract class MainView extends Application {

    private DoubleProperty subTitleFontSize = new SimpleDoubleProperty(10);
    private DoubleProperty buttonFontSize = new SimpleDoubleProperty(10);

    /**
     * Makes all font sizes responsive in the UI.
     * @param scene current scene
     */
    public void bindFonts(Scene scene) {

        subTitleFontSize.bind(scene.widthProperty().add(scene.heightProperty()).divide(75));

        buttonFontSize.bind(Bindings.min(12,
                scene.widthProperty().add(scene.heightProperty()).divide(120)));

        Parent root = scene.getRoot();

        // Put the font sizes on all according nodes
        for (Node node : root.lookupAll(".subTitleText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    subTitleFontSize.asString(), ";"));
        }



        for (Node node : root.lookupAll(".buttonText")) {
            node.styleProperty().bind(Bindings.concat("-fx-font-size: ",
                    buttonFontSize.asString(), ";"));
        }

    }


}
