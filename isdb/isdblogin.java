/**
 * isdblogin.java
 * ISDBj
 */

package isdb;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.datas.*;
import isdb.ifaces.dbi;
import isdb.ifaces.cfgi;
import isdb.ifaces.htmli;
import isdb.miscs.dclrs;

/**
 * Реєстрація користувача БД.
 * @version 1.0 final, 24-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdblogin extends HttpServlet
{
    private String strTitle = isdb.miscs.dclrs.RPT_REGISTR;
    private String strGuest = null;
    private String strOptionGuestEnabled = "";
    private static final String PAR_USER = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "USER";
    private static final String PAR_PSWD = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "PSWD";
    private static final String PAR_TYPEUSER = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "TYPEU";

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
        strGuest = cfgi.getOption (isdb.miscs.dclrs.OPT_GUEST);
        if (String.valueOf (strGuest) != "null")     // використовуеться опція гістя?
            strOptionGuestEnabled =
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell ("", 20) +
                isdb.ifaces.htmli.cell ("Утел " + isdb.ifaces.htmli.formradiopar (PAR_TYPEUSER, "utel", true), 40, "left") +
                isdb.ifaces.htmli.cell ("Гість " + isdb.ifaces.htmli.formradiopar (PAR_TYPEUSER, "guest"), 40, "left"));
    }

    //
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        String strLastLoginUser = "";
        boolean bErrFlag = true;
        boolean bGuest = false;
        String strClientIP = null;
        String strBody = "";
        String strPicture = null;
        String strAppl = "isdblogin";
        String strButton = isdb.miscs.dclrs.REGIME_LOGIN;
        // HttpServletRequest.getHeader("User-Agent");

        // получение текущей сессии
        HttpSession session = req.getSession (true);
        String strSesnId = session.getId ();
        strClientIP = req.getRemoteAddr ();
        dbdata oThisDBData = new dbdata (strSesnId);
        cookiedata oThisCookieData = new cookiedata ();
        jsdata oJSData = new jsdata ();

        // получення вхідних параметрів
        oThisDBData.getData (req);

        // выходной поток
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter pwOut = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream(), isdb.ifaces.cfgi.getJavaCharSet ())));

        // одержання аутентифікації
        // String strAuth = req.getHeader ("Authorization");

        // закінчення роботи користувача?
        if ((String.valueOf (oThisDBData.getVal (isdb.miscs.dclrs.PAR_LOGOFF)) != "null") &&
                oThisDBData.getVal (isdb.miscs.dclrs.PAR_LOGOFF).equals (isdb.miscs.dclrs.YES))
        {
            dbi.clearConn (oThisDBData);
            session.invalidate ();
            oThisDBData.removeVal (isdb.miscs.dclrs.PAR_LOGOFF);
            strAppl = oThisDBData.getVal (isdb.miscs.dclrs.PAR_FAILAPPL) + "?" +
                      oThisDBData.getHTTPParams ();
            oThisDBData.removeVal (isdb.miscs.dclrs.PAR_FAILAPPL);

            // res.sendError (HttpServletResponse.SC_UNAUTHORIZED);
            res.sendRedirect (
              res.encodeRedirectUrl (
                res.encodeUrl (strAppl)));
        }

        // це - гість чи співробітник Утел?
        if (String.valueOf (oThisDBData.getVal (PAR_TYPEUSER)) != "null")
        {
            if (oThisDBData.getVal (PAR_TYPEUSER).equals ("guest"))     // це гість?
            {
                int iPos = strGuest.indexOf ('/');
                oThisDBData.setVal (PAR_USER, strGuest.substring (0, iPos));
                oThisDBData.setVal (PAR_PSWD, strGuest.substring (iPos + 1, strGuest.length ()));
                bGuest = true;              // режим гістя
            }
        }

        // одержання cookies
        oThisCookieData.getData (req);
        strLastLoginUser = oThisCookieData.getVal (isdb.miscs.dclrs.COOKIE_LASTLOGINUSER);

        // перевірка реєстрації
        if (!dbi.chkConn (strSesnId /*, strAuth */ ))
        {
            if ((String.valueOf (oThisDBData.getVal (PAR_USER)) == "null") &&
                    (String.valueOf (oThisDBData.getVal (PAR_PSWD)) == "null"))
            {
                strPicture = isdb.ifaces.htmli.image ("lock.gif", 150, 111, isdb.miscs.dclrs.RPT_REGISTR);

                // звіт про відсутність прав доступу
                // res.setHeader ("WWW-Authenticate", "BASIC realm=\"users\"");
                // res.sendError (res.SC_UNAUTHORIZED);
            }
            else
            {

                // реєстрація користувача
                if (dbi.setConn (strSesnId, oThisDBData.getVal (PAR_USER), oThisDBData.getVal (PAR_PSWD), strClientIP))
                {
                    strPicture = isdb.ifaces.htmli.image ("lockok.gif", 150, 100, isdb.miscs.dclrs.RPT_REGISTR);
                    bErrFlag = false;

                    // встановлення cookies
                    oThisCookieData.setVal (isdb.miscs.dclrs.COOKIE_LASTLOGINUSER, oThisDBData.getVal (PAR_USER));

                    // відправлення cookies кліенту
                    oThisCookieData.setData (res);
                }
                else
                    strPicture = isdb.ifaces.htmli.image ("lockng.gif", 150, 111, isdb.miscs.dclrs.RPT_REGISTR);
            }
        }
        else
        {
            strPicture = isdb.ifaces.htmli.image ("lockok.gif", 150, 100, isdb.miscs.dclrs.RPT_REGISTR);
            bErrFlag = false;

            // встановлення значеннь конфігураційних отцій
            cfgi.setOptions (oThisCookieData);
        }
        if (!bErrFlag)   // були помилки при реєстрації?
        {
            strBody += isdb.ifaces.htmli.hline () + isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_REGISTR_OK);
            strButton = isdb.miscs.dclrs.REGIME_CONTINUE;
            strAppl = oThisDBData.getVal (isdb.miscs.dclrs.PAR_FAILAPPL);
            oThisDBData.removeVal (isdb.miscs.dclrs.PAR_FAILAPPL);
            oThisDBData.removeVal (isdb.miscs.dclrs.PAR_LOGOFF);
            oThisDBData.removeVal (PAR_USER);
            oThisDBData.removeVal (PAR_TYPEUSER);
            oThisDBData.removeVal (PAR_PSWD);
            if (bGuest)           // звіт для попередження гостя потрібен?
                strBody += isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_REGISTR_RESTRICT);
            strBody += isdb.ifaces.htmli.hline ();
        }
        else
        {
            strBody += isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_REGISTR_NG);
            strButton = isdb.miscs.dclrs.REGIME_LOGIN;
            oThisDBData.removeVal (PAR_USER);
            oThisDBData.removeVal (PAR_PSWD);
            oJSData.setJS (PAR_USER, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (PAR_PSWD, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            strBody += isdb.ifaces.htmli.center (isdb.ifaces.htmli.bold (cfgi.getAffiliate ())) +
                       isdb.ifaces.htmli.crlf () +
                       isdb.ifaces.htmli.hline () +
                       isdb.ifaces.htmli.place (
                         isdb.ifaces.htmli.row (
                           isdb.ifaces.htmli.cell (isdb.ifaces.htmli.place (
                                                     isdb.ifaces.htmli.row (
                                                       isdb.ifaces.htmli.cell ("Користувач " +
                                                                               isdb.ifaces.htmli.formtextpar (
                                                                                 PAR_USER, strLastLoginUser, "20", oJSData.getJS (PAR_USER, isdb.ifaces.jsi.JS_ACTION_ONBLUR)), 100, "right")) +
                                                     isdb.ifaces.htmli.row (
                                                       isdb.ifaces.htmli.cell ("Пароль " +
                                                                               isdb.ifaces.htmli.formpswdpar (
                                                                                 PAR_PSWD, "20", oJSData.getJS (PAR_PSWD, isdb.ifaces.jsi.JS_ACTION_ONBLUR)), 100, "right")) +
                                                     strOptionGuestEnabled, 0, false), 70)
                         ), 0, false) +
                       isdb.ifaces.htmli.formclearbuttonpar () +
                       isdb.ifaces.htmli.hline ();
        }

        // приготування всіеї сторінки
        String strOut =
          isdb.ifaces.htmli.page (strTitle,

                                  // підготовлення тіла сторінки
                                  isdb.ifaces.htmli.body (strTitle,
                                                          strPicture,
                                                          "mailto:" + cfgi.getOption (isdb.miscs.dclrs.OPT_EMAILADM),

                                                          // тіло сторінки
                                                          isdb.ifaces.htmli.center (
                                                            isdb.ifaces.htmli.place (
                                                              isdb.ifaces.htmli.div (
                                                                isdb.ifaces.htmli.form (res.encodeUrl (strAppl),
                                                                                        isdb.ifaces.htmli.center (strBody) +
                                                                                        oThisDBData.getHTMLParams (),
                                                                                        oThisDBData.getTitleButton (strButton), oJSData.getJS ())), false)), null),
                                  isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NULL)));

        // видача підготовлен. HTML сторінки в вихідний потік
        pwOut.println (strOut);
        pwOut.close ();
    }
}

