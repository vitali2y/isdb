/**
 * menudata.java
 * ISDBj
 */

package isdb.datas;

import java.util.Hashtable;

/**
 * Поточні дані меню.
 * @version 1.0 final, 11-V-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class menudata
{
    /** Признак зарезервованого пункту меню */
    private static String RESERVED_ITEM = isdb.miscs.dclrs.SPECIAL_SERVICE_PREFIX + isdb.miscs.dclrs.NO;
    /** Признак зарезервованого пункту меню */
    private static String RESERVED_MESSAGE = " (зарезерв.)";
    /** Файл відображення зарезервованого пункту меню */
    private static String RESERVED_FILE = "/reconstr.html";

    /** Назва відділу */
    private String strDept;
    /** Поточний URL меню */
    private Hashtable hashAppl;
    /** Поточна шапка меню */
    private Hashtable hashTitl;
    /** Ознака відкриття в новому вікні */
    private Hashtable hashNewWnd;
    /** Ознака підтвердження виконання пункту меню */
    private Hashtable hashConfirmExec;
    /** Кількість пунктів меню */
    private int iCountItem;
    /** Поточний пункт меню */
    private int iPosItem;

    /**
     * Конструктор.
     * @param strDept назва відділу
     */
    public menudata (String strDept)
    {
        this.strDept = strDept;
        hashAppl = new Hashtable ();
        hashTitl = new Hashtable ();
        hashNewWnd = new Hashtable ();
        hashConfirmExec = new Hashtable ();
        iCountItem = -1;
        initMenu ();
    }

    /**
     * Первин. встановлення номеру поточн. пункту меню
     */
    public void initMenu ()
    {
        iPosItem = -1;
    }

    /**
     * Встановлення наступного пункту меню.
     * @param strAppl назва сервлету
     * @param strParams параметри сервлету
     * @param strTitle назва пункту меню
     */
    public void setMenuItem (String strAppl, String strParams, String strTitle)
    {
        hashTitl.put (new Integer (++iCountItem), strTitle);
        if (String.valueOf (strAppl) == "null")     // зарезервовано?
            hashAppl.put (new Integer (iCountItem), RESERVED_ITEM);
        else
            hashAppl.put (new Integer (iCountItem), strAppl + "?" + strParams);
        hashNewWnd.put (new Integer (iCountItem), isdb.miscs.dclrs.NO);
        hashConfirmExec.put (new Integer (iCountItem), isdb.miscs.dclrs.NO);
    }

    /**
     * Встановлення наступного пункту меню зарезервованим.
     * @param strTitle назва зарезервованого пункту меню
     * @see #setMenuItem (String, String, String)
     */
    public void setMenuItem (String strTitle)
    {
        setMenuItem (null, null, strTitle);
    }

    /**
     * Повернення признака, чі є ще запис в меню.
     * <P>Якщо true, то встановлюється наступний запис меню.
     * @return true - ще є запис в меню
     */
    public boolean isPresentNextItem ()
    {
        if (iPosItem + 1 <= iCountItem)
        {
            ++iPosItem;
            return true;
        }
        return false;
    }

    /**
     * Повернення поточного URL пункту меню.
     * @return поточний URL пункт меню
     */
    public String getURL ()
    {
        String strAppl = (String) hashAppl.get (new Integer (iPosItem));
        if (strAppl.equals (RESERVED_ITEM))    // зарезервовано?
            return RESERVED_FILE;
        else
            return strAppl + "&" + isdb.miscs.dclrs.PAR_DEPT + "=" + this.strDept;
    }

    /**
     * Повернення назви пункту меню.
     * @return назва пункту меню
     */
    public String getTitle ()
    {
        String strAppl = (String) hashAppl.get (new Integer (iPosItem));
        String strTitle = (String) hashTitl.get (new Integer (iPosItem));
        if (strAppl.equals (RESERVED_ITEM))    // зарезервовано?
            strTitle = strTitle + isdb.ifaces.htmli.font (RESERVED_MESSAGE, "red");
        else
            strTitle = isdb.ifaces.htmli.bold (strTitle);
        return strTitle;
    }

    /**
     * Встановлення нознаки відкриття поточного пункту меню в новому вікні.
     */
    public void setOpenNewWnd ()
    {
        hashNewWnd.put (new Integer (iCountItem), isdb.miscs.dclrs.YES);
    }

    /**
     * Повернення ознаки чі відкривати пункт меню в новому вікні.
     * @return ознака відкриття пункту меню в новому вікні
     * (true - відкривати пункт меню в новому вікні).
     * <P>По замовчанню - false, не встановлено (не потрібно відкривати).
     */
    public boolean isOpenNewWnd ()
    {
        if (((String) hashNewWnd.get (new Integer (iPosItem))).equals (isdb.miscs.dclrs.YES))   // відкривати URL в новоиу вікні?
            return true;
        else
            return false;
    }

    /**
     * Встановлення ознаки підтвердження виконання пункту меню.
     */
    public void setConfirmExec ()
    {
        hashConfirmExec.put (new Integer (iCountItem), isdb.miscs.dclrs.YES);
    }

    /**
     * Повернення ознаки чі підтвердити виконання пункту меню.
     * @return ознака підтвердження виконання пункту меню
     * (true - підтвердити виконання).
     * <P>По замовчанню - false, не підтверджувати.
     */
    public boolean isConfirmExec ()
    {
        if (((String) hashConfirmExec.get (new Integer (iPosItem))).equals (isdb.miscs.dclrs.YES))   //  підтвердити виконання пункту?
            return true;
        else
            return false;
    }

    /**
     * Відображення об'екта в текстовій формі.
     * @return параметри об'екту в текстовій формі
     */
    public String toString ()
    {
        int iKey = 0;
        Integer iTmpKey = new Integer (0);
        String strTmp = "\nmenudata:\n";
        while (iKey++ < iCountItem)
        {
            iTmpKey = new Integer (iKey);
            strTmp += (String) hashAppl.get (iTmpKey) + ", hashNewWnd=" +
                      (String) hashNewWnd.get (iTmpKey) + ", hashConfirmExec=" +
                      (String) hashConfirmExec.get (iTmpKey) + "\n";
        }
        return strTmp;
    }
}

