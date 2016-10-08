package vPakkaus.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import vPakkaus.Product;

class EditingCell extends TableCell<Product, Object > { //List<? extends Foo> list

    private TextField textField;
    private int datatyyppi;//1 Int,2 String,3 double,5 float
    private Product muokattava;//Olio jota muokataan
    private String columnName; //Product olion atribuutin nimi eli taulukon kolumnin nimi
    private boolean valChangedByUser;//Boolean, joka asetetaan aina true kun käyttäjä tekee muutoksia taulukkoon

    public EditingCell(int n) {
    	datatyyppi = n;
    	valChangedByUser = false;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
        	valChangedByUser = false;
            super.startEdit();
            //System.out.println(getTableView().getColumns().get(this.getIndex()));
            //System.out.println(getTableRow().getIndex());
            muokattava = (Product)getTableRow().getItem();
            columnName = this.getTableColumn().getText();
            createTextField();
            //setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
    	//Tänne muutoksia jos se yks bugi halutaan korjaa
    	//eli kun klikkaa toiselle riville niin muutokset ei tallennu
        super.cancelEdit();
        setGraphic(null);
    }

    @Override
    public void updateItem(Object item, boolean empty) {
    	System.out.println("Up");
    	if(valChangedByUser){ //Jos käyttäjä tehnyt muutoksen merkitään se rivi näkymässä
    		this.getTableRow().setStyle("-fx-background-color:lightcoral");//Muutetaan rivin väriä
    		valChangedByUser = false;
    		System.out.println("UPDATE 3 CALLED");
    	}
    	super.updateItem(item, empty);
        if (empty) {
            //setText(null);
        	System.out.println("UPDATE 1 CALLED");
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                	System.out.println("UPDATE 2 CALLED");
                    textField.setText(getString());
                }
                System.out.println("UPDATE 2.1 CALLED");
                //setText(null);
                setGraphic(textField);
            } else {
            	setText(getString());
                setGraphic(null);
            }
        }
    }

    private void setValChanged(boolean b){
    	valChangedByUser = b;
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){ //Tässä määritellään uusi sisäluokka
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                Boolean arg1, Boolean arg2) {
            	System.out.println(arg1 +" "+ arg2);
                    if (!arg2) {
                    	String s = textField.getText();
                    	try{
	                    	switch(datatyyppi){ //säilötyn luvun avulla voidaan päätellä mitä datatyyppiä pitää tallentaa
	                    		case(1): //Int
	                    			Integer i = isInt(s);
	                    			if(i!=null && i.intValue()!=muokattava.getID()){
	                    				setValChanged(true);
	                    				System.out.println("muuttui true:");
	                    				commitEdit(i);
	                    			}
	                    			break;
	                    		case(2): //String
	                    			if(columnName.equals("Name") && !s.equals(muokattava.getProduct_name())){
	                    				setValChanged(true);
	                    				System.out.println("muuttui true:");
	                    				commitEdit(s);
	                    			}
	                    			break;
	                    		case(3)://Double
	                    			Double d = isNumeric(s) ? Double.parseDouble(s) : null;
	                    			if(d != null){
	                    				//Meillä atm. kaksi riviä, jotka käyttää Double arvoa. Paino ja tilavuus
	                    				if(columnName.equals("Weight") && d.doubleValue()!=muokattava.getProduct_weight()){ //Muokataan weight solua
	                    					setValChanged(true);
	                    					System.out.println("muuttui true:");
	                    					commitEdit(Double.parseDouble(s));
	                    				}else if(columnName.equals("Volume") && d.doubleValue()!=muokattava.getProduct_volume()){ //Muokataan volume solua
	                    					setValChanged(true);
	                    					System.out.println("muuttui true:");
	                    					commitEdit(Double.parseDouble(s));
	                    				}
	                    			}
	                    			break;
	                    		case(4)://Float
	                    			Float f = isNumeric(s) ? Float.parseFloat(s) : null;
	                    			System.out.println("Float = " + Float.compare(f, muokattava.getProduct_price()));
	                    			if(f!=null && columnName.equals("Price") && Float.compare(f, muokattava.getProduct_price()) != 0){
	                    				setValChanged(true);
	                    				System.out.println("muuttui true:");
	                    				commitEdit(Float.parseFloat(s));
	                    			}
	                    			break;
	                    	}
                    	}catch(Exception e){System.out.println("VIRHE HAVAITTU!!!");}
                    }
            }
        });
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    private Integer isInt(String s){
    	try{
    		Integer i = Integer.parseInt(s);
    		return i;
    	}catch(Exception e){
    		return null;
    	}
    }



    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}

