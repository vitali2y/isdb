/**
 * menugeneral.java
 * ISDBj
 */

package isdb.depts.maint;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "Загальна інформація" відділу технічного обслуговування.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menugeneral extends deptmaint
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menugeneral ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Телефони");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_DIRNR_ANI + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Телефони по категоріям АВН");
            oThisMenuData.setMenuItem ("Незадіяне додаткове обладнання");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Абоненти");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_CROSSMAP + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Кросіровки");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FAILRPT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Пошкоджені телефони");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SUBSSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Додаткові послуги");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BLDIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Телефони з встановленими обмеженнями зв'язку");
            // "Чорний список");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_MENU,
                                       isdb.miscs.dclrs.PAR_MENU + "=dbreview",
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
        return "Загальна інформація";
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

