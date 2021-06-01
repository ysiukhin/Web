package ua.traning.rd.java.finalproject.servlet.tag;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
public class CacheOffTag extends TagSupport {
    public int doEndTag() throws JspException {
        HttpServletResponse response =
                (HttpServletResponse) pageContext.getResponse();
        response.addHeader("Pragma", "No-Cache");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "pre-check=0, post-check=0");
        response.setDateHeader("Expires", 0);
        return EVAL_PAGE;
    }
}
