package be.codesolutions.domopi;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class DomopiApplicationTest {

    @Inject
    BeanManager beanManager;

    @Test
    void contextLoads() {
        assertThat(beanManager).isNotNull();
    }
}