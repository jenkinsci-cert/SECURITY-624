package org.jenkinsci.plugins.toolnamefilter;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Descriptor;
import hudson.model.JDK;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;
import hudson.tools.ToolDescriptor;
import hudson.tools.ToolInstallation;
import jenkins.model.Jenkins;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Security624 {
    @Extension
    public static class ToolNameFilter extends SaveableListener {
        @Override public void onChange(Saveable o, XmlFile file) {
            // not just ToolDescriptor: https://github.com/jenkinsci/ant-plugin/blob/26626e44da2d6794c92c6609cd8bf90670ab4fc1/src/main/java/hudson/tasks/Ant.java#L442
            if (o instanceof Jenkins || o instanceof Descriptor) {
                for (ToolDescriptor d : ToolInstallation.all()) {

                    String resetInstallers = null;
                    for (ToolInstallation t : d.getInstallations()) {
                        if (t.getName().contains("<") || t.getName().contains("&")) {
                            resetInstallers = t.getName();
                        }
                    }

                    if (resetInstallers != null) {
                        Logger.getLogger(Security624.class.getName()).log(Level.WARNING, "Resetting installations of '" + d.getDisplayName() + "' due to SECURITY-624 name: '" + resetInstallers + "' (and possibly others)");
                        try {
                            Object newInstance = Array.newInstance(d.clazz, 0);
                            Method setter = d.getClass().getMethod("setInstallations", newInstance.getClass());
                            setter.invoke(d, newInstance);
                        } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                            Logger.getLogger(Security624.class.getName()).log(Level.SEVERE, "Failed to reset installations of '" + d.getDisplayName() + "' for SECURITY-624, possibly unsafe values set!");
                        }
                    }
                }
            }
        }
    }
}
