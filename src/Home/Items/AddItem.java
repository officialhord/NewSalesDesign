package Home.Items;


import Home.DatabaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class AddItem {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String ItemsId;

    @FXML
    private TextField ItemNameText;
    @FXML
    private TextField BrandNameText;
    @FXML
    private Label ItemId;
    @FXML
    private ImageView codeview;

    @FXML
    private TextField pricetxtfield;
    @FXML
    private Button saveitem;

    @FXML
    void ScanCodeAct(ActionEvent event) {

    }

    void GenerateId() {

        int min = 10000;
        int max = 20000;
        int randomId;
        Random rand = new Random();


        randomId = rand.nextInt((max - min) + 1) + min;
        ItemsId = "SS-" + randomId;

        try {
            String sql = "Select * from Items where ItemId = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, ItemsId);
            rs = pst.executeQuery();
            if (rs.next()) {
                GenerateId();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void saveitemact(ActionEvent event) throws SQLException {

        String ProductName, BrandName;
        int ItemPrice, quantity;

        ProductName = ItemNameText.getText();
        BrandName = BrandNameText.getText();
        ItemPrice = Integer.parseInt(pricetxtfield.getText());
       // quantity = Integer.parseInt(quantitytextfield.getText());


        String sql = "Insert into Items (`ItemId`,`ItemName`,`Price`,`BrandName`,`AvailableQuantity`) values (?,?,?,?,?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, ItemsId);
        pst.setString(2, ProductName);
        pst.setInt(3, ItemPrice);
        pst.setString(4, BrandName);
     //   pst.setInt(5, quantity);
        pst.execute();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Item Added Succesfully");
        ((Node) (event.getSource())).getScene().getWindow().hide();
        alert.showAndWait();

        pst.close();
    }

    public void initialize() {

        conn = DatabaseConnector.ConnectDb();
        GenerateId();
        ItemId.setText(ItemsId);
    }

}
