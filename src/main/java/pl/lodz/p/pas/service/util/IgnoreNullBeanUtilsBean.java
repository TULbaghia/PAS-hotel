package pl.lodz.p.pas.service.util;

import org.apache.commons.beanutils.BeanUtilsBean;

public class IgnoreNullBeanUtilsBean extends BeanUtilsBean {
    @Override
    public void copyProperties(final Object dest, final Object orig) {
        final Object value = getPropertyUtils().getSimpleProperty(orig, name);
        if (value != null) {
            copyProperty(dest, name, value);
        }
    }
}