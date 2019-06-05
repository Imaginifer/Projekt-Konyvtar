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
        System.out.println("____||  Üdvözöljük a könyvtári adatbázis-kezelőben  ||____");
        do { 
            String[] ch={"Olvasók kezelése","Könyvek kezelése","Kölcsönzés","Kölcsönzési statisztikák"};
            int q = choosePath(ch,"___Főmenü___");
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
            String[] ch={"Új olvasó hozzáadása","Olvasó deaktiválása", "Olvasó újraaktiválása"};
            int q=choosePath(ch, "___Olvasók kezelése___");
            switch(q){
                case 1:
                    handler.addCustomer(textInput("az új olvasó nevét"));
                    break;
                case 2:
                    int nr2=-1;
                    nr2=lister.findCustomerIdByCustomerName(textInput("a deaktiválandó olvasó nevét"));
                    if(nr2!=-1){
                        handler.setCustomerStatus(nr2, false);
                    }
                    break;
                case 3:
                    int nr3=-1;
                    nr3=lister.findCustomerIdByCustomerName(textInput("az aktiválandó olvasó nevét"));
                    if(nr3!=-1){
                        handler.setCustomerStatus(nr3, true);
                    }
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
            int q=choosePath(ch,"___Könyvek kezelése___");
            switch(q){
                case 1:
                    String a=textInput("a könyv írójának nevét");
                    String t=textInput("a könyv címét");
                    handler.addBook(a, t);
                    break;
                case 2:
                    int nr2=-1;
                    nr2=lister.findBookId(textInput("a könyv címét"));
                    if(nr2!=-1){
                        handler.addItem(nr2);
                    }
                    break;
                case 3:
                    int nr3=-1;
                    nr3=lister.findBookId(textInput("a törlendő könyv címét"));
                    handler.removeBook(nr3);
                    break;
                case 4:
                    scrapItem();
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
            int q=choosePath(ch,"___Kölcsönzés___");
            switch(q){
                case 1:
                    borrowItem();
                    break;
                case 2:
                    returnItem();
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
    }
    
    private void returnItem(){
        String olv=textInput("az olvasó nevét");
        int readerId=-1;
        readerId=lister.findCustomerIdByCustomerName(olv);
        if(readerId!=-1){
            System.out.println("Ezeket a könyveket kölcsönözte ki:");
            lister.printOneCustomerAndHisActiveRentalsByCustomerId(readerId);
            String booktitle=textInput("a visszahozott könyv címét");
            int itemId=-1;
            itemId=lister.findBookOut(booktitle, readerId);
            if(itemId!=-1){
                handler.returnBook(itemId);
            }
            
        }
    }
    
    private void scrapItem(){
        String title=textInput("a selejtezzni szándékozott könyv címét");
        List<Integer> l=new ArrayList<>();
        l=lister.findListOfInventoryIdsByTitle(title);
        if(!l.isEmpty()){
            boolean correct=false;
            int itemId=-1;
            do{
                System.out.println("A könyv rendelkezésre álló példányainak azonosítói:");
                for (int i = 0; i < l.size(); i++) {
                    System.out.print(l.get(i)+(i==l.size()-1?"":", "));
                }
                System.out.println("");
                String nr=textInput("a selejtezendő példány számát");

                try {
                    itemId=Integer.parseInt(nr);
                    if(l.contains(itemId)){
                        correct=true;
                    }else{
                        System.out.println("Kérem, szereplő azonosítót adjon meg!");
                        correct=false;
                    }
                } catch (NumberFormatException e) {
                    correct=false;
                    System.out.println("Kérem, számot adjon meg!");
                }
            }while(!correct);
            handler.scrapItem(itemId);
        }
    }
    private void borrowItem(){
        String olv=textInput("az olvasó nevét");
        int readerId=-1;
        readerId=lister.findCustomerIdByCustomerName(olv);
        if(readerId!=-1){
            String title=textInput("a kölcsönözni szándékozott könyv címét");
            List<Integer> l=new ArrayList<>();
            l=lister.findListOfInventoryIdsByTitle(title);
            if(!l.isEmpty()){
                boolean correct=false;
                int itemId=-1;
                do{
                    System.out.println("A könyv rendelkezésre álló példányainak azonosítói:");
                    for (int i = 0; i < l.size(); i++) {
                        System.out.print(l.get(i)+(i==l.size()-1?"":", "));
                    }
                    System.out.println("");
                    String nr=textInput("a kívánt példány számát");
                
                    try {
                        itemId=Integer.parseInt(nr);
                        if(l.contains(itemId)){
                            correct=true;
                        }else{
                            System.out.println("Kérem, szereplő azonosítót adjon meg!");
                            correct=false;
                        }
                    } catch (NumberFormatException e) {
                        correct=false;
                        System.out.println("Kérem, számot adjon meg!");
                    }
                }while(!correct);
                handler.rentABook(readerId, itemId);
            }
            
        }
    }
    
    private void statsMenu(){
        //olvasó és kölcsönzései, ugyanez az összesnél, ugynaz számszerűleg, könyv kikölcsönzései, 
        //legnépszerűbb könyv, átlagos kölcsönzési idő,
        boolean quit=false;
        do{
            String[] ch={"Olvasónál lévő könyv","Minden olvasónál lévő könyv"
                    ,"Olvasó összes eddigi kölcsönzése","Könyv összes korábbi kölcsönzése"
                    ,"Könyv átlagos kölcsönzési ideje","A legtöbbet kölcsönzött könyv"};
            int q=choosePath(ch, "___Kölcsönzési statisztikák___");
            switch(q){
                case 1:
                    int nr1=-1;
                    String nom = textInput("az olvasó nevét");
                    nr1=lister.findCustomerIdByCustomerName(nom);
                    if(nr1!=-1){
                        lister.printOneCustomerAndHisActiveRentalsByCustomerId(nr1);
                    }
                    break;
                case 2:
                    lister.printListOfCustomersAndTheirRenteds();
                    break;
                case 3:
                    int nr3=-1;
                    String nev = textInput("az olvasó nevét");
                    nr3=lister.findCustomerIdByCustomerName(nev);
                    if(nr3!=-1){
                        lister.printListOfRentedsByOneCustomer(nr3);
                    }
                    break;
                case 4:
                    int nr4=-1;
                    nr4=lister.findBookId(textInput("a könyv címét"));
                    if(nr4!=-1){        
                        lister.printListOfRentsByBookId(nr4);
                    }
                    break;
                case 5:
                    int nr5=-1;
                    nr5=lister.findBookId(textInput("a könyv címét"));
                    if(nr5!=-1){
                        lister.printAverageRentedTimeByBookId(nr5);
                    }
                    break;
                case 6:
                    lister.printMostPopularBook();
                    break;
                default:
                    quit=true;
                    break;
            }
        }while(!quit);
          
    }
    
    private int choosePath(String[] choices, String title){
        boolean correct = false;
        int k = 0;

        do {
            System.out.println(title);
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
