/**
 * objregion.java
 * ISDBj
 */
package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці REGIONS.
 * @version 1.0 final, 10-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objregion extends isdbobj
{
    // Поля таблиці REGIONS
    public static final String COL_NAME = "NAME";

    /**
     * Конструктор.
     */
    public objregion ()
    {
        super (isdb.miscs.dclrs.TBL_REGION);
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
        oSQLData.setColumn (COL_NAME);
        oSQLData.setFrom (isdb.miscs.dclrs.TBL_REGION);
        oSQLData.setOrder (COL_NAME);
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки обов'язковості введення знвчення поля об'екта.
     * @param strKey назва поля
     * @param oDBData поточні дані об'екта
     * @return ознака обов'язковості введення нового знвчення
     */
    /*
        public boolean isObligatory (String strKey, dbdata oDBData)
        {
            if (strKey.equals (objfirm.COL_REGION_ID)) // ссилочне поле?
                return true;
            return super.isObligatory (strKey, oDBData);
        }
    */
}

