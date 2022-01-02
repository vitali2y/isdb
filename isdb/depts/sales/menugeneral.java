/**
 * menugeneral.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "Загальна інформація".
 * <P>Відділ продажу.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menugeneral extends deptsales
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
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_DIRNR_UNASSIGN_CAPACITY + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Незадіяна емкість");
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
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Угоди");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_AGREEMENT_TARIFF_PACKET + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Тарификаційни пакети");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SUBSSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Додаткові послуги");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BLDIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Обмеження зв'язку телефонів");
            // "Чорний список");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=general",
                                       "Интернет-послуги");
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

