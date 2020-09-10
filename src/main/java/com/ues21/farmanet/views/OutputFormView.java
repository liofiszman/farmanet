package com.ues21.farmanet.views;

import com.ues21.farmanet.model.*;
import com.ues21.farmanet.service.OutputService;
import com.ues21.farmanet.service.StockService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OutputFormView extends VerticalLayout {

    private OutputService outputService;
    private StockService stockService;
    private UserService userService;
    public Dialog parentDialog;


    H3 title = new H3("Registrar Salida");
    Button save = new Button("Guardar");
    Button close = new Button("Cancelar");

    private List<ReasonEntity> reasonList;
    final private List<OutputItemEntity> outputList = new ArrayList<>();

    Select<ReasonEntity> reasonEntitySelect = new Select<>();
    IntegerField stockEntityTextBox = new IntegerField();
    Label stockItemLabel = new Label();
    Button stockItemAddButton = new Button();



    private Grid<OutputItemEntity> outPutItemsGrid = new Grid<>();

    LocationEntity currentLocation = (LocationEntity)UI.getCurrent().getSession().getAttribute("currentLocation");

    public OutputFormView(OutputService outputService, StockService stockService, UserService userService) {
        this.stockService = stockService;
        this.outputService = outputService;
        this.userService = userService;

        addClassName("output-Form");

        configureReasons();
        updateList();
        configureGrid();


        add(title,reasonEntitySelect,configureStockSearch(),outPutItemsGrid,createButtonsLayout());

    }

    private void configureReasons(){
        reasonList = outputService.getReasons();
        reasonEntitySelect.setLabel("Motivo");
        reasonEntitySelect.setWidth("250px");
        reasonEntitySelect.setItemLabelGenerator(ReasonEntity::getName);
        reasonEntitySelect.setItems(reasonList);
        reasonEntitySelect.setValue(reasonList.get(0));
    }

    private HorizontalLayout configureStockSearch(){
        HorizontalLayout hLayout = new HorizontalLayout();
        stockEntityTextBox.setLabel("Buscar");
        stockEntityTextBox.setPlaceholder("Buscar por ID..");
        stockEntityTextBox.setWidth("250px");
        stockEntityTextBox.setValueChangeMode(ValueChangeMode.LAZY);
        stockEntityTextBox.addValueChangeListener(e -> updateItemLabel());
        stockEntityTextBox.getElement().getStyle().set("margin-bottom", "0px");
        stockEntityTextBox.getElement().getStyle().set("margin-top", "auto");
        stockItemAddButton.setIcon(new Icon(VaadinIcon.PLUS));
        stockItemAddButton.getElement().getStyle().set("margin-bottom", "4px");
        stockItemAddButton.getElement().getStyle().set("margin-top", "auto");
        stockItemAddButton.addClickListener(e -> retrieveItem());
        stockItemAddButton.setEnabled(false);

        stockItemLabel.getStyle().set("margin-bottom", "15px");
        stockItemLabel.getStyle().set("margin-top", "auto");
        stockItemLabel.setHeight("20px");
        stockItemLabel.setWidth("180px");
        stockItemLabel.getStyle().set("overflow","hidden");
        stockItemLabel.getStyle().set("white-space","nowrap");
        stockItemLabel.getStyle().set("text-overflow","ellipsis");


        hLayout.setWidth("100%");
        hLayout.add(stockEntityTextBox,stockItemAddButton,stockItemLabel);


        return hLayout;
    }


    private void configureGrid() {

        outPutItemsGrid.removeAllColumns();
        outPutItemsGrid.setSizeFull();
        outPutItemsGrid.setSelectionMode(Grid.SelectionMode.NONE);


         outPutItemsGrid.addColumn(OutputItemEntity -> OutputItemEntity.getItemByItemId().getName()
        +" " + OutputItemEntity.getItemByItemId().getQuatity() + " " +
                OutputItemEntity.getItemByItemId().getUnitByUnitId().getDescription() ).setHeader("Item").setWidth("250px");

        outPutItemsGrid.addComponentColumn(OutputItemEntity -> createQtyStepper(outPutItemsGrid,OutputItemEntity)).setAutoWidth(true);
        outPutItemsGrid.addComponentColumn(OutputItemEntity -> createDeleteButton(outPutItemsGrid,OutputItemEntity)).setAutoWidth(true);
    }

    private IntegerField createQtyStepper(Grid<OutputItemEntity> grid, OutputItemEntity outputItemEntity){
        final IntegerField numberField = new IntegerField();
        numberField.setValue(outputItemEntity.getQuantity());
        numberField.setHasControls(true);
        numberField.setMin(1);
        numberField.setMax(this.stockService.getByLocation(outputItemEntity.getItemByItemId().getId().toString(),currentLocation).getCurrentStock());
        numberField.addValueChangeListener(e ->setQty(outputItemEntity,numberField.getValue()));
        return numberField;
    }
    private Button createDeleteButton(Grid<OutputItemEntity> grid, OutputItemEntity outputItemEntity) {
        Button deleteButton = new Button("",new Icon(VaadinIcon.CLOSE_SMALL));
         deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
         deleteButton.addClickListener(e -> deleteItem(outputItemEntity));
         return  deleteButton;
    }

    private void setQty(OutputItemEntity outputItemEntity, int qty){

        int index = outputList.indexOf(outputItemEntity);

        outputList.get(index).setQuantity(qty);


    }
    private void deleteItem(OutputItemEntity outputItemEntity){
        outputList.remove(outputItemEntity);
        outPutItemsGrid.getDataProvider().refreshAll();

    }

    private void retrieveItem(){
        StockEntity stockItem = stockService.getByLocation(stockEntityTextBox.getValue().toString(),currentLocation);

        if(stockItem != null) {
            OutputItemEntity outputItemEntity = new OutputItemEntity();
           outputItemEntity.setItemByItemId(stockItem.getItemByItemId());

            outputItemEntity.setId(outputList.size());
            outputItemEntity.setQuantity(1);
            outputItemEntity.setNotes("");
            outputList.add(outputItemEntity);
            outPutItemsGrid.getDataProvider().refreshAll();
        }
    }

    private boolean searchRepeated(StockEntity stockEntity){

        boolean found = false;
        int index  = 0;
        while (!found && index < outputList.size()){
            if(outputList.get(index).getItemByItemId().getId() == stockEntity.getItemByItemId().getId()){
                found = true;
            }else {
                index++;
            }
        }

        return found;
    }

    private void updateItemLabel(){

        var a = stockEntityTextBox.getValue();
        var b = stockEntityTextBox.isEmpty();

        if(stockEntityTextBox.getValue() != null) {
            StockEntity stockEntitySelected = stockService.getByLocation(stockEntityTextBox.getValue().toString(), currentLocation);

            if (stockEntitySelected != null && !searchRepeated(stockEntitySelected)) {
                stockItemAddButton.setEnabled(true);
                stockItemLabel.setText(stockEntitySelected.getItemByItemId().getName() + " " +
                        stockEntitySelected.getItemByItemId().getPresentation() + " " +
                        stockEntitySelected.getItemByItemId().getQuatity() + " " +
                        stockEntitySelected.getItemByItemId().getUnitByUnitId().getDescription());
            } else {
                stockItemAddButton.setEnabled(false);
                stockItemLabel.setText("");
            }
        }else{
            stockItemAddButton.setEnabled(false);
            stockItemLabel.setText("");
        }


    }


    private void updateList() {
        outPutItemsGrid.setItems(outputList);
    }

    private Component createButtonsLayout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");
        horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        close.addClickListener(e -> closeForm());

        horizontalLayout.add(save,close);
        return horizontalLayout;
    }

    private void validateAndSave(){

        for (OutputItemEntity outputItemEntity: outputList) {
            outputItemEntity.setId(null);
        }

        OutputEntity output = new OutputEntity();
        output.setReasonByReasonId(reasonEntitySelect.getValue());
        output.setOutputItemsById(outputList);
        output.setLocationByLocationId(currentLocation);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = userService.findbyUserName(authentication.getName());
        output.setUserByRequesterId(currentUser);
        output.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        outputService.save(output);

        this.stockService.discountStocks(outputList,currentLocation);


        Notification notification = new Notification(
                "Stock Actualizado", 5000,
                Notification.Position.BOTTOM_CENTER);
        notification.open();

        closeForm();
    }

    private void closeForm(){
        parentDialog.close();

    }


}
