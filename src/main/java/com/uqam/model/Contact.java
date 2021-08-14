package com.uqam.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "tContacts")
public class Contact implements Cloneable{

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String address;
    private String phone;
    private String email;

    public Contact(){}

    public Contact(String address, String phone, String email) {
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    //method to validate an adress usign regex
    public boolean validAddress(String address){
        return address.matches("[A-Za-z0-9]+");
    }

    // method tov validate phone numebr using regex
    public boolean validPhone(String phone){
        if(!phone.matches("[0-9]+")){
            return false;
        }
        return phone.length()!=10;
    }


    //method to validate an email using regex expression
    public boolean validEmail(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }


    //setters
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) {this.phone = phone; }
    public void setEmail(String email) {this.email = email;}

    //getters
    public String getAddress() {
        return Optional.ofNullable(address).orElse("Non spécifié");
    }
    public String getPhone() {
        return Optional.ofNullable(phone).orElse("Non spécifié");
    }
    public String getEmail() {
        return Optional.ofNullable(email).orElse("Non spécifié");
    }

    @Override
    protected Contact clone() {
        try {
            return (Contact) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id && Objects.equals(address, contact.address) && Objects.equals(phone, contact.phone) && Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, phone, email);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
