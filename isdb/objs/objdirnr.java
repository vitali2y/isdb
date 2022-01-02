/**
 * objdirnr.java
 * ISDBj
 */

package isdb.objs;

import java.util.Enumeration;
import isdb.ifaces.graphi;

import isdb.objs.*;
import isdb.datas.*;

/**
 * Об'ект таблиці DIRNRS.
 * @version 1.0 final, 26-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objdirnr extends isdbobj
{
    // Поля таблиці DIRNRS
    public static final String COL_DIRNR = "DIRNR";
    public static final String COL_TARIFFLEVEL_ID = "TARIFFLEVEL_ID";
    public static final String COL_STREET_ID = "STREET_ID";
    public static final String COL_HOUSE = "HOUSE";
    public static final String COL_ADDLOCATION = "ADDLOCATION";
    public static final String COL_TECHREF_ID = "TECHREF_ID";
    public static final String COL_PHONESTATE_ID = "PHONESTATE_ID";
    public static final String COL_TYPEPHONE_ID = "TYPEPHONE_ID";
    public static final String COL_NORDER = "NORDER";
    public static final String COL_NORDERDATE = "NORDERDATE";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_ANICATEGORY_ID = "ANICATEGORY_ID";
    public static final String COL_TYPEAGREEMENT_ID = "TYPEAGREEMENT_ID";
    public static final String COL_STATEDATE = "STATEDATE";
    public static final String COL_PERSON_ID = "PERSON_ID";

    private String strAteTrunkLineFlag = isdb.miscs.dclrs.NO;
    private String strDistribFlag = isdb.miscs.dclrs.NO;
    private String strShelfTrunkLineFlag = isdb.miscs.dclrs.NO;
    private String strAteCrossFlag = isdb.miscs.dclrs.NO;
    private String strSwitch = "";

    /**
     * Конструктор
     */
    public objdirnr ()
    {
        super (isdb.miscs.dclrs.TBL_DIRNR);
        strAteTrunkLineFlag = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_ATETRUNKLINE);
        strDistribFlag = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_DISTRIB);
        strShelfTrunkLineFlag = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_SHELFTRUNKLINE);
        strAteCrossFlag = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.PRAGMA_CROSSMAP_ATECROSSLINE);
        strSwitch = isdb.ifaces.cfgi.getSwitch ();
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        String strTitle = null;
        if (oDBData.isPresent (isdb.miscs.dclrs.PAR_RPT_ID))
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_CONN_DIRNR_YEAR))
                return "Статистика підключень телефонних ліній за рік";
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_FAIL_RPT_YEAR))
                return "Статистика пошкодженних телефонних ліній за рік";
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_DIRNR_UNASSIGN_CAPACITY))
                return "Статистика по невикористованої емності";
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_DIRNR_ANI))
                return "Статистика по телефонам по категорії АВН";
            // Тарификаційни пакети
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID).equals (isdb.miscs.dclrs.GRAPH_AGREEMENT_TARIFF_PACKET))
                return "Діаграма розподілу телефонів по тарификаційним пакетам";
        }
        if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTOBJECT))
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT).equals (isdb.miscs.dclrs.OBJ_FAILRPT))
                return "Занесення телефонів в список пошкоджених";
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT).equals (isdb.miscs.dclrs.OBJ_SUBSSERVICE))
                return "Встановлення додаткового сервісу для телефонів";
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT).equals (isdb.miscs.dclrs.OBJ_BLDIRNR))
                return "Занесення телефонів в чорний список";
        }
        /*
                if ((String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_TYPESELECT)) != "null") &&
                (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null"))
                {
                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_TYPESELECT).equals (isdb.miscs.dclrs.PROPERTY_LIST))
        {
                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_ANSWER))
        strTitle = "Очікування відповіді на можливість встановлення телефона";
                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_AGREEMENT))
        strTitle = "Укладання угод";
                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNALLOWED))
        strTitle = "Встановлення дозволу на включення нового телефона";
                        if (String.valueOf (strTitle) != "null")
                        return "Телефон(и) у стані \"" + strTitle + "\"";
    }
                        }
        */
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
        /*
                String strTypeAgr = oDBData.getVal (COL_TYPEAGREEMENT_ID);
                if (String.valueOf (strTypeAgr) != "null")
                {
                    if (!strTypeAgr.equals (isdb.miscs.dclrs.OBJ_NULL)) // обран пакет: друкувати?
                    {
                        buttondata oAgrByTypeButton = new buttondata ();
                        dbdata oAgrByTypeButtonDBData = new dbdata (oDBData.getSession ());
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.REPORT_AGREEMENT_BY_TYPE);
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                        oAgrByTypeButtonDBData.setVal (isdb.miscs.dclrs.PAR_ID, strTypeAgr);
                        oAgrByTypeButton.setUrl ("isdbreport?" + oAgrByTypeButtonDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
                        oAgrByTypeButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
                        oDBData.setButton (oAgrByTypeButton.getButton (oOutData));
                    }
                }
        */
        if ((String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null") &&
                (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).indexOf ("PHONESTATE_ID") > -1))      // стани встановлення телефонів?
        {
            oSQLData.setColumn ("DIRNRS.ID,'Заява '||TECHREF||'. '||PROPERTY||' \"'||NAME||'\", від '||to_char(DIRNRS.STATEDATE, 'DD-MM-YY')||', '||LOCATION||', '||DIRNRS.HOUSE");
            oSQLData.setFrom ("DIRNRS,TECHREFS,PROPERTIES,FIRMS,LOCATIONS");
            oSQLData.setWhere ("TECHREFS.ID=TECHREF_ID");
            oSQLData.setWhere ("PROPERTIES.ID=PROPERTY_ID");
            oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
            oSQLData.setWhere ("LOCATIONS.ID=DIRNRS.STREET_ID");
            oSQLData.setOrder ("DIRNRS.STATEDATE", true);
        }
        else
        {
            oSQLData.setColumn ("ID,DIRNR");
            oSQLData.setFrom ("DIRNRS");
            oSQLData.setWhere ("DIRNR>99999");
            oSQLData.setOrder ("DIRNR");
        }
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
        return false;
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
        oJSData.setJS (COL_DIRNR, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
        oOutData.setJSData (oJSData);
        return super.field (COL_DIRNR, oDBData, oOutData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_PHONESTATE_ID);
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            {
                setHideColumn (COL_TECHREF_ID);
                setHideColumn (COL_TYPEAGREEMENT_ID);
            }
            return super.fields (oDBData, oOutData);
        }

        String strDirNr = "";
        String strFirm = "";
        String strTariffLevel = "";
        String strPhoneState = "";
        String strTechRef = "";
        String strLocation = "";
        String strHouse = "";
        String strAddLocation = "";
        String strTypePhone = "";
        String strNorder = "";
        String strNorderDate = "";
        String strStartDate = "";
        String strAniCategory = "";
        String strTypeAgreement = "";
        String strRemarks = "";
        String strDecision = "";
        String strOption = "";

        String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
        String strSesnId = oDBData.getSession ();

        // додаткові об'екти
        objpropertie oProperty = new objpropertie ();
        objfirm oFirm = new objfirm ();
        objtarifflevel oTariffLevel = new objtarifflevel ();
        objphonestate oPhoneState = new objphonestate ();
        objlocation oLocation = new objlocation ();
        objtypephone oTypePhone = new objtypephone ();
        objanicategorie oAniCategory = new objanicategorie ();
        objtechref oTechRef = new objtechref ();
        objtypeagreement oTypeAgreement = new objtypeagreement ();

        // ... та їх дані
        dbdata oPropertyDBData = new dbdata (strSesnId);
        dbdata oFirmDBData = new dbdata (strSesnId);
        dbdata oTariffLevelDBData = new dbdata (strSesnId);
        dbdata oPhoneStateDBData = new dbdata (strSesnId);
        dbdata oLocationDBData = new dbdata (strSesnId);
        dbdata oTypePhoneDBData = new dbdata (strSesnId);
        dbdata oAniCategoryDBData = new dbdata (strSesnId);
        dbdata oTechRefDBData = new dbdata (strSesnId);
        dbdata oTypeAgreementDBData = new dbdata (strSesnId);

        oTariffLevelDBData.setVal (COL_ID, retrieve (COL_TARIFFLEVEL_ID, oDBData));
        oTariffLevel.select (oTariffLevelDBData);
        oLocationDBData.setVal (COL_ID, retrieve (COL_STREET_ID, oDBData));
        oLocation.select (oLocationDBData);
        oTypePhoneDBData.setVal (COL_ID, retrieve (COL_TYPEPHONE_ID, oDBData));
        oTypePhone.select (oTypePhoneDBData);
        oTechRefDBData.setVal (COL_ID, retrieve (COL_TECHREF_ID, oDBData));
        oTechRef.select (oTechRefDBData);
        oFirmDBData.setVal (COL_ID, retrieve (objtechref.COL_FIRM_ID, oTechRefDBData));
        oFirm.select (oFirmDBData);
        oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
        oProperty.select (oPropertyDBData);
        oAniCategoryDBData.setVal (COL_ID, retrieve (COL_ANICATEGORY_ID, oDBData));
        oAniCategory.select (oAniCategoryDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
        {
            oTypeAgreementDBData.setVal (COL_ID, retrieve (COL_TYPEAGREEMENT_ID, oDBData));
            oTypeAgreement.select (oTypeAgreementDBData);
        }

        // номер телефона
        strDirNr = retrieve (COL_DIRNR, oDBData);
        if (String.valueOf (strDirNr) != "null")
        {
            if (strDirNr.length () < 6)              // ще не задіян?
                strDirNr = desc (COL_DIRNR) +
                           isdb.ifaces.htmli.crlf () +
                           isdb.ifaces.htmli.value (isdb.miscs.dclrs.NOTINSTALL);
            else
                strDirNr = value (COL_DIRNR, oDBData);
        }
        else strDirNr = desc (COL_DIRNR) +
                        isdb.ifaces.htmli.crlf () +
                        isdb.ifaces.htmli.value (isdb.miscs.dclrs.NOTINSTALL);
        strNorder = value (COL_NORDER, oDBData);
        strNorderDate = value (COL_NORDERDATE, oDBData);
        strTariffLevel = oTariffLevel.value (objtarifflevel.COL_TARIFFLEVEL, oTariffLevelDBData);
        strTypePhone = oTypePhone.value (objtypephone.COL_TYPEPHONE, oTypePhoneDBData);
        strLocation = oLocation.value (objlocation.COL_LOCATION, oLocationDBData);
        strHouse = value (COL_HOUSE, oDBData);
        strAddLocation = value (COL_ADDLOCATION, oDBData);
        oPhoneStateDBData.setVal (COL_ID, retrieve (COL_PHONESTATE_ID, oDBData));
        oPhoneState.select (oPhoneStateDBData);
        strPhoneState = oPhoneState.value (objphonestate.COL_STATE, oPhoneStateDBData);
        strStartDate = value (COL_STARTDATE, oDBData);
        strAniCategory = oAniCategory.value (objanicategorie.COL_ANI, oAniCategoryDBData);
        if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
        {
            strTypeAgreement = oTypeAgreement.value (objtypeagreement.COL_TYPE, oTypeAgreementDBData);
        }
        oDBData.removeVal (COL_PHONESTATE_ID);

        // оформлення нової заяви?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            // oTechRefDBData.setVal (objtechref.COL_TECHREF, retrieve (isdb.miscs.dclrs.PAR_TRANSFER_PARAM, oDBData));
            // oTechRef.id (oTechRefDBData);
            // oTechRef.select (oTechRefDBData);

            oTechRefDBData.setVal (COL_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oTechRef.select (oTechRefDBData);
            oOutData.setHideFld (COL_TECHREF_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oFirmDBData.setVal (COL_ID, retrieve (objtechref.COL_FIRM_ID, oTechRefDBData));
            oFirm.select (oFirmDBData);
            oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
            oProperty.select (oPropertyDBData);

            oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REGISTER);
            oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "nvl (count (DIRNR)+1, 0)" + isdb.miscs.dclrs.SQL_FROM + "DIRNRS" +
                                isdb.miscs.dclrs.SQL_WHERE + "DIRNR < 10000");
            oPhoneStateDBData.setVal (COL_ID, "@TR");
            oPhoneState.select (oPhoneStateDBData);
            strPhoneState = oPhoneState.value (objphonestate.COL_STATE, oPhoneStateDBData);
            oOutData.setHideFld (COL_DIRNR, getItems (oDBData));
            oOutData.setHideFld (COL_TECHREF_ID, isdb.miscs.dclrs.OBJ_NULL);
            oOutData.setHideFld (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REGISTER);
            oOutData.setHideFld (COL_TYPEAGREEMENT_ID, "1");

            oTariffLevelDBData.setVal (COL_ID, "S");         // преінсталяція значення
            oTariffLevelDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            strTariffLevel = oTariffLevel.list (COL_TARIFFLEVEL_ID, oTariffLevelDBData, oOutData);
            oTypePhoneDBData.setVal (COL_ID, "P");         // преінсталяція значення
            oTypePhoneDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            strTypePhone = oTypePhone.list (COL_TYPEPHONE_ID, oTypePhoneDBData, oOutData);
            oLocationDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            strLocation = oLocation.list (COL_STREET_ID, oLocationDBData, oOutData);
            strHouse = field (COL_HOUSE, oDBData, oOutData);
            strAddLocation = textareafield (COL_ADDLOCATION, oDBData, oOutData);
            oAniCategoryDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
            oAniCategoryDBData.setVal (COL_ID, "4");         // преінсталяція значення
            strAniCategory = oAniCategory.list (COL_ANICATEGORY_ID, oAniCategoryDBData, oOutData);
            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            {
                oTypeAgreementDBData.setVal (COL_ID, "1");
                oTypeAgreementDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                oTypeAgreement.select (oTypeAgreementDBData);
                strTypeAgreement = oTypeAgreement.value (objtypeagreement.COL_TYPE, oTypeAgreementDBData);
            }
            strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
        }
        else
        {
            // додаткові кнопки навігації
            if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
            {
                buttondata oTechRefButton = new buttondata ();
                oTechRefButton.setName ("Заява/тех. довідка");
                oTechRefButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_TECHREF);
                oTechRefButton.setPar (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_TECHREF_ID));
                oTechRefButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                oTechRefButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                oTechRefButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
                oDBData.setButton (oTechRefButton.getButton (oOutData));
            }
            buttondata oFirmButton = new buttondata ();
            oFirmButton.setName ("Абонент");
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oTechRefDBData.getVal (objtechref.COL_FIRM_ID));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oFirmButton.getButton (oOutData));
            // вибірка інформації?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                strRemarks = value (COL_REMARKS, oDBData);
            else   // режим: REGIME_UPDATE?
            {
                // спеціфічний режим оновлення під час встановлення телефона?
                if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null")
                {
                    strRemarks = textareafield (COL_REMARKS, oDBData, oOutData);
                    // укладання угоди?
                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_AGREEMENT))
                        oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_AGREEMENT);
                    else
                    {
                        // очікування відповіді/наряда?
                        if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_ANSWER))
                        {
                            strNorder = field (COL_NORDER, oDBData, oOutData);
                            strNorderDate = field (COL_NORDERDATE, oDBData, oOutData);
                            strStartDate = "";
                            oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_ANSWER);
                            // додаткові можливості
                            if (strAteTrunkLineFlag.equals (isdb.miscs.dclrs.YES))   // використовуеться режим?
                                strOption += isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_ATETRUNKLINE, "ввести (міжстанц.) з'еднув. лінії");
                            if (strAteCrossFlag.equals (isdb.miscs.dclrs.YES))   // використовуеться режим?
                                strOption += isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_ATECROSSPAIR, "ввести кросіровки на віддален. АТС");
                            if (strShelfTrunkLineFlag.equals (isdb.miscs.dclrs.YES)) // використовуеться режим?
                                strOption += isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_SHELFTRUNKLINE, "ввести шафни кросіровки");
                            if (strDistribFlag.equals (isdb.miscs.dclrs.YES))        // використовуеться режим?
                                strOption += isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_DISTRIB, "ввести розподілення");
                            strOption = isdb.ifaces.htmli.paragraph ("Введення додаткових даних",
                                        isdb.ifaces.htmli.cell (strOption, 100));

                            // додаткові об'екти
                            dbdata oNewDirNrDBData = new dbdata (strSesnId);
                            oNewDirNrDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "ID, DIRNR" + isdb.miscs.dclrs.SQL_FROM + "DIRNRS" +
                                                        isdb.miscs.dclrs.SQL_WHERE + "DIRNR > 10000" +
                                                        isdb.miscs.dclrs.SQL_AND + isdb.miscs.dclrs.STAGE_CURR_REGISTER);
                            oNewDirNrDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));

                            // рішення про можливість чі неможливість встановлення
                            objphonestate oDecision = new objphonestate ();
                            dbdata oDecisionDBData = new dbdata (strSesnId);
                            oDecisionDBData.setCriteriaObj ("ID like '#%'");
                            oDecisionDBData.setVal (isdb.miscs.dclrs.PAR_REGIME, oDBData.getVal (isdb.miscs.dclrs.PAR_REGIME));
                            oDecision.setTitle ("Неможливо по причині:");
                            strDecision = isdb.ifaces.htmli.paragraph ("Прийняття рішення про можливість встановлення телефона",
                                          isdb.ifaces.htmli.cell (
                                            isdb.ifaces.htmli.unorderedlist (
                                              isdb.ifaces.htmli.listitem (
                                                isdb.ifaces.htmli.formcheckboxpar (
                                                  COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_ORDER, list (COL_ID, oNewDirNrDBData, oOutData) + " можливо встановити" ,true)) +
                                              isdb.ifaces.htmli.listitem (
                                                isdb.ifaces.htmli.formcheckboxpar (
                                                  COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REJECT, "Абонент відмовився від встановлення телефона")) +
                                              isdb.ifaces.htmli.listitem (
                                                oDecision.list (COL_PHONESTATE_ID, oDecisionDBData, oOutData))
                                            )
                                            , 100));
                        }
                        else
                        {
                            // підключення в відділі техничн. обслуговування
                            if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNINPROGRESS))
                            {
                                oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNINPROGRESS);
                                strDirNr += isdb.ifaces.htmli.crlf () +
                                            isdb.ifaces.htmli.getCheckBoxPrintRpt (isdb.miscs.dclrs.OBJ_DIRNR, isdb.miscs.dclrs.PAPER_INFO_CONN_LINE) +
                                            isdb.ifaces.htmli.crlf () +
                                            isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_LEN, "ввести новий cтанційний порт") +
                                            isdb.ifaces.htmli.crlf () +
                                            isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_EXCHMDF, "ввести новий cтанційний MDF") +
                                            isdb.ifaces.htmli.crlf () +
                                            isdb.ifaces.htmli.getCheckBoxAbsentInList (isdb.miscs.dclrs.OBJ_LINEMDF, "ввести новий лінійний MDF");
                                strDecision = isdb.ifaces.htmli.paragraph ("Встановлення ознаки кроссування та введення в станційну БД телефона",
                                              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.formcheckboxpar (
                                                                        COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNINPROGRESS, "Телефон скросован та внесен в станційну БД"), 100));
                            }
                            else
                            {
                                // додаткові кнопки навігації
                                if (!oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT).equals (isdb.miscs.dclrs.DEPT_MAINT))        // відділ техн. обслуг-ня?
                                {
                                    objagreement oAgreement = new objagreement ();
                                    dbdata oAgreementDBData = new dbdata (strSesnId);
                                    oAgreementDBData.setVal (objagreement.COL_TECHREF_ID, retrieve (COL_ID, oTechRefDBData));
                                    oAgreementDBData = oAgreement.id (oAgreementDBData);
                                    buttondata oAgreementButton = new buttondata ();
                                    oAgreementButton.setName ("Угода");
                                    oAgreementButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_AGREEMENT);
                                    oAgreementButton.setPar (isdb.miscs.dclrs.PAR_ID, oAgreementDBData.getVal (COL_ID));
                                    oAgreementButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                                    oAgreementButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
                                    oAgreementButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
                                    oDBData.setButton (oAgreementButton.getButton (oOutData));
                                }
                                // встановлення дозволу на підключення телефона
                                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNALLOWED))
                                {
                                    // oTypeAgreementDBData.setVal (COL_ID, retrieve (COL_TYPEAGREEMENT_ID, oTypeAgreementDBData));
                                    oTypeAgreementDBData.setVal (COL_ID, "1");
                                    // oTypeAgreement.select (oTypeAgreementDBData);
                                    strTypeAgreement = oTypeAgreement.list (COL_TYPEAGREEMENT_ID, oTypeAgreementDBData, oOutData);
                                    oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNALLOWED);
                                    strDecision = isdb.ifaces.htmli.paragraph ("Встановлення дозволу на підключення телефона",
                                                  isdb.ifaces.htmli.cell (isdb.ifaces.htmli.formcheckboxpar (
                                                                            COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNALLOWED, "Підтвердження дозволу на підключення телефона"), 100));
                                }
                                else
                                {
                                    // кінцеве підключення телефона в відділі техничн. обслуговування
                                    if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNECTED))
                                    {
                                        jsdata oJSData = new jsdata ();
                                        oJSData.setJS (COL_STARTDATE, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
                                        oOutData.setJSData (oJSData);
                                        strStartDate = field (COL_STARTDATE, isdb.ifaces.dbi.dbdate (oDBData), 14, oOutData);
                                        oDBData.setVal (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNECTED);
                                        strDecision = isdb.ifaces.htmli.paragraph ("Підключення телефона",
                                                      isdb.ifaces.htmli.cell (isdb.ifaces.htmli.formcheckboxpar (
                                                                                COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_CONNECTED, "Відмітка про підключення телефона"), 100));
                                    }
                                    else
                                    {
                                        // телефон неможливо встановити
                                        if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals ("PHONESTATE_ID like '#%'"))
                                        {
                                            strDecision = isdb.ifaces.htmli.paragraph ("Рішення про подальше підключення телефона",
                                                          isdb.ifaces.htmli.cell (
                                                            isdb.ifaces.htmli.div (
                                                              isdb.ifaces.htmli.formradiopar (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REJECT) +
                                                              " відмова від подальшого підключення телефона" +
                                                              isdb.ifaces.htmli.crlf () +
                                                              isdb.ifaces.htmli.formradiopar (COL_PHONESTATE_ID, isdb.miscs.dclrs.STAGE_NEXT_REGISTER, true) +
                                                              " повторне звернення щодо техничної можливості", "left"), 100));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (String.valueOf (retrieve (COL_PHONESTATE_ID, oDBData)) != "null")
                    {
                        oPhoneStateDBData.setVal (COL_ID, retrieve (COL_PHONESTATE_ID, oDBData));
                        oPhoneState.select (oPhoneStateDBData);
                        strPhoneState = oPhoneState.value (objphonestate.COL_STATE, oPhoneStateDBData);
                    }
                }
            }
        }
        strFirm = oFirm.desc (objfirm.COL_NAME) +
                  isdb.ifaces.htmli.crlf ();
        if (String.valueOf (oFirm.retrieve (objfirm.COL_NAME, oFirmDBData)) == "null")
            strFirm += isdb.ifaces.htmli.value (isdb.miscs.dclrs.OBJ_NULL);
        else
            strFirm += isdb.ifaces.htmli.value (oProperty.retrieve (objpropertie.COL_PROPERTY, oPropertyDBData) + " \"" +
                                                oFirm.retrieve (objfirm.COL_NAME, oFirmDBData) + "\"");
        strTechRef = oTechRef.value (objtechref.COL_TECHREF, oTechRefDBData);

        // приготування сторінки
        // перший ряд
        return isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (strDirNr, 33) +
                                            isdb.ifaces.htmli.cell (strFirm, 33) +
                                            isdb.ifaces.htmli.cell (strPhoneState +
                                                                    isdb.ifaces.htmli.crlf () +
                                                                    strStartDate, 33)) +

               // рішення користувача
               strDecision +

               // додаткові можливості (ввести кросіровки на віддален. АТС, тощо)
               strOption +

               // другий ряд
               isdb.ifaces.htmli.paragraph ("Загальна інформація",
                                            isdb.ifaces.htmli.cell (strTariffLevel, 20) +
                                            isdb.ifaces.htmli.cell (strTypePhone, 20) +
                                            isdb.ifaces.htmli.cell (strTechRef, 20) +
                                            isdb.ifaces.htmli.cell (strNorder, 20) +
                                            isdb.ifaces.htmli.cell (strNorderDate, 20)) +

               // третій ряд
               isdb.ifaces.htmli.paragraph ("Фізичне розміщення",
                                            isdb.ifaces.htmli.cell (strLocation, 40) +
                                            isdb.ifaces.htmli.cell (strHouse, 20) +
                                            isdb.ifaces.htmli.cell (strAddLocation, 40)) +

               // четвертий ряд
               isdb.ifaces.htmli.paragraph (isdb.ifaces.htmli.cell (strAniCategory, 30) +
                                            isdb.ifaces.htmli.cell (strTypeAgreement, 30) +
                                            isdb.ifaces.htmli.cell (strRemarks, 40)) +

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

        ///
        // Exception e66 = new Exception ("report: " + oDBData.toString ());
        // e66.printStackTrace ();
        ///

        // Звіт по телефонам, угоди з якими укладени на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_DIRNR_RPT_CURRENT))
        {
            strRptTitle = getTitle () + " на " + isdb.ifaces.dbi.dbdate (oDBData, true);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";

            /*
            select DIRNR, AGR_NUM, AGREEMENTS.REMARKS
            from AGREEMENTS, TECHREFS, DIRNRS
            where TECHREFS.ID = DIRNRS.TECHREF_ID
            and TECHREFS.ID = AGREEMENTS.TECHREF_ID
            and AGREEMENTS.REMARKS is null
            order by DIRNR

            select DIRNR, AGR_NUM, AGREEMENTS.REMARKS
            from AGREEMENTS, TECHREFS, DIRNRS
            where TECHREFS.ID = DIRNRS.TECHREF_ID
            and TECHREFS.ID = AGREEMENTS.TECHREF_ID
            and TYPEAGREEMENT_ID not in (3,4)
            order by DIRNR
            */

            oRptData.setModeData (isdb.miscs.dclrs.SQL_SELECT + "DIRNR, nvl(to_char(DIRNRS.STARTDATE, 'DD-MM-YYYY'), '-'), PROPERTY||' \"'||FIRMS.NAME||'\"', LOCATION||', '||DIRNRS.HOUSE, AGR_NUM" +
                                  isdb.miscs.dclrs.SQL_FROM + "AGREEMENTS, TECHREFS, FIRMS, PROPERTIES, DIRNRS, LOCATIONS" +
                                  isdb.miscs.dclrs.SQL_WHERE + "PROPERTIES.ID = PROPERTY_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "FIRMS.ID = FIRM_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "TECHREFS.ID = DIRNRS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "TECHREFS.ID = AGREEMENTS.TECHREF_ID" + isdb.miscs.dclrs.SQL_AND +
                                  "LOCATIONS.ID = DIRNRS.STREET_ID" +
                                  isdb.miscs.dclrs.SQL_ORDERBY + "DIRNR");
            oRptData.createColumn ("Телефон", 15);
            oRptData.createColumn ("Дата підключення", 15);
            oRptData.createColumn ("Абонент", 25);
            oRptData.createColumn ("Фізич. розміщення", 30);
            oRptData.createColumn ("N угоди", 15);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        // наряд на включення бізнес-лінії посля кросування?
        if (strRptId.equals (isdb.miscs.dclrs.PAPER_INFO_CONN_LINE))
        {
            String strId = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
            oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "PROPERTY||' \"'||NAME||'\"'" +
                                isdb.miscs.dclrs.SQL_FROM + "PROPERTIES, DIRNRS, FIRMS, TECHREFS" +
                                isdb.miscs.dclrs.SQL_WHERE + "TECHREFS.ID = TECHREF_ID and" +
                                " PROPERTIES.ID = PROPERTY_ID and" +
                                " TECHREFS.ID = TECHREF_ID and" +
                                " FIRMS.ID = FIRM_ID and" +
                                " DIRNRS.ID = " + strId);
            String strFirm = getItems (oDBData);
            oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "LOCATION||' '||', '||FIRMS.HOUSE" +
                                " from DIRNRS, FIRMS, TECHREFS, LOCATIONS" +
                                " where TECHREFS.ID = TECHREF_ID and" +
                                " LOCATIONS.ID = FIRMS.STREET_ID and" +
                                " TECHREFS.ID = TECHREF_ID and" +
                                " FIRMS.ID = FIRM_ID and" +
                                " DIRNRS.ID = " + strId);
            String strAddressFirm = getItems (oDBData);
            oDBData.setModeObj ("select LOCATION||' '||', '||DIRNRS.HOUSE" +
                                " from DIRNRS, FIRMS, TECHREFS, LOCATIONS" +
                                " where TECHREFS.ID = TECHREF_ID and" +
                                " LOCATIONS.ID = DIRNRS.STREET_ID and" +
                                " TECHREFS.ID = TECHREF_ID and" +
                                " FIRMS.ID = FIRM_ID and" +
                                " DIRNRS.ID = " + strId);
            String strAddressPhone = getItems (oDBData);
            oDBData.setModeObj ("select DIRNR||' ('||LOCATION||', '||HOUSE||')' from DIRNRS, LOCATIONS" +
                                " where LOCATIONS.ID = STREET_ID and" +
                                " DIRNRS.ID = " + strId);
            String strDirNr = getItems (oDBData);
            if (strSwitch.equals (isdb.miscs.dclrs.OPTPAR_EWSD))      // яка використовуеться станція?
                oDBData.setModeObj ("select TRUNKPAIR from TRUNKCROSSTRACES" +
                                    " where ID = " + strId);
            else
                oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "CROSS"+ isdb.miscs.dclrs.SQL_FROM + "ATECROSSTRACES" +
                                    isdb.miscs.dclrs.SQL_WHERE + "ID=" + strId);
            String strTrunk = getItems (oDBData);
            oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + "to_char(STATEDATE, 'DD-MM-YY')" +
                                isdb.miscs.dclrs.SQL_FROM + "DIRNRS" +
                                isdb.miscs.dclrs.SQL_WHERE + "ID=" + strId);
            String strCrossDate = getItems (oDBData);
            objperson oPerson = new objperson ();
            dbdata oPersonDBData = new dbdata (oDBData.getSession ());
            oPersonDBData.setCriteriaObj ("USERNAME='" + isdb.ifaces.dbi.user (oDBData.getSession ()) + "'");
            oPerson.select (oPersonDBData);
            String strTechnician = oPerson.retrieve (objperson.COL_PERSON, oPersonDBData);
            strReport =
              isdb.ifaces.htmli.crlf (5) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Наряд на включення бізнес-лінії", "800080") +
                                              isdb.ifaces.htmli.crlf (7), 100)
                    ), 0)
                  , 100)) +

              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Назва абонента", 40) +
                      isdb.ifaces.htmli.cell (strFirm, 60)
                    ), 1, false)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Юридична адреса абонента", 40) +
                      isdb.ifaces.htmli.cell (strAddressFirm, 60)
                    ), 1, false)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Адреса, на яку мають бути надані послуги", 40) +
                      isdb.ifaces.htmli.cell (strAddressPhone, 60)
                    ), 1, false)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Номер бізнес-лінії, фізичне розміщення", 40) +
                      isdb.ifaces.htmli.cell (strDirNr, 60)
                    ), 1)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Номер з'еднувальної лінії", 40) +
                      isdb.ifaces.htmli.cell (strTrunk, 60)
                    ), 1)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Скросував. П.І.П.", 40) +
                      isdb.ifaces.htmli.cell (strTechnician, 60)
                    ), 1)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Дата кросування", 40) +
                      isdb.ifaces.htmli.cell (strCrossDate, 60)
                    ), 1)
                  , 100)) +

              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Лінію перевірив. П.І.П.", 40) +
                      isdb.ifaces.htmli.cell ("", 60)
                    ), 1)
                  , 100)) +
              isdb.ifaces.htmli.row (
                isdb.ifaces.htmli.cell (
                  isdb.ifaces.htmli.place (
                    isdb.ifaces.htmli.row (
                      isdb.ifaces.htmli.cell ("Дата перевірки та підпис", 40) +
                      isdb.ifaces.htmli.cell ("", 60)
                    ), 1)
                  , 100));
        }
        // Таблиця розподілу ліній
        if (strRptId.equals (isdb.miscs.dclrs.FORM_2_4_F2))
        {
            strRptTitle = "Таблиця розподілу ліній";
            strRptNumb = "2/4-Ф2";

            // перший ряд (заголовки)
            strReport =
              isdb.ifaces.htmli.font (
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("NN"), 5) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Найменування показників," + isdb.ifaces.htmli.crlf () + "одиниці виміру"), 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Монтована ємність"), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("Задіяна ємність"), 15)
                      ),
                      0),
                    100)) +

                // другий ряд (номери)
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("1"), 5) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("2"), 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("3"), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("4"), 15)
                      ),
                      0),
                    100)) +

                // третій ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("1", 5) +
                        isdb.ifaces.htmli.cell ("Кількість робочих місць операторів", 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value ("16"), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value ("14"), 15)
                      ),
                      0),
                    100)) +

                // четвертий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("2", 5) +
                        isdb.ifaces.htmli.cell ("Загальна ємність бізнес-АТС, абонентских ліній", 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData)), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "PHONESTATE_ID = '@CN' or" +
                                                              " PHONESTATE_ID = '@IN' or" +
                                                              " PHONESTATE_ID = '@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // п'ятий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("3", 5) +
                        isdb.ifaces.htmli.cell ("з неї:", 15) +
                        isdb.ifaces.htmli.cell ("передана в оперативний лізинг (аренду)", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='A'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='A' and" +
                                                              " PHONESTATE_ID='@CN' or" +
                                                              " PHONESTATE_ID='@IN' or" +
                                                              " PHONESTATE_ID='@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // шостий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("4", 5) +
                        isdb.ifaces.htmli.cell ("", 15) +
                        isdb.ifaces.htmli.cell ("ємність Утел", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='U' and" +
                                                              " TARIFFLEVEL_ID='D' and" +
                                                              " TARIFFLEVEL_ID='I' and" +
                                                              " TARIFFLEVEL_ID='S'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='U' and" +
                                                              " TARIFFLEVEL_ID='D' and" +
                                                              " TARIFFLEVEL_ID='I' and " +
                                                              " TARIFFLEVEL_ID='S' and" +
                                                              " PHONESTATE_ID='@CN' or" +
                                                              " PHONESTATE_ID='@IN' or" +
                                                              " PHONESTATE_ID='@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // сьомий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("5", 5) +
                        isdb.ifaces.htmli.cell ("з неї:", 15) +
                        isdb.ifaces.htmli.cell ("абонентів підвищеної якості (бізнес-абоненти)", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='S'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='S' and" +
                                                              " PHONESTATE_ID='@CN' or" +
                                                              " PHONESTATE_ID='@IN' or" +
                                                              " PHONESTATE_ID='@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // восьмий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("6", 5) +
                        isdb.ifaces.htmli.cell ("", 15) +
                        isdb.ifaces.htmli.cell ("службових телефонів", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='U'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='U' and" +
                                                              " PHONESTATE_ID='@CN' or" +
                                                              " PHONESTATE_ID='@IN' or" +
                                                              " PHONESTATE_ID='@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // дев'ятий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("7", 5) +
                        isdb.ifaces.htmli.cell ("", 15) +
                        isdb.ifaces.htmli.cell ("телефонів у готелях", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value ("0"), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value ("0"), 15)
                      ),
                      0),
                    100)) +

                // десятий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("8", 5) +
                        isdb.ifaces.htmli.cell ("", 15) +
                        isdb.ifaces.htmli.cell ("телефонів-автоматів (карткфони, дебітфони, т. і.)", 50) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='D'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='D' and" +
                                                              " PHONESTATE_ID='@CN' or" +
                                                              " PHONESTATE_ID='@IN' or" +
                                                              " PHONESTATE_ID='@AG' or" +
                                                              " PHONESTATE_ID like '!%'")), 15)
                      ),
                      0),
                    100)) +

                // одиннадцятий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("9", 5) +
                        isdb.ifaces.htmli.cell ("ISDN базового доступу (BRA)", 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='I'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='I'")), 15)
                      ),
                      0),
                    100)) +

                // дванадцятий ряд
                isdb.ifaces.htmli.row (
                  isdb.ifaces.htmli.cell (
                    isdb.ifaces.htmli.place (
                      isdb.ifaces.htmli.row (
                        isdb.ifaces.htmli.cell ("10", 5) +
                        isdb.ifaces.htmli.cell ("ISDN первинного доступу (PRA)", 65) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='I'")), 15) +
                        isdb.ifaces.htmli.cell (isdb.ifaces.htmli.value (
                                                  countItems (oDBData, "TARIFFLEVEL_ID='I'")), 15)
                      ),
                      0),
                    100)), 4);
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, "Форма " + strRptNumb);
        return oDBData;
    }

    /**
     * Приготування графіків
     * @param oDBData поточни дані об'екта
     * @return сформована HTML сторінка з аплетом для формування діаграми
     */
    public String graph (dbdata oDBData)
    {
        String strGraph = "";
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            graphi oPhonesByState = new graphi (oDBData);
            oPhonesByState.setPieChart ();
            oPhonesByState.addPieChartElement ("Бізнес-абоненти",
                                               countItems (oDBData, "TARIFFLEVEL_ID='S'"),
                                               setURLObj (oDBData, "TARIFFLEVEL_ID='S'"));
            oPhonesByState.addPieChartElement ("Арендни телефони",
                                               countItems (oDBData, "TARIFFLEVEL_ID='A'"),
                                               setURLObj (oDBData, "TARIFFLEVEL_ID='A'"));
            oPhonesByState.addPieChartElement ("Службові телефони",
                                               countItems (oDBData, "TARIFFLEVEL_ID='U'"),
                                               setURLObj (oDBData, "TARIFFLEVEL_ID='U'"));
            oPhonesByState.addPieChartElement ("Карткофони",
                                               countItems (oDBData, "TARIFFLEVEL_ID='D'"),
                                               setURLObj (oDBData, "TARIFFLEVEL_ID='D'"));
            strGraph = oPhonesByState.getApplet ();
        }
        else
        {
            // Статистика по невикористованої емності
            if (strRptId.equals (isdb.miscs.dclrs.GRAPH_DIRNR_UNASSIGN_CAPACITY))
            {
                graphi oPhonesByState = new graphi (oDBData);
                oPhonesByState.setPieChart ();
                oPhonesByState.addPieChartElement ("Використована емність",
                                                   countItems (oDBData, "PHONESTATE_ID<>'@UA'"),
                                                   setURLObj (oDBData, "PHONESTATE_ID<>'@UA'"));
                oPhonesByState.addPieChartElement ("Невикористована емність",
                                                   countItems (oDBData, "PHONESTATE_ID='@UA'"),
                                                   setURLObj (oDBData, "PHONESTATE_ID='@UA'"));
                strGraph = oPhonesByState.getApplet ();
            }
            else
            {

                // Тарификаційни пакети
                if (strRptId.equals (isdb.miscs.dclrs.GRAPH_AGREEMENT_TARIFF_PACKET))
                {
                    objdirnr oTypeOfTariff = new objdirnr ();
                    dbdata oTypeOfTariffDBData = new dbdata (oDBData.getSession ());
                    oTypeOfTariffDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
                    oTypeOfTariffDBData.setVal (isdb.miscs.dclrs.PAR_NEXTOBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
                    oTypeOfTariffDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                    oTypeOfTariff.describe (oTypeOfTariffDBData);

                    graphi oGraphTypeOfTariff = new graphi (oDBData);
                    oGraphTypeOfTariff.setPieChart ();
                    java.util.Hashtable hashVals = new java.util.Hashtable ();
                    sqldata oSQLData = new sqldata ();
                    oSQLData.setDistinct ();
                    oSQLData.setColumn ("TYPEAGREEMENTS.ID, TYPE");
                    oSQLData.setFrom ("TYPEAGREEMENTS,AGREEMENTS,TECHREFS,DIRNRS");
                    oSQLData.setWhere ("TECHREFS.ID=AGREEMENTS.TECHREF_ID");
                    oSQLData.setWhere ("TECHREFS.ID=DIRNRS.TECHREF_ID");
                    oSQLData.setWhere ("TYPEAGREEMENTS.ID=TYPEAGREEMENT_ID");
                    oSQLData.setOrder ("TYPE");
                    hashVals = isdb.ifaces.dbi.getListObj (oDBData.getSession (), hashVals, oSQLData.getData ());
                    java.util.Enumeration enumVals = hashVals.keys ();
                    while (enumVals.hasMoreElements ())
                    {
                        String strKey = (String) enumVals.nextElement ();
                        oGraphTypeOfTariff.addPieChartElement ((String) hashVals.get (strKey),
                                                               oTypeOfTariff.countItems (oTypeOfTariffDBData, "TYPEAGREEMENT_ID=" + strKey),
                                                               oTypeOfTariff.setURLObj (oTypeOfTariffDBData,
                                                                                        "TYPEAGREEMENT_ID=" + strKey,
                                                                                        isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                                                                        isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE));
                    }
                    return oGraphTypeOfTariff.getApplet ();
                }
                else
                {
                    // Телефони по АВН
                    if (strRptId.equals (isdb.miscs.dclrs.GRAPH_DIRNR_ANI))
                    {
                        objdirnr oANI = new objdirnr ();
                        dbdata oANIDBData = new dbdata (oDBData.getSession ());
                        oANIDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_DIRNR);
                        oANIDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
                        oANI.describe (oANIDBData);

                        graphi oGraphANI = new graphi (oDBData);
                        oGraphANI.setPieChart ();
                        java.util.Hashtable hashVals = new java.util.Hashtable ();
                        hashVals = isdb.ifaces.dbi.getListObj (oDBData.getSession (),
                                                               hashVals,
                                                               isdb.miscs.dclrs.SQL_SELECT + isdb.miscs.dclrs.SQL_DISTINCT + "ANICATEGORIES.ID, ANI" + isdb.miscs.dclrs.SQL_FROM + "DIRNRS, ANICATEGORIES" +
                                                               isdb.miscs.dclrs.SQL_WHERE + "ANICATEGORIES.ID = ANICATEGORY_ID" +
                                                               isdb.miscs.dclrs.SQL_ORDERBY + "ANI");
                        java.util.Enumeration enumVals = hashVals.keys ();
                        while (enumVals.hasMoreElements ())
                        {
                            String strKey = (String) enumVals.nextElement ();
                            oGraphANI.addPieChartElement ((String) hashVals.get (strKey),
                                                          oANI.countItems (oANIDBData, "ANICATEGORY_ID=" + strKey),
                                                          oANI.setURLObj (oANIDBData,
                                                                          "ANICATEGORY_ID=" + strKey,
                                                                          isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                                                          isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE));
                        }
                        strGraph = oGraphANI.getApplet ();
                    }
                    else
                    {
                        // Статистика підключень телефонних ліній за рік
                        if (strRptId.equals (isdb.miscs.dclrs.GRAPH_CONN_DIRNR_YEAR))
                        {
                            graphi oConnLines = new graphi (oDBData);
                            oConnLines.setGraphChart (13);

                            String [][] strYearValues = new String [13][2];
                            strYearValues = getYearCountItems (oDBData, "STARTDATE");
                            int iI=13;
                            while (--iI >= 0)
                            {
                                oConnLines.addGraphChartElement (strYearValues [iI][0],
                                                                 strYearValues [iI][1]);
                            }
                            strGraph = oConnLines.getApplet ();
                        }
                    }
                }
            }
        }
        return strGraph;
    }

    /**
     * Ініціалізація, змінення та повернення загальних параметрів об'екта
     * @param oDBData поточни дані об'екта
     * @return змінені дані об'екта
     */
    public dbdata exchParams (dbdata oDBData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTREGIME))
            {
                oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME));
                oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTREGIME);
            }
            else
            {
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
                    oDBData.setRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT);
                else
                    oDBData.setRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT);
            }
        }
        else
        {
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT))
            {
                if (oDBData.isPresent (isdb.miscs.dclrs.PAR_TYPESELECT) &&
                        oDBData.getVal (isdb.miscs.dclrs.PAR_TYPESELECT).equals (isdb.miscs.dclrs.PROPERTY_LIST))
                {
                    if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTOBJECT))
                    {
                        oDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT));
                        oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTOBJECT);
                    }
                    if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTREGIME))
                    {
                        oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME));
                        oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTREGIME);
                    }
                    else
                        oDBData.setRegime (isdb.miscs.dclrs.REGIME_RETRIEVE);
                }
                else
                    oDBData.setRegime (isdb.miscs.dclrs.REGIME_RETRIEVE);
            }
            else
            {
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_CONTINUE))
                    oDBData.setRegime (isdb.miscs.dclrs.REGIME_RETRIEVE);
                else
                {
                    if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_QUERY))
                        oDBData.setRegime (isdb.miscs.dclrs.REGIME_TYPESELECT);
                    else
                    {
                        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
                        {
                            if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTOBJECT))
                            {
                                oDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTOBJECT));
                                oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTOBJECT);
                            }
                            if (oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTREGIME))
                            {
                                oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME));
                                oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTREGIME);
                            }
                        }
                    }
                }
            }
        }
        return oDBData;
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни дані об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        transactiondata oTransactionData = new transactiondata ();
        if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null")
        {
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT))
            {
                // укладання угоди?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_ANSWER))
                {
                    if (oDBData.getVal (COL_ID).equals (isdb.miscs.dclrs.OBJ_NULL))	// немає ідент-ра?
                        oDBData.setVal (COL_ID, oDBData.getVal (COL_DIRNR_ID));
                    else
                    {
                        oDBData.removeVal (COL_DIRNR_ID);

                        dbdata oDirNrDBData = new dbdata (oDBData.getSession ());
                        oDirNrDBData.setVal (COL_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
                        select (oDirNrDBData);

                        // перенесення введенної інформації с незадіянного телефона на існуючий
                        oDBData.setVal (COL_ADDLOCATION, oDirNrDBData.getVal (COL_ADDLOCATION));
                        oDBData.setVal (COL_HOUSE, oDirNrDBData.getVal (COL_HOUSE));
                        oDBData.setVal (COL_REMARKS, oDirNrDBData.getVal (COL_REMARKS));
                        oDBData.setVal (COL_STREET_ID, oDirNrDBData.getVal (COL_STREET_ID));
                        oDBData.setVal (COL_TARIFFLEVEL_ID, oDirNrDBData.getVal (COL_TARIFFLEVEL_ID));
                        oDBData.setVal (COL_TECHREF_ID, oDirNrDBData.getVal (COL_TECHREF_ID));
                        oDBData.setVal (COL_TYPEPHONE_ID, oDirNrDBData.getVal (COL_TYPEPHONE_ID));

                        // стирання незадіяного запису
                        oTransactionData.setPostTransaction ("delete from DIRNRS" +
                                                             " where ID = " + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));

                        // підготовлення ідент-ра для введення кросіровочн. інформації
                        oDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_ID));
                    }
                }
            }
        }
        super.writeData (oDBData, oPoolData, oTransactionData);
    }

    /**
     * pre-маніпуляція даними поперед загрузки в стек данич
     * @param oDBData поточни дані об'екта
     */
    public void prePrepareData (dbdata oDBData)
    {
        oDBData.setVal (isdb.miscs.dclrs.PAR_TMP1, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
        oDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_ID));
    }

    /**
     * post-маніпуляція даними після вигрузки зі стека данич
     * @param oDBData поточни дані об'екта
     */
    public void postPrepareData (dbdata oDBData)
    {
        oDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_TMP1));
        oDBData.removeVal (isdb.miscs.dclrs.PAR_TMP1);
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
        {
            if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA)) != "null")
            {
                // укладаня угоди?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_AGREEMENT))
                    return "Телефонів в стані очікування укладання угоди немає!";
                // очікування відповіді/наряда?
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_ANSWER))
                    return "Телефони, на які очікують відповідь з міськТелекому, відсутні!";
                // підключення в відділі техничн. обслуговування
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNINPROGRESS))
                    return "Телефони, які потрібно кросувати та вводити в станційну БД, відсутні!";
                // встановлення дозволу на підключення телефона
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNALLOWED))
                    return "Телефони, на які є дозвіл на підключення, відсутні!";
                // кінцеве підключення телефона
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals (isdb.miscs.dclrs.STAGE_CURR_CONNECTED))
                    return "Телефони у стані кінцевого підключення відсутні!";
                // телефон неможливо встановити
                if (oDBData.getVal (isdb.miscs.dclrs.PAR_CRITERIA).equals ("PHONESTATE_ID like '#%'")) // isdb.miscs.dclrs.STAGE_CURR_CONNNOTAVAILABLE)) // "PHONESTATE_ID like '#%'"))
                    return "Телефони, які неможливо підключити, відсутні!";
            }
            return "Не вибран потрібний телефон(и)!";
        }
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
        if (strNameRefKey.equals ("ANICATEGORY_ID"))
            return "ANICATEGORIES";
        return super.getRefObj (strNameRefKey, oDBData);
    }
}


