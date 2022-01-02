/**
 * objtechref.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці TECHREFS.
 * @version 1.0 final, 29-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtechref extends isdbobj
{
    // Поля таблиці TECHREFS
    public static final String COL_TECHREF = "TECHREF";
    public static final String COL_FIRSTDATE = "FIRSTDATE";
    public static final String COL_FIRM_ID = "FIRM_ID";
    public static final String COL_STATEDATE = "STATEDATE";
    public static final String COL_PERSON_ID = "PERSON_ID";

    /** Послідуючій номер нового запису */
    private static String NUMBERTECHREF = "NUMBERTECHREF";
    private static String PAR_CONTACTPHONE_PRINT = isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX + "CONTACTPHONE_PRINT";

    private String strSalesOutNumber = null;
    private String strNameUtel = null;
    private String strPersonSales = null;
    private String strNameSales = null;
    private String strPersonDirectorTelecom = null;
    private String strNameTelecom = null;

    /**
     * Конструктор.
     */
    public objtechref ()
    {
        super (isdb.miscs.dclrs.TBL_TECHREF);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setDistinct ();
        oSQLData.setColumn ("TECHREFS.ID, 'N '||TECHREF||', '||PROPERTY||' \"'||NAME||'\", від '||to_char(FIRSTDATE, 'DD-MM-YY')");
        oSQLData.setFrom ("TECHREFS,PROPERTIES,FIRMS,DIRNRS");
        oSQLData.setWhere ("TECHREFS.ID=TECHREF_ID");
        oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
        oSQLData.setWhere ("PROPERTIES.ID=PROPERTY_ID");
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        if (strKey.equals (objagreement.COL_TECHREF_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @param oDBData поточні дані об'екта
     * @return ознака можливісті на створення нового запису
     */
    public boolean isCreateable (dbdata oDBData)
    {
        return false;
    }

    /**
     * Головне (однострокове) поле об'екта для вибірки.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return значення поля
     */
    public String field (dbdata oDBData, outdata oOutData)
    {
        jsdata oJSData = oOutData.getJSData ();
        oJSData.setJS (COL_TECHREF, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
        oOutData.setJSData (oJSData);
        return super.field (COL_TECHREF, oDBData, oOutData);
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
            return super.fields (oDBData, oOutData);
        }

        String strTechRef = null;
        String strCountLines = null;
        String strPhoneState = null;
        String strFirstDate = null;
        String strFirm = null;
        String strContactPhone = null;
        String strContactPerson = null;
        String strRemarks = null;

        String strRegime = oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME);
        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objfirm oFirm = new objfirm ();
        objphonestate oPhoneState = new objphonestate ();
        objpropertie oProperty = new objpropertie ();

        // .. та їх дані
        dbdata oFirmDBData = new dbdata (strSesnId);
        dbdata oPhoneStateDBData = new dbdata (strSesnId);
        dbdata oPropertyDBData = new dbdata (strSesnId);

        // ініціалізація додаткових данних об'ектів
        oFirmDBData.setVal (COL_ID, retrieve (objtechref.COL_FIRM_ID, oDBData));
        oFirm.select (oFirmDBData);
        oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
        oProperty.select (oPropertyDBData);

        // оформлення нової заяви?
        if (strRegime.equals (isdb.miscs.dclrs.REGIME_INSERT))
        {
            // oOutData.setHideFld (isdb.miscs.dclrs.PAR_IMMEDUPDATE, isdb.miscs.dclrs.YES);
            oPhoneStateDBData.setVal (COL_ID, isdb.miscs.dclrs.STAGE_NEXT_REGISTER);
            String strTmp = getNextSeqId (oDBData, NUMBERTECHREF);
            jsdata oJSData = oOutData.getJSData ();
            oJSData.setJS (COL_TECHREF, isdb.ifaces.jsi.JS_FUNC_CHK_VAL, "-1", strTmp);
            oOutData.setJSData (oJSData);
            strTechRef = field (COL_TECHREF, strTmp, 5, oOutData) +
                         htmli.crlf () +
                         htmli.getCheckBoxPrintRpt (isdb.miscs.dclrs.OBJ_TECHREF, isdb.miscs.dclrs.PAPER_REQUEST_LINES_PTT);
            /* +
            htmli.crlf () +
            htmli.formcheckboxpar (PAR_CONTACTPHONE_PRINT, isdb.miscs.dclrs.YES) + " друкувати контактний телефон"; */

            /*
                        strBase = htmli.div (htmli.formradiopar (COL_BASE, "U", true) + " Утел" +
                                             htmli.crlf () +
                                             htmli.formradiopar (COL_BASE, "L") + " Телеком", "left");
            */

            strCountLines = htmli.bold ("Кількість ліній") + htmli.crlf () +
                            field (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_DIRNR, "1", 5, oOutData, true);
            // oFirmDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            // strFirm = oFirm.list (COL_FIRM_ID, oFirmDBData, oOutData);

            oFirmDBData.setVal (COL_ID, retrieve (COL_ID, oDBData));
            oFirm.select (oFirmDBData);
            oOutData.setHideFld (COL_FIRM_ID, retrieve (COL_ID, oDBData));
            strFirm = oFirm.value (objfirm.COL_NAME, oFirmDBData);

            /*
             + htmli.crlf () +
                      htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_FIRM);
            */

            strFirstDate = field (COL_FIRSTDATE, dbi.dbdate (oDBData), 14, oOutData);
            strContactPerson = "";
            strContactPhone = "";
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
        }
        else	// режими: REGIME_UPDATE, REGIME_RETRIEVE?
        {
            // додаткові кнопки навігації
            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            {
                objagreement oAgreement = new objagreement ();
                dbdata oAgreementDBData = new dbdata (strSesnId);
                if (strId.equals (isdb.miscs.dclrs.OBJ_NULL))
                    strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
                oAgreementDBData.setVal (objagreement.COL_TECHREF_ID, strId);
                oAgreementDBData = oAgreement.id (oAgreementDBData);
                buttondata oAgreementButton = new buttondata ();
                oAgreementButton.setName ("Угода");
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_ID, oAgreementDBData.getVal (COL_ID));
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
                oDBData.setButton (oAgreementButton.getButton (oOutData));

                buttondata oFirmButton = new buttondata ();
                oFirmButton.setName ("Абонент");
                oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
                oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oFirmDBData.getVal (COL_ID));
                oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
                oDBData.setButton (oFirmButton.getButton (oOutData));
            }
            strTechRef = value (objtechref.COL_TECHREF, oDBData);
            strFirm = oFirm.desc (objfirm.COL_NAME) +
                      htmli.value (oProperty.retrieve (objpropertie.COL_PROPERTY, oPropertyDBData) + " \"" +
                                   oFirm.retrieve (objfirm.COL_NAME, oFirmDBData) + "\"");
            strFirstDate = value (COL_FIRSTDATE, oDBData);
            strContactPerson = oFirm.value (objfirm.COL_CONTACTPERSON, oFirmDBData);
            strContactPhone = oFirm.value (objfirm.COL_CONTACTPHONE, oFirmDBData);
            strCountLines = "";

            // оновлення полів форми?
            if (strRegime.equals (isdb.miscs.dclrs.REGIME_UPDATE))
                strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            else
                strRemarks = value (COL_REMARKS, oDBData);
        }
        oPhoneState.select (oPhoneStateDBData);
        strPhoneState = oPhoneState.value (objphonestate.COL_STATE, oPhoneStateDBData);

        // приготування сторінки
        // перший ряд
        return htmli.row (
                 htmli.cell (

                   htmli.place (
                     htmli.row (
                       htmli.cell (strTechRef, 40) +
                       htmli.cell (strPhoneState, 40)
                     ), 0)
                   , 100)) +

               // другий ряд
               htmli.row (
                 htmli.cell (

                   // заголовок
                   htmli.place (
                     htmli.row (
                       htmli.cell (htmli.subtitle ("Інформація про абонента") + htmli.crlf (), 100)
                     ), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strFirm, 50) +
                       htmli.cell (strContactPerson, 25) +
                       htmli.cell (strContactPhone, 25)
                     ), 0)
                   , 100)) +

               // третій ряд
               htmli.row (
                 htmli.cell (

                   // заголовок
                   htmli.place (
                     htmli.row (
                       htmli.cell (htmli.subtitle ("Подробиці реєстрації заяви") + htmli.crlf (), 100)
                     ), 0) +

                   htmli.place (
                     htmli.row (
                       htmli.cell (strCountLines +
                                   htmli.crlf () +
                                   strFirstDate, 50) +
                       htmli.cell (strRemarks, 50)
                     ), 0)
                   , 100)) +

               // сховани параметри
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

        objperson oPerson = new objperson ();
        dbdata oPersonDBData = new dbdata (oDBData.getSession ());
        oPersonDBData.setCriteriaObj ("USERNAME='" + dbi.user (oDBData.getSession ()) + "'");
        oPerson.select (oPersonDBData);

        strSalesOutNumber = cfgi.getOption (isdb.miscs.dclrs.OPT_SALES_OUT_NUMBER);
        strPersonSales = oPerson.retrieve (objperson.COL_PERSON, oPersonDBData);
        strNameSales = cfgi.getOption (isdb.miscs.dclrs.OPT_NAME_SALES);
        strNameUtel = cfgi.getOption (isdb.miscs.dclrs.OPT_NAME_UTEL);
        strPersonDirectorTelecom = cfgi.getOption (isdb.miscs.dclrs.OPT_PERSON_DIRECTOR_TELECOM);
        strNameTelecom = cfgi.getOption (isdb.miscs.dclrs.OPT_NAME_TELECOM);

        ///
        // Exception enew55 = new Exception ("report: " + oDBData.toString ());
        // enew55.printStackTrace ();
        ///

        // заява на надання ліній в Телеком?
        if (strRptId.equals (isdb.miscs.dclrs.PAPER_REQUEST_LINES_PTT))
        {
            String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
            oDBData.setModeObj ("select TECHREF_ID from DIRNRS" +
                                " where ID=" + strId);
            strId = getItems (oDBData);
            oDBData.setModeObj ("select TECHREF from TECHREFS" +
                                " where ID=" + strId);
            strSalesOutNumber += getItems (oDBData);
            oDBData.setModeObj ("select count(1) from DIRNRS" +
                                " where TECHREF_ID = " + strId);
            String strCountLines = getItems (oDBData);
            if (strCountLines.equals ("1"))
                strCountLines = " однієї бізнес-лінії";
            else
                strCountLines += " бізнес-ліній";
            oDBData.setModeObj ("select PROPERTY||' \"'||NAME||'\"'" +
                                " from PROPERTIES, FIRMS, TECHREFS" +
                                " where FIRMS.ID = FIRM_ID and" +
                                " PROPERTIES.ID = PROPERTY_ID and" +
                                " TECHREFS.ID = " + strId);
            String strFirm = getItems (oDBData);
            oDBData.setModeObj ("select '--  за адресою: '||TYPELOCATION||' '||NAME||', '||HOUSE" +
                                " from TECHREFS, DIRNRS, STREETS, TYPELOCATIONS" +
                                " where TYPELOCATIONS.ID = TYPELOCATION_ID and" +
                                " STREETS.ID = STREET_ID and" +
                                " TECHREFS.ID = TECHREF_ID" +
                                " and TECHREFS.ID = " + strId +
                                " order by DIRNR||TYPELOCATION||NAME||HOUSE desc");
            String strAddress = getItems (oDBData);
            String strContactPhone = "";
            if (String.valueOf (oDBData.getVal (PAR_CONTACTPHONE_PRINT)) != "null")         // друкувати контактн. телефон?
            {
                oDBData.setModeObj ("select CONTACTPHONE" +
                                    " from TECHREFS, FIRMS" +
                                    " where FIRMS.ID = FIRM_ID" +
                                    " and TECHREFS.ID = " + strId);
                strContactPhone = "Контактний телефон: " + getItems (oDBData) + ".";
            }
            strReport = htmli.row (
                          htmli.cell (
                            htmli.place (
                              htmli.row (
                                htmli.cell (strSalesOutNumber +
                                            htmli.crlf () +
                                            "від " + dbi.dbdate (oDBData).substring (0, 8) + " р.", 70, "left") +
                                htmli.cell (htmli.crlf () +
                                            strNameTelecom +
                                            htmli.crlf () +
                                            strPersonDirectorTelecom +
                                            htmli.crlf (10), 30, "left")
                              ), 0)
                            , 100)) +
                        htmli.row (
                          htmli.cell (
                            htmli.place (
                              htmli.row (
                                htmli.cell (htmli.crlf () +
                                            htmli.crlf () +
                                            htmli.crlf () +
                                            "<P>Просимо Вас розглянути техничну можливість для надання " +
                                            strCountLines +
                                            " зв'язку для:<P>" +
                                            strFirm + "," +
                                            htmli.crlf (2) +
                                            strAddress +
                                            htmli.crlf (3) +
                                            strContactPhone +
                                            htmli.crlf (10), 100, "left")
                              ), 0)
                            , 100)) +
                        htmli.row (
                          htmli.cell (
                            htmli.place (
                              htmli.row (
                                htmli.cell (strNameSales +
                                            htmli.crlf () +
                                            strNameUtel, 40, "left") +
                                htmli.cell ("", 20) +
                                htmli.cell (strPersonSales, 40)
                              ), 0)
                            , 100));
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }

    /**
     * pre-маніпуляція даними поперед загрузки в стек данич
     * @param oDBData поточни дані об'екта
     */
    public void prePrepareData (dbdata oDBData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT))
            oDBData.setVal (isdb.miscs.dclrs.PAR_TRANSFER_PARAM, oDBData.getVal (COL_TECHREF));
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни данні об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        oDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_TECHREF));
        super.writeData (oDBData, oPoolData);
        ///
        Exception enew55 = new Exception ("writeData :\nDBData: " + oDBData.toString ());
        enew55.printStackTrace ();
        ///
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
            return "Не вибрана потрібна заява (технична довідка)!";
        return super.getMsg (iNumberMsg, oDBData);
    }
}

