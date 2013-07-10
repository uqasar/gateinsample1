package chapter02.code.listing;

import com.sun.jndi.ldap.Connection;
import org.w3c.dom.Element;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ProcessAction;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.MimeResponse;
import javax.sql.DataSource;

/**
 * UseRegistrationPortlet class extends the GenericPortlet abstract class to
 * implement the portlet functionality.
 * 
 * @author asarin
 * 
 */
public class UserRegistrationPortlet extends GenericPortlet {
	private String defaultEmail;

	/*
	 * Overrides the init method of the GenericPortlet class to obtain the value
	 * of the defaultEmail initialization parameter specified in portlet.xml
	 * file. (non-Javadoc)
	 * 
	 * @see javax.portlet.GenericPortlet#init()
	 */
	public void init() {
		defaultEmail = getPortletConfig().getInitParameter("defaultEmail");
	}

	/**
	 * Renders the registration form or success JSP based on the value of
	 * request attribute actionStatus.
	 * 
	 * @param request
	 * @param response
	 * @throws PortletException
	 * @throws IOException
	 */
	@RenderMode(name = "VIEW")
	public void renderForm(RenderRequest request, RenderResponse response) throws PortletException, IOException {

        String usersGrid = this.getUsers();
        request.setAttribute("usersGrid",usersGrid);

		// -- dispatch request to success.jsp if actionStatus is success
		if ("success".equalsIgnoreCase((String) request.getAttribute("actionStatus"))) {
			PortletURL homeUrl = response.createRenderURL();
			
			request.setAttribute("homeUrl", homeUrl);
			getPortletContext().getRequestDispatcher("/WEB-INF/jsp/success.jsp").include(request, response);
			return;
		}
		// -- create action and render URLs for the registration form.
		// -- Reset hyperlink fires a render request and Submit button
		// -- results in an action request
		PortletURL registerUserActionUrl = response.createActionURL();
		registerUserActionUrl.setParameter(ActionRequest.ACTION_NAME,"registerUserAction");
		PortletURL resetRenderUrl = response.createRenderURL();
		request.setAttribute("registerUserActionUrl", registerUserActionUrl);
		request.setAttribute("resetRenderUrl", resetRenderUrl);

		// -- if actionStatus is error then show the registration form populated
		// -- with values that were entered by the user
		if (!"error".equalsIgnoreCase((String) request.getAttribute("actionStatus"))) {request.setAttribute("email", defaultEmail);
		}
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/registrationForm.jsp").include(request, response);
	}

	/**
	 * Registers the user with the system. The current implementation of this
	 * method doesn't save user information.
	 * 
	 * @param request
	 * @param response
	 * @throws PortletException
	 * @throws IOException
	 */
	@ProcessAction(name = "registerUserAction")
	public void registerUser(ActionRequest request, ActionResponse response)	throws PortletException, IOException {
		String email = request.getParameter("email");
		// --set the information entered by the user on the registration
		// --form as request attribute.
		// -- NOTE : You can't transfer complex objects
		// -- from action request to render request using setRenderParameter
		//-- method
		request.setAttribute("user", new User(
				request.getParameter("firstName"), request
						.getParameter("lastName"), email));
		
		//-- if email is not entered, show an error message. the
		//-- message is read from the resource bundle and forwarded
		//-- to render request as request attribute
		if (email == null || email.trim().equals("")) {
			ResourceBundle bundle = getPortletConfig().getResourceBundle(
					request.getLocale());
			request.setAttribute("errorMsg", bundle
					.getString("email.errorMsg.missing"));
			
			//--set actionStatus to error
			request.setAttribute("actionStatus", "error");
		} else {
			// --save the user information in the database
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            this.saveUser(firstName,lastName,email);

            // -- to show the success page, pass the information of success to
            request.setAttribute("actionStatus", "success");
		}
	}


	/**
	 * Renders the preferences page for the portlet.
	 * 
	 * @param request
	 * @param response
	 * @throws PortletException
	 * @throws IOException
	 */
	@RenderMode(name = "EDIT")
	public void renderPrefs(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getPortletContext()
				.getRequestDispatcher("/WEB-INF/jsp/preferences.jsp").include(
						request, response);
	}

	/**
	 * Renders the help page for the portlet.
	 * 
	 * @param request
	 * @param response
	 * @throws PortletException
	 * @throws IOException
	 */
	@RenderMode(name = "HELP")
	public void renderHelp(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		getPortletContext().getRequestDispatcher("/WEB-INF/jsp/help.jsp")
				.include(request, response);
	}

