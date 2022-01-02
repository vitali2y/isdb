/**
 * objpropertie.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці PROPERTIES
 * @version 1.0 final, 21-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objpropertie extends isdbobj
{
    // Поля таблиці PROPERTIES
    public static final String COL_PROPERTY = "PROPERTY";

    /**
     * Конструктор
     */
    public objpropertie ()
    {
        super (isdb.miscs.dclrs.TBL_PROPERTY);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,PROPERTY");
        oSQLData.setFrom ("PROPERTIES");
        oSQLData.setOrder ("PROPERTY");
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
        if (strKey.equals (objfirm.COL_PROPERTY_ID)) // ссилочне поле?
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
}

