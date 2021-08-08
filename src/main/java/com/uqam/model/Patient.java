package com.uqam.model;

import java.sql.Date;
import java.util.Optional;
import javax.persistence.*;

@Entity
@Table(name = "tPatients")
public class Patient implements Cloneable {

    @Id
    @GeneratedValue( strategy= GenerationType.IDENTITY )
    private long id;
    private String firstname;
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date dateOfBirth;
    private String birthCity;
    private String insuranceNumber;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn( name="contact")
    private Contact contact;

    private String father;
    private String mother;

    public Patient() {
    }

    public Patient(String firstname, String lastname, Gender gender, Date date, String birthCity, String insuranceNumber, Contact contact){
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = date;
        this.birthCity = birthCity;
        this.insuranceNumber = insuranceNumber;
        this.contact = contact;
    }



    //methode to validate the length of a name
    public boolean validName(String name){
       return name.length()>1;
    }

    //methode to validate the gender of the patient
    public boolean validGender(){
        return gender.equals(Gender.MALE) || gender.equals(Gender.FEMALE) || gender.equals(Gender.OTHER);
    }

    // methode pour valider la date selon la norme iso8601
    /*private boolean validDateOfBirth(Date date){
        Date currentDate = new Date();
        return date.after(currentDate);
    } */

    // Method to validate the length of the city's name and to make sure it only contains letter
    public boolean validBirthCity(String city){
        if (city.length() <2) return false;
        for(int i=0; i<city.length(); i++){
            if(!Character.isLetter(city.charAt(i))){
                return false;
            }
        }
        return true;
    }


    // MEthod to validate the format of insurance number: ABCD123456789999
    public boolean validInsuranceNumber(String num){
        if(num.length() != 12) return false;
        for(int i=0; i<4; i++){
            if(!Character.isLetter(num.charAt(i))){
                return false;
            }
        }
        for(int i=4;i<num.length();i++){
            if(!Character.isDigit(num.charAt(i))){
                return false;
            }
        }
        return true;
    }


    //setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    } // a supprimer le patient ne peux changer ca
    public void setLastname(String lastname) {
        this.lastname = lastname;
    } // a supprimer
    public void setGender(Gender gender) {
        this.gender = gender;
    } // a supprimer
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    } // a supprimer
    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    } // a supprimer
    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    } // a supprimer
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    //getters
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public Gender getGender() {
        return gender;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public String getBirthCity() {
        return birthCity;
    }
    public String getInsuranceNumber() {
        return insuranceNumber;
    }
    public Contact getContact() {
        return contact;
    }
    public String getFather() {
        return Optional.ofNullable(father).orElse("Non spécifié");
    }

    public String getMother() {
        return Optional.ofNullable(mother).orElse("Non spécifié");
    }

    @Override
    protected Patient clone() {
        try {
            Patient clone = (Patient) super.clone();
            clone.contact = this.contact.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", birthCity='" + birthCity + '\'' +
                ", insuranceNumber='" + insuranceNumber + '\'' +
                ", contact=" + contact +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                '}';
    }
}
