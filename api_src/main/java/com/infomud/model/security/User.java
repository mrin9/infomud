package com.infomud.model.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import lombok.*;
import javax.validation.constraints.*;
import java.util.EnumSet;
@Entity
public class User {
    @Id
    @Getter @Setter private String userName;

    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;

    //@Size(min = 4, max = 30)
    @Getter @Setter private String password = "";

    @Enumerated(EnumType.STRING)
    @Getter @Setter private Role role;

    @JsonIgnore
    @Getter @Setter private Boolean pendingActivation=true;

    @Getter @Setter EnumSet<Role> roles;
    @Getter @Setter private String company;
    @Getter @Setter private String phone;
    @Getter @Setter private String address1;
    @Getter @Setter private String address2;
    @Getter @Setter private String country;
    @Getter @Setter private String postal;
    @Getter @Setter private String secretQuestion;
    @Getter @Setter private String secretAnswer;
    @Getter @Setter private Boolean enableBetaTesting;
    @Getter @Setter private Boolean enableRenewal;

    @JsonIgnore
    @Getter @Setter private String email;

    public User(){
        this("new", "PASSWORD", Role.USER, "new", "new", true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userName, String password, String firstName, String lastName){
        this(userName, password, Role.USER, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userName, String password, Role role, String firstName, String lastName){
        this(userName, password, role, firstName, lastName, true, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userName, String password, Role role, String firstName, String lastName, boolean isPendingActivation){
        this(userName, password, role, firstName, lastName, isPendingActivation, "", "", "", "", "", "", "", "", true, false);
    }

    public User(String userName, String password, Role role, String firstName, String lastName, boolean isPendingActivation,
         String company, String phone, String address1, String address2, String country, String postal,
         String secretQuestion, String secretAnswer, boolean enableRenewal, boolean enableBetaTesting){
        this.setUserName(userName);
        this.setEmail(userName);
        this.setPassword(new BCryptPasswordEncoder().encode(password));
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPendingActivation(isPendingActivation);
        this.setCompany(company);
        this.setPhone(phone);
        this.setAddress1(address1);
        this.setAddress2(address2);
        this.setCountry(country);
        this.setCountry(postal);
        this.setSecretQuestion(secretQuestion);
        this.setSecretAnswer(secretAnswer);
        this.setEnableRenewal(enableRenewal);
        this.setEnableBetaTesting(enableBetaTesting);
    }

}
