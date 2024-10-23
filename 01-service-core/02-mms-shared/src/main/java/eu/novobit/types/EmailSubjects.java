package eu.novobit.types;

/**
 * The interface Email subjects.
 */
public interface EmailSubjects {

    /**
     * The constant FORGOT_PASSWORD_EMAIL_SUBJECT.
     */
    static final String FORGOT_PASSWORD_EMAIL_SUBJECT = "Forget password";
    /**
     * The constant PASSWORD_WILL_EXPIRE_EMAIL_SUBJECT.
     */
    static final String PASSWORD_WILL_EXPIRE_EMAIL_SUBJECT = "Password will expire";
    /**
     * The constant USER_CREATED_EMAIL_SUBJECT.
     */
    static final String USER_CREATED_EMAIL_SUBJECT = "Account created";
    /**
     * The constant RESUME_STATUS_CHANGED_SUBJECT.
     */
    static final String RESUME_STATUS_CHANGED_SUBJECT = "Resume status changed";
    /**
     * The constant ITEM_STATUS_CHANGED_SUBJECT.
     */
    static final String ITEM_STATUS_CHANGED_SUBJECT = "Item status changed";

    /**
     * The constant SHARED_RESUME.
     */
    static final String SHARED_RESUME = "New resume shared with you";

    /**
     * The constant OTP_CODE_ACCESS.
     */
    static final String OTP_CODE_ACCESS = "Otp code access";
    /**
     * The constant SHARED_JOB.
     */
    static final String SHARED_JOB = "New job shared with you";
}
