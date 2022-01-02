/**
 * objsubsservice.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці SUBSSERVICES.
 * @version 1.0final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objsubsservice extends isdbobj
{

    // Орігінальни поля таблиці SUBSSERVICES
    public static final String COL_DIRNR_ID = "DIRNR_ID";
    public static final String COL_SUBSSERV_ID = "SUBSSERV_ID";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_FINISHDATE = "FINISHDATE";

    /**
     * Конструктор.
     */
    public objsubsservice ()
    {
        super (isdb.miscs.dclrs.TBL_SUBSSERVICE);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу додаткових послуг телефонів";
        if (oDBData.isCriteriaLike ("DIRNRS.ID="))  // історія встановлення додаткових послуг телефонів?
            return "Історія встановлення додаткових послуг телефона";
        if (oDBData.isCriteriaLike ("FINISHDATE"))  // додаткові послуги телефонів на цей час?
            return "Встановлені додаткові послуги телефонів на цей час";
        return super.getTitle (oDBData);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        if (oDBData.isCriteriaLike ("FINISHDATE"))  // додаткові послуги телефонів на цей час?
        {
            buttondata oPrintCurrentButton = new buttondata ();
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT, true);
            oPrintCurrentButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
            dbdata oPrintCurrentDBData = new dbdata (oDBData.getSession ());
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.REPORT_SUBSSERVICE_CURRENT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_SUBSSERVICE);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA));
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oPrintCurrentDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
            oDBData.setButton (oPrintCurrentButton.getButton (oOutData));
        }
        oSQLData.setColumn ("SUBSSERVICES.ID, DIRNR||', ('||SERVICE||', встан. '||to_char(SUBSSERVICES.STARTDATE, 'DD-MM-YY')||', '||decode(SUBSSERVICES.FINISHDATE, null, 'активна', ', вилуч. '||to_char(SUBSSERVICES.FINISHDATE, 'DD-MM-YY'))||')'");
        oSQLData.setFrom ("TYPESUBSSERVICES,SUBSSERVICES,DIRNRS");
        oSQLData.setWhere ("TYPESUBSSERVICES.ID=SUBSSERV_ID");
        oSQLData.setWhere ("DIRNRS.ID=DIRNR_ID");
        oSQLData.setOrder ("DIRNR||SUBSSERVICES.STARTDATE");
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Головне (однострокове) поле об'екта для вибірки.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return значення поля
     */
    public String field (dbdata oDBData, outdata oOutData)
    {
        objdirnr oDirNr = new objdirnr ();
        dbdata oDirNrDBData = new dbdata (oDBData.getSession ());
        oDirNr.describe (oDBData);
        oDirNrDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
        jsdata oJSData = oOutData.getJSData ();
        oJSData.setJS (objdirnr.COL_DIRNR, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
        oOutData.setJSData (oJSData);
        return oDirNr.field (objdirnr.COL_DIRNR, oDirNrDBData, oOutData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            return super.fields (oDBData, oOutData);
        }
        String strDirNr = "";
        String strSubsService = "";
        String strStartDate = "";
        String strFinishDate = "";
        String strRemarks = "";
        String strHide = "";
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objdirnr oDirNr = new objdirnr ();
        objtypesubsservice oSubsService = new objtypesubsservice ();

        // ... та їх дані
        dbdata oDirNrDBData = new dbdata (strSesnId);
        dbdata oSubsServiceDBData = new dbdata (strSesnId);
        oDirNrDBData.setVal (COL_ID, retrieve (COL_DIRNR_ID, oDBData));
        oDirNr.select (oDirNrDBData);
        oSubsServiceDBData.setVal (COL_ID, retrieve (COL_SUBSSERV_ID, oDBData));
        oSubsServiceDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, retrieve (isdb.miscs.dclrs.PAR_REGIME, oDBData));
        oSubsService.select (oSubsServiceDBData);
        strDirNr = oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData);

        // додаткові кнопки навігації
        buttondata oDirNrButton = new buttondata ();
        oDirNrButton.setName ("Телефон");
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSERT))
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_ID, strId);
        else
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_ID, oDirNrDBData.getVal (COL_ID));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
        oDBData.setButton (oDirNrButton.getButton (oOutData));

        buttondata oHistoryButton = new buttondata ();
        oHistoryButton.setUrl ("isdbproperty");
        oHistoryButton.setName ("Історія");
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_SUBSSERVICE);
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_TYPESELECT);
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "SUBSSERV_ID='" + oSubsServiceDBData.getVal (COL_ID) + "' and DIRNRS.ID=" + oDirNrDBData.getVal (COL_ID));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
        oDBData.setButton (oHistoryButton.getButton (oOutData));

        // Активація послуги?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oDirNrDBData.setVal (COL_ID, strId);
            oDirNr.select (oDirNrDBData);
            strDirNr = oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData);
            oDBData.removeVal (COL_ID);
            strSubsService = oSubsService.list (COL_SUBSSERV_ID, oSubsServiceDBData, oOutData);
            strStartDate = field (COL_STARTDATE, dbi.dbdate (oDBData), 14, oOutData);
            strFinishDate = isdb.ifaces.htmli.value (isdb.miscs.dclrs.OBJ_NULL);
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            strHide = isdb.ifaces.htmli.formhidepar (COL_DIRNR_ID, strId);
        }
        else
        {
            oDirNrDBData.setVal (COL_ID, retrieve (COL_DIRNR_ID, oDBData));
            oDirNr.select (oDirNrDBData);
            strSubsService = oSubsService.value (objtypesubsservice.COL_SERVICE, oSubsServiceDBData);
            strStartDate = value (COL_STARTDATE, oDBData);

            // вибірка інформації стосовно телефонів з чорного списку?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                strFinishDate = value (COL_FINISHDATE, oDBData);
                strRemarks = value (COL_REMARKS, oDBData);
            }
            else	// режим: REGIME_UPDATE?
            {
                strFinishDate = field (COL_FINISHDATE, "", 14, oOutData);
                strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            }
        }

        // приготування сторінки
        // перший ряд
        return
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (

              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strDirNr, 100)), 0), 100)) +

          // другий ряд
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (

              // заголовок
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Інформація про активацію послуги") + isdb.ifaces.htmli.crlf (),
                                          100)), 0) +

              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strSubsService, 50) +
                  isdb.ifaces.htmli.cell (strStartDate, 50)), 0)
              , 100)) +

          // третій ряд
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (

              // заголовок
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Інформація про деактивацію послуги") + isdb.ifaces.htmli.crlf (),
                                          100)), 0) +

              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strFinishDate, 100)), 0), 100)) +

          // четвертий ряд
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (

              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strRemarks, 100)), 0), 100)) +

          // сховани параметри
          strHide;
    }

    /**
     * Приготування звіта
     * @param oDBData поточни дані об'екта
     * @return сформований в форматі HTML звіт
     */
    public dbdata report (dbdata oDBData)
    {
        String strReport = "???";
        String strRptTitle = "???";
        String strRptNumb = "???";
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);

        // додатковий сервіс телефонів на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_SUBSSERVICE_CURRENT))
        {
            strRptTitle = getTitle () + " на " + dbi.dbdate (oDBData);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";
            oRptData.setModeData ("select DIRNR, decode(SUBSSERVICES.STARTDATE, to_date ('01-01-01', 'DD-MM-YY'), 'невідомо', to_char (SUBSSERVICES.STARTDATE, 'DD-MM-YY'))," +
                                  " SERVICE, nvl(SUBSSERVICES.REMARKS, '-')" +
                                  " from DIRNRS, SUBSSERVICES, TYPESUBSSERVICES" +
                                  " where TYPESUBSSERVICES.ID = SUBSSERV_ID and" +
                                  " DIRNRS.ID = DIRNR_ID and" +
                                  " FINISHDATE is null" +
                                  " order by DIRNR||SUBSSERVICES.STARTDATE");
            oRptData.createColumn ("Телефон", 15);
            oRptData.createColumn ("Дата актівації послуги", 20);
            oRptData.createColumn ("Послуга", 35);
            oRptData.createColumn ("Примітки", 30);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }

    /**
     * Приготування графіків
     * @param oDBData поточни дані об'екта
     * @return сформована HTML сторінка з аплетом для формування діаграми.
     */
    public String graph (dbdata oDBData)
    {
        String strGraph = null;
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            objsubsservice oSubsServ = new objsubsservice ();
            dbdata oSubsServDBData = new dbdata (oDBData.getSession ());
            oSubsServDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
            oSubsServDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));

            graphi oSubsServGraph = new graphi (oDBData);
            oSubsServGraph.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            hashVals = isdb.ifaces.dbi.getListObj (oDBData.getSession (),
                                                   hashVals, "select distinct SUBSSERV_ID, SERVICE" +
                                                   " from TYPESUBSSERVICES, SUBSSERVICES" +
                                                   " where TYPESUBSSERVICES.ID = SUBSSERV_ID" +
                                                   " order by SERVICE");
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oSubsServGraph.addPieChartElement ((String) hashVals.get (strKey),
                                                   oSubsServ.countItems (oSubsServDBData, "FINISHDATE is null and SUBSSERV_ID='" + strKey + "'"),
                                                   oSubsServ.setURLObj (oSubsServDBData,
                                                                        "FINISHDATE%20is%20null%20and%20SUBSSERV_ID='" + strKey + "'"));
            }
            strGraph = oSubsServGraph.getApplet ();
        }
        return strGraph;
    }

    /**
     * Повернення назви ссилочного об'екта.
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals ("SUBSSERV_ID"))
            return "TYPESUBSSERVICES";
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)      // не знайдено?
            return "Додаткові телефонні послуги не встановлени!";
        return super.getMsg (iNumberMsg, oDBData);
    }
}

