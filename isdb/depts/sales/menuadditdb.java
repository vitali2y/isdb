/**
 * menuadditdb.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "Додаткові бази даних".
 * <P>Відділ продажу.
 * @version 1.0 final, 10-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menuadditdb extends deptsales
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menuadditdb ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_STREET + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=additdb",
                                       "Зміна вулиць, площ, тощо");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_ZIP + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=additdb",
                                       "Зміна поштових індексів");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BANK + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=additdb",
                                       "Зміна реквизитів банків");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_PERSON + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + "DEPT_ID=2%20and%20PERSONS.ID>0" + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=additdb",
                                       "Персонал відділу");
            /*
                        oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                                   isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TYPEWWWSERVICE + "&" +
                                                   isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                   isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                                   isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                                   isdb.miscs.dclrs.PAR_APPL + "=additdb",
                                                   "Типи Інтернет-послуг");
            */
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
        return "Додаткові бази даних";
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

