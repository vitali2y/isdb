/**
 * isdbcommit.java
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
 * Фіксування введенної користувачем інформації в БД.
 *
 * <P>Вхідни параметри:
 * <UL>
 * <LI>Обов'язкові параметри:
 * <UL>
 * <LI>PAR_APPL - назва викликаемого після проведення операції наступного сервлета,
 * <LI>PAR_OBJECT - використовуемий об'ект БД,
 * <LI>PAR_ID - ідентификатор об'екта PAR_OBJECT,
 * <LI>PAR_DEPT - назва відділу,
 * <LI>PAR_REGIME - режим функционування:<BR>
 * <UL>
 * <LI>REGIME_UPDCOMMIT,
 * <LI>REGIME_INSCOMMIT
 * </UL>
 * </UL>
 * <LI>Необов'язкові параметри:
 * <UL>
 * <LI>PAR_ROLLBACK
 * <LI>PAR_TRANSFER_PARAM
 * </UL>
 * </UL>
 * @see isdb.miscs.dclrs#PAR_APPL
 * @see isdb.miscs.dclrs#PAR_OBJECT
 * @see isdb.miscs.dclrs#PAR_ID
 * @see isdb.miscs.dclrs#PAR_DEPT
 * @see isdb.miscs.dclrs#PAR_REGIME
 * @see isdb.miscs.dclrs#REGIME_UPDCOMMIT
 * @see isdb.miscs.dclrs#REGIME_INSCOMMIT
 * @version 1.0 final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class isdbcommit extends HttpServlet
{
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = true;

    private static String strTitle = "Фіксування інформації в БД";
    private stackdata oStackData = new stackdata ();
    private pooldata oPoolData = new pooldata ();

    //
    public void init (ServletConfig conf)
    throws ServletException
    {
        super.init (conf);
        isdb.ifaces.cfgi.setDefaultOptions ();
    }

    // Метод service
    public void service (HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
    {
        String strBody = "";
        String strOut = "";

        // одержання номера поточной сессии
        HttpSession strSession = req.getSession (true);
        String strSesnId = strSession.getId ();

        dbdata oThisDBData = new dbdata (strSesnId);
        outdata oOutData = new outdata (res);
        isdbobj oThisObj = null;
        isdb.depts.deptobj oThisDept = null;

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
        catch (IllegalAccessException ex)
            { ex.printStackTrace (); }
        catch (InstantiationException ex)
            { ex.printStackTrace (); }
        oThisObj.describe (oThisDBData);

        // перевірка реєстрації користувача
        if (!isdb.ifaces.dbi.chkConn (strSesnId))
            strBody =
              isdb.ifaces.htmli.hline () +
              isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_ATTENTION, isdb.miscs.dclrs.RPT_LOGIN) +
              isdb.ifaces.htmli.div (
                isdb.ifaces.htmli.form (res.encodeUrl ("isdblogin"),
                                        oThisDBData.getHTMLParams () +
                                        isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_FAILAPPL, "isdbmenu"),
                                        oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_LOGIN), null)) + isdb.ifaces.htmli.hline ();
        else
        {
            String strTmp = null;
            String strParam = "";
            if (oThisDBData.isObjectNull ())
                oThisDBData.setError (isdb.miscs.dclrs.PAR_OBJECT);
            else
            {
                if (oThisDBData.isPresent (isdb.miscs.dclrs.PAR_ROLLBACK))
                {
                    if (oThisDBData.getVal (isdb.miscs.dclrs.PAR_ROLLBACK).equals (isdb.miscs.dclrs.YES)) // користувач відказався від продовження роботи?
                    {
                        // откат даних
                        oThisObj.rollbackData (oThisDBData);
                        // очіщення зберігаемого пула даних
                        oPoolData.clear (strSesnId);
                        oThisDBData.removeVal (isdb.miscs.dclrs.PAR_ROLLBACK);
                        oThisDBData.removeVal (isdb.miscs.dclrs.PAR_OBJECT);
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception enewr = new Exception ("Rollback data!");
                            enewr.printStackTrace ();
                        }
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception e888 = new Exception ("isdbcommit (clear, before): " + stackurldata.toString (oThisDBData));
                            e888.printStackTrace ();
                        }
                        // очіщення стека URL адрес
                        stackurldata.clear (oThisDBData);
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception e888 = new Exception ("isdbcommit (clear, after): " + stackurldata.toString (oThisDBData));
                            e888.printStackTrace ();
                        }
                        // перенаправлення
                        res.sendRedirect (
                          res.encodeRedirectUrl (res.encodeUrl (isdb.miscs.dclrs.APPL_MENU + "?" + oThisDBData.getHTTPParams ())));
                        out.close ();
                        return;
                    }
                }
                if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT) ||
                        oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT))
                {
                    dbdata oTestDBData = new dbdata (strSesnId);
                    if (oThisDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT))
                    {
                        if (!oThisDBData.isPresent (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME))       // перший раз?
                        {
                            oTestDBData = oThisDBData.cloneData ();
                            oTestDBData.setVal (isdbobj.COL_ID, oTestDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                            oTestDBData.removeVal (isdbobj.COL_REMARKS);

                            // перевірка існування в БД інформації, яка зараз оновлюеться в БД
                            if (oTestDBData.isPresent (isdb.miscs.dclrs.PAR_ID))
                                oTestDBData = oThisObj.select (oTestDBData);
                            else
                                oTestDBData = oThisObj.id (oTestDBData);

                            if (MODE_DEBUG)                // тестувал. режим?
                            {
                                Exception e888 = new Exception ("chk test (update): " + oTestDBData.toString ());
                                e888.printStackTrace ();
                            }
                            if (!oTestDBData.isResultOK ())             // немає інформації?
                            {
                                oThisDBData.setRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT);
                                if (oThisObj.isReferenced ())                  // ссилочний об'ект?
                                {
                                    oThisDBData.setVal (oThisObj.getReference (), oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                                    oThisDBData.removeVal (isdb.miscs.dclrs.PAR_ID);
                                    oThisDBData.removeVal (isdbobj.COL_ID);
                                }
                                else
                                    strParam = isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME, isdb.miscs.dclrs.YES);
                                strParam += oThisDBData.getHTMLParams ();

                                if (MODE_DEBUG)                // тестувал. режим?
                                {
                                    Exception enew11 = new Exception ("test update: " + oTestDBData.toString ());
                                    enew11.printStackTrace ();
                                }
                                oThisDBData.setError ("Інформація, яку питаються оновити, в БД не існує!" +
                                                      isdb.ifaces.htmli.crlf () +
                                                      "Продовжити занесення нової інформації?" +
                                                      isdb.ifaces.htmli.crlf () +
                                                      isdb.ifaces.htmli.center (
                                                        isdb.ifaces.htmli.form (res.encodeUrl ("isdbcommit"),
                                                                                strParam,
                                                                                oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_CONTINUE), null)));

                            }
                        }
                        else oThisDBData.removeVal (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME);
                    }
                    else
                    {
                        if (!oThisDBData.isPresent (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME))       // перший раз?
                        {
                            // витягнення назви та ідентифікатора для ссилочного об'екта
                            // та його збереження в пулі даних для подальшого використання
                            oPoolData.setVal (strSesnId, oThisObj.getNameRefIdMaster (oThisDBData), oThisObj.getNextSeqId (oThisDBData));

                            if (MODE_DEBUG)                // тестувал. режим?
                            {
                                Exception enew88 = new Exception ("oPoolData.setVal: getNameRefIdMaster: " + oThisObj.getNameRefIdMaster (oThisDBData) +
                                                                  "\n" + oThisDBData.toString () +
                                                                  "\npool: " + oPoolData.toString (oThisDBData.getSession ()));
                                enew88.printStackTrace ();
                            }
                            if (!oThisObj.isAllowMultipled ())   // можливо зберегати кілька унікальних записів?
                            {
                                oTestDBData = oThisDBData.cloneData ();

                                // не проводити пошук по деяким полям
                                oTestDBData.removeVal (isdbobj.COL_PERSON_ID);
                                oTestDBData.removeVal (isdbobj.COL_STATEDATE);
                                oTestDBData.removeVal (isdbobj.COL_REMARKS);
                                oTestDBData.removeVal (isdbobj.COL_DIRNR_ID);

                                // перевірка існування в БД інформації, яка зараз вводиться в БД
                                oTestDBData = oThisObj.id (oTestDBData);
                                if (oTestDBData.isResultOK ())             // вже існує інформація?
                                {
                                    if (MODE_DEBUG)                // тестувал. режим?
                                    {
                                        Exception enew116 = new Exception ("test insert: " + oTestDBData.toString ());
                                        enew116.printStackTrace ();
                                    }
                                    strParam = isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME, isdb.miscs.dclrs.YES) +
                                               oThisDBData.getHTMLParams ();
                                    oThisDBData.setRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT);
                                    oThisDBData.setError ("Інформація, яка призначена для введення, вже існує!" +
                                                          isdb.ifaces.htmli.crlf () +
                                                          "Продовжити оновлення існуючої інформації?" +
                                                          isdb.ifaces.htmli.crlf () +
                                                          isdb.ifaces.htmli.center (
                                                            isdb.ifaces.htmli.form (res.encodeUrl ("isdbcommit"),
                                                                                    strParam,
                                                                                    oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_CONTINUE), null)));

                                }
                            }
                        }
                        else oThisDBData.removeVal (isdb.miscs.dclrs.PAR_CONTINUE_NEXT_TIME);
                    }

                    // були зауваження чи помилки?
                    if (!oThisDBData.isError ())
                    {
                        // загрузка даних об'екта в стек даних
                        oStackData.pushData (oThisDBData);
                        // обробка стека існуючих даних для проведення транзакції
                        while (true)
                        {
                            // попередпідготовча обробка даних
                            oThisObj.prePrepareData (oThisDBData);

                            // (пере)формування значеннь стека даних
                            oStackData.prepareData (oThisDBData, oPoolData);

                            // післяпідготовча обробка даних
                            oThisObj.postPrepareData (oThisDBData);

                            // чи є ще дані для транзакції?
                            if (oStackData.isEmpty (oThisDBData))
                            {
                                oThisDBData.setResultCount (1); // ok!
                                break;
                            }
                            if (MODE_DEBUG)                // тестувал. режим?
                            {
                                Exception enew1 = new Exception ("after isEmpty: " + oThisDBData.toString () + "\n" + oPoolData.toString (oThisDBData.getSession ()));
                                enew1.printStackTrace ();
                            }
                            oThisDBData = oStackData.popData (oThisDBData);
                            if (oThisDBData.isRequiredCreateRefObj (oPoolData))            // є не заповнене ссилочне поле?
                            {
                                strTmp = oThisObj.getRefObj (oThisDBData.getIdCreateRefObj (oPoolData), oThisDBData);
                                if (MODE_DEBUG)                // тестувал. режим?
                                {
                                    Exception e646 = new Exception ("strTmp: strTmp=" + strTmp);
                                    e646.printStackTrace ();
                                }
                                if ((String.valueOf (strTmp) == "null") ||                   // класична чі переформ. назва ссил. об'екта?
                                        (strTmp.equals ("")))
                                {
                                    strTmp = oThisDBData.getIdCreateRefObj (oPoolData);
                                    strTmp = (strTmp.substring (0, strTmp.length () - 3)).toLowerCase ();
                                }
                                else
                                    strTmp = (strTmp.substring (0, strTmp.length () - 1)).toLowerCase ();

                                // загрузка даних об'екта в стек даних
                                // для послідуючого введення відсутних даних користувачами
                                oStackData.pushData (oThisDBData);
                                strParam = isdb.miscs.dclrs.APPL_FORM + "?" +
                                           isdbobj.COL_ID + "=" + oThisDBData.getVal (isdbobj.COL_ID) + "&" +
                                           isdb.miscs.dclrs.PAR_OBJECT + "=" + strTmp + "&" +
                                           isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                           isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                           isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);
                                if (MODE_DEBUG)                // тестувал. режим?
                                {
                                    Exception e66 = new Exception ("isRequiredCreateRefObj: strTmp=" + strTmp +", strParam=" + strParam + ", getIdCreateRefObj="+oThisDBData.getIdCreateRefObj (oPoolData)+
                                                                   "\n"+ oThisDBData.toString () + "\n" + oPoolData.toString (oThisDBData.getSession ()));
                                    e66.printStackTrace ();
                                }
                                res.sendRedirect (
                                  res.encodeRedirectUrl (res.encodeUrl (strParam + "&" + oThisDBData.getPrintInfo ())));
                                out.close ();
                                return;
                            }
                            if (oThisDBData.isRequiredCreateObj (oPoolData))         // потрібно создати запис нового об'екта?
                            {
                                strTmp = oThisDBData.getNameCreateObj ();
                                if (String.valueOf (strTmp) != "null")
                                {
                                    // загрузка даних об'екта в стек даних
                                    oStackData.pushData (oThisDBData);
                                    strParam = isdb.miscs.dclrs.APPL_FORM + "?" +
                                               oThisDBData.getParamCreateObj () +
                                               isdb.miscs.dclrs.PAR_ID + "=" + oThisDBData.getIdCreateObj (oThisObj.getNameRefIdMaster (oThisDBData), oPoolData) + "&" +
                                               isdb.miscs.dclrs.PAR_OBJECT + "=" + strTmp + "&" +
                                               isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                               isdb.miscs.dclrs.PAR_TRANSFER_PARAM + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_TRANSFER_PARAM) + "&" +
                                               isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                               isdb.miscs.dclrs.PAR_CRITERIA + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA) + "&" +
                                               isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);

                                    if (MODE_DEBUG)                // тестувал. режим?
                                    {
                                        Exception enew111 = new Exception ("isdbcommit (isRequiredCreateObj):\n" + oThisDBData.toString () +"\n"+
                                                                           oPoolData.toString (oThisDBData.getSession ()) +
                                                                           "strParam: "+strParam);
                                        enew111.printStackTrace ();
                                    }
                                    res.sendRedirect (
                                      res.encodeRedirectUrl (res.encodeUrl (strParam + "&" + oThisDBData.getPrintInfo ())));
                                    out.close ();
                                    return;
                                }
                            }
                            else
                            {
                                if (oThisDBData.isRequiredUpdateObj ())         // потрібно оновити запис об'екта?
                                {
                                    // загрузка даних об'екта в стек даних
                                    oStackData.pushData (oThisDBData);
                                    strTmp = oThisDBData.getNameUpdateObj ();

                                    if (MODE_DEBUG)                // тестувал. режим?
                                    {
                                        Exception enew221 = new Exception ("isdbcommit (isRequiredUpdateObj):\n(" + oThisDBData.toString () + "/" + strTmp +")");
                                        enew221.printStackTrace ();
                                    }
                                    strParam = isdb.miscs.dclrs.APPL_FORM + "?" +
                                               isdb.miscs.dclrs.PAR_ID + "=" + oThisDBData.getIdUpdateObj (strTmp) + "&" +
                                               isdb.miscs.dclrs.PAR_OBJECT + "=" + strTmp + "&" +
                                               isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                               isdb.miscs.dclrs.PAR_APPL + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL) + "&" +
                                               isdb.miscs.dclrs.PAR_DEPT + "=" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT);
                                    res.sendRedirect (
                                      res.encodeRedirectUrl (res.encodeUrl (strParam + "&" + oThisDBData.getPrintInfo ())));
                                    out.close ();
                                    return;
                                }
                                else
                                {
                                    try
                                    {
                                        oThisObj = (isdb.objs.isdbobj) Class.forName ("isdb.objs.obj" + oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT)).newInstance ();
                                    }
                                    catch (ClassNotFoundException ex)
                                    {
                                        Exception enew2212 = new Exception ("isdbcommit, ClassNotFoundException (exc): " + oThisDBData.toString ());
                                        enew2212.printStackTrace ();
                                    }
                                    catch (ClassCastException ex)
                                    { ex.printStackTrace (); }
                                    catch (IllegalAccessException ex)
                                    { ex.printStackTrace (); }
                                    catch (InstantiationException ex)
                                    { ex.printStackTrace (); }
                                    oThisObj.describe (oThisDBData);

                                    // проведення транзакції
                                    if (!oThisDBData.isPrepared ())
                                    {
                                        oThisObj.writeData (oThisDBData, oPoolData);
                                        if (!oThisDBData.isResultOK ())       // неуспішне проведення транзакції?
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
                else oThisDBData.setError (isdb.miscs.dclrs.PAR_REGIME);

                // була помилка?
                if (oThisDBData.isError ())
                    strBody = isdb.ifaces.htmli.error (oThisDBData.getError ()) + strBody;
                else
                {
                    if (oThisDBData.isResultOK ())       // успішне проведення транзакції?
                    {
                        // фіксація даних
                        oThisObj.commitData (oThisDBData);
                        strBody =
                          isdb.ifaces.htmli.hline () +
                          isdb.ifaces.htmli.msg (isdb.miscs.dclrs.RPT_TRANSACT_OK) +
                          isdb.ifaces.htmli.hline ();

                        // потрібна роздруківка документ(а/ів)?
                        if (oThisDBData.isRequiredPrint ())
                        {
                            strTmp = oThisDBData.getVal (isdbobj.COL_ID);
                            if (String.valueOf (strTmp) == "null")
                                strTmp = oThisDBData.getVal (isdb.miscs.dclrs.PAR_ID);
                            oThisDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, strTmp);
                            buttondata oPrintButton = new buttondata ();
                            oPrintButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oThisDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
                            oPrintButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
                            oThisDBData.setButton (oPrintButton.getButton (oOutData));
                            strBody += oThisDBData.getButtons ();
                            if (MODE_DEBUG)                // тестувал. режим?
                            {
                                Exception enew114 = new Exception ("print params: " + oThisDBData.toString () +
                                                                   "\n" + oPoolData.toString (oThisDBData.getSession ()) +
                                                                   oPrintButton.toString ());
                                enew114.printStackTrace ();
                            }
                        }
                    }
                    else
                    {
                        buttondata oReturnEditButton = new buttondata ();
                        oReturnEditButton.setName (isdb.miscs.dclrs.TITLE_REG_PREVFORM);
                        // вилучення зі стеку URL адрес останньої адреси та її підготовлення
                        oReturnEditButton.setPar (stackurldata.popURL (oThisDBData));
                        oThisDBData.setButton (oReturnEditButton.getButton (oOutData));
                        if (MODE_DEBUG)                // тестувал. режим?
                        {
                            Exception e578 = new Exception ("isdbcommit (after popURL): " + stackurldata.toString (oThisDBData) +
                                                            oReturnEditButton.toString ());
                            e578.printStackTrace ();
                        }
                        // откат даних
                        oThisObj.rollbackData (oThisDBData);
                        strBody =
                          isdb.ifaces.htmli.error (isdb.miscs.dclrs.RPT_TRANSACT_NG) +
                          oThisDBData.getButtons ();
                    }
                    oPoolData.clear (strSesnId);
                }
            }
        }
        // формування HTML сторінки
        strOut =
          isdb.ifaces.htmli.page (
            oThisObj.getTitle (),

            // формування шапки сторінки
            isdb.ifaces.htmli.body (
              oThisDept.getName (),
              oThisDept.getLogo (),
              res.encodeUrl (oThisDept.getInfo ()),

              // формування тіла сторінки
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (strBody), 0, false) +

              isdb.ifaces.htmli.center (
                isdb.ifaces.htmli.form (
                  res.encodeUrl (isdb.miscs.dclrs.APPL_COMMIT),
                  isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_OBJECT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT)) +
                  isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_MENU, oThisDBData.getVal (isdb.miscs.dclrs.PAR_APPL)) +
                  isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_DEPT, oThisDBData.getVal (isdb.miscs.dclrs.PAR_DEPT)) +
                  isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_ROLLBACK, isdb.miscs.dclrs.YES),
                  oThisDBData.getTitleButton (isdb.miscs.dclrs.REGIME_RETURN), null)), null),
            isdb.ifaces.jsi.getJS (isdb.ifaces.jsi.getJSBody (isdb.ifaces.jsi.JS_FUNC_WND_OPEN)));

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

