package pl.lodz.p.pas.model.guest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicGuestTypeExceptionTest {
    @Test
    public void exceptionsTestCase() {
        assertThrows(BasicGuestTypeException.class, () -> {
            throw new BasicGuestTypeException();
        });
        assertThrows(BasicGuestTypeException.class, () -> {
            throw new BasicGuestTypeException("TEST");
        });
        assertThrows(BasicGuestTypeException.class, () -> {
            throw new BasicGuestTypeException(new NullPointerException());
        });
        assertThrows(BasicGuestTypeException.class, () -> {
            throw new BasicGuestTypeException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        BasicGuestTypeException e = assertThrows(BasicGuestTypeException.class, () -> {
            throw new BasicGuestTypeException("TEST");
        });

        assertEquals("TEST", e.getMessage());
    }
}
