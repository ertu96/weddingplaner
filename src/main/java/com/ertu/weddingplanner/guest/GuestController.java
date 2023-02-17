package com.ertu.weddingplanner.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping
    public void addGuest(@RequestBody Guest guest) {
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
