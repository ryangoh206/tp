package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void equals() {
        Note note = new Note("Likes morning workouts.");

        // EP: same object reference
        // same object -> returns true
        assertTrue(note.equals(note));

        // EP: logically equivalent objects
        // same values -> returns true
        Note noteCopy = new Note(note.value);
        assertTrue(note.equals(noteCopy));

        // EP: different runtime type
        // different types -> returns false
        assertFalse(note.equals(1));

        // EP: null comparison
        // null -> returns false
        assertFalse(note.equals(null));

        // EP: same type, different value
        // different note -> returns false
        Note differentNote = new Note("Likes evening workouts.");
        assertFalse(note.equals(differentNote));
    }
}
