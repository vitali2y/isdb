/**
 * objloclinemdftrace.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Об'ект представлення LOCLINEMDFTRACES
 * @version 1.0 final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objloclinemdftrace extends isdbobj
{
    public static final String COL_LINEMDF = "LINEMDF";

    /**
     * Конструктор
     */
    public objloclinemdftrace ()
    {
        super (isdb.miscs.dclrs.VIEW_LOCLINEMDFTRACE);
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

