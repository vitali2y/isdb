/**
 * objtarifflevel.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TARIFFLEVELS
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtarifflevel extends isdbobj
{
    // Поля таблиці TARIFFLEVELS
    public static final String COL_TARIFFLEVEL = "TARIFFLEVEL";

    /**
     * Конструктор
     */
    public objtarifflevel ()
    {
        super (isdb.miscs.dclrs.TBL_TARIFFLEVEL);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,TARIFFLEVEL");
        oSQLData.setFrom ("TARIFFLEVELS");
        oSQLData.setOrder ("TARIFFLEVEL");
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
        if (strKey.equals (objdirnr.COL_TARIFFLEVEL_ID)) // ссилочне поле?
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

