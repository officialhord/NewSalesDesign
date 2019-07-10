package Sales.SalesScreen;

import Sales.DatabaseConnector;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

public class Controller {

    final int Initialvalue = 0;
    ObservableList stafflevel = FXCollections.observableArrayList("Receptionist", "Admin");
    ObservableList items = FXCollections.observableArrayList();
    ObservableList cartitems = FXCollections.observableArrayList();
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    String Itemname;
    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, Initialvalue);
    @FXML
    private TableView<CartItems> Itemtable;
    @FXML
    private TableColumn<String, CartItems> itemname;
    @FXML
    private TableColumn<Integer, CartItems> Itemquantity;
    @FXML
    private TableColumn<Integer, CartItems> Itemprice;
    @FXML
    private TableColumn<Integer, CartItems> itemtotal;
    @FXML
    private ComboBox itemsearchbox;
    @FXML
    private Label subtotal;
    @FXML
    private Label totals;
    @FXML
    private Label due;
    @FXML
    private Spinner<Integer> quantity;
    @FXML
    private Button addToCart;
    private int subTotal;
    @FXML
    private Button genrecieptbtn;

    private void getItems() {

        try {
            String sql = "Select * from Items";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            String item;

            while (rs.next()) {
                item = rs.getString("ItemName");
                items.add(item);
                itemsearchbox.setItems(items);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long GenerateId() {
        int randomId = 0;
        Random rand = new Random();
        for (int j = 1; j < 1000; j++) {
            randomId = (int) rand.nextLong();
        }
        return randomId;
    }

    @FXML
    private void generatereciept(ActionEvent evt) {
        try {

            Document document = new Document(PageSize.A5);
            PdfWriter.getInstance(document, new FileOutputStream("Report.pdf"));
            document.open();

            document.add(new Paragraph("V E N A Stores", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD)));
            document.add(new Paragraph("No. 17, Second Floor, Novare Mall, Ajah Lagos", FontFactory.getFont(FontFactory.HELVETICA, 8)));
            document.add(new Paragraph("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", FontFactory.getFont(FontFactory.HELVETICA, 8)));
            document.add(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD)));
            document.add(new Paragraph("Sales Invoice", FontFactory.getFont(FontFactory.HELVETICA, 14)));

            document.close();

            String add1 = ".//Report.pdf";
            String s = "rundll32 url.dll,FileProtocolHandler " + add1;
            Runtime.getRuntime().exec(s);


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void AddtoCart(ActionEvent evt) {
        String itemId = null;
        int qunatity = quantity.getValue();
        int price = 0;
        int total = 0;
        Itemname = itemsearchbox.getSelectionModel().getSelectedItem().toString();

        try {
            String sql = "Select * from Items where ItemName=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, Itemname);
            rs = ps.executeQuery();

            if (rs.next()) {

                price = Integer.parseInt(rs.getString("Price"));
                itemId = rs.getString("ItemId");
            }
            total = price * qunatity;

            subTotal += total;
            addtodbcart(itemId, qunatity);
            cartitems.add(new CartItems(total, qunatity, price, Itemname));
            addItems();


        } catch (Exception e) {
            e.printStackTrace();
        }

        subtotal.setText("" + subTotal);
        totals.setText("" + subTotal);
        due.setText("" + subTotal);

    }



    private void addtodbcart(String itemId,int qunatity){
        try {
            String mql = "Insert into Cart (`ItemId`,`ItemName`,`TotalPrice`,`Quantity`) Values (?,?,?,?)";
            ps = conn.prepareStatement(mql);
            ps.setString(1, itemId);
            ps.setString(2, Itemname);
            ps.setString(3, totals.getText());
            ps.setString(4, String.valueOf(qunatity));
            ps.execute();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void addItems() {

        itemtotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        itemname.setCellValueFactory(new PropertyValueFactory<>("Itemname"));
        Itemprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        Itemquantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        Itemtable.setItems(cartitems);

    }

    public void initialize() {

        conn = DatabaseConnector.ConnectDb();
        getItems();
        quantity.setValueFactory(valueFactory);

    }

}