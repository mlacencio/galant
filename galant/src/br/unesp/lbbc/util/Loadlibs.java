

package br.unesp.lbbc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import com.install4j.runtime.installer.platform.win32.FolderInfo;
import com.lowagie.text.pdf.codec.Base64.OutputStream;

/**
 *
 * @author castromaa
 */
public class Loadlibs {
    
	
	
    /**
     * p.s. in order to run this template, add 3 libraries (e.g. .dll, .lib, and .so)
     * to the resources application folder (e.g. "/myapp/application/resources/")
     */
       
    public static void loadMyLibs(){
    	
    //	String fileName = System.getProperty("user.home");
    //	File folderCytoscape = new File(fileName+"//.cytoscape");
    	
    	String fileName = System.getProperty("user.dir");
        File folderCytoscape = new File(fileName+"//plugins");
        	
    	
    	//verifica se as libs ja estao salvas
    	
    	
    	 //verifica plataforma e decide qual lib. vai usar
    	String bitness = System.getProperty("os.arch");
        String osType = System.getProperty("os.name");
        String nativeLib;
      
        if((osType.toLowerCase().contains("win")) && bitness.contains("32")){
            nativeLib = "binaries-windows-i586"; //win
        }
        else if((osType.toLowerCase().contains("win")) && bitness.contains("64")){
            nativeLib = "binaries-windows-amd64"; //win
        }
        else if(osType.toLowerCase().contains("mac")){
            nativeLib="binaries-macosx";//mac
        }
        else if(osType.toLowerCase().contains("linux") && bitness.contains("32")){
            nativeLib="binaries-linux-i586";//linux
        }
        
        else if(osType.toLowerCase().contains("linux") && bitness.contains("64")){
            nativeLib="binaries-linux-amd64";//linux
        }
        
        else {
            nativeLib = "binaries-windows-amd64";
        }
        
        //cria uma lista com as libs
        
        String address = "/binaries/"+nativeLib;
        URL resourceUrl = URL.class.getResource(address);
       
       File folder=null;
     /*  try {
			folder = new File(resourceUrl.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(e+"- Native Libs not found");
		}
        File[] listOfFiles = folder.listFiles();
        
        System.out.println("ponto 1");
        
       for (int i = 0; i < listOfFiles.length; i++) {
				 
    	 try {
			FileInputStream source = new FileInputStream(listOfFiles[i]);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        }	
       
    	*/
    	
    	String setPath = System.getProperty("java.library.path");
    	
    	setPath = setPath + System.getProperty("path.separator")+folderCytoscape;
    	System.setProperty("java.library.path", setPath );
        System.out.println(setPath);

        
        //forca reavaliacao do java.library.path
        try {
    Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
    fieldSysPath.setAccessible( true );
    fieldSysPath.set(null, null);
} catch (NoSuchFieldException ex) {
	JOptionPane.showMessageDialog(null,"Unable to set the environmental variable path. "+ ex);
} catch (SecurityException ex) {
	JOptionPane.showMessageDialog(null,"Unable to set the environmental variable path. "+ ex);
} catch (IllegalArgumentException ex) {
	JOptionPane.showMessageDialog(null,"Unable to set the environmental variable path. "+ ex);
} catch (IllegalAccessException ex) {
	JOptionPane.showMessageDialog(null,"Unable to set the environmental variable path. "+ ex);
}
        
        
    }
    
    
  
    public static void copyJar(){
    	
    	   	
    	
    	//copy jar to a temporary file and extract them
    	
    	String fileName = System.getProperty("user.home");
    	File folderCytoscape = new File(fileName+"//.cytoscape");
    	
    	String currentFile = System.getProperty("user.dir");
    	File source = new File(currentFile+"//plugins//galant.jar");
    	
    
    	try {
			FileUtils.copyFileToDirectory(source, folderCytoscape);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
   
    	//extrai os arquivos
    /*	File jarFile = new File(fileName+"//.cytoscape//galant.jar");
    	File folderKeep = (new File(fileName+"//.cytoscape//galant"));
    	try {
			extractJar(jarFile,folderKeep.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	File myJar = new File(folderCytoscape+"//galant.jar");
    	File extrairAqui = new File(folderCytoscape+"//galant");
    	
    	try {
			Runtime.getRuntime().exec("jar xf "+myJar+" "+extrairAqui );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    }

    private static void extractJar(File jar, String folderDest) throws java.io.IOException{
    	
    	java.util.jar.JarFile jarfile = new java.util.jar.JarFile(jar); 
        java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
        while(enu.hasMoreElements())
        {
            
       	
        	String destdir = folderDest;     
            java.util.jar.JarEntry je = enu.nextElement();

            
            java.io.File fl = new java.io.File(destdir +"//"+ je.getName());
            if(!fl.exists())
            {
                fl.getParentFile().mkdirs();
                fl = new java.io.File(destdir  +"//"+ je.getName());
            }

           			
            if(je.isDirectory())
            {
                continue;
            }
            
            java.io.InputStream is = jarfile.getInputStream(je);
            java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
            while(is.available()>0)
            {
                fo.write(is.read());
            }
            fo.close();
            is.close();
        }
    
    
    }
    
   
   
    
    
    
}
