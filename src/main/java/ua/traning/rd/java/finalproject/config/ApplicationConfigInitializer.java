package ua.traning.rd.java.finalproject.config;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

public class ApplicationConfigInitializer implements ServletContainerInitializer {
	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
//		JavaConfigServlet servlet = new JavaConfigServlet();
//		ServletRegistration.Dynamic servletConfig = ctx.addServlet("JavaConfigSrvlet", servlet);
//		ServletRegistration.Dynamic servletConfig = ctx.addServlet("JavaConfigSrvlet", JavaConfigServlet.class);
//		servletConfig.addMapping("/java");

//		ctx.addServlet("JavaConfigServlet", GetAllAccounts.class).addMapping("/accounts");
//		ctx.addServlet("JavaConfigServlet", XMLConfigServlet.class).addMapping("/xml");
    }
}