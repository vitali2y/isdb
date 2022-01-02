/**
 * dbi.java
 * ISDBj
 */

package isdb.ifaces;

import java.lang.ClassNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Enumeration;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;

import isdb.objs.dbobj;
import isdb.objs.isdbobj;

import isdb.datas.dbdata;
import isdb.datas.sqldata;
import isdb.datas.pooldata;
import isdb.datas.reportdata;
import isdb.datas.transactiondata;

/**
 * Загальний інтерфейс до БД Oracle.
 * @version 1.0 final, 10-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class dbi
{
    /** Хеш-таблиця для збереження з'еднань користувачів до БД */
    private static Hashtable hashConns = new Hashtable ();
    /** Хеш-таблиця для збереження IP адрес користувачів */
    private static Hashtable hashAddrs = new Hashtable ();
    /** Хеш-таблиця для збереження ідентифікаторів користувачів */
    private static Hashtable hashUsers = new Hashtable ();
    /** Хеш-таблиця для збереження довідкової інформації стосовно користувачів */
    private static Hashtable hashHists = new Hashtable ();
    /** Хеш-таблиця для збереження номерів ідентифікаторів користувачів (PERSON_ID) */
    private static Hashtable hashUsids = new Hashtable ();
    /** Кількість з'еднань користувачів */
    private static int iNumbOfConns = 0;
    /** Інформація стосовно статичної конфігурації */
    private static isdb.ifaces.cfgi oCfg = new isdb.ifaces.cfgi ();
    /** Встановлен чі ні режим тестування? (по замовчанню: false - ні) */
    private static boolean MODE_DEBUG = true;

    /**
     * Реєстрація користувача БД.
     * @param strSsnId номер сесії користувача
     * @param strUsr назва користувача
     * @param strPsw пароль користувача
     * @param strIP IP аддреса користувача
     * @return true - якщо успішна реєстрація, false - якщо ні
     */
    public static boolean setConn (String strSsnId, String strUsr, String strPsw, String strIP)
    {
        Connection connCur = null;

        // реєстрація JDBC драйвера
        try
        {
            Class.forName (oCfg.getOption ("driver"));
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace ();
            return false;
        }
        // параметр для з'еднання з БД, наприклад: jdbc:oracle:thin:@10.140.35.5:1526:zyt1
        String strUrl = "jdbc:oracle:" +
                        oCfg.getOption ("subprotocol") + ":@" +
                        oCfg.getOption ("ipaddress") + ":" +
                        oCfg.getOption ("port") + ":" +
                        oCfg.getOption ("dbinstance");
        try
        {
            strUsr = strUsr.toUpperCase ();
            connCur = DriverManager.getConnection (strUrl, strUsr, strPsw);
            String strCurHist = getUserInfo (connCur);
            if ((String.valueOf (dbdate (connCur, true)) == "null") ||
                    (String.valueOf (strCurHist) == "null")) // успішне чі ні з'еднання з БД?
                return false;
            else
            {
                connCur.setAutoCommit (false);               // режим auto-commit відключен!
                // встановлення системної інформації
                hashConns.put (strSsnId, connCur);
                hashHists.put (strSsnId, strCurHist + ", " + strIP);
                hashUsers.put (strSsnId, strUsr);
                hashUsids.put (strSsnId, getUserId (connCur, strUsr));
                hashAddrs.put (strSsnId, strIP);
                iNumbOfConns++;
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace ();
            return false;
        }
        return true;
    }

    /**
     * Реєстрація користувача БД.
     * @param strSsnId номер сесії користувача
     * @param strUsr назва користувача
     * @param strPsw пароль користувача
     * @return true - якщо успішна реєстрація, false - якщо ні
     * @see #setConn (String, String, String, String)
     */
    public static boolean setConn (String strSsnId, String strUsr, String strPsw)
    {
        return setConn (strSsnId, strUsr, strPsw, isdb.miscs.dclrs.OBJ_NULL);
    }

    /**
      * Перевірка реєстрації користувача БД по номеру сесії.
      * @param strSsnId номер сесії
      * @return true - якщо успішна реєстрація,
      * false - якщо ні
      */
    public static boolean chkConn (String strSsnId)
    {
        if (String.valueOf (strSsnId) != "null")
        {
            if (hashConns != null)
            {
                if (hashConns.containsKey (strSsnId))
                    return true;
            }
        }
        return false;
    }

    /**
     * Перевірка реєстрації користувача БД по номеру сесії
     * @param strSsnId номер сесії
     * @param strAuth
     * @return true - якщо успішна реєстрація,
     * false - якщо ні
     */
    /*
        public static boolean chkConn (String strSsnId, String strAuth)
        {
            if (strAuth == null)
                return false;            // no auth
            if (!strAuth.toUpperCase ().startsWith ("BASIC ")) // we only do BASIC
                return false;

            // Get encoded user and password, comes after "BASIC "
            String strUserPassEncoded = strAuth.substring (6);

            // Decode it, using any base 64 decoder
            String strUserPassDecoded = new String (Base64.decode (strUserPassEncoded));

            // Check our user list to see if that user and password are "allowed"
            String strUsr = strUserPassDecoded.substring (0, strUserPassDecoded.indexOf (':'));
            String strPsw = strUserPassDecoded.substring (strUserPassDecoded.indexOf (':') + 1, strUserPassDecoded.length ());

            if (putConn (strSsnId, strUsr, strPsw, "xx" /* strIP * ))
                return true;
            else
                return false;
        }
    */

    /**
     * Повернення поточної інформації стосовно користувача з БД.
     * @param connId номер з'еднання
     * @return поточна інформація стосовно користувача,
     * null - якщо неуспішно
     */
    private static String getUserInfo (Connection connId)
    {
        String strRetHist = null;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) connId).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + "to_char (SYSDATE, 'DD-MM-YY HH24:MI')||', '||userenv('TERMINAL')||', '||user" + isdb.miscs.dclrs.SQL_FROM + "SYS.DUAL");
            if (rsResult.next ())
                strRetHist = rsResult.getString (1);
        }
        catch (SQLException ex) { ex.printStackTrace (); }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return strRetHist;
    }

    /**
     * Повернення номера ідентифікатора користувача з БД.
     * @param strSesnId поточна сесія
     * @return номер ідентифікатора користувача
     */
    private static String getUserId (String strSesnId)
    {
        return (String) hashUsids.get (strSesnId);
    }

    /**
     * Повернення номера ідентифікатора користувача з БД.
     * @param connId номер з'еднання
     * @param strUsr ідентифікатор користувача
     * @return номер ідентифікатора користувача,
     * null - якщо неуспішна операція
     */
    private static String getUserId (Connection connId, String strUsr)
    {
        String strRetUser = null;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) connId).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + "ID" + isdb.miscs.dclrs.SQL_FROM + "PERSONS" + isdb.miscs.dclrs.SQL_WHERE + "upper(USERNAME)='" + strUsr + "'");
            if (rsResult.next ())
                strRetUser = rsResult.getString (1);
        }
        catch (SQLException ex) { ex.printStackTrace (); }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return strRetUser;
    }


    /**
     * Повернення ідентифікатора користувача з БД.
     * @param strSesnId поточна сесія
     * @return ідентифікатор користувача
     */
    public static String user (String strSesnId)
    {
        return (String) hashUsers.get (strSesnId);
    }

    /**
     * Повернення поточної дати з БД.
     * @param connId номер db з'еднання
     * @param bWithTime true - повернення дати в форматі: DD-MM-YY HH24:MI
     * @return поточна дата з БД в форматі DD-MM-YY HH24:MI або DD-MM-YY,
     * null - якщо неуспішнє з'еднання з БД
     */
    private static String dbdate (Connection connId, boolean bWithTime)
    {
        String strFmtDate = "DD-MM-YY";
        String strRetDate = null;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        if (bWithTime)    // повернути дату в форматі: DD-MM-YY HH24:MI?
            strFmtDate += " HH24:MI";
        try
        {
            stmtStmt = ((Connection) connId).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + "to_char(SYSDATE,'" + strFmtDate + "')" + isdb.miscs.dclrs.SQL_FROM + "SYS.DUAL");
            if (rsResult.next ())
                strRetDate = rsResult.getString (1);
        }
        catch (SQLException ex) { ex.printStackTrace (); }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return strRetDate;
    }

    /**
     * Повернення поточної дати з БД.
     * @param oDBData поточни дані об'екта
     * @return поточна дата з БД в форматі DD-MM-YY HH24:MI
     * null - якщо неуспішнє з'еднання з БД
     * @see #dbdate (Connection, boolean)
     */
    public static String dbdate (dbdata oDBData)
    {
        return dbdate ((Connection) hashConns.get (oDBData.getSession ()), false);
    }

    /**
     * Повернення поточної дати з БД.
     * @param oDBData поточни дані об'екта
     * @param bWithTime якщо true, то повернення дати в форматі: DD-MM-YY HH24:MI
     * @return поточна дата з БД в форматі DD-MM-YY HH24:MI або DD-MM-YY,
     * null - якщо неуспішнє з'еднання з БД
     */
    public static String dbdate (dbdata oDBData, boolean bWithTime)
    {
        return dbdate ((Connection) hashConns.get (oDBData.getSession ()), bWithTime);
    }

    /**
     * Повернення списку періодів дат з БД.
     * @param strNameId назва ідентифікатора
     * @param oDBData поточни дані об'екта
     * @return список періодів дат
     */
    public static String getListDateObj (String strNameId, dbdata oDBData)
    {
        String strRetDate = "";
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + "to_char(SYSDATE,'MM-YYYY')" + isdb.miscs.dclrs.SQL_FROM + "SYS.DUAL");
            if (rsResult.next ())
            {
                String strTmp;
                String strTmpDate = rsResult.getString (1);
                for (int iI = new Integer (strTmpDate.substring (0, 2)).intValue (); iI <= 12; iI++)
                {
                    strTmp = new Integer (iI).toString ();
                    if (iI < 10) strTmp = "0" + strTmp;
                    // strTmp += "-" + strTmpDate.substring (5, 7);
                    // strRetDate += isdb.ifaces.htmli.select_option ("01-" + strTmp + " 00:00", strTmpDate);

                    //           strTmp += "-" + strTmpDate.substring (5, 7);
                    strRetDate += isdb.ifaces.htmli.select_option (
                                    "01-" + strTmp + "-" + strTmpDate.substring (5, 7) + " 00:00", strTmp + "-" + strTmpDate.substring (3, 7));
                }
            }
        }
        catch (SQLException ex) { ex.printStackTrace (); }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return isdb.ifaces.htmli.select (strNameId, strRetDate);
    }

    /**
     * Повернення значення з БД.
     * @param strSesnId номер сесії
     * @param strSqlCmd підготовленна SQL команда
     * @param strDelimRow розподільник записів (наприклад "\n")
     * @return значення після виконання SQL запиту,<BR>
     * null - якщо неуспішний запит
     */
    public static String getValObj (String strSesnId, String strSqlCmd, String strDelimRow)
    {
        String strRtrn = null;
        Statement stmtSQLSttm = null;
        ResultSet rsResult = null;
        try
        {
            stmtSQLSttm = ((Connection) hashConns.get (strSesnId)).createStatement ();
            rsResult = stmtSQLSttm.executeQuery (isdb.miscs.cnv2ukr.cnv2ukr (strSqlCmd));
            if (!rsResult.next ())
                strRtrn = isdb.miscs.dclrs.OBJ_NULL;
            else
            {
                strRtrn = rsResult.getString (1);
                if (String.valueOf (strDelimRow) == "null")
                    strDelimRow = "";
                while (rsResult.next ())
                    strRtrn += strDelimRow + rsResult.getString (1);
            }
        }
        catch (SQLException eold)
        {
            SQLException enew = new SQLException (eold.getMessage () +
                                                  " (exc):\n(" + strSqlCmd + ")");
            enew.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtSQLSttm != null) stmtSQLSttm.close ();
          }
        catch (SQLException exnext) {}
        }
        return strRtrn;
    }

    /**
     * Повернення значення з БД.
     * @param strSesnId номер сесії
     * @param strSqlCmd підготовленна SQL команда
     * @return значення після виконання SQL запиту,<BR>
     * null - якщо неуспішний запит
     * "ыуу №getValObj (String, String, String)
     */
    public static String getValObj (String strSesnId, String strSqlCmd)
    {
        return getValObj (strSesnId, strSqlCmd, null);
    }

    /**
     * Одержання ідентифікатора(ів) запису(ів) об'екта.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @return ідентифікатор(и) об'екта в поточних даних об'екта dbdata, якщо вони були вибрани
     */
    public static dbdata getIdObj (String strObjName, dbdata oDBData)
    {
        String strId = null;
        String strRetId = "";
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            if (dbobj.isReferenced (strObjName))
                strId = dbobj.getReference (strObjName);
            else
                strId = "ID";
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            oDBData.setModeObj (isdb.miscs.dclrs.SQL_SELECT + strId + isdb.miscs.dclrs.SQL_FROM + strObjName +
                                isdb.miscs.dclrs.SQL_WHERE + oDBData.getCriteriaObj ());
            rsResult = stmtStmt.executeQuery (isdb.miscs.cnv2ukr.cnv2ukr (oDBData.getModeObj ()));
            int iI = 0;
            while (rsResult.next ())
            {
                strRetId += rsResult.getString (1) + ",";
                iI++;
            }
            if (iI > 0)
                strRetId = strRetId.substring (0, strRetId.length () - 1);
            oDBData.setResultCount (iI);
        }
        catch (Exception e1)
        {
            Exception e2 = new Exception (e1.getMessage () +
                                          " (exc): " + oDBData.toString () +"\ngetModeObj="+ oDBData.getModeObj () + "\nstrRetId="+ strRetId);
            e2.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        oDBData.setVal (isdbobj.COL_ID, strRetId);
        return oDBData;
    }

    /**
     * Одержання ідентифікатора(ів) запису(ів) об'екта.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param strSqlCmd SQL команда вибірки ідентифікатора(ів)
     * @return enumeration-об'ект, який зберігає (якщо вибрани) ідентифікатор(и) об'екта
     */
    public static Enumeration getIdObj (String strObjName, dbdata oDBData, String strSqlCmd)
    {
        Hashtable hashId = new Hashtable ();
        int iCnt = 0;
        if (String.valueOf (strSqlCmd) == "null")
        {
            strSqlCmd = isdb.miscs.dclrs.SQL_SELECT;
            if (dbobj.isReferenced (strObjName))  // чи є у об'екта ссилка на номер телефона?
                strSqlCmd += dbobj.getReference (strObjName);
            else
                strSqlCmd += "ID";
            strSqlCmd += isdb.miscs.dclrs.SQL_FROM + strObjName +
                         isdb.miscs.dclrs.SQL_WHERE + oDBData.getCriteriaObj ();
        }

        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.cnv2ukr.cnv2ukr (strSqlCmd));
            while (rsResult.next ())
                hashId.put (new Integer (iCnt++), rsResult.getString (1));
            oDBData.setResultCount (iCnt);

            ///
            Exception e66 = new Exception ("getIdObj:" + oDBData.toString () +"\n"+
                                           "sql=" + strSqlCmd +"\ncnt=" + new Integer (iCnt));
            e66.printStackTrace ();
            ///

        }
        catch (SQLException eold)
        {
            SQLException enew = new SQLException (eold.getMessage () +
                                                  " (exc):\nsql=" + strSqlCmd + "\n" + oDBData.toString ());
            enew.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return hashId.elements ();
    }

    /**
     * Одержання ідентифікатора(ів) запису(ів) об'екта.
     * @param strObjName назва поточного об'екта
     * @param strSqlCmd SQL команда вибірки ідентифікатора(ів)
     * @return enumeration-об'ект, який зберігає (якщо вибрани) ідентифікатор(и) об'екта
     * @see #getIdObj (dbobj, dbdata, String)
     */
    public static Enumeration getIdObj (dbdata oDBData, String strQueryVal)
    {
        return getIdObj (null, oDBData, strQueryVal);
    }

    /**
     * Повернення HTML списку після виконання запиту з БД.
     * @param strDefaultId значення об'екта по замовчанню
     * @param oDBData поточни дані об'екта
     * @return сформований HTML список об'екта
     */
    public static String getListObj (String strDefaultId, dbdata oDBData)
    {
        String strId;
        boolean bSelect;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        String strHtmlRslt = isdb.ifaces.htmli.select_option (isdb.miscs.dclrs.OBJ_NULL, isdb.miscs.dclrs.RPT_SELECT_ANY);
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.cnv2ukr.cnv2ukr (oDBData.getModeObj ()));
            // rsResult.setFetchSize (500);
            int iCnt = 0;
            while (rsResult.next ())
            {
                strId = rsResult.getString (1);
                bSelect = false;
                if (strId.equals (strDefaultId))                 // існує потрібне значення?
                    bSelect = true;
                strHtmlRslt += isdb.ifaces.htmli.select_option (strId, rsResult.getString (2), bSelect);

                // "select '<OPTION '||decode(ID, " + dbobj.getDBColumn ("ID", strDefaultId) + ", 'SELECTED ', '')||'VALUE="'||ID||'">'||STATE||'</OPTION>' from PHONESTATES"
                // select "'<OPTION '||decode(ID, '!99', 'SELECTED ', '')||'VALUE="'||ID||'">'||STATE||'</OPTION>' from PHONESTATES
                iCnt++;
            }
            oDBData.setResultCount (iCnt);
        }
        catch (SQLException eold)
        {
            Exception enew = new Exception ("__getListObj (exc): " + oDBData.toString () + "\nsql="+ oDBData.getModeObj ());
            enew.printStackTrace ();
            eold.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }

        ///
        Exception enew = new Exception ("getListObj: " + oDBData.toString ());
        enew.printStackTrace ();
        ///
        return strHtmlRslt;
    }

    /**
     * Повернення HTML списку після виконання запиту з БД.
     * @param oDBData поточни дані об'екта
     * @return сформований HTML список об'екта
     * @see #getListObj (String, dbdata)
     */
    public static String getListObj (dbdata oDBData)
    {
        return getListObj (null, oDBData);
    }

    /**
     * Повернення хеш-таблиці значень після виконання запиту з БД.
     * @param strSsnId
     * @param hashVals
     * @param strSqlCmd
     * @return хеш-таблиця значень
     */
    public static Hashtable getListObj (String strSsnId, Hashtable hashVals, String strSqlCmd)
    {
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (strSsnId)).createStatement ();
            rsResult = stmtStmt.executeQuery (strSqlCmd);
            while (rsResult.next ())
            {
                hashVals.put (rsResult.getString (1), rsResult.getString (2));
            }
        }
        catch (SQLException eold)
        {
            ///
            Exception enew = new Exception ("getListObj (exc):\n(" + hashVals.toString () + "\nsql="+strSqlCmd+ ")");
            enew.printStackTrace ();
            ///
            eold.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }
        return hashVals;
    }

    /**
     * Повернення HTML списку радіо-кнопок (checkbox) після виконання запиту з БД.
     * @param strObjName назва поточного об'екта для створення нового запису
     * @param strObjName назва ссилочного об'екта для вибірки потрібних даних
     * @param strFieldName назва вибіраемого поля об'екта
     * @param oDBData поточни дані об'екта
     * @return сформований HTML список радіо-кнопок існуючих значеннь об'екта
     */
    public static String getCheckBoxListObj (String strObjName, String strRefObjName, String strFieldName, dbdata oDBData)
    {
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        String strHtmlRslt = "";
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            sqldata oSQLData = new sqldata ();
            oSQLData.setColumn (isdb.objs.isdbobj.COL_ID);
            oSQLData.setColumn (strFieldName);
            oSQLData.setFrom (strRefObjName.substring (0, strRefObjName.length() - 3) + "S");
            oSQLData.setOrder (strFieldName);
            rsResult = stmtStmt.executeQuery (oSQLData.getData ());
            int iCnt = 0;
            while (rsResult.next ())
            {
                strHtmlRslt +=
                  isdb.ifaces.htmli.formcheckboxpar (
                    isdb.miscs.dclrs.PAR_ABSENT_IN_LIST +
                    (strObjName.substring (0, strObjName.length () - 1)).toLowerCase () +
                    "(" + strRefObjName + "=" + rsResult.getString (1) + ")", "1",
                    rsResult.getString (2)) + htmli.crlf ();
                iCnt++;
            }
            oDBData.setResultCount (iCnt);
        }
        catch (SQLException eold)
        {
            ///
            Exception enew1 = new Exception ("__getCheckBoxListObj (exc): " + oDBData.toString () + "\nstrHtmlRslt=" + strHtmlRslt);
            enew1.printStackTrace ();
            ///
            eold.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) {}
        }

        ///
        Exception enew = new Exception ("getCheckBoxListObj: " + oDBData.toString () + "\nstrHtmlRslt=" + strHtmlRslt);
        enew.printStackTrace ();
        ///
        return strHtmlRslt;
    }

    /**
     * Очіщення реєстрації користувача
     * @param oDBData поточни дані об'екта
     */
    public static void clearConn (dbdata oDBData)
    {
        String strSsnId = oDBData.getSession ();
        rollbackDataObj (oDBData);
        hashConns.remove (strSsnId);
        hashUsers.remove (strSsnId);
        hashHists.remove (strSsnId);
        hashAddrs.remove (strSsnId);
        hashUsids.remove (strSsnId);
        iNumbOfConns--;
    }

    /**
     * Очіщення всіх реєстрацій користувачів
     */
    public static void clearAllConns ()
    {
        Connection connCurrent = null;
        if (iNumbOfConns > 0)     // ще є відкрити з'еднання з БД?
        {
            Enumeration enumConns = hashConns.elements ();
            while (enumConns.hasMoreElements ())
            {
                try
                {
                    connCurrent = ((Connection) enumConns.nextElement ());
                    connCurrent.rollback ();
                    connCurrent.close ();
                }
                catch (SQLException e) { e.printStackTrace (); }
            }
            hashConns.clear ();
            hashUsers.clear ();
            hashHists.clear ();
            hashAddrs.clear ();
            hashUsids.clear ();
            iNumbOfConns = 0;
        }
    }

    /**
     * Фіксування даних та структури об'екта.
     * @param strObjName назва поточного об'екта
     * @param strMnemoObjName мнемо назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param bSelect признак необхідності вибірки даних об'екта
     * @return оновленні (якщо вибрани) поточни дані об'екта
     */
    public static dbdata getObj (String strObjName, String strMnemoObjName, dbdata oDBData, boolean bSelect)
    {
        if (!dbobj.isInitilized (strMnemoObjName))       // ініціалізован вже цей об'ект?
            getPropObj (strObjName, strMnemoObjName, oDBData);
        if (bSelect)                                     // потрібно вибирати дані?
            return getDataObj (strObjName, strMnemoObjName, oDBData);
        else
            return oDBData;
    }

    /**
     * Фіксування даних та структури об'екта.
     * @param strObjName назва поточного об'екта
     * @param strMnemoObjName мнемо назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @return оновленні (якщо вибрани) поточни дані об'екта
     * @see #getObj (dbobj, dbdata, boolean)
     */
    public static dbdata getObj (String strObjName, String strMnemoObjName, dbdata oDBData)
    {
        return getObj (strObjName, strMnemoObjName, oDBData, true);
    }

    /**
     * Фиксування структури об'екта.
     * @param strObjName назва поточного об'екта
     * @param strMnemoObjName мнемо назва поточного об'екта
     * @param oDBData поточни дані об'екта
     */
    private static void getPropObj (String strObjName, String strMnemoObjName, dbdata oDBData)
    {
        String strName = null;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        String strSsnId = oDBData.getSession ();
        if ((Connection) hashConns.get (strSsnId) == (Connection) null) // час роботи користувача вийшов?
            return;

        // встановлення назви об'екта
        dbobj.setTitle (strObjName, oCfg.getMessage (strObjName));
        try
        {
            stmtStmt = ((Connection) hashConns.get (strSsnId)).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + "*" + isdb.miscs.dclrs.SQL_FROM + strObjName + isdb.miscs.dclrs.SQL_WHERE + "1=0");
            ResultSetMetaData rsmdData = rsResult.getMetaData ();
            for (int iI = 1; iI <= rsmdData.getColumnCount (); iI++)
            {
                strName = rsmdData.getColumnName (iI);

                // встановлення назви та типу поля об'екта
                dbobj.setColumn (strMnemoObjName, strName, new Integer (rsmdData.getColumnType (iI)));

                // встановлення заборони на нульовий запис (якщо потрібно)
                if (rsmdData.isNullable (iI) == rsmdData.columnNoNulls)
                    dbobj.setColumnConstraintNotNull (strMnemoObjName, strName);

                // встановлення назви заголовку об'екта
                dbobj.setColumnLabel (strMnemoObjName, strName, oCfg.getMessage (strObjName + "." + strName));

                // встановлення типу та розміру поля об'екта
                if (rsmdData.getColumnType (iI) == java.sql.Types.TIMESTAMP)
                    dbobj.setColumnSize (strMnemoObjName, strName, new Integer (14));
                else
                    dbobj.setColumnSize (strMnemoObjName, strName, new Integer (rsmdData.getColumnDisplaySize (iI)));
            }
            ///
            // Exception enew = new Exception ("getPropObj: declare " + strObjName);
            // enew.printStackTrace ();
            ///

        }
        catch (SQLException eold)
        {
            Exception enew = new Exception (eold.getMessage () +
                                            " (exc):\nSQL=" + isdb.miscs.dclrs.SQL_SELECT + "*" + isdb.miscs.dclrs.SQL_FROM + strObjName + isdb.miscs.dclrs.SQL_WHERE + "1=0" +
                                            "\ngetMessage ()=" + eold.getMessage () + "\n" +
                                            oDBData.toString ());
            enew.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
    }

    /**
     * Вибірка та фиксування конкретних даних об'екта.
     * @param strObjName назва поточного об'екта
     * @param strMnemoObjName мнемо назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @return вибрані дані об'екта
     */
    private static dbdata getDataObj (String strObjName, String strMnemoObjName, dbdata oDBData)
    {
        String strCol = "";
        String strTmp = "";
        String strSqlCmd = isdb.miscs.dclrs.SQL_SELECT + "*" + isdb.miscs.dclrs.SQL_FROM + strObjName;
        String strId = oDBData.getVal (isdbobj.COL_ID);
        if ((String.valueOf (strId) == "null") ||         // NULL об'ект?
                strId.equals ("") ||
                strId.equals (isdb.miscs.dclrs.OBJ_NULL))
        {
            String strCriteria = oDBData.getCriteriaObj ();
            if ((String.valueOf (strCriteria) == "null") ||   // є крітерії вибірки?
                    strCriteria.equals ("") ||
                    strCriteria.equals (isdb.miscs.dclrs.OBJ_NULL))
                return oDBData;
            else
                strSqlCmd += isdb.miscs.dclrs.SQL_WHERE + strCriteria;
        }
        else
        {
            if (dbobj.isReferenced (strObjName))  // чи є у об'екта ссилка на номер телефона?
                strSqlCmd += isdb.miscs.dclrs.SQL_WHERE + dbobj.getReference (strObjName) + "=" + dbobj.getDBColumn (strObjName, dbobj.getReference (strObjName), strId);
            else
                strSqlCmd += isdb.miscs.dclrs.SQL_WHERE + "ID=" + dbobj.getDBColumn (strObjName, "ID", strId);
        }
        oDBData.setModeObj (strSqlCmd);
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (strSqlCmd);
            if (rsResult.next ())
            {
                oDBData.setResultCount (1);
                Enumeration enumCols = dbobj.getColumns (strObjName);
                for (int iI = 1; iI <= dbobj.getNumberCols (strObjName); iI++)
                {
                    while (enumCols.hasMoreElements ())
                    {
                        strCol = dbobj.getColumnName (strMnemoObjName, (Integer) enumCols.nextElement ());
                        if (String.valueOf (strCol) != "null")
                        {
                            if (dbobj.getColumnType (strMnemoObjName, strCol) == java.sql.Types.TIMESTAMP)
                            {
                                SimpleDateFormat dfDateMask = new SimpleDateFormat ("dd-MM-yy");
                                SimpleDateFormat dfTimeMask = new SimpleDateFormat ("HH:mm");
                                Date dateFieldDate = rsResult.getDate (strCol);
                                Date dateFieldTime = rsResult.getTime (strCol);
                                strTmp = null;
                                if (dateFieldDate != null)
                                {
                                    if (dfDateMask.format (dateFieldDate).equals ("01-01-70"))  // нульова дата?
                                        strTmp = isdb.miscs.dclrs.NOTINSTALL;
                                    else
                                    {
                                        strTmp = dfTimeMask.format (dateFieldTime);
                                        if (strTmp.endsWith ("0:00"))              // час не встановлено?
                                            strTmp = "";
                                        else
                                            strTmp = " " + strTmp;
                                        strTmp = dfDateMask.format (dateFieldDate) + strTmp;
                                    }
                                }
                            }
                            else
                                strTmp = rsResult.getString (strCol);
                            if (String.valueOf (strTmp) == "null")   // нема значення?
                                strTmp = isdb.miscs.dclrs.OBJ_NULL;

                            // збереження полученного значення
                            oDBData.setVal (strCol, strTmp);
                        }
                    }
                }
            }
        }
        catch (SQLException eold)
        {
            SQLException enew = new SQLException (eold.getMessage () +
                                                  " (exc): " + strSqlCmd + "\n" + oDBData.toString ());
            enew.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        return oDBData;
    }

    /**
     * Одержання інформації про ссилочний об'ект
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param strRefId ідентифікатор ссилочного об'ектe
     * @return назва ссилочного об'екта
     */
    public static String getRefObj (String strObjName, dbdata oDBData, String strRefId)
    {
        String strVal = null;
        String strSqlCmd = isdb.miscs.dclrs.SQL_SELECT +
                           "replace (COLUMN_NAME, '_ID', 'S')" +
                           isdb.miscs.dclrs.SQL_FROM + "ALL_CONS_COLUMNS" +
                           isdb.miscs.dclrs.SQL_WHERE + "CONSTRAINT_NAME = 'FK_" +
                           strObjName + "_" + strRefId + "'";

        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (strSqlCmd);
            if (rsResult.next ())
                strVal = rsResult.getString (1);
        }
        catch (SQLException e)
        {
            Exception e66 = new Exception ("dbi.getRefObj: (exc) " + strSqlCmd +"\n"+strRefId+"="+strVal+"\n"+oDBData.toString ());
            e66.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception e = new Exception ("dbi.getRefObj: sql="+ strSqlCmd +"\n"+strRefId+"="+strVal+"\n"+oDBData.toString ());
            e.printStackTrace ();
        }
        return strVal;
    }

    /**
     * Повернення кількості записів об'екта по крітеріям.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @return кількость записів об'екта
     */
    public static String getCountItems (String strObjName, dbdata oDBData)
    {
        String strCount = "?";
        String strCriteria = oDBData.getCriteriaObj ();
        if ((String.valueOf (strCriteria) == "null") ||
                strCriteria.equals (""))
            strCriteria = "";
        else
            strCriteria = isdb.miscs.dclrs.SQL_WHERE + strCriteria;
        String strSqlCmd = isdb.miscs.dclrs.SQL_SELECT + "count(1)" + isdb.miscs.dclrs.SQL_FROM + strObjName + strCriteria;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (strSqlCmd);
            if (rsResult.next ())
                strCount = rsResult.getString (1);
        }
        catch (SQLException ex)
        {
            Exception e = new Exception ("dbi.getCountItems: (exc)" + strSqlCmd + "\n" + oDBData.toString () +"\nstrCount=" + strCount);
            e.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        return strCount;
    }

    /**
     * Повернення кількості записів об'екта по крітеріям за рік.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param strFieldName назва поля вибырки
     * @return кількость записів об'екта по крітеріям за рік
     */
    public static String [][] getYearCountItems (String strObjName, dbdata oDBData, String strFieldName)
    {
        String strCurMonth = null;
        String [][] strYearValues = new String [13][2];
        String strCriteria = oDBData.getCriteriaObj ();
        if ((strCriteria == null) ||
                strCriteria.equals (""))
            strCriteria = "";
        else
            strCriteria = " and " + strCriteria;
        int iI=14;
        while (--iI > 0)
        {
            strCurMonth = new Integer (iI).toString ();
            oDBData.setCriteriaObj ("to_char(" + strFieldName + ",'DD.MM.YY') like '__.'||to_char(add_months (SYSDATE, -" +
                                    strCurMonth +
                                    "), 'MM.YY')" +
                                    strCriteria);
            strYearValues [iI - 1][0] =
              getValObj (oDBData.getSession (), isdb.miscs.dclrs.SQL_SELECT + "to_char(add_months (SYSDATE, -" + strCurMonth + "),'MM.YY')" + isdb.miscs.dclrs.SQL_FROM + "DUAL");
            strYearValues [iI - 1][1] =
              getCountItems (strObjName, oDBData);
        }
        return strYearValues;
    }

    /**
     * Повернення значеннь об'ектів в форматі звіту для друку.
     * @param oDBData поточни дані об'екта
     * @param reportdata-значення об'екта в форматі звіту HTML
     * @return reportdata-значення об'екта в форматі звіту HTML
     */
    public static reportdata getItems (dbdata oDBData, reportdata oRptData)
    {
        int iRow = 0;
        int iColumnCount = 0;
        boolean bAllColumns = false;
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        try
        {
            for (int iColumn = 0; iColumn <= oRptData.getNumberOfColumns (); iColumn++)
            {
                if (MODE_DEBUG)                // тестувал. режим?
                {
                    Exception e99 = new Exception ("oRptData.getModeData="+oRptData.getModeData (iColumn));
                    e99.printStackTrace ();
                }
                if (String.valueOf (oRptData.getModeData (iColumn)) != "null") // є критерій вибірки для колонки?
                {
                    ///
                    // Exception e999 = new Exception ("sql=" + oRptData.getModeData (iColumn));
                    // e999.printStackTrace ();
                    ///

                    stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
                    rsResult = stmtStmt.executeQuery (oRptData.getModeData (iColumn));
                    ResultSetMetaData rsmdData = rsResult.getMetaData ();
                    iColumnCount = oRptData.getOutputSize (iColumn);
                    if (iColumnCount == 0)           // примусово не встановлена?
                        iColumnCount = rsmdData.getColumnCount ();
                    iRow = 0;

                    ///
                    // Exception e8 = new Exception ("1: iColumn=" + new Integer (iColumn).toString () +", getColumnCount="+rsmdData.getColumnCount () +"\ngetModeData (iColumn)="+ oRptData.getModeData (iColumn));
                    // e8.printStackTrace ();
                    ///

                    if (iColumn == 0)     // критерій вибірки зразу для всіх полів?
                    {
                        iColumn = 1;
                        bAllColumns = true;
                    }
                    while (rsResult.next ())
                    {
                        iRow++;
                        for (int iJ = 1; iJ <= iColumnCount; iJ++)
                        {
                            ///
                            /*
                                                        Exception e9 = new Exception ("2: iJ + iColumn - 1=" + new Integer (iJ + iColumn - 1).toString () +
                                                                                      "\niRow=" + new Integer (iRow).toString () +
                                                                                      "\nrsResult.getString (iJ)="+ rsResult.getString (iJ));
                                                        e9.printStackTrace ();
                            */
                            ///

                            oRptData.setVal (iJ + iColumn - 1, iRow, rsResult.getString (iJ));
                        }
                    }
                    oDBData.setResultCount (iRow);
                    rsResult.close ();
                    stmtStmt.close ();
                    if (bAllColumns)  // критерій вибірки зразу для всіх полів?
                        break;
                    iColumn += iColumnCount - 1;
                }
            }
        }
        catch (SQLException ex)
        {
            ///
            Exception e = new Exception ("dbi.getItems 2 (exc): " + oDBData.toString () +"\nsql: "+oRptData.getModeData ());
            e.printStackTrace ();
            ///
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        return oRptData;
    }

    /**
     * Повернення значеннь об'ектів по крітеріям.
     * @param oDBData поточни дані об'екта
     * @return вибрані значення об'екта в форматі строки HTML
     */
    public static String getItems (dbdata oDBData)
    {
        int iI = 0;
        String strItem = "";
        Statement stmtStmt = null;
        ResultSet rsResult = null;
        String strCriteria = oDBData.getCriteriaObj ();
        if (String.valueOf (strCriteria) == "null")
            strCriteria = "";
        else
            strCriteria = " " + strCriteria;
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (oDBData.getModeObj () + strCriteria);
            while (rsResult.next ())
            {
                strItem += rsResult.getString (1) + isdb.ifaces.htmli.crlf ();
                iI++;
            }
            if (!strItem.equals (""))
                strItem = strItem.substring (0, strItem.length () - 4);
            oDBData.setResultCount (iI);
        }
        catch (SQLException ex)
        {
            Exception e = new Exception (ex.getMessage () + " dbi.getItems (exc): " + oDBData.getModeObj () + strCriteria + "\niI=" + iI  + "\n" + oDBData.toString ());
            e.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        return strItem;
    }

    /**
     * Одержання значення послідуючого номера послідовності об'екта
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param strSeqName орігінальна назва послідовності
     * @return значення послідуючого номера послідовності об'екта
     */
    public static String getNextSeqNumber (String strObjName, dbdata oDBData, String strSeqName)
    {
        Statement stmtStmt = null;
        String strResult = null;
        ResultSet rsResult = null;
        if (String.valueOf (strSeqName) != "null")   // орігінальна назва послідовності?
            strSeqName += "SEQ";
        else
            strSeqName = dbobj.getSeqName (strObjName);
        try
        {
            stmtStmt = ((Connection) hashConns.get (oDBData.getSession ())).createStatement ();
            rsResult = stmtStmt.executeQuery (isdb.miscs.dclrs.SQL_SELECT + strSeqName + ".NEXTVAL" + isdb.miscs.dclrs.SQL_FROM + "DUAL");
            if (rsResult.next ())
                strResult = rsResult.getString (1);
        }
        catch (SQLException e)
        {
            Exception e2 = new Exception ("dbi.getNextSeqNumber: " + isdb.miscs.dclrs.SQL_SELECT + strSeqName + ".NEXTVAL" + isdb.miscs.dclrs.SQL_FROM + "DUAL" + "\n" + oDBData.toString () );
            e2.printStackTrace ();
        }
        finally
        {
        try
          {
            if (rsResult != null) rsResult.close ();
            if (stmtStmt != null) stmtStmt.close ();
          }
        catch (SQLException exnext) { }
        }
        rsResult = null;
        stmtStmt = null;
        return strResult;
    }

    /**
     * Проведення транзакції.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param oTransactionData дані об'екта для додаткових транзакцій
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     */
    public static void writeDataObj (String strObjName, dbdata oDBData, pooldata oPoolData, transactiondata oTransactionData)
    {
        int iNumItem = 0;
        String strKey = null;
        String strVal = null;
        String strTmp = null;
        String strTmp1 = "";
        String strTmp2 = "";
        String strSqlCmd = "";
        String strSsnId = oDBData.getSession ();
        String strRefIdMaster = null;
        Statement stmtStmt1 = null;
        Statement stmtStmt2 = null;
        Statement stmtStmt3 = null;
        try
        {
            if (oTransactionData != null)         // є дані для додаткових pre транзакцій?
            {

                // одержання даних для проведення додаткових транзакцій поперед головної та їх проведення
                Enumeration enumPreTransaction = oTransactionData.getPreTransactions ();
                if (enumPreTransaction != null)
                {
                    stmtStmt1 = ((Connection) hashConns.get (strSsnId)).createStatement ();
                    while (enumPreTransaction.hasMoreElements ())
                        stmtStmt1.executeUpdate (isdb.miscs.cnv2ukr.cnv2ukr ((String) enumPreTransaction.nextElement ()));
                }
            }

            // встановлення фіксованої інформації
            if (dbobj.isPresent (strObjName, isdb.miscs.dclrs.PAR_PERSON_ID))
                oDBData.setVal (isdb.miscs.dclrs.PAR_PERSON_ID, getUserId (strSsnId));
            if (dbobj.isPresent (strObjName, isdb.miscs.dclrs.PAR_STATEDATE))
                oDBData.setVal (isdb.miscs.dclrs.PAR_STATEDATE, dbdate ((Connection) hashConns.get (strSsnId), true));

            // одержання назв полів
            Enumeration enumPars = oDBData.getKeys ();

            // режим INSERT
            if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_INSCOMMIT))
            {

                // підготовлення ідентифікатора та його значення
                strTmp1 = "ID,";
                strVal = oPoolData.getVal (strSsnId, (oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT) + "_ID").toUpperCase ());
                if (String.valueOf (strVal) == "null")
                {
                    isdbobj oRefIdMaster = null;

                    // будова об'екта
                    try
                    {
                        oRefIdMaster = (isdbobj) Class.forName ("isdb.objs.obj" + oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT)).newInstance ();
                    }
                    catch (ClassNotFoundException ex)
                    { ex.printStackTrace (); }
                    catch (ClassCastException ex)
                    { ex.printStackTrace (); }
                    catch (IllegalAccessException  ex)
                    { ex.printStackTrace (); }
                    catch (InstantiationException ex)
                    { ex.printStackTrace (); }
                    dbdata oRefIdMasterDBData = new dbdata (strSsnId);
                    oRefIdMasterDBData = oDBData.cloneData ();
                    oRefIdMaster.describe (oRefIdMasterDBData);
                    strRefIdMaster = oRefIdMaster.getNameRefIdMaster (oDBData);
                    strVal = oPoolData.getVal (strSsnId, strRefIdMaster);

                }
                if (String.valueOf (strVal) == "null")
                    strVal = oDBData.getVal (isdbobj.COL_ID);
                strTmp2 = strVal + ",";
                if (MODE_DEBUG)                // тестувал. режим?
                {
                    Exception e66 = new Exception ("RefIdMaster: ID=" +
                                                   oPoolData.getVal (strSsnId, (oDBData.getVal (isdb.miscs.dclrs.PAR_OBJECT) + "_ID").toUpperCase ()) +
                                                   "\nstrRefIdMaster=" + strRefIdMaster + ", strVal=" + strVal +
                                                   "\n"+ oDBData.toString ());
                    e66.printStackTrace ();
                }

                // сканування параметрів SQL коианди
                while (enumPars.hasMoreElements ())
                {
                    strKey = (String) enumPars.nextElement ();
                    if (strKey.equals (isdbobj.COL_ID) ||
                            strKey.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX));
                    else
                    {
                        strTmp1 += strKey + ",";
                        strVal = oDBData.getVal (strKey);

                        // поточне знечення є, чи витягнуте з пула збережених раніше даних?
                        if ((String.valueOf (strVal) == "null") ||
                                strVal.equals ("") ||
                                strVal.equals (isdb.miscs.dclrs.OBJ_NULL))
                            strVal = oPoolData.getVal (strSsnId, strKey);
                        if (String.valueOf (strVal) == "null")
                            strVal = "";
                        strTmp2 += dbobj.setDBColumn (strObjName, strKey, strVal) + ",";
                    }
                }
                // додавання ссилочного поля
                strTmp = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
                if (dbobj.isReferenced (strObjName) &&
                        (String.valueOf (strTmp) != "null"))
                {
                    strTmp1 += dbobj.getReference (strObjName) + ",";
                    strTmp2 += strTmp + ",";
                }

                strSqlCmd = isdb.miscs.dclrs.SQL_INSERT + strObjName +
                            " (" + strTmp1.substring (0, strTmp1.length () - 1) + ") values (" +
                            strTmp2.substring (0, strTmp2.length () - 1) + ")";
            }
            else
            {
                // режим UPDATE
                if (oDBData.isRegime (isdb.miscs.dclrs.REGIME_UPDCOMMIT))
                {
                    while (enumPars.hasMoreElements ())
                    {
                        strKey = (String) enumPars.nextElement ();
                        if (strKey.equals (isdbobj.COL_ID) ||
                                strKey.startsWith (isdb.miscs.dclrs.SPECIAL_PARAM_PREFIX));
                        else
                        {
                            strVal = oDBData.getVal (strKey);
                            if ((String.valueOf (strVal) == "null") ||
                                    strVal.equals (isdb.miscs.dclrs.OBJ_NULL))
                                strVal = "";

                            // поточне знечення є, чи витягнути з пула збережених раніше даних?
                            if ((String.valueOf (strVal) == "null") ||
                                    strVal.equals (""))
                                strVal = oPoolData.getVal (strSsnId, strKey);
                            if (String.valueOf (strVal) == "null")
                                strVal = "";
                            strTmp1 += strKey + "=" + dbobj.setDBColumn (strObjName, strKey, strVal) + ",";
                        }
                    }
                    strTmp = oDBData.getVal (isdb.miscs.dclrs.PAR_ID);
                    if ((String.valueOf (strTmp) == "null") ||
                            strTmp.equals (isdb.miscs.dclrs.OBJ_NULL))
                        strTmp = oDBData.getVal (isdbobj.COL_ID);
                    strSqlCmd = isdb.miscs.dclrs.SQL_UPDATE + strObjName +
                                isdb.miscs.dclrs.SQL_SET + strTmp1.substring (0, strTmp1.length () - 1) +
                                isdb.miscs.dclrs.SQL_WHERE + dbobj.getReference (strObjName) + "=" + strTmp;
                }
                else
                {
                    oDBData.setResultCount (0);
                    return;
                }
            }
            stmtStmt2 = ((Connection) hashConns.get (strSsnId)).createStatement ();
            iNumItem = stmtStmt2.executeUpdate (isdb.miscs.cnv2ukr.cnv2ukr (strSqlCmd));
            if (oTransactionData != null)         // є дані для додаткових post транзакцій?
            {

                // одержання даних для проведення додаткових транзакцій після головної та їх проведення
                Enumeration enumPostTransaction = oTransactionData.getPostTransactions ();
                if (enumPostTransaction != null)
                {
                    stmtStmt3 = ((Connection) hashConns.get (strSsnId)).createStatement ();
                    while (enumPostTransaction.hasMoreElements ())
                        stmtStmt3.executeUpdate (isdb.miscs.cnv2ukr.cnv2ukr ((String) enumPostTransaction.nextElement ()));
                }
            }
            oDBData.setModeObj (strSqlCmd);
            oDBData.setResultCount (iNumItem);
        }
        catch (SQLException eold)
        {
            SQLException enew = new SQLException (eold.getMessage () +
                                                  "writeDataObj (exc):\n" + strSqlCmd + "\n" + oDBData.toString ());
            enew.printStackTrace ();
            oDBData.setResultCount (-1);
        }
        finally
        {
        try
          {
            if (stmtStmt1 != null) stmtStmt1.close ();
            if (stmtStmt2 != null) stmtStmt2.close ();
            if (stmtStmt3 != null) stmtStmt3.close ();
          }
        catch (SQLException exnext) {}
        }
        if (MODE_DEBUG)                // тестувал. режим?
        {
            Exception enew = new Exception ("writeDataObj: sql=" + strSqlCmd + "\n" + oDBData.toString ());
            enew.printStackTrace ();
        }
    }

    /**
     * Проведення транзакції.
     * @param strObjName назва поточного об'екта
     * @param oDBData поточни дані об'екта
     * @param oPoolData пул зберегаемих значеннь використовуемих в транзакцыях об'ектів
     @ @see #writeDataObj (dbobj, dbdata, pooldata, transactiondata)
     */
    public static void writeDataObj (String strObjName, dbdata oDBData, pooldata oPoolData)
    {
        writeDataObj (strObjName, oDBData, oPoolData, null);
    }

    /**
     * Кінцева фіксація даних об'екта.
     * @param oDBData поточни дані об'екта
     */
    public static void commitDataObj (dbdata oDBData)
    {
        try
        {
            ((Connection) hashConns.get (oDBData.getSession ())).commit ();
        }
        catch (SQLException e) { e.printStackTrace (); }
    }

    /**
     * Откат даних об'екта.
     * @param oDBData поточни дані об'екта
     */
    public static void rollbackDataObj (dbdata oDBData)
    {
        if ((Connection) hashConns.get (oDBData.getSession ()) != null)
        {
            try
            {
                ((Connection) hashConns.get (oDBData.getSession ())).rollback ();
            }
            catch (SQLException e) { e.printStackTrace (); }
        }
    }
}

