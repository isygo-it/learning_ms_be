package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Workflow not found exception.
 */
@MsgLocale("workflow.not.found.exception")
public class WorkflowNotFoundException extends ManagedException {

    /**
     * Instantiates a new Workflow not found exception.
     *
     * @param s the s
     */
    public WorkflowNotFoundException(String s) {
        super(s);
    }
}
