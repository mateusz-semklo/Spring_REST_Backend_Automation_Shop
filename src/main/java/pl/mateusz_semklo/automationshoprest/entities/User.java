package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USERS")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username",scope = User.class)
public class User implements Serializable{

    private static final long serialVersionUID = 1234567L;

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "ENABLED")
    private boolean enabled;

    @Basic
    @Column(name = "USER_EMAIL")
    private String userEmail;

    @Basic
    @Column(name = "USER_FIRSTNAME")
    private String userFirstname;

    @Basic
    @Column(name = "USER_LASTNAME")
    private String userLastname;

    @Basic
    @Column(name = "USER_STREET")
    private String userStreet;

    @Basic
    @Column(name = "USER_CITY")
    private String userCity;

    @Basic
    @Column(name = "USER_COUNTRY")
    private String userCountry;

    @Basic
    @Column(name = "USER_POST_CODE")
    private String userPostCode;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="AUTHORITIES",joinColumns = @JoinColumn(name="USERNAME"))
    @Column(name = "AUTHORITY",nullable = false)
    public List<String> authorities=new ArrayList<>();

   // @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Order> orders=new ArrayList<>();

}
