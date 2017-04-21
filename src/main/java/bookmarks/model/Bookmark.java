package bookmarks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by agup101 on 4/5/2017.
 */
@Entity
@Getter
public class Bookmark {

    @Id
    @GeneratedValue
    private Long id;

    public String uri;
    public String description;


    @JsonIgnore
    @ManyToOne
    private Account account;

    public Bookmark() { // jpa only
    }

    public Bookmark(Account account, String uri, String description) {
        this.uri = uri;
        this.description = description;
        this.account = account;
    }

}
