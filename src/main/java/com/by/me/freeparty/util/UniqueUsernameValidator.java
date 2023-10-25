package com.by.me.freeparty.util;

import com.by.me.freeparty.Repositorys.PersonRep;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final PersonRep personRepository;

    public UniqueUsernameValidator(PersonRep personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !personRepository.existsByUsername(username);
    }
}