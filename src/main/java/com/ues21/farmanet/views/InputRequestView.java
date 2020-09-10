package com.ues21.farmanet.views;

import com.ues21.farmanet.model.InputRequestEntity;
import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.service.InputRequestService;
import com.ues21.farmanet.service.ItemService;
import com.ues21.farmanet.service.StatusService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value="inputrequest", layout = MainLayout.class)
@PageTitle("Farmanet | Solicitudes de Mercadería")
@CssImport("./styles/shared-styles.css")
public class InputRequestView extends VerticalLayout {

    private InputRequestService inputRequestService;
    private ItemService itemService;
    private UserService userService;
    private StatusService statusService;



    LocationEntity currentLocation;

    Dialog inputFormDialog = new Dialog();
    private InputFormView inputFormView;

    Dialog inputApprovalDialog = new Dialog();
    private InputApprovalFormView inputApprovalFormView;


    private HorizontalLayout inputHeader = new HorizontalLayout();

    private H2 title = new H2("Ingreso de Mercadería");
    private Button inputButton = new Button("Solicitud de Mercadería" , click -> inputFormDialog.open());



    private IntegerField searchTextField = new IntegerField();
    private Grid<InputRequestEntity> grid = new Grid<>(InputRequestEntity.class);

    @Autowired
    public InputRequestView(InputRequestService inputRequestService, ItemService itemService, UserService userService, StatusService statusService) {

        this.inputRequestService = inputRequestService;
        this.itemService = itemService;
        this.userService = userService;
        this.statusService = statusService;

        addClassName("input-view");
        setSizeFull();
        configureSubHeader();
        configureSearch();
        configureGrid();
        configureNewForm();
        configureApprobalForm();


        add(inputHeader,searchTextField,grid);
        setHorizontalComponentAlignment(Alignment.END, searchTextField);
               updateList();
    }



    private void configureSubHeader(){

        title.getElement().getStyle().set("margin-right", "auto");
        inputButton.getElement().getStyle().set("margin-left", "auto");
        inputButton.getElement().getStyle().set("margin-top", "auto");
        inputButton.getElement().getStyle().set("margin-bottom", "auto");


        inputHeader.setWidth("100%");
        inputHeader.add(title,inputButton);
    }

    private void configureSearch() {

        searchTextField.setPlaceholder("Buscar por ID...");
        searchTextField.setWidth("300px");
        searchTextField.setClearButtonVisible(true);
        searchTextField.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
        searchTextField.setValueChangeMode(ValueChangeMode.LAZY);
        searchTextField.addValueChangeListener(e -> updateList());

    }

    private void configureGrid() {
        grid.addClassName("inputRequest-grid");
        grid.setSizeFull();

        grid.removeAllColumns();
        grid.addColumn(InputRequestEntity::getId).setHeader("ID").setAutoWidth(true).setSortable(true);
        grid.addComponentColumn(InputRequestEntity -> statusLabel(grid,InputRequestEntity)).setHeader("Estado").setAutoWidth(true).setSortable(true);
        grid.addColumn(InputRequestEntity ->InputRequestEntity.getUserByRequesterId().getFirstName() + " " + InputRequestEntity.getUserByRequesterId().getLastName()).setHeader("Solicitante").setAutoWidth(true).setSortable(true);
        grid.addColumn(InputRequestEntity ->InputRequestEntity.getUserByApproverId().getFirstName() + " " + InputRequestEntity.getUserByApproverId().getLastName()).setHeader("Validado por").setAutoWidth(true).setSortable(true);
        grid.addColumn(InputRequestEntity::getUpdateTime).setHeader("Ultima Actualización").setAutoWidth(true).setSortable(true);
        grid.addComponentColumn(InputRequestEntity -> createDetailButton(grid,InputRequestEntity)).setAutoWidth(true);

    }

    private Button createDetailButton(Grid<InputRequestEntity> grid, InputRequestEntity inputRequestEntity) {
        Button detailButton = new Button("",new Icon(VaadinIcon.EDIT));
        detailButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        detailButton.addClickListener(e->createApprovalForm(inputRequestEntity));
        return  detailButton;
    }

    private Label statusLabel(Grid<InputRequestEntity> grid, InputRequestEntity inputRequestEntity){

        Label label = new Label();
        String statusString = inputRequestEntity.getStatusByStatusId().getName();
        label.setText(statusString);
        label.getElement().getStyle().set("display","inline-block");
        label.getElement().getStyle().set("padding","0 15px");
        label.getElement().getStyle().set("font-size","16px");
        label.getElement().getStyle().set("line-height","30px");
        label.getElement().getStyle().set("border-radius","5px");
        label.getElement().getStyle().set("text-align", "center");

        switch (inputRequestEntity.getStatusByStatusId().getId()){
            case 1:
                label.getElement().getStyle().set("background-color","#FFB533");
                break;
            case 2:
                label.getElement().getStyle().set("background-color","#FF3333");
                break;
            case 3:
                label.getElement().getStyle().set("background-color","#05D505");
                break;
            case 4:
                label.getElement().getStyle().set("background-color","#03A203");
                break;
            case 5:
                label.getElement().getStyle().set("background-color","#FFB533");
                break;
            case 6:
                label.getElement().getStyle().set("background-color","#FFB533");
                break;
        }

        return label;
    }


    private void configureNewForm(){
        createForm();
        inputFormDialog.setHeight("700px");
        inputFormDialog.setWidth("1000");
        inputFormDialog.setCloseOnOutsideClick(false);
        inputFormDialog.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {
            @Override
            public void onComponentEvent(GeneratedVaadinDialog.OpenedChangeEvent<Dialog> dialogOpenedChangeEvent) {
                if (!dialogOpenedChangeEvent.isOpened()) {
                    updateList();
                    createForm();
                }
            }
        });
    }


    private void configureApprobalForm(){

        inputFormDialog.setHeight("750px");
        inputFormDialog.setWidth("1250");
        inputFormDialog.setCloseOnOutsideClick(false);

    }

    private void createApprovalForm(InputRequestEntity inputRequestEntity){
        inputApprovalFormView = new InputApprovalFormView(inputRequestEntity,this.inputRequestService,this.itemService,this.userService,this.statusService);

        inputApprovalFormView.parentDialog = inputApprovalDialog;
        inputApprovalFormView.setHeight("700px");
        inputApprovalFormView.setWidth("1200px");
        inputApprovalDialog.removeAll();
        inputApprovalDialog.add(inputApprovalFormView);
        inputApprovalDialog.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {
            @Override
            public void onComponentEvent(GeneratedVaadinDialog.OpenedChangeEvent<Dialog> dialogOpenedChangeEvent) {
                if (!dialogOpenedChangeEvent.isOpened()) {
                    updateList();
                }
            }
        });

        inputApprovalDialog.open();


    }


    private void createForm(){
        inputFormView = new InputFormView(this.inputRequestService,this.itemService,this.userService,this.statusService);

        inputFormView.parentDialog = inputFormDialog;
        inputFormView.setHeight("650px");
        inputFormView.setWidth("950px");
        inputFormDialog.removeAll();
        inputFormDialog.add(inputFormView);


    }


    private void updateList() {
        currentLocation = (LocationEntity) UI.getCurrent().getSession().getAttribute("currentLocation");

            grid.setItems(inputRequestService.findAllbyLocationAndId(currentLocation,searchTextField.getValue()));

    }




}