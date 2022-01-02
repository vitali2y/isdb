/**
 * isdboption.java
 * ISDBj
 */

package isdb;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.objs.*;
import isdb.datas.*;
import isdb.ifaces.cfgi;

/**
 * Робота с установками користувача.
 * <P><B>Увага!</B>
 * <P>Для використання ціеї можливості підтримуються cookies,
 * за допомогою якіх орігінальна (для кожного користувача!)
 * кофігураційна інформація зберегається на машині кліента!
 * <P>Вхідни параметри:
 * <UL>
 * <LI>PAR_APPL - назва викликаемого після проведення операції наступного сервлета,
 * <LI>PAR_DEPT - назва відділу
 * </UL>
 * <LI>Необов'язкові параметри:
 * <UL>
 * <LI>PAR_DEFAULT - YES
 * </UL>
 * @see isdb.miscs.dclrs#PAR_APPL
 * @see isdb.miscs.dclrs#PAR_DEPT
 * @see isdb.miscs.dclrs#PAR_YES
 * @version 1.0 final, 24-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdboption extends HttpServlet
{
    private static final String PAR_DEFAULT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "DEFAULT";

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    /** Метод service */
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        isdbobj oThisObj = null;
        isdb.depts.deptobj oThisDept = null;

        String strErrMsg = null;
        String strBody = null;
        String strAppl = "isdboption";
        String strTitle = "Установки користувача";

        // одержання номера текущей сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);
        cookiedata oThisCookieData = new cookiedata ();

        // одержання вхідних параметрів
        oThisDBData.getData (req);
        // одержання cookies
        oThisCookieData.getData (req);

        String strVal = null;
        String strFont = null;
        String strColorBG = null;
        String strColorTitle = null;
        String strColorBGTitle = null;
        String strColorForm = null;
        String strColorHead = null;
        String strColorText = null;
        String strColorLink = null;
        String strPictureBG = null;
        if (String.valueOf (oThisDBData.getVal (PAR_DEFAULT)) != "null")
        {
            if (oThisDBData.getVal (PAR_DEFAULT).equals (isdb.miscs.dclrs.YES))      // первинне встановлення?
            {
                isdb.ifaces.cfgi.setDefaultOptions ();
                strFont = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_FONT);
                strColorBG = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
                strColorTitle = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TITLE);
                strColorBGTitle = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
                strColorForm = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
                strColorText = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
                strColorHead = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
                strColorLink = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_LINK);
                strPictureBG = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_PICTURE_BG);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_FONT, strFont);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_BG, strColorBG);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_TITLE, strColorTitle);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, strColorBGTitle);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_FORM, strColorForm);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_TEXT, strColorText);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_HEAD, strColorHead);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_COLOR_LINK, strColorLink);
                oThisCookieData.setVal (isdb.miscs.dclrs.OPT_PICTURE_BG, strPictureBG);
                oThisDBData.removeVal (PAR_DEFAULT);
            }
        }
        else
        {
            // встановлення значеннь конфігураційних отцій
            isdb.ifaces.cfgi.setOptions (oThisDBData, oThisCookieData);
            // встановлення значеннь конфігураційних отцій для відображення та змін
            strFont = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_FONT);
            strColorBG = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BG);
            strColorTitle = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TITLE);
            strColorBGTitle = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_BGTITLE);
            strColorForm = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_FORM);
            strColorText = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_TEXT);
            strColorHead = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_HEAD);
            strColorLink = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_COLOR_LINK);
            strPictureBG = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_PICTURE_BG);
        }
        // відправлення cookies кліенту
        oThisCookieData.setData (res);

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), isdb.ifaces.cfgi.getJavaCharSet ())));

        // будова об'ектів
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
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdboption"),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) +
                      isdb.ifaces.htmli.hline ();
        }
        else
        {
            // додаткові кнопки навігації
            buttondata oDefaultButton = new buttondata ();
            oDefaultButton.setUrl ("isdboption");
            oDefaultButton.setName (isdb.miscs.dclrs.TITLE_REG_ORIGIN_STATE);
            oDefaultButton.setPar (PAR_DEFAULT, isdb.miscs.dclrs.YES);
            oDefaultButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oDefaultButton.setPar (isdb.miscs.dclrs.PAR_APPL, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oThisDBData.setButton (oDefaultButton.getButton (oOutData));

            jsdata oJSData = new jsdata ();
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_BG, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_TITLE, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_FORM, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_TEXT, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_HEAD, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (isdb.miscs.dclrs.OPT_COLOR_LINK, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);

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
                    isdb.ifaces.htmli.paragraph ("Установки кольорів",
                                                 isdb.ifaces.htmli.cell ("Колір фону усіх сторінок" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_BG, strColorBG, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_BG, isdb.ifaces.jsi.JS_ACTION_ONBLUR)) +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         "Фонт відображення" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_FONT, strFont, "15") +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         "Фонові шпалери" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_PICTURE_BG, strPictureBG, "15"), 25) +
                                                 isdb.ifaces.htmli.cell ("Кольор назви форми" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_TITLE, strColorTitle, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_TITLE, isdb.ifaces.jsi.JS_ACTION_ONBLUR)) +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         "Кольор фону назви форми" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, strColorBGTitle, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_BGTITLE, isdb.ifaces.jsi.JS_ACTION_ONBLUR)), 25) +
                                                 isdb.ifaces.htmli.cell ("Колір форм" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_FORM, strColorForm, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_FORM, isdb.ifaces.jsi.JS_ACTION_ONBLUR)) +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         "Колір заголовків форм" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_HEAD, strColorHead, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_HEAD, isdb.ifaces.jsi.JS_ACTION_ONBLUR)), 25) +
                                                 isdb.ifaces.htmli.cell ("Колір інформації користувача" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_TEXT, strColorText, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_TEXT, isdb.ifaces.jsi.JS_ACTION_ONBLUR)) +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         "Колір гіпер-переходів" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.formtextpar (isdb.miscs.dclrs.OPT_COLOR_LINK, strColorLink, "6", oJSData.getJS (isdb.miscs.dclrs.OPT_COLOR_LINK, isdb.ifaces.jsi.JS_ACTION_ONBLUR)), 25))
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (

                    // заголовок
                    isdb.ifaces.htmli.paragraph ("Загальні установки",
                                                 isdb.ifaces.htmli.cell ("Встановлення мови відображення" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.select (isdb.miscs.dclrs.OPT_LANGUAGE,
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_UA, "Українська", true) +
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_RU, "Русский") +
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OPTPAR_LANG_EN, "English")), 33) +
                                                 isdb.ifaces.htmli.cell ("Контролювання режиму підказування" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.select (isdb.miscs.dclrs.OPT_HELP_ENABLED,
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.NO, "Ні", true) +
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.YES, "Так")), 33) +
                                                 isdb.ifaces.htmli.cell ("Встановлення режиму новин" +
                                                                         isdb.ifaces.htmli.crlf () +
                                                                         isdb.ifaces.htmli.select (isdb.miscs.dclrs.OPT_NEWS,
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.NO, "Ні", true) +
                                                                                                   isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.YES, "Так")), 33))
                    , 100)), 1);

            strBody =
              isdb.ifaces.htmli.crlf () +
              isdb.ifaces.htmli.form (res.encodeUrl ("isdboption"),
                                      strBody +
                                      oThisDBData.getHTMLParams (),
                                      oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_UPDATE), null);
            oOutData.setJSData (oJSData);
        }

        // встановлення кнопки допомоги
        oThisDBData.setDocButton ("options", oOutData);
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
                                                            ), 0, false), null),
                                  isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NULL) +
                                                         isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN)));

        // відображення HTML сторінки
        out.println (strOut);
        out.close ();
    }
}

