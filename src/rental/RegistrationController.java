package rental;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAddress;
    public TextField txtAddress2;
    public TextField txtZip;
    public TextField txtCity;
    public TextField txtTelephone;
    public TextField txtMail;
    public TextField txtCreditCard;
    public TextField txtPassword;
    public TextField txtPasswordConfirm;
    public Button btnRegister;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void register(ActionEvent actionEvent) throws IOException {
        // check if pws correlate
        if (txtPassword.getText().equals(txtPasswordConfirm.getText())) {
            User userToRegister = new User(
                    0,
                    txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtZip.getText(),
                    txtCity.getText(), txtTelephone.getText(), txtMail.getText(), txtCreditCard.getText(), txtPassword.getText());

            DBUtils.registerUser(userToRegister);
            cancel(actionEvent);
        } else {
            System.out.println("Passwords don't match");
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("./login.fxml");
    }
}
