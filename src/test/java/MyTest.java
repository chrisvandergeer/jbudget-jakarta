import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.IntStream;

public class MyTest {

    @Test
    void test() {
        String txt = "Hello World!";

        boolean found = false;
        Long uuid = UUID.randomUUID().getMostSignificantBits();
        int n = 0;
        while (!found) {
            n++;
            Long otherUuid = UUID.randomUUID().getMostSignificantBits();
            if (n % 1000000 == 0) {
                System.out.println(String.format("Try %s", n));
            }
            if (String.valueOf(uuid.hashCode()).equals(String.valueOf(otherUuid.hashCode()))) {
                System.out.println(String.format("%s - %s found in %s tries", uuid, otherUuid, n));
            }
        }
    }
}
