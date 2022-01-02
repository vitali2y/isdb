/**
 * menumain.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Головна сторінка відділу продажу.
 * @version 1.0 final, 10-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menumain extends deptsales
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menumain ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=operations",
                                       "Операції");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=dbreview",
                                       "Огляд баз даних відділу");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=reports",
                                       "Звіти та графіки");
            oThisMenuData.setMenuItem ("isdboption",
                                       isdb.miscs.dclrs.PAR_APPL + "=main",
                                       "Установки користувача");
            oThisMenuData.setMenuItem ("isdbinfo",
                                       isdb.miscs.dclrs.PAR_ID + "=info",
                                       "Інформація");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_LOGIN,
                                       isdb.miscs.dclrs.PAR_FAILAPPL + "=isdbmenu&" +
                                       isdb.miscs.dclrs.PAR_MENU + "=main&" +
                                       isdb.miscs.dclrs.PAR_LOGOFF + "=" + isdb.miscs.dclrs.YES,
                                       "Закінчення роботи");
            oThisMenuData.setConfirmExec ();
        }
        oThisMenuData.initMenu ();             // первин. встановлення номеру поточн. пункту меню
    }

    /**
     * Повернення назви меню.
     * @return назва меню
     */
    public String getName ()
    {
        return "Головна сторінка";
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

