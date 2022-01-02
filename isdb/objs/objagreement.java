/**
 * objagreement.java
 * ISDBj
 */

package isdb.objs;

import java.util.Enumeration;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці AGREEMENTS.
 * @version 1.0 final, 27-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objagreement extends isdbobj
{
    // Поля таблиці AGREEMENTS
    public static final String COL_AGR_NUM = "AGR_NUM";
    public static final String COL_PREV_ID = "PREV_ID";
    public static final String COL_TECHREF_ID = "TECHREF_ID";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_STATE_ID = "STATE_ID";
    public static final String COL_FINISHDATE = "FINISHDATE";
    public static final String COL_TYPEREJECTAGR_ID = "TYPEREJECTAGR_ID";
    public static final String COL_SPECCONDITION = "SPECCONDITION";
    public static final String COL_STATEDATE = "STATEDATE";
    public static final String COL_PERSON_ID = "PERSON_ID";

    /** Послідуючій номер нового запису */
    private static String NUMBERAGREEMENT = "NUMBERAGREEMENT";

    /**
     * Конструктор.
     */
    public objagreement ()
    {
        super (isdb.miscs.dclrs.TBL_AGREEMENT);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.isPresent (isdb.miscs.dclrs.PAR_RPT_ID))
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_SIGN_AGREEMENT_YEAR))
                return "Статистика укладених угод за рік";
            objtypeagreement oTypeAgreement = new objtypeagreement ();
            dbdata oTypeAgreementDBData = new dbdata (oDBData.getSession ());
            oTypeAgreementDBData.setVal (COL_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oTypeAgreement.select (oTypeAgreementDBData);
            return "Угоди (тариф. пакет: " + oTypeAgreementDBData.getVal (objtypeagreement.COL_TYPE).toLowerCase () + ")";
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
        oSQLData.setColumn ("AGREEMENTS.ID, AGR_NUM||', '||PROPERTY||' \"'||NAME||'\", '||LOCATION||', '||HOUSE");
        oSQLData.setFrom ("AGREEMENTS,TECHREFS,PROPERTIES,FIRMS,LOCATIONS");
        oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
        oSQLData.setWhere ("PROPERTIES.ID=PROPERTY_ID");
        oSQLData.setWhere ("TECHREFS.ID=TECHREF_ID");
        oSQLData.setWhere ("LOCATIONS.ID=FIRMS.STREET_ID");
        oSQLData.setOrder ("AGR_NUM");
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Головне (однострокове) поле об'екта для вибірки
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return значення поля
     */
    public String field (dbdata oDBData, outdata oOutData)
    {
        jsdata oJSData = oOutData.getJSData ();
        oJSData.setJS (COL_AGR_NUM, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
        oOutData.setJSData (oJSData);
        return super.field (COL_AGR_NUM, oDBData, oOutData);
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
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME).equals (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            setHideColumn (COL_PREV_ID);
            jsdata oJSData = oOutData.getJSData ();
            oJSData.setJS (COL_FINISHDATE, isdb.ifaces.jsi.JS_FUNC_CHK_DATE, "", "false");
            oOutData.setJSData (oJSData);
            return super.fields (oDBData, oOutData);
        }
        String strAgrNum = null;
        String strPrevId = null;
        String strTechRef = null;
        String strAgreementState = "";
        String strStartDate = null;
        String strFinishDate = null;
        String strContactPhone = null;
        String strContactPerson = null;
        String strRemarks = null;
        String strCountLines = isdb.ifaces.htmli.value ("?");
        String strDecision = "";
        String strPhones = null;
        String strFirm = "";
        String strHide = "";

        String strBody = "";
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objpropertie oProperty = new objpropertie ();
        objfirm oFirm = new objfirm ();
        objtechref oTechRef = new objtechref ();
        objphonestate oPhoneState = new objphonestate ();
        objdirnr oDirNr = new objdirnr ();
        objagreementstate oAgreementState = new objagreementstate ();
        objtyperejectagr oTypeRejectAgr = new objtyperejectagr ();

        // ... та їх дані
        dbdata oPropertyDBData = new dbdata (strSesnId);
        dbdata oFirmDBData = new dbdata (strSesnId);
        dbdata oTechRefDBData = new dbdata (strSesnId);
        dbdata oPhoneStateDBData = new dbdata (strSesnId);
        dbdata oDirNrDBData = new dbdata (strSesnId);
        dbdata oAgreementStateDBData = new dbdata (strSesnId);
        dbdata oTypeRejectAgrDBData = new dbdata (strSesnId);

        // оформлення нової заяви?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            // ініціалізація загальних параметрів
            oTechRefDBData.setVal (COL_ID, retrieve (COL_ID, oDBData));
            oTechRef.select (oTechRefDBData);
            oFirmDBData.setVal (COL_ID, retrieve (objtechref.COL_FIRM_ID, oTechRefDBData));
            oFirm.select (oFirmDBData);
            oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
            oProperty.select (oPropertyDBData);
            oAgreementStateDBData.setVal (COL_ID, "W");
            oAgreementState.select (oAgreementStateDBData);

            // додаткові кнопки навігації
            buttondata oTechRefButton = new buttondata ();
            oTechRefButton.setName ("Заява/тех. довідка");
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_TECHREF);
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_ID, oTechRefDBData.getVal (COL_ID));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oTechRefButton.getButton (oOutData));

            buttondata oFirmButton = new buttondata ();
            oFirmButton.setName ("Абонент");
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oFirmDBData.getVal (COL_ID));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oFirmButton.getButton (oOutData));

            oDirNrDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "DIRNR" + isdb.miscs.dclrs.SQL_FROM + "TECHREFS, DIRNRS" +
                                     isdb.miscs.dclrs.SQL_WHERE + "TECHREFS.ID = TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                     " " + isdb.miscs.dclrs.STAGE_CURR_AGREEMENT + isdb.miscs.dclrs.SQL_AND +
                                     " TECHREF_ID = " + oTechRefDBData.getVal (COL_ID) + isdb.miscs.dclrs.SQL_AND +
                                     " DIRNR > 10000" +
                                     isdb.miscs.dclrs.SQL_ORDERBY + "DIRNR");
            strPhones = oDirNr.getItems (oDirNrDBData);
            // strPhones = oDirNr.list (isdbobj.COL_DIRNR_ID, oDirNrDBData, oOutData, true);
            if (!oDirNrDBData.isResultOK ())
                strPhones = isdb.miscs.dclrs.NOTSELECTED;
            strHide += isdb.ifaces.htmli.formhidepar (COL_TECHREF_ID, oTechRefDBData.getVal (COL_ID)) +
                       isdb.ifaces.htmli.formhidepar (COL_STATEDATE, dbi.dbdate (oDBData)) +

                       // редагувати та вводити інформацію про абонента
                       isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_UPDATE_RECORD + isdb.miscs.dclrs.OBJ_FIRM,
                                                      oFirmDBData.getVal (COL_ID));

            oPhoneStateDBData.setVal (isdbobj.COL_ID, isdb.miscs.dclrs.STAGE_NEXT_AGREEMENT);
            String strTmp = getNextSeqId (oDBData, NUMBERAGREEMENT);
            jsdata oJSData = oOutData.getJSData ();
            oJSData.setJS (COL_AGR_NUM, isdb.ifaces.jsi.JS_FUNC_CHK_VAL, "-1", strTmp);
            oOutData.setJSData (oJSData);
            strAgrNum = field (COL_AGR_NUM, strTmp, 5, oOutData) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.getCheckBoxPrintRpt (isdb.miscs.dclrs.OBJ_AGREEMENT, isdb.miscs.dclrs.PAPER_ORDER_LINE);
            strTechRef = oTechRef.value (objtechref.COL_TECHREF, oTechRefDBData);
            oOutData.setFld (COL_STATE_ID, oAgreementState.value (objagreementstate.COL_STATE, oAgreementStateDBData));
            oOutData.setFld (COL_SPECCONDITION, textareafield (COL_SPECCONDITION, oDBData, oOutData));
            strStartDate = field (COL_STARTDATE, dbi.dbdate (oDBData), 14, oOutData);
            strFinishDate = value (COL_FINISHDATE, oDBData);
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            oOutData.setHideFld (COL_STATE_ID, oAgreementStateDBData.getVal (COL_ID));

            // рішення про укладання угоди
            strDecision = isdb.ifaces.htmli.row (
                            isdb.ifaces.htmli.cell (

                              // заголовок
                              isdb.ifaces.htmli.place (
                                isdb.ifaces.htmli.row (
                                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Прийняття рішення про укладання угоди") +
                                                          isdb.ifaces.htmli.crlf (), 100)
                                ), 0) +

                              isdb.ifaces.htmli.place (
                                isdb.ifaces.htmli.row (
                                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.div (
                                                            isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_AGREEMENT) +
                                                            " угода буде укладена" +
                                                            isdb.ifaces.htmli.crlf () +
                                                            isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REJECT) +
                                                            " відмова абонента від угоди" +
                                                            isdb.ifaces.htmli.crlf () +
                                                            isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNECTED, true) +
                                                            " телефон був вже включен раніше", "left"), 100)
                                ), 0)
                              , 100));
        }
        else	// режими: REGIME_UPDATE, REGIME_RETRIEVE?
        {
            // ініціалізація додаткових даних об'ектів
            oAgreementStateDBData.setVal (COL_ID, oDBData.getVal (COL_STATE_ID));
            oAgreementState.select (oAgreementStateDBData);
            oTechRefDBData.setVal (COL_ID, retrieve (COL_TECHREF_ID, oDBData));
            oTechRef.select (oTechRefDBData);
            oFirmDBData.setVal (COL_ID, retrieve (objtechref.COL_FIRM_ID, oTechRefDBData));
            oFirm.select (oFirmDBData);
            oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
            oProperty.select (oPropertyDBData);
            oTypeRejectAgrDBData.setVal (COL_ID, oDBData.getVal (COL_TYPEREJECTAGR_ID));
            oTypeRejectAgr.select (oTypeRejectAgrDBData);

            // додаткові кнопки навігації
            buttondata oTechRefButton = new buttondata ();
            oTechRefButton.setName ("Заява/тех. довідка");
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_TECHREF);
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_ID, oTechRefDBData.getVal (COL_ID));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oTechRefButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oTechRefButton.getButton (oOutData));

            buttondata oDirNrButton = new buttondata ();
            oDirNrButton.setUrl ("isdbproperty");
            oDirNrButton.setName ("Телефон(и)");
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_TYPESELECT);
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "TECHREF_ID=" + oTechRefDBData.getVal (COL_ID));
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
            oDBData.setButton (oDirNrButton.getButton (oOutData));

            buttondata oFirmButton = new buttondata ();
            oFirmButton.setName ("Абонент");
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oFirmDBData.getVal (COL_ID));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oFirmButton.getButton (oOutData));

            oDirNrDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "DIRNR" + isdb.miscs.dclrs.SQL_FROM + "DIRNRS, AGREEMENTS" +
                                     isdb.miscs.dclrs.SQL_WHERE + "DIRNRS.TECHREF_ID = AGREEMENTS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                     " AGREEMENTS.ID = " + oDBData.getVal (isdb.miscs.dclrs.PAR_ID) +
                                     isdb.miscs.dclrs.SQL_ORDERBY + "DIRNR");
            strPhones = getItems (oDirNrDBData);
            // strPhones = oDirNr.list (isdbobj.COL_DIRNR_ID, oDirNrDBData, oOutData, true);
            strAgrNum = value (COL_AGR_NUM, oDBData);
            strTechRef = oTechRef.value (objtechref.COL_TECHREF, oTechRefDBData);
            strStartDate = value (COL_STARTDATE, oDBData);
            strFinishDate = value (COL_FINISHDATE, oDBData);
            oOutData.setFld (COL_TYPEREJECTAGR_ID, oTypeRejectAgr.value (objtyperejectagr.COL_REJECT, oTypeRejectAgrDBData));

            // оновлення полів форми?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE))
            {
                oOutData.setFld (COL_STATE_ID, oAgreementState.list (COL_STATE_ID, oAgreementStateDBData, oOutData));
                oOutData.setFld (COL_SPECCONDITION, textareafield (COL_SPECCONDITION, oDBData, oOutData));
                strRemarks = textareafield (COL_REMARKS, oDBData);
            }
            else
            {
                oOutData.setFld (COL_STATE_ID, oAgreementState.value (objagreementstate.COL_STATE, oAgreementStateDBData));
                oOutData.setFld (COL_SPECCONDITION, value (COL_SPECCONDITION, oDBData));
                // вибірка інформації?
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                {
                    strStartDate = value (COL_STARTDATE, oDBData);
                    strRemarks = value (COL_REMARKS, oDBData);
                    strCountLines = "1";
                }
            }
        }
        strAgreementState = oAgreementState.value (objagreementstate.COL_STATE, oAgreementStateDBData);
        strFirm = oFirm.desc (objfirm.COL_NAME) +
                  isdb.ifaces.htmli.crlf () +
                  isdb.ifaces.htmli.value (oProperty.retrieve (objpropertie.COL_PROPERTY, oPropertyDBData) + " \"" +
                                           oFirm.retrieve (objfirm.COL_NAME, oFirmDBData) + "\"");
        strContactPerson = oFirm.value (objfirm.COL_CONTACTPERSON, oFirmDBData);
        strContactPhone = oFirm.value (objfirm.COL_CONTACTPHONE, oFirmDBData);
        strPhones = isdb.ifaces.htmli.value (strPhones);
        oPhoneState.select (oPhoneStateDBData);

        // приготування сторінки
        // перший ряд
        return isdb.ifaces.htmli.row (
                 isdb.ifaces.htmli.cell (

                   isdb.ifaces.htmli.place (
                     isdb.ifaces.htmli.row (
                       isdb.ifaces.htmli.cell (strAgrNum, 20) +
                       isdb.ifaces.htmli.cell (strAgreementState, 25) +
                       isdb.ifaces.htmli.cell (strFirm, 25) +
                       isdb.ifaces.htmli.cell (oOutData.getFld (COL_SPECCONDITION), 30)
                     ), 0)
                   , 100)) +

               // рішення про укладання угоди (тільки для REGIME_INSERT)
               strDecision +

               // другий ряд
               isdb.ifaces.htmli.row (
                 isdb.ifaces.htmli.cell (

                   // заголовок
                   isdb.ifaces.htmli.place (
                     isdb.ifaces.htmli.row (
                       isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Інформація про хід заключення угоди") + isdb.ifaces.htmli.crlf (), 100)
                     ), 0) +

                   isdb.ifaces.htmli.place (
                     isdb.ifaces.htmli.row (
                       isdb.ifaces.htmli.cell (strTechRef, 25) +
                       isdb.ifaces.htmli.cell ("Телефон(и):" + isdb.ifaces.htmli.crlf () +
                                               strPhones, 25) +
                       isdb.ifaces.htmli.cell (strStartDate, 25) +
                       isdb.ifaces.htmli.cell (strContactPerson +
                                               isdb.ifaces.htmli.crlf () +
                                               strContactPhone, 25)
                     ), 0)
                   , 100)) +

               // третій ряд
               isdb.ifaces.htmli.row (
                 isdb.ifaces.htmli.cell (
                   isdb.ifaces.htmli.place (
                     isdb.ifaces.htmli.row (
                       isdb.ifaces.htmli.cell (oOutData.getFld (COL_STATE_ID), 30) +
                       isdb.ifaces.htmli.cell (strFinishDate, 20) +
                       isdb.ifaces.htmli.cell (oOutData.getFld (COL_TYPEREJECTAGR_ID), 30) +
                       isdb.ifaces.htmli.cell (strRemarks, 20)
                     ), 0)
                   , 100)) +
               strBody +

               // сховани параметри
               strHide +
               oOutData.getHideFld ();
    }

    /**
     * Приготування звіта.
     * @param oDBData поточни дані об'екта
     * @return сформований в форматі HTML звіт.
     */
    public dbdata report (dbdata oDBData)
    {
        String strReport = "???";
        String strRptTitle = "";
        String strRptNumb = "";
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);
        sqldata oSQLData = new sqldata ();

        // Звіт по укладеним угодам на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_AGREEMENT_RPT_CURRENT))
        {
            strRptTitle = getTitle () + " на " + dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);
            strRptNumb = "";
            oRptData.setModeData (isdb.miscs.dclrs.SQL_SELECT + "AGR_NUM, to_char(AGREEMENTS.STARTDATE, 'DD-MM-YYYY'), TYPE, PROPERTY||' \"'||NAME||'\"', count(DIRNR)" +
                                  isdb.miscs.dclrs.SQL_FROM + "AGREEMENTS, TECHREFS, FIRMS, PROPERTIES, DIRNRS, TYPEAGREEMENTS" +
                                  isdb.miscs.dclrs.SQL_WHERE + "PROPERTIES.ID = PROPERTY_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "FIRMS.ID = FIRM_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "TECHREFS.ID = DIRNRS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "TECHREFS.ID = AGREEMENTS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "TYPEAGREEMENTS.ID = TYPEAGREEMENT_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "FINISHDATE" + isdb.miscs.dclrs.SQL_IS + "null" +
                                  isdb.miscs.dclrs.SQL_GROUPBY + "AGR_NUM, to_char(AGREEMENTS.STARTDATE, 'DD-MM-YYYY'), TYPE, PROPERTY||' \"'||NAME||'\"'" +
                                  isdb.miscs.dclrs.SQL_ORDERBY + "AGR_NUM");
            oRptData.createColumn ("N угоди", 10);
            oRptData.createColumn ("Дата укладання угоди", 15);
            oRptData.createColumn ("Тип угоди", 15);
            oRptData.createColumn ("Абонент", 50);
            oRptData.createColumn ("Кількість телефонів", 10);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        // довідка про включення бізнес-абонента?
        if (strRptId.equals (isdb.miscs.dclrs.PAPER_ORDER_LINE))
        {
            String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_POOL_ID);
            oDBData.setModeObj ("select PROPERTY||' \"'||NAME||'\"'" +
                                " from PROPERTIES, FIRMS, TECHREFS, AGREEMENTS" +
                                " where FIRMS.ID = FIRM_ID and" +
                                " PROPERTIES.ID = PROPERTY_ID and" +
                                " TECHREFS.ID = TECHREF_ID and" +
                                " AGREEMENTS.ID = " + strId);
            String strFirm = getItems (oDBData);
            oDBData.setModeObj ("select LOCATION||' '||', '||FIRMS.HOUSE||', конт. тел. '||CONTACTPHONE" +
                                " from AGREEMENTS, TECHREFS, FIRMS, LOCATIONS" +
                                " where LOCATIONS.ID = STREET_ID and" +
                                " FIRMS.ID = FIRM_ID and" +
                                " TECHREFS.ID = TECHREF_ID and" +
                                " AGREEMENTS.ID = " + strId +
                                " order by LOCATION||FIRMS.HOUSE desc");
            String strAddress = getItems (oDBData);
            oDBData.setModeObj ("select AGR_NUM" +
                                " from AGREEMENTS" +
                                " where ID = " + strId);
            String strAgrNum = getItems (oDBData);
            oDBData.setModeObj ("select to_char(STARTDATE, 'DD-MM-YY')" +
                                " from AGREEMENTS" +
                                " where AGREEMENTS.ID = " + strId);
            String strStartDate = getItems (oDBData);
            oDBData.setModeObj ("select count(1) from DIRNRS, AGREEMENTS" +
                                " where DIRNRS.TECHREF_ID = AGREEMENTS.TECHREF_ID and" +
                                " (" + isdb.miscs.dclrs.STAGE_CURR_OK + " or" +
                                " " + isdb.miscs.dclrs.STAGE_CURR_CONNINPROGRESS + ") and" +
                                " AGREEMENTS.ID = " + strId);
            String strCountLines = getItems (oDBData);
            oDBData.setModeObj ("select DIRNR||' ('||LOCATION||', '||HOUSE||')' from DIRNRS, AGREEMENTS, LOCATIONS" +
                                " where DIRNRS.TECHREF_ID = AGREEMENTS.TECHREF_ID and" +
                                " LOCATIONS.ID = STREET_ID and" +
                                " (" + isdb.miscs.dclrs.STAGE_CURR_OK + " or" +
                                " " + isdb.miscs.dclrs.STAGE_CURR_CONNINPROGRESS + ") and" +
                                " AGREEMENTS.ID = " + strId);
            String strDirNrs = getItems (oDBData);
            strReport =
              isdb.ifaces.htmli.crlf (5) +
              isdb.ifaces.htmli.value (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Довідка N " + strAgrNum +
                                                isdb.ifaces.htmli.crlf () +
                                                "про включення бізнес-абонента", "800080") +
                                                isdb.ifaces.htmli.crlf (2) +
                                                "від " + dbi.dbdate (oDBData).substring (0, 8) + " р." +
                                                isdb.ifaces.htmli.crlf (7)
                                                , 100)
                      ), 0)
                    , 100)) +

                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Назва абонента", 40) +
                        isdb.ifaces.htmli.cell (strFirm, 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Юридична адреса та контактний телефон", 40) +
                        isdb.ifaces.htmli.cell (strAddress, 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Номер угоди", 40) +
                        isdb.ifaces.htmli.cell (strAgrNum, 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Дата підключення", 40) +
                        isdb.ifaces.htmli.cell (strStartDate, 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Кількість бізнес-ліній", 40) +
                        isdb.ifaces.htmli.cell (strCountLines, 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Номер(а) бізнес-ліні(ї/й), фізичне розміщення", 40) +
                        isdb.ifaces.htmli.cell (strDirNrs, 60)
                      ), 1)
                    , 100)) +

                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Надана послуга", 40) +
                        isdb.ifaces.htmli.cell ("", 60)
                      ), 1)
                    , 100)) +
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("Підпис:", 40) +
                        isdb.ifaces.htmli.cell ("", 60)
                      ), 1)
                    , 100))
                , "000000");   // чорний колір
        }
        // Друкувати угоди по типам обраних пакетів
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_AGREEMENT_BY_TYPE))
        {
            strRptTitle = getTitle (oDBData) + " на " + dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);
            strRptNumb = "";

            oSQLData.setColumn ("AGR_NUM, to_char(AGREEMENTS.STARTDATE, 'DD-MM-YYYY'), PROPERTY||' \"'||NAME||'\"', count(DIRNR)");
            oSQLData.setFrom ("AGREEMENTS");
            oSQLData.setFrom ("TECHREFS");
            oSQLData.setFrom ("FIRMS");
            oSQLData.setFrom ("PROPERTIES");
            oSQLData.setFrom ("DIRNRS");
            oSQLData.setWhere ("PROPERTIES.ID=PROPERTY_ID");
            oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
            oSQLData.setWhere ("TECHREFS.ID=DIRNRS.TECHREF_ID");
            oSQLData.setWhere ("TECHREFS.ID=AGREEMENTS.TECHREF_ID");
            oSQLData.setWhere ("FINISHDATE" + isdb.miscs.dclrs.SQL_IS + "null");
            oSQLData.setWhere ("TYPEAGREEMENT_ID=" + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oSQLData.setGroup ("AGR_NUM,to_char(AGREEMENTS.STARTDATE,'DD-MM-YYYY'),PROPERTY||' \"'||NAME||'\"'");
            oSQLData.setOrder ("to_char(AGR_NUM,'99999')");
            oRptData.setModeData (oSQLData.getData ());
            oRptData.createColumn ("N угоди", 10);
            oRptData.createColumn ("Дата укладання угоди", 15);
            oRptData.createColumn ("Абонент", 60);
            oRptData.createColumn ("Кількість телефонів", 15);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        // Звіт по розподіленню абонентів по тарифікаційним пакетам на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_AGREEMENT_TARIFF_PACKET))
        {
            strRptTitle = getTitle (oDBData) + " на " + dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);
            strRptNumb = "";

            /*
            select distinct
            FIRMS.ID, NAME,AGR_NUM,TYPE
            -- FIRMS.ID||', '||NAME||', '||AGR_NUM||', '||TYPE
            from agreements, techrefs, firms, typeagreements
            where TECHREFS.ID = AGREEMENTS.TECHREF_ID and
            -- AGREEMENTS.ID = DIRNRS.TECHREF_ID and
            FIRMS.ID = FIRM_ID and
            TYPEAGREEMENTS.ID = TYPEAGREEMENT_ID
            ORDER BY FIRMS.ID
            */
            oSQLData.setDistinct ();
            oSQLData.setColumn ("FIRMS.ID");
            oSQLData.setColumn ("NAME");
            oSQLData.setColumn ("AGR_NUM");
            oSQLData.setColumn ("TYPE");
            oSQLData.setFrom ("AGREEMENTS,TECHREFS,FIRMS,TYPEAGREEMENTS");
            oSQLData.setWhere ("TECHREFS.ID=AGREEMENTS.TECHREF_ID");
            oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
            oSQLData.setWhere ("TYPEAGREEMENTS.ID=TYPEAGREEMENT_ID");
            oSQLData.setOrder ("FIRMS.ID");
            // oDBData.setModeObj (oSQLData.getData ());
            oRptData.setModeData (oSQLData.getData ());
            oRptData.createColumn ("N", 10);
            oRptData.createColumn ("Абонент", 50);
            oRptData.createColumn ("Номер угоди", 15);
            oRptData.createColumn ("Тариф. пакет", 25);

            ///
            // Exception enew = new Exception ("test: sql=" + oDBData.getModeObj ());
            // enew.printStackTrace ();
            ///

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
     * @return сформована HTML сторінка з аплетом для формування діаграми
     */
    public String graph (dbdata oDBData)
    {
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);
        sqldata oSQLData = new sqldata ();

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            objagreement oAgrByState = new objagreement ();
            dbdata oAgrByStateDBData = new dbdata (oDBData.getSession ());
            oAgrByStateDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
            oAgrByStateDBData.setVal (isdb.miscs.dclrs.PAR_NEXTOBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
            oAgrByStateDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oAgrByState.describe (oAgrByStateDBData);

            graphi oGraphAgrState = new graphi (oDBData);
            oGraphAgrState.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            oSQLData.setDistinct ();
            oSQLData.setColumn ("AGREEMENTSTATES.ID,STATE");
            oSQLData.setFrom ("AGREEMENTSTATES,AGREEMENTS");
            oSQLData.setWhere ("AGREEMENTSTATES.ID=STATE_ID");
            oSQLData.setOrder ("STATE");
            hashVals = dbi.getListObj (oDBData.getSession (), hashVals, oSQLData.getData ());
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oGraphAgrState.addPieChartElement ((String) hashVals.get (strKey),
                                                   oAgrByState.countItems (oAgrByStateDBData, "STATE_ID='" + strKey + "'"),
                                                   oAgrByState.setURLObj (oAgrByStateDBData,
                                                                          "STATE_ID='" + strKey + "'",
                                                                          isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                                                          isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE));
            }
            return oGraphAgrState.getApplet ();
        }
        // Статистика укладених угод за рік
        if (strRptId.equals (isdb.miscs.dclrs.GRAPH_SIGN_AGREEMENT_YEAR))
            return graph (oDBData, "STARTDATE");
        return null;
    }

    /**
     * Проведення транзакції
     * @param oDBData поточни дані об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        transactiondata oTransactionData = new transactiondata ();
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT))  // укладання угоди?
        {
            Enumeration enumId = dbi.getIdObj (oDBData, isdb.miscs.dclrs.SQL_SELECT + "DIRNRS.ID" + isdb.miscs.dclrs.SQL_FROM + "DIRNRS, TECHREFS" +
                                               isdb.miscs.dclrs.SQL_WHERE + "TECHREFS.ID = TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                               isdb.miscs.dclrs.STAGE_CURR_AGREEMENT + isdb.miscs.dclrs.SQL_AND +
                                               "TECHREF_ID = " + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            while (enumId.hasMoreElements ())
            {
                oTransactionData.setPostTransaction (isdb.miscs.dclrs.SQL_UPDATE + "DIRNRS" + isdb.miscs.dclrs.SQL_SET + "PHONESTATE_ID = '" +
                                                     oDBData.getVal (objdirnr.COL_PHONESTATE_ID) +
                                                     "'" + isdb.miscs.dclrs.SQL_WHERE + "ID = " + (String) enumId.nextElement ());
            }
            oDBData.removeVal (objdirnr.COL_PHONESTATE_ID);
        }
        super.writeData (oDBData, oPoolData, oTransactionData);
    }

    /**
     * pre-маніпуляція даними поперед загрузки в стек данич
     * @param oDBData поточни дані об'екта
     */
    public void prePrepareData (dbdata oDBData)
    {
        // не треба оновляти інформацію стосовно абонента?
        if (oDBData.isRegime (isdb.miscs.dclrs.STAGE_STATUS_REJECT) || // відмова абонента від угоди?
                oDBData.isRegime (isdb.miscs.dclrs.STAGE_STATUS_CONNECTED))    // телефон був вже включен раніше?
            oDBData.removeVal (isdb.miscs.dclrs.PAR_UPDATE_RECORD + isdb.miscs.dclrs.OBJ_FIRM);
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)       // не знайдено?
            return "Не вибрана потрібна угода!";
        return super.getMsg (iNumberMsg, oDBData);
    }

    /**
     * Повернення назви ссилочного об'екта.
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals ("STATE_ID"))
            return "AGREEMENTSTATES";
        return super.getRefObj (strNameRefKey, oDBData);
    }
}


