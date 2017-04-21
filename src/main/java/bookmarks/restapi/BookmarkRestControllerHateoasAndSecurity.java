package bookmarks.restapi;

import bookmarks.model.Bookmark;
import bookmarks.repository.AccountRepository;
import bookmarks.repository.BookmarkRepository;
import bookmarks.resource.BookmarkResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by agup101 on 4/5/2017.
 */
@RestController
@RequestMapping("/bookmarkshateoassecurity")
public class BookmarkRestControllerHateoasAndSecurity {
    private final BookmarkRepository bookmarkRepository;

    private final AccountRepository accountRepository;

    BookmarkRestControllerHateoasAndSecurity(BookmarkRepository bookmarkRepository,
                                             AccountRepository accountRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<BookmarkResource> readBookmarks(Principal principal) {

        this.validateUser(principal);

        List<BookmarkResource> bookmarkResourceList = bookmarkRepository
                .findByAccountUsername(principal.getName()).stream().map(BookmarkResource::new)
                .collect(Collectors.toList());

        return new Resources<>(bookmarkResourceList);
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(Principal principal, @RequestBody Bookmark input) {

        this.validateUser(principal);

        return accountRepository.findByUsername(principal.getName())
                .map(account -> {
                    Bookmark bookmark = bookmarkRepository
                            .save(new Bookmark(account, input.uri, input.description));

                    Link forOneBookmark = new BookmarkResource(bookmark).getLink("self");

                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bookmarkId}")
    public BookmarkResource readBookmark(Principal principal,
                                         @PathVariable Long bookmarkId) {
        this.validateUser(principal);
        return new BookmarkResource(this.bookmarkRepository.findOne(bookmarkId));
    }

    private void validateUser(Principal principal) {
        String userId = principal.getName();
        this.accountRepository
                .findByUsername(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(userId));
    }
}
