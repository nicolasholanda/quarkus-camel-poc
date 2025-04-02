import org.apache.camel.Exchange;
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

    private String getClientIp(Exchange exchange) {
        String ip = exchange.getIn().getHeader("X-Forwarded-For", String.class);
        if(ip == null || ip.isEmpty()) {
            ip = exchange.getIn().getHeader("Remote-Addr", String.class);
        }

        return ip != null ? ip : "Unknown";
    }
}
