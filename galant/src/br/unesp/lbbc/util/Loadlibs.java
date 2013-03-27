

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
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
    	
    	File tempFile = null;
    	
        //verifica plataforma e decide qual lib. vai usar
    	String bitness = System.getProperty("os.arch");
        String osType = System.getProperty("os.name");
        String nativeLib;
      
        if(osType.contains("win") && bitness.contains("32")){
            nativeLib = "binaries-windows-i586"; //win
        }
        else if(osType.contains("win") && bitness.contains("64")){
            nativeLib = "binaries-windows-amd64"; //win
        }
        else if(osType.contains("mac")){
            nativeLib="binaries-macosx";//mac
        }
        else if(osType.contains("linux") && bitness.contains("32")){
            nativeLib="binaries-linux-i586";//linux
        }
        
        else if(osType.contains("linux") && bitness.contains("64")){
            nativeLib="binaries-linux-amd64";//linux
        }
        
        else {
            nativeLib = "binaries-windows-amd64";
        }
        
        //cria uma lista com as libs
    
        String address = "/binaries/"+nativeLib;
        URL resourceUrl = URL.class.getResource(address);
        
        File folder=null;
        try {
			folder = new File(resourceUrl.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"Native Libs not found");
		}
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
				 
			//tempFile=null;
			File tempAdress=null;
		    InputStream in = null;
		    
		    
		    try {
				in = new FileInputStream(listOfFiles[i]);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"Native Libs not found");
				e.printStackTrace();
			}
		    try {
				tempFile = File.createTempFile("temp", ".lib");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,"Not allowed to save temp file");
				e.printStackTrace();
			}
		    FileOutputStream out = null;
		    
		    try {
				out = new FileOutputStream(tempFile);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null,"Not allowed to save temp file");
				e.printStackTrace();
			}
		    byte[] temp = new byte[4 * 1024];
		    int rc;
		    try {
		    while((rc = in.read(temp)) > 0)
				
					out.write(temp, 0, rc);
				
		            in.close();
		            out.close();
		            tempFile.deleteOnExit();
		            //renomeia arquivo temp?.lib
		            tempAdress= new File(tempFile.getParent()+"/"+listOfFiles[i].getName());
		            tempFile.renameTo(tempAdress);
				 
		    } catch (IOException e) {
		    	JOptionPane.showMessageDialog(null,"Native Libs not found");
				e.printStackTrace();
			}
        
		 
		    
        
        }
        
        
        //atualiza path para incluir endereco da lib
        String setPath = System.getProperty("java.library.path");
        setPath = setPath + System.getProperty("path.separator")+tempFile.getParent();
        System.setProperty("java.library.path", setPath );

        //forca reavaliacao do java.library.path
        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible( true );
            fieldSysPath.set(null, null);
        } catch (NoSuchFieldException ex) {
            //do somethig...
        } catch (SecurityException ex) {
            //do somethig...
        } catch (IllegalArgumentException ex) {
            //do somethig...
        } catch (IllegalAccessException ex) {
            //do somethig...
        }
        
        
			 
			 
		

    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadMyLibs();
    }
    
}
