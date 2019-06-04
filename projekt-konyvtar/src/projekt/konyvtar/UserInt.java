/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt.konyvtar;

import java.util.*;

/**
 *
 * @author imaginifer
 */
public class UserInt {
    
    private DataBaseHandler handler;
    private DataBaseLister lister;
    
    public UserInt(){
        handler = new DataBaseHandler();
        lister = new DataBaseLister();
    }
    
    public void mainMenu(){
        do { 
            String[] ch={"Olvasók kezelése","Könyvek kezelése","Kölcsönzés","Kölcsönzési statisztikák"};
            int q = choosePath(ch);
            switch(q){
                case 1:
                    readerMenu();
                    break;
                case 2:
                    booksMenu();
                    break;
                case 3:
                    rentingMenu();
                    break;
                case 4:
                    statsMenu();
                    break;
                default:
                    break;
            }
            
        } while (!exit("a programból"));
    }
    
    private void readerMenu(){
        //olvasó létrehozása, keresése vagy deaktiválása
        boolean quit=false;
        do{
            String[] ch={"Új olvasó hozzásadása","Olvasó deaktiválása"};
            int q=choosePath(ch);
            switch(q){
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
    }
    
    private void booksMenu(){
        //könyv hozzáadása, törlése, példányok hozzáadása, selejtezése
        boolean quit=false;
        do{
            String[] ch={"Új könyv hozzáadása","Könyv új példányának hozzáadása"
                    ,"Könyv törlése a nyilvántartásból", "Könyv példányának selejtezése"};
            int q=choosePath(ch);
            switch(q){
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    //TODO
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
    }
    
    private void rentingMenu(){
        //kölcsönzés és visszahozatal
        boolean quit=false;
        do{
            String[] ch={"Könyv kikölcsönzése","Könyv visszaérkezése"};
            int q=choosePath(ch);
            switch(q){
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
    }
    
    private void statsMenu(){
        //olvasó és kölcsönzései, ugyanez az összesnél, ugynaz számszerűleg, könyv kikölcsönzései, 
        //legnépszerűbb könyv, átlagos kölcsönzési idő,
        boolean quit=false;
        do{
            String[] ch={"Olvasónál lévő könyv","Minden olvasónál lévő könyv"
                    ,"Olvasó összes eddigi kölcsönzése","Könyv összes korábbi kölcsönzése"
                    ,"Könyv átlagos kölcsönzési ideje","A legtöbbet kölcsönzött könyv"};
            int q=choosePath(ch);
            switch(q){
                case 1:
                    //TODO
                    break;
                case 2:
                    //TODO
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    //TODO
                    break;
                case 5:
                    //TODO
                    break;
                case 6:
                    //TODO
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
          
    }
    
    private int choosePath(String[] choices){
        boolean correct = false;
        int k = 0;

        do {
            for (int i = 0; i < choices.length; i++) {
                System.out.printf("   %-7d%s%n", (i + 1), choices[i]);
            }
            System.out.printf("   %-7d%s%n", 0, "Kilép");
            Scanner sc = new Scanner(System.in);
            try {
                String s = sc.nextLine();
                k = Integer.parseInt(s);
                if (k > 0 && k <= choices.length) {
                    correct = true;
                } else if (k==0){
                    correct = true;
                    k = 0;
                } else {
                    System.out.println("A bevitel sajnos érvénytelen, kérjük próbálja újra!");
                }
            } catch (NumberFormatException e) {
                System.out.println("A bevitel sajnos érvénytelen, kérjük próbálja újra!");
            }
        } while (!correct);
        return k;
    }
    
    private boolean exit(String loc){
        System.out.printf("Kilép %s? i/n", loc);
        Scanner sc=new Scanner(System.in);
        String f=sc.nextLine();
        return f.toLowerCase().equals("i");
                
    }
    
    private String textInput(String question){
        System.out.printf("Kérem, adja meg %s:%n", question);
        Scanner sc=new Scanner(System.in);
        return sc.nextLine();
    }
    
}
