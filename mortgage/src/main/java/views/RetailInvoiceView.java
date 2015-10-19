package views;

import java.io.File;
import java.util.Collection;

import com.abhishek.fmanage.mortgage.data.container.CustomItemContainerInterface;
import com.abhishek.fmanage.mortgage.data.container.DiamondItemContainer;
import com.abhishek.fmanage.mortgage.data.container.GoldItemContainer;
import com.abhishek.fmanage.mortgage.data.container.ItemContainerType;
import com.abhishek.fmanage.mortgage.data.container.SilverItemContainer;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class RetailInvoiceView extends VerticalLayout implements View{

	private static final long serialVersionUID = 1L;
	private GoldItemContainer goldItemContainer = new GoldItemContainer();
	private SilverItemContainer silverItemContainer = new SilverItemContainer();
	private DiamondItemContainer diamondItemContainer = new DiamondItemContainer();
	private Table goldBillingTable = new Table();
	private Table silverBillingTable = new Table();
	private Table diamondBillingTable = new Table(); 
	private VerticalLayout retailViewVerticalLayout = new VerticalLayout();
	private Panel p = new Panel();
	
	double totalSilverItemPrice = 0.0;
	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();
		goldBillingTable = getGoldBillingTable();
		silverBillingTable = getSilverItemTable();
		diamondBillingTable = getDiamondTable();
		//retailViewVerticalLayout.setSizeFull();
		//retailViewVerticalLayout.addComponent(testpdf());
		VerticalLayout goldBillingLayout = getBillingLayout(goldBillingTable, goldItemContainer, ItemContainerType.GOLD);
		VerticalLayout silverBillingLayout = getBillingLayout(silverBillingTable, silverItemContainer, ItemContainerType.SILVER);
		VerticalLayout diamondBillingLayout = getBillingLayout(diamondBillingTable, diamondItemContainer, ItemContainerType.DIAMOND);
		retailViewVerticalLayout.addComponent(getToolbar());
		retailViewVerticalLayout.addComponent(goldBillingLayout);
		retailViewVerticalLayout.addComponent(silverBillingLayout);
		retailViewVerticalLayout.addComponent(diamondBillingLayout);
		//retailViewVerticalLayout.set
		retailViewVerticalLayout.setSpacing(true);
		//retailViewVerticalLayout.setHeight("900px");
		//addComponent(testpdf());
		//p.setHeight("600px");
		//p.setWidth("100%");
		p.setSizeFull();
		p.setContent(retailViewVerticalLayout);
		addComponent(p);
		
	}

	private HorizontalLayout getToolbar(){
		HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        toolbar.setSpacing(true);
        toolbar.setMargin(true);
        toolbar.addStyleName("toolbar");
        
        Button newBillBtn = new Button("New Bill");
        newBillBtn.setSizeUndefined();

        newBillBtn.addClickListener(new Button.ClickListener() {
            private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo("/retailbilling");
            }
        });
