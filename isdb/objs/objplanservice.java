/**
 * objplanservice.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PLANSERVICES.
 * @version 1.0 final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objplanservice extends isdbobj
{
    // Поля таблиці PLANSERVICES
    public static final String COL_PLAN_ID = "PLAN_ID";
    public static final String COL_SERVICE_ID = "SERVICE_ID";
    public static final String COL_SERVICES = "SERVICES";

    /**
     * Конструктор.
     */
    public objplanservice ()
    {
        super (isdb.miscs.dclrs.TBL_PLANSERVICE);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.isNextRegime (isdb.miscs.dclrs.REGIME_UPDATE))  // переланування послуг?
            return "Перепланування послуг";
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
        if (String.valueOf (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME)) != "null")
        {
            if (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME).equals (isdb.miscs.dclrs.REGIME_MAINT))
            {
                // додаткові кнопки навігації
                // створити новий запис
                buttondata oCreateNewRecordButton = new buttondata (oDBData);
                oDBData.setButton (oCreateNewRecordButton.getButton (oOutData));
            }
        }
        oSQLData.setColumn ("PLANSERVICES.ID,SERVICE");
        oSQLData.setWhere ("SERVICES.ID=SERVICE_ID");
        oSQLData.setWhere ("PLANS.ID=PLAN_ID");
        oSQLData.setFrom ("SERVICES,PLANSERVICES,PLANS");
        oSQLData.setOrder ("SERVICE");
        oDBData.setSQLData (oSQLData);
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
            return super.fields (oDBData, oOutData);
        }
        // додаткові об'екти
        objplan oPlan = new objplan ();
        objservice oService = new objservice ();

        // ... та їх дані
        dbdata oPlanDBData = new dbdata (oDBData.getSession ());
        dbdata oServiceDBData = new dbdata (oDBData.getSession ());

        // новий період?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            // ініціалізація додаткових данних об'ектів
            oPlanDBData.setVal (COL_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oPlan.select (oPlanDBData);
            oOutData.setFld ("PLAN", oPlan.value (objplan.COL_PERIOD, oPlanDBData, true));

            oServiceDBData.setVal (COL_ID, retrieve (COL_SERVICE_ID, oDBData));
            oService.select (oServiceDBData);

            oOutData.setFld ("SERVICES", field (COL_SERVICES, oDBData, oOutData));
            oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));

            oOutData.setHideFld (COL_PLAN_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oOutData.setHideFld (COL_SERVICE_ID, retrieve (COL_SERVICE_ID, oDBData));
        }
        else
        {
            // ініціалізація додаткових данних об'ектів
            oPlanDBData.setVal (isdbobj.COL_ID, retrieve (COL_PLAN_ID, oDBData));
            oPlan.select (oPlanDBData);
            oOutData.setFld ("PLAN", oPlan.value (objplan.COL_PERIOD, oPlanDBData, true));
            oServiceDBData.setVal (isdbobj.COL_ID, retrieve (COL_SERVICE_ID, oDBData));
            oService.select (oServiceDBData);
            oOutData.setFld ("SERVICE", oService.value (objservice.COL_SERVICE, oServiceDBData));

            // вибірка інформації?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                oOutData.setFld ("SERVICES", value (COL_SERVICES, oDBData));
                oOutData.setFld ("REMARKS", value (COL_REMARKS, oDBData));
            }
            else	// оновлення інформації стосовно планування?
            {
                oOutData.setFld ("SERVICES", field (COL_SERVICES, oDBData, oOutData));
                oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
            }
        }
        oOutData.setFld ("SERVICE", oService.value (objservice.COL_SERVICE, oServiceDBData));

        // приготування HTML сторінки
        return
          // перший ряд
          isdb.ifaces.htmli.paragraph (
            isdb.ifaces.htmli.cell (oOutData.getFld ("PLAN"), 100)) +

          // другий ряд
          isdb.ifaces.htmli.paragraph ("Інформація стосовно послуги",
                                       isdb.ifaces.htmli.cell (oOutData.getFld ("SERVICE"), 50) +
                                       isdb.ifaces.htmli.cell (oOutData.getFld ("SERVICES"), 50)) +

          // третій ряд
          isdb.ifaces.htmli.paragraph (
            isdb.ifaces.htmli.cell (oOutData.getFld ("REMARKS"), 100)) +

          // сховани параметри форми
          oOutData.getHideFld ();
    }
}

