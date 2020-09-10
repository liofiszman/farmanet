package com.ues21.farmanet.views;

import com.ues21.farmanet.model.InputRequestEntity;
import com.ues21.farmanet.model.InputRequestItemEntity;
import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.model.StatusEntity;
import com.ues21.farmanet.repository.InputRequestItemRepository;
import com.ues21.farmanet.service.InputRequestService;
import com.ues21.farmanet.service.ItemService;
import com.ues21.farmanet.service.StatusService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InputApprovalFormView extends VerticalLayout {

    private InputRequestEntity inputRequestEntity;

    private InputRequestService inputRequestService;
    private ItemService itemService;
    private UserService userService;
    private StatusService statusService;
    public Dialog parentDialog;


    H3 title = new H3("Validar Solicitud de Mercadería");
    Button save = new Button("Guardar");
    Button close = new Button("Cancelar");

    Label requesterUserLabel = new Label();
    VerticalLayout approverAndNotesLayout = new VerticalLayout();
    TextArea notesTextArea = new TextArea("Notas");

    List<InputRequestItemRepository> inputItems = new ArrayList<>();

    private Grid<InputRequestItemEntity> inPutItemsGrid = new Grid<>();

    LocationEntity currentLocation = (LocationEntity)UI.getCurrent().getSession().getAttribute("currentLocation");

    public InputApprovalFormView(InputRequestEntity inputRequestEntity,InputRequestService inputRequestService, ItemService itemService, UserService userService, StatusService statusService) {
        this.inputRequestEntity = inputRequestEntity;
        this.inputRequestService = inputRequestService;


        this.itemService = itemService;
        this.userService = userService;
        this.statusService = statusService;
        addClassName("input-Form");

        configureApproverAndNotes();
        updateList();
        configureGrid();

        add(title,approverAndNotesLayout,inPutItemsGrid,createButtonsLayout());
       }

    private void configureApproverAndNotes(){
        approverAndNotesLayout.setWidth("100%");


        requesterUserLabel.setText("Solicitante: " + inputRequestEntity.getUserByRequesterId().getFirstName()
                +" "+ inputRequestEntity.getUserByRequesterId().getLastName());
        requesterUserLabel.setWidth("250px");


        notesTextArea.getStyle().set("maxHeight", "80px");
        notesTextArea.getStyle().set("height", "80px");
        notesTextArea.getStyle().set("width", "700px");
        notesTextArea.setValue(inputRequestEntity.getNotes());

        approverAndNotesLayout.add(requesterUserLabel,notesTextArea);
    }




    private void configureGrid() {

        inPutItemsGrid.removeAllColumns();
        inPutItemsGrid.setSizeFull();
        inPutItemsGrid.setSelectionMode(Grid.SelectionMode.NONE);


        inPutItemsGrid.addColumn(InputRequestItemEntity->InputRequestItemEntity.getItemByItemId().getId()).setHeader("ID").setAutoWidth(true);
        inPutItemsGrid.addColumn(InputRequestItemEntity->InputRequestItemEntity.getItemByItemId().getCategoryByCategoryId().getTypeByTypeId().getName()).setHeader("Tipo").setAutoWidth(true);
        inPutItemsGrid.addColumn(InputRequestItemEntity->InputRequestItemEntity.getItemByItemId().getCategoryByCategoryId().getName()).setHeader("Categoría").setAutoWidth(true);
        inPutItemsGrid.addColumn(IntputRequestItemEntity -> IntputRequestItemEntity.getItemByItemId().getName()
        +" " + IntputRequestItemEntity.getItemByItemId().getQuatity() + " " +
                IntputRequestItemEntity.getItemByItemId().getUnitByUnitId().getDescription() ).setHeader("Item").setWidth("250px").setHeader("Item");
        inPutItemsGrid.addColumn(InputRequestItemEntity->InputRequestItemEntity.getItemByItemId().getVendorByVendorId().getName()).setHeader("Proveedor").setAutoWidth(true);
        inPutItemsGrid.addComponentColumn(IntputRequestItemEntity -> createStatusItemSelect(IntputRequestItemEntity)).setAutoWidth(true).setHeader("Estado");
        inPutItemsGrid.addComponentColumn(IntputRequestItemEntity -> deliveryDatePicker(IntputRequestItemEntity)).setAutoWidth(true).setHeader("Fecha de entrega");

    }

    private Select<StatusEntity> createStatusItemSelect(InputRequestItemEntity inputRequestItemEntity){
        Select<StatusEntity> statusSelect = new Select<>();
        statusSelect.setItemLabelGenerator(StatusEntity::getName);
        statusSelect.setItems(statusService.findAll());
        statusSelect.setValue(inputRequestItemEntity.getStatusByStatusId());
        statusSelect.addValueChangeListener(e-> updateItemStatus(inputRequestItemEntity,statusSelect.getValue()));
        return statusSelect;
    }

    private void  updateItemStatus(InputRequestItemEntity inputRequestItemEntity,StatusEntity statusEntity){
        for(InputRequestItemEntity i: inputRequestEntity.getInputRequestItemsById()){
            if(i.equals(inputRequestItemEntity))
                i.setStatusByStatusId(statusEntity);
        }
    }

    private DatePicker deliveryDatePicker(InputRequestItemEntity inputRequestItemEntity){
        DatePicker valueDatePicker = new DatePicker();
        if(inputRequestItemEntity.getDeliveryDate() == null) {
            LocalDate now = LocalDate.now();
            valueDatePicker.setValue(now);
        }else
            valueDatePicker.setValue(inputRequestItemEntity.getDeliveryDate().toLocalDateTime().toLocalDate());
        valueDatePicker.addValueChangeListener(e->updateDeliveryDate(inputRequestItemEntity,valueDatePicker.getValue()));
        return valueDatePicker;
    }


    private void  updateDeliveryDate(InputRequestItemEntity inputRequestItemEntity,LocalDate deliveryDate){
        for(InputRequestItemEntity i: inputRequestEntity.getInputRequestItemsById()){
            if(i.equals(inputRequestItemEntity))
                i.setDeliveryDate(Timestamp.valueOf(deliveryDate.atStartOfDay()));
        }
    }


    private void updateList() {
        inPutItemsGrid.setItems(inputRequestEntity.getInputRequestItemsById());
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

        this.inputRequestService.updateStatus(inputRequestEntity);

        Notification notification = new Notification(
                "Solicitud Actualizada", 5000,
                Notification.Position.BOTTOM_CENTER);
        notification.open();

        closeForm();
    }

    private void closeForm(){
        parentDialog.close();
    }


}
