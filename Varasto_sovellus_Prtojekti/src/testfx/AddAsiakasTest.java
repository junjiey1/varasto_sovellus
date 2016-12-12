package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import vPakkaus.MainLaunch;

public class AddAsiakasTest extends GuiTest{

  @Test
  public void fieldTest(){
    click("#customerName");
    click("#customerStreet");
    click("#customerPostalCode");
    click("#customerCity");
    click("#contactPersonFname");
    click("#contactPersonLname");
    click("#contactPersonEmail");
    click("#contactPersonPhone");
    click("#save");
    click("OK");
  }

  @Test
  public void lisaaAsiakas(){

    click("#customerName").type("TestFx2");
    click("#save");
    click("OK");
    click("#customerStreet").type("koulu");
    click("#save");
    click("OK");
    click("#customerPostalCode").type("00100");
    click("#save");
    click("OK");
    click("#customerCity").type("Helsinki");
    click("#save");
    click("OK");
    click("#contactPersonFname").type("Test");
    click("#save");
    click("OK");
    click("#contactPersonLname").type("Fx");
    click("#save");
    click("OK");
    click("#contactPersonEmail").type("Tes");
    click("#save");
    click("OK");
    click("#contactPersonEmail").press(KeyCode.CONTROL).press(KeyCode.ALT).press(KeyCode.DIGIT2).release(KeyCode.DIGIT2).release(KeyCode.ALT).release(KeyCode.CONTROL).type("mail.com");
    click("#save");
    click("OK");
    click("#contactPersonPhone").press(KeyCode.PLUS).release(KeyCode.PLUS).type("359 12 354 4564");
    click("#save");
    click("OK");
  }
  @Override
  protected Parent getRootNode() {

    MainLaunch m = new MainLaunch();
    m.testFX_Esivalmistelut();
    Parent parent = m.getAnchorPane("customer");
    return parent;
  }

}
