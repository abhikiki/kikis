package views;

import java.util.Collection;

import com.abhishek.fmanage.mortgage.data.container.DiamondItemContainer;
import com.abhishek.fmanage.mortgage.data.container.GoldItemContainer;
import com.abhishek.fmanage.mortgage.data.container.SilverItemContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class RetailInvoiceView extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;
	private GoldItemContainer goldItemContainer = new GoldItemContainer();
	private SilverItemContainer silverItemContainer = new SilverItemContainer();
	private DiamondItemContainer diamondItemContainer = new DiamondItemContainer();
	private Table goldBillingTable = new Table();
	
	double totalSilverItemPrice = 0.0;
	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
		addComponent(getGoldBillingLayout());
		addComponent(getSilverItemTable());
		addComponent(getDiamondTable());
	}

	private VerticalLayout getGoldBillingLayout() {
		 ThemeResource resource = new ThemeResource("img/addButtonSmall.jpg");
	        Image addNewGoldItemimage = new Image("", resource);
	        addNewGoldItemimage.setHeight("30px");
	        addNewGoldItemimage.setWidth("30px");
	        addNewGoldItemimage.setDescription("Add New Item");
	        addNewGoldItemimage.addClickListener(new MouseEvents.ClickListener()
	        {
				private static final long serialVersionUID = 1L;

				@Override
	            public void click(com.vaadin.event.MouseEvents.ClickEvent event)
	            {
					goldItemContainer.addGoldItem();
	                //goldBillingTable.setPageLength(goldItemContainer.size());
	                addCustomTableDataContainerValueChange(goldBillingTable);
	            }
	        });
	        
		VerticalLayout goldLayout = new VerticalLayout();
		goldLayout.setSizeFull();
		goldBillingTable = getGoldBillingTable();
		goldLayout.addComponent(addNewGoldItemimage);
		goldLayout.addComponent(goldBillingTable);
		goldLayout.setExpandRatio(goldBillingTable, 3);
		goldLayout.setExpandRatio(addNewGoldItemimage, 1f);
	//	goldLayout.setComponentAlignment(newGoldItemBtn, Alignment.TOP_RIGHT);
		return goldLayout;
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
	        diamondItemTable.setPageLength(2);
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

	private Table getGoldBillingTable() {
		 Table goldItemTable = new Table();
	        goldItemTable.setSizeFull();
	        goldItemTable.addStyleName("borderless");
	        goldItemTable.setSelectable(false);
	        goldItemTable.setColumnCollapsingAllowed(false);
	        goldItemTable.setColumnReorderingAllowed(true);
	        goldItemTable.setWidth("85%");
	        goldItemContainer.addGoldItem();
	        goldItemTable.setContainerDataSource(goldItemContainer);
	        goldItemTable.addStyleName("gold-table-header");
	        goldItemTable.addStyleName("gold-table-footer");
	        goldItemTable.addStyleName("gold-table-footer-container");
	        goldItemTable.addStyleName("gold-table-row");
	        goldItemTable.setVisibleColumns(new Object[] {
	        		GoldItemContainer.DELETE,
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
	        goldItemTable.setPageLength(5);
	        goldItemTable.setColumnWidth(GoldItemContainer.DELETE, 70);
	        goldItemTable.setColumnWidth(GoldItemContainer.ITEM_NAME, 150);
	        goldItemTable.setColumnWidth(GoldItemContainer.HALL_MARK_TYPE, 90);
	        goldItemTable.setColumnWidth(GoldItemContainer.QUANTITY, 60);
	        goldItemTable.setColumnWidth(GoldItemContainer.WEIGHT, 70);
	        goldItemTable.setColumnWidth(GoldItemContainer.MAKING_CHARGE, 95);
	        goldItemTable.setColumnWidth(GoldItemContainer.GOLD_RATE, 65);
	        goldItemTable.setColumnWidth(GoldItemContainer.MAKING_CHARGE_TYPE, 100);
	        goldItemTable.setColumnWidth(GoldItemContainer.PIECE_PAIR, 100);
	        goldItemTable.setColumnWidth(GoldItemContainer.PRICE, 120);
	        goldItemTable.setColumnFooter(GoldItemContainer.PRICE, ((GoldItemContainer)goldItemTable.getContainerDataSource()).getTotal() +"");
	        goldItemTable.setColumnFooter(GoldItemContainer.DELETE, ("No. Of Items=" + goldItemTable.size()));

	        addCustomTableDataContainerValueChange(goldItemTable);
	        goldItemTable.setImmediate(true);
	        return goldItemTable;
	}

	private void addCustomTableDataContainerValueChange(Table goldItemTable) {
		Collection<?> itemIdsList = goldItemTable.getItemIds();
		goldItemTable.setColumnFooter(GoldItemContainer.DELETE, ("Items=" + goldItemTable.size()));
		for (Object obj : itemIdsList){
			TextField itemTxtField = (TextField)(goldItemContainer.getItem(obj).getItemProperty(GoldItemContainer.PRICE).getValue());
		    itemTxtField.setImmediate(true);
		    itemTxtField.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					goldItemTable.setColumnFooter(GoldItemContainer.PRICE, "Sum=" + ((GoldItemContainer)goldItemTable.getContainerDataSource()).getTotal().toString());
				}
			});
		    Image deleteImage = (Image)(goldItemContainer.getItem(obj).getItemProperty(GoldItemContainer.DELETE).getValue());
		    deleteImage.addClickListener(new MouseEvents.ClickListener()
	           {
	               @Override
	               public void click(com.vaadin.event.MouseEvents.ClickEvent event)
	               {
	            	   goldItemTable.setColumnFooter(GoldItemContainer.PRICE, "Sum=" + ((GoldItemContainer)goldItemTable.getContainerDataSource()).getTotal());
	            	   goldItemTable.setColumnFooter(GoldItemContainer.DELETE, ("Items=" + goldItemTable.size()));
	               }
	           });
		}
	}
}
