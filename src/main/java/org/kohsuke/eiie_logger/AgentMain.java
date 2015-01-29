package org.kohsuke.eiie_logger;

import org.kohsuke.eiie_logger.transform.ClassTransformSpec;
import org.kohsuke.eiie_logger.transform.CodeGenerator;
import org.kohsuke.eiie_logger.transform.MethodAppender;
import org.kohsuke.eiie_logger.transform.TransformerImpl;

import java.lang.instrument.Instrumentation;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Java agent that instruments JDK classes to keep track of where file descriptors are opened.
 * @author Kohsuke Kawaguchi
 */
@SuppressWarnings("Since15")
public class AgentMain {
    public static void agentmain(String agentArguments, Instrumentation instrumentation) throws Exception {
        premain(agentArguments,instrumentation);
    }
    
    public static void premain(String agentArguments, Instrumentation instrumentation) throws Exception {
        System.err.println("Logging enabled for ExceptionInInitializerError ");
        instrumentation.addTransformer(new TransformerImpl(createSpec()),true);
        
        instrumentation.retransformClasses(ExceptionInInitializerError.class);
    }

    static List<ClassTransformSpec> createSpec() {
        return Arrays.asList(
            new ClassTransformSpec("java/lang/ExceptionInInitializerError",
                    new ConstructorInterceptor("()V"),
                    new ConstructorInterceptor("(Ljava/lang/Throwable;)V"),
                    new ConstructorInterceptor("(Ljava/lang/String;)V")
            )
        );
    }

    /**
     * Intercepts the this.open(...) call in the constructor.
     */
    private static class ConstructorInterceptor extends MethodAppender {
        public ConstructorInterceptor(String constructorDesc) {
            super("<init>", constructorDesc);
        }

        protected void append(CodeGenerator g) {
            g.invokeAppStatic(EiieLog.class,"log",
                    new Class[]{ExceptionInInitializerError.class},
                    new int[]{0});
        }
    }
}
