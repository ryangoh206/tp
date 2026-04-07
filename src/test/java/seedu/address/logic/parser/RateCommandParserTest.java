package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rate;

public class RateCommandParserTest {
    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_validValue_success() {
        // EP: valid index + valid non-empty rate value
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE + "120.5";
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate("120.5"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_clearValue_success() {
        // EP: valid index + empty rate value
        // BVA: empty string after rate prefix
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RATE;
        RateCommand expectedCommand = new RateCommand(INDEX_FIRST_PERSON, new Rate(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingIndexOrPrefix_failure() {
        String expectedMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

        // EP: missing index and missing prefix/value
        // BVA: empty command body
        assertParseFailure(parser, "", expectedMessage);

        // EP: missing compulsory index
        assertParseFailure(parser, PREFIX_RATE + "120.5", expectedMessage);

        // EP: missing compulsory rate prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " 120.5", expectedMessage);

        // EP: invalid index partition (non-positive indices)
        // BVA: just outside valid one-based lower bound (0)
        assertParseFailure(parser, "0 " + PREFIX_RATE + "120.5", expectedMessage);
        // another value outside valid lower bound (-1)
        assertParseFailure(parser, "-1 " + PREFIX_RATE + "120.5", expectedMessage);
    }

    @Test
    public void parse_repeatedRatePrefix_failure() {
        // EP: duplicate rate prefix in same command
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_RATE + "120.00 " + PREFIX_RATE + "100.00";
        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_RATE));
    }

    @Test
    public void parse_invalidRate_failure() {
        // EP: invalid numeric payload for rate
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_RATE + ".";
        assertParseFailure(parser, userInput, Rate.MESSAGE_CONSTRAINTS);
    }
}
