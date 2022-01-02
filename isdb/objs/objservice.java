/**
 * objservice.java
 * ISDBj
 */
package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці SERVICES.
 * @version 1.0 final, 4-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objservice extends isdbobj
{
    // Поля таблиці SERVICES
    public static final String COL_SERVICE = "SERVICE";
    public static final String COL_STARTDATE = "STARTDATE";

    /**
     * Конструктор,
     */
    public objservice ()
    {
        super (isdb.miscs.dclrs.TBL_SERVICE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,SERVICE");
        oSQLData.setFrom ("SERVICES");
        oSQLData.setOrder ("SERVICE");
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
        if (strKey.equals (objplanservice.COL_SERVICE_ID)) // ссилочне поле?
            return true;
        return super.isObligatory (strKey, oDBData);
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @return ознака можливості створення нових записів об'екта.
     */
    public boolean isCreateable ()
    {
        return false;
    }
}

