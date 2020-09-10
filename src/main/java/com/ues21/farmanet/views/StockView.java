package com.ues21.farmanet.views;

import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.model.StockEntity;
import com.ues21.farmanet.service.OutputService;
import com.ues21.farmanet.service.StockService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value="", layout = MainLayout.class)
@PageTitle("Farmanet | Stock")
public class StockView extends VerticalLayout {

    // Other fields omitted
    LocationEntity currentLocation;


    private Grid<StockEntity> grid = new Grid<>(StockEntity.class);
    private StockService stockService;
    private OutputService outputService;
    private UserService userService;

    private HorizontalLayout stockHeader = new HorizontalLayout();
    private H2 title = new H2("Stock");

    private TextField searchTextField = new TextField();

    Dialog outputFormDialog = new Dialog();
    private OutputFormView outputFormView;
    private Button outputButton = new Button("Registrar Salida" ,click -> outputFormDialog.open());

    @Autowired
    public StockView(StockService stockService, OutputService outputService, UserService userService) {

        this.stockService = stockService;
        this.outputService = outputService;
        this.userService = userService;

        addClassName("stock-view");
        setSizeFull();
        configureSubHeader();
        configureSearch();
        configureGrid();
        configureForm();


        add(stockHeader,searchTextField,grid);

        setHorizontalComponentAlignment(Alignment.END, searchTextField);
        updateList();

    }

    private void configureSubHeader(){

        title.getElement().getStyle().set("margin-right", "auto");
        outputButton.getElement().getStyle().set("margin-left", "auto");
        outputButton.getElement().getStyle().set("margin-top", "auto");
        outputButton.getElement().getStyle().set("margin-bottom", "auto");


        stockHeader.setWidth("100%");
        stockHeader.add(title,outputButton);
    }
    private void configureGrid() {
        grid.addClassName("stock-grid");
        grid.setSizeFull();

        grid.removeAllColumns();
        grid.addColumn(StockEntity -> StockEntity.getItemByItemId().getId()).setHeader("Id").setAutoWidth(true).setSortable(true);
        grid.addColumn(StockEntity -> StockEntity.getItemByItemId().getName()).setHeader("Ítem").setSortable(true);
        grid.addColumn(StockEntity -> StockEntity.getItemByItemId().getPresentation()).setHeader("Presentación").setSortable(true);
        grid.addColumn(StockEntity -> StockEntity.getItemByItemId().getQuatity()+" " + StockEntity.getItemByItemId().getUnitByUnitId().getDescription()).setHeader("Tamaño").setSortable(true);
        grid.addColumn(StockEntity -> StockEntity.getCurrentStock()).setHeader("Stock").setAutoWidth(true).setSortable(true);
        grid.addColumn(StockEntity -> StockEntity.getBatchNumber()).setHeader("Nro de Lote").setSortable(true);
        grid.addColumn(StockEntity::getExpirationDate).setHeader("Fecha Vto.").setAutoWidth(true).setSortable(true);

    }


    private void configureSearch() {

        searchTextField.setPlaceholder("Buscar por ID o Item...");
        searchTextField.setWidth("300px");
        searchTextField.setClearButtonVisible(true);
        searchTextField.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
        searchTextField.setValueChangeMode(ValueChangeMode.LAZY);
        searchTextField.addValueChangeListener(e -> updateList());
    }

    private void configureForm(){
        createForm();
        outputFormDialog.setHeight("600px");
        outputFormDialog.setWidth("600px");
        outputFormDialog.setCloseOnOutsideClick(false);
        outputFormDialog.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {
         @Override
        public void onComponentEvent(GeneratedVaadinDialog.OpenedChangeEvent<Dialog> dialogOpenedChangeEvent) {
             if (!dialogOpenedChangeEvent.isOpened()) {
                 updateList();
                createForm();
             }
         }
        });
    }

    private void createForm(){
        outputFormView = new OutputFormView(this.outputService,this.stockService,this.userService);
        outputFormView.parentDialog = outputFormDialog;
        outputFormView.setHeight("550px");
        outputFormView.setWidth("550px");
        outputFormDialog.removeAll();
        outputFormDialog.add(outputFormView);
    }


    private void updateList() {
        currentLocation = (LocationEntity)UI.getCurrent().getSession().getAttribute("currentLocation");

        grid.setItems(stockService.findAll(searchTextField.getValue(),currentLocation));
    }



}

