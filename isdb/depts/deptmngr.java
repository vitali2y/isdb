/**
 * deptmngr.java
 * ISDBj
 */

package isdb.depts;

import isdb.depts.deptobj;

/**
 * Клас адміністратівного відділу.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class deptmngr extends deptobj
{
    /**
     * Конструктор.
     */
    public deptmngr ()
    {
        super (isdb.miscs.dclrs.DEPT_MNGR);
        putLogo (179, 142);
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу
     */
    public String getName ()
    {
        return "Адміністрація";
    }
}

