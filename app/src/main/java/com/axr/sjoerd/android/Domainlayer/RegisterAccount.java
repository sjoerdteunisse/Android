package com.axr.sjoerd.android.Domainlayer;

public class RegisterAccount {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public RegisterAccount(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
