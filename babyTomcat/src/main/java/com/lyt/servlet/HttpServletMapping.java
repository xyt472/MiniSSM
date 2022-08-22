package com.lyt.servlet;

public interface HttpServletMapping {


    /**
     * @return The value that was matched or the empty String if not known.
     */
    String getMatchValue();

    /**
     * @return The {@code url-pattern} that matched this request or the empty
     *         String if not known.
     */
    String getPattern();


    String getServletName();

    /**
     * @return The type of match ({@code null} if not known)
     */
  //  MappingMatch getMappingMatch();
}
