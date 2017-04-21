package bookmarks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by agup101 on 4/5/2017.
 */
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    public String password;
    public String username;

    @OneToMany(mappedBy = "account")
    private Set<Bookmark> bookmarks = new HashSet<>();

    Account() {// jpa only
    }

    public Account(String name, String password) {
        this.username = name;
        this.password = password;
    }

}
