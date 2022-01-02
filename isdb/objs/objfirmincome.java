/**
 * objfirmincome.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці FIRMINCOMES.
 * @version 1.0 final, 27-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objfirmincome extends isdbobj
{
    // Орігінальни поля таблиці FIRMINCOMES
    public static final String COL_PERIOD = "PERIOD";
    public static final String COL_NAME = "NAME";
    public static final String COL_PAYCODE = "PAYCODE";
    public static final String COL_PAY = "PAY";
    public static final String COL_CALLPAY = "CALLPAY";
    public static final String COL_SUBSCRIPTIONPAY = "SUBSCRIPTIONPAY";
    public static final String COL_INSTALLPAY = "INSTALLPAY";
    public static final String COL_DEBT = "DEBT";
    public static final String COL_CARD = "CARD";

    /**
     * Конструктор.
     */
    public objfirmincome ()
    {
        super (isdb.miscs.dclrs.TBL_FIRMINCOME);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        // додаткові об'екти ...
        objfirm oFirm = new objfirm ();
        objpropertie oProperty = new objpropertie ();

        // ... та їх дані
        dbdata oFirmDBData = new dbdata (oDBData.getSession ());
        dbdata oPropertyDBData = new dbdata (oDBData.getSession ());
        oFirmDBData.setVal (COL_ID, retrieve (COL_ID, oDBData));
        oFirm.select (oFirmDBData);
        oPropertyDBData.setVal (COL_ID, retrieve (objfirm.COL_PROPERTY_ID, oFirmDBData));
        oProperty.select (oPropertyDBData);

        reportdata oRptData = new reportdata (getTitle ());
        oRptData.createColumn (desc (COL_PERIOD), 30);
        oRptData.createColumn (desc (COL_PAY), 20);
        oRptData.createColumn (desc (COL_CALLPAY), 10);
        oRptData.createColumn (desc (COL_SUBSCRIPTIONPAY), 10);
        oRptData.createColumn (desc (COL_INSTALLPAY), 10);
        oRptData.createColumn (desc (COL_DEBT), 10);
        oRptData.createColumn (desc (COL_CARD), 10);
        oRptData.setWithoutSummary ();
        sqldata oSQLData = new sqldata ();
        oSQLData.setColumn ("PERIOD,PAY,CALLPAY,SUBSCRIPTIONPAY,INSTALLPAY,DEBT,CARD");
        oSQLData.setFrom (getDBName ());
        oSQLData.setWhere ("ID=" + oFirmDBData.getVal (COL_ID));
        oSQLData.setOrder (COL_PERIOD);
        oRptData.setModeData (oSQLData.getData ());

        // додаткові кнопки навігації
        // абонент
        buttondata oFirmButton = new buttondata ();
        oFirmButton.setName ("Абонент");
        oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
        oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
        oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_ID));
        oDBData.setButton (oFirmButton.getButton (oOutData));

        // друкувати
        buttondata oPrintIncomeButton = new buttondata ();
        oPrintIncomeButton.setUrl (isdb.miscs.dclrs.APPL_REPORT, true);
        oPrintIncomeButton.setName (isdb.miscs.dclrs.TITLE_REG_PRINT);
        dbdata oPrintIncomeDBData = new dbdata (oDBData.getSession ());
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_TYPE_OUT, isdb.miscs.dclrs.TYPE_OUT_REPORT);
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_RPT_ID, isdb.miscs.dclrs.REPORT_FIRMINCOME_CURRENT);
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRMINCOME);
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
        oPrintIncomeDBData.setVal (isdb.miscs.dclrs.PAR_ID, oDBData.getVal (COL_ID));
        oPrintIncomeButton.setUrl (isdb.miscs.dclrs.APPL_REPORT + "?" + oPrintIncomeDBData.getHTTPParams (), true, "'menubar=yes,scrollbars=yes'");
        oDBData.setButton (oPrintIncomeButton.getButton (oOutData));
        return isdb.ifaces.htmli.paragraph (oPropertyDBData.getVal (objpropertie.COL_PROPERTY) +
                                            " \"" + oFirmDBData.getVal (objfirm.COL_NAME) + "\"",
                                            getItems (oDBData, oRptData).getReport ());
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

        // Звіт по прибуткам
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_FIRMINCOME_CURRENT))
        {
            oDBData.setVal (COL_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            select (oDBData);
            strRptTitle = getTitle () + " " + oDBData.getVal (COL_NAME);
            reportdata oRptData = new reportdata (getTitle ());
            oRptData.createColumn (desc (COL_PERIOD), 20);
            oRptData.createColumn (desc (COL_PAY), 30);
            oRptData.createColumn (desc (COL_CALLPAY), 10);
            oRptData.createColumn (desc (COL_SUBSCRIPTIONPAY), 10);
            oRptData.createColumn (desc (COL_INSTALLPAY), 10);
            oRptData.createColumn (desc (COL_DEBT), 10);
            oRptData.createColumn (desc (COL_CARD), 10);
            sqldata oSQLData = new sqldata ();
            oSQLData.setColumn (COL_PERIOD);
            oSQLData.setColumn (COL_PAY);
            oSQLData.setColumn (COL_CALLPAY);
            oSQLData.setColumn (COL_SUBSCRIPTIONPAY);
            oSQLData.setColumn (COL_INSTALLPAY);
            oSQLData.setColumn (COL_DEBT);
            oSQLData.setColumn (COL_CARD);
            oSQLData.setFrom (getDBName ());
            oSQLData.setWhere (COL_ID + "=" + oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oRptData.setModeData (oSQLData.getData ());
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }
}

