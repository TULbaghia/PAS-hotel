package pl.lodz.p.pas.model.guest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialGuestTypeExceptionTest {
    @Test
    public void exceptionsTestCase() {
        assertThrows(SpecialGuestTypeException.class, () -> {
            throw new SpecialGuestTypeException();
        });
        assertThrows(SpecialGuestTypeException.class, () -> {
            throw new SpecialGuestTypeException("TEST");
        });
        assertThrows(SpecialGuestTypeException.class, () -> {
            throw new SpecialGuestTypeException(new NullPointerException());
        });
        assertThrows(SpecialGuestTypeException.class, () -> {
            throw new SpecialGuestTypeException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        SpecialGuestTypeException e = assertThrows(SpecialGuestTypeException.class, () -> {
            throw new SpecialGuestTypeException("TEST");
        });

        assertEquals("TEST", e.getMessage());
    }
}
