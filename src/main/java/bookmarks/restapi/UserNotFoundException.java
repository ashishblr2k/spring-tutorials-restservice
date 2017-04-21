package bookmarks.restapi;

/**
 * Created by agup101 on 4/5/2017.
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super("could not find user '" + userId + "'.");
    }
}