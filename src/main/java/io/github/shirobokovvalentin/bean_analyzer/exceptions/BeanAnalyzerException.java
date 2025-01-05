package io.github.shirobokovvalentin.bean_analyzer.exceptions;

public class BeanAnalyzerException extends RuntimeException
{
    public BeanAnalyzerException(String message)
    {
        super(message);
    }

    public BeanAnalyzerException(Throwable cause)
    {
        super(cause);
    }
}
