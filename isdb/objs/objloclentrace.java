/**
 * objloclentrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення LOCLENTRACES
 * @version 1.0betta, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objloclentrace extends isdbobj
{
    public static final String COL_LEN = "LEN";

    /**
     * Конструктор
     */
    public objloclentrace ()
    {
        super (isdb.miscs.dclrs.VIEW_LOCLENTRACE);
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

