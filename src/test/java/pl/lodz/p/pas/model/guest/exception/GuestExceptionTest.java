package pl.lodz.p.pas.model.guest.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuestExceptionTest {

    @Test
    public void exceptionsTestCase() {
        assertThrows(GuestException.class, () -> {
            throw new GuestException();
        });
        assertThrows(GuestException.class, () -> {
            throw new GuestException("TEST");
        });
        assertThrows(GuestException.class, () -> {
            throw new GuestException(new NullPointerException());
        });
        assertThrows(GuestException.class, () -> {
            throw new GuestException("TEST", new NullPointerException());
        });
    }

    @Test
    public void getMessageTestCase() {
        GuestException e = assertThrows(GuestException.class, () -> {
            throw new GuestException("TEST");
        });

        assertEquals("TEST", e.getMessage());
    }
}
