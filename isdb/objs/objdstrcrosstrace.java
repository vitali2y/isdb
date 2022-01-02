/**
 * objdstrcrosstrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
/**
 * Об'ект представлення DSTRCROSSTRACES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objdstrcrosstrace extends isdbobj
{
    public static final String COL_DISTRIB = "DISTRIB";

    /**
     * Конструктор
     */
    public objdstrcrosstrace ()
    {
        super (isdb.miscs.dclrs.VIEW_DSTRCROSSTRACE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, DISTRIB");
        oSQLData.setFrom ("DSTRCROSSTRACES");
        oSQLData.setOrder ("DISTRIB");
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

