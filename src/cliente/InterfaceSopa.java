package cliente;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.Socket;
import java.util.Random;
import java.awt.Color;
import servidor.Tablero;

/**
 *
 * @author Jorge Ortega Hernandez
 */
public class InterfaceSopa extends JFrame implements MouseListener{
    private JButton botones[];
    private boolean ban=false,ban2=true;
    private Container contenedor;
    private GridLayout cuadricula;
    //private char palabra[]=new char[20];
    private Point pos;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JButton bufBoton[]=new JButton[25];
    private String bufPalabra="";
    private int ctrl=1,encontradas;
    private Paneltiempo util;
    Socket cl;
    JPanel extra[];
    //Genera la interface en base a botones en su mayoria vacios con un Layout
    //del tipo GridLayout de una matriz de 32 por 32 botones
    public InterfaceSopa(Tablero table,ObjectOutputStream os,ObjectInputStream oi,Socket cl,JPanel extra[]){
        super("Demostracion de GridLayout");
        oos=os;
        ois=oi;
        this.cl=cl;
        this.extra=extra;
        JPanel titulo=new JPanel();
        JPanel sopa=new JPanel();
        JLabel eti=new JLabel("___________Sopa de letras___________");
        util=new Paneltiempo();
        
        //vacio.setBorder(null);
        eti.setFont(new Font("Monospaced",Font.BOLD,30));
        cuadricula=new GridLayout(32,32);
        contenedor=getContentPane();
        titulo.setLayout(new BorderLayout());
        botones=new JButton[256];
        
        sopa.setLayout(cuadricula);
        setLayout(new BorderLayout());
        int i=0,k=0,h=0;
        for(int indice=0;indice<512;indice++){
            if(indice%32==0){
                for(int j=0;j<32;j++){
                    JButton vacio=new JButton();
                    vacio.setBorder(null);
                    vacio.setBackground(java.awt.Color.white);
                    sopa.add(vacio);
                }
            }
            if((indice%2)==0 && indice<512){
                //char l=(char)(65+numAlea.nextInt(26));
                char l=table.tablero[h][k];
                //System.out.println("Letra insertada: "+l+" h:"+h+" k:"+k+" i:"+i);
                botones[i]=new JButton(""+l);
                botones[i].setBorder(null);
                botones[i].setBackground(java.awt.Color.WHITE);
                botones[i].addMouseListener(this);
                sopa.add(botones[i]);
                if((h%15)==0 && h!=0){
                    k++;
                    h=0;
                }else{
                    h++;
                }
                i++;
            }else{
                JButton vacio=new JButton();
                vacio.setBorder(null);
                vacio.setBackground(java.awt.Color.white);
                sopa.add(vacio);
            }
        }
        
        titulo.add(eti,BorderLayout.CENTER);
        titulo.setBackground(java.awt.Color.yellow);
        add(titulo,BorderLayout.NORTH);
        add(sopa,BorderLayout.CENTER);
        add(util,BorderLayout.SOUTH);
        try{
            encontradas=ois.readInt();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //No se ocupa
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    //Sirve para controlar la accion cuando el boton izquierdo del mouse es oprimido
    @Override
    public void mousePressed(MouseEvent e) {
        ban=true;
    }
    //Sirve para controlar cuando el boton izquierdo del mouse se deja de oprimir
    @Override
    public void mouseReleased(MouseEvent e) {
        ban=false;
        ban2=true;
        try{
            oos.writeUTF(bufPalabra);
            oos.flush();
            if(ois.readBoolean()){
                encontradas--;
                for(int i=0;i<extra.length;i++){
                    JLabel et=(JLabel)((extra[i].getComponents())[0]);
                    if(bufPalabra.equals(et.getText())){
                        extra[i].setBackground(java.awt.Color.yellow);
                    }
                }
                if(encontradas==0){
                    JOptionPane.showMessageDialog(this, "A encontrado todas las palabras con un tiempo: "+util.tiem);
                    cl.close();
                    oos.close();
                    ois.close();
                    System.exit(0);
                }
            }else{
                for(int i=0;i<ctrl;i++){
                    bufBoton[i].setBackground(java.awt.Color.white);
                }
            }
        }catch(Exception s){
            s.printStackTrace();
        }
        bufPalabra="";
        ctrl=1;
        //JOptionPane.showMessageDialog(null, "Palabra: "+bufPalabra);
        /*for(int i=0;i<ctrl;i++){
            bufBoton[i].setBackground(java.awt.Color.white);
        }*/
    }
    //Selecciona la letra que sigue de la anteriror seleccionada
    @Override
    public void mouseEntered(MouseEvent evnt) {
        if(ban==true){
            JButton bs=(JButton)evnt.getSource();
            System.out.println(""+bs.getLocation());
            if(validaLetra(bs)){
                bs.setBackground(java.awt.Color.yellow);
                pos=bs.getLocation();
                bufPalabra=bufPalabra+bs.getText();
                bufBoton[ctrl]=bs;
                ctrl++;
                if(bufPalabra.length()==24){
                    JOptionPane.showMessageDialog(null, "No puede seleccionar mas de 25 letras a la vez");
                    mouseReleased(evnt);
                }
            }
            contenedor.validate();
        }
    }
    //Valida si la ubicacion de la letra seleccionada esta proxima a la anterior
    public boolean validaLetra(JButton bs){
        boolean sino=false;
        if(((pos.getX()==bs.getLocation().x)&&(pos.getY()==(bs.getLocation().y-38))) || ((pos.getY()==bs.getLocation().y)&&(pos.getX()==(bs.getLocation().x-44)))){
            sino=true;
        }else if(((pos.getX()==bs.getLocation().x)&&(pos.getY()==(bs.getLocation().y+38))) || ((pos.getY()==bs.getLocation().y)&&(pos.getX()==(bs.getLocation().x+44)))){
            sino=true;
        }else if(((pos.getX()==(bs.getLocation().x+44))&&((pos.getY()==(bs.getLocation().y+38))))||((pos.getY()==(bs.getLocation().y-38)) && (pos.getX()==(bs.getLocation().x+44)))){
            sino=true;
        }else if(((pos.getX()==(bs.getLocation().x-44))&&(pos.getY()==(bs.getLocation().y-38)))||((pos.getY()==(bs.getLocation().y+38)))&&(pos.getX()==(bs.getLocation().x-44))){
            sino=true;
        }
        return sino;
    }
    //Selecciona la primera letra que el ususario toma
    @Override
    public void mouseExited(MouseEvent evnt) {
        if(ban==true && ban2==true){
            JButton botonselection=(JButton)evnt.getSource();
            botonselection.setBackground(java.awt.Color.yellow);
            contenedor.validate();
            pos=botonselection.getLocation();
            bufPalabra=botonselection.getText();
            bufBoton[0]=botonselection;
            ban2=false;
        }
    }
    
}
