package br.com.redhat.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class UserProfile extends PanacheEntity {
    
    public int age;
    public String gender;
    public String favoriteColor;

    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false, updatable = false)
    public User user;

}
