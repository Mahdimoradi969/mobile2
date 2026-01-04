package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class PersonData {

    private String personName;

    public PersonData(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public static List<PersonData> generateSampleUsers() {
        List<PersonData> users = new ArrayList<>();
        users.add(new PersonData("John"));
        users.add(new PersonData("Alice"));
        users.add(new PersonData("Bob"));
        users.add(new PersonData("Charlie"));
        users.add(new PersonData("Eve"));
        return users;
    }
}