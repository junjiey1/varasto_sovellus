package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;
/**
 * Testi luokka, missa testataan MainPage kayttajaliittyma toimintoja
 * @author benyi
 *
 */
public class MainPageTestFX_TEST extends GuiTest{
/**
 * Testaa Tabien painaminen
 */
	@Test
	public void clickTabs(){
		click("#addProductTab");
		click("#tab3");
		click("#tab4");
		click("#homeTab");
		click("#addProductTab");
	}
/**
 * Testataan LogOut-painike
 */
	@Test
	public void logOut(){
		click("#logout");

	}
/**
 * Testataan Management-painike
 */
	@Test
	public void management(){
		click("#management");

	}

 /**
  * getRootNode, joka  palauttaa testauttavan luokan
  */

	@Override
	protected Parent getRootNode() {
		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("mainpage");
		return parent;
	}

}
