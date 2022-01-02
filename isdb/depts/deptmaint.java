/**
 * deptmaint.java
 * ISDBj
 */

package isdb.depts;

import isdb.depts.deptobj;

/**
 * Клас відділу технічного забеспечення.
 * @version 1.0 final, 27-III-2000
 * @author Yermolenko Vitaly, Utel-Zhitomir
 */
public class deptmaint extends deptobj
{
    /**
     * Конструктор.
     */
    public deptmaint ()
    {
        super (isdb.miscs.dclrs.DEPT_MAINT);
        putLogo (179, 133);
    }

    /**
     * Повернення назви відділу.
     * @return назва відділу
     */
    public String getName ()
    {
        return "Відділ технічного обслуговування";
    }
}

