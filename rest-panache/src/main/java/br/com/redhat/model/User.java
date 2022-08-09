package br.com.redhat.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class User extends PanacheEntity {
    
    public String email;
    public String name;
    public String password;

    @JsonIgnore
    @OneToOne(optional = true, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public UserProfile profile;

    @Transactional
    public static List<User> withAge(int age) {
        try(Stream<User> users = User.streamAll()) {
            return users.filter(user -> user.profile.age.equals(age))
                        .collect(Collectors.toList());
        }
    }

    @Transactional
    public static List<User> withJobTitle(String jobTitle) {
        return User.list("profile.jobTitle", jobTitle);
    }
}
