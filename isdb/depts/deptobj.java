/**
 * deptobj.java
 * ISDBj
 */

package isdb.depts;

import isdb.datas.menudata;

/**
 * Загальний клас для створення відділів Утел,
 * які використовують БД ISDB.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class deptobj
{
    /** Назва відділу */
    private /* static */ String strDept;
    /** Ширина файла графічного зображення */
    private int iWidth;
    /** Висота файла графічного зображення */
    private int iHeight;
    /** Назва графічного файла logo */
    private String strLogo;

    /**
     * Конструктор.
     * @param strDept назва відділу
     */
    public deptobj (String strDept)
    {
        this.strDept = strDept;
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу
     * @see "Наслідуемі об'екти"
     */
    public String getName ()
    {
        return null;
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу.
     */
    public String getDept ()
    {
        return this.strDept;
    }

    /**
      * Загальна інформація.
      * @return URL адреса файлу с загальною інформаціею стосовно відділу.
      */
    public String getInfo ()
    {
        return isdb.miscs.dclrs.OBJ_INFO + "_" + this.strDept + ".html";
    }

    /**
     * Встановлення графічного зображення (logo) відділу.
     * @see "Наслідуемі об'екти"
     */
    public void putLogo ()
    {
    }

    /**
     * Встановлення графічного зображення (logo) відділу.
     * @param strLogo назва графічного файла с графічним зображенням logo відділу
     * @param iWidth ширина файла графічного зображення
     * @param iHeight висота файла графічного зображення
     */
    public void putLogo (String strLogo, int iWidth, int iHeight)
    {
        this.strLogo = strLogo;
        this.iWidth = iWidth;
        this.iHeight = iHeight;
    }

    /**
     * Встановлення графічного зображення (logo) відділу (по замовчанню - gif файл).
     * @param iWidth ширина файла графічного зображення
     * @param iHeight висота файла графічного зображення
     * @see #putLogo (String, int, int)
     */
    public void putLogo (int iWidth, int iHeight)
    {
        putLogo (getDept () + ".gif", iWidth, iHeight);
    }

    /**
     * Повернення графічного зображення logo відділу.
     * @return графічне зображення logo відділу (в форматі HTML).
     */
    public String getLogo ()
    {
        return isdb.ifaces.htmli.image (
                 this.strLogo, this.iWidth, this.iHeight, getDept ());
    }

    /**
     * Витягнення класа відділу по назві відділу.
     * @param strDept назва відділу
     * @return створений клас відділу
     */
    public static deptobj getDept (String strDept)
        throws ClassNotFoundException, ClassCastException, IllegalAccessException, InstantiationException
    {
        try
        {
            return ((isdb.depts.deptobj) Class.forName ("isdb.depts.dept" + strDept).newInstance ());
        }
        catch (ClassNotFoundException ex)
        {
            throw new ClassNotFoundException ("ClassNotFoundException, " + isdb.miscs.dclrs.PAR_DEPT + "=" + strDept);
        }
        catch (ClassCastException ex)
        {
            throw new ClassCastException ("ClassCastException, " + isdb.miscs.dclrs.PAR_DEPT + "=" + strDept);
        }
        catch (IllegalAccessException ex)
        {
            throw new IllegalAccessException ("IllegalAccessException, " + isdb.miscs.dclrs.PAR_DEPT + "=" + strDept);
        }
        catch (InstantiationException ex)
        {
            throw new InstantiationException ("InstantiationException, " + isdb.miscs.dclrs.PAR_DEPT + "=" + strDept);
        }
    }

    /**
     * Витягнення об'екта меню по назві меню.
     * @param strMenu назва меню
     * @return сформований об'ект меню
     */
    public deptobj getMenu (String strMenu)
    throws ClassNotFoundException, ClassCastException, IllegalAccessException, InstantiationException
    {
        try
        {
            return ((isdb.depts.deptobj) Class.forName ("isdb.depts." + getDept () + ".menu" + strMenu).newInstance ());
        }
        catch (ClassNotFoundException ex)
        {
            throw new ClassNotFoundException ("ClassNotFoundException, " + isdb.miscs.dclrs.PAR_MENU + "=" + strMenu);
        }
        catch (ClassCastException ex)
        {
            throw new ClassCastException ("ClassCastException, " + isdb.miscs.dclrs.PAR_MENU + "=" + strMenu);
        }
        catch (IllegalAccessException ex)
        {
            throw new IllegalAccessException ("IllegalAccessException, " + isdb.miscs.dclrs.PAR_MENU + "=" + strMenu);
        }
        catch (InstantiationException ex)
        {
            throw new InstantiationException ("InstantiationException, " + isdb.miscs.dclrs.PAR_MENU + "=" + strMenu);
        }
    }

    /**
     * Витягнення поточних даних об'екта меню.
     * @return сформовані поточні дані об'екта меню
     * @see "Наслідуемі об'екти"
     */
    public menudata getData ()
    {
        return null;
    }
}

