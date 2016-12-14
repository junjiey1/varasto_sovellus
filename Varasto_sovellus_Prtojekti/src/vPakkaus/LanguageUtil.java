package vPakkaus;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class LanguageUtil {
  private static ResourceBundle messages=null;
  private static String country;
  private static String language;

  public static void setMessagesResource(ResourceBundle resource){
    messages = resource;
    country = messages.getLocale().getCountry();
    language = messages.getLocale().getLanguage();
  }

  public static String getMessageFromResource(String key){
    if(messages==null)
      return "!!! String with a key " + key + " doesn't exist !!!";
    return messages.getString(key);
  }

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
