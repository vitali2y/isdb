/**
 * isdbgraph.java
 * ISDBj
 */

package isdb;

// Імпортування классів
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.objs.*;
import isdb.datas.*;
import isdb.ifaces.dbi;
import isdb.ifaces.cfgi;
import isdb.ifaces.htmli;
import isdb.miscs.dclrs;
import isdb.depts.deptobj;

/**
 * Приготування графіків.
 * @version 1.0 final, 24-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbgraph extends HttpServlet
{
    private String strSite = null;
    private String strSwitch = null;

    /**
     * Метод ініціаліції сервлета
     * @param
     */
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
        strSwitch = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_SWITCH);
        strSite = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_SITE);
    }

    //
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws IOException
    {
        // получение текущей сессии
        HttpSession session = req.getSession (true);
        String strSesnId = session.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);

        // получення вхідних параметрів
        oThisDBData.getData (req);

        isdbobj oThisObj = null;
        isdb.depts.deptobj oThisDept = null;

        //
        String strErrMsg = null;
        String strTitle = null;
        String strBody = null;

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream(), isdb.ifaces.cfgi.getJavaCharSet ())));

        // будова об'ектів
        String strObject = "isdb.objs.obj";
        try
        {
            strObject += oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT);
            oThisObj = (isdb.objs.isdbobj) Class.forName (strObject).newInstance ();
            oThisDept = isdb.depts.deptobj.getDept (oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        }
        catch (ClassNotFoundException ex)
            { ex.printStackTrace (); }
        catch (ClassCastException ex)
            { ex.printStackTrace (); }
        catch (IllegalAccessException ex)
            { ex.printStackTrace (); }
        catch (InstantiationException ex)
            { ex.printStackTrace (); }

        // перевірка реєстрації користувача
        if (!dbi.chkConn (strSesnId))
        {
            strBody = isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                      isdb.ifaces.htmli.div (
                        isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                oThisDBData.getHTMLParams () +
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbgraph"),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) + isdb.ifaces.htmli.hline ();
        }
        else
        {
            // була помилка во вхідних параметрах?
            if (String.valueOf (strErrMsg) == "null")
            {

                // одержання параметрів для сторінки
                oThisObj.select (oThisDBData);
                strTitle = oThisObj.getTitle (oThisDBData);

                // кінцеве приготування тіла сторінки
                strBody = isdb.ifaces.htmli.crlf () +

                          // початок головної таблиці
                          isdb.ifaces.htmli.place (

                            // назва
                            isdb.ifaces.htmli.row (
                              isdb.ifaces.htmli.cell (
                                isdb.ifaces.htmli.title (strTitle), 100)) +
                            isdb.ifaces.htmli.row (
                              isdb.ifaces.htmli.cell (
                                oThisObj.graph (oThisDBData)
                                , 100)), 1, false);
            }
            else
                strBody = isdb.ifaces.htmli.error (strErrMsg);
        }

        // приготування всіеї сторінки
        String strOut =
          isdb.ifaces.htmli.page (strTitle,

                                  // підготовлення тіла сторінки
                                  isdb.ifaces.htmli.body (oThisDept.getName (),
                                                          oThisDept.getLogo (),
                                                          res.encodeUrl (oThisDept.getInfo ()),

                                                          // підготовлення тіла сторінки
                                                          strBody +
                                                          isdb.ifaces.htmli.crlf () +

                                                          // кнопка повернення
                                                          isdb.ifaces.htmli.place (
                                                            isdb.ifaces.htmli.row (
                                                              isdb.ifaces.htmli.cell (
                                                                isdb.ifaces.htmli.form (res.encodeUrl ("isdbmenu"),
                                                                                        isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                                                                                        isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL)),
                                                                                        oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN), null), 100)), 0, false), null));

        // видача підготовлен. HTML сторінки в вихідний потік
        out.println (strOut);
        out.close ();

        // сбирання сміття
        // System.gc ();
    }
}

