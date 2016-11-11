package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class MainPageTestFX_TEST extends GuiTest{

	@Test
	public void clickTabs(){
		click("#addProductTab");
		click("#tab3");
		click("#tab4");
		click("#homeTab");
	}

	@Override
	protected Parent getRootNode() {
		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("mainpage");
		return parent;
	}

}
