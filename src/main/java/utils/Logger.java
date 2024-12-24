package utils;

import burp.api.montoya.logging.Logging;

public class Logger
{
    private final String extensionName;
    private final Logging montoyaLogging;

    public Logger(String extensionName, Logging montoyaLogging)
    {
        this.extensionName = extensionName;
        this.montoyaLogging = montoyaLogging;
    }

    public void logToOutput(String s)
    {
        montoyaLogging.logToOutput(extensionName + " - " + s);
    }

    public void logToError(String s)
    {
        montoyaLogging.logToError(extensionName + " - " + s);
    }

    public void logToError(String s, Throwable throwable)
    {
        montoyaLogging.logToError(extensionName + " - " + s, throwable);
    }

    public void logToError(Throwable throwable)
    {
        montoyaLogging.logToError(throwable);
    }

    public void raiseErrorEvent(String s)
    {
        montoyaLogging.raiseErrorEvent(s);
    }
}
