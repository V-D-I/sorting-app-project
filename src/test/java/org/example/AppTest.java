package org.example;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class AppTest {
    private String[] argsForTesting;
    private String[] expectedResult;

    private ByteArrayOutputStream sink;
    private PrintStream controlledOut;
    private PrintStream defaultOut;

    public AppTest(String[] argsForTesting, String[] expectedResult) {
        this.argsForTesting = argsForTesting;
        this.expectedResult = expectedResult;
    }

    @Test
    public void appShouldCorrectlySortEntryArguments() {
        try {
            setControlledStream();
            App.main(argsForTesting);
            controlledOut.flush();
            assertEquals(Arrays.toString(expectedResult), sink.toString().trim());
        } finally {
            setStandardStream();
        }
    }

    private void setControlledStream() {
        sink = new ByteArrayOutputStream();
        controlledOut = new PrintStream(sink);

        defaultOut = System.out;

        System.setOut(controlledOut);
    }

    private void setStandardStream() {
        System.setOut(defaultOut);
    }


    @Test
    public void appShouldThrowIAExceptionIfPassNoValidArgs() {
        boolean illegalArgumentExceptionWasCaught = false;
        try {
            App.main(new String[]{"1", "Hello World", "3"});
        } catch (Exception e) {
            illegalArgumentExceptionWasCaught = true;
        }
        assertTrue(illegalArgumentExceptionWasCaught);
    }

    @Test
    public void numberOfArgumentsShouldBeLimited() {
        String[] greaterThanLimitArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        String[] expected = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        try {
            setControlledStream();
            App.main(greaterThanLimitArray);
            controlledOut.flush();
            assertEquals(Arrays.toString(expected), sink.toString().trim());
        } finally {
            setStandardStream();
        }

    }


    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {new String[]{"62", "49", "40", "58", "78"}, new String[]{"40", "49", "58", "62", "78"}},
                {new String[]{"53", "55", "1", "47", "95", "80"}, new String[]{"1", "47", "53", "55", "80", "95"}},
                {new String[]{"85", "92", "48"}, new String[]{"48", "85", "92"}},
                {new String[]{"89", "8", "29", "96", "55"}, new String[]{"8", "29", "55", "89", "96"}},
                {new String[]{"97", "3", "45", "27", "67", "70", "29", "91", "90"}, new String[]{"3", "27", "29", "45", "67", "70", "90", "91", "97"}},
                {new String[]{"70", "85", "96", "67", "29", "68", "2", "90"}, new String[]{"2", "29", "67", "68", "70", "85", "90", "96"}},
                {new String[]{"70", "85", "96", "67", "29", "68", "2", "90", "10", "2"}, new String[]{"2", "2", "10", "29", "67", "68", "70", "85", "90", "96"}},
                {new String[]{"17", "83", "7", "58", "47", "17"}, new String[]{"7", "17", "17", "47", "58", "83"}},
                {new String[]{"1"}, new String[]{"1"}},
                {new String[]{"68", "98", "26", "17", "29", "91", "45", "36", "92"}, new String[]{"17", "26", "29", "36", "45", "68", "91", "92", "98"}},
                {new String[]{"6", "29", "71", "20"}, new String[]{"6", "20", "29", "71"}},
        });
    }
}