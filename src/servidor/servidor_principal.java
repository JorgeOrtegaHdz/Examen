package servidor;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Jorge Ortega Hernandez
 */
public class servidor_principal {
    public static void main(String[]args){
        try{
            ServerSocket s=new ServerSocket(1234);
            JOptionPane.showMessageDialog(null, "Servicio iniciado... Esperando cliente");
            String ruta=JOptionPane.showInputDialog(null,"Escribe la ruta del archivo de las palabras: " , "C:\\Users\\jorge\\OneDrive\\Documentos\\NetBeansProjects\\Examen\\src\\datos.txt");
            Socket cl=s.accept();
            ObjectOutputStream oos=new ObjectOutputStream(cl.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
            LeePalabras palabras=new LeePalabras(ruta);
            Tablero table=new Tablero(palabras.cadena);
            oos.writeObject(table);
            oos.flush();
            oos.writeInt(palabras.cadena.length);
            oos.flush();
            for(;;){
                boolean ban=false;
                String palabra=ois.readUTF();
                for(int i=0;i<palabras.cadena.length;i++){
                    if(palabra.equals(palabras.cadena[i]))
                        ban=true;
                }
                oos.writeBoolean(ban);
                oos.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
