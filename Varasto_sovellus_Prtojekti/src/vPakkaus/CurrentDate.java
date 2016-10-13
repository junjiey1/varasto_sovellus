package vPakkaus;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Hakea taman paivan paivamaarat
 *
 */
public class CurrentDate {

	public String[] getCurrentDate(){

	    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	    String[] split = date.split("-");

		return split;
	}

}
