package Sales.Dashboard;

import com.jfoenix.controls.JFXDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Dash {

    @FXML
    private Button addItembtn;
    @FXML
    private JFXDrawer drawer;


    @FXML
    private void draweract(ActionEvent evt) throws IOException {
        VBox vbox = FXMLLoader.load(getClass().getResource("Sales/SidePane.fxml"));

        if (drawer.isShown()) {
            drawer.close();
        } else {
            drawer.open();
            drawer.setSidePane(vbox);
        }
    }


    @FXML
    private void AddItemAct(ActionEvent evt) throws IOException {


        Stage stage = new Stage();

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Sales/Item/AddItem.fxml"));
        Loader.load();
        Parent root;
        root = Loader.getRoot();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
