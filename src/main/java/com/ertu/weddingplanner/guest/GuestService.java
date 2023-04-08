package com.ertu.weddingplanner.guest;

import com.ertu.weddingplanner.mail.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    private final MailService mailService;

    @Autowired
    public GuestService(GuestRepository guestRepository, MailService mailService) {
        this.guestRepository = guestRepository;
        this.mailService = mailService;
    }

    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> getGuest(Long id) {
        return guestRepository.findById(id);
    }

    public void createGuest(Guest guest) throws MessagingException {
        Optional<Guest> guestOptional = guestRepository.findGuestByEmail(guest.getEmail());
        if (guestOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        guestRepository.save(guest);

        mailService.sendHtmlMail(guest);
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
