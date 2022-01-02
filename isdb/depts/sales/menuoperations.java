/**
 * menuoperations.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "операції" відділу продажу.
 * @version 1.0 final, 18-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menuoperations extends deptsales
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
            oThisMenuData.setMenuItem (
              /*
                          isdb.miscs.dclrs.APPL_FORM,
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TECHREF + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
              isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_TECHREF + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Первинна реєстрація заяви на встановлення телефон(а/ів)");
            oThisMenuData.setMenuItem (
              /*
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TECHREF + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
              isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_TECHREF + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Зміна реквізитів заяв/техничних довідок");
            oThisMenuData.setMenuItem (
              /*
                          isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Зміна реквізитів телефона");
            oThisMenuData.setMenuItem (
              /*
                          isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Зміна реквізитів абонента");
            oThisMenuData.setMenuItem (
              /*
                          isdb.miscs.dclrs.APPL_PROPERTY,
                                                     isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                                     isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                     isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                                     isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
                                                     isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_MAINT + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Зміна реквізитів угод");
            oThisMenuData.setMenuItem ("Перенос телефона");
            oThisMenuData.setMenuItem ("Припинення угоди");
            oThisMenuData.setMenuItem ("Перезаключення угоди");
            oThisMenuData.setMenuItem ("Передача абонентом телефона в аренду");
            oThisMenuData.setMenuItem ("Відмова від телефона та його подальша передача другому абоненту");
            oThisMenuData.setMenuItem (
              /*
              isdb.miscs.dclrs.APPL_PROPERTY,
                                         isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
                                         isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                         isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                         isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
                                         isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWORDER + "&" +
                                         isdb.miscs.dclrs.PAR_APPL + "=operations",
              */
              isdb.miscs.dclrs.APPL_PROPERTY,
              isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FIRM + "&" +
              isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY + "&" +
              isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_INSERT + "&" +
              isdb.miscs.dclrs.PAR_NEXTOBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWORDER + "&" +
              isdb.miscs.dclrs.PAR_APPL + "=operations",
              "Реєстрація нового замовлення абонента на послуги Інтернет");
            oThisMenuData.setMenuItem ("Послуга \"800\"");
            oThisMenuData.setMenuItem ("Послуга цифрового комутованого зв'язку ISDN");
            oThisMenuData.setMenuItem ("Контакти з потенційними кліентами");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_FORM,
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_PLAN + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=operations&" +
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_QUERY,
                                       "Планування діяльності");
            oThisMenuData.setMenuItem (
              /*
               isdb.miscs.dclrs.APPL_PROPERTY,
                                                                               isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_CONSTRUCTOR + "&" +
                                                                               isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                                                               isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                                                               isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPDATE + "&" +
                                                                               isdb.miscs.dclrs.PAR_APPL + "=operations",
                                                                               */
              "Конструювання форм, звітів та графіків");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_PROPERTY,
                                       isdb.miscs.dclrs.PAR_REGIME + "=" + isdb.miscs.dclrs.REGIME_TYPESELECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SESSION + "&" +
                                       isdb.miscs.dclrs.PAR_TYPESELECT + "=" + isdb.miscs.dclrs.PROPERTY_LIST + "&" +
                                       isdb.miscs.dclrs.PAR_NEXTREGIME + "=" + isdb.miscs.dclrs.REGIME_UPLOAD + "&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_FILE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=operations",
                                       "Вибірка даних сесій Internet для тарифікації");
            oThisMenuData.setMenuItem ("Вибірка даних картотеки абонентів Internet для тарифікації");
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

