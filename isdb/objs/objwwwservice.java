/**
 * objwwwservice.java
 * ISDBj
 */

package isdb.objs;

import isdb.datas.*;
import isdb.ifaces.*;

/**
 * Об'ект таблиці WWWSERVICES.
 * @version 1.0 final, 24-IV-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class objwwwservice extends isdbobj
{

    // Орігінальни поля таблиці WWWSERVICES
    public static final String COL_WWWORDER_ID = "WWWORDER_ID";
    public static final String COL_WWWSERV_ID = "WWWSERV_ID";
    public static final String COL_SRVVALUE1 = "SRVVALUE1";
    public static final String COL_SRVVALUE2 = "SRVVALUE2";
    public static final String COL_SRVVALUE3 = "SRVVALUE3";
    public static final String COL_STARTDATE = "STARTDATE";
    public static final String COL_FINISHDATE = "FINISHDATE";

    // Послуги
    public static final String SRV_DNS = "D";
    public static final String SRV_EMAIL = "E";
    public static final String SRV_ISDNCARD = "C";
    public static final String SRV_WEBHOST = "H";
    public static final String SRV_WWW = "W";
    public static final String SRV_SMTP = "P";
    public static final String SRV_CONSTIP = "I";

    /**
     * Конструктор.
     */
    public objwwwservice ()
    {
        super (isdb.miscs.dclrs.TBL_WWWSERVICE);
    }

    /**
     * Повернення шапки об'екта.
     * @param oDBData поточни дані об'екта
     * @return шапка об'екта
     */
    public String getTitle (dbdata oDBData)
    {
        if (oDBData.getVal (isdb.miscs.dclrs.PAR_APPL).equals ("general"))
            return "Діаграма розподілу Интернет-послуг";
        return super.getTitle (oDBData);
    }

    /**
     * Формування параметрів SQL об'екта
     * @param oSQLData поточні дані SQL об'екта
     * @param oDBData поточні дані об'екта
     * @param res вихідний потік сервлета
     */
    public void listproperty (sqldata oSQLData, dbdata oDBData, javax.servlet.http.HttpServletResponse res)
    {
        oSQLData.setColumn ("WWWSERVICES.ID, 'Угода '||ORD_NUM||', \"'||NAME||'\", '||SERVICE||', '||decode(WWWSERVICES.STARTDATE,to_date('01-01-01','DD-MM-YY'),'не активована','встан. '||to_char(WWWSERVICES.STARTDATE,'DD-MM-YY'))");
        oSQLData.setFrom ("TYPEWWWSERVICES,WWWSERVICES,WWWORDERS,FIRMS");
        oSQLData.setWhere ("TYPEWWWSERVICES.ID=WWWSERV_ID");
        oSQLData.setWhere ("WWWORDERS.ID=WWWORDER_ID");
        oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
        oSQLData.setOrder ("WWWSERVICES.STARTDATE");
    }

    /**
     * Встановлення параметрів списку об'екта.
     * @param oOutData поточні вихідні дані об'екта
     * @param oDBData поточні робочі дані об'екта
     */
    public void listproperty (dbdata oDBData, outdata oOutData)
    {
        sqldata oSQLData = oDBData.getSQLData ();
        oSQLData.setColumn ("WWWSERVICES.ID, 'Угода '||ORD_NUM||', \"'||NAME||'\", '||SERVICE||', '||decode(WWWSERVICES.STARTDATE,to_date('01-01-01','DD-MM-YY'),'не активована','встан. '||to_char(WWWSERVICES.STARTDATE,'DD-MM-YY'))");
        oSQLData.setFrom ("TYPEWWWSERVICES,WWWSERVICES,WWWORDERS,FIRMS");
        oSQLData.setWhere ("TYPEWWWSERVICES.ID=WWWSERV_ID");
        oSQLData.setWhere ("WWWORDERS.ID=WWWORDER_ID");
        oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
        oSQLData.setOrder ("WWWSERVICES.STARTDATE");
        oDBData.setSQLData (oSQLData);
    }

    /*
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
        if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_TYPESELECT) ||
                oDBData.isRegime (isdb.miscs.dclrs.REGIME_MAINT))
        {
            setHideColumn (COL_STATEDATE);
            setHideColumn (COL_PERSON_ID);
            return super.fields (oDBData, oOutData);
        }

        // будова об'екта
        isdbobj oThisObj = null;
        String strObject = null;
        try
        {
            switch (oDBData.getVal (COL_WWWSERV_ID).charAt (0))
            {
            case 'W': // Послуги WWW та FTP
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVWWW;
                break;
            case 'D': //Послуга DNS
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVDNS;
                break;
            case 'I': // Постiйнi IP-адреси
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVCONSTIP;
                break;
            case 'E': // Послуга електроної почти (e-mail)
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVEMAIL;
                break;
            case 'P': // Підтримка поштового серверу (SMTP)
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVSMTP;
                break;
            case 'H': // Розмiщення Web-сторiнок
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVWEBHOST;
                break;
            case 'C': // Використання ISDN-карти
                strObject = isdb.miscs.dclrs.OBJ_WWWSRVISDNCARD;
                break;
            default:
                strObject = null;
            }
            if (String.valueOf (strObject) != "null")
                oThisObj = (isdb.objs.isdbobj) Class.forName ("isdb.objs.obj" + strObject).newInstance ();
        }
        catch (ClassNotFoundException ex)
            { ex.printStackTrace (); }
        catch (ClassCastException ex)
            { ex.printStackTrace (); }
        catch (IllegalAccessException  ex)
            { ex.printStackTrace (); }
        catch (InstantiationException ex)
            { ex.printStackTrace (); }
        ///
        Exception e9 = new Exception ("fields 1: " + "isdb.objs.obj" + strObject + "\n" + oDBData.toString ());
        e9.printStackTrace ();
        ///

        if (String.valueOf (strObject) != "null")
            oThisObj.select (oDBData);

        ///
        Exception e97 = new Exception ("fields after: " + oDBData.toString ());
        e97.printStackTrace ();
        ///
        return oThisObj.fields (oDBData, oOutData);
    }

    /**
     * Приготування звіта.
     * @param oDBData поточни дані об'екта
     * @return сформований в форматі HTML звіт
     */
    public dbdata report (dbdata oDBData)
    {
        String strReport = "???";
        String strRptTitle = "???";
        String strRptNumb = "???";
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);
        sqldata oSQLData = new sqldata ();

        // Інтернет послуги телефонів на цей час
        if (strRptId.equals (isdb.miscs.dclrs.REPORT_WWWSERVICE_CURRENT))
        {
            strRptTitle = this.getTitle () + " на " + dbi.dbdate (oDBData);
            reportdata oRptData = new reportdata (strRptTitle);

            strRptNumb = "";
            oSQLData.setColumn ("ORD_NUM");
            oSQLData.setColumn ("'\"'||NAME||'\"'");
            oSQLData.setColumn ("decode(WWWSERVICES.STARTDATE, to_date('01-01-01','DD-MM-YY'), 'невідомо', to_char (WWWSERVICES.STARTDATE, 'DD-MM-YY'))");
            oSQLData.setColumn ("SERVICE");
            oSQLData.setColumn ("WWWSERVICES.REMARKS");
            oSQLData.setFrom ("WWWSERVICES");
            oSQLData.setFrom ("TYPEWWWSERVICES");
            oSQLData.setFrom ("WWWORDERS");
            oSQLData.setFrom ("FIRMS");
            oSQLData.setWhere ("TYPEWWWSERVICES.ID=WWWSERV_ID");
            oSQLData.setWhere ("FIRMS.ID=FIRM_ID");
            oSQLData.setWhere ("WWWORDERS.ID=WWWORDER_ID");
            oSQLData.setOrder ("WWWSERVICES.STARTDATE||SERVICE");
            oSQLData.setOrder ("ORD_NUM");
            oRptData.setModeData (oSQLData.getData ());
            oRptData.createColumn ("N заяви", 15);
            oRptData.createColumn ("Абонент", 30);
            oRptData.createColumn ("Дата встановлення послуги", 10);
            oRptData.createColumn ("Послуга", 25);
            oRptData.createColumn ("Примітки", 20);
            strReport = getItems (oDBData, oRptData).getReport ();
        }
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_TITLE, strRptTitle);
        oDBData.setVal (isdb.miscs.dclrs.PAR_REPORT, strReport);
        oDBData.setVal (isdb.miscs.dclrs.PAR_RPT_NUMB, strRptNumb);
        return oDBData;
    }

    /**
     * Приготування графіків.
     * @param oDBData поточни дані об'екта
     * @return сформована HTML сторінка з аплетом для формування діаграми.
     */
    public String graph (dbdata oDBData)
    {
        String strGraph = null;
        String strRptId = oDBData.getVal (isdb.miscs.dclrs.PAR_RPT_ID);

        // Загальна інформація
        if (String.valueOf (strRptId) == "null")
        {
            objwwwservice oWWWServ = new objwwwservice ();
            dbdata oWWWServDBData = new dbdata (oDBData.getSession ());
            oWWWServDBData.setVal (isdb.miscs.dclrs.PAR_OBJECT, oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT));
            oWWWServDBData.setVal (isdb.miscs.dclrs.PAR_DEPT, oDBData.getVal (isdb.miscs.dclrs.PAR_DEPT));

            graphi oWWWServGraph = new graphi (oDBData);
            oWWWServGraph.setPieChart ();
            java.util.Hashtable hashVals = new java.util.Hashtable ();
            hashVals = dbi.getListObj (oDBData.getSession (),
                                       hashVals, "select distinct WWWSERV_ID, SERVICE" +
                                       " from WWWSERVICES, TYPEWWWSERVICES" +
                                       " where TYPEWWWSERVICES.ID = WWWSERV_ID" +
                                       " order by SERVICE");
            java.util.Enumeration enumVals = hashVals.keys ();
            while (enumVals.hasMoreElements ())
            {
                String strKey = (String) enumVals.nextElement ();
                oWWWServGraph.addPieChartElement ((String) hashVals.get (strKey),
                                                  oWWWServ.countItems (oWWWServDBData, "WWWSERVICES.FINISHDATE is null and WWWSERV_ID='" + strKey + "'"),
                                                  oWWWServ.setURLObj (oWWWServDBData,
                                                                      "WWWSERVICES.FINISHDATE%20is%20null%20and%20WWWSERV_ID='" + strKey + "'"));
            }
            strGraph = oWWWServGraph.getApplet ();
        }
        else
        {
            // Статистика встановлених Інтернет послуг за рік
            if (strRptId.equals (isdb.miscs.dclrs.GRAPH_CONN_WWWSERVICE_YEAR))
            {
                graphi oYearWWWServs = new graphi (oDBData);
                oYearWWWServs.setGraphChart (13);

                String [][] strYearValues = new String [13][2];
                strYearValues = getYearCountItems (oDBData, "STARTDATE");
                int iI=13;
                while (--iI >= 0)
                {
                    oYearWWWServs.addGraphChartElement (strYearValues [iI][0],
                                                        strYearValues [iI][1]);
                }
                strGraph = oYearWWWServs.getApplet ();
            }
        }
        return strGraph;
    }

    /**
     * Повернення назви ссилочного об'екта.
     * @param strNameRefKey орігінальна назва ссилочного поля об'екта
     * @param oDBData поточни дані об'екта
     * @return назва ссилочного об'екта
     */
    public String getRefObj (String strNameRefKey, dbdata oDBData)
    {
        if (strNameRefKey.equals ("WWWSERV_ID"))
            return "TYPEWWWSERVICES";
        return super.getRefObj (strNameRefKey, oDBData);
    }

    /**
     * Повідомлення об'екта в залежності від стану.
     * @param iNumberMsg номер повідомлення
     * @param oDBData поточни дані об'екта
     * @return повідомлення про помилку
     */
    public String getMsg (int iNumberMsg, dbdata oDBData)
    {
        if (iNumberMsg == isdb.miscs.dclrs.MSG_NOTSEARCHVAL)      // не знайдено?
            return "Интернет-послуги не встановлени!";
        return super.getMsg (iNumberMsg, oDBData);
    }

    /**
     * Перевірка можливості збереження кількох унікальних записів об'екта.
     * @return true - можливо зберегати кілька унікальних записів, інакше - false
     */
    public boolean isAllowMultipled ()
    {
        return true;
    }
}

