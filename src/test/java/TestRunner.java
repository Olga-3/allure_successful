import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        strict = true,
        features = {"src/test/resources"},
        tags = "@authorization",
        glue = {"ru.lanit.framework.steps"}
)

public class TestRunner extends AbstractTestNGCucumberTests {

}