/**
 * isdbreport.java
 * ISDBj
 */

package isdb;

// Імпортування классів
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.text.SimpleDateFormat;

import isdb.objs.*;
import isdb.datas.*;
import isdb.ifaces.cfgi;

/**
 * Формування звітів та графіків.
 * <P>Вхідни параметри:
 * <UL>
 * <LI>PAR_APPL - назва викликаемого після проведення операції наступного сервлета,
 * <LI>PAR_OBJECT - використовуемий об'ект БД,
 * <LI>PAR_ID - ідентификатор об'екта PAR_OBJECT,
 * <LI>PAR_DEPT - назва відділу,
 * <LI>PAR_RPT_ID - номер звіту
 * <LI>Необов'язкові параметри:
 * <UL>
 * <LI>PAR_TYPE_OUT
 * </UL>
 * </UL>
 * @see isdb.miscs.dclrs#PAR_APPL
 * @see isdb.miscs.dclrs#PAR_OBJECT
 * @see isdb.miscs.dclrs#PAR_ID
 * @see isdb.miscs.dclrs#PAR_DEPT
 * @see isdb.miscs.dclrs#PAR_RPT_ID
 * @version 1.0 final, 25-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbreport extends HttpServlet
{
    private String strSite = null;
    private String strSwitch = null;
    private String strNameExchange = "бізнес-АТС";

    /**
     * Метод ініціаліції сервлета
     * @param conf
     */
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
        strSwitch = isdb.ifaces.cfgi.getSwitch ();
        strSite = isdb.ifaces.cfgi.getAffiliate ();
    }

    //
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws IOException
    {

        isdbobj oThisObj = null;
        isdb.depts.deptobj oThisDept = null;

        String strTitle = null;
        String strFormTitle = null;
        String strBody = "";
        String strBodyRpt = "";
        int iBorder = 1;

        // одержання номера поточної сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);

        // одержання вхідних параметрів
        oThisDBData.getData (req);

        // одержання вхідних параметрів
        oThisDBData.getData (req);

        ///
        Exception e18 = new Exception ("isdbreport start: " + oThisDBData.toString ());
        e18.printStackTrace ();
        ///

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream(), isdb.ifaces.cfgi.getJavaCharSet ())));

        if (oThisDBData.isObjectNull ())
            oThisDBData.setError (isdb.miscs.dclrs.PAR_OBJECT);
        if (!oThisDBData.isPresent (isdb.miscs.dclrs.PAR_RPT_ID))
            oThisDBData.setError (isdb.miscs.dclrs.PAR_RPT_ID);

        ///
        Exception e1 = new Exception ("isdbreport: " + oThisDBData.toString ());
        e1.printStackTrace ();
        ///

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
        if (!isdb.ifaces.dbi.chkConn (strSesnId))
        {
            strBody = isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                      isdb.ifaces.htmli.div (
                        isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                oThisDBData.getHTMLParams () +
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbreport"),
                                                isdb.miscs.dclrs.REGIME_LOGIN, null)) +
                      isdb.ifaces.htmli.hline ();
        }
        else
        {
            // була помилка во вхідних параметрах?
            if (!oThisDBData.isError ())
            {
                // одержання параметрів для сторінки
                oThisObj.describe (oThisDBData);
                oThisDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, oThisDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID));
                if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_TYPE_OUT) &&
                        oThisDBData.getVal (isdb.miscs.dclrs.PAR_TYPE_OUT).equals (isdb.miscs.dclrs.TYPE_OUT_PIECHART))
                    oThisDBData = oThisObj.piechart (oThisDBData);
                else
                    oThisDBData = oThisObj.report (oThisDBData);
                strBodyRpt = oThisDBData.getVal (isdb.miscs.dclrs.PAR_REPORT);
                strTitle = oThisDBData.getVal (isdb.miscs.dclrs.PAR_RPT_TITLE);
                if (String.valueOf (strTitle) == "null")
                    strTitle = "Звіт для друку";

                SimpleDateFormat dfDateMask = new SimpleDateFormat ("dd-MM-yy");
                String strCurDate = dfDateMask.format (new Date ());

                strFormTitle = isdb.ifaces.htmli.title (strTitle);

                // це звіт для друку?
                if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).startsWith ("RPT"))
                    iBorder = 1;
                else
                {
                    // це форма?
                    if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).startsWith ("FRM"))
                    {
                        strBody = isdb.ifaces.htmli.crlf () +

                                  // шапка
                                  isdb.ifaces.htmli.place (
                                    isdb.ifaces.htmli.row (
                                      isdb.ifaces.htmli.cell (
                                        isdb.ifaces.htmli.font (strSite + isdb.ifaces.htmli.crlf () +
                                                                "  " + strSwitch + isdb.ifaces.htmli.crlf () +
                                                                "Дата: " +
                                                                strCurDate +
                                                                isdb.ifaces.htmli.crlf () +
                                                                "  Найменування станції: " + strNameExchange, "800080", 2), 70, "left") +
                                      isdb.ifaces.htmli.cell (isdb.ifaces.htmli.font (oThisDBData.getVal (isdb.miscs.dclrs.PAR_RPT_NUMB), "800080", 1), 30, "right")
                                    ), 0, false);
                    }
                    else
                    {
                        iBorder = 0;
                        strFormTitle = "";
                    }
                }

                // початок головної таблиці
                strBody +=
                  isdb.ifaces.htmli.place (

                    // назва звіта
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (strFormTitle, 100)) +

                    // сформований звіт або графік
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.place (strBodyRpt, 0, false), 100)
                    ), iBorder, false);
            }
            else oThisDBData.setError (isdb.miscs.dclrs.PAR_REGIME);
        }
        jsdata oJSData = new jsdata ();
        oJSData.setJS (isdb.miscs.dclrs.REGIME_PRINT, isdb.ifaces.jsi.JS_FUNC_PROMPT_PRINT);

        // була помилка?
        if (oThisDBData.isError ())
            strBody =
              isdb.ifaces.htmli.error (oThisDBData.getError ()) + strBody;

        // приготування всіеї сторінки
        String strOut =
          isdb.ifaces.htmli.page (strTitle,

                                  // підготовлення тіла сторінки
                                  isdb.ifaces.htmli.body (
                                    /*
                                    oThisDept.getName (),
                                                            oThisDept.getLogo (),
                                                            null,             // нема шапки
                                    */
                                    strBody,
                                    oJSData.getJS (isdb.miscs.dclrs.REGIME_PRINT, isdb.ifaces.jsi.JS_ACTION_ONLOAD)),
                                  isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_PROMPT_PRINT)));

        // видача підготовлен. HTML сторінки в вихідний потік
        out.println (strOut);
        out.close ();
    }
}

