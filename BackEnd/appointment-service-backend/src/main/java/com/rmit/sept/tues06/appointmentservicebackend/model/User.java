package com.rmit.sept.tues06.appointmentservicebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance
@DiscriminatorColumn(name = "user_type")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public abstract class User extends BaseEntity {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Role> roles = new HashSet<>();

    @NotBlank(message = "Name is required")
    private String name;
    @Column(updatable = false, unique = true)
    @Size(min = 4, max = 20, message = "Please enter at least 4 characters")
    private String username;
    @Email
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 120)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    public User() {

    }

    public User(String username, String email, String password, String name, String address, String phoneNumber) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Schema(example = "Jane Doe", description = "name of the user")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Schema(example = "customer", description = "username of the user")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Schema(example = "123 ABC Street", description = "address of the user")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Schema(example = "customer@email.com", description = "email of the user")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Schema(example = "password", description = "password of the user")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Schema(example = "0499999999", description = "phone number of the user")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, example = "ROLE_CUSTOMER", description = "role/type of the user")
    public String getRole() {
        return roles.iterator().next().getName().name();
    }
}
