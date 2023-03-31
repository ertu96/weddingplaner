package com.ertu.weddingplanner.guest;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "https://elisabeth-ertugrul.netlify.app", methods = {RequestMethod.POST})
@RequestMapping(path = "guest")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<Guest> getGuests() {
        return guestService.getGuests();
    }

    @GetMapping("{guestId}")
    public Optional<Guest> getGuest(@PathVariable Long guestId) {
        return guestService.getGuest(guestId);
    }

    @PostMapping
    public void addGuest(@RequestBody Guest guest) throws MessagingException, IOException {
        guestService.createGuest(guest);
    }

    @PutMapping("{guestId}")
    public void editGuest(
            @PathVariable("guestId") Long guestId,
            @RequestParam(required = false) String name) {
        guestService.updateGuest(guestId, name);
    }

    @DeleteMapping("{guestId}")
    public void removeGuest(@PathVariable("guestId") Long guestId) {
        guestService.deleteGuest(guestId);
    }

}
