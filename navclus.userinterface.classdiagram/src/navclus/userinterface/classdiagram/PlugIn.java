/*******************************************************************************
 * Copyright (c) 2007 UBC, SPL
 * All rights reserved.
 * 
 * Contributors:
 *     Seonah Lee - initial implementation
 *******************************************************************************/

package navclus.userinterface.classdiagram;

import navclus.userinterface.classdiagram.classfigure.ClassFigure;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class PlugIn extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "navclus.userinterface.classdiagram"; //$NON-NLS-1$
	
	// The shared instance
	private static PlugIn plugin;    
      
	/**
	 * The constructor
	 */
	public PlugIn() {
        // empty
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
    public void start(final BundleContext context) throws Exception {	
		super.start(context);
		plugin = this;
		setImage();
	}

	// salee: setImage
    public void setImage() {
    	getImageRegistry().put("int_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("int_obj.gif")));
    	getImageRegistry().put("enum_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("enum_obj.gif")));
    	getImageRegistry().put("class_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("class_obj.gif")));
    	getImageRegistry().put("methpub_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("methpub_obj.gif")));
    	getImageRegistry().put("methpri_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("methpri_obj.gif")));
    	getImageRegistry().put("methpro_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("methpro_obj.gif")));
    	getImageRegistry().put("methdef_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("methdef_obj.gif")));
    	getImageRegistry().put("field_public_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("field_public_obj.gif")));
    	getImageRegistry().put("field_private_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("field_private_obj.gif")));
    	getImageRegistry().put("field_protected_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("field_protected_obj.gif")));
    	getImageRegistry().put("field_default_obj", new Image(Display.getDefault(), ClassFigure.class.getResourceAsStream("field_default_obj.gif")));    	
    }
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
    public void stop(final BundleContext context) throws Exception {
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PlugIn getDefault() {
		return plugin;
	}
       
    /**
     * Returns a managed image for the given key.
     * These images must <b>not</b> be disposed by clients
     * @param key is an image id, see ProcessDesignerImages
     * @return a managed image
     * @throws IllegalArgumentException if no image for the given key could be found
     * @see de.spiritlink.procam.images.ImageRegistry
     */
    public Image getImage(final String key) {
        final Image result = getImageRegistry().get(key.toLowerCase());
        if (result == null) {
            final String msg = "Image for key" + key + " was null"; //$NON-NLS-1$ //$NON-NLS-2$
            throw new IllegalArgumentException(msg); 
        }
        return result;
    }
    
    /**
     * Returns an ImageDescriptor for the given key.
     * @param key is an image id, see ProcessDesignerImages
     * @return an image descriptor instance
     * @throws IllegalArgumentException if no ImageDescriptor for the given key could be found 
     * @see de.spiritlink.procam.images.ImageRegistry
     */
    public ImageDescriptor getImageDescriptor(final String key) {
        final ImageDescriptor result = getImageRegistry().getDescriptor(key);
        if (result == null) {
            final String msg = "ImageDescriptor for key" + key + " was null"; //$NON-NLS-1$ //$NON-NLS-2$
            throw new IllegalArgumentException(msg); 
        }
        return result;
    }

	/**
	 * @return The plugin's preference information.
	 */
	private static IPreferenceStore getPreferences() {
		return getDefault().getPreferenceStore();
	}
	
	/**
	 * @param key - The String key
	 * @return The preference from the preference system for the given key.
	 */
	public static boolean getBooleanPreference(String key) {
		return getPreferences().getBoolean(key);
	}

	/**
	 * @param key - The String key
	 * @return The preference from the preference system for the given key.
	 */
	public static String getPreference(String key) {
		return getPreferences().getString(key);
	}
    
	/**
	 * @return The workbench's active window's shell.
	 */
	public static Shell getDefaultShell() {
		return getDefault().getWorkbench().getActiveWorkbenchWindow()
				.getShell();
	}

}
