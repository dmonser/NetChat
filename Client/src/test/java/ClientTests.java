import org.example.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ClientTests {

    @Test
    public void testParseConfig() {
        //arrange
        Client client = new Client();
        //act
        Executable executable = client::parseConfig;
        //assert
        Assertions.assertDoesNotThrow(executable);
    }
}
