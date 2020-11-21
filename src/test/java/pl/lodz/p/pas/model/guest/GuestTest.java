package pl.lodz.p.pas.model.guest;

import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.guest.exception.GuestException;
import pl.lodz.p.pas.model.guest.guesttype.BasicGuestType;
import pl.lodz.p.pas.model.guest.guesttype.SpecialGuestType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTest {

    @Test
    public void creationGuestTestCase() throws GuestException {
        Guest guest = new Guest("Jan1", "Jan", "Test");

        assertEquals(guest.getLogin(), "Jan1");
        assertEquals(guest.getFirstname(), "Jan");
        assertEquals(guest.getSurname(), "Test");
        assertEquals(guest.getNumberOfStays(), 0);
        assertEquals(guest.getMaxApartmentsNumber(), new BasicGuestType().getMaxApartmentsNumber());
    }

    @Test
    public void changingGuestTypeTestCase() throws GuestException {
        Guest guest = new Guest("Jan", "Kowalski", "Test");

        assertEquals(guest.getMaxApartmentsNumber(), new BasicGuestType().getMaxApartmentsNumber());
        guest.changeGuestType(new SpecialGuestType());
        assertEquals(guest.getMaxApartmentsNumber(), new SpecialGuestType().getMaxApartmentsNumber());
        guest.changeGuestType(new BasicGuestType());
        assertEquals(guest.getMaxApartmentsNumber(), new BasicGuestType().getMaxApartmentsNumber());
    }
}
