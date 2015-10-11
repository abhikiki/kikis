package views;

import com.abhishek.fmanage.mortgage.data.container.DiamondItemContainer;
import com.abhishek.fmanage.mortgage.data.container.GoldItemContainer;
import com.abhishek.fmanage.mortgage.data.container.SilverItemContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RetailInvoiceView extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;
	private GoldItemContainer goldItemContainer = new GoldItemContainer();
	private SilverItemContainer silverItemContainer = new SilverItemContainer();
	private DiamondItemContainer diamondItemContainer = new DiamondItemContainer();
	
	double totalSilverItemPrice = 0.0;
	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
		//getAddItemButtom
		addComponent(getGoldBillingTable());
		addComponent(getSilverItemTable());
		addComponent(getDiamondTable());
	}

	private Component getDiamondTable() {
		 Table diamondItemTable = new Table();
	        diamondItemTable.setSizeFull();
	        diamondItemTable.addStyleName("borderless");
	        diamondItemTable.setSelectable(false);
	        diamondItemTable.setColumnCollapsingAllowed(false);
	        diamondItemTable.setColumnReorderingAllowed(true);
	        diamondItemTable.setWidth("80%");
	        diamondItemContainer.addDiamondItem();
	        diamondItemTable.setContainerDataSource(diamondItemContainer);
	       
	        diamondItemTable.setVisibleColumns(new Object[] {
	        		DiamondItemContainer.ITEM_NAME,
	        		DiamondItemContainer.QUANTITY,
	        		DiamondItemContainer.PIECE_PAIR,
	        		DiamondItemContainer.GOLD_WEIGHT,
	        		DiamondItemContainer.DIAMOND_WEIGHT,
	        		DiamondItemContainer.DIAMOND_PIECE,
	        		DiamondItemContainer.CERTIFICATE,
	        		DiamondItemContainer.PRICE
	            });
	        diamondItemTable.setFooterVisible(true);
	        diamondItemTable.setMultiSelect(false);
	        diamondItemTable.setPageLength(3);
	        diamondItemTable.setColumnFooter(DiamondItemContainer.PRICE, "Total Silver Price=" + 0.0);
	        return diamondItemTable;
	}

	private Component getSilverItemTable() {
		 Table silverItemTable = new Table();
	        silverItemTable.setSizeFull();
	        silverItemTable.addStyleName("borderless");
	        silverItemTable.setSelectable(false);
	        silverItemTable.setColumnCollapsingAllowed(false);
	        silverItemTable.setColumnReorderingAllowed(true);
	        silverItemTable.setWidth("80%");
	        silverItemContainer.addSilverItem();
	        silverItemTable.setContainerDataSource(silverItemContainer);
	       
	        silverItemTable.setVisibleColumns(new Object[] {
	        		SilverItemContainer.ITEM_NAME,
	        		SilverItemContainer.QUANTITY,
	        		SilverItemContainer.PIECE_PAIR,
	        		SilverItemContainer.WEIGHT,
	        		SilverItemContainer.MAKING_CHARGE_TYPE,
	        		SilverItemContainer.MAKING_CHARGE,
	        		SilverItemContainer.SILVER_RATE,
	        		SilverItemContainer.PRICE
	            });
	        silverItemTable.setFooterVisible(true);
	        silverItemTable.setMultiSelect(false);
	        silverItemTable.setPageLength(4);
	        silverItemTable.setColumnWidth(SilverItemContainer.ITEM_NAME, 150);
	        silverItemTable.setColumnWidth(SilverItemContainer.QUANTITY, 60);
	        silverItemTable.setColumnWidth(SilverItemContainer.WEIGHT, 70);
	        silverItemTable.setColumnWidth(SilverItemContainer.SILVER_RATE, 65);
	        silverItemTable.setColumnWidth(SilverItemContainer.MAKING_CHARGE, 95);
	        silverItemTable.setColumnFooter(SilverItemContainer.PRICE, ((SilverItemContainer)silverItemTable.getContainerDataSource()).getTotal()+"");

	        
	        for (Object obj : silverItemTable.getItemIds()){
	        	TextField itemTxtField = (TextField)(silverItemContainer.getItem(obj).getItemProperty(SilverItemContainer.PRICE).getValue());
	            itemTxtField.setImmediate(true);
	            itemTxtField.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						silverItemTable.setColumnFooter(SilverItemContainer.PRICE, ((SilverItemContainer)silverItemTable.getContainerDataSource()).getTotal()+"");
						
					}
				});
	        }
	        
	        silverItemTable.setImmediate(true);
	        return silverItemTable;
	}

	private Component getGoldBillingTable() {
		 Table goldItemTable = new Table();
	        goldItemTable.setSizeFull();
	        goldItemTable.addStyleName("borderless");
	        goldItemTable.setSelectable(false);
	        goldItemTable.setColumnCollapsingAllowed(false);
	        goldItemTable.setColumnReorderingAllowed(true);
	        goldItemTable.setWidth("80%");
	        goldItemContainer.addGoldItem();
	        goldItemTable.setContainerDataSource(goldItemContainer);
	       
	        goldItemTable.setVisibleColumns(new Object[] {
	        		GoldItemContainer.HALL_MARK_TYPE,
	        		GoldItemContainer.ITEM_NAME,
	        		GoldItemContainer.QUANTITY,
	        		GoldItemContainer.PIECE_PAIR,
	        		GoldItemContainer.WEIGHT,
	        		GoldItemContainer.MAKING_CHARGE_TYPE,
	        		GoldItemContainer.MAKING_CHARGE,
	        		GoldItemContainer.GOLD_RATE,
	        		GoldItemContainer.PRICE
	            });
	        goldItemTable.setFooterVisible(true);
	        goldItemTable.setMultiSelect(false);
	        goldItemTable.setPageLength(4);
	        goldItemTable.setColumnWidth(GoldItemContainer.ITEM_NAME, 150);
	        goldItemTable.setColumnWidth(GoldItemContainer.HALL_MARK_TYPE, 90);
	        goldItemTable.setColumnWidth(GoldItemContainer.QUANTITY, 60);
	        goldItemTable.setColumnWidth(GoldItemContainer.WEIGHT, 70);
	        goldItemTable.setColumnWidth(GoldItemContainer.MAKING_CHARGE, 95);
	        goldItemTable.setColumnWidth(GoldItemContainer.GOLD_RATE, 65);
	        goldItemTable.setColumnWidth(GoldItemContainer.MAKING_CHARGE_TYPE, 100);
	        goldItemTable.setColumnWidth(GoldItemContainer.PIECE_PAIR, 100);
	        goldItemTable.setColumnWidth(GoldItemContainer.PRICE, 120);
	        goldItemTable.setColumnFooter(SilverItemContainer.PRICE, ((GoldItemContainer)goldItemTable.getContainerDataSource()).getTotal()+"");

	        
	        for (Object obj : goldItemTable.getItemIds()){
	        	TextField itemTxtField = (TextField)(goldItemContainer.getItem(obj).getItemProperty(GoldItemContainer.PRICE).getValue());
	            itemTxtField.setImmediate(true);
	            itemTxtField.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						goldItemTable.setColumnFooter(GoldItemContainer.PRICE, ((GoldItemContainer)goldItemTable.getContainerDataSource()).getTotal()+"");
						
					}
				});
	        }
	        goldItemTable.setImmediate(true);
	        return goldItemTable;
	}
}