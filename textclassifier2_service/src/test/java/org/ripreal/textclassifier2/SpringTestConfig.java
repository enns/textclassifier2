package org.ripreal.textclassifier2;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class SpringTestConfig {
}
