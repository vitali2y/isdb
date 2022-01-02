/**
 * isdbenviron.java
 * ISDBj
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// import isdb.dclrs;
// import isdb.cfgi;
// import isdb.dbi;
// import isdb.htmli;
// import isdb.dept.deptabstract;

import isdb.datas.*;
import isdb.exceps.*;

/**
 * Загальне віртуальне середовище, яке підтримує технологію Java Servlets та
 * використовується для реалізації системи ISDB (servlet based presentation layer).
 * @version 1.0 final, 22-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbenviron extends HttpServlet
{
    private static globaldata oGlobalData;
    private String strTitle;
    private String strName;
    private String strInfo;

    /**
      * Одержання назви сервлета.
      * @return назва сервлета
      */
    public String getName ()
    {
        return this.strName;
    }

    /**
     * Встановлення назви модуля прі відображенні.
     * @param strTitle назва модуля
     */
    public void setTitle (String strTitle)
    {
        this.strTitle = strTitle;
    }

    /**
     * Одержання назви модуля прі відображенні.
     * @return назва модуля
     */
    public String getTitle ()
    {
        return this.strTitle;
    }

    /**
     * Одержання інформації сервлета.
     * @return інформація сервлета
     */
    public String getInfo ()
    {
        return this.strInfo;
    }

    /**
     * Ініціалізаційна фаза сервлета.
     * <P>Рівень - сервлет.
     * @param conf
     */
    public void init (ServletConfig conf)
    throws ServletException, isdbinitexception
    {
        super.init (conf);
        InitPhase ();
    }

    /**
     * Ініціалізаційна фаза середовища.
     * <P>Використовується під час первинного запуску середовища,
     * наприклад, для одержання конфігураційної інформації.
     * <P>Рівень - віртуальне середовище.
     * @exception isdbinitexception
     */
    public void InitPhase ()
    throws isdbinitexception
    {
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    /**
     * Фаза прийняття вхідних параметрів.
     * @param req вхідний HTTP потік
     * @param oInData поточні вхідні дані
     * @param oCookieData
     * @exception isdbinitexception
     */
    public void InputPhase (HttpServletRequest req, indata oInData, cookiedata oCookieData)
    throws isdbinitexception
    {
        // одержання вхідних параметрів
        oInData.getData (req);

        // одержання cookies
        if (oCookieData != null)
            oCookieData.getData (req);

        // параметр PAR_DEPT - відсутній?
        if (!oInData.isPresent (isdb.miscs.dclrs.PAR_DEPT) ||
                !oGlobalData.isPresent (isdb.miscs.dclrs.PAR_DEPT))
            throw new isdbinputexception (isdb.miscs.dclrs.PAR_DEPT, oInData);
    }

    /**
     * Фаза прийняття вхідних параметрів.
     * @param req вхідний HTTP потік
     * @param oInData поточні вхідні дані
     * @see #InputPhase (HttpServletRequest, indata, cookiedata)
     */
    public void InputPhase (HttpServletRequest req, indata oInData)
    throws isdbinputexception
    {
        InputPhase (req, oInData, null);
    }

    /**
     * Фаза аутентіфікації користувача в БД.
     * @param strFailAppl назва сервлета, куди після реєстрації потрібно повернутися
     * @param oInData поточні вхідні дані
     */
    public void LoginPhase (String strFailAppl, indata oInData)
    throws isdbloginexception
    {
        if (!oInData.isConnected ())
            throw isdbloginexception (strFailAppl, oInData);
    }

    /**
     * Фаза обробки вхідної інформації.
     * <P>Використовується під час обробки вхідної інформації згідно з закладеною бізнес-логікою.
     * @param oInData поточні вхідні дані
     * @param oOutData вихідні дані
     */
    public void ProcessPhase (indata oInData, outdata oOutData)
    {
    }

    /**
     * Фаза перенаправлення потоку бізнес-логікі.
     * @param res вихідний HTTP потік
     * @param strRedirAppl назва сервлета для перенаправлення
     * @param strRedirParam додаткові параметри
     */
    public void RedirectPhase (HttpServletResponse res, String strRedirAppl, String strRedirParam)
    {
        String strTmpRedirAppl = strRedirAppl;
        if (String.valueOf (strRedirParam) != "null")
            strTmpRedirAppl += "?" + strRedirParam;

        // перенаправлення
        res.sendRedirect (
          res.encodeRedirectUrl (res.encodeUrl (strTmpRedirAppl)));
        OutputPhase ();
    }

    /**
     * Фаза перенаправлення потоку бізнес-логікі.
     * @param res вихідний HTTP потік
     * @param strRedirAppl назва сервлета для перенаправлення
     * @param strRedirParam додаткові параметри
     * @see #RedirectPhase (HttpServletResponse, String, String)
     */
    public void RedirectPhase (HttpServletResponse res, String strRedirAppl)
    {
        RedirectPhase (res, strRedirAppl, null);
    }

    /**
     * Фаза формування обробленої інформації.
     * <P>Використовується після обробки для формування у потрібному вигляді інформації користувачу.
     * @param oOutData вихідні дані
     */
    public void ConstructPhase (outdata oOutData)
    {
    }

    /**
     * Фаза відображення підготовленної інформації.
     * <P>Використовується під час видачи сформованної інформації користувачу.
     * @param res вихідний HTTP потік
     * @param oOutData вихідні дані
     */
    public void OutputPhase (HttpServletResponse res, outdata oOutData)
    {
        if (res != null)
        {
            // ініціалізація вих. потоку
            res.setContentType ("text/html");
            res.setHeader ("pragma", "no-cache");
            PrintWriter out = new PrintWriter (
                                new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), isdb.ifaces.cfgi.getJavaCharSet ())));

            // відображення HTML сторінки
            out.println (oOutData.getData ());
        }
        out.close ();
    }

    /**
     * Фаза відображення підготовленної інформації.
     * <P>Використовується під час видачи сформованної інформації користувачу.
     * @see #OutputPhase (HttpServletResponse, outdata)
     */
    public void OutputPhase ()
    {
        OutputPhase (null, null);
    }

    /**
     * Фаза реагування на помилку під час роботи програми.
     * @param strErrMsg
     */
    public void ErrorPhase (String strErrMsg)
    {
        OutputPhase (strErrMsg);
    }

    /**
    * Кінцева фаза.
    * <P>Використовується під час закінчення роботи середовища.
    */
    public void DestroyPhase ()
    {
        // очищення всіх з'еднань користувачів з БД
        isdb.ifaces.dbi.clearAllConns ();
    }

    /**
     * Інформаційна фаза середовища.
     */
    public String InfoPhase ()
    {
        return getInfo ();
    }

    /**
     * Метод service сервлета.
     * @param req вхідний HTTP потік
     * @param res вихідний HTTP потік
     */
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        // одержання назви сервлета
        strName = req.getRequestURI ();

        // одержання номера поточної сессии
        indata oInData = new indbdata (req.getSession (true).getId ());
        cookiedata oCookieData = new cookiedata ();

        // ініціалізаційна фаза
        try
        {
            InitPhase ();
        }
        catch (isdbinitexception e)
        {
            ErrorPhase (isdb.miscs.dclrs.RPT_LOGIN);
            return;
        }

        // одержання вхідних параметрів
        try
        {
            InputPhase (req, oInData, oCookieData);
        }
        catch (isdbinputexception e)
        {
            ErrorPhase (isdb.miscs.dclrs.RPT_INPUT);
            return;
        }

        // перевірка реєстрації користувача
        try
        {
            LoginPhase (strName, oInData);
        }
        catch (isdbloginexception e)
        {
            RedirectPhase (isdb.ifaces.htmli.hline () +
                           isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                           isdb.ifaces.htmli.div (
                             isdb.ifaces.htmli.form (res.encodeUrl (isdb.miscs.dclrs.APPL_LOGIN),
                                                     oInData.getHTMLParams () +
                                                     isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, strFailAppl),
                                                     oInData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) +
                           isdb.ifaces.htmli.hline ());
            return;
        }

        // обробка вхідної інформації
        ProcessPhase (oDBData, oOutData);

        //
        ConstructPhase (oOutData);

        //
        OutputPhase (res, oOutData);


        ////////////////////////////////////////////////////////////////////////




        String strVal = null;
        String strColorBG = null;
        String strColorForm = null;
        String strColorHead = null;
        String strColorText = null;

        if (oThisDBData.isPresent (PAR_DEFAULT))
        {
            if (oThisDBData.getVal (PAR_DEFAULT).equals (isdb.miscs.dclrs.YES))      // первинне встановлення?
            {
                cfgi.setDefaultOptions ();
                strColorBG = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
                strColorForm = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
                strColorText = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
                strColorHead = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
                oThisCookieData.putVal (isdb.miscs.dclrs.OPT_COLOR_BG, strColorBG);
                oThisCookieData.putVal (isdb.miscs.dclrs.OPT_COLOR_FORM, strColorForm);
                oThisCookieData.putVal (isdb.miscs.dclrs.OPT_COLOR_TEXT, strColorText);
                oThisCookieData.putVal (isdb.miscs.dclrs.OPT_COLOR_HEAD, strColorHead);
                oThisDBData.removeVal (PAR_DEFAULT);
            }
        }
        else
        {

            // встановлення значеннь конфігураційних отцій
            cfgi.setOptions (oThisDBData, oThisCookieData);

            // встановлення значеннь конфігураційних отцій для відображення та змін
            strColorBG = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
            strColorForm = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
            strColorText = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
            strColorHead = cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
        }

        // відправлення cookies кліенту
        oThisCookieData.putData (res);

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), strJCharSet)));

        // будова об'ектів
        try
        {
            oThisDept = deptabstract.getDept (oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
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
        if (!dbi.chkConn (strSesnId))
        {
            strBody = isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                      isdb.ifaces.htmli.div (
                        isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                oThisDBData.getHTMLParams () +
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbenv"),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) +
                      isdb.ifaces.htmli.hline ();
        }
        else
        {

            // додаткові кнопки навігації
            isdb.buttondata oDefaultButton = new isdb.buttondata ();
            oDefaultButton.putUrl ("isdbenv");
            oDefaultButton.putName (isdb.miscs.dclrs.TITLE_REG_ORIGIN_STATE);
            oDefaultButton.putPar (PAR_DEFAULT, isdb.miscs.dclrs.YES);
            oDefaultButton.putPar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oDefaultButton.putPar (isdb.miscs.dclrs.PAR_APPL, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oThisDBData.setButton (oDefaultButton.getButton (res));

            isdb.js.jsdata oJSData = new isdb.js.jsdata ();
            oJSData.putJS (isdb.miscs.dclrs.OPT_COLOR_BG, isdb.js.jsi.JS_FUNC_CHK_NULL);
            oJSData.putJS (isdb.miscs.dclrs.OPT_COLOR_FORM, isdb.js.jsi.JS_FUNC_CHK_NULL);
            oJSData.putJS (isdb.miscs.dclrs.OPT_COLOR_TEXT, isdb.js.jsi.JS_FUNC_CHK_NULL);
            oJSData.putJS (isdb.miscs.dclrs.OPT_COLOR_HEAD, isdb.js.jsi.JS_FUNC_CHK_NULL);

            // приготування сторінки
            strBody =
              isdb.ifaces.htmli.place (

                // назва форми
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.title (strTitle), 100)) +

                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (

                    // заголовок
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Установки кольорів") +
                                                isdb.ifaces.htmli.crlf (), 100)
                      ), 0) +

                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Колір фону усіх сторінок" +
                                                isdb.ifaces.htmli.crlf () +
                                                isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_BG, strColorBG, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_BG)), 25) +
                        isdb.ifaces.htmli.cell ("Колір форм" +
                                                isdb.ifaces.htmli.crlf () +
                                                isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_FORM, strColorForm, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_FORM)), 25) +
                        isdb.ifaces.htmli.cell ("Колір інформації користувача" +
                                                isdb.ifaces.htmli.crlf () +
                                                isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_TEXT, strColorText, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_TEXT)), 25) +
                        isdb.ifaces.htmli.cell ("Колір заголовків форм" +
                                                isdb.ifaces.htmli.crlf () +
                                                isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_HEAD, strColorHead, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_HEAD)), 25)
                      ), 0)
                    , 100)) +

                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (

                    // заголовок
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Загальні установки відображення") +
                                                isdb.ifaces.htmli.crlf (), 100)
                      ), 0) +

                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Встановлення мови відображення " +
                                                isdb.ifaces.htmli.select (isdb.miscs.dclrs.OPT_LANGUAGE, isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_UA, "Українська", true) +
                                                                          isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_RU, "Русская") +
                                                                          isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_EN, "English")), 50) +
                        isdb.ifaces.htmli.cell ("Контролювання режиму підказування " +
                                                isdb.ifaces.htmli.select (isdb.miscs.dclrs.OPT_HELP_ENABLED, isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.NO, "Ні", true) +
                                                                          isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.YES, "Так")), 50))
                      , 0)
                    , 100)), 1);

            strBody =
              isdb.ifaces.htmli.crlf () +
              isdb.ifaces.htmli.form (res.encodeUrl ("isdbenv"),
                                      strBody +
                                      oThisDBData.getHTMLParams (),
                                      // isdb.miscs.dclrs.REGIME_UPDATE
                                      oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_UPDATE), null);
        }

        // встановлення кнопки допомоги
        oThisDBData.setDocButton (res, "options");

        // формування HTML сторінки
        String strOut =
          isdb.ifaces.htmli.page (strTitle,

                                  // формування тіла сторінки
                                  isdb.ifaces.htmli.body (oThisDept.getName (),
                                                          oThisDept.getLogo (),
                                                          res.encodeUrl (oThisDept.getInfo ()),

                                                          // формування тіла сторінки
                                                          isdb.ifaces.htmli.place (
                                                            isdb.ifaces.htmli.row (
                                                              strBody), 0) +

                                                          // поле кнопок
                                                          isdb.ifaces.htmli.place (
                                                            isdb.ifaces.htmli.row (

                                                              // додавання додаткових кнопок до форми
                                                              oThisDBData.getButtons () +

                                                              // та кнопки повернення
                                                              isdb.ifaces.htmli.cell (
                                                                isdb.ifaces.htmli.form (res.encodeUrl ("isdbmenu"),
                                                                                        isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                                                                                        isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL)),
                                                                                        oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN), null)
                                                                , 20)
                                                            ), 0), null),
                                  isdb.js.jsi.getJS (isdb.js.jsi.getJSBody (isdb.js.jsi.JS_FUNC_CHK_NULL) +
                                                     isdb.js.jsi.getJSBody (isdb.js.jsi.JS_FUNC_WND_OPEN)));

        // відображення HTML сторінки
        out.println (strOut);
        out.close ();
    }

    /**
     * Ініціалізаційна фаза середовища.
     * @exception isdbinitexception
     */
    public void InitPhase ()
    throws isdbinitexception
    {
        setTitle ("Установки користувача");
        super.InitPhase ();
    }

}



