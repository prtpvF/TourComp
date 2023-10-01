package com.by.me.freeparty.Services;

import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Repositorys.PersonRep;
import com.by.me.freeparty.Security.PersonDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServices implements UserDetailsService {
    private final PersonRep personRep;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(Person person){
        if(personRep.findById(person.getId())==null){
            throw new RuntimeException();
        } else
            System.out.println("толмвалопмлаврмша");
        person.setRole("ROLE_ADMIN");
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
            personRep.save(person);
    }
    public Person getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person guest = personDetails.getPerson();
        return guest;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        System.out.println("делается запрос");
        Optional<Person> person = personRep.findByUsername(username);
        System.out.println("запрос сделали, проверяем на наличие");
        if (person.isEmpty()) {
            System.out.println("пусто");
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("не пустой");
        return new PersonDetails(person.get());
    }

}
