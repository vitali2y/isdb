/**
 * menudbreview.java
 * ISDBj
 */

package isdb.depts.maint;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "огляд баз даних".
 * <P>Відділ техничного обслуговування.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menudbreview extends deptmaint
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menudbreview ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=general",
                                       "Загальна інформація");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY,
                                       "Абоненти");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_FORM,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_CROSSMAP + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY,
                                       "Кросіровки");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_PHONEBYCAT + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_FIELDS,
                                       "Телефони");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FAILRPT + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE,
                                       "Пошкоджені телефонні лінії");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BLDIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY,
                                       "Телефони з встановленими обмеженнями зв'язку");
            // "Чорний список телефонів");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_APPL + "=dbreview&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SUBSSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY,
                                       "Додаткові послуги телефонів");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=main",
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
        return "Огляд баз даних відділу";
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

