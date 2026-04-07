package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    public void constructor_invalidRate_throwsIllegalArgumentException() {
        // EP: negative numeric input (outside valid range)
        // BVA: just below lower boundary (0)
        assertThrows(IllegalArgumentException.class, () -> new Rate("-1"));

        // EP: invalid format with grouping separator
        assertThrows(IllegalArgumentException.class, () -> new Rate("1,000"));

        // EP: invalid format with currency symbol
        assertThrows(IllegalArgumentException.class, () -> new Rate("$100"));
    }

    @Test
    public void constructor_validRate_normalisesToTwoDecimalPlaces() {
        // EP: whole number input
        assertTrue(new Rate("120").value.equals("120.00"));

        // EP: decimal point without fractional digits
        // BVA: fractional boundary at zero digits after '.'
        assertTrue(new Rate("100.").value.equals("100.00"));

        // EP: single fractional digit
        assertTrue(new Rate("120.5").value.equals("120.50"));

        // EP: fractional input without leading zero
        assertTrue(new Rate(".50").value.equals("0.50"));

        // BVA: lower numeric boundary (0)
        assertTrue(new Rate("0").value.equals("0.00"));
    }

    @Test
    public void equals() {
        Rate rate = new Rate("120.5");

        // EP: same object reference
        // same object -> returns true
        assertTrue(rate.equals(rate));

        // EP: logically equivalent objects
        // same values -> returns true
        Rate rateCopy = new Rate("120.50");
        assertTrue(rate.equals(rateCopy));

        // EP: different runtime type
        // different types -> returns false
        assertFalse(rate.equals(1));

        // EP: null comparison
        // null -> returns false
        assertFalse(rate.equals(null));

        // EP: same type, different value
        // different rate -> returns false
        Rate differentRate = new Rate("80.00");
        assertFalse(rate.equals(differentRate));
    }
}
