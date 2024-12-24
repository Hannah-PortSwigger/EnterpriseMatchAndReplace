package burp;

import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.sessions.ActionResult;
import burp.api.montoya.http.sessions.SessionHandlingAction;
import burp.api.montoya.http.sessions.SessionHandlingActionData;
import utils.MatcherRecord;

import java.util.List;

import static burp.api.montoya.http.HttpService.httpService;
import static burp.api.montoya.http.message.requests.HttpRequest.httpRequest;

public class MySessionHandlingAction implements SessionHandlingAction
{
    private final String extensionName;
    private final List<MatcherRecord> matchers;

    public MySessionHandlingAction(String extensionName, List<MatcherRecord> matchers)
    {
        this.extensionName = extensionName;
        this.matchers = matchers;
    }

    @Override
    public String name()
    {
        return extensionName;
    }

    @Override
    public ActionResult performAction(SessionHandlingActionData sessionHandlingActionData)
    {
        String requestString = sessionHandlingActionData.request().toString();
        for (MatcherRecord m : matchers)
        {
            requestString = requestString.replaceAll(m.match(), m.replace());
        }

        HttpRequest request = httpRequest(requestString);
        HttpRequest requestWithService = request
                .withService(httpService(
                    request.headerValue("host"),
                    sessionHandlingActionData.request().httpService().secure()))
                .withBody(request.body());

        return ActionResult.actionResult(requestWithService);
    }
}
