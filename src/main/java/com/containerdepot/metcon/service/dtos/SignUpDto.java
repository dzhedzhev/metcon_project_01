package com.containerdepot.metcon.service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpDto {
    @NotBlank(message = "Username must not be empty.")
    @Size(min = 4, max = 20, message = "Username size must be between 4 and 20 symbols.")
    private String username;
    @NotBlank(message = "Password must not be empty.")
    @Size(min = 8, max = 20, message = "Password size must be between 8 and 20 symbols.")
    private String password;
    @NotBlank(message = "Password must not be empty.")
    @Size(min = 8, max = 20, message = "Password size must be between 8 and 20 symbols.")
    private String confirmPassword;
    @NotBlank(message = "First name must not be empty.")
    @Size(min = 2, max = 50, message = "First name size must be between 2 and 50 symbols.")
    private String firstName;
    @NotBlank(message = "Last name must not be empty.")
    @Size(min = 2, max = 50, message = "Last name size must be between 2 and 50 symbols.")
    private String lastName;
    @Email
    @NotBlank(message = "Email must not be empty.")
    private String email;
    @NotBlank(message = "Company must not be empty.")
    private String company;

    public SignUpDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
