package pl.michal_baniowski.coding_forum.services.message;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import pl.michal_baniowski.coding_forum.exception.MessageSendException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EmailService implements MessageService {

    private static EmailService emailService;

    public synchronized static EmailService getInstance() {
        if (emailService == null) {
            emailService = new EmailService();
        }
        return emailService;
    }

    private Email email;

    private EmailService() {
        email = new SimpleEmail();
        try {
            setEmailConfiguration("config/emailConfig.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void sendMessage(String message, String addressee, String subject) throws MessageSendException {
        try {
            email.setSubject(subject);
            email.setMsg(message);
            email.addTo(addressee);

            email.send();
        } catch (Exception e) {
            throw new MessageSendException("failure on sending message");
        }
    }

    private void setEmailConfiguration(String configFilePath) throws URISyntaxException, EmailException, IOException {
        Map<String, String> configParams = getConfigParmsMap(configFilePath);

        email.setHostName(configParams.get("hostName"));
        email.setAuthentication(configParams.get("userName"), configParams.get("password"));
        email.setSSLOnConnect(true);
        email.setSslSmtpPort("465");
        email.setFrom(configParams.get("sender"));
    }

    private Map<String, String> getConfigParmsMap(String configFilePath) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(configFilePath).toURI());

        Map<String, String> configParams = new HashMap<>();
        Files.readAllLines(path).stream()
                .forEach(line -> setConfigFromLine(line, configParams));
        return configParams;
    }


    private void setConfigFromLine(String line, Map<String, String> configParams) {
        String[] params = line.split("=");
        configParams.put(params[0], params[1]);
    }
}
