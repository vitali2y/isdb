/**
 * isdbproperty.java
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
 * Сервлет, якмй надає слідуючі можливості:
 * <UL>
 * <LI>пошук інформації;
 * <LI>введення;
 * <LI>оновлення інформації.
 * </UL>
 * <P>Вхідни параметри:
 * <UL>
 * <LI>Обов'язкові параметри:
 * <UL>
 * <LI>PAR_APPL - назва викликаемого після проведення операції наступного сервлета,
 * <LI>PAR_OBJECT - використовуемий об'ект БД,
 * <LI>PAR_DEPT - назва відділу,
 * <LI>PAR_REGIME - режим функционування:<BR>
 * <UL>
 * <LI>REGIME_TYPESELECT,
 * <LI>REGIME_QUERY
 * </UL>
 * </UL>
 * <LI>Необов'язкові параметри:
 * <UL>
 * <LI>PAR_ID - ідентификатор об'екта PAR_OBJECT,
 * <LI>PAR_CONTINUE_NEXT_TIME
 * </UL>
 * </UL>
 * @see isdb.miscs.dclrs#PAR_APPL
 * @see isdb.miscs.dclrs#PAR_OBJECT
 * @see isdb.miscs.dclrs#PAR_ID
 * @see isdb.miscs.dclrs#PAR_DEPT
 * @see isdb.miscs.dclrs#PAR_REGIME
 * @see isdb.miscs.dclrs#REGIME_UPDCOMMIT
 * @see isdb.miscs.dclrs#REGIME_INSCOMMIT
 * @version 1.0 final, 25-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbproperty extends HttpServlet
{
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = true;
    private static String strJS = isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_SIZE) +
                                  isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NULL) +
                                  isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NUM) +
                                  isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_DATE) +
                                  isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_LST) +
                                  isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN));

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    // Метод service
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws IOException
    {
        isdbobj oThisObj = null;

        isdb.depts.deptobj oThisDept = null;

        //
        boolean bHideButton = false;
        String strTitle = null;
        String strBody = null;
        String strDescRegime = "";
        String strDesc = "";
        String strParam = "";
        String strNextReg = isdb.miscs.dclrs.REGIME_QUERY;
        String strFormJS = null;

        // одержання номера поточної сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);
        jsdata oThisJSData = new jsdata ();
        oOutData.setJSData (oThisJSData);

        // одержання вхідних параметрів
        oThisDBData.getData (req);
        oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_PROPERTY);

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
        if (!isdb.ifaces.dbi.chkConn (strSesnId))
        {
            strBody = isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                      isdb.ifaces.htmli.div (
                        isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                oThisDBData.getHTMLParams () +
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, isdb.miscs.dclrs.APPL_PROPERTY),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) + isdb.ifaces.htmli.hline ();
        }
        else
        {
            if (MODE_DEBUG)                // тестувал. режим?
            {
                Exception e1 = new Exception ("isdbproperty (start):" + oThisDBData.toString ());
                e1.printStackTrace ();
            }
            // приготування пустого ідентифікатора, якщо він відсутній
            if (!oThisDBData.isPresent (isdb.miscs.dclrs.PAR_ID))
                oThisDBData.setVal (isdb.miscs.dclrs.PAR_ID, isdb.miscs.dclrs.OBJ_NULL);
            if (!oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID).equals (isdb.miscs.dclrs.OBJ_NULL))
                oThisDBData.setVal (isdbobj.COL_ID, oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID));

            if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT) ||
                    oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE) ||
                    oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
            {
                // збереження попередньої URL адреси
                stackurldata.pushURL (isdb.miscs.dclrs.APPL_PROPERTY, oThisDBData);
                if (MODE_DEBUG)                // тестувал. режим?
                {
                    Exception e88 = new Exception ("isdbproperty (pushURL): " + stackurldata.toString (oThisDBData));
                    e88.printStackTrace ();
                }
            }
            oThisDBData = oThisObj.select (oThisDBData);
            strTitle = oThisObj.getTitle (oThisDBData);
            // режим витягнення ідентифікатора по введеним даним?
            if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                // вибірка потрібного об'екта після попереднього списку?
                if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME))
                {
                    if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME).equals (isdb.miscs.dclrs.YES))
                    {
                        oThisDBData = oThisObj.exchParams (oThisDBData);
                        strParam = isdb.miscs.dclrs.PAR_ID + "=" + oThisDBData.getVal (isdbobj.COL_ID) + "&" +
                                   isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                   isdb.miscs.dclrs.PAR_REGIME + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_REGIME) + "&" +
                                   isdb.miscs.dclrs.PAR_OBJECT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT) + "&" +
                                   isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enew1 = new Exception ("redirection: strParam=" + strParam + "\n" + oThisDBData.toString ());
                            enew1.printStackTrace ();
                        }
                        // перенаправлення на потрібний об'ект з потрібним режимом
                        res.sendRedirect (res.encodeRedirectUrl (res.encodeUrl (isdb.miscs.dclrs.APPL_FORM + "?" + strParam)));
                        out.close ();
                        return;
                    }
                }
                // вибірка по ссилочному полю - номеру телефона?
                if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_TYPESELECT))
                {
                    if (oThisDBData.isSelectionBy (isdb.miscs.dclrs.PROPERTY_FIELD))
                    {
                        if (!oThisDBData.isObject (isdb.miscs.dclrs.OBJ_DIRNR))
                        {
                            oThisDBData.setVal (isdbobj.COL_DIRNR_ID, oThisDBData.getVal (objdirnr.COL_DIRNR));
                            oThisDBData.removeVal (objdirnr.COL_DIRNR);
                            if (MODE_DEBUG)                // тестувал. режим?
                            {
                                Exception enew111 = new Exception ("field, looking record: " + oThisDBData.toString ());
                                enew111.printStackTrace ();
                            }
                        }
                    }
                }
                // пошук ідентифікаторів по введеним значенням
                oThisDBData = oThisObj.id (oThisDBData);
                if (oThisDBData.isResultOK ())             // вибран ідентифікатор(и)?
                {
                    // вибрати ідентифікатор та перейти до isdbform
                    oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_FORM);
                    if (oThisDBData.isResultNotOne ())     // вибран список ідентифікаторів?
                    {
                        // вибірка із списку та перехід до isdbform
                        strBody = oThisObj.list (oThisDBData, oOutData);
                        // слідуючий раз витягти ідент-р та перенаправити в потрібну форму
                        oThisDBData.removeVal (isdb.miscs.dclrs.PAR_ID);
                        oThisDBData.removeVal (isdbobj.COL_ID);
                        strDesc = isdb.ifaces.htmli.subtitle ("В результаті пошуку вибрано записів: " + oThisDBData.getResultCount ());
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enew11 = new Exception ("multi list: " + oThisDBData.toString ());
                            enew11.printStackTrace ();
                        }
                    }
                    else
                    {
                        oThisDBData = oThisObj.exchParams (oThisDBData);
                        strParam = isdb.miscs.dclrs.PAR_ID + "=" + oThisDBData.getVal (isdbobj.COL_ID) + "&" +
                                   isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                   isdb.miscs.dclrs.PAR_REGIME + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_REGIME) + "&" +
                                   isdb.miscs.dclrs.PAR_OBJECT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT) + "&" +
                                   isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enew11 = new Exception ("redir 1: strParam=" + strParam + "\n" + oThisDBData.toString ());
                            enew11.printStackTrace ();
                        }
                        // перенаправлення на потрібний об'ект з потрібним режимом
                        res.sendRedirect (res.encodeRedirectUrl (res.encodeUrl (oThisDBData.getVal (isdb.miscs.dclrs.PAR_NEXTAPPL) + "?" + strParam)));
                        // res.sendRedirect (res.encodeRedirectUrl (res.encodeUrl ("isdbform?" + strParam)));
                        out.close ();
                        return;
                    }
                }
                else
                {
                    if (MODE_DEBUG)                // тестувал. режим?
                    {
                        Exception enew1 = new Exception ("not found: " + oThisDBData.toString ());
                        enew1.printStackTrace ();
                    }
                    oThisDBData.setError (oThisObj.getMsg (isdb.miscs.dclrs.MSG_NOTSEARCHVAL, oThisDBData));  // не знайдено значення?
                }
            }
            else
            {
                // вибірка, коли не було попереднього типу вибору?
                if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_QUERY))
                {
                    strBody = oThisObj.getTypeSelection (oThisDBData, oOutData);
                    // встановлення кнопки допомоги
                    oThisDBData.setDocButton ("typeselect", oOutData);

                    strDescRegime = isdb.miscs.dclrs.MODE_REVIEW;
                    strDesc = isdb.ifaces.htmli.subtitle ("Вибрати ...");
                }
                else
                {
                    // використовуеться режим редагування?
                    if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT) ||
                            oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT) ||
                            oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE))
                    {
                        strDescRegime = isdb.miscs.dclrs.MODE_UPDATE;
                        strBody = oThisObj.fields (oThisDBData, oOutData);
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enew111 = new Exception ("after fields: " + oThisDBData.toString ());
                            enew111.printStackTrace ();
                        }
                        // оновити поля та перейти до isdbcommit для проведення транзакції
                        oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_COMMIT);
                    }
                    else
                    {
                        // вибірка по полям чи значенням?
                        if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT))
                        {
                            oThisDBData.removeVal (isdbobj.COL_ID);
                            oThisDBData.removeVal (isdb.miscs.dclrs.PAR_ID);
                            // список, список/поле чи поле?
                            if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_TYPESELECT))
                            {
                                strDescRegime = isdb.miscs.dclrs.MODE_REVIEW;
                                if (oThisDBData.isSelectionBy (isdb.miscs.dclrs.PROPERTY_LIST))
                                {
                                    strDesc = oThisObj.getTitle ();
                                    // oThisDBData.setVal (isdb.miscs.dclrs.PAR_ID, isdb.miscs.dclrs.OBJ_NULL);
                                    if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_CRITERIA))
                                        oThisDBData.setCriteriaObj ("(" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA) + ")");
                                    strBody = oThisObj.list (isdb.miscs.dclrs.PAR_ID, oThisDBData, oOutData);
                                    // не пустий список?
                                    if (oThisDBData.getResultCount () == 0)
                                        oThisDBData.setError (oThisObj.getMsg (isdb.miscs.dclrs.MSG_NOTSEARCHVAL, oThisDBData));
                                    else
                                        strDesc = isdb.ifaces.htmli.subtitle ("Вибрано записів: " + oThisDBData.getResultCount ());
                                    // вибрати ідентифікатор та перейти до isdbform
                                    oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_FORM);
                                }
                                else
                                {
                                    if (oThisDBData.isSelectionBy (isdb.miscs.dclrs.PROPERTY_FIELDS))
                                    {
                                        strDesc = isdb.ifaces.htmli.subtitle ("Вибірка по:");
                                        strBody = oThisObj.fields (oThisDBData, oOutData);

                                        // вибрати потрібни поля та перейти до витягнення ідентифікатора
                                        oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_PROPERTY);
                                    }
                                    else
                                    {
                                        if (oThisDBData.isSelectionBy (isdb.miscs.dclrs.PROPERTY_FIELD))
                                        {
                                            strDesc = isdb.ifaces.htmli.subtitle ("Вибрати по:");
                                            strBody = oThisObj.field (oThisDBData, oOutData);
                                            // вибрати потрібни поля та перейти до витягнення ідентифікатора
                                            oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_PROPERTY);
                                        }
                                        else
                                        {
                                            oThisDBData.setVal (oThisDBData.getVal (isdb.miscs.dclrs.PAR_TYPESELECT), "1");
                                            // створити новий об'ект?
                                            if (oThisDBData.isRequiredCreateObj ())
                                            {
                                                // перенаправлення на створення нового об'екту
                                                oThisDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_FORM);
                                                res.sendRedirect (res.encodeRedirectUrl (res.encodeUrl (oThisDBData.getVal (isdb.miscs.dclrs.PAR_NEXTAPPL) + "?" +
                                                                  isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                                                  isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                                                  isdb.miscs.dclrs.PAR_OBJECT + "=" + oThisDBData.getNameCreateObj () + "&" +
                                                                  isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT))));
                                                out.close ();
                                                return;
                                            }
                                            else
                                                oThisDBData.setError (isdb.miscs.dclrs.PAR_TYPESELECT);
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (!oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_CONTINUE))
                                oThisDBData.setError (isdb.miscs.dclrs.PAR_REGIME);
                        }
                    }
                }
            }
            // була помилка во вхідних параметрах?
            if (!oThisDBData.isError ())
            {
                // підготування наступних параметрів об'екта в залежності від логіки програми
                oThisDBData = oThisObj.exchParams (oThisDBData);
                oThisJSData = oOutData.getJSData ();
                String strNextAppl = oThisDBData.getVal (isdb.miscs.dclrs.PAR_NEXTAPPL);
                oThisDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTAPPL);
                // отримання Submit перевірки, якщо вона є
                strFormJS = oThisJSData.getJS ();
                // формування вихідной форми
                strBody = isdb.ifaces.htmli.div (
                            isdb.ifaces.htmli.form (res.encodeUrl (strNextAppl),
                                                    isdb.ifaces.htmli.place (
                                                      isdb.ifaces.htmli.cell (strDesc, 40, "left") +
                                                      isdb.ifaces.htmli.cell (strBody, 60), 0) +
                                                    oThisDBData.getHTMLParams (),
                                                    oThisDBData.getTitleButton (),
                                                    strFormJS,
                                                    bHideButton));
                // кінцеве приготування тіла сторінки
                strBody =
                  isdb.ifaces.htmli.crlf () +
                  // початок головної таблиці
                  isdb.ifaces.htmli.place (
                    // шапка
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (
                        isdb.ifaces.htmli.title (strTitle) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.subtitle (strDescRegime), 100)) +
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (strBody, 100)), 1);
            }
            else
                strBody = isdb.ifaces.htmli.error (oThisDBData.getError ());
        }
        // формування HTML сторінки
        String strOut =
          isdb.ifaces.htmli.page (strTitle,
                                  // формування тіла сторінки
                                  isdb.ifaces.htmli.body (oThisDept.getName (),
                                                          oThisDept.getLogo (),
                                                          res.encodeUrl (oThisDept.getInfo ()),
                                                          // формування тіла сторінки
                                                          isdb.ifaces.htmli.place (
                                                            isdb.ifaces.htmli.row (strBody), 0) +
                                                          isdb.ifaces.htmli.crlf () +
                                                          // кнопка повернення
                                                          isdb.ifaces.htmli.place (
                                                            isdb.ifaces.htmli.row (
                                                              // додавання додаткових кнопок до форми
                                                              oThisDBData.getButtons () +
                                                              isdb.ifaces.htmli.cell (
                                                                isdb.ifaces.htmli.div (
                                                                  isdb.ifaces.htmli.form (res.encodeUrl (isdb.miscs.dclrs.APPL_COMMIT),
                                                                                          isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_ROLLBACK, isdb.miscs.dclrs.YES) +
                                                                                          isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_OBJECT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT)) +
                                                                                          isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                                                                                          isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL)),
                                                                                          oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN),
                                                                                          null)), 100)
                                                            ), 0, false), null), strJS);
        // видача підготовлен. HTML сторінки в вихідний потік
        out.println (strOut);
        out.close ();

        // сбирання сміття
        System.gc ();
    }

    /**
     * Кінцеве припинення роботи сервлета
     */
    public void destroy ()
    {

        // очищення всіх зеднань користувачів з БД
        isdb.ifaces.dbi.clearAllConns ();
    }
}

