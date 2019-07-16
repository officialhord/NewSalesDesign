package Home.Sales;

import Home.DatabaseConnector;
import Home.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SalesScreen {

    final int Initialvalue = 0;
    ObservableList itemslist = FXCollections.observableArrayList();
    ObservableList itemtoadd = FXCollections.observableArrayList();
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String ItemId;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, Initialvalue);
    int finaltotal = 0;
    @FXML
    private ComboBox itemsect;
    @FXML
    private Spinner<Integer> quantityfield;
    @FXML
    private Button additembtn;
    @FXML
    private TableView<Item> cartview;
    @FXML
    private TableColumn<String, Item> itemname;
    @FXML
    private TableColumn<Integer, Item> itemprice;
    @FXML
    private TableColumn<Integer, Item> itemquantity;
    @FXML
    private TableColumn<Integer, Item> totalcol;
    @FXML
    private Button updatebtn;
    @FXML
    private Button deleteitem;
    @FXML
    private Text itemcount;
    @FXML
    private Text totalpricetext;
    @FXML
    private Button checkoutbtn;
    @FXML
    private MenuButton loggeduser;
    @FXML
    private MenuItem logoutbtn;
    @FXML
    private MenuItem exitbtn;

    @FXML
    void additemact(ActionEvent event) {

        String Itemnames;
        int quantity = quantityfield.getValue();
        int price = 0;
        int total = 0;
        Itemnames = itemsect.getSelectionModel().getSelectedItem().toString();

        try {
            String sql = "Select * from Items where ItemName=? ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Itemnames);
            rs = pst.executeQuery();

            if (rs.next()) {

                price = Integer.parseInt(rs.getString("Price"));
                ItemId = rs.getString("ItemId");
            }
            total = price * quantity;

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String sql = "INSERT INTO Cart (\n" +
                    "                     ItemName,\n" +
                    "                     TotalPrice,\n" +
                    "                     Quantity,\n" +
                    "                     ItemId\n" +
                    "                 )\n" +
                    "                 VALUES (?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, Itemnames);
            pst.setInt(2, total);
            pst.setInt(3, quantity);
            pst.setString(4, ItemId);
            pst.execute();

            finaltotal += total;
            totalpricetext.setText("" + finaltotal);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemtoadd.add(new Item(Itemnames, quantity, price, total));


        itemname.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalcol.setCellValueFactory(new PropertyValueFactory<>("total"));
        cartview.setItems(itemtoadd);

    }

    @FXML
    void checkoutact(ActionEvent event) {

    }

    @FXML
    void deleteitemact(ActionEvent event) {

    }

    @FXML
    void exitact(ActionEvent event) {

    }

    @FXML
    void logoutact(ActionEvent event) throws Exception {


        Stage stage = new Stage();

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Home/Login.fxml"));
        Loader.load();
        Parent root;
        root = Loader.getRoot();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void updateitm() {
        String sql = "Select * from Cart";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {

                String itmname;
                int itmprice, itmtotal, itmquantity;


                itmname = rs.getString("ItemName");
                itmprice = rs.getInt("Price");
                itmtotal = rs.getInt("Total");
                itmquantity = rs.getInt("Quantity");
                itemtoadd.add(new Item(itmname, itmquantity, itmprice, itmtotal));

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemname.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalcol.setCellValueFactory(new PropertyValueFactory<>("total"));
        cartview.setItems(itemtoadd);


    }

    private void updateItems() {


        try {
            String sql = "Select * from Items";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            String tem;
            while (rs.next()) {
                tem = rs.getString("ItemName");
                itemslist.add(tem);
                itemsect.setItems(itemslist);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                pst.close();
                rs.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void initialize() {
        quantityfield.setValueFactory(valueFactory);
        conn = DatabaseConnector.ConnectDb();
        updateItems();

    }
}
