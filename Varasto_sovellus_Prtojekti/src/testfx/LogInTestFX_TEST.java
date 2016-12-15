package testfx;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import vPakkaus.MainLaunch;
/**
 * Luokka, missa testataan sisaankirjautuminen
 * @author benyi
 *
 */
public class LogInTestFX_TEST extends GuiTest{

/**
 * Sisaankirjautuminen onnistuusti
 */
	@Test
	public void loginOikeatTunnukset() {
		click("#usernameTxt").type("ww");
		click("#showpword");
		click("#passwordTxt").type("w");
		click("#showpword");
		click("#passwordTxt").type("w");
		click("#showpword");
		click("#loginButton");
	}
/**
 * Sisaankirjautuminen epaonnistuu vaaralla tunnuksella
 */

	@Test
	public void loginVaarillaTunnuksillaPitaaEpaonnistua() {
		click("#usernameTxt").type("eisaisionnistua");
		click("#passwordTxt").type("kayttaja");
		click("#loginButton");
		System.out.println("Ei onnistu!");
		assertTrue(find("#incorrectLabel").isVisible());
		//verifyThat(find("#incorrectLabel").isVisible(), true); ei toimi
	}

	 /**
	  * getRootNode, joka  palauttaa testauttavan luokan
	  */
	@Override
	protected Parent getRootNode() {
		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("login");
		return parent;
	}

}
