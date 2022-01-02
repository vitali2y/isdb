/**
 * isdbform.java
 * ISDBj
 */

package isdb;

// Імпортування классів
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import isdb.objs.*;
import isdb.datas.*;
import isdb.miscs.dclrs;
import isdb.ifaces.cfgi;

/**
 * Проведення роботи с HTML формами.
 * <P>Вхідни параметри:
 * <UL>
 * <LI>PAR_APPL - назва викликаемого після проведення операції наступного сервлета,
 * <LI>PAR_OBJECT - використовуемий об'ект БД,
 * <LI>PAR_ID - ідентификатор об'екта PAR_OBJECT,
 * <LI>PAR_DEPT - назва відділу,
 * <LI>PAR_REGIME - режим функционування:
 * <UL>
 * <LI>REGIME_QUERY,
 * <LI>REGIME_RETRIEVE,
 * <LI>REGIME_TYPESELECT,
 * <LI>REGIME_UPDATE,
 * <LI>REGIME_INSERT
 * </UL>
 * </UL>
 * @see isdb.miscs.dclrs#PAR_APPL
 * @see isdb.miscs.dclrs#PAR_OBJECT
 * @see isdb.miscs.dclrs#PAR_ID
 * @see isdb.miscs.dclrs#PAR_DEPT
 * @see isdb.miscs.dclrs#PAR_REGIME
 * @see isdb.miscs.dclrs#REGIME_QUERY
 * @see isdb.miscs.dclrs#REGIME_RETRIEVE
 * @see isdb.miscs.dclrs#REGIME_TYPESELECT
 * @see isdb.miscs.dclrs#REGIME_UPDATE
 * @see isdb.miscs.dclrs#REGIME_INSSERT
 * @version 1.0 final, 5-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbform extends HttpServlet
{
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = true;
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
        String strFormJS = null;
        String strBodyJS = null;
        isdbobj oThisObj = null;
        isdb.depts.deptobj oThisDept = null;

        String strJCharSet = "Cp1251";
        String strBody = isdb.ifaces.htmli.crlf ();
        String strParams = null;
        String strAppl = "isdbform";
        String strTitle = "";
        String strDescRegime = null;

        // одержання номера поточної сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();
        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);
        jsdata oThisJSData = new jsdata ();
        oOutData.setJSData (oThisJSData);

        // одержання вхідних параметрів
        oThisDBData.getData (req);

        // ініціалізація вих. потока
        res.setContentType ("text/html");
        res.setHeader ("pragma", "no-cache");
        PrintWriter out = new PrintWriter (new BufferedWriter (new OutputStreamWriter (res.getOutputStream (), isdb.ifaces.cfgi.getJavaCharSet ())));

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
        catch (IllegalAccessException  ex)
            { ex.printStackTrace (); }
        catch (InstantiationException ex)
            { ex.printStackTrace (); }

        String strOut = null;
        // перевірка реєстрації користувача
        if (!isdb.ifaces.dbi.chkConn (strSesnId))
        {
            strBody = isdb.ifaces.htmli.hline () +
                      isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
                      isdb.ifaces.htmli.div (
                        isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                                oThisDBData.getHTMLParams () +
                                                isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbform"),
                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) +
                      isdb.ifaces.htmli.hline ();
        }
        else
        {
            if (oThisDBData.isObjectNull ())
                oThisDBData.setError (isdb.miscs.dclrs.PAR_OBJECT);
            if (String.valueOf (oThisDBData.getVal (isdb.miscs.dclrs.PAR_REGIME)) == "null")
                oThisDBData.setError (isdb.miscs.dclrs.PAR_REGIME);
            if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT) ||
                    oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE) ||
                    oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
            {
                // збереження попередньої URL адреси
                stackurldata.pushURL (isdb.miscs.dclrs.APPL_FORM, oThisDBData);
                if (MODE_DEBUG)                // тестувал. режим?
                {
                    Exception e88 = new Exception ("isdbform (pushURL): " + stackurldata.toString (oThisDBData));
                    e88.printStackTrace ();
                }
            }
            // була помилка во вхідних параметрах?
            if (oThisDBData.isError ())
                strBody = oThisDBData.getError ();
            else
            {
                // одержання параметрів для сторінки
                if (!oThisDBData.isPresent (isdbobj.COL_ID))
                    oThisDBData.setVal (isdbobj.COL_ID, oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
                    oThisObj.describe (oThisDBData);
                else
                    oThisObj.select (oThisDBData);
                strTitle = oThisObj.getTitle (oThisDBData);
                if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_QUERY) ||
                        oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE) ||
                        oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT))
                    strDescRegime = isdb.miscs.dclrs.MODE_REVIEW;
                else
                {
                    if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
                        strDescRegime = isdb.miscs.dclrs.MODE_INSERT;
                    if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE) ||
                            oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
                        strDescRegime = isdb.miscs.dclrs.MODE_UPDATE;
                }
            }
            if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_TYPE_OUT))
            {
                if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_TYPE_OUT).equals (isdb.miscs.dclrs.TYPE_OUT_FILE))
                {
                    strOut = oThisObj.fields (oThisDBData, oOutData);
                    String strFile = "isessions.txt"; // "pri01-04.txt";
                    res.setHeader ("Content-Disposition", "attachment;filename=" + strFile);
                    res.setContentLength (strOut.length ());

                    // відображення HTML сторінки
                    out.println (strOut);
                    out.close ();
                    return;
                }
            }
            // приготування HTML сторінки
            strBody = // isdb.ifaces.htmli.crlf () +

              // початок головної таблиці
              isdb.ifaces.htmli.place (

                // назва форми
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.title (strTitle) +
                    isdb.ifaces.htmli.crlf () +
                    isdb.ifaces.htmli.subtitle (strDescRegime), 100)) +

                // сформовані поля об'екта
                oThisObj.fields (oThisDBData, oOutData), 1);

            // очіщення попередніх значеннь полів
            oThisDBData.clearHTMLParams ();

            // встановлення кнопку допомоги
            oThisDBData.setDocButton (oOutData);

            // підготування наступних параметрів об'екта в залежності від логіки програми
            oThisDBData = oThisObj.exchParams (oThisDBData);

            strBodyJS = isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN);
            oThisJSData = oOutData.getJSData ();

            // отримання Submit перевірки, якщо вона є
            if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT) ||
                    oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT))
            {
                strFormJS = oThisJSData.getJS ();
                strBodyJS += isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NULL) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_SIZE) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_NUM) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_ABCNUM) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_VAL) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_DATE) +
                             isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_CHK_LST);
            }
            strBodyJS = isdb.ifaces.jsi.getJS (strBodyJS);

            strBody =
              // isdb.ifaces.htmli.crlf () +
              isdb.ifaces.htmli.center (
                isdb.ifaces.htmli.form (oThisObj.getSubmitURL (oThisDBData),
                                        strBody +
                                        oThisDBData.getHTMLParams (),
                                        oThisDBData.getTitleButton (),
                                        strFormJS,
                                        oThisObj.isSubmited (oThisDBData)));
        }

        // підготування HTML сторінки
        strOut =
          isdb.ifaces.htmli.page (strTitle,

                                  // підготування тіла сторінки
                                  isdb.ifaces.htmli.body (
                                    oThisDept.getName (),
                                    oThisDept.getLogo (),
                                    res.encodeUrl (isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_DOC_HOME) + "/" + oThisDept.getInfo ()),

                                    // підготування тіла сторінки
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
                                          isdb.ifaces.htmli.center (
                                            isdb.ifaces.htmli.form (
                                              res.encodeUrl (isdb.miscs.dclrs.APPL_COMMIT),
                                              isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_ROLLBACK, isdb.miscs.dclrs.YES) +
                                              isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_OBJECT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT)) +
                                              isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                                              isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL)),
                                              oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN)))
                                          , 20)
                                      ), 0, false), null), strBodyJS);

        // відображення HTML сторінки
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

        // очищення всіх з'еднань користувачів з БД
        isdb.ifaces.dbi.clearAllConns ();
    }
}

