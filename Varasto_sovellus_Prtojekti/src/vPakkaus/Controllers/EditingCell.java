package vPakkaus.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import vPakkaus.Product;

class EditingCell extends TableCell<Product, Object > { //List<? extends Foo> list

    private TextField textField;
    private int datatyyppi;//1 Int,2 String,3 double,5 float
    private Product muokattava;
    private int columnNumber;
    private String columnName;

    public EditingCell(int n) {
    	datatyyppi = n;
    }

    public void ColumnNumber(int n){
    	columnNumber = n;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
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
        super.cancelEdit();
        super.isItemChanged(this, this);
        System.out.println("Stopped");
        //setText(Integer.toString(muokattava.getID()));
        setGraphic(null);
    }

    @Override
    public void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            //setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                //setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()* 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                Boolean arg1, Boolean arg2) {
            	System.out.println(arg1 +" "+ arg2);
                    if (!arg2) {
                    	String s = textField.getText();
                    	boolean valChanged = false;
                    	try{
	                    	switch(datatyyppi){
	                    		case(1):
	                    			Integer i = isInt(s);
	                    			if(i!=null && i.intValue()!=muokattava.getID()){
	                    				commitEdit(i);
	                    				valChanged = true;
	                    			}
	                    			break;
	                    		case(2):
	                    			if(columnName.equals("Name") && !s.equals(muokattava.getProduct_name())){
	                    				commitEdit(s);
	                    				valChanged = true;
	                    			}
	                    			break;
	                    		case(3):
	                    			Double d = isNumeric(s) ? Double.parseDouble(s) : null;
	                    			if(d != null){

	                    				if(columnName.equals("Weight") && d.doubleValue()!=muokattava.getProduct_weight()){ //Muokataan weight solua
	                    					commitEdit(Double.parseDouble(s));
		                    				valChanged = true;
	                    				}else if(columnName.equals("Volume") && d.doubleValue()!=muokattava.getProduct_volume()){ //Muokataan volume solua
	                    					commitEdit(Double.parseDouble(s));
		                    				valChanged = true;
	                    				}
	                    			}
	                    			break;
	                    		case(4):
	                    			Float f = isNumeric(s) ? Float.parseFloat(s) : null;
	                    			System.out.println("Float = " + Float.compare(f, muokattava.getProduct_price()));
	                    			if(f!=null && columnName.equals("Price") && Float.compare(f, muokattava.getProduct_price()) != 0){
	                    				commitEdit(Float.parseFloat(s));
	                    				valChanged = true;
	                    			}
	                    			break;
	                    	}
	                    	if(valChanged){
	                    		getTableRow().setStyle("-fx-background-color:lightcoral");
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

