package moneyOK.test;

import java.util.Map;

import moneyOK.chart.ChartDAO;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class TestController extends AbstractDependencyInjectionSpringContextTests {
    protected String[] getConfigLocations() {  
        return new String[] { "classpath:/moneyOK/test/mvc-config.xml" };  
    }  
    private ChartDAO chartDAO;
    
	
	public ChartDAO getChartDAO() {
		return chartDAO;
	}


	public void setChartDAO(ChartDAO chartDAO) {
		this.chartDAO = chartDAO;
	}


	public void testSaveUser() throws Exception { 
		Map map = this.chartDAO.findMonthSumary(12,3);
		System.out.println(map);
    }
}
