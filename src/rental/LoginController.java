package rental;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login initialised");
    }

    public void register() throws IOException {
        Main m = new Main();
        m.changeScene("./register.fxml");
    }

    public void cancel() {
        // get a handle to the stage
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // close window
        stage.close();
    }

    public void login(ActionEvent e) throws IOException {
        // validate user in db
        User userToValidate = DBUtils.isUser(txtMail.getText(), txtPassword.getText());
        if (userToValidate != null) {
            Main m = new Main();
            m.setLogin(userToValidate, e);
        } else {
            System.out.println("User credentials incorrect");
        }
    }
}
