/**
 * objwwwsrvemail.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці WWWSERVICES
 * для використання функції "Послуга електроної почти (e-mail)".
 * @version 1.0 final, 5-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objwwwsrvemail extends objwwwservice
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
     * Вибірка назви поля таблиці.
     * @param strNameField назва поля
     * @return значення назви поля (в форматі HTML)
     */
    public String desc (String strNameField)
    {
        if (strNameField.equals (COL_SRVVALUE1))
            return htmli.center ("Префікс користувача електроної почти перед @<domain_name>");
        if (strNameField.equals (COL_SRVVALUE2))
            return htmli.center ("Ідентифікатор користувача");
        if (strNameField.equals (COL_SRVVALUE3))
            return htmli.center ("Пароль користувача");
        return super.desc (strNameField);
    }

    /**
     * Повернення шапки об'екта.
     * @return шапка об'екта
     */
    public String getTitle ()
    {
        return "Послуга електроної почти (e-mail)";
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setWhere ("WWWSERV_ID='" + SRV_EMAIL + "'");
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
            // встановлення властивостей полів
            jsdata oJSData = oOutData.getJSData ();
            oJSData.setJS (COL_SRVVALUE1, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (COL_SRVVALUE1, isdb.ifaces.jsi.JS_FUNC_CHK_ABCNUM);
            oJSData.setJS (COL_SRVVALUE2, isdb.ifaces.jsi.JS_FUNC_CHK_NULL);
            oJSData.setJS (COL_SRVVALUE2, isdb.ifaces.jsi.JS_FUNC_CHK_ABCNUM);
            oOutData.setJSData (oJSData);
            oWWWOrderDBData.setVal (COL_ID, oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oOutData.setFld (COL_SRVVALUE1, field (COL_SRVVALUE1, oDBData, oOutData));
            oOutData.setFld (COL_SRVVALUE2, field (COL_SRVVALUE2, oDBData, oOutData));
            oOutData.setFld (COL_SRVVALUE3, value (COL_SRVVALUE3, oDBData));
            oDBData.setVal (COL_STARTDATE, isdb.miscs.dclrs.OBJ_NULL);
            oOutData.setFld (COL_STARTDATE, isdb.miscs.dclrs.NOTINSTALL);
            oDBData.setVal (COL_FINISHDATE, isdb.miscs.dclrs.OBJ_NULL);
            oOutData.setFld (COL_FINISHDATE, value (COL_FINISHDATE, oDBData));
            oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));
            oOutData.setHideFld ("WWWORDER_ID", oDBData.getVal (isdb.miscs.dclrs.PAR_ID));
            oOutData.setHideFld (COL_STARTDATE, "01-01-01");
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
            oOutData.setFld (COL_SRVVALUE1, value (COL_SRVVALUE1, oDBData));
            oWWWOrderDBData.setVal (COL_ID, oDBData.getVal (COL_WWWORDER_ID));

            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_RETRIEVE))
            {
                oOutData.setFld (COL_SRVVALUE2, value (COL_SRVVALUE2, oDBData));
                oOutData.setFld (COL_SRVVALUE3, value (COL_SRVVALUE3, oDBData));
                oOutData.setFld (COL_FINISHDATE, value (COL_FINISHDATE, oDBData));
                oOutData.setFld (COL_REMARKS, value (COL_REMARKS, oDBData));
            }
            else	// оновлення інформації?
            {
                oOutData.setFld (COL_SRVVALUE2, field (COL_SRVVALUE2, oDBData, oOutData));
                oOutData.setFld (COL_SRVVALUE3, field (COL_SRVVALUE3, oDBData, oOutData));
                oOutData.setFld (COL_FINISHDATE, field (COL_FINISHDATE, "", 14, oOutData));
                oOutData.setFld (COL_REMARKS, textareafield (COL_REMARKS, oDBData, oOutData));
            }
        }
        oWWWOrder.select (oWWWOrderDBData);
        oFirmDBData.setVal (COL_ID, retrieve (objwwworder.COL_FIRM_ID, oWWWOrderDBData));
        oFirm.select (oFirmDBData);
        oOutData.setFld (objfirm.COL_NAME, oFirm.value (objfirm.COL_NAME, oFirmDBData));
        oOutData.setFld (objwwworder.COL_ORD_NUM, oWWWOrder.value (objwwworder.COL_ORD_NUM, oWWWOrderDBData));
        oOutData.setFld (objwwworder.COL_STARTDATE, oWWWOrder.value (objwwworder.COL_STARTDATE, oWWWOrderDBData));
        oOutData.setFld (COL_STARTDATE, value (COL_STARTDATE, oDBData));

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
          htmli.paragraph (htmli.cell (oOutData.getFld (objwwworder.COL_ORD_NUM), 30) +
                           htmli.cell (oOutData.getFld (objwwworder.COL_STARTDATE), 30) +
                           htmli.cell (oOutData.getFld (objfirm.COL_NAME), 40)) +

          // назва послуги
          oOutData.getFld ("TYPESERVICE") +

          // другий ряд
          htmli.paragraph ("Інформація про встановлення послуги",
                           htmli.cell (oOutData.getFld (COL_SRVVALUE1) +
                                       oOutData.getFld (COL_SRVVALUE2) +
                                       oOutData.getFld (COL_SRVVALUE3), 60) +
                           htmli.cell (oOutData.getFld (COL_STARTDATE), 40)) +

          // третій ряд
          htmli.paragraph ("Інформація про вилучення послуги",
                           htmli.cell (oOutData.getFld (COL_FINISHDATE), 100)) +

          // четвертий ряд
          htmli.paragraph (htmli.cell (oOutData.getFld (COL_REMARKS), 100)) +

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
        oDBData.setVal (COL_WWWSERV_ID, "E");
        super.writeData (oDBData, oPoolData);
    }
}

