import org.apache.camel.builder.RouteBuilder;

public class HelloWorld extends RouteBuilder {

    @Override
    public void configure() {
        from("kafka:orders?brokers=localhost:9092")
                .log("Received: ${body}");
    }
}
