package sample;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringBootStarter {

    private static final List<String> paymentIDs = Arrays.asList("CHJ788");
    private static final List<LocalDate> paymentDueDates = Arrays.asList(
            LocalDate.now());
    private static final List<Double> paymentAmounts = Arrays.asList(345000.00D);
    private static final List<Long> paymentBANs = Arrays.asList(734567898733L);
    private static final List<Long> disbursementBankAccountNumbers = Arrays.asList(456787671234L);
    private static final List<String> paymentDescriptions = Arrays.asList("ABC Food Pvt Ltd Payout");
    private static final List<String> payeeNames = Arrays.asList("ABC Food Pvt Ltd");
    private static final List<String> paymentMethods = Arrays.asList("Domestic");
    private static final List<String> paymentTypes = Arrays.asList("SEPA");
    private static final List<String> currencyCodes = Arrays.asList("GBP");

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringBootStarter.class)
                .web(WebApplicationType.NONE)
                .run(args);

        Application app = context.getBean(Application.class);
        try {
            app.refreshToken();
            app.sendPayment(paymentIDs, paymentDueDates, paymentAmounts, paymentBANs, disbursementBankAccountNumbers,
                    paymentDescriptions, payeeNames, paymentMethods, paymentTypes, currencyCodes);
            app.getStatus();
            app.getPayments();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
