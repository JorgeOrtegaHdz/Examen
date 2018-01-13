package cliente;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import servidor.Tablero;

/**
 *
 * @author Jorge Ortega Hernandez
 */
public class Cliente_principal {
    public static void main(String[]args){
        JFrame palabras=new JFrame();
        /*String prueba[]={"HOLA","ADIOS","JORGE","ORTEGA","HERNANDEZ"};
        Tablero table=new Tablero(prueba);
        JPanel palabra[]=new JPanel[table.paBuscar.length];
        JLabel eti[]=new JLabel[table.paBuscar.length];
        palabras.setLayout(new GridLayout(12,12));
        for(int i=0;i<table.paBuscar.length;i++){
            eti[i]=new JLabel(table.paBuscar[i]);
            palabra[i]=new JPanel();
            palabra[i].add(eti[i]);
            palabras.add(palabra[i]);
        }
        InterfaceSopa sopa= new InterfaceSopa(table,null,null);
        palabras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sopa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        palabras.setSize(200, 500);
        sopa.setSize(720,720);
        palabras.setVisible(true);
        sopa.setVisible(true);*/
        
        //Conexion con el servidor
        try{
            String host=JOptionPane.showInputDialog(null,"Escribe la direccion del servidor: ");
            int pto=Integer.parseInt(JOptionPane.showInputDialog(null, "Escribe el puerto: "));
            Socket cl=new Socket(host,pto);
            JOptionPane.showMessageDialog(null, "Conexion Establecida");
            ObjectOutputStream oos=new ObjectOutputStream(cl.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
            Tablero table=(Tablero)ois.readObject();
            JPanel palabra[]=new JPanel[table.paBuscar.length];
            JLabel eti[]=new JLabel[table.paBuscar.length];
            palabras.setLayout(new GridLayout(12,12));
            for(int i=0;i<table.paBuscar.length;i++){
                eti[i]=new JLabel(revuelve(table.paBuscar[i]));
                palabra[i]=new JPanel();
                palabra[i].add(eti[i]);
                palabras.add(palabra[i]);
            }
            InterfaceSopa sopa= new InterfaceSopa(table,oos,ois,cl,palabra);
            palabras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            sopa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            palabras.setSize(200, 500);
            sopa.setSize(720,720);
            palabras.setVisible(true);
            sopa.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static String revuelve(String palabra){
        String dato="";
        char aux[]=palabra.toCharArray();
        Random numa=new Random();
        for(int i=0;i<aux.length/2;i++){
            int pos=numa.nextInt(aux.length);
            char letra=aux[pos];
            aux[pos]=aux[i];
            aux[i]=letra;
        }
        for(int i=0;i<aux.length;i++){
            dato=aux[i]+dato;
        }
        return dato;
    }
    
}
