package com.example.DemoGraphQL.service;

import com.example.DemoGraphQL.model.Person;
import com.example.DemoGraphQL.model.Skill;
import com.example.DemoGraphQL.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Optional<Person> getPerson(final long id) {
        return this.personRepository.findById(id);
    }

    public Person getRandomPerson() {
        List<Person> givenList = this.personRepository.findAll();
        Random rand = new Random();
        return givenList.get(rand.nextInt(givenList.size()));
    }

    public List<Person> getPersons(Optional<Long> id) {
        List<Person> persons = new ArrayList<>();
        return id.map(v -> {
            this.personRepository.findById(v).ifPresent(persons::add);
            return persons;
        }).orElse(this.personRepository.findAll());
    }

    public Optional<Person> getPerson(Optional<Long> id) {
        return id.map(v -> this.personRepository.findById(v)).orElse(null);
    }

    public List<Person> getFriends(Person person, Optional<Long> friendId) {
        List<Person> friends = new ArrayList<>();
        return friendId.map(v -> {
            person.getFriends().stream()
                    .filter(myFriend -> myFriend.getId().equals(v))
                    .findFirst()
                    .ifPresent(friends::add);
            return friends;
        }).orElse(new ArrayList<>(person.getFriends()));
    }

    public List<Skill> getSkills(Person person, Optional<Long> skillId) {
        List<Skill> skills = new ArrayList<>();
        return skillId.map(v -> {
            person.getSkills().stream()
                    .filter(mySkill -> mySkill.getId().equals(v))
                    .findFirst()
                    .ifPresent(skills::add);
            return skills;
        }).orElse(new ArrayList<>(person.getSkills()));
    }
}
