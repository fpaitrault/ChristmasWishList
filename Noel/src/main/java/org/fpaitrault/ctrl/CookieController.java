package org.fpaitrault.ctrl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;

public class CookieController {
    private static CookieController instance = new CookieController();
    public static CookieController instance() { return instance; }
    /**
     * Returns the value of the cookie with the given name
     *
     * @param name
     *          The name of the cookie
     * @return Returns the value of the cookie
     */
    public final String getCookie(final String name) {
        Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent().getNativeRequest()).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(name)) {
                    String value = cookie.getValue();
                    return value;
                }
            }
        }
        return null;
    }

    /**
     * Sets a new cookie
     *
     * @param name
     *          The name of the cookie
     * @param value
     *          The value of the cookie
     * @param expire
     *          The number of seconds after the cookie should expire
     */
    public final void setCookie(final String name, final String value, final int expire) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).addCookie(cookie);
    }
}
