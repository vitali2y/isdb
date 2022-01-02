/**
 * objatecrosstrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення ATECROSSTRACES
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objatecrosstrace extends isdbobj
{
    public static final String COL_CROSS = "CROSS";

    /**
    * Конструктор
    */
    public objatecrosstrace ()
    {
        super (isdb.miscs.dclrs.VIEW_ATECROSSTRACE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, CROSS");
        oSQLData.setFrom ("ATECROSSTRACES");
        oSQLData.setOrder ("CROSS");
        oDBData.setSQLData (oSQLData);
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

