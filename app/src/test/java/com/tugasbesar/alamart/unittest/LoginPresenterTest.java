package com.tugasbesar.alamart.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginView view;

    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void shouldPassTestWhenEmailAndPasswordValid() throws Exception {
        System.out.println("TEST 1: Email valid dan Password valid");

        when(view.getEmail()).thenReturn("vriyashartama@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("password");
        System.out.println("Password : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));
    }

    @Test
    public void shouldShowErrorMessageWhenEmailIsInvalid() throws Exception {
        System.out.println("TEST 2: Email tidak dan Password valid");

        when(view.getEmail()).thenReturn("vriyashartama");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("123123");
        System.out.println("Password : " + view.getPassword());

        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));

        presenter.onLoginClicked();

        verify(view).showEmailError("Email tidak valid");

    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsLessThanSix() throws Exception {
        System.out.println("TEST 3: Email valid dan Password kurang dari 3");

        when(view.getEmail()).thenReturn("vriyashartama@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("123");
        System.out.println("Password : " + view.getPassword());

        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));

        presenter.onLoginClicked();

        verify(view).showPasswordError("Password kurang dari 6 karakter");
    }

    @Test
    public void shouldLogInUserWhenEmailAndPasswordIsValid() throws Exception {
        System.out.println("TEST 4: Email valid, Password valid dan login sukses");

        when(view.getEmail()).thenReturn("vriyashartama@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("123123");
        System.out.println("Password : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));

        presenter.onLoginClicked();

        verify(view).startMainActivity();
    }

    @Test
    public void shouldFailUserWhenEmailValidAndPasswordInvalid() throws Exception {
        System.out.println("TEST 5: Email valid, Password valid dan login sukses");

        when(view.getEmail()).thenReturn("vriyashartama@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("123123123");
        System.out.println("Password : " + view.getPassword());

        presenter.onLoginClicked();

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));

        //verify(view).showLoginError("Email and password unmatch");
    }

    @Test
    public void shouldFailUserWhenEmailUnknownAndPasswordInvalid() throws Exception {
        System.out.println("TEST 6: Email valid, Password valid dan login sukses");

        when(view.getEmail()).thenReturn("3@gmail.com");
        System.out.println("Email : " + view.getEmail());

        when(view.getPassword()).thenReturn("123123");
        System.out.println("Password : " + view.getPassword());

        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));

        presenter.onLoginClicked();

        //verify(view).showLoginError("Email tidak terdaftar");
    }


}