package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Post not found exception.
 */
@MsgLocale("post.not.found.exception")
public class PostNotFoundException extends ManagedException {

    /**
     * Instantiates a new Post not found exception.
     *
     * @param s the s
     */
    public PostNotFoundException(String s) {
    }
}
