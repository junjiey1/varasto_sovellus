package testfx;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import vPakkaus.MainLaunch;
/**
 * Testi luokka, missa testataan asiakkaiden lisaaminen
 * @author benyi
 *
 */
public class AddAsiakasTest extends GuiTest{
  /**
   * Tarkistetaan, etta kaikki tekstikentat loytyvat
   */
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
  /**
   * Testataan, etta asiakas lisaaminen onnistuu
   *
   */
  @Test
  public void lisaaAsiakas(){
    //HUOM. kayttaja nimi piti joka kertaa keksia uuden, kun kerran on tallennettu.
    click("#customerName").type("TestFxx");
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
  /**
   * getRootNode, joka  palauttaa testauttavan luokan
   */
  @Override
  protected Parent getRootNode() {

    MainLaunch m = new MainLaunch();
    m.testFX_Esivalmistelut();
    Parent parent = m.getAnchorPane("customer");
    return parent;
  }

}
