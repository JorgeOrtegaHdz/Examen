package servidor;
import java.awt.Point;
import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Jorge Ortega Hernandez
 */
public class Tablero  implements Serializable{
    public char tablero[][]=new char[16][16];
    private String palabra=new String();
    public String paBuscar[];
    /*private int izquierda=0,derecha=1,arriba=2,abajo=3;
    private int ,PrimerCuadrante=4,SegundoCuadrante=5,TercerCuadrante=6,CuartoCuadrante=7;*/
      
    public Tablero(String palabras[]){
        paBuscar=palabras;
        int forma=0;
        Point pos=new Point();
        
        if(palabras.length==0){
            System.out.println("No hay palabras");
        }else{
            for(int i=0;i<palabras.length;i++){
                for(;;){
                    int posibleForma[]={0,1,2,3,4,5,6,7};
                    palabra=palabras[i];
                    pos.setLocation(dameAleatorio(16), dameAleatorio(16));
                    System.out.println("Pos: "+pos+" palabra: "+palabra);
                    forma=defineForma(posibleForma,pos);
                    if(pruebaEspacio(pos,forma)){
                        introduce(pos,forma);
                        break;
                    }else{
                        continue;
                    }
                }
                /*for(int x=0;x<16;x++){
                    for(int y=0;y<16;y++){
                        System.out.print(tablero[y][x]);
                    }
                    System.out.println();
                }*/
            }
            rellena();
        }
    }
    
    private boolean pruebaEspacio(Point pos,int forma){
        boolean ban=true;
        int k,h;
        switch(forma){
            case 0:
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    if(tablero[i][pos.y]!=0)
                        if(palabra.charAt(pos.x-i)!=tablero[i][pos.y])
                            ban=false;
                }
            break;
            case 1:
                k=0;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    if(tablero[i][pos.y]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    k++;
                }
            break;
            case 2:
                for(int i=pos.y;i>=(pos.y-(palabra.length()-1));i--){
                    if(tablero[pos.x][i]!=0)
                        if(palabra.charAt(pos.y-i)!=tablero[i][pos.y])
                            ban=false;
                }
            break;
            case 3:
                k=0;
                for(int i=pos.y;i<=((palabra.length()+pos.y)-1);i++){
                    if(tablero[pos.x][i]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    k++;
                }
            break;
            case 4:
                k=0;
                h=pos.y;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    if(tablero[i][h]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    k++;
                    h--;
                }
            break;
            case 5:
                k=0;
                h=pos.y;
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    if(tablero[i][h]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    h--;
                    k++;
                }
            break;
            case 6:
                k=0;
                h=pos.y;
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    if(tablero[i][h]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    h++;
                    k++;
                }
            break;
            case 7:
                k=0;
                int j=pos.y;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    if(tablero[i][j]!=0)
                        if(palabra.charAt(k)!=tablero[i][pos.y])
                            ban=false;
                    j++;
                    k++;
                }
            break;
        }
        return ban;
    }
    
    private void rellena(){
        for(int i=0;i<16;i++){
            for(int j=0;j<16;j++){
                if(tablero[j][i]==0){
                    //System.out.print("original: "+tablero[j][i]);
                    tablero[j][i]=(char)(65+dameAleatorio(25));
                    //System.out.print(" nueva: "+tablero[j][i]+"\n");
                }
            }
        }
    }
    
    private void introduce(Point pos,int forma){
        int k,h;
        //System.out.println("Introducir en: "+pos+" forma: "+forma);
        switch(forma){
            case 0:
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    tablero[i][pos.y]=palabra.charAt(pos.x-i);
                }
            break;
            case 1:
                k=0;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    tablero[i][pos.y]=palabra.charAt(k);
                    k++;
                }
            break;
            case 2:
                for(int i=pos.y;i>=(pos.y-(palabra.length()-1));i--){
                    tablero[pos.x][i]=palabra.charAt(pos.y-i);
                }
            break;
            case 3:
                k=0;
                for(int i=pos.y;i<=((palabra.length()+pos.y)-1);i++){
                    tablero[pos.x][i]=palabra.charAt(k);
                    k++;
                }
            break;
            case 4:
                k=0;
                h=pos.y;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    tablero[i][h]=palabra.charAt(k);
                    k++;
                    h--;
                }
            break;
            case 5:
                k=0;
                h=pos.y;
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    tablero[i][h]=palabra.charAt(k);
                    h--;
                    k++;
                }
            break;
            case 6:
                k=0;
                h=pos.y;
                for(int i=pos.x;i>=(pos.x-(palabra.length()-1));i--){
                    tablero[i][h]=palabra.charAt(k);
                    h++;
                    k++;
                }
            break;
            case 7:
                k=0;
                int j=pos.y;
                for(int i=pos.x;i<=((palabra.length()-1)+pos.x);i++){
                    //System.out.println("i: "+i+"k: "+k+"j: "+j+"palabra: "+palabra+"punto: "+pos);
                    tablero[i][j]=palabra.charAt(k);
                    j++;
                    k++;
                }
            break;
            default:
                JOptionPane.showMessageDialog(null, "Error al introducir palabra");
            break;
        }
    }
    
    private int dameAleatorio(int n){
        Random numAlea=new Random();
        n=numAlea.nextInt(n);
        return n;
    }
    
    private int defineForma(int posible[],Point pos){
        int forma=0,tam=posible.length-1;
        do{
            if(tam<=0){
                System.out.println("Error en el numero de tam");
                System.exit(-1);
            }
            int aux=dameAleatorio(tam);
            forma=posible[aux];
            for(int i=aux;i<(posible.length-1);i++){
                posible[i]=posible[i+1];
            }
            //System.out.println("posibles: "+posible[0]+posible[1]+posible[2]+posible[3]+posible[4]+posible[5]+posible[6]+posible[7]);
            tam--;
        }while(!(valida(forma,pos)));
        return forma;
    }
    
    private boolean valida(int forma,Point pos){
        boolean ban=true;
        switch(forma){
            case 0:
                if(pos.x-(palabra.length()-1)<0){
                    System.out.println("No valida para izquierda");
                    ban=false;
                }
            break;
            case 1:
                if(pos.x+(palabra.length()-1)>15){
                    System.out.println("No valida para derecha");
                    ban=false;
                }
            break;
            case 2:
                if(pos.y-(palabra.length()-1)<0){
                    System.out.println("No valida para arriva");
                    ban=false;
                }
            break;
            case 3:
                if(pos.y+(palabra.length()-1)>15){
                    System.out.println("No valida para abajo");
                    ban=false;
                }
            break;
            case 4:
                if(!(valida(1,pos) && valida(2,pos))){
                    ban=false;
                }
            break;
            case 5:
                if(!(valida(0,pos) && valida(2,pos))){
                    ban=false;
                }
            break;
            case 6:
                if(!(valida(0,pos) && valida(3,pos))){
                    ban=false;
                }
            break;
            case 7:
                if(!(valida(1,pos) && valida(3,pos))){
                    ban=false;
                }
            break;
            default:
                JOptionPane.showMessageDialog(null, "Error al generarar el numero aleatorio");
            break;
        }
        return ban;
    }
}
