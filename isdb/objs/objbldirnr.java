/**
 * objbldirnr.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці BLDIRNRS.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objbldirnr extends isdbobj
{
    // Поля таблиці BLDIRNRS
    public static final String COL_DIRNR_ID = "DIRNR_ID";
    public static final String COL_RESTRICTION_ID = "BLRESTRICTION_ID";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_FINISHDATE = "FINISHDATE";

    /**
     * Конструктор.
     * <P><B>Увага!</B><P>Для цього об'екта не використовується ссилочна можливість,
     * тому що одному телефону може належати
     * багато телефонів з чорного списку
     */
    public objbldirnr ()
    {
        super (isdb.miscs.dclrs.TBL_BLDIRNR);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу телефонів з встановленими обмеженнями зв'язку";
        // return "Діаграма розподілу телефонів у чорному списку";
        if (oDBData.isCriteriaLike ("DIRNRS.ID="))  // історія чорного списку?
            return "Історія встановлених обмеженнь зв'язку для телефонів";
        // return "Історія занесення телефона у чорний список";
        if (oDBData.isCriteriaLike ("FINISHDATE"))      // у чорному списку на цей час?
            return "Телефони з встановленими обмеженнями зв'язку на цей час";
        // return "Телефони у чорному списку на цей час";
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
        if (oDBData.isCriteriaLike ("FINISHDATE"))  // у чорному списку на цей час?
        {
            buttondata oPrintCurrentButton = new buttondata ();
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT, true);
            oPrintCurrentButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
            dbdata oPrintCurrentDBData = new dbdata (oDBData.getSession ());
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.REPORT_BLACK_LIST_CURRENT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_BLDIRNR);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA));
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oPrintCurrentDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
            oDBData.setButton (oPrintCurrentButton.getButton (oOutData));
        }
        if (oDBData.isCriteriaLike ("FINISHDATE"))  // критерій: на чей час у чорному списку?
            oSQLData.setColumn ("BLDIRNRS.ID, DIRNR||', з '|| to_char (BLDIRNRS.STARTDATE, 'DD-MM-YY')");
        else
            oSQLData.setColumn ("BLDIRNRS.ID, DIRNR||', з '|| to_char (BLDIRNRS.STARTDATE, 'DD-MM-YY')||', '||decode(FINISHDATE, null, 'ще у чорному списку', 'по '||to_char(FINISHDATE, 'DD-MM-YY'))");
        oSQLData.setFrom ("DIRNRS,BLDIRNRS");
        oSQLData.setWhere ("DIRNRS.ID=DIRNR_ID");
        oSQLData.setOrder ("DIRNR||BLDIRNRS.STARTDATE");
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
        String strRestriction = "";
        String strStartDate = "";
        String strFinishDate = "";
        String strRemarks = "";
        String strHide = "";
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objdirnr oDirNr = new objdirnr ();
        objblrestriction oRestriction = new objblrestriction ();

        // ... та їх дані
        dbdata oDirNrDBData = new dbdata (strSesnId);
        dbdata oRestrictionDBData = new dbdata (strSesnId);
        oDirNrDBData.setVal (COL_ID, retrieve (COL_DIRNR_ID, oDBData));
        oDirNr.select (oDirNrDBData);
        oRestrictionDBData.setVal (COL_ID, retrieve (COL_RESTRICTION_ID, oDBData));
        oRestrictionDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, retrieve (isdb.miscs.dclrs.PAR_REGIME, oDBData));
        oRestriction.select (oRestrictionDBData);
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

        buttondata oHistoryButton = new buttondata (oDBData, isdb.miscs.dclrs.REGIME_TYPESELECT);
        oHistoryButton.setUrl ("isdbproperty");
        oHistoryButton.setName ("Історія");
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "DIRNRS.ID=" + oDirNrDBData.getVal (COL_ID));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
        oDBData.setButton (oHistoryButton.getButton (oOutData));

        // внесення телефона в чорний список?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oDirNrDBData.setVal (COL_ID, strId);
            oDirNr.select (oDirNrDBData);
            strDirNr = oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData);
            oDBData.removeVal (COL_ID);
            strRestriction = oRestriction.list (COL_RESTRICTION_ID, oRestrictionDBData, oOutData);
            strStartDate = field (COL_STARTDATE, dbi.dbdate (oDBData), 14, oOutData);
            strFinishDate = isdb.ifaces.htmli.value (isdb.miscs.dclrs.OBJ_NULL);
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            strHide = isdb.ifaces.htmli.formhidepar (COL_DIRNR_ID, strId);
        }
        else
        {
            oDirNrDBData.setVal (COL_ID, retrieve (COL_DIRNR_ID, oDBData));
            oDirNr.select (oDirNrDBData);
            strRestriction = oRestriction.value (objblrestriction.COL_RESTRICTION, oRestrictionDBData);
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
                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Інформація про встановлення обмеження") + isdb.ifaces.htmli.crlf (),
                                          100)), 0) +

              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (strRestriction, 50) +
                  isdb.ifaces.htmli.cell (strStartDate, 50)), 0)
              , 100)) +

          // третій ряд
          isdb.ifaces.htmli.row (
            isdb.ifaces.htmli.cell (

              // заголовок
              isdb.ifaces.htmli.place (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Інформація про вилучення обмеження") + isdb.ifaces.htmli.crlf (),
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

        // телефони в чорному списку на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_BLACK_LIST_CURRENT))
        {
            strRptTitle = this.getTitle () + " на " + dbi.dbdate (oDBData);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";
            oRptData.setModeData ("select DIRNR, to_char (BLDIRNRS.STARTDATE, 'DD-MM-YY'), RESTRICTION, nvl(BLDIRNRS.REMARKS, '-')" +
                                  " from DIRNRS, BLDIRNRS, BLRESTRICTIONS" +
                                  " where BLRESTRICTIONS.ID = BLRESTRICTION_ID and" +
                                  " DIRNRS.ID = DIRNR_ID and" +
                                  " FINISHDATE is null" +
                                  " order by BLDIRNRS.STARTDATE||DIRNR");
            oRptData.createColumn ("Телефон", 15);
            oRptData.createColumn ("Початок встановлення", 20);
            oRptData.createColumn ("Обмеження", 35);
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
            objbldirnr oBlDirNr = new objbldirnr ();
            dbdata oBlDirNrDBData = new dbdata (oDBData.getSession ());
            oBlDirNrDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
            oBlDirNrDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));

            graphi oBlDirNrGraph = new graphi (oDBData);
            oBlDirNrGraph.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            hashVals = dbi.getListObj (oDBData.getSession (),
                                       hashVals, "select distinct BLRESTRICTION_ID, RESTRICTION" +
                                       " from BLRESTRICTIONS, BLDIRNRS" +
                                       " where BLRESTRICTIONS.ID = BLRESTRICTION_ID" +
                                       " order by RESTRICTION");
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oBlDirNrGraph.addPieChartElement ((String) hashVals.get (strKey),
                                                  oBlDirNr.countItems (oBlDirNrDBData, "FINISHDATE is null and BLRESTRICTION_ID=" + strKey),
                                                  oBlDirNr.setURLObj (oBlDirNrDBData,
                                                                      "FINISHDATE%20is%20null%20and%20BLRESTRICTION_ID=" + strKey));
            }
            strGraph = oBlDirNrGraph.getApplet ();
        }
        return strGraph;
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        String strMsg = null;
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)       // не знайдено?
            strMsg = "Потрібного телефона(ів) з встановленими обмеженнями зв'язку немає!";
        else strMsg = super.getMsg (iNumberMsg, oDBData);;
        return strMsg;
    }
}

