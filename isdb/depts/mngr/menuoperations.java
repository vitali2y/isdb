/**
 * menuoperations.java
 * ISDBj
 */

package isdb.depts.mngr;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "операції" адміністрації.
 * @version 1.0 final, 14-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menuoperations extends deptmngr
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menuoperations ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=stateofday",
                                       "Поточний стан справ");
            oThisMenuData.setMenuItem ("Конструювання форм, звітів та графіків");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=additdb",
                                       "Додаткові бази даних");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=main",
                                       "Повернутися до головного меню");
        }
        oThisMenuData.initMenu ();             // первин. встановлення номеру поточн. пункту меню
    }

    /**
     * Повернення назви меню.
     * @return назва меню
     */
    public String getName ()
    {
        return "Операції";
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

