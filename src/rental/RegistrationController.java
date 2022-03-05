package rental;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Label lblError;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void register(ActionEvent actionEvent) throws IOException {
        // form validation
        if (!areFieldsValid()) {
            return;
        }

        // check if pws correlate
        if (txtPassword.getText().equals(txtPasswordConfirm.getText())) {
            User userToRegister = new User(
                    0,
                    txtFirstName.getText(), txtLastName.getText(), txtAddress.getText(), txtZip.getText(),
                    txtCity.getText(), txtTelephone.getText(), txtMail.getText(), txtCreditCard.getText(), txtPassword.getText());

            DBUtils.registerUser(userToRegister);
            cancel(actionEvent);
        } else {
            lblError.setText("Passwords don't match!");
        }
    }

    private boolean areFieldsValid() {
        if (txtFirstName.getText().isEmpty()) {
            lblError.setText("First name can't be empty");
            return false;
        }
        if (txtMail.getText().isEmpty()) {
            lblError.setText("Email can't be empty");
            return false;
        }
        if (txtPassword.getText().isEmpty()) {
            lblError.setText("Password can't be empty");
            return false;
        }
        if (txtPasswordConfirm.getText().isEmpty()) {
            lblError.setText("Password can't be empty");
            return false;
        }
        return true;
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Main m = new Main();
        m.changeScene("./login.fxml");
    }
}
