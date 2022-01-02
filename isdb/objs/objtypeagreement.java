/**
 * objtypeagreement.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEAGREEMENTS
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypeagreement extends isdbobj
{
    // Поля таблиці TYPEAGREEMENTS
    public static final String COL_TYPE = "TYPE";

    /**
     * Конструктор
     */
    public objtypeagreement ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEAGREEMENT);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,TYPE");
        oSQLData.setFrom ("TYPEAGREEMENTS");
        oSQLData.setOrder ("TYPE");
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
     * @return ознака можливості створення нових записів об'екта.
     */
    public boolean isCreateable ()
    {
        return false;
    }
}

