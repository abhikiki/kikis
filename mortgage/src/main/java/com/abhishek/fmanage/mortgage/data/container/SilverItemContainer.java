package com.abhishek.fmanage.mortgage.data.container;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

public class SilverItemContainer extends IndexedContainer implements CustomItemContainerInterface{

	private static final long serialVersionUID = 1L;
	public static final String DELETE = "Delete";
	public static final String PRICE = "Price(INR)";
	public static final String SILVER_RATE = "SilverRate";
	public static final String MAKING_CHARGE = "MakingCharge";
	public static final String MAKING_CHARGE_TYPE = "MK ChargeType";
	public static final String WEIGHT = "Wt(gms)";
	public static final String PIECE_PAIR = "PiecePair";
	public static final String QUANTITY = "Quantity";
	public static final String ITEM_NAME = "ItemName";
	
	double totalPrice = 0.0;
	

	public SilverItemContainer(){
		addContainerProperty(DELETE, Image.class, new Image());
	     addContainerProperty(ITEM_NAME, ComboBox.class, new ComboBox());
	     addContainerProperty(QUANTITY, TextField.class, new TextField());
	     addContainerProperty(PIECE_PAIR, ComboBox.class, new ComboBox());
	     addContainerProperty(WEIGHT, TextField.class, new TextField());
	     addContainerProperty(MAKING_CHARGE_TYPE, ComboBox.class, new ComboBox());
	     addContainerProperty(MAKING_CHARGE, TextField.class, new TextField());
	     addContainerProperty(SILVER_RATE, TextField.class, new TextField());
	     addContainerProperty(PRICE, TextField.class, new TextField());
	    
	}
	
	 public Double getTotal(){
		 double totalCost= 0.0;
	        for (Object obj: getAllItemIds()){
	        	TextField goldPriceTxtField = (TextField)getItem(obj).getItemProperty(PRICE).getValue();
	        	String itemPrice = goldPriceTxtField.getValue();
	        	totalCost += NumberUtils.isNumber(itemPrice) ? NumberUtils.toDouble(itemPrice) : 0.0;
	        }
	        return totalCost;
	    }
	 
	@SuppressWarnings("unchecked")
    public void addCustomItem()
    {
        Object silverItemRowId = addItem();
        Item item = getItem(silverItemRowId);
        ThemeResource resource = new ThemeResource("img/removeButtonSmall.jpg");
        // Use the resource
           final Image image = new Image("", resource);
           image.setHeight("20px");
           image.setWidth("20px");
           image.setDescription("Remove Item");
           image.setData(silverItemRowId);
           image.addClickListener((event -> removeItem(image.getData())));
        if (item != null)
        {
        	item.getItemProperty(DELETE).setValue(image);
        	item.getItemProperty(ITEM_NAME).setValue(getItemNameList());
        	item.getItemProperty(QUANTITY).setValue(getQuantity(silverItemRowId));
        	item.getItemProperty(PIECE_PAIR).setValue(getPiecePair());
        	item.getItemProperty(WEIGHT).setValue(getWeight(silverItemRowId));
        	item.getItemProperty(MAKING_CHARGE_TYPE).setValue(getMakingChargeType(silverItemRowId));
        	item.getItemProperty(MAKING_CHARGE).setValue(getMakingCharge(silverItemRowId));
        	item.getItemProperty(SILVER_RATE).setValue(getSilverRate(silverItemRowId));
        	item.getItemProperty(PRICE).setValue(getPrice());
        }
    }

