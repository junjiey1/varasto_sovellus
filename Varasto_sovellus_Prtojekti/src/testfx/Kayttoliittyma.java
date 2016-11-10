package testfx;
import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Kayttoliittyma extends GuiTest{

	@Test
	public void test() {
		click("#passwordTxt").type("kayttaja");
	}

	@Override
	protected Parent getRootNode() {
		Parent parent = null;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../vPakkaus/view/LoginView.fxml"));
		try {
			parent = loader.load();
			return parent;
		} catch (IOException e) {
			e.printStackTrace();
		}


		//		try {
//			parent = FXMLLoader.load(getClass().getResource("../view/LoginView.fxml"));
//			return parent;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return parent;
	}

}
