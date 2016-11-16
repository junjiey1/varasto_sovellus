package testfx;

import org.junit.Assert;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;


public class SearchTest extends GuiTest{


	@Test
	public void modify(){
		click("#productName").type("Maito");
		click("#searchbtn");
		click("#tuoteTable");
		doubleClick("Maito1");
		doubleClick("Maito1").type("Maito2");

	}

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


	@Override
	protected Parent getRootNode() {

		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("searchpage");
		return parent;
	}

}
