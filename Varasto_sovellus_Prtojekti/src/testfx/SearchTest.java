package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class SearchTest extends GuiTest{

	@Test
	public void search(){
		click("#productName").type("a");
		click("#searchbtn");
		
	}




	@Override
	protected Parent getRootNode() {

		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("searchpage");
		return parent;
	}

}
