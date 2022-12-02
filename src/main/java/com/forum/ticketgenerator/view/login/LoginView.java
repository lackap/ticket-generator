package com.forum.ticketgenerator.view.login;

import com.forum.ticketgenerator.event.SearchEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import java.io.IOException;

@Route("login")
@PageTitle("Login")
@PermitAll
@UIScope
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

        private LoginForm login = new LoginForm();

        private Button createAccount;

        public LoginView() {
                addClassName("login-view");
                setSizeFull();

                setJustifyContentMode(JustifyContentMode.CENTER);
                setAlignItems(Alignment.CENTER);

                login.setAction("login");

                createAccount = new Button();
                createAccount.setText("Créer compte");
                createAccount.addClickListener(buttonClickEvent -> {
                        createAccount.getUI().ifPresent(ui -> ui.navigate(AccountCreationView.class));
                });
                add(new H1("Authentification"), login, createAccount);
        }

        @Override
        public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
                if(beforeEnterEvent.getLocation()
                        .getQueryParameters()
                        .getParameters()
                        .containsKey("error")) {
                        login.setError(true);
                }
        }
}
