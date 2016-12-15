package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import vPakkaus.MainLaunch;
/**
 * Testi luokka, missa testataan tavaran lisaaminen nakyma toimintoja
 *
 * @author benyi
 */
public class AddProduct extends GuiTest {
/**
 * Tavaran lisaaminen onnistuusti
 */
  @Test
  public void addProduct() {
    click("#addProductTab");
    click("#productName").type("testFx11");
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
/**
 * Tavaran lisaaminen epaonnistuu
 */
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