	private Object getPrice(){
		TextField itemPrice = new TextField();
		itemPrice.setImmediate(true);
		itemPrice.setRequired(true);
		itemPrice.setEnabled(false);
		itemPrice.setValidationVisible(true);
		itemPrice.setEnabled(false);
		itemPrice.setStyleName("my-disabled");
		itemPrice.addValidator(new DoubleRangeValidator("Must be number and > 0",  0.1, null));
		itemPrice.addValidator(
			(value) -> {
							if(!NumberUtils.isNumber(String.valueOf(value)) 
									|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) <= 0.0))){
								itemPrice.addStyleName("v-textfield-fail");
							}else{
								itemPrice.removeStyleName("v-textfield-fail");
							}});
		return itemPrice;

	}

	private Object getSilverRate(Object currentItemId) {
		TextField silverRate = new TextField();
		silverRate.setImmediate(true);
		silverRate.setRequired(true);
		silverRate.setValidationVisible(true);
		silverRate.setWidth("80%");
		silverRate.addValueChangeListener(getCustomValueChangeListener(currentItemId));
		silverRate.addValidator(
				(value) -> {
								if(!NumberUtils.isNumber(String.valueOf(value)) 
										|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) <= 0.0))){
									silverRate.addStyleName("v-textfield-fail");
								}else{
									silverRate.removeStyleName("v-textfield-fail");
								}});
		return silverRate;
	}

	private Object getMakingCharge(Object currentItemId) {
		TextField makingCharge = new TextField();
		makingCharge.setImmediate(true);
		makingCharge.setRequired(true);
		makingCharge.setValidationVisible(true);
		makingCharge.setWidth("100%");
		makingCharge.addValueChangeListener(getCustomValueChangeListener(currentItemId));
		makingCharge.addValidator(new DoubleRangeValidator("Must be number and >= 0", 0.0, null));
		makingCharge.addValidator(
			(value) -> {
				if(!NumberUtils.isNumber(String.valueOf(value)) 
						|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) < 0.0))){
					makingCharge.addStyleName("v-textfield-fail");

				}else{
					makingCharge.setComponentError(null);
					makingCharge.removeStyleName("v-textfield-fail");
				}
			});
		return makingCharge;
	}

	private ComboBox getMakingChargeType(Object goldItemRowId) {
		ComboBox itemName = new ComboBox();
		itemName.addItem("%");
		itemName.addItem("per gm");
		itemName.addItem("net");
		itemName.setValue("%");
		itemName.setImmediate(true);
		itemName.addValueChangeListener(getCustomValueChangeListener(goldItemRowId));
		return itemName;
	}

	private Object getWeight(Object currentItemId) {
		TextField weight = new TextField();
		weight.setImmediate(true);
		weight.setRequired(true);
		weight.setValidationVisible(true);
		weight.setWidth("100%");
		weight.addValueChangeListener(getCustomValueChangeListener(currentItemId));
		weight.addValidator(new DoubleRangeValidator("Must be number and > 0", 0.1, null));
		weight.addValidator(
			(value) -> {
				if(!NumberUtils.isNumber(String.valueOf(value)) 
						|| ( NumberUtils.isNumber(String.valueOf(value)) && (NumberUtils.toDouble(String.valueOf(value)) <= 0.0))){
					weight.addStyleName("v-textfield-fail");
				}else{
					weight.setComponentError(null);
					weight.removeStyleName("v-textfield-fail");
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
		quantity.setWidth("90%");
		quantity.addValueChangeListener(getCustomValueChangeListener(currentItemId));
		quantity.addValidator(new DoubleRangeValidator("Must be number and > 0", 0.1, null));
		quantity.addValidator((value) ->
			{
				if(!NumberUtils.isDigits(String.valueOf(value)) 
				|| ( NumberUtils.isDigits(String.valueOf(value))
				&& (NumberUtils.toInt(String.valueOf(value)) <= 0))){
				quantity.addStyleName("v-textfield-fail");
				}else{
					quantity.setComponentError(null);
					quantity.removeStyleName("v-textfield-fail");
				}
			});
		return quantity;
	}

	private ValueChangeListener getCustomValueChangeListener(Object currentItemId) {
		return new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;
			public void valueChange(ValueChangeEvent event) {
				ComboBox piecePairField = (ComboBox)getItem(currentItemId).getItemProperty(PIECE_PAIR).getValue();
				ComboBox makingChargeType = (ComboBox)getItem(currentItemId).getItemProperty(MAKING_CHARGE_TYPE).getValue();
				TextField quantityTxtField = (TextField)getItem(currentItemId).getItemProperty(QUANTITY).getValue();
				TextField weightTxtField = (TextField)getItem(currentItemId).getItemProperty(WEIGHT).getValue();
				TextField makingChargeTxtField = (TextField)getItem(currentItemId).getItemProperty(MAKING_CHARGE).getValue();
				TextField goldRateTxtField = (TextField)getItem(currentItemId).getItemProperty(SILVER_RATE).getValue();
				TextField goldPriceTxtField = (TextField)getItem(currentItemId).getItemProperty(PRICE).getValue();
				int quantity = NumberUtils.isDigits(quantityTxtField.getValue()) ? NumberUtils.toInt(quantityTxtField.getValue()) : 0;
				double weight = NumberUtils.isNumber(weightTxtField.getValue()) ? NumberUtils.toDouble(weightTxtField.getValue()) : 0.0;
				double makingCharge = NumberUtils.isNumber(makingChargeTxtField.getValue()) ? NumberUtils.toDouble(makingChargeTxtField.getValue()) : 0.0;
				double goldRate = NumberUtils.isNumber(goldRateTxtField.getValue()) ? NumberUtils.toDouble(goldRateTxtField.getValue()) : 0.0;
				if((quantity > 0)
						&& (weight > 0.0)
						&& (makingCharge >= 0.0)
						&& (goldRate > 0.0)
						&& !StringUtils.isBlank(String.valueOf(piecePairField.getValue()))
						&& !StringUtils.isBlank(String.valueOf(makingChargeType.getValue()))){
					double goldPrice = 0.0;
					switch(String.valueOf(makingChargeType.getValue())){
						case "%" :
							goldPrice = ((weight*goldRate)*(1 + makingCharge))/100.0;
							break;
						case "per gm":
							goldPrice = weight*(goldRate + makingCharge);
							break;
						case "net":
							goldPrice = (weight*goldRate) + makingCharge;
							
					}
					
					goldPriceTxtField.setValue(String.valueOf(goldPrice));
					goldPriceTxtField.addStyleName("v-textfield-success");
					goldPriceTxtField.setImmediate(true);
					Notification.show("Item entry complete");
				}else{
					goldPriceTxtField.addStyleName("v-textfield-warning");
					goldPriceTxtField.setImmediate(true);
				}
		        
		    }
		};
	}
	
	private ComboBox getItemNameList(){
		ComboBox itemName = new ComboBox();
		itemName.addItem("Chain");
		itemName.addItem("Necklace");
		itemName.setValue("Chain");
		itemName.setWidth("100%");
		return itemName;
	}
}
