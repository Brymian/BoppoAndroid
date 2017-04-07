package brymian.bubbles.damian.nonactivity.CustomException;

public class SetOrNotException extends Exception
{
    public SetOrNotException(){}
    public SetOrNotException(String exception) { super(exception); }
    public SetOrNotException(Throwable cause) { super(cause); }
    public SetOrNotException(String exception, Throwable cause) { super(exception, cause); }
}
