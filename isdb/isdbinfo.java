/**
 * isdbinfo.java
 * ISDBj
 */

package isdb;

// Імпортування классів
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.objs.*;
import isdb.datas.*;
import isdb.ifaces.cfgi;

/**
 * Відображення довідкової інформації.
 * <P>Вхідни параметри:
 * <UL>
 * <LI>PAR_ID - ідентификатор відображаемого файла.
 * <P>Якщо цей параметр відсутній, то відображаеться головна інформаційна сторінка.
 * </UL>
 * @version 1.0 final, 24-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbinfo extends HttpServlet
{
    private static String strJS = isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN));

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    //
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        isdb.depts.deptobj oThisDept = null;
        String strBody = isdb.ifaces.htmli.crlf ();
        String strAppl = "isdbinfo";
        String strTitle = isdb.miscs.dclrs.ISDB_NAME;
        String strDescRegime = null;

        // одержання номера поточної сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);
        jsdata oBodyJSData = new jsdata ();

        // одержання вхідних параметрів
        oThisDBData.getData (req);

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), isdb.ifaces.cfgi.getJavaCharSet ())));

        // будова відділу
        try
        {
            oThisDept = isdb.depts.deptobj.getDept (oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        }
        catch (ClassNotFoundException ex)
            { ex.printStackTrace (); }
        catch (ClassCastException ex)
            { ex.printStackTrace (); }
        catch (IllegalAccessException  ex)
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
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbinfo"),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) +
                      isdb.ifaces.htmli.hline ();
        }
        else
        {
            // була помилка во вхідних параметрах?
            if (oThisDBData.isError ())
                strBody = oThisDBData.getError ();
            else
            {
                // вітальне вікно
                // oBodyJSData.setJS (isdb.miscs.dclrs.REGIME_CONTINUE, isdb.ifaces.jsi.JS_FUNC_WND_LOGO);
                if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID).equals ("info"))          // відображати загальну інформацію?
                {
                    isdb.depts.deptobj oDeptMngr = null;
                    isdb.depts.deptobj oDeptSales = null;
                    isdb.depts.deptobj oDeptMaint = null;
                    try
                    {
                        oDeptMngr = isdb.depts.deptobj.getDept (isdb.miscs.dclrs.DEPT_MNGR);
                        oDeptSales = isdb.depts.deptobj.getDept (isdb.miscs.dclrs.DEPT_SALES);
                        oDeptMaint = isdb.depts.deptobj.getDept (isdb.miscs.dclrs.DEPT_MAINT);
                    }
                    catch (ClassNotFoundException ex)
                    { ex.printStackTrace (); }
                    catch (ClassCastException ex)
                    { ex.printStackTrace (); }
                    catch (IllegalAccessException ex)
                    { ex.printStackTrace (); }
                    catch (InstantiationException ex)
                    { ex.printStackTrace (); }

                    objperson oPerson = new objperson ();
                    dbdata oPersonDBData = new dbdata (strSesnId);
                    oPersonDBData.setCriteriaObj ("USERNAME='" + isdb.ifaces.dbi.user (strSesnId) + "'");
                    oPerson.select (oPersonDBData);

                    // додаткові кнопки
                    buttondata oMainPageButton = new buttondata ();
                    oMainPageButton.setUrl ("http://10.140.35.4/apps/isdb/index.html",
                                            true, "'scrollbars=yes,toolbar=yes'");     // відкривати в вигляді нового вікна
                    oMainPageButton.setName ("Головна сторінка " + isdb.miscs.dclrs.ISDB_SHORTNAME);
                    oThisDBData.setButton (oMainPageButton.getButton (oOutData));

                    buttondata oSupportPageButton = new buttondata ();
                    oSupportPageButton.setUrl ("http://10.140.35.4/apps/isdb/FAQ/faqindex.html",
                                               true, "'scrollbars=yes,toolbar=yes'");     // відкривати в вигляді нового вікна
                    oSupportPageButton.setName ("Підтримка абонентів");
                    oThisDBData.setButton (oSupportPageButton.getButton (oOutData));

                    strBody =
                      isdb.ifaces.htmli.crlf () +
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (

                          // назва програми, версія та філія
                          isdb.ifaces.htmli.place (
                            isdb.ifaces.htmli.row (
                              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.title (isdb.miscs.dclrs.ISDB_SHORTNAME + isdb.ifaces.htmli.crlf () + strTitle) +
                                                      isdb.ifaces.htmli.center (
                                                        isdb.ifaces.htmli.bold (isdb.ifaces.cfgi.getVersion () +
                                                                                isdb.ifaces.htmli.crlf (2) +
                                                                                isdb.ifaces.htmli.hline () +
                                                                                isdb.miscs.dclrs.ISDB_AUTHOR +
                                                                                isdb.ifaces.htmli.crlf (2) +

                                                                                isdb.ifaces.htmli.place (
                                                                                  isdb.ifaces.htmli.row (
                                                                                    isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Поточна інформація:"), 35) +
                                                                                    isdb.ifaces.htmli.cell (isdb.ifaces.cfgi.getAffiliate () +
                                                                                                            isdb.ifaces.htmli.crlf () +
                                                                                                            oThisDept.getName () +
                                                                                                            isdb.ifaces.htmli.crlf () +
                                                                                                            "Користувач: " +
                                                                                                            oPerson.retrieve (objperson.COL_PERSON, oPersonDBData), 65, "left")), 1)
                                                                               )), 100)
                            ), 0, false)
                          , 100)) +
                      isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.crlf () +

                      // загальна інформація
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (

                          isdb.ifaces.htmli.place (
                            isdb.ifaces.htmli.row (
                              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.href (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" +
                                                      oDeptMngr.getInfo (), oDeptMngr.getLogo ()) +
                                                      isdb.ifaces.htmli.crlf () +
                                                      isdb.ifaces.htmli.msg (oDeptMngr.getName ()), 33) +
                              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.href (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" +
                                                      oDeptSales.getInfo (), oDeptSales.getLogo ()) +
                                                      isdb.ifaces.htmli.crlf () +
                                                      isdb.ifaces.htmli.msg (oDeptSales.getName ()), 33) +
                              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.href (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" +
                                                      oDeptMaint.getInfo (), oDeptMaint.getLogo ()) +
                                                      isdb.ifaces.htmli.crlf () +
                                                      isdb.ifaces.htmli.msg (oDeptMaint.getName ()), 33)
                            ), 0, false) +

                          // додавання додаткових кнопок до форми
                          isdb.ifaces.htmli.place (
                            isdb.ifaces.htmli.row (
                              oThisDBData.getButtons ()), 0, false) +

                          isdb.ifaces.htmli.hline ()
                          , 100));
                }
                else            // отобразити стандартну інформацію?
                    strBody = oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID);
            }

            // відображення HTML сторінки
            out.println (
              isdb.ifaces.htmli.page (strTitle,

                                      // підготовлення тіла сторінки
                                      isdb.ifaces.htmli.body (
                                        null, null,
                                        isdb.miscs.dclrs.NO,  // нема шапки

                                        // підготовлення тіла сторінки
                                        strBody +
                                        isdb.ifaces.htmli.crlf () +

                                        // кнопка повернення
                                        isdb.ifaces.htmli.place (
                                          isdb.ifaces.htmli.row (
                                            isdb.ifaces.htmli.cell (
                                              isdb.ifaces.htmli.form (res.encodeUrl ("isdbmenu"),
                                                                      isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                                                                      isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, "main"),
                                                                      oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN), null), 100)), 0, false),
                                        oBodyJSData.getJS (isdb.miscs.dclrs.REGIME_CONTINUE, isdb.ifaces.jsi.JS_ACTION_ONLOAD)),
                                      // null),
                                      // isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN)

                                      isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN) +
                                                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_LOGO)

                                                             /* +
                                                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_LOGO)
                                                             */
                                                            )));
            out.close ();
        }
    }
}

