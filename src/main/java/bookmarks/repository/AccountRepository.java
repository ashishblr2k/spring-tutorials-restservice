package bookmarks.repository;

import bookmarks.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by agup101 on 4/5/2017.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

        Optional<Account> findByUsername(String username);

}
