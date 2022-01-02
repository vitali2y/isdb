/**
 * objequipcrosstrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення EQUIPCROSSTRACES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objequipcrosstrace extends isdbobj
{
    public static final String COL_EQUIP = "EQUIP";

    /**
     * Конструктор
     */
    public objequipcrosstrace ()
    {
        super (isdb.miscs.dclrs.VIEW_EQUIPCROSSTRACE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, EQUIP");
        oSQLData.setFrom ("EQUIPCROSSTRACES");
        oSQLData.setOrder ("EQUIP");
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

