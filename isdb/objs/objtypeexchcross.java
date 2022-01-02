/**
 * objtypeexchcross.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;

/**
 * Глобальний об'ект таблиць станційного кроса.
 * @version 1.0 final, 28-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objtypeexchcross extends isdbobj
{
    /** Встановлен чі ні режим тестування (по замовчанню: false - ні)? */
    private static boolean MODE_DEBUG = false;

    private String strSwitch = null;
    private objlen oLen = null;
    private objexchmdfpair oExchMDF = null;
    private objlinemdfpair oLineMDF = null;
    private dbdata oLineMDFDBData = null;
    private dbdata oExchMDFDBData = null;
    private dbdata oLenDBData = null;

    /**
     * Конструктор.
     */
    public objtypeexchcross ()
    {
        super (isdb.miscs.dclrs.VIEW_LOCLENTRACE);
        oLen = new objlen ();
        oExchMDF = new objexchmdfpair ();
        oLineMDF = new objlinemdfpair ();
        strSwitch = isdb.ifaces.cfgi.getOption (isdb.miscs.dclrs.OPT_SWITCH);
    }

    /**
     * Повернення шапки об'екта.
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Станційні кросіровки";
    }

    /**
     * Повернення списку полів об'екта.
     * @param strNameId ідент-р поля об'екта
     * @param oDBData поточні дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформований з назвою список полів об'екта (в форматі HTML).
     */
    public String list (String strNameId, dbdata oDBData, outdata oOutData)
    {
        String strSesnId = oDBData.getSession ();
        oLenDBData = new dbdata (strSesnId);
        oExchMDFDBData = new dbdata (strSesnId);
        oLineMDFDBData = new dbdata (strSesnId);
        oLen.describe (oDBData);
        oExchMDF.describe (oExchMDFDBData);
        oLineMDF.describe (oLineMDFDBData);
        oDBData.setResultCount (1);
        return isdb.ifaces.htmli.div (isdb.ifaces.htmli.formradiopar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_LEN) + " " + oLen.getTitle () +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_EXCHMDF, true) + " " + oExchMDF.getTitle () +
                                      isdb.ifaces.htmli.crlf () +
                                      isdb.ifaces.htmli.formradiopar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_LINEMDF) + " " + oLineMDF.getTitle (), "left");

    }

    /**
     * Повернення ознаки можливості створення нових записів об'екта.
     * @param oDBData поточні дані об'екта
     * @return ознака можливісті на створення нового запису
     */
    public boolean isCreateable (dbdata oDBData)
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
        return list (null, oDBData, oOutData);
    }

    /**
     * Ініціалізація, змінення та повернення загальних параметрів об'екта.
     * @param oDBData поточни дані об'екта
     * @return змінені дані об'екта
     */
    public dbdata exchParams (dbdata oDBData)
    {
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception e = new Exception ("exchParams (objtypeexchcross): " + oDBData.toString ());
            e.printStackTrace ();
        }
        oDBData.removeVal (isdbobj.COL_REMARKS);
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_QUERY))
            oDBData.setRegime (isdb.miscs.dclrs.REGIME_TYPESELECT);
        else
        {
            oDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_PROPERTY);
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) &&
                    oDBData.isPresent (isdb.miscs.dclrs.PAR_NEXTREGIME))
            {
                oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.PAR_NEXTREGIME));
                oDBData.removeVal (isdb.miscs.dclrs.PAR_NEXTREGIME);
            }
            else
            {
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT) ||
                        oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDATE))
                {
                    oDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_FORM);
                    oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.REGIME_UPDATE));
                }
                else
                {
                    if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT) ||
                            oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
                    {
                        oDBData.setVal (isdb.miscs.dclrs.PAR_NEXTAPPL, isdb.miscs.dclrs.APPL_FORM);
                        oDBData.setRegime (oDBData.getVal (isdb.miscs.dclrs.REGIME_INSERT));
                    }
                    else
                        oDBData.setRegime (isdb.miscs.dclrs.REGIME_TYPESELECT);
                }
                oDBData.removeVal (isdb.miscs.dclrs.PAR_OBJECT);
            }
        }
        return oDBData;
    }
}

