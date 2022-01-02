/**
 * objtrunkcrosstrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення TRUNKCROSSTRACES
 * @version 1.0betta, 19-I-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtrunkcrosstrace extends isdbobj
{
    public static final String COL_TRUNKPAIR = "TRUNKPAIR";

    /**
     * Конструктор
     */
    public objtrunkcrosstrace ()
    {
        super (isdb.miscs.dclrs.VIEW_TRUNKCROSSTRACE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID, TRUNKPAIR");
        oSQLData.setFrom ("TRUNKCROSSTRACES");
        oSQLData.setOrder ("TRUNKPAIR");
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

