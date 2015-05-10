<%@ page import="com.google.api.client.auth.oauth2.Credential" %>
<%@ page import="com.google.api.services.mirror.model.Contact" %>
<%@ page import="com.nbcu.medialabs.MirrorClient" %>
<%@ page import="com.nbcu.medialabs.WebUtil" %>
<%@ page import="com.nbcu.medialabs.MainServlet" %>
<%@ page import="com.nbcu.medialabs.AuthUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.api.services.mirror.model.TimelineItem" %>
<%@ page import="com.google.api.services.mirror.model.Subscription" %>
<%@ page import="com.google.api.services.mirror.model.Attachment" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<%
  String userId = AuthUtil.getUserId(request);
  String appBaseUrl = WebUtil.buildUrl(request, "/");

  Credential credential = AuthUtil.getCredential(userId);

%>

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>CNBC News Alerts</title>
    <link href='http://fonts.googleapis.com/css?family=Ruda:400,700,900' rel='stylesheet' type='text/css'>
  </head>

  <body style="padding:0; margin:0; font-family: 'Ruda', sans-serif;">
  <div style="background:#084863;">
  <div style="width:970px; height:70px; margin:0 auto;"><a href="http://www.cnbc.com"><img id="cnbc-logo" style="padding:10px;" src="http://fm.cnbc.com/applications/cnbc.com/staticcontent/img/cnbc-hdr-logo.png" width="189" height="49" title="CNBC" alt="CNBC"></a></div>
  <div style="background:#032231; height:29px;"></div>
 </div>
  </br>
  <div style="width:970px; margin:0 auto;">
  <h1>NBC News Alerts Mission Control  </h1>
     
	<div>We invite you to try our new service specifically for users of Glass.</div>
	<div></div>
	<div>Please click "Get it on GLASS" below to start using, receive latest CNBC news alerts that will deliver CNBC news information directly to Glass™ device.</div>
	<div></div>
	<br>
	<div>The sign in process will involve authenticating via your Google Account at which time Google will be asking you to:
	  <ul>
	    <li>Allow us to add items to your Glass timeline</li>
	    <li>Allow us to retrieve the email address tied to your Glass device.</li>
      </ul>
    </div>
<!--	<div>Also, you may use "Connect My Glass" to unsubscribe and stop receiving these messages.</div>-->
	<div></div>
	<div>Glass is a trademark of Google Inc.</div></br>
    <form action="<%= WebUtil.buildUrl(request, "/main") %>" method="post">
	    <input type="hidden" name="operation" value="triggerCNBCNews">
	    <input type="image" src="https://developers.google.com/glass/images/buttons/getitonglass_172x60_action_button.png">
	</form>
	   
	<h2>CNBC News Activation</h2>
	<form action="<%= WebUtil.buildUrl(request, "/main") %>" method="post">
	    <input type="hidden" name="operation" value="triggerCNBCNews">
	    <button class="btn btn-block" type="submit">Send me the latest CNBC News</button>
	</form>

  </div>
  
  <p>&nbsp;</p>
  <div class="sec-footer-wrapper" style="position:absolute; bottom:0; width:100%; background:#032231;">
  <div class="sec-footer-content" style="width:970px; margin:0 auto; color: #48565f;"><a href="/" title="CNBC Homepage" style="background: transparent url('http://fm.cnbc.com/applications/cnbc.com/staticcontent/img/footer-sprite.png') no-repeat -2px -54px; display: inline-block; float: left; height: 58px; width: 70px; margin-right: 30px;"></a><div class="copyright-info"><a href="/id/16061983/"><div class="data-provider"></div></a><div class="footer-disclaimer">
    <p>Data is a real-time snapshot *Data is not delayed for Glass<br>
      Global Business and Financial News, Stock Quotes, and Market Data and Analysis</p><p>© 2014 CNBC LLC.  All Rights Reserved.</p><p><a href="http://www.nbcuni.com/" style="display: inline-block; width: 196px; height: 16px; background: transparent url('http://fm.cnbc.com/applications/cnbc.com/staticcontent/img/footer-sprite.png') no-repeat 0px -36px;"></a></p></div></div></div></div>

  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  </body>
</html>
