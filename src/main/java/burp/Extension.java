package burp;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.sessions.ActionResult;
import burp.api.montoya.http.sessions.SessionHandlingAction;
import burp.api.montoya.http.sessions.SessionHandlingActionData;
import utils.ConfigurationProvider;
import utils.Logger;

import static burp.api.montoya.http.sessions.ActionResult.actionResult;

@SuppressWarnings("unused")
public class Extension implements BurpExtension
{
    public static final String EXTENSION_NAME = "Match and replace";

    @Override
    public void initialize(MontoyaApi montoyaApi)
    {
        montoyaApi.extension().setName(EXTENSION_NAME);
        Logger logging = new Logger(EXTENSION_NAME, montoyaApi.logging());

        logging.logToOutput("Loaded.");

        String sessionConfig = montoyaApi.burpSuite().exportProjectOptionsAsJson("project_options.sessions");

        try
        {
            ConfigurationProvider configurationProvider = new ConfigurationProvider(logging, sessionConfig);

            montoyaApi.http().registerSessionHandlingAction(new MySessionHandlingAction(EXTENSION_NAME, configurationProvider.getMatchers()));
        }
        catch (RuntimeException e)
        {
            logging.raiseErrorEvent(e.getMessage());
            logging.logToOutput(e.getMessage());
            logging.logToError(e);
            montoyaApi.http().registerSessionHandlingAction(new InvalidSessionHandlingAction());
        }
    }

    public static class InvalidSessionHandlingAction implements SessionHandlingAction
    {
        @Override
        public String name()
        {
            return EXTENSION_NAME;
        }

        @Override
        public ActionResult performAction(SessionHandlingActionData sessionHandlingActionData)
        {
            return actionResult(sessionHandlingActionData.request());
        }
    }
}
