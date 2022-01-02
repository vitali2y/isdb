/**
 * objshlfcrosstrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення SHLFCROSSTRACES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objshlfcrosstrace extends isdbobj
{
    public static final String COL_TRUNKLINE = "TRUNKLINE";

    /**
     * Конструктор
     */
    public objshlfcrosstrace ()
    {
        super (isdb.miscs.dclrs.VIEW_SHLFCROSSTRACE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, TRUNKLINE");
        oSQLData.setFrom ("SHLFCROSSTRACES");
        oSQLData.setOrder ("TRUNKLINE");
        oDBData.setSQLData (oSQLData);
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

