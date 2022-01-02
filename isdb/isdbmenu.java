/*
 * isdbmenu.java
 * ISDBj
 */

package isdb;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.datas.*;
import isdb.ifaces.cfgi;

/**
 * Відображення HTML сторінки меню.
 * @version 1.0 final, 11-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbmenu extends HttpServlet
{
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = false;

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        isdb.depts.deptobj oThisMenu = null;
        isdb.depts.deptobj oThisDept = null;

        String strOut = "";
        String strJS = null;

        // одержання номера поточної сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        jsdata oBodyJSData = new jsdata ();
        menudata oMenuData = null;

        // одержання вхідних параметрів
        oThisDBData.getData (req);

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        PrintWriter pwOut = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), isdb.ifaces.cfgi.getJavaCharSet ())));

        String strDept = oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);
        String strMenu = oThisDBData.getVal (isdb.miscs.dclrs.PAR_MENU);
        try
        {
            oThisDept = isdb.depts.deptobj.getDept (strDept);
            oThisMenu = oThisDept.getMenu (strMenu);
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
            if (String.valueOf (oThisDBData.getVal (isdb.miscs.dclrs.PAR_FAILAPPL)) != "null")
                oThisDBData.removeVal (isdb.miscs.dclrs.PAR_FAILAPPL);
            strOut = isdb.ifaces.htmli.hline () +
                     isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                     isdb.ifaces.htmli.place (
                       isdb.ifaces.htmli.div (
                         isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                 oThisDBData.getHTMLParams () +
                                                 isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbmenu"),
                                                 oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)), false) +
                     isdb.ifaces.htmli.hline ();

            // вітальне вікно
            // oBodyJSData.setJS (isdb.miscs.dclrs.REGIME_CONTINUE, isdb.ifaces.jsi.JS_FUNC_WND_LOGO);
        }
        else
        {
            oMenuData = oThisMenu.getData ();

            if (MODE_DEBUG)                // тестувал. режим?
            {
                Exception e1 = new Exception (oMenuData.toString ());
                e1.printStackTrace ();
            }

            jsdata oJSData = new jsdata ();
            oJSData.setJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + isdb.miscs.dclrs.REGIME_HELP, isdb.ifaces.jsi.JS_FUNC_WND_OPEN,
                           res.encodeUrl ("'" + isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" +
                                          isdb.miscs.dclrs.OBJ_MENU + "_" + strMenu + "_" + strDept + ".html'"), "'scrollbars=yes,toolbar=yes'");

            // допомога
            strOut =
              isdb.ifaces.htmli.menuitem (
                "javascript:void(0)",
                isdb.ifaces.htmli.bold ("Допомога"),
                oJSData.getJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + isdb.miscs.dclrs.REGIME_HELP, isdb.ifaces.jsi.JS_ACTION_ONCLICK));

            // сканування пунктів меню
            String strMenuURL;
            int iParPos = 0;
            while (oMenuData.isPresentNextItem ())
            {
                strMenuURL = oMenuData.getURL ();
                if (oMenuData.isOpenNewWnd ())    // створити в новому вікні?
                {
                    oJSData.setJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + (new Integer (iParPos)).toString (), isdb.ifaces.jsi.JS_FUNC_WND_OPEN,
                                   "'" + res.encodeUrl (strMenuURL) + "'",
                                   "'menubar=yes,scrollbars=yes'");
                    strOut += isdb.ifaces.htmli.menuitem (
                                "javascript:void(0)", oMenuData.getTitle (),
                                oJSData.getJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + (new Integer (iParPos++)).toString (), isdb.ifaces.jsi.JS_ACTION_ONCLICK));
                }
                else
                {
                    if (oMenuData.isConfirmExec ())   //  підтвердити виконання?
                    {
                        oJSData.setJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + (new Integer (iParPos)).toString (), isdb.ifaces.jsi.JS_FUNC_CONFIRM,
                                       "'Дійсно потрібно закінчити роботу?'");
                        strOut += isdb.ifaces.htmli.menuitem (
                                    res.encodeUrl (strMenuURL), oMenuData.getTitle (),
                                    oJSData.getJS (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + (new Integer (iParPos++)).toString (), isdb.ifaces.jsi.JS_ACTION_ONCLICK));
                    }
                    else
                        strOut += isdb.ifaces.htmli.menuitem (
                                    res.encodeUrl (strMenuURL), oMenuData.getTitle ());
                }
            }

            // початок головної таблиці
            strOut =
              isdb.ifaces.htmli.div (
                isdb.ifaces.htmli.table (
                  isdb.ifaces.htmli.row (
                    isdb.ifaces.htmli.cell (
                      isdb.ifaces.htmli.title (oThisMenu.getName ()), 100)) +

                  // пункти меню
                  isdb.ifaces.htmli.row (
                    isdb.ifaces.htmli.cell (
                      isdb.ifaces.htmli.menu (strOut), 80, "left")
                  ), 1)
              );
        }

        // відображення HTML сторінки
        pwOut.println (isdb.ifaces.htmli.page (oThisDept.getName (),
                                               isdb.ifaces.htmli.body (oThisDept.getName (),
                                                                       oThisDept.getLogo (),
                                                                       res.encodeUrl (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" + oThisDept.getInfo ()),
                                                                       strOut, oBodyJSData.getJS (isdb.miscs.dclrs.REGIME_CONTINUE, isdb.ifaces.jsi.JS_ACTION_ONLOAD)),
                                               isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN) +
                                                                      isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_LOGO))));
        pwOut.close ();

        // сбирання сміття
        System.gc ();
    }
}

