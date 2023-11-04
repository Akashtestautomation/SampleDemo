package PageRepository;

import java.util.ArrayList;
import java.util.Iterator;

public class Logic {

    public static void main(String[] args) {

         int num=13;boolean flag=true;
         for(int i=2;i<num/2;i++)
         {
              if(num%i==0)
              {
                  flag=false;
                  break;
              }
         }
         if(flag)
             System.out.println("prime");
               else
                 System.out.println("No prime");


        System.out.println();           //Output : CoreJavajspservletsjdbcstrutshibernatespring
    }

}
