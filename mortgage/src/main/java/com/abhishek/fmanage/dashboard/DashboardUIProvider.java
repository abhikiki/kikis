package com.abhishek.fmanage.dashboard;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class DashboardUIProvider extends UIProvider
{
    private static final long serialVersionUID = -5419308728686499083L;

    private static final String MOBILE = "mobile";
    private static final String IPAD = "ipad";

    /**
     * Get the UI class based on the device type which is accessing the application
     */
    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event)
    {
        if (event.getRequest().getParameter(MOBILE) != null
            && event.getRequest().getParameter(MOBILE).equals("false"))
        {
            return DashboardUI.class;
        }
        if (event.getRequest().getHeader("user-agent").toLowerCase().contains(MOBILE)
            && !event.getRequest().getHeader("user-agent").toLowerCase().contains(IPAD))
        {
            return MobileCheckUI.class;
        }
        return DashboardUI.class;
    }
}