import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
public class DashboardFX {

    private int userId;

    public DashboardFX(int userId) {
        this.userId = userId;
    }

    public void start(Stage stage) {
        Label balanceCard = new Label("Current Balance: ₹0");
        balanceCard.setStyle(
                "-fx-background-color: #2a2a40;" +
                        "-fx-text-fill: #00ffcc;" +
                        "-fx-font-size: 16px;" +
                        "-fx-padding: 15;" +
                        "-fx-background-radius: 10;"
        );

        Button deposit = new Button("Deposit");
        Button withdraw = new Button("Withdraw");
        Button balance = new Button("Check Balance");
        Button historyBtn = new Button("Transaction History");
        deposit.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        withdraw.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        balance.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        historyBtn.setStyle("-fx-background-color: #6f42c1; -fx-text-fill: white;");

        historyBtn.setOnAction(e -> {

            TransactionDAO dao = new TransactionDAO();
            java.util.List<Transaction> list = dao.getTransactions(userId);

            Stage stage2 = new Stage();

            TableView<Transaction> table = new TableView<>();

            TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID");
            idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

            TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
            typeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));

            TableColumn<Transaction, Double> amtCol = new TableColumn<>("Amount");
            amtCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("amount"));

            TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
            dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

            table.getColumns().addAll(idCol, typeCol, amtCol, dateCol);

            table.getItems().addAll(list);

            VBox vbox = new VBox(table);

            stage2.setScene(new Scene(vbox, 500, 300));
            stage2.setTitle("Transaction History");
            stage2.show();
        });

        Label output = new Label();

        BankService service = new BankService();

        deposit.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter amount");

            dialog.showAndWait().ifPresent(val -> {
                service.deposit(userId, Double.parseDouble(val));
                output.setText("Deposited ₹" + val);
            });
        });

        withdraw.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText("Enter amount");

            dialog.showAndWait().ifPresent(val -> {
                service.withdraw(userId, Double.parseDouble(val));
                output.setText("Withdraw ₹" + val);
            });
        });

        balance.setOnAction(e -> {
            double bal = service.checkBalance(userId);
            balanceCard.setText("Current Balance: ₹" + bal);
        });

        VBox layout = new VBox(10, balanceCard, deposit, withdraw, balance, historyBtn, output);
        layout.setStyle("-fx-padding:20; -fx-alignment:center;");
        layout.setSpacing(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.setSpacing(15);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
}