package testfx;

import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

/**
 * Testi luokka, missa testataan tuotteiden haku.
 * @author benyi
 *
 */
public class SearchTest extends GuiTest{

/**
 * Testataan teitteiden etsiminen
 */
	@Test
	public void search(){
		click("#productName").type("a");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("#productName");
		click("#productName").type("jjjjj");
		click("#searchbtn");
		click("OK");
	}
/**
 * Testataan tuotteiden muokkaaminen, kun tuote on haettu.
 *
 */
	@Test
	public void modify(){
		click("#productName").type("Maito");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("Maito3");
		//Huom. klikkattava tuote taytyy vaihtaa, kun kerran on muokannut tuote nimi aikasemmassa testissa.
		doubleClick("Maito3").type("Maito3");
		click("#paivita");
		click("Ei");
		click("#paivita");
		click("Kyllä");
		click("OK");

	}

	/**
	 * Testataan Clear- toiminta
	 */
	@Test
	public void clear(){
		click("#productName").type("a");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("#productName");
		click("#tyhjenta");
	}

	/**
	 * getRootNode, joka  palauttaa testauttavan luokan
	 */
	@Override
	protected Parent getRootNode() {

		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("searchpage");
		return parent;
	}

}
