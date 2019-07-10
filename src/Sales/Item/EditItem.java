package Sales.Item;

import Sales.DatabaseConnector;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditItem {
    Connection conn = null;
    PreparedStatement pst = null;

    ResultSet rs = null;


    @FXML
    private JFXTextField Itemidtext;
    @FXML
    private Label itemnamelabel;

    @FXML
    private Label itembrandlabel;

    @FXML
    private JFXTextField itempricefield;

    @FXML
    private JFXTextField Itemquantityfield;

    @FXML
    private JFXButton updatebtn;

    @FXML
    private JFXButton deletebtn;

    @FXML
    private Button searchbtn;

    @FXML
    void searchact(ActionEvent event) throws SQLException {
        String ItemId = Itemidtext.getText();

        String sql = "Select * from Items where ItemId = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, ItemId);
        rs = pst.executeQuery();

        if (rs.next()) {

            itemnamelabel.setText(rs.getString("itemName"));
            itembrandlabel.setText(rs.getString("BrandName"));
            itempricefield.setText(rs.getString("Price"));
            Itemquantityfield.setText(rs.getString("AvailableQuantity"));
        }

        pst.close();
    }


    private void delete() throws SQLException {
        String ItemId = Itemidtext.getText();

        String sql = "DELETE FROM Items WHERE ItemId = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, ItemId);
        pst.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item Delete Successful");
        alert.showAndWait();
    }

    private void update() throws SQLException {
        String ItemId = Itemidtext.getText();

        String sql = "Update Items SET ItemName=?,Price=?,BrandName=?,AvailableQuantity=? Where ItemId = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, itemnamelabel.getText());
        pst.setInt(2, Integer.parseInt(itempricefield.getText()));
        pst.setString(3, itembrandlabel.getText());
        pst.setInt(4, Integer.parseInt(Itemquantityfield.getText()));
        pst.setString(5, ItemId);
        pst.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item Update Successful");
        alert.showAndWait();
    }

    @FXML
    void deletebtnact(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Delete this Item?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    delete();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @FXML
    void updatebtnact(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure you want to Update this Item?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });


    }


    public void initialize() {

        conn = DatabaseConnector.ConnectDb();
    }
}

