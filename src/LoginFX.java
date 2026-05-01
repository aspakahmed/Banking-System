import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginFX extends Application {

    @Override
    public void start(Stage stage) {

        TextField email = new TextField();
        email.setPromptText("Email");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        Button loginBtn = new Button("Login");

        Label result = new Label();

        UserDAO dao = new UserDAO();

        loginBtn.setOnAction(e -> {
            int userId = dao.login(email.getText(), password.getText());

            if (userId != -1) {
                result.setText("Login Success");
                new DashboardFX(userId).start(new Stage());
                stage.close();
            } else {
                result.setText("Invalid Login");
            }
        });

        VBox layout = new VBox(10, email, password, loginBtn, result);
        layout.setStyle("-fx-padding:20; -fx-alignment:center;");

        Scene scene = new Scene(layout, 300, 250);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Bank Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}