package vPakkaus.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import vPakkaus.MainLaunch;

public class LoginController implements SetMainController {

	@FXML
	private TextField usernameTxt;
	@FXML
	private PasswordField passwordTxt;
	@FXML
	private CheckBox showpword;
	@FXML
	private Label incorrectLabel;
	@FXML
    private TextField visiblePasswordTxt;

	private String uname, pword;
	private MainController mc;
	boolean allGood;

	public LoginController() {
		System.out.println("LOG IN CONTROLLER");
	}

	public void initialize() {
		incorrectLabel.setVisible(false);
		visiblePasswordTxt.setFocusTraversable(false);
	}

	public void setMainController(MainController m) {
		mc = m;
	}

	public void login() throws IOException {
		allGood = true;
		incorrectLabel.setVisible(false);
		if (usernameTxt.getText().isEmpty()) {
			allGood = false;
		}

		if (passwordTxt.getText().isEmpty()) {
			allGood = false;
		}

		if (allGood) {
			uname = usernameTxt.getText();
			pword = passwordTxt.getText();

			checkUnamePword(uname, pword);
		}
	}

	public void showpword() {
		if (showpword.isSelected()) {
			passwordTxt.toBack();
			visiblePasswordTxt.toFront();
			visiblePasswordTxt.setText(passwordTxt.getText());
			passwordTxt.setFocusTraversable(false);
			visiblePasswordTxt.setFocusTraversable(true);
		} else {
			visiblePasswordTxt.toBack();
			passwordTxt.toFront();
			passwordTxt.setText(visiblePasswordTxt.getText());
			visiblePasswordTxt.setFocusTraversable(false);
			passwordTxt.setFocusTraversable(true);
		}
	}

	public void checkUnamePword(String uname, String pword) throws IOException {
		if (showpword.isSelected()) {
			passwordTxt.setText(visiblePasswordTxt.getText());
		}
		if (mc.LogIn(uname, pword)) {
			System.out.println("LOG IN ONNISTUI : " + uname);
			MainLaunch.windowDestroyer();
			MainLaunch.windowConstructor("view/MainPageView.fxml", "VarastoSovellus 1.03", null);
		} else {
			incorrectLabel.setVisible(true);
			passwordTxt.setText("");
			usernameTxt.requestFocus();
		}
	}
}
