package com.ues21.farmanet.views;

import com.ues21.farmanet.model.LocationEntity;
import com.ues21.farmanet.model.UserEntity;
import com.ues21.farmanet.security.FarmanetUserDetailService;
import com.ues21.farmanet.service.LocationService;
import com.ues21.farmanet.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;


@CssImport("./styles/shared-styles.css")
@Theme(value = Lumo.class)
public class MainLayout extends AppLayout {


    private UserService userService;
    private LocationService locationService;
    private FarmanetUserDetailService userDetailService = new FarmanetUserDetailService();


    private Label locationLabel = new Label();
    private Select<LocationEntity> locationSelect = new Select<>();
    private List<LocationEntity> locationList = new ArrayList<LocationEntity>();


    @Autowired
    public MainLayout(UserService userService,LocationService locationService) {
        this.userService = userService;
        this.locationService = locationService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Farmanet");
        logo.addClassName("logo");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);


        header.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        header.add(locationLayout());
        addToNavbar(header);

    }

    private HorizontalLayout locationLayout(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setDefaultVerticalComponentAlignment(
                FlexComponent.Alignment.CENTER);


        locationLabel.setText("Sucursal");
        locationSelect.setItemLabelGenerator(LocationEntity::getName);
        locationList = locationService.findAll();
        locationSelect.setItems(locationList);



        LocationEntity locationEntity =  (LocationEntity)UI.getCurrent().getSession().getAttribute("currentLocation");

        if(locationEntity == null){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity currentUser = userService.findbyUserName(authentication.getName());
            UI.getCurrent().getSession().setAttribute("currentLocation",currentUser.getLocationByDefaultLocationId());
            locationEntity = currentUser.getLocationByDefaultLocationId();
        }

        locationSelect.setValue(locationEntity);

        locationSelect.addValueChangeListener(e ->changeLocation());



        horizontalLayout.getElement().getStyle().set("margin-left", "auto");
        horizontalLayout.getElement().getStyle().set("margin-top", "auto");
        horizontalLayout.getElement().getStyle().set("margin-bottom", "auto");

        horizontalLayout.add(locationLabel,locationSelect);
        return horizontalLayout;
    }




    private void createDrawer() {

        RouterLink stockLink = new RouterLink("Stock", StockView.class);
        stockLink.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink inputRequestLink = new RouterLink("Solicitudes de Mercadería", InputRequestView.class);
        inputRequestLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(stockLink), new VerticalLayout(inputRequestLink));

    }

    private void changeLocation(){
        UI.getCurrent().getSession().setAttribute("currentLocation",locationSelect.getValue());
        UI.getCurrent().getPage().reload();

    }
}




