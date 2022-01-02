/**
 * objwwworderstate.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект таблиці WWWORDERSTATES
 * @version 1.0 final, 20-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objwwworderstate extends isdbobj
{

    // Орігінальни поля таблиці WWWORDERSTATES
    public static final String COL_STATE = "STATE";

    /**
     * Конструктор
     */
    public objwwworderstate ()
    {
        super (isdb.miscs.dclrs.TBL_WWWORDERSTATE);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("ID,STATE");
        oSQLData.setFrom ("WWWORDERSTATES");
        oSQLData.setOrder ("STATE");
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

