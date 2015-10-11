package com.abhishek.fmanage.mortgage.data.container;

import org.apache.commons.lang.math.NumberUtils;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;

public class DiamondItemContainer extends IndexedContainer{

	private static final long serialVersionUID = 1L;
	public static final String ITEM_NAME = "ItemName";
	public static final String QUANTITY = "Quantity";
	public static final String PIECE_PAIR = "PiecePair";
	public static final String GOLD_WEIGHT = "Gold Wt(gms)";
	public static final String DIAMOND_WEIGHT = "Diamond Wt(gms)";
	public static final String DIAMOND_PIECE = "No. Of Diamond Piece";
	public static final String CERTIFICATE = "Certificate";
	public static final String PRICE = "Net Price";
	
	
	

	public DiamondItemContainer(){
	     addContainerProperty(ITEM_NAME, ComboBox.class, new ComboBox());
	     addContainerProperty(QUANTITY, TextField.class, new TextField());
	     addContainerProperty(PIECE_PAIR, ComboBox.class, new ComboBox());
	     addContainerProperty(GOLD_WEIGHT, TextField.class, new TextField());
	     addContainerProperty(DIAMOND_WEIGHT, TextField.class, new TextField());
	     addContainerProperty(DIAMOND_PIECE, TextField.class, new TextField());
	     addContainerProperty(CERTIFICATE, ComboBox.class, new ComboBox());
	     addContainerProperty(PRICE, TextField.class, new TextField());
	}
	
	@SuppressWarnings("unchecked")
    public void addDiamondItem()
    {
        Object diamondItemRowId = addItem();
        Item item = getItem(diamondItemRowId);
        if (item != null)
        {
        	item.getItemProperty(ITEM_NAME).setValue(getItemNameList());
        	item.getItemProperty(QUANTITY).setValue(getQuantity(diamondItemRowId));
        	item.getItemProperty(PIECE_PAIR).setValue(getPiecePair());
        	item.getItemProperty(GOLD_WEIGHT).setValue(getWeight(diamondItemRowId));
        	item.getItemProperty(DIAMOND_WEIGHT).setValue(getWeight(diamondItemRowId));
        	item.getItemProperty(DIAMOND_PIECE).setValue(getQuantity(diamondItemRowId));
        	item.getItemProperty(CERTIFICATE).setValue(getCertificate(diamondItemRowId));
        	item.getItemProperty(PRICE).setValue(getPrice());
        }
    }

	private Object getCertificate(Object diamondItemRowId) {
		ComboBox itemName = new ComboBox();
		itemName.addItem("Yes");
		itemName.addItem("No");
		itemName.setValue("Yes");
		return itemName;
	}

	private Object getPrice(){
		TextField itemPrice = new TextField();
		itemPrice.setImmediate(true);
		itemPrice.setRequired(true);
		itemPrice.setValidationVisible(true);
		itemPrice.addValidator(new Validator() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if(!NumberUtils.isNumber(String.valueOf(value)) 
						|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) <= 0.0))){
					itemPrice.setComponentError(new UserError("Must be number and > 0"));
					itemPrice.addStyleName("v-textfield-fail");
				}else{
					itemPrice.setComponentError(null);
					itemPrice.removeStyleName("v-textfield-fail");
				}
			}
		});
		return itemPrice;

	}

	private Object getWeight(Object currentItemId) {
		TextField weight = new TextField();
		weight.setImmediate(true);
		weight.setRequired(true);
		weight.setValidationVisible(true);
		weight.addValidator(new Validator() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if(!NumberUtils.isNumber(String.valueOf(value)) 
						|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) <= 0.0))){
					weight.setComponentError(new UserError("Must be number and > 0"));
					weight.addStyleName("v-textfield-fail");
					
				}else{
					weight.setComponentError(null);
					weight.removeStyleName("v-textfield-fail");
				}
			}
		});
		return weight;
	}

	private ComboBox getPiecePair() {
		ComboBox itemName = new ComboBox();
		itemName.addItem("Piece");
		itemName.addItem("Pair");
		itemName.setValue("Piece");
		return itemName;
	}

	private TextField getQuantity(Object currentItemId){
		TextField quantity = new TextField();
		quantity.setImmediate(true);
		quantity.setRequired(true);
		quantity.setValidationVisible(true);
		quantity.addValidator(new Validator() {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void validate(Object value) throws InvalidValueException {
				if(!NumberUtils.isDigits(String.valueOf(value)) 
						|| ( NumberUtils.isDigits(String.valueOf(value)) && (NumberUtils.toInt(String.valueOf(value)) <= 0))){
					quantity.setComponentError(new UserError("Must be number and > 0"));
					quantity.addStyleName("v-textfield-fail");
				}else{
					quantity.setComponentError(null);
					quantity.removeStyleName("v-textfield-fail");
				}
			}
		});
		return quantity;
	}
	
	private ComboBox getItemNameList(){
		ComboBox itemName = new ComboBox();
		itemName.addItem("Chain");
		itemName.addItem("Necklace");
		itemName.setValue("Chain");
		return itemName;
	}
}
