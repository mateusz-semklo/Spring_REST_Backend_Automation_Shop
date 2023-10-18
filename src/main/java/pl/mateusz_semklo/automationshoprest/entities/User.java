package pl.mateusz_semklo.automationshoprest.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Order> orders=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled() == user.isEnabled() && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getUserEmail(), user.getUserEmail()) && Objects.equals(getUserFirstname(), user.getUserFirstname()) && Objects.equals(getUserLastname(), user.getUserLastname()) && Objects.equals(getUserStreet(), user.getUserStreet()) && Objects.equals(getUserCity(), user.getUserCity()) && Objects.equals(getUserCountry(), user.getUserCountry()) && Objects.equals(getUserPostCode(), user.getUserPostCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isEnabled(), getUserEmail(), getUserFirstname(), getUserLastname(), getUserStreet(), getUserCity(), getUserCountry(), getUserPostCode());
    }
}
