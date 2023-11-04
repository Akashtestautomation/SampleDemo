package TestGroup.SampleDemo;

import java.util.Scanner;

import org.checkerframework.checker.units.qual.Length;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
          //checking whether palindrome or not
      
    	 String str="MadaM";
         boolean flag=false;
          int left = 0, right = str.length() - 1;
          
          while(left < right)
          {
              if(str.charAt(left) != str.charAt(right))
              {
                  flag=false;
              }
              left++;
              right--;
          }
          flag=true;
      

          if(flag)
        	  System.out.println("Palindrome");else System.out.println("Not a Palindrome");
        	  
          
    }    

}