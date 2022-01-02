/**
 * menureports.java
 * ISDBj
 */

package isdb.depts.sales;

import isdb.datas.menudata;
import isdb.depts.*;

/**
 * Сторінка "звіти та графіки".
 * <P>Відділ продажу.
 * @version 1.0 final, 28-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menureports extends deptsales
{
    /** Поточна структура меню */
    private static menudata oThisMenuData;

    /**
     * Конструктор.
     */
    public menureports ()
    {
        if (oThisMenuData == null)        // первинна ініціалізація?
        {
            oThisMenuData = new menudata (getDept ());
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_CONN_DIRNR_YEAR + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_GRAPHCHART + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports",
                                       "Статистика підключень телефонних ліній за рік");
            oThisMenuData.setMenuItem ("Статистика підключень телефонних ліній за час роботи станції");
            oThisMenuData.setMenuItem ("Статистика по незадіянної емкості");
            oThisMenuData.setMenuItem ("Звіт по абонентам, угоди з якими укладени на цей час");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_AGREEMENT_RPT_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по укладеним угодам на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_AGREEMENT_TARIFF_PACKET + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по розподіленню абонентів по тарифікаційним пакетам на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_DIRNR_RPT_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_DIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по телефонам, угоди з якими укладени на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_FAIL_RPT_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_FAILRPT + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по пошкодженним телефонним лініям на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_WWWSERVICE_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по встановленим Інтернет послугам на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_SIGN_AGREEMENT_YEAR + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_AGREEMENT + "&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_GRAPHCHART + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports",
                                       "Статистика укладених угод за рік");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_GRAPH,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.GRAPH_CONN_WWWSERVICE_YEAR + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_WWWSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_GRAPHCHART + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports",
                                       "Статистика встановлених Інтернет послуг за рік");
            oThisMenuData.setMenuItem ("Звіт по підключеним телефонним лініям за минулий місяць");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_BLACK_LIST_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_BLDIRNR + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по телефонам, яким встановлені обмеження зв'язку на цей час");
            // "Звіт по телефонам, які знаходяться в чорному списку на цей час");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_SUBSSERVICE_CURRENT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_SUBSSERVICE + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Звіт по додатковим послугам телефонів на цей час");
            oThisMenuData.setOpenNewWnd ();
            oThisMenuData.setMenuItem ("Звіт по контактам з потенційними кліентами");
            oThisMenuData.setMenuItem (isdb.miscs.dclrs.APPL_REPORT,
                                       isdb.miscs.dclrs.PAR_RPT_ID + "=" + isdb.miscs.dclrs.REPORT_PLAN_PROJECT + "&" +
                                       isdb.miscs.dclrs.PAR_OBJECT + "=" + isdb.miscs.dclrs.OBJ_PLAN + "&" +
                                       isdb.miscs.dclrs.PAR_APPL + "=reports&" +
                                       isdb.miscs.dclrs.PAR_TYPE_OUT + "=" + isdb.miscs.dclrs.TYPE_OUT_REPORT,
                                       "Робочий проект по плануванню діяльності");
            oThisMenuData.setOpenNewWnd ();
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
        return "Звіти та графіки";
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

