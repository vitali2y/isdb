/**
 * objphonebycat.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Телефони по категоріям
 * @version 1.0final, 16-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objphonebycat extends isdbobj
{

    /**
     * Конструктор
     */
    public objphonebycat ()
    {
        super (isdb.miscs.dclrs.TBL_DIRNR);
    }

    /**
     * Повернення шапки об'екта
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Телефони по категоріям";
    }

    /**
     * Вибірка ідентифікатора таблиці по полям таблиці
     * @return ідентифікатор,
     * null - якщо не знайдено
     */
    public dbdata id (dbdata oDBData)
    {
        if ((String.valueOf (oDBData.getVal (objdirnr.COL_DIRNR)) == "null") ||
                oDBData.getVal (objdirnr.COL_DIRNR).equals (""))
            oDBData.removeVal (objdirnr.COL_DIRNR);
        else
        {
            oDBData.removeVal (objdirnr.COL_TARIFFLEVEL_ID);
            oDBData.removeVal (objdirnr.COL_PHONESTATE_ID);
        }
        return super.id (oDBData);
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        String strCriteria = "";
        if ((String.valueOf (oDBData.getVal (objdirnr.COL_DIRNR)) == "null") ||
                oDBData.getVal (objdirnr.COL_DIRNR).equals (""))
        {
            if (String.valueOf (oDBData.getVal (objdirnr.COL_TARIFFLEVEL_ID)) != "null")
                strCriteria = objdirnr.COL_TARIFFLEVEL_ID + " like '" +
                              oDBData.getVal (objdirnr.COL_TARIFFLEVEL_ID) + "'";
            if (String.valueOf (oDBData.getVal (objdirnr.COL_PHONESTATE_ID)) != "null")
                strCriteria += " and " + objdirnr.COL_PHONESTATE_ID + " like '" +
                               oDBData.getVal (objdirnr.COL_PHONESTATE_ID) + "'";
            oDBData.removeVal (objdirnr.COL_DIRNR);
        }
        else
            strCriteria = objdirnr.COL_DIRNR + " like '" + oDBData.getVal (objdirnr.COL_DIRNR) + "'";
        oDBData.setCriteriaObj (strCriteria);
        oSQLData.setColumn ("ID, DIRNR");
        oSQLData.setFrom ("DIRNRS");
        oSQLData.setWhere ("DIRNR > 99999");
        oSQLData.setOrder ("DIRNR");
        oDBData.setVal (isdb.miscs.dclrs.PAR_TYPESELECT, isdb.miscs.dclrs.PROPERTY_LIST);
        oDBData.setSQLData (oSQLData);
    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @return ознака можливості створення нових записів об'екта.
     */
    public boolean isCreateable ()
    {
        return false;
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        return
          isdb.ifaces.htmli.place (
            isdb.ifaces.htmli.row (
              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.subtitle ("... номеру телефона ") +
                                      isdb.ifaces.htmli.formtextpar (objdirnr.COL_DIRNR, "", "6") +
                                      isdb.ifaces.htmli.crlf (2) +
                                      "або" +
                                      isdb.ifaces.htmli.crlf ()
                                      , 100)), 0) +

          isdb.ifaces.htmli.place (
            isdb.ifaces.htmli.row (
              isdb.ifaces.htmli.cell (
                isdb.ifaces.htmli.crlf (3) +
                isdb.ifaces.htmli.formradiopar (objdirnr.COL_TARIFFLEVEL_ID, "S") +
                isdb.ifaces.htmli.crlf () +
                isdb.ifaces.htmli.formradiopar (objdirnr.COL_TARIFFLEVEL_ID, "A") +
                isdb.ifaces.htmli.crlf () +
                isdb.ifaces.htmli.formradiopar (objdirnr.COL_TARIFFLEVEL_ID, "U") +
                isdb.ifaces.htmli.crlf () +
                isdb.ifaces.htmli.formradiopar (objdirnr.COL_TARIFFLEVEL_ID, "D") +
                isdb.ifaces.htmli.crlf () +
                isdb.ifaces.htmli.formradiopar (objdirnr.COL_TARIFFLEVEL_ID, "%", true)
                , 5) +
              isdb.ifaces.htmli.cell ( isdb.ifaces.htmli.crlf () + isdb.ifaces.htmli.subtitle ("... по типу") + isdb.ifaces.htmli.crlf (2) +
                                       "Бізнес-телефони" + isdb.ifaces.htmli.crlf () +
                                       "Арендни" + isdb.ifaces.htmli.crlf () +
                                       "Службові" + isdb.ifaces.htmli.crlf () +
                                       "Карткофони" + isdb.ifaces.htmli.crlf () +
                                       "Усі разом", 40) +
              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.subtitle ("та") + isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "@UA") +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "@CN") +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "@IN") +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "!%") +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "@DS") +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (objdirnr.COL_PHONESTATE_ID, "%", true)
                                      , 5) +
              isdb.ifaces.htmli.cell (isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.subtitle ("... по стану") + isdb.ifaces.htmli.crlf () + isdb.ifaces.htmli.crlf () +
                                      "Незадіянні" + isdb.ifaces.htmli.crlf () +
                                      "Підключенні" + isdb.ifaces.htmli.crlf () +
                                      "У стані підключення" + isdb.ifaces.htmli.crlf () +
                                      "У стані пошкодження" + isdb.ifaces.htmli.crlf () +
                                      "Яки були відключени" + isdb.ifaces.htmli.crlf () +
                                      "Усі разом"
                                      , 60)
            ), 0);
    }
}

