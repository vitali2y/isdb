/**
 * objtypefirm.java
 * ISDBj
 */
package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці TYPEFIRMS.
 * @version 1.0 final, 11-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypefirm extends isdbobj
{
    // Поля таблиці TYPEFIRMS
    public static final String COL_TYPE = "TYPE";

    /**
     * Конструктор.
     */
    public objtypefirm ()
    {
        super (isdb.miscs.dclrs.TBL_TYPEFIRM);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn (COL_ID);
        oSQLData.setColumn (COL_TYPE);
        oSQLData.setFrom (isdb.miscs.dclrs.TBL_TYPEFIRM);
        oSQLData.setOrder (COL_TYPE);
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
        if (strKey.equals (objfirm.COL_TYPEFIRM_ID)) // ссилочне поле?
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

