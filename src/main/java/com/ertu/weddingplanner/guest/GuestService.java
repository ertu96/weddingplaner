package com.ertu.weddingplanner.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

    public void createGuest(Guest guest) {
        Optional<Guest> guestOptional = guestRepository.findGuestByEmail(guest.getEmail());
        if (guestOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        guestRepository.save(guest);
    }

    @Transactional
    public void updateGuest(Long id, String name) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new IllegalStateException("guest with id " + id + " does not exist"));
        if (name != null && name.length() > 0) {
            guest.setName(name);
        }
    }

    public void deleteGuest(Long guestId) {
        boolean exists = guestRepository.existsById(guestId);
        if (!exists) {
            throw new IllegalStateException("guest with id " + guestId + " does not exist");
        }
        guestRepository.deleteById(guestId);
    }


}
