/**
 * objtypephone.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEPHONES
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypephone extends isdbobj
{
    // Поля таблиці TYPEPHONES
    public static final String COL_TYPEPHONE = "TYPEPHONE";

    /**
     * Конструктор
     */
    public objtypephone ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEPHONE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oDBData.setSQLData (oSQLData);


        oSQLData.setColumn ("ID,TYPEPHONE");
        oSQLData.setFrom ("TYPEPHONES");
        oSQLData.setOrder ("TYPEPHONE");
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    public boolean isObligatory (String strKey, dbdata oDBData)
    {
        if (strKey.equals (objdirnr.COL_TYPEPHONE_ID)) // ссилочне поле?
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

