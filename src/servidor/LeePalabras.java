package servidor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jorge Ortega Hernandez
 */
public class LeePalabras {
    String cadena[];
    public LeePalabras(String ruta){
        int i=0;
        try{
            FileReader f = new FileReader(ruta);
            BufferedReader b = new BufferedReader(f);
            while((b.readLine())!=null) {
                i++;
            }
            f.close();
            b.close();
            FileReader fR=new FileReader(ruta);
            BufferedReader bR = new BufferedReader(fR);
            cadena=new String[i];
            for(int j=0;j<i;j++){
                cadena[j]=bR.readLine();
            }
            bR.close();
            fR.close();
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
