package sunposition.springdays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SpringDaysApplicationTests {

    @Autowired
    private SpringDaysApplication springDaysApplication;

    @Test
    void contextLoads() {
        assertThat(springDaysApplication).isNotNull();
    }

}