    @Override
    public void doHeaders(RenderRequest request, RenderResponse response)
    {
        Element css = response.createElement("link");
        css.setAttribute("id", "jqueryDemo");
        css.setAttribute("type", "text/css");
        css.setAttribute("rel", "stylesheet");
        css.setAttribute("href", request.getContextPath() + "/css/jqueryDemo.css");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, css);

        Element jQuery = response.createElement("script");
        jQuery.setAttribute("type", "text/javascript");
        jQuery.setAttribute("src", request.getContextPath() + "/js/jquery-1.4.2.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jQuery);

        Element jQueryUI = response.createElement("script");
        jQueryUI.setAttribute("type", "text/javascript");
        jQueryUI.setAttribute("src", request.getContextPath() + "/js/jquery-ui-1.8.1.custom.min.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, jQueryUI);

        Element myJS = response.createElement("script");
        myJS.setAttribute("type", "text/javascript");
        myJS.setAttribute("src", request.getContextPath() + "/js/myJS.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, myJS);

        //dhtmlxcommon

        Element dhtmlxcommon = response.createElement("script");
        dhtmlxcommon.setAttribute("type", "text/javascript");
        dhtmlxcommon.setAttribute("src", request.getContextPath() + "/js/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxcommon.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, dhtmlxcommon);

        //dhtmlxGrid

        Element cssdhtmlxGrid = response.createElement("link");
        cssdhtmlxGrid.setAttribute("id", "dhtmlxgrid");
        cssdhtmlxGrid.setAttribute("type", "text/css");
        cssdhtmlxGrid.setAttribute("rel", "stylesheet");
        cssdhtmlxGrid.setAttribute("href", request.getContextPath() + "/js/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.css");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, cssdhtmlxGrid);

        Element cssdhtmlxgrid_skins = response.createElement("link");
        cssdhtmlxgrid_skins.setAttribute("id", "dhtmlxgrid_skins");
        cssdhtmlxgrid_skins.setAttribute("type", "text/css");
        cssdhtmlxgrid_skins.setAttribute("rel", "stylesheet");
        cssdhtmlxgrid_skins.setAttribute("href", request.getContextPath() + "/js/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid_skins.css");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, cssdhtmlxgrid_skins);

        Element dhtmlxGrid = response.createElement("script");
        dhtmlxGrid.setAttribute("type", "text/javascript");
        dhtmlxGrid.setAttribute("src", request.getContextPath() + "/js/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgrid.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, dhtmlxGrid);

        //dhtmlxgridcell

        Element dhtmlxgridcell = response.createElement("script");
        dhtmlxgridcell.setAttribute("type", "text/javascript");
        dhtmlxgridcell.setAttribute("src", request.getContextPath() + "/js/dhtmlxSuite/dhtmlxGrid/codebase/dhtmlxgridcell.js");
        response.addProperty(MimeResponse.MARKUP_HEAD_ELEMENT, dhtmlxgridcell);
    }


     public String getUsers(){
         String usersGrid="";

         try{

             DataSource dataSource = (DataSource) new InitialContext().lookup("java:/uqasardb");
             java.sql.Connection conn = dataSource.getConnection();

             PreparedStatement pstmt;

             conn.setAutoCommit(false);
             pstmt = conn.prepareStatement("select * from users");

             ResultSet rs = pstmt.executeQuery();

             usersGrid =   "<textarea id=\"str_xml\" style=\"display:none;\"><rows>";
             while (rs.next()) {

                 usersGrid += "<row id='"+rs.getString("user_id")+"'><cell>"+rs.getString("name")+"</cell><cell>"+rs.getString("surname")+"</cell></row>";
             }
             usersGrid +=   "</rows></textarea>";
             pstmt.execute();
             conn.commit();
             pstmt.close();
             conn.close();


         }   catch(Exception e){

             System.out.println("Exception Message"+e.toString());

         }
         return usersGrid;
     }

     private void saveUser(String firstName,String lastName, String email){

         try{

             DataSource dataSource = (DataSource) new InitialContext().lookup("java:/uqasardb");
             java.sql.Connection conn = dataSource.getConnection();

             PreparedStatement pstmt;

             conn.setAutoCommit(false);

             pstmt = conn.prepareStatement("INSERT INTO users(name,surname,mail) VALUES ('"+firstName+"','"+lastName+"','"+email+"')");

             pstmt.execute();
             conn.commit();
             pstmt.close();
             conn.close();

         }   catch(Exception e){

             System.out.println("Exception Message"+e.toString());

         }

     }
}
