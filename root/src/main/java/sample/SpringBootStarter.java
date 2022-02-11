package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.*;

@SpringBootApplication
public class SpringBootStarter {

    private static final List<String> paymentIDs = Arrays.asList("CHJ788");
    private static final List<Date> paymentDueDates = Arrays.asList(
            new GregorianCalendar(2022, Calendar.FEBRUARY, 23).getTime());
    private static final List<Double> paymentAmounts = Arrays.asList(345000.00D);
    private static final List<Long> paymentBANs = Arrays.asList(734567898733L);
    private static final List<Long> disbursementBankAccountNumbers = Arrays.asList(456787671234L);
    private static final List<String> paymentDescriptions = Arrays.asList("ABC Food Pvt Ltd Payout");
    private static final List<String> payeeNames = Arrays.asList("ABC Food Pvt Ltd");
    private static final List<String> paymentMethods = Arrays.asList("Domestic");
    private static final List<String> paymentTypes = Arrays.asList("SEPA");
    private static final List<String> currencyCodes = Arrays.asList("GBP");

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootStarter.class, args);

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
