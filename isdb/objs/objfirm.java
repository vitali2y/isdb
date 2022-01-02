/**
 * objfirm.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.objs.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці FIRMS.
 * @version 1.0 final, 26-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objfirm extends isdbobj
{
    public static final String COL_NAME = "NAME";
    public static final String COL_NAME_EN = "NAME_EN";
    public static final String COL_PAYCODE = "PAYCODE";
    public static final String COL_PROPERTY_ID = "PROPERTY_ID";
    public static final String COL_FIRMSTATE_ID = "FIRMSTATE_ID";
    public static final String COL_TYPEFIRM_ID = "TYPEFIRM_ID";
    public static final String COL_ACTIVITY_ID = "ACTIVITY_ID";
    public static final String COL_BANK_ID = "BANK_ID";
    public static final String COL_BANKACCOUNT = "BANKACCOUNT";
    public static final String COL_BANKNUMBER = "BANKNUMBER";
    public static final String COL_TAXNUMBER = "TAXNUMBER";
    public static final String COL_TAXCERTIFICATE = "TAXCERTIFICATE";
    public static final String COL_ZIP_ID = "ZIP_ID";
    public static final String COL_STREET_ID = "STREET_ID";
    public static final String COL_HOUSE = "HOUSE";
    public static final String COL_ADDLOCATION = "ADDLOCATION";
    public static final String COL_POSTADDRESS = "POSTADDRESS";
    public static final String COL_CONTACTPHONE = "CONTACTPHONE";
    public static final String COL_CONTACTPERSON = "CONTACTPERSON";
    public static final String COL_YEARINCOME = "YEARINCOME";
    public static final String COL_STATEDATE = "STATEDATE";
    public static final String COL_PERSON_ID = "PERSON_ID";

    /**
     * Конструктор.
     */
    public objfirm ()
    {
        super (isdb.miscs.dclrs.TBL_FIRM);
    }

    /**
     * Повернення шапки об'екта
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу абонентів по типам власності";
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
        oSQLData.setColumn ("FIRMS.ID, NAME||', '||PROPERTY");
        oSQLData.setFrom ("FIRMS, PROPERTIES");
        oSQLData.setWhere ("PROPERTIES.ID = PROPERTY_ID");
        oSQLData.setOrder ("NAME||PROPERTY");
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
        if (strKey.equals (objtechref.COL_FIRM_ID)) // ссилочне поле?
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
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
            return false;
        else
            return true;
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
        oJSData.setJS (COL_NAME, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
        oOutData.setJSData (oJSData);
        return field (COL_NAME, oDBData, oOutData);
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
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            {
                setHideColumn (COL_NAME_EN);
                setHideColumn (COL_PAYCODE);
                setHideColumn (COL_BANK_ID);
                setHideColumn (COL_BANKACCOUNT);
                setHideColumn (COL_POSTADDRESS);
                setHideColumn (COL_BANKNUMBER);
                setHideColumn (COL_TAXNUMBER);
                setHideColumn (COL_ACTIVITY_ID);
                setHideColumn (COL_FIRMSTATE_ID);
                setHideColumn (COL_TYPEFIRM_ID);
                setHideColumn (COL_TAXCERTIFICATE);
                setHideColumn (COL_POSTADDRESS);
                setHideColumn (COL_YEARINCOME);
            }
            return super.fields (oDBData, oOutData);
        }

        String strName = null;
        String strPayCode = null;
        String strProperty = null;
        String strBankName = null;
        String strBankNumber = null;
        String strBankAccount = null;
        String strTaxNumber = null;
        String strTaxCertificate = null;
        String strZip = null;
        String strLocation = null;
        String strHouse = null;
        String strAddLocation = null;
        String strPostAddress = "";
        String strContactPhone = null;
        String strContactPerson = null;
        String strRemarks = null;
        String strDetailInfo = "";

        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти ...
        objpropertie oProperty = new objpropertie ();
        objbank oBank = new objbank ();
        objlocation oLocation = new objlocation ();
        objzip oZip = new objzip ();
        objtypefirm oTypeFirm = new objtypefirm ();
        objfirmstate oFirmState = new objfirmstate ();
        objactivitie oActivity = new objactivitie ();

        // ... та їх дані
        dbdata oPropertyDBData = new dbdata (strSesnId);
        dbdata oBankDBData = new dbdata (strSesnId);
        dbdata oLocationDBData = new dbdata (strSesnId);
        dbdata oZipDBData = new dbdata (strSesnId);
        dbdata oTypeFirmDBData = new dbdata (strSesnId);
        dbdata oFirmStateDBData = new dbdata (strSesnId);
        dbdata oActivityDBData = new dbdata (strSesnId);

        // ініціалізація додаткових даних об'ектів
        oPropertyDBData.setVal (COL_ID, retrieve (COL_PROPERTY_ID, oDBData));
        oLocationDBData.setVal (COL_ID, retrieve (COL_STREET_ID, oDBData));
        oBankDBData.setVal (COL_ID, retrieve (COL_BANK_ID, oDBData));
        oZipDBData.setVal (COL_ID, retrieve (COL_ZIP_ID, oDBData));
        oTypeFirmDBData.setVal (COL_ID, retrieve (COL_TYPEFIRM_ID, oDBData));
        oFirmStateDBData.setVal (COL_ID, retrieve (COL_FIRMSTATE_ID, oDBData));
        oActivityDBData.setVal (COL_ID, retrieve (COL_ACTIVITY_ID, oDBData));

        oProperty.select (oPropertyDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            oBank.select (oBankDBData);
        oLocation.select (oLocationDBData);
        oZip.select (oZipDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            oTypeFirm.select (oTypeFirmDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            oFirmState.select (oFirmStateDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            oActivity.select (oActivityDBData);

        // оформлення нової заяви?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oOutData.setHideFld (COL_STATEDATE, dbi.dbdate (oDBData));
            strName = field (COL_NAME, oDBData, oOutData);
            oOutData.setFld (COL_NAME_EN, field (COL_NAME_EN, oDBData, oOutData));
            strPayCode = field (COL_PAYCODE, oDBData, oOutData);
            oPropertyDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            strProperty = oProperty.list (COL_PROPERTY_ID, oPropertyDBData, oOutData);
            strBankName = isdb.ifaces.htmli.formhidepar (COL_BANK_ID, "1");
            strBankAccount = isdb.ifaces.htmli.formhidepar (COL_BANKACCOUNT, "0");
            strBankNumber = isdb.ifaces.htmli.formhidepar (COL_BANKNUMBER, "0");
            strTaxNumber = isdb.ifaces.htmli.formhidepar (COL_TAXNUMBER, "0");
            strTaxCertificate = isdb.ifaces.htmli.formhidepar (COL_TAXCERTIFICATE, "0");
            strZip = isdb.ifaces.htmli.formhidepar (COL_ZIP_ID, "1");
            strLocation = isdb.ifaces.htmli.formhidepar (COL_STREET_ID, "1");
            strHouse = isdb.ifaces.htmli.formhidepar (COL_HOUSE, "999");
            strContactPerson = field (COL_CONTACTPERSON, oDBData, oOutData);
            strContactPhone = field (COL_CONTACTPHONE, oDBData, oOutData);
            oOutData.setFld (COL_YEARINCOME, field (COL_YEARINCOME, oDBData, oOutData));
            oTypeFirmDBData.setVal (COL_ID, "B");
            oTypeFirm.select (oTypeFirmDBData);
            oOutData.setFld (COL_TYPEFIRM_ID, oTypeFirm.list (COL_TYPEFIRM_ID, oTypeFirmDBData, oOutData));
            oFirmStateDBData.setVal (COL_ID, "F");
            oFirmState.select (oFirmStateDBData);
            oOutData.setFld (COL_FIRMSTATE_ID, oFirmState.list (COL_FIRMSTATE_ID, oFirmStateDBData, oOutData));
            oActivityDBData.setVal (COL_ID, "0");
            oActivity.select (oActivityDBData);
            oOutData.setFld (COL_ACTIVITY_ID, oActivity.list (COL_ACTIVITY_ID, oActivityDBData, oOutData));
            // введення нових даних стосовно технич. довыдок
            oOutData.setHideFld (isdb.ifaces.htmli.formhidepar (isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + isdb.miscs.dclrs.OBJ_TECHREF, "1"));
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            strDetailInfo = strBankName +
                            strBankNumber +
                            strBankAccount +
                            strTaxNumber +
                            strTaxCertificate +
                            strZip +
                            strLocation +
                            strHouse;
        }
        else	// режими: REGIME_UPDATE, REGIME_RETRIEVE?
        {
            objtechref oTechRef = new objtechref ();
            dbdata oTechRefDBData = new dbdata (strSesnId);
            oTechRefDBData.setVal (objtechref.COL_FIRM_ID, strId);
            oTechRefDBData = oTechRef.id (oTechRefDBData);

            // додаткові кнопки навігації
            buttondata oDirNrButton = new buttondata ();
            oDirNrButton.setUrl ("isdbproperty");
            oDirNrButton.setName ("Телефон(и)");
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_TYPESELECT);
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "TECHREF_ID in (" + oTechRefDBData.getVal (COL_ID) + ")");
            oDirNrButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
            oDBData.setButton (oDirNrButton.getButton (oOutData));

            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))
            {
                // угоди
                buttondata oAgreementButton = new buttondata ();
                oAgreementButton.setUrl ("isdbproperty");
                oAgreementButton.setName ("Угода(и)");
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_TYPESELECT);
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_CRITERIA, "TECHREF_ID in (" + oTechRefDBData.getVal (COL_ID) + ")");
                oAgreementButton.setPar (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
                oDBData.setButton (oAgreementButton.getButton (oOutData));
                // баланс
                buttondata oFirmIncomeButton = new buttondata ();
                oFirmIncomeButton.setName ("Баланс");
                oFirmIncomeButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRMINCOME);
                oFirmIncomeButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oFirmIncomeButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oFirmIncomeButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
                oFirmIncomeButton.setPar (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_ID));
                oDBData.setButton (oFirmIncomeButton.getButton (oOutData));
            }
            // оновлення полів форми?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE))
            {
                oOutData.setHideFld (COL_STATEDATE, dbi.dbdate (oDBData));
                strName = field (COL_NAME, oDBData, oOutData);
                oOutData.setFld (COL_NAME_EN, field (COL_NAME_EN, oDBData, oOutData));
                strPayCode = field (COL_PAYCODE, oDBData, oOutData);
                oPropertyDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                strProperty = oProperty.list (oPropertyDBData, oOutData);
                oBankDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                strBankName = oBank.list (oBankDBData, oOutData);
                strBankAccount = field (COL_BANKACCOUNT, oDBData, oOutData);
                strBankNumber = field (COL_BANKNUMBER, oDBData, oOutData);
                strTaxNumber = field (COL_TAXNUMBER, oDBData, oOutData);
                strTaxCertificate = field (COL_TAXCERTIFICATE, oDBData, oOutData);
                oZipDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                strZip = oZip.list (oZipDBData, oOutData) + isdb.ifaces.htmli.crlf ();
                oLocationDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                strLocation = oLocation.list (oLocationDBData, oOutData);
                strHouse = field (COL_HOUSE, oDBData, oOutData);
                strAddLocation = textareafield (COL_ADDLOCATION, oDBData, oOutData);
                strPostAddress = textareafield (COL_POSTADDRESS, oDBData, oOutData);
                strContactPerson = field (COL_CONTACTPERSON, oDBData, oOutData);
                strContactPhone = field (COL_CONTACTPHONE, oDBData, oOutData);
                oOutData.setFld (COL_YEARINCOME, field (COL_YEARINCOME, oDBData, oOutData));
                oOutData.setFld (COL_TYPEFIRM_ID, oTypeFirm.list (COL_TYPEFIRM_ID, oTypeFirmDBData, oOutData));
                oOutData.setFld (COL_FIRMSTATE_ID, oFirmState.list (COL_FIRMSTATE_ID, oFirmStateDBData, oOutData));
                oOutData.setFld (COL_ACTIVITY_ID, oActivity.list (COL_ACTIVITY_ID, oActivityDBData, oOutData));
                strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
            }
            else
            {
                // вибірка інформації?
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                {
                    strName = value (COL_NAME, oDBData);
                    oOutData.setFld (COL_NAME_EN, value (COL_NAME_EN, oDBData));
                    strPayCode = value (COL_PAYCODE, oDBData);
                    strProperty = oProperty.value (objpropertie.COL_PROPERTY, oPropertyDBData);
                    strContactPerson = value (COL_CONTACTPERSON, oDBData);
                    strContactPhone = value (COL_CONTACTPHONE, oDBData);
                    if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
                        strBankName = oBank.value (objbank.COL_NAME, oBankDBData);
                    else
                        strBankName = "";
                    strBankNumber = value (COL_BANKNUMBER, oDBData);
                    strBankAccount = value (COL_BANKACCOUNT, oDBData);
                    strTaxNumber = value (COL_TAXNUMBER, oDBData);
                    strTaxCertificate = value (COL_TAXCERTIFICATE, oDBData);
                    strZip = oZip.value (objzip.COL_ZIP, oZipDBData);
                    strLocation = oLocation.value (objlocation.COL_LOCATION, oLocationDBData);
                    strHouse = value (COL_HOUSE, oDBData);
                    strAddLocation = value (COL_ADDLOCATION, oDBData);
                    strPostAddress = value (COL_POSTADDRESS, oDBData);
                    if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
                    {
                        oOutData.setFld (COL_YEARINCOME, value (COL_YEARINCOME, oDBData));
                        oOutData.setFld (COL_TYPEFIRM_ID, oTypeFirm.value (objtypefirm.COL_TYPE, oTypeFirmDBData));
                        oOutData.setFld (COL_FIRMSTATE_ID, oFirmState.value (objfirmstate.COL_STATE, oFirmStateDBData));
                        oOutData.setFld (COL_ACTIVITY_ID, oActivity.value (objactivitie.COL_ACTIVITY, oActivityDBData));
                    }
                    else
                    {
                        oOutData.setFld (COL_YEARINCOME, "");
                        oOutData.setFld (COL_TYPEFIRM_ID, "");
                        oOutData.setFld (COL_FIRMSTATE_ID, "");
                        oOutData.setFld (COL_ACTIVITY_ID, "");
                    }
                    strRemarks = value (COL_REMARKS, oDBData);
                }
            }
            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?

                // третій (додатковий) ряд
                strDetailInfo = isdb.ifaces.htmli.paragraph ("Банківська інформація",
                                isdb.ifaces.htmli.cell (strBankName, 33) +
                                isdb.ifaces.htmli.cell (strBankAccount, 33) +
                                isdb.ifaces.htmli.cell (strBankNumber, 33)) +

                                // четвертий ряд
                                isdb.ifaces.htmli.paragraph ("Податкова інформація",
                                                             isdb.ifaces.htmli.cell (strTaxNumber, 50) +
                                                             isdb.ifaces.htmli.cell (strTaxCertificate, 50));

            // п'ятий ряд
            strDetailInfo += isdb.ifaces.htmli.paragraph ("Юрідична адреса",
                             isdb.ifaces.htmli.cell (strLocation +
                                                     isdb.ifaces.htmli.crlf () +
                                                     strZip, 50) +
                             isdb.ifaces.htmli.cell (strHouse +
                                                     isdb.ifaces.htmli.crlf () +
                                                     strAddLocation, 50));
        }

        // приготування сторінки
        // перший ряд
        return isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (strName, 50) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_NAME_EN), 50)) +

               // другий ряд
               isdb.ifaces.htmli.paragraph ("Загальна інформація",
                                            isdb.ifaces.htmli.cell (strPayCode, 10) +
                                            isdb.ifaces.htmli.cell (strProperty, 20) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_YEARINCOME), 10) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_TYPEFIRM_ID), 20) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_ACTIVITY_ID), 20) +
                                            isdb.ifaces.htmli.cell (oOutData.getFld (COL_FIRMSTATE_ID), 20)) +

               // третій (додатковий) ряд
               strDetailInfo +

               // шостий ряд
               isdb.ifaces.htmli.paragraph ("Контактна інформація",
                                            isdb.ifaces.htmli.cell (strContactPerson, 50) +
                                            isdb.ifaces.htmli.cell (strContactPhone, 50)) +

               // сьомий ряд
               isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (strPostAddress, 50) +
                                            isdb.ifaces.htmli.cell (strRemarks, 50)) +

               // сховані параметри
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

        // Звіт по абонентам, угоди з якими укладени на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_FIRM_RPT_CURRENT))
        {
            strRptTitle = getTitle () + " на " + dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";

            /*
            select PROPERTY||' "'||NAME||'"', LOCATION||', '||HOUSE, AGR_NUM, count(AGR_NUM)
            from AGREEMENTS, TECHREFS, FIRMS, PROPERTIES, LOCATIONS
            where TECHREFS.ID = TECHREF_ID and
            FIRMS.ID = FIRM_ID and
            PROPERTIES.ID = PROPERTY_ID and
            LOCATIONS.ID = STREET_ID
            group by PROPERTY||' "'||NAME||'"', LOCATION||', '||HOUSE, AGR_NUM

            select PROPERTY||' "'||NAME||'"', LOCATION||', '||HOUSE, AGR_NUM, count(AGR_NUM)
            from AGREEMENTS, TECHREFS, FIRMS, PROPERTIES, LOCATIONS
            where TECHREFS.ID = TECHREF_ID and
            FIRMS.ID = FIRM_ID and
            PROPERTIES.ID = PROPERTY_ID and
            LOCATIONS.ID = STREET_ID and
            FIRMS.ID=131
            group by PROPERTY||' "'||NAME||'"', LOCATION||', '||HOUSE, AGR_NUM
            */

            /*
            oRptData.setModeData (isdb.miscs.dclrs.SQL_SELECT + "DIRNR, to_char(DIRNRS.STARTDATE, 'DD-MM-YYYY'), PROPERTY||' \"'||FIRMS.NAME||'\"', TYPELOCATION||' '||STREETS.NAME, AGR_NUM" +
                        isdb.miscs.dclrs.SQL_FROM + "AGREEMENTS, TECHREFS, FIRMS, PROPERTIES, DIRNRS, STREETS, TYPELOCATIONS
                        isdb.miscs.dclrs.SQL_WHERE + "PROPERTIES.ID = PROPERTY_ID" + isdb.miscs.dclrs.SQL_AND +
                        "FIRMS.ID = FIRM_ID" + isdb.miscs.dclrs.SQL_AND +
                        "TECHREFS.ID = DIRNRS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                        "TECHREFS.ID = AGREEMENTS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
            "STREETS.ID = DIRNRS.STREET_ID" + isdb.miscs.dclrs.SQL_AND +
            "TYPELOCATIONS.ID = TYPELOCATION_ID" +
            	isdb.miscs.dclrs.SQL_ORDERBY + "DIRNR");
                        oRptData.createColumn ("Телефон", 15);
                        oRptData.createColumn ("Дата підключення", 15);
                        oRptData.createColumn ("Абонент", 25);
                        oRptData.createColumn ("Фізич. розміщення", 30);
                        oRptData.createColumn ("N угоди", 15);
                        strReport = getItems (oDBData, oRptData).getReport ();
            */
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

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            objfirm oPropertyOfFirm = new objfirm ();
            dbdata oPropertyOfFirmDBData = new dbdata (oDBData.getSession ());
            oPropertyOfFirmDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oPropertyOfFirmDBData.setVal (isdb.miscs.dclrs.PAR_NEXTOBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oPropertyOfFirmDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oPropertyOfFirm.describe (oPropertyOfFirmDBData);

            graphi oGraphPropertyOfFirm = new graphi (oDBData);
            oGraphPropertyOfFirm.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            hashVals = dbi.getListObj (oDBData.getSession (),
                                       hashVals,
                                       isdb.miscs.dclrs.SQL_SELECT + isdb.miscs.dclrs.SQL_DISTINCT + "PROPERTIES.ID, PROPERTY" + isdb.miscs.dclrs.SQL_FROM + "FIRMS, PROPERTIES" +
                                       isdb.miscs.dclrs.SQL_WHERE + "PROPERTIES.ID = PROPERTY_ID" +
                                       isdb.miscs.dclrs.SQL_ORDERBY + "PROPERTY");
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oGraphPropertyOfFirm.addPieChartElement ((String) hashVals.get (strKey),
                        oPropertyOfFirm.countItems (oPropertyOfFirmDBData, "PROPERTY_ID=" + strKey),
                        oPropertyOfFirm.setURLObj (oPropertyOfFirmDBData,
                                                   "PROPERTY_ID=" + strKey,
                                                   isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                                   isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE));
            }
            return oGraphPropertyOfFirm.getApplet ();
        }
        return null;
    }

    /**
     * Повернення назви ссилочного об'екта.
     * @param strNameRefKey
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals (COL_PROPERTY_ID))
            return isdb.miscs.dclrs.TBL_PROPERTY;
        if (strNameRefKey.equals (COL_ACTIVITY_ID))
            return isdb.miscs.dclrs.TBL_ACTIVITY;
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Повернення різних можливостей вибірки даних об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return типи можливостей вибірки даних
     */
    public String getTypeSelection (dbdata oDBData, outdata oOutData)
    {
        String strAddFld = null;
        if (isCreateable (oDBData) &&         // чи існує можливість створити новий запис?
                oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTOBJECT) &&
                oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT).equals (isdb.miscs.dclrs.OBJ_TECHREF) &&
                oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTREGIME) &&
                oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME).equals (isdb.miscs.dclrs.REGIME_INSERT))
            strAddFld = isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.formradiopar (isdb.miscs.dclrs.PAR_TYPESELECT,
                                                        isdb.miscs.dclrs.PAR_ABSENT_IN_LIST + getName ()) +
                        isdb.ifaces.htmli.subtitle ("створити нового абонента");
        return super.getTypeSelection (oDBData, oOutData, strAddFld);
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
            return "Не вибрана потрібна фірма(и)!";
        return super.getMsg (iNumberMsg, oDBData);
    }

    /**
     * Перевірка можливісті на створення нового запису об'екта.
     * @param strKey - назва поля
     * @return true - потрібно створити CHECHBOX на створення нового запису.
     */
    public boolean isPossibleCreateNewRecord (String strKey)
    {
        ///
        Exception e66 = new Exception ("isPossibleCreateNewRecord: " + strKey);
        e66.printStackTrace ();
        ///

        if (strKey.equals (COL_BANK_ID) ||
                strKey.equals (COL_STREET_ID) ||
                strKey.equals (COL_ZIP_ID))
            return true;
        else
            return false;
    }
}

