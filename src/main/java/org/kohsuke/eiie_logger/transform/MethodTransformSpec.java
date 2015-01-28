package org.kohsuke.eiie_logger.transform;

import org.kohsuke.asm5.MethodVisitor;

/**
 * Transforms a specific method.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class MethodTransformSpec {
    /**
     * Name of the method to transform.
     */
    public final String name;

    /**
     * Method signature.
     */
    public final String desc;

    public MethodTransformSpec(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    /**
     * Creates a visitor that receives the original method definition and writes
     * the transformed method to the given base.
     */
    public abstract MethodVisitor newAdapter(MethodVisitor base, int access, String name, String desc, String signature, String[] exceptions);
}
