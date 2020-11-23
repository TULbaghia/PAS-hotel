package pl.lodz.p.pas.model.guest.guesttype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.pas.model.guest.Guest;
import pl.lodz.p.pas.model.guest.exception.GuestException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestTypeTest {
    Guest guest;

    @BeforeEach
    public void setUp() {
        guest = new Guest("Jan", "Kowalski", "Test");
    }

    @Test
    public void guestTypeWithParameterTestCase() throws GuestException {
        guest.changeGuestType(new BasicGuestType(3));
        assertEquals(guest.getGuestType().calculateDiscount(30), (1 * 30));
        assertEquals(guest.getGuestType().getMaxApartmentsNumber(), 3);

        guest.changeGuestType(new SpecialGuestType(6));
        assertEquals(guest.getGuestType().calculateDiscount(300), (.8 * 300));
        assertEquals(guest.getGuestType().getMaxApartmentsNumber(), 6);
    }

    @Test
    public void guestTypeNoParameterTestCase() throws GuestException {
        guest.changeGuestType(new BasicGuestType());
        assertEquals(guest.getGuestType().calculateDiscount(30), (1 * 30));
        assertEquals(guest.getGuestType().getMaxApartmentsNumber(), 2);

        guest.changeGuestType(new SpecialGuestType());
        assertEquals(guest.getGuestType().calculateDiscount(300), (.8 * 300));
        assertEquals(guest.getGuestType().getMaxApartmentsNumber(), 4);
    }
}
