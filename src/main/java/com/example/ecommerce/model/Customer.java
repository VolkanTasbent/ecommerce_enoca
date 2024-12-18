package com.example.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Customer extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String surname;  // Ekle

    @Column(nullable = false)
    private String password;  // Ekle

    // Getter ve setter yöntemlerini tanımla
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {  // Ekle
        return surname;
    }

    public void setSurname(String surname) {  // Ekle
        this.surname = surname;
    }

    public String getPassword() {  // Ekle
        return password;
    }

    public void setPassword(String password) {  // Ekle
        this.password = password;
    }

    // id için setter yöntemi ekle
    @Override
    public void setId(Long id) {
        super.setId(id); // BaseEntity'deki setId metodunu çağırarak id'yi ayarla
    }
}
