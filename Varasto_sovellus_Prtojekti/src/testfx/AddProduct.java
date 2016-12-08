package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;

public class AddProduct extends GuiTest {

  @Test
  public void addProduct() {
    click("#addProductTab");
    click("#productName").type("testFx1");
    click("#length").type("3");
    click("#width").type("2");
    click("#height").type("4");
    click("#volume").type("4");
    click("#quantity").type("6");
    click("#weight").type("4");
    click("#price").type("20");
    click("#whLocation").type("ab-13");
    click("#addProductBtn");
    click("OK");
  }

  @Test
  public void failaddProduct() {
    click("#addProductTab");
    click("#productName").type("testFx1");
    click("#length").type("3");
    click("#width");
    click("#height").type("4");
    click("#volume").type("4");
    click("#quantity").type("6");
    click("#weight").type("4");
    click("#price");
    click("#whLocation").type("ab-13");
    click("#addProductBtn");
    click("OK");

  }

	@Override
	protected Parent getRootNode() {

		MainLaunch m = new MainLaunch();
		m.testFX_Esivalmistelut();
		Parent parent = m.getAnchorPane("mainpage");
		return parent;
	}

}
