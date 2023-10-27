package pl.mateusz_semklo.automationshoprest.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import pl.mateusz_semklo.automationshoprest.entities.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel extends RepresentationModel<UserModel> {


    private String username;
    private String password;
    private boolean enabled;
    private String userEmail;
    private String userFirstname;
    private String userLastname;
    private String userStreet;
    private String userCity;
    private String userCountry;
    private String userPostCode;
    public List<String> authorities=new ArrayList<>();
    private List<Order> orders=new ArrayList<>();

}
