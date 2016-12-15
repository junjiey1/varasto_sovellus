package vPakkaus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 *Luokka, joka vastaa ohjelman lokalisoinnista.
 *Kaikki metodit staattisia ja instanssia tästä luokasta ei voi luoda
 * */

public class LanguageUtil {
  /**
   *Muuttuja jossa säilytetään properties tiedostosta luettuja avain arvo pareja
   **/
  private static ResourceBundle messages=null;
  /**
   *String-muuttuja jossa säilytetään käytettyä maata esim. US, FI, AR
   **/
  private static String country;
  /**
   *Muuttuja jossa säilytetään käytettyä kieltä esim. en, es tai fi
   **/
  private static String language;

  private LanguageUtil(){}


  public static void setMessagesResource(ResourceBundle resource){
    messages = resource;
    country = messages.getLocale().getCountry();
    language = messages.getLocale().getLanguage();
  }

  /**
   *Hakee annetulla avainarvolla messages muuttujasta arvon jos avaimen arvolla ei löydy mitään palauttaa oletus string.
   *@param String key
   *@return String value
   * */
  public static String getMessageFromResource(String key){
    if(messages==null || !(messages.containsKey(key)))
      return "!!! String with a key " + key + " doesn't exist !!!";
    return messages.getString(key);
  }

  /**
   *Metodi, joka hakee kieliasetus tiedostosta määritellyn kielen ja lataa vastaavan kieli properties tiedoston
   */
  public static void buildResourceBundleFromLanguageSettingsPropertiesFile(){
    FileInputStream in=null;
    try {
      in = new FileInputStream("src/languages/languageSettings.properties"); //Lataa asetukset properties tiedosto
      Properties props = new Properties();
      props.load(in);
      //Rakenna ResourceBundle saaduista arvoista
      messages = ResourceBundle.getBundle("languages/MessagesBundle", new Locale(props.getProperty("language"), props.getProperty("country")));
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }finally{
      if(in!=null)
        try {
          in.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
  }

  public static String getLanguage() {
    return language;
  }

  public static String getCountry() {
    return country;
  }
}
