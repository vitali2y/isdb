/**
 * menustateofday.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "поточний стан справ".
 * <P>Відділ продажу.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menustateofday extends deptsales
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
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + isdb.miscs.dclrs.STAGE_CURR_ANSWER + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday",
                                       "Очікування відповіді на можливість встановлення телефона");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TECHREF + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + isdb.miscs.dclrs.STAGE_CURR_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday",
                                       "Укладання угод");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + isdb.miscs.dclrs.STAGE_CURR_CONNALLOWED + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday",
                                       "Встановлення дозволу на включення нового телефона");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + isdb.miscs.dclrs.STAGE_CURR_CONNNOTAVAILABLE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday",
                                       "Телефони, які тимчасово неможливо підключити");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FAILRPT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + "RESOLVEDATE%20is%20null" + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST,
                                       "Телефони у стані пошкодження");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BLDIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + "FINISHDATE%20is%20null" + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST,
                                       "Телефони з встановленим обмеженням зв'язку");
            // "Телефони у чорному списку");
            oThisMenuData.setMenuItem ( /* isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                                     isdb.miscs.dclrs.PAR_CRITERIA + "=" + "WWWORDERSTATE_ID='L'" + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST, */
              "Очікування актівації Інтернет-послуг");
            oThisMenuData.setMenuItem ( /* isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                                     isdb.miscs.dclrs.PAR_CRITERIA + "=" + "WWWORDERSTATE_ID='B'" + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST, */
              "Заблоковані Інтернет-послуги");
            oThisMenuData.setMenuItem ( /* isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                                     isdb.miscs.dclrs.PAR_CRITERIA + "=" + "WWWORDERSTATE_ID='N'" + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST, */
              "Не активні Інтернет-послуги");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + "FINISHDATE%20is%20null" + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST,
                                       "Деактивація Інтернет-послуг");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SUBSSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                       isdb.miscs.dclrs.PAR_CRITERIA + "=" + "FINISHDATE%20is%20null" + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST,
                                       "Додаткові послуги телефонів");
            oThisMenuData.setMenuItem (
              /*
              isdb.miscs.dclrs.APPL_PROPERTY,
                                         isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                         isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                         isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                         isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE + "&" +
                                         isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRMINCOME + "&" +
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
              isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRMINCOME + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=stateofday",
              "Баланс абонентів");
            oThisMenuData.setMenuItem ("Угоди, строк дії котрих незабаром вийде");
            oThisMenuData.setMenuItem ("Оновлення інформації стосовно контактів з потенційними кліентами");
            oThisMenuData.setMenuItem ("Оновлення інформації стосовно планування діяльності");
            oThisMenuData.setMenuItem (
              /* isdb.miscs.dclrs.APPL_PROPERTY,
                                        isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                        isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_CONSTRUCTOR + "&" +
                                        isdb.miscs.dclrs.PAR_APPL + "=stateofday" + "&" +
                                        isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                        isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST,
                                        */
              "Cконструйовані форми, звіти та графіки");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_FORM,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_PLAN + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=stateofday&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_RETRIEVE,
                                       "Планування діяльності");
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

