/**
 * objwwwsrvwww.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці WWWSERVICES
 * для використання функції "Послуги WWW та FTP".
 * @version 1.0 final, 6-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objwwwsrvwww extends objwwwservice
{

    // Орігінальни поля таблиці WWWSERVICES
    public static final String COL_WWWORDER_ID = "WWWORDER_ID";
    public static final String COL_WWWSERV_ID = "WWWSERV_ID";
    public static final String COL_SRVVALUE1 = "SRVVALUE1";
    public static final String COL_SRVVALUE2 = "SRVVALUE2";
    public static final String COL_SRVVALUE3 = "SRVVALUE3";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_FINISHDATE = "FINISHDATE";

    /**
     * Повернення шапки об'екта.
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Послуги WWW та FTP";
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setWhere ("WWWSERV_ID='" + SRV_WWW + "'");
        oDBData.setSQLData (oSQLData);
        super.listproperty (oDBData, oOutData);
    }

    /**
     * Редагуеми поля об'екта.
     * @param oDBData поточни дані об'екта
     * @param oOutData поточні вихідні дані об'екта
     * @return сформовані редагуеми поля
     */
    public String fields (dbdata oDBData, outdata oOutData)
    {
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            setHideColumn (COL_SRVVALUE1);
            setHideColumn (COL_SRVVALUE2);
            setHideColumn (COL_SRVVALUE3);
            return super.fields (oDBData, oOutData);
        }
        // додаткові об'екти
        objfirm oFirm = new objfirm ();
        objwwworder oWWWOrder = new objwwworder ();

        // ... та їх дані
        dbdata oFirmDBData = new dbdata (oDBData.getSession ());
        dbdata oWWWOrderDBData = new dbdata (oDBData.getSession ());

        // нова Интернет-послуга?
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            oWWWOrderDBData.setVal (COL_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            // oOutData.setFld ("SRVVALUE1", field (COL_SRVVALUE1, oDBData, oOutData));
            oDBData.setVal (COL_STARTDATE, isdb.miscs.dclrs.OBJ_NULL);
            oOutData.setFld ("STARTDATE",isdb.miscs.dclrs.NOTINSTALL);
            oDBData.setVal (COL_FINISHDATE, isdb.miscs.dclrs.OBJ_NULL);
            oOutData.setFld ("FINISHDATE", value (COL_FINISHDATE, oDBData));
            oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
            oOutData.setHideFld ("WWWORDER_ID", oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oOutData.setHideFld ("STARTDATE", "01-01-01");
        }
        else    // оновлення або вибірка інформації?
        {
            // назва послуги
            objtypewwwservice oTypeService = new objtypewwwservice ();
            dbdata oTypeServiceDBData = new dbdata (oDBData.getSession ());
            oTypeServiceDBData.setVal (COL_ID, oDBData.getVal (COL_WWWSERV_ID));
            oTypeService.select (oTypeServiceDBData);
            oOutData.setFld ("TYPESERVICE",
                             htmli.paragraph (htmli.cell (oTypeService.value (objtypewwwservice.COL_SERVICE, oTypeServiceDBData), 100)));
            // oOutData.setFld ("SRVVALUE1", value (COL_SRVVALUE1, oDBData));
            oWWWOrderDBData.setVal (COL_ID, oDBData.getVal (COL_WWWORDER_ID));

            // вибірка інформації стосовно замовлених Интернет-послуг?
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                oOutData.setFld ("FINISHDATE", value (COL_FINISHDATE, oDBData));
                oOutData.setFld ("REMARKS", value (COL_REMARKS, oDBData));
            }
            else	// оновлення інформації?
            {
                oOutData.setFld ("FINISHDATE", field (COL_FINISHDATE, "", 14, oOutData));
                oOutData.setFld ("REMARKS", textareafield (COL_REMARKS, oDBData, oOutData));
            }
        }
        oWWWOrder.select (oWWWOrderDBData);
        oFirmDBData.setVal (COL_ID, retrieve (objwwworder.COL_FIRM_ID, oWWWOrderDBData));
        oFirm.select (oFirmDBData);
        oOutData.setFld ("FIRM_NAME", oFirm.value (objfirm.COL_NAME, oFirmDBData));
        oOutData.setFld ("WWWORDER_ORD_NUM", oWWWOrder.value (objwwworder.COL_ORD_NUM, oWWWOrderDBData));
        oOutData.setFld ("WWWORDER_STARTDATE", oWWWOrder.value (objwwworder.COL_STARTDATE, oWWWOrderDBData));
        oOutData.setFld ("STARTDATE", value (COL_STARTDATE, oDBData));

        // додаткові кнопки навігації
        if (!oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSERT))
        {
            buttondata oFirmButton = new buttondata ();
            oFirmButton.setName ("Абонент");
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_OBJECT, isdb.miscs.dclrs.OBJ_FIRM);
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_ID, oFirmDBData.getVal (COL_ID));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_APPL, oDBData.getVal (isdb.miscs.dclrs.PAR_APPL));
            oFirmButton.setPar (isdb.miscs.dclrs.PAR_REGIME, isdb.miscs.dclrs.REGIME_RETRIEVE);
            oDBData.setButton (oFirmButton.getButton (oOutData));
        }

        // приготування HTML сторінки
        return
          // перший ряд
          htmli.paragraph (htmli.cell (oOutData.getFld ("WWWORDER_ORD_NUM"), 30) +
                           htmli.cell (oOutData.getFld ("WWWORDER_STARTDATE"), 30) +
                           htmli.cell (oOutData.getFld ("FIRM_NAME"), 40)) +

          // назва послуги
          oOutData.getFld ("TYPESERVICE") +

          // другий ряд
          htmli.paragraph ("Інформація про встановлення послуги",
                           // htmli.cell (oOutData.getFld ("SRVVALUE1"), 50) +
                           htmli.cell (oOutData.getFld ("STARTDATE"), 100)) +

          // третій ряд
          htmli.paragraph ("Інформація про вилучення послуги",
                           htmli.cell (oOutData.getFld ("FINISHDATE"), 100)) +

          // четвертий ряд
          htmli.paragraph (htmli.cell (oOutData.getFld ("REMARKS"), 100)) +

          // сховани параметри форми
          oOutData.getHideFld ();
    }

    /**
     * Проведення транзакції.
     * @param oDBData поточни данні об'екта
     * @param oPoolData пул зберегаемих значеннь, використовуемих в транзакціях об'ектів
     */
    public void writeData (dbdata oDBData, pooldata oPoolData)
    {
        oDBData.setVal (COL_WWWSERV_ID, SRV_WWW);
        super.writeData (oDBData, oPoolData);
    }
}

