package testfx;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class LogInTestFX_TEST extends GuiTest{


	@Test
	public void LoginOikeatTunnukset() {
		click("#usernameTxt").type("ww");
		click("#showpword");
		click("#passwordTxt").type("w");
		click("#showpword");
		click("#passwordTxt").type("w");
		click("#showpword");
		click("#loginButton");
	}

	@Test
	public void LoginVaarillaTunnuksillaPitaaEpaonnistua() {
		click("#usernameTxt").type("eisaisionnistua");
		click("#passwordTxt").type("kayttaja");
		click("#loginButton");
		System.out.println("Ei onnistu!");
		assertTrue(find("#incorrectLabel").isVisible());
		//verifyThat(find("#incorrectLabel").isVisible(), true); ei toimi
	}


	@Override
	protected Parent getRootNode() {
		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("login");
		return parent;
	}

}
