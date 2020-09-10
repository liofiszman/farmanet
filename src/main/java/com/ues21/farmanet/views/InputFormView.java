package com.ues21.farmanet.views;

import com.ues21.farmanet.model.*;
import com.ues21.farmanet.service.InputRequestService;
import com.ues21.farmanet.service.ItemService;
import com.ues21.farmanet.service.StatusService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
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
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InputFormView extends VerticalLayout {

    private InputRequestService inputRequestService;
    private ItemService itemService;
    private UserService userService;
    private StatusService statusService;
    public Dialog parentDialog;


    H3 title = new H3("Solicitud de Mercadería");
    Button save = new Button("Guardar");
    Button close = new Button("Cancelar");

    final private List<InputRequestItemEntity> inputList = new ArrayList<>();
    private List<UserEntity> userList = new ArrayList<UserEntity>();

    Select<UserEntity> userEntitySelect = new Select<>();
    IntegerField itemEntityTextBox = new IntegerField();
    HorizontalLayout approverAndNotesLayout = new HorizontalLayout();
    TextArea notesTextArea = new TextArea("Notas");
    Label itemLabel = new Label();
    Button itemAddButton = new Button();



    private Grid<InputRequestItemEntity> inPutItemsGrid = new Grid<>();

    LocationEntity currentLocation = (LocationEntity)UI.getCurrent().getSession().getAttribute("currentLocation");

    public InputFormView(InputRequestService inputRequestService, ItemService itemService, UserService userService, StatusService statusService) {
        this.inputRequestService = inputRequestService;
        this.itemService = itemService;
        this.userService = userService;
        this.statusService = statusService;
        addClassName("input-Form");

        configureApproverAndNotes();
        updateList();
        configureGrid();

        add(title,approverAndNotesLayout,configureItemSearch(),inPutItemsGrid,createButtonsLayout());

    }

    private void configureApproverAndNotes(){
        approverAndNotesLayout.setWidth("100%");

        userList = userService.getApprovers();
        userEntitySelect.setLabel("Autorizador");
        userEntitySelect.setWidth("250px");
        userEntitySelect.setItemLabelGenerator(new ItemLabelGenerator<UserEntity>() {
            @Override
            public String apply(UserEntity userEntity) {
                return userEntity.getFirstName() + " " + userEntity.getLastName();
            }
        });
        userEntitySelect.setItems(userList);
        userEntitySelect.setValue(userList.get(0));


        notesTextArea.getStyle().set("maxHeight", "80px");
        notesTextArea.getStyle().set("height", "80px");
        notesTextArea.getStyle().set("width", "700px");
        notesTextArea.setPlaceholder("");

        approverAndNotesLayout.add(userEntitySelect,notesTextArea);
    }


    private HorizontalLayout configureItemSearch(){
        HorizontalLayout hLayout = new HorizontalLayout();
        itemEntityTextBox.setLabel("Buscar");
        itemEntityTextBox.setPlaceholder("Buscar por ID..");
        itemEntityTextBox.setWidth("250px");
        itemEntityTextBox.setValueChangeMode(ValueChangeMode.LAZY);
        itemEntityTextBox.addValueChangeListener(e -> updateItemLabel());
        itemEntityTextBox.getElement().getStyle().set("margin-bottom", "0px");
        itemEntityTextBox.getElement().getStyle().set("margin-top", "auto");
        itemAddButton.setIcon(new Icon(VaadinIcon.PLUS));
        itemAddButton.getElement().getStyle().set("margin-bottom", "4px");
        itemAddButton.getElement().getStyle().set("margin-top", "auto");
        itemAddButton.addClickListener(e -> retrieveItem());
        itemAddButton.setEnabled(false);

        itemLabel.getStyle().set("margin-bottom", "15px");
        itemLabel.getStyle().set("margin-top", "auto");
        itemLabel.setHeight("20px");
        itemLabel.setWidth("400px");
        itemLabel.getStyle().set("overflow","hidden");
        itemLabel.getStyle().set("white-space","nowrap");
        itemLabel.getStyle().set("text-overflow","ellipsis");


        hLayout.setWidth("100%");
        hLayout.add(itemEntityTextBox,itemAddButton,itemLabel);


        return hLayout;
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
        inPutItemsGrid.addComponentColumn(IntputRequestItemEntity -> createQtyStepper(inPutItemsGrid,IntputRequestItemEntity)).setAutoWidth(true).setHeader("Cantidad");
        inPutItemsGrid.addComponentColumn(IntputRequestItemEntity -> createDeleteButton(inPutItemsGrid,IntputRequestItemEntity)).setAutoWidth(true);
    }

    private IntegerField createQtyStepper(Grid<InputRequestItemEntity> grid, InputRequestItemEntity inputRequestItemEntity){
        final IntegerField numberField = new IntegerField();
        numberField.setValue(inputRequestItemEntity.getQuantity());
        numberField.setHasControls(true);
        numberField.setMin(1);
        numberField.addValueChangeListener(e ->setQty(inputRequestItemEntity,numberField.getValue()));
        return numberField;
    }
    private Button createDeleteButton(Grid<InputRequestItemEntity> grid, InputRequestItemEntity inputRequestItemEntity) {
        Button deleteButton = new Button("",new Icon(VaadinIcon.CLOSE_SMALL));
         deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
         deleteButton.addClickListener(e -> deleteItem(inputRequestItemEntity));
         return  deleteButton;
    }

    private void setQty(InputRequestItemEntity inputRequestItemEntity, int qty){

        int index = inputList.indexOf(inputRequestItemEntity);

        inputList.get(index).setQuantity(qty);


    }
    private void deleteItem(InputRequestItemEntity inputRequestItemEntity){
        inputList.remove(inputRequestItemEntity);
        inPutItemsGrid.getDataProvider().refreshAll();

    }

    private void retrieveItem(){
        ItemEntity item = itemService.get(itemEntityTextBox.getValue());

        if(item != null) {
            InputRequestItemEntity inputRequestItemEntity = new InputRequestItemEntity();
            inputRequestItemEntity.setItemByItemId(item);

            inputRequestItemEntity.setId(inputList.size());
            inputRequestItemEntity.setQuantity(1);

            inputList.add(inputRequestItemEntity);
            inPutItemsGrid.getDataProvider().refreshAll();
            itemEntityTextBox.clear();
            itemAddButton.setEnabled(false);
        }
    }

    private boolean searchRepeated(ItemEntity itemEntity){

        boolean found = false;
        int index  = 0;
        while (!found && index < inputList.size()){
            if(inputList.get(index).getItemByItemId().getId() == itemEntity.getId()){
                found = true;
            }else {
                index++;
            }
        }

        return found;
    }

    private void updateItemLabel(){

        if(itemEntityTextBox.getValue() != null) {
            ItemEntity itemSelected = itemService.get(itemEntityTextBox.getValue());

            if (itemSelected != null && !searchRepeated(itemSelected)) {
                itemAddButton.setEnabled(true);
                itemLabel.setText(itemSelected.getName() + " " +
                        itemSelected.getPresentation() + " " +
                        itemSelected.getQuatity() + " " +
                        itemSelected.getUnitByUnitId().getDescription());
            } else {
                itemAddButton.setEnabled(false);
                itemLabel.setText("");
            }
        }else{
            itemAddButton.setEnabled(false);
            itemLabel.setText("");
        }


    }


    private void updateList() {
        inPutItemsGrid.setItems(inputList);
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
        if(inputList.size() > 0) {
            for (InputRequestItemEntity inputRequestItemEntity : inputList) {
                inputRequestItemEntity.setId(null);
                inputRequestItemEntity.setStatusByStatusId(statusService.get(1));
            }

            InputRequestEntity inputRequestEntity = new InputRequestEntity();
            inputRequestEntity.setStatusByStatusId(statusService.get(1));
            inputRequestEntity.setInputRequestItemsById(inputList);
            inputRequestEntity.setLocationByLocationId(currentLocation);
            inputRequestEntity.setNotes(notesTextArea.getValue());

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity currentUser = userService.findbyUserName(authentication.getName());
            inputRequestEntity.setUserByRequesterId(currentUser);
            inputRequestEntity.setUserByApproverId(userEntitySelect.getValue());

            inputRequestEntity.setCreationDate(new Timestamp(System.currentTimeMillis()));
            inputRequestEntity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            inputRequestService.save(inputRequestEntity);

            Notification notification = new Notification(
                    "Solicitud Realizada", 5000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();

            closeForm();
        }else{
            Notification notification = new Notification(
                    "Verifique los items Cargados", 5000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }
    }

    private void closeForm(){
        parentDialog.close();

    }


}

