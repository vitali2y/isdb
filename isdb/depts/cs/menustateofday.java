/**
 * menustateofday.java
 * ISDBj
 */

package isdb.depts.cs;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "поточний стан справ".
 * <P>Відділ Інтернет забеспечення.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menustateofday extends deptcs
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menustateofday ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem ("Cконструйовані форми, звіти та графіки");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=operations",
                                       "Повернутися до попереднього меню");
        }
        oThisMenuData.initMenu ();             // первин. встановлення номеру поточн. пункту меню
    }

    /**
     * Повернення назви меню.
     * @return назва меню
     */
    public String getName ()
    {
        return "Поточний стан справ";
    }

    /**
     * Витягнення поточних даних об'екта меню.
     * @return сформовані поточні дані об'екта меню
     */
    public menudata getData ()
    {
        return oThisMenuData;
    }
}

