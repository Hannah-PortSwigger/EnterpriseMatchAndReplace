import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Utilities;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EscapingTest
{
    private static Stream<Arguments> data()
    {
        return Stream.of(
                arguments("foo", "foo")
        );
    }

    @MethodSource("data")
    @ParameterizedTest
    void jsonEscapingTesting(String unescapedString, String expectedEscapedString)
    {
        String actualOutput = Utilities.escapeString(unescapedString);

        assertThat(actualOutput).isEqualTo(expectedEscapedString);
    }
}
