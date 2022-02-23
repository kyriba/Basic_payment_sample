package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sample.model.Payment;
import sample.model.PaymentStatus;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@SpringBootApplication
public class SpringBootStarter {

    private static final String paymentID = "CHJ788";
    private static final Date paymentDueDate =
            new GregorianCalendar(2022, Calendar.FEBRUARY, 23).getTime();
    private static final Double paymentAmount = 345000.00D;
    private static final Long paymentBAN = 734567898733L;
    private static final Long disbursementBankAccountNumber = 456787671234L;
    private static final String paymentDescription = "ABC Food Pvt Ltd Payout";
    private static final String payeeName = "ABC Food Pvt Ltd";
    private static final String paymentMethod = "Domestic";
    private static final String paymentType = "SEPA";
    private static final String currencyCode = "GBP";
    private static final Payment payment = new Payment(paymentID, paymentDueDate, paymentAmount, paymentBAN,
            disbursementBankAccountNumber, paymentDescription, payeeName, paymentMethod, paymentType,
            currencyCode);
    private static final Payment payment2 = new Payment(paymentID, paymentDueDate, 300000.00D, paymentBAN,
            disbursementBankAccountNumber, paymentDescription, payeeName, paymentMethod, paymentType,
            currencyCode);
    private static final Payment payment3 = new Payment(paymentID, paymentDueDate, 102000.00D, paymentBAN,
            disbursementBankAccountNumber, paymentDescription, payeeName, paymentMethod, paymentType,
            currencyCode);


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootStarter.class, args);

        Application app = context.getBean(Application.class);
        try {
            app.refreshToken();
            System.out.println(app.singlePayment(paymentID, paymentDueDate, paymentAmount, paymentBAN,
                    disbursementBankAccountNumber, paymentDescription, payeeName, paymentMethod, paymentType,
                    currencyCode));
            System.out.println(app.singlePayment(payment));
            System.out.println(app.bulkPayment(Arrays.asList(payment, payment2, payment3)));
            List<PaymentStatus> paymentStatuses = app.getPaymentStatus("Registered");
            for (PaymentStatus paymentStatus : paymentStatuses) {
                System.out.println(paymentStatus);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
