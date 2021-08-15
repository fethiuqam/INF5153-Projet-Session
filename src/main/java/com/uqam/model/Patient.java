package com.uqam.model;

import java.sql.Date;
import java.util.Objects;
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
    private Date insuranceExpirationDate;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn( name="contact")
    private Contact contact;

    private String father;
    private String mother;

    public Patient() {
    }

    public Patient(String firstname, String lastname, Gender gender, Date date, String birthCity, String insuranceNumber, Date insuranceExpirationDate, Contact contact){
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.dateOfBirth = date;
        this.birthCity = birthCity;
        this.insuranceNumber = insuranceNumber;
        this.insuranceExpirationDate = insuranceExpirationDate;
        this.contact = contact;
    }

    //methode to validate the length of a name
    public boolean validName(String name) throws AppException{
       if(name.length()<2){
           throw new AppException("Le nom est trop court. Veuillez entrer un nom avec au moins 2 lettres.");
       }
       return true;
    }


    // Method to validate the length of the city's name and to make sure it only contains letter
    public boolean validBirthCity(String city) throws AppException{
        if (city.length() <2) throw new AppException("Le nom est trop court. Veuillez entrer un nom d'au moins 2 chiffres.");
        for(int i=0; i<city.length(); i++){
            if(!Character.isLetter(city.charAt(i))){
                throw new AppException("Il y des caracteres non valide. Veuillez n'entrer que des lettres.");
            }
        }
        return true;
    }


    // MEthod to validate the format of insurance number: ABCD12345678
    public boolean validInsuranceNumber(String num) throws AppException{
        if(num.length() != 12) throw new AppException("Le numero d'assurance doit contenir 12 chiffres.");
        for(int i=0; i<4; i++){
            if(!Character.isLetter(num.charAt(i))){
                throw new AppException("Les 4 premiers caracteres doivent contenir des lettres.");
            }
        }
        for(int i=4;i<num.length();i++){
            if(!Character.isDigit(num.charAt(i))){
                throw new AppException("Les 8 derniers caracteres doivent contenir des chiffres.");
            }
        }
        return true;
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
    public Date getInsuranceExpirationDate() {
        return insuranceExpirationDate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && Objects.equals(firstname, patient.firstname) && Objects.equals(lastname, patient.lastname) && gender == patient.gender && Objects.equals(dateOfBirth, patient.dateOfBirth) && Objects.equals(birthCity, patient.birthCity) && Objects.equals(insuranceNumber, patient.insuranceNumber) && Objects.equals(insuranceExpirationDate, patient.insuranceExpirationDate) && Objects.equals(contact, patient.contact) && Objects.equals(father, patient.father) && Objects.equals(mother, patient.mother);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, gender, dateOfBirth, birthCity, insuranceNumber, insuranceExpirationDate, contact, father, mother);
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
                ", insuranceExpirationDate=" + insuranceExpirationDate +
                ", contact=" + contact +
                ", father='" + father + '\'' +
                ", mother='" + mother + '\'' +
                '}';
    }
}
