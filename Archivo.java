import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.EventQueue;

public class Archivo extends JFrame{
    
    private JTextArea texto;
    private JButton guardar;
    private JPanel panel;
    private JButton abrirArchivo;
    private File archivo;
    private JFileChooser jfc;//este es el objeto que muestra la ventana para guardar y abrir archivos
    private JScrollPane jsp;
    
    public Archivo(){
        iniciar();
        iniciarComponentes();
        accionesBotones();
    }
    
    //metodo para iniciar las caracteristicas de la ventana del programa
    public void iniciar(){
        setVisible(true);
        setSize(500,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Archivos");
    }
    
    //metodo para iniciar los componentes internos de la ventana
    public void iniciarComponentes(){
        archivo = null;
        
        //se instancia el objeto, la ruta que esta como parametro es la direccion que abrira siempre que se muestre
        jfc = new JFileChooser("C:\\Users\\Angel\\Desktop");
        
        texto = new JTextArea();
        texto.setColumns(40);
        texto.setRows(20);
        
        //se instancia el objeto que permitirá hacer scroll a el JTextArea, es importante que el JTextArea se instancie primero
        jsp = new JScrollPane(texto);
        jsp.setBounds(3, 3, 20, 20);
        
        panel = new JPanel();
        panel.setSize(500,500);
        panel.setBackground(Color.gray);
        
        guardar = new JButton("Guardar");
        
        abrirArchivo = new JButton("Abrir Archivo");
        
        panel.add(jsp);
        panel.add(guardar);
        panel.add(abrirArchivo);
        
        add(panel);
    }
    
    //metodo para declarar las acciones de los botones
    public void accionesBotones(){
        abrirArchivo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //antes de abrir un archivo y mostralo borramos lo que esta escrito en el area de texto para que no se junte con el nuevo archivo
                texto.setText("");
                
                //abrimos el menu para seleccionar el archivo, el parametro es el padre de jfc puede ser null o un componente
                //como JPanel,JFrame
                jfc.showOpenDialog(null);
                
                //obtenemos el archivo que seleccionamos
                archivo = jfc.getSelectedFile();
                
                String linea;
                try {
                    //instanciamos el FileReader y el BufferedReader que seran los encargados de leer el archivo
                    FileReader fr = new FileReader(archivo);
                    BufferedReader br = new BufferedReader(fr);
                    
                    //obtenemos las lineas del archivo y las escribimos en el JTextArea con un salto de linea \n
                    while((linea = br.readLine())!=null){
                        texto.append(linea+"\n");
                    }
                    
                    fr.close();
                    br.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        guardar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //si no hemos seleccionado un archivo
                if(archivo == null){
                    //abrimos el jfc que nos mostrara una ventana para guardar archivos, el parametro es el padre de jfc puede ser null o un componente
                    //como JPanel,JFrame
                    jfc.showSaveDialog(null);
                    
                    //obtenemos el archivo
                    archivo = jfc.getSelectedFile();
                    
                    //si el achivo no existe lo crea
                    if(!archivo.exists()){
                        try {
                            archivo.createNewFile();
                        } catch (IOException ex) {
                            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                }
                //si el archivo ya habia sido seleccionado
                archivo = jfc.getSelectedFile();
                
                //escribimos en el archivo
                try {
                    //creamos a FileWriter y BufferedWriter que seran los encargados de escribir en el archivo1
                    FileWriter fw = new FileWriter(archivo);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    //obtener lineas separandolas por el saltado de linea \n
                    String[] linea = texto.getText().split("\n");
                    
                    //escribimos esas lineas en el archivo
                    for(int i=0; i<linea.length;i++){
                        bw.write(linea[i]);
                        //escribimos un salto de linea en el archivo
                        bw.newLine();
                    }
                    
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        
        //la clase EventQueue tiene el metodo estatico vacio invokeLater() que tiene de parametro un objeto de tipo Runnable
        //por lo que entiendo primero ejecuta el hilo principal del programa -main- y despues lo que hay en este metodo
        //esto sirve para que carguen todos los recursos de la maquina antes de mostrar la ventana porque si no aveces no se muestra
        //el area de texto ni los botones y se tiene que maximizar la ventana para que los muestre, 
        //IMPORTANTE LO DE ABAJO
        //prueba el programa comentando este metodo excepto la linea del -----Archivo a = new Archivo();----
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                Archivo a = new Archivo();
            }
        });
        
    }
    
}
