/**
 * objblrestriction.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці BLRESTRICTIONS.
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objblrestriction extends isdbobj
{
    // Поля таблиці BLRESTRICTIONS
    public static final String COL_RESTRICTION = "RESTRICTION";

    /**
     * Конструктор.
     */
    public objblrestriction ()
    {
        super (isdb.miscs.dclrs.TBL_BLRESTRICTION);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, RESTRICTION");
        oSQLData.setFrom ("BLRESTRICTIONS");
        oSQLData.setOrder ("RESTRICTION");
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
        if (strKey.equals (objbldirnr.COL_RESTRICTION_ID)) // ссилочне поле?
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
     * Повернення назви ссилочного об'екта
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
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
}

