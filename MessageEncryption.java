import java.io.*;
import java.util.*;
import java.util.Scanner;
public class MessageEncryption
{
  public static String encoder(String msg){
    String encoded="";
    //the shift in characters in the encoded messege
    int posShift = (int)(Math.random()*94)+1;
    //the character that will replace 'e' in the encoded messege
    int replaceE = (int)(Math.random()*94)+32;
    //if the replace e value is the same as e it will reroll the replacement
    while ((char)replaceE == ('e')){
      replaceE = (int)(Math.random()*94)+32;
    }


    int count=0;
    for (int i = 0; i < msg.length(); i++){
        if (msg.charAt(i)=='e')
            count++;
    }
    
    int[] eArray = new int [count];

    int x = 0;
    for(int i = 0; i < msg.length(); i++){
      if(msg.charAt(i)=='e'){
        eArray[x] = i;
        x++;
        if ((replaceE+posShift)>126)
          encoded += (char)((replaceE)+(posShift)-94);
        else
          encoded += (char)((replaceE)+(posShift));
      }
      else{
        if ((msg.charAt(i)+posShift)>126)
          encoded += (char)((int)msg.charAt(i)+posShift-94);
        else
          encoded += (char)((int)msg.charAt(i)+posShift);
      }
    }
    String key = (posShift+":"+replaceE+":"+Arrays.toString(eArray));

    return("Original Message: "+msg+"\n\nEncoded Message: "+encoded+"\nKey: "+key);
  }
  
  public static String Decode(String msg, String key){
    String decodeMsg = "";
    int pS = key.indexOf(':');
    int rE = key.indexOf(':', pS + 1);
    int posShift = Integer.parseInt(key.substring(0, pS));
    int replaceE = Integer.parseInt(key.substring(pS + 1, rE));
    String arr = key.substring(rE + 1, key.length());
    String[] items = arr.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
    int numberOfItems=0;
    if (!items[0].equals(""))
      numberOfItems = items.length;
    int[] results = new int[numberOfItems];
  
        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //NOTE: write something here if you need to recover from formatting errors
            }
        }
    for(int i = 0; i < msg.length(); i++){
      if ((msg.charAt(i)-posShift)<32)
        decodeMsg += (char)((int)msg.charAt(i)-posShift+94);
      else
        decodeMsg += (char)((int)msg.charAt(i)-posShift);
    }
    if (results.length > 0){
      for (int i = 0; i < items.length; i++){
        decodeMsg = (decodeMsg.substring(0, results[i]) + "e" + decodeMsg.substring(results[i] + 1));
      }
    }
   return ("\nDecoded Message: " + decodeMsg);     
  }

 

  public static void main(String[] args)
  {
    Scanner scan = new Scanner(System.in);
    System.out.println("Type in the message to be encoded/decoded");
    System.out.println(encoder(scan.nextLine()));
    System.out.println(Decode(scan.nextLine(), scan.nextLine()));
     
  }
}