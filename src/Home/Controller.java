package Home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;

public class Controller {

    ObservableList userlevel = FXCollections.observableArrayList("Admin", "Sales Personnel");

    @FXML
    private ComboBox userlvl;

    @FXML
    private Button loginbtn;

    @FXML
    private TextField usernametext;

    @FXML
    private PasswordField passwordtext;

    @FXML
    private void loginact(ActionEvent evt)throws Exception{

        String username, password, level;
        level=userlvl.getSelectionModel().getSelectedItem().toString();
        username=usernametext.getText();
        password=passwordtext.getText();

        if(level.equals("Sales Personnel")&&username.equals("Test")&&password.equals("Test")){

            Stage stage = new Stage();

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/Home/Sales/SalesScreen.fxml"));
            Loader.load();
            Parent root;
            root = Loader.getRoot();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    }


    public void initialize() {
        userlvl.setItems(userlevel);
        Connection conn = DatabaseConnector.ConnectDb();
    }
}
