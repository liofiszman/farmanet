package com.ues21.farmanet.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.userdetails.UserDetailsService;

@Route
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
    private UserDetailsService userService;


    private LoginForm login = new LoginForm();

    public LoginView(UserDetailsService userService) {

        this.userService = userService;

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        login.setForgotPasswordButtonVisible(false);
        login.setAction("login");
        login.setI18n(createLoginI18n());

        add(new H1("Farmanet"), login);
    }


    private LoginI18n createLoginI18n(){
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.setForm(new LoginI18n.Form());
        i18n.setErrorMessage(new LoginI18n.ErrorMessage());


        i18n.getHeader().setTitle("Farmanet");
        i18n.getHeader().setDescription("Sistema de stock");
        i18n.getForm().setUsername("Usuario (Email)"); // this is the one you asked for.
        i18n.getForm().setTitle("Acceso");
        i18n.getForm().setSubmit("Login");
        i18n.getForm().setPassword("Contraseña");
        i18n.getForm().setForgotPassword("");
        i18n.getErrorMessage().setTitle("Usuario / Contraseña invalida");
        i18n.getErrorMessage()
                .setMessage("Confirme su usuario / contraseña e intente nuevamente");

        return i18n;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

    /*
    private boolean authenticate(AbstractLogin.LoginEvent e) {
        boolean isAuthenticated = false;
        List<UserEntity> users = userService.();
        int counter = 0;
        while (!isAuthenticated && counter < users.size()) {
            if (users.get(counter).getEmail() == e.getUsername())
                isAuthenticated = true;
            else
                counter++;
        }

        return isAuthenticated;
    }*/
}