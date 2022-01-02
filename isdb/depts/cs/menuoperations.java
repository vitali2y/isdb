/**
 * menuoperations.java
 * ISDBj
 */

package isdb.depts.cs;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "операції".
 * <P>Відділ Інтернет забеспечення.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menuoperations extends deptcs
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
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + "cus" + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=operations",
                                       "Зміна реквізитів користувачів");
            oThisMenuData.setMenuItem ("Конструювання форм, звітів та графіків");
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

