import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class HelloWorld extends RouteBuilder {

    @Override
    public void configure() {
        from("platform-http:/hello")
                .routeId("hello-route")
                .setBody(constant("Hello World!"))
                .log(LoggingLevel.INFO, "Received a request for /hello");
    }
}
