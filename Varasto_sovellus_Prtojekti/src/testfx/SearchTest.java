package testfx;

import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;


public class SearchTest extends GuiTest{



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

	@Test
	public void modify(){
		click("#productName").type("Maito");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("Maito2");
		doubleClick("Maito2").type("Maito3");
		click("#paivita");
		click("Ei");
		click("OK");
		click("#paivita");
		click("Kyllä");
		click("OK");

	}

	@Test
	public void clear(){
		click("#productName").type("a");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("#productName");
		click("#tyhjenta");
	}


	@Override
	protected Parent getRootNode() {

		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("searchpage");
		return parent;
	}

}
