/**
 * objfailrpt.java
 * ISDBj
 */

package isdb.objs;

import java.util.Enumeration;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці FAILRPTS.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objfailrpt extends isdbobj
{

    // Поля таблиці FAILRPTS
    public static final String COL_DISPATCHERNUMBER = "DISPATCHERNUMBER";
    public static final String COL_FAIL_ID = "FAIL_ID";
    public static final String COL_CONTACTPHONE = "CONTACTPHONE";
    public static final String COL_CONTACTPERSON = "CONTACTPERSON";
    public static final String COL_COMPLAINT = "COMPLAINT";
    public static final String COL_COMPLAINTDATE = "COMPLAINTDATE";
    public static final String COL_INFORMDATE = "INFORMDATE";
    public static final String COL_RESOLVEDATE = "RESOLVEDATE";
    public static final String COL_DCLRTECHNICIAN_ID = "DCLRTECHNICIAN_ID";
    public static final String COL_RSLVTECHNICIAN_ID = "RSLVTECHNICIAN_ID";

    /** Список пошкоджень, загальна статистика по яким не фіксується */
    public static final String strFailUnSelected = " FAIL_ID not in ('!5', '!6', '!7', '!8', '!10', '!99')";
    private String strAteCrossFlag = isdb.miscs.dclrs.NO;
    private String strAteInfo = null;

    /**
     * Конструктор.
     */
    public objfailrpt ()
    {
        super (isdb.miscs.dclrs.TBL_FAILRPT);
        strAteCrossFlag = cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_ATECROSSLINE);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу пошкоджених телефонних ліній на цей час";
        if (oDBData.isCriteriaLike ("DIRNRS.ID="))  // історія пошкодженнь?
            return "Історія пошкодження телефонної лінії";
        if (oDBData.isCriteriaLike ("RESOLVEDATE"))  // у стані пошкодження на цей час?
            return "Пошкоджені телефонні лінії на цей час";
        if (oDBData.isPresent (isdb.miscs.dclrs.PAR_RPT_ID))
        {
            // Пошкоджені телефоні лінії за попередній місяць (детальний звіт)
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.FORM_FAIL_RPT_MONTH_DTL))
                return super.getTitle (oDBData) + " за попередній місяць: детальний звіт";
            // Пошкоджені телефонни лінії за попередній місяць
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.FORM_FAIL_RPT_MONTH))
                return super.getTitle () + " за попередній місяць";
        }
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
        if (oDBData.isCriteriaLike ("DIRNRS.ID="))  // історія пошкодженнь?
        {
            buttondata oPrintHistoryButton = new buttondata ();
            oPrintHistoryButton.setUrl (isdb.miscs.dclrs.APPL_REPORT, true);
            oPrintHistoryButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
            dbdata oPrintHistoryDBData = new dbdata (oDBData.getSession ());
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.FORM_FAIL_RPT_HISTORY);
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FAILRPT);
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oPrintHistoryDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA));
            oPrintHistoryButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oPrintHistoryDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
            oDBData.setButton (oPrintHistoryButton.getButton (oOutData));
        }
        if (oDBData.isCriteriaLike ("RESOLVEDATE"))  // у стані пошкодження на цей час?
        {
            buttondata oPrintCurrentButton = new buttondata ();
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT, true);
            oPrintCurrentButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
            dbdata oPrintCurrentDBData = new dbdata (oDBData.getSession ());
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.REPORT_FAIL_RPT_CURRENT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FAILRPT);
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oPrintCurrentDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA));
            oPrintCurrentButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oPrintCurrentDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
            oDBData.setButton (oPrintCurrentButton.getButton (oOutData));
        }
        oSQLData.setColumn ("FAILRPTS.ID,DIRNR||', '||STATE||', з '||to_char(COMPLAINTDATE, 'DD-MM-YY')||" +
                            "', не робить '||ceil (to_number (sysdate-COMPLAINTDATE)*24)||' годин(и)'");
        oSQLData.setFrom ("FAILRPTS, DIRNRS, PHONESTATES");
        oSQLData.setWhere ("DIRNRS.ID = DIRNR_ID");
        oSQLData.setWhere ("PHONESTATES.ID = FAILRPTS.FAIL_ID");
        oSQLData.setOrder ("COMPLAINTDATE");
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

        // вибірка інформації по полям об'екта
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT))
        {
            setHideColumn (COL_FAIL_ID);
            return super.fields (oDBData, oOutData);
        }
        String strDirNr = "";
        String strTariffLevel = "";
        String strAteInfo = "";
        String strLocation = "";
        String strComplaintDate = "";
        String strDclrTechnician = "";
        String strRslvTechnician = "";
        String strContactPerson = "";
        String strContactPhone = "";
        String strComplaint = "";
        String strFailure = "";
        String strDispatcherNumber = "";
        String strInformDate = "";
        String strResolveDate = "";
        String strRemarks = "";
        String strFailLastMonth = "";
        String strFailStatInfo = "-";

        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strRegime = oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objdirnr oDirNr = new objdirnr ();
        objtarifflevel oTariffLevel = new objtarifflevel ();
        objperson oDclrTechnician = new objperson ();
        objperson oRslvTechnician = new objperson ();
        objphonestate oPhoneState = new objphonestate ();
        objatetechnician oAteTechnician = null;
        objlocation oLocation = new objlocation ();

        // ... та їх дані
        dbdata oDirNrDBData = new dbdata (strSesnId);
        dbdata oTariffLevelDBData = new dbdata (strSesnId);
        dbdata oPhoneStateDBData = new dbdata (strSesnId);
        dbdata oDclrTechDBData = new dbdata (strSesnId);
        dbdata oRslvTechDBData = new dbdata (strSesnId);
        dbdata oAteTechnicianDBData = new dbdata (strSesnId);
        dbdata oLocationDBData = new dbdata (strSesnId);

        // ініціалізація додаткових данних об'ектів
        oPhoneStateDBData.setVal (COL_ID, retrieve (objfailrpt.COL_FAIL_ID, oDBData));
        oPhoneState.select (oPhoneStateDBData);
        oRslvTechDBData.setVal (COL_ID, retrieve (objfailrpt.COL_RSLVTECHNICIAN_ID, oDBData));
        oRslvTechDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oRslvTechnician.select (oRslvTechDBData);

        // оформлення нової картки пошкодження?
        if (strRegime.equals (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oDirNrDBData.setVal (COL_ID, strId);
            oDirNr.select (oDirNrDBData);

            // ініціалізація додаткових данних об'ектів
            oAteTechnicianDBData.setVal (COL_ID, retrieve (COL_ID, oDirNrDBData));
            oTariffLevelDBData.setVal (COL_ID, retrieve (objdirnr.COL_TARIFFLEVEL_ID, oDirNrDBData));
            oLocationDBData.setVal (COL_ID, retrieve (objdirnr.COL_STREET_ID, oDirNrDBData));
            strDirNr = oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData);

            // перевірка, чі існує вже телефон в списку пошкоджених
            dbdata oTestFailDBData = new dbdata (strSesnId);
            oTestFailDBData.setCriteriaObj ("RESOLVEDATE is null and DIRNR_ID=" + strId);
            select (oTestFailDBData);
            if (oTestFailDBData.isResultOK ())          // телефон вже в списку пошкоджених?
            {
                oDBData.setError (isdb.miscs.dclrs.RPT_HAVE_BEEN_SELECTED);
                return htmli.error (oDBData.getError ());
            }

            // кільк-ть пошкоджен. тел. за останній місяць
            strFailLastMonth = countItems (oDBData, "COMPLAINTDATE>=add_months(SYSDATE, -1) and" +
                                           " DIRNR_ID = " + strId);
            // int iFailLastMonth = (new Integer (strFailLastMonth)).intValue ();
            // strFailLastMonth = (new Integer (iFailLastMonth - 1)).toString ();

            // середньостат. пошкоджуемість
            oDBData.setModeObj ("select to_char(count(DIRNR_ID)/(SYSDATE-STATEDATE),'0.999')" +
                                " from DIRNRS,FAILRPTS" +
                                " where DIRNRS.ID=DIRNR_ID and" +
                                " DIRNR_ID=" + strId +
                                " group by DIRNR_ID,(SYSDATE-STATEDATE)");
            oDBData.setCriteriaObj ("");
            strFailStatInfo = getItems (oDBData);

            strContactPhone = field (COL_CONTACTPHONE, oDBData, oOutData);
            strContactPerson = field (COL_CONTACTPERSON, oDBData, oOutData);
            strComplaintDate = field (COL_COMPLAINTDATE, dbi.dbdate (oDBData, true), 14, oOutData);
            strComplaint = textareafield (COL_COMPLAINT, oDBData, oOutData);
            oDclrTechDBData.setCriteriaObj ("USERNAME='" + dbi.user (strSesnId) + "'");
            oDclrTechnician.select (oDclrTechDBData);
            strDclrTechnician = oDclrTechnician.value (objperson.COL_PERSON, oDclrTechDBData) +
                                htmli.formhidepar (COL_DCLRTECHNICIAN_ID, oDclrTechnician.retrieve (COL_ID, oDclrTechDBData)) +
                                htmli.formhidepar (COL_DIRNR_ID, strId);
            oPhoneStateDBData.setCriteriaObj ("ID like '!%'");
            oPhoneStateDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            strFailure = oPhoneState.list (COL_FAIL_ID, oPhoneStateDBData, oOutData);
            strDispatcherNumber = field (COL_DISPATCHERNUMBER, oDBData, oOutData);
            strInformDate = field (COL_INFORMDATE, oDBData, oOutData);
            strResolveDate = value (COL_RESOLVEDATE, oDBData);
            strRslvTechnician = oRslvTechnician.value (objperson.COL_PERSON, oRslvTechDBData);
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
        }
        else	// режими: REGIME_UPDATE, REGIME_RETRIEVE?
        {
            oDirNrDBData.setVal (COL_ID, retrieve (isdbobj.COL_DIRNR_ID, oDBData));
            oDirNr.select (oDirNrDBData);

            // ініціалізація додаткових данних об'ектів
            oAteTechnicianDBData.setVal (COL_ID, retrieve (isdbobj.COL_DIRNR_ID, oDBData));
            oTariffLevelDBData.setVal (COL_ID, retrieve (objdirnr.COL_TARIFFLEVEL_ID, oDirNrDBData));
            oLocationDBData.setVal (COL_ID, retrieve (objdirnr.COL_STREET_ID, oDirNrDBData));

            oDclrTechDBData.setVal (COL_ID, retrieve (objfailrpt.COL_DCLRTECHNICIAN_ID, oDBData));
            oDclrTechDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oDclrTechnician.select (oDclrTechDBData);

            strFailLastMonth = countItems (oDBData, "COMPLAINTDATE >= add_months(SYSDATE, -1) and" +
                                           " DIRNR_ID = " + oDirNrDBData.getVal (COL_ID));
            oDBData.setModeObj ("select to_char(count(DIRNR_ID)/(SYSDATE-STATEDATE),'0.999')" +
                                " from DIRNRS,FAILRPTS" +
                                " where DIRNRS.ID=DIRNR_ID and" +
                                " DIRNR_ID=" + oDirNrDBData.getVal (COL_ID) +
                                " group by DIRNR_ID,(SYSDATE-STATEDATE)");
            oDBData.setCriteriaObj ("");
            strFailStatInfo = getItems (oDBData);

            strDirNr = oDirNr.value (objdirnr.COL_DIRNR, oDirNrDBData);
            strComplaintDate = value (COL_COMPLAINTDATE, oDBData);
            strContactPerson = value (COL_CONTACTPERSON, oDBData);
            strContactPhone = value (COL_CONTACTPHONE, oDBData);
            strComplaint = value (COL_COMPLAINT, oDBData);
            strFailure = oPhoneState.value (objphonestate.COL_STATE, oPhoneStateDBData);
            strDispatcherNumber = value (COL_DISPATCHERNUMBER, oDBData);
            strInformDate = value (COL_INFORMDATE, oDBData);
            strDclrTechnician = oDclrTechnician.value (objperson.COL_PERSON, oDclrTechDBData);

            /*
            // додаткові кнопки навігації
            buttondata oParamLineButton = new buttondata ();
            oParamLineButton.setName ("Виміри");
            oParamLineButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_TSTRPT);
            oParamLineButton.setPar (isdb.miscs.dclrs.PAR_ID, oDirNrDBData.getVal (COL_ID));
            oParamLineButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oParamLineButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oParamLineButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oParamLineButton.getButton (oOutData));
            */

            // оновлення полів форми?
            if (strRegime.equals (isdb.miscs.dclrs.REGIME_UPDATE))
            {
                strResolveDate = field (COL_RESOLVEDATE, oDBData, oOutData);
                oRslvTechDBData.setCriteriaObj ("USERNAME='" + dbi.user (strSesnId) + "'");
                oRslvTechnician.select (oRslvTechDBData);
                strRslvTechnician = oRslvTechnician.value (objperson.COL_PERSON, oRslvTechDBData) +
                                    htmli.formhidepar (COL_RSLVTECHNICIAN_ID, oRslvTechnician.retrieve (COL_ID, oRslvTechDBData));

                strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
                oDBData.removeVal (COL_COMPLAINT);
                oDBData.removeVal (COL_COMPLAINTDATE);
                oDBData.removeVal (COL_CONTACTPERSON);
                oDBData.removeVal (COL_CONTACTPHONE);
                oDBData.removeVal (COL_DCLRTECHNICIAN_ID);
                oDBData.removeVal (COL_DIRNR_ID);
                oDBData.removeVal (COL_DISPATCHERNUMBER);
                oDBData.removeVal (COL_FAIL_ID);
            }
            else
            {
                // вибірка інформації?
                if (strRegime.equals (isdb.miscs.dclrs.REGIME_RETRIEVE))
                {
                    strResolveDate = value (COL_RESOLVEDATE, oDBData);
                    strRslvTechnician = oRslvTechnician.value (objperson.COL_PERSON, oRslvTechDBData);
                    strRemarks = value (COL_REMARKS, oDBData);
                }
            }
        }
        oTariffLevel.select (oTariffLevelDBData);
        if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
        {
            oAteTechnician = new objatetechnician ();
            oAteTechnician.select (oAteTechnicianDBData);
        }
        oLocation.select (oLocationDBData);

        strTariffLevel = oTariffLevel.value (objtarifflevel.COL_TARIFFLEVEL, oTariffLevelDBData);
        if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))     // використовуеться режим?
            strAteInfo = "Проходить через: " +
                         htmli.value (oAteTechnician.retrieve (oAteTechnician.COL_PHONETECHNICIAN, oAteTechnicianDBData));
        else
            strAteInfo = "";
        strLocation = oLocation.value (objlocation.COL_LOCATION, oLocationDBData) +
                      htmli.value (oDirNrDBData.getVal (objdirnr.COL_HOUSE));

        // додаткові кнопки навігації
        buttondata oCrossMapButton = new buttondata ();
        oCrossMapButton.setName ("Кросіровки");
        oCrossMapButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_CROSSMAP);
        oCrossMapButton.setPar (isdb.miscs.dclrs.PAR_ID, oDirNrDBData.getVal (COL_ID));
        oCrossMapButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oCrossMapButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oCrossMapButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
        oDBData.setButton (oCrossMapButton.getButton (oOutData));

        buttondata oDirNrButton = new buttondata ();
        oDirNrButton.setName ("Телефон");
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_ID, oDirNrDBData.getVal (COL_ID));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oDirNrButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
        oDBData.setButton (oDirNrButton.getButton (oOutData));

        buttondata oHistoryButton = new buttondata ();
        oHistoryButton.setUrl ("isdbproperty");
        oHistoryButton.setName ("Історія");
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FAILRPT);
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_TYPESELECT);
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "DIRNRS.ID=" + oDirNrDBData.getVal (COL_ID));
        oHistoryButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
        oDBData.setButton (oHistoryButton.getButton (oOutData));

        // приготування сторінки
        // перший ряд
        return htmli.row (
                 htmli.cell (
                   htmli.place (
                     htmli.row (
                       htmli.cell (strDirNr, 25) +
                       htmli.cell (strTariffLevel, 25) +
                       htmli.cell (strAteInfo, 25) +
                       htmli.cell (strLocation, 25)), 0), 100)) +

               // другий ряд (довідка про попередні пошкодження)
               htmli.row (
                 htmli.cell (

                   // заголовок
                   htmli.place (
                     htmli.row (
                       htmli.cell (htmli.subtitle ("Довідка про попередні пошкодження телефона") +
                                   htmli.crlf (), 100)), 0) +
                   htmli.place (
                     htmli.row (
                       htmli.cell ("За останній місяць був пошкоджений (кільк.): " +
                                   htmli.value (strFailLastMonth), 50) +
                       htmli.cell ("Середньостат. пошкоджуемість (кільк./тривал.): " +
                                   htmli.value (strFailStatInfo), 50)
                     ), 0)
                   , 100)) +


               // третій ряд (інформація про початок пошкодження)
               htmli.row (
                 htmli.cell (

                   // заголовок
                   htmli.place (
                     htmli.row (
                       htmli.cell (htmli.subtitle ("Інформація про початок пошкодження") + htmli.crlf (), 100)), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strComplaintDate, 35) +
                       htmli.cell (strContactPerson, 35) +
                       htmli.cell (strContactPhone, 30)
                     ), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strDclrTechnician, 40) +
                       htmli.cell (strComplaint, 60)
                     ), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strFailure, 50) +
                       htmli.cell (strDispatcherNumber, 25) +
                       htmli.cell (strInformDate, 25)
                     ), 0)
                   , 100)) +

               // четвертий ряд (рішення проблеми та примітки)
               htmli.row (
                 htmli.cell (

                   // заголовок
                   htmli.place (
                     htmli.row (
                       htmli.cell (htmli.subtitle ("Інформація про вирішення проблеми") + htmli.crlf (), 100)), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strRslvTechnician, 50) +
                       htmli.cell ( strResolveDate, 50)
                     ), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strRemarks, 100)
                     ), 0)
                   , 100));
    }

    /**
     * Приготування звіта.
     * @param oDBData поточни дані об'екта
     * @return сформований в форматі HTML звіт
     */
    public dbdata report (dbdata oDBData)
    {
        String strReport = "???";
        String strRptTitle = "???";
        String strRptNumb = "???";
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);
        sqldata oSQLData = new sqldata ();

        // Пошкоджені телефоні лінії на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_FAIL_RPT_CURRENT))
        {
            strRptTitle = getTitle () + " на " + dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";
            oRptData.setModeData ("select DIRNR, STATE, to_char(COMPLAINTDATE, 'DD-MM-YY')," +
                                  " ceil (to_number (sysdate-COMPLAINTDATE)*24), nvl(FAILRPTS.REMARKS, '-')" +
                                  " from FAILRPTS, DIRNRS, PHONESTATES" +
                                  " where DIRNRS.ID = DIRNR_ID and" +
                                  " PHONESTATES.ID = FAILRPTS.FAIL_ID and" +
                                  " RESOLVEDATE is null" +
                                  " order by COMPLAINTDATE");
            oRptData.createColumn ("Телефон", 15);
            oRptData.createColumn ("Стан пошкодження", 20);
            oRptData.createColumn ("Початок пошкодження", 20);
            oRptData.createColumn ("Тривалість пошкодження (год.)", 15);
            oRptData.createColumn ("Примітки", 30);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        // Пошкоджені телефоні лінії за попередній місяць (детальний звіт)
        if (strRptId.equals (isdb.miscs.dclrs.FORM_FAIL_RPT_MONTH_DTL))
        {
            strRptTitle = getTitle ();
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";
            oRptData.setModeData ("select DIRNR, STATE," +
                                  " decode(ceil(to_number(RESOLVEDATE-COMPLAINTDATE)*24), null," +
                                  " trunc((last_day(add_months (trunc(SYSDATE, 'DD'), -1))+1-COMPLAINTDATE)*24)," +
                                  " ceil(to_number(RESOLVEDATE-COMPLAINTDATE)*24))," +
                                  " to_char(COMPLAINTDATE, 'DD-MM-YY HH24:MI')," +
                                  " decode(to_char(RESOLVEDATE, 'DD-MM-YY HH24:MI'), null, 'не закрито', to_char(RESOLVEDATE, 'DD-MM-YY HH24:MI'))," +
                                  " nvl(FAILRPTS.REMARKS, '-')" +
                                  " from FAILRPTS, DIRNRS, PHONESTATES" +
                                  " where DIRNRS.ID = DIRNR_ID and" +
                                  " PHONESTATES.ID = FAILRPTS.FAIL_ID and" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  strFailUnSelected +
                                  " group by DIRNR, STATE," +
                                  " decode(ceil(to_number(RESOLVEDATE-COMPLAINTDATE)*24), null," +
                                  " trunc((last_day(add_months (trunc(SYSDATE, 'DD'), -1))+1-COMPLAINTDATE)*24)," +
                                  " ceil(to_number(RESOLVEDATE-COMPLAINTDATE)*24))," +
                                  " to_char(COMPLAINTDATE, 'DD-MM-YY HH24:MI')," +
                                  " decode(to_char(RESOLVEDATE, 'DD-MM-YY HH24:MI'), null, 'не закрито', to_char(RESOLVEDATE, 'DD-MM-YY HH24:MI'))," +
                                  " nvl(FAILRPTS.REMARKS, '-')" +
                                  " order by to_char(COMPLAINTDATE, 'DD-MM-YY HH24:MI')");
            oRptData.createColumn ("Телефон", 10);
            oRptData.createColumn ("Стан пошкодження", 20);
            oRptData.createColumn ("Трив-сть пошкодження (год.)", 10);
            oRptData.createColumn ("Початок пошкодження", 20);
            oRptData.createColumn ("Кінець пошкодження", 20);
            oRptData.createColumn ("Примітки", 20);
            strReport = getItems (oDBData, oRptData).getReport ();
        }

        // Пошкоджені телефонни лінії за попередній місяць
        if (strRptId.equals (isdb.miscs.dclrs.FORM_FAIL_RPT_MONTH))
        {
            strRptTitle = getTitle ();
            strRptNumb = "";
            reportdata oRptData = new reportdata (strRptTitle);
            oRptData.createColumn ("Загальна кількість" +
                                   htmli.crlf () +
                                   "прийнятих заявок", 15);
            oRptData.setModeData (1,
                                  "select count(1) from FAILRPTS where" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  strFailUnSelected);
            oRptData.createColumn ("Загальна кількість" +
                                   htmli.crlf () +
                                   "пошкоджених ліній", 15);
            oRptData.setModeData (2,
                                  "select count(distinct DIRNR_ID) from FAILRPTS where" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  strFailUnSelected);
            oRptData.createColumn ("Загальна тривалість," +
                                   htmli.crlf () +
                                   "пошкоджень ліній (в хв.)", 15);

            oRptData.setModeData (3,
                                  "select trunc(sum ((RESOLVEDATE-COMPLAINTDATE)*24*60))" +
                                  " from FAILRPTS" +
                                  " where to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  " RESOLVEDATE is not null and" +
                                  strFailUnSelected +
                                  " union" +
                                  " select trunc(sum ((last_day(add_months (SYSDATE, -1))+23/24+59/(60*24)-COMPLAINTDATE)*24*60))" +
                                  " from FAILRPTS" +
                                  " where to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  " RESOLVEDATE is null and" +
                                  strFailUnSelected);
            oRptData.createColumn ("Найчастіше пошкоджувались," +
                                   htmli.crlf () +
                                   "лінія (кількість)", 20);
            oRptData.setModeData (4,
                                  "select DIRNR||' ('||count (DIRNR)||')'" +
                                  " from FAILRPTS, DIRNRS" +
                                  " where DIRNRS.ID = DIRNR_ID and" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  strFailUnSelected +
                                  " group by DIRNR" +
                                  " having count(*) > 1" +
                                  " order by count (DIRNR) desc");
            oRptData.createColumn ("Найбільш тривалі пошкодження," +
                                   htmli.crlf () +
                                   "лінія (тривалість, в хв.)", 20);
            oRptData.setModeData (5,
                                  "select DIRNR||' ('||ceil((trunc(last_day(COMPLAINTDATE))+23/24+59/(60*24)-COMPLAINTDATE)*24*60)||')'," +
                                  " ceil((trunc(last_day(COMPLAINTDATE))+23/24+59/(60*24)-COMPLAINTDATE)*24*60)" +
                                  " from FAILRPTS, DIRNRS" +
                                  " where DIRNRS.ID = DIRNR_ID and" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  " RESOLVEDATE is null and" +
                                  strFailUnSelected +
                                  // " rownum < 11" +
                                  " union" +
                                  " select DIRNR||' ('||ceil((RESOLVEDATE-COMPLAINTDATE)*24*60)||')'," +
                                  " ceil((RESOLVEDATE-COMPLAINTDATE)*24*60)" +
                                  " from FAILRPTS, DIRNRS" +
                                  " where DIRNRS.ID = DIRNR_ID and" +
                                  " to_char(COMPLAINTDATE,'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -1), 'MM.YY') and" +
                                  " RESOLVEDATE is not null and" +
                                  strFailUnSelected +
                                  " order by 2 desc", 1);
            oRptData.createColumn ("Примітки", 15);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        // Історія пошкоджень телефона
        if (strRptId.equals (isdb.miscs.dclrs.FORM_FAIL_RPT_HISTORY))
        {
            dbdata oDirNrDBData = new dbdata (oDBData.getSession ());
            oDirNrDBData.setModeObj ("select DIRNR from DIRNRS" +
                                     " where " + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            strRptTitle = "Історія пошкодженнь телефонної лінії " + getItems (oDirNrDBData) + " станом на " + dbi.dbdate (oDBData, true);
            strRptNumb = "";
            reportdata oRptData = new reportdata (strRptTitle);
            oRptData.createColumn ("NN", 10);
            oRptData.createColumn ("Тип пошкодження", 30);
            oRptData.createColumn ("Коли було звернення", 20);
            oRptData.createColumn ("Проблема вирішена", 20);
            oRptData.createColumn ("Примітки", 20);
            oSQLData.setColumn ("ROWNUM, STATE");
            oSQLData.setColumn ("to_char(COMPLAINTDATE, 'DD-MM-YY HH24:MI')");
            oSQLData.setColumn ("decode(RESOLVEDATE, null, 'не закрито', to_char(RESOLVEDATE, 'DD-MM-YY HH24:MI'))");
            oSQLData.setColumn ("nvl(FAILRPTS.REMARKS,'-')");
            oSQLData.setFrom ("FAILRPTS,DIRNRS,PHONESTATES");
            oSQLData.setWhere ("DIRNRS.ID=DIRNR_ID");
            oSQLData.setWhere ("PHONESTATES.ID = FAILRPTS.FAIL_ID");
            oSQLData.setWhere (oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oSQLData.setOrder ("COMPLAINTDATE");
            oRptData.setModeData (oSQLData.getData ());
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }

    /**
     * Приготування графіків.
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
            objdirnr oDirNr = new objdirnr ();
            dbdata oDirNrDBData = new dbdata (oDBData.getSession ());
            graphi oPhonesByState = new graphi (oDBData);
            oPhonesByState.setPieChart ();
            oPhonesByState.addPieChartElement ("Телефони у стані пошкодження",
                                               oDirNr.countItems (oDirNrDBData, "PHONESTATE_ID like '!%'"),
                                               setURLObj (oDBData, "RESOLVEDATE%20is%20null"));
            oPhonesByState.addPieChartElement ("Телефони в робочому стані",
                                               oDirNr.countItems (oDirNrDBData, "PHONESTATE_ID not like '!%'"),
                                               setURLObj (oDBData, "RESOLVEDATE%20is%20not%20null"));
            strGraph = oPhonesByState.getApplet ();
        }
        else
        {
            // Статістика пошкодженних телефонних ліній за рік
            if (strRptId.equals (isdb.miscs.dclrs.GRAPH_FAIL_RPT_YEAR))
            {
                graphi oYearFailLines = new graphi (oDBData);
                oYearFailLines.setGraphChart (13);

                String [][] strYearValues = new String [13][2];
                strYearValues = getYearCountItems (oDBData, "COMPLAINTDATE");
                int iI=13;
                while (--iI >= 0)
                {
                    oYearFailLines.addGraphChartElement (strYearValues [iI][0],
                                                         strYearValues [iI][1]);
                }
                strGraph = oYearFailLines.getApplet ();
            }
        }
        return strGraph;
    }

    /**
    * Повернення назви ссилочного об'екта
    * @param strNameRefKey
    * @param oDBData поточни дані об'екта
    * @return назва ссилочного об'екта
    */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals ("DCLRTECHNICIAN_ID") ||
                strNameRefKey.equals ("RSLVTECHNICIAN_ID"))
        {
            oDBData.setCriteriaObj ("PERSONS.ID > 0 and NOTUSED is null");
            return "PERSONS";
        }
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Проведення збереження інформації (транзакції).
     * @param oDBData поточни дані об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        transactiondata oTransactionData = new transactiondata ();
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_UPDCOMMIT))  // оновлення?
        {
            if (oDBData.getVal (COL_RESOLVEDATE).equals (isdb.miscs.dclrs.OBJ_NULL) ||
                    oDBData.getVal (COL_RESOLVEDATE).equals ("") ||
                    (String.valueOf (oDBData.getVal (COL_RESOLVEDATE)) == "null"))
                oDBData.removeVal (COL_RSLVTECHNICIAN_ID);
            else  // убрати з списку пошкоджених?
            {
                Enumeration enumId = dbi.getIdObj (oDBData, "select DIRNR_ID from FAILRPTS" +
                                                   " where ID = " + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                while (enumId.hasMoreElements ())
                {
                    oTransactionData.setPostTransaction ("update DIRNRS set PHONESTATE_ID = '@CN'" +
                                                         " where ID = " + (String) enumId.nextElement ());
                }
            }
        }
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_INSCOMMIT))  // добавити в список пошкоджених?
            oTransactionData.setPostTransaction ("update DIRNRS set PHONESTATE_ID = '" + oDBData.getVal (COL_FAIL_ID) + "'" +
                                                 " where ID = " + oDBData.getVal (COL_DIRNR_ID));
        super.writeData (oDBData, oPoolData, oTransactionData);
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
            return "Телефонів в списку пошкоджених немає!";
        return super.getMsg (iNumberMsg, oDBData);
    }
}

