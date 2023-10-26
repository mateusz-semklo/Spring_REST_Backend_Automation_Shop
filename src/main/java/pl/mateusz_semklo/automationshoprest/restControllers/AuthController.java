package pl.mateusz_semklo.automationshoprest.restControllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz_semklo.automationshoprest.security.AuthRequest;
import pl.mateusz_semklo.automationshoprest.security.JwtService;

import java.util.HashSet;
import java.util.Set;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    JwtService jwtService;

    @PostMapping(value = "/authenticate",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    public String authenticate(@RequestBody AuthRequest authRequest, HttpServletResponse response){

        Authentication authentication=authenticationManagerBuilder.getObject().authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        if(authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()){

            Set<String> authorities=new HashSet<>();
            authentication.getAuthorities().forEach((auth)->authorities.add(auth.getAuthority()));
            return jwtService.generateToken(authentication.getName(),authorities);
        }
        else{
            throw new UsernameNotFoundException("invalid user request!");
        }

    }


}
