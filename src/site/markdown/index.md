# What is this?

This little Java agent lets you log whenever `ExceptionInInitializerError` is instantiated.
I wrote this because this exception doesn't get chained to later `NoClassDefFoundError`,
and as such it is very easy to lose track of why the class failed to load in the first place.

See [the bug report against JDK for this](https://bugs.openjdk.java.net/browse/JDK-8051847)
for further justification, which has been ignored for more than 3 years.

With this agent, any `ExceptionInInitializerError` gets logged through `java.util.logging`
logger of the name `org.kohsuke.eiie_logger` at the warning level. In this way, you only
need to look at one place to find all the `ExceptionInInitializerError` problems in your JVM.

# Usage

Use it as a Java agent to activate this from the beginning of the program.
This is recommended so as not to miss any `ExceptionInInitializerError`

    $ java -javaagent:path/to/eiie-logger.jar ...your usual Java args follows...

# Attaching after VM startup

When run as a regular jar file, this tool can be used to attach the logger into other JVMs
on the same system. You specify the JVM by its PID. The following example attaches
the tool to the pid 1500.

    $ java -jar path/to/eiie-logger.jar 1500
