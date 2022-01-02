/**
 * cookiedata.java
 * ISDBj
 */

package isdb.datas;

import javax.servlet.http.Cookie;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Використання cookie для тимчасового збереження
 * конфігураційних даних користувача на машині користувача.
 * @version 1.0 final, 16-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class cookiedata
{
    /** Поточни cookies */
    private Hashtable hashCookies;

    /**
     * Конструктор.
     */
    public cookiedata ()
    {
        hashCookies = new Hashtable ();
    }

    /**
     * Повернення значення cookie.
     * @param oKey назва cookie
     * @return значення cookie
     */
    public String getVal (Object oKey)
    {
        return (String) hashCookies.get (oKey);
    }

    /**
     * Збереження значення cookie.
     * @param oKey назва cookie
     * @param oValue значення cookie
     */
    public void setVal (Object oKey, Object oValue)
    {
        hashCookies.put (oKey, oValue);
    }

    /**
     * Повернення усіх cookies від кліента.
     * @param req вхідний потік сервлета
     */
    public void getData (javax.servlet.http.HttpServletRequest req)
    {
        String strCurCookie = null;
        Cookie [] cookCookies = req.getCookies ();
        for (int iI = 0; iI < cookCookies.length; iI++)
        {
            strCurCookie = cookCookies [iI].getName ();
            if (!strCurCookie.equals ("JServSessionId"))   // це не cookie, який належить Apache?
                hashCookies.put (strCurCookie, cookCookies [iI].getValue ());
        }
    }

    /**
     * Відправлення усіх cookies до кліента.
     * @param res вихідний потік сервлета
     */
    public void setData (javax.servlet.http.HttpServletResponse res)
    {
        String strCookieName = null;
        Enumeration enumCookies = hashCookies.keys ();
        while (enumCookies.hasMoreElements ())
        {
            strCookieName = (String) enumCookies.nextElement ();
            Cookie cookNewCookie = new Cookie (strCookieName, (String) hashCookies.get (strCookieName));
            res.addCookie (cookNewCookie);
        }
    }
}

