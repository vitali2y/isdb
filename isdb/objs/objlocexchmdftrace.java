/**
 * objlocexchmdftrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення LOCEXCHMDFTRACES
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objlocexchmdftrace extends isdbobj
{
    public static final String COL_EXCHMDF = "EXCHMDF";

    /**
     * Конструктор
     */
    public objlocexchmdftrace ()
    {
        super (isdb.miscs.dclrs.VIEW_LOCEXCHMDFTRACE);
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

