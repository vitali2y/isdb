/**
 * objplanequip.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PLANEQUIPS.
 * @version 1.0 final, 15-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objplanequip extends isdbobj
{
    // Поля таблиці PLANEQUIPS
    public static final String COL_PLAN_ID = "PLAN_ID";
    public static final String COL_TYPEEQUIP_ID = "TYPEEQUIP_ID";
    public static final String COL_EQUIPS = "EQUIPS";

    /**
     * Конструктор.
     */
    public objplanequip ()
    {
        super (isdb.miscs.dclrs.TBL_PLANEQUIP);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.isNextRegime (isdb.miscs.dclrs.REGIME_UPDATE))  // переланування плануемого додаткового обладнання?
            return "Перепланування додаткового обладнання";
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
        oSQLData.setColumn ("PLANEQUIPS.ID,NAME");
        oSQLData.setWhere ("TYPEEQUIPS.ID=TYPEEQUIP_ID");
        oSQLData.setWhere ("PLANS.ID=PLAN_ID");
        oSQLData.setFrom ("TYPEEQUIPS,PLANEQUIPS,PLANS");
        oSQLData.setOrder ("NAME");
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
        objtypeequip oTypeEquip = new objtypeequip ();

        // ... та їх дані
        dbdata oPlanDBData = new dbdata (oDBData.getSession ());
        dbdata oTypeEquipDBData = new dbdata (oDBData.getSession ());

        // новий період?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            // ініціалізація додаткових данних об'ектів
            oPlanDBData.setVal (COL_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oPlan.select (oPlanDBData);
            oOutData.setFld ("PLAN", oPlan.value (objplan.COL_PERIOD, oPlanDBData, true));

            oTypeEquipDBData.setVal (COL_ID, retrieve (COL_TYPEEQUIP_ID, oDBData));
            oTypeEquip.select (oTypeEquipDBData);

            oOutData.setFld ("EQUIPS", field (COL_EQUIPS, oDBData, oOutData));
            oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));

            oOutData.setHideFld (COL_PLAN_ID, retrieve (isdb.miscs.dclrs.PAR_ID, oDBData));
            oOutData.setHideFld (COL_TYPEEQUIP_ID, retrieve (COL_TYPEEQUIP_ID, oDBData));
        }
        else
        {
            // ініціалізація додаткових данних об'ектів
            oPlanDBData.setVal (isdbobj.COL_ID, retrieve (COL_PLAN_ID, oDBData));
            oPlan.select (oPlanDBData);
            oOutData.setFld ("PLAN", oPlan.value (objplan.COL_PERIOD, oPlanDBData, true));
            oTypeEquipDBData.setVal (isdbobj.COL_ID, retrieve (COL_TYPEEQUIP_ID, oDBData));
            oTypeEquip.select (oTypeEquipDBData);
            oOutData.setFld ("TYPEEQUIP", oTypeEquip.value (objtypeequip.COL_NAME, oTypeEquipDBData));

            // вибірка інформації?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                oOutData.setFld ("EQUIPS", value (COL_EQUIPS, oDBData));
                oOutData.setFld ("REMARKS", value (COL_REMARKS, oDBData));
            }
            else	// оновлення інформації стосовно планування?
            {
                oOutData.setFld ("EQUIPS", field (COL_EQUIPS, oDBData, oOutData));
                oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
            }
        }
        oOutData.setFld ("TYPEEQUIP", oTypeEquip.value (objtypeequip.COL_NAME, oTypeEquipDBData));

        // приготування HTML сторінки
        return
          // перший ряд
          isdb.ifaces.htmli.paragraph (
            isdb.ifaces.htmli.cell (oOutData.getFld ("PLAN"), 100)) +

          // другий ряд
          isdb.ifaces.htmli.paragraph ("Інформація стосовно послуги",
                                       isdb.ifaces.htmli.cell (oOutData.getFld ("TYPEEQUIP"), 50) +
                                       isdb.ifaces.htmli.cell (oOutData.getFld ("EQUIPS"), 50)) +

          // третій ряд
          isdb.ifaces.htmli.paragraph (
            isdb.ifaces.htmli.cell (oOutData.getFld ("REMARKS"), 100)) +

          // сховани параметри форми
          oOutData.getHideFld ();
    }
}
