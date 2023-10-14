package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username",scope = Users.class)
public class Users implements Serializable{

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
    private Object userLastname;

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
    @Column(name = "AUTHORITY")
    public List<String> authorities=new ArrayList<>();


    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Orders> orders=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return isEnabled() == users.isEnabled() && Objects.equals(getUsername(), users.getUsername()) && Objects.equals(getPassword(), users.getPassword()) && Objects.equals(getUserEmail(), users.getUserEmail()) && Objects.equals(getUserFirstname(), users.getUserFirstname()) && Objects.equals(getUserLastname(), users.getUserLastname()) && Objects.equals(getUserStreet(), users.getUserStreet()) && Objects.equals(getUserCity(), users.getUserCity()) && Objects.equals(getUserCountry(), users.getUserCountry()) && Objects.equals(getUserPostCode(), users.getUserPostCode()) && Objects.equals(getAuthorities(), users.getAuthorities()) && Objects.equals(getOrders(), users.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isEnabled(), getUserEmail(), getUserFirstname(), getUserLastname(), getUserStreet(), getUserCity(), getUserCountry(), getUserPostCode(), getAuthorities(), getOrders());
    }
}
