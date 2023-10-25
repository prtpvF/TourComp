package com.by.me.freeparty.Services;

import com.by.me.freeparty.Model.Person;
import com.by.me.freeparty.Model.Tour;
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


import java.util.List;
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
        person.setRole("ROLE_USER");
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
            personRep.save(person);
    }
    public Person getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person guest = personDetails.getPerson();
        return guest;
    }
    public Person getOne(){
        return getAuth();
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

    public List<Tour> getAllTours(int id){
        Person person = personRep.findById(id).orElseThrow(()-> new RuntimeException("Такого пользвоателя не найденно"));
        if(person.getTours().isEmpty()){
            throw new RuntimeException("список пуст");
        }
        List<Tour> tours = person.getTours();
        return  tours;
    }

    public Person getPerson(int id){
       Person person = personRep.findById(id).orElseThrow(()-> new RuntimeException("такого пользователя нет"));
        return person;
    }

}