//        Label title = new Label("All Transactions");
//        title.addStyleName("h1");
//        title.setSizeUndefined();
        toolbar.addComponent(newBillBtn);
        toolbar.setComponentAlignment(newBillBtn, Alignment.MIDDLE_LEFT);
        return toolbar;
	}
	private Image testpdf() {
		 ThemeResource resource = new ThemeResource("img/addButtonSmall.jpg");
	        Image addNewItemimage = new Image("", resource);
	        addNewItemimage.setHeight("30px");
	        addNewItemimage.setWidth("30px");
	        String description = "";
	        addNewItemimage.setDescription(description);
	        addNewItemimage.addClickListener(new MouseEvents.ClickListener()
	        {
				private static final long serialVersionUID = 1L;

				@Override
	            public void click(com.vaadin.event.MouseEvents.ClickEvent event)
	            {
					File pdfFile = new File("C:\\Data\\eclipse_projects\\9_15_2014\\ENV_DIR\\workspace\\Itext\\PDFGenerator.pdf");

	                Embedded pdf = new Embedded("", new FileResource(pdfFile));
	                pdf.setMimeType("application/pdf");
	                pdf.setType(Embedded.TYPE_BROWSER);
	                pdf.setSizeFull();
	                
					 Window w = new Window("Bill");
					 w.setModal(true);
					 w.center();
					 w.setCloseShortcut(KeyCode.ESCAPE, null);
					 w.setResizable(false);
					 w.setWidth("90%");
					 w.setHeight("90%");
					 w.setClosable(false);
					 w.addStyleName("no-vertical-drag-hints");
					 w.addStyleName("no-horizontal-drag-hints");
					 w.setContent(pdf);
		             UI.getCurrent().addWindow(w);
		             w.focus();

		             //getUI().getNavigator().navigateTo("/retailbilling");
		             //Navigator nav = new Navigator(getUI(), this);
		             //nav.navigateTo("/" + "retailbilling");
			    }
	        });
		return addNewItemimage;
	}

	private VerticalLayout getBillingLayout(Table table, CustomItemContainerInterface container, ItemContainerType itemContainerType) {
		 ThemeResource resource = new ThemeResource("img/addButtonSmall.jpg");
	        Image addNewItemimage = new Image("", resource);
	        addNewItemimage.setHeight("30px");
	        addNewItemimage.setWidth("30px");
	        String description = "";
	        switch(itemContainerType.ordinal()){
	        	case 0: description = "Add Gold Item";
	        		break;
	        	case 1: description = "Add Silver Item";
	        		break;
	        	case 2: description = "Add Diamond Item";
	        }
	        addNewItemimage.setDescription(description);
	        addNewItemimage.addClickListener(new MouseEvents.ClickListener()
	        {
				private static final long serialVersionUID = 1L;

				@Override
	            public void click(com.vaadin.event.MouseEvents.ClickEvent event)
	            {
					container.addCustomItem();
					//table.setHeight(String.valueOf((table.getHeight() + 10)) + "px");
					table.setImmediate(true);
	                addCustomTableDataContainerValueChange(table, container);
	            }
	        });
	        
	        
		VerticalLayout itemHolderLayout = new VerticalLayout();
		//itemHolderLayout.setSizeFull();
		itemHolderLayout.addComponent(addNewItemimage);
		itemHolderLayout.addComponent(table);
		itemHolderLayout.setSpacing(true);
		//itemHolderLayout.setExpandRatio(table, 3);
		//itemHolderLayout.setExpandRatio(addNewItemimage, 2f);
		return itemHolderLayout;
	}

	private Table getDiamondTable() {
		 Table diamondItemTable = new Table();
	        diamondItemTable.setSizeFull();
	        diamondItemTable.addStyleName("borderless");
	        diamondItemTable.setSelectable(false);
	        diamondItemTable.setColumnCollapsingAllowed(false);
	        diamondItemTable.setColumnReorderingAllowed(true);
	        diamondItemTable.setWidth("95%");
	        diamondItemContainer.addCustomItem();
	        //diamondItemTable.addStyleName("billing-table-row");
	        diamondItemTable.setContainerDataSource(diamondItemContainer);
	       
	        diamondItemTable.setVisibleColumns(new Object[] {
	        		DiamondItemContainer.DELETE,
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
	        diamondItemTable.setColumnFooter(DiamondItemContainer.PRICE, ((DiamondItemContainer)diamondItemTable.getContainerDataSource()).getTotal()+"");

	        for (Object obj : diamondItemTable.getItemIds()){
	        	TextField itemTxtField = (TextField)(diamondItemContainer.getItem(obj).getItemProperty(DiamondItemContainer.PRICE).getValue());
	            itemTxtField.setImmediate(true);
	            itemTxtField.addValueChangeListener(new ValueChangeListener() {
					private static final long serialVersionUID = 1L;

					@Override
					public void valueChange(ValueChangeEvent event) {
						diamondItemTable.setColumnFooter(SilverItemContainer.PRICE, ((DiamondItemContainer)diamondItemTable.getContainerDataSource()).getTotal()+"");
						
					}
				});
	        }
	        addCustomTableDataContainerValueChange(diamondItemTable, diamondItemContainer);
	        diamondItemTable.setImmediate(true);
	        return diamondItemTable;
	}

	private Table getSilverItemTable() {
		 Table silverItemTable = new Table();
	        silverItemTable.setSizeFull();
	        silverItemTable.addStyleName("borderless");
	        silverItemTable.setSelectable(false);
	        silverItemTable.setColumnCollapsingAllowed(false);
	        silverItemTable.setColumnReorderingAllowed(true);
	        silverItemTable.setWidth("95%");
	        silverItemContainer.addCustomItem();
	        silverItemTable.addStyleName("silver-table-header");
	        silverItemTable.addStyleName("silver-table-footer");
	        silverItemTable.addStyleName("silver-table-footer-container");
	        //silverItemTable.addStyleName("billing-table-row");
	        silverItemTable.setContainerDataSource(silverItemContainer);
	       
	        silverItemTable.setVisibleColumns(new Object[] {
	        		SilverItemContainer.DELETE,
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
	        silverItemTable.setColumnWidth(SilverItemContainer.DELETE, 70);
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
	        addCustomTableDataContainerValueChange(silverItemTable, silverItemContainer);
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
	        goldItemTable.setWidth("95%");
	        goldItemContainer.addCustomItem();
	        goldItemTable.setContainerDataSource(goldItemContainer);
	        goldItemTable.addStyleName("gold-table-header");
	        goldItemTable.addStyleName("gold-table-footer");
	        goldItemTable.addStyleName("gold-table-footer-container");
	        //goldItemTable.addStyleName("billing-table-row");
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
	        goldItemTable.setPageLength(4);
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
	        addCustomTableDataContainerValueChange(goldItemTable, goldItemContainer);
	        goldItemTable.setImmediate(true);
	        return goldItemTable;
	}

	private void addCustomTableDataContainerValueChange(Table table, CustomItemContainerInterface container) {
		Collection<?> itemIdsList = table.getItemIds();
		table.setColumnFooter(GoldItemContainer.DELETE, ("Items=" + table.size()));
		for (Object obj : itemIdsList){
			TextField itemTxtField = (TextField)(((IndexedContainer)container).getItem(obj).getItemProperty(GoldItemContainer.PRICE).getValue());
		    itemTxtField.setImmediate(true);
		    itemTxtField.addValueChangeListener(new ValueChangeListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					table.setColumnFooter(GoldItemContainer.PRICE, "Sum=" + ((CustomItemContainerInterface)table.getContainerDataSource()).getTotal().toString());
				}
			});
		    Image deleteImage = (Image)(((IndexedContainer)container).getItem(obj).getItemProperty(GoldItemContainer.DELETE).getValue());
		    deleteImage.addClickListener(new MouseEvents.ClickListener()
	           {
	               @Override
	               public void click(com.vaadin.event.MouseEvents.ClickEvent event)
	               {
	            	   table.setColumnFooter(GoldItemContainer.PRICE, "Sum=" + ((CustomItemContainerInterface)table.getContainerDataSource()).getTotal());
	            	   table.setColumnFooter(GoldItemContainer.DELETE, ("Items=" + table.size()));
	               }
	           });
		}
	}
}
