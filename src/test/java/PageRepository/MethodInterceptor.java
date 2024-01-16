package PageRepository;

import Drivers.EnvironmentVariables;
import Methods.GenericMethod;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodInterceptor extends GenericMethod implements IMethodInterceptor{


    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> method, ITestContext context)
    {
        List<Map<String,String>> list=null;
        try {
             list= ReadExcelSheet(EnvironmentVariables.ExcelsheetPath,"TestCase");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<IMethodInstance> res=new ArrayList<>();
        for(int i=0;i<method.size();i++) {
           for(int j=0;j<list.size();j++){
               if(method.get(i).getMethod().getMethodName().equalsIgnoreCase(list.get(j).get("Test_Case"))) {
                   if(list.get(j).get("ExecutionType").equalsIgnoreCase("Y")){
                       method.get(i).getMethod().setDescription(list.get(i).get("ScenarioName"));
                       res.add(method.get(i));
                   }
               }
           }
        }
        return  res;
    }

}
