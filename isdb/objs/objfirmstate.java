/**
 * objfirmstate.java
 * ISDBj
 */
package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці FIRMSTATES.
 * @version 1.0 final, 11-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objfirmstate extends isdbobj
{
    // Поля таблиці FIRMSTATES
    public static final String COL_STATE = "STATE";

    /**
     * Конструктор.
     */
    public objfirmstate ()
    {
        super (isdb.miscs.dclrs.TBL_FIRMSTATE);
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
        oSQLData.setColumn (COL_STATE);
        oSQLData.setFrom (isdb.miscs.dclrs.TBL_FIRMSTATE);
        oSQLData.setOrder (COL_STATE);
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
        if (strKey.equals (objfirm.COL_FIRMSTATE_ID)) // ссилочне поле?
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

